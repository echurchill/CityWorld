package me.daddychurchill.CityWorld.Plats.Astral;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.MazeArray;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.BadMagic.TrapDoor;
import me.daddychurchill.CityWorld.Support.MazeArray.MazeBit;

import org.bukkit.DyeColor;
import org.bukkit.Material;

public abstract class AstralStructureTowerLot extends AstralStructureLot {

	public AstralStructureTowerLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
	}

	// right now: w--ww--ww--ww--w 
	// should be: w--w--w--w--w--w
	private final static Material emptyHallMaterial = Material.AIR;
	private final static Material specialHallMaterial = Material.STONE;
	private final static int mazeWidth = 9; 
	private final static int towerFloorHeight = 4;
	private final static int towerBottom = towerFloorHeight;
	protected enum TowerStyle {DARK, LIGHT};
	
	protected void generateTower(CityWorldGenerator generator, RealBlocks chunk, TowerStyle style) {
		
		// set things up for darkness
		Material wallMaterial = generator.materialProvider.itemsSelectMaterial_AstralTowerDark.getRandomMaterial(chunkOdds, Material.OBSIDIAN);
		Material trimMaterial = generator.materialProvider.itemsSelectMaterial_AstralTowerTrim.getRandomMaterial(chunkOdds, Material.AIR);
		DyeColor windowPrimaryColor = DyeColor.BLACK;
		DyeColor windowSecondaryColor = DyeColor.PURPLE;
		
		// adjust for lightness
		if (style == TowerStyle.LIGHT) {
			wallMaterial = generator.materialProvider.itemsSelectMaterial_AstralTowerLight.getRandomMaterial(chunkOdds, Material.ENDER_STONE);
			trimMaterial = generator.materialProvider.itemsSelectMaterial_AstralTowerTrim.getRandomMaterial(chunkOdds, Material.GLOWSTONE);
			windowPrimaryColor = DyeColor.WHITE;
			windowSecondaryColor = DyeColor.SILVER;
		}
		
		// calculate a few things
		int y1 = towerBottom;
		int y2 = 128 - towerFloorHeight * 2;//generator.seaLevel + mazeFloorHeight * 10;
		y2 = y2 / towerFloorHeight * towerFloorHeight + towerBottom + 1;
		
		// outside wall please
		chunk.setBlocks(0, 16, 1, y2, 0, 16, wallMaterial);
		
		// now clear out the inner bits
		while (y1 < y2) {
			
			// punch down to below
			if (y1 > towerBottom) {
				int x = chunkOdds.getRandomInt(mazeWidth / 2) * 4 + 1;
				int z = chunkOdds.getRandomInt(mazeWidth / 2) * 4 + 1;
				if (y1 == y2 - 1)
					chunk.setTrapDoor(x, y1, z, TrapDoor.TOP_NORTH);
				else
					chunk.setBlocks(x, x + 2, y1, y1 + 1, z, z + 2, getHallMaterial(generator));
			}
			
			// new floor please
			MazeArray floor = new MazeArray(chunkOdds, mazeWidth, mazeWidth);
			for (int m = 1; m < mazeWidth; m++)
				for (int n = 1; n < mazeWidth; n++)
					if (floor.getBit(m, n) == MazeBit.HALL) {
						int x1 = m * 2 - 1;
						int z1 = n * 2 - 1;
						if (chunk.isType(x1, y1 + 1, z1, wallMaterial)) {
							Material hallMaterial = getHallMaterial(generator);
							chunk.setBlocks(x1, x1 + 2, y1 + 1, y1 + towerFloorHeight, z1, z1 + 2, hallMaterial);

							if (hallMaterial == specialHallMaterial) {
								for (int y = y1 + 1; y < y1 + towerFloorHeight; y++) {
									int x = x1 + chunkOdds.getRandomInt(2);
									int z = z1 + chunkOdds.getRandomInt(2);
									chunk.setBlock(x, y, z, getSpecialOre(generator));
								}
							}
						}
					}
			
			// move up a level
			y1 += towerFloorHeight;
		}
		
		// now the top bit
		y1 = y1 - towerFloorHeight + 1;
		for (int i = 1; i < 15; i += 3) {
			chunk.setBlocks(i, i + 2, y1, y1 + 1, 0, 1, wallMaterial);
			chunk.setBlocks(i, i + 2, y1, y1 + 1, 15, 16, wallMaterial);
			chunk.setBlocks(0, 1, y1, y1 + 1, i, i + 2, wallMaterial);
			chunk.setBlocks(15, 16, y1, y1 + 1, i, i + 2, wallMaterial);
		}
		
		// trim the corners
		int y3 = generator.seaLevel + towerFloorHeight * 2;
		chunk.setDoPhysics(true);
		chunk.setBlocks(0, y3, y1 - 4, 0, trimMaterial);
		chunk.setBlocks(15, y3, y1 - 4, 0, trimMaterial);
		chunk.setBlocks(0, y3, y1 - 4, 15, trimMaterial);
		chunk.setBlocks(15, y3, y1 - 4, 15, trimMaterial);
		chunk.setDoPhysics(false);
		
		// top windows
		y1 = y1 - towerFloorHeight + 1;
		for (int i = 1; i < 15; i += 4) {
			chunk.setGlass(i, i + 2, y1, y1 + 2, 0, 1, windowPrimaryColor);
			chunk.setGlass(i, i + 2, y1, y1 + 2, 15, 16, windowPrimaryColor);
			chunk.setGlass(0, 1, y1, y1 + 2, i, i + 2, windowPrimaryColor);
			chunk.setGlass(15, 16, y1, y1 + 2, i, i + 2, windowPrimaryColor);
		}
			
		// top windows
		y1 = y1 - towerFloorHeight;
		for (int i = 5; i < 10; i += 4) {
			chunk.setGlass(i, i + 2, y1, y1 + 2, 0, 1, windowSecondaryColor);
			chunk.setGlass(i, i + 2, y1, y1 + 2, 15, 16, windowSecondaryColor);
			chunk.setGlass(0, 1, y1, y1 + 2, i, i + 2, windowSecondaryColor);
			chunk.setGlass(15, 16, y1, y1 + 2, i, i + 2, windowSecondaryColor);
		}
		
	}

	private final static double oddsOfSpecialOre = Odds.oddsExtremelyLikely;
	private final static double oddsOfSpecialHall = Odds.oddsUnlikely;

	private Material getSpecialOre(CityWorldGenerator generator) {
		if (chunkOdds.playOdds(oddsOfSpecialOre))
			return generator.materialProvider.itemsSelectMaterial_AstralTowerOres.getRandomMaterial(chunkOdds);
		else
			return specialHallMaterial;
	}
	
	private Material getHallMaterial(CityWorldGenerator generator) {
		if (chunkOdds.playOdds(oddsOfSpecialHall))
			return generator.materialProvider.itemsSelectMaterial_AstralTowerHalls.getRandomMaterial(chunkOdds, specialHallMaterial);
		else
			return emptyHallMaterial;
	}
}
