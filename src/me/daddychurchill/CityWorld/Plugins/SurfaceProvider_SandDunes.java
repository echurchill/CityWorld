package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.util.noise.NoiseGenerator;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.CoverProvider.CoverageType;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class SurfaceProvider_SandDunes extends SurfaceProvider_Normal {

	public SurfaceProvider_SandDunes(Odds odds) {
		super(odds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateSurfacePoint(CityWorldGenerator generator, PlatLot lot, SupportChunk chunk, CoverProvider foliage, 
			int x, double perciseY, int z, boolean includeTrees) {
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
				
			// else
			} else if (y < generator.treeLevel) {
				if (primary < foliageOdds) {
					
					// what to pepper about
					if (secondary < flowerRedOdds)
						foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.DEAD_BUSH);
					else 
						foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.DEAD_GRASS);
					
				} else if (primary < (cactusOdds / 2) && x % 2 == 0 && z % 2 != 0) {
					if (chunk.isSurroundedByEmpty(x, y + 1, z))
						foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.CACTUS);
				}
			}
		}
	}
}
