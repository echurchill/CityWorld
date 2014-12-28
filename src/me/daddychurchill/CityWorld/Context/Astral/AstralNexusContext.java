package me.daddychurchill.CityWorld.Context.Astral;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.Astral.AstralNexusLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralNexusLot.NexusSegment;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class AstralNexusContext extends AstralDataContext {

	public AstralNexusContext(CityWorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void populateMap(CityWorldGenerator generator, PlatMap platmap) {
		//TODO, This doesn't handle schematics quite right yet
		// let the user add their stuff first, then plug any remaining holes with our stuff
		//mapsSchematics.populate(generator, platmap);
		
		// where it all begins
		int nexusX = AstralNexusLot.chunkX;
		int nexusZ = AstralNexusLot.chunkZ;
		int chunkX = platmap.originX + nexusX;
		int chunkZ = platmap.originZ + nexusZ;
		
		// is this natural or buildable?
		platmap.setLot(nexusX, nexusZ, new AstralNexusLot(platmap, chunkX, chunkZ, NexusSegment.NORTHWEST));
		platmap.setLot(nexusX + 1, nexusZ, new AstralNexusLot(platmap, chunkX + 1, chunkZ, NexusSegment.NORTHEAST));
		platmap.setLot(nexusX, nexusZ + 1, new AstralNexusLot(platmap, chunkX, chunkZ + 1, NexusSegment.SOUTHWEST));
		platmap.setLot(nexusX + 1, nexusZ + 1, new AstralNexusLot(platmap, chunkX + 1, chunkZ + 1, NexusSegment.SOUTHEAST));
	}

	@Override
	public void validateMap(CityWorldGenerator generator, PlatMap platmap) {
		// TODO Auto-generated method stub

	}

	@Override
	public Material getMapRepresentation() {
		return Material.BEACON;
	}
}
