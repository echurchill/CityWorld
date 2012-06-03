package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorld;
import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class PlatTower extends PlatIsolated {

	public PlatTower(Random random, PlatMap platmap) {
		super(random, platmap);

	}
	
	private final static int platformWidth = 8;

	private final static Material airMat = Material.AIR;
	private final static Material platformMat = Material.SMOOTH_BRICK;
	private final static Material supportMat = Material.COBBLESTONE;
	
	@Override
	public void generateBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, ContextData context, int platX, int platZ) {
		super.generateBlocks(generator, platmap, chunk, context, platX, platZ);

		// compute offset to start of chunk
		int originX = maxHeightX - platformWidth / 2;
		int originZ = maxHeightZ - platformWidth / 2;
		CityWorld.log.info("Tower @ " + (chunk.worldX + originX) + ", " + (chunk.worldZ + originZ));
		
		// base
		for (int x = originX; x < originX + platformWidth; x++) {
			for (int z = originZ; z < originZ + platformWidth; z++) {
				int y = generator.findBlockY(chunk.worldX + x, chunk.worldZ + z);

				// erase or draw, that is the question
				if (y > maxHeight)
					chunk.setWorldBlocks(x, x + 1, maxHeight, y + 1, z, z + 1, airMat);
				else
					chunk.setWorldBlocks(x, x + 1, y, maxHeight, z, z + 1, supportMat);
			}
		}
		
		// top it off
		chunk.setWorldBlocks(originX, originX + platformWidth, maxHeight, maxHeight + 1, originZ, originZ + platformWidth, platformMat);
	}
}
