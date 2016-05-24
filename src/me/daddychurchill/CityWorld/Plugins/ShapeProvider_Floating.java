package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;
import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.ConstructionContext;
import me.daddychurchill.CityWorld.Context.FarmContext;
import me.daddychurchill.CityWorld.Context.HighriseContext;
import me.daddychurchill.CityWorld.Context.IndustrialContext;
import me.daddychurchill.CityWorld.Context.LowriseContext;
import me.daddychurchill.CityWorld.Context.MidriseContext;
import me.daddychurchill.CityWorld.Context.MunicipalContext;
import me.daddychurchill.CityWorld.Context.NeighborhoodContext;
import me.daddychurchill.CityWorld.Context.ParkContext;
import me.daddychurchill.CityWorld.Context.Floating.FloatingNatureContext;
import me.daddychurchill.CityWorld.Context.Floating.FloatingRoadContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Floating.FloatingBlimpLot;
import me.daddychurchill.CityWorld.Plats.PlatLot.LotStyle;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class ShapeProvider_Floating extends ShapeProvider_Normal {

	public ShapeProvider_Floating(CityWorldGenerator generator, Odds odds) {
		super(generator, odds);
		long seed = generator.getWorldSeed();
		
		terrainShape = new SimplexNoiseGenerator(seed + 3);
		noiseShape = new SimplexNoiseGenerator(seed + 4);
		
		//streetLevel = height / 8 * 3;
		constructMin = seaLevel + landRange + 32;
		constructRange = height - 32 - constructMin; 
	}

	@Override
	protected void allocateContexts(CityWorldGenerator generator) {
		if (!contextInitialized) {
			natureContext = new FloatingNatureContext(generator);
			roadContext = new FloatingRoadContext(generator);
			
			parkContext = new ParkContext(generator);
			highriseContext = new HighriseContext(generator);
			constructionContext = new ConstructionContext(generator);
			midriseContext = new MidriseContext(generator);
			municipalContext = new MunicipalContext(generator);
			industrialContext = new IndustrialContext(generator);
			lowriseContext = new LowriseContext(generator);
			neighborhoodContext = new NeighborhoodContext(generator);
			farmContext = new FarmContext(generator);
			outlandContext = farmContext;
			
			contextInitialized = true;
		}
	}
	
	@Override
	public String getCollectionName() {
		return "Floating";
	}

	@Override
	protected void validateLots(CityWorldGenerator generator, PlatMap platmap) {

		// find blimp moorings
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				if (needBlimpLot(platmap, x, z))
					platmap.setLot(x, z, new FloatingBlimpLot(platmap, platmap.originX + x, platmap.originZ + z));
			}
		}
	}
	
	private boolean needBlimpLot(PlatMap platmap, int x, int z) {
		if (platmap.isNaturalLot(x, z)) {
			return platmap.isStructureLot(x - 1, z) || platmap.isStructureLot(x + 1, z) ||
				   platmap.isStructureLot(x, z - 1) || platmap.isStructureLot(x, z + 1);
		} else
			return false;
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
	public int findGroundY(CityWorldGenerator generator, int blockX, int blockZ) {
		
		// calculator the way down there bits
		double terrainAt = terrainShape.noise(blockX * terrainScale, blockZ * terrainScale) * midRange;
		double noiseAt = noiseShape.noise(blockX * noiseScale, blockZ * noiseScale) * noiseRange;
		return NoiseGenerator.floor(terrainAt + noiseAt) + midPoint;
	}
	
	@Override
	public void preGenerateChunk(CityWorldGenerator generator, PlatLot lot, InitialBlocks chunk, BiomeGrid biomes, CachedYs blockYs) {
		Biome resultBiome = lot.getChunkBiome();
		OreProvider ores = generator.oreProvider;
		
		// shape the world
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {
				// where is the ground?
				int groundY = findGroundY(generator, chunk.getBlockX(x), chunk.getBlockZ(z));
				
				// which one are we doing?
				switch (generator.settings.subSurfaceStyle) {
				case LAND:
					// make the base
					chunk.setBlock(x, 0, z, ores.substratumMaterial);
					
					// place the way down there bits
					chunk.setBlocks(x, 1, groundY - 1, z, ores.stratumMaterial);
					
					// seas?
					if (groundY < seaLevel) {
						chunk.setBlock(x, groundY - 1, z, ores.fluidSubsurfaceMaterial);
						chunk.setBlock(x, groundY, z, ores.fluidSurfaceMaterial);
						if (generator.settings.includeAbovegroundFluids) {
							if (generator.settings.includeDecayedNature)
								chunk.setBlocks(x, groundY + 1, deepSeaLevel, z, ores.fluidMaterial);
							else
								chunk.setBlocks(x, groundY + 1, seaLevel, z, ores.fluidMaterial);
						}
					} else {
						chunk.setBlock(x, groundY - 1, z, ores.subsurfaceMaterial);
						chunk.setBlock(x, groundY, z, ores.surfaceMaterial);
					}
					break;
				case CLOUD:
					
					// where is the fluff?
					if (groundY >= seaLevel) {
						int thickness = groundY - seaLevel;
						int bottomY = Math.max(0, seaLevel - thickness);
						int topY = seaLevel + thickness;
						int midY = topY - 5;
						if (midY > bottomY) {
							chunk.setBlocks(x, bottomY, midY, z, Material.WOOL);
							chunk.setBlocks(x, midY, topY, z, Material.WEB);
						} else
							chunk.setBlocks(x, bottomY, topY, z, Material.WEB);
					}
					break;
				case LAVA:
					
					// make the base
					chunk.setBlock(x, 0, z, ores.substratumMaterial);
					
					// add the nasty bit
					chunk.setBlocks(x, 1, groundY, z, Material.STATIONARY_LAVA);
					break;
				case NONE:
				default:
					break;
				}
					
				// set biome for block
				biomes.setBiome(x, z, generator.oreProvider.remapBiome(resultBiome));
			}
		}
	}
	
	private final static double underworldOdds = 0.50;
	private final static int underworldLength = 6;
	
	@Override
	public void postGenerateChunk(CityWorldGenerator generator, PlatLot lot, InitialBlocks chunk, CachedYs blockYs) {
		OreProvider ores = generator.oreProvider;
		int lotBottomY = lot.getBottomY(generator);
		if (lotBottomY != 0) {
			
			// shape the underworld
			if (lot.style == LotStyle.STRUCTURE ||
				lot.style == LotStyle.ROAD) {
//				int h = (lotBottomY - 1) / 3;
//				chunk.setBlocks(5, 11, h * 2, lotBottomY, 5, 11, Material.STONE);
//				chunk.setBlocks(6, 10, h, h * 2, 6, 10, Material.STONE);
//				chunk.setBlocks(7, 9, 1, h, 7, 9, Material.STONE);
				for (int x = 0; x < chunk.width; x++) {
					for (int z = 0; z < chunk.width; z++) {
						if (odds.playOdds(underworldOdds)) {
							int y = lotBottomY - odds.getRandomInt(underworldLength);
							if (!chunk.isEmpty(x, lotBottomY, z))
								chunk.setBlocks(x, y, lotBottomY, z, ores.subsurfaceMaterial);
						}
					}
				}
			}
			
			// cross beams
			chunk.setBlocks(7, 9, lotBottomY - 2, lotBottomY, 0, 16, Material.STONE);
			chunk.setBlocks(0, 16, lotBottomY - 2, lotBottomY, 7, 9, Material.STONE);
		}
	}

	@Override
	public void preGenerateBlocks(CityWorldGenerator generator, PlatLot lot, RealBlocks chunk, CachedYs blockYs) {
		
	}

	@Override
	public void postGenerateBlocks(CityWorldGenerator generator, PlatLot lot, RealBlocks chunk, CachedYs blockYs) {
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
	public boolean notACave(CityWorldGenerator generator, int blockX, int blockY, int blockZ) {
		return true;
	}
}
