package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Maps.FloatingMap;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.PlatLot.LotStyle;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class ShapeProvider_Floating extends ShapeProvider {

	public ShapeProvider_Floating(WorldGenerator generator) {
		super(generator);
		World world = generator.getWorld();
		long seed = generator.getWorldSeed();
		
		landShape1 = new SimplexOctaveGenerator(seed, 4);
		landShape1.setScale(landHorizontalScale1);
		landShape2 = new SimplexOctaveGenerator(seed, 6);
		landShape2.setScale(landHorizontalScale2);
		gapShape = new SimplexOctaveGenerator(seed + 2, 8);
		gapShape.setScale(seaHorizontalScale);
		terrainShape = new SimplexNoiseGenerator(seed + 3);
		noiseShape = new SimplexNoiseGenerator(seed + 4);

		// get ranges
		height = world.getMaxHeight();
		streetLevel = world.getSeaLevel() + 1;
	}

	@Override
	public PlatMap createPlatMap(WorldGenerator generator, SupportChunk cornerChunk, int platX, int platZ) {
		return new FloatingMap(generator, cornerChunk, platX, platZ);
	}

	private SimplexOctaveGenerator landShape1;
	private SimplexOctaveGenerator landShape2;
	private SimplexOctaveGenerator gapShape;
	private SimplexNoiseGenerator terrainShape;
	private SimplexNoiseGenerator noiseShape;
	
	private int height;
	private int streetLevel;
	
	private final static int landFlattening = 32;
	private final static int gapFlattening = 4;
	private final static int landFactor1to2 = 3;
	
	private final static double landFrequency1 = 1.50;
	private final static double landAmplitude1 = 20.0;
	private final static double landHorizontalScale1 = 1.0 / 2048.0;
	private final static double landFrequency2 = 1.0;
	private final static double landAmplitude2 = landAmplitude1 / landFactor1to2;
	private final static double landHorizontalScale2 = landHorizontalScale1 * landFactor1to2;

	private final static double gapFrequency = 1.00;
	private final static double gapAmplitude = 2.00;
	private final static double seaHorizontalScale = 1.0 / 384.0;
	
	private final static int seaBed = 4;
	private final static int gapRange = 4;
	private final static int landRange = 8;
	private final static int seaLevel = seaBed + gapRange;
	private final static int deepSeaLevel = seaLevel - gapRange / 2;
	
	private final static int noiseRange = seaBed / 2;
	private final static int midRange = (gapRange + landRange) / 2;
	private final static int midPoint = seaBed + midRange;
	
	private final static double terrainScale = 1.0 / 281.0;
	private final static double noiseScale = 1.0 / 23.0;
	
	public final static int floatingMin = seaLevel + landRange + 32;
	public final static int floatingRange = 256 - 32 - floatingMin; //TODO I really need to make this more dynamic
	
	@Override
	public double findPerciseY(WorldGenerator generator, int blockX, int blockZ) {
		double y = 0;
		
		double land1Y = streetLevel + (landShape1.noise(blockX, blockZ, landFrequency1, landAmplitude1, true) * 
				landRange) - landFlattening;
		double land2Y = streetLevel + (landShape2.noise(blockX, blockZ, landFrequency2, landAmplitude2, true) * 
				(landRange / (double) landFactor1to2)) - landFlattening;
		
		double landY = Math.max(land1Y, land2Y);
		double gapNoise = gapShape.noise(blockX, blockZ, gapFrequency, gapAmplitude, true);
		
		// calculate the Ys
		double gapY = streetLevel + (gapNoise * gapRange) + gapFlattening;

		// land is below the gap
		if (landY <= streetLevel) {

			// if gap is too high... then we might be buildable
			if (gapY >= streetLevel) {
				y = streetLevel + 1;

				// if we are too near the gap then we must be on the beach
				if (gapY <= streetLevel + 1) {
					y = streetLevel;
				}

			// if land is higher than the gap use land to smooth out  
			} else if (landY >= gapY) {
				y = Math.min(streetLevel, landY + 1);

			// otherwise just take the gap bed as is
			} else {
				y = Math.min(streetLevel, gapY);
			}

		// must be a mountain then
		} else {
			y = Math.max(streetLevel, landY + 1);
		}
		
		// range validation
		return Math.min(height - 3, Math.max(y, 3));
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

				// buildable?
				if (lot.style == LotStyle.STRUCTURE || lot.style == LotStyle.ROUNDABOUT) {
					chunk.setBlock(x, y, z, ores.subsurfaceId);
					
				// possibly buildable?
				} else if (y == generator.streetLevel) {
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
	public void postGenerateChunk(WorldGenerator generator, PlatLot lot, ByteChunk chunk, CachedYs blockYs) {
		
	}

	@Override
	public void preGenerateBlocks(WorldGenerator generator, PlatLot lot, RealChunk chunk, CachedYs blockYs) {
		
	}

	@Override
	public void postGenerateBlocks(WorldGenerator generator, PlatLot lot, RealChunk chunk, CachedYs blockYs) {
		
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

	@Override
	public int getWorldHeight() {
		return height;
	}

	@Override
	public int getStreetLevel() {
		return streetLevel;
	}

	@Override
	public int getSeaLevel() {
		return seaLevel;
	}

	@Override
	public int getLandRange() {
		return landRange;
	}

	@Override
	public int getSeaRange() {
		return gapRange;
	}

}
