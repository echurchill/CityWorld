package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;

import org.bukkit.entity.EntityType;

public class SpawnProvider_Normal extends SpawnProvider {

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	
	@Override
	public EntityType getEntity(WorldGenerator generator, Odds odds, SpawnerLocation location) {

		switch(location) {
		case BUNKER:
			switch (odds.getRandomInt(3)) {
			case 1:
				return EntityType.ENDERMAN;
			case 2:
				return EntityType.BLAZE;
			default:
				return EntityType.PIG_ZOMBIE;
			}
		case MINE:
			switch (odds.getRandomInt(3)) {
			case 1:
				return EntityType.SKELETON;
			case 2:
				return EntityType.CAVE_SPIDER;
			default:
				return EntityType.ZOMBIE;
			}
		default: //case SEWER:
			switch (odds.getRandomInt(3)) {
			case 1:
				return EntityType.CREEPER;
			case 2:
				return EntityType.SPIDER;
			default:
				return EntityType.ZOMBIE;
			}
		}
	}
}
