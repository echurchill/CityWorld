package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

public abstract class SurfaceProvider extends Provider {

	public SurfaceProvider(Odds odds) {
		super();
		this.odds = odds;
	}

	protected final static double treeOdds = Odds.oddsVeryUnlikely;
	protected final static double treeTallOdds = Odds.oddsLikely;
	protected final static double treeAltOdds = Odds.oddsLikely;
	protected final static double treeAltTallOdds = Odds.oddsVeryUnlikely;
	protected final static double foliageOdds = Odds.oddsVeryUnlikely;
	protected final static double cactusOdds = Odds.oddsUnlikely;
	protected final static double reedOdds = Odds.oddsPrettyUnlikely;
	protected final static double flowerRedOdds = Odds.oddsVeryUnlikely;
	protected final static double flowerYellowOdds = Odds.oddsExtremelyUnlikely;
	protected final static double flowerFernOdds = Odds.oddsSomewhatLikely;
	
	protected final static double vagrantOdds = Odds.oddsTremendouslyUnlikely;
	
	protected Odds odds;
	
	public abstract void generateSurfacePoint(CityWorldGenerator generator, PlatLot lot, SupportBlocks chunk, CoverProvider foliage, 
			int x, double perciseY, int z, boolean includeTrees);
	
	public void generateSurface(CityWorldGenerator generator, PlatLot lot, SupportBlocks chunk, CachedYs blockYs, boolean includeTrees) {
		generateSurface(generator, lot, chunk, blockYs, 0, includeTrees);
	}
	
	protected boolean inTreeRange(int x, int z) {
		return x > 2 && x < 15 && z > 2 && z < 15;// && x % 2 == 0 && z % 2 != 0;
	}
	
	protected boolean inBigTreeRange(int x, int z) {
		return x > 4 && x < 11 && z > 4 && z < 11 && x % 2 == 0 && z % 2 != 0;
	}
	
	public void generateSurface(CityWorldGenerator generator, PlatLot lot, SupportBlocks chunk, CachedYs blockYs, int addTo, boolean includeTrees) {
		CoverProvider foliage = generator.coverProvider;
		int topY = lot.getTopY(generator);
			
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {
				double y = blockYs.getPerciseY(x, z) + addTo;
//				if (x == 0 && z == 0) {
//					int inty = NoiseGenerator.floor(y);
//					chunk.setBlock(x, inty + 9, z, Material.STONE);
//					chunk.setBlock(x + 1, inty + 9, z, Material.STONE);
//					chunk.setBlock(x, inty + 9, z + 1, Material.STONE);
//					chunk.setSignPost(x, inty + 10, z, BlockFace.SOUTH, "topY = " + topY, "inty = " + inty);
//				} else
				if (topY < y)
					generateSurfacePoint(generator, lot, chunk, foliage, x, y, z, includeTrees);
			}
		}
	}
	
	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	public static SurfaceProvider loadProvider(CityWorldGenerator generator, Odds odds) {
		
		SurfaceProvider provider = null;

		switch (generator.worldStyle) {
		case FLOATING:
			provider = new SurfaceProvider_Floating(odds);
			break;
		case FLOODED:
			provider = new SurfaceProvider_Flooded(odds);
			break;
		case SANDDUNES:
			provider = new SurfaceProvider_SandDunes(odds);
			break;
		case SNOWDUNES:
			provider = new SurfaceProvider_SnowDunes(odds);
			break;
		case ASTRAL:
			provider = new SurfaceProvider_Astral(odds);
			break;
		case MAZE:
			provider = new SurfaceProvider_Maze(odds);
			break;
		case NATURE:
		case METRO:
		case DESTROYED:
		case NORMAL:
			provider = new SurfaceProvider_Normal(odds);
			break;
		}
		
		return provider;
	}
	
}
