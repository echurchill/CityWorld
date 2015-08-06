package me.daddychurchill.CityWorld.Context.Astral;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralBuriedBuildingLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralBuriedRoadLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralBuriedRoadLot.SidewalkStyle;
import me.daddychurchill.CityWorld.Support.HeightInfo;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

public class AstralBuriedCityContext extends AstralDataContext {

	public AstralBuriedCityContext(CityWorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void populateMap(CityWorldGenerator generator, PlatMap platmap) {
		//TODO, This doesn't handle schematics quite right yet
		// let the user add their stuff first, then plug any remaining holes with our stuff
		//mapsSchematics.populate(generator, platmap);
		
		// where it all begins
		int originX = platmap.originX;
		int originZ = platmap.originZ;
		HeightInfo heights;
		
		// is this natural or buildable?
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);
				if (current == null) {
				
					// what is the world location of the lot?
					int blockX = (originX + x) * SupportBlocks.sectionBlockWidth;
					int blockZ = (originZ + z) * SupportBlocks.sectionBlockWidth;
					
					// get the height info for this chunk
					heights = HeightInfo.getHeightsFaster(generator, blockX, blockZ);
					if (!heights.anyEmpties) {
						
						// sidewalks?
						SidewalkStyle sidewalks = SidewalkStyle.NONE;
						if (x == 2 || x == PlatMap.Width - 3) {
							sidewalks = SidewalkStyle.EASTWEST;
							if (z == 2 || z == PlatMap.Width - 3)
								sidewalks = SidewalkStyle.INTERSECTION;
						} else if (z == 2 || z == PlatMap.Width - 3)
							sidewalks = SidewalkStyle.NORTHSOUTH;
						
						// building or not?
						if (sidewalks != SidewalkStyle.NONE)
							current = new AstralBuriedRoadLot(platmap, originX + x, originZ + z, sidewalks);
						else
							current = new AstralBuriedBuildingLot(platmap, originX + x, originZ + z);
					}
	
					// did current get defined?
					if (current != null)
						platmap.setLot(x, z, current);
				}
			}
		}
	}

	@Override
	public void validateMap(CityWorldGenerator generator, PlatMap platmap) {
		// TODO Auto-generated method stub

	}

	@Override
	public Material getMapRepresentation() {
		return Material.STEP;
	}
}
