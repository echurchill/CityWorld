package me.daddychurchill.CityWorld;

import java.util.Random;

import org.bukkit.Material;

public class PlatPark extends PlatLot {

	protected Material[] materials;
	
	public PlatPark(Random rand) {
		super(rand);
		
		// TODO Auto-generated constructor stub
		//TODO define materials, height and depth
	}

	@Override
	public void generateChunk(PlatMap platmap, Chunk chunk, int platX, int platZ) {

		// starting with the bottom
		generateBedrock(chunk, PlatMap.StreetLevel);

		// TODO add cisterns, fountains and fences
		chunk.setLayer(PlatMap.StreetLevel, Material.GRASS);
	}

	public void makeConnected(Random rand, PlatBuilding relative) {
		super.makeConnected(rand, relative);
		
		// other bits

		// copy over the material bits
		if (relative.materials != null) {
			materials = relative.materials.clone();
		}
	}
	
}
