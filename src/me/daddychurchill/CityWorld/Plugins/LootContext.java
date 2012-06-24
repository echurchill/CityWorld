package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.inventory.ItemStack;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plugins.LootProvider;

public class LootContext {

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	
	private WorldGenerator generator;
	private LootProvider provider;
	private String name;

	// world?
	
	public LootContext(WorldGenerator generator, LootProvider provider, String name) {
		this.generator = generator;
		this.provider = provider;
		this.name = name;
	}

	public ItemStack[] getItems() {
		return provider.getItems(generator, name);
	}

}
