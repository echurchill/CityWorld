package me.daddychurchill.CityWorld;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

//DONE Global.Sewer = sewers between street (and plumbing) and underworld (true)
//DONE Global.Cistern = cisterns beneath parks (true)
//DONE Global.Basement = basements beneath buildings (true)
//DONE Global.TreasureInFountain = treasure ores in the Fountains (true)
//DONE Global.OresInSewer = ore vaults in sewer (true)
//DONE Global.TreasureInSewer = treasure chests in the sewer (true)
//DONE Global.SpawnersInSewer = sewers treasure rooms might have spawners (true)
//DONE Global.MaximumFloors = tallest building (100)
//DONE Oceans/lakes
//DONE Farms
//DONE Residential
//DONE Predictable platmaps types/seeds via noise instead of random
//DONE Mob generators in Sewers.. maybe instead of treasure chests... sometimes
//DONE Sewers with iron bars instead of bricks sometimes
//DONE Sewers more maze like
//DONE Sewers with vines coming down
//DONE Sewers with indents to remove the hallways aspect of them
//DONE Sewer treasure chests should be limited in what they can "auto-populate" with

//TODO "worldname".<option> support for world specific options
//DONE Command.CityWorld
//TODO Command.CityWorld Leave
//TODO Command.CityWorld Regenerate
//TODO Command.CityWorld Regenerate "PlatMapType"
//DONE player.hasPermission("cityworld.command") = CityWorld command enabled (true)
//TODO player.hasPermission("cityworld.cityblock") = CityWorld command block regeneration option enabled (true)

//TODO Add central park context
//TODO Dynamically load platmap "engines" from plugin/cityworld/*.platmaps
//TODO Autoregister platmap "generators" from code
//TODO Sewers with levels
//TODO Underworld with "noisy" terrain and ores

public class CityWorld extends JavaPlugin{
	
	public static final Logger log = Logger.getLogger("Minecraft.CityWorld");
   	
    public CityWorld() {
		super();
		
	}
    
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String name, String style){
		return new WorldGenerator(this, name, style);
	}
	
	@Override
	public void onDisable() {
		// remember for the next time
		saveConfig();
		
		// tell the world we are out of here
		log.info(getDescription().getFullName() + " has been disabled" );
	}

	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.addPermission(new Permission("cityworld.command", PermissionDefault.OP));
		
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
	public World getCityWorld() {
		
		// built yet?
		World cityWorldPrime = Bukkit.getServer().getWorld(WORLD_NAME);
		if (cityWorldPrime == null) {
			
			// if neither then create/build it!
			WorldCreator worldcreator = new WorldCreator(WORLD_NAME);
			worldcreator.environment(World.Environment.NORMAL);
			//worldcreator.seed(1989196586659682103L); 
			worldcreator.generator(new WorldGenerator(this, WORLD_NAME, ""));
			cityWorldPrime = Bukkit.getServer().createWorld(worldcreator);
		}
		return cityWorldPrime;
	}
}

