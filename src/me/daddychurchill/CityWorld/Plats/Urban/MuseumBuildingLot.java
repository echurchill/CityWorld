package me.daddychurchill.CityWorld.Plats.Urban;

import org.bukkit.Material;
import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.FinishedBuildingLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.Surroundings;
import me.daddychurchill.CityWorld.Support.BadMagic.General;
import me.daddychurchill.CityWorld.Support.BadMagic.StairWell;
import me.daddychurchill.CityWorld.Support.BadMagic.Torch;

public class MuseumBuildingLot extends FinishedBuildingLot {

	public MuseumBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		firstFloorHeight = firstFloorHeight * 5;
		height = 1;
		depth = 0;
		rounded = false;
		roofFeature = roofFeature == RoofFeature.ANTENNAS ? RoofFeature.CONDITIONERS : roofFeature;
		interiorStyle = InteriorStyle.EMPTY;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new MuseumBuildingLot(platmap, chunkX, chunkZ);
	}

	@Override
	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);
		
//		// other bits
//		if (result && relative instanceof WarehouseBuildingLot) {
//			MuseumBuildingLot relativebuilding = (MuseumBuildingLot) relative;
//
//			// any other bits
//			contentStyle = relativebuilding.contentStyle;
//		}
		
		return result;
	}

	@Override
	protected void calculateOptions(DataContext context) {
		super.calculateOptions(context);
		
		// how do the walls inset?
		insetWallWE = 1;
		insetWallNS = 1;
		
		// what about the ceiling?
		insetCeilingWE = insetWallWE;
		insetCeilingNS = insetWallNS;
		
		// nudge in a bit more as we go up
		insetInsetMidAt = 1;
		insetInsetHighAt = 1;
		insetStyle = InsetStyle.STRAIGHT;
	}

	@Override
	protected void drawInteriorParts(CityWorldGenerator generator, RealBlocks chunk, DataContext context,
			RoomProvider rooms, int floor, int floorAt, int floorHeight, int insetNS, int insetWE, boolean allowRounded,
			Material materialWall, Material materialGlass, StairWell stairLocation, Material materialStair,
			Material materialStairWall, Material materialPlatform, boolean drawStairWall, boolean drawStairs,
			boolean topFloor, boolean singleFloor, Surroundings heights) {
		
		
		// outside 
		drawExteriorDoors(generator, chunk, context, 
				floor, floorAt, floorHeight, insetNS, insetWE, 
				allowRounded, materialWall, materialGlass, 
				stairLocation, heights);
		
		if (singleFloor && generator.settings.includeBones) {
			
			// calculate if we should do it
			boolean placeBones = false;
			if (allowRounded) {

				// do the sides (yea this could be done tighter but it doesn't get called much)
				if (heights.toSouth()) {
					if (heights.toWest()) {
						placeBones = false;
					} else if (heights.toEast()) {
						placeBones = false;
					}
				} else if (heights.toNorth()) {
					if (heights.toWest()) {
						placeBones = false;
					} else if (heights.toEast()) {
						placeBones = false;
					}
				}
			} else
				placeBones = true;
			
			// ok... then do it
			if (placeBones) {
				int sidewalkLevel = getSidewalkLevel(generator);
				chunk.setBlocksTypeAndColor(3, 13, sidewalkLevel + 1, 3, 13, Material.CARPET, chunkOdds.getRandomColor());
				generator.thingProvider.generateBones(generator, this, chunk, 7, sidewalkLevel + 1, 11, chunkOdds, true);
				
				// it looked so nice for a moment... but the moment has passed
				if (generator.settings.includeDecayedBuildings) {
					destroyLot(generator, sidewalkLevel, sidewalkLevel + firstFloorHeight);
					
				} else {
					chunk.setBlocks(7, sidewalkLevel + 1, sidewalkLevel + 3, 4, Material.STONE);
					chunk.setWallSign(7, sidewalkLevel + 2, 3, General.NORTH, generator.odonymProvider.generateFossilOdonym(generator, chunkOdds));
					chunk.setTorch(7, sidewalkLevel + 2, 5, Material.TORCH, Torch.SOUTH);
				}
			}
		}
	}
	
}
