package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LootProvider_Default extends LootProvider {

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	
	private final static int minTreasureId = Material.IRON_SPADE.getId();
	private final static int maxTreasureId = Material.ROTTEN_FLESH.getId();
	private final static int countTreasureIds = maxTreasureId - minTreasureId;
	private final static int maxTreasureCount = 5;

	@Override
	public ItemStack[] getItems(WorldGenerator generator, String name) {
		//CityWorldSettings settings = generator.getSettings();
		Random random = generator.getStashRandomGenerator();

		// how many items are we talking about?
		int treasureCount = random.nextInt(maxTreasureCount) + 1;
		ItemStack[] items = new ItemStack[treasureCount];
		
		// which mix?
		if (name == chestInSewer) {
			
			// fabricate the SewerVault treasures
			for (int i = 0; i < treasureCount; i++) {
				items[i] = new ItemStack(random.nextInt(countTreasureIds) + minTreasureId, random.nextInt(2) + 1);
			}
			
		} else if (name == chestInMine) {
			
		} else if (name == chestInBunker) {
			
		}
		return items;
	}
}
