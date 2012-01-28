package me.daddychurchill.CityWorld;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

//TODO Global.BedrockIsolation = obsidian or bedrock barriers (false)
//TODO Global.Plumbing = plumbing between street and underworld (true)
//TODO Global.Sewers = sewers between street (and plumbing) and underworld (true)
//TODO Global.Underworld = underworld beneath the city (true)
//TODO "worldname".<option> support for world specific options

//DONE Command.CityWorld
//TODO Command.CityWorld Leave
//TODO Command.CityWorld Regenerate
//TODO Command.CityWorld Regenerate "PlatMapType"
//TODO player.hasPermission("CityWorld.CityWorldCommand") = CityWorld command enabled (true)
//TODO player.hasPermission("CityWorld.CityWorldCommandLeave") = CityWorld command leave option enabled (true)
//TODO player.hasPermission("CityWorld.CityWorldCommandRegeneration") = CityWorld command block regeneration option enabled (true)

//TODO Dynamically load platmap "engines" from plugin/cityworld/*.platmaps
//TODO Autoregister platmap "generators" from code
//TODO Predictable platmaps types/seeds via noise instead of random
//TODO Oceans/lakes
//TODO Farms
//TODO Residential
//TODO Sewers more maze like
//TODO Sewers with levels
//TODO Sewers with iron bars instead of bricks sometimes
//TODO Sewers with vines coming down
//TODO Mob generators in Sewers
//TODO Treasure chests instead of chunks of ores
//TODO Underworld with "noisy" terrain and ores

public class CityWorld extends JavaPlugin{
	
	public static final Logger log = Logger.getLogger("Minecraft.CityWorld");
   	
    public CityWorld() {
		super();
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String name, String style){
		return new CityWorldChunkGenerator(this, name, style);
	}
	
	@Override
	public void onDisable() {
		log.info(getDescription().getFullName() + " has been disabled" );
	}

	@Override
	public void onEnable() {
		//PluginManager pm = getServer().getPluginManager();
		
		addCommand("cityworld", new CityWorldCreateCMD(this));
//		addCommand("cityblock", new CityWorldBlockCMD(this));

		// configFile can be retrieved via getConfig()
		log.info(getDescription().getFullName() + " is enabled" );
	}
	
	private void addCommand(String keyword, CommandExecutor exec) {
		PluginCommand cmd = getCommand(keyword);
		if (cmd == null || exec == null) {
			log.info("Cannot create command for " + keyword);
		} else {
			cmd.setExecutor(exec);
		}
	}
	
    // prime world support (loosely based on ExpansiveTerrain)
	public final static String WORLD_NAME = "CityWorld";
	private static World cityWorldPrime = null;
	public World getCityWorld() {
		
		// created yet?
		if (cityWorldPrime == null) {
			
			// built yet?
			cityWorldPrime = Bukkit.getServer().getWorld(WORLD_NAME);
			if (cityWorldPrime == null) {
				
				// if neither then create/build it!
				WorldCreator worldcreator = new WorldCreator(WORLD_NAME);
				worldcreator.environment(World.Environment.NORMAL);
				worldcreator.generator(new CityWorldChunkGenerator(this, WORLD_NAME, ""));
				cityWorldPrime = Bukkit.getServer().createWorld(worldcreator);
			}
		}
		return cityWorldPrime;
	}
}

