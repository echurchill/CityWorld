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
//DONE Treasure chests instead of chunks of ores in the sewers
//DONE Sewers with iron bars instead of bricks sometimes

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
//TODO Sewers more maze like
//TODO Sewers with levels
//TODO Sewers with vines coming down
//TODO Sewers with indents to remove the hallways aspect of them
//TODO Sewer treasure chests should be limited in what they can "auto-populate" with
//TODO Underworld with "noisy" terrain and ores

public class CityWorld extends JavaPlugin{
	
	public static final Logger log = Logger.getLogger("Minecraft.CityWorld");
   	
	private Material isolationMaterial;
	private boolean doSewer;
	private boolean doCistern;
	private boolean doBasement;
	private boolean doTreasureInSewer;
	private boolean doTreasureInFountain;
	private boolean doSpawnerInSewer;
	private boolean doOresInSewer;
	private boolean doWorkingLights;
	private int maximumFloors;
	
	public final static boolean defaultDoSewer = true;
	public final static boolean defaultDoCistern = true;
	public final static boolean defaultDoBasement = true;
	public final static boolean defaultDoTreasureInSewer = true;
	public final static boolean defaultDoTreasureInFountain = true;
	public final static boolean defaultDoSpawnerInSewer = true;
	public final static boolean defaultDoOresInSewer = true;
	public final static boolean defaultDoWorkingLights = false;
	public final static int defaultMaximumFloors = 20;
	
    public CityWorld() {
		super();
		
		setDoSewer(defaultDoSewer);
		setDoCistern(defaultDoCistern);
		setDoBasement(defaultDoBasement);
		setDoTreasureInSewer(defaultDoTreasureInSewer);
		setDoTreasureInFountain(defaultDoTreasureInFountain);
		setDoSpawnerInSewer(defaultDoSpawnerInSewer);
		setDoOresInSewer(defaultDoOresInSewer);
		setDoWorkingLights(defaultDoWorkingLights);
	}
    
    public void setBedrockIsolation(boolean doit) {
    	isolationMaterial = doit ? Material.BEDROCK : Material.OBSIDIAN;
    }
    
    public Material getIsolationMaterial() {
		return isolationMaterial;
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

	public boolean isDoTreasureInSewer() {
		return doTreasureInSewer;
	}
	
	public void setDoTreasureInSewer(boolean doit) {
		doTreasureInSewer = doit;
	}

	public boolean isDoTreasureInFountain() {
		return doTreasureInFountain;
	}
	
	public void setDoTreasureInFountain(boolean doit) {
		doTreasureInFountain = doit;
	}

	public boolean isDoSpawnerInSewer() {
		return doSpawnerInSewer;
	}
	
	public void setDoSpawnerInSewer(boolean doit) {
		doSpawnerInSewer = doit;
	}

	public boolean isDoOresInSewer() {
		return doOresInSewer;
	}
	
	public void setDoOresInSewer(boolean doit) {
		doOresInSewer = doit;
	}

	public boolean isDoWorkingLights() {
		return doWorkingLights;
	}
	
	public void setDoWorkingLights(boolean doit) {
		doWorkingLights = doit;
	}

	public int getMaximumFloors() {
		return maximumFloors;
	}
	
	public void setMaximumFloors(int value) {
		maximumFloors = value;
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

		// add/get the configuration
		FileConfiguration config = getConfig();
		config.options().header("CityWorld Global Options");
		config.addDefault("Global.Sewer", defaultDoSewer);
		config.addDefault("Global.Cistern", defaultDoCistern);
		config.addDefault("Global.Basement", defaultDoBasement);
		config.addDefault("Global.TreasureInFountain", defaultDoTreasureInFountain);
		config.addDefault("Global.TreasureInSewer", defaultDoTreasureInSewer);
		config.addDefault("Global.SpawnerInSewer", defaultDoSpawnerInSewer);
		config.addDefault("Global.OresInSewer", defaultDoOresInSewer);
		config.addDefault("Global.MaximumFloors", defaultMaximumFloors);
		config.options().copyDefaults(true);
		saveConfig();
		
		// now read out the bits for real
		setBedrockIsolation(config.getBoolean("Global.BedrockIsolation"));
		setDoSewer(config.getBoolean("Global.Sewer"));
		setDoCistern(config.getBoolean("Global.Cistern"));
		setDoBasement(config.getBoolean("Global.Basement"));
		setDoTreasureInFountain(config.getBoolean("Global.TreasureInFountain"));
		setDoTreasureInSewer(config.getBoolean("Global.TreasureInSewer"));
		setDoSpawnerInSewer(config.getBoolean("Global.SpawnerInSewer"));
		setDoOresInSewer(config.getBoolean("Global.OresInSewer"));
		setMaximumFloors(config.getInt("Global.MaximumFloors"));
		
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
			//worldcreator.seed(-5068588521833479712L); // nearby oil platform
			worldcreator.seed(-8052576251523963231L);
			worldcreator.generator(new WorldGenerator(this, WORLD_NAME, ""));
			cityWorldPrime = Bukkit.getServer().createWorld(worldcreator);
		}
		return cityWorldPrime;
	}
}

