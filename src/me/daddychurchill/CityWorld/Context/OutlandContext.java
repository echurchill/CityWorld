package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Clipboard.PasteProvider.SchematicFamily;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.PlatLot.LotStyle;
import me.daddychurchill.CityWorld.Plats.Nature.CampgroundLot;
import me.daddychurchill.CityWorld.Plats.Nature.GravelMineLot;
import me.daddychurchill.CityWorld.Plats.Nature.GravelworksLot;
import me.daddychurchill.CityWorld.Plats.Nature.MineEntranceLot;
import me.daddychurchill.CityWorld.Plats.Nature.WoodframeLot;
import me.daddychurchill.CityWorld.Plats.Nature.WoodworksLot;
import me.daddychurchill.CityWorld.Support.HeightInfo;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

public class OutlandContext extends RuralContext {

	public OutlandContext(CityWorldGenerator generator) {
		super(generator);

		oddsOfIsolatedLots = Odds.oddsVeryLikely;
		
		setSchematicFamily(SchematicFamily.OUTLAND);
	}

	@Override
	public void populateMap(CityWorldGenerator generator, PlatMap platmap) {
		
		// now add our stuff
		Odds platmapOdds = platmap.getOddsGenerator();
		boolean singletonOneUsed = false;
		boolean singletonTwoUsed = false;
		HeightInfo heights;
		
		// where do we begin?
		int originX = platmap.originX;
		int originZ = platmap.originZ;
		
		// clean up the platmap of singletons and odd road structures
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);
				
				// something here?
				if (current == null) {
					
					// but there aren't neighbors
					if (!platmap.isEmptyLot(x - 1, z) && !platmap.isEmptyLot(x + 1, z) &&
						!platmap.isEmptyLot(x, z - 1) && !platmap.isEmptyLot(x, z + 1))
						platmap.recycleLot(x, z);
				}
				
				// look for singleton nature and roundabouts
				else {
					
					// if a single natural thing is here but surrounded by four "things"
					if (current.style == LotStyle.NATURE &&
						platmap.isEmptyLot(x - 1, z) && platmap.isEmptyLot(x + 1, z) &&
						platmap.isEmptyLot(x, z - 1) && platmap.isEmptyLot(x, z + 1))
						platmap.emptyLot(x, z);
					
					// get rid of roundabouts
					else if (current.style == LotStyle.ROUNDABOUT) {
						platmap.paveLot(x, z, false);
						platmap.emptyLot(x - 1, z - 1);
						platmap.emptyLot(x - 1, z + 1);
						platmap.emptyLot(x + 1, z - 1);
						platmap.emptyLot(x + 1, z + 1);
					}
				}
			}
		}
		
		// let the user add their stuff first
		getSchematics(generator).populate(generator, platmap);
		
		// fill with more stuff
		if (!generator.settings.includeDecayedBuildings) {
			boolean stoneworks = platmapOdds.flipCoin() && generator.settings.includeMines;
			for (int x = 0; x < PlatMap.Width; x++) {
				for (int z = 0; z < PlatMap.Width; z++) {
					PlatLot current = platmap.getLot(x, z);
					if (current == null) {
						
						// what is the world location of the lot?
						int chunkX = originX + x;
						int chunkZ = originZ + z;
						int blockX = chunkX * SupportBlocks.sectionBlockWidth;
						int blockZ = chunkZ * SupportBlocks.sectionBlockWidth;
						
						// get the height info for this chunk
						heights = HeightInfo.getHeightsFaster(generator, blockX, blockZ);
						if (heights.isBuildable()) {
							if (stoneworks) {
								if (!singletonOneUsed && platmapOdds.playOdds(Odds.oddsUnlikely)) {
									current = new GravelMineLot(platmap, chunkX, chunkZ);
									singletonOneUsed = true;
								} else if (!singletonTwoUsed && platmapOdds.playOdds(Odds.oddsUnlikely)) {
									current = new MineEntranceLot(platmap, chunkX, chunkZ);
									singletonTwoUsed = true;
								} else
									current = new GravelworksLot(platmap, chunkX, chunkZ);
								
							} else { // woodworks
								if (!singletonOneUsed && platmapOdds.playOdds(Odds.oddsVeryUnlikely)) {
									current = new WoodframeLot(platmap, chunkX, chunkZ);
									singletonOneUsed = true;
								} else if (!singletonTwoUsed && platmapOdds.playOdds(Odds.oddsSomewhatUnlikely)) {
									current = new CampgroundLot(platmap, chunkX, chunkZ);
									singletonTwoUsed = true;
								} else
									current = new WoodworksLot(platmap, chunkX, chunkZ);
							}
							
							// remember what we did
							if (current != null)
								platmap.setLot(x, z, current);
						}
					}
				}
			}
		}
	}

	@Override
	protected PlatLot getBackfillLot(CityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		// this will eventually be filled in with nature
		return null;
	}

}
