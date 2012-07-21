package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import org.bukkit.inventory.ItemStack;

import me.daddychurchill.CityWorld.WorldGenerator;

public abstract class LootProvider {

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	
	public enum LootLocation {SEWER, MINE, BUNKER};
	
	public abstract ItemStack[] getItems(WorldGenerator generator, Random random, LootLocation lootLocation);

	public static LootProvider loadProvider(WorldGenerator generator) {

		LootProvider provider = null;
		
		// try PhatLoots...
		provider = LootProvider_Phat.loadPhatLoots();
		
		// default to stock LootProvider
		if (provider == null) {
			provider = new LootProvider_Default();
		}
	
		return provider;
	}
}
