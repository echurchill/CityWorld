package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import me.daddychurchill.CityWorld.PlatMaps.PlatMap;
import me.daddychurchill.CityWorld.Support.ByteChunk;

import org.bukkit.Material;
import org.bukkit.block.Biome;

public class PlatBiome extends PlatLot {

	protected final static byte stoneId = (byte) Material.STONE.getId();
	
	public PlatBiome(Random rand) {
		super(rand);
	}

	@Override
	public void generateChunk(PlatMap platmap, ByteChunk byteChunk, int platX, int platZ) {
		
		byteChunk.setLayer(0, PlatMap.StreetLevel + 1, stoneId);
		
		Biome biome = platmap.theWorld.getBiome(byteChunk.X, byteChunk.Z);
		int tens = biome.ordinal() / 10;
		int ones = biome.ordinal() % 10;
		byteChunk.drawCoordinate(tens, ones, PlatMap.StreetLevel + 1, (platX == 0 && platZ == 0));
	}

}
