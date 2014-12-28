package me.daddychurchill.CityWorld.Rooms;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Direction.Facing;
import me.daddychurchill.CityWorld.Support.Direction.Stair;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;

public class LoungeQuadRoom extends LoungeRoom {

	public LoungeQuadRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealChunk chunk, Odds odds, int floor, int x,
			int y, int z, int width, int height, int depth,
			Facing sideWithWall, Material materialWall, Material materialGlass) {
		
		chunk.setStair(x + 1, y, z, Material.WOOD_STAIRS, Stair.NORTH);
		chunk.setStair(x + 1, y, z + 2, Material.WOOD_STAIRS, Stair.SOUTH);
		chunk.setStair(x, y, z + 1, Material.WOOD_STAIRS, Stair.WEST);
		chunk.setStair(x + 2, y, z + 1, Material.WOOD_STAIRS, Stair.EAST);

		chunk.setTable(x + 1, y, z + 1);
	}

}
