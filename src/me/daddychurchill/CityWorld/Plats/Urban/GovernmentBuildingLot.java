package me.daddychurchill.CityWorld.Plats.Urban;

import org.bukkit.Material;
import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.FinishedBuildingLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.SupportBlocks;
import me.daddychurchill.CityWorld.Support.Surroundings;
import me.daddychurchill.CityWorld.Support.BadMagic.Stair;
import me.daddychurchill.CityWorld.Support.BadMagic.StairWell;
import me.daddychurchill.CityWorld.Support.BlackMagic;

public class GovernmentBuildingLot extends FinishedBuildingLot {

	private int columnStep;
	public GovernmentBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		depth = 0;
		height = chunkOdds.calcRandomRange(2, 4);
	}

	@Override
	protected void loadMaterials(PlatMap platmap) {

		// what is it made of?
		wallMaterial = platmap.generator.materialProvider.itemsSelectMaterial_GovernmentWalls.getRandomMaterial(chunkOdds, Material.QUARTZ_BLOCK);
		foundationMaterial = platmap.generator.materialProvider.itemsSelectMaterial_GovernmentFoundations.getRandomMaterial(chunkOdds, Material.QUARTZ_BLOCK);
		ceilingMaterial = platmap.generator.materialProvider.itemsSelectMaterial_GovernmentCeilings.getRandomMaterial(chunkOdds, Material.WOOL);
		roofMaterial = platmap.generator.materialProvider.itemsSelectMaterial_GovernmentCeilings.getRandomMaterial(chunkOdds, Material.WOOL);
		columnMaterial = platmap.generator.materialProvider.itemsSelectMaterial_GovernmentWalls.getRandomMaterial(chunkOdds, pickColumnMaterial(wallMaterial));
		foundationSteps = SupportBlocks.filterStairMaterial(foundationMaterial);
	}
	
	private Material foundationSteps;
	
	private static int higher = 2;
	private static int deeper = 3;
	
	@Override
	protected void calculateOptions(DataContext context) {
		super.calculateOptions(context);
		
		neighborsHaveIdenticalHeights = true;
		insetCeilingNS = deeper;
		insetCeilingWE = deeper;
		insetWallNS = deeper;
		insetWallWE = deeper;
		insetInsetMidAt = 1;
		insetInsetHighAt = 1;
		insetStyle = InsetStyle.STRAIGHT;
		interiorStyle = InteriorStyle.COLUMNS_ONLY;
		rounded = false;
		roofStyle = RoofStyle.PEAKS;
		roofFeature = RoofFeature.PLAIN;
		outsetEffects = false;
		columnStep = chunkOdds.calcRandomRange(2, 4);
		cornerWallStyle = CornerWallStyle.FILLED;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new GovernmentBuildingLot(platmap, chunkX, chunkZ);
	}

	@Override
	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);
		
		// other bits
		if (result && relative instanceof GovernmentBuildingLot) {
			GovernmentBuildingLot relativebuilding = (GovernmentBuildingLot) relative;

			columnStep = relativebuilding.columnStep;
			foundationMaterial = relativebuilding.foundationMaterial;
			foundationSteps = relativebuilding.foundationSteps;
		}
		return result;
	}
	
	@Override
	protected void drawExteriorParts(CityWorldGenerator generator, InitialBlocks byteChunk, DataContext context, int y1,
			int height, int insetNS, int insetWE, int floor, boolean onTopFloor, boolean inMiddleSection,
			CornerWallStyle cornerStyle, boolean allowRounded, boolean outsetEffect, Material wallMaterial,
			Material glassMaterial, Surroundings heights) {
		
		super.drawExteriorParts(generator, byteChunk, context, y1 + higher, height, insetNS, insetWE, floor, onTopFloor, inMiddleSection,
				cornerStyle, allowRounded, outsetEffect, wallMaterial, glassMaterial, heights);
		
	}
	
	@Override
	protected void drawCeilings(CityWorldGenerator generator, InitialBlocks byteChunk, DataContext context, int y1,
			int height, int insetNS, int insetWE, boolean allowRounded, boolean outsetEffect, boolean onRoof,
			Material ceilingMaterial, Surroundings heights) {

		if (onRoof)
			super.drawCeilings(generator, byteChunk, context, y1 + higher, height, insetNS - deeper + 1, insetWE - deeper + 1, allowRounded, outsetEffect, onRoof,
					ceilingMaterial, heights);
		else
			super.drawCeilings(generator, byteChunk, context, y1 + higher, height, insetNS, insetWE, allowRounded, outsetEffect, onRoof,
					ceilingMaterial, heights);
	}
	
	@Override
	protected void drawRoof(CityWorldGenerator generator, InitialBlocks chunk, DataContext context, 
			int y1, int insetNS, int insetWE, int floor, 
			boolean allowRounded, Material roofMaterial, Surroundings heights) {

		super.drawRoof(generator, chunk, context, y1 + higher, insetNS - deeper + 1, insetWE - deeper + 1, floor, allowRounded, roofMaterial, heights);
	}
	
	
	@Override
	protected void drawInteriorParts(CityWorldGenerator generator, RealBlocks blocks, DataContext context,
			RoomProvider rooms, int floor, int floorAt, int floorHeight, int insetNS, int insetWE, boolean allowRounded,
			Material materialWall, Material materialGlass, StairWell stairLocation, Material materialStair,
			Material materialStairWall, Material materialPlatform, boolean drawStairWall, boolean drawStairs,
			boolean topFloor, boolean singleFloor, Surroundings heights) {
		
		// TODO Auto-generated method stub
		super.drawInteriorParts(generator, blocks, context, rooms, floor, floorAt + higher, floorHeight, insetNS, insetWE, allowRounded,
				materialWall, materialGlass, stairLocation, materialStair, materialStairWall, materialPlatform, drawStairWall,
				drawStairs, topFloor, singleFloor, heights);
		
		if (floor == 0) {
			drawFoundationSteps(blocks, floorAt, floorAt + 2, heights);
			//drawFoundationColumns(blocks, floorAt + 1, 1, heights);
		} 
//		drawFoundationColumns(blocks, floorAt + higher - 1, DataContext.FloorHeight, heights);
	};
	
	private void drawFoundationSteps(SupportBlocks blocks, int y1, int y2, Surroundings heights) {
		blocks.setBlocks(3, 13, y1, y2, 3, 13, foundationMaterial);
		
		if (heights.toNorth()) {
			blocks.setBlocks(3, 13, y1, y2, 0, 3, foundationMaterial);
		} else {
			blocks.setStairs(3, 13, y1, 1, 2, foundationSteps, Stair.SOUTH);
			blocks.setBlocks(3, 13, y1, 2, 3, foundationMaterial);
			blocks.setStairs(3, 13, y1 + 1, 2, 3, foundationSteps, Stair.SOUTH);
		}
		
		if (heights.toSouth()) {
			blocks.setBlocks(3, 13, y1, y2, 13, 16, foundationMaterial);
		} else {
			blocks.setStairs(3, 13, y1, 14, 15, foundationSteps, Stair.NORTH);
			blocks.setBlocks(3, 13, y1, 13, 14, foundationMaterial);
			blocks.setStairs(3, 13, y1 + 1, 13, 14, foundationSteps, Stair.NORTH);
		}
		
		if (heights.toWest()) {
			blocks.setBlocks(0, 3, y1, y2, 3, 13, foundationMaterial);
		} else {
			blocks.setStairs(1, 2, y1, 3, 13, foundationSteps, Stair.EAST);
			blocks.setBlocks(2, 3, y1, 3, 13, foundationMaterial);
			blocks.setStairs(2, 3, y1 + 1, 3, 13, foundationSteps, Stair.EAST);
		}
		
		if (heights.toEast()) {
			blocks.setBlocks(13, 16, y1, y2, 3, 13, foundationMaterial);
		} else {
			blocks.setStairs(14, 15, y1, 3, 13, foundationSteps, Stair.WEST);
			blocks.setBlocks(13, 14, y1, 3, 13, foundationMaterial);
			blocks.setStairs(13, 14, y1 + 1, 3, 13, foundationSteps, Stair.WEST);
		}
		
		if (heights.toNorth() && !heights.toWest()) {
			drawFoundationHeadingEastBit(blocks, 0, y1, 0);
		} else if (heights.toWest() && !heights.toNorth()) {
			drawFoundationHeadingSouthBit(blocks, 0, y1, 0);
		} else if (heights.toNorth() && heights.toWest()) {
//			if (heights.toNorthWest()) {
				blocks.setBlocks(0, 3, y1, y2, 0, 3, foundationMaterial);
//			} else {
//				// small bit leaning south east
//			}
		}
		
		if (heights.toNorth() && !heights.toEast()) {
			drawFoundationHeadingWestBit(blocks, 13, y1, 0);
		} else if (heights.toEast() && !heights.toNorth()) {
			drawFoundationHeadingSouthBit(blocks, 13, y1, 0);
		} else if (heights.toNorth() && heights.toEast()) {
//			if (heights.toNorthEast()) {
				blocks.setBlocks(13, 16, y1, y2, 0, 3, foundationMaterial);
//			} else {
//				// small bit leaning south west
//			}
		}
		
		if (heights.toSouth() && !heights.toWest()) {
			drawFoundationHeadingEastBit(blocks, 0, y1, 13);
		} else if (heights.toWest() && !heights.toSouth()) {
			drawFoundationHeadingNorthBit(blocks, 0, y1, 13);
		} else if (heights.toSouth() && heights.toWest()) {
//			if (heights.toSouthWest()) {
				blocks.setBlocks(0, 3, y1, y2, 13, 16, foundationMaterial);
//			} else {
//				// small bit leaning south east
//			}
		}
		
		if (heights.toSouth() && !heights.toEast()) {
			drawFoundationHeadingWestBit(blocks, 13, y1, 13);
		} else if (heights.toEast() && !heights.toSouth()) {
			drawFoundationHeadingNorthBit(blocks, 13, y1, 13);
		} else if (heights.toSouth() && heights.toEast()) {
//			if (heights.toSouthEast()) {
				blocks.setBlocks(13, 16, y1, y2, 13, 16, foundationMaterial);
//			} else {
//				// small bit leaning south west
//			}
		}
	}
	
	private void drawFoundationHeadingNorthBit(SupportBlocks blocks, int x, int y, int z) {
		blocks.setStairs(x, x + 3, y    , z + 1, z + 2, foundationSteps, Stair.NORTH);
		blocks.setBlocks(x, x + 3, y    , z    , z + 1, foundationMaterial);
		blocks.setStairs(x, x + 3, y + 1, z    , z + 1, foundationSteps, Stair.NORTH);
	}
	
	private void drawFoundationHeadingSouthBit(SupportBlocks blocks, int x, int y, int z) {
		blocks.setStairs(x, x + 3, y    , z + 1, z + 2, foundationSteps, Stair.SOUTH);
		blocks.setBlocks(x, x + 3, y    , z + 2, z + 3, foundationMaterial);
		blocks.setStairs(x, x + 3, y + 1, z + 2, z + 3, foundationSteps, Stair.SOUTH);
	}
	
	private void drawFoundationHeadingWestBit(SupportBlocks blocks, int x, int y, int z) {
		blocks.setStairs(x + 1, x + 2, y    , z, z + 3, foundationSteps, Stair.WEST);
		blocks.setBlocks(x    , x + 1, y    , z, z + 3, foundationMaterial);
		blocks.setStairs(x    , x + 1, y + 1, z, z + 3, foundationSteps, Stair.WEST);
	}
	
	private void drawFoundationHeadingEastBit(SupportBlocks blocks, int x, int y, int z) {
		blocks.setStairs(x + 1, x + 2, y    , z, z + 3, foundationSteps, Stair.EAST);
		blocks.setBlocks(x + 2, x + 3, y    , z, z + 3, foundationMaterial);
		blocks.setStairs(x + 2, x + 3, y + 1, z, z + 3, foundationSteps, Stair.EAST);
	}
	
//	private void drawFoundationColumns(SupportBlocks blocks, int y1, int height, Surroundings heights) {
//		int x1 = heights.toWest() ? 0 : 1;
//		int x2 = heights.toEast() ? 16 : 16 - 1;
//		int z1 = heights.toNorth() ? 0 : 1;
//		int z2 = heights.toSouth() ? 16 : 16 - 1;
//		int y2 = y1 + height;
//		
//		int step = 0;
//		for (int x = x1; x < x2; x++) {
//			if (step % columnStep == 0) {
//				if (!heights.toNorth())
//					drawColumn(blocks, x, y1, y2, 1);
//				if (!heights.toSouth())
//					drawColumn(blocks, 15 - x, y1, y2, 14);
//			}
//			step++;
//		}
//
//		step = 0;
//		for (int z = z1; z < z2; z++) {
//			if (step % columnStep == 0) {
//				if (!heights.toWest())
//					drawColumn(blocks, 1, y1, y2, z);
//				if (!heights.toEast())
//					drawColumn(blocks, 14, y1, y2, 15 - z);
//			}
//			step++;
//		}
//	}
//	
//	private void drawColumn(SupportBlocks blocks, int x, int y1, int y2, int z) {
//		switch (columnMaterial) {
//		case QUARTZ_BLOCK:
//			BlackMagic.setBlocks(blocks, x, y1, y2, z, Material.QUARTZ_BLOCK, 2);
//			break;
//		default:
//			blocks.setBlocks(x, y1, y2, z, columnMaterial);
//			break;
//		}
//	}
	
	@Override
	protected void drawRoof(CityWorldGenerator generator, RealBlocks chunk, DataContext context, int y1, int insetNS,
			int insetWE, int floor, boolean allowRounded, boolean outsetEffect, Material material, Surroundings heights,
			RoofStyle thisStyle, RoofFeature thisFeature) {
		super.drawRoof(generator, chunk, context, y1 + higher, insetNS - 2, insetWE - 2, floor, allowRounded, outsetEffect, Material.LAPIS_BLOCK, heights,
				thisStyle, thisFeature);
	}
	
}
