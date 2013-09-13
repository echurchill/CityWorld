package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.Direction.Facing;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class LoungeGameRoom extends LoungeChairsRoom {

	public LoungeGameRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(WorldGenerator generator, RealChunk chunk, Odds odds, int floor, int x,
			int y, int z, int width, int height, int depth,
			Facing sideWithWall, Material materialWall, Material materialGlass) {

		super.drawFixture(generator, chunk, odds, floor, x, y, z, width, height, depth,
				sideWithWall, materialWall, materialGlass);
		
		switch (sideWithWall) {
		case NORTH:
		case SOUTH:
			chunk.setTable(x + 1, y, z);
			chunk.setTable(x + 1, y, z + 2);
			break;
		case WEST:
		case EAST:
			chunk.setTable(x, y, z + 1);
			chunk.setTable(x + 2, y, z + 1);
			break;
		}
	}

}
