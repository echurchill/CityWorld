package me.daddychurchill.CityWorld;

import java.util.Random;

import me.daddychurchill.CityWorld.PlatMaps.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class CityWorldBlockPopulator extends BlockPopulator {

	private CityWorldChunkGenerator chunkGen;
	
	public CityWorldBlockPopulator(CityWorldChunkGenerator chunkGen){
		this.chunkGen = chunkGen;
	}
	
	@Override
	public void populate(World world, Random random, Chunk source) {
		int chunkX = source.getX();
		int chunkZ = source.getZ();
		
		// place to work
		RealChunk chunk = new RealChunk(source);
		
		// figure out what everything looks like
		PlatMap platmap = chunkGen.getPlatMap(world, random, chunkX, chunkZ);
		if (platmap != null) {
			platmap.generateBlocks(chunk);
		}
	}
}
