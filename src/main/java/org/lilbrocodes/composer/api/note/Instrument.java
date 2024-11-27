package org.lilbrocodes.composer.api.note;

import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.Objects;

public enum Instrument {
    HARP(SoundEvents.BLOCK_NOTE_BLOCK_HARP.value()),
    BASS(SoundEvents.BLOCK_NOTE_BLOCK_BASS.value()),
    SNARE(SoundEvents.BLOCK_NOTE_BLOCK_SNARE.value()),
    HAT(SoundEvents.BLOCK_NOTE_BLOCK_HAT.value()),
    BASEDRUM(SoundEvents.BLOCK_NOTE_BLOCK_BASEDRUM.value()),
    BELL(SoundEvents.BLOCK_NOTE_BLOCK_BELL.value()),
    FLUTE(SoundEvents.BLOCK_NOTE_BLOCK_FLUTE.value()),
    CHIME(SoundEvents.BLOCK_NOTE_BLOCK_CHIME.value()),
    GUITAR(SoundEvents.BLOCK_NOTE_BLOCK_GUITAR.value()),
    XYLOPHONE(SoundEvents.BLOCK_NOTE_BLOCK_XYLOPHONE.value()),
    IRON_XYLOPHONE(SoundEvents.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE.value()),
    COW_BELL(SoundEvents.BLOCK_NOTE_BLOCK_COW_BELL.value()),
    DIDGERIDOO(SoundEvents.BLOCK_NOTE_BLOCK_DIDGERIDOO.value()),
    BIT(SoundEvents.BLOCK_NOTE_BLOCK_BIT.value()),
    BANJO(SoundEvents.BLOCK_NOTE_BLOCK_BANJO.value()),
    PLING(SoundEvents.BLOCK_NOTE_BLOCK_PLING.value());

    private final SoundEvent soundKey;

    Instrument(SoundEvent soundKey) {
        this.soundKey = soundKey;
    }

    public SoundEvent getSoundKey() {
        return this.soundKey;
    }

    public static Instrument fromId(Identifier id) {
        for (Instrument instrument : values()) {
            if (Objects.equals(Registries.SOUND_EVENT.getId(instrument.getSoundKey()), id)) {
                return instrument;
            }
        }
        return null;
    }
}