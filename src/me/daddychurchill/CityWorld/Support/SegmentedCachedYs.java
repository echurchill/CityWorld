package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.CityWorldGenerator;

public class SegmentedCachedYs extends CachedYs {

	private int[][] segmentYs= new int[width][width];
	
	public SegmentedCachedYs(CityWorldGenerator generator, int chunkX, int chunkZ) {
		super(generator, chunkX, chunkZ);

		// lets get started?
		segmentWidth = calcSegmentWidth(generator.seaLevel);
		int currentSegment = 1;
		
		// total height
		int sumHeight = 0;
		
		// which segment are we doing?
		switch (segmentWidth) {
		case 2: // two by two
			for (int x = 0; x < width; x = x + 2) {
				for (int z = 0; z < width; z = z + 2) {
					sumHeight = flattenSegment(x, z, 2, 2, currentSegment);
					currentSegment++;
				}
			}
			break;
			
		case 4: // four by four
			for (int x = 0; x < width; x = x + 4) {
				for (int z = 0; z < width; z = z + 4) {
					sumHeight = flattenSegment(x, z, 4, 4, currentSegment);
					currentSegment++;
				}
			}
			break;
			
		case 8: // eight by eight
			for (int x = 0; x < width; x = x + 8) {
				for (int z = 0; z < width; z = z + 8) {
					sumHeight = flattenSegment(x, z, 8, 8, currentSegment);
					currentSegment++;
				}
			}
			break;
			
		case 16: // sixteen by sixteen
			sumHeight = flattenSegment(0, 0, 16, 16, currentSegment);
			break;
			
		default:// one by one
			for (int x = 0; x < width; x++) {
				for (int z = 0; z < width; z++) {
					segmentYs[x][z] = 0;
				}
			}
			
			// all done, no need to reaverage
			return;
		}

		// what was the average height
		averageHeight = sumHeight / (width * width);
	}

	@Override
	public int getSegment(int x, int z) {
		return segmentYs[x][z];
	}
	
	private int calcSegmentWidth(int surfaceY) {
		if (averageHeight > surfaceY) {
			int heightSegment = (averageHeight - surfaceY) / 8;
			switch (heightSegment) {
			case 0:
				return 1;
			case 1:
				return 2;
			case 2:
			case 3:
				return 4;
			case 4:
			case 5:
			case 6:
				return 8;
			default:
				return 16;
			}
		} else
			return 1;
	}
	
	private int flattenSegment(int x1, int z1, int xw, int zw, int currentSegment) {
		int sumHeight = 0;
		
		// find the topmost one
		double atY = average(blockYs[x1][z1], 
							 blockYs[x1 + xw - 1][z1],
							 blockYs[x1][z1 + zw - 1],
							 blockYs[x1 + xw - 1][z1 + zw - 1]);
		
		// make the segment equal that
		for (int x = x1; x < x1 + xw; x++) {
			for (int z = z1; z < z1 + zw; z++) {
				blockYs[x][z] = atY;
				sumHeight += atY;
				segmentYs[x][z] = currentSegment;
			}
		}
		
		return sumHeight;
	}
	
	private double average(double ... values) {
		double result = 0;
		for (double value: values)
			result += value;
		return result / values.length;
	}
	
}
