package me.daddychurchill.CityWorld.Plats;

import org.bukkit.World.Environment;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.HouseFactory;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class PlatHouse extends PlatIsolated {

	public PlatHouse(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		style = LotStyle.STRUCTURE;
	}

	@Override
	protected int getTopStrataY(WorldGenerator generator, int blockX, int blockZ) {
		return generator.sidewalkLevel - 1;
	}

	@Override
	protected void generateActualChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, ContextData context, int platX, int platZ) {
		
		// ground please
		if (generator.settings.environment == Environment.NETHER)
			chunk.setLayer(generator.sidewalkLevel, sandId);
		else
			chunk.setLayer(generator.sidewalkLevel, grassId);
	}
	
	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, ContextData context, int platX, int platZ) {

		// now make a house
		int floors = HouseFactory.generateHouse(chunk, context, chunkRandom, generator.sidewalkLevel + 1, 2);
		
		// not a happy place?
		if (generator.settings.environment == Environment.NETHER) {
			destroyLot(generator, chunk, generator.sidewalkLevel + 1, generator.sidewalkLevel + 1 + ContextData.FloorHeight * (floors + 1));
		}
	}

}
