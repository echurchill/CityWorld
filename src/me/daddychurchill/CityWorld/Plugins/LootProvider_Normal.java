package me.daddychurchill.CityWorld.Plugins;
import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;

import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LootProvider_Normal extends LootProvider {
	
	@Override
	public void setLoot(CityWorldGenerator generator, Odds odds, String worldPrefix, LootLocation lootLocation, Block block) {
		Chest chest = (Chest) block.getState();
		Inventory inv = chest.getInventory();
		inv.clear();
		ItemStack[] items = getLoot(generator, odds, lootLocation, block);
		if (items != null)
			inv.addItem(items);
		chest.update(true);
	}
	
	@Override
	public void saveLoots() {
		// we don't need to do anything
	}
	
	private ItemStack[] getLoot(CityWorldGenerator generator, Odds odds, LootLocation lootLocation, Block block) {
		
		// which mix?
		switch (lootLocation) {
		case EMPTY:
			return null;
		case BUNKER:
			return pickFromTreasures(generator.materialProvider.itemsRandomMaterials_BunkerChests, odds, 3, 2);
		case MINE:
			return pickFromTreasures(generator.materialProvider.itemsRandomMaterials_MineChests, odds, 3, 2);
		case SEWER:
			return pickFromTreasures(generator.materialProvider.itemsRandomMaterials_SewerChests, odds, 3, 2);
		case BUILDING:
			return pickFromTreasures(generator.materialProvider.itemsRandomMaterials_BuildingChests, odds, 5, 3);
		case WAREHOUSE:
			return pickFromTreasures(generator.materialProvider.itemsRandomMaterials_WarehouseChests, odds, 1, 32);
		case FOOD:
			return pickFromTreasures(generator.materialProvider.itemsRandomMaterials_FoodChests, odds, 5, 3);
		case STORAGESHED:
			return pickFromTreasures(generator.materialProvider.itemsRandomMaterials_StorageShedChests, odds, 3, 2);
		case FARMWORKS:
			return pickFromTreasures(generator.materialProvider.itemsRandomMaterials_FarmChests, odds, 4, 2);
		case FARMWORKSOUTPUT:
			return pickFromTreasures(generator.materialProvider.itemsRandomMaterials_FarmOutputChests, odds, 8, 6);
		case WOODWORKS:
			return pickFromTreasures(generator.materialProvider.itemsRandomMaterials_LumberChests, odds, 4, 2);
		case WOODWORKSOUTPUT:
			return pickFromTreasures(generator.materialProvider.itemsRandomMaterials_LumberOutputChests, odds, 8, 6);
		case STONEWORKS:
			return pickFromTreasures(generator.materialProvider.itemsRandomMaterials_QuaryChests, odds, 4, 2);
		case STONEWORKSOUTPUT:
			return pickFromTreasures(generator.materialProvider.itemsRandomMaterials_QuaryOutputChests, odds, 10, 6);
		case RANDOM:
			lootLocation = LootLocation.values()[odds.getRandomInt(LootLocation.values().length - 1) + 1];
			return getLoot(generator, odds, lootLocation, block);
		}
		
		throw new IllegalArgumentException(); 
	}
	
}
