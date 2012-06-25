package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;

import org.bukkit.entity.EntityType;

public class SpawnProvider_Default extends SpawnProvider {

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	
	@Override
	public EntityType getEntity(WorldGenerator generator, String name) {
		//CityWorldSettings settings = generator.getSettings();
		Random random = generator.getStashRandomGenerator();

		if (name == spawnerInSewers) {
			switch (random.nextInt(4)) {
			case 1:
				return EntityType.CREEPER;
			case 2:
				return EntityType.SPIDER;
			case 3:
				return EntityType.SILVERFISH;
			default:
				return EntityType.ZOMBIE;
			}
		} else if (name == spawnerInMines) {
			switch (random.nextInt(4)) {
			case 1:
				return EntityType.SKELETON;
			case 2:
				return EntityType.CAVE_SPIDER;
			case 3:
				return EntityType.SILVERFISH;
			default:
				return EntityType.ZOMBIE;
			}
		} else if (name == spawnerInBunkers) {
			switch (random.nextInt(3)) {
			case 1:
				return EntityType.ENDERMAN;
			case 2:
				return EntityType.BLAZE;
			default:
				return EntityType.PIG_ZOMBIE;
			}
		} else
			return EntityType.ZOMBIE;
	}
}
