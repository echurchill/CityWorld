package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Context.Astral.AstralBaseContext;
import me.daddychurchill.CityWorld.Context.Astral.AstralBlackZoneContext;
import me.daddychurchill.CityWorld.Context.Astral.AstralBuriedCityContext;
import me.daddychurchill.CityWorld.Context.Astral.AstralCrystalSpiresContext;
import me.daddychurchill.CityWorld.Context.Astral.AstralDataContext;
import me.daddychurchill.CityWorld.Context.Astral.AstralForestContext;
import me.daddychurchill.CityWorld.Context.Astral.AstralForestContext.ForestStyle;
import me.daddychurchill.CityWorld.Context.Astral.AstralMushroomContext.MushroomStyle;
import me.daddychurchill.CityWorld.Context.Astral.AstralNatureContext;
import me.daddychurchill.CityWorld.Context.Astral.AstralMushroomContext;
import me.daddychurchill.CityWorld.Context.Astral.AstralNexusContext;
import me.daddychurchill.CityWorld.Context.Astral.AstralRoadContext;
import me.daddychurchill.CityWorld.Context.Astral.AstralWhiteZoneContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.SegmentedCachedYs;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class ShapeProvider_Astral extends ShapeProvider {

	public SimplexOctaveGenerator landShape1;
	public SimplexOctaveGenerator landShape2;
	public SimplexOctaveGenerator seaShape;
	public SimplexOctaveGenerator noiseShape;
	public SimplexOctaveGenerator featureShape;
//	public SimplexNoiseGenerator caveShape;
	public SimplexNoiseGenerator ecoShape;
	
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
	
//	public final static double caveScale = 1.0 / 64.0;
//	public final static double caveScaleY = caveScale * 2;
//	public final static double caveThreshold = 0.75; // smaller the number the more larger the caves will be
//	
	public final static double ecoScale = 1.0 / 4.0;
	public final static double ecoScaleY = ecoScale;

	public ShapeProvider_Astral(CityWorldGenerator generator, Odds odds) {
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
		
//		caveShape = new SimplexNoiseGenerator(seed);
		ecoShape = new SimplexNoiseGenerator(seed + 5);
		
		// get ranges
		height = world.getMaxHeight();
		seaLevel = world.getSeaLevel();
		landRange = height - seaLevel - fudgeVerticalScale + landFlattening;
		seaRange = seaLevel - fudgeVerticalScale + seaFlattening;
		constructMin = seaLevel;
		constructRange = height - constructMin;
	}
	
	@Override
	public CachedYs getCachedYs(CityWorldGenerator generator, int chunkX, int chunkZ) {
		return new SegmentedCachedYs(generator, chunkX, chunkZ);
	}
	
	@Override
	protected void validateLots(CityWorldGenerator generator, PlatMap platmap) {
		// nothing to do in this one
	}
	
	private AstralDataContext nexusContext;
	private AstralDataContext baseContext;
	
	private AstralDataContext brownMushroomsContext;
	private AstralDataContext redMushroomsContext;
	private AstralDataContext mixedMushroomsContext;
	private AstralDataContext yellowSpongesContext;
	
	private AstralDataContext fernForestContext;
	private AstralDataContext hedgeForestContext;
	private AstralDataContext canopyForestContext;

	private AstralDataContext crystalSpiresContext;
	
	private AstralDataContext blackZoneContext;
	private AstralDataContext whiteZoneContext;
	private AstralDataContext cityZoneContext;
	
	@Override
	protected void allocateContexts(CityWorldGenerator generator) {
		if (!contextInitialized) {
			natureContext = new AstralNatureContext(generator);
			roadContext = new AstralRoadContext(generator);
			
			nexusContext = new AstralNexusContext(generator); // the one at 0, 0
			baseContext = new AstralBaseContext(generator); // bunkers on pedestals
			
			brownMushroomsContext = new AstralMushroomContext(generator, MushroomStyle.BROWN); // brown ones
			redMushroomsContext = new AstralMushroomContext(generator, MushroomStyle.RED); // red ones
			mixedMushroomsContext = new AstralMushroomContext(generator, MushroomStyle.REDBROWN); // mix of brown and red ones
			yellowSpongesContext = new AstralMushroomContext(generator, MushroomStyle.YELLOW); // yellow ones

			fernForestContext = new AstralForestContext(generator, ForestStyle.FERN);
			hedgeForestContext = new AstralForestContext(generator, ForestStyle.HEDGE);
			canopyForestContext = new AstralForestContext(generator, ForestStyle.CANOPY);
			
			crystalSpiresContext = new AstralCrystalSpiresContext(generator); // crystal pokie bits
			
			blackZoneContext = new AstralBlackZoneContext(generator); // little boxes of unhappiness
			whiteZoneContext = new AstralWhiteZoneContext(generator); // little boxes of happiness
			cityZoneContext = new AstralBuriedCityContext(generator);

			// obsidianMineContext = new AstralObsidianContext(generator); // obsidian maze mines
			// landingZoneContext = new AstralLandingContext(generator); // spaceship landing zone
			// wallContext = new AstralWallContext(generator); // the walls going north/south/east/west from the nexus zone
			// punctureContext = new AstralPunctureContext(generator); // hole in the world
			
			// MazeRunner
			// Damned Lake
			
//			testSeeds(generator);
			contextInitialized = true;
		}
	}
	
//	private void testSeeds(WorldGenerator generator) {
//		Random seeder = new Random();
//		for (int i = 0; i < 100; i++)
//			testSeed(generator, seeder.nextLong());
//		
//		String tallystring = "count = " + countTally + " ";
//		for (int i = 0; i < totalTally.length; i++) {
//			totalTally[i] = totalTally[i] / countTally;
//			tallystring = tallystring + " [" + i + "]=" + totalTally[i];
//		}
//		generator.reportMessage(tallystring);
//	}
//	
//	private int[] totalTally = new int[11];
//	private int countTally = 0;
////	private int factor = 160;
//	private void testSeed(WorldGenerator generator, long seed) {
//		SimplexNoiseGenerator testShape = new SimplexNoiseGenerator(seed);
//		int limit = 500;
//		int curI = 0;
//		int minI = 100;
//		int maxI = -100;
//		int totalI = 0;
//		int[] tally = new int[11];
//		for (int x = -limit; x < limit; x++) {
//			for (int z = -limit; z < limit; z++) {
//				curI = NoiseGenerator.floor(((Math.max(-0.9999, Math.min(0.9999, testShape.noise(x, z) * 1.375)) + 1.0) / 2.0) * 10);
////				curI = Math.max(0, Math.min(9, NoiseGenerator.floor((((ecoShape.noise(x, z) * 1.5) + 1) / 2) * 10)));
////				curI = Math.max(0, Math.min(9, NoiseGenerator.floor((((ecoShape.noise(x, z) * 1.5) + 1) / 2) * 10)));
////				curI = NoiseGenerator.floor((Math.max(-1.0, Math.min(1.0, ecoShape.noise(x, z) * 1.5 + 1)) / 3) * 10);
////				curI = NoiseGenerator.floor(((ecoShape.noise(x, z) + 1) / 2) * 10);
//				totalI = totalI + curI;
//				
//				if (curI > maxI)
//					maxI = curI;
//				if (curI < minI)
//					minI = curI;
//				
//				tally[curI] = tally[curI] + 1;
//			}
//		}
//		
//		for (int i = 0; i < tally.length; i++) {
//			totalTally[i] = totalTally[i] + tally[i];
//			countTally++;
//		}
//	}
	
	@Override
	public DataContext getContext(int originX, int originZ) {
//		CityWorld.log.info("X, Z = " + originX + ", " + originZ);
		if (originX == 0 && originZ == 0)
			return nexusContext;
		else {
			double rawValue = (Math.max(-0.9999, Math.min(0.9999, ecoShape.noise(originX, originZ) * 1.375)) + 1.0) / 2.0;
			switch (NoiseGenerator.floor(rawValue * 13)) { // the constant here should ALWAYS be one more than the biggest case statement constant!
			default: // always leave default at zero
			case 0:
				return natureContext;
			case 1:
				return baseContext;
	
			case 2:
				return brownMushroomsContext;
			case 3:
				return redMushroomsContext;
			case 4:
				return mixedMushroomsContext;
			case 5:
				return yellowSpongesContext;
	
			case 6:
				return crystalSpiresContext;
	
			case 7:
				return blackZoneContext;
			case 8:
				return whiteZoneContext;
			case 9:
				return cityZoneContext;

			case 10:
				return fernForestContext;
			case 11:
				return hedgeForestContext;
			case 12:
				return canopyForestContext;
			}
		}
	}
	
	@Override
	public DataContext getContext(PlatMap platmap) {
		return getContext(platmap.originX, platmap.originZ);
	}

	@Override
	public String getCollectionName() {
		return "Astral";
	}
	
	@Override
	protected Biome remapBiome(CityWorldGenerator generator, PlatLot lot, Biome biome) {
		return generator.oreProvider.remapBiome(biome);
	}

	@Override
	public void preGenerateChunk(CityWorldGenerator generator, PlatLot lot, InitialBlocks chunk, BiomeGrid biomes, CachedYs blockYs) {
		Biome biome = lot.getChunkBiome();
		OreProvider ores = generator.oreProvider;
//		boolean surfaceCaves = isSurfaceCaveAt(chunk.chunkX, chunk.chunkZ);
		boolean flattened = blockYs.segmentWidth > 1;
		
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
					chunk.setBlock(x, 1, z, ores.fluidMaterial);
					chunk.setBlocks(x, 2, noiseY + 2, z, Material.WEB);
				
				// On the edge?
				} else if (y == seaLevel && blockYs.getSegment(x, z) == 0) {
					chunk.setBlocks(x, 1, noiseY * 3, z, ores.stratumMaterial);
					
//					int atY = noiseY * 3;
//					int bandY = Math.max(7, noiseY);
//					while (bandY > 0 && atY + bandY * 2 < y - 1) {
//						chunk.setBlocks(x, atY, atY + bandY, z, ores.subsurfaceMaterial);
//						chunk.setBlocks(x, atY + bandY, atY + bandY * 2, z, ores.stratumMaterial);
//						atY = atY + bandY * 2;
//						bandY--;
//					}
//					chunk.setBlocks(x, atY, y - 1, z, ores.subsurfaceMaterial);
					
					chunk.setBlocks(x, noiseY * 3, y - 1, z, ores.subsurfaceMaterial);
					chunk.setBlocks(x, y - 1, y, z, ores.stratumMaterial);
				
				// Valley? Mountain?
				} else {

					// dented?
					int baseY = Math.min(seaLevel + noiseY, y);
					if (flattened)
						baseY = Math.min(seaLevel, Math.max(16, baseY - blockYs.segmentWidth * 2));
					
					// initial stuff, we will do the rest later
					chunk.setBlocks(x, 1, noiseY * 3, z, ores.stratumMaterial);
					chunk.setBlocks(x, noiseY * 3, baseY - 3, z, ores.subsurfaceMaterial);
					chunk.setBlocks(x, baseY - 3, baseY, z, ores.surfaceMaterial);
					
					// we will do the rest later
				}
				
				// set biome for block
				biomes.setBiome(x, z, remapBiome(generator, lot, biome));
			}
		}	
	}
	
	@Override
	public void postGenerateChunk(CityWorldGenerator generator, PlatLot lot, InitialBlocks chunk, CachedYs blockYs) {
		
//		// mines please
//		lot.generateMines(generator, chunk);
	}

	@Override
	public void preGenerateBlocks(CityWorldGenerator generator, PlatLot lot, RealBlocks chunk, CachedYs blockYs) {
		OreProvider ores = generator.oreProvider;
//		boolean surfaceCaves = isSurfaceCaveAt(chunk.chunkX, chunk.chunkZ);
		int originX = chunk.getOriginX();
		int originZ = chunk.getOriginZ();
		boolean flattened = blockYs.segmentWidth > 1;
		
		// shape the world
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {
				int blockX = chunk.getBlockX(x);
				int blockZ = chunk.getBlockZ(z);
				int y = blockYs.getBlockY(x, z);
				
				// Chasm?
				if (y == 0) {
					
					// nothing... yet
				
				// On the edge?
				} else if (y == seaLevel && blockYs.getSegment(x, z) == 0) {
					
					// nothing... yet
				
				// Valley? Mountain?
				} else {
					
					// roughness
					double noise = noiseShape.noise(blockX, blockZ, 0, noiseFrequency, noiseAmplitude, true);
					int noiseY = NoiseGenerator.floor(noise * 5) + 5;
					
					// dented?
					int baseY = Math.min(seaLevel + noiseY, y);
					if (flattened)
						baseY = Math.min(seaLevel, Math.max(16, baseY - blockYs.segmentWidth * 2));
					
					// backfill valley
					if (y < seaLevel) {
						
						// little more snow
						chunk.setBlocks(x, baseY, y, z, ores.surfaceMaterial);
						double perciseY = blockYs.getPerciseY(x, z);
						chunk.setSnowCover(x, y, z, (byte) NoiseGenerator.floor((perciseY - Math.floor(perciseY)) * 8.0));
						
					// backfill mountain
					} else if (y > seaLevel) {
				
						// now the pretty colors
						if (y > baseY) {
							int segmentX = x / blockYs.segmentWidth * blockYs.segmentWidth + originX;
							int segmentZ = z / blockYs.segmentWidth * blockYs.segmentWidth + originZ;
							double colorD = noiseShape.noise(segmentX, segmentZ, blockYs.getSegment(x, z), noiseFrequency, noiseAmplitude, true);
							chunk.setGlass(x, x + 1, baseY, y, z, z + 1, DyeColor.values()[Math.min(15, Math.max(0, NoiseGenerator.floor(colorD * 8) + 8))]);
						
						// sprinkle a little bit more snow?
						} else {
							
							// little more snow
							chunk.setBlocks(x, baseY, y, z, ores.surfaceMaterial);
							double perciseY = blockYs.getPerciseY(x, z);
							chunk.setSnowCover(x, y, z, (byte) NoiseGenerator.floor((perciseY - Math.floor(perciseY)) * 8.0));
						}
					}
				}
			}
		}	
	}

	@Override
	public void postGenerateBlocks(CityWorldGenerator generator, PlatLot lot, RealBlocks chunk, CachedYs blockYs) {
		
//		// put ores in?
//		lot.generateOres(generator, chunk);
//
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
	public double findPerciseY(CityWorldGenerator generator, int blockX, int blockZ) {
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
			} else if (NoiseGenerator.floor(seaY) == seaLevel) {
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
	}
	
	@Override
	public boolean isHorizontalNSShaft(int chunkX, int chunkY, int chunkZ) {
		return false;
//		return mineShape.noise(chunkX * mineScale, chunkY * mineScale, chunkZ * mineScale + 0.5) > 0.0;
	}

	@Override
	public boolean isHorizontalWEShaft(int chunkX, int chunkY, int chunkZ) {
		return false;
//		return mineShape.noise(chunkX * mineScale + 0.5, chunkY * mineScale, chunkZ * mineScale) > 0.0;
	}

	@Override
	public boolean isVerticalShaft(int chunkX, int chunkY, int chunkZ) {
		return false;
//		return mineShape.noise(chunkX * mineScale, chunkY * mineScale + 0.5, chunkZ * mineScale) > 0.0;
	}

	@Override
	public boolean notACave(CityWorldGenerator generator, int blockX, int blockY, int blockZ) {
		return true;
//		if (generator.settings.includeCaves) {
//			double cave = caveShape.noise(blockX * caveScale, blockY * caveScaleY, blockZ * caveScale);
//			return !(cave > caveThreshold || cave < -caveThreshold);
//		} else
//			return true;
	}
		
}
