package me.daddychurchill.CityWorld;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class CityWorldGenerator extends ChunkGenerator {

	@SuppressWarnings("unused")
	private CityWorld plugin;
	public String citystyle;
	
	public CityWorldGenerator(CityWorld instance, String id){
		this.plugin = instance;
		this.citystyle = id;
	}

	static final int cityblocksize = 5;
	static final int citystreetat = 15;
	
	@Override
	public Location getFixedSpawnLocation(World world, Random random) {
		// TODO do something smarter!
		return new Location(world, 8, citystreetat + Chunk.foundationheight + 1, 8);
	}

	@Override
	public byte[] generate(World world, Random random, int chunkX, int chunkZ) {
		Chunk chunk = new Chunk();
		
		// what type of road?
		boolean northsouth = chunkX % cityblocksize == 0;
		boolean eastwest = chunkZ % cityblocksize == 0;
		
		// underlayment
		chunk.setLayer(random, 0, Material.BEDROCK);
		
		// TODO Need to do something MUCH smarter
		// TODO Multiple width buildings
		
		// roads?
		if (northsouth || eastwest) {
			chunk.setStreet(world, random, citystreetat, northsouth, eastwest);
		
		// park? (about one per city block, on average)
		} else if (random.nextInt((cityblocksize - 1) * (cityblocksize - 1)) == 0) {
			chunk.setPark(world, random, citystreetat);
		
		// building...
		} else {
			chunk.setBuilding(world, random, citystreetat);
		}
		
		return chunk.blocks;
	}
}
