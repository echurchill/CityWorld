package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

public abstract class SurfaceProvider extends Provider {

	public SurfaceProvider(Odds odds) {
		super();
		this.odds = odds;
	}

	
	protected final static double treeOdds = DataContext.oddsPrettyUnlikely;
	protected final static double treeTallOdds = DataContext.oddsLikely;
	protected final static double treeAltOdds = DataContext.oddsLikely;
	protected final static double treeAltTallOdds = DataContext.oddsPrettyUnlikely;
	protected final static double foliageOdds = DataContext.oddsSomewhatLikely;
	protected final static double cactusOdds = DataContext.oddsVeryUnlikely;
	protected final static double reedOdds = DataContext.oddsPrettyUnlikely;
	protected final static double flowerRedOdds = DataContext.oddsPrettyUnlikely;
	protected final static double flowerYellowOdds = DataContext.oddsExtremelyUnlikely;
	protected final static double flowerFernOdds = DataContext.oddsSomewhatLikely;
	
	protected Odds odds;
	
	public abstract void generateSurfacePoint(WorldGenerator generator, PlatLot lot, RealChunk chunk, FoliageProvider foliage, 
			int x, double perciseY, int z, boolean includeTrees);
	
	public void generateSurface(WorldGenerator generator, PlatLot lot, RealChunk chunk, CachedYs blockYs, boolean includeTrees) {
		FoliageProvider foliage = generator.foliageProvider;
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {
				generateSurfacePoint(generator, lot, chunk, foliage, x, blockYs.getPerciseY(x, z), z, includeTrees);
			}
		}
	}
	
	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	public static SurfaceProvider loadProvider(WorldGenerator generator, Odds odds) {

		switch (generator.worldStyle) {
		case FLOATING:
			return new SurfaceProvider_Floating(odds);
		case FLOODED:
			return new SurfaceProvider_Flooded(odds);
		case SANDDUNES:
			return new SurfaceProvider_SandDunes(odds);
		case SNOWDUNES:
			return new SurfaceProvider_SnowDunes(odds);
		default:
			return new SurfaceProvider_Normal(odds);
		}
	}
	
}
