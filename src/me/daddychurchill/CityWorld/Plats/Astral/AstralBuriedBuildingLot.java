package me.daddychurchill.CityWorld.Plats.Astral;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.Nature.MineEntranceLot;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.Populators.BuriedWithRandom;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.BadMagic.Facing;

public class AstralBuriedBuildingLot extends AstralBuriedCityLot {

	private Material wallMaterial;
	private Material stepMaterial;
	private static RoomProvider roomRandom = new BuriedWithRandom();
	
	public AstralBuriedBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		wallMaterial = Material.SMOOTH_BRICK;
		stepMaterial = Material.SMOOTH_STAIRS;
		
		switch (chunkOdds.getRandomInt(10)) {
		case 1:
			wallMaterial = Material.BRICK;
			stepMaterial = Material.BRICK_STAIRS;
			break;
		case 2:
			wallMaterial = Material.WOOD;
			stepMaterial = Material.WOOD_STAIRS;
			break;
		case 3:
			wallMaterial = Material.SANDSTONE;
			stepMaterial = Material.SANDSTONE_STAIRS;
			break;
		case 4:
			wallMaterial = Material.NETHER_BRICK;
			stepMaterial = Material.NETHER_BRICK_STAIRS;
			break;
		case 5:
			wallMaterial = Material.QUARTZ_BLOCK;
			stepMaterial = Material.QUARTZ_STAIRS;
			break;
		case 6:
			wallMaterial = Material.CLAY;
			stepMaterial = Material.QUARTZ_STAIRS;
			break;
		case 7:
			wallMaterial = Material.STAINED_CLAY;
			break;
		case 8:
			wallMaterial = Material.DOUBLE_STEP;
			break;
		case 9:
			wallMaterial = Material.WOOL;
			stepMaterial = Material.QUARTZ_STAIRS;
			break;
		default:
			break;
		}
	}
	
	private final static Material baseMaterial = Material.SMOOTH_BRICK;
	private final static int floorHeight = 4;

	@Override
	protected void generateActualBlocks(CityWorldGenerator generator,
			PlatMap platmap, RealBlocks chunk, DataContext context, int platX,
			int platZ) {
		
		// what is the potential height?
		int possibleFloors = (Math.max(blockYs.minHeight, generator.seaLevel / 2) - 8) / floorHeight;
		chunk.setBlocks(0, 16, StreetLevel, StreetLevel + 1, 0, 16, baseMaterial);
		
		// do able?
		if (possibleFloors > 0) {
			int floors = chunkOdds.getRandomInt(possibleFloors + 1);
			if (floors > 0) {
			
				int y = StreetLevel + 1;
				int lastY = y;
				for (int floor = 0; floor < floors; floor++) {
					lastY = y;
					y = generateFloor(generator, chunk, y, wallMaterial);
				}
				
				// add stairs?
				chunk.setDoClearData(true);
				int topY = lastY - 1;
				MineEntranceLot.generateStairWell(generator, chunk, chunkOdds, 
						6, 6, StreetLevel + 1, topY, topY, topY,
						stepMaterial, wallMaterial, wallMaterial);
				chunk.setDoClearData(false);
			}
		}

		// clear out some space
		int y1 = StreetLevel + 1;
		for (int i = 0; i < 15; i++) {
			int y2 = y1 + chunkOdds.getRandomInt(5);
			chunk.setBlocks(i, y1, y2, 0, Material.AIR);
			chunk.setBlocks(15, y1, y2, i, Material.AIR);
			chunk.setBlocks(i + 1, y1, y2, 15, Material.AIR);
			chunk.setBlocks(0, y1, y2, i + 1, Material.AIR);
		}
		
	}

	private final static double oddsOfWindows = Odds.oddsLikely;
	private final static double oddsOfTallFloor = Odds.oddsVeryUnlikely;
	private final static double oddsOfFurniture = Odds.oddsVeryLikely;
	
	private int generateFloor(CityWorldGenerator generator, RealBlocks chunk, int y, Material wallMaterial) {
		
		// tall one?
		int y2 = y + floorHeight - 1;
		if (chunkOdds.playOdds(oddsOfTallFloor))
			y2 += floorHeight;
		
		// clear room
		chunk.setBlocks(2, 14, y, y2, 2, 14, Material.AIR);
		
		// the walls themselves
		chunk.setDoClearData(true);
		chunk.setWalls(1, 15, y, y2, 1, 15, wallMaterial);
		
		// add a ceiling
		chunk.setBlocks(1, 15, y2, y2 + 1, 1, 15, wallMaterial);
		
		// add stairwells?
		chunk.setBlocks(7, 10, y, y2, 5, 6, wallMaterial); // bottom & top floors should have some closure
		chunk.setBlocks(10, 11, y, y2, 6, 10, wallMaterial);
		chunk.setBlocks(6, 10, y, y2, 10, 11, wallMaterial);
		chunk.setBlocks(5, 6, y, y2, 7, 10, wallMaterial); 
		chunk.setDoClearData(false);
		
		// add stuff?
		if (chunkOdds.playOdds(oddsOfFurniture))
			roomRandom.drawFixtures(generator, chunk, chunkOdds, 0, 2, y, 2, 3, 3, 3, Facing.NORTH, Material.CLAY, Material.THIN_GLASS);
		if (chunkOdds.playOdds(oddsOfFurniture))
			roomRandom.drawFixtures(generator, chunk, chunkOdds, 0, 11, y, 2, 3, 3, 3, Facing.EAST, Material.CLAY, Material.THIN_GLASS);
		if (chunkOdds.playOdds(oddsOfFurniture))
			roomRandom.drawFixtures(generator, chunk, chunkOdds, 0, 2, y, 11, 3, 3, 3, Facing.WEST, Material.CLAY, Material.THIN_GLASS);
		if (chunkOdds.playOdds(oddsOfFurniture))
			roomRandom.drawFixtures(generator, chunk, chunkOdds, 0, 11, y, 11, 3, 3, 3, Facing.SOUTH, Material.CLAY, Material.THIN_GLASS);

		// prep for windows
		boolean regularWindows = chunkOdds.playOdds(oddsOfWindows);
		
		// punch the windows
		// 0123456789ABCDEF
		// .WW--WW--WW--WW.
		for (int i = 3; i < 12; i = i + 4) {
			if (regularWindows || chunkOdds.playOdds(oddsOfWindows))
				chunk.setBlocks(i, i + 2, y + 1, y2, 1, 2, Material.AIR);
			if (regularWindows || chunkOdds.playOdds(oddsOfWindows))
				chunk.setBlocks(i, i + 2, y + 1, y2, 14, 15, Material.AIR);
			if (regularWindows || chunkOdds.playOdds(oddsOfWindows))
				chunk.setBlocks(1, 2, y + 1, y2, i, i + 2, Material.AIR);
			if (regularWindows || chunkOdds.playOdds(oddsOfWindows))
				chunk.setBlocks(14, 15, y + 1, y2, i, i + 2, Material.AIR);
		}
		
		// done?
		return y2 + 1;
	}
}
