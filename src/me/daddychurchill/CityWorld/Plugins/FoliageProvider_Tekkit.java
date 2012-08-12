package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.TekkitMaterial;

import org.bukkit.Material;
import org.bukkit.TreeType;

public class FoliageProvider_Tekkit extends FoliageProvider_Normal {

	public FoliageProvider_Tekkit(Random random) {
		super(random);
	}
	
	@Override
	public boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, TreeType treeType) {
		if (treeType == TreeType.TREE && random.nextDouble() < 0.01) {
			//TODO what is a rubber tree made of? 
			//return generateTree(chunk, random, x, y, z, treeType, trunkId, leavesId1, leavesId2);
			chunk.setBlock(x, y - 1, z, Material.GRASS);
			chunk.setBlock(x, y, z, TekkitMaterial.RUBBER_SAPLING, (byte) 0, true);
			return true;
		} else
			return generateTree(chunk, random, x, y, z, treeType, logId, leavesId, leavesId);
	}

	@Override
	public boolean generateFlora(WorldGenerator generator, RealChunk chunk, int x, int y, int z, FloraType floraType) {
		if (likelyFlora(generator, random)) {
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
				chunk.setBlocks(x, y, y + 3, z, Material.CACTUS);
				break;
			}
			return true;
		}
		return false;
	}
}
