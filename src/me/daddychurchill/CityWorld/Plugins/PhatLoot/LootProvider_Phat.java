package me.daddychurchill.CityWorld.Plugins.PhatLoot;

import java.util.HashSet;
import java.util.Iterator;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plugins.LootProvider;
import me.daddychurchill.CityWorld.Support.Odds;

import com.codisimus.plugins.phatloots.*;

import org.bukkit.Bukkit;
import org.bukkit.plugin.*;
import org.bukkit.block.Block;

public class LootProvider_Phat extends LootProvider {
	
	private HashSet<PhatLoot> phatLoots;
	public LootProvider_Phat() {
		phatLoots = new HashSet<PhatLoot>();
	}
	
	@Override
	public void setLoot(CityWorldGenerator generator, Odds odds, String worldPrefix, LootLocation lootLocation, Block block) {
		String name = worldPrefix + "_" + lootLocation;
		PhatLoot phatLoot = getByName(name);
		phatLoot.addChest(block);
		
		// remember it!
		if (!phatLoots.contains(phatLoot))
			phatLoots.add(phatLoot);
	}
	
	@Override
	public void saveLoots() {
		
		// something to do?
		if (!phatLoots.isEmpty()) {
			
			// save everything
			Iterator<PhatLoot> aPhatLoot = phatLoots.iterator();
			while (aPhatLoot.hasNext()) {
				PhatLoot loot = aPhatLoot.next();
				
				// sometimes hasNext is true but there really isn't a next... go figure
				// reported by EODStevens on SpigotMC.org
				if (loot != null)
					loot.saveChests();
			}
			
			// for get about it!
			phatLoots.clear();
		}
	}
	
	private static PhatLoot getByName(String name){
		PhatLoot phatLoot;
		
		if (!PhatLoots.hasPhatLoot(name)) {
        	PhatLoots.addPhatLoot(new PhatLoot(name));    	
        }
		
		phatLoot = PhatLoots.getPhatLoot(name);
		phatLoot.save();
		
		return phatLoot;
	}

	private static String name = "PhatLoots";
	public static LootProvider loadPhatLoots(CityWorldGenerator generator) {

		PhatLoots phatLoots = null;

		try {

			PluginManager pm = Bukkit.getServer().getPluginManager();
			if (pm != null) {
				Plugin plugin = pm.getPlugin(name);
				if (plugin != null)
					phatLoots = (PhatLoots) plugin;
			}
	
			if (phatLoots == null) {
				generator.reportMessage("[PasteProvider] Problem loading PhatLoots, could not find it");
				return null;
			}
	
			//CityWorld.(String.format("[LootProvider] Found %s.", name));
		
			if (!pm.isPluginEnabled(phatLoots)) {
				//CityWorld.reportMessage(String.format("[LootProvider] Enabling %s.", name));
				pm.enablePlugin(phatLoots);
			}
			//CityWorld.reportMessage(String.format("[LootProvider] %s Enabled.", name));
			
			return new LootProvider_Phat();
			
		} catch (Exception e) {
			generator.reportMessage("[LootProvider] Problem loading PhatLoots (" + e.getMessage() + ")");
			return null;
		}
	}
}
