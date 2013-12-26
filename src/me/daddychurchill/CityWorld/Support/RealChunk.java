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
}
