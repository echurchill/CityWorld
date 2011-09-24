package me.daddychurchill.CityWorld;

import java.util.logging.Logger;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class CityWorld extends JavaPlugin{

	protected Logger log = Logger.getLogger("Minecraft");
	
	@Override
	public void onDisable() {
		log.info("CityWorld has been disabled");
		
	}

	@Override
	public void onEnable() {
		log.info("CityWorld has been enabled");
		
	}

	
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id){
		return new CityWorldGenerator(this);
	}
}
