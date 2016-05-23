package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.SnowDunes.SnowDunesConstructionContext;
import me.daddychurchill.CityWorld.Context.SnowDunes.SnowDunesFarmContext;
import me.daddychurchill.CityWorld.Context.SnowDunes.SnowDunesHighriseContext;
import me.daddychurchill.CityWorld.Context.SnowDunes.SnowDunesLowriseContext;
import me.daddychurchill.CityWorld.Context.SnowDunes.SnowDunesMidriseContext;
import me.daddychurchill.CityWorld.Context.SnowDunes.SnowDunesNatureContext;
import me.daddychurchill.CityWorld.Context.SnowDunes.SnowDunesNeighborhoodContext;
import me.daddychurchill.CityWorld.Context.SnowDunes.SnowDunesParkContext;
import me.daddychurchill.CityWorld.Context.SnowDunes.SnowDunesRoadContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class ShapeProvider_SnowDunes extends ShapeProvider_Normal {

	protected int floodY;
	
	private SimplexOctaveGenerator duneFeature1;
	private SimplexOctaveGenerator duneFeature2;
//	private SimplexOctaveGenerator duneNoise;
	
	private final static int featureOctaves = 2;
	private final static int featureVerticalScale = 5;
	private final static double featureFrequency = 1.50;
	private final static double featureAmplitude = 1;
	private final static double featureHorizontalScale = 1.0 / 64.0;
	
//	private final static int noiseOctaves = 16;
//	private final static int noiseVerticalScale = 3;
//	private final static double noiseFrequency = 1.50;
//	private final static double noiseAmplitude = 0.70;
//	private final static double noiseHorizontalScale = 1.0 / 64.0;
	
	public ShapeProvider_SnowDunes(CityWorldGenerator generator, Odds odds) {
		super(generator, odds);
		
		floodY = seaLevel + 15;

		long seed = generator.getWorldSeed();
		duneFeature1 = new SimplexOctaveGenerator(seed + 20, featureOctaves);
		duneFeature1.setScale(featureHorizontalScale);
		duneFeature2 = new SimplexOctaveGenerator(seed + 30, featureOctaves * 2);
		duneFeature2.setScale(featureHorizontalScale);
//		duneNoise = new SimplexOctaveGenerator(seed + 40, noiseOctaves);
//		duneNoise.setScale(noiseHorizontalScale);
	}

	@Override
	public void allocateContexts(CityWorldGenerator generator) {
		if (!contextInitialized) {
			natureContext = new SnowDunesNatureContext(generator);
			roadContext = new SnowDunesRoadContext(generator);
			
			parkContext = new SnowDunesParkContext(generator);
			highriseContext = new SnowDunesHighriseContext(generator);
			constructionContext = new SnowDunesConstructionContext(generator);
			midriseContext = new SnowDunesMidriseContext(generator);
			municipalContext = midriseContext;
			lowriseContext = new SnowDunesLowriseContext(generator);
			industrialContext = lowriseContext;
			neighborhoodContext = new SnowDunesNeighborhoodContext(generator);
			farmContext = new SnowDunesFarmContext(generator);
			outlandContext = farmContext;
			
			contextInitialized = true;
		}
	}
	
	@Override
	public String getCollectionName() {
		return "SnowDunes";
	}
	
	@Override
	public double findPerciseFloodY(CityWorldGenerator generator, int blockX, int blockZ) {
		
		// shape the noise
//		double noiseY = 0;//duneNoise.noise(blockX, blockZ, noiseFrequency, noiseAmplitude, true);
		double featureY = duneFeature1.noise(blockX, blockZ, featureFrequency, featureAmplitude, true) -
						  Math.abs(duneFeature2.noise(blockX + 20, blockZ + 20, featureFrequency, featureAmplitude, true));
		
		return floodY + (featureY * featureVerticalScale);// + (noiseY * noiseVerticalScale);	
	}
	
	@Override
	public int findFloodY(CityWorldGenerator generator, int blockX, int blockZ) {
		return NoiseGenerator.floor(findPerciseFloodY(generator, blockX, blockZ));
	}

	@Override
	public int findHighestFloodY(CityWorldGenerator generator) {
		return floodY + featureVerticalScale + noiseVerticalScale;
	}

	@Override
	public int findLowestFloodY(CityWorldGenerator generator) {
		return floodY;
	}

//	@Override
//	public byte findAtmosphereIdAt(WorldGenerator generator, int blockY) {
//		if (blockY < floodY)
//			return BlackMagic.snowBlockId;
//		else
//			return super.findAtmosphereIdAt(generator, blockY);
//	}
	
	@Override
	public boolean clearAtmosphere(CityWorldGenerator generator) {
		return false;
	}
	
	@Override
	public Material findAtmosphereMaterialAt(CityWorldGenerator generator, int blockY) {
		if (blockY < floodY)
			return Material.SNOW_BLOCK;
		else
			return super.findAtmosphereMaterialAt(generator, blockY);
	}
	
//	@Override
//	public byte findGroundCoverIdAt(WorldGenerator generator, int blockY) {
//		if (blockY < floodY)
//			return BlackMagic.snowCoverId;
//		else
//			return super.findGroundCoverIdAt(generator, blockY);
//	}
	
	@Override
	public Material findGroundCoverMaterialAt(CityWorldGenerator generator, int blockY) {
		if (blockY < floodY)
			return Material.SNOW;
		else
			return super.findGroundCoverMaterialAt(generator, blockY);
	}
	
	@Override
	protected Biome remapBiome(CityWorldGenerator generator, PlatLot lot, Biome biome) {
		return Biome.ICE_MOUNTAINS;
	}

	@Override
	protected void generateStratas(CityWorldGenerator generator, PlatLot lot,
			InitialBlocks chunk, int x, int z, Material substratumMaterial, Material stratumMaterial,
			int stratumY, Material subsurfaceMaterial, int subsurfaceY, Material surfaceMaterial,
			int coverY, Material coverMaterial, boolean surfaceCaves) {

		// do the default bit
		super.generateStratas(generator, lot, chunk, x, z, substratumMaterial, stratumMaterial, stratumY, subsurfaceMaterial, subsurfaceY, surfaceMaterial, coverY, coverMaterial, surfaceCaves);
		
		// cover it up a bit
		actualGenerateSnow(generator, lot, chunk, x, z, coverY);
	}
	
	@Override
	protected void generateStratas(CityWorldGenerator generator, PlatLot lot,
			InitialBlocks chunk, int x, int z, Material substratumMaterial, Material stratumMaterial,
			int stratumY, Material subsurfaceMaterial, int subsurfaceY, Material surfaceMaterial,
			boolean surfaceCaves) {

		// do the default bit
		super.generateStratas(generator, lot, chunk, x, z, substratumMaterial, stratumMaterial, stratumY, subsurfaceMaterial, subsurfaceY, surfaceMaterial, surfaceCaves);
		
		// cover it up a bit
		actualGenerateSnow(generator, lot, chunk, x, z, subsurfaceY);
	}
	
	protected void actualGenerateSnow(CityWorldGenerator generator, PlatLot lot, InitialBlocks chunk, int x, int z, int y) {
		int baseY = chunk.findLastEmptyBelow(x, y + 1, z, y - 6);
		int snowY = findFloodY(generator, chunk.getBlockX(x), chunk.getBlockZ(z));
		if (snowY > baseY) 
			chunk.setBlocks(x, baseY, snowY, z, Material.SNOW_BLOCK);
	}
	
	@Override
	public void postGenerateBlocks(CityWorldGenerator generator, PlatLot lot, RealBlocks chunk, CachedYs blockYs) {
		
		// let the other guy do it's thing
		super.postGenerateBlocks(generator, lot, chunk, blockYs);
		
		// where to start?
		int topY = lot.getTopY(generator);
		
		// now sprinkle snow
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {
				double snowCoverY = findPerciseFloodY(generator, chunk.getBlockX(x), chunk.getBlockZ(z));
				int y = Math.max(topY, NoiseGenerator.floor(snowCoverY));
				int snowY = chunk.findFirstEmpty(x, y, z, y - 1, y + 6);
				if (!chunk.isNonstackableBlock(x, snowY - 1, z)) {
					int snowAmount = NoiseGenerator.floor((snowCoverY - Math.floor(snowCoverY)) * 8.0);
					if (snowAmount > 3 & !chunk.isType(x, snowY - 1, z, Material.SNOW_BLOCK))
						snowAmount = 7 - snowAmount;
					chunk.setSnowCover(x, snowY, z, snowAmount);
				}
			}
		}
		
		// add the snow
//		ShapeProvider shape = generator.shapeProvider;
//		OreProvider ore = generator.oreProvider;
		
//		// let the other guy do it's thing
//		super.postGenerateBlocks(generator, lot, chunk, blockYs);
		
//		// how tall can it be?
//		int maxY = lot.getTopY(generator);
//
//		// shape the world
//		for (int x = 0; x < chunk.width; x++) {
//			for (int z = 0; z < chunk.width; z++) {
//				int surfaceY = blockYs.getBlockY(x, z);
//				
//				// where to drop the snow
//				double floodY = shape.findPerciseFloodY(generator, chunk.getBlockX(x), chunk.getBlockZ(z));
//				byte amount = (byte) NoiseGenerator.floor((floodY - Math.floor(floodY)) * 8.0);
//				int y = NoiseGenerator.floor(floodY);
//				if (surfaceY > floodY)
//					y = surfaceY;
//				
//				// find the bottom
////				y = chunk.findLastEmptyBelow(x, maxY, z);
////				if (chunk.isEmpty(x, y, z)) {
////					if (!chunk.isPartialHeight(x, y - 1, z))
////						chunk.setBlock(x, y, z, snowCoverId, amount, false);
////					y--;
////				}
//				if (chunk.isEmpty(x, y, z)) {
//					chunk.setBlock(x, y - 1, z, Material.GLASS);
//					chunk.setBlock(x, y, z, snowCoverId, amount, false);
//				}
//				
////				// see if there is more we can do
////				boolean thisOneEmpty = false;
////				boolean lastOneSolid = false;
////				while (amount > 0 && y < chunk.height) {
////					y++;
////					thisOneEmpty = chunk.isEmpty(x, y, z);
////					if (lastOneSolid && thisOneEmpty) {
////						if (!chunk.isPartialHeight(x, y - 1, z))
////							chunk.setBlock(x, y, z, snowCoverId, amount, false);
////						amount--;
////					}
////					lastOneSolid = !thisOneEmpty;
////				}
//			}
//		}
	}
	
//	public void dropSnow(WorldGenerator generator, RealChunk chunk, int x, int y, int z, byte level) {
//		y = chunk.findLastEmptyBelow(x, y + 1, z);
//		if (chunk.isEmpty(x, y, z))
//			chunk.setBlock(x, y, z, snowCoverId, level, false);
//	}
}
