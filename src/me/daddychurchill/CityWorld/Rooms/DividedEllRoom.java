package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.BadMagic.Facing;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class DividedEllRoom extends FilledRoom {

	public DividedEllRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, 
			Odds odds, int floor, int x, int y, int z, int width, 
			int height, int depth, Facing sideWithWall, Material materialWall, Material materialGlass) {
		switch (sideWithWall) {
		case NORTH:
			chunk.setBlocks(x, x + 1, y, y + height, z, z + depth, materialWall);
			chunk.setBlocks(x + 1, x + width, y, y + height, z + depth - 1, z + depth, materialWall);
			break;
		case SOUTH:
			chunk.setBlocks(x + width - 1, x + width, y, y + height, z, z + depth, materialWall);
			chunk.setBlocks(x, x + width - 1, y, y + height, z, z + 1, materialWall);
			break;
		case WEST:
			chunk.setBlocks(x, x + width, y, y + height, z + depth - 1, z + depth, materialWall);
			chunk.setBlocks(x + width - 1, x + width, y, y + height, z, z + depth - 1, materialWall);
			break;
		case EAST:
			chunk.setBlocks(x, x + width, y, y + height, z, z + 1, materialWall);
			chunk.setBlocks(x, x + 1, y, y + height, z + 1, z + depth, materialWall);
			break;
		}
	}

}
