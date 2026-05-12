package net.odinius.insanitymod.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.odinius.insanitymod.InsanityMod;

public class ModItemGroups {

    public static final ItemGroup ALCOHOL_ITEM_GROUP = Registry.register(
            Registries.ITEM_GROUP,
            Identifier.of(InsanityMod.MOD_ID, "alcohol_items"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.WHISKEY))
                    .displayName(Text.translatable("itemgroup.insanitymod.alcohol_items"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.WHISKEY);
                        entries.add(ModItems.WINE);
                        entries.add(ModItems.CARLSBERG);
                        entries.add(ModItems.HEINEKEN);
                        entries.add(ModItems.ABSOLUTVODKA);
                    })
                    .build()
    );

    public static void registerItemGroups() {
        InsanityMod.LOGGER.info("Registering Item Groups for " + InsanityMod.MOD_ID);
    }
}