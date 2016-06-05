package me.daddychurchill.CityWorld.Plats.Astral;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import me.daddychurchill.CityWorld.Support.BadMagic.General;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class AstralBlackCubesLot extends AstralNatureLot {

	public AstralBlackCubesLot(PlatMap platmap, int chunkX, int chunkZ, double populationChance) {
		super(platmap, chunkX, chunkZ, populationChance);
		// TODO Auto-generated constructor stub
	}
	
	private final static int cubeWidth = 6;

	@Override
	protected void generateActualBlocks(CityWorldGenerator generator,
			PlatMap platmap, RealBlocks chunk, DataContext context, int platX,
			int platZ) {
		
		for (int x = 1; x < 16; x = x + cubeWidth + 2) {
			for (int z = 1; z < 16; z = z + cubeWidth + 2) {
				if (chunkOdds.playOdds(populationChance)) {
					int y = getBlockY(x + 1, z + 1);
					if (y > cubeWidth && y < generator.seaLevel - cubeWidth)
						generateBlackCube(generator, chunk, x, y - 1, z);
				}
			}
		}
	}
	
	private void generateBlackCube(CityWorldGenerator generator, RealBlocks chunk, int x, int y, int z) {
		
//		chunk.setBlocks(x, x + cubeWidth, y + cubeWidth - 1, z, z + cubeWidth, Material.OBSIDIAN);
//		chunk.setWalls(x, x + cubeWidth, y + 1, y + cubeWidth - 1, z, z + cubeWidth, Material.OBSIDIAN);
		chunk.setWalls(x, x + cubeWidth, y + 1, y + cubeWidth, z, z + cubeWidth, Material.OBSIDIAN);
		chunk.setBlocks(x, x + cubeWidth, y, z, z + cubeWidth, Material.OBSIDIAN);
		chunk.setBlocks(x + 2, x + cubeWidth - 2, y - 8, y, z + 2, z + cubeWidth - 2, Material.OBSIDIAN);
		
		
		switch (chunkOdds.getRandomInt(4)) {
		case 0:
			chunk.setBlocks(x + 1, x + cubeWidth - 1, y + 1, y + cubeWidth - 2, z + 1, z + cubeWidth - 1, Material.AIR);
			if (chunkOdds.playOdds(populationChance))
				chunk.setChest(generator, x + 3, y + 1, z + 3, General.NORTH, chunkOdds, generator.lootProvider, LootLocation.RANDOM);
			if (chunkOdds.playOdds(populationChance))
				chunk.setChest(generator, x + 2, y + 1, z + 2, General.NORTH, chunkOdds, generator.lootProvider, LootLocation.RANDOM);
			break;
		case 1:
			chunk.setBlocks(x + 1, x + cubeWidth - 1, y + 1, y + cubeWidth - 2, z + 1, z + cubeWidth - 1, 
					generator.materialProvider.itemsSelectMaterial_AstralCubeOres.getRandomMaterial(chunkOdds, Material.STONE));
			break;
		default:
			chunk.setBlocks(x + 1, x + cubeWidth - 1, y + 1, y + 3, z + 1, z + cubeWidth - 1, Material.TNT);
			chunk.setBlocks(x + 1, x + cubeWidth - 1, y + 3, y + 4, z + 1, z + cubeWidth - 1, Material.STONE);
			chunk.setBlocks(x + 1, x + cubeWidth - 1, y + 4, y + 5, z + 1, z + cubeWidth - 1, Material.WOOD_PLATE);

			chunk.setDoPhysics(true);
			chunk.setBlock(x + 1, y + 4, z + 1, Material.GLOWSTONE);
			chunk.setBlock(x + cubeWidth - 2, y + 4, z + 1, Material.GLOWSTONE);
			chunk.setBlock(x + 1, y + 4, z + cubeWidth - 2, Material.GLOWSTONE);
			chunk.setBlock(x + cubeWidth - 2, y + 4, z + cubeWidth - 2, Material.GLOWSTONE);
			chunk.setDoPhysics(false);
			
			break;
		}

		chunk.setSlabs(x + 1, x + cubeWidth - 1, y + 5, y + 6, z + 1, z + cubeWidth - 1, Material.NETHER_BRICK, false);
		chunk.setBlock(x + 1, y + 5, z + 1, Material.OBSIDIAN);
		chunk.setBlock(x + cubeWidth - 2, y + 5, z + 1, Material.OBSIDIAN);
		chunk.setBlock(x + 1, y + 5, z + cubeWidth - 2, Material.OBSIDIAN);
		chunk.setBlock(x + cubeWidth - 2, y + 5, z + cubeWidth - 2, Material.OBSIDIAN);
	}
	
}
