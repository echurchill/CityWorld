package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.WorldGenerator;

public final class HeightInfo {
	
	public enum HeightState {DEEPSEA, SEA, BUILDING, LOWLAND, MIDLAND, HIGHLAND, PEAK};
	public HeightState state;

	private int count = 0;
	private int sumHeight = 0;
	
	public int averageHeight = 0;

	public int minHeight = Integer.MAX_VALUE;
	public int minHeightX = 0;
	public int minHeightZ = 0;
	public int maxHeight = Integer.MIN_VALUE;
	public int maxHeightX = 0;
	public int maxHeightZ = 0;
	
	public final static HeightInfo getHeightsFaster(WorldGenerator generator, int blockX, int blockZ) {
		HeightInfo heights = new HeightInfo();
		
		heights.add(generator, blockX + 8, blockZ + 8); // center
		heights.add(generator, blockX + 0, blockZ + 0); // corners
		heights.add(generator, blockX + 15, blockZ + 0); 
		heights.add(generator, blockX + 0, blockZ + 15); 
		heights.add(generator, blockX + 15, blockZ + 15);
		
		heights.calcState(generator);
		return heights;
	}
	
	public final static HeightInfo getHeightsFast(WorldGenerator generator, int blockX, int blockZ) {
		HeightInfo heights = new HeightInfo();
		
		heights.add(generator, blockX + 8, blockZ + 8); // center
		heights.add(generator, blockX + 0, blockZ + 0); // corners
		heights.add(generator, blockX + 15, blockZ + 0); 
		heights.add(generator, blockX + 0, blockZ + 15); 
		heights.add(generator, blockX + 15, blockZ + 15);
		heights.add(generator, blockX + 0, blockZ + 8); // edges 
		heights.add(generator, blockX + 15, blockZ + 8); 
		heights.add(generator, blockX + 8, blockZ + 15); 
		heights.add(generator, blockX + 8, blockZ + 15);
		
		heights.calcState(generator);
		return heights;
	}
	
	public final static boolean isBuildableAt(WorldGenerator generator, int blockX, int blockZ) {
		return getHeightsFaster(generator, blockX, blockZ).state == HeightState.BUILDING;
	}
	
	public final static boolean isBuildableToNorth(WorldGenerator generator, AbstractChunk chunk) {
		return isBuildableAt(generator, chunk.getOriginX(), chunk.getOriginZ() - chunk.width);
	}

	public final static boolean isBuildableToSouth(WorldGenerator generator, AbstractChunk chunk) {
		return isBuildableAt(generator, chunk.getOriginX(), chunk.getOriginZ() + chunk.width);
	}

	public final static boolean isBuildableToWest(WorldGenerator generator, AbstractChunk chunk) {
		return isBuildableAt(generator, chunk.getOriginX() - chunk.width, chunk.getOriginZ());
	}

	public final static boolean isBuildableToEast(WorldGenerator generator, AbstractChunk chunk) {
		return isBuildableAt(generator, chunk.getOriginX() + chunk.width, chunk.getOriginZ());
	}
	
	private final void calcState(WorldGenerator generator) {
		averageHeight = sumHeight / count;
		if (maxHeight <= generator.deepseaLevel)
			state = HeightState.DEEPSEA;
		else if (maxHeight <= generator.seaLevel)
			state = HeightState.SEA;
		else if (maxHeight == generator.structureLevel && minHeight == generator.structureLevel)
			state = HeightState.BUILDING;
		else {
			if (averageHeight <= generator.treeLevel)
				state = HeightState.LOWLAND;
			else if (averageHeight <= generator.evergreenLevel)
				state = HeightState.MIDLAND;
			else if (averageHeight >= generator.snowLevel)
				state = HeightState.PEAK;
			else
				state = HeightState.HIGHLAND;
		}
	}
	
	public final int getRange() {
		return maxHeight - minHeight;
	}
	
	public final boolean isSortaFlat() {
		return (maxHeight - minHeight) < 8;
	}
	
	public final boolean isAbsolutelyFlat() {
		return maxHeight == minHeight;
	}
	
	public final boolean isOnLevel(int value) {
		return value == maxHeight && value == minHeight;
	}
	
	public final boolean isBuildable() {
		return state == HeightState.BUILDING;
	}
	
	public final boolean isSea() {
		return state == HeightState.DEEPSEA || state == HeightState.SEA;
	}
	
	public final void add(WorldGenerator generator, int x, int z) {
		// we will need to get the Y the hard way
		int value = generator.getFarBlockY(x, z);
		count++;
		sumHeight += value;
		if (value < minHeight) {
			minHeight = value;
			minHeightX = x;
			minHeightZ = z;
		}
		if (value > maxHeight) {
			maxHeight = value;
			maxHeightX = x;
			maxHeightZ = z;
		}
	}
}
