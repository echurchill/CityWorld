package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.Plugins.FoliageProvider;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class TreeCustomDelegate extends TreeVanillaDelegate {

	private Odds odds;
	private byte trunkId;
	private byte leavesId1;
	private byte leavesId2;
	
	public TreeCustomDelegate(RealChunk chunk, Odds odds, Material trunk, Material leaves1, Material leaves2) {
		super(chunk);
		this.odds = odds;
		this.trunkId = SupportChunk.getMaterialId(trunk);
		this.leavesId1 = SupportChunk.getMaterialId(leaves1);
		this.leavesId2 = SupportChunk.getMaterialId(leaves2);
	}
	
	@Override
	public int getHeight() {
		return chunk.height;
	}

	@Override
	public int getTypeId(int x, int y, int z) {
		return SupportChunk.getMaterialId(world.getBlockAt(x, y, z));
	}

	@Override
	public boolean isEmpty(int x, int y, int z) {
		return world.getBlockAt(x, y, z).isEmpty();
	}

	@Override
	public boolean setRawTypeId(int x, int y, int z, int id) {
		return setTypeIdAndData(x, y, z, id, 0, false);
	}

	@Override
	public boolean setRawTypeIdAndData(int x, int y, int z, int id, int data) {
		return setTypeIdAndData(x, y, z, id, data, false);
	}

	@Override
	public boolean setTypeId(int x, int y, int z, int id) {
		return setTypeIdAndData(x, y, z, id, 0, false);
	}

	@Override
	public boolean setTypeIdAndData(int x, int y, int z, int id, int data) {
		return setTypeIdAndData(x, y, z, id, data, false);
	}
	
	private boolean setTypeIdAndData(int x, int y, int z, int id, int data, boolean update) {
		Block block = world.getBlockAt(x, y, z);
		if (id == FoliageProvider.logId)
			if (trunkId == FoliageProvider.logId)
				return SupportChunk.setBlockType(block, FoliageProvider.logId, (byte) data, update);
			else
				return SupportChunk.setBlockType(block, trunkId, update);
		
		else if (id == FoliageProvider.leavesId)
			if (odds.flipCoin())
				return SupportChunk.setBlockType(block, leavesId1, update);
			else
				return SupportChunk.setBlockType(block, leavesId2, update);
		else
			return false;
	}
}
