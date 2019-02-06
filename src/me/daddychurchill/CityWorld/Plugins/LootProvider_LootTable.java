package me.daddychurchill.CityWorld.Plugins;

import java.util.Arrays;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;

public class LootProvider_LootTable extends LootProvider {

	@SuppressWarnings("deprecation")
	@Override
	public void setLoot(CityWorldGenerator generator, Odds odds, String worldPrefix, LootLocation chestLocation,
			Block block) {
		Chest chest = (Chest) block.getState();

		// if random then lets see what we get out of it
		if (chestLocation == LootLocation.RANDOM) {
			chestLocation = Arrays.copyOfRange(LootLocation.values(), 2, LootLocation.values().length)[odds.getRandomInt(LootLocation.values().length - 1) + 1];
		}

		// special case for Empty
		if (chestLocation == LootLocation.EMPTY) {
			chest.setLootTable(Bukkit.getLootTable(NamespacedKey.minecraft("empty")));

		} else {
			String lootTable = "chests/" + generator.getWorld().getName().toLowerCase(Locale.ROOT) + "_" + chestLocation.name().toLowerCase(Locale.ROOT);

			// SIGH... I really wish this wasn't true
			// Bukkit doesn't seem to want us to use this API for custom namespaces, but doesn't offer any proper API solutions
			NamespacedKey key = new NamespacedKey("cityworld", lootTable);
			chest.setLootTable(Bukkit.getLootTable(key));
			chest.update();
		}
	}

	@Override
	public void saveLoots() {

	}

}
