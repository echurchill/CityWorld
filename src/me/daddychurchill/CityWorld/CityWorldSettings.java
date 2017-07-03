package me.daddychurchill.CityWorld;

import me.daddychurchill.CityWorld.Plugins.SurfaceProvider_Floating;
import me.daddychurchill.CityWorld.Plugins.SurfaceProvider_Floating.SubSurfaceStyle;
import me.daddychurchill.CityWorld.Plugins.TreeProvider;
import me.daddychurchill.CityWorld.Plugins.TreeProvider.TreeStyle;
import me.daddychurchill.CityWorld.Support.AbstractBlocks;
import me.daddychurchill.CityWorld.Support.Odds;
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
	public boolean includeAirborneStructures = true;

	public boolean includeCaves = true;
	public boolean includeLavaFields = true;
	public boolean includeSeas = true;
	public boolean includeMountains = true;
	public boolean includeOres = true;
	public boolean includeBones = true;
	
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
	
	public boolean forceLoadWorldEdit = false;
	public boolean broadcastSpecialPlaces = false;
	
	public TreeStyle treeStyle = TreeStyle.NORMAL;
	public double spawnTrees = Odds.oddsLikely;
//	public double oddsOfFoliage = Odds.oddsAlwaysGoingToHappen;
	public SubSurfaceStyle subSurfaceStyle = SubSurfaceStyle.LAND;

	public final static int maxRadius = 30000000 / AbstractBlocks.sectionBlockWidth; // 1875000 is the actual maximum chunk limit for today's minecraft world format
	public int centerPointOfChunkRadiusX = 0;
	public int centerPointOfChunkRadiusZ = 0;
	public int constructChunkRadius = maxRadius;
	private boolean checkConstructRange = false;
	public int roadChunkRadius = maxRadius;
	private boolean checkRoadRange = false;
	public int cityChunkRadius = maxRadius;
	private boolean checkCityRange = false;
	private boolean buildOutsideRadius = false;
	
	public int minInbetweenChunkDistanceOfCities = 0;
	private boolean checkMinInbetweenChunkDistanceOfCities = false;
	public double ruralnessLevel = 0.0;
	
	public double oddsOfTreasureInSewers = Odds.oddsLikely;
	public double oddsOfTreasureInBunkers = Odds.oddsLikely;
	public double oddsOfTreasureInMines = Odds.oddsLikely;
	public double oddsOfTreasureInBuildings = Odds.oddsLikely;
	public double oddsOfAlcoveInMines = Odds.oddsVeryLikely;
	
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
	public final static String tagIncludeAirborneStructures = "IncludeAirborneStructures";

	public final static String tagIncludeCaves = "IncludeCaves";
	public final static String tagIncludeLavaFields = "IncludeLavaFields";
	public final static String tagIncludeSeas = "IncludeSeas";
	public final static String tagIncludeMountains = "IncludeMountains";
	public final static String tagIncludeOres = "IncludeOres";
	public final static String tagIncludeBones = "IncludeBones";
	
	public final static String tagSpawnAnimals = "SpawnAnimals";
	public final static String tagSpawnBeings = "SpawnBeings";
	public final static String tagSpawnBaddies = "SpawnBaddies";
	public final static String tagSpawnVagrants = "SpawnVagrants";
	public final static String tagNameVillagers = "NameVillagers";
	public final static String tagShowVillagersNames = "ShowVillagersNames";
	
	public final static String tagSpawnersInBunkers = "SpawnersInBunkers";
	public final static String tagSpawnersInMines = "SpawnersInMines";
	public final static String tagSpawnersInSewers = "SpawnersInSewers";
	
	public final static String tagTreasuresInBunkers = "TreasuresInBunkers";
	public final static String tagTreasuresInMines = "TreasuresInMines";
	public final static String tagTreasuresInSewers = "TreasuresInSewers";
	public final static String tagTreasuresInBuildings = "TreasuresInBuildings";

	public final static String tagIncludeUndergroundFluids = "IncludeUndergroundFluids";
	public final static String tagIncludeAbovegroundFluids = "IncludeAbovegroundFluids";
	public final static String tagIncludeWorkingLights = "IncludeWorkingLights";
	public final static String tagIncludeNamedRoads = "IncludeNamedRoads";
	public final static String tagIncludeDecayedRoads = "IncludeDecayedRoads";
	public final static String tagIncludeDecayedBuildings = "IncludeDecayedBuildings";
	public final static String tagIncludeDecayedNature = "IncludeDecayedNature";
	public final static String tagIncludeBuildingInteriors = "IncludeBuildingInteriors";
	public final static String tagIncludeFloatingSubsurface = "IncludeFloatingSubsurface";
	
	public final static String tagForceLoadWorldEdit = "ForceLoadWorldEdit";
	public final static String tagBroadcastSpecialPlaces = "BroadcastSpecialPlaces";
	
	public final static String tagTreeStyle = "TreeStyle";
	public final static String tagSpawnTrees = "SpawnTrees";
//	public final static String tagOddsOfFoliage = "OddsOfFoliage";
	public final static String tagSubSurfaceStyle = "SubSurfaceStyle";
	
	public final static String tagCenterPointOfChunkRadiusX = "CenterPointOfChunkRadiusX";
	public final static String tagCenterPointOfChunkRadiusZ = "CenterPointOfChunkRadiusZ";
	public final static String tagConstructChunkRadius = "ConstructChunkRadius";
	public final static String tagRoadChunkRadius = "RoadChunkRadius";
	public final static String tagCityChunkRadius = "CityChunkRadius";
	public final static String tagBuildOutsideRadius = "BuildOutsideRadius";
	
	public final static String tagMinInbetweenChunkDistanceOfCities = "MinInbetweenChunkDistanceOfCities";
	public final static String tagRuralnessLevel = "RuralnessLevel";
	
//	public final static String tagOddsOfTreasureInSewers = "OddsOfTreasureInSewers";
//	public final static String tagOddsOfTreasureInBunkers = "OddsOfTreasureInBunkers";
//	public final static String tagOddsOfTreasureInMines = "OddsOfTreasureInMines";
//	public final static String tagOddsOfTreasureInBuildings = "OddsOfTreasureInBuildings";
//	public final static String tagOddsOfAlcovesInMines = "OddsOfAlcovesInMines";
	
	public CityWorldSettings(CityWorldGenerator generator) {
		super();
		String worldname = generator.worldName;
		generator.worldEnvironment = generator.getWorld().getEnvironment();
		
		// get the right defaults
		switch (generator.worldEnvironment) {
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
		validateSettingsAgainstWorldStyle(generator);
		
		//generator.reportMessage("Items.Count = " + itemsTreasureInBunkers.count());
		
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
		if (section == null)
			section = config.createSection(worldname);
		
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
			
			//===========================================================================
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
			section.addDefault(tagIncludeAirborneStructures, includeAirborneStructures);
			
			section.addDefault(tagIncludeCaves, includeCaves);
			section.addDefault(tagIncludeLavaFields, includeLavaFields);
			section.addDefault(tagIncludeSeas, includeSeas);
			section.addDefault(tagIncludeMountains, includeMountains);
			section.addDefault(tagIncludeOres, includeOres);
			section.addDefault(tagIncludeBones, includeBones);
			
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

			//===========================================================================
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
			includeAirborneStructures = section.getBoolean(tagIncludeAirborneStructures, includeAirborneStructures);
			
			includeCaves = section.getBoolean(tagIncludeCaves, includeCaves);
			includeLavaFields = section.getBoolean(tagIncludeLavaFields, includeLavaFields);
			includeSeas = section.getBoolean(tagIncludeSeas, includeSeas);
			includeMountains = section.getBoolean(tagIncludeMountains, includeMountains);
			includeOres = section.getBoolean(tagIncludeOres, includeOres);
			includeBones = section.getBoolean(tagIncludeBones, includeBones);

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

			generator.materialProvider.read(generator, section);
			generator.spawnProvider.read(generator, section);
			generator.odonymProvider.read(generator, section);
			
			includeUndergroundFluids = section.getBoolean(tagIncludeUndergroundFluids, includeUndergroundFluids);
			includeAbovegroundFluids = section.getBoolean(tagIncludeAbovegroundFluids, includeAbovegroundFluids);
			includeWorkingLights = section.getBoolean(tagIncludeWorkingLights, includeWorkingLights);
			includeNamedRoads = section.getBoolean(tagIncludeNamedRoads, includeNamedRoads);
			includeDecayedRoads = section.getBoolean(tagIncludeDecayedRoads, includeDecayedRoads);
			includeDecayedBuildings = section.getBoolean(tagIncludeDecayedBuildings, includeDecayedBuildings);
			includeDecayedNature = section.getBoolean(tagIncludeDecayedNature, includeDecayedNature);
			includeBuildingInteriors = section.getBoolean(tagIncludeBuildingInteriors, includeBuildingInteriors);
			
			forceLoadWorldEdit = section.getBoolean(tagForceLoadWorldEdit, forceLoadWorldEdit);
			broadcastSpecialPlaces = section.getBoolean(tagBroadcastSpecialPlaces, broadcastSpecialPlaces);
			
			treeStyle = TreeProvider.toTreeStyle(section.getString(tagTreeStyle, treeStyle.name()), treeStyle);
			spawnTrees = limitTo(section.getDouble(tagSpawnTrees, spawnTrees), 0.0, 1.0);
//			oddsOfFoliage = limitTo(section.getDouble(tagOddsOfFoliage, oddsOfFoliage), 0.0, 1.0);
			
			if (section.contains(tagSubSurfaceStyle)) { // are we using the next property yet?
				subSurfaceStyle = SurfaceProvider_Floating.toSubSurfaceStyle(section.getString(tagSubSurfaceStyle, subSurfaceStyle.name()), subSurfaceStyle);
				
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
			
			minInbetweenChunkDistanceOfCities = limitTo(section.getInt(tagMinInbetweenChunkDistanceOfCities, minInbetweenChunkDistanceOfCities), 0, maxRadius);
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

			//===========================================================================
			// validate settings against world style settings
			validateSettingsAgainstWorldStyle(generator);
			
			//===========================================================================
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
			section.set(tagIncludeAirborneStructures, includeAirborneStructures);
			
			section.set(tagIncludeCaves, includeCaves);
			section.set(tagIncludeLavaFields, includeLavaFields);
			section.set(tagIncludeSeas, includeSeas);
			section.set(tagIncludeMountains, includeMountains);
			section.set(tagIncludeOres, includeOres);
			section.set(tagIncludeBones, includeBones);
			
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
			
			generator.materialProvider.write(generator, section);
			generator.spawnProvider.write(generator, section);
			generator.odonymProvider.write(generator, section);
			
			//===========================================================================
			// note the depreciations
			deprecateOption(section, "IncludeWoolRoads", "DEPRECATED: CityWorld now uses stained clay and quartz for roads");
			deprecateOption(section, "IncludePavedRoads", "DEPRECATED: See deprecated note for IncludeWoolRoads");
			deprecateOption(section, "RoadRange", "DEPRECATED: Use RoadChunkRadius instead");
			deprecateOption(section, "CityRange", "DEPRECATED: Use CityChunkRadius instead");
			deprecateOption(section, "IncludeTekkitMaterials", "DEPRECATED: ForgeTekkit is auto-recognized");
			deprecateOption(section, "ForceLoadTekkit", "DEPRECATED: Direct Tekkit support removed as of 3.0");
			deprecateOption(section, "IncludeFloatingSubsurface", "DEPRECATED: Use SubSurfaceStyle instead");
			
			//===========================================================================
			// write it back out 
			plugin.saveConfig();
		}
		
	}
	
	private void validateSettingsAgainstWorldStyle(CityWorldGenerator generator) {
		// now get the right defaults for the world style
		// anything commented out is up for user modification
		switch (generator.worldStyle) {
		case NORMAL:
		case METRO:
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
			i = -((Math.abs(i + 1) / minInbetweenChunkDistanceOfCities * minInbetweenChunkDistanceOfCities) + minInbetweenChunkDistanceOfCities);
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
		} return true;
	}
	
	public boolean inRoadRange(int x, int z) {
		if (checkRoadRange) {
			Vector centerPoint = getCenterPoint(x, z);
			if (buildOutsideRadius)
				return centerPoint.distance(new Vector(x, 0, z)) > roadChunkRadius;
			else
				return centerPoint.distance(new Vector(x, 0, z)) <= roadChunkRadius;
		} return true;
	}
	
	public boolean inCityRange(int x, int z) {
		if (checkCityRange) {
			Vector centerPoint = getCenterPoint(x, z);
			if (buildOutsideRadius)
				return centerPoint.distance(new Vector(x, 0, z)) > cityChunkRadius;
			else
				return centerPoint.distance(new Vector(x, 0, z)) <= cityChunkRadius;
		} return true;
	}
}
