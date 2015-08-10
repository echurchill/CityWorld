package me.daddychurchill.CityWorld.Plats.Astral;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.WorldBlocks;

public class AstralForestHedgeLot extends AstralForestLot {

	public AstralForestHedgeLot(PlatMap platmap, int chunkX, int chunkZ,
			double populationChance) {
		super(platmap, chunkX, chunkZ, populationChance);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void plantTree(CityWorldGenerator generator, WorldBlocks blocks,
			int blockX, int blockY, int blockZ, int snowY) {
		
		// Buried bit
		blocks.setLogs(blockX, blockY, blockY + snowY, blockZ, Material.LOG, TreeSpecies.GENERIC, BlockFace.UP);
		blockY += snowY;
		
		// how tall is it?
		int treeSegment = (generator.seaLevel - blockY) / 4;
		int leavesBottom = blockY + treeSegment;
		int treeTop = blockY + treeSegment + chunkOdds.getRandomInt(treeSegment * 3);
		
		// more trunk!
		blocks.setLogs(blockX, blockY, treeTop, blockZ, Material.LOG, TreeSpecies.GENERIC, BlockFace.UP);
		
		// now add the hedge bit
		for (int x = blockX - 1; x < blockX + 2; x++) {
			for (int z = blockZ - 1; z < blockZ + 2; z++) {
				for (int y = leavesBottom; y <= treeTop; y++) {
					setLeaf(blocks, x, y, z, chunkOdds.getRandomInt(4) + 4);
				}
			}
		}
	}
}
