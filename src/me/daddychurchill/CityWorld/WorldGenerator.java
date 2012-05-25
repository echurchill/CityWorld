package me.daddychurchill.CityWorld;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import me.daddychurchill.CityWorld.Context.ContextAllPark;
import me.daddychurchill.CityWorld.Context.ContextCityCenter;
import me.daddychurchill.CityWorld.Context.ContextHighrise;
import me.daddychurchill.CityWorld.Context.ContextLowrise;
import me.daddychurchill.CityWorld.Context.ContextMall;
import me.daddychurchill.CityWorld.Context.ContextMidrise;
import me.daddychurchill.CityWorld.Context.ContextUnfinished;
import me.daddychurchill.CityWorld.Context.PlatMapContext;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SupportChunk;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class WorldGenerator extends ChunkGenerator {

	private CityWorld plugin;
	private World world;
	private String worldname;
	private String worldstyle;

	public SimplexOctaveGenerator landShape1;
	public SimplexOctaveGenerator landShape2;
	public SimplexOctaveGenerator seaShape;
	public SimplexOctaveGenerator noiseShape;
	public SimplexOctaveGenerator featureShape;
	public SimplexNoiseGenerator caveShape;
	public SimplexNoiseGenerator oreShape;
	public SimplexNoiseGenerator roadShape;
	
	public int topLevel;
	public int seaLevel;
	public int sidewalkLevel;
	public int landRange;
	public int seaRange;
	
	public long connectedKeyForPavedRoads;
	public long connectedKeyForParks;

	public WorldGenerator(CityWorld aPlugin, String aWorldname, String aWorldstyle) {
		plugin = aPlugin;
		worldname = aWorldname;
		worldstyle = aWorldstyle;
	}

	public CityWorld getPlugin() {
		return plugin;
	}

	public World getWorld() {
		return world;
	}

	public String getWorldname() {
		return worldname;
	}

	public String getWorldstyle() {
		return worldstyle;
	}

	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		return Arrays.asList((BlockPopulator) new CityWorldBlockPopulator(this));
	}
	
	public int landFlattening = 32;
	public int seaFlattening = 4;

	public int landFactor1to2 = 3;
	public double landFrequency1 = 1.50;
	public double landAmplitude1 = 20.0;
	public double landHorizontalScale1 = 1.0 / 2048.0;
	public double landFrequency2 = 1.0;
	public double landAmplitude2 = landAmplitude1 / landFactor1to2;
	public double landHorizontalScale2 = landHorizontalScale1 * landFactor1to2;

	public double seaFrequency = 1.00;
	public double seaAmplitude = 2.00;
	public double seaHorizontalScale = 1.0 / 384.0;

	public double noiseFrequency = 1.50;
	public double noiseAmplitude = 0.50;
	public double noiseHorizontalScale = 1.0 / 32.0;
	public int noiseVerticalScale = 3;

	public double featureFrequency = 1.50;
	public double featureAmplitude = 0.75;
	public double featureHorizontalScale = 1.0 / 64.0;
	public int featureVerticalScale = 10;
	
	public int fudgeVerticalScale = noiseVerticalScale * landFactor1to2 + featureVerticalScale * landFactor1to2;

	public double caveScale = 1.0 / 32.0;
	public double caveScaleY = caveScale * 2;
	public double caveThreshold = 0.70;

	public double oreScale = 1.0 / 16.0;
	public double oreScaleY = oreScale * 2;
	public double oreThreshold = 0.85;

	public double bridgeScale = 1.0 / 512.0;
	public double roundaboutThreshold = 0.50;
	
	@Override
	public byte[][] generateBlockSections(World aWorld, Random random, int chunkX, int chunkZ, BiomeGrid biomes) {

		// initialize the shaping logic
		if (world == null) {
			world = aWorld;
			long seed = world.getSeed();

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
			oreShape = new SimplexNoiseGenerator(seed + 1);
			roadShape = new SimplexNoiseGenerator(seed + 2);
			
			// get ranges
			topLevel = world.getMaxHeight();
			seaLevel = world.getSeaLevel();
			sidewalkLevel = seaLevel + 1;
			landRange = topLevel - seaLevel - fudgeVerticalScale + landFlattening;
			seaRange = seaLevel - fudgeVerticalScale + seaFlattening;
			
			// get the connectionKeys
			connectedKeyForPavedRoads = random.nextLong();
			connectedKeyForParks = random.nextLong();
		}
		
		// place to work
		ByteChunk byteChunk = new ByteChunk(this, random, chunkX, chunkZ);
		
		// figure out what everything looks like
		PlatMap platmap = getPlatMap(byteChunk, chunkX, chunkZ);
		if (platmap != null) {
			platmap.generateChunk(byteChunk, biomes);
		}

		return byteChunk.blocks;
	}
	
	public double findPerciseY(int blockX, int blockZ) {
		double y = 0;
		
		// shape the noise
		double noise = noiseShape.noise(blockX, blockZ, noiseFrequency, noiseAmplitude, true);
		double feature = featureShape.noise(blockX, blockZ, featureFrequency, featureAmplitude, true);

		// shape the shapes
//		double land = (landShape1.noise(blockX, blockZ, landFrequency, landAmplitude, true) +
//					   landShape2.noise(blockX, blockZ, landFrequency, landAmplitude, true)) / 2;
//		double land1 = landShape1.noise(blockX, blockZ, landFrequency1, landAmplitude1, true);
//		double land2 = landShape2.noise(blockX, blockZ, landFrequency2, landAmplitude2, true);
		
		double land1 = seaLevel + (landShape1.noise(blockX, blockZ, landFrequency1, landAmplitude1, true) * landRange) + 
				(noise * noiseVerticalScale * landFactor1to2 + feature * featureVerticalScale * landFactor1to2) - landFlattening;
		double land2 = seaLevel + (landShape2.noise(blockX, blockZ, landFrequency2, landAmplitude2, true) * (landRange / landFactor1to2)) + 
				(noise * noiseVerticalScale + feature * featureVerticalScale) - landFlattening;
		
		double landY = Math.max(land1, land2);
		double sea = seaShape.noise(blockX, blockZ, seaFrequency, seaAmplitude, true);
		
		// calculate the Ys
//		int landY = seaLevel + NoiseGenerator.floor(land * landRange) + 
//				NoiseGenerator.floor(noise * noiseVerticalScale + feature * featureVerticalScale) - landFlattening;
		double seaY = seaLevel + (sea * seaRange) + (noise * noiseVerticalScale) + seaFlattening;

		//TODO there is a fracture between mountains and the sea (and sometimes on the land itself) that appeared when I made this floating point
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

		// range validation
		return Math.min(topLevel - 3, Math.max(y, 3));
	}
	
	public int findBlockY(int blockX, int blockZ) {
		return NoiseGenerator.floor(findPerciseY(blockX, blockZ));
	}
	
	private final static int naturalNSBridgeOddSlot = 0; 
	private final static int naturalRoundaboutOddSlot = 10; 
	
	public boolean getBridgePolarityAt(double chunkX, double chunkZ) {
		return true; //roadShape.noise(chunkX * bridgeScale, chunkZ * bridgeScale, naturalNSBridgeOddSlot) >= 0.0;
	}

	public boolean isRoundaboutAt(double chunkX, double chunkZ) {
		return roadShape.noise(chunkX, chunkZ, naturalRoundaboutOddSlot) >= roundaboutThreshold;
	}
	
	public boolean isTheSea(int blockX, int blockZ) {
		return (findBlockY(blockX + 8, blockZ + 8) <= seaLevel && // center
				findBlockY(blockX + 0, blockZ + 0) <= seaLevel && // corners
				findBlockY(blockX + 0, blockZ + 15) <= seaLevel && 
				findBlockY(blockX + 15, blockZ + 0) <= seaLevel && 
				findBlockY(blockX + 15, blockZ + 15) <= seaLevel &&
				findBlockY(blockX + 0, blockZ + 8) <= seaLevel && // edges 
				findBlockY(blockX + 15, blockZ + 8) <= seaLevel && 
				findBlockY(blockX + 8, blockZ + 15) <= seaLevel && 
				findBlockY(blockX + 8, blockZ + 15) <= seaLevel);
	}
	
	public boolean onTheLevel(int blockX, int blockZ, int level) {
		return (findBlockY(blockX + 8, blockZ + 8) == level && // center
				findBlockY(blockX + 0, blockZ + 0) == level && // corners
				findBlockY(blockX + 0, blockZ + 15) == level && 
				findBlockY(blockX + 15, blockZ + 0) == level && 
				findBlockY(blockX + 15, blockZ + 15) == level);
	}
	
	public boolean isBuildableAt(int blockX, int blockZ) {
		return onTheLevel(blockX, blockZ, sidewalkLevel);
	}

	public boolean notACave(int blockX, int blockY, int blockZ) {

		// cave or not?
		double cave = caveShape.noise(blockX * caveScale, blockY * caveScaleY, blockZ * caveScale);
		return !(cave > caveThreshold || cave < -caveThreshold);
	}

	public byte getOre(ByteChunk byteChunk, int blockX, int blockY, int blockZ, byte defaultId) {

		// ore or not?
		double ore = oreShape.noise(blockX * oreScale, blockY * oreScaleY, blockZ * oreScale);
		if (ore > oreThreshold || ore < -oreThreshold)
			return byteChunk.getOre(blockY);
		else
			return defaultId;
	}
	
	private final static int spawnRadius = 100;
	
	@Override
	public Location getFixedSpawnLocation(World world, Random random) {
		int spawnX = random.nextInt(spawnRadius * 2) - spawnRadius;
		int spawnZ = random.nextInt(spawnRadius * 2) - spawnRadius;
		
		// find the first non empty spot;
		int spawnY = world.getMaxHeight();
		while ((spawnY > 0) && world.getBlockAt(spawnX, spawnY - 1, spawnZ).isEmpty()) {
			spawnY--;
		}
		
		// return the location
		return new Location(world, spawnX, spawnY, spawnZ);
	}

	// manager for handling the city plat maps collection
	private Hashtable<Long, PlatMap> platmaps;
	private PlatMap getPlatMap(SupportChunk cornerChunk, int chunkX, int chunkZ) {

		// get the plat map collection
		if (platmaps == null)
			platmaps = new Hashtable<Long, PlatMap>();

		// find the origin for the plat
		int platX = calcOrigin(chunkX);
		int platZ = calcOrigin(chunkZ);

		// calculate the plat's key
//TODO	Long platkey = Long.valueOf(((long) platX << 32 + (long) platZ));
		Long platkey = Long.valueOf(((long) platX * (long) Integer.MAX_VALUE + (long) platZ));

		// get the right plat
		PlatMap platmap = platmaps.get(platkey);
		
		// doesn't exist? then make it!
		if (platmap == null) {
			
			// what is the context for this one?
			PlatMapContext context = getContext(cornerChunk, chunkX, chunkZ);
			platmap = new PlatMap(this, cornerChunk, context, platX, platZ);
			
			// remember it for quicker look up
			platmaps.put(platkey, platmap);
		}

		// finally return the plat
		return platmap;
	}

	private PlatMapContext getContext(SupportChunk cornerChunk, int chunkX, int chunkZ) {
		Random random = cornerChunk.random;
		
		// TODO derive this from the CityNoise generator
		switch (random.nextInt(20)) {
		case 0:
		case 1:
		case 2:
		case 3:
			return new ContextLowrise(plugin, cornerChunk);
		case 4:
		case 5:
		case 6:
		case 7:
			return new ContextMidrise(plugin, cornerChunk);
		case 8:
		case 9:
		case 10:
			return new ContextHighrise(plugin, cornerChunk);
		case 11:
		case 12:
			return new ContextAllPark(plugin, cornerChunk);
		case 13:
		case 14:
			return new ContextMall(plugin, cornerChunk);
		case 15:
		case 16:
		case 17:
			return new ContextCityCenter(plugin, cornerChunk);
		case 18:
		case 19:
		default:
			return new ContextUnfinished(plugin, cornerChunk);
		}
	}

	// Supporting code used by getPlatMap
	private int calcOrigin(int i) {
		if (i >= 0) {
			return i / PlatMap.Width * PlatMap.Width;
		} else {
			return -((Math.abs(i + 1) / PlatMap.Width * PlatMap.Width) + PlatMap.Width);
		}
	}

	private class CityWorldBlockPopulator extends BlockPopulator {

		private WorldGenerator chunkGen;

		public CityWorldBlockPopulator(WorldGenerator chunkGen) {
			this.chunkGen = chunkGen;
		}

		@Override
		public void populate(World world, Random random, Chunk chunk) {
			int chunkX = chunk.getX();
			int chunkZ = chunk.getZ();

			// place to work
			RealChunk realChunk = new RealChunk(chunkGen, random, chunk);

			// figure out what everything looks like
			PlatMap platmap = chunkGen.getPlatMap(realChunk, chunkX, chunkZ);
			if (platmap != null) {
				platmap.generateBlocks(realChunk);
			}
		}
	}
}
