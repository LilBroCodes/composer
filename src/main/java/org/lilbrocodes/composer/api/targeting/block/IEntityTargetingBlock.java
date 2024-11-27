package org.lilbrocodes.composer.api.targeting.block;

import net.minecraft.util.math.BlockPos;

public interface IEntityTargetingBlock {
    BlockPos composer$getLastBlockTarget();
    void composer$setLastBlockTarget(BlockPos target);
}
