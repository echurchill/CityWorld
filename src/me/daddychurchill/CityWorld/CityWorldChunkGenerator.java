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
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class CityWorldChunkGenerator extends ChunkGenerator {

	private CityWorld plugin;
	private String worldname;
	private String worldstyle;
	
	public CityWorldChunkGenerator(CityWorld aPlugin, String name, String style) {
		plugin = aPlugin;
		worldname = name;
		worldstyle = style;
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

	@Override
	public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomes) {
		
		// place to work
		ByteChunk byteChunk = new ByteChunk(world, chunkX, chunkZ);
		
		// figure out what everything looks like
		PlatMap platmap = getPlatMap(world, random, chunkX, chunkZ);
		if (platmap != null) {
			platmap.generateChunk(byteChunk);
			platmap.generateBiome(byteChunk, biomes);
		}
		 
		return byteChunk.blocks;
	}

	// ***********
	// manager for handling the city plat maps collection
	private Hashtable<Long, PlatMap> platmaps;
	private PlatMap getPlatMap(World world, Random random, int chunkX, int chunkZ) {

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
			PlatMapContext context = getContext(world, plugin, random, chunkX, chunkZ);
			platmap = new PlatMap(world, random, context, platX, platZ);
			
			// remember it for quicker look up
			platmaps.put(platkey, platmap);
		}
		
		// finally return the plat
		return platmap;
	}
	
	private PlatMapContext getContext(World world, CityWorld plugin, Random random, int chunkX, int chunkZ) {
		//TODO derive this from the CityNoise generator
		
		switch (random.nextInt(20)) {
		case 0:
		case 1:
		case 2:
		case 3:
			return new ContextLowrise(plugin, world, random);
		case 4:
		case 5:
		case 6:
		case 7:
			return new ContextMidrise(plugin, world, random);
		case 8:
		case 9:
		case 10:
			return new ContextHighrise(plugin, world, random);
		case 11:
		case 12:
			return new ContextAllPark(plugin, world, random);
		case 13:
		case 14:
			return new ContextMall(plugin, world, random);
		case 15:
		case 16:
		case 17:
			return new ContextCityCenter(plugin, world, random);
		case 18:
		case 19:
		default:
			return new ContextUnfinished(plugin, world, random);
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

		private CityWorldChunkGenerator chunkGen;
		
		public CityWorldBlockPopulator(CityWorldChunkGenerator chunkGen){
			this.chunkGen = chunkGen;
		}
		
		@Override
		public void populate(World world, Random random, Chunk source) {
			int chunkX = source.getX();
			int chunkZ = source.getZ();
			
			// place to work
			RealChunk chunk = new RealChunk(world, source);
			
			// figure out what everything looks like
			PlatMap platmap = chunkGen.getPlatMap(world, random, chunkX, chunkZ);
			if (platmap != null) {
				platmap.generateBlocks(chunk);
			}
		}
	}
}
