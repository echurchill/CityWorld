package me.daddychurchill.CityWorld.Context.Maze;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Clipboard.PasteProvider.SchematicFamily;
import me.daddychurchill.CityWorld.Context.NatureContext;
import me.daddychurchill.CityWorld.Plats.NatureLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Maze.MazeCoveredLot;
import me.daddychurchill.CityWorld.Plats.Maze.MazeNatureLot;
import me.daddychurchill.CityWorld.Plats.Nature.MountainShackLot;
import me.daddychurchill.CityWorld.Plats.Nature.MountainTentLot;
import me.daddychurchill.CityWorld.Plats.Nature.OldCastleLot;
import me.daddychurchill.CityWorld.Plats.Rural.FarmLot;
import me.daddychurchill.CityWorld.Plats.Rural.HouseLot;
import me.daddychurchill.CityWorld.Plats.Urban.ConcreteLot;
import me.daddychurchill.CityWorld.Plats.Urban.LibraryBuildingLot;
import me.daddychurchill.CityWorld.Plats.Urban.OfficeBuildingLot;
import me.daddychurchill.CityWorld.Plats.Urban.ParkLot;
import me.daddychurchill.CityWorld.Plats.Urban.RoundaboutCenterLot;
import me.daddychurchill.CityWorld.Plats.Urban.StorageLot;
import me.daddychurchill.CityWorld.Plats.Urban.StoreBuildingLot;
import me.daddychurchill.CityWorld.Plats.Urban.UnfinishedBuildingLot;
import me.daddychurchill.CityWorld.Plats.Urban.WarehouseBuildingLot;
import me.daddychurchill.CityWorld.Support.HeightInfo;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class MazeNatureContext extends NatureContext {

	public MazeNatureContext(WorldGenerator generator) {
		super(generator);

		oddsOfParks = Odds.oddsUnlikely;
		oddsOfIsolatedLots = Odds.oddsLikely;
		oddsOfIdenticalBuildingHeights = Odds.oddsExtremelyLikely;
		oddsOfSimilarBuildingHeights = Odds.oddsExtremelyLikely;
		oddsOfSimilarBuildingRounding = Odds.oddsExtremelyLikely;
		oddsOfUnfinishedBuildings = Odds.oddsPrettyUnlikely;
		oddsOfOnlyUnfinishedBasements = Odds.oddsVeryUnlikely;
		
		oddsOfArt = Odds.oddsExceedinglyLikely;
		oddsOfNaturalArt = Odds.oddsUnlikely;
		 
		oddsOfStairWallMaterialIsWallMaterial = Odds.oddsExtremelyLikely;
		oddsOfBuildingWallInset = Odds.oddsExtremelyLikely;
		oddsOfFlatWalledBuildings = Odds.oddsExtremelyLikely;
		oddsOfSimilarInsetBuildings = Odds.oddsExtremelyLikely;
		rangeOfWallInset = 2;
		
		schematicFamily = SchematicFamily.MIDRISE;
		
		maximumFloorsAbove = absoluteMaximumFloorsAbove / 3;
		maximumFloorsBelow = 2;
	}
	
	private enum whatToBuild {
		FARM, PARK, HOUSE, TOWN, CITY, UNFINISHED, STORAGE,
		STATUE, FOREST, CAMPGROUND, CASTLE, BOX};
		
	private whatToBuild pickRandomWhat(Odds odds) {
		return whatToBuild.values()[odds.getRandomInt(whatToBuild.values().length)];
	}
	
	private int openingWidth = 4;

	private double getSpecialOdds(Odds odds, int x, int z, boolean specialMade) {
		if (specialMade)
			return Odds.oddsNeverGoingToHappen;
		else if (x == openingWidth - 1 && z == openingWidth - 1)
			return Odds.oddsAlwaysGoingToHappen;
		else {
			double theOdds = Odds.oddsSomewhatLikely;
			if (x == 0 || x == openingWidth - 1)
				theOdds = theOdds / 2;
			if (z == 0 || z == openingWidth - 1)
				theOdds = theOdds / 2;
			return theOdds;
		}
	}
	
	private boolean placeSpecial(Odds odds, int x, int z, boolean specialMade) {
		double theOdds = getSpecialOdds(odds, x, z, specialMade);
		return odds.playOdds(theOdds);
	}
	
	private double getNatureOdds(Odds odds, int x, int z) {
		double theOdds = Odds.oddsPrettyUnlikely;
		if (x == 0 || x == openingWidth - 1)
			theOdds = theOdds * 3;
		if (z == 0 || z == openingWidth - 1)
			theOdds = theOdds * 3;
		return theOdds;
	}
	
	private boolean placeNature(Odds odds, int x, int z) {
		double theOdds = getNatureOdds(odds, x, z);
		return odds.playOdds(theOdds);
	}
	
	private static int[] xS = {1, 2, 2, 1,  1, 2, 3, 3, 1, 2, 0, 0,  0, 3, 3, 0};
	private static int[] zS = {1, 1, 2, 2,  0, 0, 1, 2, 3, 3, 1, 2,  0, 0, 3, 3};
	
	@Override
	public void populateMap(WorldGenerator generator, PlatMap platmap) {
		
		// random stuff?
		Odds platmapOdds = platmap.getOddsGenerator();
		boolean specialMade = false;
		
		// where it all begins
		int originX = platmap.originX;
		int originZ = platmap.originZ;
		int offsetX = platmapOdds.getRandomInt(1, 5);
		int offsetZ = platmapOdds.getRandomInt(1, 5);
		HeightInfo heights;
		PlatLot lastOne = null;
		
		// what to build?
		whatToBuild what = pickRandomWhat(platmapOdds);
		for (int i = 0; i < xS.length; i++) {
			int x = xS[i];
			int z = zS[i];
			PlatLot current = platmap.getLot(offsetX + x, offsetZ + z);
			if (current == null) {
				
				// what is the world location of the lot?
				int chunkX = originX + offsetX + x;
				int chunkZ = originZ + offsetZ + z;
				int blockX = chunkX * SupportChunk.chunksBlockWidth;
				int blockZ = chunkZ * SupportChunk.chunksBlockWidth;
				
				// get the height info for this chunk
				heights = HeightInfo.getHeightsFaster(generator, blockX, blockZ);
				if (heights.isBuildable()) {
					switch (what) {
					case FARM: // farm
						if (placeSpecial(platmapOdds, x, z, specialMade)) {
							specialMade = true;
							current = new HouseLot(platmap, chunkX, chunkZ);
						} else
							current = new FarmLot(platmap, chunkX, chunkZ);
						break;
					case PARK: // park
						current = new ParkLot(platmap, chunkX, chunkZ, 100);
						if (lastOne != null)
							current.makeConnected(lastOne);
						break;
					case HOUSE:
						if (placeSpecial(platmapOdds, x, z, specialMade)) {
							specialMade = true;
							current = new MountainShackLot(platmap, chunkX, chunkZ);
						} else
							current = new NatureLot(platmap, chunkX, chunkZ);
						break;
					case TOWN:
						if (placeNature(platmapOdds, x, z))
							current = new NatureLot(platmap, chunkX, chunkZ);
						else
							switch (platmapOdds.getRandomInt(4)) { // one more than needed to force extra houses
							case 0:
								current = new StoreBuildingLot(platmap, chunkX, chunkZ);
								break;
							case 1:
								current = new ParkLot(platmap, chunkX, chunkZ, 100);
								break;
							default:
								current = new HouseLot(platmap, chunkX, chunkZ);
								break;
							}
						break;
					case CITY:
						if (placeNature(platmapOdds, x, z))
							current = new ConcreteLot(platmap, chunkX, chunkZ);
						else
							switch (platmapOdds.getRandomInt(5)) {
							case 0:
								current = new LibraryBuildingLot(platmap, chunkX, chunkZ);
								break;
							case 1:
								current = new StoreBuildingLot(platmap, chunkX, chunkZ);
								break;
							case 2:
								current = new WarehouseBuildingLot(platmap, chunkX, chunkZ);
								break;
							case 3:
								current = new OfficeBuildingLot(platmap, chunkX, chunkZ);
								break;
							default:
								current = new ParkLot(platmap, chunkX, chunkZ, 100);
								break;
							}
						break;
					case UNFINISHED:
						if (placeNature(platmapOdds, x, z))
							current = new ConcreteLot(platmap, chunkX, chunkZ);
						else
							current = new UnfinishedBuildingLot(platmap, chunkX, chunkZ);
						break;
					case STORAGE: // storage lot
						if (placeSpecial(platmapOdds, x, z, specialMade)) {
							specialMade = true;
							current = new StorageLot(platmap, chunkX, chunkZ);
						} else
							current = new ConcreteLot(platmap, chunkX, chunkZ);
						break;
					case STATUE:
						if (placeSpecial(platmapOdds, x, z, specialMade)) {
							specialMade = true;
							current = new RoundaboutCenterLot(platmap, chunkX, chunkZ);
						} else
							current = new ConcreteLot(platmap, chunkX, chunkZ);
						break;
					case FOREST:
						if (placeSpecial(platmapOdds, x, z, specialMade)) {
							specialMade = true;
							current = new RoundaboutCenterLot(platmap, chunkX, chunkZ);
						} else
							current = new NatureLot(platmap, chunkX, chunkZ);
						break;
					case CAMPGROUND:
						if (placeSpecial(platmapOdds, x, z, specialMade)) {
							specialMade = true;
							current = new MountainTentLot(platmap, chunkX, chunkZ);
						} else if (platmapOdds.playOdds(Odds.oddsSomewhatUnlikely))
							current = new MountainTentLot(platmap, chunkX, chunkZ);
						else
							current = new NatureLot(platmap, chunkX, chunkZ);
						break;
					case CASTLE:
						if (placeSpecial(platmapOdds, x, z, specialMade)) {
							specialMade = true;
							current = new OldCastleLot(platmap, chunkX, chunkZ);
						} else
							current = new NatureLot(platmap, chunkX, chunkZ);
						break;
					case BOX:
						current = new MazeCoveredLot(platmap, chunkX, chunkZ);
						break;
					}
					
					// put it somewhere then
					if (current != null) {
						
						// maybe?
						if (z != 0 && lastOne != null && lastOne.isConnectable(current))
							current.makeConnected(lastOne);
						
						// place it
						platmap.setLot(offsetX + x, offsetZ + z, current);
						lastOne = current;
					}
				}
			}
		}
		
		// pass on the effort
		super.populateMap(generator, platmap);
	}

	@Override
	public PlatLot createNaturalLot(WorldGenerator generator, PlatMap platmap, int x, int z) {
		return new MazeNatureLot(platmap, platmap.originX + x, platmap.originZ + z);
	}
}
