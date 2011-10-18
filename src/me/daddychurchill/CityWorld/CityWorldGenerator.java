package me.daddychurchill.CityWorld;

import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class CityWorldGenerator extends ChunkGenerator {

	@SuppressWarnings("unused")
	private CityWorld plugin;
	public String citystyle;
	
	//TODO Debugging
	protected Logger log = Logger.getLogger("Minecraft");
	
	public CityWorldGenerator(CityWorld instance, String id){
		this.plugin = instance;
		this.citystyle = id;
	}
	
	@Override
	public Location getFixedSpawnLocation(World world, Random random) {
		// TODO do something smarter!
        int x = Chunk.Width / 2;
        int z = Chunk.Width / 2;
        int y = PlatMap.StreetLevel + 1;
        return new Location(world, x, y, z);
	}
	
	@Override
	public byte[] generate(World world, Random random, int chunkX, int chunkZ) {
		
		// place to work
		Chunk chunk = new Chunk(chunkX, chunkZ);
		
		// figure out what everything looks like
		PlatMap platmap = PlatMap.getPlatMap(world, random, chunkX, chunkZ);
		if (platmap != null) {
			platmap.generateChunk(chunk);
		}
		 
		return chunk.blocks;
	}
}
