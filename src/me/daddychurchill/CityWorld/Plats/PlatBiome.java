package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import me.daddychurchill.CityWorld.Context.PlatMapContext;
import me.daddychurchill.CityWorld.PlatMaps.PlatMap;
import me.daddychurchill.CityWorld.Support.ByteChunk;

import org.bukkit.Material;
import org.bukkit.block.Biome;

public class PlatBiome extends PlatLot {

	protected final static byte stoneId = (byte) Material.STONE.getId();
	
	public PlatBiome(Random rand, PlatMapContext context) {
		super(rand, context);
	}

	@Override
	public void generateChunk(PlatMap platmap, ByteChunk byteChunk, PlatMapContext context, int platX, int platZ) {
		
		byteChunk.setLayer(0, context.streetLevel + 1, stoneId);
		
		Biome biome = platmap.theWorld.getBiome(byteChunk.chunkX, byteChunk.chunkZ);
		int tens = biome.ordinal() / 10;
		int ones = biome.ordinal() % 10;
		byteChunk.drawCoordinate(tens, ones, context.streetLevel + 1, (platX == 0 && platZ == 0));
	}

}
