package me.daddychurchill.CityWorld.Plats.Maze;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.NatureLot;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.MazeArray;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.MazeArray.MazeBit;

public class MazeNatureLot extends NatureLot {

	public MazeNatureLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void generateActualChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {
	}
	
	protected final static int mazeWidth = 11; 
	protected final static int mazeHeight = 12;
	protected final static int mazeDepth = 3;
	protected final static Material wallMaterial = Material.OBSIDIAN;
	
	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, DataContext context, int platX, int platZ) {
		
		// new maze please
		MazeArray maze = new MazeArray(chunkOdds, mazeWidth, mazeWidth);
		for (int m = 1; m < mazeWidth - 2; m++) {
			for (int n = 1; n < mazeWidth - 2; n++) {
				int x1 = m * 2 - 2;
				int z1 = n * 2 - 2;
				if (maze.getBit(m, n) == MazeBit.WALL) {
					generateWallPart(generator, chunk, x1, x1 + 2, z1, z1 + 2);
				} else {
					generateHallPart(generator, chunk, x1, x1 + 2, z1, z1 + 2);
				}
			}
		}

		if (blockYs.averageHeight > generator.streetLevel + mazeHeight)
			generateSurface(generator, chunk, true);
	}
	
	protected void generateWallPart(WorldGenerator generator, RealChunk chunk, int x1, int x2, int z1, int z2) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				int my = generator.streetLevel + mazeHeight;
				int y = Math.min(my, getBlockY(x, z));
				chunk.setBlocks(x, y - mazeDepth, y + mazeHeight - mazeDepth, z, wallMaterial);
			}
		}
	}

	protected void generateHallPart(WorldGenerator generator, RealChunk chunk, int x1, int x2, int z1, int z2) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				int my = generator.streetLevel + mazeHeight;
				int y = getBlockY(x, z);
				
				// underlayment to screw with diggers
				chunk.setBlock(x, Math.min(my, y) - mazeDepth, z, wallMaterial);
				
				// make room in the mountains
				if (y >= my)
					chunk.setBlocks(x, my, my + 3 + chunkOdds.getRandomInt(3), z, Material.AIR);
			}
		}
	}
}
