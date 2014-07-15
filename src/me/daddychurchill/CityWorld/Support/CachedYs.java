package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.WorldGenerator;

import org.bukkit.util.noise.NoiseGenerator;

public class CachedYs {
	
	private static int width = 16;

	// extremes
	public int averageHeight;
	public int minHeight = Integer.MAX_VALUE;
	public int minHeightX = 0;
	public int minHeightZ = 0;
	public int maxHeight = Integer.MIN_VALUE;
	public int maxHeightX = 0;
	public int maxHeightZ = 0;
	private double[][] blockYs= new double[width][width];
	
	public CachedYs(WorldGenerator generator, int chunkX, int chunkZ) {
		
		// total height
		int sumHeight = 0;
		
		// compute offset to start of chunk
		int originX = chunkX * AbstractChunk.chunksBlockWidth;
		int originZ = chunkZ * AbstractChunk.chunksBlockWidth;
		
		// calculate the Ys for this chunk
		for (int x = 0; x < width; x++) {
			for (int z = 0; z < width; z++) {

				// how high are we?
				blockYs[x][z] = generator.shapeProvider.findPerciseY(generator, originX + x, originZ + z);
				
				// keep the tally going
				sumHeight += blockYs[x][z];
				calcTally(blockYs[x][z], x, z);
			}
		}
		
		// what was the average height
		averageHeight = sumHeight / (width * width);
	}
	
	private void calcTally(double realY, int x, int z) {
		int y = NoiseGenerator.floor(realY);
		if (y < minHeight) {
			minHeight = y;
			minHeightX = x;
			minHeightZ = z;
		}
		if (y > maxHeight) {
			maxHeight = y;
			maxHeightX = x;
			maxHeightZ = z;
		}
	}
	
	public int getBlockY(int x, int z) {
		return NoiseGenerator.floor(blockYs[x][z]);
	}
	
	public double getPerciseY(int x, int z) {
		return blockYs[x][z];
	}
	
	public void lift(int h) {
		// total height
		int sumHeight = 0;
		
		// change the height
		for (int x = 0; x < width; x++) {
			for (int z = 0; z < width; z++) {
				blockYs[x][z] = blockYs[x][z] + h;
				
				// keep the tally going
				sumHeight += blockYs[x][z];
				calcTally(blockYs[x][z], x, z);
			}
		}
		
		// what was the average height
		averageHeight = sumHeight / (width * width);
	}
	
}
