package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;

import org.bukkit.entity.EntityType;

public class SpawnContext {

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	
	private WorldGenerator generator;
	private SpawnProvider provider;
	private String name;

	// world?
	
	public SpawnContext(WorldGenerator generator, SpawnProvider provider, String name) {
		this.generator=generator;
		this.provider=provider;
		this.name=name;
	}

	public EntityType getEntity() {
		return provider.getEntity(generator, name);
	}
}
