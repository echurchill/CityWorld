package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Maps.NormalMap;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.PlatLot.LotStyle;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SupportChunk;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import org.bukkit.util.noise.SimplexNoiseGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class ShapeProvider_Normal extends ShapeProvider {

	public SimplexOctaveGenerator landShape1;
	public SimplexOctaveGenerator landShape2;
	public SimplexOctaveGenerator seaShape;
	public SimplexOctaveGenerator noiseShape;
	public SimplexOctaveGenerator featureShape;
	public SimplexNoiseGenerator caveShape;
	public SimplexNoiseGenerator mineShape;

	protected int height;
	private int seaLevel;
	private int landRange;
	private int seaRange;
	
	public final static int landFlattening = 32;
	public final static int seaFlattening = 4;
	public final static int landFactor1to2 = 3;
	public final static int noiseVerticalScale = 3;
	public final static int featureVerticalScale = 10;
	public final static int fudgeVerticalScale = noiseVerticalScale * landFactor1to2 + featureVerticalScale * landFactor1to2;

	public final static double landFrequency1 = 1.50;
	public final static double landAmplitude1 = 20.0;
	public final static double landHorizontalScale1 = 1.0 / 2048.0;
	public final static double landFrequency2 = 1.0;
	public final static double landAmplitude2 = landAmplitude1 / landFactor1to2;
	public final static double landHorizontalScale2 = landHorizontalScale1 * landFactor1to2;

	public final static double seaFrequency = 1.00;
	public final static double seaAmplitude = 2.00;
	public final static double seaHorizontalScale = 1.0 / 384.0;

	public final static double noiseFrequency = 1.50;
	public final static double noiseAmplitude = 0.70;
	public final static double noiseHorizontalScale = 1.0 / 32.0;
	
	public final static double featureFrequency = 1.50;
	public final static double featureAmplitude = 0.75;
	public final static double featureHorizontalScale = 1.0 / 64.0;
	
	public final static double caveScale = 1.0 / 64.0;
	public final static double caveScaleY = caveScale * 2;
	public final static double caveThreshold = 0.75; // smaller the number the more larger the caves will be
	
	public final static double mineScale = 1.0 / 4.0;
	public final static double mineScaleY = mineScale;

	public ShapeProvider_Normal(WorldGenerator generator) {
		super(generator);
		World world = generator.getWorld();
		long seed = generator.getWorldSeed();
		
		landShape1 = new SimplexOctaveGenerator(seed, 4);
		landShape1.setScale(landHorizontalScale1);
		landShape2 = new SimplexOctaveGenerator(seed, 6);
		landShape2.setScale(landHorizontalScale2);
		seaShape = new SimplexOctaveGenerator(seed + 2, 8);
		seaShape.setScale(seaHorizontalScale);
		noiseShape = new SimplexOctaveGenerator(seed + 3, 16);
		noiseShape.setScale(noiseHorizontalScale);
		featureShape = new SimplexOctaveGenerator(seed + 4, 2);
		featureShape.setScale(featureHorizontalScale);
		
		caveShape = new SimplexNoiseGenerator(seed);
		mineShape = new SimplexNoiseGenerator(seed + 1);
		
		// get ranges
		height = world.getMaxHeight();
		seaLevel = world.getSeaLevel();
		landRange = height - seaLevel - fudgeVerticalScale + landFlattening;
		seaRange = seaLevel - fudgeVerticalScale + seaFlattening;
	}
	
	@Override
	public PlatMap createPlatMap(WorldGenerator generator, SupportChunk cornerChunk, int platX, int platZ) {
		return new NormalMap(generator, cornerChunk, platX, platZ);
	}

	@Override
	public void preGenerateChunk(WorldGenerator generator, PlatLot lot, ByteChunk chunk, BiomeGrid biomes, CachedYs blockYs) {
		Biome resultBiome = lot.getChunkBiome();
		OreProvider ores = generator.oreProvider;
		boolean surfaceCaves = isSurfaceCaveAt(chunk.chunkX, chunk.chunkZ);
		
		// shape the world
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {
				int y = blockYs.getBlockY(x, z);
				
				// buildable?
				if (lot.style == LotStyle.STRUCTURE || lot.style == LotStyle.ROUNDABOUT) {
					generateStratas(generator, lot, chunk, x, z, ores.substratumId, ores.stratumId, generator.streetLevel - 2, ores.subsurfaceId, generator.streetLevel, ores.subsurfaceId, false);
					
				// possibly buildable?
				} else if (y == generator.streetLevel) {
					generateStratas(generator, lot, chunk, x, z, ores.substratumId, ores.stratumId, y - 3, ores.subsurfaceId, y, ores.surfaceId, generator.settings.includeDecayedNature);
				
				// won't likely have a building
				} else {

					// on the beach
					if (y == generator.seaLevel) {
						generateStratas(generator, lot, chunk, x, z, ores.substratumId, ores.stratumId, y - 2, ores.fluidSubsurfaceId, y, ores.fluidSurfaceId, generator.settings.includeDecayedNature);
						resultBiome = Biome.BEACH;

					// we are in the water! ...or are we?
					} else if (y < generator.seaLevel) {
						resultBiome = Biome.DESERT;
						if (generator.settings.includeDecayedNature)
							if (generator.settings.includeAbovegroundFluids && y < generator.deepseaLevel)
								generateStratas(generator, lot, chunk, x, z, ores.substratumId, ores.stratumId, y - 2, ores.fluidSubsurfaceId, y, ores.fluidSurfaceId, generator.deepseaLevel, ores.fluidId, false);
							else
								generateStratas(generator, lot, chunk, x, z, ores.substratumId, ores.stratumId, y - 2, ores.fluidSubsurfaceId, y, ores.fluidSurfaceId, true);
						else 
							if (generator.settings.includeAbovegroundFluids) {
								generateStratas(generator, lot, chunk, x, z, ores.substratumId, ores.stratumId, y - 2, ores.fluidSubsurfaceId, y, ores.fluidSurfaceId, generator.seaLevel, ores.fluidId, false);
								resultBiome = Biome.OCEAN;
							} else
								generateStratas(generator, lot, chunk, x, z, ores.substratumId, ores.stratumId, y - 2, ores.fluidSubsurfaceId, y, ores.fluidSurfaceId, false);

					// we are in the mountains
					} else {

						// regular trees only
						if (y < generator.treeLevel) {
							generateStratas(generator, lot, chunk, x, z, ores.substratumId, ores.stratumId, y - 3, ores.subsurfaceId, y, ores.surfaceId, generator.settings.includeDecayedNature);
							resultBiome = Biome.FOREST;

						// regular trees and some evergreen trees
						} else if (y < generator.evergreenLevel) {
							generateStratas(generator, lot, chunk, x, z, ores.substratumId, ores.stratumId, y - 2, ores.subsurfaceId, y, ores.surfaceId, surfaceCaves);
							resultBiome = Biome.FOREST_HILLS;

						// evergreen and some of fallen snow
						} else if (y < generator.snowLevel) {
							generateStratas(generator, lot, chunk, x, z, ores.substratumId, ores.stratumId, y - 1, ores.subsurfaceId, y, ores.surfaceId, surfaceCaves);
							resultBiome = Biome.TAIGA_HILLS;
							
						// only snow up here!
						} else {
							if (generator.settings.includeAbovegroundFluids)
								generateStratas(generator, lot, chunk, x, z, ores.substratumId, ores.stratumId, y - 1, ores.stratumId, y, ores.fluidFrozenId, surfaceCaves);
							else
								generateStratas(generator, lot, chunk, x, z, ores.substratumId, ores.stratumId, y - 1, ores.stratumId, y, ores.surfaceId, surfaceCaves);
							resultBiome = Biome.ICE_MOUNTAINS;
						}
					}
				}
				
				// set biome for block
				biomes.setBiome(x, z, resultBiome);
			}
		}	
	}
	
	@Override
	public void postGenerateChunk(WorldGenerator generator, PlatLot lot, ByteChunk chunk, CachedYs blockYs) {
		
		// mines please
		lot.generateMines(generator, chunk);
	}

	@Override
	public void preGenerateBlocks(WorldGenerator generator, PlatLot lot, RealChunk chunk, CachedYs blockYs) {
		// nothing... yet
	}

	@Override
	public void postGenerateBlocks(WorldGenerator generator, PlatLot lot, RealChunk chunk, CachedYs blockYs) {
		
		// put ores in?
		lot.generateOres(generator, chunk);

		// do we do it or not?
		lot.generateMines(generator, chunk);
	}

	@Override
	public int getWorldHeight() {
		return height;
	}

	@Override
	public int getStreetLevel() {
		return seaLevel + 1;
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
		return seaRange;
	}

	@Override
	public double findPerciseY(WorldGenerator generator, int blockX, int blockZ) {
		double y = 0;
		
		// shape the noise
		double noise = noiseShape.noise(blockX, blockZ, noiseFrequency, noiseAmplitude, true);
		double feature = featureShape.noise(blockX, blockZ, featureFrequency, featureAmplitude, true);

		double land1 = seaLevel + (landShape1.noise(blockX, blockZ, landFrequency1, landAmplitude1, true) * landRange) + 
				(noise * noiseVerticalScale * landFactor1to2 + feature * featureVerticalScale * landFactor1to2) - landFlattening;
		double land2 = seaLevel + (landShape2.noise(blockX, blockZ, landFrequency2, landAmplitude2, true) * (landRange / (double) landFactor1to2)) + 
				(noise * noiseVerticalScale + feature * featureVerticalScale) - landFlattening;
		
		double landY = Math.max(land1, land2);
		double sea = seaShape.noise(blockX, blockZ, seaFrequency, seaAmplitude, true);
		
		// calculate the Ys
		double seaY = seaLevel + (sea * seaRange) + (noise * noiseVerticalScale) + seaFlattening;

		// land is below the sea
		if (landY <= seaLevel) {

			// if seabed is too high... then we might be buildable
			if (seaY >= seaLevel) {
				y = seaLevel + 1;

				// if we are too near the sea then we must be on the beach
				if (seaY <= seaLevel + 1) {
					y = seaLevel;
				}

			// if land is higher than the seabed use land to smooth
			// out under water base of the mountains 
			} else if (landY >= seaY) {
				y = Math.min(seaLevel, landY + 1);

			// otherwise just take the sea bed as is
			} else {
				y = Math.min(seaLevel, seaY);
			}

		// must be a mountain then
		} else {
			y = Math.max(seaLevel, landY + 1);
		}
		
		// for real?
		if (!generator.settings.includeMountains)
			y = Math.min(seaLevel + 1, y);
		if (!generator.settings.includeSeas)
			y = Math.max(seaLevel + 1, y);

		// range validation
		return Math.min(height - 3, Math.max(y, 3));
	}
	
	@Override
	public boolean isHorizontalNSShaft(int chunkX, int chunkY, int chunkZ) {
		return mineShape.noise(chunkX * mineScale, chunkY * mineScale, chunkZ * mineScale + 0.5) > 0.0;
	}

	@Override
	public boolean isHorizontalWEShaft(int chunkX, int chunkY, int chunkZ) {
		return mineShape.noise(chunkX * mineScale + 0.5, chunkY * mineScale, chunkZ * mineScale) > 0.0;
	}

	@Override
	public boolean isVerticalShaft(int chunkX, int chunkY, int chunkZ) {
		return mineShape.noise(chunkX * mineScale, chunkY * mineScale + 0.5, chunkZ * mineScale) > 0.0;
	}

	@Override
	public boolean notACave(WorldGenerator generator, int blockX, int blockY, int blockZ) {
		if (generator.settings.includeCaves) {
			double cave = caveShape.noise(blockX * caveScale, blockY * caveScaleY, blockZ * caveScale);
			return !(cave > caveThreshold || cave < -caveThreshold);
		} else
			return true;
	}
	
	public boolean isSurfaceCaveAt(double chunkX, double chunkZ) {
		return microBooleanAt(chunkX, chunkZ, microSurfaceCaveSlot);
	}
	
}
