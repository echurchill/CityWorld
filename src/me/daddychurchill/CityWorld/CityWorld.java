package me.daddychurchill.CityWorld;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

//This is just a test I'm not changing anything just to test. this text is worthless :D

//DONE Global.BedrockIsolation = obsidian or bedrock barriers (true)
//DONE Global.Plumbing = plumbing between street and underworld (true)
//DONE Global.Sewer = sewers between street (and plumbing) and underworld (true)
//DONE Global.Cistern = cisterns beneath parks (true)
//DONE Global.Basement = basements beneath buildings (true)
//WIP Global.StreetLevel = where the streets start (30)
//DONE Global.TreasureInSewer = treasure rooms in the sewer (true)
//DONE Global.TreasureInPlumbing = treasure in the Plumbing (true)
//DONE Global.SpawnersInSewer = sewers treasure rooms might have spawners (true)
//TODO Global.SpawnersInPlumbing = plumbing might have spawners (true)
//DONE Global.Underworld = underworld beneath the city (true)
//TODO clamp streetlevel this value to something sane!
//TODO if streetlevel gets too small turn off underworld, sewer, cistern and plumbing!
//TODO streetlevel controls the various constants found in PlatMap
//TODO move the constants in PlatMap to ContextUrban
//TODO "worldname".<option> support for world specific options

//DONE Command.CityWorld
//TODO Command.CityWorld Leave
//TODO Command.CityWorld Regenerate
//TODO Command.CityWorld Regenerate "PlatMapType"
//DONE player.hasPermission("CityWorld.CityWorldCommand") = CityWorld command enabled (true)
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
//DONE Sewers with iron bars instead of bricks sometimes
//TODO Sewers with vines coming down
//TODO Sewers with indents to remove the hallways aspect of them
//TODO Mob generators in Sewers.. maybe instead of treasure chests... sometimes
//TODO Treasure chests instead of chunks of ores in the sewers
//TODO Underworld with "noisy" terrain and ores

public class CityWorld extends JavaPlugin{
	
	public static final Logger log = Logger.getLogger("Minecraft.CityWorld");
   	
	private FileConfiguration config;
	
	private Material isolationMaterial;
	private boolean doPlumbing;
	private boolean doSewer;
	private boolean doCistern;
	private boolean doBasement;
	private boolean doUnderworld;
	private boolean doTreasureInSewer;
	private boolean doTreasureInPlumbing;
	private boolean doSpawnerInSewer;
	private int streetLevel;
	
	public final static boolean defaultBedrockIsolation = false;
	public final static boolean defaultDoPlumbing = true;
	public final static boolean defaultDoSewer = true;
	public final static boolean defaultDoCistern = true;
	public final static boolean defaultDoBasement = true;
	public final static boolean defaultDoUnderworld = true;
	public final static boolean defaultDoTreasureInSewer = true;
	public final static boolean defaultDoTreasureInPlumbing = true;
	public final static boolean defaultDoSpawnerInSewer = true;
	public final static int defaultStreetLevel = 30;
	
    public CityWorld() {
		super();
		
		setBedrockIsolation(defaultBedrockIsolation);
		setDoPlumbing(defaultDoPlumbing);
		setDoSewer(defaultDoSewer);
		setDoCistern(defaultDoCistern);
		setDoBasement(defaultDoBasement);
		setDoUnderworld(defaultDoUnderworld);
		setDoTreasureInSewer(defaultDoTreasureInSewer);
		setDoTreasureInPlumbing(defaultDoTreasureInPlumbing);
		setDoSpawnerInSewer(defaultDoSpawnerInSewer);
		setStreetLevel(defaultStreetLevel);
	}
    
    public void setBedrockIsolation(boolean doit) {
    	isolationMaterial = doit ? Material.BEDROCK : Material.OBSIDIAN;
    }
    
    public Material getIsolationMaterial() {
		return isolationMaterial;
	}

	public boolean isDoPlumbing() {
		return doPlumbing;
	}

	public void setDoPlumbing(boolean doit) {
		doPlumbing = doit;
	}

	public boolean isDoSewer() {
		return doSewer;
	}

	public void setDoSewer(boolean doit) {
		doSewer = doit;
	}

	public boolean isDoCistern() {
		return doCistern;
	}

	public void setDoCistern(boolean doit) {
		doCistern = doit;
	}

	public boolean isDoBasement() {
		return doBasement;
	}

	public void setDoBasement(boolean doit) {
		doBasement = doit;
	}

	public boolean isDoUnderworld() {
		return doUnderworld;
	}
	
	public void setDoUnderworld(boolean doit) {
		doUnderworld = doit;
	}

	public boolean isDoTreasureInSewer() {
		return doTreasureInSewer;
	}
	
	public void setDoTreasureInSewer(boolean doit) {
		doTreasureInSewer = doit;
	}

	public boolean isDoTreasureInPlumbing() {
		return doTreasureInPlumbing;
	}
	
	public void setDoTreasureInPlumbing(boolean doit) {
		doTreasureInPlumbing = doit;
	}

	public boolean isDoSpawnerInSewer() {
		return doSpawnerInSewer;
	}
	
	public void setDoSpawnerInSewer(boolean doit) {
		doSpawnerInSewer = doit;
	}

	public int getStreetLevel() {
		return streetLevel;
	}
	
	public void setStreetLevel(int value) {
		//TODO clamp streetlevel this value to something sane!
		//TODO if streetlevel gets too small turn off underworld, sewer, cistern and plumbing!
		streetLevel = value;
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String name, String style){
		return new CityWorldChunkGenerator(this, name, style);
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

		// add/get the configuration
		config = getConfig();
		config.options().header("CityWorld Global Options");
		config.addDefault("Global.BedrockIsolation", defaultBedrockIsolation);
		config.addDefault("Global.Plumbing", defaultDoPlumbing);
		config.addDefault("Global.Sewer", defaultDoSewer);
		config.addDefault("Global.Cistern", defaultDoCistern);
		config.addDefault("Global.Basement", defaultDoBasement);
		config.addDefault("Global.Underworld", defaultDoUnderworld);
		config.addDefault("Global.TreasureInSewer", defaultDoTreasureInSewer);
		config.addDefault("Global.TreasureInPlumbing", defaultDoTreasureInPlumbing);
		config.addDefault("Global.SpawnerInSewer", defaultDoSpawnerInSewer);
		config.addDefault("Global.StreetLevel", defaultStreetLevel);
		config.options().copyDefaults(true);
		saveConfig();
		
		// now read out the bits for real
		setBedrockIsolation(config.getBoolean("Global.BedrockIsolation"));
		setDoPlumbing(config.getBoolean("Global.Plumbing"));
		setDoSewer(config.getBoolean("Global.Sewer"));
		setDoCistern(config.getBoolean("Global.Cistern"));
		setDoBasement(config.getBoolean("Global.Basement"));
		setDoUnderworld(config.getBoolean("Global.Underworld"));
		setDoTreasureInSewer(config.getBoolean("Global.TreasureInSewer"));
		setDoTreasureInPlumbing(config.getBoolean("Global.TreasureInPlumbing"));
		setDoSpawnerInSewer(config.getBoolean("Global.SpawnerInSewer"));
		setStreetLevel(config.getInt("Global.StreetLevel"));
		
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

