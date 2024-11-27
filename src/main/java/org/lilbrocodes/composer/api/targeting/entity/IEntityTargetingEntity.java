package org.lilbrocodes.composer.api.targeting.entity;

import net.minecraft.entity.Entity;

public interface IEntityTargetingEntity {
    Entity composer$getLastEntityTarget();
    void composer$setLastEntityTarget(Entity target);
}
