package me.daddychurchill.CityWorld.Context.Flooded;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.NatureContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Flooded.FloodedNatureLot;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.HeightInfo.HeightState;

public class FloodedNatureContext extends NatureContext {

	public FloodedNatureContext(WorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlatLot createNaturalLot(WorldGenerator generator, PlatMap platmap, int x, int z) {
		return new FloodedNatureLot(platmap, platmap.originX + x, platmap.originZ + z);
	}
	
	@Override
	public PlatLot createBuriedBuildingLot(WorldGenerator generator, PlatMap platmap, int x, int z) {
		return null; // for now
	}
	
	@Override
	public PlatLot createSurfaceBuildingLot(WorldGenerator generator, PlatMap platmap, int x, int z) {
		return null; // for now
	}
	
	@Override
	protected void populateSpecial(WorldGenerator generator, PlatMap platmap, int x, int z, HeightState state) {
		// for now
	}
	
}
