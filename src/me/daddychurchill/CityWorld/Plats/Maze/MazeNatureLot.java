package me.daddychurchill.CityWorld.Plats.Maze;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.NatureLot;
import me.daddychurchill.CityWorld.Plugins.CoverProvider.CoverageSets;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.MazeArray;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.MazeArray.MazeBit;

public class MazeNatureLot extends NatureLot {

	public MazeNatureLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void generateActualChunk(CityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {
	}
	
	protected final static int mazeWidth = 11; 
	protected final static int mazeHeight = 12;
	protected final static int mazeDepth = 3;
	
	protected Material getWallMaterial(CityWorldGenerator generator) {
		return generator.materialProvider.itemsMaterialListFor_MazeWalls.getNthMaterial(0, Material.OBSIDIAN);
	}
	
	protected Material getLaymentMaterial(CityWorldGenerator generator) {
		return generator.materialProvider.itemsMaterialListFor_MazeWalls.getNthMaterial(1, Material.OBSIDIAN);
	}
	
	@Override
	protected void generateActualBlocks(CityWorldGenerator generator, PlatMap platmap, RealBlocks chunk, DataContext context, int platX, int platZ) {
		
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

		generateEntities(generator, chunk);
	}
	
	protected void generateWallPart(CityWorldGenerator generator, RealBlocks chunk, int x1, int x2, int z1, int z2) {
		Material wallMaterial = getWallMaterial(generator);
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {

				int surfaceY = getBlockY(x, z);
				int mazeY = Math.min(generator.streetLevel, surfaceY);
				chunk.setBlocks(x, mazeY - mazeDepth, mazeY + mazeHeight - mazeDepth + chunkOdds.getRandomInt(1), z, wallMaterial);
			}
		}
	}

	protected void generateHallPart(CityWorldGenerator generator, RealBlocks chunk, int x1, int x2, int z1, int z2) {
		Material wallMaterial = getWallMaterial(generator);
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {

				int surfaceY = getBlockY(x, z);
				int mazeY = Math.min(generator.streetLevel, surfaceY);
				CoverageSets flowers = CoverageSets.SHORT_FLOWERS;
				
				// carve out room under the mountains
				if (surfaceY > mazeY) {
					if (chunk.isEmpty(x, mazeY, z))
						chunk.setBlocks(x, mazeY - mazeDepth + 1, mazeY + 1, z, Material.STONE);
					chunk.setBlocks(x, mazeY + 1, mazeY + 4 + chunkOdds.getRandomInt(3), z, Material.AIR);
					flowers = CoverageSets.SHORT_MUSHROOMS;
				}
				
				// underlayment to screw with diggers
				if (chunkOdds.playOdds(Odds.oddsExtremelyUnlikely)) {
					if (chunk.isEmpty(x, mazeY + 1, z) && !chunk.isEmpty(x, mazeY, z))
						generator.coverProvider.generateCoverage(generator, chunk, x, mazeY + 1, z, flowers);
				} else
					chunk.setBlock(x, mazeY - mazeDepth, z, wallMaterial);
			}
		}
	}
}
