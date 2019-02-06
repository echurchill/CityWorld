package me.daddychurchill.CityWorld;

import java.io.File;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.Vector;

import me.daddychurchill.CityWorld.CityWorldGenerator.WorldStyle;
import me.daddychurchill.CityWorld.Plugins.SurfaceProvider_Floating;
import me.daddychurchill.CityWorld.Plugins.SurfaceProvider_Floating.SubSurfaceStyle;
import me.daddychurchill.CityWorld.Plugins.TreeProvider;
import me.daddychurchill.CityWorld.Plugins.TreeProvider.TreeStyle;
import me.daddychurchill.CityWorld.Support.AbstractBlocks;
import me.daddychurchill.CityWorld.Support.Odds;

public class CityWorldSettings {

	public boolean darkEnvironment = false;

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
	public boolean includeMunicipalities = true;
	public boolean includeIndustrialSectors = true;
	public boolean includeAirborneStructures = true;

	public boolean includeCaves = true;
	public boolean includeLavaFields = true;
	public boolean includeSeas = true;
	public boolean includeMountains = true;
	public boolean includeOres = true;
	public boolean includeBones = true;
	public boolean includeFires = true;

	public double spawnBeings = Odds.oddsLikely;
	public double spawnBaddies = Odds.oddsPrettyUnlikely;
	public double spawnAnimals = Odds.oddsVeryLikely;
	public double spawnVagrants = Odds.oddsSomewhatUnlikely;
	public boolean nameVillagers = true;
	public boolean showVillagersNames = true;

	public boolean spawnersInBunkers = true;
	public boolean spawnersInMines = true;
	public boolean spawnersInSewers = true;

	public boolean treasuresInBunkers = true;
	public boolean treasuresInMines = true;
	public boolean treasuresInSewers = true;
	public boolean treasuresInBuildings = true;

	public boolean includeUndergroundFluids = true;
	public boolean includeAbovegroundFluids = true;
	public boolean includeWorkingLights = true;
	public boolean includeNamedRoads = true;
	public boolean includeDecayedRoads = false;
	public boolean includeDecayedBuildings = false;
	public boolean includeDecayedNature = false;
	public boolean includeBuildingInteriors = true;
//	public boolean includeFloatingSubsurface = false; // not needed anymore
//	public boolean includeFloatingSubclouds = true;
//	public double spawnCities = Odds.oddsAlwaysGoingToHappen;

	public boolean useMinecraftLootTables = true;
	public boolean forceLoadWorldEdit = false;
	public boolean broadcastSpecialPlaces = false;

	public TreeStyle treeStyle = TreeStyle.NORMAL;
	public double spawnTrees = Odds.oddsLikely;
	//	public double oddsOfFoliage = Odds.oddsAlwaysGoingToHappen;
	public SubSurfaceStyle subSurfaceStyle = SubSurfaceStyle.LAND;

	private final static int maxRadius = 30000000 / AbstractBlocks.sectionBlockWidth; // 1875000 is the actual maximum
	// chunk limit for today's
	// minecraft world format
	private int centerPointOfChunkRadiusX = 0;
	private int centerPointOfChunkRadiusZ = 0;
	private int constructChunkRadius = maxRadius;
	private boolean checkConstructRange = false;
	private int roadChunkRadius = maxRadius;
	private boolean checkRoadRange = false;
	private int cityChunkRadius = maxRadius;
	private boolean checkCityRange = false;
	private boolean buildOutsideRadius = false;

	private int minInbetweenChunkDistanceOfCities = 0;
	private boolean checkMinInbetweenChunkDistanceOfCities = false;
	public double ruralnessLevel = 0.0;

	public double oddsOfTreasureInSewers = Odds.oddsLikely;
	public double oddsOfTreasureInBunkers = Odds.oddsLikely;
	public double oddsOfTreasureInMines = Odds.oddsLikely;
	public double oddsOfTreasureInBuildings = Odds.oddsLikely;
	public double oddsOfAlcoveInMines = Odds.oddsVeryLikely;

	private static final String tagIncludeRoads = "IncludeRoads";
	private static final String tagIncludeRoundabouts = "IncludeRoundabouts";
	private static final String tagIncludeSewers = "IncludeSewers";
	private static final String tagIncludeCisterns = "IncludeCisterns";
	private static final String tagIncludeBasements = "IncludeBasements";
	private static final String tagIncludeMines = "IncludeMines";
	private static final String tagIncludeBunkers = "IncludeBunkers";
	private static final String tagIncludeBuildings = "IncludeBuildings";
	private static final String tagIncludeHouses = "IncludeHouses";
	private static final String tagIncludeFarms = "IncludeFarms";
	private static final String tagIncludeMunicipalities = "IncludeMunicipalities";
	private static final String tagIncludeIndustrialSectors = "IncludeIndustrialSectors";
	private static final String tagIncludeAirborneStructures = "IncludeAirborneStructures";

	private static final String tagIncludeCaves = "IncludeCaves";
	private static final String tagIncludeLavaFields = "IncludeLavaFields";
	private static final String tagIncludeSeas = "IncludeSeas";
	private static final String tagIncludeMountains = "IncludeMountains";
	private static final String tagIncludeOres = "IncludeOres";
	private static final String tagIncludeBones = "IncludeBones";
	private static final String tagIncludeFires = "IncludeFires";

	private static final String tagSpawnAnimals = "SpawnAnimals";
	private static final String tagSpawnBeings = "SpawnBeings";
	private static final String tagSpawnBaddies = "SpawnBaddies";
	private static final String tagSpawnVagrants = "SpawnVagrants";
	private static final String tagNameVillagers = "NameVillagers";
	private static final String tagShowVillagersNames = "ShowVillagersNames";

	private static final String tagSpawnersInBunkers = "SpawnersInBunkers";
	private static final String tagSpawnersInMines = "SpawnersInMines";
	private static final String tagSpawnersInSewers = "SpawnersInSewers";

	private static final String tagTreasuresInBunkers = "TreasuresInBunkers";
	private static final String tagTreasuresInMines = "TreasuresInMines";
	private static final String tagTreasuresInSewers = "TreasuresInSewers";
	private static final String tagTreasuresInBuildings = "TreasuresInBuildings";

	private static final String tagIncludeUndergroundFluids = "IncludeUndergroundFluids";
	private static final String tagIncludeAbovegroundFluids = "IncludeAbovegroundFluids";
	private static final String tagIncludeWorkingLights = "IncludeWorkingLights";
	private static final String tagIncludeNamedRoads = "IncludeNamedRoads";
	private static final String tagIncludeDecayedRoads = "IncludeDecayedRoads";
	private static final String tagIncludeDecayedBuildings = "IncludeDecayedBuildings";
	private static final String tagIncludeDecayedNature = "IncludeDecayedNature";
	private static final String tagIncludeBuildingInteriors = "IncludeBuildingInteriors";
	private static final String tagIncludeFloatingSubsurface = "IncludeFloatingSubsurface";

	private static final String tagUseMinecraftLootTables = "UseMinecraftLootTables";
	private static final String tagBroadcastSpecialPlaces = "BroadcastSpecialPlaces";
	public static final String tagForceLoadWorldEdit = "ForceLoadWorldEdit";

	private static final String tagTreeStyle = "TreeStyle";
	private static final String tagSpawnTrees = "SpawnTrees";
	//	private static String tagOddsOfFoliage = "OddsOfFoliage";
	private static final String tagSubSurfaceStyle = "SubSurfaceStyle";

	private static final String tagCenterPointOfChunkRadiusX = "CenterPointOfChunkRadiusX";
	private static final String tagCenterPointOfChunkRadiusZ = "CenterPointOfChunkRadiusZ";
	private static final String tagConstructChunkRadius = "ConstructChunkRadius";
	private static final String tagRoadChunkRadius = "RoadChunkRadius";
	private static final String tagCityChunkRadius = "CityChunkRadius";
	private static final String tagBuildOutsideRadius = "BuildOutsideRadius";

	private static final String tagMinInbetweenChunkDistanceOfCities = "MinInbetweenChunkDistanceOfCities";
	private static final String tagRuralnessLevel = "RuralnessLevel";

//	private static String tagOddsOfTreasureInSewers = "OddsOfTreasureInSewers";
//	private static String tagOddsOfTreasureInBunkers = "OddsOfTreasureInBunkers";
//	private static String tagOddsOfTreasureInMines = "OddsOfTreasureInMines";
//	private static String tagOddsOfTreasureInBuildings = "OddsOfTreasureInBuildings";
//	private static String tagOddsOfAlcovesInMines = "OddsOfAlcovesInMines";

	private CityWorldSettings() {
		super();
	}

	public static CityWorldSettings loadSettings(CityWorld plugin) {
		plugin.reportMessage("Loading default settings");
		CityWorldSettings settings = new CityWorldSettings();

		FileConfiguration config = plugin.getConfig();
		settings.loadSettings(null, config, "default", Environment.NORMAL, WorldType.NORMAL, WorldStyle.NORMAL);
		plugin.saveConfig();

		return settings;
	}

	public static CityWorldSettings loadSettings(CityWorldGenerator generator, World aWorld) {
		String worldName = generator.worldName;
		generator.reportMessage("Loading settings for '" + worldName + "'");
		CityWorldSettings settings = new CityWorldSettings();
		Environment worldEnv = aWorld.getEnvironment();
		WorldType worldType = aWorld.getWorldType();
		WorldStyle worldStyle = generator.worldStyle;

		try {

			File pluginFolder = generator.getPlugin().getDataFolder();
			if (pluginFolder.isDirectory()) {
				File worldConfig = new File(pluginFolder, worldName + ".yml");

				// if this returns true then we need to copy over the defaults
				if (worldConfig.createNewFile()) {

					FileConfiguration defaults = generator.getPlugin().getConfig();
					if (defaults.isConfigurationSection(worldName)) {

						CityWorldSettings oldSettings = new CityWorldSettings();
						oldSettings.loadSettings(generator, defaults, worldName, worldEnv, worldType, worldStyle);
						settings.copySettings(oldSettings);

						generator.reportMessage("*********************************************");
						generator.reportMessage("Copied the settings from CityWorld/config.yml");
						generator.reportMessage(" to " + worldName + ".yml. You should verify");
						generator.reportMessage(" that everything copied over to it correctly.");
						generator.reportMessage(" Note: material, spawn, loot and names not copied.");
						generator.reportMessage("*********************************************");

					} else {
						settings.copySettings(generator.getPlugin().getDefaults());
					}
				} else {
					FileConfiguration defaults = generator.getPlugin().getConfig();
					if (defaults.isConfigurationSection(worldName)) {
						generator.reportMessage("*********************************************");
						generator.reportMessage("Note: CityWorld/config.yml still contains information");
						generator.reportMessage(" information about " + worldName + ", you should consider");
						generator.reportMessage(" remove it from there since it is no longer used.");
						generator.reportMessage("*********************************************");
					}
				}

				// now load it
				YamlConfiguration config = YamlConfiguration.loadConfiguration(worldConfig);
				try {
					settings.loadSettings(generator, config, worldName, worldEnv, worldType, worldStyle);

				} finally {
					config.save(worldConfig);
				}
			}
		} catch (Exception e) {
			generator.reportException("Could not load settings", e);
			return null;
		}
		return settings;
	}

	private void copySettings(CityWorldSettings otherSettings) {
		darkEnvironment = otherSettings.darkEnvironment;

		includeRoads = otherSettings.includeRoads;
		includeRoundabouts = otherSettings.includeRoundabouts;
		includeSewers = otherSettings.includeSewers;
		includeCisterns = otherSettings.includeCisterns;
		includeBasements = otherSettings.includeBasements;
		includeMines = otherSettings.includeMines;
		includeBunkers = otherSettings.includeBunkers;
		includeBuildings = otherSettings.includeBuildings;
		includeHouses = otherSettings.includeHouses;
		includeFarms = otherSettings.includeFarms;
		includeMunicipalities = otherSettings.includeMunicipalities;
		includeIndustrialSectors = otherSettings.includeIndustrialSectors;
		includeAirborneStructures = otherSettings.includeAirborneStructures;

		includeCaves = otherSettings.includeCaves;
		includeLavaFields = otherSettings.includeLavaFields;
		includeSeas = otherSettings.includeSeas;
		includeMountains = otherSettings.includeMountains;
		includeOres = otherSettings.includeOres;
		includeBones = otherSettings.includeBones;
		includeFires = otherSettings.includeFires;

		spawnBeings = otherSettings.spawnBeings;
		spawnBaddies = otherSettings.spawnBaddies;
		spawnAnimals = otherSettings.spawnAnimals;
		spawnVagrants = otherSettings.spawnVagrants;
		nameVillagers = otherSettings.nameVillagers;
		showVillagersNames = otherSettings.showVillagersNames;

		spawnersInBunkers = otherSettings.spawnersInBunkers;
		spawnersInMines = otherSettings.spawnersInMines;
		spawnersInSewers = otherSettings.spawnersInSewers;

		treasuresInBunkers = otherSettings.treasuresInBunkers;
		treasuresInMines = otherSettings.treasuresInMines;
		treasuresInSewers = otherSettings.treasuresInSewers;
		treasuresInBuildings = otherSettings.treasuresInBuildings;

		includeUndergroundFluids = otherSettings.includeUndergroundFluids;
		includeAbovegroundFluids = otherSettings.includeAbovegroundFluids;
		includeWorkingLights = otherSettings.includeWorkingLights;
		includeNamedRoads = otherSettings.includeNamedRoads;
		includeDecayedRoads = otherSettings.includeDecayedRoads;
		includeDecayedBuildings = otherSettings.includeDecayedBuildings;
		includeDecayedNature = otherSettings.includeDecayedNature;
		includeBuildingInteriors = otherSettings.includeBuildingInteriors;

		useMinecraftLootTables = otherSettings.useMinecraftLootTables;
		forceLoadWorldEdit = otherSettings.forceLoadWorldEdit;
		broadcastSpecialPlaces = otherSettings.broadcastSpecialPlaces;

		treeStyle = otherSettings.treeStyle;
		spawnTrees = otherSettings.spawnTrees;
		subSurfaceStyle = otherSettings.subSurfaceStyle;

		centerPointOfChunkRadiusX = otherSettings.centerPointOfChunkRadiusX;
		centerPointOfChunkRadiusZ = otherSettings.centerPointOfChunkRadiusZ;
		constructChunkRadius = otherSettings.constructChunkRadius;
		checkConstructRange = otherSettings.checkConstructRange;
		roadChunkRadius = otherSettings.roadChunkRadius;
		checkRoadRange = otherSettings.checkRoadRange;
		cityChunkRadius = otherSettings.cityChunkRadius;
		checkCityRange = otherSettings.checkCityRange;
		buildOutsideRadius = otherSettings.buildOutsideRadius;

		minInbetweenChunkDistanceOfCities = otherSettings.minInbetweenChunkDistanceOfCities;
		checkMinInbetweenChunkDistanceOfCities = otherSettings.checkMinInbetweenChunkDistanceOfCities;
		ruralnessLevel = otherSettings.ruralnessLevel;

		oddsOfTreasureInSewers = otherSettings.oddsOfTreasureInSewers;
		oddsOfTreasureInBunkers = otherSettings.oddsOfTreasureInBunkers;
		oddsOfTreasureInMines = otherSettings.oddsOfTreasureInMines;
		oddsOfTreasureInBuildings = otherSettings.oddsOfTreasureInBuildings;
		oddsOfAlcoveInMines = otherSettings.oddsOfAlcoveInMines;


	}

	private void loadSettings(CityWorldGenerator generator, FileConfiguration config, String worldname, Environment environment, WorldType worldtype, WorldStyle worldstyle) {

		// get the right defaults
		switch (environment) {
		case NORMAL:
			darkEnvironment = false;
			treeStyle = TreeStyle.NORMAL;
			subSurfaceStyle = SubSurfaceStyle.LAND;
			break;
		case NETHER:
			darkEnvironment = true;
			includeWorkingLights = false;
			includeDecayedRoads = true;
			includeDecayedBuildings = true;
			includeDecayedNature = true;
			treeStyle = TreeStyle.SPOOKY;
			subSurfaceStyle = SubSurfaceStyle.LAVA;
			break;
		case THE_END:
			darkEnvironment = true;
			treeStyle = TreeStyle.CRYSTAL;
			subSurfaceStyle = SubSurfaceStyle.CLOUD;
			break;
		}

		// Initialize based world style settings
		validateSettingsAgainstWorldStyle(worldstyle);

		// generator.reportMessage("Items.Count = " + itemsTreasureInBunkers.count());

		// see if the new configuration is out there?
//		// find the files
//		File pluginFolder = generator.getPlugin().getDataFolder();
//		if (pluginFolder.isDirectory()) {
//			
//			// forget all those shape and ore type and just go for the world name
//			schematicsFolder = findFolder(pluginFolder, "Schematics for " + generator.worldName);

		// add/get the configuration
		if (generator != null) {
			config.options().header("CityWorld World Options");
		} else {
			config.options().header("CityWorld Default Options");
		}
		config.options().copyDefaults(true);

		// get the right section
		ConfigurationSection section = null;

		// see if we can find the specific world
		if (config.isConfigurationSection(worldname))
			section = config.getConfigurationSection(worldname);

		// if not then create it
		if (section == null)
			section = config.createSection(worldname);

		/*
		 * Create a config in the world's folder Find the generation section Does the
		 * global config contain a world section? Load from that world section Copy that
		 * world section over to the generation section of the world's config Delete the
		 * world section in the global config Save the global config Read from the
		 * generation section Save the generation section
		 */

		// did we get a section?
		if (section != null) {

			// create items stacks

			// ===========================================================================
			// set up the defaults if needed
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
			section.addDefault(tagIncludeMunicipalities, includeMunicipalities);
			section.addDefault(tagIncludeIndustrialSectors, includeIndustrialSectors);
			section.addDefault(tagIncludeAirborneStructures, includeAirborneStructures);

			section.addDefault(tagIncludeCaves, includeCaves);
			section.addDefault(tagIncludeLavaFields, includeLavaFields);
			section.addDefault(tagIncludeSeas, includeSeas);
			section.addDefault(tagIncludeMountains, includeMountains);
			section.addDefault(tagIncludeOres, includeOres);
			section.addDefault(tagIncludeBones, includeBones);
			section.addDefault(tagIncludeFires, includeFires);

			section.addDefault(tagSpawnAnimals, spawnAnimals);
			section.addDefault(tagSpawnBeings, spawnBeings);
			section.addDefault(tagSpawnBaddies, spawnBaddies);
			section.addDefault(tagSpawnVagrants, spawnVagrants);
			section.addDefault(tagNameVillagers, nameVillagers);
			section.addDefault(tagShowVillagersNames, showVillagersNames);

			section.addDefault(tagSpawnersInBunkers, spawnersInBunkers);
			section.addDefault(tagSpawnersInMines, spawnersInMines);
			section.addDefault(tagSpawnersInSewers, spawnersInSewers);

			section.addDefault(tagTreasuresInBunkers, treasuresInBunkers);
			section.addDefault(tagTreasuresInMines, treasuresInMines);
			section.addDefault(tagTreasuresInSewers, treasuresInSewers);
			section.addDefault(tagTreasuresInBuildings, treasuresInBuildings);

			section.addDefault(tagIncludeUndergroundFluids, includeUndergroundFluids);
			section.addDefault(tagIncludeAbovegroundFluids, includeAbovegroundFluids);
			section.addDefault(tagIncludeWorkingLights, includeWorkingLights);
			section.addDefault(tagIncludeNamedRoads, includeNamedRoads);
			section.addDefault(tagIncludeDecayedRoads, includeDecayedRoads);
			section.addDefault(tagIncludeDecayedBuildings, includeDecayedBuildings);
			section.addDefault(tagIncludeDecayedNature, includeDecayedNature);
			section.addDefault(tagIncludeBuildingInteriors, includeBuildingInteriors);

			section.addDefault(tagUseMinecraftLootTables, useMinecraftLootTables);
			section.addDefault(tagForceLoadWorldEdit, forceLoadWorldEdit);
			section.addDefault(tagBroadcastSpecialPlaces, broadcastSpecialPlaces);

			section.addDefault(tagTreeStyle, TreeStyle.NORMAL.name());
			section.addDefault(tagSpawnTrees, spawnTrees);
//			section.addDefault(tagOddsOfFoliage, oddsOfFoliage);
			section.addDefault(tagSubSurfaceStyle, SubSurfaceStyle.LAND.name());

			section.addDefault(tagCenterPointOfChunkRadiusX, centerPointOfChunkRadiusX);
			section.addDefault(tagCenterPointOfChunkRadiusZ, centerPointOfChunkRadiusZ);
			section.addDefault(tagConstructChunkRadius, constructChunkRadius);
			section.addDefault(tagRoadChunkRadius, roadChunkRadius);
			section.addDefault(tagCityChunkRadius, cityChunkRadius);
			section.addDefault(tagBuildOutsideRadius, buildOutsideRadius);

			section.addDefault(tagMinInbetweenChunkDistanceOfCities, minInbetweenChunkDistanceOfCities);
			section.addDefault(tagRuralnessLevel, ruralnessLevel);

			// ===========================================================================
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
			includeMunicipalities = section.getBoolean(tagIncludeMunicipalities, includeMunicipalities);
			includeIndustrialSectors = section.getBoolean(tagIncludeIndustrialSectors, includeIndustrialSectors);
			includeAirborneStructures = section.getBoolean(tagIncludeAirborneStructures, includeAirborneStructures);

			includeCaves = section.getBoolean(tagIncludeCaves, includeCaves);
			includeLavaFields = section.getBoolean(tagIncludeLavaFields, includeLavaFields);
			includeSeas = section.getBoolean(tagIncludeSeas, includeSeas);
			includeMountains = section.getBoolean(tagIncludeMountains, includeMountains);
			includeOres = section.getBoolean(tagIncludeOres, includeOres);
			includeBones = section.getBoolean(tagIncludeBones, includeBones);
			includeFires = section.getBoolean(tagIncludeFires, includeFires);

			spawnAnimals = limitTo(section.getDouble(tagSpawnAnimals, spawnAnimals), 0.0, 1.0);
			spawnBeings = limitTo(section.getDouble(tagSpawnBeings, spawnBeings), 0.0, 1.0);
			spawnBaddies = limitTo(section.getDouble(tagSpawnBaddies, spawnBaddies), 0.0, 1.0);
			spawnVagrants = limitTo(section.getDouble(tagSpawnVagrants, spawnVagrants), 0.0, 1.0);
			nameVillagers = section.getBoolean(tagNameVillagers, nameVillagers);
			showVillagersNames = section.getBoolean(tagShowVillagersNames, showVillagersNames);

			spawnersInBunkers = section.getBoolean(tagSpawnersInBunkers, spawnersInBunkers);
			spawnersInMines = section.getBoolean(tagSpawnersInMines, spawnersInMines);
			spawnersInSewers = section.getBoolean(tagSpawnersInSewers, spawnersInSewers);

			treasuresInBunkers = section.getBoolean(tagTreasuresInBunkers, treasuresInBunkers);
			treasuresInMines = section.getBoolean(tagTreasuresInMines, treasuresInMines);
			treasuresInSewers = section.getBoolean(tagTreasuresInSewers, treasuresInSewers);
			treasuresInBuildings = section.getBoolean(tagTreasuresInBuildings, treasuresInBuildings);

			if (generator != null) {
				generator.materialProvider.read(generator, section);
				generator.spawnProvider.read(generator, section);
				generator.odonymProvider.read(generator, section);
			}

			includeUndergroundFluids = section.getBoolean(tagIncludeUndergroundFluids, includeUndergroundFluids);
			includeAbovegroundFluids = section.getBoolean(tagIncludeAbovegroundFluids, includeAbovegroundFluids);
			includeWorkingLights = section.getBoolean(tagIncludeWorkingLights, includeWorkingLights);
			includeNamedRoads = section.getBoolean(tagIncludeNamedRoads, includeNamedRoads);
			includeDecayedRoads = section.getBoolean(tagIncludeDecayedRoads, includeDecayedRoads);
			includeDecayedBuildings = section.getBoolean(tagIncludeDecayedBuildings, includeDecayedBuildings);
			includeDecayedNature = section.getBoolean(tagIncludeDecayedNature, includeDecayedNature);
			includeBuildingInteriors = section.getBoolean(tagIncludeBuildingInteriors, includeBuildingInteriors);

			useMinecraftLootTables = section.getBoolean(tagUseMinecraftLootTables, useMinecraftLootTables);
			forceLoadWorldEdit = section.getBoolean(tagForceLoadWorldEdit, forceLoadWorldEdit);
			broadcastSpecialPlaces = section.getBoolean(tagBroadcastSpecialPlaces, broadcastSpecialPlaces);

			treeStyle = TreeProvider.toTreeStyle(section.getString(tagTreeStyle, treeStyle.name()), treeStyle);
			spawnTrees = limitTo(section.getDouble(tagSpawnTrees, spawnTrees), 0.0, 1.0);
//			oddsOfFoliage = limitTo(section.getDouble(tagOddsOfFoliage, oddsOfFoliage), 0.0, 1.0);

			if (section.contains(tagSubSurfaceStyle)) { // are we using the next property yet?
				subSurfaceStyle = SurfaceProvider_Floating.toSubSurfaceStyle(
						section.getString(tagSubSurfaceStyle, subSurfaceStyle.name()), subSurfaceStyle);

			} else { // still old property, lets read it one last time
				if (section.getBoolean(tagIncludeFloatingSubsurface, true))
					subSurfaceStyle = SubSurfaceStyle.LAND;
			}

			centerPointOfChunkRadiusX = section.getInt(tagCenterPointOfChunkRadiusX, centerPointOfChunkRadiusX);
			centerPointOfChunkRadiusZ = section.getInt(tagCenterPointOfChunkRadiusZ, centerPointOfChunkRadiusZ);
			centerPointOfChunkRadius = new Vector(centerPointOfChunkRadiusX, 0, centerPointOfChunkRadiusZ);
			constructChunkRadius = limitTo(section.getInt(tagConstructChunkRadius, constructChunkRadius), 0, maxRadius);
			roadChunkRadius = limitTo(section.getInt(tagRoadChunkRadius, roadChunkRadius), 0, maxRadius);
			cityChunkRadius = limitTo(section.getInt(tagCityChunkRadius, cityChunkRadius), 0, maxRadius);
			buildOutsideRadius = section.getBoolean(tagBuildOutsideRadius, buildOutsideRadius);

			minInbetweenChunkDistanceOfCities = limitTo(
					section.getInt(tagMinInbetweenChunkDistanceOfCities, minInbetweenChunkDistanceOfCities), 0,
					maxRadius);
			ruralnessLevel = limitTo(section.getDouble(tagRuralnessLevel, ruralnessLevel), 0.0, 1.0);

			// validate the range values
			if (buildOutsideRadius) {
				constructChunkRadius = Math.max(0, constructChunkRadius);
				roadChunkRadius = Math.max(constructChunkRadius, roadChunkRadius);
				cityChunkRadius = Math.max(roadChunkRadius, cityChunkRadius);

				checkConstructRange = constructChunkRadius > 0;
				checkRoadRange = roadChunkRadius > 0;
				checkCityRange = cityChunkRadius > 0;

				if (roadChunkRadius == Integer.MAX_VALUE) {
					includeRoads = false;
					includeSewers = false;
				}
				if (cityChunkRadius == Integer.MAX_VALUE) {
					includeCisterns = false;
					includeBasements = false;
					includeMines = false;
					includeBunkers = false;
					includeBuildings = false;
					includeHouses = false;
					includeFarms = false;
				}
			} else {
				constructChunkRadius = Math.min(Integer.MAX_VALUE, constructChunkRadius);
				roadChunkRadius = Math.min(constructChunkRadius, roadChunkRadius);
				cityChunkRadius = Math.min(roadChunkRadius, cityChunkRadius);

				checkConstructRange = constructChunkRadius < Integer.MAX_VALUE;
				checkRoadRange = roadChunkRadius < Integer.MAX_VALUE;
				checkCityRange = cityChunkRadius < Integer.MAX_VALUE;

				if (roadChunkRadius == 0) {
					includeRoads = false;
					includeSewers = false;
				}
				if (cityChunkRadius == 0) {
					includeCisterns = false;
					includeBasements = false;
					includeMines = false;
					includeBunkers = false;
					includeBuildings = false;
					includeHouses = false;
					includeFarms = false;
				}
			}
			checkMinInbetweenChunkDistanceOfCities = minInbetweenChunkDistanceOfCities > 0;

			// ===========================================================================
			// validate settings against world style settings
			validateSettingsAgainstWorldStyle(worldstyle);

			// ===========================================================================
			// write things back out with corrections
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
			section.set(tagIncludeMunicipalities, includeMunicipalities);
			section.set(tagIncludeIndustrialSectors, includeIndustrialSectors);
			section.set(tagIncludeAirborneStructures, includeAirborneStructures);

			section.set(tagIncludeCaves, includeCaves);
			section.set(tagIncludeLavaFields, includeLavaFields);
			section.set(tagIncludeSeas, includeSeas);
			section.set(tagIncludeMountains, includeMountains);
			section.set(tagIncludeOres, includeOres);
			section.set(tagIncludeBones, includeBones);
			section.set(tagIncludeFires, includeFires);

			section.set(tagSpawnAnimals, spawnAnimals);
			section.set(tagSpawnBeings, spawnBeings);
			section.set(tagSpawnBaddies, spawnBaddies);
			section.set(tagSpawnVagrants, spawnVagrants);
			section.set(tagNameVillagers, nameVillagers);
			section.set(tagShowVillagersNames, showVillagersNames);

			section.set(tagSpawnersInBunkers, spawnersInBunkers);
			section.set(tagSpawnersInMines, spawnersInMines);
			section.set(tagSpawnersInSewers, spawnersInSewers);

			section.set(tagTreasuresInBunkers, treasuresInBunkers);
			section.set(tagTreasuresInMines, treasuresInMines);
			section.set(tagTreasuresInSewers, treasuresInSewers);
			section.set(tagTreasuresInBuildings, treasuresInBuildings);

			section.set(tagIncludeUndergroundFluids, includeUndergroundFluids);
			section.set(tagIncludeAbovegroundFluids, includeAbovegroundFluids);
			section.set(tagIncludeWorkingLights, includeWorkingLights);
			section.set(tagIncludeNamedRoads, includeNamedRoads);
			section.set(tagIncludeDecayedRoads, includeDecayedRoads);
			section.set(tagIncludeDecayedBuildings, includeDecayedBuildings);
			section.set(tagIncludeDecayedNature, includeDecayedNature);
			section.set(tagIncludeBuildingInteriors, includeBuildingInteriors);

			section.set(tagUseMinecraftLootTables, useMinecraftLootTables);
			section.set(tagForceLoadWorldEdit, forceLoadWorldEdit);
			section.set(tagBroadcastSpecialPlaces, broadcastSpecialPlaces);

			section.set(tagTreeStyle, treeStyle.name());
			section.set(tagSpawnTrees, spawnTrees);
//			section.set(tagOddsOfFoliage, oddsOfFoliage);
			section.set(tagSubSurfaceStyle, subSurfaceStyle.name());

			section.set(tagCenterPointOfChunkRadiusX, centerPointOfChunkRadiusX);
			section.set(tagCenterPointOfChunkRadiusZ, centerPointOfChunkRadiusZ);
			section.set(tagConstructChunkRadius, constructChunkRadius);
			section.set(tagRoadChunkRadius, roadChunkRadius);
			section.set(tagCityChunkRadius, cityChunkRadius);
			section.set(tagBuildOutsideRadius, buildOutsideRadius);

			section.set(tagMinInbetweenChunkDistanceOfCities, minInbetweenChunkDistanceOfCities);
			section.set(tagRuralnessLevel, ruralnessLevel);

			if (generator != null) {
				generator.materialProvider.write(generator, section);
				generator.spawnProvider.write(generator, section);
				generator.odonymProvider.write(generator, section);
			}

			// ===========================================================================
			// note the depreciations
			deprecateOption(section, "IncludeWoolRoads",
					"DEPRECATED: CityWorld now uses stained clay and quartz for roads");
			deprecateOption(section, "IncludePavedRoads",
					"DEPRECATED: See deprecated note for IncludeWoolRoads");
			deprecateOption(section, "RoadRange",
					"DEPRECATED: Use RoadChunkRadius instead");
			deprecateOption(section, "CityRange",
					"DEPRECATED: Use CityChunkRadius instead");
			deprecateOption(section, "IncludeTekkitMaterials",
					"DEPRECATED: ForgeTekkit is auto-recognized");
			deprecateOption(section, "ForceLoadTekkit",
					"DEPRECATED: Direct Tekkit support removed as of 3.0");
			deprecateOption(section, "IncludeFloatingSubsurface",
					"DEPRECATED: Use SubSurfaceStyle instead");
		}
	}

	private void validateSettingsAgainstWorldStyle(WorldStyle style) {
		// now get the right defaults for the world style
		// anything commented out is up for user modification
		switch (style) {
		case NORMAL:
		case METRO:
			subSurfaceStyle = SubSurfaceStyle.NONE; // DIFFERENT
			break;
		case SPARSE:
			centerPointOfChunkRadiusX = 0; // DIFFERENT
			centerPointOfChunkRadiusZ = 0; // DIFFERENT
			constructChunkRadius = 150; // DIFFERENT
			roadChunkRadius = 150; // DIFFERENT
			cityChunkRadius = 50; // DIFFERENT
			buildOutsideRadius = false; // DIFFERENT
			minInbetweenChunkDistanceOfCities = 100; // DIFFERENT

			subSurfaceStyle = SubSurfaceStyle.NONE; // DIFFERENT
			break;
		case NATURE:
			includeRoads = false; // DIFFERENT
			includeRoundabouts = false; // DIFFERENT
			break;
		case DESTROYED:
			includeDecayedRoads = true; // DIFFERENT
			includeDecayedBuildings = true; // DIFFERENT
			includeDecayedNature = true; // DIFFERENT
			includeAirborneStructures = false; // DIFFERENT;

			subSurfaceStyle = SubSurfaceStyle.NONE; // DIFFERENT
			break;
		case MAZE:
			includeRoads = true; // This has to be true in order for things to generate correctly
			includeRoundabouts = false; // DIFFERENT
			includeMines = false; // DIFFERENT
			includeBunkers = false; // DIFFERENT

			spawnersInMines = false; // DIFFERENT
			spawnersInBunkers = false; // DIFFERENT
			treasuresInMines = false; // DIFFERENT
			treasuresInBunkers = false; // DIFFERENT

			subSurfaceStyle = SubSurfaceStyle.NONE; // DIFFERENT
			break;
		case ASTRAL:
			includeRoundabouts = false; // DIFFERENT
			includeSewers = false; // DIFFERENT
			includeCisterns = false; // DIFFERENT
			includeBasements = false; // DIFFERENT
			includeMines = false; // DIFFERENT
			includeBunkers = false; // DIFFERENT
			includeFarms = false; // DIFFERENT
			includeAirborneStructures = false; // DIFFERENT;

			includeSeas = true; // THIS MUST BE SET TO TRUE
			includeMountains = true; // THIS MUST BE SET TO TRUE

			spawnersInBunkers = false; // DIFFERENT
			spawnersInMines = false; // DIFFERENT
			spawnersInSewers = false; // DIFFERENT

			treasuresInBunkers = false; // DIFFERENT
			treasuresInMines = false; // DIFFERENT
			treasuresInSewers = false; // DIFFERENT

			includeUndergroundFluids = false; // THIS MUST BE SET TO FALSE
			includeAbovegroundFluids = false; // THIS MUST BE SET TO FALSE
			subSurfaceStyle = SubSurfaceStyle.NONE; // DIFFERENT
			break;
		case FLOATING:
			includeMines = false; // DIFFERENT
			includeBunkers = false; // DIFFERENT
			includeAirborneStructures = false; // DIFFERENT;

			includeCaves = false; // DIFFERENT
			includeLavaFields = false; // DIFFERENT
			includeSeas = false; // DIFFERENT
			includeMountains = true; // THIS MUST BE SET TO TRUE
			includeOres = false; // DIFFERENT
			includeBones = false; // DIFFERENT
			includeFires = false; // DIFFERENT

			spawnersInBunkers = false; // DIFFERENT
			spawnersInMines = false; // DIFFERENT
			spawnersInSewers = false; // DIFFERENT

			treasuresInBunkers = false; // DIFFERENT
			treasuresInMines = false; // DIFFERENT
			treasuresInSewers = false; // DIFFERENT

			includeUndergroundFluids = false; // DIFFERENT
			includeAbovegroundFluids = true; // THIS MUST BE SET TO TRUE

			break;
		case FLOODED:
			includeRoundabouts = false; // DIFFERENT
			includeSewers = false; // DIFFERENT
			includeMines = false; // DIFFERENT
			includeBunkers = false; // DIFFERENT
			includeAirborneStructures = false; // DIFFERENT;

			includeCaves = false; // DIFFERENT
			includeLavaFields = false; // DIFFERENT
			includeSeas = true; // THIS MUST BE SET TO TRUE
			includeMountains = true; // THIS MUST BE SET TO TRUE
			includeFires = false; // DIFFERENT

			spawnersInBunkers = false; // DIFFERENT
			spawnersInMines = false; // DIFFERENT
			spawnersInSewers = false; // DIFFERENT

			treasuresInBunkers = false; // DIFFERENT
			treasuresInMines = false; // DIFFERENT
			treasuresInSewers = false; // DIFFERENT

			includeUndergroundFluids = false; // DIFFERENT
			includeAbovegroundFluids = true; // THIS MUST BE SET TO TRUE
			includeWorkingLights = false; // DIFFERENT
			includeNamedRoads = false; // DIFFERENT
			includeDecayedRoads = false; // DIFFERENT
			includeDecayedBuildings = false; // DIFFERENT
			subSurfaceStyle = SubSurfaceStyle.NONE; // DIFFERENT
			break;
		case SANDDUNES:
			includeRoundabouts = false; // DIFFERENT
			includeSewers = false; // DIFFERENT
			includeMines = false; // DIFFERENT
			includeBunkers = false; // DIFFERENT
			includeAirborneStructures = false; // DIFFERENT;

			includeCaves = false; // DIFFERENT
			includeLavaFields = false; // DIFFERENT
			includeSeas = true; // THIS MUST BE SET TO TRUE
			includeMountains = true; // THIS MUST BE SET TO TRUE

			spawnersInBunkers = false; // DIFFERENT
			spawnersInMines = false; // DIFFERENT
			spawnersInSewers = false; // DIFFERENT

			treasuresInBunkers = false; // DIFFERENT
			treasuresInMines = false; // DIFFERENT
			treasuresInSewers = false; // DIFFERENT

			includeAbovegroundFluids = false; // THIS MUST BE SET TO FALSE
			includeWorkingLights = false; // DIFFERENT
			includeNamedRoads = false; // DIFFERENT
			includeDecayedNature = true; // DIFFERENT
			subSurfaceStyle = SubSurfaceStyle.NONE; // DIFFERENT
			break;
		case SNOWDUNES:
			includeRoundabouts = false; // DIFFERENT
			includeSewers = false; // DIFFERENT
			includeMines = false; // DIFFERENT
			includeBunkers = false; // DIFFERENT
			includeAirborneStructures = false; // DIFFERENT;

			includeCaves = false; // DIFFERENT
			includeLavaFields = false; // DIFFERENT
			includeSeas = true; // THIS MUST BE SET TO TRUE
			includeMountains = true; // THIS MUST BE SET TO TRUE

			spawnersInBunkers = false; // DIFFERENT
			spawnersInMines = false; // DIFFERENT
			spawnersInSewers = false; // DIFFERENT

			treasuresInBunkers = false; // DIFFERENT
			treasuresInMines = false; // DIFFERENT
			treasuresInSewers = false; // DIFFERENT

			includeAbovegroundFluids = true; // THIS MUST BE SET TO TRUE
			includeWorkingLights = false; // DIFFERENT
			includeNamedRoads = false; // DIFFERENT
			subSurfaceStyle = SubSurfaceStyle.NONE; // DIFFERENT
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

	private double limitTo(double value, double min, double max) {
		return Math.max(min, Math.min(max, value));
	}

	private int limitTo(int value, int min, int max) {
		return Math.max(min, Math.min(max, value));
	}

	private Vector centerPointOfChunkRadius;

	private Vector getCenterPoint(int x, int z) {
		if (checkMinInbetweenChunkDistanceOfCities)
			return new Vector(calcOrigin(x, centerPointOfChunkRadiusX), 0, calcOrigin(z, centerPointOfChunkRadiusZ));
		else
			return centerPointOfChunkRadius;
	}

	// Supporting code used by getPlatMap
	private int calcOrigin(int i, int offset) {
		i = i - offset;
		if (i >= 0) {
			i = i / minInbetweenChunkDistanceOfCities * minInbetweenChunkDistanceOfCities;
		} else {
			i = -((Math.abs(i + 1) / minInbetweenChunkDistanceOfCities * minInbetweenChunkDistanceOfCities)
					+ minInbetweenChunkDistanceOfCities);
		}
		return i + offset;
	}

	public boolean inConstructRange(int x, int z) {
		if (checkConstructRange) {
			Vector centerPoint = getCenterPoint(x, z);
			if (buildOutsideRadius)
				return centerPoint.distance(new Vector(x, 0, z)) > constructChunkRadius;
			else
				return centerPoint.distance(new Vector(x, 0, z)) <= constructChunkRadius;
		}
		return true;
	}

	public boolean inRoadRange(int x, int z) {
		if (checkRoadRange) {
			Vector centerPoint = getCenterPoint(x, z);
			if (buildOutsideRadius)
				return centerPoint.distance(new Vector(x, 0, z)) > roadChunkRadius;
			else
				return centerPoint.distance(new Vector(x, 0, z)) <= roadChunkRadius;
		}
		return true;
	}

	public boolean inCityRange(int x, int z) {
		if (checkCityRange) {
			Vector centerPoint = getCenterPoint(x, z);
			if (buildOutsideRadius)
				return centerPoint.distance(new Vector(x, 0, z)) > cityChunkRadius;
			else
				return centerPoint.distance(new Vector(x, 0, z)) <= cityChunkRadius;
		}
		return true;
	}
}
