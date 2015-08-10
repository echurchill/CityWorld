package me.daddychurchill.CityWorld.Plats.Astral;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.WorldBlocks;

public class AstralForestCanopyLot extends AstralForestLot {

	public AstralForestCanopyLot(PlatMap platmap, int chunkX, int chunkZ,
			double populationChance) {
		super(platmap, chunkX, chunkZ, populationChance);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void plantTree(CityWorldGenerator generator, WorldBlocks blocks,
			int blockX, int blockY, int blockZ, int snowY) {
		
		// Buried bit
		blocks.setLogs(blockX, blockX + 2, blockY, blockY + snowY, blockZ, blockZ + 2, Material.LOG, TreeSpecies.GENERIC, BlockFace.UP);
		blockY += snowY;
		
		// how tall is it?
		int treeSegment = (generator.seaLevel - blockY) / 4;
		int leavesBottom = blockY + treeSegment;
		int treeTop = blockY + treeSegment + chunkOdds.getRandomInt(treeSegment * 3);
		
		// more trunk!
		blocks.setLogs(blockX, blockX + 2, blockY, treeTop, blockZ, blockZ + 2, Material.LOG, TreeSpecies.GENERIC, BlockFace.UP);
		
		// now add the canopy bit
		for (int x = blockX - 3; x < blockX + 5; x++) {
			for (int z = blockZ - 3; z < blockZ + 5; z++) {
				int c = chunkOdds.getRandomInt(4) + 4;
				switch (chunkOdds.getRandomInt(10)) {
				case 0:
					int maxThickness = treeTop - leavesBottom;
					if (maxThickness > 0) 
						for (int y = chunkOdds.getRandomInt(maxThickness) + leavesBottom; y <= treeTop; y++)
							setLeaf(blocks, x, y, z, c);
					break;
				case 1:
					// no leaves
					break;
				default:
					setLeaf(blocks, x, treeTop, z, c);
				}
			}
		}
	}
}
