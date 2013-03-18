package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.Support.Direction.Facing;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class LoungeKitchenetteRoom extends LoungeRoom {

	public LoungeKitchenetteRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(RealChunk chunk, Odds odds, int floor, int x, int y,
			int z, int width, int height, int depth, Facing sideWithWall,
			Material materialWall, Material materialGlass) {
		switch (sideWithWall) {
		case NORTH:
			chunk.setBlocks(x, x + 1, y, y + height, z, z + depth, materialWall);
			chunk.setBlock(x + 1, y, z, Material.PISTON_BASE);
			chunk.setBlock(x + 1, y, z + 1, Material.CAULDRON, odds.getRandomByte(4));
			chunk.setBlock(x + 1, y, z + 2, Material.PISTON_BASE);
			if (odds.flipCoin())
				chunk.setBlock(x + 1, y + 1, z, Material.BREWING_STAND);
			break;
		case SOUTH:
			chunk.setBlocks(x + width - 1, x + width, y, y + height, z, z + depth, materialWall);
			chunk.setBlock(x + 1, y, z, Material.PISTON_BASE);
			chunk.setBlock(x + 1, y, z + 1, Material.CAULDRON, odds.getRandomByte(4));
			chunk.setBlock(x + 1, y, z + 2, Material.PISTON_BASE);
			if (odds.flipCoin())
				chunk.setBlock(x + 1, y + 1, z + 2, Material.BREWING_STAND);
			break;
		case WEST:
			chunk.setBlocks(x, x + width, y, y + height, z + depth - 1, z + depth, materialWall);
			chunk.setBlock(x, y, z + 1, Material.PISTON_BASE);
			chunk.setBlock(x + 1, y, z + 1, Material.CAULDRON, odds.getRandomByte(4));
			chunk.setBlock(x + 2, y, z + 1, Material.PISTON_BASE);
			if (odds.flipCoin())
				chunk.setBlock(x, y + 1, z + 1, Material.BREWING_STAND);
			break;
		case EAST:
			chunk.setBlocks(x, x + width, y, y + height, z, z + 1, materialWall);
			chunk.setBlock(x, y, z + 1, Material.PISTON_BASE);
			chunk.setBlock(x + 1, y, z + 1, Material.CAULDRON, odds.getRandomByte(4));
			chunk.setBlock(x + 2, y, z + 1, Material.PISTON_BASE);
			if (odds.flipCoin())
				chunk.setBlock(x + 2, y + 1, z + 1, Material.BREWING_STAND);
			break;
		}
	}

}
