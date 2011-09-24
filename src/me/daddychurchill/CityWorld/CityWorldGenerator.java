package me.daddychurchill.CityWorld;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class CityWorldGenerator extends ChunkGenerator {

	@SuppressWarnings("unused")
	private CityWorld plugin;
	
	public CityWorldGenerator(CityWorld instance){
		this.plugin = instance;
	}
	
	@Override
	public Location getFixedSpawnLocation(World world, Random random) {
		// TODO do something smarter!
		return new Location(world, 8, 17, 8);
	}
	
	static final int cityBlockSize = 5;

	@Override
	public byte[] generate(World world, Random random, int chunkX, int chunkZ) {
		Chunk chunk = new Chunk();
		
		// what type of road?
		boolean northsouth = chunkX % cityBlockSize == 0;
		boolean eastwest = chunkZ % cityBlockSize == 0;
		
		// basement
		chunk.setLayer(random, 0, Material.BEDROCK);
		
		// roads?
		if (northsouth || eastwest) {
			chunk.setLayer(random, 15, Material.STONE);
			chunk.setSidewalks(random, 16, Material.STEP, northsouth, eastwest);
		
		// buildings?
		} else {
			
			// foundation
			chunk.setLayer(random, 15, Material.DOUBLE_STEP);
			chunk.setLayer(random, 16, Material.DOUBLE_STEP);
			
			// actual floors
			chunk.setFloors(random, 17, random.nextInt(20) + 1, Material.WOOL);
		}
		
		return chunk.blocks;
	}
}
