package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;

import org.bukkit.entity.EntityType;

public abstract class SpawnProvider extends Provider {

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	
	public enum SpawnerLocation {SEWER, MINE, BUNKER, HOUSE};
	
	public abstract EntityType getEntity(WorldGenerator generator, Odds odds, SpawnerLocation location);

	public static SpawnProvider loadProvider(WorldGenerator generator) {

		SpawnProvider provider = null;
		
//		We need something like PhatLoot but for spawners
//		provider = SpawnProvider_PhatSpawn.loadPhatSpawn();
		
		// default to stock SpawnProvider
		if (provider == null) {
			provider = new SpawnProvider_Normal();
		}
	
		return provider;
	}
}
