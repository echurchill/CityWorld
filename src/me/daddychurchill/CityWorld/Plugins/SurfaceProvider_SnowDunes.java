package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.Support.Odds;

public class SurfaceProvider_SnowDunes extends SurfaceProvider_Normal {

	public SurfaceProvider_SnowDunes(Odds odds) {
		super(odds);
		// TODO Auto-generated constructor stub
	}
	
//	@Override
//	public void generateSurfacePoint(WorldGenerator generator, PlatLot lot,
//			RealChunk chunk, FoliageProvider foliage, int x, double perciseY,
//			int z, boolean includeTrees) {
//		
//		// do the regular stuff
//		super.generateSurfacePoint(generator, lot, chunk, foliage, x, perciseY, z, includeTrees);
//		
//		// where to draw?
//		double floodY = generator.shapeProvider.findPerciseFloodY(generator, chunk.getBlockX(x), chunk.getBlockZ(z));
//		int y = NoiseGenerator.floor(floodY);
//		if (perciseY > floodY)
//			y = NoiseGenerator.floor(perciseY);
//
//		// add the snow
//		generator.oreProvider.dropSnow(generator, chunk, x, y, z, 
//			(byte) NoiseGenerator.floor((floodY - Math.floor(floodY)) * 8.0));
//	}

}
