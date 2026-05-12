package net.odinius.insanitymod.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.Vec3d;

public class DrunkEffect extends StatusEffect {

    public DrunkEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.isAlive() || entity.getHealth() <= 0) {
            return false;
        }

        Vec3d velocity = entity.getVelocity();

        double strength = 0.03D * (amplifier + 1);
        double wobbleX = (entity.getRandom().nextDouble() - 0.5D) * strength;
        double wobbleZ = (entity.getRandom().nextDouble() - 0.5D) * strength;

        entity.setVelocity(
                velocity.x + wobbleX,
                velocity.y,
                velocity.z + wobbleZ
        );

        if (entity.age % 40 == 0 && amplifier >= 3) {
            entity.damage(entity.getDamageSources().magic(), 1.0F);
        }

        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}