package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.block.Biome;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Maps.FloatingMap;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.PlatLot.LotStyle;
import me.daddychurchill.CityWorld.Support.ByteChunk;
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
	
	@Override
	public Biome generateCrust(WorldGenerator generator, PlatLot lot, ByteChunk chunk, int x, int y, int z, boolean surfaceCaves) {
		Biome resultBiome = lot.getChunkBiome();
		OreProvider ores = generator.oreProvider;
		
		// make the base
		chunk.setBlock(x, 0, z, ores.substratumId);
		
		// where are we?
		int blockX = chunk.getBlockX(x);
		int blockZ = chunk.getBlockZ(z);
		
		// place the way down there bits
		double terrainAt = terrainShape.noise(blockX * terrainScale, blockZ * terrainScale) * midRange;
		double noiseAt = noiseShape.noise(blockX * noiseScale, blockZ * noiseScale) * noiseRange;
		int terrainY = NoiseGenerator.floor(terrainAt + noiseAt) + midPoint;
		//CityWorld.log.info("TerrainAt = " + terrainAt + " NoiseAt = " + noiseAt + " TerrainY = " + terrainY);
		chunk.setBlocks(x, 1, terrainY - 1, z, ores.stratumId);
		
		// seas?
		if (terrainY < seaLevel) {
			chunk.setBlock(x, terrainY - 1, z, ores.fluidSubsurfaceId);
			chunk.setBlock(x, terrainY, z, ores.fluidSurfaceId);
			chunk.setBlocks(x, terrainY + 1, seaLevel, z, ores.fluidId);
		} else {
			chunk.setBlock(x, terrainY - 1, z, ores.subsurfaceId);
			chunk.setBlock(x, terrainY, z, ores.surfaceId);
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

		return resultBiome;
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
