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

public final class BlockESPChunkScanner {
    private final ChunkAccess chunkAccess;
    private final Set entriesSet;
    private final boolean enabledState;
    private volatile List entriesList;
    private CompletableFuture pendingTaskFuture;
    private volatile boolean enabledState1;

    BlockESPChunkScanner(ChunkAccess arg0, Set arg1, boolean arg2) {
        List var5 = Collections.emptyList();
        this.entriesList = var5;
        this.pendingTaskFuture = null;
        this.enabledState1 = false;
        this.chunkAccess = arg0;
        this.entriesSet = arg1;
        this.enabledState = arg2;
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
        int var22 = 0;
        int var23 = 0;
        int var24 = 0;
        ArrayList var1 = new ArrayList();
        BlockPos.MutableBlockPos var27 = new BlockPos.MutableBlockPos();
        int var31 = this.chunkAccess.getPos().getMinBlockX();
        int var35 = this.chunkAccess.getPos().getMinBlockZ();
        int var38 = this.chunkAccess.getMinY();
        int var41 = this.chunkAccess.getMaxY();
        var22 = var31;
        while (true) {
            int var43 = var22;
            int var75 = (var31 + 16);
            if (var43 < var75) {
                if (!this.enabledState1) {
                    var23 = var35;
                    while (true) {
                        int var47 = var23;
                        int var77 = (var35 + 16);
                        if (var47 >= var77) break;
                        if (this.enabledState1) break;
                        var24 = var38;
                        while (true) {
                            if (var24 >= var41) break;
                            if (this.enabledState1) break;
                            int var79 = var22;
                            int var89 = var24;
                            int var3 = var23;
                            BlockPos.MutableBlockPos var55 = var27.set(var79, var89, var3);
                            BlockState var58 = this.chunkAccess.getBlockState(var27);
                            Set var60 = this.entriesSet;
                            Block var82 = var58.getBlock();
                            if (!var60.contains(var82)) {
                            } else {
                                if (this.enabledState) {
                                    int var83 = var22;
                                    int var90 = var24;
                                    int var92 = var23;
                                    if (!(this.isEligible(var83, var90, var92, var31, var35, var38, var41))) {
                                    }
                                } else {
                                    ChunkAccess var85 = this.chunkAccess;
                                    int var68 = var58.getMapColor(var85, var27).col;
                                    double var97 = ((double) var22);
                                    double var103 = ((double) var24);
                                    double var107 = ((double) var23);
                                    double var111 = ((double) (var22 + 1));
                                    double var114 = ((double) (var24 + 1));
                                    double var117 = ((double) (var23 + 1));
                                    AABB var93 = new AABB(var97, var103, var107, var111, var114, var117);
                                    Block var95 = var58.getBlock();
                                    int var105 = var22;
                                    int var8 = var24;
                                    int var109 = var23;
                                    BlockPos var99 = new BlockPos(var105, var8, var109);
                                    int var101 = (var68 & 16777215);
                                    BlockESPEntry var86 = new BlockESPEntry(var93, var95, var99, var101);
                                    boolean var70 = var1.add(var86);
                                }
                                var24 = (var24 + 1);
                            }
                        }
                    }
                }
                if (!this.enabledState1) {
                    this.entriesList = var1;
                }
                return;
                var23 = (var23 + 1);
                var22 = (var22 + 1);
            }
        }
    }

    private boolean isEligible(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6) {
        if ((arg1 + 1) < arg6) {
            ChunkAccess var15 = this.chunkAccess;
            int var79 = (arg1 + 1);
            if (isEligible(var15, arg0, var79, arg2)) {
                return true;
            }
        }
        if ((arg1 - 1) >= arg5) {
            ChunkAccess var20 = this.chunkAccess;
            int var81 = (arg1 - 1);
            if (isEligible(var20, arg0, var81, arg2)) {
                return true;
            }
        }
        if ((arg1 + 1) < arg6) {
            if ((arg1 - 1) < arg5) {
            }
        }
        return true;
        if ((arg2 - 1) >= arg4) {
            ChunkAccess var29 = this.chunkAccess;
            int var94 = (arg2 - 1);
            if (isEligible(var29, arg0, arg1, var94)) {
                return true;
            }
        }
        int var32 = (arg2 + 1);
        int var68 = (arg4 + 16);
        if (var32 < var68) {
            ChunkAccess var34 = this.chunkAccess;
            int var96 = (arg2 + 1);
            if (isEligible(var34, arg0, arg1, var96)) {
                return true;
            }
        }
        if ((arg0 - 1) >= arg3) {
            ChunkAccess var40 = this.chunkAccess;
            int var73 = (arg0 - 1);
            if (isEligible(var40, var73, arg1, arg2)) {
                return true;
            }
        }
        int var44 = (arg0 + 1);
        int var76 = (arg3 + 16);
        if (var44 < var76) {
            ChunkAccess var46 = this.chunkAccess;
            int var78 = (arg0 + 1);
            if (isEligible(var46, var78, arg1, arg2)) {
                return true;
            }
        }
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
