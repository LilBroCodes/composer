package org.lilbrocodes.composer.api.targeting.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.lilbrocodes.composer.ComposerConfig;

import java.util.function.Predicate;

public class Targeting {
    public static boolean isValidTarget(@Nullable Entity target, PlayerEntity player) {
        if (!(target instanceof LivingEntity) && !ComposerConfig.TARGET_NON_LIVING) return false;
        if (player == null) return false;
        if (target == player) return false;
        if (!player.canSee(target)) return false;
        if (target instanceof LivingEntity living) {
            if (living.isDead()) return false;
        }
        assert target != null;
        if (target.isRemoved()) return false;
        if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(target)) return false;
        if (target instanceof TameableEntity tamed && tamed.isOwner(player) && !ComposerConfig.TARGET_TAMED) return false;
        return target.canHit();
    }

    /**
     * Casts a ray from the player, detecting entities within the scaled hitboxes.
     *
     * @param player      The entity (usually a player) from which the ray originates.
     * @param box         The bounding box defining the ray's range.
     * @param predicate   A filter for entities to include in the raycast (e.g., exclude certain entity types).
     * @param hitboxScale A factor to scale entity hitboxes (1.0 = normal size, 2.0 = double size, etc.).
     * @return An EntityHitResult if an entity is hit, or null if no entity is hit.
     */
    public static EntityHitResult raycast(Entity player, Box box, Predicate<Entity> predicate, float hitboxScale) {
        World world = player.getWorld();
        Vec3d start = player.getEyePos();
        Vec3d direction = player.getRotationVec(1.0F);
        Vec3d end = start.add(direction.multiply(box.getXLength()));

        Entity closestEntity = null;
        double closestDistance = Double.MAX_VALUE;
        Vec3d hitEntityPos = null;

        for (Entity entity : world.getOtherEntities(player, box, predicate)) {
            Box scaledBox = entity.getBoundingBox().expand(hitboxScale - 1.0);
            Vec3d hitPos = scaledBox.raycast(start, end).orElse(null);

            if (hitPos != null) {
                double distance = start.squaredDistanceTo(hitPos);
                if (distance < closestDistance) {
                    closestEntity = entity;
                    closestDistance = distance;
                    hitEntityPos = hitPos;
                }
            }
        }

        if (closestEntity != null) return new EntityHitResult(closestEntity, hitEntityPos);
        return null;
    }
}
