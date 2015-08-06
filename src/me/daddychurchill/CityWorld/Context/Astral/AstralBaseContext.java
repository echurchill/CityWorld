package me.daddychurchill.CityWorld.Context.Astral;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralTownBuildingLot;
import me.daddychurchill.CityWorld.Plats.Astral.AstralTownEmptyLot;
import me.daddychurchill.CityWorld.Support.HeightInfo;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

public class AstralBaseContext extends AstralDataContext {

	public AstralBaseContext(CityWorldGenerator generator) {
		super(generator);

		oddsOfIsolatedLots = Odds.oddsPrettyUnlikely;
		oddsOfUnfinishedBuildings = Odds.oddsUnlikely;
	}

	@Override
	public void populateMap(CityWorldGenerator generator, PlatMap platmap) {
		
		//TODO, This doesn't handle schematics quite right yet
		// let the user add their stuff first, then plug any remaining holes with our stuff
		//mapsSchematics.populate(generator, platmap);
		
		// random fluff
		Odds odds = platmap.getOddsGenerator();
		
		// where it all begins
		int originX = platmap.originX;
		int originZ = platmap.originZ;
		HeightInfo heights;
		boolean addingBases = false;
		
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
					if (!heights.anyEmpties && heights.averageHeight < generator.seaLevel - 8) {
						if (!addingBases)
							addingBases = odds.playOdds(oddsOfIsolatedLots);
						
						if (addingBases) {
							if (odds.playOdds(oddsOfUnfinishedBuildings)) 
								current = new AstralTownEmptyLot(platmap, originX + x, originZ + z);
							else
								current = new AstralTownBuildingLot(platmap, originX + x, originZ + z, AstralTownBuildingLot.pickBuildingType(odds));
						}
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
		return Material.QUARTZ_BLOCK;
	}
}
