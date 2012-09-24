package me.daddychurchill.CityWorld.Plugins.PhatLoot;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plugins.LootProvider;
import me.daddychurchill.CityWorld.Support.Odds;

import org.bukkit.inventory.ItemStack;

public class LootProvider_Phat extends LootProvider {
	
	public final static String chestInSewers = "CityWorld_Chest_Sewer";
	public final static String chestInMines = "CityWorld_Chest_Mine";
	public final static String chestInBunkers = "CityWorld_Chest_Bunker";
	public final static String chestInBankVault = "CityWorld_Chest_BankVault";
	public final static String chestInStorageShed = "CityWorld_Chest_StorageShed";
	
	@Override
	public ItemStack[] getItems(WorldGenerator generator, Odds odds, LootLocation lootLocation) {
		
		// which mix?
		switch (lootLocation) {
		case BUNKER:
			return getItemsByName(generator, chestInBunkers);
		case MINE:
			return getItemsByName(generator, chestInMines);
		case BANKVAULT:
			return getItemsByName(generator, chestInBankVault);
		case STORAGESHED:
			return getItemsByName(generator, chestInStorageShed);
		default: //case SEWER:
			return getItemsByName(generator, chestInSewers);
		}
	}

	public ItemStack[] getItemsByName(WorldGenerator generator, String name) {

		//https://github.com/Codisimus/PhatLoots
		
//		PhatLoot phatLoot;
//		if (!PhatLoots.hasPhatLoot(name)) {
//        	PhatLoots.addPhatLoot(new PhatLoot(name));        	
//        }
//		phatLoot = PhatLoots.getPhatLoot(name);
//		
//		phatLoot.chests.add(new PhatLootChest(block));
//		phatLoot.save();
		return null;
		
	}

//	private static String name = "PhatLoots";
	public static LootProvider loadPhatLoots() {

//		PhatLoots phatLoots = null;
//
//		PluginManager pm = Bukkit.getServer().getPluginManager();
//
//		try {
//			phatLoots = (PhatLoots) pm.getPlugin(name);
//		} catch (Exception e) {
//			CityWorld.reportException(String.format("[LootProvider] Bad Version %s.", name), e);
//		}
//
//		if (phatLoots == null)
			return null;
//
//		CityWorld.(String.format("[LootProvider] Found %s.", name));
//		
//		try {
//
//			if (!pm.isPluginEnabled(phatLoots)) {
//				CityWorld.reportMessage(String.format("[LootProvider] Enabling %s.", name));
//				pm.enablePlugin(phatLoots);
//			}
//			CityWorld.reportMessage(String.format("[LootProvider] %s Enabled.", name));
//			
//			return new LootProvider_PhatLoots();
//			
//		} catch (Exception e) {
//			CityWorld.reportException(String.format("[LootProvider] Failed to enable %s.", name), e);
//			return null;
//		}
	}
}
