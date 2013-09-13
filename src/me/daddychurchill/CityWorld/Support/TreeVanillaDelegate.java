package me.daddychurchill.CityWorld.Support;

import org.bukkit.BlockChangeDelegate;
import org.bukkit.World;

public class TreeVanillaDelegate implements BlockChangeDelegate {

	protected World world;
	protected RealChunk chunk;
	
	public TreeVanillaDelegate(RealChunk chunk) {
		this.chunk = chunk;
		this.world = chunk.world;
	}

	@Override
	public int getHeight() {
		return chunk.height;
	}

	@Override
	public int getTypeId(int x, int y, int z) {
		return world.getBlockAt(x, y, z).getTypeId();
	}

	@Override
	public boolean isEmpty(int x, int y, int z) {
		return world.getBlockAt(x, y, z).isEmpty();
	}

	@Override
	public boolean setRawTypeId(int x, int y, int z, int id) {
		return world.getBlockAt(x, y, z).setTypeIdAndData(id, (byte) 0, false);
	}

	@Override
	public boolean setRawTypeIdAndData(int x, int y, int z, int id, int data) {
		return world.getBlockAt(x, y, z).setTypeIdAndData(id, (byte) data, false);
	}

	@Override
	public boolean setTypeId(int x, int y, int z, int id) {
		return world.getBlockAt(x, y, z).setTypeIdAndData(id, (byte) 0, false);
	}

	@Override
	public boolean setTypeIdAndData(int x, int y, int z, int id, int data) {
		return world.getBlockAt(x, y, z).setTypeIdAndData(id, (byte) data, false);
	}
}
