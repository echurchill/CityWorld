package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.inventory.ItemStack;

import me.daddychurchill.CityWorld.WorldGenerator;

public abstract class LootProvider {

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	
	public final static String chestInSewers = "CityWorld_Chest_Sewer";
	public final static String chestInMines = "CityWorld_Chest_Mine";
	public final static String chestInBunkers = "CityWorld_Chest_Bunker";
	
	protected LootProvider() {
		// who's your daddy?
	}
	
	public abstract ItemStack[] getItems(WorldGenerator generator, String name);

	public static LootProvider loadProvider() {

		LootProvider provider = null;
		
//		// try PhatLoots
//		provider = LootProvider_PhatLoots.loadPhatLoots();
		
		// default to stock LootProvider
		if (provider == null) {
			provider = new LootProvider_Default();
		}
	
		return provider;
	}
}
