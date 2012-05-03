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

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class CityWorldChunkGenerator extends ChunkGenerator {

	private CityWorld plugin;
	private World world;
	private String worldname;
	private String worldstyle;
	
	public SimplexOctaveGenerator terrainShape;
	public SimplexOctaveGenerator terrainNoise;
	
	public CityWorldChunkGenerator(CityWorld aPlugin, String aWorldname, String aWorldstyle) {
		plugin = aPlugin;
		worldname = aWorldname;
		worldstyle = aWorldstyle;
	}
	
	public CityWorld getPlugin() {
		return plugin;
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
	
	private final static double shapeFrequency = 1.00;
	private final static double shapeAmplitude = 1.00;
	private final static double shapeHorizontalScale = 1.0 / 256.0;
	
	private final static double noiseFrequency = 1.50;
	private final static double noiseAmplitude = 0.50;
	private final static double noiseHorizontalScale = 1.0 / 32.0;
	private final static int noiseVerticalScale = 5;
	
	// looks a little crazy but it keeps all the data in check
	private final static int topY = 255;
	private final static int mountainBaseY = 96;
	private final static int mountainScale = topY - mountainBaseY - noiseVerticalScale * 2; 
	private final static int foothillScale = 24;
	private final static int foothillBaseY = mountainBaseY - foothillScale;
	private final static int plainScale = 8;
	private final static int plainBaseY = foothillBaseY - plainScale;
	private final static int shallowScale = 8;
	private final static int shallowBaseY = plainBaseY - shallowScale;
	private final static int seaBaseY = 8;
	private final static int seaScale = shallowBaseY - seaBaseY;
	
	private final static double mountainOdds = 0.25;
	private final static double foothillOdds = 0.15;
	private final static double plainOdds = 0.20;
	private final static double shallowOdds = 0.10;
	private final static double seaOdds = 1.0 - mountainOdds - foothillOdds - plainOdds - shallowOdds;
	
	private final static double mountainMinOdds = 1.0 - mountainOdds;
	private final static double foothillMinOdds = mountainMinOdds - foothillOdds;
	private final static double plainMinOdds = foothillMinOdds - plainOdds;
	private final static double shallowMinOdds = plainMinOdds - shallowOdds;
	
	private final static int snowLevel = topY - 48;
	private final static int treeLevel = snowLevel - 32;
	private final static int waterLevel = 64;
	
	private final static byte snowId = (byte) Material.SNOW_BLOCK.getId();
	private final static byte grassId = (byte) Material.GRASS.getId();
	private final static byte dirtId = (byte) Material.DIRT.getId();
	private final static byte stoneId = (byte) Material.STONE.getId();
	private final static byte waterId = (byte) Material.STATIONARY_WATER.getId();
	private final static byte shallowbedId = (byte) Material.SAND.getId();
	private final static byte seabedId = (byte) Material.SANDSTONE.getId();
	private final static byte bedrockId = (byte) Material.BEDROCK.getId();
	
	private final static byte buildableId = (byte) Material.GLOWSTONE.getId();
	
	@Override
	public byte[][] generateBlockSections(World aWorld, Random random, int chunkX, int chunkZ, BiomeGrid biomes) {
		
		// initialize the shaping logic
		if (world == null) {
			world = aWorld;
			long seed = world.getSeed();
			terrainShape = new SimplexOctaveGenerator(seed, 3);
			terrainShape.setScale(shapeHorizontalScale);
			
			terrainNoise = new SimplexOctaveGenerator(seed + 1, 5);
			terrainNoise.setScale(noiseHorizontalScale);
		}
		
		// place to work
		ByteChunk byteChunk = new ByteChunk(world, chunkX, chunkZ);
		boolean buildableSpot = true;
		
		// shape the world
		for (int x = 0; x < byteChunk.width; x++) {
			for (int z = 0; z < byteChunk.width; z++) {
				
				// compute the world block coordinates
				int blockX = chunkX * byteChunk.width + x;
				int blockZ = chunkZ * byteChunk.width + z;
				
				// shape the noise
				double noise = terrainNoise.noise(blockX, blockZ, noiseFrequency, noiseAmplitude, true);
				int noiseY = NoiseGenerator.floor(noise * noiseVerticalScale);
				
				// shape the shape
				double shape = (1 + terrainShape.noise(blockX, blockZ, shapeFrequency, shapeAmplitude, true)) / 2;
				
				// make a base
				byteChunk.setBlock(x, 0, z, bedrockId);
				
				// scale the shape
				if (shape > mountainMinOdds) { // mountains
					buildableSpot = false;
					
					shape = (shape - mountainMinOdds) / mountainOdds;
					int y = NoiseGenerator.floor(shape * mountainScale) + noiseY * 4 + mountainBaseY;

					// too high?
					if (y > topY)
						y = topY - Math.abs(noiseY * 2);
					
					byteChunk.setBlocks(x, 1, y - noiseVerticalScale, z, stoneId);
					if (y < treeLevel) {
						byteChunk.setBlocks(x, y - noiseVerticalScale, y, z, dirtId);
						byteChunk.setBlock(x, y, z, grassId);
					} else if (y < snowLevel) {
						byteChunk.setBlocks(x, y - noiseVerticalScale, y + 1, z, stoneId);
					} else {
						byteChunk.setBlocks(x, y - noiseVerticalScale, y - 2, z, stoneId);
						byteChunk.setBlocks(x, y - 2, y + 1, z, snowId);
					}
					
				} else if (shape > foothillMinOdds) { // foothills
					buildableSpot = false;
					
					shape = (shape - foothillMinOdds) / foothillOdds;
					int y = NoiseGenerator.floor(shape * foothillScale) + noiseY + foothillBaseY;
					
					// too low?
					if (y < plainBaseY + plainScale)
						y = plainBaseY + plainScale;
					
					byteChunk.setBlocks(x, 1, y - noiseVerticalScale, z, stoneId);
					byteChunk.setBlocks(x, y - noiseVerticalScale, y, z, dirtId);
					byteChunk.setBlock(x, y, z, grassId);
					
				} else if (shape > plainMinOdds) { // plains
					//buildableSpot = true; this is only true if every spot in the cell is actually plains
					
					shape = (shape - plainMinOdds) / plainOdds;
					int y = NoiseGenerator.floor(shape * plainScale) + noiseY / 2 + plainBaseY;
					byteChunk.setBlocks(x, 1, y - noiseVerticalScale, z, stoneId);
					if (y >= waterLevel) {
						byteChunk.setBlocks(x, y - noiseVerticalScale, y, z, dirtId);
						byteChunk.setBlock(x, y, z, grassId);
					} else {
						byteChunk.setBlocks(x, y - noiseVerticalScale, y - 1, z, dirtId);
						byteChunk.setBlocks(x, y - 1, y, z, shallowbedId);
						byteChunk.setBlocks(x, y, waterLevel + 1, z, waterId);
					}
					
				} else if (shape > shallowMinOdds) { // shallows
					buildableSpot = false;
					
					shape = (shape - shallowMinOdds) / shallowOdds;
					int y = NoiseGenerator.floor(shape * shallowScale) + noiseY + shallowBaseY;
					byteChunk.setBlocks(x, 1, y - noiseVerticalScale, z, stoneId);
					byteChunk.setBlocks(x, y - noiseVerticalScale, y - 2, z, seabedId);
					byteChunk.setBlocks(x, y - 2, y, z, shallowbedId);
					byteChunk.setBlocks(x, y, waterLevel + 1, z, waterId);
					
				} else { // oceans
					buildableSpot = false;
					
					shape = shape / seaOdds;
					int y = NoiseGenerator.floor(shape * seaScale) + noiseY + seaBaseY;
					byteChunk.setBlocks(x, 1, y - 2, z, stoneId);
					byteChunk.setBlocks(x, y - 2, y, z, seabedId);
					byteChunk.setBlocks(x, y, waterLevel + 1, z, waterId);
				}
				
				//TODO based on the height we should set the biome
			}
		}
		
		// if everything is buildable then let's show it somehow
		if (buildableSpot) {
			byteChunk.setBlocks(0, plainBaseY, plainBaseY + 30, 0, buildableId);
			byteChunk.setBlocks(15, plainBaseY, plainBaseY + 30, 0, buildableId);
			byteChunk.setBlocks(0, plainBaseY, plainBaseY + 30, 15, buildableId);
			byteChunk.setBlocks(15, plainBaseY, plainBaseY + 30, 15, buildableId);
		}
		
		// figure out what everything looks like
		PlatMap platmap = getPlatMap(chunkX, chunkZ);
		if (platmap != null) {
			platmap.generateChunk(byteChunk);
			platmap.generateBiome(byteChunk, biomes);
		}
		 
		return byteChunk.blocks;
	}

	// ***********
	// manager for handling the city plat maps collection
	private Hashtable<Long, PlatMap> platmaps;
	private PlatMap getPlatMap(int chunkX, int chunkZ) {

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
//		if (platmap == null) {
//			
//			// what is the context for this one?
//			PlatMapContext context = getContext(chunkX, chunkZ);
//			platmap = new PlatMap(context, platX, platZ);
//			
//			// remember it for quicker look up
//			platmaps.put(platkey, platmap);
//		}
		
		// finally return the plat
		return platmap;
	}
	
	private PlatMapContext getContext(int chunkX, int chunkZ) {
		//TODO derive this from the CityNoise generator
//		
//		switch (random.nextInt(20)) {
//		case 0:
//		case 1:
//		case 2:
//		case 3:
//			return new ContextLowrise(plugin, world, random);
//		case 4:
//		case 5:
//		case 6:
//		case 7:
//			return new ContextMidrise(plugin, world, random);
//		case 8:
//		case 9:
//		case 10:
//			return new ContextHighrise(plugin, world, random);
//		case 11:
//		case 12:
//			return new ContextAllPark(plugin, world, random);
//		case 13:
//		case 14:
//			return new ContextMall(plugin, world, random);
//		case 15:
//		case 16:
//		case 17:
//			return new ContextCityCenter(plugin, world, random);
//		case 18:
//		case 19:
//		default:
//			return new ContextUnfinished(plugin, world, random);
//		}
		return null;
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

		private CityWorldChunkGenerator chunkGen;
		
		public CityWorldBlockPopulator(CityWorldChunkGenerator chunkGen){
			this.chunkGen = chunkGen;
		}
		
		@Override
		public void populate(World aWorld, Random aRandom, Chunk aSource) {
			int chunkX = aSource.getX();
			int chunkZ = aSource.getZ();
			
			// place to work
			RealChunk chunk = new RealChunk(aWorld, aSource);
			
			// figure out what everything looks like
			PlatMap platmap = chunkGen.getPlatMap(chunkX, chunkZ);
			if (platmap != null) {
				platmap.generateBlocks(chunk);
			}
		}
	}
}
