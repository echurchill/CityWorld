package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;

import org.bukkit.entity.EntityType;

public abstract class SpawnProvider {

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	
	public final static String spawnerInSewers = "CityWorld_Spawner_Sewer";
	public final static String spawnerInMines = "CityWorld_Spawner_Mine";
	public final static String spawnerInBunkers = "CityWorld_Spawner_Bunker";
	
	protected SpawnProvider() {
		// who's your daddy?
	}
	
	public abstract EntityType getEntity(WorldGenerator generator, String name);

	public static SpawnProvider loadProvider(WorldGenerator generator) {

		SpawnProvider provider = null;
		
//		// try PhatLoots
//		provider = LootProvider_PhatLoots.loadPhatLoots();
		
		// default to stock SpawnProvider
		if (provider == null) {
			provider = new SpawnProvider_Default();
		}
	
		return provider;
	}
}
