package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.BadMagic.Facing;
import me.daddychurchill.CityWorld.Support.BadMagic.Stair;

public class MeetingForFourRoom extends MeetingRoom {

	public MeetingForFourRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x,
			int y, int z, int width, int height, int depth,
			Facing sideWithWall, Material materialWall, Material materialGlass) {

		switch (sideWithWall) {
		case NORTH:
		case SOUTH:
			chunk.setStair(x, y, z, Material.WOOD_STAIRS, Stair.WEST);
			chunk.setStair(x, y, z + 2, Material.WOOD_STAIRS, Stair.WEST);

			chunk.setTable(x + 1, x + 2, y, z, z + 3);
			
			chunk.setStair(x + 2, y, z, Material.WOOD_STAIRS, Stair.EAST);
			chunk.setStair(x + 2, y, z + 2, Material.WOOD_STAIRS, Stair.EAST);
			break;
		case WEST:
		case EAST:
			chunk.setStair(x, y, z, Material.WOOD_STAIRS, Stair.NORTH);
			chunk.setStair(x + 2, y, z, Material.WOOD_STAIRS, Stair.NORTH);

			chunk.setTable(x, x + 3, y, z + 1, z + 2);

			chunk.setStair(x, y, z + 2, Material.WOOD_STAIRS, Stair.SOUTH);
			chunk.setStair(x + 2, y, z + 2, Material.WOOD_STAIRS, Stair.SOUTH);
			break;
		}
	}
	
}
