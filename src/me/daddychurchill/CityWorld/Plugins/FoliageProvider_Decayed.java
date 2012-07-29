package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;
import org.bukkit.TreeType;

public class FoliageProvider_Decayed extends FoliageProvider {
	
	public FoliageProvider_Decayed(Random random) {
		super(random);
	}
	
	@Override
	protected boolean likelyFlora(WorldGenerator generator, Random random) {
		return random.nextDouble() < oddsOfDarkFlora;
	}
	
	private final static int airId = Material.AIR.getId();
	
	@Override
	public boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, TreeType treeType) {
		if (likelyFlora(generator, random)) {
			return generateTree(chunk, random, x, y, z, treeType, logId, airId, airId);
		} else
			return false;
	}

	@Override
	public boolean generateFlora(WorldGenerator generator, RealChunk chunk, int x, int y, int z, FloraType floraType) {
		if (likelyFlora(generator, random)) {
			
			// no flowers in a decayed world
			switch (floraType) {
			case GRASS:
			case FERN:
				chunk.setBlock(x, y, z, Material.DEAD_BUSH);
				return true;
			}
		}
		return false;
	}
}
