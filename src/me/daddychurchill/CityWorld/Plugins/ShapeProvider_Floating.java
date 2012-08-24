package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Maps.FloatingMap;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.PlatLot.LotStyle;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class ShapeProvider_Floating extends ShapeProvider_Normal {

	public ShapeProvider_Floating(WorldGenerator generator, Random random) {
		super(generator, random);
		long seed = generator.getWorldSeed();
		
		terrainShape = new SimplexNoiseGenerator(seed + 3);
		noiseShape = new SimplexNoiseGenerator(seed + 4);
		
		//streetLevel = height / 8 * 3;
		constructMin = seaLevel + landRange + 32;
		constructRange = height - 32 - constructMin; 
	}

	@Override
	public PlatMap createPlatMap(WorldGenerator generator, SupportChunk cornerChunk, int platX, int platZ) {
		return new FloatingMap(generator, cornerChunk, platX, platZ);
	}
	
	//private int streetLevel;
	private SimplexNoiseGenerator terrainShape;
	private SimplexNoiseGenerator noiseShape;
	
	public final static int seaBed = 8;
	public final static int gapRange = 8;
	public final static int landRange = 16;
	public final static int seaLevel = seaBed + gapRange;
	public final static int deepSeaLevel = seaLevel - gapRange / 2;
	
	public final static int noiseRange = seaBed / 2;
	public final static int midRange = (gapRange + landRange) / 2;
	public final static int midPoint = seaBed + midRange;
	public final static int snowPoint = midPoint + midRange - 2;
	
	private final static double terrainScale = 1.0 / 281.0;
	private final static double noiseScale = 1.0 / 23.0;
	
	@Override
	public int getStreetLevel() {
		return super.getStreetLevel() / 2 * 3;
	}

	@Override
	public int getStructureLevel() {
		return super.getStreetLevel();
	}

	@Override
	public int findGroundY(WorldGenerator generator, int blockX, int blockZ) {
		
		// calculator the way down there bits
		double terrainAt = terrainShape.noise(blockX * terrainScale, blockZ * terrainScale) * midRange;
		double noiseAt = noiseShape.noise(blockX * noiseScale, blockZ * noiseScale) * noiseRange;
		return NoiseGenerator.floor(terrainAt + noiseAt) + midPoint;
	}
	
	@Override
	public void preGenerateChunk(WorldGenerator generator, PlatLot lot, ByteChunk chunk, BiomeGrid biomes, CachedYs blockYs) {
		Biome resultBiome = lot.getChunkBiome();
		OreProvider ores = generator.oreProvider;
		
		// shape the world
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {
				
				// where is the ground?
				int groundY = findGroundY(generator, chunk.getBlockX(x), chunk.getBlockZ(z));
				
				// make the base
				chunk.setBlock(x, 0, z, ores.substratumId);
				
				// place the way down there bits
				chunk.setBlocks(x, 1, groundY - 1, z, ores.stratumId);
				
				// seas?
				if (groundY < seaLevel) {
					chunk.setBlock(x, groundY - 1, z, ores.fluidSubsurfaceId);
					chunk.setBlock(x, groundY, z, ores.fluidSurfaceId);
					if (generator.settings.includeAbovegroundFluids) {
						if (generator.settings.includeDecayedNature)
							chunk.setBlocks(x, groundY + 1, deepSeaLevel, z, ores.fluidId);
						else
							chunk.setBlocks(x, groundY + 1,  seaLevel, z, ores.fluidId);
					}
				} else {
					chunk.setBlock(x, groundY - 1, z, ores.subsurfaceId);
					chunk.setBlock(x, groundY, z, ores.surfaceId);
				}
				
				// set biome for block
				biomes.setBiome(x, z, resultBiome);
			}
		}
		
	}
	
	private final static double underworldOdds = 0.50;
	private final static int underworldLength = 6;
	private final static byte stoneId = (byte) Material.STONE.getId();
	
	@Override
	public void postGenerateChunk(WorldGenerator generator, PlatLot lot, ByteChunk chunk, CachedYs blockYs) {
		OreProvider ores = generator.oreProvider;
		int lotBottomY = lot.getBottomY(generator);
		if (lotBottomY != 0) {
			
			// shape the underworld
			if (lot.style == LotStyle.STRUCTURE) {
				for (int x = 0; x < chunk.width; x++) {
					for (int z = 0; z < chunk.width; z++) {
						if (random.nextDouble() < underworldOdds) {
							int y = lotBottomY - random.nextInt(underworldLength);
							if (!chunk.isEmpty(x, lotBottomY, z))
								chunk.setBlocks(x, y, lotBottomY, z, ores.subsurfaceId);
						}
					}
				}
			}
			
			// cross beams
			chunk.setBlocks(7, 9, lotBottomY - 2, lotBottomY, 0, 16, stoneId);
			chunk.setBlocks(0, 16, lotBottomY - 2, lotBottomY, 7, 9, stoneId);
		}
	}

	@Override
	public void preGenerateBlocks(WorldGenerator generator, PlatLot lot, RealChunk chunk, CachedYs blockYs) {
		
	}

	@Override
	public void postGenerateBlocks(WorldGenerator generator, PlatLot lot, RealChunk chunk, CachedYs blockYs) {
		// there is always something to plant!
		lot.generateSurface(generator, chunk, true);
	}

	@Override
	public boolean isHorizontalNSShaft(int chunkX, int chunkY, int chunkZ) {
		return false;
	}

	@Override
	public boolean isHorizontalWEShaft(int chunkX, int chunkY, int chunkZ) {
		return false;
	}

	@Override
	public boolean isVerticalShaft(int chunkX, int chunkY, int chunkZ) {
		return false;
	}

	@Override
	public boolean notACave(WorldGenerator generator, int blockX, int blockY, int blockZ) {
		return true;
	}
}
