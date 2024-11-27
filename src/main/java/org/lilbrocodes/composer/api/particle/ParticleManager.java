package org.lilbrocodes.composer.api.particle;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * This class handles the management and spawning of particles in the world.
 */
public class ParticleManager {

    /**
     * Spawns a particle at the specified position with the given velocity and particle type.
     *
     * @param world        The world in which to spawn the particle.
     * @param position     The position to spawn the particle at.
     * @param velocity     The velocity of the particle.
     * @param particleType The type of the particle to spawn.
     */
    public static void spawnParticle(World world, Vec3d position, Vec3d velocity, ParticleEffect particleType) {
        world.addParticle(
                particleType,
                position.x,
                position.y,
                position.z,
                velocity.x,
                velocity.y,
                velocity.z
        );
    }
}
