package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.AbstractCachedYs;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

public abstract class SurfaceProvider extends Provider {

	SurfaceProvider(Odds odds) {
		super();
		this.odds = odds;
	}

	final static double treeOdds = Odds.oddsVeryUnlikely;
	final static double treeTallOdds = Odds.oddsLikely;
	final static double treeAltOdds = Odds.oddsLikely;
	final static double treeAltTallOdds = Odds.oddsVeryUnlikely;
	final static double foliageOdds = Odds.oddsSomewhatLikely;
	final static double cactusOdds = Odds.oddsUnlikely;
	final static double reedOdds = Odds.oddsPrettyUnlikely;
	protected final static double flowerRedOdds = Odds.oddsVeryUnlikely;
	protected final static double flowerYellowOdds = Odds.oddsExtremelyUnlikely;
	final static double flowerFernOdds = Odds.oddsSomewhatLikely;

	final static double vagrantOdds = Odds.oddsTremendouslyUnlikely;

	final Odds odds;

	protected abstract void generateSurfacePoint(CityWorldGenerator generator, PlatLot lot, SupportBlocks chunk,
			CoverProvider foliage, int x, double perciseY, int z, boolean includeTrees);

	public void generateSurface(CityWorldGenerator generator, PlatLot lot, SupportBlocks chunk,
			AbstractCachedYs blockYs, boolean includeTrees) {
		generateSurface(generator, lot, chunk, blockYs, 0, includeTrees);
	}

	boolean inTreeRange(int x, int z) {
		return x > 2 && x < 15 && z > 2 && z < 15;// && x % 2 == 0 && z % 2 != 0;
	}

	boolean inBigTreeRange(int x, int z) {
		return x > 4 && x < 11 && z > 4 && z < 11 && x % 2 == 0 && z % 2 != 0;
	}

	public void generateSurface(CityWorldGenerator generator, PlatLot lot, SupportBlocks chunk, int x, int y, int z,
			boolean includeTrees) {
		CoverProvider foliage = generator.coverProvider;

		generateSurfacePoint(generator, lot, chunk, foliage, x, y, z, includeTrees);
	}

	public void generateSurface(CityWorldGenerator generator, PlatLot lot, SupportBlocks chunk,
			AbstractCachedYs blockYs, int addTo, boolean includeTrees) {
		CoverProvider foliage = generator.coverProvider;

		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {
				int topY = lot.getTopY(generator, blockYs, x, z);
				double y = blockYs.getPerciseY(x, z) + addTo;
				if (topY <= y) {
					generateSurfacePoint(generator, lot, chunk, foliage, x, y, z, includeTrees);
				}
			}
		}
	}

	// Based on work contributed by drew-bahrue
	// (https://github.com/echurchill/CityWorld/pull/2)
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
		case SPARSE:
		case DESTROYED:
		case NORMAL:
			provider = new SurfaceProvider_Normal(odds);
			break;
		}

		return provider;
	}

}
