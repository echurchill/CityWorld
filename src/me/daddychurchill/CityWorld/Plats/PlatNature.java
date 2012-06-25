package me.daddychurchill.CityWorld.Plats;

import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class PlatNature extends PlatIsolated {

	public PlatNature(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		style = LotStyle.NATURE;
	}

	@Override
	protected void generateActualChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, ContextData context, int platX, int platZ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, ContextData context, int platX, int platZ) {
		generateSurface(generator, platmap, chunk, context, platX, platZ, true);
	}
	
}
