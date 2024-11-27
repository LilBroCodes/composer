package org.lilbrocodes.composer.advancement;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.lilbrocodes.composer.Composer;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * A utility class for managing advancements in a Fabric modding context.
 * Provides methods to grant advancements to players and register advancements
 * with custom criteria.
 */
public class AdvancementManager {

    /**
     * Grants an advancement to the specified player.
     *
     * @param player     The player to whom the advancement should be granted. Must be a server-side player.
     * @param identifier The {@link Identifier} of the advancement to be granted.
     * @throws NullPointerException if the player’s server or advancement loader is unavailable.
     */
    public static void grantAdvancement(ServerPlayerEntity player, Identifier identifier) {
        Advancement advancement = Objects.requireNonNull(player.getServer()).getAdvancementLoader().get(identifier);
        if (advancement != null) {
            AdvancementProgress progress = player.getAdvancementTracker().getProgress(advancement);
            if (!progress.isDone()) {
                progress.getUnobtainedCriteria().forEach(criterion ->
                        player.getAdvancementTracker().grantCriterion(advancement, criterion));
            }
        }
    }

    /**
     * Registers an advancement with a custom condition (predicate). This method
     * ensures that the registered advancement is granted to every player that meets
     * the condition defined in the provided {@link Predicate}.
     *
     * @param identifier The {@link Identifier} of the advancement to register.
     * @param predicate  A {@link Predicate} that defines the condition for granting
     *                   the advancement. The advancement will be granted to every
     *                   player for whom this condition evaluates to {@code true}.
     */
    public static void register(Identifier identifier, Predicate<PlayerEntity> predicate) {
        Composer.REGISTERED_ADVANCEMENTS.add(new PredicateAdvancement(identifier, predicate));
    }

}
