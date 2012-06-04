package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.HeightInfo;
import me.daddychurchill.CityWorld.Support.RealChunk;

public abstract class PlatLot {
	
	// extremes
	private boolean extremeComputed = false;
	protected int averageHeight;
	protected int minHeight = Integer.MAX_VALUE;
	protected int minHeightX = 0;
	protected int minHeightZ = 0;
	protected int maxHeight = Integer.MIN_VALUE;
	protected int maxHeightX = 0;
	protected int maxHeightZ = 0;
	
	public enum LotStyle {NATURE, STRUCTURE, ROAD, ROUNDABOUT};
	public LotStyle style;
	
	public PlatLot(Random random) {
		super();
		style = LotStyle.NATURE;
	}
	
	protected final static byte airId = (byte) Material.AIR.getId();
	protected final static byte snowId = (byte) Material.SNOW_BLOCK.getId();
	protected final static byte grassId = (byte) Material.GRASS.getId();
	protected final static byte dirtId = (byte) Material.DIRT.getId();
	protected final static byte stoneId = (byte) Material.STONE.getId();
	protected final static byte waterId = (byte) Material.STATIONARY_WATER.getId();
	protected final static byte sandId = (byte) Material.SAND.getId();
	protected final static byte sandstoneId = (byte) Material.SANDSTONE.getId();
	protected final static byte bedrockId = (byte) Material.BEDROCK.getId();
	protected final static byte lavaId = (byte) Material.LAVA.getId();
	protected final static byte fenceId = (byte) Material.FENCE.getId();

	public abstract long getConnectedKey();
	public abstract boolean makeConnected(Random random, PlatLot relative);
	public abstract boolean isConnectable(PlatLot relative);
	public abstract boolean isIsolatedLot(Random random, int oddsOfIsolation);
	public abstract boolean isConnected(PlatLot relative);
	
	public void generateChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, ContextData context, int platX, int platZ) {
		
		// total height
		int sumHeight = 0;
		
		// compute offset to start of chunk
		int originX = chunk.getOriginX();
		int originZ = chunk.getOriginZ();
		
		// surface caves?
		boolean surfaceCaves = generator.isSurfaceCaveAt(chunk.chunkX, chunk.chunkZ);
		
		// shape the world
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {

				// how high are we?
				int y = generator.findBlockY(originX + x, originZ + z);
				
				// keep the tally going
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

				// make the base
				chunk.setBlock(x, 0, z, bedrockId);

				// buildable?
				if (style == LotStyle.STRUCTURE || style == LotStyle.ROUNDABOUT) {
					generateCrust(generator, chunk, x, z, stoneId, generator.sidewalkLevel - 2, dirtId, generator.sidewalkLevel, dirtId, false);
					biomes.setBiome(x, z, Biome.JUNGLE);
					
				// possibly buildable?
				} else if (y == generator.sidewalkLevel) {
					generateCrust(generator, chunk, x, z, stoneId, y - 3, dirtId, y, grassId, false);
					biomes.setBiome(x, z, Biome.PLAINS);
				
				// won't likely have a building
				} else {

					// on the beach
					if (y == generator.seaLevel) {
						generateCrust(generator, chunk, x, z, stoneId, y - 2, sandId, y, sandId, false);
						biomes.setBiome(x, z, Biome.BEACH);

						// we are in the water!
					} else if (y < generator.seaLevel) {
						generateCrust(generator, chunk, x, z, stoneId, y - 2, sandstoneId, y, sandId, generator.seaLevel, waterId, surfaceCaves);
						biomes.setBiome(x, z, Biome.OCEAN);

						// we are in the mountains
					} else {

						// regular trees only
						if (y < generator.treeLevel) {
							generateCrust(generator, chunk, x, z, stoneId, y - 3, dirtId, y, grassId, false);
							biomes.setBiome(x, z, Biome.FOREST_HILLS);

						// regular trees and some evergreen trees
						} else if (y < generator.evergreenLevel) {
							generateCrust(generator, chunk, x, z, stoneId, y - 2, dirtId, y, grassId, surfaceCaves);
							biomes.setBiome(x, z, Biome.EXTREME_HILLS);

						// evergreen and some of fallen snow
						} else if (y < generator.snowLevel) {
							generateCrust(generator, chunk, x, z, stoneId, y - 1, dirtId, y, grassId, surfaceCaves);
							biomes.setBiome(x, z, Biome.ICE_MOUNTAINS);
							
						// only snow up here!
						} else {
							generateCrust(generator, chunk, x, z, stoneId, y - 1, stoneId, y, snowId, surfaceCaves);
							biomes.setBiome(x, z, Biome.ICE_PLAINS);
						}
					}
				}
			}
		}
		
		// what was the average height
		averageHeight = sumHeight / (chunk.width * chunk.width);
		extremeComputed = true;
	}

	public void generateBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, ContextData context, int platX, int platZ) {
		
		// have we done this yet?
		if (!extremeComputed) {
			HeightInfo heights = HeightInfo.getHeights(generator, chunk.worldX, chunk.worldZ);
			averageHeight = heights.averageHeight;
			minHeight = heights.minHeight;
			minHeightX = heights.minHeightX;
			minHeightZ = heights.minHeightZ;
			maxHeight = heights.maxHeight;
			maxHeightX = heights.maxHeightX;
			maxHeightZ = heights.maxHeightZ;
			extremeComputed = true;
		}
		
		//TODO additional natural sub-terrain structures, if any
	}
	
	private void generateCrust(WorldGenerator generator, ByteChunk byteChunk, int x, int z, byte baseId,
			int baseY, byte substrateId, int substrateY, byte surfaceId,
			boolean surfaceCaves) {

		// compute the world block coordinates
		int blockX = byteChunk.chunkX * byteChunk.width + x;
		int blockZ = byteChunk.chunkZ * byteChunk.width + z;

		// stony bits
		for (int y = 1; y < baseY; y++)
			if (generator.notACave(blockX, y, blockZ))
//				byteChunk.setBlock(x, y, z, baseId);
				byteChunk.setBlock(x, y, z, generator.getOre(byteChunk, blockX, y, blockZ, baseId));

		// aggregate bits
		for (int y = baseY; y < substrateY; y++)
			if (!surfaceCaves || generator.notACave(blockX, y, blockZ))
				byteChunk.setBlock(x, y, z, substrateId);

		// icing for the cake
		if (!surfaceCaves || generator.notACave(blockX, substrateY, blockZ))
			byteChunk.setBlock(x, substrateY, z, surfaceId);

	}

	private void generateCrust(WorldGenerator generator, ByteChunk byteChunk, int x, int z, byte baseId,
			int baseY, byte substrateId, int substrateY, byte surfaceId,
			int coverY, byte coverId, boolean surfaceCaves) {

		// a little crust please?
		generateCrust(generator, byteChunk, x, z, baseId, baseY, substrateId, substrateY, surfaceId, surfaceCaves);

		// cover it up
		for (int y = substrateY + 1; y <= coverY; y++)
			byteChunk.setBlock(x, y, z, coverId);
	}

	//TODO move this logic to SurroundingLots, add to it the ability to produce SurroundingHeights and SurroundingDepths
	public PlatLot[][] getNeighborPlatLots(PlatMap platmap, int platX, int platZ, boolean onlyConnectedNeighbors) {
		PlatLot[][] miniPlatMap = new PlatLot[3][3];
		
		// populate the results
		for (int x = 0; x < 3; x++) {
			for (int z = 0; z < 3; z++) {
				
				// which platchunk are we looking at?
				int atX = platX + x - 1;
				int atZ = platZ + z - 1;

				// is it in bounds?
				if (!(atX < 0 || atX > PlatMap.Width - 1 || atZ < 0 || atZ > PlatMap.Width - 1)) {
					PlatLot relative = platmap.getLot(atX, atZ);
					
					if (!onlyConnectedNeighbors || isConnected(relative)) {
						miniPlatMap[x][z] = relative;
					}
				}
			}
		}
		
		return miniPlatMap;
	}
}
