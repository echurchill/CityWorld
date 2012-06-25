package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LootProvider_Default extends LootProvider {

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	
	@Override
	public ItemStack[] getItems(WorldGenerator generator, String name) {
		Random random = generator.getStashRandomGenerator();

		// which mix?
		if (name.equals(chestInSewers))
			return createTreasures(random, Material.IRON_SPADE, Material.COAL, 5, 2);
			
		else if (name.equals(chestInMines))
			return createTreasures(random, Material.FLINT, Material.ROTTEN_FLESH, 5, 1);
			
		else //if (name == chestInBunkers) {
			return createTreasures(random, Material.IRON_SWORD, Material.GOLD_BOOTS, 2, 1);
	}
	
	private ItemStack[] createTreasures(Random random, Material minTreasure, Material maxTreasure, int maxCount, int maxStack) {
		int minId = minTreasure.getId();
		int maxId = maxTreasure.getId();
		int range = maxId - minId;
		int count = random.nextInt(maxCount) + 1;
		
		// make room
		ItemStack[] items = new ItemStack[count];
		
		// populate
		for (int i = 0; i < count; i++)
			items[i] = new ItemStack(random.nextInt(range) + minId, random.nextInt(maxStack) + 1);
		
		// all done
		return items;
	}
}
