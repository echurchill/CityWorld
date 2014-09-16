package me.daddychurchill.CityWorld.Context.Astral;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.Astral.AstralNexusLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralNexusLot.NexusSegment;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class AstralNexusContext extends AstralDataContext {

	public AstralNexusContext(WorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void populateMap(WorldGenerator generator, PlatMap platmap) {
		//TODO, This doesn't handle schematics quite right yet
		// let the user add their stuff first, then plug any remaining holes with our stuff
		//mapsSchematics.populate(generator, platmap);
		
		// where it all begins
		int originX = platmap.originX;
		int originZ = platmap.originZ;
		
		// is this natural or buildable?
		platmap.setLot(5, 5, new AstralNexusLot(platmap, originX + 5, originZ + 5, NexusSegment.NORTHWEST));
		platmap.setLot(6, 5, new AstralNexusLot(platmap, originX + 6, originZ + 5, NexusSegment.NORTHEAST));
		platmap.setLot(5, 6, new AstralNexusLot(platmap, originX + 5, originZ + 6, NexusSegment.SOUTHWEST));
		platmap.setLot(6, 6, new AstralNexusLot(platmap, originX + 6, originZ + 6, NexusSegment.SOUTHEAST));
	}

	@Override
	public void validateMap(WorldGenerator generator, PlatMap platmap) {
		// TODO Auto-generated method stub

	}

}
