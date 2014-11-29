package me.daddychurchill.CityWorld.Plats.Maze;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.NatureLot;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class MazeNatureLot extends NatureLot {

	public MazeNatureLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void generateActualChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {
	}
	
	private static int wallHeight = 20;
	
	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, DataContext context, int platX, int platZ) {
		
		if (blockYs.averageHeight > generator.streetLevel - wallHeight && 
			blockYs.averageHeight < generator.streetLevel + wallHeight) {
			chunk.setWalls(0, 16, blockYs.averageHeight - 2, blockYs.averageHeight + wallHeight - 2, 0, 16, Material.OBSIDIAN);
//			chunk.setBlocks(0, getBlockY(0, 0), getBlockY(0, 0) + wallHeight, 0, Material.OBSIDIAN);
//			chunk.setBlocks(0, getBlockY(0, 15), getBlockY(0, 15) + wallHeight, 15, Material.OBSIDIAN);
//			chunk.setBlocks(15, getBlockY(15, 0), getBlockY(15, 0) + wallHeight, 0, Material.OBSIDIAN);
//			chunk.setBlocks(15, getBlockY(15, 15), getBlockY(15, 15) + wallHeight, 15, Material.OBSIDIAN);
		} else {
//			generator.reportMessage("calling generateSurface");
			generateSurface(generator, chunk, true);
		}
		
//		MazeArray floor = new MazeArray(chunkOdds, mazeWidth, mazeWidth);
		
	}

}
