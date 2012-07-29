package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.util.noise.NoiseGenerator;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.FoliageProvider.FloraType;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class GroundProvider_Nether extends GroundProvider_Normal {
	
	public final static byte stillLavaId = (byte) Material.STATIONARY_LAVA.getId();
	public final static byte netherrackId = (byte) Material.NETHERRACK.getId();
	
	public GroundProvider_Nether(WorldGenerator generator, Random random) {
		super(generator, random);
		
		fluidId = stillLavaId;
		surfaceId = netherrackId;
		subsurfaceId = netherrackId;
		substratumId = netherrackId;
	}

	@Override
	public void generateSurface(WorldGenerator generator, PlatLot lot, RealChunk chunk, int x, double perciseY, int z, boolean includeTrees) {
		int y = NoiseGenerator.floor(perciseY);
		
		// roll the dice
		double primary = random.nextDouble();
		double secondary = random.nextDouble();
		
		// top of the world?
		if (y >= generator.snowLevel) {
			if (random.nextDouble() < 0.20)
				setFire(chunk, x, y, z);
		
		// are on a plantable spot?
		} else if (generator.foliageProvider.isPlantable(generator, chunk, x, y, z)) {
			
			// regular trees, grass and flowers only
			if (y < generator.treeLevel) {

				// trees? but only if we are not too close to the edge
				if (includeTrees && primary > treeOdds && x > 0 && x < 15 && z > 0 && z < 15 && x % 2 == 0 && z % 2 != 0) {
					if (secondary > 0.90 && x > 5 && x < 11 && z > 5 && z < 11)
						generator.foliageProvider.generateTree(generator, chunk, x, y + 1, z, TreeType.BIG_TREE);
					else if (secondary > 0.50)
						generator.foliageProvider.generateTree(generator, chunk, x, y + 1, z, TreeType.BIRCH);
					else 
						generator.foliageProvider.generateTree(generator, chunk, x, y + 1, z, TreeType.TREE);
				
				// foliage?
				} else if (primary < foliageOdds) {
					
					// what to pepper about
					if (secondary > 0.90)
						generator.foliageProvider.generateFlora(generator, chunk, x, y + 1, z, FloraType.FLOWER_RED);
					else if (secondary > 0.80)
						generator.foliageProvider.generateFlora(generator, chunk, x, y + 1, z, FloraType.FLOWER_YELLOW);
					else 
						generator.foliageProvider.generateFlora(generator, chunk, x, y + 1, z, FloraType.GRASS);
				}
				
			// regular trees, grass and some evergreen trees... no flowers
			} else if (y < generator.evergreenLevel) {

				// trees? but only if we are not too close to the edge
				if (includeTrees && primary > treeOdds && x % 2 == 0 && z % 2 != 0) {
					
					// range change?
					if (secondary > ((double) (y - generator.treeLevel) / (double) generator.deciduousRange))
						generator.foliageProvider.generateTree(generator, chunk, x, y + 1, z, TreeType.TREE);
					else
						generator.foliageProvider.generateTree(generator, chunk, x, y + 1, z, TreeType.REDWOOD);
				
				// foliage?
				} else if (primary < foliageOdds) {

					// range change?
					if (secondary > ((double) (y - generator.treeLevel) / (double) generator.deciduousRange))
						generator.foliageProvider.generateFlora(generator, chunk, x, y + 1, z, FloraType.GRASS);
					else
						generator.foliageProvider.generateFlora(generator, chunk, x, y + 1, z, FloraType.FERN);
				}
				
			// evergreen and some grass and fallen snow, no regular trees or flowers
			} else if (y < generator.snowLevel) {
				
				// trees? but only if we are not too close to the edge
				if (includeTrees && primary > treeOdds && x % 2 == 0 && z % 2 != 0) {
					if (secondary > 0.50)
						generator.foliageProvider.generateTree(generator, chunk, x, y + 1, z, TreeType.REDWOOD);
					else
						generator.foliageProvider.generateTree(generator, chunk, x, y + 1, z, TreeType.TALL_REDWOOD);
				
				// foliage?
				} else if (primary < foliageOdds) {
					
					// range change?
					if (secondary > ((double) (y - generator.evergreenLevel) / (double) generator.evergreenRange)) {
						if (random.nextDouble() < 0.40)
							generator.foliageProvider.generateFlora(generator, chunk, x, y + 1, z, FloraType.FERN);
					} else {
						setFire(chunk, x, y, z);
					}
				}
			}
		
		// can't plant, maybe there is something else I can do
		} else {
			
			// regular trees, grass and flowers only
			if (y < generator.treeLevel) {

			// regular trees, grass and some evergreen trees... no flowers
			} else if (y < generator.evergreenLevel) {

			// evergreen and some grass and fallen snow, no regular trees or flowers
			} else if (y < generator.snowLevel) {
				
				if (primary < foliageOdds) {
					
					// range change?
					if (secondary > ((double) (y - generator.evergreenLevel) / (double) generator.evergreenRange)) {
						if (random.nextDouble() < 0.10 && generator.foliageProvider.isPlantable(generator, chunk, x, y, z))
							generator.foliageProvider.generateFlora(generator, chunk, x, y + 1, z, FloraType.FERN);
					} else {
						setFire(chunk, x, y, z);
					}
				}
			}
		}	
	}
	
	private void setFire(RealChunk chunk, int x, int y, int z) {
		y = chunk.findLastEmptyBelow(x, y + 1, z);
		if (chunk.isEmpty(x, y, z) && chunk.isType(x, y - 1, z, Material.NETHERRACK))
			chunk.setBlock(x, y, z, Material.FIRE);
	}
}
