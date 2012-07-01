package me.daddychurchill.CityWorld;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import me.daddychurchill.CityWorld.Plugins.LootProvider;
import me.daddychurchill.CityWorld.Plugins.SpawnProvider;
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
	private Random connectionKeyGen;
	private Random stashRandomGenerator;
	private LootProvider lootProvider;
	private SpawnProvider spawnProvider;

	public CityWorldSettings settings;

	public SimplexOctaveGenerator landShape1;
	public SimplexOctaveGenerator landShape2;
	public SimplexOctaveGenerator seaShape;
	public SimplexOctaveGenerator noiseShape;
	public SimplexOctaveGenerator featureShape;
	public SimplexNoiseGenerator geologyShape;
	public SimplexNoiseGenerator oreShape;
	public SimplexNoiseGenerator mineShape;
	public SimplexNoiseGenerator macroShape;
	public SimplexNoiseGenerator microShape;
	
	public int deepseaLevel;
	public int seaLevel;
	public int sidewalkLevel;
	public int treeLevel;
	public int evergreenLevel;
	public int height;
	public int snowLevel;
	public int landRange;
	public int seaRange;
	
	public long connectedKeyForPavedRoads;
	public long connectedKeyForParks;

	public WorldGenerator(CityWorld aPlugin, String aWorldname, String aWorldstyle) {
		plugin = aPlugin;
		worldname = aWorldname;
		worldstyle = aWorldstyle;
		settings = new CityWorldSettings(plugin, worldname);
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
	public double noiseAmplitude = 0.70;
	public double noiseHorizontalScale = 1.0 / 32.0;
	public int noiseVerticalScale = 3;

	public double featureFrequency = 1.50;
	public double featureAmplitude = 0.75;
	public double featureHorizontalScale = 1.0 / 64.0;
	public int featureVerticalScale = 10;
	
	public int fudgeVerticalScale = noiseVerticalScale * landFactor1to2 + featureVerticalScale * landFactor1to2;

	public double caveScale = 1.0 / 64.0;
	public double caveScaleY = caveScale * 2;
	public double caveThreshold = 0.75; //was 70
	
	public double strataFluidScale = 1.0 / 8.0;
	public double strataFluidThreshold = 0.10;

	public double oreScale = 1.0 / 16.0;
	public double oreScaleY = oreScale * 2;
	public double oreThreshold = 0.90; //was 85

	public double mineScale = 1.0 / 4.0;
	public double mineScaleY = mineScale;

	public double macroScale = 1.0 / 384.0;
	public double microScale = 2.0;
	
	public double oddsIsolatedBuilding = 0.75;
	
	@Override
	public byte[][] generateBlockSections(World aWorld, Random random, int chunkX, int chunkZ, BiomeGrid biomes) {
		
		// initialize the shaping logic
		if (world == null) {
			world = aWorld;
			long seed = world.getSeed();
			connectionKeyGen = new Random(seed + 1);
			stashRandomGenerator = new Random(seed + 2);
			lootProvider = LootProvider.loadProvider();
			spawnProvider = SpawnProvider.loadProvider();

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
			
			geologyShape = new SimplexNoiseGenerator(seed);
			oreShape = new SimplexNoiseGenerator(seed + 1);
			mineShape = new SimplexNoiseGenerator(seed + 2);
			macroShape = new SimplexNoiseGenerator(seed + 3);
			microShape = new SimplexNoiseGenerator(seed + 4);
			
			// get ranges
			height = world.getMaxHeight();
			seaLevel = world.getSeaLevel();
			landRange = height - seaLevel - fudgeVerticalScale + landFlattening;
			seaRange = seaLevel - fudgeVerticalScale + seaFlattening;

			// now the other vertical points
			deepseaLevel = seaLevel - seaRange / 3;
			sidewalkLevel = seaLevel + 1;
			snowLevel = seaLevel + (landRange / 4 * 3);
			evergreenLevel = seaLevel + (landRange / 4 * 2);
			treeLevel = seaLevel + (landRange / 4);
			
//			// seabed = 35 deepsea = 50 sea = 64 sidewalk = 65 tree = 110 evergreen = 156 snow = 202 top = 249
//			CityWorld.log.info("seabed = " + (seaLevel - seaRange) + 
//							   " deepsea = " + deepseaLevel + 
//							   " sea = " + seaLevel + 
//							   " sidewalk = " + sidewalkLevel + 
//							   " tree = " + treeLevel + 
//							   " evergreen = " + evergreenLevel + 
//							   " snow = " + snowLevel + 
//							   " top = " + (seaLevel + landRange));
			
			// get the connectionKeys
			connectedKeyForPavedRoads = connectionKeyGen.nextLong();
			connectedKeyForParks = connectionKeyGen.nextLong();
		}
		
		// get the chunk specific random 
		Random chunkRandom = getMicroRandomGeneratorAt(chunkX, chunkZ);

		// place to work
		ByteChunk byteChunk = new ByteChunk(this, chunkRandom, chunkX, chunkZ);
		
		// figure out what everything looks like
		PlatMap platmap = getPlatMap(byteChunk, chunkX, chunkZ);
		if (platmap != null) {
			platmap.generateChunk(byteChunk, biomes);
		}

		return byteChunk.blocks;
	}
	
	public long getConnectionKey() {
		return connectionKeyGen.nextLong();
	}
	
	public Random getStashRandomGenerator() {
		return stashRandomGenerator;
	}
	
	public LootProvider getLootProvider() {
		return lootProvider;
	}
	
	public SpawnProvider getSpawnProvider() {
		return spawnProvider;
	}
	
	public double findPerciseY(int blockX, int blockZ) {
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
		if (!settings.includeMountains)
			y = Math.min(seaLevel + 1, y);
		if (!settings.includeSeas)
			y = Math.max(seaLevel + 1, y);

		// range validation
		return Math.min(height - 3, Math.max(y, 3));
	}
	
	public int findBlockY(int blockX, int blockZ) {
		return NoiseGenerator.floor(findPerciseY(blockX, blockZ));
	}
	
	// macro slots
	private final static int macroRandomGeneratorSlot = 0;
	private final static int macroNSBridgeSlot = 1; 
	
	// micro slots
	private final static int microRandomGeneratorSlot = 0;
	private final static int microRoundaboutSlot = 1; 
	private final static int microIsolatedLotSlot = 3;
	private final static int microCaveSlot = 2; 
	
	public Random getMicroRandomGeneratorAt(int x, int z) {
		double noise = microShape.noise(x * microScale, z * microScale, microRandomGeneratorSlot);
		return new Random((long) (noise * Long.MAX_VALUE));
	}
	
	public Random getMacroRandomGeneratorAt(int x, int z) {
		double noise = macroShape.noise(x * macroScale, z * macroScale, macroRandomGeneratorSlot);
		return new Random((long) (noise * Long.MAX_VALUE));
	}
	
	public boolean getBridgePolarityAt(double chunkX, double chunkZ) {
		return macroBooleanAt(chunkX, chunkZ, macroNSBridgeSlot);
	}

	public boolean isRoundaboutAt(double chunkX, double chunkZ) {
		return microBooleanAt(chunkX, chunkZ, microRoundaboutSlot);
	}
	
	public boolean isSurfaceCaveAt(double chunkX, double chunkZ) {
		return microBooleanAt(chunkX, chunkZ, microCaveSlot);
	}
	
	public boolean isIsolatedBuildingAt(double chunkX, double chunkZ) {
		return isIsolatedLotAt(chunkX, chunkZ, oddsIsolatedBuilding);
	}
	
	public boolean isNotSoIsolatedBuildingAt(double chunkX, double chunkZ) {
		return isIsolatedLotAt(chunkX, chunkZ, oddsIsolatedBuilding / 2);
	}
	
	public boolean isIsolatedLotAt(double chunkX, double chunkZ, double odds) {
		return microScaleAt(chunkX, chunkZ, microIsolatedLotSlot) > odds;
	}
	
	protected boolean macroBooleanAt(double chunkX, double chunkZ, int slot) {
		return macroShape.noise(chunkX * macroScale, chunkZ * macroScale, slot) >= 0.0;
	}
	
	protected boolean microBooleanAt(double chunkX, double chunkZ, int slot) {
		return microShape.noise(chunkX * microScale, chunkZ * microScale, slot) >= 0.0;
	}
	
	protected int macroValueAt(double chunkX, double chunkZ, int slot, int scale) {
		return NoiseGenerator.floor(macroScaleAt(chunkX, chunkZ, slot) * scale);
	}
	
	protected int microValueAt(double chunkX, double chunkZ, int slot, int scale) {
		return NoiseGenerator.floor(microScaleAt(chunkX, chunkZ, slot) * scale);
	}
	
	protected double macroScaleAt(double chunkX, double chunkZ, int slot) {
		return (macroShape.noise(chunkX * macroScale, chunkZ * macroScale, slot) + 1.0) / 2.0;
	}

	protected double microScaleAt(double chunkX, double chunkZ, int slot) {
		return (microShape.noise(chunkX * microScale, chunkZ * microScale, slot) + 1.0) / 2.0;
	}
	
	public boolean getHorizontalNSShaft(int chunkX, int chunkY, int chunkZ) {
		return mineShape.noise(chunkX * mineScale, chunkY * mineScale, chunkZ * mineScale + 0.5) > 0.0;
	}

	public boolean getHorizontalWEShaft(int chunkX, int chunkY, int chunkZ) {
		return mineShape.noise(chunkX * mineScale + 0.5, chunkY * mineScale, chunkZ * mineScale) > 0.0;
	}

	public boolean getVerticalShaft(int chunkX, int chunkY, int chunkZ) {
		return mineShape.noise(chunkX * mineScale, chunkY * mineScale + 0.5, chunkZ * mineScale) > 0.0;
	}

	public boolean notACave(int blockX, int blockY, int blockZ) {

		// cave or not?
		if (settings.includeCaves) {
			double cave = geologyShape.noise(blockX * caveScale, blockY * caveScaleY, blockZ * caveScale);
			return !(cave > caveThreshold || cave < -caveThreshold);
		} else
			return true;
	}
	
	public boolean anyStrataFluid(int blockX, int blockY, int blockZ) {
		double fluid = geologyShape.noise(blockX * strataFluidScale, blockY * strataFluidScale, blockZ * strataFluidScale); // why 50? why not?
		return !(fluid > strataFluidThreshold || fluid < -strataFluidThreshold);
	}

	public byte getOre(ByteChunk byteChunk, int blockX, int blockY, int blockZ, byte defaultId) {

		// ore or not?
		if (settings.includeOres) {
			double ore = oreShape.noise(blockX * oreScale, blockY * oreScaleY, blockZ * oreScale);
			if (ore > oreThreshold || ore < -oreThreshold)
				return byteChunk.getOre(blockY);
			else
				return defaultId;
		} else
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
		Long platkey = Long.valueOf(((long) platX * (long) Integer.MAX_VALUE + (long) platZ));

		// get the right plat
		PlatMap platmap = platmaps.get(platkey);
		
		// doesn't exist? then make it!
		if (platmap == null) {
			
			// what is the context for this one?
			platmap = new PlatMap(this, cornerChunk, platX, platZ);
			
			// remember it for quicker look up
			platmaps.put(platkey, platmap);
		}

		// finally return the plat
		return platmap;
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
			
			// replace random with our chunk specific random 
			Random chunkRandom = getMicroRandomGeneratorAt(chunkX, chunkZ);

			// place to work
			RealChunk realChunk = new RealChunk(chunkGen, chunkRandom, chunk);

			// figure out what everything looks like
			PlatMap platmap = chunkGen.getPlatMap(realChunk, chunkX, chunkZ);
			if (platmap != null) {
				platmap.generateBlocks(realChunk);
			}
		}
	}
}
