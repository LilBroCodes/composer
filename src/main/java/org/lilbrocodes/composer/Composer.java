package org.lilbrocodes.composer;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lilbrocodes.composer.advancement.AdvancementManager;
import org.lilbrocodes.composer.advancement.PredicateAdvancement;
import org.lilbrocodes.composer.api.targeting.block.IEntityTargetingBlock;
import org.lilbrocodes.composer.api.targeting.entity.IEntityTargetingEntity;

import java.util.ArrayList;
import java.util.List;

public class Composer implements ModInitializer {
    public static final Identifier ENTITY_TARGET_PACKET = new Identifier("target");
    public static final Identifier BLOCK_TARGET_PACKET = new Identifier("block");

    public static List<PredicateAdvancement> REGISTERED_ADVANCEMENTS = new ArrayList<>();

    @Override
    public void onInitialize() {
        ServerPlayNetworking.registerGlobalReceiver(ENTITY_TARGET_PACKET, (server, player, networkHandler, packetByteBuf, sender) -> {
            int id = packetByteBuf.readInt();
            server.execute(() -> {
                if (player instanceof IEntityTargetingEntity targeting) {
                    if (!ComposerConfig.TARGET_NON_LIVING) {
                        if (player.getWorld().getEntityById(id) instanceof LivingEntity living) {
                            targeting.composer$setLastEntityTarget(living);
                        }
                    } else {
                        targeting.composer$setLastEntityTarget(player.getWorld().getEntityById(id));
                    }
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(BLOCK_TARGET_PACKET, (server, player, networkHandler, packetByteBuf, sender) -> {
            BlockPos blockPos = packetByteBuf.readBlockPos();
            server.execute(() -> {
                if (player instanceof IEntityTargetingBlock targeting) {
                    targeting.composer$setLastBlockTarget(blockPos);
                }
            });
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (World world : server.getWorlds()) {
                for (PlayerEntity player : world.getPlayers()) {
                    for (PredicateAdvancement advancement : REGISTERED_ADVANCEMENTS) {
                        if (advancement.predicate().test(player)) {
                            AdvancementManager.grantAdvancement((ServerPlayerEntity) player, advancement.identifier());
                        }
                    }
                }
            }
        });
    }
}
