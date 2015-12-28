package me.daddychurchill.CityWorld.Context.Maze;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.HeightInfo;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

public abstract class MazeConstructContext extends MazeNatureContext {

	public MazeConstructContext(CityWorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

//	private enum whatToBuild {
//		FARM, PARK, HOUSE, TOWN, CITY, UNFINISHED, STORAGE,
//		STATUE, FOREST, CAMPGROUND, CASTLE, BOX, THEPIT, // TREEHOUSE, PYRAMID, 
//		
//		// 1.8 ones from here on, if you add more remember to update the numberWithNewStuff const
//		LAVA, WATER, MYSTERY};
//	private int numberWithNewStuff = 3;
//		
//	private whatToBuild pickRandomWhat(CityWorldGenerator generator, Odds odds) {
//		if (generator.minecraftVer > 1.7) {
//			return whatToBuild.values()[odds.getRandomInt(whatToBuild.values().length)];
//		} else {
//			return whatToBuild.values()[odds.getRandomInt(whatToBuild.values().length - numberWithNewStuff)];
//		}
//	}
	
	private int openingWidth = 6;

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
	
	protected abstract PlatLot generateSpecialOneLot(PlatMap platmap, Odds odds, int chunkX, int chunkZ);
	protected abstract PlatLot generateNormalLot(PlatMap platmap, Odds odds, int chunkX, int chunkZ);
	
	protected PlatLot generateSpecialTooLot(PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return generateSpecialOneLot(platmap, odds, chunkX, chunkZ);
	}
	
	// I am pretty sure there is a GREAT reason to do this in this specific order but 
	//  for the life of me I can't remember why I do it this way. This just goes to show
	//  you that you should comment the heck out of your code, especially the "clever" bits
	// Ok, I just remembered what this is about. By starting in the middle and circling out
	//  there much better chance the special lot will be in the middle
//	private static int[] xS = {1, 2, 2, 1,  1, 2, 3, 3, 1, 2, 0, 0,  0, 3, 3, 0};
//	private static int[] zS = {1, 1, 2, 2,  0, 0, 1, 2, 3, 3, 1, 2,  0, 0, 3, 3};
	
	private static int[] xS = {2, 3, 3, 2,
							   2, 3, 4, 4, 3, 2, 1, 1, 1, 4,  4, 1,
							   1, 2, 3, 4, 5, 5, 5, 5, 4, 3,  2, 1, 0, 0, 0, 0, 0, 5, 5, 0};
	private static int[] zS = {2, 2, 3, 3,
							   1, 1, 2, 3, 4, 4, 3, 2, 1, 1,  4, 4, 
							   0, 0, 0, 0, 1, 2, 3, 4, 5, 5,  5, 5, 4, 3, 2, 1, 0, 0, 5, 5};
	
	@Override
	public void populateMap(CityWorldGenerator generator, PlatMap platmap) {
		
		// random stuff?
		Odds platmapOdds = platmap.getOddsGenerator();
//		whatToBuild what = pickRandomWhat(generator, platmapOdds);
//		int waterDepth = ParkLot.getWaterDepth(platmapOdds);
		
		// where it all begins
		int originX = platmap.originX;
		int originZ = platmap.originZ;
		int offsetX = platmapOdds.getRandomInt(1, 3);
		int offsetZ = platmapOdds.getRandomInt(1, 3);
		HeightInfo heights;
		boolean specialOneMade = false;
		boolean specialTooMade = false;
		PlatLot lastOne = null;
		
		// what to build?
		for (int i = 0; i < xS.length; i++) {
			int x = xS[i];
			int z = zS[i];
			PlatLot current = platmap.getLot(offsetX + x, offsetZ + z);
			if (current == null) {
				
				// what is the world location of the lot?
				int chunkX = originX + offsetX + x;
				int chunkZ = originZ + offsetZ + z;
				int blockX = chunkX * SupportBlocks.sectionBlockWidth;
				int blockZ = chunkZ * SupportBlocks.sectionBlockWidth;
				
				// get the height info for this chunk
				heights = HeightInfo.getHeightsFaster(generator, blockX, blockZ);
				if (heights.isBuildable()) {
					if (placeSpecial(platmapOdds, x, z, specialOneMade)) {
						current = generateSpecialOneLot(platmap, platmapOdds, chunkX, chunkZ);
						specialOneMade = true;
					} else if (placeSpecial(platmapOdds, x, z, specialTooMade)) {
						current = generateSpecialTooLot(platmap, platmapOdds, chunkX, chunkZ);
						specialTooMade = true;
					} else
						current = generateNormalLot(platmap, platmapOdds, chunkX, chunkZ);

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
		
		//TODO surround the thing we just created with partial walls
		
		// pass on the effort
		super.populateMap(generator, platmap);
	}
}
