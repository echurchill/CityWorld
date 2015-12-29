package me.daddychurchill.CityWorld.Plats.Maze;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class MazeLavaWalledLot extends MazeNatureLot {

	public MazeLavaWalledLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
	}

	@Override
	protected Material getWallMaterial(CityWorldGenerator generator) {
		return Material.STATIONARY_LAVA;
	}
	
	@Override
	protected void generateWallPart(CityWorldGenerator generator, RealBlocks chunk, int x1, int x2, int z1, int z2) {
		int mazeY = generator.streetLevel - mazeDepth;
		chunk.setBlocks(x1, x2, mazeY, mazeY + mazeHeight, z1, z2, getWallMaterial(generator));
	}

	@Override
	protected void generateHallPart(CityWorldGenerator generator, RealBlocks chunk, int x1, int x2, int z1, int z2) {
		int mazeY = generator.streetLevel - mazeDepth;
		chunk.setBlocks(x1, x2, mazeY, generator.streetLevel, z1, z2, getWallMaterial(generator));
		chunk.setBlocks(x1, x2, generator.streetLevel, generator.streetLevel + 1, z1, z2, Material.BARRIER);
	}
	
}
