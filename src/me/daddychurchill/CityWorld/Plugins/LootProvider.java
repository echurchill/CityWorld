package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.daddychurchill.CityWorld.WorldGenerator;

public abstract class LootProvider {

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	
	public enum LootLocation {SEWER, MINE, BUNKER, STORAGESHED, BANKVAULT};
	
	public abstract ItemStack[] getItems(WorldGenerator generator, Random random, LootLocation lootLocation);

	public static LootProvider loadProvider(WorldGenerator generator) {

		LootProvider provider = null;
		
		// try PhatLoots...
		provider = LootProvider_Phat.loadPhatLoots();
		
		// default to stock LootProvider
		if (provider == null) {
			provider = new LootProvider_Normal();
		}
	
		return provider;
	}

	protected ItemStack[] createTreasures(WorldGenerator generator, Random random, Material minTreasure, Material maxTreasure, int maxCount, int maxStack) {
		int minId = minTreasure.getId();
		int maxId = maxTreasure.getId();
		int rangeId = maxId - minId + 1;
		int count = maxCount > 0 ? random.nextInt(maxCount) + 1 : 0;
		
		// make room
		ItemStack[] items = new ItemStack[count];
		
		// populate
		for (int i = 0; i < count; i++)
			items[i] = new ItemStack(random.nextInt(rangeId) + minId, random.nextInt(maxStack) + 1);
		
		// all done
		return items;
	}
}
