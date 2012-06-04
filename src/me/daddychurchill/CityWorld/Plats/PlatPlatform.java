package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class PlatPlatform extends PlatIsolated {

	public PlatPlatform(Random random, PlatMap platmap) {
		super(random, platmap);

	}

	private final static byte platformId = (byte) Material.DOUBLE_STEP.getId();
	private final static byte supportId = (byte) Material.CLAY.getId();
	
	private final static int aboveSea = 6;
	private final static int floorHeight = 4;

	@Override
	public void generateChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, ContextData context, int platX, int platZ) {
		super.generateChunk(generator, platmap, chunk, biomes, context, platX, platZ);
		
//		CityWorld.log.info("Platform @ " + (chunk.chunkX * chunk.width) + ", " + (chunk.chunkZ * chunk.width));
		
		// legs
		chunk.setBlocks(2, 4, minHeight, generator.seaLevel + aboveSea, 2, 4, supportId);
		chunk.setBlocks(2, 4, minHeight, generator.seaLevel + aboveSea, 12, 14, supportId);
		chunk.setBlocks(12, 14, minHeight, generator.seaLevel + aboveSea, 2, 4, supportId);
		chunk.setBlocks(12, 14, minHeight, generator.seaLevel + aboveSea, 12, 14, supportId);

		// platform
		chunk.setLayer(generator.seaLevel + aboveSea, platformId);
		chunk.setBlocks(2, 3, minHeight, generator.seaLevel + aboveSea, 2, 3, supportId);
		chunk.setBlocks(2, 3, minHeight, generator.seaLevel + aboveSea, 13, 14, supportId);
		chunk.setBlocks(13, 14, minHeight, generator.seaLevel + aboveSea, 2, 3, supportId);
		chunk.setBlocks(13, 14, minHeight, generator.seaLevel + aboveSea, 13, 14, supportId);
		chunk.setLayer(generator.seaLevel + aboveSea + floorHeight, platformId);
	}
	
	@Override
	public void generateBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, ContextData context, int platX, int platZ) {
		super.generateBlocks(generator, platmap, chunk, context, platX, platZ);

		// now fill it in with cool bits
	}
}
