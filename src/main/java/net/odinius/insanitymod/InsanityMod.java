package net.odinius.insanitymod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.odinius.insanitymod.item.ModItemGroups;
import net.odinius.insanitymod.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Very important comment
public class InsanityMod implements ModInitializer {
	public static final String MOD_ID = "insanitymod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();

	ModItems.registerModItems();

	}
}