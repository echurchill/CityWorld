package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.BadMagic.Facing;
import me.daddychurchill.CityWorld.Support.BadMagic.Stair;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class LoungeEllCouchRoom extends LoungeCouchRoom {

	public LoungeEllCouchRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x,
			int y, int z, int width, int height, int depth,
			Facing sideWithWall, Material materialWall, Material materialGlass) {

		super.drawFixture(generator, chunk, odds, floor, x, y, z, width, height, depth,
				sideWithWall, materialWall, materialGlass);
		
		switch (sideWithWall) {
		case NORTH:
			for (int z1 = z + 1; z1 < z + depth; z1++)
				chunk.setStair(x + width - 1, y, z1, Material.WOOD_STAIRS, Stair.EAST);
			chunk.setTable(x, x + width - 2, y, z + depth - 1, z + depth);
			break;
		case SOUTH:
			for (int z1 = z; z1 < z + depth - 1; z1++)
				chunk.setStair(x, y, z1, Material.WOOD_STAIRS, Stair.WEST);
			chunk.setTable(x + 2, x + width, y, z, z + 1);
			break;
		case WEST:
			for (int x1 = x + 1; x1 < x + width; x1++)
				chunk.setStair(x1, y, z, Material.WOOD_STAIRS, Stair.NORTH);
			chunk.setTable(x + 2, x + width, y, z + depth - 1, z + depth);
			break;
		case EAST:
			for (int x1 = x; x1 < x + width - 1; x1++)
				chunk.setStair(x1, y, z + depth - 1, Material.WOOD_STAIRS, Stair.SOUTH);
			chunk.setTable(x, x + width - 2, y, z, z + 1);
			break;
		}
	}

}
