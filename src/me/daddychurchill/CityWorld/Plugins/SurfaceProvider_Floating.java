package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.util.noise.NoiseGenerator;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.CoverProvider.CoverageSets;
import me.daddychurchill.CityWorld.Plugins.CoverProvider.CoverageType;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

public class SurfaceProvider_Floating extends SurfaceProvider {

	public enum SubSurfaceStyle {NONE, LAND, CLOUD, LAVA};

	public static SubSurfaceStyle toSubSurfaceStyle(String value, SubSurfaceStyle defaultValue) {
		try {
			return SubSurfaceStyle.valueOf(value);
		} catch(Exception e) {
			return defaultValue;
		}
	}
	
	public SurfaceProvider_Floating(Odds odds) {
		super(odds);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void generateSurface(CityWorldGenerator generator, PlatLot lot, SupportBlocks chunk, CachedYs blockYs, boolean includeTrees) {
		if (generator.settings.subSurfaceStyle == SubSurfaceStyle.LAND) {
			ShapeProvider shape = generator.shapeProvider;
			CoverProvider foliage = generator.coverProvider;
			int iX = odds.calcRandomRange(1, 3);
			int iZ = odds.calcRandomRange(1, 3);
			int n = 0;
			for (int x = 0; x < chunk.width; x = x + iX) {
				for (int z = 0; z < chunk.width; z = x + iZ) {
					int y = shape.findGroundY(generator, chunk.getBlockX(x), chunk.getBlockZ(z));
					if (chunk.isEmpty(x, y, z) && !chunk.isEmpty(x, y - 1, z)) {
						generateSurfacePoint(generator, lot, chunk, foliage, x, y, z, includeTrees);
					} else
						n++;
				}
			}
			if (n > 0)
				generator.reportMessage("Found " + n + " place we can't build");
		}
	}

	private final static double treeOdds = Odds.oddsUnlikely;
	private final static double treePineOdds = Odds.oddsLikely;
	private final static double treeBirchOdds = Odds.oddsVeryUnlikely;
	
	@Override
	public void generateSurfacePoint(CityWorldGenerator generator, PlatLot lot, SupportBlocks chunk, CoverProvider foliage, int x, double perciseY, int z, boolean includeTrees) {
		int y = NoiseGenerator.floor(perciseY);
		
		// roll the dice
		double primary = odds.getRandomDouble();
		double secondary = odds.getRandomDouble();
		
		// are on a plantable spot?
		if (foliage.isPlantable(generator, chunk, x, y, z)) {
			
			// trees? but only if we are not too close to the edge
			if (includeTrees && primary < treeOdds && inTreeRange(x, z)) {
				if (secondary < treePineOdds)
					foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.MINI_PINE_TREE);
				else if (secondary < treeBirchOdds)
					foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.MINI_BIRCH_TREE);
				else 
					foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.MINI_OAK_TREE);
			
			// foliage?
			} else if (primary < foliageOdds && y <= ShapeProvider_Floating.snowPoint) {
				
				// what to pepper about
				foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageSets.PRARIE_PLANTS);
//				if (secondary < flowerRedOdds)
//					foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.POPPY);
//				else if (secondary < flowerYellowOdds)
//					foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.DANDELION);
//				else 
//					foliage.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.GRASS);
			}
		}
		
		// snow?
		if (y > ShapeProvider_Floating.snowPoint)
			generator.oreProvider.dropSnow(generator, chunk, x, y + 5, z);
	}

}
