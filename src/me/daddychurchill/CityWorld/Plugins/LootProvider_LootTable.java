package me.daddychurchill.CityWorld.Plugins;

import java.util.Arrays;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.loot.LootTable;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;

public class LootProvider_LootTable extends LootProvider {

	// Bukkit doesn't seem to want us to use this API for custom namespaces, but doesn't offer any proper API solutions
	@SuppressWarnings("deprecation")
	@Override
	public void setLoot(CityWorldGenerator generator, Odds odds, String worldPrefix, LootLocation chestLocation,
			Block block) {
		Chest chest = (Chest) block.getState();
		if (chestLocation == LootLocation.RANDOM) {
			// Exclude EMPTY and RANDOM
			chestLocation = Arrays.copyOfRange(LootLocation.values(), 2, LootLocation.values().length)[odds.getRandomInt(LootLocation.values().length - 1) + 1];
		}
		if (chestLocation == LootLocation.EMPTY) {
			chest.setLootTable(Bukkit.getLootTable(NamespacedKey.minecraft("empty")));
		}
		else {
			String lootTable = "chests/" + chestLocation.name().toLowerCase(Locale.ROOT);
			NamespacedKey key = new NamespacedKey("cityworld", lootTable);
			chest.setLootTable(Bukkit.getLootTable(key));
			chest.update();
		}
	}

	@Override
	public void saveLoots() {

	}

}
