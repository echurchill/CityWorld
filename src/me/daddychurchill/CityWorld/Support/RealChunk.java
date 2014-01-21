package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.WorldGenerator;

import org.bukkit.Chunk;
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

	@Override
	public boolean isSurroundedByEmpty(int x, int y, int z) {
		return (x > 0 && x < 15 && z > 0 && z < 15) && 
			   (isEmpty(x - 1, y, z) && 
				isEmpty(x + 1, y, z) &&
				isEmpty(x, y, z - 1) && 
				isEmpty(x, y, z + 1));
	}

	@Override
	public boolean isSurroundedByWater(int x, int y, int z) {
		return (x > 0 && x < 15 && z > 0 && z < 15) && 
			   (isWater(x - 1, y, z) && 
				isWater(x + 1, y, z) &&
				isWater(x, y, z - 1) && 
				isWater(x, y, z + 1));
	}
}
