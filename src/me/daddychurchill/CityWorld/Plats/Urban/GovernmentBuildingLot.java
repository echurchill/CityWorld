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
//		roofStyle = RoofStyle.PEAKS;
		roofFeature = RoofFeature.PLAIN;
		outsetEffects = false;
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

			foundationMaterial = relativebuilding.foundationMaterial;
			foundationSteps = relativebuilding.foundationSteps;
		}
		return result;
	}
	
	private boolean doBuilding(Surroundings heights) {
//		return false;
		return heights.adjacentNeighbors();
	}
	
	@Override
	protected void drawExteriorParts(CityWorldGenerator generator, InitialBlocks byteChunk, DataContext context, int y1,
			int height, int insetNS, int insetWE, int floor, boolean onTopFloor, boolean inMiddleSection,
			CornerWallStyle cornerStyle, boolean allowRounded, boolean outsetEffect, Material wallMaterial,
			Material glassMaterial, Surroundings heights) {
		
		if (doBuilding(heights)) {
			super.drawExteriorParts(generator, byteChunk, context, y1 + higher, height, insetNS, insetWE, floor, onTopFloor, inMiddleSection,
					cornerStyle, allowRounded, outsetEffect, wallMaterial, glassMaterial, heights);
		}
	}
	
	@Override
	protected void drawCeilings(CityWorldGenerator generator, InitialBlocks byteChunk, DataContext context, int y1,
			int height, int insetNS, int insetWE, boolean allowRounded, boolean outsetEffect, boolean onRoof,
			Material ceilingMaterial, Surroundings heights) {

		if (doBuilding(heights)) {
			if (onRoof)
				super.drawCeilings(generator, byteChunk, context, y1 + higher, height, insetNS - deeper, insetWE - deeper, allowRounded, outsetEffect, onRoof,
						ceilingMaterial, heights);
			else
				super.drawCeilings(generator, byteChunk, context, y1 + higher, height, insetNS, insetWE, allowRounded, outsetEffect, onRoof,
						ceilingMaterial, heights);
		}
	}
	
	@Override
	protected void drawRoof(CityWorldGenerator generator, InitialBlocks chunk, DataContext context, 
			int y1, int insetNS, int insetWE, int floor, 
			boolean allowRounded, Material roofMaterial, Surroundings heights) {

		if (doBuilding(heights)) {
			super.drawRoof(generator, chunk, context, y1 + higher, insetNS - deeper, insetWE - deeper, floor, allowRounded, roofMaterial, heights);
		}
	}
	
	
	@Override
	protected void drawInteriorParts(CityWorldGenerator generator, RealBlocks blocks, DataContext context,
			RoomProvider rooms, int floor, int floorAt, int floorHeight, int insetNS, int insetWE, boolean allowRounded,
			Material materialWall, Material materialGlass, StairWell stairLocation, Material materialStair,
			Material materialStairWall, Material materialPlatform, boolean drawStairWall, boolean drawStairs,
			boolean topFloor, boolean singleFloor, Surroundings heights) {
		
		if (doBuilding(heights)) {
			// TODO Auto-generated method stub
			super.drawInteriorParts(generator, blocks, context, rooms, floor, floorAt + higher, floorHeight, insetNS, insetWE, allowRounded,
					materialWall, materialGlass, stairLocation, materialStair, materialStairWall, materialPlatform, drawStairWall,
					drawStairs, topFloor, singleFloor, heights);
			
			if (floor == 0) {
				drawFoundationSteps(blocks, floorAt, floorAt + 2, heights);
				drawFoundationColumns(blocks, floorAt, 1, heights);
			} 
			drawFoundationColumns(blocks, floorAt + higher - 1, DataContext.FloorHeight, heights);
		}
	};
	
	private void drawFoundationSteps(SupportBlocks blocks, int y1, int y2, Surroundings heights) {
		// NorthWest
		if (heights.toNorth()) {
			if (heights.toWest()) {
				if (heights.toNorthWest()) {
					// 33
					blocks.setBlocks(0, 3, y1, y2, 0, 3, foundationMaterial);
				} else {
					// 11
					// should be steps
					blocks.setBlocks(0, 3, y1, y2, 0, 3, foundationMaterial);
				}
			} else {
				// 24
				drawFoundationHeadingEastBit(blocks, 0, y1, 0, 3);
			}
		} else {
			if (heights.toWest()) {
				// 14
				drawFoundationHeadingSouthBit(blocks, 0, y1, 0, 3);
			} else {
				// 1 
				// should be steps
				blocks.setBlocks(0, 3, y1, y2, 0, 3, foundationMaterial);
			}
		}
		
		// North
		if (heights.toNorth())
			// 12
			blocks.setBlocks(3, 13, y1, y2, 0, 3, foundationMaterial);
		else
			// 2
			drawFoundationHeadingSouthBit(blocks, 3, y1, 0, 10);
		
		// NorthEast
		if (heights.toNorth()) {
			if (heights.toEast()) {
				if (heights.toNorthEast()) {
					// 32
					blocks.setBlocks(13, 16, y1, y2, 0, 3, foundationMaterial);
				} else {
					// 13
					// should be steps
					blocks.setBlocks(13, 16, y1, y2, 0, 3, foundationMaterial);
				}
			} else {
				// 26
				drawFoundationHeadingWestBit(blocks, 13, y1, 0, 3);
			}
		} else {
			if (heights.toEast()) {
				// 10
				drawFoundationHeadingSouthBit(blocks, 13, y1, 0, 3);
			} else {
				// 3
				// should be steps
				blocks.setBlocks(13, 16, y1, y2, 0, 3, foundationMaterial);
			}
		}
		
		// West
		if (heights.toWest()) {
			// 16
			blocks.setBlocks(0, 3, y1, y2, 3, 13, foundationMaterial);
		} else {
			// 4
			drawFoundationHeadingEastBit(blocks, 0, y1, 3, 10);
		}
		
		// Center
		// 5
		blocks.setBlocks(3, 13, y1, y2, 3, 13, foundationMaterial);
		
		// East
		if (heights.toEast()) {
			// 15
			blocks.setBlocks(13, 16, y1, y2, 3, 13, foundationMaterial);
		} else {
			// 6
			drawFoundationHeadingWestBit(blocks, 13, y1, 3, 10);
		}
		
		// SouthWest
		if (heights.toSouth()) {
			if (heights.toWest()) {
				if (heights.toSouthWest()) {
					// 31
					blocks.setBlocks(0, 3, y1, y2, 13, 16, foundationMaterial);
				} else {
					// 20
					// should be steps
					blocks.setBlocks(0, 3, y1, y2, 13, 16, foundationMaterial);
				}
			} else {
				// 7
				drawFoundationHeadingEastBit(blocks, 0, y1, 13, 3);
			}
		} else {
			if (heights.toWest()) {
				// 22
				drawFoundationHeadingNorthBit(blocks, 0, y1, 13, 3);
			} else {
				// 17
				// should be steps
				blocks.setBlocks(0, 3, y1, y2, 13, 16, foundationMaterial);
			}
		}
		
		// South
		if (heights.toSouth()) {
			// 8
			blocks.setBlocks(3, 13, y1, y2, 13, 16, foundationMaterial);
		} else {
			// 18
			drawFoundationHeadingNorthBit(blocks, 3, y1, 13, 10);
		}
		
		// SouthEast
		if (heights.toSouth()) {
			if (heights.toEast()) {
				if (heights.toSouthEast()) {
					// 30
					blocks.setBlocks(13, 16, y1, y2, 13, 16, foundationMaterial);
				} else {
					// 21
					// should be steps
					blocks.setBlocks(13, 16, y1, y2, 13, 16, foundationMaterial);
				}
			} else {
				// 9
				drawFoundationHeadingWestBit(blocks, 13, y1, 13, 3);
			}
		} else {
			if (heights.toEast()) {
				// 19
				drawFoundationHeadingNorthBit(blocks, 13, y1, 13, 3);
			} else {
				// 23
				// should be steps
				blocks.setBlocks(13, 16, y1, y2, 13, 16, foundationMaterial);
			}
		}
	}
		
	// 18 & 19
	private void drawFoundationHeadingNorthBit(SupportBlocks blocks, int x, int y, int z, int l) {
		blocks.setStairs(x, x + l, y    , z + 1, z + 2, foundationSteps, Stair.NORTH);
		blocks.setBlocks(x, x + l, y    , z    , z + 1, foundationMaterial);
		blocks.setStairs(x, x + l, y + 1, z    , z + 1, foundationSteps, Stair.NORTH);
	}
	
	// 2 & 10
	private void drawFoundationHeadingSouthBit(SupportBlocks blocks, int x, int y, int z, int l) {
		blocks.setStairs(x, x + l, y    , z + 1, z + 2, foundationSteps, Stair.SOUTH);
		blocks.setBlocks(x, x + l, y    , z + 2, z + 3, foundationMaterial);
		blocks.setStairs(x, x + l, y + 1, z + 2, z + 3, foundationSteps, Stair.SOUTH);
	}
	
	// 6 & 9
	private void drawFoundationHeadingWestBit(SupportBlocks blocks, int x, int y, int z, int l) {
		blocks.setStairs(x + 1, x + 2, y    , z, z + l, foundationSteps, Stair.WEST);
		blocks.setBlocks(x    , x + 1, y    , z, z + l, foundationMaterial);
		blocks.setStairs(x    , x + 1, y + 1, z, z + l, foundationSteps, Stair.WEST);
	} 
	
	// 4 & 7
	private void drawFoundationHeadingEastBit(SupportBlocks blocks, int x, int y, int z, int l) {
		blocks.setStairs(x + 1, x + 2, y    , z, z + l, foundationSteps, Stair.EAST);
		blocks.setBlocks(x + 2, x + 3, y    , z, z + l, foundationMaterial);
		blocks.setStairs(x + 2, x + 3, y + 1, z, z + l, foundationSteps, Stair.EAST);
	}
	
	private void drawFoundationColumns(SupportBlocks blocks, int y1, int height, Surroundings heights) {
		int y2 = y1 + height;
		
		if (!heights.toNorthWest())
			drawColumn(blocks, 1, y1, y2, 1);
		if (!heights.toNorthEast())
			drawColumn(blocks, 14, y1, y2, 1);
		if (!heights.toSouthWest())
			drawColumn(blocks, 1, y1, y2, 14);
		if (!heights.toSouthEast())
			drawColumn(blocks, 14, y1, y2, 14);
		
		if (!heights.toNorth()) {
			drawColumn(blocks, 4, y1, y2, 1);
			drawColumn(blocks, 6, y1, y2, 1);
			drawColumn(blocks, 9, y1, y2, 1);
			drawColumn(blocks, 11, y1, y2, 1);
		}
		
		if (!heights.toSouth()) {
			drawColumn(blocks, 4, y1, y2, 14);
			drawColumn(blocks, 6, y1, y2, 14);
			drawColumn(blocks, 9, y1, y2, 14);
			drawColumn(blocks, 11, y1, y2, 14);
		}
		
		if (!heights.toWest()) {
			drawColumn(blocks, 1, y1, y2, 4);
			drawColumn(blocks, 1, y1, y2, 6);
			drawColumn(blocks, 1, y1, y2, 9);
			drawColumn(blocks, 1, y1, y2, 11);
		}
		
		if (!heights.toEast()) {
			drawColumn(blocks, 14, y1, y2, 4);
			drawColumn(blocks, 14, y1, y2, 6);
			drawColumn(blocks, 14, y1, y2, 9);
			drawColumn(blocks, 14, y1, y2, 11);
		}
	}
	
	private void drawColumn(SupportBlocks blocks, int x, int y1, int y2, int z) {
		switch (columnMaterial) {
		case QUARTZ_BLOCK:
			BlackMagic.setBlocks(blocks, x, y1, y2, z, Material.QUARTZ_BLOCK, 2);
			break;
		default:
			blocks.setBlocks(x, y1, y2, z, columnMaterial);
			break;
		}
	}
	
	@Override
	protected void drawRoof(CityWorldGenerator generator, RealBlocks chunk, DataContext context, int y1, int insetNS,
			int insetWE, int floor, boolean allowRounded, boolean outsetEffect, Material material, Surroundings heights,
			RoofStyle thisStyle, RoofFeature thisFeature) {
		if (doBuilding(heights)) {
			super.drawRoof(generator, chunk, context, y1 + higher, insetNS - 2, insetWE - 2, floor, allowRounded, outsetEffect, Material.LAPIS_BLOCK, heights,
					thisStyle, thisFeature);
		}
	}
}
