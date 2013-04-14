package me.daddychurchill.CityWorld.Buildings.Rooms;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.Direction.Door;
import me.daddychurchill.CityWorld.Support.Direction.Facing;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class ClosetRoom extends FilledRoom {

	public ClosetRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(WorldGenerator generator, RealChunk chunk, Odds odds, int floor, int x,
			int y, int z, int width, int height, int depth,
			Facing sideWithWall, Material materialWall, Material materialGlass) {
		
		switch (sideWithWall) {
		case NORTH:
			chunk.setBlocks(x, x + 1, y, y + height, z, z + depth, materialWall);
			chunk.setBlocks(x + width - 1, x + width, y, y + height, z, z + depth, materialWall);
			chunk.setBlocks(x + 1, x + width - 1, y, y + height, z + depth - 1, z + depth, materialWall);
			chunk.setWoodenDoor(x + 1, y, z + depth - 1, Door.SOUTHBYSOUTHEAST);
			
			//TODO add a chest from time to time
			break;
		case SOUTH:
			chunk.setBlocks(x, x + 1, y, y + height, z, z + depth, materialWall);
			chunk.setBlocks(x + width - 1, x + width, y, y + height, z, z + depth, materialWall);
			chunk.setBlocks(x + 1, x + width - 1, y, y + height, z, z + 1, materialWall);
			chunk.setWoodenDoor(x + 1, y, z, Door.NORTHBYNORTHWEST);
			break;
		case WEST:
			chunk.setBlocks(x, x + width, y, y + height, z, z + 1, materialWall);
			chunk.setBlocks(x, x + width, y, y + height, z + depth - 1, z + depth, materialWall);
			chunk.setBlocks(x + width - 1, x + width, y, y + height, z + 1, z + depth - 1, materialWall);
			chunk.setWoodenDoor(x + width - 1, y, z + 1, Door.EASTBYNORTHEAST);
			break;
		case EAST:
			chunk.setBlocks(x, x + width, y, y + height, z, z + 1, materialWall);
			chunk.setBlocks(x, x + width, y, y + height, z + depth - 1, z + depth, materialWall);
			chunk.setBlocks(x, x + 1, y, y + height, z + 1, z + depth - 1, materialWall);
			chunk.setWoodenDoor(x, y, z + 1, Door.WESTBYNORTHWEST);
			break;
		}
	}

}
