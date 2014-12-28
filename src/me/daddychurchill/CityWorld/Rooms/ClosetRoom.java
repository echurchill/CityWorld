package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Direction.Door;
import me.daddychurchill.CityWorld.Support.Direction.Facing;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class ClosetRoom extends FilledRoom {

	public ClosetRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealChunk chunk, Odds odds, int floor, int x,
			int y, int z, int width, int height, int depth,
			Facing sideWithWall, Material materialWall, Material materialGlass) {
		
		switch (sideWithWall) {
		case NORTH:
			drawShelves(chunk, odds, x, y, z, width, height, depth, materialWall);
			chunk.setWoodenDoor(x + 1, y, z + depth - 1, Door.SOUTHBYSOUTHEAST);
			break;
		case SOUTH:
			drawShelves(chunk, odds, x, y, z, width, height, depth, materialWall);
			chunk.setWoodenDoor(x + 1, y, z, Door.NORTHBYNORTHWEST);
			break;
		case WEST:
			drawShelves(chunk, odds, x, y, z, width, height, depth, materialWall);
			chunk.setWoodenDoor(x + width - 1, y, z + 1, Door.EASTBYNORTHEAST);
			break;
		case EAST:
			drawShelves(chunk, odds, x, y, z, width, height, depth, materialWall);
			chunk.setWoodenDoor(x, y, z + 1, Door.WESTBYNORTHWEST);
			break;
		}
	}
	
	private void drawShelves(RealChunk chunk, Odds odds, int x, int y, int z, 
			int width, int height, int depth, Material materialWall) {
		
		// walls and room
		chunk.setBlocks(x, x + width, y, y + height, z, z + depth, materialWall);
		chunk.setBlocks(x + 1, y, y + height, z + 1, Material.AIR);
		
		// now the shelves
		Material shelveMaterial = getShelveMaterial(materialWall);
		drawShelve(chunk, odds, x + 1, y, z + 1, shelveMaterial);
		drawShelve(chunk, odds, x + 1, y + 1, z + 1, shelveMaterial);
	}
	
	private void drawShelve(RealChunk chunk, Odds odds, int x, int y, int z,
			Material shelveMaterial) {
		if (odds.flipCoin())
			chunk.setBlock(x, y, z, shelveMaterial);
		else
			chunk.setBlock(x, y, z, Material.BOOKSHELF);
	}
	
	private Material getShelveMaterial(Material wall) {
		switch (wall) {
		case QUARTZ_BLOCK:
			return Material.STEP;
		
		default: // WOOD
			return Material.WOOD_STEP;
		}
	}

}
