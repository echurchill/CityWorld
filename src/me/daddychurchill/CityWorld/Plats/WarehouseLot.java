package me.daddychurchill.CityWorld.Plats;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class WarehouseLot extends FinishedBuildingLot {

	public WarehouseLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		aboveFloorHeight = aboveFloorHeight * 2;
		height = 1;
//		depth = 0;
		roofStyle = platmapRandom.nextBoolean() ? RoofStyle.EDGED : RoofStyle.FLATTOP;
	}

	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, 
			RealChunk chunk, DataContext context, int platX, int platZ) {
		super.generateActualBlocks(generator, platmap, chunk, context, platX, platZ);
		
		//TODO Remove this flag
		//chunk.setBlocks(0, generator.streetLevel, generator.streetLevel + 60, 0, Material.BEDROCK);
	}
}
