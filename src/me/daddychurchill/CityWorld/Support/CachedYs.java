package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.CityWorldGenerator;

import org.bukkit.util.noise.NoiseGenerator;

public class CachedYs {
	
	// extremes
	public int minHeight = Integer.MAX_VALUE;
	private int minHeightX = 0;
	private int minHeightZ = 0;
	public int maxHeight = Integer.MIN_VALUE;
	private int maxHeightX = 0;
	private int maxHeightZ = 0;
	public int averageHeight;
	public int segmentWidth;
	
	protected final static int width = AbstractBlocks.sectionBlockWidth;
	protected double[][] blockYs = new double[width][width];
	
	public CachedYs(CityWorldGenerator generator, int chunkX, int chunkZ) {
		
		// total height
		int sumHeight = 0;
		
		// compute offset to start of chunk
		int originX = chunkX * width;
		int originZ = chunkZ * width;
		
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
	
	public Point getHighPoint() {
		return new Point(maxHeightX, maxHeight, maxHeightZ);
	}
	
	public Point getLowPoint() {
		return new Point(minHeightX, minHeight, minHeightZ);
	}
	
	public int getSegment(int x, int z) {
		return 0;
	}
	
//	public void lift(int h) {
//		// total height
//		int sumHeight = 0;
//		
//		// change the height
//		for (int x = 0; x < width; x++) {
//			for (int z = 0; z < width; z++) {
//				blockYs[x][z] = blockYs[x][z] + h;
//				
//				// keep the tally going
//				sumHeight += blockYs[x][z];
//				calcTally(blockYs[x][z], x, z);
//			}
//		}
//		
//		// what was the average height
//		averageHeight = sumHeight / (width * width);
//	}
	
//	public void draw(AbstractBlocks chunk) {
//		for (int x = 0; x < width; x++) {
//			for (int z = 0; z < width; z++) {
//				chunk.setBlock(x, getBlockY(x, z), z, Material.GOLD_BLOCK);
//			}
//		}
//		chunk.setBlock(minHeightX, minHeight, minHeightZ, Material.DIAMOND_BLOCK);
//		chunk.setBlock(maxHeightX, maxHeight, maxHeightZ, Material.LAPIS_BLOCK);
//		chunk.setBlock(0, averageHeight - 1, 0, Material.COAL_BLOCK);
//		chunk.setBlock(0, averageHeight + 1, 0, Material.COAL_BLOCK);
//	}
//	
//	public void report(CityWorldGenerator generator, String prefix) {
//		generator.reportFormatted("%s minHeight = %s maxHeight = %s averageHeight = %s streetLevel = %s", 
//				prefix, minHeight, maxHeight, averageHeight, generator.streetLevel);
//	}
//
//	public void reportMatrix(CityWorldGenerator generator, String prefix) {
//		for (int z = 0; z < width; z++) {
//			StringBuilder line = new StringBuilder(String.format("%s [%2s] =", prefix, z));
//			for (int x = 0; x < width; x++) {
//				line.append(String.format(" %4s", getBlockY(x, z)));
//			}
//			generator.reportMessage(line.toString());
//		}
//	}
}
