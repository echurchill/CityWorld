package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import me.daddychurchill.CityWorld.CityWorld;
import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatBunker;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.PlatPlatform;
import me.daddychurchill.CityWorld.Plats.PlatRoad;
import me.daddychurchill.CityWorld.Plats.PlatShack;
import me.daddychurchill.CityWorld.Plats.PlatTower;
import me.daddychurchill.CityWorld.Support.HeightInfo;
import me.daddychurchill.CityWorld.Support.HeightInfo.HeightState;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class ContextNature extends ContextRural {

	public ContextNature(CityWorld plugin, WorldGenerator generator, SupportChunk typicalChunk) {
		super(plugin, generator, typicalChunk);

	}

	@Override
	public void populateMap(WorldGenerator generator, PlatMap platmap, SupportChunk typicalChunk) {
		Random random = typicalChunk.random;
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
					int blockX = (originX + x) * typicalChunk.width;
					int blockZ = (originZ + z) * typicalChunk.width;
					
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
							boolean innermost = x >= PlatRoad.PlatMapRoadInset && x < PlatMap.Width - PlatRoad.PlatMapRoadInset && 
												z >= PlatRoad.PlatMapRoadInset && z < PlatMap.Width - PlatRoad.PlatMapRoadInset;
							
							// what type of height are we talking about?
							switch (heights.state) {
							case MIDLAND: 
								//TODO Mine entrances
								if (!innermost && (minHeight < generator.sidewalkLevel + PlatBunker.bunkerMinHeight)) {
									if (heights.isSortaFlat() && generator.isIsolatedBuildingAt(originX + x, originZ + z))
										current = new PlatShack(random, platmap);
									break;
								}
							case HIGHLAND:
							case PEAK:
								//TODO Bunkers
								if (innermost) {
									current = new PlatBunker(random, platmap);
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
							platmap.recycleLot(random, x, z);
					}
				}
			}
		}
		
		// any special things to do?
		populateSpecial(generator, platmap, typicalChunk, maxHeightX, maxHeightZ, maxState);
		populateSpecial(generator, platmap, typicalChunk, minHeightX, minHeightZ, minState);
	}
	
	private void populateSpecial(WorldGenerator generator, PlatMap platmap, SupportChunk typicalChunk, int x, int z, HeightState state) {
		Random random = typicalChunk.random;

		// what type of height are we talking about?
		if (state != HeightState.BUILDING && generator.isNotSoIsolatedBuildingAt(platmap.originX + x, platmap.originZ + z)) {
			switch (state) {
//			case SEA:
			case DEEPSEA:
				// Oil rigs
				platmap.setLot(x, z, new PlatPlatform(random, platmap));
				break;
//			case SEA:
//				break;
//			case BUILDING:
//				break;
//			case LOWLAND:
//				break;
//			case MIDLAND: 
//				break;
			case HIGHLAND: 
				// Radio towers
				platmap.setLot(x, z, new PlatTower(random, platmap));
				break;
//			case PEAK:
//				// Observatories
//				platmap.setLot(x, z, new PlatObservatory(random, platmap));
//				break;
			default:
				break;
			}
		}
	}
}
