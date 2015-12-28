package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.BadMagic.Facing;
import me.daddychurchill.CityWorld.Support.BadMagic.Stair;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class LoungeTVRoom extends LoungeRoom {

	public LoungeTVRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x,
			int y, int z, int width, int height, int depth,
			Facing sideWithWall, Material materialWall, Material materialGlass) {
		switch (sideWithWall) {
		case NORTH:
			chunk.setStair(x, y, z + depth - 1, Material.WOOD_STAIRS, Stair.SOUTH);
			chunk.setStair(x + 1, y, z + depth - 1, Material.WOOD_STAIRS, Stair.SOUTH);
			chunk.setStair(x + 2, y, z + depth - 1, Material.WOOD_STAIRS, Stair.SOUTH);

			chunk.setBlocks(x, x + 3, y, y + height, z - 1, z, materialWall);
			
			//TODO add picture to wall at x, x + 3, y, y + height, z, z + 1
			break;
		case SOUTH:
			chunk.setStair(x, y, z, Material.WOOD_STAIRS, Stair.NORTH);
			chunk.setStair(x + 1, y, z, Material.WOOD_STAIRS, Stair.NORTH);
			chunk.setStair(x + 2, y, z, Material.WOOD_STAIRS, Stair.NORTH);

			chunk.setBlocks(x, x + 3, y, y + height, z + depth, z + depth + 1, materialWall);
			break;
		case WEST:
			chunk.setStair(x + width - 1, y, z, Material.WOOD_STAIRS, Stair.EAST);
			chunk.setStair(x + width - 1, y, z + 1, Material.WOOD_STAIRS, Stair.EAST);
			chunk.setStair(x + width - 1, y, z + 2, Material.WOOD_STAIRS, Stair.EAST);

			chunk.setBlocks(x - 1, x, y, y + height, z, z + 3, materialWall);
			break;
		case EAST:
			chunk.setStair(x, y, z, Material.WOOD_STAIRS, Stair.WEST);
			chunk.setStair(x, y, z + 1, Material.WOOD_STAIRS, Stair.WEST);
			chunk.setStair(x, y, z + 2, Material.WOOD_STAIRS, Stair.WEST);

			chunk.setBlocks(x + width, x + width + 1, y, y + height, z, z + 3, materialWall);
			break;
		}
	}

}
