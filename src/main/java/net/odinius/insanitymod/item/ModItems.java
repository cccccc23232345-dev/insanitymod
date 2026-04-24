package net.odinius.insanitymod.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;
import net.odinius.insanitymod.InsanityMod;

public class ModItems {

    public static final FoodComponent ALCOHOL_COMPONENT = new FoodComponent.Builder()
            .nutrition(2)
            .saturationModifier(0.2f)
            .alwaysEdible()
            .build();

    public static final Item WHISKEY = registerItem("whiskey", createDrinkItem());
    public static final Item WINE = registerItem("wine", createDrinkItem());
    public static final Item CARLSBERG = registerItem("carlsberg", createDrinkItem());
    public static final Item HEINEKEN = registerItem("heineken", createDrinkItem());

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
        };
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(InsanityMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        InsanityMod.LOGGER.info("Registering Mod Items for " + InsanityMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.add(WHISKEY);
            entries.add(WINE);
            entries.add(CARLSBERG);
            entries.add(HEINEKEN);
        });
    }

}
