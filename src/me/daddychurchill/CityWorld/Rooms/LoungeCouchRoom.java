package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.BadMagic.Facing;
import me.daddychurchill.CityWorld.Support.BadMagic.Stair;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class LoungeCouchRoom extends LoungeRoom {

	public LoungeCouchRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x,
			int y, int z, int width, int height, int depth,
			Facing sideWithWall, Material materialWall, Material materialGlass) {
		switch (sideWithWall) {
		case NORTH:
			for (int x1 = x; x1 < x + width; x1++)
				chunk.setStair(x1, y, z, Material.WOOD_STAIRS, Stair.NORTH);
			break;
		case SOUTH:
			for (int x1 = x; x1 < x + width; x1++)
				chunk.setStair(x1, y, z + depth - 1, Material.WOOD_STAIRS, Stair.SOUTH);
			break;
		case WEST:
			for (int z1 = z; z1 < z + depth; z1++)
				chunk.setStair(x, y, z1, Material.WOOD_STAIRS, Stair.WEST);
			break;
		case EAST:
			for (int z1 = z; z1 < z + depth; z1++)
				chunk.setStair(x + width - 1, y, z1, Material.WOOD_STAIRS, Stair.EAST);
			break;
		}
	}

}
