package me.daddychurchill.CityWorld.Support;

import org.bukkit.Chunk;
import org.bukkit.block.Block;

import me.daddychurchill.CityWorld.CityWorldGenerator;

public final class RealBlocks extends SupportBlocks {
	private final Chunk chunk;

	public RealBlocks(CityWorldGenerator generator, Chunk aChunk) {
		super(generator);

		chunk = aChunk;
		sectionX = chunk.getX();
		sectionZ = chunk.getZ();
	}

	@Override
	public Block getActualBlock(int x, int y, int z) {
		return chunk.getBlock(x, y, z);
	}

	@Override
	public boolean isSurroundedByEmpty(int x, int y, int z) {
		return (x > 0 && x < 15 && z > 0 && z < 15)
				&& (isEmpty(x - 1, y, z) && isEmpty(x + 1, y, z) && isEmpty(x, y, z - 1) && isEmpty(x, y, z + 1));
	}

	@Override
	public boolean isByWater(int x, int y, int z) {
		return (x > 0 && x < 15 && z > 0 && z < 15)
				&& (isWater(x - 1, y, z) || isWater(x + 1, y, z) || isWater(x, y, z - 1) || isWater(x, y, z + 1));
	}

//	public static Material DOUBLE_ACACIA_SLAB = DoubleIt(Material.ACACIA_SLAB);
//	
//	private static Material DoubleIt(Material material) {
//		return Slab
//	}
}
