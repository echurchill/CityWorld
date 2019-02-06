package me.daddychurchill.CityWorld.Support;

import org.bukkit.util.noise.NoiseGenerator;

import me.daddychurchill.CityWorld.CityWorldGenerator;

public abstract class AbstractCachedYs extends AbstractYs {

	// extremes
	int segmentWidth;

	final double[][] blockYs = new double[width][width];

	AbstractCachedYs(CityWorldGenerator generator, int chunkX, int chunkZ) {

		// compute offset to start of chunk
		int originX = chunkX * width;
		int originZ = chunkZ * width;
		double sumHeight = 0.0;

		// calculate the Ys for this chunk
		for (int x = 0; x < width; x++) {
			for (int z = 0; z < width; z++) {

				// how high are we?
				blockYs[x][z] = generator.shapeProvider.findPerciseY(generator, originX + x, originZ + z);
				sumHeight = sumHeight + blockYs[x][z];

				// keep the tally going
				calcMinMax(x, NoiseGenerator.floor(blockYs[x][z]), z);
			}
		}

		//noinspection SuspiciousNameCombination
		calcState(generator, NoiseGenerator.floor(sumHeight), width * width);
	}

	public int getMaxYWithin(int x1, int x2, int z1, int z2) {
		assert (x1 >= 0 && x2 <= 15 && z1 >= 0 && z2 <= 15);
		int maxY = Integer.MIN_VALUE;
		for (int x = x1; x < x2; x++)
			for (int z = z1; z < z2; z++) {
				int y = getBlockY(x, z);
				if (y > maxY)
					maxY = y;
			}
		return maxY;
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

	@Override
	public int getMinHeight() {
		return minHeight;
	}

	@Override
	public int getMaxHeight() {
		return maxHeight;
	}

	@Override
	public int getAverageHeight() {
		return averageHeight;
	}

	public int getSegmentWidth() {
		return segmentWidth;
	}
}
