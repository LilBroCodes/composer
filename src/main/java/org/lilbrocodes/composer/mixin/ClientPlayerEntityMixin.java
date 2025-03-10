package org.lilbrocodes.composer.mixin;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import org.lilbrocodes.composer.Composer;
import org.lilbrocodes.composer.ComposerConfig;
import org.lilbrocodes.composer.api.targeting.block.IEntityTargetingBlock;
import org.lilbrocodes.composer.api.targeting.entity.IEntityTargetingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin implements IEntityTargetingEntity, IEntityTargetingBlock {

    @Unique private Entity lastEntityTarget;
    @Unique private int entityTargetDecay = 0;

    @Unique private BlockPos lastBlockTarget;
    @Unique private int blockTargetDecay = 0;

    @Override
    public Entity composer$getLastEntityTarget() {
        return this.lastEntityTarget;
    }

    @Override
    public void composer$setLastEntityTarget(Entity target) {
        this.lastEntityTarget = target;
        if (ComposerConfig.DECAY_ENTITY_TARGETS) {
            this.entityTargetDecay = ComposerConfig.ENTITY_DECAY_TICKS;
        } else {
            this.entityTargetDecay = 2;
        }
        if (target != null) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeInt(target.getId());
            ClientPlayNetworking.send(Composer.ENTITY_TARGET_PACKET, buf);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void composer$decayTarget(CallbackInfo ci) {
        if (lastEntityTarget != null) {
            entityTargetDecay--;
            if (this.entityTargetDecay == 0) {
                this.lastEntityTarget = null;
            }
        }
        if (lastBlockTarget != null) {
            blockTargetDecay--;
            if (this.blockTargetDecay == 0) {
                this.lastBlockTarget = null;
            }
        }
    }

    @Override
    public BlockPos composer$getLastBlockTarget() {
        return this.lastBlockTarget;
    }

    @Override
    public void composer$setLastBlockTarget(BlockPos target) {
        this.lastBlockTarget = target;
        if (ComposerConfig.DECAY_BLOCK_TARGETS) {
            this.blockTargetDecay = ComposerConfig.BLOCK_DECAY_TICKS;
        } else {
            this.blockTargetDecay = 2;
        }
        if (target != null) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBlockPos(target);
            ClientPlayNetworking.send(Composer.BLOCK_TARGET_PACKET, buf);
        }
    }
}
