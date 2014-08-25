package me.daddychurchill.CityWorld.Plats.Astral;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class AstralBlackCubesLot extends AstralNatureLot {

	public AstralBlackCubesLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
	}
	
	private final static int cubeWidth = 6;

	@Override
	protected void generateActualBlocks(WorldGenerator generator,
			PlatMap platmap, RealChunk chunk, DataContext context, int platX,
			int platZ) {
		
		double oddsOfCube = Odds.oddsPrettyLikely;
		if (platX == 0 && platZ == 0 && platX == PlatMap.Width - 1 && platZ == PlatMap.Width - 1)
			oddsOfCube = Odds.oddsSomewhatLikely;

		for (int x = 1; x < 16; x = x + cubeWidth + 2) {
			for (int z = 1; z < 16; z = z + cubeWidth + 2) {
				if (chunkOdds.playOdds(oddsOfCube)) {
					int y = getBlockY(x + 1, z + 1);
					if (y > cubeWidth && y < generator.seaLevel - cubeWidth)
						generateBlackCube(chunk, x, y - 1, z);
				}
			}
		}
	}
	
	private void generateBlackCube(RealChunk chunk, int x, int y, int z) {
		chunk.setBlocks(x + 2, x + cubeWidth - 2, y - 2, y, z + 2, z + cubeWidth - 2, Material.OBSIDIAN);
		chunk.setBlocks(x, x + cubeWidth, y, y + cubeWidth, z, z + cubeWidth, Material.OBSIDIAN);
		
		chunk.setBlocks(x + 1, x + cubeWidth - 1, y + 1, y + cubeWidth - 1, z + 1, z + cubeWidth - 1, Material.AIR);
		
		//TODO put something in the boxes
	}
		
}
