package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.Trees;

public class ClosetRoom extends FilledRoom {

	public ClosetRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, BlockFace sideWithWall, Material materialWall, Material materialGlass) {
		Trees trees = new Trees(odds);
		Material door = trees.getRandomWoodDoor();

		switch (sideWithWall) {
		default:
		case NORTH:
			drawShelves(generator, chunk, odds, x, y, z, width, height, depth, materialWall, BlockFace.SOUTH);
			chunk.setDoor(x + 1, y, z + depth - 1, door, BlockFace.SOUTH_SOUTH_EAST);
			break;
		case SOUTH:
			drawShelves(generator, chunk, odds, x, y, z, width, height, depth, materialWall, BlockFace.NORTH);
			chunk.setDoor(x + 1, y, z, door, BlockFace.NORTH_NORTH_WEST);
			break;
		case WEST:
			drawShelves(generator, chunk, odds, x, y, z, width, height, depth, materialWall, BlockFace.EAST);
			chunk.setDoor(x + width - 1, y, z + 1, door, BlockFace.EAST_NORTH_EAST);
			break;
		case EAST:
			drawShelves(generator, chunk, odds, x, y, z, width, height, depth, materialWall, BlockFace.WEST);
			chunk.setDoor(x, y, z + 1, door, BlockFace.WEST_NORTH_WEST);
			break;
		}
	}

	private void drawShelves(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int x, int y, int z, int width,
			int height, int depth, Material materialWall, BlockFace facing) {

		// walls and room
		chunk.setBlocks(x, x + width, y, y + height, z, z + depth, materialWall);
		chunk.setBlocks(x + 1, y, y + height, z + 1, Material.AIR);

		// now the stuff
		if (generator.getSettings().treasuresInBuildings
				&& odds.playOdds(generator.getSettings().oddsOfTreasureInBuildings)) {
			chunk.setChest(generator, x + 1, y, z + 1, facing, odds, generator.lootProvider, LootLocation.BUILDING);
		} else {
			Material shelveMaterial = getShelveMaterial(odds, materialWall);
			drawShelve(chunk, odds, x + 1, y, z + 1, shelveMaterial);
			drawShelve(chunk, odds, x + 1, y + 1, z + 1, shelveMaterial);
		}
	}

	private void drawShelve(RealBlocks chunk, Odds odds, int x, int y, int z, Material shelveMaterial) {
		if (odds.flipCoin())
			chunk.setBlock(x, y, z, shelveMaterial);
		else
			chunk.setBlock(x, y, z, Material.BOOKSHELF);
	}

	private Material getShelveMaterial(Odds odds, Material wall) {
		if (wall == Material.QUARTZ_BLOCK) {
			return Material.STONE_SLAB;
		}
		Trees trees = new Trees(odds);
		return trees.getRandomWoodSlab();
	}

}
