package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plugins.PhatLoot.LootProvider_Phat;
import me.daddychurchill.CityWorld.Support.Odds;

public abstract class LootProvider extends Provider {

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	
	public enum LootLocation {SEWER, MINE, BUNKER, STORAGESHED};
	
	public void setLoot(Odds odds, LootLocation chestLocation, Block block) {}

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

	protected ItemStack[] createTreasures(Odds odds, Material minTreasure, Material maxTreasure, int maxCount, int maxStack) {
		int minId = minTreasure.getId();
		int maxId = maxTreasure.getId();
		int rangeId = maxId - minId + 1;
		int count = maxCount > 0 ? odds.getRandomInt(maxCount) + 1 : 0;
		
		// make room
		ItemStack[] items = new ItemStack[count];
		
		// populate
		for (int i = 0; i < count; i++)
			items[i] = new ItemStack(odds.getRandomInt(rangeId) + minId, odds.getRandomInt(maxStack) + 1);
		
		// all done
		return items;
	}
}
