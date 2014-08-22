package me.daddychurchill.CityWorld.Plats.Astral;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class AstralBuildingLot extends AstralEmptyLot {

	public AstralBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

	}

	@Override
	protected void generateActualBlocks(WorldGenerator generator,
			PlatMap platmap, RealChunk chunk, DataContext context,
			int platX, int platZ) {
		
		super.generateActualBlocks(generator, platmap, chunk, context, platX, platZ);
		chunk.setBlocks(2, 14, generator.seaLevel + 6, generator.seaLevel + 7, 2, 14, Material.DIAMOND_BLOCK);
	}

}
