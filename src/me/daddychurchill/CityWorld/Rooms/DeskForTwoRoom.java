package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.BadMagic.Facing;
import me.daddychurchill.CityWorld.Support.BadMagic.Stair;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class DeskForTwoRoom extends DeskRoom {

	public DeskForTwoRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x,
			int y, int z, int width, int height, int depth,
			Facing sideWithWall, Material materialWall, Material materialGlass) {
		switch (sideWithWall) {
		case NORTH:
			chunk.setBlocks(x, x + 1, y, y + height, z, z + depth, materialWall);
			chunk.setTable(x + 1, x + 2, y, z, z + 3, Material.WOOD_PLATE);
			chunk.setStair(x + 2, y, z, Material.WOOD_STAIRS, Stair.EAST);
			chunk.setStair(x + 2, y, z + 2, Material.WOOD_STAIRS, Stair.EAST);
			break;
		case SOUTH:
			chunk.setBlocks(x + width - 1, x + width, y, y + height, z, z + depth, materialWall);
			chunk.setTable(x + 1, x + 2, y, z, z + 3, Material.WOOD_PLATE);
			chunk.setStair(x, y, z, Material.WOOD_STAIRS, Stair.WEST);
			chunk.setStair(x, y, z + 2, Material.WOOD_STAIRS, Stair.WEST);
			break;
		case WEST:
			chunk.setBlocks(x, x + width, y, y + height, z + depth - 1, z + depth, materialWall);
			chunk.setTable(x, x + 3, y, z + 1, z + 2, Material.WOOD_PLATE);
			chunk.setStair(x, y, z, Material.WOOD_STAIRS, Stair.NORTH);
			chunk.setStair(x + 2, y, z, Material.WOOD_STAIRS, Stair.NORTH);
			break;
		case EAST:
			chunk.setBlocks(x, x + width, y, y + height, z, z + 1, materialWall);
			chunk.setTable(x, x + 3, y, z + 1, z + 2, Material.WOOD_PLATE);
			chunk.setStair(x, y, z + 2, Material.WOOD_STAIRS, Stair.SOUTH);
			chunk.setStair(x + 2, y, z + 2, Material.WOOD_STAIRS, Stair.SOUTH);
			break;
		}
	}

}
