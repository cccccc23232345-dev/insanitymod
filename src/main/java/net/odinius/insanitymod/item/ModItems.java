package net.odinius.insanitymod.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.odinius.insanitymod.InsanityMod;
import net.odinius.insanitymod.effect.ModEffects;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ModItems {

    public static final Map<UUID, Integer> drinkCounts = new HashMap<>();

    public static final int DRUNK_THRESHOLD = 2;
    public static final int DAMAGE_THRESHOLD = 3;

    public static final FoodComponent ALCOHOL_COMPONENT = new FoodComponent.Builder()
            .nutrition(2)
            .saturationModifier(0.2f)
            .alwaysEdible()
            .build();

    public static final Item WHISKEY = registerItem("whiskey", createDrinkItem());
    public static final Item WINE = registerItem("wine", createDrinkItem());
    public static final Item CARLSBERG = registerItem("carlsberg", createDrinkItem());
    public static final Item HEINEKEN = registerItem("heineken", createDrinkItem());
    public static final Item ABSOLUTVODKA = registerItem("absolutvodka", createDrinkItem());

    private static Item createDrinkItem() {
        return new Item(new Item.Settings().food(ALCOHOL_COMPONENT)) {

            @Override
            public UseAction getUseAction(ItemStack stack) {
                return UseAction.DRINK;
            }

            @Override
            public SoundEvent getDrinkSound() {
                return SoundEvents.ENTITY_GENERIC_DRINK;
            }

            @Override
            public SoundEvent getEatSound() {
                return SoundEvents.ENTITY_GENERIC_DRINK;
            }

            @Override
            public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
                ItemStack result = super.finishUsing(stack, world, user);

                if (!world.isClient() && user instanceof PlayerEntity player) {
                    UUID id = player.getUuid();
                    int count = drinkCounts.getOrDefault(id, 0) + 1;
                    drinkCounts.put(id, count);

                    if (count >= DRUNK_THRESHOLD) {
                        int amplifier = Math.min(count - DRUNK_THRESHOLD, 4);

                        player.addStatusEffect(new StatusEffectInstance(
                                Registries.STATUS_EFFECT.getEntry(ModEffects.DRUNK), 20 * 30, amplifier));
                        player.addStatusEffect(new StatusEffectInstance(
                                StatusEffects.NAUSEA, 20 * 20, amplifier));
                        player.addStatusEffect(new StatusEffectInstance(
                                StatusEffects.SLOWNESS, 20 * 20, amplifier));
                        player.addStatusEffect(new StatusEffectInstance(
                                StatusEffects.WEAKNESS, 20 * 20, amplifier));
                    }
                }

                return result;
            }
        };
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(
                Registries.ITEM,
                Identifier.of(InsanityMod.MOD_ID, name),
                item
        );
    }

    public static void registerModItems() {
        InsanityMod.LOGGER.info("Registering Mod Items for " + InsanityMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.add(WHISKEY);
            entries.add(WINE);
            entries.add(CARLSBERG);
            entries.add(HEINEKEN);
            entries.add(ABSOLUTVODKA);
        });
    }
}