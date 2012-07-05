package me.daddychurchill.CityWorld;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class CityWorldSettings {

	public boolean includeSewers = true;
	public boolean includeCisterns = true;
	public boolean includeBasements = true;
	public boolean includeMines = true;
	public boolean includeBunkers = true;
	public boolean includeBuildings = true;
	public boolean includePavedRoads = true;

	public boolean includeCaves = true;
	public boolean includeOres = true;
	public boolean includeUndergroundFluids = true;
	public boolean includeLavaFields = true;
	public boolean includeSeas = true;
	public boolean includeMountains = true;
	
	public boolean treasuresInSewers = true;
	public boolean spawnersInSewers = true;
	public boolean treasuresInMines = true;
	public boolean spawnersInMines = true;
	public boolean treasuresInBunkers = true;
	public boolean spawnersInBunkers = true;
	
	public boolean workingLights = true;
	
	public boolean tekkitServer = false;
	
	private final static String tagIncludeSewers = "IncludeSewers";
	private final static String tagIncludeCisterns = "IncludeCisterns";
	private final static String tagIncludeBasements = "IncludeBasements";
	private final static String tagIncludeMines = "IncludeMines";
	private final static String tagIncludeBunkers = "IncludeBunkers";
	private final static String tagIncludeBuildings = "IncludeBuildings";
	private final static String tagIncludePavedRoads = "IncludePavedRoads";

	private final static String tagIncludeCaves = "IncludeCaves";
	private final static String tagIncludeOres = "IncludeOres";
	private final static String tagIncludeUndergroundFluids = "IncludeUndergroundFluids";
	private final static String tagIncludeLavaFields = "IncludeLavaFields";
	private final static String tagIncludeSeas = "IncludeSeas";
	private final static String tagIncludeMountains = "IncludeMountains";
	
	private final static String tagTreasuresInSewers = "TreasuresInSewers";
	private final static String tagSpawnersInSewers = "SpawnersInSewers";
	private final static String tagTreasuresInMines = "TreasuresInMines";
	private final static String tagSpawnersInMines = "SpawnersInMines";
	private final static String tagTreasuresInBunkers = "TreasuresInBunkers";
	private final static String tagSpawnersInBunkers = "SpawnersInBunkers";
	
	private final static String tagWorkingLights = "WorkingLights";
	
	private final static String tagTekkitServer = "TekkitServer";
	
	public CityWorldSettings(CityWorld plugin, String worldname) {
		super();
		
		// add/get the configuration
		FileConfiguration config = plugin.getConfig();
		config.options().header("CityWorld Plugin Options");
		config.options().copyDefaults(true);
		
		// get the right section
		ConfigurationSection section = null;
		
		// see if we can find the specific world
		if (config.isConfigurationSection(worldname))
			section = config.getConfigurationSection(worldname);
		
		// if not then create it
		else {
			section = config.createSection(worldname);
			section.addDefault(tagIncludeSewers, includeSewers);
			section.addDefault(tagIncludeCisterns, includeCisterns);
			section.addDefault(tagIncludeBasements, includeBasements);
			section.addDefault(tagIncludeMines, includeMines);
			section.addDefault(tagIncludeBunkers, includeBunkers);
			section.addDefault(tagIncludeBuildings, includeBuildings);
			section.addDefault(tagIncludePavedRoads, includePavedRoads);
			
			section.addDefault(tagIncludeCaves, includeCaves);
			section.addDefault(tagIncludeOres, includeOres);
			section.addDefault(tagIncludeUndergroundFluids, includeUndergroundFluids);
			section.addDefault(tagIncludeLavaFields, includeLavaFields);
			section.addDefault(tagIncludeSeas, includeSeas);
			section.addDefault(tagIncludeMountains, includeMountains);
			
			section.addDefault(tagTreasuresInSewers, treasuresInSewers);
			section.addDefault(tagSpawnersInSewers, spawnersInSewers);
			section.addDefault(tagTreasuresInMines, treasuresInMines);
			section.addDefault(tagSpawnersInMines, spawnersInMines);
			section.addDefault(tagTreasuresInBunkers, treasuresInBunkers);
			section.addDefault(tagSpawnersInBunkers, spawnersInBunkers);
			
			section.addDefault(tagWorkingLights, workingLights);
		}
		
		// did we get a section?
		if (section != null) {
			includeSewers = section.getBoolean(tagIncludeSewers);
			includeCisterns = section.getBoolean(tagIncludeCisterns);
			includeBasements = section.getBoolean(tagIncludeBasements);
			includeMines = section.getBoolean(tagIncludeMines);
			includeBunkers = section.getBoolean(tagIncludeBunkers);
			includeBuildings = section.getBoolean(tagIncludeBuildings);
			includePavedRoads = section.getBoolean(tagIncludePavedRoads);
			
			includeCaves = section.getBoolean(tagIncludeCaves);
			includeOres = section.getBoolean(tagIncludeOres);
			includeUndergroundFluids = section.getBoolean(tagIncludeUndergroundFluids);
			includeLavaFields = section.getBoolean(tagIncludeLavaFields);
			includeSeas = section.getBoolean(tagIncludeSeas);
			includeMountains = section.getBoolean(tagIncludeMountains);

			treasuresInSewers = section.getBoolean(tagTreasuresInSewers);
			spawnersInSewers = section.getBoolean(tagSpawnersInSewers);
			treasuresInMines = section.getBoolean(tagTreasuresInMines);
			spawnersInMines = section.getBoolean(tagSpawnersInMines);
			treasuresInBunkers = section.getBoolean(tagTreasuresInBunkers);
			spawnersInBunkers = section.getBoolean(tagSpawnersInBunkers);
			
			workingLights = section.getBoolean(tagWorkingLights);
			
			tekkitServer = section.getBoolean(tagTekkitServer);
		}
		
		// write it back out if needed
		plugin.saveConfig();
	}
}
