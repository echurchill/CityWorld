package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class RegisterRoom extends FilledRoom {

	public RegisterRoom() {
		super();
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, BlockFace sideWithWall, Material materialWall, Material materialGlass) {
		switch (sideWithWall) {
		default:
		case NORTH:
			chunk.setBlocks(x, x + 1, y, y + 2, z, z + 3, materialWall);
			chunk.setBlocks(x, x + 1, y + 2, y + 3, z, z + 3, Material.GLASS_PANE, BlockFace.NORTH, BlockFace.SOUTH);
			chunk.setBlocks(x + 1, x + 2, y, y + 1, z, z + 1, materialWall);
			chunk.setBlock(x + 1, y + 1, z, Material.QUARTZ_STAIRS, BlockFace.NORTH);
			chunk.setBlocks(x + 2, x + 3, y, y + 1, z, z + 3, Material.PISTON, BlockFace.UP);
			break;
		case SOUTH:
			chunk.setBlocks(x, x + 1, y, y + 2, z, z + 3, materialWall);
			chunk.setBlocks(x, x + 1, y + 2, y + 3, z, z + 3, Material.GLASS_PANE, BlockFace.NORTH, BlockFace.SOUTH);
			chunk.setBlocks(x + 1, x + 2, y, y + 1, z + 2, z + 3, materialWall);
			chunk.setBlock(x + 1, y + 1, z + 2, Material.QUARTZ_STAIRS, BlockFace.SOUTH);
			chunk.setBlocks(x + 2, x + 3, y, y + 1, z, z + 3, Material.PISTON, BlockFace.UP);
			break;
		case WEST:
			chunk.setBlocks(x, x + 3, y, y + 2, z, z + 1, materialWall);
			chunk.setBlocks(x, x + 3, y + 2, y + 3, z, z + 1, Material.GLASS_PANE, BlockFace.EAST, BlockFace.WEST);
			chunk.setBlocks(x, x + 1, y, y + 1, z + 1, z + 2, materialWall);
			chunk.setBlock(x, y + 1, z + 1, Material.QUARTZ_STAIRS, BlockFace.WEST);
			chunk.setBlocks(x, x + 3, y, y + 1, z + 2, z + 3, Material.PISTON, BlockFace.UP);
			break;
		case EAST:
			chunk.setBlocks(x, x + 3, y, y + 2, z, z + 1, materialWall);
			chunk.setBlocks(x, x + 3, y + 2, y + 3, z, z + 1, Material.GLASS_PANE, BlockFace.EAST, BlockFace.WEST);
			chunk.setBlocks(x + 2, x + 3, y, y + 1, z + 1, z + 2, materialWall);
			chunk.setBlock(x + 2, y + 1, z + 1, Material.QUARTZ_STAIRS, BlockFace.EAST);
			chunk.setBlocks(x, x + 3, y, y + 1, z + 2, z + 3, Material.PISTON, BlockFace.UP);
			break;
		}
	}

}
