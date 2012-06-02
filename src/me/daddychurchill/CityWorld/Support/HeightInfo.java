package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.WorldGenerator;

public final class HeightInfo {
	
	public enum HeightState {DEEPSEA, SEA, BUILDING, LOWLAND, MIDLAND, HIGHLAND, PEAK};

	int count = 0;
	int summary = 0;
	int average = 0;
	int maximum = Integer.MIN_VALUE;
	int minimum = Integer.MAX_VALUE;
	public HeightState state;
	
	public final static HeightInfo getHeightsFaster(WorldGenerator generator, int blockX, int blockZ) {
		HeightInfo heights = new HeightInfo();
		
		heights.add(generator.findBlockY(blockX + 8, blockZ + 8)); // center
		heights.add(generator.findBlockY(blockX + 0, blockZ + 0)); // corners
		heights.add(generator.findBlockY(blockX + 15, blockZ + 0)); 
		heights.add(generator.findBlockY(blockX + 0, blockZ + 15)); 
		heights.add(generator.findBlockY(blockX + 15, blockZ + 15));
		
		heights.calcState(generator);
		return heights;
	}
	
	public final static HeightInfo getHeightsFast(WorldGenerator generator, int blockX, int blockZ) {
		HeightInfo heights = new HeightInfo();
		
		heights.add(generator.findBlockY(blockX + 8, blockZ + 8)); // center
		heights.add(generator.findBlockY(blockX + 0, blockZ + 0)); // corners
		heights.add(generator.findBlockY(blockX + 15, blockZ + 0)); 
		heights.add(generator.findBlockY(blockX + 0, blockZ + 15)); 
		heights.add(generator.findBlockY(blockX + 15, blockZ + 15));
		heights.add(generator.findBlockY(blockX + 0, blockZ + 8)); // edges 
		heights.add(generator.findBlockY(blockX + 15, blockZ + 8)); 
		heights.add(generator.findBlockY(blockX + 8, blockZ + 15)); 
		heights.add(generator.findBlockY(blockX + 8, blockZ + 15));
		
		heights.calcState(generator);
		return heights;
	}
	
	public final static HeightInfo getHeights(WorldGenerator generator, int blockX, int blockZ) {
		HeightInfo heights = new HeightInfo();
		
		for (int x = 0; x < SupportChunk.chunksBlockWidth; x++) {
			for (int z = 0; z < SupportChunk.chunksBlockWidth; z++) {
				heights.add(generator.findBlockY(blockX + x, blockZ + z));
			}
		}
		
		heights.calcState(generator);
		return heights;
	}
	
	public final static boolean isBuildableAt(WorldGenerator generator, int blockX, int blockZ) {
		return getHeightsFaster(generator, blockX, blockZ).state == HeightState.BUILDING;
	}
	
	public final static boolean isBuildableToNorth(WorldGenerator generator, SupportChunk chunk) {
		return isBuildableAt(generator, chunk.getOriginX(), chunk.getOriginZ() - chunk.width);
	}

	public final static boolean isBuildableToSouth(WorldGenerator generator, SupportChunk chunk) {
		return isBuildableAt(generator, chunk.getOriginX(), chunk.getOriginZ() + chunk.width);
	}

	public final static boolean isBuildableToWest(WorldGenerator generator, SupportChunk chunk) {
		return isBuildableAt(generator, chunk.getOriginX() - chunk.width, chunk.getOriginZ());
	}

	public final static boolean isBuildableToEast(WorldGenerator generator, SupportChunk chunk) {
		return isBuildableAt(generator, chunk.getOriginX() + chunk.width, chunk.getOriginZ());
	}
	
	private final void calcState(WorldGenerator generator) {
		average = summary / count;
		if (maximum <= generator.seaLevel / 2)
			state = HeightState.DEEPSEA;
		else if (maximum <= generator.seaLevel)
			state = HeightState.SEA;
		else if (maximum == generator.sidewalkLevel && minimum == generator.sidewalkLevel)
			state = HeightState.BUILDING;
		else {
			int delta = (generator.topLevel - generator.seaLevel) / 4;
			if (average <= generator.seaLevel + delta)
				state = HeightState.LOWLAND;
			else if (average <= generator.seaLevel + delta * 2)
				state = HeightState.MIDLAND;
			else if (average >= generator.topLevel - delta)
				state = HeightState.PEAK;
			else
				state = HeightState.HIGHLAND;
		}
	}
	
	public final int getRange() {
		return maximum - minimum;
	}
	
	public final boolean isSortaFlat() {
		return (maximum - minimum) < 8;
	}
	
	public final boolean isAbsolutelyFlat() {
		return maximum == minimum;
	}
	
	public final boolean isOnLevel(int value) {
		return value == maximum && value == minimum;
	}
	
	public final boolean isBuildable() {
		return state == HeightState.BUILDING;
	}
	
	public final boolean isSea() {
		return state == HeightState.DEEPSEA || state == HeightState.SEA;
	}
	
	public final void add(int value) {
		count++;
		summary += value;
		if (value < minimum) 
			minimum = value;
		if (value > maximum) 
			maximum = value;
	}
}
