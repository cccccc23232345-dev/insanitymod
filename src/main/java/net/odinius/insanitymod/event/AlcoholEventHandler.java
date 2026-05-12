package net.odinius.insanitymod.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.odinius.insanitymod.effect.ModEffects;
import net.odinius.insanitymod.item.ModItems;

import java.util.UUID;

public class AlcoholEventHandler {

    public static void register() {

        // Reset drink count AND remove the drunk effect on respawn
        ServerPlayerEvents.AFTER_RESPAWN.register((ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) -> {
            UUID id = newPlayer.getUuid();
            ModItems.drinkCounts.put(id, 0);
            newPlayer.removeStatusEffect(Registries.STATUS_EFFECT.getEntry(ModEffects.DRUNK));
            newPlayer.removeStatusEffect(net.minecraft.entity.effect.StatusEffects.NAUSEA);
            newPlayer.removeStatusEffect(net.minecraft.entity.effect.StatusEffects.SLOWNESS);
            newPlayer.removeStatusEffect(net.minecraft.entity.effect.StatusEffects.WEAKNESS);
        });

        // Also reset on death just to be safe
        ServerLivingEntityEvents.AFTER_DEATH.register((LivingEntity entity, DamageSource source) -> {
            if (entity instanceof PlayerEntity player) {
                ModItems.drinkCounts.put(player.getUuid(), 0);
            }
        });

        ServerTickEvents.END_SERVER_TICK.register((MinecraftServer server) -> {
            for (ServerWorld world : server.getWorlds()) {
                for (PlayerEntity player : world.getPlayers()) {
                    UUID id = player.getUuid();
                    int count = ModItems.drinkCounts.getOrDefault(id, 0);

                    StatusEffectInstance drunkInstance = player.getStatusEffect(
                            Registries.STATUS_EFFECT.getEntry(ModEffects.DRUNK));

                    boolean isDrunk = drunkInstance != null;

                    if (!isDrunk && count > 0) {
                        ModItems.drinkCounts.put(id, 0);
                        continue;
                    }

                    if (isDrunk && count >= ModItems.DAMAGE_THRESHOLD && server.getTicks() % 40 == 0) {
                        float damage = (float) Math.pow(3, count - ModItems.DAMAGE_THRESHOLD);
                        player.damage(world.getDamageSources().generic(), damage);
                    }
                }
            }
        });
    }
}