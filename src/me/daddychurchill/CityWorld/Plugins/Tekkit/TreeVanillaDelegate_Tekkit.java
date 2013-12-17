package me.daddychurchill.CityWorld.Plugins.Tekkit;

import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SupportChunk;

import org.bukkit.World;
import forge.bukkit.BukkitForgeHooks.ForgeBlockChangeDelegate;

// based on work from brikeener 
// commit: http://code.google.com/p/cityworld-tekkit/source/detail?spec=svn148c2e29ecc3d9b8ba7c63927da30e790ff2e6c0&r=148c2e29ecc3d9b8ba7c63927da30e790ff2e6c0
public class TreeVanillaDelegate_Tekkit implements ForgeBlockChangeDelegate {

	protected World world;
	protected RealChunk chunk;
	
	public TreeVanillaDelegate_Tekkit(RealChunk chunk) {
		
		this.chunk = chunk;
		this.world = chunk.world;
	}
	
	public net.minecraft.server.World unwrap() {
		return ((org.bukkit.craftbukkit.CraftWorld)this.world).getHandle();
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
		return SupportChunk.setBlockType(world.getBlockAt(x, y, z), id, 0, false);
	}

	@Override
	public boolean setRawTypeIdAndData(int x, int y, int z, int id, int data) {
		return SupportChunk.setBlockType(world.getBlockAt(x, y, z), id, 0, false);
	}

	@Override
	public boolean setTypeId(int x, int y, int z, int id) {
		return SupportChunk.setBlockType(world.getBlockAt(x, y, z), id, 0, false);
	}

	@Override
	public boolean setTypeIdAndData(int x, int y, int z, int id, int data) {
		return SupportChunk.setBlockType(world.getBlockAt(x, y, z), id, data, false);
	}
}
