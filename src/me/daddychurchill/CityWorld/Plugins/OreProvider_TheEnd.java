package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;
import org.bukkit.block.Biome;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class OreProvider_TheEnd extends OreProvider {
	
	public final static byte endStoneId = (byte) Material.ENDER_STONE.getId();
	
	public OreProvider_TheEnd(WorldGenerator generator) {
		super(generator);

		subsurfaceId = endStoneId;
		stratumId = endStoneId;
	}

	@Override
	public String getCollectionName() {
		return "TheEnd";
	}

	@Override
	public Biome remapBiome(Biome biome) {
		return Biome.SKY;
	}

	@Override
	public void sprinkleOres(WorldGenerator generator, PlatLot lot, RealChunk chunk, CachedYs blockYs, Odds odds, OreLocation location) {
		// no ores here!
	}
}
