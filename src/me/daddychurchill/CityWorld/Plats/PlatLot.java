package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.CityWorldChunkGenerator;
import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.Context.PlatMapContext;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public abstract class PlatLot {
	
	public short[][] heightmap;

	public PlatLot(Random random) {
		super();
		
		heightmap = new short[SupportChunk.chunksBlockWidth][SupportChunk.chunksBlockWidth];
	}
	
	private final static byte snowId = (byte) Material.SNOW_BLOCK.getId();
	private final static byte grassId = (byte) Material.GRASS.getId();
	private final static byte dirtId = (byte) Material.DIRT.getId();
	private final static byte stoneId = (byte) Material.STONE.getId();
	private final static byte waterId = (byte) Material.STATIONARY_WATER.getId();
	private final static byte sandId = (byte) Material.SAND.getId();
	private final static byte sandstoneId = (byte) Material.SANDSTONE.getId();
	private final static byte bedrockId = (byte) Material.BEDROCK.getId();

	public abstract long getConnectedKey();
	public abstract boolean makeConnected(Random random, PlatLot relative);
	public abstract boolean isConnectable(PlatLot relative);
	public abstract boolean isIsolatedLot(Random random, int oddsOfIsolation);
	public abstract boolean isConnected(PlatLot relative);
	
	public abstract void generateBlocks(CityWorldChunkGenerator generator, PlatMap platmap, RealChunk chunk, PlatMapContext context, int platX, int platZ);
	
	public void generateChunk(CityWorldChunkGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, PlatMapContext context, int platX, int platZ) {
		
		// compute offset to start of chunk
		int blockX = chunk.chunkX * chunk.width;
		int blockZ = chunk.chunkZ * chunk.width;
		
		// shape the world
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {

				// how high are we?
				int y = generator.findBlockY(blockX + x, blockZ + z);

				// make the base
				chunk.setBlock(x, 0, z, bedrockId);

				// buildable?
				if (y == chunk.streetlevel) {
					generateCrust(generator, chunk, x, z, stoneId, y - 2, dirtId, y, grassId, false);
					biomes.setBiome(x, z, Biome.PLAINS);
				} else {

					// on the beach
					if (y == chunk.sealevel) {
						generateCrust(generator, chunk, x, z, stoneId, y - 2, sandId, y, sandId, false);
						biomes.setBiome(x, z, Biome.BEACH);

						// we are in the water!
					} else if (y < chunk.sealevel) {
						generateCrust(generator, chunk, x, z, stoneId, y - 2, sandstoneId, y, sandId, chunk.sealevel, waterId, true);
						biomes.setBiome(x, z, Biome.OCEAN);

						// we are in the mountains
					} else {

						// what treeline are we at?
						if (y < chunk.treelevel - generator.fudgeVerticalScale) {
							generateCrust(generator, chunk, x, z, stoneId, y - 2, dirtId, y, grassId, false);
							biomes.setBiome(x, z, Biome.FOREST_HILLS);

						} else if (y < chunk.snowlevel - generator.fudgeVerticalScale) {
							generateCrust(generator, chunk, x, z, stoneId, y - 2, stoneId, y, grassId, true);
							biomes.setBiome(x, z, Biome.EXTREME_HILLS);

						} else {
							generateCrust(generator, chunk, x, z, stoneId, y - 1, grassId, y, snowId, true);
							biomes.setBiome(x, z, Biome.ICE_MOUNTAINS);
						}
					}
				}
			}
		}
	}
	
	private void generateCrust(CityWorldChunkGenerator generator, ByteChunk byteChunk, int x, int z, byte baseId,
			int baseY, byte substrateId, int substrateY, byte surfaceId,
			boolean surfaceCaves) {

		// compute the world block coordinates
		int blockX = byteChunk.chunkX * byteChunk.width + x;
		int blockZ = byteChunk.chunkZ * byteChunk.width + z;

		// stony bits
		for (int y = 1; y < baseY; y++)
			if (notACave(generator, blockX, y, blockZ))
				byteChunk.setBlock(x, y, z, getOre(generator, byteChunk, blockX, y, blockZ, baseId));

		// aggregate bits
		for (int y = baseY; y < substrateY; y++)
			if (!surfaceCaves || notACave(generator, blockX, y, blockZ))
				byteChunk.setBlock(x, y, z, substrateId);

		// icing for the cake
		if (!surfaceCaves || notACave(generator, blockX, substrateY, blockZ))
			byteChunk.setBlock(x, substrateY, z, surfaceId);

	}

	private void generateCrust(CityWorldChunkGenerator generator, ByteChunk byteChunk, int x, int z, byte baseId,
			int baseY, byte substrateId, int substrateY, byte surfaceId,
			int coverY, byte coverId, boolean surfaceCaves) {

		// a little crust please?
		generateCrust(generator, byteChunk, x, z, baseId, baseY, substrateId, substrateY, surfaceId, surfaceCaves);

		// cover it up
		for (int y = substrateY + 1; y <= coverY; y++)
			byteChunk.setBlock(x, y, z, coverId);
	}

	private boolean notACave(CityWorldChunkGenerator generator, int blockX, int blockY, int blockZ) {

		// cave or not?
		double cave = generator.caveShape.noise(blockX * generator.caveScale, blockY * generator.caveScaleY, blockZ * generator.caveScale);
		return !(cave > generator.caveThreshold || cave < -generator.caveThreshold);
	}

	private byte getOre(CityWorldChunkGenerator generator, ByteChunk byteChunk, int blockX, int blockY,
			int blockZ, byte defaultId) {

		// ore or not?
		double ore = generator.oreShape.noise(blockX * generator.oreScale, blockY * generator.oreScaleY, blockZ * generator.oreScale);
		if (ore > generator.oreThreshold || ore < -generator.oreThreshold)
			return byteChunk.getOre(blockY);
		else
			return defaultId;
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
