package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

public abstract class SurfaceProvider extends Provider {

	public SurfaceProvider(Odds odds) {
		super();
		this.odds = odds;
	}

	
	protected final static double treeOdds = 0.90;
	protected final static double foliageOdds = 0.40;
	
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

		SurfaceProvider provider = null;
		
//		// need something like PhatLoot but for foliage
//		provider = FoliageProvider_PhatFoliage.loadPhatFoliage();
		if (provider == null) {
			
			switch (generator.worldStyle) {
			case FLOATING:
				provider = new SurfaceProvider_Floating(odds);
				break;
			default:
				provider = new SurfaceProvider_Normal(odds);
				break;
			}
		}
	
		return provider;
	}
	
}
