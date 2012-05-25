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

//DONE Global.BedrockIsolation = obsidian or bedrock barriers (true)
//DONE Global.Plumbing = plumbing between street and underworld (true)
//DONE Global.Sewer = sewers between street (and plumbing) and underworld (true)
//DONE Global.Cistern = cisterns beneath parks (true)
//DONE Global.Basement = basements beneath buildings (true)
//DONE Global.Underworld = underworld beneath the city (true)
//DONE Global.TreasureInFountain = treasure ores in the Fountains (true)
//DONE Global.TreasureInPlumbing = treasure blocks in the Plumbing (true)
//DONE Global.OresInSewer = ore vaults in sewer (true)
//DONE Global.OresInUnderworld = ore vaults in underworld (true)
//DONE Global.TreasureInSewer = treasure chests in the sewer (true)
//DONE Global.SpawnersInSewer = sewers treasure rooms might have spawners (true)
//DONE Global.StreetLevel = where the streets start (30)
//DONE Global.MaximumFloors = tallest building (100)
//TODO Global.SpawnersInPlumbing = plumbing might have spawners (??)
//TODO clamp streetlevel this value to something sane!
//TODO if streetlevel gets too small turn off underworld, sewer, cistern and plumbing!
//TODO streetlevel controls the various constants found in PlatMap
//TODO move the constants in PlatMap to ContextUrban
//TODO "worldname".<option> support for world specific options

//DONE Command.CityWorld
//TODO Command.CityWorld Leave
//TODO Command.CityWorld Regenerate
//TODO Command.CityWorld Regenerate "PlatMapType"
//DONE player.hasPermission("cityworld.command") = CityWorld command enabled (true)
//TODO player.hasPermission("cityworld.cityblock") = CityWorld command block regeneration option enabled (true)

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
//TODO Sewer treasure chests should be limited in what they can "auto-populate" with
//TODO Mob generators in Sewers.. maybe instead of treasure chests... sometimes
//TODO Treasure chests instead of chunks of ores in the sewers
//TODO Underworld with "noisy" terrain and ores

public class CityWorld extends JavaPlugin{
	
	public static final Logger log = Logger.getLogger("Minecraft.CityWorld");
   	
	private Material isolationMaterial;
	private boolean doPlumbing;
	private boolean doSewer;
	private boolean doCistern;
	private boolean doBasement;
	private boolean doUnderworld;
	private boolean doTreasureInSewer;
	private boolean doTreasureInPlumbing;
	private boolean doTreasureInFountain;
	private boolean doSpawnerInSewer;
	private boolean doOresInSewer;
	private boolean doOresInUnderworld;
	private int streetLevel;
	private int maximumFloors;
	
	public final static boolean defaultBedrockIsolation = true;
	public final static boolean defaultDoPlumbing = true;
	public final static boolean defaultDoSewer = true;
	public final static boolean defaultDoCistern = true;
	public final static boolean defaultDoBasement = true;
	public final static boolean defaultDoUnderworld = true;
	public final static boolean defaultDoTreasureInSewer = true;
	public final static boolean defaultDoTreasureInPlumbing = true;
	public final static boolean defaultDoTreasureInFountain = true;
	public final static boolean defaultDoSpawnerInSewer = true;
	public final static boolean defaultDoOresInSewer = true;
	public final static boolean defaultDoOresInUnderworld = true;
	public final static int defaultStreetLevel = 30;
	public final static int defaultMaximumFloors = 20;
	
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
		setDoTreasureInFountain(defaultDoTreasureInFountain);
		setDoSpawnerInSewer(defaultDoSpawnerInSewer);
		setDoOresInSewer(defaultDoOresInSewer);
		setDoOresInUnderworld(defaultDoOresInUnderworld);
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

	public boolean isDoOresInUnderworld() {
		return doOresInUnderworld;
	}
	
	public void setDoOresInUnderworld(boolean doit) {
		doOresInUnderworld = doit;
	}

	public int getStreetLevel() {
		return streetLevel;
	}
	
	public void setStreetLevel(int value) {
		streetLevel = value;
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
		config.addDefault("Global.BedrockIsolation", defaultBedrockIsolation);
		config.addDefault("Global.Plumbing", defaultDoPlumbing);
		config.addDefault("Global.Sewer", defaultDoSewer);
		config.addDefault("Global.Cistern", defaultDoCistern);
		config.addDefault("Global.Basement", defaultDoBasement);
		config.addDefault("Global.Underworld", defaultDoUnderworld);
		config.addDefault("Global.TreasureInFountain", defaultDoTreasureInFountain);
		config.addDefault("Global.TreasureInPlumbing", defaultDoTreasureInPlumbing);
		config.addDefault("Global.TreasureInSewer", defaultDoTreasureInSewer);
		config.addDefault("Global.SpawnerInSewer", defaultDoSpawnerInSewer);
		config.addDefault("Global.OresInSewer", defaultDoOresInSewer);
		config.addDefault("Global.OresInUnderworld", defaultDoOresInUnderworld);
		config.addDefault("Global.StreetLevel", defaultStreetLevel);
		config.addDefault("Global.MaximumFloors", defaultMaximumFloors);
		config.options().copyDefaults(true);
		saveConfig();
		
		// now read out the bits for real
		setBedrockIsolation(config.getBoolean("Global.BedrockIsolation"));
		setDoPlumbing(config.getBoolean("Global.Plumbing"));
		setDoSewer(config.getBoolean("Global.Sewer"));
		setDoCistern(config.getBoolean("Global.Cistern"));
		setDoBasement(config.getBoolean("Global.Basement"));
		setDoUnderworld(config.getBoolean("Global.Underworld"));
		setDoTreasureInFountain(config.getBoolean("Global.TreasureInFountain"));
		setDoTreasureInPlumbing(config.getBoolean("Global.TreasureInPlumbing"));
		setDoTreasureInSewer(config.getBoolean("Global.TreasureInSewer"));
		setDoSpawnerInSewer(config.getBoolean("Global.SpawnerInSewer"));
		setDoOresInSewer(config.getBoolean("Global.OresInSewer"));
		setDoOresInUnderworld(config.getBoolean("Global.OresInUnderworld"));
		setStreetLevel(config.getInt("Global.StreetLevel"));
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
				worldcreator.seed(2631667543040264851L);
				worldcreator.generator(new WorldGenerator(this, WORLD_NAME, ""));
				cityWorldPrime = Bukkit.getServer().createWorld(worldcreator);
			}
		}
		return cityWorldPrime;
	}
}

