package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.TreeType;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class FoliageProvider_Default extends FoliageProvider {

	public FoliageProvider_Default(Random random) {
		super(random);
	}
	
	@Override
	public boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, TreeType treeType) {
		return generateTree(chunk, random, x, y, z, treeType, logId, leavesId, leavesId);
	}

	@Override
	public boolean generateFlora(WorldGenerator generator, RealChunk chunk, int x, int y, int z, FloraType floraType) {
		switch (floraType) {
		case FLOWER_RED:
			chunk.setBlock(x, y, z, Material.RED_ROSE);
			break;
		case FLOWER_YELLOW:
			chunk.setBlock(x, y, z, Material.YELLOW_FLOWER);
			break;
		case GRASS:
			chunk.setBlock(x, y, z, Material.LONG_GRASS, (byte) 1);
			break;
		case FERN:
			chunk.setBlock(x, y, z, Material.LONG_GRASS, (byte) 2);
			break;
		case CACTUS:
			chunk.setBlock(x, y - 1, z, Material.SAND);
			chunk.setBlocks(x, y, y + random.nextInt(3) + 2, z, Material.CACTUS);
			break;
		}
		return true;
	}
}
