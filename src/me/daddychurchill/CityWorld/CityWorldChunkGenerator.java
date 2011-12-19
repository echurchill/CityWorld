package me.daddychurchill.CityWorld;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import me.daddychurchill.CityWorld.PlatMaps.PlatMap;
import me.daddychurchill.CityWorld.Support.ByteChunk;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class CityWorldChunkGenerator extends ChunkGenerator {

	//private CityWorld plugin;
	public String worldname;
	public String worldstyle;
	
	public CityWorldChunkGenerator(CityWorld instance, String name, String style){
		//this.plugin = instance;
		this.worldname = name;
		this.worldstyle = style;
	}
	
	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		return Arrays.asList((BlockPopulator) new CityWorldBlockPopulator());
	}

	@Override
	public Location getFixedSpawnLocation(World world, Random random) {
		// see if this works any better (borrowed from ExpansiveTerrain)
		int x = random.nextInt(250) - 250;
		int z = random.nextInt(250) - 250;
		int y = Math.max(world.getHighestBlockYAt(x, z), PlatMap.StreetLevel + 1);
		return new Location(world, x, y, z);
	}
	
	@Override
	public byte[] generate(World world, Random random, int chunkX, int chunkZ) {
		
		// place to work
		ByteChunk byteChunk = new ByteChunk(chunkX, chunkZ);
		
		// figure out what everything looks like
		PlatMap platmap = PlatMap.getPlatMap(world, random, chunkX, chunkZ);
		if (platmap != null) {
			platmap.generateChunk(byteChunk);
		}
		 
		return byteChunk.blocks;
	}
}
