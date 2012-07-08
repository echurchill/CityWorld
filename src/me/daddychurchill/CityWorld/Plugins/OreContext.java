package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class OreContext {

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	
	private WorldGenerator generator;
	private OreProvider provider;
	private String name;

	// world?
	
	public OreContext(WorldGenerator generator, OreProvider provider, String name) {
		this.generator = generator;
		this.provider = provider;
		this.name = name;
	}

	public void sprinkleOres(RealChunk chunk) {
		provider.sprinkleOres(generator, chunk, name);
	}

}
