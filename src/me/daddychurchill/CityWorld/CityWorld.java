package me.daddychurchill.CityWorld;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class CityWorld extends JavaPlugin{
    public static final Logger log = Logger.getLogger("Minecraft.CityWorld");
   	
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
		addCommand("cityworlddebug", new CityWorldDebugCMD(this));

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
	
	// debugging support (from HaxtorMoogle) (eddie: I am still fuzzy on what this is for)
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
	public boolean isDebugging(Player player) 
	{
		return debugees.containsKey(player) ? debugees.get(player) : false;
    }

    public void setDebugging(Player player, boolean value) 
    {
        debugees.put(player, value);
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

