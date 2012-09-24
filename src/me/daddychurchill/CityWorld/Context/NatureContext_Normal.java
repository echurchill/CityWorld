package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plats.BunkerLot;
import me.daddychurchill.CityWorld.Plats.OldCastleLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.MineEntranceLot;
import me.daddychurchill.CityWorld.Plats.OilPlatformLot;
import me.daddychurchill.CityWorld.Plats.RoadLot;
import me.daddychurchill.CityWorld.Plats.MountainShackLot;
import me.daddychurchill.CityWorld.Plats.RadioTowerLot;
import me.daddychurchill.CityWorld.Support.HeightInfo;
import me.daddychurchill.CityWorld.Support.HeightInfo.HeightState;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class NatureContext_Normal extends RuralContext {

	public NatureContext_Normal(WorldGenerator generator) {
		super(generator);
	}
	
	@Override
	protected void initialize() {

	}
	
	private final static double oddsOfBunkers = 0.50;

	@Override
	public void populateMap(WorldGenerator generator, PlatMap platmap) {

		// let the user add their stuff first, then plug any remaining holes with our stuff
		populateWithSchematics(generator, platmap);
		
		// random stuff?
		Odds platmapOdds = platmap.getOddsGenerator();
		boolean doBunkers = platmapOdds.playOdds(oddsOfBunkers);
		
		// where it all begins
		int originX = platmap.originX;
		int originZ = platmap.originZ;
		HeightInfo heights;
		
		// highest special place
		int maxHeight = Integer.MIN_VALUE;
		int maxHeightX = -1;
		int maxHeightZ = -1;
		HeightState maxState = HeightState.BUILDING;
		
		// lowest special place
		int minHeight = Integer.MAX_VALUE;
		int minHeightX = -1;
		int minHeightZ = -1;
		HeightState minState = HeightState.BUILDING;
		
		// is this natural or buildable?
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);
				if (current == null) {
					
					// what is the world location of the lot?
					int blockX = (originX + x) * SupportChunk.chunksBlockWidth;
					int blockZ = (originZ + z) * SupportChunk.chunksBlockWidth;
					
					// get the height info for this chunk
					heights = HeightInfo.getHeightsFaster(generator, blockX, blockZ);
					if (!heights.isBuildable()) {
						
						// our inner chunks?
						if (x > 0 && x < PlatMap.Width - 1 && z > 0 && z < PlatMap.Width - 1) {
							
							// extreme changes?
							if (heights.minHeight < minHeight) {
								minHeight = heights.minHeight;
								minHeightX = x;
								minHeightZ = z;
								minState = heights.state;
							}
							if (heights.maxHeight > maxHeight) {
								maxHeight = heights.maxHeight;
								maxHeightX = x;
								maxHeightZ = z;
								maxState = heights.state;
							}
							
							// innermost chunks?
							boolean innermost = x >= RoadLot.PlatMapRoadInset && x < PlatMap.Width - RoadLot.PlatMapRoadInset && 
												z >= RoadLot.PlatMapRoadInset && z < PlatMap.Width - RoadLot.PlatMapRoadInset;
							
							// what type of height are we talking about?
							switch (heights.state) {
							case MIDLAND: 
								
								// if not one of the innermost or the height isn't tall enough for bunkers
								if (generator.settings.includeHouses)
									if (!innermost || minHeight < BunkerLot.calcBunkerMinHeight(generator)) {
										if (heights.isSortaFlat() && generator.shapeProvider.isIsolatedBuildingAt(originX + x, originZ + z))
											current = new MountainShackLot(platmap, originX + x, originZ + z);
										break;
									}
							case HIGHLAND:
							case PEAK:
								if (generator.settings.includeBunkers)
									if (doBunkers && innermost) {
										current = new BunkerLot(platmap, originX + x, originZ + z);
									}
								break;
							default:
								break;
							}
						}
						
						// did current get defined?
						if (current != null)
							platmap.setLot(x, z, current);
						else
							platmap.recycleLot(x, z);
					}
				}
			}
		}
		
		// any special things to do?
		populateSpecial(generator, platmap, maxHeightX, maxHeightZ, maxState);
		populateSpecial(generator, platmap, minHeightX, minHeightZ, minState);
	}
	
	private void populateSpecial(WorldGenerator generator, PlatMap platmap, int x, int z, HeightState state) {

		// what type of height are we talking about?
		if (state != HeightState.BUILDING && 
			generator.shapeProvider.isNotSoIsolatedBuildingAt(platmap.originX + x, platmap.originZ + z)) {
			
			// what to make?
			switch (state) {
			case DEEPSEA:
				// Oil rigs
				//TODO: Do I want to fix this?
				// SEED: -7145037513581384357 in v1.04
				// POS: 1080, 80, 284
				// Two platforms are created right next to two bridges
				if (generator.settings.includeBuildings) {
					platmap.setLot(x, z, new OilPlatformLot(platmap, platmap.originX + x, platmap.originZ + z));
				}
				break;
//			case SEA:
//				break;
//			case BUILDING:
//				break;
//			case LOWLAND:
//				//TODO Statue overlooking the city?
//				break;
			case MIDLAND: 
				// Mine entrance
				if (generator.settings.includeMines)
					platmap.setLot(x, z, new MineEntranceLot(platmap, platmap.originX + x, platmap.originZ + z));
				break;
			case HIGHLAND: 
				// Radio towers
				if (generator.settings.includeBuildings)
					platmap.setLot(x, z, new RadioTowerLot(platmap, platmap.originX + x, platmap.originZ + z));
				break;
			case PEAK:
				// Old castle
				platmap.setLot(x, z, new OldCastleLot(platmap, platmap.originX + x, platmap.originZ + z));
				break;
			default:
				break;
			}
		}
	}
}
