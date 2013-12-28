package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.WorldGenerator;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

public final class RealChunk extends SupportChunk {
	private Chunk chunk;

	public RealChunk(WorldGenerator generator, Chunk aChunk) {
		super(generator);
		
		chunk = aChunk;
		chunkX = chunk.getX();
		chunkZ = chunk.getZ();
	}
	
	@Override
	public Block getActualBlock(int x, int y, int z) {
		return chunk.getBlock(x, y, z);
	}

	public boolean isSurroundedByEmpty(int x, int y, int z) {
		return (x > 0 && x < 15 && z > 0 && z < 15) && 
			   (getActualBlock(x - 1, y, z).isEmpty() && 
				getActualBlock(x + 1, y, z).isEmpty() &&
				getActualBlock(x, y, z - 1).isEmpty() && 
				getActualBlock(x, y, z + 1).isEmpty());
	}
	
	@Deprecated
	public void setBlocks(int x, int y1, int y2, int z, Material primary, Material secondary, MaterialFactory maker) {
//		setBlocks(x, y1, y2, z, BlackMagic.getMaterialId(primary), BlackMagic.getMaterialId(secondary), maker);
	}

	@Deprecated
	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material primary, Material secondary, MaterialFactory maker) {
//		setBlocks(x1, x2, y1, y2, z1, z2, BlackMagic.getMaterialId(primary), BlackMagic.getMaterialId(secondary), maker);
	}

	
}
