package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.BadMagic.Facing;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class LibraryDoubleRoom extends LibraryRoom {

	public LibraryDoubleRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x,
			int y, int z, int width, int height, int depth,
			Facing sideWithWall, Material materialWall, Material materialGlass) {

		switch (sideWithWall) {
		case SOUTH:
			drawNSBookshelves(chunk, x, y, z, width, height, depth, depth - 1);
			break;
		case NORTH:
			drawNSBookshelves(chunk, x, y, z, width, height, depth, 0);
			break;
		case EAST:
			drawWEBookshelves(chunk, x, y, z, width, height, depth, width - 1);
			break;
		case WEST:
			drawWEBookshelves(chunk, x, y, z, width, height, depth, 0);
			break;
		}
	}
	
	public void drawNSBookshelves(RealBlocks chunk, int x, int y, int z, int width, int height, int depth, int i) {
		for (int offset = 0; offset < width; offset += 2) {
			chunk.setBlocks(x + offset, x + 1 + offset, y, y + height, z, z + depth, Material.BOOKSHELF);
			if (offset < width - 1)
				chunk.setBlock(x + offset + 1, y, z + i, Material.BOOKSHELF);
//				chunk.setBlock(x + offset + 1, y, z + i, Material.ENCHANTMENT_TABLE);
		}
	}
	
	public void drawWEBookshelves(RealBlocks chunk, int x, int y, int z, int width, int height, int depth, int i) {
		for (int offset = 0; offset < depth; offset += 2) {
			chunk.setBlocks(x, x + width, y, y + height, z + offset, z + 1 + offset, Material.BOOKSHELF);
			if (offset < depth - 1)
				chunk.setBlock(x + i, y, z + offset + 1, Material.BOOKSHELF);
//				chunk.setBlock(x + i, y, z + offset + 1, Material.ENCHANTMENT_TABLE);
		}
	}
}
