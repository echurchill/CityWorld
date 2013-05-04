package me.daddychurchill.CityWorld.Plats.Nature;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class MountainTentLot extends MountainBuildingLot {

	public MountainTentLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		retainingWalls = false;
	}
	
	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new MountainTentLot(platmap, chunkX, chunkZ);
	}

	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, DataContext context, int platX, int platZ) {
		
		//TODO: I need to add a tent generator

//		// now make a shack
//		int floors = generator.houseProvider.generateShack(generator, chunk, context, chunkOdds, averageHeight + 1, 5);
//		
//		// not a happy place?
//		if (generator.settings.includeDecayedBuildings)
//			destroyBuilding(generator, generator.streetLevel + 1, floors);
	}
}
