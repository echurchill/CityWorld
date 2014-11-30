package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public abstract class SurfaceProvider extends Provider {

	public SurfaceProvider(Odds odds) {
		super();
		this.odds = odds;
	}

	protected final static double treeOdds = Odds.oddsVeryUnlikely;
	protected final static double treeTallOdds = Odds.oddsLikely;
	protected final static double treeAltOdds = Odds.oddsLikely;
	protected final static double treeAltTallOdds = Odds.oddsVeryUnlikely;
	protected final static double foliageOdds = Odds.oddsSomewhatLikely;
	protected final static double cactusOdds = Odds.oddsPrettyUnlikely;
	protected final static double reedOdds = Odds.oddsVeryUnlikely;
	protected final static double flowerRedOdds = Odds.oddsVeryUnlikely;
	protected final static double flowerYellowOdds = Odds.oddsExtremelyUnlikely;
	protected final static double flowerFernOdds = Odds.oddsSomewhatLikely;
	
	protected Odds odds;
	
	public abstract void generateSurfacePoint(WorldGenerator generator, PlatLot lot, SupportChunk chunk, CoverProvider foliage, 
			int x, double perciseY, int z, boolean includeTrees);
	
	public void generateSurface(WorldGenerator generator, PlatLot lot, SupportChunk chunk, CachedYs blockYs, boolean includeTrees) {
		CoverProvider foliage = generator.coverProvider;
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {
				generateSurfacePoint(generator, lot, chunk, foliage, x, blockYs.getPerciseY(x, z), z, includeTrees);
			}
		}
	}
	
	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	public static SurfaceProvider loadProvider(WorldGenerator generator, Odds odds) {
		
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
		case DESTROYED:
		case NORMAL:
			provider = new SurfaceProvider_Normal(odds);
			break;
		}
		
		return provider;
	}
	
}
