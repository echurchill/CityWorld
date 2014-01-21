package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.util.noise.NoiseGenerator;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.CoverProvider.CoverageType;
import me.daddychurchill.CityWorld.Plugins.CoverProvider.LigneousType;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class SurfaceProvider_Floating extends SurfaceProvider {

	public SurfaceProvider_Floating(Odds odds) {
		super(odds);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void generateSurface(WorldGenerator generator, PlatLot lot, RealChunk chunk, CachedYs blockYs, boolean includeTrees) {
		if (generator.settings.includeFloatingSubsurface) {
			ShapeProvider shape = generator.shapeProvider;
			CoverProvider foliage = generator.coverProvider;
			for (int x = 0; x < chunk.width; x++) {
				for (int z = 0; z < chunk.width; z++) {
					generateSurfacePoint(generator, lot, chunk, foliage, x, shape.findGroundY(generator, chunk.getBlockX(x), chunk.getBlockZ(z)), z, includeTrees);
				}
			}
		}
	}

	private final static double treeOdds = DataContext.oddsUnlikely;
	private final static double treePineOdds = DataContext.oddsLikely;
	private final static double treeBirchOdds = DataContext.oddsPrettyUnlikely;
	
	@Override
	public void generateSurfacePoint(WorldGenerator generator, PlatLot lot, RealChunk chunk, CoverProvider foliage, int x, double perciseY, int z, boolean includeTrees) {
		int y = NoiseGenerator.floor(perciseY);
		
		// roll the dice
		double primary = odds.getRandomDouble();
		double secondary = odds.getRandomDouble();
		
		// are on a plantable spot?
		if (foliage.isPlantable(generator, chunk, x, y, z)) {
			
			// trees? but only if we are not too close to the edge
			if (includeTrees && primary < treeOdds && x > 0 && x < 15 && z > 0 && z < 15 && x % 2 == 0 && z % 2 != 0) {
				if (secondary < treePineOdds)
					foliage.generateTree(generator, chunk, x, y + 1, z, LigneousType.MINI_PINE);
				else if (secondary < treeBirchOdds)
					foliage.generateTree(generator, chunk, x, y + 1, z, LigneousType.MINI_BIRCH);
				else 
					foliage.generateTree(generator, chunk, x, y + 1, z, LigneousType.MINI_OAK);
			
			// foliage?
			} else if (primary < foliageOdds && y <= ShapeProvider_Floating.snowPoint) {
				
				// what to pepper about
				if (secondary < flowerRedOdds)
					foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.POPPY);
				else if (secondary < flowerYellowOdds)
					foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.DANDELION);
				else 
					foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.GRASS);
			}
		}
		
		// snow?
		if (y > ShapeProvider_Floating.snowPoint)
			generator.oreProvider.dropSnow(generator, chunk, x, y + 5, z);
	}

}
