package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class LoungeQuadRoom extends LoungeRoom {

	public LoungeQuadRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, BlockFace sideWithWall, Material materialWall, Material materialGlass) {

		chunk.setBlock(x + 1, y, z, Material.BIRCH_STAIRS, BlockFace.NORTH);
		chunk.setBlock(x + 1, y, z + 2, Material.BIRCH_STAIRS, BlockFace.SOUTH);
		chunk.setBlock(x, y, z + 1, Material.BIRCH_STAIRS, BlockFace.WEST);
		chunk.setBlock(x + 2, y, z + 1, Material.BIRCH_STAIRS, BlockFace.EAST);

		Material tableLeg = getTableLeg(odds);
		Material tableTop = getTableTop(odds);

		chunk.setTable(x + 1, y, z + 1, tableLeg, tableTop);
	}

}
