package net.odinius.insanitymod.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.odinius.insanitymod.InsanityMod;

public class ModEffects {

    public static final StatusEffect DRUNK = Registry.register(
            Registries.STATUS_EFFECT,
            Identifier.of(InsanityMod.MOD_ID, "drunk"),
            new DrunkEffect(StatusEffectCategory.HARMFUL, 0x556B2F)
    );

    public static void registerEffects() {
        InsanityMod.LOGGER.info("Registering Effects for " + InsanityMod.MOD_ID);
    }
}