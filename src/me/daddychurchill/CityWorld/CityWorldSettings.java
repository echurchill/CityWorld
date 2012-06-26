package me.daddychurchill.CityWorld;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class CityWorldSettings {

	public boolean includeSewers = true;
	public boolean includeCisterns = true;
	public boolean includeBasements = true;
	public boolean includeMines = true;
	public boolean includeBunkers = true;
	public boolean includeOres = true;
	public boolean includeCaves = true;
	public boolean includeBuildings = true;
	public boolean treasuresInSewers = true;
	public boolean spawnersInSewers = true;
	public boolean treasuresInMines = true;
	public boolean spawnersInMines = true;
	public boolean treasuresInBunkers = true;
	public boolean spawnersInBunkers = true;
	public boolean workingLights = true;
	
	private final static String tagIncludeSewers = "IncludeSewers";
	private final static String tagIncludeCisterns = "IncludeCisterns";
	private final static String tagIncludeBasements = "IncludeBasements";
	private final static String tagIncludeMines = "IncludeMines";
	private final static String tagIncludeBunkers = "IncludeBunkers";
	private final static String tagIncludeOres = "IncludeOres";
	private final static String tagIncludeCaves = "IncludeCaves";
	private final static String tagIncludeBuildings = "IncludeBuildings";
	private final static String tagTreasuresInSewers = "TreasuresInSewers";
	private final static String tagSpawnersInSewers = "SpawnersInSewers";
	private final static String tagTreasuresInMines = "TreasuresInMines";
	private final static String tagSpawnersInMines = "SpawnersInMines";
	private final static String tagTreasuresInBunkers = "TreasuresInBunkers";
	private final static String tagSpawnersInBunkers = "SpawnersInBunkers";
	private final static String tagWorkingLights = "WorkingLights";
	
	public CityWorldSettings(CityWorld plugin, String worldname) {
		super();
		
		// add/get the configuration
		FileConfiguration config = plugin.getConfig();
		config.options().header("CityWorld Plugin Options");
		config.addDefault(tagIncludeSewers, includeSewers);
		config.addDefault(tagIncludeCisterns, includeCisterns);
		config.addDefault(tagIncludeBasements, includeBasements);
		config.addDefault(tagIncludeMines, includeMines);
		config.addDefault(tagIncludeBunkers, includeBunkers);
		config.addDefault(tagIncludeOres, includeOres);
		config.addDefault(tagIncludeCaves, includeCaves);
		config.addDefault(tagIncludeBuildings, includeBuildings);
		config.addDefault(tagTreasuresInSewers, treasuresInSewers);
		config.addDefault(tagSpawnersInSewers, spawnersInSewers);
		config.addDefault(tagTreasuresInMines, treasuresInMines);
		config.addDefault(tagSpawnersInMines, spawnersInMines);
		config.addDefault(tagTreasuresInBunkers, treasuresInBunkers);
		config.addDefault(tagSpawnersInBunkers, spawnersInBunkers);
		config.addDefault(tagWorkingLights, workingLights);
		config.options().copyDefaults(true);
		config.options().copyHeader(true);
		
		// get the right section
		ConfigurationSection section = config.getConfigurationSection(worldname);
		if (section != null) {
			includeSewers = section.getBoolean(tagIncludeSewers);
			includeCisterns = section.getBoolean(tagIncludeSewers);
			includeBasements = section.getBoolean(tagIncludeSewers);
			includeMines = section.getBoolean(tagIncludeMines);
			includeBunkers = section.getBoolean(tagIncludeBunkers);
			includeOres = section.getBoolean(tagIncludeOres);
			includeCaves = section.getBoolean(tagIncludeCaves);
			includeBuildings = section.getBoolean(tagIncludeBuildings);
			treasuresInSewers = section.getBoolean(tagIncludeSewers);
			spawnersInSewers = section.getBoolean(tagIncludeSewers);
			treasuresInMines = section.getBoolean(tagIncludeMines);
			spawnersInMines = section.getBoolean(tagIncludeMines);
			treasuresInBunkers = section.getBoolean(tagIncludeBunkers);
			spawnersInBunkers = section.getBoolean(tagIncludeBunkers);
			workingLights = section.getBoolean(tagWorkingLights);
		}
		
		// write it back out if needed
		plugin.saveConfig();
	}
}
