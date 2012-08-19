package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plats.FloatingHouseLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.ShapeProvider;
import me.daddychurchill.CityWorld.Plugins.ShapeProvider_Floating;
import me.daddychurchill.CityWorld.Support.HeightInfo;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class NatureContext_Floating extends RuralContext {

	public NatureContext_Floating(WorldGenerator generator, PlatMap platmap) {
		super(generator, platmap);

	}

	@Override
	public void populateMap(WorldGenerator generator, PlatMap platmap) {
		Random random = platmap.getRandomGenerator();
		ShapeProvider shapeProvider = generator.shapeProvider;
		
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
					int blockX = (originX + x) * SupportChunk.chunksBlockWidth;
					int blockZ = (originZ + z) * SupportChunk.chunksBlockWidth;
					
					// get the height info for this chunk
					heights = HeightInfo.getHeightsFaster(generator, blockX, blockZ);
					if (!heights.isBuildable()) {
						
						// our inner chunks?
						if (x > 0 && x < PlatMap.Width - 1 && z > 0 && z < PlatMap.Width - 1) {
							
							// floating building?
							if (generator.settings.includeHouses) {
								if (shapeProvider.isIsolatedBuildingAt(originX + x, originZ + z))
									current = new FloatingHouseLot(platmap, originX + x, originZ + z, 
											random.nextInt(ShapeProvider_Floating.floatingRange) + ShapeProvider_Floating.floatingMin);
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
	}
}
