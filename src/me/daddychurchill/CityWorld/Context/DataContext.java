package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Clipboard.Clipboard;
import me.daddychurchill.CityWorld.Clipboard.ClipboardList;
import me.daddychurchill.CityWorld.Clipboard.PasteProvider.SchematicFamily;
import me.daddychurchill.CityWorld.Maps.PlatMap;

public abstract class DataContext {
	public static int oddsNeverGoingToHappen = Integer.MAX_VALUE;
	public static int oddsExtremelyUnlikely = 80;
	public static int oddsVeryUnlikely = 40;
	public static int oddsUnlikely = 20;
	public static int oddsLikely = 10;
	public static int oddsVeryLikely = 5;
	public static int oddsExtremelyLikely = 3;
	public static int oddsAlwaysGoingToHappen = 1;
	
	public int oddsOfIsolatedLots = oddsExtremelyLikely; // isolated buildings 1/n of the time
	
	public int oddsOfParks = oddsVeryLikely; // parks show up 1/n of the time
	
	public int maximumFloorsAbove;
	public int maximumFloorsBelow;
	public int oddsOfIdenticalBuildingHeights = oddsExtremelyLikely; // similar height 1/n of the time
	public int oddsOfSimilarBuildingHeights = oddsExtremelyLikely; // identical height 1/n of the time
	public int oddsOfSimilarBuildingRounding = oddsExtremelyLikely; // like rounding 1/n of the time
	public int oddsOfStairWallMaterialIsWallMaterial = oddsExtremelyLikely; // stair walls are the same as walls 1/n of the time
	public int buildingWallInsettedMinLowPoint; // minimum building height before insetting is allowed
	public int buildingWallInsettedMinMidPoint; // lowest point of inset
	public int buildingWallInsettedMinHighPoint; // lowest highest point of inset
	
	public int oddsOfUnfinishedBuildings = oddsLikely; // buildings are unfinished 1/n of the time
	public int oddsOfOnlyUnfinishedBasements = oddsVeryLikely; // unfinished buildings only have basements 1/n of the time
	public int oddsOfCranes = oddsVeryLikely; // plop a crane on top of the last horizontal girder 1/n of the time
	
	public int oddsOfBuildingWallInset = oddsExtremelyLikely; // building walls inset as they go up 1/n of the time
	public int oddsOfSimilarInsetBuildings = oddsExtremelyLikely; // the east/west inset is used for north/south inset 1/n of the time
	public int rangeOfWallInset = 2; // 1 or 2 in... but not zero
	public int oddsOfFlatWalledBuildings = oddsExtremelyLikely; // the ceilings are inset like the walls 1/n of the time
	
	public int oddsOfMissingRoad = oddsLikely; // roads are missing 1/n of the time
	public int oddsOfRoundAbouts = oddsLikely; // roundabouts are created 1/n of the time
	
	public int oddsOfMissingArt = oddsUnlikely; // art is missing 1/n of the time
	public int oddsOfNaturalArt = oddsExtremelyLikely; // sometimes nature is art 1/n of the time 
	
	public static final int FloorHeight = 4;
	public static final int FudgeFloorsBelow = 2;
	public static final int FudgeFloorsAbove = 0;//3;
	public static final int absoluteMinimumFloorsAbove = 5; // shortest tallest building
	public static final int absoluteAbsoluteMaximumFloorsBelow = 3; // that is as many basements as I personally can tolerate
	public static final int absoluteAbsoluteMaximumFloorsAbove = 20; // that is tall enough folks
	public int buildingMaximumY;
	public int absoluteMaximumFloorsBelow;
	public int absoluteMaximumFloorsAbove; 
	
	public Material lightMat;
	public Byte lightId;
	public Material torchMat;
	public Byte torchId;
	
	public SchematicFamily schematicFamily = SchematicFamily.NATURE;
	
	public DataContext(WorldGenerator generator, PlatMap platmap) {
		super();
		Random platmapRandom = platmap.getRandomGenerator();
		
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
		lightId = (byte) lightMat.getId();
		torchId = (byte) torchMat.getId();

		// default floor range
		setFloorRange(platmapRandom, 2, 2);
	}
	
	protected void setFloorRange(Random random, int aboveRange, int belowRange) {
		// calculate the extremes for this plat
		maximumFloorsAbove = Math.min((random.nextInt(aboveRange) + 1) * 2, absoluteMaximumFloorsAbove);
		maximumFloorsBelow = Math.min(random.nextInt(belowRange) + 1, absoluteMaximumFloorsBelow);
		
		int floorsFourth = Math.max((maximumFloorsAbove) / 4, 1);
		buildingWallInsettedMinLowPoint = floorsFourth;
		buildingWallInsettedMinMidPoint = floorsFourth * 2;
		buildingWallInsettedMinHighPoint = floorsFourth * 3;
	}
	
	public abstract void populateMap(WorldGenerator generator, PlatMap platmap);
	
	protected void populateWithSchematics(WorldGenerator generator, PlatMap platmap) {

		// grab platmap's random
		Random random = platmap.getRandomGenerator();
		
		// for each schematic
		ClipboardList clips = generator.pasteProvider.getFamilyClips(generator, schematicFamily);
		for (Clipboard clip: clips) {

			// that succeeds the OddsOfAppearance
			if (random.nextDouble() < clip.oddsOfAppearance) {
				platmap.placeSpecificClip(generator, random, clip);
			}
		}
	}

	public void pasteFamilyClips(WorldGenerator generator, PlatMap platmap, SchematicFamily family) {
		
	}
	
	
}
