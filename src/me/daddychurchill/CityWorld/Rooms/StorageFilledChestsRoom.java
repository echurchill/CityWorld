package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Slab.Type;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class StorageFilledChestsRoom extends StorageRoom {

	public StorageFilledChestsRoom() {
		// TODO Auto-generated constructor stub
	}

	private final Material matPole = Material.STONE_SLAB;

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, BlockFace sideWithWall, Material materialWall, Material materialGlass) {
		if (generator.getSettings().treasuresInBuildings) {
			switch (sideWithWall) {
			default:
			case NORTH:
				chunk.setBlocks(x, y, y + height - 1, z, matPole, Type.DOUBLE);
				chunk.setBlocks(x + 2, y, y + height - 1, z, matPole, Type.DOUBLE);
				drawChests(generator, chunk, odds, BlockFace.EAST, x, y, z + 1, height);
				drawChests(generator, chunk, odds, BlockFace.EAST, x, y, z + 2, height);
				drawChests(generator, chunk, odds, BlockFace.WEST, x + 2, y, z + 1, height);
				drawChests(generator, chunk, odds, BlockFace.WEST, x + 2, y, z + 2, height);
				chunk.setBlocks(x, y, y + height - 1, z + 3, matPole, Type.DOUBLE);
				chunk.setBlocks(x + 2, y, y + height - 1, z + 3, matPole, Type.DOUBLE);
				break;
			case SOUTH:
				chunk.setBlocks(x, y, y + height - 1, z + 2, matPole, Type.DOUBLE);
				chunk.setBlocks(x + 2, y, y + height - 1, z + 2, matPole, Type.DOUBLE);
				drawChests(generator, chunk, odds, BlockFace.EAST, x, y, z, height);
				drawChests(generator, chunk, odds, BlockFace.EAST, x, y, z + 1, height);
				drawChests(generator, chunk, odds, BlockFace.WEST, x + 2, y, z, height);
				drawChests(generator, chunk, odds, BlockFace.WEST, x + 2, y, z + 1, height);
				chunk.setBlocks(x, y, y + height - 1, z - 1, matPole, Type.DOUBLE);
				chunk.setBlocks(x + 2, y, y + height - 1, z - 1, matPole, Type.DOUBLE);
				break;
			case WEST:
				chunk.setBlocks(x, y, y + height - 1, z, matPole, Type.DOUBLE);
				chunk.setBlocks(x, y, y + height - 1, z + 2, matPole, Type.DOUBLE);
				drawChests(generator, chunk, odds, BlockFace.SOUTH, x + 1, y, z, height);
				drawChests(generator, chunk, odds, BlockFace.SOUTH, x + 2, y, z, height);
				drawChests(generator, chunk, odds, BlockFace.NORTH, x + 1, y, z + 2, height);
				drawChests(generator, chunk, odds, BlockFace.NORTH, x + 2, y, z + 2, height);
				chunk.setBlocks(x + 3, y, y + height - 1, z, matPole, Type.DOUBLE);
				chunk.setBlocks(x + 3, y, y + height - 1, z + 2, matPole, Type.DOUBLE);
				break;
			case EAST:
				chunk.setBlocks(x + 2, y, y + height - 1, z, matPole, Type.DOUBLE);
				chunk.setBlocks(x + 2, y, y + height - 1, z + 2, matPole, Type.DOUBLE);
				drawChests(generator, chunk, odds, BlockFace.SOUTH, x, y, z, height);
				drawChests(generator, chunk, odds, BlockFace.SOUTH, x + 1, y, z, height);
				drawChests(generator, chunk, odds, BlockFace.NORTH, x, y, z + 2, height);
				drawChests(generator, chunk, odds, BlockFace.NORTH, x + 1, y, z + 2, height);
				chunk.setBlocks(x - 1, y, y + height - 1, z, matPole, Type.DOUBLE);
				chunk.setBlocks(x - 1, y, y + height - 1, z + 2, matPole, Type.DOUBLE);
				break;
			}
		}
	}

	private void drawChests(CityWorldGenerator generator, RealBlocks chunk, Odds odds, BlockFace direction, int x,
			int y, int z, int height) {
		if (odds.playOdds(generator.getSettings().oddsOfTreasureInBuildings))
			drawChest(generator, chunk, odds, direction, x, y, z);
		if (height > 3) {
			chunk.setBlock(x, y + 1, z, matPole, Type.TOP);
			if (odds.playOdds(generator.getSettings().oddsOfTreasureInBuildings))
				drawChest(generator, chunk, odds, direction, x, y + 2, z);
			if (height > 5) {
				chunk.setBlock(x, y + 3, z, matPole, Type.TOP);
				if (odds.playOdds(generator.getSettings().oddsOfTreasureInBuildings))
					drawChest(generator, chunk, odds, direction, x, y + 4, z);
			}
		}
	}

	void drawChest(CityWorldGenerator generator, RealBlocks chunk, Odds odds, BlockFace direction, int x,
			int y, int z) {
		chunk.setChest(generator, x, y, z, direction, odds, generator.lootProvider, LootLocation.WAREHOUSE);
	}
}
