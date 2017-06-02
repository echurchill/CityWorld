package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.util.noise.NoiseGenerator;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.CoverProvider.CoverageType;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

public class SurfaceProvider_Flooded extends SurfaceProvider_Normal {

	public SurfaceProvider_Flooded(Odds odds) {
		super(odds);
		// TODO Auto-generated constructor stub
	}
	
	private static int shoreRange = 4;

	@Override
	public void generateSurfacePoint(CityWorldGenerator generator, PlatLot lot, SupportBlocks chunk,
			CoverProvider foliage, int x, double perciseY, int z, boolean includeTrees) {
		int y = NoiseGenerator.floor(perciseY);
		int floodY = NoiseGenerator.floor(generator.shapeProvider.findFloodY(generator, chunk.getBlockX(x), chunk.getBlockZ(z)));
		
		// high enough for normal
		if (y >= floodY - 1) {
			generateNormalPoint(generator, lot, chunk, foliage, x, perciseY, z, includeTrees);

		// just a bit below normal
		} else if (y >= floodY - shoreRange) {
			generateShorePoint(generator, lot, chunk, foliage, x, y, z, includeTrees);
			
		// out in the dunes?
		} else {
			generateFloodedPoint(generator, lot, chunk, foliage, x, y, z, floodY);
		}
	}
	
	protected void generateNormalPoint(CityWorldGenerator generator, PlatLot lot, SupportBlocks chunk,
			CoverProvider foliage, int x, double perciseY, int z, boolean includeTrees) {
		super.generateSurfacePoint(generator, lot, chunk, foliage, x, perciseY, z, includeTrees);
	}
	
	protected void generateShorePoint(CityWorldGenerator generator, PlatLot lot, SupportBlocks chunk,
			CoverProvider foliage, int x, int y, int z, boolean includeTrees) {
		// roll the dice
		double primary = odds.getRandomDouble();
		double secondary = odds.getRandomDouble();
		
		// trees? 
		if (includeTrees && primary < treeOdds && inTreeRange(x, z)) {
			if (secondary < treeAltTallOdds)
				generator.coverProvider.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.OAK_TRUNK);
			else if (secondary < treeAltOdds)
				generator.coverProvider.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.BIRCH_TRUNK);
			else 
				generator.coverProvider.generateCoverage(generator, chunk, x, y + 1, z, CoverageType.PINE_TRUNK);
		}
	}
	
	protected void generateFloodedPoint(CityWorldGenerator generator, PlatLot lot, SupportBlocks chunk, 
			CoverProvider foliage, int x, int y, int z, int floodY) {
		
		if (odds.playOdds(vagrantOdds)) {
			generator.spawnProvider.spawnSeaAnimals(generator, chunk, odds, x, floodY, z);
		}
	}
}
