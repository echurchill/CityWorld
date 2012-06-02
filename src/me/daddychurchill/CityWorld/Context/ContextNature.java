package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import me.daddychurchill.CityWorld.CityWorld;
import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.PlatShack;
import me.daddychurchill.CityWorld.Support.HeightInfo;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class ContextNature extends ContextRural {

	public ContextNature(CityWorld plugin, SupportChunk typicalChunk) {
		super(plugin, typicalChunk);

	}

	@Override
	public void populateMap(WorldGenerator generator, PlatMap platmap, SupportChunk typicalChunk) {
		Random random = typicalChunk.random;
		int originX = platmap.originX;
		int originZ = platmap.originZ;
		int deltaY = typicalChunk.sealevel / 2;
		HeightInfo heights;
		
		// special items?
		int observatoryX = -1;
		int observatoryZ = -1;
		int oilplatformX = -1;
		int oilplatformZ = -1;
		
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
						
						// what type of height are we talking about?
						switch (heights.state) {
						case DEEPSEA:
							// Oil rigs
							break;
						case SEA:
							// Boats?
							break;
						case BUILDING:
							// taken care off
							break;
						case LOWLAND: 
							break;
						case MIDLAND: 
							// Bunkers
							// Mine entrances
							if (generator.isMountainShackAt(originX + x, originZ + z) && heights.isSortaFlat())
								current = new PlatShack(random, platmap);
							break;
						case HIGHLAND: 
							// Radio towers
							break;
						case PEAK:
							// Observatories
							break;
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
	}
}
