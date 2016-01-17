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
import me.daddychurchill.CityWorld.Support.BadMagic.StairWell;

public class MuseumBuildingLot extends FinishedBuildingLot {

	public MuseumBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		firstFloorHeight = firstFloorHeight * 5;
		height = 1;
		depth = 0;
		roofFeature = roofFeature == RoofFeature.ANTENNAS ? RoofFeature.CONDITIONERS : roofFeature;
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
		
		// how do the walls inset?
		insetWallWE = 1;
		insetWallNS = 1;
		
		// what about the ceiling?
		insetCeilingWE = insetWallWE;
		insetCeilingNS = insetWallNS;
		
		// nudge in a bit more as we go up
		insetInsetMidAt = 1;
		insetInsetHighAt = 1;
		insetInsetted = false;
	}

	@Override
	protected InteriorStyle pickInteriorStyle() {
		return InteriorStyle.EMPTY;
	}

	@Override
	protected RoofStyle pickRoofStyle() {
		return RoofStyle.FLATTOP;
	}
	
	@Override
	protected void drawInteriorParts(CityWorldGenerator generator, RealBlocks chunk, DataContext context,
			RoomProvider rooms, int floor, int floorAt, int floorHeight, int insetNS, int insetWE, boolean allowRounded,
			Material materialWall, Material materialGlass, StairWell stairLocation, Material materialStair,
			Material materialStairWall, Material materialPlatform, boolean drawStairWall, boolean drawStairs,
			boolean topFloor, boolean singleFloor, Surroundings heights) {
		
		if (singleFloor && generator.settings.includeBones) {
			
			// calculate if we should do it
			boolean placeBones = false;
			if (allowRounded) {

				// do the sides
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
				generator.bonesProvider.generateBones(generator, this, chunk, 7, sidewalkLevel + 1, 11, chunkOdds, true);
			}
		}
	}
	
}
