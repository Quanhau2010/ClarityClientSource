package need.clarity.modules.visual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.phys.AABB;

public final class SearchChunkScanner {
    private final ChunkAccess chunkAccess;
    private final ChunkAccess chunkAccess1;
    private final ChunkAccess chunkAccess2;
    private final ChunkAccess chunkAccess3;
    private final ChunkAccess chunkAccess4;
    private final Set entriesSet;
    private final boolean enabledState;
    private volatile List entriesList;
    private CompletableFuture pendingTaskFuture;
    private volatile boolean enabledState1;

    SearchChunkScanner(ChunkAccess arg0, ChunkAccess arg1, ChunkAccess arg2, ChunkAccess arg3, ChunkAccess arg4, Set arg5, boolean arg6) {
        List var9 = Collections.emptyList();
        this.entriesList = var9;
        this.pendingTaskFuture = null;
        this.enabledState1 = false;
        this.chunkAccess = arg0;
        this.chunkAccess1 = arg1;
        this.chunkAccess2 = arg2;
        this.chunkAccess3 = arg3;
        this.chunkAccess4 = arg4;
        this.entriesSet = arg5;
        this.enabledState = arg6;
    }

    void applyStep(ExecutorService arg0) {
        CompletableFuture var6 = CompletableFuture.runAsync(() -> this.applyStep1(), arg0);
        this.pendingTaskFuture = var6;
    }

    void applyStep() {
        this.enabledState1 = true;
        if (this.pendingTaskFuture != null) {
            boolean var7 = this.pendingTaskFuture.cancel(false);
        }
    }

    List evaluate() {
        return this.entriesList;
    }

    private void applyStep1() {
        int var21 = 0;
        int var22 = 0;
        int var23 = 0;
        ArrayList var1 = new ArrayList();
        BlockPos.MutableBlockPos var25 = new BlockPos.MutableBlockPos();
        int var29 = this.chunkAccess.getPos().getMinBlockX();
        int var33 = this.chunkAccess.getPos().getMinBlockZ();
        int var36 = this.chunkAccess.getMinY();
        int var39 = this.chunkAccess.getMaxY();
        var21 = var29;
        while (true) {
            int var41 = var21;
            int var70 = (var29 + 16);
            if (var41 >= var70) break;
            if (this.enabledState1) break;
            var22 = var33;
            while (true) {
                int var45 = var22;
                int var72 = (var33 + 16);
                if (var45 >= var72) break;
                if (this.enabledState1) break;
                var23 = var36;
                while (true) {
                    if (var23 < var39) {
                        if (!this.enabledState1) {
                            int var74 = var21;
                            int var82 = var23;
                            int var3 = var22;
                            BlockPos.MutableBlockPos var53 = var25.set(var74, var82, var3);
                            BlockState var56 = this.chunkAccess.getBlockState(var25);
                            Set var58 = this.entriesSet;
                            Block var77 = var56.getBlock();
                            if (!var58.contains(var77)) {
                            }
                        } else {
                        }
                    }
                    var22 = (var22 + 1);
                }
            }
            var21 = (var21 + 1);
        }
        if (this.enabledState) {
            int var78 = var21;
            int var83 = var23;
            int var84 = var22;
            if (!(this.isEligible(var78, var83, var84, var29, var33, var36, var39))) {
            } else {
            }
        }
        double var89 = ((double) var21);
        double var92 = ((double) var23);
        double var94 = ((double) var22);
        double var97 = ((double) (var21 + 1));
        double var100 = ((double) (var23 + 1));
        double var103 = ((double) (var22 + 1));
        AABB var85 = new AABB(var89, var92, var94, var97, var100, var103);
        Block var87 = var56.getBlock();
        SearchBlockEntry var79 = new SearchBlockEntry(var85, var87);
        boolean var65 = var1.add(var79);
        var23 = (var23 + 1);
        if (!this.enabledState1) {
            this.entriesList = var1;
        }
    }

    private boolean isEligible(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6) {
        if ((arg1 + 1) < arg6) {
            ChunkAccess var15 = this.chunkAccess;
            int var109 = (arg1 + 1);
            if (isEligible(var15, arg0, var109, arg2)) {
                return true;
            }
        }
        if ((arg1 - 1) >= arg5) {
            ChunkAccess var21 = this.chunkAccess;
            int var111 = (arg1 - 1);
            if (isEligible(var21, arg0, var111, arg2)) {
                return true;
            }
        }
        if ((arg1 + 1) < arg6) {
            if ((arg1 - 1) < arg5) {
            }
        }
        return true;
        if ((arg2 - 1) >= arg4) {
            ChunkAccess var31 = this.chunkAccess;
            int var130 = (arg2 - 1);
            if (isEligible(var31, arg0, arg1, var130)) {
                return true;
            }
        }
        if (this.chunkAccess1 != null) {
            ChunkAccess var74 = this.chunkAccess1;
            int var140 = (arg2 - 1);
            if (isEligible(var74, arg0, arg1, var140)) {
            }
        }
        return true;
        int var35 = (arg2 + 1);
        int var92 = (arg4 + 16);
        if (var35 < var92) {
            ChunkAccess var37 = this.chunkAccess;
            int var132 = (arg2 + 1);
            if (isEligible(var37, arg0, arg1, var132)) {
                return true;
            }
        }
        if (this.chunkAccess2 != null) {
            ChunkAccess var68 = this.chunkAccess2;
            int var138 = (arg2 + 1);
            if (isEligible(var68, arg0, arg1, var138)) {
            }
        }
        return true;
        if ((arg0 - 1) >= arg3) {
            ChunkAccess var43 = this.chunkAccess;
            int var97 = (arg0 - 1);
            if (isEligible(var43, var97, arg1, arg2)) {
                return true;
            }
        }
        if (this.chunkAccess3 != null) {
            ChunkAccess var62 = this.chunkAccess3;
            int var106 = (arg0 - 1);
            if (isEligible(var62, var106, arg1, arg2)) {
            }
        }
        return true;
        int var47 = (arg0 + 1);
        int var100 = (arg3 + 16);
        if (var47 < var100) {
            ChunkAccess var56 = this.chunkAccess;
            int var104 = (arg0 + 1);
            if (isEligible(var56, var104, arg1, arg2)) {
                return true;
            }
        }
        if (this.chunkAccess4 != null) {
            ChunkAccess var51 = this.chunkAccess4;
            int var102 = (arg0 + 1);
            if (isEligible(var51, var102, arg1, arg2)) {
            }
        }
        return true;
        return false;
    }

    private static boolean isEligible(ChunkAccess arg0, int arg1, int arg2, int arg3) {
        try {
            BlockPos var5 = new BlockPos(arg1, arg2, arg3);
            boolean var11 = arg0.getBlockState(var5).isAir();
        } catch (Exception ignored) {
        }
        return var11;
    }

}
