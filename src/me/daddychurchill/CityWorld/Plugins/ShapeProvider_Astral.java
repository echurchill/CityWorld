package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Context.Astral.AstralNatureContext;
import me.daddychurchill.CityWorld.Context.Astral.AstralRoadContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class ShapeProvider_Astral extends ShapeProvider {

//	public DataContext buildingsContext;
	
	public SimplexOctaveGenerator landShape1;
	public SimplexOctaveGenerator landShape2;
	public SimplexOctaveGenerator seaShape;
	public SimplexOctaveGenerator noiseShape;
	public SimplexOctaveGenerator featureShape;
	public SimplexNoiseGenerator caveShape;
	public SimplexNoiseGenerator mineShape;

	protected int height;
	protected int seaLevel;
	protected int landRange;
	protected int seaRange;
	protected int constructMin;
	protected int constructRange;
	
	public final static int landFlattening = 32;
	public final static int seaFlattening = 4;
	public final static int landFactor1to2 = 3;
	public final static int noiseVerticalScale = 3;
	public final static int featureVerticalScale = 10;
	public final static int fudgeVerticalScale = noiseVerticalScale * landFactor1to2 + featureVerticalScale * landFactor1to2;

	public final static double landFrequency1 = 1.50;
	public final static double landAmplitude1 = 20.0;
	public final static double landHorizontalScale1 = 1.0 / 1024;//2048.0;
	public final static double landFrequency2 = 1.0;
	public final static double landAmplitude2 = landAmplitude1 / landFactor1to2;
	public final static double landHorizontalScale2 = landHorizontalScale1 * landFactor1to2;

	public final static double seaFrequency = 1.00;
	public final static double seaAmplitude = 2.00;
	public final static double seaHorizontalScale = 1.0 / 256;//384.0;

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

	public ShapeProvider_Astral(WorldGenerator generator, Odds odds) {
		super(generator, odds);
		
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
		constructMin = seaLevel;
		constructRange = height - constructMin;
	}
	
	@Override
	public CachedYs getCachedYs(WorldGenerator generator, int chunkX, int chunkZ) {
		CachedYs result = super.getCachedYs(generator, chunkX, chunkZ);
		result.flattenSegments(generator.seaLevel);
		return result;
	}
	
	@Override
	protected void validateLots(WorldGenerator generator, PlatMap platmap) {
		// nothing to do in this one
	}
	
	@Override
	protected void allocateContexts(WorldGenerator generator) {
		if (!contextInitialized) {
			natureContext = new AstralNatureContext(generator);
			roadContext = new AstralRoadContext(generator);
			
//			buildingsContext = new AstralBuildingsContext(generator);
			
			contextInitialized = true;
		}
	}
	
	@Override
	protected DataContext getContext(PlatMap platmap) {
		
		// how natural is this platmap?
//		float nature = platmap.getNaturePercent();
//		if (nature < 0.15)
//			return buildingsContext;
//		
//		// otherwise just keep what we have
//		else
			return natureContext;
	}

	@Override
	public String getCollectionName() {
		return "Astral";
	}
	
	@Override
	protected Biome remapBiome(WorldGenerator generator, PlatLot lot, Biome biome) {
		return generator.oreProvider.remapBiome(biome);
	}

	@Override
	public void preGenerateChunk(WorldGenerator generator, PlatLot lot, ByteChunk chunk, BiomeGrid biomes, CachedYs blockYs) {
		Biome biome = lot.getChunkBiome();
		OreProvider ores = generator.oreProvider;
//		boolean surfaceCaves = isSurfaceCaveAt(chunk.chunkX, chunk.chunkZ);
		
		// shape the world
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {
				int y = blockYs.getBlockY(x, z);
				
				// roughness
				double noise = noiseShape.noise(chunk.getBlockX(x), chunk.getBlockZ(z), 0, noiseFrequency, noiseAmplitude, true);
				int noiseY = NoiseGenerator.floor(noise * 5) + 5;
				
				// bottom please
				chunk.setBlock(x, 0, z, ores.substratumMaterial);
				
				// Chasm?
				if (y == 0) {
					biome = Biome.OCEAN;
					chunk.setBlock(x, 1, z, ores.fluidFluidMaterial);
					chunk.setBlocks(x, 2, noiseY + 2, z, Material.WEB);
				
				// on the edge
				} else if (y == seaLevel) {
					chunk.setBlocks(x, 1, y, z, ores.subsurfaceMaterial);
					
				// Valley? Mountain?
				} else {
					int baseY = Math.min(seaLevel + noiseY, y);
					chunk.setBlocks(x, 1, baseY, z, ores.surfaceMaterial);
					if (baseY < y)
						chunk.setBlocks(x, baseY, y, z, Material.STAINED_GLASS);
//					switch (blockYs.segmentWidth) {
//					case 2:
//						
//					case 4:
//					case 8:
//					case 16:
//					default:
//					}
					
//					generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial, ores.stratumMaterial, y - noiseY, ores.subsurfaceMaterial, y - 1, ores.surfaceMaterial, generator.settings.includeDecayedNature);
						
//				// Valley?
//				} else if (y < seaLevel) {
//					chunk.setBlocks(x, 1, y, z, ores.stratumMaterial);
//					
//				// Mountain?
//				} else {
//					chunk.setBlocks(x, 1, y, z, ores.surfaceMaterial);
					
					// Everywere stuff
//					double noise = noiseShape.noise(chunk.getBlockX(x), 0, chunk.getBlockZ(z), noiseFrequency, noiseAmplitude, true);
//					int noiseY = NoiseGenerator.floor(noise * 20) + 20;
//					
////					generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial, ores.stratumMaterial, y - 3, ores.subsurfaceMaterial, y, ores.surfaceMaterial, generator.settings.includeDecayedNature);
//					chunk.setBlocks(x, 1, y - noiseY, z, ores.stratumMaterial);
//					chunk.setBlocks(x, y - noiseY, y + 1, z, ores.subsurfaceMaterial);
//					
//					// Mountainous stuff
//					if (y > generator.seaLevel) {
//	//					generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial, ores.stratumMaterial, y - 3, ores.subsurfaceMaterial, y, ores.surfaceMaterial, generator.settings.includeDecayedNature);
//						chunk.setBlock(x, y, z, ores.surfaceMaterial);
//					}
				}
//				
//				
//				// buildable?
//				if (lot.style == LotStyle.STRUCTURE || lot.style == LotStyle.ROUNDABOUT) {
//					generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial, ores.stratumMaterial, generator.streetLevel - 2, ores.subsurfaceMaterial, generator.streetLevel, ores.subsurfaceMaterial, false);
//					
//				// possibly buildable?
//				} else if (y == generator.streetLevel) {
//					generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial, ores.stratumMaterial, y - 3, ores.subsurfaceMaterial, y, ores.surfaceMaterial, generator.settings.includeDecayedNature);
//				
//				// won't likely have a building
//				} else {
//
////					// on the beach
////					if (y == generator.seaLevel) {
////						generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial, ores.stratumMaterial, y - 2, ores.fluidSubsurfaceMaterial, y, ores.fluidSurfaceMaterial, generator.settings.includeDecayedNature);
////						biome = Biome.BEACH;
////
////					// we are in the water! ...or are we?
////					} else if (y < generator.seaLevel) {
////						biome = Biome.DESERT;
////						if (generator.settings.includeDecayedNature)
////							if (generator.settings.includeAbovegroundFluids && y < generator.deepseaLevel)
////								generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial, ores.stratumMaterial, y - 2, ores.fluidSubsurfaceMaterial, y, ores.fluidSurfaceMaterial, generator.deepseaLevel, ores.fluidMaterial, false);
////							else
////								generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial, ores.stratumMaterial, y - 2, ores.fluidSubsurfaceMaterial, y, ores.fluidSurfaceMaterial, true);
////						else 
////							if (generator.settings.includeAbovegroundFluids) {
////								generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial, ores.stratumMaterial, y - 2, ores.fluidSubsurfaceMaterial, y, ores.fluidSurfaceMaterial, generator.seaLevel, ores.fluidMaterial, false);
////								biome = Biome.OCEAN;
////							} else
////								generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial, ores.stratumMaterial, y - 2, ores.fluidSubsurfaceMaterial, y, ores.fluidSurfaceMaterial, false);
//					if (y <= generator.seaLevel) {
//						biome = Biome.OCEAN;
//						double noise = noiseShape.noise(chunk.getBlockX(x), chunk.getBlockZ(z), noiseFrequency, noiseAmplitude, true) + 1;
//						chunk.setBlock(x, 0, z, Material.WOOL);
//						chunk.setBlocks(x, 1, NoiseGenerator.floor(noise * 10 + 1), z, Material.WEB);
////						generateStratas(generator, lot, chunk, x, z, Material.AIR, Material.AIR, y - 2, Material.AIR, y, Material.AIR, false);
//
//					// we are in the mountains
//					} else {
//
//						// regular trees only
//						if (y < generator.treeLevel) {
//							generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial, ores.stratumMaterial, y - 3, ores.subsurfaceMaterial, y, ores.surfaceMaterial, generator.settings.includeDecayedNature);
//							biome = Biome.FOREST;
//
//						// regular trees and some evergreen trees
//						} else if (y < generator.evergreenLevel) {
//							generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial, ores.stratumMaterial, y - 2, ores.subsurfaceMaterial, y, ores.surfaceMaterial, surfaceCaves);
//							biome = Biome.FOREST_HILLS;
//
//						// evergreen and some of fallen snow
//						} else if (y < generator.snowLevel) {
//							generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial, ores.stratumMaterial, y - 1, ores.subsurfaceMaterial, y, ores.surfaceMaterial, surfaceCaves);
//							biome = Biome.TAIGA_HILLS;
//							
//						// only snow up here!
//						} else {
//							if (generator.settings.includeAbovegroundFluids && y > generator.snowLevel + 2)
//								generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial, ores.stratumMaterial, y - 1, ores.stratumMaterial, y, ores.fluidFrozenMaterial, surfaceCaves);
//							else
//								generateStratas(generator, lot, chunk, x, z, ores.substratumMaterial, ores.stratumMaterial, y - 1, ores.stratumMaterial, y, ores.stratumMaterial, surfaceCaves);
//							biome = Biome.ICE_MOUNTAINS;
//						}
//					}
//				}
				
				// set biome for block
				biomes.setBiome(x, z, remapBiome(generator, lot, biome));
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
	public int getConstuctMin() {
		return constructMin;
	}

	@Override
	public int getConstuctRange() {
		return constructRange;
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
		double seaY = seaLevel + (sea * seaRange) + (noise * noiseVerticalScale) + seaFlattening;

		// Mountain?
		if (landY > seaLevel) {
			y = landY;	
		} else {
			
			// Chasm?
			if (seaY < seaLevel) {
				y = 0;
				
			// Cliff?
			} else if (seaY == seaLevel) {
				y = seaLevel;
			
			// Something in between?
			} else {
				
				// invert the sea
//				seaY = Math.max(0, seaLevel - Math.max(seaLevel, (sea * seaRange * 3) + (noise * noiseVerticalScale * 2)));
//				seaY = Math.max(0, seaLevel - (Math.abs(sea) * seaRange * 3) + (noise * noiseVerticalScale * 2));
				seaY = Math.min(seaLevel, 3 + Math.max(0, seaLevel - ((seaY - seaLevel) * 3)));
				
				// who is on top?
				y = Math.max(landY, seaY);
			}
		}
		
		// range validation
		return Math.min(height - 3, Math.max(y, 0));
		
//		
//		// land is below the sea
//		if (landY <= seaLevel) {
//			
//			// if seabed is too high... then we might be buildable
//			if (seaY >= seaLevel) {
//				y = seaLevel + 1;
//
//				// if we are too near the sea then we must be on the beach
//				if (seaY <= seaLevel + 1) {
//					y = seaLevel;
//				}
//
//			// if land is higher than the seabed use land to smooth
//			// out under water base of the mountains 
//			} else if (landY >= seaY) {
//				y = Math.min(seaLevel, landY + 1);
//
//			// otherwise just take the sea bed as is
//			} else {
//				y = Math.min(seaLevel, seaY);
//			}
//
//		// must be a mountain then
//		} else {
//			y = Math.max(seaLevel, landY + 1);
//		}
//		
//		// range validation
//		return Math.min(height - 3, Math.max(y, 3));
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
		
}
