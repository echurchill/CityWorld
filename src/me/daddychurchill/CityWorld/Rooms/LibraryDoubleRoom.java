package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.Support.Direction.Facing;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class LibraryDoubleRoom extends LibraryRoom {

	public LibraryDoubleRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Draw(RealChunk chunk, Odds odds, int x, int y, int z,
			int width, int height, int depth, Facing sideWithWall, byte wallId,
			byte glassId) {

		int stand = 0;
		switch (sideWithWall) {
		case SOUTH:
			stand = depth - 1;
		case NORTH:
			for (int offset = 0; offset < width; offset += 2) {
				chunk.setBlocks(x + offset, x + 1 + offset, y, y + height, z, z + depth, Material.BOOKSHELF);
				if (offset < width - 1)
					chunk.setBlock(x + offset + 1, y, z + stand, Material.ENCHANTMENT_TABLE);
			}
			break;
		case EAST:
			stand = width - 1; 
		case WEST:
			for (int offset = 0; offset < depth; offset += 2) {
				chunk.setBlocks(x, x + width, y, y + height, z + offset, z + 1 + offset, Material.BOOKSHELF);
				if (offset < depth - 1)
					chunk.setBlock(x + stand, y, z + offset + 1, Material.ENCHANTMENT_TABLE);
			}
			break;
		}
	}

}
