package me.daddychurchill.CityWorld;

import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plugins.Tekkit.TekkitMaterial;
import me.daddychurchill.CityWorld.Support.MaterialStack;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.Vector;

public class CityWorldSettings {
	
	public boolean darkEnvironment;
	
	public boolean includeRoads = true;
	public boolean includeRoundabouts = true;
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
	public boolean includeWoolRoads = false;
	public boolean includeNamedRoads = true;
	public boolean includeDecayedRoads = false;
	public boolean includeDecayedBuildings = false;
	public boolean includeDecayedNature = false;
	public boolean includeBuildingInteriors = true;
	public boolean includeFloatingSubsurface = true;

	public boolean includeTekkitMaterials = false;
	public boolean forceLoadWorldEdit = false;
	public boolean forceLoadTekkit = false;
	
	public int centerPointOfChunkRadiusX = 0;
	public int centerPointOfChunkRadiusZ = 0;
	public int constructChunkRadius = Integer.MAX_VALUE;
	public boolean checkConstructRange = false;
	public int roadChunkRadius = Integer.MAX_VALUE;
	public boolean checkRoadRange = false;
	public int cityChunkRadius = Integer.MAX_VALUE;
	public boolean checkCityRange = false;
	
	public double oddsOfTreasureInSewers = DataContext.oddsLikely;
	public double oddsOfTreasureInBunkers = DataContext.oddsLikely;
	public double oddsOfTreasureInMines = DataContext.oddsLikely;
	public double oddsOfTreasureInMineAlcove = DataContext.oddsSomewhatLikely;
	public double oddsOfSpawnerInSewers = DataContext.oddsSomewhatUnlikely;
	public double oddsOfSpawnerInBunkers = DataContext.oddsSomewhatUnlikely;
	public double oddsOfSpawnerInMines = DataContext.oddsSomewhatUnlikely;
	public double oddsOfSpawnerInMineAlcove = DataContext.oddsSomewhatLikely;
	public double oddsOfAlcoveInMines = DataContext.oddsVeryLikely;
	
	public MaterialStack itemsTreasureInSewers;
	public MaterialStack itemsTreasureInBunkers;
	public MaterialStack itemsTreasureInMines;
	
	public final static String tagCenterPointOfChunkRadiusX = "CenterPointOfChunkRadiusX";
	public final static String tagCenterPointOfChunkRadiusZ = "CenterPointOfChunkRadiusZ";
	public final static String tagConstructChunkRadius = "ConstructChunkRadius";
	public final static String tagRoadChunkRadius = "RoadChunkRadius";
	public final static String tagCityChunkRadius = "CityChunkRadius";
	
	public final static String tagIncludeRoads = "IncludeRoads";
	public final static String tagIncludeRoundabouts = "IncludeRoundabouts";
	public final static String tagIncludeSewers = "IncludeSewers";
	public final static String tagIncludeCisterns = "IncludeCisterns";
	public final static String tagIncludeBasements = "IncludeBasements";
	public final static String tagIncludeMines = "IncludeMines";
	public final static String tagIncludeBunkers = "IncludeBunkers";
	public final static String tagIncludeBuildings = "IncludeBuildings";
	public final static String tagIncludeHouses = "IncludeHouses";
	public final static String tagIncludeFarms = "IncludeFarms";

	public final static String tagIncludeCaves = "IncludeCaves";
	public final static String tagIncludeLavaFields = "IncludeLavaFields";
	public final static String tagIncludeSeas = "IncludeSeas";
	public final static String tagIncludeMountains = "IncludeMountains";
	public final static String tagIncludeOres = "IncludeOres";
	
	public final static String tagTreasuresInSewers = "TreasuresInSewers";
	public final static String tagSpawnersInSewers = "SpawnersInSewers";
	public final static String tagTreasuresInMines = "TreasuresInMines";
	public final static String tagSpawnersInMines = "SpawnersInMines";
	public final static String tagTreasuresInBunkers = "TreasuresInBunkers";
	public final static String tagSpawnersInBunkers = "SpawnersInBunkers";
	
	public final static String tagIncludeUndergroundFluids = "IncludeUndergroundFluids";
	public final static String tagIncludeAbovegroundFluids = "IncludeAbovegroundFluids";
	public final static String tagIncludeWorkingLights = "IncludeWorkingLights";
	public final static String tagIncludeWoolRoads = "IncludeWoolRoads";
	public final static String tagIncludeNamedRoads = "IncludeNamedRoads";
	public final static String tagIncludeDecayedRoads = "IncludeDecayedRoads";
	public final static String tagIncludeDecayedBuildings = "IncludeDecayedBuildings";
	public final static String tagIncludeDecayedNature = "IncludeDecayedNature";
	public final static String tagIncludeBuildingInteriors = "IncludeBuildingInteriors";
	public final static String tagIncludeFloatingSubsurface = "IncludeFloatingSubsurface";
	
	public final static String tagForceLoadWorldEdit = "ForceLoadWorldEdit";
	public final static String tagForceLoadTekkit = "ForceLoadTekkit";
	
	public CityWorldSettings(WorldGenerator generator) {
		super();
		String worldname = generator.worldName;
		generator.worldEnvironment = generator.getWorld().getEnvironment();
		
		// get the right defaults
		switch (generator.worldEnvironment) {
		case NORMAL:
			darkEnvironment = false;
			break;
		case NETHER:
			darkEnvironment = true;
			includeWorkingLights = false;
			includeDecayedRoads = true;
			includeDecayedBuildings = true;
			includeDecayedNature = true;
			break;
		case THE_END:
			darkEnvironment = true;
			break;
		}
		
		// intialize based world style settings
		validateSettingsAgainstWorldStyle(generator);
		
		// stacks of materials
		itemsTreasureInSewers = new MaterialStack("Treasure_In_Sewers");
		itemsTreasureInBunkers = new MaterialStack("Treasure_In_Bunkers");
		itemsTreasureInMines = new MaterialStack("Treasure_In_Mines");
		
		// see if the new configuration is out there?
//		// find the files
//		File pluginFolder = generator.getPlugin().getDataFolder();
//		if (pluginFolder.isDirectory()) {
//			
//			// forget all those shape and ore type and just go for the world name
//			schematicsFolder = findFolder(pluginFolder, "Schematics for " + generator.worldName);
			
		
		// add/get the configuration
		CityWorld plugin = generator.getPlugin();
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
		}
		
		/* Create a config in the world's folder
		 * Find the generation section
		 * Does the global config contain a world section?
		 *   Load from that world section
		 *   Copy that world section over to the generation section of the world's config
		 *   Delete the world section in the global config
		 *   Save the global config
		 * Read from the generation section
		 * Save the generation section
		 */
		
		// did we get a section?
		if (section != null) {
			
			// create items stacks
			
			// set up the defaults if needed
			section.addDefault(tagCenterPointOfChunkRadiusX, centerPointOfChunkRadiusX);
			section.addDefault(tagCenterPointOfChunkRadiusZ, centerPointOfChunkRadiusZ);
			section.addDefault(tagConstructChunkRadius, constructChunkRadius);
			section.addDefault(tagRoadChunkRadius, roadChunkRadius);
			section.addDefault(tagCityChunkRadius, cityChunkRadius);
			
			section.addDefault(tagIncludeRoads, includeRoads);
			section.addDefault(tagIncludeRoundabouts, includeRoundabouts);
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
			section.addDefault(tagIncludeWoolRoads, includeWoolRoads);
			section.addDefault(tagIncludeNamedRoads, includeNamedRoads);
			section.addDefault(tagIncludeDecayedRoads, includeDecayedRoads);
			section.addDefault(tagIncludeDecayedBuildings, includeDecayedBuildings);
			section.addDefault(tagIncludeDecayedNature, includeDecayedNature);
			section.addDefault(tagIncludeBuildingInteriors, includeBuildingInteriors);
			section.addDefault(tagIncludeFloatingSubsurface, includeFloatingSubsurface);
			
			section.addDefault(tagForceLoadWorldEdit, forceLoadWorldEdit);
			section.addDefault(tagForceLoadTekkit, forceLoadTekkit);
			
			// now read the bits
			includeRoads = section.getBoolean(tagIncludeRoads, includeRoads);
			includeRoundabouts = section.getBoolean(tagIncludeRoundabouts, includeRoundabouts);
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
			includeWoolRoads = section.getBoolean(tagIncludeWoolRoads, includeWoolRoads);
			includeNamedRoads = section.getBoolean(tagIncludeNamedRoads, includeNamedRoads);
			includeDecayedRoads = section.getBoolean(tagIncludeDecayedRoads, includeDecayedRoads);
			includeDecayedBuildings = section.getBoolean(tagIncludeDecayedBuildings, includeDecayedBuildings);
			includeDecayedNature = section.getBoolean(tagIncludeDecayedNature, includeDecayedNature);
			includeBuildingInteriors = section.getBoolean(tagIncludeBuildingInteriors, includeBuildingInteriors);
			includeFloatingSubsurface = section.getBoolean(tagIncludeFloatingSubsurface, includeFloatingSubsurface);
			
			forceLoadWorldEdit = section.getBoolean(tagForceLoadWorldEdit, forceLoadWorldEdit);
			
			centerPointOfChunkRadiusX = section.getInt(tagCenterPointOfChunkRadiusX, centerPointOfChunkRadiusX);
			centerPointOfChunkRadiusZ = section.getInt(tagCenterPointOfChunkRadiusZ, centerPointOfChunkRadiusZ);
			centerPointOfChunkRadius = new Vector(centerPointOfChunkRadiusX, 0, centerPointOfChunkRadiusZ);
			
			constructChunkRadius = Math.min(Integer.MAX_VALUE, Math.max(0, section.getInt(tagConstructChunkRadius, constructChunkRadius)));
			checkConstructRange = constructChunkRadius > 0 && constructChunkRadius < Integer.MAX_VALUE;
			
			roadChunkRadius = Math.min(constructChunkRadius, Math.max(0, section.getInt(tagRoadChunkRadius, roadChunkRadius)));
			checkRoadRange = roadChunkRadius > 0 && roadChunkRadius < Integer.MAX_VALUE;
			if (roadChunkRadius == 0) {
				includeRoads = false;
				includeSewers = false;
			}

			cityChunkRadius = Math.min(roadChunkRadius, Math.max(0, section.getInt(tagCityChunkRadius, cityChunkRadius)));
			checkCityRange = cityChunkRadius > 0 && cityChunkRadius < Integer.MAX_VALUE;
			if (cityChunkRadius == 0) {
				includeCisterns = false;
				includeBasements = false;
				includeMines = false;
				includeBunkers = false;
				includeBuildings = false;
				includeHouses = false;
				includeFarms = false;
			}
			
			// one more time for world style settings
			validateSettingsAgainstWorldStyle(generator);
			
			// write things back out with corrections
			section.set(tagCenterPointOfChunkRadiusX, centerPointOfChunkRadiusX);
			section.set(tagCenterPointOfChunkRadiusZ, centerPointOfChunkRadiusZ);
			section.set(tagConstructChunkRadius, constructChunkRadius);
			section.set(tagRoadChunkRadius, roadChunkRadius);
			section.set(tagCityChunkRadius, cityChunkRadius);
			
			section.set(tagIncludeRoads, includeRoads);
			section.set(tagIncludeRoundabouts, includeRoundabouts);
			section.set(tagIncludeSewers, includeSewers);
			section.set(tagIncludeCisterns, includeCisterns);
			section.set(tagIncludeBasements, includeBasements);
			section.set(tagIncludeMines, includeMines);
			section.set(tagIncludeBunkers, includeBunkers);
			section.set(tagIncludeBuildings, includeBuildings);
			section.set(tagIncludeHouses, includeHouses);
			section.set(tagIncludeFarms, includeFarms);
			
			section.set(tagIncludeCaves, includeCaves);
			section.set(tagIncludeLavaFields, includeLavaFields);
			section.set(tagIncludeSeas, includeSeas);
			section.set(tagIncludeMountains, includeMountains);
			section.set(tagIncludeOres, includeOres);
			
			section.set(tagTreasuresInSewers, treasuresInSewers);
			section.set(tagSpawnersInSewers, spawnersInSewers);
			section.set(tagTreasuresInMines, treasuresInMines);
			section.set(tagSpawnersInMines, spawnersInMines);
			section.set(tagTreasuresInBunkers, treasuresInBunkers);
			section.set(tagSpawnersInBunkers, spawnersInBunkers);
			
			section.set(tagIncludeUndergroundFluids, includeUndergroundFluids);
			section.set(tagIncludeAbovegroundFluids, includeAbovegroundFluids);
			section.set(tagIncludeWorkingLights, includeWorkingLights);
			section.set(tagIncludeWoolRoads, includeWoolRoads);
			section.set(tagIncludeNamedRoads, includeNamedRoads);
			section.set(tagIncludeDecayedRoads, includeDecayedRoads);
			section.set(tagIncludeDecayedBuildings, includeDecayedBuildings);
			section.set(tagIncludeDecayedNature, includeDecayedNature);
			section.set(tagIncludeBuildingInteriors, includeBuildingInteriors);
			section.set(tagIncludeFloatingSubsurface, includeFloatingSubsurface);
			
			section.set(tagForceLoadWorldEdit, forceLoadWorldEdit);
			section.set(tagForceLoadTekkit, forceLoadTekkit);
			
			// note the depreciations
			deprecateOption(section, "IncludePavedRoads", "DEPRECATED: Use IncludeWoolRoads if you want the old style paved roads");
			deprecateOption(section, "RoadRange", "DEPRECATED: Use RoadChunkRadius instead");
			deprecateOption(section, "CityRange", "DEPRECATED: Use CityChunkRadius instead");
			deprecateOption(section, "IncludeTekkitMaterials", "DEPRECATED: ForgeTekkit is auto-recognized");
			
			// write it back out 
			plugin.saveConfig();
		}
		
		// what about tekkit?
		includeTekkitMaterials = TekkitMaterial.isTekkitForgeEnabled() || forceLoadTekkit;
		
	}
	
	private void validateSettingsAgainstWorldStyle(WorldGenerator generator) {
		// now get the right defaults for the world style
		switch (generator.worldStyle) {
		case NORMAL:
			break;
		case FLOATING:
//			includeRoads = true;
//			includeRoundabouts = true;
//			includeSewers = true;
//			includeCisterns = true;
//			includeBasements = true;
			includeMines = false; // DIFFERENT
			includeBunkers = false; // DIFFERENT
//			includeBuildings = true;
//			includeHouses = true;
//			includeFarms = true;

			includeCaves = false; // DIFFERENT
			includeLavaFields = false; // DIFFERENT
			includeSeas = false; // DIFFERENT
			includeMountains = true; // THIS MUST BE SET TO TRUE
			includeOres = false; // DIFFERENT
			
			treasuresInSewers = false; // DIFFERENT
			spawnersInSewers = false; // DIFFERENT
			treasuresInMines = false; // DIFFERENT
			spawnersInMines = false; // DIFFERENT
			treasuresInBunkers = false; // DIFFERENT
			spawnersInBunkers = false; // DIFFERENT
			
			includeUndergroundFluids = false; // DIFFERENT
//			includeAbovegroundFluids = true;
//			includeWorkingLights = true;
//			includeWoolRoads = false;
//			includeNamedRoads = true;
//			includeDecayedRoads = false;
//			includeDecayedBuildings = false;
//			includeDecayedNature = false;
//			includeBuildingInteriors = true;
//			includeFloatingSubsurface = true;
			break;
		case FLOODED:
		case SANDDUNES:
		case SNOWDUNES:
//			includeRoads = true;
//			includeRoundabouts = true;
			includeSewers = false; // DIFFERENT
			includeCisterns = false; // DIFFERENT
//			includeBasements = true;
			includeMines = false; // DIFFERENT
			includeBunkers = false; // DIFFERENT
//			includeBuildings = true;
//			includeHouses = true;
			includeFarms = false; // DIFFERENT

//			includeCaves = true; 
			includeLavaFields = false; // DIFFERENT
			includeSeas = true; // THIS MUST BE SET TO TRUE
			includeMountains = true; // THIS MUST BE SET TO TRUE
//			includeOres = true;
			
			treasuresInSewers = false; // DIFFERENT
			spawnersInSewers = false; // DIFFERENT
			treasuresInMines = false; // DIFFERENT
			spawnersInMines = false; // DIFFERENT
			treasuresInBunkers = false; // DIFFERENT
			spawnersInBunkers = false; // DIFFERENT
			
			includeUndergroundFluids = false; // DIFFERENT
//			includeAbovegroundFluids = true;
			includeWorkingLights = false; // DIFFERENT
//			includeWoolRoads = false;
			includeNamedRoads = false; // DIFFERENT
//			includeDecayedRoads = false;
//			includeDecayedBuildings = false;
//			includeDecayedNature = false;
			includeBuildingInteriors = false; // DIFFERENT
//			includeFloatingSubsurface = true;
			break;
		}
		
	}
	
//	private File findFolder(File parent, String name) throws Exception {
//		name = toCamelCase(name);
//		File result = new File(parent, name);
//		if (!result.isDirectory())
//			if (!result.mkdir())
//				throw new UnsupportedOperationException("[WorldEdit] Could not create/find the folder: " + parent.getAbsolutePath() + File.separator + name);
//		return result;
//	}
//	
//	private String toCamelCase(String text) {
//		return text.substring(0, 1).toUpperCase() + text.substring(1, text.length()).toLowerCase();
//	}

	private void deprecateOption(ConfigurationSection section, String oldOption, String message) {
		if (section.contains(oldOption))
			section.set(oldOption, message);
	}
	
	private Vector centerPointOfChunkRadius;
	
	public boolean inConstructRange(int x, int z) {
		return !checkConstructRange || centerPointOfChunkRadius.distance(new Vector(x, 0, z)) <= constructChunkRadius;
	}
	
	public boolean inRoadRange(int x, int z) {
		return !checkRoadRange || centerPointOfChunkRadius.distance(new Vector(x, 0, z)) <= roadChunkRadius;
	}
	
	public boolean inCityRange(int x, int z) {
		return !checkCityRange || centerPointOfChunkRadius.distance(new Vector(x, 0, z)) <= cityChunkRadius;
	}
	
//	private HashMap<String, ItemStack[]> materials;
//	
//	public void addMaterials(String name, Material low, Material high) {
//		
//		// build the collection
//		int base = low.getId();
//		int count = high.getId() - base;
//		ItemStack[] collection = new ItemStack[count];
//		for (int i = 0; i < count; i++) 
//			collection[i] = new ItemStack(base + i);
//		
//		// remember it
//		materials.put(name, collection);
//	}
//	
//	public void addMaterials(String name, Material ... items) {
//		
//		// build the collection
//		int count = items.length;
//		ItemStack[] collection = new ItemStack[count];
//		for (int i = 0; i < count; i++) 
//			collection[i] = new ItemStack(items[i]);
//		
//		// remember it
//		materials.put(name, collection);
//	}
//	
//	public Material getRandomMaterial(String name, Odds odds) {
//		ItemStack[] collection = materials.get(name);
//		return collection == null ? Material.AIR : collection[odds.getRandomInt(collection.length)].getType();
//	}
//
//	public Byte getRandomTypeId(String name, Odds odds) {
//		ItemStack[] collection = materials.get(name);
//		return (byte) ((collection == null ? Material.AIR : collection[odds.getRandomInt(collection.length)].getType()).getId());
//	}
}
