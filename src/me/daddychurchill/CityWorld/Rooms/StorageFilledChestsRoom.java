package me.daddychurchill.CityWorld.Rooms;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import me.daddychurchill.CityWorld.Support.BadMagic.Facing;
import me.daddychurchill.CityWorld.Support.BadMagic.General;
import me.daddychurchill.CityWorld.Support.BadMagic;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

import org.bukkit.Material;

public class StorageFilledChestsRoom extends StorageRoom {

	public StorageFilledChestsRoom() {
		// TODO Auto-generated constructor stub
	}
	
	private Material matPole = Material.DOUBLE_STEP;

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x,
			int y, int z, int width, int height, int depth,
			Facing sideWithWall, Material materialWall, Material materialGlass) {
		if (generator.settings.treasuresInBuildings) {
			switch (sideWithWall) {
			case NORTH:
				chunk.setBlocks(x, y, y + height - 1, z, matPole);
				chunk.setBlocks(x + 2, y, y + height - 1, z, matPole);
				drawChests(generator, chunk, odds, General.EAST, x, y, z + 1, height);
				drawChests(generator, chunk, odds, General.EAST, x, y, z + 2, height);
				drawChests(generator, chunk, odds, General.WEST, x + 2, y, z + 1, height);
				drawChests(generator, chunk, odds, General.WEST, x + 2, y, z + 2, height);
				chunk.setBlocks(x, y, y + height - 1, z + 3, matPole);
				chunk.setBlocks(x + 2, y, y + height - 1, z + 3, matPole);
				break;
			case SOUTH:
				chunk.setBlocks(x, y, y + height - 1, z + 2, matPole);
				chunk.setBlocks(x + 2, y, y + height - 1, z + 2, matPole);
				drawChests(generator, chunk, odds, General.EAST, x, y, z, height);
				drawChests(generator, chunk, odds, General.EAST, x, y, z + 1, height);
				drawChests(generator, chunk, odds, General.WEST, x + 2, y, z, height);
				drawChests(generator, chunk, odds, General.WEST, x + 2, y, z + 1, height);
				chunk.setBlocks(x, y, y + height - 1, z - 1, matPole);
				chunk.setBlocks(x + 2, y, y + height - 1, z - 1, matPole);
				break;
			case WEST:
				chunk.setBlocks(x, y, y + height - 1, z, matPole);
				chunk.setBlocks(x, y, y + height - 1, z + 2, matPole);
				drawChests(generator, chunk, odds, General.SOUTH, x + 1, y, z, height);
				drawChests(generator, chunk, odds, General.SOUTH, x + 2, y, z, height);
				drawChests(generator, chunk, odds, General.NORTH, x + 1, y, z + 2, height);
				drawChests(generator, chunk, odds, General.NORTH, x + 2, y, z + 2, height);
				chunk.setBlocks(x + 3, y, y + height - 1, z, matPole);
				chunk.setBlocks(x + 3, y, y + height - 1, z + 2, matPole);
				break;
			case EAST:
				chunk.setBlocks(x + 2, y, y + height - 1, z, matPole);
				chunk.setBlocks(x + 2, y, y + height - 1, z + 2, matPole);
				drawChests(generator, chunk, odds, General.SOUTH, x, y, z, height);
				drawChests(generator, chunk, odds, General.SOUTH, x + 1, y, z, height);
				drawChests(generator, chunk, odds, General.NORTH, x, y, z + 2, height);
				drawChests(generator, chunk, odds, General.NORTH, x + 1, y, z + 2, height);
				chunk.setBlocks(x - 1, y, y + height - 1, z, matPole);
				chunk.setBlocks(x - 1, y, y + height - 1, z + 2, matPole);
				break;
			}
		}
	}
	
	protected void drawChests(CityWorldGenerator generator, RealBlocks chunk, Odds odds, BadMagic.General direction, int x, int y, int z, int height) {
		if (odds.playOdds(generator.settings.oddsOfTreasureInBuildings))
			drawChest(generator, chunk, odds, direction, x, y, z);
		if (height > 3) {
			chunk.setSlab(x, y + 1, z, matPole, true);
			if (odds.playOdds(generator.settings.oddsOfTreasureInBuildings))
				drawChest(generator, chunk, odds, direction, x, y + 2, z);
			if (height > 5) {
				chunk.setSlab(x, y + 3, z, matPole, true);
				if (odds.playOdds(generator.settings.oddsOfTreasureInBuildings))
					drawChest(generator, chunk, odds, direction, x, y + 4, z);
			}
		}
	}
	
	protected void drawChest(CityWorldGenerator generator, RealBlocks chunk, Odds odds, BadMagic.General direction, int x, int y, int z) {
		chunk.setChest(generator, x, y, z, direction, odds, generator.lootProvider, LootLocation.WAREHOUSE);
	}
}
