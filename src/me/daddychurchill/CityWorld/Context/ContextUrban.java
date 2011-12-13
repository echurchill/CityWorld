package me.daddychurchill.CityWorld.Context;

public class ContextUrban {
	//TODO need to clean up and generalize odds management of PlatMapUrban, PlatMapCity, PlatBuilding and PlatOfficeBuilding (and the like)
	// We want to do this so we can have alternatives to PlatMapCity like PlatMapTown, PlatMapMidRise, PlatMapHighRise
	
	public int overallParkOdds = 5; //5; // parks show up 1/n of the time
	public int overallIsolatedBuildingOdds = 3; // isolated buildings 1/n of the time
	public int overallIdenticalHeightsOdds = 2; // similar height 1/n of the time
	public int overallSimilarHeightsOdds = 2; // identical height 1/n of the time
	public int overallSimilarRoundedOdds = 2; // like rounding 1/n of the time
	public int onlyUnfinishedBuildingsOdds = 10; // buildings are unfinished 1/n of the time
	public int onlyUnfinishedBasementsOdds = 4; // unfinished buildings only have basements 1/n of the time
	public int oddsOfMissingRoad = 2; // roads are missing 1/n of the time
	public int oddsOfRoundAbouts = 4; // roundabouts are created 1/n of the time
	 
	public int stairWallMaterialIsWallMaterialOdds = 2; // stair walls are the same as walls 1/n of the time
	public int buildingWallInsettedOdds = 2; // building walls inset as they go up 1/n of the time
	
	public int floorsMaximumAbove;
	public int floorsMaximumBelow;
	public int buildingWallInsettedMinHeight = 8; // minimum building height before insetting is allowed
	public int buildingWallInsettedMinMidPoint = 2; // lowest point of inset
	public int buildingWallInsettedMinHighPoint = buildingWallInsettedMinHeight; // lowest highest point of inset
}
