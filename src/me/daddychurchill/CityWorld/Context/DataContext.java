package me.daddychurchill.CityWorld.Context;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Clipboard.ClipboardList;
import me.daddychurchill.CityWorld.Clipboard.PasteProvider.SchematicFamily;
import me.daddychurchill.CityWorld.Support.PlatMap;

public abstract class DataContext {
	
	// see PlatMaps.xlsx for more info on this Fibonacci variant
	public final static double oddsNeverGoingToHappen =			 0.0;          //   0.00%
	public final static double oddsNearlyNeverGoingToHappen =	 1.0 / 377.00; //   0.27%
	public final static double oddsEffinUnlikely =				 1.0 / 233.00; //   0.43%

	public final static double oddsTremendouslyUnlikely =		 1.0 / 144.00; //   0.69%
	public final static double oddsExceedinglyUnlikely =		 1.0 /  89.00; //   1.12%
	public final static double oddsEnormouslyUnlikely =			 1.0 /  55.00; //   1.82%
	public final static double oddsExtremelyUnlikely =			 1.0 /  34.00; //   2.94%
	public final static double oddsPrettyUnlikely =				 1.0 /  21.00; //   4.76%
	public final static double oddsVeryUnlikely =				 1.0 /  13.00; //   7.69%
	public final static double oddsUnlikely =					 1.0 /   8.00; //  12.50%
	public final static double oddsSomewhatUnlikely =			 1.0 /   5.00; //  20.00%

	public final static double oddsSomewhatLikely =				 1.0 /   3.00; //  33.33%
	public final static double oddsLikely =						 1.0 /   2.00; //  50.00%
	public final static double oddsVeryLikely =					 2.0 /   3.00; //  66.67%
	public final static double oddsPrettyLikely =				 4.0 /   5.00; //  80.00%
	public final static double oddsExtremelyLikely =			 7.0 /   8.00; //  87.50%
	public final static double oddsEnormouslyLikely =			12.0 /  13.00; //  92.31%
	public final static double oddsExceedinglyLikely =			20.0 /  21.00; //  95.24%
	public final static double oddsTremendouslyLikely =			33.0 /  34.00; //  97.06%
	
	public final static double oddsEffinLikely =				54.0 /  55.00; //  98.18%
	public final static double oddsNearlyAlwaysGoingToHappen =	88.0 /  89.00; //  98.88%
	public final static double oddsAlwaysGoingToHappen =		 1.0;          // 100.00%
	
	// While these are initialized here, the real defaults live in CivilizedContext and UncivilizedContext
	
	public double oddsOfIsolatedLots = oddsNeverGoingToHappen; // isolated buildings 1/n of the time
	public double oddsOfIsolatedConstructs = oddsNeverGoingToHappen;
	
	public double oddsOfParks = oddsNeverGoingToHappen; // parks show up 1/n of the time
	
	public double oddsOfIdenticalBuildingHeights = oddsNeverGoingToHappen; // similar height 1/n of the time
	public double oddsOfSimilarBuildingHeights = oddsNeverGoingToHappen; // identical height 1/n of the time
	public double oddsOfRoundedBuilding = oddsLikely; // how naturally rounded are buildings that can be rounded
	public double oddsOfSimilarBuildingRounding = oddsNeverGoingToHappen; // like rounding 1/n of the time
	public double oddsOfStairWallMaterialIsWallMaterial = oddsNeverGoingToHappen; // stair walls are the same as walls 1/n of the time
	public int buildingWallInsettedMinLowPoint; // minimum building height before insetting is allowed
	public int buildingWallInsettedMinMidPoint; // lowest point of inset
//	public int buildingWallInsettedMinHighPoint; // lowest highest point of inset
	public int rangeOfWallInset = 2; // 1 or 2 in... but not zero
	public double oddsOfForcedNarrowInteriorMode = oddsLikely;
	public double oddsOfDifferentInteriorModes = oddsUnlikely;
	
	public double oddsOfUnfinishedBuildings = oddsNeverGoingToHappen; // buildings are unfinished 1/n of the time
	public double oddsOfOnlyUnfinishedBasements = oddsNeverGoingToHappen; // unfinished buildings only have basements 1/n of the time
	public double oddsOfCranes = oddsVeryLikely; // plop a crane on top of the last horizontal girder 1/n of the time
	
	public double oddsOfBuildingWallInset = oddsNeverGoingToHappen; // building walls inset as they go up 1/n of the time
	public double oddsOfSimilarInsetBuildings = oddsNeverGoingToHappen; // the east/west inset is used for north/south inset 1/n of the time
	public double oddsOfFlatWalledBuildings = oddsNeverGoingToHappen; // the ceilings are inset like the walls 1/n of the time
	
	//TODO oddsOfMissingRoad is current not used... I need to fix this
	//public double oddsOfMissingRoad = oddsNeverGoingToHappen; // roads are missing 1/n of the time
	public double oddsOfRoundAbouts = oddsNeverGoingToHappen; // roundabouts are created 1/n of the time
	
	public double oddsOfMissingArt = oddsNeverGoingToHappen; // art is missing 1/n of the time
	public double oddsOfNaturalArt = oddsNeverGoingToHappen; // sometimes nature is art 1/n of the time 
	
	public static final int FloorHeight = 4;
	public static final int FudgeFloorsBelow = 2;
	public static final int FudgeFloorsAbove = 0;//3;
	public static final int absoluteMinimumFloorsAbove = 5; // shortest tallest building
	public static final int absoluteAbsoluteMaximumFloorsBelow = 3; // that is as many basements as I personally can tolerate
	public static final int absoluteAbsoluteMaximumFloorsAbove = 20; // that is tall enough folks
	public int buildingMaximumY;
	public int maximumFloorsAbove = 2;
	public int maximumFloorsBelow = 2;
	public int absoluteMaximumFloorsBelow;
	public int absoluteMaximumFloorsAbove; 
	
	public Material lightMat;
	public Material torchMat;
//	public Byte lightId;
//	public Byte torchId;
	
	protected ClipboardList mapsSchematics;
	public SchematicFamily schematicFamily = SchematicFamily.NATURE;
	public int schematicMaxX = 4;
	public int schematicMaxZ = 4;
	
	public DataContext(WorldGenerator generator) {
		super();
		
		buildingMaximumY = Math.min(126 + FudgeFloorsAbove * FloorHeight, generator.height);
		
		// where is the ground
		absoluteMaximumFloorsBelow = Math.max(Math.min(generator.streetLevel / FloorHeight - FudgeFloorsBelow, absoluteAbsoluteMaximumFloorsBelow), 0);
		absoluteMaximumFloorsAbove = Math.max(Math.min((buildingMaximumY - generator.streetLevel) / FloorHeight - FudgeFloorsAbove, absoluteAbsoluteMaximumFloorsAbove), absoluteMinimumFloorsAbove);
		
		// lights?
		if (generator.settings.includeWorkingLights) {
			lightMat = Material.GLOWSTONE;
			torchMat = Material.TORCH;
		} else {
			lightMat = Material.REDSTONE_LAMP_OFF;
			torchMat = Material.REDSTONE_TORCH_OFF;
		}

		// let the other guy do it
		initialize();

		// calculate the extremes for this plat
		maximumFloorsAbove = Math.min(maximumFloorsAbove, absoluteMaximumFloorsAbove);
		maximumFloorsBelow = Math.min(maximumFloorsBelow, absoluteMaximumFloorsBelow);
		
		int floorsFourth = Math.max((maximumFloorsAbove) / 4, 1);
		buildingWallInsettedMinLowPoint = floorsFourth;
		buildingWallInsettedMinMidPoint = floorsFourth * 2;
//		buildingWallInsettedMinHighPoint = floorsFourth * 3;
		
		// finally load any schematics if they exists
		mapsSchematics = generator.pasteProvider.getFamilyClips(generator, schematicFamily, schematicMaxX, schematicMaxZ);
	}
	
	protected abstract void initialize();
	public abstract void populateMap(WorldGenerator generator, PlatMap platmap);
	public abstract void validateMap(WorldGenerator generator, PlatMap platmap);
	
	protected void setSchematicMaxSize(int maxX, int maxZ) {
		schematicMaxX = maxX;
		schematicMaxZ = maxZ;
	}
	
}
