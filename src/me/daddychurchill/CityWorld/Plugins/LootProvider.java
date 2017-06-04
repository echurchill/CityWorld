package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plugins.PhatLoot.LootProvider_Phat;
import me.daddychurchill.CityWorld.Support.MaterialList;
import me.daddychurchill.CityWorld.Support.Odds;

public abstract class LootProvider extends Provider {

	public enum LootLocation {EMPTY, RANDOM, SEWER, MINE, BUNKER, BUILDING, WAREHOUSE, FOOD, STORAGESHED, FARMWORKS, FARMWORKSOUTPUT, 
		WOODWORKS, WOODWORKSOUTPUT, STONEWORKS, STONEWORKSOUTPUT};
	
	public abstract void setLoot(CityWorldGenerator generator, Odds odds, String worldPrefix, LootLocation chestLocation, Block block);
	public abstract void saveLoots();

	public static LootProvider loadProvider(CityWorldGenerator generator) {
		// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)

		LootProvider provider = null;
		
		// try PhatLoots...
		provider = LootProvider_Phat.loadPhatLoots(generator);
		
		// default to stock LootProvider
		if (provider == null) {
			provider = new LootProvider_Normal();
		}
	
		return provider;
	}
	
	protected ItemStack[] pickFromTreasures(MaterialList materials, Odds odds, int maxCount, int maxStack) {
		int count = maxCount > 0 ? odds.getRandomInt(maxCount) + 1 : 0;
		
		// make room
		ItemStack[] items = new ItemStack[count];
		
		// populate
		for (int i = 0; i < count; i++) {
			items[i] = new ItemStack(materials.getRandomMaterial(odds), odds.getRandomInt(maxStack) + 1);
		}
		
		// all done
		return items;
	}
	
}
