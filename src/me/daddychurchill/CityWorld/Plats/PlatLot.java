package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;

public abstract class PlatLot {
	
	protected int averageHeight;
	protected int minHeight = 1024;
	protected int maxHeight = 0;
	
	public enum LotStyle {NATURE, STRUCTURE, ROAD, ROUNDABOUT};
	public LotStyle style;
	
	public PlatLot(Random random) {
		super();
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
				averageHeight = averageHeight + y;
				minHeight = Math.min(minHeight, y);
				maxHeight = Math.max(maxHeight, y);

				// make the base
				chunk.setBlock(x, 0, z, bedrockId);

				// buildable?
				if (style == LotStyle.STRUCTURE || style == LotStyle.ROUNDABOUT) {
					generateCrust(generator, chunk, x, z, stoneId, chunk.sidewalklevel - 2, dirtId, chunk.sidewalklevel, dirtId, false);
					biomes.setBiome(x, z, Biome.JUNGLE);
					
				// possibly buildable?
				} else if (y == chunk.sidewalklevel) {
					generateCrust(generator, chunk, x, z, stoneId, y - 3, dirtId, y, grassId, false);
					biomes.setBiome(x, z, Biome.PLAINS);
				
				// won't likely have a building
				} else {

					// on the beach
					if (y == chunk.sealevel) {
						generateCrust(generator, chunk, x, z, stoneId, y - 2, sandId, y, sandId, false);
						biomes.setBiome(x, z, Biome.BEACH);

						// we are in the water!
					} else if (y < chunk.sealevel) {
						generateCrust(generator, chunk, x, z, stoneId, y - 2, sandstoneId, y, sandId, chunk.sealevel, waterId, surfaceCaves);
						biomes.setBiome(x, z, Biome.OCEAN);

						// we are in the mountains
					} else {

						// regular trees only
						if (y < chunk.treelevel) {
							generateCrust(generator, chunk, x, z, stoneId, y - 3, dirtId, y, grassId, false);
							biomes.setBiome(x, z, Biome.FOREST_HILLS);

						// regular trees and some evergreen trees
						} else if (y < chunk.evergreenlevel) {
							generateCrust(generator, chunk, x, z, stoneId, y - 2, dirtId, y, grassId, surfaceCaves);
							biomes.setBiome(x, z, Biome.EXTREME_HILLS);

						// evergreen and some of fallen snow
						} else if (y < chunk.snowlevel) {
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
		averageHeight = averageHeight / (chunk.width * chunk.width);
	}
	
	//TODO make surfaceCaves dependent on a random factor per chunk rather than the snow line

	private void generateCrust(WorldGenerator generator, ByteChunk byteChunk, int x, int z, 
			byte baseId, int baseY, byte substrateId, int substrateY, byte surfaceId,
			boolean surfaceCaves) {

		// compute the world block coordinates
		int blockX = byteChunk.getBlockX(x);
		int blockZ = byteChunk.getBlockZ(z);

		// stony bits
		for (int y = 1; y < baseY; y++)
			if (generator.notACave(blockX, y, blockZ))
				byteChunk.setBlock(x, y, z, baseId);

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

	public void generateBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, ContextData context, int platX, int platZ) {
		//TODO add minerals and additional natural sub-terrain structures, if any
	}
	
//	private void generateCrust(WorldGenerator generator, ByteChunk byteChunk, int x, int z, byte baseId,
//			int baseY, byte substrateId, int substrateY, byte surfaceId,
//			boolean surfaceCaves) {
//
//		// compute the world block coordinates
//		int blockX = byteChunk.chunkX * byteChunk.width + x;
//		int blockZ = byteChunk.chunkZ * byteChunk.width + z;
//
//		// stony bits
//		for (int y = 1; y < baseY; y++)
//			if (notACave(generator, blockX, y, blockZ))
//				byteChunk.setBlock(x, y, z, getOre(generator, byteChunk, blockX, y, blockZ, baseId));
//
//		// aggregate bits
//		for (int y = baseY; y < substrateY; y++)
//			if (!surfaceCaves || notACave(generator, blockX, y, blockZ))
//				byteChunk.setBlock(x, y, z, substrateId);
//
//		// icing for the cake
//		if (!surfaceCaves || notACave(generator, blockX, substrateY, blockZ))
//			byteChunk.setBlock(x, substrateY, z, surfaceId);
//
//	}
//
//	private void generateCrust(WorldGenerator generator, ByteChunk byteChunk, int x, int z, byte baseId,
//			int baseY, byte substrateId, int substrateY, byte surfaceId,
//			int coverY, byte coverId, boolean surfaceCaves) {
//
//		// a little crust please?
//		generateCrust(generator, byteChunk, x, z, baseId, baseY, substrateId, substrateY, surfaceId, surfaceCaves);
//
//		// cover it up
//		for (int y = substrateY + 1; y <= coverY; y++)
//			byteChunk.setBlock(x, y, z, coverId);
//	}
//

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
					PlatLot relative = platmap.platLots[atX][atZ];
					
					if (!onlyConnectedNeighbors || isConnected(relative)) {
						miniPlatMap[x][z] = relative;
					}
				}
			}
		}
		
		return miniPlatMap;
	}
}
