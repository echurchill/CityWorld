package me.daddychurchill.CityWorld.Plugins;
import me.daddychurchill.CityWorld.Support.Odds;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LootProvider_Normal extends LootProvider {

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	public void setLoot(Odds odds, LootLocation lootLocation, Block block) {
		Chest chest = (Chest) block.getState();
		Inventory inv = chest.getInventory();
		inv.clear();
		ItemStack[] items = getLoot(odds, lootLocation, block);
		inv.addItem(items);
		chest.update(true);
	}
	
	private ItemStack[] getLoot( Odds odds, LootLocation lootLocation, Block block) {

		// which mix?
		switch (lootLocation) {
		case BUNKER:
			return createTreasures( odds, Material.IRON_SWORD, Material.GOLD_BOOTS, 2, 1);
		case MINE:
			return createTreasures( odds, Material.FLINT, Material.ROTTEN_FLESH, 3, 1);
		case SEWER:
			return createTreasures( odds, Material.IRON_SPADE, Material.COAL, 3, 2);
		case STORAGESHED:
			return createTreasures( odds, Material.IRON_SPADE, Material.IRON_AXE, 2, 1);
		default:
			return createTreasures( odds, Material.IRON_SPADE, Material.IRON_SPADE, 0, 0);
		}
	}
	
}
