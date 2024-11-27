package org.lilbrocodes.composer.advancement;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.function.Predicate;

public record PredicateAdvancement(Identifier identifier, Predicate<PlayerEntity> predicate) {}
