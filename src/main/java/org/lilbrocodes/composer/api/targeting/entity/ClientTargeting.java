package org.lilbrocodes.composer.api.targeting.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.lilbrocodes.composer.ComposerConfig;

import java.util.function.Predicate;

/**
 * A utility class for client-side targeting of entities. This class provides methods
 * for determining which entity the player is currently targeting based on the player's
 * camera position and rotation.
 */
public class ClientTargeting extends Targeting {

    /**
     * Retrieves the entity that the player is currently targeting based on the player's
     * camera position and rotation. This method considers the player's line of sight
     * and provides the closest valid entity within the targeting range.
     *
     * @param player          The player whose targeting information is being checked.
     * @param ignoreTamed     A flag indicating whether tamed entities should be ignored.
     * @param ignoreNonLiving A flag indicating whether non-living entities (e.g., items) should be ignored.
     * @return The {@link Entity} that the player is currently targeting, or {@code null} if no valid target is found.
     */
    @Nullable
    public static Entity getCurrentlyTargeting(PlayerEntity player, boolean ignoreTamed, boolean ignoreNonLiving) {
        double distanceCap = 128f * 128f;
        assert MinecraftClient.getInstance().cameraEntity != null;
        Vec3d cameraPos = MinecraftClient.getInstance().cameraEntity.getCameraPosVec(1.0f);
        Vec3d cameraRot = MinecraftClient.getInstance().cameraEntity.getRotationVec(1.0f);
        Vec3d cameraTarget = cameraPos.add(cameraRot.multiply(distanceCap));
        Box box = player.getBoundingBox().stretch(cameraTarget).expand(1.0, 1.0, 1.0);
        Predicate<Entity> predicate = entity -> isValidTarget(entity, player);
        EntityHitResult entityHitResult = raycast(player, box, predicate, ComposerConfig.HITBOX_SCALE);
        if (entityHitResult != null) {
            Entity target = entityHitResult.getEntity();
            if (target instanceof LivingEntity || ignoreNonLiving) {
                return target;
            }
        }
        return null;
    }
}
