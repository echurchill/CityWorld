package me.daddychurchill.CityWorld.Plugins;

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
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class ShapeProvider_Floating extends ShapeProvider_Normal {

	public ShapeProvider_Floating(WorldGenerator generator) {
		super(generator);
		long seed = generator.getWorldSeed();
		
		terrainShape = new SimplexNoiseGenerator(seed + 100);
		noiseShape = new SimplexNoiseGenerator(seed + 101);
	}

	@Override
	public PlatMap createPlatMap(WorldGenerator generator, SupportChunk cornerChunk, int platX, int platZ) {
		return new FloatingMap(generator, cornerChunk, platX, platZ);
	}

	private SimplexNoiseGenerator terrainShape;
	private SimplexNoiseGenerator noiseShape;
	
	private final static int seaBed = 4;
	private final static int seaRange = 4;
	private final static int landRange = 8;
	private final static int seaLevel = seaBed + seaRange;
	
	private final static int noiseRange = seaBed / 2;
	private final static int midRange = (seaRange + landRange) / 2;
	private final static int midPoint = seaBed + midRange;
	
	private final static double terrainScale = 1.0 / 281.0;
	private final static double noiseScale = 1.0 / 23.0;
	
	public final static int floatingMin = seaLevel + landRange + 16;
	public final static int floatingRange = 256 - 32;
	
	@Override
	public int findGroundY(WorldGenerator generator, int blockX, int blockZ) {
		
		// calculator the way down there bits
		double terrainAt = terrainShape.noise(blockX * terrainScale, blockZ * terrainScale) * midRange;
		double noiseAt = noiseShape.noise(blockX * noiseScale, blockZ * noiseScale) * noiseRange;
		return NoiseGenerator.floor(terrainAt + noiseAt) + midPoint;
	}
	
	@Override
	public void generateCrust(WorldGenerator generator, PlatLot lot, ByteChunk chunk, BiomeGrid biomes, CachedYs blockYs) {
		Biome resultBiome = lot.getChunkBiome();
		OreProvider ores = generator.oreProvider;
		
		// shape the world
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {
				int y = blockYs.getBlockY(x, z);
				
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
					chunk.setBlocks(x, groundY + 1, seaLevel, z, ores.fluidId);
				} else {
					chunk.setBlock(x, groundY - 1, z, ores.subsurfaceId);
					chunk.setBlock(x, groundY, z, ores.surfaceId);
				}

				// buildable?
				if (lot.style == LotStyle.STRUCTURE || lot.style == LotStyle.ROUNDABOUT) {
					chunk.setBlock(x, y, z, ores.subsurfaceId);
					
				// possibly buildable?
				} else if (y == generator.sidewalkLevel) {
					chunk.setBlock(x, y, z, ores.stratumId);
				

				// on the beach
				} else if (y == generator.seaLevel) {
					chunk.setBlock(x, y, z, ores.surfaceId);
					//generateStratas(generator, lot, chunk, x, z, substratumId, y - 2, sandId, y, sandId, generator.settings.includeDecayedNature);
					//generateStratas(generator, lot, chunk, x, z, substratumId, y - 2, sandId, y, stoneId, generator.settings.includeDecayedNature);
					resultBiome = Biome.BEACH;
				}

				// set biome for block
				biomes.setBiome(x, z, resultBiome);
			}
		}
		
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
