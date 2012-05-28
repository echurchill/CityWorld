package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Support.ByteChunk;

public class PlatHouse extends PlatIsolated {

	public PlatHouse(Random random, PlatMap platmap) {
		super(random, platmap);
		
		style = lotStyle.STRUCTURE;
	}

	@Override
	public void generateChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, ContextData context, int platX, int platZ) {
		super.generateChunk(generator, platmap, chunk, biomes, context, platX, platZ);
		
		chunk.setBlocks(2, 13, context.streetLevel, context.streetLevel + 10, 2, 13, Material.WOOD);
	}
}
