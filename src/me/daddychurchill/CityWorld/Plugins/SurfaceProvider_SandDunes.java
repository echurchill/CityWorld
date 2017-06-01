package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.entity.EntityType;
import org.bukkit.util.noise.NoiseGenerator;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.CoverProvider.CoverageType;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

public class SurfaceProvider_SandDunes extends SurfaceProvider_Flooded {

	public SurfaceProvider_SandDunes(Odds odds) {
		super(odds);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void generateNormalPoint(CityWorldGenerator generator, PlatLot lot, SupportBlocks chunk,
			CoverProvider foliage, int x, double perciseY, int z, boolean includeTrees) {
		int y = NoiseGenerator.floor(perciseY);
		
		// roll the dice
		double primary = odds.getRandomDouble();
		double secondary = odds.getRandomDouble();
		
		// are on a plantable spot?
		if (foliage.isPlantable(generator, chunk, x, y, z)) {

			// below sea level and plantable.. then cactus?
			if (y <= generator.seaLevel) {
				
				// trees? but only if we are not too close to the edge
				if (includeTrees) {
					if (generator.settings.includeAbovegroundFluids) {

					} else {
						if (primary < cactusOdds && x % 2 == 0 && z % 2 != 0) {
							if (chunk.isSurroundedByEmpty(x, y + 1, z))
								foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.CACTUS);
						}
					}
				}
				
			// regular trees, grass and flowers only
			} else if (y < generator.treeLevel) {

				// trees? but only if we are not too close to the edge of the chunk
				if (includeTrees && primary < treeOdds && inTreeRange(x, z)) {
					if (secondary < treeAltTallOdds)
						foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.TALL_OAK_TRUNK);
					else if (secondary < treeAltOdds)
						foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.BIRCH_TRUNK);
					else 
						foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.OAK_TRUNK);
				
				// foliage?
				} else if (primary < foliageOdds / 3) {
					
					// what to pepper about
					foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.DEAD_GRASS);
				}
				
			// regular trees, grass and some evergreen trees... no flowers
			} else if (y < generator.evergreenLevel) {

				// trees? 
				if (includeTrees && primary < treeOdds && inTreeRange(x, z)) {
					
					// range change?
					if (secondary > ((double) (y - generator.treeLevel) / (double) generator.deciduousRange))
						foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.OAK_TRUNK);
					else
						foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.PINE_TRUNK);
				
				}
				
			// evergreen and some grass and fallen snow, no regular trees or flowers
			} else if (y < generator.snowLevel) {
				
				// trees? 
				if (includeTrees && primary < treeOdds && x % 2 == 0 && z % 2 != 0) {
					if (secondary < treeTallOdds)
						foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.PINE_TRUNK);
					else
						foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.TALL_PINE_TRUNK);
				
				}
			}
		}
	}
	
	@Override
	protected void generateShorePoint(CityWorldGenerator generator, PlatLot lot, SupportBlocks chunk,
			CoverProvider foliage, int x, int y, int z, boolean includeTrees) {
		
		// this will do just fine
		super.generateShorePoint(generator, lot, chunk, foliage, x, y, z, includeTrees);
	}
	
	@Override
	protected void generateFloodedPoint(CityWorldGenerator generator, PlatLot lot, SupportBlocks chunk, 
			CoverProvider foliage, int x, int y, int z, int floodY) {

		// add plants out there?
		if (odds.playOdds(foliageOdds)) {
			if (odds.playOdds(cactusOdds)) {
				if (x % 2 == 0 && z % 2 != 0 && chunk.isSurroundedByEmpty(x, floodY + 1, z)) {
					foliage.generateCoverage(generator, chunk, x, floodY, z, CoverageType.CACTUS);
				}
			} else {
				foliage.generateCoverage(generator, chunk, x, floodY, z, CoverageType.DEAD_BUSH);
			}
		} else if (odds.playOdds(vagrantOdds)) {
			generator.spawnProvider.spawnVagrant(generator, chunk, odds, x, floodY, z, EntityType.WOLF, EntityType.SKELETON_HORSE);
		}
		
	}
}
