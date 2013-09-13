package me.daddychurchill.CityWorld;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import me.daddychurchill.CityWorld.Clipboard.PasteProvider;
import me.daddychurchill.CityWorld.Plugins.BalloonProvider;
import me.daddychurchill.CityWorld.Plugins.FoliageProvider;
import me.daddychurchill.CityWorld.Plugins.HouseProvider;
import me.daddychurchill.CityWorld.Plugins.LootProvider;
import me.daddychurchill.CityWorld.Plugins.OdonymProvider;
import me.daddychurchill.CityWorld.Plugins.OreProvider;
import me.daddychurchill.CityWorld.Plugins.ShapeProvider;
import me.daddychurchill.CityWorld.Plugins.SpawnProvider;
import me.daddychurchill.CityWorld.Plugins.SurfaceProvider;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.WorldBlocks;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class WorldGenerator extends ChunkGenerator {

	private CityWorld plugin;
	private World world;
	private Long worldSeed;
	private Odds connectionKeyGen;
	
	public String worldName;
	public WorldStyle worldStyle;
	public Environment worldEnvironment;
	
	public ShapeProvider shapeProvider;
	public PasteProvider pasteProvider;
	public LootProvider lootProvider;
	public SpawnProvider spawnProvider;
	public OreProvider oreProvider;
	public SurfaceProvider surfaceProvider;
	public FoliageProvider foliageProvider;
	public OdonymProvider odonymProvider;
	public BalloonProvider balloonProvider;
	public HouseProvider houseProvider;
	
	public WorldBlocks decayBlocks;
	
	public CityWorldSettings settings;

	public int streetLevel;
	
	public int deepseaLevel;
	public int seaLevel;
	public int structureLevel;
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
	
	public enum WorldStyle {
		FLOATING,		// very low terrain with floating houses and cities
		FLOODED,		// traditional terrain and cities but with raised sea level
		SNOWDUNES,		// traditional terrain and cities but covered with snow dunes
		SANDDUNES,		// traditional terrain and cities but covered with sand dunes
		//UNDERWATER,	// traditional terrain with raised sea level with under water cities
		//WESTERN,		// desert landscape with sparse western styled towns and ranches
		//UNDERGROUND,	// elevated terrain with underground cities
		//MINING,		// elevated terrain with very shallow mines and very small towns
		//LUNAR,		// lunar landscape with lunar bases
		//NETHER,		// nether landscape with destroyed cities
		//DESTROYED,	// normal landscape with destroyed cities
		//THE_END,		// stark landscape with smaller light colored cities
		NORMAL};   		// traditional terrain and cities
	
	public WorldGenerator(CityWorld plugin, String worldName, String worldStyle) {
		this.plugin = plugin;
		this.worldName = worldName;
		this.worldStyle = WorldStyle.NORMAL;
		
		// parse the style string
		if (worldStyle != null) {
			try {
				this.worldStyle = WorldStyle.valueOf(worldStyle.trim().toUpperCase());
			} catch (IllegalArgumentException e) {
				reportMessage("[Generator] Unknown world style " + worldStyle + ", switching to NORMAL");
				this.worldStyle = WorldStyle.NORMAL;
			}
		}
	}

	public CityWorld getPlugin() {
		return plugin;
	}
	
	public String getPluginName() {
		return plugin.getPluginName();
	}
	
	public World getWorld() {
		return world;
	}

	public Long getWorldSeed() {
		return worldSeed;
	}

	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		return Arrays.asList((BlockPopulator) new CityWorldBlockPopulator(this));
	}
	
	public void initializeWorldInfo(World aWorld) {
		
		// initialize the shaping logic
		if (world == null) {
			world = aWorld;
			settings = new CityWorldSettings(this);
			worldSeed = world.getSeed();
			connectionKeyGen = new Odds(worldSeed + 1);

			shapeProvider = ShapeProvider.loadProvider(this, new Odds(worldSeed + 2));
			lootProvider = LootProvider.loadProvider(this);
			spawnProvider = SpawnProvider.loadProvider(this);
			oreProvider = OreProvider.loadProvider(this);
			foliageProvider = FoliageProvider.loadProvider(this, new Odds(worldSeed + 3));
			odonymProvider = OdonymProvider.loadProvider(this, new Odds(worldSeed + 4));
			surfaceProvider = SurfaceProvider.loadProvider(this, new Odds(worldSeed + 5));
			balloonProvider = BalloonProvider.loadProvider(this);
			houseProvider = HouseProvider.loadProvider(this);
			decayBlocks = new WorldBlocks(this, new Odds(worldSeed + 6));
			pasteProvider = PasteProvider.loadProvider(this);
			
			// get ranges and contexts
			height = shapeProvider.getWorldHeight();
			seaLevel = shapeProvider.getSeaLevel();
			landRange = shapeProvider.getLandRange();
			seaRange = shapeProvider.getSeaRange();
			structureLevel = shapeProvider.getStructureLevel();
			streetLevel = shapeProvider.getStreetLevel();
			
			// did we load any schematics?
			pasteProvider.reportStatus(this);

			// now the other vertical points
			deepseaLevel = seaLevel - seaRange / 3;
			snowLevel = seaLevel + (landRange / 4 * 3);
			evergreenLevel = seaLevel + (landRange / 4 * 2);
			treeLevel = seaLevel + (landRange / 4);
			deciduousRange = evergreenLevel - treeLevel;
			evergreenRange = snowLevel - evergreenLevel;
			
//				// seabed = 35 deepsea = 50 sea = 64 sidewalk = 65 tree = 110 evergreen = 156 snow = 202 top = 249
//				CityWorld.reportMessage("seabed = " + (seaLevel - seaRange) + 
//								        " deepsea = " + deepseaLevel + 
//								        " sea = " + seaLevel + 
//								        " sidewalk = " + sidewalkLevel + 
//								        " tree = " + treeLevel + 
//								        " evergreen = " + evergreenLevel + 
//								        " snow = " + snowLevel + 
//								        " top = " + (seaLevel + landRange));
			
			// get the connectionKeys
			connectedKeyForPavedRoads = connectionKeyGen.getRandomLong();
			connectedKeyForParks = connectionKeyGen.getRandomLong();
			
//			reportMessage("Plugins...");
//			PluginManager pm = Bukkit.getServer().getPluginManager();
//			Plugin[] plugins = pm.getPlugins();
//			for (Plugin plugin: plugins) {
//				reportMessage("Plugin = " + plugin.getName());
//			}
		}
	}
	
	@Override
	public byte[][] generateBlockSections(World aWorld, Random random, int chunkX, int chunkZ, BiomeGrid biomes) {
		try {

			initializeWorldInfo(aWorld);

			// place to work
			ByteChunk byteChunk = new ByteChunk(this, chunkX, chunkZ);
		
			// figure out what everything looks like
			PlatMap platmap = getPlatMap(chunkX, chunkZ);
			if (platmap != null) {
				//CityWorld.reportMessage("generate X,Z = " + chunkX + "," + chunkZ);
				platmap.generateChunk(byteChunk, biomes);
			}
			
			// This was added by Sablednah
			// https://github.com/echurchill/CityWorld/pull/5
			// MOVED to the chunk populator by DaddyChurchill 10/27/12
			//CityWorldEvent event = new CityWorldEvent(chunkX, chunkZ, platmap.context, platmap.getPlatLots()[chunkX - platmap.originX][chunkZ - platmap.originZ]);
			//Bukkit.getServer().getPluginManager().callEvent(event);
			
			return byteChunk.blocks;
			
		} catch (Exception e) {
			reportException("ChunkPopulator FAILED", e);
			return null;
		} 
	}
	
//	@Override
//	public short[][] generateExtBlockSections(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomes) {
//		try {
//
//			initializeWorldInfo(world);
//
//			// place to work
//			ShortChunk shortChunk = new ShortChunk(this, chunkX, chunkZ);
//		
//			// figure out what everything looks like
//			PlatMap platmap = getPlatMap(chunkX, chunkZ);
//			if (platmap != null) {
//				//CityWorld.reportMessage("generate X,Z = " + chunkX + "," + chunkZ);
//				platmap.generateChunk(shortChunk, biomes);
//			}
//			
//			// This was added by Sablednah
//			// https://github.com/echurchill/CityWorld/pull/5
//			// MOVED to the chunk populator by DaddyChurchill 10/27/12
//			//CityWorldEvent event = new CityWorldEvent(chunkX, chunkZ, platmap.context, platmap.getPlatLots()[chunkX - platmap.originX][chunkZ - platmap.originZ]);
//			//Bukkit.getServer().getPluginManager().callEvent(event);
//			
//			return shortChunk.blocks;
//			
//		} catch (Exception e) {
//			reportException("ChunkPopulator FAILED", e);
//			return null;
//		} 
//	}

	public long getConnectionKey() {
		return connectionKeyGen.getRandomLong();
	}
	
	public int getFarBlockY(int blockX, int blockZ) {
		return shapeProvider.findBlockY(this, blockX, blockZ);
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
	public PlatMap getPlatMap(int chunkX, int chunkZ) {

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
			platmap = new PlatMap(this, shapeProvider, platX, platZ);
			
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
	
	public void reportMessage(String message) {
		plugin.reportMessage(message);
	}

	public void reportMessage(String message1, String message2) {
		plugin.reportMessage(message1, message2);
	}

	public void reportException(String message, Exception e) {
		plugin.reportException(message, e);
	}

	private class CityWorldBlockPopulator extends BlockPopulator {

		private WorldGenerator chunkGen;

		public CityWorldBlockPopulator(WorldGenerator chunkGen) {
			this.chunkGen = chunkGen;
		}

		@Override
		public void populate(World aWorld, Random random, Chunk chunk) {
			try {

				chunkGen.initializeWorldInfo(aWorld);
				
				// where are we?
				int chunkX = chunk.getX();
				int chunkZ = chunk.getZ();
				
				// place to work
				RealChunk realChunk = new RealChunk(chunkGen, chunk);

				// figure out what everything looks like
				PlatMap platmap = chunkGen.getPlatMap(chunkX, chunkZ);
				if (platmap != null) {
					platmap.generateBlocks(realChunk);
					
					// finalize things
					chunkGen.lootProvider.saveLoots();
					
					// Originally by Sablednah
					// Moved and modified a bit by DaddyChurchill
					CityWorldEvent event = new CityWorldEvent(chunk, platmap, platmap.getMapLot(chunkX, chunkZ));
					Bukkit.getServer().getPluginManager().callEvent(event);
				}
			} catch (Exception e) {
				reportException("BlockPopulator FAILED", e);
			} 
		}
	}
}
