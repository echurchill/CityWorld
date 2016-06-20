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
	}
	
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
			drawFoundationSteps(blocks, floorAt, 1, heights);
			drawFoundationSteps(blocks, floorAt + 1, 2, heights);
			//drawFoundationColumns(blocks, floorAt + 1, 1, heights);
		} 
//		drawFoundationColumns(blocks, floorAt + higher - 1, DataContext.FloorHeight, heights);
	};
	
	private void drawFoundationSteps(SupportBlocks blocks, int y1, int inset, Surroundings heights) {
		int x1 = heights.toWest() ? 0 : inset;
		int x2 = heights.toEast() ? 16 : 16 - inset;
		int z1 = heights.toNorth() ? 0 : inset;
		int z2 = heights.toSouth() ? 16 : 16 - inset;
		
		blocks.setBlocks(x1, x2, y1, z1, z2, foundationMaterial);
		
		
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
