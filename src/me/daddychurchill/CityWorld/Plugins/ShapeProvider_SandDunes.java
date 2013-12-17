package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.SandDunes.SandDunesConstructionContext;
import me.daddychurchill.CityWorld.Context.SandDunes.SandDunesFarmContext;
import me.daddychurchill.CityWorld.Context.SandDunes.SandDunesHighriseContext;
import me.daddychurchill.CityWorld.Context.SandDunes.SandDunesLowriseContext;
import me.daddychurchill.CityWorld.Context.SandDunes.SandDunesMidriseContext;
import me.daddychurchill.CityWorld.Context.SandDunes.SandDunesNatureContext;
import me.daddychurchill.CityWorld.Context.SandDunes.SandDunesNeighborhoodContext;
import me.daddychurchill.CityWorld.Context.SandDunes.SandDunesParkContext;
import me.daddychurchill.CityWorld.Context.SandDunes.SandDunesRoadContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class ShapeProvider_SandDunes extends ShapeProvider_Normal {

	protected int floodY;
	
	private SimplexOctaveGenerator duneFeature1;
	private SimplexOctaveGenerator duneFeature2;
//	private SimplexOctaveGenerator duneNoise;
	
	private final static int featureOctaves = 2;
	private final static int featureVerticalScale = 15;
	private final static double featureFrequency = 1.50;
	private final static double featureAmplitude = 1;
	private final static double featureHorizontalScale = 1.0 / 128.0;
	
//	private final static int noiseOctaves = 16;
//	private final static int noiseVerticalScale = 3;
//	private final static double noiseFrequency = 1.50;
//	private final static double noiseAmplitude = 0.70;
//	private final static double noiseHorizontalScale = 1.0 / 64.0;
	
	public ShapeProvider_SandDunes(WorldGenerator generator, Odds odds) {
		super(generator, odds);
		
		floodY = seaLevel + 20;

		long seed = generator.getWorldSeed();
		duneFeature1 = new SimplexOctaveGenerator(seed + 20, featureOctaves);
		duneFeature1.setScale(featureHorizontalScale);
		duneFeature2 = new SimplexOctaveGenerator(seed + 30, featureOctaves * 2);
		duneFeature2.setScale(featureHorizontalScale);
//		duneNoise = new SimplexOctaveGenerator(seed + 40, noiseOctaves);
//		duneNoise.setScale(noiseHorizontalScale);
	}

	@Override
	public void allocateContexts(WorldGenerator generator) {
		if (!contextInitialized) {
			natureContext = new SandDunesNatureContext(generator);
			roadContext = new SandDunesRoadContext(generator);
			
			parkContext = new SandDunesParkContext(generator);
			highriseContext = new SandDunesHighriseContext(generator);
			constructionContext = new SandDunesConstructionContext(generator);
			midriseContext = new SandDunesMidriseContext(generator);
			municipalContext = midriseContext;
			lowriseContext = new SandDunesLowriseContext(generator);
			industrialContext = lowriseContext;
			neighborhoodContext = new SandDunesNeighborhoodContext(generator);
			farmContext = new SandDunesFarmContext(generator);
			
			contextInitialized = true;
		}
	}
	
	@Override
	public String getCollectionName() {
		return "SandDunes";
	}
	
	public double findPerciseFloodY(WorldGenerator generator, int blockX, int blockZ) {
		
		// shape the noise
//		double noiseY = 0;//duneNoise.noise(blockX, blockZ, noiseFrequency, noiseAmplitude, true);
		double featureY = duneFeature1.noise(blockX, blockZ, featureFrequency, featureAmplitude, true) -
						  Math.abs(duneFeature2.noise(blockX + 20, blockZ + 20, featureFrequency, featureAmplitude, true));
		
		return floodY + (featureY * featureVerticalScale);// + (noiseY * noiseVerticalScale);	
	}
	
	@Override
	public int findFloodY(WorldGenerator generator, int blockX, int blockZ) {
		return NoiseGenerator.floor(findPerciseFloodY(generator, blockX, blockZ));
	}

	@Override
	public int findHighestFloodY(WorldGenerator generator) {
		return floodY + featureVerticalScale + noiseVerticalScale;
	}

	@Override
	public int findLowestFloodY(WorldGenerator generator) {
		return floodY;
	}

	@Override
	protected Biome remapBiome(WorldGenerator generator, PlatLot lot, Biome biome) {
		return Biome.DESERT_HILLS;
	}

	@Override
	protected void generateStratas(WorldGenerator generator, PlatLot lot,
			ByteChunk chunk, int x, int z, byte substratumId, byte stratumId,
			int stratumY, byte subsurfaceId, int subsurfaceY, byte surfaceId,
			int coverY, byte coverId, boolean surfaceCaves) {

		// do the default bit
		actualGenerateStratas(generator, lot, chunk, x, z, substratumId, stratumId, stratumY, 
				subsurfaceId, subsurfaceY, surfaceId, surfaceCaves);
		
		// cover it up a bit
		actualGenerateSand(generator, lot, chunk, x, z, subsurfaceY);
	}
	
	@Override
	protected void generateStratas(WorldGenerator generator, PlatLot lot,
			ByteChunk chunk, int x, int z, byte substratumId, byte stratumId,
			int stratumY, byte subsurfaceId, int subsurfaceY, byte surfaceId,
			boolean surfaceCaves) {

		// do the default bit
		actualGenerateStratas(generator, lot, chunk, x, z, substratumId, stratumId, stratumY, 
				subsurfaceId, subsurfaceY, surfaceId, surfaceCaves);
		
		// cover it up a bit
		actualGenerateSand(generator, lot, chunk, x, z, subsurfaceY);
	}
	
	protected void actualGenerateSand(WorldGenerator generator, PlatLot lot, ByteChunk chunk, int x, int z, int subsurfaceY) {
		int y = findFloodY(generator, chunk.getBlockX(x), chunk.getBlockZ(z));
		if (y > subsurfaceY) {
			chunk.setBlocks(x, subsurfaceY, y - 2, z, SupportChunk.sandstoneId);
			chunk.setBlocks(x, y - 2, y, z, SupportChunk.sandId);
		}
	}
	
//	private final static Material sandMat = Material.SAND;
////	private final static Material sandMat = Material.GLASS;
//	private final static byte sandId = (byte) sandMat.getId();
//	private final static byte subSandId = (byte) Material.SANDSTONE.getId();
////	private final static byte subSandId = sandId;
	
	@Override
	public byte findAtmosphereIdAt(WorldGenerator generator, int blockY) {
		if (blockY < floodY)
			return SupportChunk.sandId;
		else
			return super.findAtmosphereIdAt(generator, blockY);
	}
	
	@Override
	public Material findAtmosphereMaterialAt(WorldGenerator generator, int blockY) {
		if (blockY < floodY)
			return Material.GLASS;
		else
			return super.findAtmosphereMaterialAt(generator, blockY);
	}
}
