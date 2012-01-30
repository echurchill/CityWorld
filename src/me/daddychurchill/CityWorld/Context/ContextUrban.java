package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import me.daddychurchill.CityWorld.CityWorld;
import me.daddychurchill.CityWorld.PlatMaps.PlatMap;

public class ContextUrban {
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
	
	public int oddsOfPlumbingConnection = oddsExtremelyLikely;
	public int oddsOfPlumbingTreasure = oddsVeryLikely;
	public int oddsOfSewerVines = oddsUnlikely;
	public int oddsOfSewerTreasure = oddsExtremelyLikely;
	public int oddsOfSewerTrick = oddsExtremelyLikely;
	public int maxTreasureCount = 5;
	
	public int oddsOfMissingRoad = oddsLikely; // roads are missing 1/n of the time
	public int oddsOfRoundAbouts = oddsLikely; // roundabouts are created 1/n of the time
	
	public int oddsOfMoneyInFountains = oddsLikely; // gold is in the fountain 1/n of the time
	public int oddsOfMissingArt = oddsUnlikely; // art is missing 1/n of the time
	public int oddsOfLavaDownBelow = oddsUnlikely; // how often does lava show up in the underworld 1/n of the time
	public int oddsOfManholeToDownBelow = oddsExtremelyLikely; // manhole/ladder down to the lowest levels 1/n of the time
	public int oddsOfNaturalArt = oddsExtremelyLikely; // sometimes nature is art 1/n of the time 
	
	public ContextUrban(Random rand) {
		setFloorRange(rand, 2, 2);
	}
	
	public byte isolationId;
	public boolean doPlumbing;
	public boolean doSewer;
	public boolean doCistern;
	public boolean doBasement;
	public boolean doUnderworld;
	public boolean doTreasureInSewer;
	public boolean doTreasureInPlumbing;
	public boolean doSpawnerInSewer;
	public int streetLevel;
	
	public void copyGlobals(CityWorld plugin) {
		isolationId = (byte) plugin.getIsolationMaterial().getId();
		doPlumbing = plugin.isDoPlumbing();
		doSewer = plugin.isDoSewer();
		doCistern = plugin.isDoCistern();
		doBasement = plugin.isDoBasement();
		doUnderworld = plugin.isDoUnderworld();
		doTreasureInSewer = plugin.isDoTreasureInSewer();
		doTreasureInPlumbing = plugin.isDoTreasureInPlumbing();
		doSpawnerInSewer = plugin.isDoSpawnerInSewer();
		streetLevel = plugin.getStreetLevel();
	}
	
	protected void setFloorRange(Random rand, int aboveRange, int belowRange) {
		// calculate the extremes for this plat
		maximumFloorsAbove = Math.min((rand.nextInt(aboveRange) + 1) * 2, PlatMap.AbsoluteMaximumFloorsAbove);
		maximumFloorsBelow = Math.min(rand.nextInt(belowRange) + 1, PlatMap.AbsoluteMaximumFloorsBelow);
		
		int floorsFourth = Math.max((maximumFloorsAbove) / 4, 1);
		buildingWallInsettedMinLowPoint = floorsFourth;
		buildingWallInsettedMinMidPoint = floorsFourth * 2;
		buildingWallInsettedMinHighPoint = floorsFourth * 3;
	}
}
