package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LootProvider_Normal extends LootProvider {

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	
	@Override
	public ItemStack[] getItems(WorldGenerator generator, Random random, LootLocation lootLocation) {

		// which mix?
		switch (lootLocation) {
		case BUNKER:
			return createTreasures(generator, random, Material.IRON_SWORD, Material.GOLD_BOOTS, 2, 1);
		case MINE:
			return createTreasures(generator, random, Material.FLINT, Material.ROTTEN_FLESH, 3, 1);
		case SEWER:
			return createTreasures(generator, random, Material.IRON_SPADE, Material.COAL, 3, 2);
		case STORAGESHED:
			return createTreasures(generator, random, Material.IRON_SPADE, Material.IRON_AXE, 2, 1);
		case BANKVAULT:
			return createTreasures(generator, random, Material.DIAMOND, Material.GOLD_INGOT, 2, 10);
		default:
			return createTreasures(generator, random, Material.IRON_SPADE, Material.IRON_SPADE, 0, 0);
		}
	}
}
