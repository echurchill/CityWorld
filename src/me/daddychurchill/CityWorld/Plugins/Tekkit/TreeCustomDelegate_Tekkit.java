package me.daddychurchill.CityWorld.Plugins.Tekkit;

import org.bukkit.block.Block;

import me.daddychurchill.CityWorld.Plugins.FoliageProvider;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

// based on work from brikeener 
// commit: http://code.google.com/p/cityworld-tekkit/source/detail?spec=svn148c2e29ecc3d9b8ba7c63927da30e790ff2e6c0&r=148c2e29ecc3d9b8ba7c63927da30e790ff2e6c0
public class TreeCustomDelegate_Tekkit extends TreeVanillaDelegate_Tekkit {

	public TreeCustomDelegate_Tekkit(RealChunk chunk, Odds odds, int trunkId, int leavesId1, int leavesId2)  {
		super(chunk);
		this.odds = odds;
		this.trunkId = trunkId;
		this.leavesId1 = leavesId1;
		this.leavesId2 = leavesId2;
	}
	
	private Odds odds;
	private int trunkId;
	private int leavesId1;
	private int leavesId2;
	
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
				return block.setTypeIdAndData(FoliageProvider.logId, (byte) data, update);
			else
				return block.setTypeId(trunkId, update);
		
		else if (id == FoliageProvider.leavesId)
			if (odds.flipCoin())
				return block.setTypeId(leavesId1, update);
			else
				return block.setTypeId(leavesId2, update);
		else
			return false;
	}
}
