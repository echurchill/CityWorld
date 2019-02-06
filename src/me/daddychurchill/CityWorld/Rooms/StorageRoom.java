package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.type.Slab.Type;

import me.daddychurchill.CityWorld.Support.RealBlocks;

abstract class StorageRoom extends FilledRoom {

	StorageRoom() {
		// TODO Auto-generated constructor stub
	}

	void drawNSEmptyShelve(RealBlocks chunk, int x, int y, int z, int height, int run) {
		for (int y1 = 0; y1 < height; y1++) {
			chunk.setBlock(x, y + y1, z, Material.BIRCH_STAIRS, BlockFace.NORTH, Half.TOP);
			chunk.setBlocks(x, x + 1, y + y1, z + 1, z + run - 1, Material.BIRCH_SLAB, Type.TOP);
			chunk.setBlock(x, y + y1, z + run - 1, Material.BIRCH_STAIRS, BlockFace.SOUTH, Half.TOP);
		}
	}

	void drawWEEmptyShelve(RealBlocks chunk, int x, int y, int z, int height, int run) {
		for (int y1 = 0; y1 < height; y1++) {
			chunk.setBlock(x, y + y1, z, Material.BIRCH_STAIRS, BlockFace.WEST, Half.TOP);
			chunk.setBlocks(x + 1, x + run - 1, y + y1, z, z + 1, Material.BIRCH_SLAB, Type.TOP);
			chunk.setBlock(x + run - 1, y + y1, z, Material.BIRCH_STAIRS, BlockFace.EAST, Half.TOP);
		}
	}
}
