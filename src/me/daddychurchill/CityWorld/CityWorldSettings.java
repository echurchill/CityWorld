package me.daddychurchill.CityWorld;

import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class CityWorldSettings {

	public boolean includeSewers = true;
	public boolean includeCisterns = true;
	public boolean includeBasements = true;
	public boolean includeMines = true;
	public boolean includeBunkers = true;
	public boolean includeBuildings = true;
	public boolean includeHouses = true;
	public boolean includeFarms = true;

	public boolean includeCaves = true;
	public boolean includeLavaFields = true;
	public boolean includeSeas = true;
	public boolean includeMountains = true;
	public boolean includeOres = true;
	
	public boolean treasuresInSewers = true;
	public boolean spawnersInSewers = true;
	public boolean treasuresInMines = true;
	public boolean spawnersInMines = true;
	public boolean treasuresInBunkers = true;
	public boolean spawnersInBunkers = true;
	
	public boolean includeUndergroundFluids = true;
	public boolean includeAbovegroundFluids = true;
	public boolean includeWorkingLights = true;
	public boolean includePavedRoads = true;
	public boolean includeDecayedRoads = false;
	public boolean includeTekkitMaterials = false;
	
	public World.Environment environment;

	private final static String tagIncludeSewers = "IncludeSewers";
	private final static String tagIncludeCisterns = "IncludeCisterns";
	private final static String tagIncludeBasements = "IncludeBasements";
	private final static String tagIncludeMines = "IncludeMines";
	private final static String tagIncludeBunkers = "IncludeBunkers";
	private final static String tagIncludeBuildings = "IncludeBuildings";
	private final static String tagIncludeHouses = "IncludeHouses";
	private final static String tagIncludeFarms = "IncludeFarms";

	private final static String tagIncludeCaves = "IncludeCaves";
	private final static String tagIncludeLavaFields = "IncludeLavaFields";
	private final static String tagIncludeSeas = "IncludeSeas";
	private final static String tagIncludeMountains = "IncludeMountains";
	private final static String tagIncludeOres = "IncludeOres";
	
	private final static String tagTreasuresInSewers = "TreasuresInSewers";
	private final static String tagSpawnersInSewers = "SpawnersInSewers";
	private final static String tagTreasuresInMines = "TreasuresInMines";
	private final static String tagSpawnersInMines = "SpawnersInMines";
	private final static String tagTreasuresInBunkers = "TreasuresInBunkers";
	private final static String tagSpawnersInBunkers = "SpawnersInBunkers";
	
	private final static String tagIncludeUndergroundFluids = "IncludeUndergroundFluids";
	private final static String tagIncludeAbovegroundFluids = "IncludeAbovegroundFluids";
	private final static String tagIncludeWorkingLights = "IncludeWorkingLights";
	private final static String tagIncludePavedRoads = "IncludePavedRoads";
	private final static String tagIncludeDecayedRoads = "IncludeDecayedRoads";
	private final static String tagIncludeTekkitMaterials = "IncludeTekkitMaterials";
	
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
			section.addDefault(tagIncludeHouses, includeHouses);
			section.addDefault(tagIncludeFarms, includeFarms);
			
			section.addDefault(tagIncludeCaves, includeCaves);
			section.addDefault(tagIncludeLavaFields, includeLavaFields);
			section.addDefault(tagIncludeSeas, includeSeas);
			section.addDefault(tagIncludeMountains, includeMountains);
			section.addDefault(tagIncludeOres, includeOres);
			
			section.addDefault(tagTreasuresInSewers, treasuresInSewers);
			section.addDefault(tagSpawnersInSewers, spawnersInSewers);
			section.addDefault(tagTreasuresInMines, treasuresInMines);
			section.addDefault(tagSpawnersInMines, spawnersInMines);
			section.addDefault(tagTreasuresInBunkers, treasuresInBunkers);
			section.addDefault(tagSpawnersInBunkers, spawnersInBunkers);
			
			section.addDefault(tagIncludeUndergroundFluids, includeUndergroundFluids);
			section.addDefault(tagIncludeAbovegroundFluids, includeAbovegroundFluids);
			section.addDefault(tagIncludeWorkingLights, includeWorkingLights);
			section.addDefault(tagIncludePavedRoads, includePavedRoads);
			section.addDefault(tagIncludeDecayedRoads, includeDecayedRoads);
			section.addDefault(tagIncludeTekkitMaterials, includeTekkitMaterials);
		}
		
		// did we get a section?
		if (section != null) {
			includeSewers = section.getBoolean(tagIncludeSewers, includeSewers);
			includeCisterns = section.getBoolean(tagIncludeCisterns, includeCisterns);
			includeBasements = section.getBoolean(tagIncludeBasements, includeBasements);
			includeMines = section.getBoolean(tagIncludeMines, includeMines);
			includeBunkers = section.getBoolean(tagIncludeBunkers, includeBunkers);
			includeBuildings = section.getBoolean(tagIncludeBuildings, includeBuildings);
			includeHouses = section.getBoolean(tagIncludeHouses, includeHouses);
			includeFarms = section.getBoolean(tagIncludeFarms, includeFarms);
			
			includeCaves = section.getBoolean(tagIncludeCaves, includeCaves);
			includeLavaFields = section.getBoolean(tagIncludeLavaFields, includeLavaFields);
			includeSeas = section.getBoolean(tagIncludeSeas, includeSeas);
			includeMountains = section.getBoolean(tagIncludeMountains, includeMountains);
			includeOres = section.getBoolean(tagIncludeOres, includeOres);

			treasuresInSewers = section.getBoolean(tagTreasuresInSewers, treasuresInSewers);
			spawnersInSewers = section.getBoolean(tagSpawnersInSewers, spawnersInSewers);
			treasuresInMines = section.getBoolean(tagTreasuresInMines, treasuresInMines);
			spawnersInMines = section.getBoolean(tagSpawnersInMines, spawnersInMines);
			treasuresInBunkers = section.getBoolean(tagTreasuresInBunkers, treasuresInBunkers);
			spawnersInBunkers = section.getBoolean(tagSpawnersInBunkers, spawnersInBunkers);
			
			includeUndergroundFluids = section.getBoolean(tagIncludeUndergroundFluids, includeUndergroundFluids);
			includeAbovegroundFluids = section.getBoolean(tagIncludeAbovegroundFluids, includeAbovegroundFluids);
			includeWorkingLights = section.getBoolean(tagIncludeWorkingLights, includeWorkingLights);
			includePavedRoads = section.getBoolean(tagIncludePavedRoads, includePavedRoads);
			includeDecayedRoads = section.getBoolean(tagIncludeDecayedRoads, includeDecayedRoads);
			includeTekkitMaterials = section.getBoolean(tagIncludeTekkitMaterials, includeTekkitMaterials);
		}
		
		// write it back out if needed
		plugin.saveConfig();
	}
	
	public void setEnvironment(World.Environment aEnvironment) {
		environment = aEnvironment;
		switch (environment) {
		case NORMAL:
			break;
		case NETHER:
			includeAbovegroundFluids = false;
			includeWorkingLights = false;
			includeDecayedRoads = true;
			break;
		case THE_END:
			includeMines = false;
			includeBunkers = false;
			includeHouses = false;
			includeFarms = false;

			includeLavaFields = false;
//			includeSeas = false;
//			includeMountains = false;
			break;
		}
	}
}
