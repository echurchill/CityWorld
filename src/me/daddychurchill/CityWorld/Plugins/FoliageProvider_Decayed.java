package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;

public class FoliageProvider_Decayed extends FoliageProvider {
	
	public FoliageProvider_Decayed(Random random) {
		super(random);
	}
	
	@Override
	public boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, LigneousType ligneousType) {
		if (likelyFlora(generator, random)) {
			return generateTree(chunk, random, x, y, z, ligneousType, log, Material.AIR, Material.AIR);
		} else
			return false;
	}

	@Override
	public boolean generateFlora(WorldGenerator generator, RealChunk chunk, int x, int y, int z, HerbaceousType herbaceousType) {
		if (likelyFlora(generator, random)) {
			
			// no flowers in a decayed world
			switch (herbaceousType) {
			case GRASS:
			case FERN:
				chunk.setBlock(x, y, z, Material.DEAD_BUSH);
				return true;
			case COVER:
			case CACTUS:
			case FLOWER_RED:
			case FLOWER_YELLOW:
			default:
				break;
			}
		}
		return false;
	}
	
	@Override
	public boolean isPlantable(WorldGenerator generator, RealChunk chunk, int x, int y, int z) {
		
		// only if the spot above is empty
		if (!chunk.isEmpty(x, y + 1, z))
			return false;
		
		// depends on the block's type and what the world is like
		return !chunk.isEmpty(x, y, z);
	}
}
