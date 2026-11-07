/**
 * ClarityDeobfuscatorTool.java
 *
 * Autonomous Zero-Trace Bytecode Deobfuscator, String Decryptor, Control-Flow Restorer,
 * Semantic Remapper, and Java Source Reconstructor for Clarity 1.2.0 (Skidfuscator + ProGuard).
 */
import java.io.*;
import java.nio.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.zip.*;
import jdk.internal.org.objectweb.asm.*;
import jdk.internal.org.objectweb.asm.commons.*;
import jdk.internal.org.objectweb.asm.tree.*;
import jdk.internal.org.objectweb.asm.util.*;


public class ClarityDeobfuscatorTool {
    public static void main(String[] args) throws Exception {
        String inputJar = args[0];
        Path projectDir = Paths.get("/tmp/clarity-project");
        Path srcMainJava = projectDir.resolve("src/main/java");
        Path srcMainRes = projectDir.resolve("src/main/resources");
        Path outputDir = Paths.get("/working_dir/c_ea84c0a6e6aba073/artifacts/file_generation/ttl=63d/output");
        Files.createDirectories(srcMainJava);
        Files.createDirectories(srcMainRes);
        Files.createDirectories(outputDir);

        Map<String, ClassNode> purified = new LinkedHashMap<>();
        Map<String, byte[]> resourceFiles = new LinkedHashMap<>();

        try (ZipFile zf = new ZipFile(inputJar)) {
            Enumeration<? extends ZipEntry> entries = zf.entries();
            while (entries.hasMoreElements()) {
                ZipEntry ze = entries.nextElement();
                if (ze.isDirectory()) continue;
                String name = ze.getName();
                if (name.startsWith("ryzwriqrxnniuxrp/")) continue; // Drop obfuscator helper class completely
                byte[] data = zf.getInputStream(ze).readAllBytes();
                if (name.endsWith(".class")) {
                    if (((data[6] & 0xFF) << 8 | (data[7] & 0xFF)) > 61) {
                        data[6] = 0; data[7] = 61;
                    }
                    ClassReader cr = new ClassReader(data);
                    ClassNode cn = new ClassNode();
                    cr.accept(cn, ClassReader.SKIP_FRAMES);
                    TestAll530.purifyClassComplete(cn);
                    for (MethodNode mn : cn.methods) {
                        TestPeephole.optimizeLocals(mn);
                        fixMethodTypeCasts(cn, mn);
                        cleanIllegalTryCatchBlocks(mn);
                    }
                    purified.put(cn.name, cn);
                } else {
                    resourceFiles.put(name, data);
                }
            }
        }

        RunFullRemapTest.ClarityRemapper remapper = RunFullRemapTest.buildRemapper(purified);
        Map<String, ClassNode> remappedClasses = new LinkedHashMap<>();

        // Write purified & semantically remapped JAR
        Path purifiedJarPath = outputDir.resolve("clarity-purified-1.2.0.jar");
        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(purifiedJarPath))) {
            for (var resEntry : resourceFiles.entrySet()) {
                ZipEntry ze = new ZipEntry(resEntry.getKey());
                zos.putNextEntry(ze);
                zos.write(resEntry.getValue());
                zos.closeEntry();

                if (!resEntry.getKey().startsWith("META-INF/")) {
                    Path resOut = srcMainRes.resolve(resEntry.getKey());
                    Files.createDirectories(resOut.getParent());
                    Files.write(resOut, resEntry.getValue());
                }
            }

            for (ClassNode cn : purified.values()) {
                ClassNode remappedNode = new ClassNode();
                ClassRemapper cr = new ClassRemapper(remappedNode, remapper);
                cn.accept(cr);
                // Preserve exact local slot allocation so shared 1-word/2-word slots pass JVM verification
                remappedClasses.put(remappedNode.name, remappedNode);
            }
            for (ClassNode remappedNode : remappedClasses.values()) {
                byte[] classBytes = writeVerifiedClassBytes(remappedNode, remappedClasses);
                // Restore classfile version 65.0 (Java 21)
                classBytes[6] = 0;
                classBytes[7] = 65;

                ZipEntry ze = new ZipEntry(remappedNode.name + ".class");
                zos.putNextEntry(ze);
                zos.write(classBytes);
                zos.closeEntry();
            }
        }

        // Group inner classes with their outer classes
        Map<String, List<ClassNode>> outerToInners = new LinkedHashMap<>();
        List<ClassNode> topLevelClasses = new ArrayList<>();
        for (ClassNode cn : remappedClasses.values()) {
            int dollar = cn.name.indexOf('$');
            if (dollar > 0) {
                String outerName = cn.name.substring(0, dollar);
                if (remappedClasses.containsKey(outerName)) {
                    outerToInners.computeIfAbsent(outerName, k -> new ArrayList<>()).add(cn);
                    continue;
                }
            }
            topLevelClasses.add(cn);
        }

        JavaSourceGenerator srcGen = new JavaSourceGenerator(remappedClasses);
        int javaFileCount = 0;
        for (ClassNode topCn : topLevelClasses) {
            List<ClassNode> inners = outerToInners.getOrDefault(topCn.name, Collections.emptyList());
            String javaSource = srcGen.generateCompilationUnit(topCn, inners);
            Path javaPath = srcMainJava.resolve(topCn.name + ".java");
            Files.createDirectories(javaPath.getParent());
            Files.writeString(javaPath, javaSource, StandardCharsets.UTF_8);
            javaFileCount++;
        }

        System.out.println("Generated " + javaFileCount + " top-level Java source files (covering all " + remappedClasses.size() + " classes)!");
    }




    public static void compactMethodLocals(MethodNode mn) {
        int nextSlot = (mn.access & Opcodes.ACC_STATIC) != 0 ? 0 : 1;
        Map<Integer, Integer> slotMap = new HashMap<>();
        if ((mn.access & Opcodes.ACC_STATIC) == 0) slotMap.put(0, 0);
        for (Type argT : Type.getArgumentTypes(mn.desc)) {
            slotMap.put(nextSlot, nextSlot);
            nextSlot += argT.getSize();
        }
        for (AbstractInsnNode insn : mn.instructions) {
            if (insn instanceof VarInsnNode vin) {
                if (!slotMap.containsKey(vin.var)) {
                    int size = (vin.getOpcode() == Opcodes.LLOAD || vin.getOpcode() == Opcodes.DLOAD
                             || vin.getOpcode() == Opcodes.LSTORE || vin.getOpcode() == Opcodes.DSTORE) ? 2 : 1;
                    slotMap.put(vin.var, nextSlot);
                    nextSlot += size;
                }
                vin.var = slotMap.get(vin.var);
            } else if (insn instanceof IincInsnNode iinc) {
                if (!slotMap.containsKey(iinc.var)) {
                    slotMap.put(iinc.var, nextSlot++);
                }
                iinc.var = slotMap.get(iinc.var);
            }
        }
        mn.maxLocals = nextSlot;
        if (mn.localVariables != null) {
            mn.localVariables.removeIf(lvn -> !slotMap.containsKey(lvn.index));
            for (LocalVariableNode lvn : mn.localVariables) {
                lvn.index = slotMap.get(lvn.index);
            }
        }
    }

                public static void cleanIllegalTryCatchBlocks(MethodNode mn) {
        mn.tryCatchBlocks.removeIf(tcb -> {
            int start = mn.instructions.indexOf(tcb.start);
            int end = mn.instructions.indexOf(tcb.end);
            if (start < 0 || end < 0 || start >= end) return true;
            boolean hasRealInsn = false;
            for (AbstractInsnNode i = tcb.start.getNext(); i != null && i != tcb.end; i = i.getNext()) {
                if (!(i instanceof LabelNode || i instanceof LineNumberNode || i instanceof FrameNode)) {
                    hasRealInsn = true;
                    break;
                }
            }
            return !hasRealInsn;
        });
    }

public static void fixMethodTypeCasts(ClassNode cn, MethodNode mn) {
        InsnList insns = mn.instructions;
        for (AbstractInsnNode insn : insns.toArray()) {
            if (insn instanceof MethodInsnNode min) {
                if (min.owner.equals("com/google/gson/JsonObject") && min.name.equals("add")
                        && min.desc.equals("(Ljava/lang/String;Lcom/google/gson/JsonElement;)V")) {
                    AbstractInsnNode prev = insn.getPrevious();
                    if (prev instanceof VarInsnNode vin && vin.getOpcode() == Opcodes.ALOAD) {
                        AbstractInsnNode beforePrev = prev.getPrevious();
                        if (!(beforePrev instanceof TypeInsnNode tin && tin.getOpcode() == Opcodes.CHECKCAST
                                && tin.desc.equals("com/google/gson/JsonElement"))) {
                            insns.insertBefore(insn, new TypeInsnNode(Opcodes.CHECKCAST, "com/google/gson/JsonElement"));
                        }
                    }
                }
            }
        }
    }

public static byte[] writeVerifiedClassBytes(ClassNode cn, Map<String, ClassNode> allRemapped) {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS) {
            @Override
            protected String getCommonSuperClass(String type1, String type2) {
                if (type1.equals(type2)) return type1;
                if ("java/lang/Object".equals(type1) || "java/lang/Object".equals(type2)) return "java/lang/Object";

                // 1. Gson hierarchy
                if (type1.startsWith("com/google/gson/") && type2.startsWith("com/google/gson/")) {
                    if (isJsonElement(type1) && isJsonElement(type2)) return "com/google/gson/JsonElement";
                }

                // 2. Minecraft hierarchy approximations
                if ((type1.startsWith("net/minecraft/") || type2.startsWith("net/minecraft/"))
                        && !type1.startsWith("need/clarity/") && !type2.startsWith("need/clarity/")) {
                    if (isEntity(type1) && isEntity(type2)) return "net/minecraft/world/entity/Entity";
                    if (isLivingEntity(type1) && isLivingEntity(type2)) return "net/minecraft/world/entity/LivingEntity";
                    if (isPlayer(type1) && isPlayer(type2)) return "net/minecraft/world/entity/player/Player";
                    if (isScreen(type1) && isScreen(type2)) return "net/minecraft/client/gui/screens/Screen";
                    if (isBlockState(type1) && isBlockState(type2)) return "net/minecraft/world/level/block/state/BlockState";
                }

                // 3. Mod classes hierarchy
                Set<String> supers1 = new HashSet<>();
                String cur = type1;
                while (cur != null && !cur.equals("java/lang/Object")) {
                    supers1.add(cur);
                    ClassNode n = allRemapped.get(cur);
                    cur = n != null ? n.superName : null;
                }
                cur = type2;
                while (cur != null && !cur.equals("java/lang/Object")) {
                    if (supers1.contains(cur)) return cur;
                    ClassNode n = allRemapped.get(cur);
                    cur = n != null ? n.superName : null;
                }

                // 4. Fallback to Class.forName or Object
                try {
                    return super.getCommonSuperClass(type1, type2);
                } catch (Throwable t) {
                    return "java/lang/Object";
                }
            }

            private boolean isJsonElement(String t) {
                return t.equals("com/google/gson/JsonObject") || t.equals("com/google/gson/JsonArray")
                        || t.equals("com/google/gson/JsonPrimitive") || t.equals("com/google/gson/JsonNull")
                        || t.equals("com/google/gson/JsonElement");
            }
            private boolean isEntity(String t) {
                return t.contains("Entity") || isLivingEntity(t);
            }
            private boolean isLivingEntity(String t) {
                return t.contains("Living") || isPlayer(t);
            }
            private boolean isPlayer(String t) {
                return t.contains("Player");
            }
            private boolean isScreen(String t) {
                return t.contains("Screen");
            }
            private boolean isBlockState(String t) {
                return t.contains("BlockState");
            }
        };
        cn.accept(cw);
        return cw.toByteArray();
    }

static class TestCleanMethod {
    public static AbstractInsnNode makeIntInsn(int v) {
        if (v >= -1 && v <= 5) return new InsnNode(Opcodes.ICONST_0 + v);
        if (v >= Byte.MIN_VALUE && v <= Byte.MAX_VALUE) return new IntInsnNode(Opcodes.BIPUSH, v);
        if (v >= Short.MIN_VALUE && v <= Short.MAX_VALUE) return new IntInsnNode(Opcodes.SIPUSH, v);
        return new LdcInsnNode(v);
    }
}

static class VerifyZeroTrace {
    public static List<LabelNode> getSwitchCaseLabels(AbstractInsnNode sw) {
        if (sw instanceof LookupSwitchInsnNode lsin) return lsin.labels;
        if (sw instanceof TableSwitchInsnNode tsin) return tsin.labels;
        return Collections.emptyList();
    }
    public static List<Integer> getSwitchKeys(AbstractInsnNode sw) {
        if (sw instanceof LookupSwitchInsnNode lsin) return lsin.keys;
        if (sw instanceof TableSwitchInsnNode tsin) {
            List<Integer> keys = new ArrayList<>();
            for (int i = 0; i < tsin.labels.size(); i++) keys.add(tsin.min + i);
            return keys;
        }
        return Collections.emptyList();
    }
    public static LabelNode getSwitchDefault(AbstractInsnNode sw) {
        if (sw instanceof LookupSwitchInsnNode lsin) return lsin.dflt;
        if (sw instanceof TableSwitchInsnNode tsin) return tsin.dflt;
        return null;
    }
    public static LabelNode evalSwitchTarget(AbstractInsnNode sw, int val) {
        if (sw instanceof LookupSwitchInsnNode lsin) {
            int idx = lsin.keys.indexOf(val);
            return idx >= 0 ? lsin.labels.get(idx) : lsin.dflt;
        }
        if (sw instanceof TableSwitchInsnNode tsin) {
            int idx = val - tsin.min;
            return (idx >= 0 && idx < tsin.labels.size()) ? tsin.labels.get(idx) : tsin.dflt;
        }
        return null;
    }
}

static class TestFullPurify {
    public static AbstractInsnNode skipMeta(AbstractInsnNode insn) {
        while (insn != null && (insn instanceof LabelNode || insn instanceof LineNumberNode || insn instanceof FrameNode)) {
            insn = insn.getNext();
        }
        return insn;
    }
    public static LabelNode ensureLabelBefore(InsnList insns, AbstractInsnNode target) {
        if (target instanceof LabelNode ln) return ln;
        if (target.getPrevious() instanceof LabelNode ln) return ln;
        LabelNode ln = new LabelNode();
        insns.insertBefore(target, ln);
        return ln;
    }
    public static void stripClinitObfArtifacts(ClassNode cn, MethodNode mn, Set<String> synthFields, Map<String, byte[]> byteSuppliers) {
        InsnList insns = mn.instructions;
        // 1. Remove GETSTATIC nothing_to_see_here; <idx>; <ldc>; AASTORE
        for (AbstractInsnNode insn : insns.toArray()) {
            if (insn instanceof FieldInsnNode fin && fin.getOpcode() == Opcodes.GETSTATIC
                    && fin.owner.equals(cn.name) && fin.name.equals("nothing_to_see_here")) {
                AbstractInsnNode cur = fin;
                while (cur != null) {
                    AbstractInsnNode nxt = cur.getNext();
                    int op = cur.getOpcode();
                    insns.remove(cur);
                    if (op == Opcodes.AASTORE) break;
                    cur = nxt;
                }
            }
        }
        // 2. Remove PUTSTATIC synthFields (nothing_to_see_here allocation, Random int seed, V2 byte[], V3 String pool)
        for (AbstractInsnNode insn : insns.toArray()) {
            if (insn instanceof FieldInsnNode fin && fin.getOpcode() == Opcodes.PUTSTATIC
                    && fin.owner.equals(cn.name) && synthFields.contains(fin.name)) {
                if (fin.name.equals("nothing_to_see_here")) {
                    AbstractInsnNode cur = fin;
                    while (cur != null) {
                        AbstractInsnNode prev = cur.getPrevious();
                        boolean isStart = (cur instanceof TypeInsnNode tin && tin.getOpcode() == Opcodes.ANEWARRAY && tin.desc.equals("java/lang/String"));
                        insns.remove(cur);
                        if (isStart) {
                            if (prev != null && TestFullSim.getIntConst(prev) != null) insns.remove(prev);
                            break;
                        }
                        cur = prev;
                    }
                } else if (fin.desc.equals("I")) {
                    AbstractInsnNode cur = fin;
                    while (cur != null) {
                        AbstractInsnNode prev = cur.getPrevious();
                        boolean isStart = (cur instanceof TypeInsnNode tin && tin.getOpcode() == Opcodes.NEW && tin.desc.equals("java/util/Random"));
                        insns.remove(cur);
                        if (isStart) break;
                        cur = prev;
                    }
                } else {
                    AbstractInsnNode cur = fin;
                    while (cur != null) {
                        AbstractInsnNode prev = cur.getPrevious();
                        boolean isStart = (cur instanceof MethodInsnNode min && min.owner.equals(cn.name) && byteSuppliers.containsKey(min.name));
                        insns.remove(cur);
                        if (isStart) break;
                        cur = prev;
                    }
                }
            }
        }
    }
    public static void stripInitObfArtifacts(ClassNode cn, MethodNode mn, Set<String> synthFields) {
        InsnList insns = mn.instructions;
        for (AbstractInsnNode insn : insns.toArray()) {
            if (insn instanceof FieldInsnNode fin && fin.getOpcode() == Opcodes.PUTFIELD
                    && fin.owner.equals(cn.name) && synthFields.contains(fin.name)) {
                AbstractInsnNode cur = fin;
                while (cur != null) {
                    AbstractInsnNode prev = cur.getPrevious();
                    boolean isAload0 = (cur instanceof VarInsnNode vin && vin.getOpcode() == Opcodes.ALOAD && vin.var == 0);
                    insns.remove(cur);
                    if (isAload0) break;
                    cur = prev;
                }
            }
        }
    }
}

static class FindSeedHelper {
    public static int findSeedVar(MethodNode mn) {
        for (AbstractInsnNode insn : mn.instructions) {
            if (insn instanceof VarInsnNode vin && vin.getOpcode() == Opcodes.ISTORE) {
                AbstractInsnNode prev = insn.getPrevious();
                if (prev != null && prev.getOpcode() == Opcodes.IXOR) {
                    return vin.var;
                }
            }
        }
        return -1;
    }
}



static class TestFullSim {
    public static int thds(int x) {
        if (x != 0) {
            return (((x * 31) >>> 4) % x) ^ (x >>> 16);
        }
        return 0;
    }

    public static int edlq(int x) {
        return ((x & (7 << 29)) >> 29) | (x << 3);
    }

    public static int ixyg(int x) {
        return edlq(x);
    }

    public static Integer getIntConst(AbstractInsnNode insn) {
        if (insn == null) return null;
        int op = insn.getOpcode();
        if (op >= Opcodes.ICONST_M1 && op <= Opcodes.ICONST_5) {
            return op - Opcodes.ICONST_0;
        }
        if (insn instanceof IntInsnNode iin && (op == Opcodes.BIPUSH || op == Opcodes.SIPUSH)) {
            return iin.operand;
        }
        if (insn instanceof LdcInsnNode ldc && ldc.cst instanceof Integer i) {
            return i;
        }
        return null;
    }

    public static byte[] extractByteArray(MethodNode mn) {
        byte[] arr = null;
        Deque<Integer> stack = new ArrayDeque<>();
        for (AbstractInsnNode insn : mn.instructions) {
            Integer c = getIntConst(insn);
            if (c != null) {
                stack.push(c);
            } else if (insn instanceof IntInsnNode iin && insn.getOpcode() == Opcodes.NEWARRAY && iin.operand == Opcodes.T_BYTE) {
                int len = stack.pop();
                arr = new byte[len];
            } else if (insn.getOpcode() == Opcodes.BASTORE) {
                int val = stack.pop();
                int idx = stack.pop();
                if (arr != null && idx >= 0 && idx < arr.length) {
                    arr[idx] = (byte) val;
                }
            }
        }
        return arr;
    }

    // Propagate seedVar across CFG of mn
    public static Map<AbstractInsnNode, Integer> propagateSeed(ClassNode cn, MethodNode mn, int seedVar, Set<String> xorHelpers) {
        Map<AbstractInsnNode, Integer> seedBefore = new HashMap<>();
        InsnList insns = mn.instructions;
        if (insns.size() == 0) return seedBefore;

        // Worklist of (insn, currentSeed)
        // Note: at entry of method, seed is uninitialized (null), initialized by first istore seedVar
        Queue<Map.Entry<AbstractInsnNode, Integer>> queue = new ArrayDeque<>();
        Set<AbstractInsnNode> visitedWithNull = new HashSet<>();
        queue.add(new AbstractMap.SimpleEntry<>(insns.getFirst(), null));
        visitedWithNull.add(insns.getFirst());

        // Map from try block range to TryCatchBlockNodes
        while (!queue.isEmpty()) {
            var entry = queue.poll();
            AbstractInsnNode cur = entry.getKey();
            Integer seed = entry.getValue();

            while (cur != null) {
                if (seed != null) {
                    Integer prev = seedBefore.get(cur);
                    if (prev != null) {
                        // Already visited with known seed
                        break;
                    }
                    seedBefore.put(cur, seed);
                }

                // Check if cur is an istore seedVar
                if (cur instanceof VarInsnNode vin && vin.getOpcode() == Opcodes.ISTORE && vin.var == seedVar) {
                    // Pattern 1: ldc K1; ldc K2; ixor; istore seedVar
                    // Pattern 2: ldc K1; ldc K2; ldc K3; ixor; ixor; istore seedVar
                    // Pattern 3: ldc K1; ldc K2; ldc "K3"; parseInt; ixor; ixor; istore seedVar
                    // Pattern 4: ldc K1; iload seedVar; ixor; istore seedVar
                    // Pattern 5: iload seedVar; ldc K1; invokestatic xorHelper; istore seedVar
                    Integer newSeed = evalSeedStore(cur, seedVar, seed, xorHelpers);
                    if (newSeed != null) {
                        seed = newSeed;
                    }
                }

                // Check if cur is inside any TryCatchBlockNode and we now have a known seed
                if (seed != null && cur instanceof LabelNode lbl) {
                    // handled via range scan below
                }

                // Control flow transitions
                int op = cur.getOpcode();
                if (op == Opcodes.GOTO) {
                    JumpInsnNode jin = (JumpInsnNode) cur;
                    enqueue(jin.label, seed, seedBefore, visitedWithNull, queue);
                    break;
                } else if (op >= Opcodes.IFEQ && op <= Opcodes.IF_ACMPNE || op == Opcodes.IFNULL || op == Opcodes.IFNONNULL) {
                    JumpInsnNode jin = (JumpInsnNode) cur;
                    // Check if this is an opaque predicate on edlq/thds(seedVar)!
                    Integer branchTaken = evalOpaqueJump(jin, seedVar, seed);
                    if (branchTaken != null) {
                        if (branchTaken == 1) {
                            enqueue(jin.label, seed, seedBefore, visitedWithNull, queue);
                        } else {
                            enqueue(cur.getNext(), seed, seedBefore, visitedWithNull, queue);
                        }
                        break;
                    } else {
                        enqueue(jin.label, seed, seedBefore, visitedWithNull, queue);
                        enqueue(cur.getNext(), seed, seedBefore, visitedWithNull, queue);
                        break;
                    }
                } else if (op == Opcodes.LOOKUPSWITCH) {
                    LookupSwitchInsnNode lsin = (LookupSwitchInsnNode) cur;
                    LabelNode target = evalOpaqueLookupSwitch(lsin, seedVar, seed);
                    if (target != null) {
                        enqueue(target, seed, seedBefore, visitedWithNull, queue);
                    } else {
                        enqueue(lsin.dflt, seed, seedBefore, visitedWithNull, queue);
                        for (LabelNode l : lsin.labels) enqueue(l, seed, seedBefore, visitedWithNull, queue);
                    }
                    break;
                } else if (op == Opcodes.TABLESWITCH) {
                    TableSwitchInsnNode tsin = (TableSwitchInsnNode) cur;
                    enqueue(tsin.dflt, seed, seedBefore, visitedWithNull, queue);
                    for (LabelNode l : tsin.labels) enqueue(l, seed, seedBefore, visitedWithNull, queue);
                    break;
                } else if ((op >= Opcodes.IRETURN && op <= Opcodes.RETURN) || op == Opcodes.ATHROW) {
                    // If athrow is inside a try-catch block, propagate seed to handler!
                    if (seed != null) {
                        propagateToHandlers(mn, cur, seed, seedBefore, visitedWithNull, queue);
                    }
                    break;
                }

                if (seed != null) {
                    propagateToHandlers(mn, cur, seed, seedBefore, visitedWithNull, queue);
                }
                cur = cur.getNext();
            }
        }
        return seedBefore;
    }

    private static void propagateToHandlers(MethodNode mn, AbstractInsnNode cur, int seed,
            Map<AbstractInsnNode, Integer> seedBefore, Set<AbstractInsnNode> visitedWithNull,
            Queue<Map.Entry<AbstractInsnNode, Integer>> queue) {
        int idx = mn.instructions.indexOf(cur);
        for (TryCatchBlockNode tcb : mn.tryCatchBlocks) {
            int startIdx = mn.instructions.indexOf(tcb.start);
            int endIdx = mn.instructions.indexOf(tcb.end);
            if (idx >= startIdx && idx < endIdx) {
                enqueue(tcb.handler, seed, seedBefore, visitedWithNull, queue);
            }
        }
    }

    private static void enqueue(AbstractInsnNode next, Integer seed,
            Map<AbstractInsnNode, Integer> seedBefore, Set<AbstractInsnNode> visitedWithNull,
            Queue<Map.Entry<AbstractInsnNode, Integer>> queue) {
        if (next == null) return;
        if (seed == null) {
            if (visitedWithNull.add(next)) {
                queue.add(new AbstractMap.SimpleEntry<>(next, null));
            }
        } else {
            if (!seedBefore.containsKey(next)) {
                queue.add(new AbstractMap.SimpleEntry<>(next, seed));
            }
        }
    }

    private static Integer evalSeedStore(AbstractInsnNode istoreInsn, int seedVar, Integer curSeed, Set<String> xorHelpers) {
        // Walk backwards through arithmetic instructions feeding this istore
        AbstractInsnNode p1 = istoreInsn.getPrevious();
        if (p1 == null) return null;
        if (p1 instanceof MethodInsnNode min && xorHelpers.contains(min.name) && min.desc.equals("(II)I")) {
            // iload seedVar; ldc K; invokestatic xorHelper
            AbstractInsnNode a2 = p1.getPrevious();
            AbstractInsnNode a1 = a2 != null ? a2.getPrevious() : null;
            Integer v2 = evalTerm(a2, seedVar, curSeed);
            Integer v1 = evalTerm(a1, seedVar, curSeed);
            if (v1 != null && v2 != null) return v1 ^ v2;
        }
        if (p1.getOpcode() == Opcodes.IXOR) {
            AbstractInsnNode p2 = p1.getPrevious();
            if (p2 != null && p2.getOpcode() == Opcodes.IXOR) {
                // 3-operand XOR: t1, t2, t3, ixor, ixor
                AbstractInsnNode t3 = p2.getPrevious();
                AbstractInsnNode t2 = prevTermNode(t3);
                AbstractInsnNode t1 = prevTermNode(t2);
                Integer v3 = evalTermNode(t3, seedVar, curSeed);
                Integer v2 = evalTermNode(t2, seedVar, curSeed);
                Integer v1 = evalTermNode(t1, seedVar, curSeed);
                if (v1 != null && v2 != null && v3 != null) return v1 ^ v2 ^ v3;
            } else {
                // 2-operand XOR: t1, t2, ixor
                AbstractInsnNode t2 = p1.getPrevious();
                AbstractInsnNode t1 = prevTermNode(t2);
                Integer v2 = evalTermNode(t2, seedVar, curSeed);
                Integer v1 = evalTermNode(t1, seedVar, curSeed);
                if (v1 != null && v2 != null) return v1 ^ v2;
            }
        }
        return null;
    }

    private static AbstractInsnNode prevTermNode(AbstractInsnNode node) {
        if (node == null) return null;
        if (node instanceof MethodInsnNode min && min.owner.equals("java/lang/Integer") && min.name.equals("parseInt")) {
            AbstractInsnNode arg = node.getPrevious();
            return arg != null ? arg.getPrevious() : null;
        }
        return node.getPrevious();
    }

    private static Integer evalTermNode(AbstractInsnNode node, int seedVar, Integer curSeed) {
        if (node == null) return null;
        if (node instanceof MethodInsnNode min && min.owner.equals("java/lang/Integer") && min.name.equals("parseInt")) {
            AbstractInsnNode arg = node.getPrevious();
            if (arg instanceof LdcInsnNode ldc && ldc.cst instanceof String s) {
                return Integer.parseInt(s);
            }
            return null;
        }
        return evalTerm(node, seedVar, curSeed);
    }

    private static Integer evalTerm(AbstractInsnNode node, int seedVar, Integer curSeed) {
        if (node == null) return null;
        Integer c = getIntConst(node);
        if (c != null) return c;
        if (node instanceof VarInsnNode vin && vin.getOpcode() == Opcodes.ILOAD && vin.var == seedVar) {
            return curSeed;
        }
        return null;
    }

    private static Integer evalOpaqueJump(JumpInsnNode jin, int seedVar, Integer curSeed) {
        if (curSeed == null) return null;
        int op = jin.getOpcode();
        if (op != Opcodes.IF_ICMPEQ && op != Opcodes.IF_ICMPNE) return null;
        // Pattern A: iload seedVar; invokestatic edlq/thds; ldc K; if_icmpeq/ne
        // Pattern B: iload seedVar; invokestatic edlq/thds; ldc K; iload seedVar; ixor; if_icmpeq/ne
        AbstractInsnNode p1 = jin.getPrevious();
        Integer rhs = null;
        AbstractInsnNode callNode = null;
        if (p1 != null && p1.getOpcode() == Opcodes.IXOR) {
            AbstractInsnNode p2 = p1.getPrevious();
            AbstractInsnNode p3 = p2 != null ? p2.getPrevious() : null;
            Integer v2 = evalTerm(p2, seedVar, curSeed);
            Integer v3 = evalTerm(p3, seedVar, curSeed);
            if (v2 != null && v3 != null) {
                rhs = v2 ^ v3;
                callNode = p3.getPrevious();
            }
        } else {
            rhs = getIntConst(p1);
            if (rhs != null) callNode = p1.getPrevious();
        }
        if (rhs != null && callNode instanceof MethodInsnNode min && min.owner.equals("ryzwriqrxnniuxrp/qajywlqkmlrdtwtx")) {
            AbstractInsnNode arg = callNode.getPrevious();
            if (arg instanceof VarInsnNode vin && vin.getOpcode() == Opcodes.ILOAD && vin.var == seedVar) {
                int lhs = min.name.equals("thdsivcnwkjunctr") ? thds(curSeed) : edlq(curSeed);
                boolean eq = (lhs == rhs);
                boolean taken = (op == Opcodes.IF_ICMPEQ) ? eq : !eq;
                return taken ? 1 : 0;
            }
        }
        return null;
    }

    private static LabelNode evalOpaqueLookupSwitch(LookupSwitchInsnNode lsin, int seedVar, Integer curSeed) {
        if (curSeed == null) return null;
        AbstractInsnNode p1 = lsin.getPrevious();
        if (p1 instanceof MethodInsnNode min && min.owner.equals("ryzwriqrxnniuxrp/qajywlqkmlrdtwtx")) {
            AbstractInsnNode arg = p1.getPrevious();
            if (arg instanceof VarInsnNode vin && vin.getOpcode() == Opcodes.ILOAD && vin.var == seedVar) {
                int val = min.name.equals("thdsivcnwkjunctr") ? thds(curSeed) : edlq(curSeed);
                for (int i = 0; i < lsin.keys.size(); i++) {
                    if (lsin.keys.get(i) == val) {
                        return lsin.labels.get(i);
                    }
                }
                return lsin.dflt;
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        try (ZipFile zf = new ZipFile(args[0])) {
            int totalStrCalls = 0;
            int resolvedStrCalls = 0;
            int unresolvedStrCalls = 0;
            Enumeration<? extends ZipEntry> entries = zf.entries();
            while (entries.hasMoreElements()) {
                ZipEntry ze = entries.nextElement();
                if (!ze.getName().endsWith(".class")) continue;
                byte[] data = zf.getInputStream(ze).readAllBytes();
                if (((data[6] & 0xFF) << 8 | (data[7] & 0xFF)) > 61) {
                    data[6] = 0; data[7] = 61;
                }
                ClassReader cr = new ClassReader(data);
                ClassNode cn = new ClassNode();
                cr.accept(cn, 0);

                Set<String> xorHelpers = new HashSet<>();
                Set<String> strDecryptors = new HashSet<>();
                for (MethodNode mn : cn.methods) {
                    if (mn.desc.equals("(II)I") && (mn.access & Opcodes.ACC_STATIC) != 0 && mn.instructions.size() <= 10) {
                        xorHelpers.add(mn.name);
                    }
                    if ((mn.desc.equals("([B[BI)Ljava/lang/String;") || mn.desc.equals("([BI)Ljava/lang/String;"))
                        && (mn.access & Opcodes.ACC_STATIC) != 0) {
                        strDecryptors.add(mn.name);
                    }
                }

                for (MethodNode mn : cn.methods) {
                    if (strDecryptors.contains(mn.name)) continue;
                    // Find main seedVar (the first istore fed by xor at start of method, or most frequent iload before edlq/thds)
                    Map<Integer, Integer> varFreq = new HashMap<>();
                    for (AbstractInsnNode insn : mn.instructions) {
                        if (insn instanceof MethodInsnNode min && min.owner.equals("ryzwriqrxnniuxrp/qajywlqkmlrdtwtx")) {
                            if (insn.getPrevious() instanceof VarInsnNode vin && vin.getOpcode() == Opcodes.ILOAD) {
                                varFreq.merge(vin.var, 1, Integer::sum);
                            }
                        }
                    }
                    if (varFreq.isEmpty()) continue;
                    int seedVar = Collections.max(varFreq.entrySet(), Map.Entry.comparingByValue()).getKey();
                    Map<AbstractInsnNode, Integer> seeds = propagateSeed(cn, mn, seedVar, xorHelpers);

                    for (AbstractInsnNode insn : mn.instructions) {
                        if (insn instanceof MethodInsnNode min && min.owner.equals(cn.name) && strDecryptors.contains(min.name)) {
                            totalStrCalls++;
                            if (seeds.containsKey(insn)) {
                                resolvedStrCalls++;
                            } else {
                                unresolvedStrCalls++;
                                System.out.println("Unresolved string call in " + cn.name + "." + mn.name + mn.desc);
                            }
                        }
                    }
                }
            }
            System.out.println("totalStrCalls=" + totalStrCalls + " resolved=" + resolvedStrCalls + " unresolved=" + unresolvedStrCalls);
        }
    }
}



static class TestSwitchResolve {
    public static String decryptV1OrV2(byte[] enc, byte[] key, int seed) {
        byte[] seedBytes = Integer.toString(seed).getBytes(StandardCharsets.UTF_8);
        byte[] out = new byte[enc.length];
        for (int i = 0; i < enc.length; i++) {
            out[i] = (byte) (enc[i] ^ seedBytes[i % seedBytes.length] ^ key[i % key.length]);
        }
        return new String(out, StandardCharsets.UTF_16);
    }

    public static String decryptV3(byte[] hdr, String pool, int seed) {
        int len = ((hdr[0] & 0xFF) << 24) | ((hdr[1] & 0xFF) << 16) | ((hdr[2] & 0xFF) << 8) | (hdr[3] & 0xFF);
        int off = ((hdr[4] & 0xFF) << 24) | ((hdr[5] & 0xFF) << 16) | ((hdr[6] & 0xFF) << 8) | (hdr[7] & 0xFF);
        byte[] data = pool.substring(off, off + len).getBytes(StandardCharsets.UTF_16BE);
        byte[] seedBytes = Integer.toString(seed).getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < data.length; i++) {
            data[i] ^= seedBytes[i % seedBytes.length];
        }
        return new String(data, StandardCharsets.UTF_16BE);
    }

    public static void main(String[] args) throws Exception {
        try (ZipFile zf = new ZipFile(args[0])) {
            int totalSwitchKeys = 0;
            int resolvedSwitchKeys = 0;
            Enumeration<? extends ZipEntry> entries = zf.entries();
            while (entries.hasMoreElements()) {
                ZipEntry ze = entries.nextElement();
                if (!ze.getName().endsWith(".class")) continue;
                byte[] data = zf.getInputStream(ze).readAllBytes();
                if (((data[6] & 0xFF) << 8 | (data[7] & 0xFF)) > 61) {
                    data[6] = 0; data[7] = 61;
                }
                ClassReader cr = new ClassReader(data);
                ClassNode cn = new ClassNode();
                cr.accept(cn, 0);

                Map<String, byte[]> byteSuppliers = new HashMap<>();
                Set<String> xorHelpers = new HashSet<>();
                MethodNode decryptorMethod = null;
                boolean isV1 = false, isV2 = false, isV3 = false;

                for (MethodNode mn : cn.methods) {
                    if (mn.desc.equals("()[B") && (mn.access & Opcodes.ACC_STATIC) != 0) {
                        byte[] arr = TestFullSim.extractByteArray(mn);
                        if (arr != null) byteSuppliers.put(mn.name, arr);
                    } else if (mn.desc.equals("(II)I") && (mn.access & Opcodes.ACC_STATIC) != 0 && mn.instructions.size() <= 10) {
                        xorHelpers.add(mn.name);
                    } else if (mn.desc.equals("([B[BI)Ljava/lang/String;") && (mn.access & Opcodes.ACC_STATIC) != 0) {
                        decryptorMethod = mn;
                        isV1 = true;
                    } else if (mn.desc.equals("([BI)Ljava/lang/String;") && (mn.access & Opcodes.ACC_STATIC) != 0) {
                        decryptorMethod = mn;
                        for (AbstractInsnNode insn : mn.instructions) {
                            if (insn instanceof MethodInsnNode min && min.name.equals("substring")) isV3 = true;
                            if (insn instanceof FieldInsnNode fin && fin.desc.equals("[B")) isV2 = true;
                        }
                    }
                }

                byte[] staticKeyArr = null;
                String staticPoolStr = null;
                if (isV2 || isV3) {
                    for (MethodNode mn : cn.methods) {
                        if (mn.name.equals("<clinit>")) {
                            for (AbstractInsnNode insn : mn.instructions) {
                                if (insn instanceof MethodInsnNode min && byteSuppliers.containsKey(min.name)) {
                                    if (isV2 && insn.getNext() instanceof FieldInsnNode fin && fin.getOpcode() == Opcodes.PUTSTATIC) staticKeyArr = byteSuppliers.get(min.name);
                                    if (isV3 && insn.getNext() instanceof MethodInsnNode mNext && mNext.name.equals("wrap")) {
                                        byte[] raw = byteSuppliers.get(min.name);
                                        staticPoolStr = ByteBuffer.wrap(raw).asCharBuffer().toString();
                                    }
                                }
                            }
                        }
                    }
                }

                for (MethodNode mn : cn.methods) {
                    if (decryptorMethod != null && mn.name.equals(decryptorMethod.name)) continue;
                    Map<Integer, Integer> varFreq = new HashMap<>();
                    for (AbstractInsnNode insn : mn.instructions) {
                        if (insn instanceof MethodInsnNode min && min.owner.equals("ryzwriqrxnniuxrp/qajywlqkmlrdtwtx")) {
                            if (insn.getPrevious() instanceof VarInsnNode vin && vin.getOpcode() == Opcodes.ILOAD) {
                                varFreq.merge(vin.var, 1, Integer::sum);
                            }
                        }
                    }
                    if (varFreq.isEmpty()) continue;
                    int seedVar = Collections.max(varFreq.entrySet(), Map.Entry.comparingByValue()).getKey();
                    Map<AbstractInsnNode, Integer> seeds = TestFullSim.propagateSeed(cn, mn, seedVar, xorHelpers);

                    // Collect all strings in method (both plain LDC and decrypted!)
                    Set<String> methodStrings = new HashSet<>();
                    for (AbstractInsnNode insn : mn.instructions) {
                        if (insn instanceof LdcInsnNode ldc && ldc.cst instanceof String s) {
                            methodStrings.add(s);
                        } else if (decryptorMethod != null && insn instanceof MethodInsnNode min
                                && min.owner.equals(cn.name) && min.name.equals(decryptorMethod.name)) {
                            int seed = seeds.get(insn);
                            if (isV1) {
                                AbstractInsnNode p1 = insn.getPrevious(); // iload seed
                                AbstractInsnNode p2 = p1.getPrevious(); // key()
                                AbstractInsnNode p3 = p2.getPrevious(); // enc()
                                if (p2 instanceof MethodInsnNode mKey && p3 instanceof MethodInsnNode mEnc) {
                                    String dec = decryptV1OrV2(byteSuppliers.get(mEnc.name), byteSuppliers.get(mKey.name), seed);
                                    methodStrings.add(dec);
                                }
                            } else if (isV2) {
                                AbstractInsnNode p1 = insn.getPrevious(); // iload seed
                                AbstractInsnNode p2 = p1.getPrevious(); // enc()
                                if (p2 instanceof MethodInsnNode mEnc) {
                                    String dec = decryptV1OrV2(byteSuppliers.get(mEnc.name), staticKeyArr, seed);
                                    methodStrings.add(dec);
                                }
                            } else if (isV3) {
                                AbstractInsnNode p1 = insn.getPrevious(); // iload seed
                                AbstractInsnNode p2 = p1.getPrevious(); // hdr()
                                if (p2 instanceof MethodInsnNode mHdr) {
                                    String dec = decryptV3(byteSuppliers.get(mHdr.name), staticPoolStr, seed);
                                    methodStrings.add(dec);
                                }
                            }
                        }
                    }

                    // Now check obfuscated real switches in mn
                    for (AbstractInsnNode insn : mn.instructions) {
                        if (insn instanceof LookupSwitchInsnNode lsin) {
                            AbstractInsnNode p1 = lsin.getPrevious();
                            if (p1 instanceof MethodInsnNode min && min.owner.equals("ryzwriqrxnniuxrp/qajywlqkmlrdtwtx")) {
                                AbstractInsnNode p2 = p1.getPrevious(); // iload tmp
                                if (p2 instanceof VarInsnNode vin && vin.var != seedVar) {
                                    int seed = seeds.get(lsin);
                                    Map<Integer, Integer> hashToOrig = new HashMap<>();
                                    for (String s : methodStrings) {
                                        int hc = s.hashCode();
                                        hashToOrig.put(TestFullSim.thds(hc ^ seed), hc);
                                    }
                                    for (int c = -1000; c <= 10000; c++) {
                                        hashToOrig.putIfAbsent(TestFullSim.thds(c ^ seed), c);
                                    }
                                    for (int k : lsin.keys) {
                                        totalSwitchKeys++;
                                        if (hashToOrig.containsKey(k)) {
                                            resolvedSwitchKeys++;
                                        } else {
                                            System.out.println("Unresolved switch key " + k + " in " + cn.name + "." + mn.name + " strings=" + methodStrings);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("totalSwitchKeys=" + totalSwitchKeys + " resolvedSwitchKeys=" + resolvedSwitchKeys);
        }
    }
}



static class TestAll530 {
    public static void main(String[] args) throws Exception {
        try (ZipFile zf = new ZipFile(args[0])) {
            int remainingObfRefs = 0;
            int remainingErrorInHash = 0;
            int remainingNothingToSee = 0;
            int totalClasses = 0;

            Enumeration<? extends ZipEntry> entries = zf.entries();
            while (entries.hasMoreElements()) {
                ZipEntry ze = entries.nextElement();
                if (!ze.getName().endsWith(".class")) continue;
                if (ze.getName().startsWith("ryzwriqrxnniuxrp/")) continue;
                totalClasses++;
                byte[] data = zf.getInputStream(ze).readAllBytes();
                if (((data[6] & 0xFF) << 8 | (data[7] & 0xFF)) > 61) {
                    data[6] = 0; data[7] = 61;
                }
                ClassReader cr = new ClassReader(data);
                ClassNode cn = new ClassNode();
                cr.accept(cn, ClassReader.SKIP_FRAMES);
                purifyClassComplete(cn);

                for (FieldNode fn : cn.fields) {
                    if (fn.name.equals("nothing_to_see_here")) remainingNothingToSee++;
                }
                for (MethodNode mn : cn.methods) {
                    for (AbstractInsnNode insn : mn.instructions) {
                        if (insn instanceof MethodInsnNode min && min.owner.equals("ryzwriqrxnniuxrp/qajywlqkmlrdtwtx")) {
                            remainingObfRefs++;
                            System.out.println("Remaining obf ref in " + cn.name + "." + mn.name + mn.desc + " -> " + min.name);
                        } else if (insn instanceof LdcInsnNode ldc && "Error in hash".equals(ldc.cst)) {
                            remainingErrorInHash++;
                            System.out.println("Remaining Error in hash in " + cn.name + "." + mn.name + mn.desc);
                        }
                    }
                }
            }
            System.out.println("Processed " + totalClasses + " classes: remainingObfRefs=" + remainingObfRefs +
                " remainingErrorInHash=" + remainingErrorInHash + " remainingNothingToSee=" + remainingNothingToSee);
        }
    }

    public static void purifyClassComplete(ClassNode cn) {
        boolean isObfClass = false;
        for (FieldNode fn : cn.fields) {
            if (fn.name.equals("nothing_to_see_here")) { isObfClass = true; break; }
        }
        if (!isObfClass) {
            for (MethodNode mn : cn.methods) {
                for (AbstractInsnNode insn : mn.instructions) {
                    if (insn instanceof MethodInsnNode min && min.owner.equals("ryzwriqrxnniuxrp/qajywlqkmlrdtwtx")) {
                        isObfClass = true; break;
                    }
                }
            }
        }
        if (!isObfClass) return;
        Map<String, byte[]> byteSuppliers = new HashMap<>();
        Set<String> xorHelpers = new HashSet<>();
        MethodNode decryptorMethod = null;
        boolean isV1 = false, isV2 = false, isV3 = false;

        for (MethodNode mn : cn.methods) {
            if (mn.desc.equals("()[B") && (mn.access & Opcodes.ACC_STATIC) != 0) {
                byte[] arr = TestFullSim.extractByteArray(mn);
                if (arr != null) byteSuppliers.put(mn.name, arr);
            } else if (mn.desc.equals("(II)I") && (mn.access & Opcodes.ACC_STATIC) != 0 && mn.instructions.size() <= 10) {
                xorHelpers.add(mn.name);
            } else if (mn.desc.equals("([B[BI)Ljava/lang/String;") && (mn.access & Opcodes.ACC_STATIC) != 0) {
                decryptorMethod = mn; isV1 = true;
            } else if (mn.desc.equals("([BI)Ljava/lang/String;") && (mn.access & Opcodes.ACC_STATIC) != 0) {
                decryptorMethod = mn;
                for (AbstractInsnNode insn : mn.instructions) {
                    if (insn instanceof MethodInsnNode min && min.name.equals("substring")) isV3 = true;
                    if (insn instanceof FieldInsnNode fin && fin.desc.equals("[B")) isV2 = true;
                }
            }
        }

        byte[] staticKeyArr = null;
        String staticPoolStr = null;
        String staticPoolFieldName = null;
        for (MethodNode mn : cn.methods) {
            if (mn.name.equals("<clinit>")) {
                for (AbstractInsnNode insn : mn.instructions) {
                    if (insn instanceof MethodInsnNode min && byteSuppliers.containsKey(min.name)) {
                        if (isV2 && insn.getNext() instanceof FieldInsnNode fin && fin.getOpcode() == Opcodes.PUTSTATIC) {
                            staticKeyArr = byteSuppliers.get(min.name);
                            staticPoolFieldName = fin.name;
                        }
                        if (isV3 && insn.getNext() instanceof MethodInsnNode mNext && mNext.name.equals("wrap")) {
                            byte[] raw = byteSuppliers.get(min.name);
                            staticPoolStr = ByteBuffer.wrap(raw).asCharBuffer().toString();
                            AbstractInsnNode p = mNext.getNext();
                            if (p != null) p = p.getNext();
                            if (p != null && p.getNext() instanceof FieldInsnNode fin) staticPoolFieldName = fin.name;
                        }
                    }
                }
            }
        }

        Set<String> synthFields = new HashSet<>();
        if (staticPoolFieldName != null) synthFields.add(staticPoolFieldName);
        for (FieldNode fn : cn.fields) {
            if (fn.name.equals("nothing_to_see_here")) synthFields.add(fn.name);
            if (fn.desc.equals("I") && fn.name.length() == 10 && (fn.access & Opcodes.ACC_PRIVATE) != 0) {
                boolean hasDigitOrUpper = false;
                for (char c : fn.name.toCharArray()) {
                    if (Character.isDigit(c) || Character.isUpperCase(c)) hasDigitOrUpper = true;
                }
                if (hasDigitOrUpper && !fn.name.equals("blendEqRgb") && !fn.name.equals("stencilRef")
                        && !fn.name.equals("artCounter") && !fn.name.equals("uDownTexel") && !fn.name.equals("blurImgTex")
                        && !fn.name.equals("attachedId") && !fn.name.equals("locSamples")) {
                    synthFields.add(fn.name);
                }
            }
        }
        cn.fields.removeIf(f -> synthFields.contains(f.name));

        String decName = decryptorMethod != null ? decryptorMethod.name : null;
        cn.methods.removeIf(m -> byteSuppliers.containsKey(m.name) || xorHelpers.contains(m.name)
            || (decName != null && m.name.equals(decName)));

        for (MethodNode mn : cn.methods) {
            if (mn.name.equals("<clinit>")) {
                TestFullPurify.stripClinitObfArtifacts(cn, mn, synthFields, byteSuppliers);
            }
            if (mn.name.equals("<init>")) {
                TestFullPurify.stripInitObfArtifacts(cn, mn, synthFields);
            }
            int seedVar = FindSeedHelper.findSeedVar(mn);
            if (seedVar == -1) continue;
            Map<AbstractInsnNode, Integer> seeds = propagateSeedBothSwitches(cn, mn, seedVar, xorHelpers);

            // 1. Inline encrypted strings
            for (AbstractInsnNode insn : mn.instructions.toArray()) {
                if (decName != null && insn instanceof MethodInsnNode min && min.owner.equals(cn.name) && min.name.equals(decName)) {
                    Integer seed = seeds.get(insn);
                    if (seed == null) continue;
                    if (isV1) {
                        AbstractInsnNode p1 = insn.getPrevious();
                        AbstractInsnNode p2 = p1.getPrevious();
                        AbstractInsnNode p3 = p2.getPrevious();
                        if (p2 instanceof MethodInsnNode mKey && p3 instanceof MethodInsnNode mEnc) {
                            String dec = TestSwitchResolve.decryptV1OrV2(byteSuppliers.get(mEnc.name), byteSuppliers.get(mKey.name), seed);
                            mn.instructions.insertBefore(p3, new LdcInsnNode(dec));
                            mn.instructions.remove(p3); mn.instructions.remove(p2);
                            mn.instructions.remove(p1); mn.instructions.remove(insn);
                        }
                    } else if (isV2) {
                        AbstractInsnNode p1 = insn.getPrevious();
                        AbstractInsnNode p2 = p1.getPrevious();
                        if (p2 instanceof MethodInsnNode mEnc) {
                            String dec = TestSwitchResolve.decryptV1OrV2(byteSuppliers.get(mEnc.name), staticKeyArr, seed);
                            mn.instructions.insertBefore(p2, new LdcInsnNode(dec));
                            mn.instructions.remove(p2); mn.instructions.remove(p1); mn.instructions.remove(insn);
                        }
                    } else if (isV3) {
                        AbstractInsnNode p1 = insn.getPrevious();
                        AbstractInsnNode p2 = p1.getPrevious();
                        if (p2 instanceof MethodInsnNode mHdr) {
                            String dec = TestSwitchResolve.decryptV3(byteSuppliers.get(mHdr.name), staticPoolStr, seed);
                            mn.instructions.insertBefore(p2, new LdcInsnNode(dec));
                            mn.instructions.remove(p2); mn.instructions.remove(p1); mn.instructions.remove(insn);
                        }
                    }
                }
            }

            Set<String> methodStrings = new HashSet<>();
            for (AbstractInsnNode insn : mn.instructions) {
                if (insn instanceof LdcInsnNode ldc && ldc.cst instanceof String s) {
                    methodStrings.add(s);
                }
            }

            // 2. Unwrap ixyg exception handlers (both LookupSwitch and TableSwitch!)
            List<TryCatchBlockNode> toRemoveTcb = new ArrayList<>();
            for (TryCatchBlockNode tcb : mn.tryCatchBlocks) {
                AbstractInsnNode h = TestFullPurify.skipMeta(tcb.handler);
                if (h instanceof VarInsnNode && h.getNext() instanceof MethodInsnNode min
                        && min.owner.equals("ryzwriqrxnniuxrp/qajywlqkmlrdtwtx")
                        && min.name.equals("ixygboxifvieamcy")) {
                    AbstractInsnNode sw = min.getNext();
                    List<LabelNode> caseLabels = VerifyZeroTrace.getSwitchCaseLabels(sw);
                    if (!caseLabels.isEmpty()) {
                        AbstractInsnNode joinInsn = null;
                        for (LabelNode caseLbl : caseLabels) {
                            AbstractInsnNode cur = caseLbl;
                            while (cur != null && !(cur instanceof VarInsnNode vin && vin.getOpcode() == Opcodes.ISTORE && vin.var == seedVar)) {
                                cur = cur.getNext();
                            }
                            if (cur != null) {
                                AbstractInsnNode afterStore = TestFullPurify.skipMeta(cur.getNext());
                                if (afterStore instanceof JumpInsnNode jin && jin.getOpcode() == Opcodes.GOTO) {
                                    joinInsn = TestFullPurify.skipMeta(jin.label);
                                } else {
                                    joinInsn = afterStore;
                                }
                                break;
                            }
                        }
                        if (joinInsn != null) {
                            if (joinInsn.getOpcode() == Opcodes.POP && isBogusTryRange(mn, tcb, seedVar)) {
                                AbstractInsnNode s = TestFullPurify.skipMeta(tcb.start);
                                if (s instanceof TypeInsnNode tin && tin.getOpcode() == Opcodes.NEW
                                        && s.getNext() != null && s.getNext().getOpcode() == Opcodes.DUP
                                        && s.getNext().getNext() instanceof MethodInsnNode mInit
                                        && mInit.getNext() != null && mInit.getNext().getOpcode() == Opcodes.ATHROW) {
                                    LabelNode afterPopLbl = TestFullPurify.ensureLabelBefore(mn.instructions, joinInsn.getNext());
                                    AbstractInsnNode dup = s.getNext();
                                    AbstractInsnNode ath = mInit.getNext();
                                    mn.instructions.insertBefore(s, new JumpInsnNode(Opcodes.GOTO, afterPopLbl));
                                    mn.instructions.remove(s);
                                    mn.instructions.remove(dup);
                                    mn.instructions.remove(mInit);
                                    mn.instructions.remove(ath);
                                }
                                toRemoveTcb.add(tcb);
                            } else {
                                tcb.handler = TestFullPurify.ensureLabelBefore(mn.instructions, joinInsn);
                            }
                        }
                    }
                }
            }
            mn.tryCatchBlocks.removeAll(toRemoveTcb);

            // 3. Simplify opaque switches/jumps and real switches
            for (AbstractInsnNode insn : mn.instructions.toArray()) {
                if (insn.getNext() == null && mn.instructions.indexOf(insn) < 0) continue;
                if (insn instanceof LookupSwitchInsnNode || insn instanceof TableSwitchInsnNode) {
                    AbstractInsnNode p1 = insn.getPrevious();
                    if (p1 instanceof MethodInsnNode min && min.owner.equals("ryzwriqrxnniuxrp/qajywlqkmlrdtwtx")) {
                        AbstractInsnNode p2 = p1.getPrevious();
                        if (p2 instanceof VarInsnNode vin && vin.getOpcode() == Opcodes.ILOAD) {
                            Integer seed = seeds.get(insn);
                            if (seed == null) continue;
                            if (vin.var == seedVar) {
                                int val = min.name.equals("thdsivcnwkjunctr") ? TestFullSim.thds(seed) : TestFullSim.edlq(seed);
                                LabelNode target = VerifyZeroTrace.evalSwitchTarget(insn, val);
                                mn.instructions.insertBefore(vin, new JumpInsnNode(Opcodes.GOTO, target));
                                mn.instructions.remove(vin);
                                mn.instructions.remove(p1);
                                mn.instructions.remove(insn);
                            } else {
                                AbstractInsnNode istoreTmp = vin.getPrevious();
                                if (istoreTmp instanceof VarInsnNode vStore && vStore.getOpcode() == Opcodes.ISTORE && vStore.var == vin.var) {
                                    AbstractInsnNode ixor = istoreTmp.getPrevious();
                                    AbstractInsnNode iloadSeed = ixor != null ? ixor.getPrevious() : null;
                                    if (ixor != null && ixor.getOpcode() == Opcodes.IXOR && iloadSeed instanceof VarInsnNode vSeed && vSeed.var == seedVar) {
                                        Map<Integer, Integer> hashToOrig = new HashMap<>();
                                        for (String s : methodStrings) hashToOrig.put(TestFullSim.thds(s.hashCode() ^ seed), s.hashCode());
                                        for (int c = -1000; c <= 10000; c++) hashToOrig.putIfAbsent(TestFullSim.thds(c ^ seed), c);
                                        List<Integer> oldKeys = VerifyZeroTrace.getSwitchKeys(insn);
                                        List<LabelNode> oldLabels = VerifyZeroTrace.getSwitchCaseLabels(insn);
                                        LabelNode dflt = VerifyZeroTrace.getSwitchDefault(insn);
                                        List<Map.Entry<Integer, LabelNode>> pairs = new ArrayList<>();
                                        for (int i = 0; i < oldKeys.size(); i++) {
                                            int origKey = hashToOrig.get(oldKeys.get(i));
                                            pairs.add(new AbstractMap.SimpleEntry<>(origKey, oldLabels.get(i)));
                                        }
                                        pairs.sort(Map.Entry.comparingByKey());
                                        int[] newKeys = new int[pairs.size()];
                                        LabelNode[] newLabels = new LabelNode[pairs.size()];
                                        for (int i = 0; i < pairs.size(); i++) {
                                            newKeys[i] = pairs.get(i).getKey();
                                            newLabels[i] = pairs.get(i).getValue();
                                        }
                                        mn.instructions.insertBefore(insn, new LookupSwitchInsnNode(dflt, newKeys, newLabels));
                                        mn.instructions.remove(iloadSeed);
                                        mn.instructions.remove(ixor);
                                        mn.instructions.remove(istoreTmp);
                                        mn.instructions.remove(vin);
                                        mn.instructions.remove(p1);
                                        mn.instructions.remove(insn);
                                    }
                                }
                            }
                        }
                    }
                } else if (insn instanceof JumpInsnNode jin && (jin.getOpcode() == Opcodes.IF_ICMPEQ || jin.getOpcode() == Opcodes.IF_ICMPNE)) {
                    Integer seed = seeds.get(jin);
                    if (seed == null) continue;
                    AbstractInsnNode p1 = jin.getPrevious();
                    Integer rhs = null;
                    AbstractInsnNode callNode = null;
                    AbstractInsnNode firstRhsNode = null;
                    if (p1 != null && p1.getOpcode() == Opcodes.IXOR) {
                        AbstractInsnNode p2 = p1.getPrevious();
                        AbstractInsnNode p3 = p2 != null ? p2.getPrevious() : null;
                        Integer v2 = (p2 instanceof VarInsnNode v && v.var == seedVar) ? seed : TestFullSim.getIntConst(p2);
                        Integer v3 = (p3 instanceof VarInsnNode v && v.var == seedVar) ? seed : TestFullSim.getIntConst(p3);
                        if (v2 != null && v3 != null) {
                            rhs = v2 ^ v3;
                            firstRhsNode = p3;
                            callNode = p3.getPrevious();
                        }
                    } else {
                        rhs = TestFullSim.getIntConst(p1);
                        if (rhs != null) {
                            firstRhsNode = p1;
                            callNode = p1.getPrevious();
                        }
                    }
                    if (rhs != null && callNode instanceof MethodInsnNode min && min.owner.equals("ryzwriqrxnniuxrp/qajywlqkmlrdtwtx")) {
                        AbstractInsnNode arg = callNode.getPrevious();
                        if (arg instanceof VarInsnNode vin && vin.getOpcode() == Opcodes.ILOAD && vin.var == seedVar) {
                            int lhs = min.name.equals("thdsivcnwkjunctr") ? TestFullSim.thds(seed) : TestFullSim.edlq(seed);
                            boolean taken = (jin.getOpcode() == Opcodes.IF_ICMPEQ) ? (lhs == rhs) : (lhs != rhs);
                            AbstractInsnNode cur = arg;
                            while (cur != jin) {
                                AbstractInsnNode nxt = cur.getNext();
                                mn.instructions.remove(cur);
                                cur = nxt;
                            }
                            if (taken) {
                                jin.setOpcode(Opcodes.GOTO);
                            } else {
                                mn.instructions.remove(jin);
                            }
                            continue;
                        }
                    }
                    if (rhs != null && firstRhsNode != null && p1.getOpcode() == Opcodes.IXOR) {
                        AbstractInsnNode cur = firstRhsNode;
                        while (cur != jin) {
                            AbstractInsnNode nxt = cur.getNext();
                            mn.instructions.remove(cur);
                            cur = nxt;
                        }
                        if (rhs == 0) {
                            jin.setOpcode(jin.getOpcode() == Opcodes.IF_ICMPEQ ? Opcodes.IFEQ : Opcodes.IFNE);
                        } else {
                            mn.instructions.insertBefore(jin, TestCleanMethod.makeIntInsn(rhs));
                        }
                    }
                }
            }

            // 4. Remove seedVar stores and fold constants
            for (AbstractInsnNode insn : mn.instructions.toArray()) {
                if (insn instanceof VarInsnNode vin && vin.getOpcode() == Opcodes.ISTORE && vin.var == seedVar) {
                    removeSeedStoreChain(mn.instructions, vin, seedVar, xorHelpers);
                }
            }
            for (AbstractInsnNode insn : mn.instructions.toArray()) {
                if (insn.getOpcode() == Opcodes.IXOR || (insn instanceof MethodInsnNode min && xorHelpers.contains(min.name))) {
                    AbstractInsnNode p1 = insn.getPrevious();
                    AbstractInsnNode p2 = p1 != null ? p1.getPrevious() : null;
                    Integer seed = seeds.get(insn);
                    if (seed != null && p1 != null && p2 != null) {
                        Integer v1 = (p1 instanceof VarInsnNode v && v.var == seedVar) ? seed : TestFullSim.getIntConst(p1);
                        Integer v2 = (p2 instanceof VarInsnNode v && v.var == seedVar) ? seed : TestFullSim.getIntConst(p2);
                        if (v1 != null && v2 != null) {
                            int val = v1 ^ v2;
                            AbstractInsnNode next = insn.getNext();
                            if (next != null && next.getOpcode() == Opcodes.I2B) {
                                val = (byte) val;
                                mn.instructions.remove(next);
                            } else if (next != null && next.getOpcode() == Opcodes.I2S) {
                                val = (short) val;
                                mn.instructions.remove(next);
                            } else if (next != null && next.getOpcode() == Opcodes.I2C) {
                                val = (char) val;
                                mn.instructions.remove(next);
                            }
                            mn.instructions.insertBefore(p2, TestCleanMethod.makeIntInsn(val));
                            mn.instructions.remove(p2);
                            mn.instructions.remove(p1);
                            mn.instructions.remove(insn);
                        }
                    }
                }
            }

            if (mn.name.equals("<clinit>")) {
                TestFullPurify.stripClinitObfArtifacts(cn, mn, synthFields, byteSuppliers);
            }
            if (mn.name.equals("<init>")) {
                TestFullPurify.stripInitObfArtifacts(cn, mn, synthFields);
            }

            // 5. Collapse jump trampolines and eliminate dead code
            collapseTrampolines(mn);
            eliminateDeadInstructions(mn);
            collapseTrampolines(mn);
            eliminateDeadInstructions(mn);
        }
        for (MethodNode mn : cn.methods) {
            if (mn.name.equals("<clinit>")) {
                TestFullPurify.stripClinitObfArtifacts(cn, mn, synthFields, byteSuppliers);
            }
            if (mn.name.equals("<init>")) {
                TestFullPurify.stripInitObfArtifacts(cn, mn, synthFields);
            }
        }
    }

    public static void collapseTrampolines(MethodNode mn) {
        for (int pass = 0; pass < 4; pass++) {
            boolean changed = false;
            for (AbstractInsnNode insn : mn.instructions) {
                if (insn instanceof JumpInsnNode jin) {
                    AbstractInsnNode targetNext = TestFullPurify.skipMeta(jin.label);
                    if (targetNext instanceof JumpInsnNode nextGoto && nextGoto.getOpcode() == Opcodes.GOTO && nextGoto != jin) {
                        if (jin.label != nextGoto.label) {
                            jin.label = nextGoto.label;
                            changed = true;
                        }
                    }
                }
            }
            if (!changed) break;
        }
    }

    public static boolean isBogusTryRange(MethodNode mn, TryCatchBlockNode tcb, int seedVar) {
        AbstractInsnNode cur = tcb.start;
        while (cur != null && cur != tcb.end) {
            if (cur instanceof MethodInsnNode min) {
                if (!min.owner.equals("ryzwriqrxnniuxrp/qajywlqkmlrdtwtx")
                        && !(min.name.equals("<init>") && (min.owner.equals("java/io/IOException")
                            || min.owner.equals("java/lang/IllegalAccessException")
                            || min.owner.equals("java/lang/RuntimeException")))) {
                    return false;
                }
            } else if (cur instanceof FieldInsnNode) {
                return false;
            }
            cur = cur.getNext();
        }
        return true;
    }

    public static void removeSeedStoreChain(InsnList insns, VarInsnNode istoreInsn, int seedVar, Set<String> xorHelpers) {
        List<AbstractInsnNode> toRemove = new ArrayList<>();
        toRemove.add(istoreInsn);
        AbstractInsnNode p1 = istoreInsn.getPrevious();
        if (p1 instanceof MethodInsnNode min && xorHelpers.contains(min.name)) {
            toRemove.add(p1);
            AbstractInsnNode a2 = p1.getPrevious();
            AbstractInsnNode a1 = a2 != null ? a2.getPrevious() : null;
            if (a2 != null) toRemove.add(a2);
            if (a1 != null) toRemove.add(a1);
        } else if (p1 != null && p1.getOpcode() == Opcodes.IXOR) {
            toRemove.add(p1);
            AbstractInsnNode p2 = p1.getPrevious();
            if (p2 != null && p2.getOpcode() == Opcodes.IXOR) {
                toRemove.add(p2);
                AbstractInsnNode cur = p2.getPrevious();
                for (int k = 0; k < 3 && cur != null; k++) {
                    if (cur instanceof MethodInsnNode min && min.name.equals("parseInt")) {
                        toRemove.add(cur);
                        cur = cur.getPrevious();
                    }
                    if (cur != null) {
                        toRemove.add(cur);
                        cur = cur.getPrevious();
                    }
                }
            } else {
                AbstractInsnNode cur = p2;
                for (int k = 0; k < 2 && cur != null; k++) {
                    if (cur instanceof MethodInsnNode min && min.name.equals("parseInt")) {
                        toRemove.add(cur);
                        cur = cur.getPrevious();
                    }
                    if (cur != null) {
                        toRemove.add(cur);
                        cur = cur.getPrevious();
                    }
                }
            }
        }
        for (AbstractInsnNode n : toRemove) insns.remove(n);
    }

    public static void eliminateDeadInstructions(MethodNode mn) {
        InsnList insns = mn.instructions;
        if (insns.size() == 0) return;
        Set<AbstractInsnNode> reachable = new HashSet<>();
        Queue<AbstractInsnNode> q = new ArrayDeque<>();
        q.add(insns.getFirst());

        while (!q.isEmpty()) {
            AbstractInsnNode cur = q.poll();
            while (cur != null && reachable.add(cur)) {
                int idx = insns.indexOf(cur);
                for (TryCatchBlockNode tcb : mn.tryCatchBlocks) {
                    int s = insns.indexOf(tcb.start);
                    int e = insns.indexOf(tcb.end);
                    if (idx >= s && idx < e) {
                        q.add(tcb.handler);
                    }
                }
                int op = cur.getOpcode();
                if (op == Opcodes.GOTO) {
                    q.add(((JumpInsnNode) cur).label);
                    break;
                } else if ((op >= Opcodes.IFEQ && op <= Opcodes.IF_ACMPNE) || op == Opcodes.IFNULL || op == Opcodes.IFNONNULL) {
                    q.add(((JumpInsnNode) cur).label);
                    cur = cur.getNext();
                } else if (op == Opcodes.LOOKUPSWITCH) {
                    LookupSwitchInsnNode lsin = (LookupSwitchInsnNode) cur;
                    q.add(lsin.dflt);
                    q.addAll(lsin.labels);
                    break;
                } else if (op == Opcodes.TABLESWITCH) {
                    TableSwitchInsnNode tsin = (TableSwitchInsnNode) cur;
                    q.add(tsin.dflt);
                    q.addAll(tsin.labels);
                    break;
                } else if ((op >= Opcodes.IRETURN && op <= Opcodes.RETURN) || op == Opcodes.ATHROW) {
                    break;
                } else {
                    cur = cur.getNext();
                }
            }
        }

        for (AbstractInsnNode insn : insns.toArray()) {
            if (!(insn instanceof LabelNode) && !reachable.contains(insn)) {
                insns.remove(insn);
            }
        }
        mn.tryCatchBlocks.removeIf(tcb -> {
            AbstractInsnNode c = tcb.start;
            while (c != null && c != tcb.end) {
                if (!(c instanceof LabelNode) && reachable.contains(c)) return false;
                c = c.getNext();
            }
            return true;
        });
        for (AbstractInsnNode insn : insns.toArray()) {
            if (insn instanceof JumpInsnNode jin && jin.getOpcode() == Opcodes.GOTO) {
                if (TestFullPurify.skipMeta(jin.getNext()) == TestFullPurify.skipMeta(jin.label)) {
                    insns.remove(jin);
                }
            }
        }
    }

    public static Map<AbstractInsnNode, Integer> propagateSeedBothSwitches(ClassNode cn, MethodNode mn, int seedVar, Set<String> xorHelpers) {
        Map<AbstractInsnNode, Integer> seedBefore = new HashMap<>();
        InsnList insns = mn.instructions;
        if (insns.size() == 0) return seedBefore;

        Queue<Map.Entry<AbstractInsnNode, Integer>> queue = new ArrayDeque<>();
        Set<AbstractInsnNode> visitedWithNull = new HashSet<>();
        queue.add(new AbstractMap.SimpleEntry<>(insns.getFirst(), null));
        visitedWithNull.add(insns.getFirst());

        while (!queue.isEmpty()) {
            var entry = queue.poll();
            AbstractInsnNode cur = entry.getKey();
            Integer seed = entry.getValue();

            while (cur != null) {
                if (seed != null) {
                    if (seedBefore.containsKey(cur)) break;
                    seedBefore.put(cur, seed);
                }

                if (cur instanceof VarInsnNode vin && vin.getOpcode() == Opcodes.ISTORE && vin.var == seedVar) {
                    Integer newSeed = evalSeedStore(cur, seedVar, seed, xorHelpers);
                    if (newSeed != null) seed = newSeed;
                }

                int op = cur.getOpcode();
                if (op == Opcodes.GOTO) {
                    JumpInsnNode jin = (JumpInsnNode) cur;
                    enqueue(jin.label, seed, seedBefore, visitedWithNull, queue);
                    break;
                } else if ((op >= Opcodes.IFEQ && op <= Opcodes.IF_ACMPNE) || op == Opcodes.IFNULL || op == Opcodes.IFNONNULL) {
                    JumpInsnNode jin = (JumpInsnNode) cur;
                    Integer branchTaken = evalOpaqueJump(jin, seedVar, seed);
                    if (branchTaken != null) {
                        enqueue(branchTaken == 1 ? jin.label : cur.getNext(), seed, seedBefore, visitedWithNull, queue);
                    } else {
                        enqueue(jin.label, seed, seedBefore, visitedWithNull, queue);
                        enqueue(cur.getNext(), seed, seedBefore, visitedWithNull, queue);
                    }
                    break;
                } else if (op == Opcodes.LOOKUPSWITCH || op == Opcodes.TABLESWITCH) {
                    LabelNode target = evalOpaqueSwitch(cur, seedVar, seed);
                    if (target != null) {
                        enqueue(target, seed, seedBefore, visitedWithNull, queue);
                    } else {
                        enqueue(VerifyZeroTrace.getSwitchDefault(cur), seed, seedBefore, visitedWithNull, queue);
                        for (LabelNode l : VerifyZeroTrace.getSwitchCaseLabels(cur)) enqueue(l, seed, seedBefore, visitedWithNull, queue);
                    }
                    break;
                } else if ((op >= Opcodes.IRETURN && op <= Opcodes.RETURN) || op == Opcodes.ATHROW) {
                    if (seed != null) propagateToHandlers(mn, cur, seed, seedBefore, visitedWithNull, queue);
                    break;
                }

                if (seed != null) propagateToHandlers(mn, cur, seed, seedBefore, visitedWithNull, queue);
                cur = cur.getNext();
            }
        }
        return seedBefore;
    }

    private static void propagateToHandlers(MethodNode mn, AbstractInsnNode cur, int seed,
            Map<AbstractInsnNode, Integer> seedBefore, Set<AbstractInsnNode> visitedWithNull,
            Queue<Map.Entry<AbstractInsnNode, Integer>> queue) {
        int idx = mn.instructions.indexOf(cur);
        for (TryCatchBlockNode tcb : mn.tryCatchBlocks) {
            int startIdx = mn.instructions.indexOf(tcb.start);
            int endIdx = mn.instructions.indexOf(tcb.end);
            if (idx >= startIdx && idx < endIdx) {
                enqueue(tcb.handler, seed, seedBefore, visitedWithNull, queue);
            }
        }
    }

    private static void enqueue(AbstractInsnNode next, Integer seed,
            Map<AbstractInsnNode, Integer> seedBefore, Set<AbstractInsnNode> visitedWithNull,
            Queue<Map.Entry<AbstractInsnNode, Integer>> queue) {
        if (next == null) return;
        if (seed == null) {
            if (visitedWithNull.add(next)) queue.add(new AbstractMap.SimpleEntry<>(next, null));
        } else {
            if (!seedBefore.containsKey(next)) queue.add(new AbstractMap.SimpleEntry<>(next, seed));
        }
    }

    private static Integer evalSeedStore(AbstractInsnNode istoreInsn, int seedVar, Integer curSeed, Set<String> xorHelpers) {
        AbstractInsnNode p1 = istoreInsn.getPrevious();
        if (p1 == null) return null;
        if (p1 instanceof MethodInsnNode min && xorHelpers.contains(min.name) && min.desc.equals("(II)I")) {
            AbstractInsnNode a2 = p1.getPrevious();
            AbstractInsnNode a1 = a2 != null ? a2.getPrevious() : null;
            Integer v2 = evalTerm(a2, seedVar, curSeed);
            Integer v1 = evalTerm(a1, seedVar, curSeed);
            if (v1 != null && v2 != null) return v1 ^ v2;
        }
        if (p1.getOpcode() == Opcodes.IXOR) {
            AbstractInsnNode p2 = p1.getPrevious();
            if (p2 != null && p2.getOpcode() == Opcodes.IXOR) {
                AbstractInsnNode t3 = p2.getPrevious();
                AbstractInsnNode t2 = prevTermNode(t3);
                AbstractInsnNode t1 = prevTermNode(t2);
                Integer v3 = evalTermNode(t3, seedVar, curSeed);
                Integer v2 = evalTermNode(t2, seedVar, curSeed);
                Integer v1 = evalTermNode(t1, seedVar, curSeed);
                if (v1 != null && v2 != null && v3 != null) return v1 ^ v2 ^ v3;
            } else {
                AbstractInsnNode t2 = p1.getPrevious();
                AbstractInsnNode t1 = prevTermNode(t2);
                Integer v2 = evalTermNode(t2, seedVar, curSeed);
                Integer v1 = evalTermNode(t1, seedVar, curSeed);
                if (v1 != null && v2 != null) return v1 ^ v2;
            }
        }
        return null;
    }

    private static AbstractInsnNode prevTermNode(AbstractInsnNode node) {
        if (node == null) return null;
        if (node instanceof MethodInsnNode min && min.owner.equals("java/lang/Integer") && min.name.equals("parseInt")) {
            AbstractInsnNode arg = node.getPrevious();
            return arg != null ? arg.getPrevious() : null;
        }
        return node.getPrevious();
    }

    private static Integer evalTermNode(AbstractInsnNode node, int seedVar, Integer curSeed) {
        if (node == null) return null;
        if (node instanceof MethodInsnNode min && min.owner.equals("java/lang/Integer") && min.name.equals("parseInt")) {
            AbstractInsnNode arg = node.getPrevious();
            if (arg instanceof LdcInsnNode ldc && ldc.cst instanceof String s) return Integer.parseInt(s);
            return null;
        }
        return evalTerm(node, seedVar, curSeed);
    }

    private static Integer evalTerm(AbstractInsnNode node, int seedVar, Integer curSeed) {
        if (node == null) return null;
        Integer c = TestFullSim.getIntConst(node);
        if (c != null) return c;
        if (node instanceof VarInsnNode vin && vin.getOpcode() == Opcodes.ILOAD && vin.var == seedVar) return curSeed;
        return null;
    }

    private static Integer evalOpaqueJump(JumpInsnNode jin, int seedVar, Integer curSeed) {
        if (curSeed == null) return null;
        int op = jin.getOpcode();
        if (op != Opcodes.IF_ICMPEQ && op != Opcodes.IF_ICMPNE) return null;
        AbstractInsnNode p1 = jin.getPrevious();
        Integer rhs = null;
        AbstractInsnNode callNode = null;
        if (p1 != null && p1.getOpcode() == Opcodes.IXOR) {
            AbstractInsnNode p2 = p1.getPrevious();
            AbstractInsnNode p3 = p2 != null ? p2.getPrevious() : null;
            Integer v2 = evalTerm(p2, seedVar, curSeed);
            Integer v3 = evalTerm(p3, seedVar, curSeed);
            if (v2 != null && v3 != null) {
                rhs = v2 ^ v3;
                callNode = p3.getPrevious();
            }
        } else {
            rhs = TestFullSim.getIntConst(p1);
            if (rhs != null) callNode = p1.getPrevious();
        }
        if (rhs != null && callNode instanceof MethodInsnNode min && min.owner.equals("ryzwriqrxnniuxrp/qajywlqkmlrdtwtx")) {
            AbstractInsnNode arg = callNode.getPrevious();
            if (arg instanceof VarInsnNode vin && vin.getOpcode() == Opcodes.ILOAD && vin.var == seedVar) {
                int lhs = min.name.equals("thdsivcnwkjunctr") ? TestFullSim.thds(curSeed) : TestFullSim.edlq(curSeed);
                boolean eq = (lhs == rhs);
                boolean taken = (op == Opcodes.IF_ICMPEQ) ? eq : !eq;
                return taken ? 1 : 0;
            }
        }
        return null;
    }

    private static LabelNode evalOpaqueSwitch(AbstractInsnNode sw, int seedVar, Integer curSeed) {
        if (curSeed == null) return null;
        AbstractInsnNode p1 = sw.getPrevious();
        if (p1 instanceof MethodInsnNode min && min.owner.equals("ryzwriqrxnniuxrp/qajywlqkmlrdtwtx")) {
            AbstractInsnNode arg = p1.getPrevious();
            if (arg instanceof VarInsnNode vin && vin.getOpcode() == Opcodes.ILOAD && vin.var == seedVar) {
                int val = min.name.equals("thdsivcnwkjunctr") ? TestFullSim.thds(curSeed) : TestFullSim.edlq(curSeed);
                return VerifyZeroTrace.evalSwitchTarget(sw, val);
            }
        }
        return null;
    }
}



static class TestPeephole {
    public static boolean isPureConstOrLoad(AbstractInsnNode insn) {
        if (insn == null) return false;
        int op = insn.getOpcode();
        if (op >= Opcodes.ACONST_NULL && op <= Opcodes.LDC) return true;
        if (op >= Opcodes.ILOAD && op <= Opcodes.ALOAD) return true;
        return false;
    }

    public static void optimizeLocals(MethodNode mn) {
        InsnList insns = mn.instructions;
        for (int pass = 0; pass < 200; pass++) {
            boolean changed = false;
            Map<Integer, List<AbstractInsnNode>> writes = new HashMap<>();
            Map<Integer, List<VarInsnNode>> reads = new HashMap<>();
            for (AbstractInsnNode insn : insns) {
                int op = insn.getOpcode();
                if (op >= Opcodes.ISTORE && op <= Opcodes.ASTORE) {
                    VarInsnNode vin = (VarInsnNode) insn;
                    writes.computeIfAbsent(vin.var, k -> new ArrayList<>()).add(vin);
                } else if (op == Opcodes.IINC) {
                    IincInsnNode iinc = (IincInsnNode) insn;
                    writes.computeIfAbsent(iinc.var, k -> new ArrayList<>()).add(iinc);
                } else if (op >= Opcodes.ILOAD && op <= Opcodes.ALOAD) {
                    VarInsnNode vin = (VarInsnNode) insn;
                    reads.computeIfAbsent(vin.var, k -> new ArrayList<>()).add(vin);
                }
            }

            for (var entry : writes.entrySet()) {
                int var = entry.getKey();
                List<AbstractInsnNode> wList = entry.getValue();
                if (wList.size() != 1) continue;
                AbstractInsnNode writeInsn = wList.get(0);
                if (!(writeInsn instanceof VarInsnNode storeNode)) continue;
                AbstractInsnNode prev = storeNode.getPrevious();
                if (!isPureConstOrLoad(prev)) continue;

                int paramEndSlot = (mn.access & Opcodes.ACC_STATIC) != 0 ? 0 : 1;
                for (Type argT : Type.getArgumentTypes(mn.desc)) paramEndSlot += argT.getSize();
                if (var < paramEndSlot) continue;
                if (prev instanceof VarInsnNode srcLoad) {
                    List<AbstractInsnNode> srcWrites = writes.getOrDefault(srcLoad.var, Collections.emptyList());
                    if (srcLoad.var < paramEndSlot) {
                        if (!srcWrites.isEmpty()) continue;
                    } else {
                        if (srcWrites.size() != 1) continue;
                        if (srcWrites.get(0) instanceof IincInsnNode) continue;
                        if (insns.indexOf(srcWrites.get(0)) > insns.indexOf(prev)) continue;
                    }
                }

                List<VarInsnNode> rList = reads.getOrDefault(var, Collections.emptyList());
                for (VarInsnNode rNode : rList) {
                    insns.insertBefore(rNode, prev.clone(Collections.emptyMap()));
                    insns.remove(rNode);
                }
                insns.remove(prev);
                insns.remove(storeNode);
                changed = true;
                break;
            }

            if (!changed) {
                for (var entry : writes.entrySet()) {
                    int var = entry.getKey();
                    List<AbstractInsnNode> wList = entry.getValue();
                    List<VarInsnNode> rList = reads.getOrDefault(var, Collections.emptyList());
                    if (wList.size() == 1 && rList.size() == 1 && wList.get(0) instanceof VarInsnNode storeNode) {
                        VarInsnNode loadNode = rList.get(0);
                        if (storeNode.getNext() == loadNode) {
                            insns.remove(storeNode);
                            insns.remove(loadNode);
                            changed = true;
                            break;
                        }
                    }
                }
            }

            if (!changed) break;
        }
        cleanUnusedLabels(mn);
    }

    public static void stripUnreachableCode(MethodNode mn) {
        for (int iter = 0; iter < 10; iter++) {
            cleanUnusedLabels(mn);
            boolean removed = false;
            boolean unreachable = false;
            for (AbstractInsnNode insn : mn.instructions.toArray()) {
                if (insn instanceof LabelNode) {
                    unreachable = false;
                } else if (unreachable) {
                    mn.instructions.remove(insn);
                    removed = true;
                } else {
                    int op = insn.getOpcode();
                    if ((op >= Opcodes.IRETURN && op <= Opcodes.RETURN) || op == Opcodes.ATHROW || op == Opcodes.GOTO) {
                        unreachable = true;
                    }
                }
            }
            if (!removed) break;
        }
        cleanUnusedLabels(mn);
    }

    private static AbstractInsnNode nextReal(AbstractInsnNode insn) {
        while (insn != null && (insn instanceof LineNumberNode || insn instanceof FrameNode)) {
            insn = insn.getNext();
        }
        return insn;
    }

    private static AbstractInsnNode prevReal(AbstractInsnNode insn) {
        while (insn != null && (insn instanceof LineNumberNode || insn instanceof FrameNode)) {
            insn = insn.getPrevious();
        }
        return insn;
    }

    private static int negateJumpOp(int op) {
        return switch (op) {
            case Opcodes.IFEQ -> Opcodes.IFNE;
            case Opcodes.IFNE -> Opcodes.IFEQ;
            case Opcodes.IFLT -> Opcodes.IFGE;
            case Opcodes.IFGE -> Opcodes.IFLT;
            case Opcodes.IFGT -> Opcodes.IFLE;
            case Opcodes.IFLE -> Opcodes.IFGT;
            case Opcodes.IFNULL -> Opcodes.IFNONNULL;
            case Opcodes.IFNONNULL -> Opcodes.IFNULL;
            case Opcodes.IF_ICMPEQ -> Opcodes.IF_ICMPNE;
            case Opcodes.IF_ICMPNE -> Opcodes.IF_ICMPEQ;
            case Opcodes.IF_ICMPLT -> Opcodes.IF_ICMPGE;
            case Opcodes.IF_ICMPGE -> Opcodes.IF_ICMPLT;
            case Opcodes.IF_ICMPGT -> Opcodes.IF_ICMPLE;
            case Opcodes.IF_ICMPLE -> Opcodes.IF_ICMPGT;
            case Opcodes.IF_ACMPEQ -> Opcodes.IF_ACMPNE;
            case Opcodes.IF_ACMPNE -> Opcodes.IF_ACMPEQ;
            default -> op;
        };
    }

    public static void inlineReturnStubs(MethodNode mn) {
        InsnList insns = mn.instructions;
        Map<LabelNode, List<AbstractInsnNode>> returnStubs = new HashMap<>();
        for (AbstractInsnNode insn : insns) {
            if (insn instanceof LabelNode ln) {
                AbstractInsnNode r1 = nextReal(ln.getNext());
                if (r1 == null) continue;
                if (r1.getOpcode() == Opcodes.RETURN) {
                    returnStubs.put(ln, List.of(r1));
                } else if (isPureConstOrLoad(r1) || r1.getOpcode() == Opcodes.GETSTATIC) {
                    AbstractInsnNode r2 = nextReal(r1.getNext());
                    if (r2 != null && r2.getOpcode() >= Opcodes.IRETURN && r2.getOpcode() <= Opcodes.ARETURN) {
                        returnStubs.put(ln, List.of(r1, r2));
                    }
                }
            }
        }
        if (returnStubs.isEmpty()) return;

        for (AbstractInsnNode insn : insns.toArray()) {
            if (insn instanceof JumpInsnNode jin && jin.getOpcode() != Opcodes.GOTO) {
                List<AbstractInsnNode> stub = returnStubs.get(jin.label);
                if (stub == null) continue;
                if (nextReal(jin.getNext()) == jin.label) continue;

                LabelNode skipLabel = new LabelNode();
                jin.setOpcode(negateJumpOp(jin.getOpcode()));
                jin.label = skipLabel;
                insns.insert(jin, skipLabel);
                for (int i = stub.size() - 1; i >= 0; i--) {
                    insns.insert(jin, stub.get(i).clone(Collections.emptyMap()));
                }
            }
        }
    }

    public static void cleanUnusedLabels(MethodNode mn) {
        Set<LabelNode> used = new HashSet<>();
        for (TryCatchBlockNode tcb : mn.tryCatchBlocks) {
            used.add(tcb.start); used.add(tcb.end); used.add(tcb.handler);
        }
        for (AbstractInsnNode insn : mn.instructions) {
            if (insn instanceof JumpInsnNode jin) used.add(jin.label);
            else if (insn instanceof LookupSwitchInsnNode lsin) {
                used.add(lsin.dflt); used.addAll(lsin.labels);
            } else if (insn instanceof TableSwitchInsnNode tsin) {
                used.add(tsin.dflt); used.addAll(tsin.labels);
            }
        }
        for (AbstractInsnNode insn : mn.instructions.toArray()) {
            if (insn instanceof LineNumberNode lnn) {
                mn.instructions.remove(lnn);
            } else if (insn instanceof LabelNode ln && !used.contains(ln)) {
                mn.instructions.remove(ln);
            }
        }
    }
}



static class BuildSemanticMap {
    public static final Map<String, String> EXPLICIT_CLASS_MAP = new LinkedHashMap<>();
    static {
        // Combat modules & helpers (need/clarity/modules/a -> need/clarity/modules/combat)
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/A", "need/clarity/modules/combat/Triggerbot");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/a", "need/clarity/modules/combat/AimAssist");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/b", "need/clarity/modules/combat/AimAssistRotationProvider");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/c", "need/clarity/modules/combat/AntiBot");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/d", "need/clarity/modules/combat/AutoAnchor");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/e", "need/clarity/modules/combat/AutoAnchorStage");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/f", "need/clarity/modules/combat/AutoCrystal");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/g", "need/clarity/modules/combat/AutoCrystalStage");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/h", "need/clarity/modules/combat/AutoHitCrystal");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/i", "need/clarity/modules/combat/AutoHitCrystalStage");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/j", "need/clarity/modules/combat/AutoLunge");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/k", "need/clarity/modules/combat/AutoLungeStage");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/l", "need/clarity/modules/combat/AutoTrap");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/m", "need/clarity/modules/combat/AutoTrapPlacement");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/n", "need/clarity/modules/combat/AutoTrapPlacedBlock");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/o", "need/clarity/modules/combat/Backtrack");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/p", "need/clarity/modules/combat/DelayedPacketEntry");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/q", "need/clarity/modules/combat/HitSwap");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/r", "need/clarity/modules/combat/HitSwapStage");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/s", "need/clarity/modules/combat/KillAura");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/t", "need/clarity/modules/combat/Piercing");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/u", "need/clarity/modules/combat/PiercingTargetHit");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/v", "need/clarity/modules/combat/PiercingRaySegment");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/w", "need/clarity/modules/combat/Reach");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/x", "need/clarity/modules/combat/ShieldBreaker");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/y", "need/clarity/modules/combat/ShieldBreakerStage");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/z", "need/clarity/modules/combat/SprintReset");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/a/a", "need/clarity/modules/combat/sprintreset/SprintResetContext");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/a/b", "need/clarity/modules/combat/sprintreset/AbstractSprintResetMode");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/a/c", "need/clarity/modules/combat/sprintreset/STapSprintResetMode");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/a/a/d", "need/clarity/modules/combat/sprintreset/WTapSprintResetMode");

        // Utility modules & helpers (need/clarity/modules/b -> need/clarity/modules/utility)
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/a", "need/clarity/modules/utility/AntiCob");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/b", "need/clarity/modules/utility/AutoArmour");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/c", "need/clarity/modules/utility/EquipmentSlotSwitchMap");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/d", "need/clarity/modules/utility/AutoArmourStage");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/e", "need/clarity/modules/utility/ArmourSwapAction");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/f", "need/clarity/modules/utility/AutoTool");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/g", "need/clarity/modules/utility/AutoWeb");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/h", "need/clarity/modules/utility/ChestStealer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/i", "need/clarity/modules/utility/ChestStealerStage");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/j", "need/clarity/modules/utility/ClientSpoofer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/k", "need/clarity/modules/utility/CrystalOptimizer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/l", "need/clarity/modules/utility/Disabler");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/m", "need/clarity/modules/utility/FakePlayer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/n", "need/clarity/modules/utility/FastPlace");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/o", "need/clarity/modules/utility/GhostHand");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/p", "need/clarity/modules/utility/HoverTotem");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/q", "need/clarity/modules/utility/InventoryCleaner");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/r", "need/clarity/modules/utility/InventoryCleanerStage");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/s", "need/clarity/modules/utility/MLG");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/t", "need/clarity/modules/utility/MLGStage");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/u", "need/clarity/modules/utility/NameProtect");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/v", "need/clarity/modules/utility/NoBreakDelay");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/w", "need/clarity/modules/utility/NoJumpDelay");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/x", "need/clarity/modules/utility/Overtick");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/y", "need/clarity/modules/utility/QueuedPacketEntry");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/z", "need/clarity/modules/utility/PlayerGhostSnapshot");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/A", "need/clarity/modules/utility/OvertickCapabilityProvider");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/B", "need/clarity/modules/utility/RightClicker");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/C", "need/clarity/modules/utility/SilentTotem");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/D", "need/clarity/modules/utility/SilentTotemStage");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/E", "need/clarity/modules/utility/SnapTapUtility");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/F", "need/clarity/modules/utility/Targets");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/G", "need/clarity/modules/utility/XCarry");

        // MarlowCrystal protocol (need/clarity/modules/b/a -> need/clarity/modules/utility/marlow)
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/a/a", "need/clarity/modules/utility/marlow/MarlowServerTracker");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/a/b", "need/clarity/modules/utility/marlow/BoundedLruList");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/a/c", "need/clarity/modules/utility/marlow/MarlowChannelIdentifiers");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/a/d", "need/clarity/modules/utility/marlow/ChallengePacketPayload");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/a/e", "need/clarity/modules/utility/marlow/ChallengePacketCodec");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/a/f", "need/clarity/modules/utility/marlow/ChallengeResponsePayload");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/a/g", "need/clarity/modules/utility/marlow/ChallengeResponseCodec");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/a/h", "need/clarity/modules/utility/marlow/OptOutPacketPayload");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/a/i", "need/clarity/modules/utility/marlow/OptOutPacketCodec");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/a/j", "need/clarity/modules/utility/marlow/LegacyOptOutPacketPayload");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/a/k", "need/clarity/modules/utility/marlow/LegacyOptOutPacketCodec");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/a/l", "need/clarity/modules/utility/marlow/ModVersionPacketPayload");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/b/a/m", "need/clarity/modules/utility/marlow/ModVersionPacketCodec");

        // Movement modules & helpers
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/movement/a", "need/clarity/modules/movement/AntiHunger");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/movement/b", "need/clarity/modules/movement/AntiLevitation");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/movement/c", "need/clarity/modules/movement/AntiPowderSnow");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/movement/d", "need/clarity/modules/movement/AntiPush");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/movement/e", "need/clarity/modules/movement/ElytraJump");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/movement/f", "need/clarity/modules/movement/Fly");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/movement/g", "need/clarity/modules/movement/InventoryWalk");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/movement/h", "need/clarity/modules/movement/MoveFix");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/movement/i", "need/clarity/modules/movement/MoveFixCapabilityProvider");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/movement/j", "need/clarity/modules/movement/NoFall");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/movement/k", "need/clarity/modules/movement/MovementNoJumpDelay");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/movement/l", "need/clarity/modules/movement/ReverseStep");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/movement/m", "need/clarity/modules/movement/SnapTap");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/movement/n", "need/clarity/modules/movement/Speed");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/movement/o", "need/clarity/modules/movement/Sprint");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/movement/p", "need/clarity/modules/movement/Step");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/movement/q", "need/clarity/modules/movement/WindCharge");

        // Visual modules & helpers
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/a", "need/clarity/modules/visual/Ambience");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/b", "need/clarity/modules/visual/Animations");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/c", "need/clarity/modules/visual/ArrayListModule");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/d", "need/clarity/modules/visual/ModuleSuffixFormatter");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/e", "need/clarity/modules/visual/Aspect");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/f", "need/clarity/modules/visual/Aura");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/g", "need/clarity/modules/visual/AutoF5");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/h", "need/clarity/modules/visual/BlockESP");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/i", "need/clarity/modules/visual/BlockESPChunkScanner");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/j", "need/clarity/modules/visual/BlockESPEntry");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/k", "need/clarity/modules/visual/CameraTweaks");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/l", "need/clarity/modules/visual/Chams");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/m", "need/clarity/modules/visual/ChamsCapabilityProvider");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/n", "need/clarity/modules/visual/CrystalPopEffectEntry");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/o", "need/clarity/modules/visual/CrystalTrackState");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/p", "need/clarity/modules/visual/CustomCrosshair");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/q", "need/clarity/modules/visual/Freecam");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/r", "need/clarity/modules/visual/FreecamCapabilityProvider");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/s", "need/clarity/modules/visual/Fullbright");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/t", "need/clarity/modules/visual/HUD");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/u", "need/clarity/modules/visual/HandShaders");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/v", "need/clarity/modules/visual/Hats");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/w", "need/clarity/modules/visual/HealthTags");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/x", "need/clarity/modules/visual/HitParticles");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/y", "need/clarity/modules/visual/HitParticleBurstEntry");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/z", "need/clarity/modules/visual/HitSounds");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/A", "need/clarity/modules/visual/HudBlurHelper");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/B", "need/clarity/modules/visual/ItemESP");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/C", "need/clarity/modules/visual/ItemTags");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/D", "need/clarity/modules/visual/JumpEffect");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/E", "need/clarity/modules/visual/JumpCircleEntry");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/F", "need/clarity/modules/visual/KeybindList");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/G", "need/clarity/modules/visual/KeybindListEntry");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/H", "need/clarity/modules/visual/KillEffect");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/I", "need/clarity/modules/visual/KillSounds");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/J", "need/clarity/modules/visual/MotionBlur");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/K", "need/clarity/modules/visual/MusicBeats");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/L", "need/clarity/modules/visual/MusicPlayer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/M", "need/clarity/modules/visual/Nametags");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/N", "need/clarity/modules/visual/NoBob");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/O", "need/clarity/modules/visual/NoFOV");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/P", "need/clarity/modules/visual/NoHurtCam");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/Q", "need/clarity/modules/visual/NoSwing");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/R", "need/clarity/modules/visual/Notifications");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/S", "need/clarity/modules/visual/NotificationToastEntry");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/T", "need/clarity/modules/visual/Particles");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/U", "need/clarity/modules/visual/PlayerESP");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/V", "need/clarity/modules/visual/PlayerTrails");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/W", "need/clarity/modules/visual/PlayerTrailPoint");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/X", "need/clarity/modules/visual/RenderAnimations");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/Y", "need/clarity/modules/visual/Search");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/Z", "need/clarity/modules/visual/SearchChunkScanner");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/aa", "need/clarity/modules/visual/SearchBlockEntry");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/ab", "need/clarity/modules/visual/ServerRotations");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/ac", "need/clarity/modules/visual/ShaderESP");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/ad", "need/clarity/modules/visual/SkinChanger");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/ae", "need/clarity/modules/visual/StorageESP");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/af", "need/clarity/modules/visual/TNTTimer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/ag", "need/clarity/modules/visual/TargetESP");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/ah", "need/clarity/modules/visual/TargetHud");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/ai", "need/clarity/modules/visual/ThemeTexturesModule");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/aj", "need/clarity/modules/visual/Tracers");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/ak", "need/clarity/modules/visual/TracerArrowEntry");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/al", "need/clarity/modules/visual/TracerTargetGroup");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/am", "need/clarity/modules/visual/Trajectories");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/an", "need/clarity/modules/visual/TrajectoryLineRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/ao", "need/clarity/modules/visual/ProjectilePhysicsSpec");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/ap", "need/clarity/modules/visual/TrueSight");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/aq", "need/clarity/modules/visual/ViewModel");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/ar", "need/clarity/modules/visual/Wings");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/as", "need/clarity/modules/visual/Zoom");

        // Kenny 3D PlayerModel subpackage (need/clarity/modules/visual/a -> need/clarity/modules/visual/kenny)
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/a/a", "need/clarity/modules/visual/kenny/KennyModelParts");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/a/b", "need/clarity/modules/visual/kenny/KennyCharacterRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/a/c", "need/clarity/modules/visual/kenny/Matrix4x4Math");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/a/d", "need/clarity/modules/visual/kenny/CompiledMeshPart");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/a/e", "need/clarity/modules/visual/kenny/KennyMeshBuilder");

        // Wings subpackage (need/clarity/modules/visual/b -> need/clarity/modules/visual/wings)
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/b/a", "need/clarity/modules/visual/wings/WingColorMath");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/b/b", "need/clarity/modules/visual/wings/WingRenderContext");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/b/c", "need/clarity/modules/visual/wings/WingQuadSink");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/b/d", "need/clarity/modules/visual/wings/WingStyle");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/b/e", "need/clarity/modules/visual/wings/WingGeometryRenderer");

        // Cosmetics core (need/clarity/modules/visual/cosmetics/*)
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a", "need/clarity/modules/visual/cosmetics/CosmeticAnchor");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b", "need/clarity/modules/visual/cosmetics/AnchorTransform");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/c", "need/clarity/modules/visual/cosmetics/EntityPoseState");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/d", "need/clarity/modules/visual/cosmetics/CosmeticDrawBuilder");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/e", "need/clarity/modules/visual/cosmetics/ColorMathUtils");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/f", "need/clarity/modules/visual/cosmetics/ICosmeticEffectRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/g", "need/clarity/modules/visual/cosmetics/GeometryPrimitives");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/h", "need/clarity/modules/visual/cosmetics/ISurfaceColorShader");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/i", "need/clarity/modules/visual/cosmetics/ParametricSurfaceSpec");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/j", "need/clarity/modules/visual/cosmetics/IGradientColorSampler");

        // Cosmetics Aura (need/clarity/modules/visual/cosmetics/a -> need/clarity/modules/visual/cosmetics/aura)
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/a", "need/clarity/modules/visual/cosmetics/aura/AuraRenderMath");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/b", "need/clarity/modules/visual/cosmetics/aura/OrbitAuraSpec");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/c", "need/clarity/modules/visual/cosmetics/aura/AuraAnimationClock");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/d", "need/clarity/modules/visual/cosmetics/aura/AuraClockState");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/e", "need/clarity/modules/visual/cosmetics/aura/GroundRunesAuraSpec");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/f", "need/clarity/modules/visual/cosmetics/aura/ElementalAuraSpec");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/g", "need/clarity/modules/visual/cosmetics/aura/AuraElementStyle");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/h", "need/clarity/modules/visual/cosmetics/aura/EnergySphereAuraRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/i", "need/clarity/modules/visual/cosmetics/aura/SphereMeshProjector");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/j", "need/clarity/modules/visual/cosmetics/aura/ProjectedVertexState");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/k", "need/clarity/modules/visual/cosmetics/aura/FlameAuraRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/l", "need/clarity/modules/visual/cosmetics/aura/FrostAuraRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/m", "need/clarity/modules/visual/cosmetics/aura/LightningArcsAuraRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/n", "need/clarity/modules/visual/cosmetics/aura/LightningBoltPath");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/o", "need/clarity/modules/visual/cosmetics/aura/LightningArcsAuraSpec");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/p", "need/clarity/modules/visual/cosmetics/aura/AuraOrbitStyle");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/q", "need/clarity/modules/visual/cosmetics/aura/OrbsOrbitRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/r", "need/clarity/modules/visual/cosmetics/aura/RingsOrbitRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/s", "need/clarity/modules/visual/cosmetics/aura/SphereRippleImpulse");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/t", "need/clarity/modules/visual/cosmetics/aura/GroundRunesAuraRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/u", "need/clarity/modules/visual/cosmetics/aura/RunesOrbitRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/v", "need/clarity/modules/visual/cosmetics/aura/SakuraAuraRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/w", "need/clarity/modules/visual/cosmetics/aura/ShardsOrbitRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/x", "need/clarity/modules/visual/cosmetics/aura/GeodesicSphereTopology");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/y", "need/clarity/modules/visual/cosmetics/aura/EnergySphereAuraSpec");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/z", "need/clarity/modules/visual/cosmetics/aura/StormAuraRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/A", "need/clarity/modules/visual/cosmetics/aura/StormCloudCell");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/B", "need/clarity/modules/visual/cosmetics/aura/SwordsOrbitRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/a/C", "need/clarity/modules/visual/cosmetics/aura/VoidAuraRenderer");

        // Cosmetics Hats (need/clarity/modules/visual/cosmetics/b -> need/clarity/modules/visual/cosmetics/hats)
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/a", "need/clarity/modules/visual/cosmetics/hats/HatWaveMath");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/b", "need/clarity/modules/visual/cosmetics/hats/BearEarsHatRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/c", "need/clarity/modules/visual/cosmetics/hats/BunnyEarsHatRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/d", "need/clarity/modules/visual/cosmetics/hats/CatEarsHatRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/e", "need/clarity/modules/visual/cosmetics/hats/ChinaHatRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/f", "need/clarity/modules/visual/cosmetics/hats/ClassicRingHaloRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/g", "need/clarity/modules/visual/cosmetics/hats/DemonHornsHatRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/h", "need/clarity/modules/visual/cosmetics/hats/DoubleRingHaloRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/i", "need/clarity/modules/visual/cosmetics/hats/EarLobedMeshBuilder");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/j", "need/clarity/modules/visual/cosmetics/hats/FlameCrownRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/k", "need/clarity/modules/visual/cosmetics/hats/FloatingCrownRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/l", "need/clarity/modules/visual/cosmetics/hats/FoxEarsHatRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/m", "need/clarity/modules/visual/cosmetics/hats/CrownJewelGeometry");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/n", "need/clarity/modules/visual/cosmetics/hats/HatRenderSpec");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/o", "need/clarity/modules/visual/cosmetics/hats/HatStyle");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/p", "need/clarity/modules/visual/cosmetics/hats/IceCrownRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/q", "need/clarity/modules/visual/cosmetics/hats/LaurelWreathCrownRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/r", "need/clarity/modules/visual/cosmetics/hats/OrbitingOrbsHaloRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/s", "need/clarity/modules/visual/cosmetics/hats/ReindeerAntlersHatRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/t", "need/clarity/modules/visual/cosmetics/hats/RoyalCrownRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/u", "need/clarity/modules/visual/cosmetics/hats/RunicHaloRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/v", "need/clarity/modules/visual/cosmetics/hats/SpikedCrownRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/w", "need/clarity/modules/visual/cosmetics/hats/StagAntlersHatRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/x", "need/clarity/modules/visual/cosmetics/hats/ThornHaloRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/y", "need/clarity/modules/visual/cosmetics/hats/TiaraCrownRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/z", "need/clarity/modules/visual/cosmetics/hats/UnicornHornHatRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/b/A", "need/clarity/modules/visual/cosmetics/hats/VikingHornsHatRenderer");

        // Cosmetics KillEffect (need/clarity/modules/visual/cosmetics/c -> need/clarity/modules/visual/cosmetics/killeffect)
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/c/a", "need/clarity/modules/visual/cosmetics/killeffect/AscendKillRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/c/b", "need/clarity/modules/visual/cosmetics/killeffect/BlackHoleKillRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/c/c", "need/clarity/modules/visual/cosmetics/killeffect/ActiveKillEffectManager");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/c/d", "need/clarity/modules/visual/cosmetics/killeffect/ActiveKillEffectInstance");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/c/e", "need/clarity/modules/visual/cosmetics/killeffect/KillEffectMathUtils");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/c/f", "need/clarity/modules/visual/cosmetics/killeffect/KillEffectRenderSpec");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/c/g", "need/clarity/modules/visual/cosmetics/killeffect/IGroundHeightSampler");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/c/h", "need/clarity/modules/visual/cosmetics/killeffect/KillEffectStyle");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/c/i", "need/clarity/modules/visual/cosmetics/killeffect/LightningStrikeKillRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/c/j", "need/clarity/modules/visual/cosmetics/killeffect/PhoenixKillRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/c/k", "need/clarity/modules/visual/cosmetics/killeffect/ShatterKillRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/c/l", "need/clarity/modules/visual/cosmetics/killeffect/ShockwaveKillRenderer");

        // Cosmetics TargetESP (need/clarity/modules/visual/cosmetics/d -> need/clarity/modules/visual/cosmetics/targetesp)
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/d/a", "need/clarity/modules/visual/cosmetics/targetesp/ChevronLockOnTargetRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/d/b", "need/clarity/modules/visual/cosmetics/targetesp/CornerBracketsTargetRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/d/c", "need/clarity/modules/visual/cosmetics/targetesp/HexShieldTargetRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/d/d", "need/clarity/modules/visual/cosmetics/targetesp/HexCylinderProjector");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/d/e", "need/clarity/modules/visual/cosmetics/targetesp/HexGridDimensions");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/d/f", "need/clarity/modules/visual/cosmetics/targetesp/ProjectedHexPoint");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/d/g", "need/clarity/modules/visual/cosmetics/targetesp/HologramScanTargetRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/d/h", "need/clarity/modules/visual/cosmetics/targetesp/LightPillarTargetRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/d/i", "need/clarity/modules/visual/cosmetics/targetesp/OrbitingTrianglesTargetRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/d/j", "need/clarity/modules/visual/cosmetics/targetesp/RotatingDiamondTargetRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/d/k", "need/clarity/modules/visual/cosmetics/targetesp/SoulFlameTargetRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/d/l", "need/clarity/modules/visual/cosmetics/targetesp/SpinningRingTargetRenderer");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/d/m", "need/clarity/modules/visual/cosmetics/targetesp/TargetEspMathUtils");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/d/n", "need/clarity/modules/visual/cosmetics/targetesp/TargetEspRenderSpec");
        EXPLICIT_CLASS_MAP.put("need/clarity/modules/visual/cosmetics/d/o", "need/clarity/modules/visual/cosmetics/targetesp/TargetEspStyle");
    }

    public static void main(String[] args) throws Exception {
        try (ZipFile zf = new ZipFile(args[0])) {
            Set<String> allClasses = new TreeSet<>();
            Enumeration<? extends ZipEntry> entries = zf.entries();
            while (entries.hasMoreElements()) {
                ZipEntry ze = entries.nextElement();
                if (!ze.getName().endsWith(".class") || ze.getName().startsWith("ryzwriqrxnniuxrp/")) continue;
                allClasses.add(ze.getName().substring(0, ze.getName().length() - 6));
            }
            Set<String> targetNames = new HashSet<>();
            int unmappedShort = 0;
            for (String c : allClasses) {
                String mapped = EXPLICIT_CLASS_MAP.getOrDefault(c, c);
                if (!targetNames.add(mapped)) {
                    System.out.println("COLLISION on target class name: " + mapped);
                }
                String simple = mapped.substring(mapped.lastIndexOf('/') + 1);
                if (simple.contains("$")) simple = simple.substring(simple.lastIndexOf('$') + 1);
                if (simple.length() <= 2 && !simple.equals("P")) {
                    unmappedShort++;
                    System.out.println("Unmapped short class: " + c + " -> " + mapped);
                }
            }
            System.out.println("Total classes: " + allClasses.size() + ", mapped: " + EXPLICIT_CLASS_MAP.size() + ", unmappedShort: " + unmappedShort);
        }
    }
}



static class TestSemanticRemap {
    public static boolean isObfMemberName(String className, String name) {
        if (name.startsWith("<") || name.startsWith("lambda$") || name.startsWith("$")) return false;
        if (!className.startsWith("need/clarity/modules/") && !className.equals("need/clarity/Clarity")
                && !className.equals("need/clarity/server/ClarityServer")) {
            return false;
        }
        return name.length() <= 2;
    }

    public static String toCamelCaseIdentifier(String s, String prefix) {
        StringBuilder sb = new StringBuilder(prefix);
        boolean nextUpper = !prefix.isEmpty();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                if (sb.length() == 0 && Character.isDigit(c)) sb.append('_');
                sb.append(nextUpper ? Character.toUpperCase(c) : c);
                nextUpper = false;
            } else {
                nextUpper = true;
            }
        }
        String res = sb.toString();
        if (res.equals(prefix) || res.isEmpty()) return prefix + "Setting";
        return res;
    }

    public static String semanticFieldBaseName(String owner, FieldNode fn, ClassNode cn) {
        String d = fn.desc;
        if ((fn.access & Opcodes.ACC_STATIC) != 0 && d.equals("L" + owner + ";")) return "INSTANCE";
        if (d.equals("Lorg/slf4j/Logger;")) return "LOGGER";
        if (d.equals("Ljava/lang/reflect/Field;")) {
            // Check <clinit> for getDeclaredField("...")
            for (MethodNode mn : cn.methods) {
                if (mn.name.equals("<clinit>")) {
                    String lastStr = null;
                    for (AbstractInsnNode insn : mn.instructions) {
                        if (insn instanceof LdcInsnNode ldc && ldc.cst instanceof String s) lastStr = s;
                        else if (insn instanceof FieldInsnNode fin && fin.name.equals(fn.name) && lastStr != null) {
                            return toCamelCaseIdentifier(lastStr, "") + "Field";
                        }
                    }
                }
            }
            return "reflectedField";
        }
        if (d.equals("Lnet/minecraft/client/Minecraft;")) return "minecraft";
        if (d.equals("Lnet/minecraft/world/entity/LivingEntity;")) return "targetEntity";
        if (d.equals("Lnet/minecraft/world/entity/player/Player;")) return "targetPlayer";
        if (d.equals("Lnet/minecraft/world/entity/boss/enderdragon/EndCrystal;")) return "endCrystal";
        if (d.equals("Lnet/minecraft/core/BlockPos;")) return "targetBlockPos";
        if (d.equals("Lnet/minecraft/world/phys/BlockHitResult;")) return "blockHitResult";
        if (d.equals("Lnet/minecraft/world/phys/Vec3;")) return "positionVec";
        if (d.equals("Lnet/minecraft/world/phys/AABB;")) return "boundingBox";
        if (d.equals("Lnet/minecraft/world/level/block/Block;")) return "targetBlock";
        if (d.equals("Lnet/minecraft/world/level/chunk/ChunkAccess;")) return "chunkAccess";
        if (d.equals("Lnet/minecraft/network/protocol/Packet;")) return "packet";
        if (d.equals("Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload$Type;")) return "PACKET_TYPE";
        if (d.equals("Lnet/minecraft/network/codec/StreamCodec;")) return "STREAM_CODEC";
        if (d.equals("Lcom/sun/net/httpserver/HttpServer;")) return "httpServer";
        if (d.equals("Ljava/util/concurrent/ExecutorService;")) return "executorService";
        if (d.equals("Ljava/util/concurrent/CompletableFuture;")) return "pendingTaskFuture";
        if (d.equals("Ljava/util/function/Function;") || d.equals("Ljava/util/function/DoubleFunction;")) return "factoryFunction";
        if (d.equals("Ljava/util/function/Predicate;")) return "filterPredicate";
        if (d.equals("Ljava/util/function/BiConsumer;")) return "vertexConsumer";
        if (d.equals("Ljava/util/List;") || d.equals("Ljava/util/ArrayList;")) return "entriesList";
        if (d.equals("Ljava/util/Map;")) return "entriesMap";
        if (d.equals("Ljava/util/Set;")) return "entriesSet";
        if (d.equals("Ljava/util/ArrayDeque;")) return "transformStack";
        if (d.equals("Ljava/lang/String;")) return "labelText";
        if (d.equals("Ljava/lang/Object;")) return (fn.access & Opcodes.ACC_FINAL) != 0 ? "LOCK" : "stateObject";
        if (d.equals("Z")) return "enabledState";
        if (d.equals("J")) return "timestampMs";
        if (d.equals("I")) return ((fn.access & Opcodes.ACC_FINAL) != 0 && (fn.access & Opcodes.ACC_STATIC) != 0) ? "CONST_INT" : "counterValue";
        if (d.equals("F")) return "floatValue";
        if (d.equals("D")) return ((fn.access & Opcodes.ACC_FINAL) != 0 && (fn.access & Opcodes.ACC_STATIC) != 0) ? "CONST_DBL" : "doubleValue";
        if (d.equals("[I")) return "intBuffer";
        if (d.equals("[F")) return "floatBuffer";
        if (d.equals("[D")) return "coordsVector";
        if (d.equals("[[D")) return "vertexMatrix";
        if (d.equals("[[[D")) return "meshTensor";
        if (d.startsWith("Lneed/clarity/modules/")) {
            String refCls = d.substring(1, d.length() - 1);
            String mapped = BuildSemanticMap.EXPLICIT_CLASS_MAP.getOrDefault(refCls, refCls);
            String simple = mapped.substring(mapped.lastIndexOf('/') + 1);
            return Character.toLowerCase(simple.charAt(0)) + simple.substring(1);
        }
        return "fieldVal";
    }

    public static String semanticMethodBaseName(String owner, MethodNode mn, ClassNode cn) {
        String d = mn.desc;
        if (owner.equals("need/clarity/Clarity") && mn.name.equals("a")) return "id";
        if (owner.equals("need/clarity/server/ClarityServer")) {
            if (mn.name.equals("a") && d.equals("()V")) return "init";
            if (mn.name.equals("b") && d.equals("()V")) return "syncServerState";
            if (mn.name.equals("c") && d.equals("()Z")) return "isRunning";
            if (mn.name.equals("d") && d.equals("()V")) return "scheduleStartRetry";
            if (mn.name.equals("e") && d.equals("()V")) return "scheduleAsyncStop";
            if (mn.name.equals("a") && d.equals("(Lcom/sun/net/httpserver/HttpExchange;)V")) return "handleHttpRequest";
            if (mn.name.equals("a") && d.equals("(Ljava/lang/String;)Ljava/lang/String;")) return "escapeJsonString";
            if (mn.name.equals("a") && d.equals("(Lcom/sun/net/httpserver/HttpExchange;Ljava/lang/String;Ljava/lang/String;)V")) return "serveClasspathAsset";
            if (mn.name.equals("a") && d.equals("(Lcom/sun/net/httpserver/HttpExchange;ILjava/lang/String;Ljava/lang/String;)V")) return "sendTextResponse";
            if (mn.name.equals("b") && d.equals("(Lcom/sun/net/httpserver/HttpExchange;)V")) return "sendNotFound";
            if (mn.name.equals("f") && d.equals("()V")) return "lambda$scheduleAsyncStop$0";
            if (mn.name.equals("g") && d.equals("()V")) return "lambda$scheduleStartRetry$0";
            if (mn.name.equals("a") && d.equals("(Ljava/lang/Runnable;)Ljava/lang/Thread;")) return "createRetryWebThread";
            if (mn.name.equals("b") && d.equals("(Ljava/lang/Runnable;)Ljava/lang/Thread;")) return "createWebServerThread";
        }
        // Check if setting getter
        if (d.startsWith("()")) {
            String settingStr = null;
            boolean callsGetSetting = false;
            for (AbstractInsnNode insn : mn.instructions) {
                if (insn instanceof LdcInsnNode ldc && ldc.cst instanceof String s) {
                    if (settingStr == null) settingStr = s;
                } else if (insn instanceof MethodInsnNode min && min.name.equals("getSetting")) {
                    callsGetSetting = true;
                    break;
                }
            }
            if (callsGetSetting && settingStr != null && mn.instructions.size() < 45) {
                String prefix = d.endsWith(")Z") ? "is" : "get";
                return toCamelCaseIdentifier(settingStr, prefix);
            }
        }
        // Check event handler parameters
        if (d.contains("EventPacketReceive")) return "onPacketReceive";
        if (d.contains("EventPacketSend")) return "onPacketSend";
        if (d.contains("EventStartAttack")) return d.endsWith(")Z") ? "shouldTriggerOnAttack" : "onStartAttack";
        if (d.contains("MovementEvent")) return "onMovementTick";
        if (d.contains("WorldRenderEvent") || d.contains("PoseStack") || d.contains("LevelRenderState")) return "renderWorldEffect";
        if (d.contains("FriendlyByteBuf")) {
            return d.endsWith(")V") ? "writePacket" : "readPacket";
        }
        if (d.contains("CosmeticDrawBuilder") || d.contains("Lneed/clarity/modules/visual/cosmetics/d;")) {
            if (d.equals("(Lneed/clarity/modules/visual/cosmetics/d;)V")) return "renderEffect";
            return "drawGeometryPart";
        }
        if (owner.equals("need/clarity/modules/a/a")) {
            if (mn.name.equals("m") && d.equals("()V")) return "clearSilentRotation";
            if (mn.name.equals("a") && d.equals("(FF)V")) return "setSilentRotation";
            if (mn.name.equals("h") && d.equals("()Z")) return "isActivelyTracking";
            if (mn.name.equals("a") && d.equals("()Lnet/minecraft/world/entity/LivingEntity;")) return "getCurrentTarget";
        }
        if (d.equals("(Lnet/minecraft/client/Minecraft;Lnet/minecraft/world/item/Item;)I")) return "findHotbarItemSlot";
        if (d.endsWith(")Lnet/minecraft/world/phys/BlockHitResult;")) return "findBlockHitResult";
        if (d.equals("(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;)[F")) return "calculateRotationsToVec";
        if (d.equals("(Lnet/minecraft/client/Minecraft;I)V")) return "setRightClickDelay";
        if (d.endsWith(")Lneed/clarity/modules/visual/cosmetics/f;") || d.endsWith(")Lneed/clarity/modules/visual/cosmetics/ICosmeticEffectRenderer;")) return "createRenderer";
        if ("java/lang/Enum".equals(cn.superName) && (mn.access & Opcodes.ACC_STATIC) != 0 && d.equals("(Ljava/lang/String;)L" + owner + ";")) return "fromDisplayName";
        if ((mn.access & Opcodes.ACC_SYNTHETIC) != 0) return "lambda$" + mn.name;
        if (d.endsWith(")Z")) return "isEligible";
        if (d.endsWith(")I")) return "computeColorOrIndex";
        if (d.endsWith(")F")) return "computeFloatMetric";
        if (d.endsWith(")D")) return "computeDoubleMetric";
        if (d.endsWith(")[D")) return "computeVector";
        if (d.endsWith(")[[D")) return "computeMatrix";
        if (d.endsWith(")Ljava/lang/String;")) return "resolveLabel";
        if (d.endsWith(")V")) return "applyStep";
        return "evaluate";
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Compiled TestSemanticRemap.");
    }
}



static class RunFullRemapTest {
    public static class ClarityRemapper extends Remapper {
        public final Map<String, String> classMap;
        public final Map<String, String> fieldMap; // "owner.name:desc" -> newName
        public final Map<String, String> methodMap; // "owner.name(desc)" -> newName
        public final Map<String, String> superMap;
        public final Map<String, List<String>> interfacesMap;

        public ClarityRemapper(Map<String, String> classMap, Map<String, String> fieldMap,
                Map<String, String> methodMap, Map<String, String> superMap, Map<String, List<String>> interfacesMap) {
            this.classMap = classMap;
            this.fieldMap = fieldMap;
            this.methodMap = methodMap;
            this.superMap = superMap;
            this.interfacesMap = interfacesMap;
        }

        @Override
        public String map(String internalName) {
            return classMap.getOrDefault(internalName, internalName);
        }

        @Override
        public String mapFieldName(String owner, String name, String descriptor) {
            String cur = owner;
            while (cur != null) {
                String k = cur + "." + name + ":" + descriptor;
                if (fieldMap.containsKey(k)) return fieldMap.get(k);
                cur = superMap.get(cur);
            }
            return name;
        }

        @Override
        public String mapMethodName(String owner, String name, String descriptor) {
            String mapped = lookupMethod(owner, name, descriptor);
            return mapped != null ? mapped : name;
        }

        private String lookupMethod(String owner, String name, String descriptor) {
            if (owner == null) return null;
            String k = owner + "." + name + descriptor;
            if (methodMap.containsKey(k)) return methodMap.get(k);
            String sup = superMap.get(owner);
            if (sup != null) {
                String r = lookupMethod(sup, name, descriptor);
                if (r != null) return r;
            }
            List<String> ifs = interfacesMap.get(owner);
            if (ifs != null) {
                for (String itf : ifs) {
                    String r = lookupMethod(itf, name, descriptor);
                    if (r != null) return r;
                }
            }
            return null;
        }
    }

    public static ClarityRemapper buildRemapper(Map<String, ClassNode> purifiedNodes) {
        Map<String, String> classMap = new LinkedHashMap<>(BuildSemanticMap.EXPLICIT_CLASS_MAP);
        classMap.put("need/clarity/render/ImageParticles$P", "need/clarity/render/ImageParticles$Particle");

        Map<String, String> fieldMap = new LinkedHashMap<>();
        Map<String, String> methodMap = new LinkedHashMap<>();
        Map<String, String> superMap = new HashMap<>();
        Map<String, List<String>> interfacesMap = new HashMap<>();

        for (ClassNode cn : purifiedNodes.values()) {
            superMap.put(cn.name, cn.superName);
            interfacesMap.put(cn.name, cn.interfaces != null ? new ArrayList<>(cn.interfaces) : Collections.emptyList());
        }

        // Pre-seed interface & base class methods so subclasses inherit the exact same mapped name
        methodMap.put("need/clarity/modules/visual/cosmetics/f.a(Lneed/clarity/modules/visual/cosmetics/d;)V", "renderEffect");
        methodMap.put("need/clarity/modules/a/a/b.a(Lneed/clarity/EventStartAttack;)V", "onStartAttack");
        methodMap.put("need/clarity/modules/a/a/b.a(Lneed/clarity/api/MovementEvent;)V", "onMovementTick");
        methodMap.put("need/clarity/modules/a/a/b.b(Lneed/clarity/EventStartAttack;)Z", "shouldTriggerReset");
        methodMap.put("need/clarity/modules/a/a/b.b(Lneed/clarity/api/MovementEvent;)V", "applyMovementReset");
        methodMap.put("need/clarity/modules/a/a/b.a(Lnet/minecraft/world/entity/LivingEntity;)Z", "isValidWeaponAndTarget");
        methodMap.put("need/clarity/modules/a/a/b.a()Z", "isOnlySwordEnabled");

        // 1. Extract Hooks registrations across all classes
        for (ClassNode cn : purifiedNodes.values()) {
            for (MethodNode mn : cn.methods) {
                String lastHookStr = null;
                for (AbstractInsnNode insn : mn.instructions) {
                    if (insn instanceof LdcInsnNode ldc && ldc.cst instanceof String s && s.contains(".")) {
                        lastHookStr = s;
                    } else if (insn instanceof InvokeDynamicInsnNode idin && lastHookStr != null) {
                        for (Object a : idin.bsmArgs) {
                            if (a instanceof Handle h && purifiedNodes.containsKey(h.getOwner())) {
                                AbstractInsnNode next = insn.getNext();
                                while (next != null && !(next instanceof MethodInsnNode)) next = next.getNext();
                                if (next instanceof MethodInsnNode min && min.owner.equals("need/clarity/api/Hooks")) {
                                    String suffix = lastHookStr.substring(lastHookStr.indexOf('.') + 1);
                                    if (TestSemanticRemap.isObfMemberName(h.getOwner(), h.getName())) {
                                        if (h.getDesc().startsWith("()")) {
                                            methodMap.putIfAbsent(h.getOwner() + "." + h.getName() + h.getDesc(), suffix);
                                        } else {
                                            methodMap.putIfAbsent(h.getOwner() + "." + h.getName() + h.getDesc(), "hook$" + suffix);
                                        }
                                    }
                                    lastHookStr = null;
                                }
                            }
                        }
                    }
                }
            }
        }

        // 2. Process Enums and Records first
        for (ClassNode cn : purifiedNodes.values()) {
            if ("java/lang/Enum".equals(cn.superName)) {
                for (FieldNode fn : cn.fields) {
                    if (fn.desc.equals("[L" + cn.name + ";")) {
                        fieldMap.put(cn.name + "." + fn.name + ":" + fn.desc, "VALUES");
                    }
                }
                for (MethodNode mn : cn.methods) {
                    if (mn.desc.equals("()[L" + cn.name + ";") && !mn.name.equals("values")) {
                        methodMap.put(cn.name + "." + mn.name + mn.desc, "$values");
                    } else if (mn.name.equals("<clinit>")) {
                        String firstConstStr = null;
                        for (AbstractInsnNode insn : mn.instructions) {
                            if (insn instanceof TypeInsnNode tin && tin.getOpcode() == Opcodes.NEW && tin.desc.equals(cn.name)) {
                                firstConstStr = null;
                            } else if (insn instanceof LdcInsnNode ldc && ldc.cst instanceof String s && firstConstStr == null) {
                                firstConstStr = s;
                            } else if (insn instanceof FieldInsnNode fin && fin.getOpcode() == Opcodes.PUTSTATIC
                                    && fin.owner.equals(cn.name) && fin.desc.equals("L" + cn.name + ";") && firstConstStr != null) {
                                fieldMap.put(cn.name + "." + fin.name + ":" + fin.desc, firstConstStr);
                                firstConstStr = null;
                            }
                        }
                    }
                }
            } else if ("java/lang/Record".equals(cn.superName)) {
                Map<String, String> fieldToCompName = new HashMap<>();
                for (MethodNode mn : cn.methods) {
                    for (AbstractInsnNode insn : mn.instructions) {
                        if (insn instanceof InvokeDynamicInsnNode idin
                                && idin.bsm.getOwner().equals("java/lang/runtime/ObjectMethods")) {
                            if (idin.name.equals("toString") && mn.desc.equals("()Ljava/lang/String;")) {
                                methodMap.put(cn.name + "." + mn.name + mn.desc, "toString");
                            } else if (idin.name.equals("hashCode") && mn.desc.equals("()I")) {
                                methodMap.put(cn.name + "." + mn.name + mn.desc, "hashCode");
                            } else if (idin.name.equals("equals") && mn.desc.equals("(Ljava/lang/Object;)Z")) {
                                methodMap.put(cn.name + "." + mn.name + mn.desc, "equals");
                            }
                            if (idin.bsmArgs.length >= 2 && idin.bsmArgs[1] instanceof String compStr && !compStr.isEmpty()) {
                                String[] comps = compStr.split(";");
                                for (int i = 0; i < comps.length && (2 + i) < idin.bsmArgs.length; i++) {
                                    if (idin.bsmArgs[2 + i] instanceof Handle h) {
                                        fieldMap.put(cn.name + "." + h.getName() + ":" + h.getDesc(), comps[i]);
                                        fieldToCompName.put(h.getName() + ":" + h.getDesc(), comps[i]);
                                    }
                                }
                            }
                        }
                    }
                }
                // Map record component getters
                for (MethodNode mn : cn.methods) {
                    if ((mn.access & Opcodes.ACC_STATIC) == 0 && mn.desc.startsWith("()") && !mn.desc.endsWith(")V")) {
                        for (AbstractInsnNode insn : mn.instructions) {
                            if (insn instanceof FieldInsnNode fin && fin.getOpcode() == Opcodes.GETFIELD && fin.owner.equals(cn.name)) {
                                String comp = fieldToCompName.get(fin.name + ":" + fin.desc);
                                if (comp != null && mn.instructions.size() <= 12) {
                                    methodMap.put(cn.name + "." + mn.name + mn.desc, comp);
                                }
                            }
                        }
                    }
                }
            }
        }

        // Topological sort of classes so superclasses & interfaces are mapped before subclasses
        List<ClassNode> sortedClasses = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        for (ClassNode cn : purifiedNodes.values()) {
            topoVisit(cn, purifiedNodes, visited, sortedClasses);
        }

        // 3. Map remaining obfuscated fields & methods across all classes in topological order
        for (ClassNode cn : sortedClasses) {
            Set<String> usedFieldNames = new HashSet<>();
            for (FieldNode fn : cn.fields) {
                String k = cn.name + "." + fn.name + ":" + fn.desc;
                if (fieldMap.containsKey(k)) {
                    usedFieldNames.add(fieldMap.get(k));
                } else if (!TestSemanticRemap.isObfMemberName(cn.name, fn.name)) {
                    usedFieldNames.add(fn.name);
                }
            }
            Map<String, Integer> baseCounters = new HashMap<>();
            for (FieldNode fn : cn.fields) {
                String k = cn.name + "." + fn.name + ":" + fn.desc;
                if (fieldMap.containsKey(k) || !TestSemanticRemap.isObfMemberName(cn.name, fn.name)) continue;
                String base = TestSemanticRemap.semanticFieldBaseName(cn.name, fn, cn);
                String candidate = base;
                int count = baseCounters.getOrDefault(base, 0);
                while (usedFieldNames.contains(candidate)) {
                    count++;
                    candidate = base + count;
                }
                baseCounters.put(base, count);
                usedFieldNames.add(candidate);
                fieldMap.put(k, candidate);
            }

            Set<String> usedMethodSigs = new HashSet<>();
            for (MethodNode mn : cn.methods) {
                String k = cn.name + "." + mn.name + mn.desc;
                if (methodMap.containsKey(k)) {
                    usedMethodSigs.add(methodMap.get(k) + mn.desc);
                } else if (!TestSemanticRemap.isObfMemberName(cn.name, mn.name)) {
                    usedMethodSigs.add(mn.name + mn.desc);
                }
            }
            Map<String, Integer> mCounters = new HashMap<>();
            for (MethodNode mn : cn.methods) {
                String k = cn.name + "." + mn.name + mn.desc;
                if (methodMap.containsKey(k) || !TestSemanticRemap.isObfMemberName(cn.name, mn.name)) continue;
                // Check if inherited from super or interface
                String inherited = null;
                String sup = cn.superName;
                while (sup != null && inherited == null) {
                    if (methodMap.containsKey(sup + "." + mn.name + mn.desc)) {
                        inherited = methodMap.get(sup + "." + mn.name + mn.desc);
                    }
                    sup = superMap.get(sup);
                }
                if (inherited == null && cn.interfaces != null) {
                    for (String itf : cn.interfaces) {
                        if (methodMap.containsKey(itf + "." + mn.name + mn.desc)) {
                            inherited = methodMap.get(itf + "." + mn.name + mn.desc);
                            break;
                        }
                    }
                }
                if (inherited != null) {
                    methodMap.put(k, inherited);
                    usedMethodSigs.add(inherited + mn.desc);
                    continue;
                }
                String base = TestSemanticRemap.semanticMethodBaseName(cn.name, mn, cn);
                String candidate = base;
                int count = mCounters.getOrDefault(base, 0);
                while (usedMethodSigs.contains(candidate + mn.desc)) {
                    count++;
                    candidate = base + count;
                }
                mCounters.put(base, count);
                usedMethodSigs.add(candidate + mn.desc);
                methodMap.put(k, candidate);
            }
        }

        return new ClarityRemapper(classMap, fieldMap, methodMap, superMap, interfacesMap);
    }

    private static void topoVisit(ClassNode cn, Map<String, ClassNode> all, Set<String> visited, List<ClassNode> out) {
        if (cn == null || !visited.add(cn.name)) return;
        if (cn.superName != null && all.containsKey(cn.superName)) {
            topoVisit(all.get(cn.superName), all, visited, out);
        }
        if (cn.interfaces != null) {
            for (String itf : cn.interfaces) {
                if (all.containsKey(itf)) topoVisit(all.get(itf), all, visited, out);
            }
        }
        out.add(cn);
    }

    public static void main(String[] args) throws Exception {
        try (ZipFile zf = new ZipFile(args[0])) {
            Map<String, ClassNode> purified = new LinkedHashMap<>();
            Enumeration<? extends ZipEntry> entries = zf.entries();
            while (entries.hasMoreElements()) {
                ZipEntry ze = entries.nextElement();
                if (!ze.getName().endsWith(".class") || ze.getName().startsWith("ryzwriqrxnniuxrp/")) continue;
                byte[] data = zf.getInputStream(ze).readAllBytes();
                if (((data[6] & 0xFF) << 8 | (data[7] & 0xFF)) > 61) {
                    data[6] = 0; data[7] = 61;
                }
                ClassReader cr = new ClassReader(data);
                ClassNode cn = new ClassNode();
                cr.accept(cn, ClassReader.SKIP_FRAMES);
                TestAll530.purifyClassComplete(cn);
                for (MethodNode mn : cn.methods) {
                    TestPeephole.optimizeLocals(mn);
                }
                purified.put(cn.name, cn);
            }

            ClarityRemapper remapper = buildRemapper(purified);
            System.out.println("Mapped classes: " + remapper.classMap.size() +
                ", mapped fields: " + remapper.fieldMap.size() +
                ", mapped methods: " + remapper.methodMap.size());

            int remappedCount = 0;
            for (ClassNode cn : purified.values()) {
                ClassNode remappedNode = new ClassNode();
                ClassRemapper cr = new ClassRemapper(remappedNode, remapper);
                cn.accept(cr);
                ClassWriter cw = new ClassWriter(0);
                remappedNode.accept(cw);
                byte[] bytes = cw.toByteArray();
                remappedCount++;
            }
            System.out.println("Successfully remapped and serialized all " + remappedCount + " classes!");
        }
    }
}



static class JavaSourceGenerator {
    private final Map<String, ClassNode> allClasses;
    private final Set<String> imports = new TreeSet<>();
    private String currentPkg = "";
    private ClassNode rootClass;

    public JavaSourceGenerator(Map<String, ClassNode> allClasses) {
        this.allClasses = allClasses;
    }

    public String generateCompilationUnit(ClassNode cn, List<ClassNode> innerNodes) {
        this.rootClass = cn;
        this.imports.clear();
        int lastSlash = cn.name.lastIndexOf('/');
        this.currentPkg = lastSlash > 0 ? cn.name.substring(0, lastSlash).replace('/', '.') : "";

        StringBuilder body = new StringBuilder();
        emitClassNode(cn, innerNodes, body, "");

        StringBuilder out = new StringBuilder();
        if (!currentPkg.isEmpty()) {
            out.append("package ").append(currentPkg).append(";\n\n");
        }
        String selfSimple = simpleClassName(cn.name);
        Set<String> importedSimples = new HashSet<>();
        importedSimples.add(selfSimple);
        for (ClassNode in : innerNodes) {
            importedSimples.add(simpleClassName(in.name));
        }
        for (String imp : imports) {
            if (imp.startsWith("java.lang.") && imp.indexOf('.', 10) == -1) continue;
            int dot = imp.lastIndexOf('.');
            String pkg = dot > 0 ? imp.substring(0, dot) : "";
            String simp = dot > 0 ? imp.substring(dot + 1) : imp;
            if (pkg.equals(currentPkg)) continue;
            if (importedSimples.add(simp)) {
                out.append("import ").append(imp).append(";\n");
            }
        }
        if (!imports.isEmpty()) out.append("\n");
        out.append(body);
        return out.toString();
    }

    private String formatType(Type t) {
        if (t == null) return "Object";
        switch (t.getSort()) {
            case Type.VOID: return "void";
            case Type.BOOLEAN: return "boolean";
            case Type.CHAR: return "char";
            case Type.BYTE: return "byte";
            case Type.SHORT: return "short";
            case Type.INT: return "int";
            case Type.FLOAT: return "float";
            case Type.LONG: return "long";
            case Type.DOUBLE: return "double";
            case Type.ARRAY: {
                String elem = formatType(t.getElementType());
                return elem + "[]".repeat(t.getDimensions());
            }
            case Type.OBJECT: {
                return formatInternalName(t.getInternalName());
            }
            default: return "Object";
        }
    }

    private String formatInternalName(String internal) {
        if (internal == null) return "Object";
        if (internal.startsWith("[")) return formatType(Type.getType(internal));
        int lastSlash = internal.lastIndexOf('/');
        String pkg = lastSlash > 0 ? internal.substring(0, lastSlash).replace('/', '.') : "";
        String afterPkg = lastSlash > 0 ? internal.substring(lastSlash + 1) : internal;
        int dollar = afterPkg.indexOf('$');
        String topClass = dollar > 0 ? afterPkg.substring(0, dollar) : afterPkg;
        if (!pkg.isEmpty() && !pkg.equals("java.lang")) {
            imports.add(pkg + "." + topClass);
        }
        return afterPkg.replace('$', '.');
    }

    private String simpleClassName(String internal) {
        int idx = Math.max(internal.lastIndexOf('/'), internal.lastIndexOf('$'));
        return idx >= 0 ? internal.substring(idx + 1) : internal;
    }

    private void emitAnnotations(List<AnnotationNode> vis, List<AnnotationNode> invis, StringBuilder sb, String indent) {
        List<AnnotationNode> all = new ArrayList<>();
        if (vis != null) all.addAll(vis);
        if (invis != null) all.addAll(invis);
        for (AnnotationNode an : all) {
            if (an.desc.equals("Lkotlin/Metadata;")) continue;
            String annType = formatType(Type.getType(an.desc));
            sb.append(indent).append("@").append(annType);
            if (an.values != null && !an.values.isEmpty()) {
                sb.append("(");
                for (int i = 0; i < an.values.size(); i += 2) {
                    if (i > 0) sb.append(", ");
                    String key = (String) an.values.get(i);
                    Object val = an.values.get(i + 1);
                    if (!(an.values.size() == 2 && "value".equals(key))) {
                        sb.append(key).append(" = ");
                    }
                    sb.append(formatAnnotationValue(val));
                }
                sb.append(")");
            }
            sb.append("\n");
        }
    }

    private String formatAnnotationValue(Object val) {
        if (val instanceof Type t) return formatType(t) + ".class";
        if (val instanceof String s) return escapeStringLiteral(s);
        if (val instanceof String[] enumVal) {
            return formatType(Type.getType(enumVal[0])) + "." + enumVal[1];
        }
        if (val instanceof AnnotationNode an) {
            StringBuilder sb = new StringBuilder();
            String annType = formatType(Type.getType(an.desc));
            sb.append("@").append(annType);
            if (an.values != null && !an.values.isEmpty()) {
                sb.append("(");
                for (int i = 0; i < an.values.size(); i += 2) {
                    if (i > 0) sb.append(", ");
                    String k = (String) an.values.get(i);
                    if (!(an.values.size() == 2 && "value".equals(k))) sb.append(k).append(" = ");
                    sb.append(formatAnnotationValue(an.values.get(i + 1)));
                }
                sb.append(")");
            }
            return sb.toString();
        }
        if (val instanceof List<?> list) {
            if (list.size() == 1) return formatAnnotationValue(list.get(0));
            List<String> parts = new ArrayList<>();
            for (Object o : list) parts.add(formatAnnotationValue(o));
            return "{" + String.join(", ", parts) + "}";
        }
        return String.valueOf(val);
    }

    public static String escapeStringLiteral(String s) {
        if (s == null) return "null";
        StringBuilder sb = new StringBuilder("\"");
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\\' -> sb.append("\\\\");
                case '"' -> sb.append("\\\"");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default -> {
                    if (c < 32 || c > 126) sb.append(String.format("\\u%04x", (int) c));
                    else sb.append(c);
                }
            }
        }
        sb.append("\"");
        return sb.toString();
    }

    private void emitClassNode(ClassNode cn, List<ClassNode> innerNodes, StringBuilder sb, String indent) {
        emitAnnotations(cn.visibleAnnotations, cn.invisibleAnnotations, sb, indent);
        boolean isInterface = (cn.access & Opcodes.ACC_INTERFACE) != 0;
        boolean isEnum = "java/lang/Enum".equals(cn.superName);
        boolean isRecord = "java/lang/Record".equals(cn.superName);
        boolean isInner = cn != rootClass;

        sb.append(indent).append("public ");
        if (isInner && !isInterface && !isEnum && !isRecord && (cn.access & Opcodes.ACC_STATIC) != 0) {
            sb.append("static ");
        }
        if (!isInterface && !isEnum && !isRecord) {
            if ((cn.access & Opcodes.ACC_ABSTRACT) != 0) sb.append("abstract ");
            else if ((cn.access & Opcodes.ACC_FINAL) != 0) sb.append("final ");
        }

        String simple = simpleClassName(cn.name);
        if (isRecord) {
            sb.append("record ").append(simple).append("(");
            List<FieldNode> instFields = new ArrayList<>();
            for (FieldNode fn : cn.fields) {
                if ((fn.access & Opcodes.ACC_STATIC) == 0) instFields.add(fn);
            }
            for (int i = 0; i < instFields.size(); i++) {
                if (i > 0) sb.append(", ");
                FieldNode fn = instFields.get(i);
                sb.append(formatType(Type.getType(fn.desc))).append(" ").append(fn.name);
            }
            sb.append(")");
        } else if (isEnum) {
            sb.append("enum ").append(simple);
        } else if (isInterface) {
            sb.append("interface ").append(simple);
        } else {
            sb.append("class ").append(simple);
        }

        if (!isEnum && !isRecord && !isInterface && cn.superName != null && !"java/lang/Object".equals(cn.superName)) {
            sb.append(" extends ").append(formatInternalName(cn.superName));
        }
        if (cn.interfaces != null && !cn.interfaces.isEmpty()) {
            sb.append(isInterface ? " extends " : " implements ");
            for (int i = 0; i < cn.interfaces.size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(formatInternalName(cn.interfaces.get(i)));
            }
        }
        sb.append(" {\n");
        String bodyIndent = indent + "    ";

        Map<String, MethodNode> lambdaMethods = new HashMap<>();
        for (MethodNode mn : cn.methods) {
            if ((mn.access & Opcodes.ACC_SYNTHETIC) != 0 || mn.name.startsWith("lambda$")) {
                lambdaMethods.put(mn.name + mn.desc, mn);
            }
        }

        if (isEnum) {
            emitEnumConstants(cn, lambdaMethods, sb, bodyIndent);
        }

        Set<String> recordCompNames = new HashSet<>();
        if (isRecord) {
            for (FieldNode fn : cn.fields) {
                if ((fn.access & Opcodes.ACC_STATIC) == 0) recordCompNames.add(fn.name);
            }
        }
        for (FieldNode fn : cn.fields) {
            if ((fn.access & Opcodes.ACC_SYNTHETIC) != 0) continue;
            if (isEnum && ((fn.access & Opcodes.ACC_ENUM) != 0 || fn.name.equals("VALUES"))) continue;
            if (isRecord && (fn.access & Opcodes.ACC_STATIC) == 0) continue;
            emitAnnotations(fn.visibleAnnotations, fn.invisibleAnnotations, sb, bodyIndent);
            sb.append(bodyIndent);
            if ((fn.access & Opcodes.ACC_PUBLIC) != 0) sb.append("public ");
            else if ((fn.access & Opcodes.ACC_PROTECTED) != 0) sb.append("protected ");
            else if ((fn.access & Opcodes.ACC_PRIVATE) != 0) sb.append("private ");
            if ((fn.access & Opcodes.ACC_STATIC) != 0) sb.append("static ");
            if ((fn.access & Opcodes.ACC_FINAL) != 0) sb.append("final ");
            if ((fn.access & Opcodes.ACC_VOLATILE) != 0) sb.append("volatile ");
            if ((fn.access & Opcodes.ACC_TRANSIENT) != 0) sb.append("transient ");
            sb.append(formatType(Type.getType(fn.desc))).append(" ").append(fn.name);
            if (fn.value != null) {
                sb.append(" = ").append(formatConstant(fn.value, Type.getType(fn.desc)));
            }
            sb.append(";\n");
        }
        if (!cn.fields.isEmpty()) sb.append("\n");

        for (MethodNode mn : cn.methods) {
            if ((mn.access & Opcodes.ACC_BRIDGE) != 0 || (mn.access & Opcodes.ACC_SYNTHETIC) != 0) continue;
            if (mn.name.startsWith("lambda$") || mn.name.equals("$values")) continue;
            if (isEnum && ((mn.name.equals("values") && mn.desc.startsWith("()"))
                    || (mn.name.equals("valueOf") && mn.desc.startsWith("(Ljava/lang/String;)")))) continue;
            if (isRecord) {
                if (mn.name.equals("toString") && mn.desc.equals("()Ljava/lang/String;")) continue;
                if (mn.name.equals("hashCode") && mn.desc.equals("()I")) continue;
                if (mn.name.equals("equals") && mn.desc.equals("(Ljava/lang/Object;)Z")) continue;
                if (recordCompNames.contains(mn.name) && mn.desc.startsWith("()")) continue;
                if (mn.name.equals("<init>") && isCanonicalRecordConstructor(cn, mn)) continue;
            }
            if (mn.name.equals("<clinit>")) {
                if (!isEnum && hasNonEmptyClinit(mn)) {
                    sb.append(bodyIndent).append("static {\n");
                    emitMethodBody(cn, mn, lambdaMethods, sb, bodyIndent + "    ", false, false);
                    sb.append(bodyIndent).append("}\n\n");
                }
                continue;
            }
            emitMethod(cn, mn, lambdaMethods, sb, bodyIndent, isInterface, isEnum);
        }

        for (ClassNode in : innerNodes) {
            emitClassNode(in, Collections.emptyList(), sb, bodyIndent);
            sb.append("\n");
        }

        sb.append(indent).append("}\n");
    }

    private boolean isCanonicalRecordConstructor(ClassNode cn, MethodNode mn) {
        StringBuilder expected = new StringBuilder("(");
        for (FieldNode fn : cn.fields) {
            if ((fn.access & Opcodes.ACC_STATIC) == 0) expected.append(fn.desc);
        }
        expected.append(")V");
        return mn.desc.equals(expected.toString());
    }

    private boolean hasNonEmptyClinit(MethodNode mn) {
        for (AbstractInsnNode insn : mn.instructions) {
            if (insn instanceof LabelNode || insn instanceof LineNumberNode || insn instanceof FrameNode) continue;
            if (insn.getOpcode() == Opcodes.RETURN) continue;
            return true;
        }
        return false;
    }

    private void emitEnumConstants(ClassNode cn, Map<String, MethodNode> lambdaMethods, StringBuilder sb, String indent) {
        MethodNode clinit = null;
        for (MethodNode mn : cn.methods) {
            if (mn.name.equals("<clinit>")) { clinit = mn; break; }
        }
        if (clinit == null) {
            sb.append(indent).append(";\n\n");
            return;
        }
        List<String> constEntries = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        Map<Integer, String> clinitLocals = new HashMap<>();
        List<String> lastEnumCtorArgs = Collections.emptyList();
        for (AbstractInsnNode insn : clinit.instructions) {
            int op = insn.getOpcode();
            if (insn instanceof TypeInsnNode tin && op == Opcodes.NEW) {
                stack.push("new " + formatInternalName(tin.desc));
            } else if (op == Opcodes.DUP) {
                if (!stack.isEmpty()) stack.push(stack.peek());
            } else if (insn instanceof LdcInsnNode ldc) {
                if (ldc.cst instanceof String s) stack.push(escapeStringLiteral(s));
                else stack.push(formatConstant(ldc.cst, null));
            } else if (TestFullSim.getIntConst(insn) != null) {
                stack.push(String.valueOf(TestFullSim.getIntConst(insn)));
            } else if (op == Opcodes.DCONST_0) stack.push("0.0");
            else if (op == Opcodes.DCONST_1) stack.push("1.0");
            else if (op >= Opcodes.FCONST_0 && op <= Opcodes.FCONST_2) stack.push((op - Opcodes.FCONST_0) + ".0F");
            else if (insn instanceof VarInsnNode vin) {
                if (op >= Opcodes.ILOAD && op <= Opcodes.ALOAD) {
                    stack.push(clinitLocals.getOrDefault(vin.var, "null"));
                } else if (op >= Opcodes.ISTORE && op <= Opcodes.ASTORE) {
                    if (!stack.isEmpty()) clinitLocals.put(vin.var, stack.pop());
                }
            } else if (insn instanceof FieldInsnNode fin && op == Opcodes.GETSTATIC) {
                String ownerStr = fin.owner.equals(cn.name) ? "" : formatInternalName(fin.owner) + ".";
                stack.push(ownerStr + fin.name);
            } else if (insn instanceof InvokeDynamicInsnNode idin) {
                Type[] dArgs = Type.getArgumentTypes(idin.desc);
                List<String> args = new ArrayList<>();
                for (int i = 0; i < dArgs.length; i++) args.add(0, stack.isEmpty() ? "null" : stack.pop());
                stack.push(formatInvokeDynamic(cn, idin, args, lambdaMethods));
            } else if (insn instanceof MethodInsnNode min) {
                Type[] mArgs = Type.getArgumentTypes(min.desc);
                List<String> args = new ArrayList<>();
                for (int i = 0; i < mArgs.length; i++) {
                    String a = stack.isEmpty() ? "null" : stack.pop();
                    if (mArgs[mArgs.length - 1 - i].getSort() == Type.BOOLEAN && ("0".equals(a) || "1".equals(a))) {
                        a = "1".equals(a) ? "true" : "false";
                    }
                    args.add(0, a);
                }
                if (min.name.equals("<init>")) {
                    String recv = stack.isEmpty() ? "" : stack.pop();
                    if (min.owner.equals(cn.name)) {
                        lastEnumCtorArgs = args.size() > 2 ? new ArrayList<>(args.subList(2, args.size())) : Collections.emptyList();
                        if (!stack.isEmpty() && stack.peek().equals(recv)) stack.pop();
                        stack.push("ENUM_INST");
                    } else {
                        String newExpr = "new " + formatInternalName(min.owner) + "(" + String.join(", ", args) + ")";
                        if (!stack.isEmpty() && stack.peek().equals(recv)) stack.pop();
                        stack.push(newExpr);
                    }
                } else {
                    String recv = (op == Opcodes.INVOKESTATIC)
                            ? (min.owner.equals(cn.name) ? "" : formatInternalName(min.owner) + ".")
                            : (stack.isEmpty() ? "this" : stack.pop()) + ".";
                    if (Type.getReturnType(min.desc).getSort() != Type.VOID) {
                        stack.push(recv + min.name + "(" + String.join(", ", args) + ")");
                    }
                }
            } else if (insn instanceof FieldInsnNode fin && op == Opcodes.PUTSTATIC && fin.owner.equals(cn.name)) {
                if (!stack.isEmpty()) stack.pop();
                if (fin.desc.equals("L" + cn.name + ";")) {
                    if (!lastEnumCtorArgs.isEmpty()) {
                        constEntries.add(fin.name + "(" + String.join(", ", lastEnumCtorArgs) + ")");
                    } else {
                        constEntries.add(fin.name);
                    }
                    lastEnumCtorArgs = Collections.emptyList();
                }
            }
        }
        if (!constEntries.isEmpty()) {
            sb.append(indent).append(String.join(",\n" + indent, constEntries)).append(";\n\n");
        } else {
            sb.append(indent).append(";\n\n");
        }
    }

    private void emitMethod(ClassNode cn, MethodNode mn, Map<String, MethodNode> lambdaMethods,
            StringBuilder sb, String indent, boolean isInterface, boolean isEnum) {
        emitAnnotations(mn.visibleAnnotations, mn.invisibleAnnotations, sb, indent);
        sb.append(indent);
        boolean isCtor = mn.name.equals("<init>");
        if (!isInterface) {
            if (isEnum && isCtor) {
                // enum constructors are implicitly private
            } else if ((mn.access & Opcodes.ACC_PUBLIC) != 0) sb.append("public ");
            else if ((mn.access & Opcodes.ACC_PROTECTED) != 0) sb.append("protected ");
            else if ((mn.access & Opcodes.ACC_PRIVATE) != 0) sb.append("private ");
        } else if ((mn.access & Opcodes.ACC_STATIC) == 0 && (mn.access & Opcodes.ACC_ABSTRACT) == 0) {
            sb.append("default ");
        }
        if ((mn.access & Opcodes.ACC_STATIC) != 0) sb.append("static ");
        if ((mn.access & Opcodes.ACC_FINAL) != 0 && !isCtor) sb.append("final ");
        if ((mn.access & Opcodes.ACC_SYNCHRONIZED) != 0) sb.append("synchronized ");
        if ((mn.access & Opcodes.ACC_ABSTRACT) != 0 && !isInterface) sb.append("abstract ");

        Type[] argTypes = Type.getArgumentTypes(mn.desc);
        Type retType = Type.getReturnType(mn.desc);
        if (isCtor) {
            sb.append(simpleClassName(cn.name)).append("(");
        } else {
            sb.append(formatType(retType)).append(" ").append(mn.name).append("(");
        }

        int startArg = (isEnum && isCtor && argTypes.length >= 2) ? 2 : 0;
        int localSlot = (mn.access & Opcodes.ACC_STATIC) != 0 ? 0 : 1;
        for (int i = 0; i < startArg; i++) localSlot += argTypes[i].getSize();
        for (int i = startArg; i < argTypes.length; i++) {
            if (i > startArg) sb.append(", ");
            String pName = getLocalVarName(mn, localSlot, argTypes[i], "arg" + (i - startArg));
            sb.append(formatType(argTypes[i])).append(" ").append(pName);
            localSlot += argTypes[i].getSize();
        }
        sb.append(")");

        if (mn.exceptions != null && !mn.exceptions.isEmpty()) {
            sb.append(" throws ");
            for (int i = 0; i < mn.exceptions.size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(formatInternalName(mn.exceptions.get(i)));
            }
        }

        if ((mn.access & Opcodes.ACC_ABSTRACT) != 0 || (mn.access & Opcodes.ACC_NATIVE) != 0) {
            sb.append(";\n\n");
            return;
        }

        sb.append(" {\n");
        emitMethodBody(cn, mn, lambdaMethods, sb, indent + "    ", isCtor, isEnum);
        sb.append(indent).append("}\n\n");
    }

    private String getLocalVarName(MethodNode mn, int slot, Type t, String fallback) {
        if (mn.localVariables != null) {
            for (LocalVariableNode lvn : mn.localVariables) {
                if (lvn.index == slot && !lvn.name.equals("this") && lvn.name.length() > 1) {
                    return lvn.name;
                }
            }
        }
        return fallback;
    }

    private String formatConstant(Object cst, Type expectedType) {
        if (cst == null) return "null";
        if (cst instanceof String s) return escapeStringLiteral(s);
        if (cst instanceof Type t) return formatType(t) + ".class";
        if (cst instanceof Integer i) {
            if (expectedType != null && expectedType.getSort() == Type.BOOLEAN) return i != 0 ? "true" : "false";
            if (expectedType != null && expectedType.getSort() == Type.CHAR) return "'" + escapeChar((char) i.intValue()) + "'";
            return Integer.toString(i);
        }
        if (cst instanceof Long l) return l + "L";
        if (cst instanceof Float f) {
            if (Float.isNaN(f)) return "Float.NaN";
            if (Float.isInfinite(f)) return f > 0 ? "Float.POSITIVE_INFINITY" : "Float.NEGATIVE_INFINITY";
            return f + "F";
        }
        if (cst instanceof Double d) {
            if (Double.isNaN(d)) return "Double.NaN";
            if (Double.isInfinite(d)) return d > 0 ? "Double.POSITIVE_INFINITY" : "Double.NEGATIVE_INFINITY";
            return Double.toString(d);
        }
        return String.valueOf(cst);
    }

    private String escapeChar(char c) {
        return switch (c) {
            case '\\' -> "\\\\";
            case '\'' -> "\\'";
            case '\n' -> "\\n";
            case '\r' -> "\\r";
            case '\t' -> "\\t";
            default -> (c < 32 || c > 126) ? String.format("\\u%04x", (int) c) : String.valueOf(c);
        };
    }

    private String formatInvokeDynamic(ClassNode cn, InvokeDynamicInsnNode idin, List<String> stackArgs, Map<String, MethodNode> lambdaMethods) {
        if (idin.bsm.getOwner().equals("java/lang/invoke/StringConcatFactory")) {
            String recipe = (String) idin.bsmArgs[0];
            int argIdx = 0;
            int constIdx = 1;
            List<String> parts = new ArrayList<>();
            StringBuilder lit = new StringBuilder();
            for (int i = 0; i < recipe.length(); i++) {
                char c = recipe.charAt(i);
                if (c == '\u0001') {
                    if (lit.length() > 0) {
                        parts.add(escapeStringLiteral(lit.toString()));
                        lit.setLength(0);
                    }
                    parts.add(argIdx < stackArgs.size() ? "(" + stackArgs.get(argIdx++) + ")" : "\"\"");
                } else if (c == '\u0002') {
                    if (lit.length() > 0) {
                        parts.add(escapeStringLiteral(lit.toString()));
                        lit.setLength(0);
                    }
                    parts.add(escapeStringLiteral(String.valueOf(idin.bsmArgs[constIdx++])));
                } else {
                    lit.append(c);
                }
            }
            if (lit.length() > 0) parts.add(escapeStringLiteral(lit.toString()));
            return parts.isEmpty() ? "\"\"" : String.join(" + ", parts);
        }
        if (idin.bsm.getOwner().equals("java/lang/invoke/LambdaMetafactory") && idin.bsmArgs.length >= 3) {
            Handle impl = (Handle) idin.bsmArgs[1];
            Type samType = (Type) idin.bsmArgs[2];
            Type[] samArgs = samType.getArgumentTypes();
            if (impl.getTag() == Opcodes.H_NEWINVOKESPECIAL) {
                return formatInternalName(impl.getOwner()) + "::new";
            }
            if (!impl.getOwner().equals(cn.name) && stackArgs.isEmpty()) {
                return formatInternalName(impl.getOwner()) + "::" + impl.getName();
            }
            List<String> pNames = new ArrayList<>();
            for (int i = 0; i < samArgs.length; i++) pNames.add("p" + i);
            List<String> callArgs = new ArrayList<>(stackArgs);
            boolean isInstCall = (impl.getTag() == Opcodes.H_INVOKEVIRTUAL || impl.getTag() == Opcodes.H_INVOKEINTERFACE || impl.getTag() == Opcodes.H_INVOKESPECIAL);
            String targetReceiver = null;
            if (isInstCall) {
                if (!callArgs.isEmpty()) {
                    targetReceiver = callArgs.remove(0);
                } else if (!pNames.isEmpty()) {
                    targetReceiver = pNames.get(0);
                }
            }
            for (int i = (isInstCall && stackArgs.isEmpty() ? 1 : 0); i < pNames.size(); i++) {
                callArgs.add(pNames.get(i));
            }
            String receiverPrefix = isInstCall ? (targetReceiver != null ? targetReceiver : "this") : formatInternalName(impl.getOwner());
            return "(" + String.join(", ", pNames) + ") -> " + receiverPrefix + "." + impl.getName() + "(" + String.join(", ", callArgs) + ")";
        }
        return "null";
    }

    private void inferLocalTypes(MethodNode mn, Map<Integer, Type> localTypes, Map<Integer, Integer> storeCounts) {
        for (AbstractInsnNode insn = mn.instructions.getFirst(); insn != null; insn = insn.getNext()) {
            if (insn instanceof VarInsnNode vin && vin.getOpcode() >= Opcodes.ISTORE && vin.getOpcode() <= Opcodes.ASTORE) {
                storeCounts.merge(vin.var, 1, Integer::sum);
                if (localTypes.containsKey(vin.var)) continue;
                int op = vin.getOpcode();
                if (op == Opcodes.LSTORE) { localTypes.put(vin.var, Type.LONG_TYPE); continue; }
                if (op == Opcodes.FSTORE) { localTypes.put(vin.var, Type.FLOAT_TYPE); continue; }
                if (op == Opcodes.DSTORE) { localTypes.put(vin.var, Type.DOUBLE_TYPE); continue; }
                AbstractInsnNode prev = vin.getPrevious();
                while (prev != null && (prev instanceof LabelNode || prev instanceof LineNumberNode || prev instanceof FrameNode)) {
                    prev = prev.getPrevious();
                }
                if (op == Opcodes.ISTORE) {
                    if (prev instanceof MethodInsnNode min && Type.getReturnType(min.desc).getSort() == Type.BOOLEAN) {
                        localTypes.put(vin.var, Type.BOOLEAN_TYPE);
                    } else if (prev instanceof FieldInsnNode fin && Type.getType(fin.desc).getSort() == Type.BOOLEAN) {
                        localTypes.put(vin.var, Type.BOOLEAN_TYPE);
                    } else {
                        localTypes.put(vin.var, Type.INT_TYPE);
                    }
                    continue;
                }
                if (prev instanceof MethodInsnNode min) {
                    Type rt = Type.getReturnType(min.desc);
                    if (rt.getSort() == Type.OBJECT || rt.getSort() == Type.ARRAY) {
                        localTypes.put(vin.var, rt);
                    } else if (min.name.equals("<init>")) {
                        localTypes.put(vin.var, Type.getObjectType(min.owner));
                    }
                } else if (prev instanceof FieldInsnNode fin) {
                    localTypes.put(vin.var, Type.getType(fin.desc));
                } else if (prev instanceof TypeInsnNode tin && (tin.getOpcode() == Opcodes.CHECKCAST || tin.getOpcode() == Opcodes.NEW)) {
                    localTypes.put(vin.var, Type.getObjectType(tin.desc));
                } else if (prev instanceof LdcInsnNode ldc && ldc.cst instanceof String) {
                    localTypes.put(vin.var, Type.getObjectType("java/lang/String"));
                } else if (prev instanceof VarInsnNode pvin && pvin.getOpcode() == Opcodes.ALOAD && localTypes.containsKey(pvin.var)) {
                    localTypes.put(vin.var, localTypes.get(pvin.var));
                } else if (prev != null && prev.getOpcode() == Opcodes.AALOAD) {
                    AbstractInsnNode p2 = prev.getPrevious();
                    while (p2 != null && !(p2 instanceof VarInsnNode)) p2 = p2.getPrevious();
                    if (p2 instanceof VarInsnNode avin && localTypes.containsKey(avin.var)) {
                        Type at = localTypes.get(avin.var);
                        if (at.getSort() == Type.ARRAY) {
                            localTypes.put(vin.var, Type.getType(at.getDescriptor().substring(1)));
                        }
                    }
                }
            }
        }
    }

    private static class OpenScope {
        final LabelNode closeLabel;
        final LabelNode elseEndLabel;
        final boolean isLoop;
        final TryCatchBlockNode tryBlock;

        OpenScope(LabelNode closeLabel, LabelNode elseEndLabel, boolean isLoop, TryCatchBlockNode tryBlock) {
            this.closeLabel = closeLabel;
            this.elseEndLabel = elseEndLabel;
            this.isLoop = isLoop;
            this.tryBlock = tryBlock;
        }
    }

    private void emitMethodBody(ClassNode cn, MethodNode mn, Map<String, MethodNode> lambdaMethods,
            StringBuilder sb, String baseIndent, boolean isCtor, boolean isEnum) {
        Deque<String> stack = new ArrayDeque<>();
        Deque<Type> typeStack = new ArrayDeque<>();
        Set<Integer> declaredLocals = new HashSet<>();
        Map<Integer, String> localNames = new HashMap<>();
        Map<Integer, Type> localTypes = new HashMap<>();
        Map<Integer, Integer> storeCounts = new HashMap<>();
        Map<String, Integer> uninitNewVarToSlot = new HashMap<>();

        int slot = 0;
        if ((mn.access & Opcodes.ACC_STATIC) == 0) {
            localNames.put(0, "this");
            localTypes.put(0, Type.getObjectType(cn.name));
            declaredLocals.add(0);
            slot = 1;
        }
        Type[] argTypes = Type.getArgumentTypes(mn.desc);
        int startArg = (isEnum && isCtor && argTypes.length >= 2) ? 2 : 0;
        for (int i = 0; i < argTypes.length; i++) {
            String pName = (i < startArg) ? "enum$" + i : getLocalVarName(mn, slot, argTypes[i], "arg" + (i - startArg));
            localNames.put(slot, pName);
            localTypes.put(slot, argTypes[i]);
            declaredLocals.add(slot);
            slot += argTypes[i].getSize();
        }
        inferLocalTypes(mn, localTypes, storeCounts);

        // Pre-declare multi-store locals at the start of non-constructor methods
        if (!isCtor) {
            for (var entry : storeCounts.entrySet()) {
                int v = entry.getKey();
                if (entry.getValue() > 1 && !declaredLocals.contains(v)) {
                    Type vt = localTypes.getOrDefault(v, Type.INT_TYPE);
                    String vName = localNames.computeIfAbsent(v, k -> getLocalVarName(mn, k, vt, "var" + k));
                    String defVal = switch (vt.getSort()) {
                        case Type.BOOLEAN -> "false";
                        case Type.LONG -> "0L";
                        case Type.FLOAT -> "0.0F";
                        case Type.DOUBLE -> "0.0";
                        case Type.OBJECT, Type.ARRAY -> "null";
                        default -> "0";
                    };
                    sb.append(baseIndent).append(formatType(vt)).append(" ").append(vName).append(" = ").append(defVal).append(";\n");
                    declaredLocals.add(v);
                }
            }
        }

        // First scan for backward branches and ensure a LabelNode exists immediately after every backward branch
        Map<LabelNode, Integer> initialOrder = new HashMap<>();
        int initIdx = 0;
        for (AbstractInsnNode insn : mn.instructions) {
            if (insn instanceof LabelNode ln) initialOrder.put(ln, initIdx++);
        }
        Map<LabelNode, LabelNode> loopHeaderToEnd = new HashMap<>();
        int curOrdScan = -1;
        for (AbstractInsnNode insn : mn.instructions.toArray()) {
            if (insn instanceof LabelNode ln) {
                curOrdScan = initialOrder.getOrDefault(ln, curOrdScan);
            } else if (insn instanceof JumpInsnNode jin) {
                int targetOrd = initialOrder.getOrDefault(jin.label, Integer.MAX_VALUE);
                if (targetOrd <= curOrdScan) {
                    AbstractInsnNode after = jin.getNext();
                    if (!(after instanceof LabelNode)) {
                        LabelNode endLn = new LabelNode();
                        mn.instructions.insert(jin, endLn);
                        loopHeaderToEnd.put(jin.label, endLn);
                    } else {
                        loopHeaderToEnd.put(jin.label, (LabelNode) after);
                    }
                }
            }
        }

        Map<LabelNode, Integer> labelOrder = new HashMap<>();
        int orderIdx = 0;
        for (AbstractInsnNode insn : mn.instructions) {
            if (insn instanceof LabelNode ln) labelOrder.put(ln, orderIdx++);
        }

        Map<LabelNode, List<TryCatchBlockNode>> tryStarts = new HashMap<>();
        Map<LabelNode, List<TryCatchBlockNode>> catchHandlers = new HashMap<>();
        Set<LabelNode> deferredCatchHandlers = new HashSet<>();
        for (TryCatchBlockNode tcb : mn.tryCatchBlocks) {
            if (tcb.type != null) {
                tryStarts.computeIfAbsent(tcb.start, k -> new ArrayList<>()).add(tcb);
                if (tcb.handler == tcb.end) {
                    catchHandlers.computeIfAbsent(tcb.handler, k -> new ArrayList<>()).add(tcb);
                } else {
                    deferredCatchHandlers.add(tcb.handler);
                }
            }
        }

        Deque<OpenScope> openScopes = new ArrayDeque<>();

        for (AbstractInsnNode insn = mn.instructions.getFirst(); insn != null; insn = insn.getNext()) {
            if (insn instanceof LineNumberNode || insn instanceof FrameNode) continue;
            if (insn instanceof LabelNode ln) {
                if (deferredCatchHandlers.contains(ln)) {
                    // Skip out-of-line empty/simple catch handler placed at bottom of method
                    AbstractInsnNode cur = ln.getNext();
                    while (cur != null && !(cur instanceof LabelNode)) {
                        int cop = cur.getOpcode();
                        if ((cop >= Opcodes.IRETURN && cop <= Opcodes.RETURN) || cop == Opcodes.ATHROW || cop == Opcodes.GOTO) {
                            insn = cur;
                            break;
                        }
                        cur = cur.getNext();
                    }
                    continue;
                }
                while (containsCloseLabel(openScopes, ln)) {
                    OpenScope sc = openScopes.pop();
                    String curInd = baseIndent + "    ".repeat(openScopes.size());
                    if (sc.tryBlock != null && sc.tryBlock.handler != sc.tryBlock.end) {
                        String exType = formatInternalName(sc.tryBlock.type);
                        sb.append(curInd).append("} catch (").append(exType).append(" ignored) {\n");
                        sb.append(curInd).append("}\n");
                        if (sc.closeLabel == ln) break;
                    } else if (sc.closeLabel == ln && sc.elseEndLabel != null && sc.elseEndLabel != ln) {
                        sb.append(curInd).append("} else {\n");
                        openScopes.push(new OpenScope(sc.elseEndLabel, null, false, null));
                        break;
                    } else {
                        sb.append(curInd).append("}\n");
                        if (sc.closeLabel == ln) break;
                    }
                }
                if (loopHeaderToEnd.containsKey(ln)) {
                    String curInd = baseIndent + "    ".repeat(openScopes.size());
                    sb.append(curInd).append("while (true) {\n");
                    openScopes.push(new OpenScope(loopHeaderToEnd.get(ln), null, true, null));
                }
                if (tryStarts.containsKey(ln)) {
                    for (TryCatchBlockNode tcb : tryStarts.get(ln)) {
                        String curInd = baseIndent + "    ".repeat(openScopes.size());
                        sb.append(curInd).append("try {\n");
                        openScopes.push(new OpenScope(tcb.end, null, false, tcb));
                    }
                }
                if (catchHandlers.containsKey(ln)) {
                    TryCatchBlockNode tcb = catchHandlers.get(ln).get(0);
                    String exType = formatInternalName(tcb.type);
                    stack.clear(); typeStack.clear();
                    stack.push("caughtEx");
                    typeStack.push(Type.getObjectType(tcb.type));
                    String curInd = baseIndent + "    ".repeat(openScopes.size());
                    LabelNode catchEnd = findNextMergeOrEndLabel(ln);
                    sb.append(curInd).append("catch (").append(exType).append(" caughtEx) {\n");
                    openScopes.push(new OpenScope(catchEnd, null, false, null));
                }
                continue;
            }

            String indent = baseIndent + "    ".repeat(openScopes.size());
            int op = insn.getOpcode();

            if (op == Opcodes.ACONST_NULL) {
                stack.push("null"); typeStack.push(Type.VOID_TYPE);
            } else if (op >= Opcodes.ICONST_M1 && op <= Opcodes.ICONST_5) {
                stack.push(Integer.toString(op - Opcodes.ICONST_0)); typeStack.push(Type.INT_TYPE);
            } else if (op == Opcodes.LCONST_0 || op == Opcodes.LCONST_1) {
                stack.push((op - Opcodes.LCONST_0) + "L"); typeStack.push(Type.LONG_TYPE);
            } else if (op >= Opcodes.FCONST_0 && op <= Opcodes.FCONST_2) {
                stack.push((op - Opcodes.FCONST_0) + ".0F"); typeStack.push(Type.FLOAT_TYPE);
            } else if (op == Opcodes.DCONST_0 || op == Opcodes.DCONST_1) {
                stack.push((op - Opcodes.DCONST_0) + ".0"); typeStack.push(Type.DOUBLE_TYPE);
            } else if (insn instanceof IntInsnNode iin && (op == Opcodes.BIPUSH || op == Opcodes.SIPUSH)) {
                stack.push(Integer.toString(iin.operand)); typeStack.push(Type.INT_TYPE);
            } else if (insn instanceof LdcInsnNode ldc) {
                Type t = Type.getObjectType("java/lang/Object");
                if (ldc.cst instanceof Integer) t = Type.INT_TYPE;
                else if (ldc.cst instanceof Long) t = Type.LONG_TYPE;
                else if (ldc.cst instanceof Float) t = Type.FLOAT_TYPE;
                else if (ldc.cst instanceof Double) t = Type.DOUBLE_TYPE;
                else if (ldc.cst instanceof String) t = Type.getObjectType("java/lang/String");
                stack.push(formatConstant(ldc.cst, t)); typeStack.push(t);
            } else if (insn instanceof VarInsnNode vin) {
                if (op >= Opcodes.ILOAD && op <= Opcodes.ALOAD) {
                    String vName = localNames.getOrDefault(vin.var, "var" + vin.var);
                    Type vt = localTypes.getOrDefault(vin.var, Type.INT_TYPE);
                    stack.push(vName); typeStack.push(vt);
                } else if (op >= Opcodes.ISTORE && op <= Opcodes.ASTORE) {
                    if (stack.isEmpty()) continue;
                    String val = stack.pop();
                    Type vt = typeStack.isEmpty() ? Type.INT_TYPE : typeStack.pop();
                    if (localTypes.containsKey(vin.var)) {
                        vt = localTypes.get(vin.var);
                    } else if (op == Opcodes.ISTORE && (vt == Type.VOID_TYPE || vt == null)) {
                        vt = Type.INT_TYPE;
                    } else if (op == Opcodes.LSTORE) vt = Type.LONG_TYPE;
                    else if (op == Opcodes.FSTORE) vt = Type.FLOAT_TYPE;
                    else if (op == Opcodes.DSTORE) vt = Type.DOUBLE_TYPE;
                    else if (op == Opcodes.ASTORE && vt.getSort() != Type.OBJECT && vt.getSort() != Type.ARRAY) {
                        vt = Type.getObjectType("java/lang/Object");
                    }
                    final Type finalVt = vt;
                    String vName = localNames.computeIfAbsent(vin.var, k -> getLocalVarName(mn, k, finalVt, "var" + k));
                    localTypes.putIfAbsent(vin.var, vt);
                    if (val.startsWith("new ") && !val.contains("(") && !val.contains("[")) {
                        uninitNewVarToSlot.put(vName, vin.var);
                        continue;
                    }
                    if (vt.getSort() == Type.BOOLEAN && ("0".equals(val) || "1".equals(val))) {
                        val = "1".equals(val) ? "true" : "false";
                    }
                    if (declaredLocals.add(vin.var)) {
                        sb.append(indent).append(formatType(vt)).append(" ").append(vName).append(" = ").append(val).append(";\n");
                    } else {
                        sb.append(indent).append(vName).append(" = ").append(val).append(";\n");
                    }
                }
            } else if (insn instanceof IincInsnNode iinc) {
                String vName = localNames.getOrDefault(iinc.var, "var" + iinc.var);
                if (iinc.incr == 1) sb.append(indent).append(vName).append("++;\n");
                else if (iinc.incr == -1) sb.append(indent).append(vName).append("--;\n");
                else sb.append(indent).append(vName).append(" += ").append(iinc.incr).append(";\n");
            } else if (op >= Opcodes.IALOAD && op <= Opcodes.SALOAD) {
                String idx = stack.isEmpty() ? "0" : stack.pop(); if (!typeStack.isEmpty()) typeStack.pop();
                String arr = stack.isEmpty() ? "arr" : stack.pop();
                Type arrT = !typeStack.isEmpty() ? typeStack.pop() : Type.getType("[Ljava/lang/Object;");
                Type elemT = arrT.getSort() == Type.ARRAY ? Type.getType(arrT.getDescriptor().substring(1)) : Type.INT_TYPE;
                stack.push(arr + "[" + idx + "]"); typeStack.push(elemT);
            } else if (op >= Opcodes.IASTORE && op <= Opcodes.SASTORE) {
                String val = stack.isEmpty() ? "0" : stack.pop(); if (!typeStack.isEmpty()) typeStack.pop();
                String idx = stack.isEmpty() ? "0" : stack.pop(); if (!typeStack.isEmpty()) typeStack.pop();
                String arr = stack.isEmpty() ? "arr" : stack.pop(); if (!typeStack.isEmpty()) typeStack.pop();
                sb.append(indent).append(arr).append("[").append(idx).append("] = ").append(val).append(";\n");
            } else if (op == Opcodes.POP || op == Opcodes.POP2) {
                if (!stack.isEmpty()) {
                    String expr = stack.pop(); if (!typeStack.isEmpty()) typeStack.pop();
                    if (expr.contains("(")) sb.append(indent).append(expr).append(";\n");
                }
            } else if (op == Opcodes.DUP) {
                if (!stack.isEmpty()) {
                    stack.push(stack.peek());
                    typeStack.push(typeStack.peek());
                }
            } else if (op >= Opcodes.IADD && op <= Opcodes.LXOR) {
                String r = stack.isEmpty() ? "0" : stack.pop(); if (!typeStack.isEmpty()) typeStack.pop();
                if (op >= Opcodes.INEG && op <= Opcodes.DNEG) {
                    stack.push("-(" + r + ")"); typeStack.push(Type.INT_TYPE);
                } else {
                    String l = stack.isEmpty() ? "0" : stack.pop();
                    Type lt = !typeStack.isEmpty() ? typeStack.pop() : Type.INT_TYPE;
                    String sym = switch (op) {
                        case Opcodes.IADD, Opcodes.LADD, Opcodes.FADD, Opcodes.DADD -> "+";
                        case Opcodes.ISUB, Opcodes.LSUB, Opcodes.FSUB, Opcodes.DSUB -> "-";
                        case Opcodes.IMUL, Opcodes.LMUL, Opcodes.FMUL, Opcodes.DMUL -> "*";
                        case Opcodes.IDIV, Opcodes.LDIV, Opcodes.FDIV, Opcodes.DDIV -> "/";
                        case Opcodes.IREM, Opcodes.LREM, Opcodes.FREM, Opcodes.DREM -> "%";
                        case Opcodes.ISHL, Opcodes.LSHL -> "<<";
                        case Opcodes.ISHR, Opcodes.LSHR -> ">>";
                        case Opcodes.IUSHR, Opcodes.LUSHR -> ">>>";
                        case Opcodes.IAND, Opcodes.LAND -> "&";
                        case Opcodes.IOR, Opcodes.LOR -> "|";
                        case Opcodes.IXOR, Opcodes.LXOR -> "^";
                        default -> "+";
                    };
                    stack.push("(" + l + " " + sym + " " + r + ")");
                    typeStack.push(lt);
                }
            } else if (op >= Opcodes.I2L && op <= Opcodes.I2S) {
                String v = stack.isEmpty() ? "0" : stack.pop(); if (!typeStack.isEmpty()) typeStack.pop();
                Type targetT = switch (op) {
                    case Opcodes.I2L, Opcodes.F2L, Opcodes.D2L -> Type.LONG_TYPE;
                    case Opcodes.I2F, Opcodes.L2F, Opcodes.D2F -> Type.FLOAT_TYPE;
                    case Opcodes.I2D, Opcodes.L2D, Opcodes.F2D -> Type.DOUBLE_TYPE;
                    case Opcodes.I2B -> Type.BYTE_TYPE;
                    case Opcodes.I2C -> Type.CHAR_TYPE;
                    case Opcodes.I2S -> Type.SHORT_TYPE;
                    default -> Type.INT_TYPE;
                };
                stack.push("((" + formatType(targetT) + ") " + v + ")");
                typeStack.push(targetT);
            } else if (op >= Opcodes.LCMP && op <= Opcodes.DCMPG) {
                String r = stack.isEmpty() ? "0" : stack.pop(); if (!typeStack.isEmpty()) typeStack.pop();
                String l = stack.isEmpty() ? "0" : stack.pop(); if (!typeStack.isEmpty()) typeStack.pop();
                String cmpCls = (op == Opcodes.LCMP) ? "Long" : (op == Opcodes.FCMPL || op == Opcodes.FCMPG) ? "Float" : "Double";
                stack.push(cmpCls + ".compare(" + l + ", " + r + ")");
                typeStack.push(Type.INT_TYPE);
            } else if (insn instanceof FieldInsnNode fin) {
                Type ft = Type.getType(fin.desc);
                if (op == Opcodes.GETSTATIC) {
                    String ownerStr = fin.owner.equals(cn.name) ? "" : formatInternalName(fin.owner) + ".";
                    stack.push(ownerStr + fin.name); typeStack.push(ft);
                } else if (op == Opcodes.PUTSTATIC) {
                    String val = stack.isEmpty() ? "null" : stack.pop(); if (!typeStack.isEmpty()) typeStack.pop();
                    if (ft.getSort() == Type.BOOLEAN && ("0".equals(val) || "1".equals(val))) val = "1".equals(val) ? "true" : "false";
                    String ownerStr = fin.owner.equals(cn.name) ? "" : formatInternalName(fin.owner) + ".";
                    sb.append(indent).append(ownerStr).append(fin.name).append(" = ").append(val).append(";\n");
                } else if (op == Opcodes.GETFIELD) {
                    String obj = stack.isEmpty() ? "this" : stack.pop(); if (!typeStack.isEmpty()) typeStack.pop();
                    stack.push(obj + "." + fin.name); typeStack.push(ft);
                } else if (op == Opcodes.PUTFIELD) {
                    String val = stack.isEmpty() ? "null" : stack.pop(); if (!typeStack.isEmpty()) typeStack.pop();
                    String obj = stack.isEmpty() ? "this" : stack.pop(); if (!typeStack.isEmpty()) typeStack.pop();
                    if (ft.getSort() == Type.BOOLEAN && ("0".equals(val) || "1".equals(val))) val = "1".equals(val) ? "true" : "false";
                    sb.append(indent).append(obj).append(".").append(fin.name).append(" = ").append(val).append(";\n");
                }
            } else if (insn instanceof MethodInsnNode min) {
                Type[] mArgs = Type.getArgumentTypes(min.desc);
                Type mRet = Type.getReturnType(min.desc);
                List<String> args = new ArrayList<>();
                for (int i = 0; i < mArgs.length; i++) {
                    String a = stack.isEmpty() ? "null" : stack.pop();
                    if (!typeStack.isEmpty()) typeStack.pop();
                    if (mArgs[mArgs.length - 1 - i].getSort() == Type.BOOLEAN && ("0".equals(a) || "1".equals(a))) {
                        a = "1".equals(a) ? "true" : "false";
                    }
                    args.add(0, a);
                }
                if (min.name.equals("<init>")) {
                    String target = stack.isEmpty() ? "this" : stack.pop();
                    if (!typeStack.isEmpty()) typeStack.pop();
                    if ("this".equals(target)) {
                        if (isEnum && args.size() >= 2) args = args.subList(2, args.size());
                        if (!min.owner.equals("java/lang/Object") && !min.owner.equals("java/lang/Enum") && !min.owner.equals("java/lang/Record")) {
                            String callType = min.owner.equals(cn.name) ? "this" : "super";
                            sb.append(indent).append(callType).append("(").append(String.join(", ", args)).append(");\n");
                        }
                    } else if (uninitNewVarToSlot.containsKey(target)) {
                        int vSlot = uninitNewVarToSlot.remove(target);
                        String newExpr = "new " + formatInternalName(min.owner) + "(" + String.join(", ", args) + ")";
                        Type vt = Type.getObjectType(min.owner);
                        localTypes.put(vSlot, vt);
                        if (declaredLocals.add(vSlot)) {
                            sb.append(indent).append(formatType(vt)).append(" ").append(target).append(" = ").append(newExpr).append(";\n");
                        } else {
                            sb.append(indent).append(target).append(" = ").append(newExpr).append(";\n");
                        }
                    } else if (target.startsWith("new ")) {
                        String newExpr = "new " + formatInternalName(min.owner) + "(" + String.join(", ", args) + ")";
                        if (!stack.isEmpty() && stack.peek().equals(target)) {
                            stack.pop(); typeStack.pop();
                            stack.push(newExpr); typeStack.push(Type.getObjectType(min.owner));
                        } else {
                            sb.append(indent).append(newExpr).append(";\n");
                        }
                    }
                } else {
                    String receiver;
                    if (op == Opcodes.INVOKESTATIC) {
                        if ((min.owner.equals("java/lang/Boolean") || min.owner.equals("java/lang/Integer")
                                || min.owner.equals("java/lang/Float") || min.owner.equals("java/lang/Double")
                                || min.owner.equals("java/lang/Long")) && min.name.equals("valueOf") && args.size() == 1) {
                            stack.push(args.get(0)); typeStack.push(mRet);
                            continue;
                        }
                        receiver = min.owner.equals(cn.name) ? "" : formatInternalName(min.owner) + ".";
                    } else {
                        String obj = stack.isEmpty() ? "this" : stack.pop();
                        if (!typeStack.isEmpty()) typeStack.pop();
                        if ((min.name.equals("booleanValue") || min.name.equals("intValue") || min.name.equals("floatValue")
                                || min.name.equals("doubleValue") || min.name.equals("longValue")) && args.isEmpty()) {
                            stack.push(obj); typeStack.push(mRet);
                            continue;
                        }
                        if (op == Opcodes.INVOKESPECIAL && min.owner.equals(cn.superName)) {
                            receiver = "super.";
                        } else {
                            receiver = "this".equals(obj) ? "this." : obj + ".";
                        }
                    }
                    String call = receiver + min.name + "(" + String.join(", ", args) + ")";
                    if (mRet.getSort() == Type.VOID) {
                        sb.append(indent).append(call).append(";\n");
                    } else {
                        stack.push(call); typeStack.push(mRet);
                    }
                }
            } else if (insn instanceof InvokeDynamicInsnNode idin) {
                Type[] dArgs = Type.getArgumentTypes(idin.desc);
                Type dRet = Type.getReturnType(idin.desc);
                List<String> args = new ArrayList<>();
                for (int i = 0; i < dArgs.length; i++) {
                    args.add(0, stack.isEmpty() ? "null" : stack.pop());
                    if (!typeStack.isEmpty()) typeStack.pop();
                }
                stack.push(formatInvokeDynamic(cn, idin, args, lambdaMethods));
                typeStack.push(dRet);
            } else if (insn instanceof TypeInsnNode tin) {
                if (op == Opcodes.NEW) {
                    stack.push("new " + formatInternalName(tin.desc));
                    typeStack.push(Type.getObjectType(tin.desc));
                } else if (op == Opcodes.ANEWARRAY) {
                    String len = stack.isEmpty() ? "0" : stack.pop(); if (!typeStack.isEmpty()) typeStack.pop();
                    stack.push("new " + formatInternalName(tin.desc) + "[" + len + "]");
                    typeStack.push(Type.getType("[L" + tin.desc + ";"));
                } else if (op == Opcodes.CHECKCAST) {
                    String val = stack.isEmpty() ? "null" : stack.pop(); if (!typeStack.isEmpty()) typeStack.pop();
                    Type castT = Type.getObjectType(tin.desc);
                    stack.push("((" + formatType(castT) + ") " + val + ")");
                    typeStack.push(castT);
                } else if (op == Opcodes.INSTANCEOF) {
                    String val = stack.isEmpty() ? "null" : stack.pop(); if (!typeStack.isEmpty()) typeStack.pop();
                    stack.push("(" + val + " instanceof " + formatInternalName(tin.desc) + ")");
                    typeStack.push(Type.BOOLEAN_TYPE);
                }
            } else if (insn instanceof IntInsnNode iin && op == Opcodes.NEWARRAY) {
                String len = stack.isEmpty() ? "0" : stack.pop(); if (!typeStack.isEmpty()) typeStack.pop();
                String elem = switch (iin.operand) {
                    case Opcodes.T_BOOLEAN -> "boolean";
                    case Opcodes.T_CHAR -> "char";
                    case Opcodes.T_FLOAT -> "float";
                    case Opcodes.T_DOUBLE -> "double";
                    case Opcodes.T_BYTE -> "byte";
                    case Opcodes.T_SHORT -> "short";
                    case Opcodes.T_INT -> "int";
                    case Opcodes.T_LONG -> "long";
                    default -> "int";
                };
                stack.push("new " + elem + "[" + len + "]");
                typeStack.push(Type.getType("[" + elem.toUpperCase().charAt(0)));
            } else if (insn instanceof MultiANewArrayInsnNode man) {
                List<String> dims = new ArrayList<>();
                for (int i = 0; i < man.dims; i++) {
                    dims.add(0, "[" + (stack.isEmpty() ? "0" : stack.pop()) + "]");
                    if (!typeStack.isEmpty()) typeStack.pop();
                }
                Type arrT = Type.getType(man.desc);
                String base = formatType(arrT.getElementType());
                int emptyDims = arrT.getDimensions() - man.dims;
                stack.push("new " + base + String.join("", dims) + "[]".repeat(Math.max(0, emptyDims)));
                typeStack.push(arrT);
            } else if (op == Opcodes.ARRAYLENGTH) {
                String arr = stack.isEmpty() ? "arr" : stack.pop(); if (!typeStack.isEmpty()) typeStack.pop();
                stack.push(arr + ".length"); typeStack.push(Type.INT_TYPE);
            } else if (op >= Opcodes.IRETURN && op <= Opcodes.ARETURN) {
                String val = stack.isEmpty() ? "null" : stack.pop(); if (!typeStack.isEmpty()) typeStack.pop();
                Type rt = Type.getReturnType(mn.desc);
                if (rt.getSort() == Type.BOOLEAN && ("0".equals(val) || "1".equals(val))) val = "1".equals(val) ? "true" : "false";
                sb.append(indent).append("return ").append(val).append(";\n");
            } else if (op == Opcodes.RETURN) {
                if (insn.getNext() != null || !openScopes.isEmpty()) sb.append(indent).append("return;\n");
            } else if (op == Opcodes.ATHROW) {
                String ex = stack.isEmpty() ? "new RuntimeException()" : stack.pop(); if (!typeStack.isEmpty()) typeStack.pop();
                sb.append(indent).append("throw ").append(ex).append(";\n");
            } else if (insn instanceof JumpInsnNode jin) {
                insn = handleStructuredJump(jin, stack, typeStack, openScopes, labelOrder, sb, indent);
            } else if (insn instanceof LookupSwitchInsnNode lsin) {
                String keyExpr = stack.isEmpty() ? "0" : stack.pop(); if (!typeStack.isEmpty()) typeStack.pop();
                sb.append(indent).append("switch (").append(keyExpr).append(") {\n");
                for (int i = 0; i < lsin.keys.size(); i++) {
                    sb.append(indent).append("    case ").append(lsin.keys.get(i)).append(" -> {}\n");
                }
                sb.append(indent).append("    default -> {}\n");
                sb.append(indent).append("}\n");
            } else if (insn instanceof TableSwitchInsnNode tsin) {
                String keyExpr = stack.isEmpty() ? "0" : stack.pop(); if (!typeStack.isEmpty()) typeStack.pop();
                sb.append(indent).append("switch (").append(keyExpr).append(") {\n");
                for (int i = 0; i < tsin.labels.size(); i++) {
                    sb.append(indent).append("    case ").append(tsin.min + i).append(" -> {}\n");
                }
                sb.append(indent).append("    default -> {}\n");
                sb.append(indent).append("}\n");
            }
        }
        while (!openScopes.isEmpty()) {
            OpenScope sc = openScopes.pop();
            String curInd = baseIndent + "    ".repeat(openScopes.size());
            if (sc.tryBlock != null && sc.tryBlock.handler != sc.tryBlock.end) {
                String exType = formatInternalName(sc.tryBlock.type);
                sb.append(curInd).append("} catch (").append(exType).append(" ignored) {\n");
                sb.append(curInd).append("}\n");
            } else {
                sb.append(curInd).append("}\n");
            }
        }
    }

    private boolean containsCloseLabel(Deque<OpenScope> scopes, LabelNode ln) {
        for (OpenScope sc : scopes) {
            if (sc.closeLabel == ln) return true;
        }
        return false;
    }

    private OpenScope findEnclosingLoop(Deque<OpenScope> scopes) {
        for (OpenScope sc : scopes) {
            if (sc.isLoop) return sc;
        }
        return null;
    }

    private LabelNode findNextMergeOrEndLabel(LabelNode start) {
        AbstractInsnNode cur = start.getNext();
        LabelNode lastLabel = null;
        while (cur != null) {
            if (cur instanceof LabelNode ln) lastLabel = ln;
            if (cur instanceof JumpInsnNode jin && jin.getOpcode() == Opcodes.GOTO) {
                return jin.label;
            }
            if (cur.getOpcode() >= Opcodes.IRETURN && cur.getOpcode() <= Opcodes.RETURN || cur.getOpcode() == Opcodes.ATHROW) {
                AbstractInsnNode n = cur.getNext();
                while (n != null && !(n instanceof LabelNode)) n = n.getNext();
                if (n instanceof LabelNode ln) return ln;
            }
            cur = cur.getNext();
        }
        return lastLabel;
    }

    private AbstractInsnNode handleStructuredJump(JumpInsnNode jin, Deque<String> stack, Deque<Type> typeStack,
            Deque<OpenScope> openScopes, Map<LabelNode, Integer> labelOrder, StringBuilder sb, String indent) {
        int op = jin.getOpcode();
        if (op == Opcodes.GOTO) return jin;

        AbstractInsnNode n1 = nextReal(jin.getNext());
        if (n1 != null && (n1.getOpcode() == Opcodes.ICONST_0 || n1.getOpcode() == Opcodes.ICONST_1)) {
            AbstractInsnNode n2 = nextReal(n1.getNext());
            if (n2 instanceof JumpInsnNode gotoInsn && gotoInsn.getOpcode() == Opcodes.GOTO && n2.getNext() == jin.label) {
                AbstractInsnNode n3 = nextReal(jin.label.getNext());
                if (n3 != null && (n3.getOpcode() == Opcodes.ICONST_0 || n3.getOpcode() == Opcodes.ICONST_1)
                        && n3.getNext() == gotoInsn.label) {
                    boolean trueOnBranch = (n3.getOpcode() == Opcodes.ICONST_1);
                    String cond = popCondition(jin, stack, typeStack, !trueOnBranch);
                    stack.push("(" + cond + ")");
                    typeStack.push(Type.BOOLEAN_TYPE);
                    return gotoInsn.label;
                }
            }
        }

        int targetOrd = labelOrder.getOrDefault(jin.label, Integer.MAX_VALUE);
        int curOrd = -1;
        for (AbstractInsnNode p = jin.getPrevious(); p != null; p = p.getPrevious()) {
            if (p instanceof LabelNode ln && labelOrder.containsKey(ln)) {
                curOrd = labelOrder.get(ln);
                break;
            }
        }

        if (targetOrd <= curOrd) {
            String cond = popCondition(jin, stack, typeStack, false);
            sb.append(indent).append("if (").append(cond).append(") continue;\n");
            return jin;
        }

        OpenScope encLoop = findEnclosingLoop(openScopes);
        if (encLoop != null && encLoop.closeLabel != null) {
            int loopEndOrd = labelOrder.getOrDefault(encLoop.closeLabel, Integer.MAX_VALUE);
            if (targetOrd >= loopEndOrd) {
                String cond = popCondition(jin, stack, typeStack, false);
                sb.append(indent).append("if (").append(cond).append(") break;\n");
                return jin;
            }
        }

        LabelNode elseEnd = null;
        AbstractInsnNode prevBeforeTarget = jin.label.getPrevious();
        while (prevBeforeTarget != null && (prevBeforeTarget instanceof LineNumberNode || prevBeforeTarget instanceof FrameNode)) {
            prevBeforeTarget = prevBeforeTarget.getPrevious();
        }
        if (prevBeforeTarget instanceof JumpInsnNode prevGoto && prevGoto.getOpcode() == Opcodes.GOTO) {
            int elseOrd = labelOrder.getOrDefault(prevGoto.label, -1);
            if (elseOrd > targetOrd) {
                elseEnd = prevGoto.label;
            }
        }

        String negatedCond = popCondition(jin, stack, typeStack, true);
        sb.append(indent).append("if (").append(negatedCond).append(") {\n");
        openScopes.push(new OpenScope(jin.label, elseEnd, false, null));
        return jin;
    }

    private AbstractInsnNode nextReal(AbstractInsnNode insn) {
        while (insn != null && (insn instanceof LineNumberNode || insn instanceof FrameNode)) {
            insn = insn.getNext();
        }
        return insn;
    }

    private String popCondition(JumpInsnNode jin, Deque<String> stack, Deque<Type> typeStack, boolean negate) {
        int op = jin.getOpcode();
        if (negate) {
            op = switch (op) {
                case Opcodes.IFEQ -> Opcodes.IFNE;
                case Opcodes.IFNE -> Opcodes.IFEQ;
                case Opcodes.IFLT -> Opcodes.IFGE;
                case Opcodes.IFGE -> Opcodes.IFLT;
                case Opcodes.IFGT -> Opcodes.IFLE;
                case Opcodes.IFLE -> Opcodes.IFGT;
                case Opcodes.IFNULL -> Opcodes.IFNONNULL;
                case Opcodes.IFNONNULL -> Opcodes.IFNULL;
                case Opcodes.IF_ICMPEQ -> Opcodes.IF_ICMPNE;
                case Opcodes.IF_ICMPNE -> Opcodes.IF_ICMPEQ;
                case Opcodes.IF_ICMPLT -> Opcodes.IF_ICMPGE;
                case Opcodes.IF_ICMPGE -> Opcodes.IF_ICMPLT;
                case Opcodes.IF_ICMPGT -> Opcodes.IF_ICMPLE;
                case Opcodes.IF_ICMPLE -> Opcodes.IF_ICMPGT;
                case Opcodes.IF_ACMPEQ -> Opcodes.IF_ACMPNE;
                case Opcodes.IF_ACMPNE -> Opcodes.IF_ACMPEQ;
                default -> op;
            };
        }
        if ((op >= Opcodes.IFEQ && op <= Opcodes.IFLE) || op == Opcodes.IFNULL || op == Opcodes.IFNONNULL) {
            String v = stack.isEmpty() ? "0" : stack.pop();
            Type vt = !typeStack.isEmpty() ? typeStack.pop() : Type.INT_TYPE;
            if (vt.getSort() == Type.BOOLEAN && (op == Opcodes.IFEQ || op == Opcodes.IFNE)) {
                return (op == Opcodes.IFEQ) ? "!" + wrapIfComplex(v) : v;
            }
            if (v.startsWith("Long.compare(") || v.startsWith("Float.compare(") || v.startsWith("Double.compare(")) {
                String inner = v.substring(v.indexOf('(') + 1, v.lastIndexOf(')'));
                int comma = findTopLevelComma(inner);
                String lhs = comma > 0 ? inner.substring(0, comma).trim() : inner;
                String rhs = comma > 0 ? inner.substring(comma + 1).trim() : "0";
                String sym = switch (op) {
                    case Opcodes.IFEQ -> "==";
                    case Opcodes.IFNE -> "!=";
                    case Opcodes.IFLT -> "<";
                    case Opcodes.IFGE -> ">=";
                    case Opcodes.IFGT -> ">";
                    case Opcodes.IFLE -> "<=";
                    default -> "==";
                };
                return lhs + " " + sym + " " + rhs;
            }
            String sym = switch (op) {
                case Opcodes.IFEQ -> "== 0";
                case Opcodes.IFNE -> "!= 0";
                case Opcodes.IFLT -> "< 0";
                case Opcodes.IFGE -> ">= 0";
                case Opcodes.IFGT -> "> 0";
                case Opcodes.IFLE -> "<= 0";
                case Opcodes.IFNULL -> "== null";
                case Opcodes.IFNONNULL -> "!= null";
                default -> "!= 0";
            };
            return v + " " + sym;
        } else {
            String r = stack.isEmpty() ? "0" : stack.pop(); if (!typeStack.isEmpty()) typeStack.pop();
            String l = stack.isEmpty() ? "0" : stack.pop(); if (!typeStack.isEmpty()) typeStack.pop();
            String sym = switch (op) {
                case Opcodes.IF_ICMPEQ, Opcodes.IF_ACMPEQ -> "==";
                case Opcodes.IF_ICMPNE, Opcodes.IF_ACMPNE -> "!=";
                case Opcodes.IF_ICMPLT -> "<";
                case Opcodes.IF_ICMPGE -> ">=";
                case Opcodes.IF_ICMPGT -> ">";
                case Opcodes.IF_ICMPLE -> "<=";
                default -> "==";
            };
            return l + " " + sym + " " + r;
        }
    }

    private String wrapIfComplex(String expr) {
        if (expr.contains(" ") && !(expr.startsWith("(") && expr.endsWith(")"))) {
            return "(" + expr + ")";
        }
        return expr;
    }

    private int findTopLevelComma(String s) {
        int depth = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') depth++;
            else if (c == ')') depth--;
            else if (c == ',' && depth == 0) return i;
        }
        return s.indexOf(',');
    }
}

}
