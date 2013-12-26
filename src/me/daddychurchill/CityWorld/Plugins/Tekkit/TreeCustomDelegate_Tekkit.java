package me.daddychurchill.CityWorld.Plugins.Tekkit;

import org.bukkit.Material;
import org.bukkit.block.Block;

import me.daddychurchill.CityWorld.Plugins.FoliageProvider;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.BlackMagic;

// based on work from brikeener 
// commit: http://code.google.com/p/cityworld-tekkit/source/detail?spec=svn148c2e29ecc3d9b8ba7c63927da30e790ff2e6c0&r=148c2e29ecc3d9b8ba7c63927da30e790ff2e6c0
public class TreeCustomDelegate_Tekkit extends TreeVanillaDelegate_Tekkit {

	public TreeCustomDelegate_Tekkit(RealChunk chunk, Odds odds, 
			Material trunk, Material leaves1, Material leaves2) {
		super(chunk);
		this.odds = odds;
		this.trunkId = BlackMagic.getMaterialId(trunk);
		this.leavesId1 = BlackMagic.getMaterialId(leaves1);
		this.leavesId2 = BlackMagic.getMaterialId(leaves2);
	}
	
	private Odds odds;
	private byte trunkId;
	private byte leavesId1;
	private byte leavesId2;
	
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
