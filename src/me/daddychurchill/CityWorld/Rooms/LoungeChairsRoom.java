package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.Support.Direction.Facing;
import me.daddychurchill.CityWorld.Support.Direction.Stair;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class LoungeChairsRoom extends LoungeRoom {

	public LoungeChairsRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Draw(RealChunk chunk, Odds odds, int x, int y, int z,
			int width, int height, int depth, Facing sideWithWall, byte wallId,
			byte glassId) {
		
		switch (sideWithWall) {
		case NORTH:
		case SOUTH:
			chunk.setStair(x, y, z, Material.WOOD_STAIRS, Stair.WEST);
			chunk.setStair(x + 2, y, z, Material.WOOD_STAIRS, Stair.EAST);

			chunk.setStair(x, y, z + 2, Material.WOOD_STAIRS, Stair.WEST);
			chunk.setStair(x + 2, y, z + 2, Material.WOOD_STAIRS, Stair.EAST);
			break;
		case WEST:
		case EAST:
			chunk.setStair(x, y, z, Material.WOOD_STAIRS, Stair.NORTH);
			chunk.setStair(x, y, z + 2, Material.WOOD_STAIRS, Stair.SOUTH);

			chunk.setStair(x + 2, y, z, Material.WOOD_STAIRS, Stair.NORTH);
			chunk.setStair(x + 2, y, z + 2, Material.WOOD_STAIRS, Stair.SOUTH);
			break;
		}
	}

}
