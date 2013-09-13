package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;
import org.bukkit.util.noise.NoiseGenerator;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.FoliageProvider.HerbaceousType;
import me.daddychurchill.CityWorld.Plugins.FoliageProvider.LigneousType;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class SurfaceProvider_Normal extends SurfaceProvider {

	public SurfaceProvider_Normal(Odds odds) {
		super(odds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateSurfacePoint(WorldGenerator generator, PlatLot lot, RealChunk chunk, FoliageProvider foliage, 
			int x, double perciseY, int z, boolean includeTrees) {
		OreProvider ores = generator.oreProvider;
		int y = NoiseGenerator.floor(perciseY);
		
		// roll the dice
		double primary = odds.getRandomDouble();
		double secondary = odds.getRandomDouble();
		
		// top of the world?
		if (y >= generator.snowLevel) {
			ores.dropSnow(generator, chunk, x, y, z, (byte) NoiseGenerator.floor((perciseY - Math.floor(perciseY)) * 8.0));
		
		// are on a plantable spot?
		} else if (foliage.isPlantable(generator, chunk, x, y, z)) {
			
			// below sea level and plantable.. then cactus?
			if (y <= generator.seaLevel) {
				
				// trees? but only if we are not too close to the edge
				if (includeTrees) {
					if (generator.settings.includeAbovegroundFluids) {

					} else {
						if (primary < cactusOdds && x % 2 == 0 && z % 2 != 0) {
							if (chunk.isSurroundedByEmpty(x, y + 1, z))
								foliage.generateFlora(generator, chunk, x, y + 1, z, HerbaceousType.CACTUS);
						}
					}
				}
				
			// regular trees, grass and flowers only
			} else if (y < generator.treeLevel) {

				// trees? but only if we are not too close to the edge of the chunk
				if (includeTrees && primary < treeOdds && x > 0 && x < 15 && z > 0 && z < 15 && x % 2 == 0 && z % 2 != 0) {
					if (secondary < treeAltTallOdds && x > 5 && x < 11 && z > 5 && z < 11)
						foliage.generateTree(generator, chunk, x, y + 1, z, LigneousType.TALL_OAK);
					else if (secondary < treeAltOdds)
						foliage.generateTree(generator, chunk, x, y + 1, z, LigneousType.BIRCH);
					else 
						foliage.generateTree(generator, chunk, x, y + 1, z, LigneousType.OAK);
				
				// foliage?
				} else if (primary < foliageOdds) {
					
					// what to pepper about
					if (secondary < flowerRedOdds)
						foliage.generateFlora(generator, chunk, x, y + 1, z, HerbaceousType.FLOWER_RED);
					else if (secondary < flowerYellowOdds)
						foliage.generateFlora(generator, chunk, x, y + 1, z, HerbaceousType.FLOWER_YELLOW);
					else 
						foliage.generateFlora(generator, chunk, x, y + 1, z, HerbaceousType.GRASS);
				}
				
			// regular trees, grass and some evergreen trees... no flowers
			} else if (y < generator.evergreenLevel) {

				// trees? 
				if (includeTrees && primary < treeOdds && x % 2 == 0 && z % 2 != 0) {
					
					// range change?
					if (secondary > ((double) (y - generator.treeLevel) / (double) generator.deciduousRange))
						foliage.generateTree(generator, chunk, x, y + 1, z, LigneousType.OAK);
					else
						foliage.generateTree(generator, chunk, x, y + 1, z, LigneousType.PINE);
				
				// foliage?
				} else if (primary < foliageOdds) {

					// range change?
					if (secondary > ((double) (y - generator.treeLevel) / (double) generator.deciduousRange))
						foliage.generateFlora(generator, chunk, x, y + 1, z, HerbaceousType.GRASS);
					else
						foliage.generateFlora(generator, chunk, x, y + 1, z, HerbaceousType.FERN);
				}
				
			// evergreen and some grass and fallen snow, no regular trees or flowers
			} else if (y < generator.snowLevel) {
				
				// trees? 
				if (includeTrees && primary < treeOdds && x % 2 == 0 && z % 2 != 0) {
					if (secondary < treeTallOdds)
						foliage.generateTree(generator, chunk, x, y + 1, z, LigneousType.PINE);
					else
						foliage.generateTree(generator, chunk, x, y + 1, z, LigneousType.TALL_PINE);
				
				// foliage?
				} else if (primary < foliageOdds) {
					
					// range change?
					if (secondary > ((double) (y - generator.evergreenLevel) / (double) generator.evergreenRange)) {
						if (odds.playOdds(flowerFernOdds))
							foliage.generateFlora(generator, chunk, x, y + 1, z, HerbaceousType.FERN);
					} else {
						foliage.generateFlora(generator, chunk, x, y, z, HerbaceousType.COVER);
					}
				}
			}
		
		// can't plant, maybe there is something else I can do
		} else {
			
			// below sea level?
			if (y < generator.seaLevel) {
				
			// at sea level?	
			} else if (y == generator.seaLevel) {
				
				// trees? but only if we are not too close to the edge
				if (includeTrees) {
					if (generator.settings.includeAbovegroundFluids) {
						if (primary < reedOdds)
							if (chunk.isType(x, y, z, Material.SAND))
								if (chunk.isSurroundedByWater(x, y, z))
									foliage.generateFlora(generator, chunk, x, y + 1, z, HerbaceousType.REED);
					} else {
						
					}
				}
				
			// regular trees, grass and flowers only
			} else if (y < generator.treeLevel) {

			// regular trees, grass and some evergreen trees... no flowers
			} else if (y < generator.evergreenLevel) {

			// evergreen and some grass and fallen snow, no regular trees or flowers
			} else if (y < generator.snowLevel) {
				
				if (primary < foliageOdds) {
					
					// range change?
					if (secondary > ((double) (y - generator.evergreenLevel) / (double) generator.evergreenRange)) {
						if (odds.playOdds(0.10) && foliage.isPlantable(generator, chunk, x, y, z))
							foliage.generateFlora(generator, chunk, x, y + 1, z, HerbaceousType.FERN);
					} else {
						ores.dropSnow(generator, chunk, x, y, z);
					}
				}
			}
		}
	}
}
