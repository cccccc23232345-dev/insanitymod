package net.odinius.insanitymod;

import net.fabricmc.api.ModInitializer;
import net.odinius.insanitymod.effect.ModEffects;
import net.odinius.insanitymod.item.ModItemGroups;
import net.odinius.insanitymod.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsanityMod implements ModInitializer {
	public static final String MOD_ID = "insanitymod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModEffects.registerEffects();
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
	}
}