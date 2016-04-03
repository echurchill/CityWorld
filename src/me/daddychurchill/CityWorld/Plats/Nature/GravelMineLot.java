package me.daddychurchill.CityWorld.Plats.Nature;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class GravelMineLot extends GravelLot {

	public GravelMineLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		trulyIsolated = true;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new GravelMineLot(platmap, chunkX, chunkZ);
	}
	
	@Override
	protected void generateActualBlocks(CityWorldGenerator generator,
			PlatMap platmap, RealBlocks chunk, DataContext context, int platX,
			int platZ) {
		generateHole(generator, chunkOdds, chunk, generator.streetLevel, 14, 16);
		
		// place snow
		generateSurface(generator, chunk, false);
		
	}
}
