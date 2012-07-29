package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;

import org.bukkit.entity.EntityType;

public abstract class SpawnProvider {

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	
	public enum SpawnerLocation {SEWER, MINE, BUNKER};
	
	public abstract EntityType getEntity(WorldGenerator generator, Random random, SpawnerLocation location);

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
