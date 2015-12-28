package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.BadMagic.Facing;
import me.daddychurchill.CityWorld.Support.BadMagic.Stair;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class LibraryStudyRoom extends LibraryRoom {

	public LibraryStudyRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x,
			int y, int z, int width, int height, int depth,
			Facing sideWithWall, Material materialWall, Material materialGlass) {
		switch (sideWithWall) {
		case NORTH:
			chunk.setBlocks(x, x + width, y, y + height, z, z + 1, Material.BOOKSHELF);
			chunk.setStair(x, y, z + 2, Material.WOOD_STAIRS, Stair.WEST);
			chunk.setTable(x + 1, y, z + 2);
			break;
		case SOUTH:
			chunk.setBlocks(x, x + width, y, y + height, z + depth - 1, z + depth, Material.BOOKSHELF);
			chunk.setStair(x, y, z, Material.WOOD_STAIRS, Stair.WEST);
			chunk.setTable(x + 1, y, z);
			chunk.setStair(x + 2, y, z, Material.WOOD_STAIRS, Stair.EAST);
			break;
		case WEST:
			chunk.setBlocks(x, x + 1, y, y + height, z, z + depth, Material.BOOKSHELF);
			chunk.setStair(x + 2, y, z, Material.WOOD_STAIRS, Stair.NORTH);
			chunk.setTable(x + 2, y, z + 1);
			chunk.setStair(x + 2, y, z + 2, Material.WOOD_STAIRS, Stair.SOUTH);
			break;
		case EAST:
			chunk.setBlocks(x + width - 1, x + width, y, y + height, z, z + depth, Material.BOOKSHELF);
			chunk.setStair(x, y, z, Material.WOOD_STAIRS, Stair.NORTH);
			chunk.setTable(x, y, z + 1);
			chunk.setStair(x, y, z + 2, Material.WOOD_STAIRS, Stair.SOUTH);
			break;
		}
	}

}
