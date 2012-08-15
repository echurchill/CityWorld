package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.util.noise.NoiseGenerator;

public class FoliageProvider_Floating extends FoliageProvider_Normal {

	public FoliageProvider_Floating(Random random) {
		super(random);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateSurface(WorldGenerator generator, PlatLot lot, RealChunk chunk, CachedYs blockYs, boolean includeTrees) {
		ShapeProvider shape = generator.shapeProvider;
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {
				generateSurfacePoint(generator, lot, chunk, x, shape.findGroundY(generator, chunk.getBlockX(x), chunk.getBlockZ(z)), z, includeTrees);
			}
		}
	}
	
	@Override
	public void generateSurfacePoint(WorldGenerator generator, PlatLot lot, RealChunk chunk, int x, double perciseY, int z, boolean includeTrees) {
		int y = NoiseGenerator.floor(perciseY);
		
		// roll the dice
		double primary = random.nextDouble();
		double secondary = random.nextDouble();
		
		// are on a plantable spot?
		if (isPlantable(generator, chunk, x, y, z)) {
			
			// trees? but only if we are not too close to the edge
			if (includeTrees && primary > treeOdds && x > 0 && x < 15 && z > 0 && z < 15 && x % 2 == 0 && z % 2 != 0) {
				if (secondary > 0.75)
					generateTree(generator, chunk, x, y + 1, z, TreeType.REDWOOD);
				else if (secondary > 0.50)
					generateTree(generator, chunk, x, y + 1, z, TreeType.BIRCH);
				else 
					generateTree(generator, chunk, x, y + 1, z, TreeType.TREE);
			
			// foliage?
			} else if (primary < foliageOdds) {
				
				// what to pepper about
				if (secondary > 0.90)
					generateFlora(generator, chunk, x, y + 1, z, FloraType.FLOWER_RED);
				else if (secondary > 0.80)
					generateFlora(generator, chunk, x, y + 1, z, FloraType.FLOWER_YELLOW);
				else 
					generateFlora(generator, chunk, x, y + 1, z, FloraType.GRASS);
			}
		}
	}
	
	@Override
	public boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, TreeType treeType) {
		if (likelyFlora(generator, random)) {
			int treeHeight;
			byte treeData;
			switch (treeType) {
			case BIRCH:
				treeHeight = 3;
				treeData = 2;
			case REDWOOD:
				treeHeight = 4;
				treeData = 1;
			default:
				treeHeight = 2;
				treeData = 0;
			}
			int trunkHeight = treeHeight - 1;
			
			chunk.setBlocks(x, y, y + treeHeight, z, Material.LOG, treeData);
			chunk.setBlock(x - 1, y + trunkHeight, z, Material.LEAVES, treeData);
			chunk.setBlock(x + 1, y + trunkHeight, z, Material.LEAVES, treeData);
			chunk.setBlock(x, y + trunkHeight, z - 1, Material.LEAVES, treeData);
			chunk.setBlock(x, y + trunkHeight, z + 1, Material.LEAVES, treeData);
			chunk.setBlock(x, y + treeHeight, z, Material.LEAVES, treeData);
			
			return true;
		} else
			return false;
	}
}
