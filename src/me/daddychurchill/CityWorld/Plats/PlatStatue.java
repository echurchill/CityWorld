package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import me.daddychurchill.CityWorld.PlatMaps.PlatMap;
import me.daddychurchill.CityWorld.Support.Chunk;

import org.bukkit.Material;

public class PlatStatue extends PlatLot {

	protected final static byte brickId = (byte) Material.SMOOTH_BRICK.getId();
	
	public PlatStatue(Random rand) {
		super(rand);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateChunk(PlatMap platmap, Chunk chunk, int platX, int platZ) {

		// starting with the bottom
		generateBedrock(chunk, PlatMap.StreetLevel + 1);

		// TODO add cisterns, fountains and fences
		chunk.setLayer(PlatMap.StreetLevel + 1, brickId);
	}

	public void makeConnected(Random rand, PlatBuilding relative) {
		super.makeConnected(rand, relative);
		
		// other bits
	}
	
}
