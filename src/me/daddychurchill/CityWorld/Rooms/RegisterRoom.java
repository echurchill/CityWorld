package me.daddychurchill.CityWorld.Rooms;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.BadMagic.Facing;
import me.daddychurchill.CityWorld.Support.BadMagic.Stair;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class RegisterRoom extends FilledRoom {

	public RegisterRoom() {
		super();
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk,
			Odds odds, int floor, int x, int y, int z, int width, int height,
			int depth, Facing sideWithWall, Material materialWall,
			Material materialGlass) {
		switch (sideWithWall) {
		case NORTH:
			chunk.setBlocks(x, x + 1, y, y + 2, z, z + 3, materialWall);
			chunk.setBlocks(x, x + 1, y + 2, y + 3, z, z + 3, Material.THIN_GLASS);
			chunk.setBlocks(x + 1, x + 2, y, y + 1, z, z + 1, materialWall);
			chunk.setStair(x + 1, y + 1, z, Material.QUARTZ_STAIRS, Stair.NORTH);
			chunk.setBlocksTypeAndDirection(x + 2, x + 3, y, y + 1, z, z + 3, Material.PISTON_BASE, BlockFace.UP);
			break;
		case SOUTH:
			chunk.setBlocks(x, x + 1, y, y + 2, z, z + 3, materialWall);
			chunk.setBlocks(x, x + 1, y + 2, y + 3, z, z + 3, Material.THIN_GLASS);
			chunk.setBlocks(x + 1, x + 2, y, y + 1, z + 2, z + 3, materialWall);
			chunk.setStair(x + 1, y + 1, z + 2, Material.QUARTZ_STAIRS, Stair.SOUTH);
			chunk.setBlocksTypeAndDirection(x + 2, x + 3, y, y + 1, z, z + 3, Material.PISTON_BASE, BlockFace.UP);
			break;
		case WEST:
			chunk.setBlocks(x, x + 3, y, y + 2, z, z + 1, materialWall);
			chunk.setBlocks(x, x + 3, y + 2, y + 3, z, z + 1, Material.THIN_GLASS);
			chunk.setBlocks(x, x + 1, y, y + 1, z + 1, z + 2, materialWall);
			chunk.setStair(x, y + 1, z + 1, Material.QUARTZ_STAIRS, Stair.WEST);
			chunk.setBlocksTypeAndDirection(x, x + 3, y, y + 1, z + 2, z + 3, Material.PISTON_BASE, BlockFace.UP);
			break;
		case EAST:
			chunk.setBlocks(x, x + 3, y, y + 2, z, z + 1, materialWall);
			chunk.setBlocks(x, x + 3, y + 2, y + 3, z, z + 1, Material.THIN_GLASS);
			chunk.setBlocks(x + 2, x + 3, y, y + 1, z + 1, z + 2, materialWall);
			chunk.setStair(x + 2, y + 1, z + 1, Material.QUARTZ_STAIRS, Stair.EAST);
			chunk.setBlocksTypeAndDirection(x, x + 3, y, y + 1, z + 2, z + 3, Material.PISTON_BASE, BlockFace.UP);
			break;
		}
	}

}
