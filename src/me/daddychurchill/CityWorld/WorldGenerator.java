package me.daddychurchill.CityWorld;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import me.daddychurchill.CityWorld.Plugins.GroundProvider;
import me.daddychurchill.CityWorld.Plugins.FoliageProvider;
import me.daddychurchill.CityWorld.Plugins.LootProvider;
import me.daddychurchill.CityWorld.Plugins.OdonymProvider;
import me.daddychurchill.CityWorld.Plugins.OreProvider;
import me.daddychurchill.CityWorld.Plugins.ShapeProvider;
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

public class WorldGenerator extends ChunkGenerator {

	private CityWorld plugin;
	private World world;
	private String worldname;
	private String worldstyle;
	private Random connectionKeyGen;
	
	public ShapeProvider shapeProvider;
	public LootProvider lootProvider;
	public SpawnProvider spawnProvider;
	public OreProvider oreProvider;
	public FoliageProvider foliageProvider;
	public OdonymProvider odonymProvider;
	public GroundProvider groundProvider;

	public CityWorldSettings settings;

	public int deepseaLevel;
	public int seaLevel;
	public int sidewalkLevel;
	public int treeLevel;
	public int evergreenLevel;
	public int deciduousRange;
	public int evergreenRange;
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
	
	public double oddsOfIsolatedBuilding = 0.75;
	public double oddsOfRoundabouts = 0.30;
	
	private void initializeWorldInfo(World aWorld) {
		
		// initialize the shaping logic
		if (world == null) {
			world = aWorld;
			settings = new CityWorldSettings(plugin, world);
			long seed = world.getSeed();
			connectionKeyGen = new Random(seed + 1);
			shapeProvider = ShapeProvider.loadProvider(this);
			lootProvider = LootProvider.loadProvider(this);
			spawnProvider = SpawnProvider.loadProvider(this);
			groundProvider = GroundProvider.loadProvider(this, new Random(seed + 2));
			oreProvider = OreProvider.loadProvider(this);
			foliageProvider = FoliageProvider.loadProvider(this, new Random(seed + 3));
			odonymProvider = OdonymProvider.loadProvider(this, new Random(seed + 4));
			
			// get ranges
			height = shapeProvider.getWorldHeight();
			seaLevel = shapeProvider.getSeaLevel();
			landRange = shapeProvider.getLandRange();
			seaRange = shapeProvider.getSeaRange();

			// now the other vertical points
			deepseaLevel = seaLevel - seaRange / 3;
			sidewalkLevel = seaLevel + 1;
			snowLevel = seaLevel + (landRange / 4 * 3);
			evergreenLevel = seaLevel + (landRange / 4 * 2);
			treeLevel = seaLevel + (landRange / 4);
			deciduousRange = evergreenLevel - treeLevel;
			evergreenRange = snowLevel - evergreenLevel;
			
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
	}
	
	@Override
	public byte[][] generateBlockSections(World aWorld, Random random, int chunkX, int chunkZ, BiomeGrid biomes) {
		initializeWorldInfo(aWorld);
		
		// place to work
		ByteChunk byteChunk = new ByteChunk(this, chunkX, chunkZ);
		
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
	
	public double findPerciseY(int blockX, int blockZ) {
		return shapeProvider.findPerciseY(this, blockX, blockZ);
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
	private final static int microSurfaceCaveSlot = 2; 
	
	public Random getMicroRandomGeneratorAt(int x, int z) {
		return new Random((long) (shapeProvider.getMicroNoiseAt(x, z, microRandomGeneratorSlot) * Long.MAX_VALUE));
	}
	
	public Random getMacroRandomGeneratorAt(int x, int z) {
		return new Random((long) (shapeProvider.getMacroNoiseAt(x, z, macroRandomGeneratorSlot) * Long.MAX_VALUE));
	}
	
	public boolean getBridgePolarityAt(double chunkX, double chunkZ) {
		return macroBooleanAt(chunkX, chunkZ, macroNSBridgeSlot);
	}

	public boolean isRoundaboutAt(double chunkX, double chunkZ) {
		return settings.includeRoundabouts && 
			   microScaleAt(chunkX, chunkZ, microRoundaboutSlot) < oddsOfRoundabouts;
	}
	
	public boolean isSurfaceCaveAt(double chunkX, double chunkZ) {
		return microBooleanAt(chunkX, chunkZ, microSurfaceCaveSlot);
	}
	
	public boolean isIsolatedBuildingAt(double chunkX, double chunkZ) {
		return isIsolatedLotAt(chunkX, chunkZ, oddsOfIsolatedBuilding);
	}
	
	public boolean isNotSoIsolatedBuildingAt(double chunkX, double chunkZ) {
		return isIsolatedLotAt(chunkX, chunkZ, oddsOfIsolatedBuilding / 2);
	}
	
	public boolean isIsolatedLotAt(double chunkX, double chunkZ, double odds) {
		return microScaleAt(chunkX, chunkZ, microIsolatedLotSlot) > odds;
	}
	
	protected boolean macroBooleanAt(double chunkX, double chunkZ, int slot) {
		return shapeProvider.getMacroNoiseAt(chunkX, chunkZ, slot) >= 0.0;
	}
	
	protected boolean microBooleanAt(double chunkX, double chunkZ, int slot) {
		return shapeProvider.getMicroNoiseAt(chunkX, chunkZ, slot) >= 0.0;
	}
	
	protected int macroValueAt(double chunkX, double chunkZ, int slot, int scale) {
		return NoiseGenerator.floor(macroScaleAt(chunkX, chunkZ, slot) * scale);
	}
	
	protected int microValueAt(double chunkX, double chunkZ, int slot, int scale) {
		return NoiseGenerator.floor(microScaleAt(chunkX, chunkZ, slot) * scale);
	}
	
	protected double macroScaleAt(double chunkX, double chunkZ, int slot) {
		return (shapeProvider.getMacroNoiseAt(chunkX, chunkZ, slot) + 1.0) / 2.0;
	}

	protected double microScaleAt(double chunkX, double chunkZ, int slot) {
		return (shapeProvider.getMicroNoiseAt(chunkX, chunkZ, slot) + 1.0) / 2.0;
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
		public void populate(World aWorld, Random random, Chunk chunk) {
			chunkGen.initializeWorldInfo(aWorld);
			
			// where are we?
			int chunkX = chunk.getX();
			int chunkZ = chunk.getZ();
			
			// place to work
			RealChunk realChunk = new RealChunk(chunkGen, chunk);

			// figure out what everything looks like
			PlatMap platmap = chunkGen.getPlatMap(realChunk, chunkX, chunkZ);
			if (platmap != null) {
				platmap.generateBlocks(realChunk);
			}
		}
	}
}
