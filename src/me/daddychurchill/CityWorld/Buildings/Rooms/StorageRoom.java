package me.daddychurchill.CityWorld.Buildings.Rooms;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.Support.Direction.Stair;
import me.daddychurchill.CityWorld.Support.RealChunk;

public abstract class StorageRoom extends FilledRoom {

	public StorageRoom() {
		// TODO Auto-generated constructor stub
	}
	
	protected void drawNSEmptyShelve(RealChunk chunk, int x, int y, int z, int height, int run) {
		for (int y1 = 0; y1 < height; y1++) {
			chunk.setStair(x, y + y1, z, Material.WOOD_STAIRS, Stair.NORTHFLIP);
			chunk.setBlocks(x, x + 1, y + y1, z + 1, z + run - 1, Material.WOOD_STEP, (byte) 0x8);
			chunk.setStair(x, y + y1, z + run - 1, Material.WOOD_STAIRS, Stair.SOUTHFLIP);
		}
	}
	
	protected void drawWEEmptyShelve(RealChunk chunk, int x, int y, int z, int height, int run) {
		for (int y1 = 0; y1 < height; y1++) {
			chunk.setStair(x, y + y1, z, Material.WOOD_STAIRS, Stair.WESTFLIP);
			chunk.setBlocks(x + 1, x + run - 1, y + y1, z, z + 1, Material.WOOD_STEP, (byte) 0x8);
			chunk.setStair(x + run - 1, y + y1, z, Material.WOOD_STAIRS, Stair.EASTFLIP);
		}
	}
}
