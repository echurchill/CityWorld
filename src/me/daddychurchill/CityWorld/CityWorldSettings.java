package me.daddychurchill.CityWorld;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class CityWorldSettings {

	private boolean includeSewers = true;
	private boolean includeCisterns = true;
	private boolean includeBasements = true;
	private boolean includeMines = true;
	private boolean includeBunkers = true;
	private boolean treasuresInSewers = true;
	private boolean spawnersInSewers = true;
	private boolean treasuresInMines = true;
	private boolean spawnersInMines = true;
	private boolean treasuresInBunkers = true;
	private boolean spawnersInBunkers = true;
	private boolean workingLights = false;
	
	private final static String tagIncludeSewers = "IncludeSewers";
	private final static String tagIncludeCisterns = "IncludeCisterns";
	private final static String tagIncludeBasements = "IncludeBasements";
	private final static String tagIncludeMines = "IncludeMines";
	private final static String tagIncludeBunkers = "IncludeBunkers";
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
	
	public boolean isIncludeSewer() {
		return includeSewers;
	}

	public boolean isIncludeCistern() {
		return includeCisterns;
	}

	public boolean isIncludeBasement() {
		return includeBasements;
	}

	public boolean isIncludeMines() {
		return includeMines;
	}

	public boolean isIncludeBunkers() {
		return includeBunkers;
	}

	public boolean isTreasuresInSewers() {
		return treasuresInSewers;
	}
	
	public boolean isSpawnersInSewers() {
		return spawnersInSewers;
	}
	
	public boolean isTreasuresInMines() {
		return treasuresInMines;
	}
	
	public boolean isSpawnersInMines() {
		return spawnersInMines;
	}
	
	public boolean isTreasuresInBunkers() {
		return treasuresInBunkers;
	}
	
	public boolean isSpawnersInBunkers() {
		return spawnersInBunkers;
	}
	
	public boolean isWorkingLights() {
		return workingLights;
	}
}
