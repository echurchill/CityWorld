package me.daddychurchill.CityWorld.Plugins;
import me.daddychurchill.CityWorld.Support.Odds;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LootProvider_Normal extends LootProvider {

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	@Override
	public void setLoot(Odds odds, String worldPrefix, LootLocation lootLocation, Block block) {
		Chest chest = (Chest) block.getState();
		Inventory inv = chest.getInventory();
		inv.clear();
		ItemStack[] items = getLoot(odds, lootLocation, block);
		inv.addItem(items);
		chest.update(true);
	}
	
	@Override
	public void saveLoots() {
		// we don't need to do anything
	}
	
	private ItemStack[] getLoot(Odds odds, LootLocation lootLocation, Block block) {
		
		// roll the dice
		if (lootLocation == LootLocation.RANDOM) {
			
			// skip the first one
			lootLocation = LootLocation.values()[odds.getRandomInt(LootLocation.values().length - 1) + 1];
		}

		// which mix?
		switch (lootLocation) {
		case BUNKER:
			return createTreasures(odds, Material.IRON_SWORD, Material.GOLD_BOOTS, 2, 1);
		case MINE:
			return createTreasures(odds, Material.FLINT, Material.ROTTEN_FLESH, 3, 1);
		case SEWER:
			return createTreasures(odds, Material.IRON_SPADE, Material.COAL, 3, 2);
		case STORAGESHED:
			return pickFromTreasures(odds, 3, 2, 
					Material.IRON_SPADE, 
					Material.WOOD_SPADE, 
					Material.IRON_PICKAXE,
					Material.WOOD_PICKAXE,
					Material.IRON_SWORD,
					Material.WOOD_SWORD,
					Material.TORCH,
					Material.IRON_HELMET,
					Material.COOKED_MUTTON);
		case FARMWORKS:
			return pickFromTreasures(odds, 4, 3, 
					Material.IRON_HOE, 
					Material.WOOD_HOE, 
					Material.BOW,
					Material.ARROW,
					Material.TORCH,
					Material.SHEARS,
					Material.WATER_BUCKET,
					Material.SEEDS,
					Material.PUMPKIN_SEEDS,
					Material.MELON_SEEDS,
					Material.CARROT_ITEM,
					Material.POTATO_ITEM);
		case WOODWORKS:
			return pickFromTreasures(odds, 4, 3, 
					Material.STONE_AXE, 
					Material.WOOD_AXE, 
					Material.STONE_SPADE, 
					Material.WOOD_SPADE, 
					Material.STONE_SWORD,
					Material.WOOD_SWORD,
					Material.TORCH,
					Material.STRING,
					Material.FISHING_ROD,
					Material.LEATHER,
					Material.COOKED_MUTTON,
					Material.COOKED_FISH);
		case STONEWORKS:
			return pickFromTreasures(odds, 3, 2, 
					Material.IRON_SPADE, 
					Material.STONE_SPADE, 
					Material.IRON_PICKAXE,
					Material.STONE_PICKAXE,
					Material.IRON_SWORD,
					Material.STONE_SWORD,
					Material.TORCH,
					Material.BUCKET,
					Material.COOKED_BEEF,
					Material.COOKED_CHICKEN);
		case STONEWORKSOUTPUT:
			return pickFromTreasures(odds, 6, 3, 
					Material.IRON_INGOT, // easy but stupid way to increase odds of some of these happening
					Material.IRON_INGOT,
					Material.IRON_INGOT,
					Material.IRON_INGOT,
					Material.IRON_INGOT,
					Material.IRON_INGOT,
					Material.IRON_INGOT,
					Material.COAL,
					Material.COAL,
					Material.COAL,
					Material.COAL,
					Material.COAL,
					Material.COAL,
					Material.COAL,
					Material.GOLD_INGOT,
					Material.GOLD_INGOT,
					Material.GOLD_INGOT,
					Material.REDSTONE,
					Material.REDSTONE,
					Material.REDSTONE,
					Material.DIAMOND,
					Material.EMERALD);
		default:
			return createTreasures(odds, Material.IRON_SPADE, Material.IRON_SPADE, 0, 0);
		}
	}
	
}
