package me.daddychurchill.CityWorld.Rooms;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.BadMagic.Facing;
import me.daddychurchill.CityWorld.Support.BadMagic.Stair;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

import org.bukkit.Material;

public class LoungeTrioRoom extends LoungeRoom {

	public LoungeTrioRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x,
			int y, int z, int width, int height, int depth,
			Facing sideWithWall, Material materialWall, Material materialGlass) {
		switch (sideWithWall) {
		case NORTH:
			chunk.setStair(x + 1, y, z, Material.WOOD_STAIRS, Stair.NORTH);
			chunk.setStair(x, y, z + 1, Material.WOOD_STAIRS, Stair.WEST);
			chunk.setStair(x + 2, y, z + 1, Material.WOOD_STAIRS, Stair.EAST);

			chunk.setTable(x, y, z);
			chunk.setTable(x + 2, y, z);
			break;
		case SOUTH:
			chunk.setStair(x + 1, y, z + 2, Material.WOOD_STAIRS, Stair.SOUTH);
			chunk.setStair(x, y, z + 1, Material.WOOD_STAIRS, Stair.WEST);
			chunk.setStair(x + 2, y, z + 1, Material.WOOD_STAIRS, Stair.EAST);

			chunk.setTable(x, y, z + 2);
			chunk.setTable(x + 2, y, z + 2);
			break;
		case WEST:
			chunk.setStair(x + 1, y, z, Material.WOOD_STAIRS, Stair.NORTH);
			chunk.setStair(x + 1, y, z + 2, Material.WOOD_STAIRS, Stair.SOUTH);
			chunk.setStair(x, y, z + 1, Material.WOOD_STAIRS, Stair.WEST);

			chunk.setTable(x, y, z);
			chunk.setTable(x, y, z + 2);
			break;
		case EAST:
			chunk.setStair(x + 1, y, z, Material.WOOD_STAIRS, Stair.NORTH);
			chunk.setStair(x + 1, y, z + 2, Material.WOOD_STAIRS, Stair.SOUTH);
			chunk.setStair(x + 2, y, z + 1, Material.WOOD_STAIRS, Stair.EAST);
			
			chunk.setTable(x + 2, y, z);
			chunk.setTable(x + 2, y, z + 2);
			break;
		}

	}

}
