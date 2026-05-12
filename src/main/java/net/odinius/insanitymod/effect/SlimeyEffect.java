package net.odinius.insanitymod.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.Vec3d;

public class SlimeyEffect extends StatusEffect {

    public SlimeyEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        Vec3d velocity = entity.getVelocity();

        double wobbleX = (entity.getRandom().nextDouble() - 0.5D) * 0.2D * (amplifier + 1);
        double wobbleZ = (entity.getRandom().nextDouble() - 0.5D) * 0.2D * (amplifier + 1);
        double wobbleY = (entity.isOnGround() ? 0 : (entity.getRandom().nextDouble() - 0.5D) * 0.05D);

        entity.setVelocity(
                velocity.x + wobbleX,
                velocity.y + wobbleY,
                velocity.z + wobbleZ
        );

        entity.velocityModified = true;
        return false;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}