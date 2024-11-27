package org.lilbrocodes.composer.api.note;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class NoteUtil {
    /**
     * Spawns a note particle at the specified location with the given note block pitch.
     *
     * @param world The world where the particle should be spawned.
     * @param position The position to spawn the particle at.
     * @param pitch The note block pitch (0-24).
     */
    public static void spawnNoteParticle(World world, Vec3d position, int pitch) {
        if (pitch < 0 || pitch > 24) {
            throw new IllegalArgumentException("Pitch must be between 0 and 24 (inclusive).");
        }

        float hue = pitch / 24.0f;

        world.addParticle(
                ParticleTypes.NOTE,
                position.x,
                position.y,
                position.z,
                hue,
                0,
                0
        );
    }

    /**
     * Plays a note block sound at the specified location with the given pitch and instrument.
     *
     * @param world      The world where the sound should be played.
     * @param position   The position to play the sound at.
     * @param pitch      The note block pitch (0-24).
     * @param instrument The instrument to use for the note block sound.
     */
    public static void playNoteSound(World world, BlockPos position, int pitch, Instrument instrument) {
        if (pitch < 0 || pitch > 24) {
            throw new IllegalArgumentException("Pitch must be between 0 and 24 (inclusive).");
        }

        float soundPitch = (float) Math.pow(2.0, (pitch - 12) / 12.0);

        world.playSound(
                null,
                position,
                instrument.getSoundKey(),
                SoundCategory.PLAYERS,
                1.0f,
                soundPitch
        );
    }
}
