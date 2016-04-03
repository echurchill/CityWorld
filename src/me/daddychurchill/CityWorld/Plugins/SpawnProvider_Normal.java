package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;

import org.bukkit.entity.EntityType;

public class SpawnProvider_Normal extends SpawnProvider {

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	
	@Override
	public EntityType getEntity(CityWorldGenerator generator, Odds odds, SpawnerLocation location) {

		switch(location) {
		case SEWER:
		default: 
			switch (odds.getRandomInt(4)) {
			case 0:
			default:
				return EntityType.ZOMBIE;
			case 1:
				return EntityType.CREEPER;
			case 2:
				return EntityType.SPIDER;
			case 3:
				return EntityType.BAT;
			}
		case BUNKER:
			switch (odds.getRandomInt(3)) {
			case 0:
			default:
				return EntityType.PIG_ZOMBIE;
			case 1:
				return EntityType.ENDERMAN;
			case 2:
				return EntityType.BLAZE;
			}
		case MINE:
			switch (odds.getRandomInt(4)) {
			case 0:
			default:
				return EntityType.ZOMBIE;
			case 1:
				return EntityType.SKELETON;
			case 2:
				return EntityType.CAVE_SPIDER;
			case 3:
				return EntityType.BAT;
			}
		case HOUSE:
			switch (odds.getRandomInt(2)) {
			case 0:
			default:
				return EntityType.VILLAGER;
			case 1:
				return EntityType.WITCH;
			}
		case WATERPIT:
			switch (odds.getRandomInt(3)) {
			case 0:
			default:
//				return EntityType.SQUID;
			case 1:
				return EntityType.GUARDIAN;
			}
		case LAVAPIT:
			switch (odds.getRandomInt(2)) {
			case 0:
			default:
				return EntityType.BLAZE;
			case 1:
				return EntityType.SHULKER;
			}
		}
	}
}
