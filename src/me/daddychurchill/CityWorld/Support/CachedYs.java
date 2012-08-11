package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.WorldGenerator;

import org.bukkit.util.noise.NoiseGenerator;

public class CachedYs {

	// extremes
	public int averageHeight;
	public int minHeight = Integer.MAX_VALUE;
	public int minHeightX = 0;
	public int minHeightZ = 0;
	public int maxHeight = Integer.MIN_VALUE;
	public int maxHeightX = 0;
	public int maxHeightZ = 0;
	private double[][] blockYs= new double[16][16];
	
	public CachedYs(WorldGenerator generator, SupportChunk chunk, int platX, int platZ) {
		
		// total height
		int sumHeight = 0;
		
		// compute offset to start of chunk
		int originX = chunk.getOriginX();
		int originZ = chunk.getOriginZ();
		
		// calculate the Ys for this chunk
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {

				// how high are we?
				blockYs[x][z] = generator.shapeProvider.findPerciseY(generator, originX + x, originZ + z);
				
				// keep the tally going
				int y = NoiseGenerator.floor(blockYs[x][z]);
				sumHeight += y;
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
		}
		
		// what was the average height
		averageHeight = sumHeight / (chunk.width * chunk.width);
	}
	
	public int getBlockY(int x, int z) {
		return NoiseGenerator.floor(blockYs[x][z]);
	}
	
	public double getPerciseY(int x, int z) {
		return blockYs[x][z];
	}
	
}
