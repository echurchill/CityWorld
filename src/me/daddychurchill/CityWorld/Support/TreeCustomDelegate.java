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
		this.trunkId = BlackMagic.getMaterialId(trunk);
		this.leavesId1 = BlackMagic.getMaterialId(leaves1);
		this.leavesId2 = BlackMagic.getMaterialId(leaves2);
	}
	
	@Override
	public int getHeight() {
		return chunk.height;
	}

	@Override
	public int getTypeId(int x, int y, int z) {
		return BlackMagic.getMaterialId(world.getBlockAt(x, y, z));
	}

	@Override
	public boolean isEmpty(int x, int y, int z) {
		return world.getBlockAt(x, y, z).isEmpty();
	}

	@Override
	public boolean setRawTypeId(int x, int y, int z, int id) {
		return setTypeIdAndData(x, y, z, id, 0);
	}

	@Override
	public boolean setRawTypeIdAndData(int x, int y, int z, int id, int data) {
		return setTypeIdAndData(x, y, z, id, data);
	}

	@Override
	public boolean setTypeId(int x, int y, int z, int id) {
		return setTypeIdAndData(x, y, z, id, 0);
	}

	@Override
	public boolean setTypeIdAndData(int x, int y, int z, int id, int data) {
		Block block = world.getBlockAt(x, y, z);
		if (id == FoliageProvider.logId)
			if (trunkId == FoliageProvider.logId)
				BlackMagic.setBlockType(block, FoliageProvider.logId, data);
			else
				BlackMagic.setBlockType(block, trunkId);
		
		else if (id == FoliageProvider.leavesId)
			if (odds.flipCoin())
				BlackMagic.setBlockType(block, leavesId1);
			else
				BlackMagic.setBlockType(block, leavesId2);
		else
			return false;
		return true;
	}
}
