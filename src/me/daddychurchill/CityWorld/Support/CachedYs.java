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
	public int segmentWidth = 1;
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
	
	public void flattenSegments(int surfaceY) {
		if (averageHeight > surfaceY) {
			segmentWidth = calcSegmentWidth(surfaceY);
			
			// which segment are we doing?
			switch (segmentWidth) {
			case 2: // two by two
				for (int x = 0; x < width; x = x + 2) {
					for (int z = 0; z < width; z = z + 2) {
						flattenSegment(x, z, 2, 2);
					}
				}
				break;
			case 4: // four by four
				for (int x = 0; x < width; x = x + 4) {
					for (int z = 0; z < width; z = z + 4) {
						flattenSegment(x, z, 4, 4);
					}
				}
				break;
			case 8: // eight by eight
				for (int x = 0; x < width; x = x + 8) {
					for (int z = 0; z < width; z = z + 8) {
						flattenSegment(x, z, 8, 8);
					}
				}
				break;
			case 16: // sixteen by sixteen
				flattenSegment(0, 0, 16, 16);
				break;
			default:// one by one
				
				// nothing to do here.. move along
				break;
			}
		}
	}
	
	private int calcSegmentWidth(int surfaceY) {
		int heightSegment = (averageHeight - surfaceY) / 8;
		switch (heightSegment) {
		case 0:
			return 1;
		case 1:
		case 2:
			return 2;
		case 3:
		case 4:
			return 4;
		case 5:
		case 6:
			return 8;
		default:
			return 16;
		}
	}
	
	private void flattenSegment(int x1, int z1, int xw, int zw) {
		
		// find the topmost one
		double atY = average(blockYs[x1][z1], 
							blockYs[x1 + xw - 1][z1],
							blockYs[x1][z1 + zw - 1],
							blockYs[x1 + xw - 1][z1 + zw - 1]);
		
		// make the segment equal that
		for (int x = x1; x < x1 + xw; x++) {
			for (int z = z1; z < z1 + zw; z++) {
				blockYs[x][z] = atY;
			}
		}
	}
	
//	private double max(double ... values) {
//		double result = 0;
//		for (double value: values)
//			if (result < value)
//				result = value;
//		return result;
//	}
	
	private double average(double ... values) {
		double result = 0;
		for (double value: values)
			result += value;
		return result / values.length;
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
