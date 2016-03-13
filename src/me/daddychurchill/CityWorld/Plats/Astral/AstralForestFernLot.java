package me.daddychurchill.CityWorld.Plats.Astral;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.BlockFace;
import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.WorldBlocks;

public class AstralForestFernLot extends AstralForestLot {

	public AstralForestFernLot(PlatMap platmap, int chunkX, int chunkZ,
			double populationChance) {
		super(platmap, chunkX, chunkZ, populationChance);
		// TODO Auto-generated constructor stub
	}
	
	protected final static int lightLeaves = 6;
	protected final static int darkLeaves = 7;

	@Override
	protected void plantTree(CityWorldGenerator generator, WorldBlocks blocks,
			int blockX, int blockY, int blockZ, int snowY) {
		
		// Buried bit
		blocks.setLogs(blockX, blockY, blockY + snowY, blockZ, Material.LOG, TreeSpecies.GENERIC, BlockFace.UP);
		blockY += snowY;
		
		// how tall is it?
		int treeSegment = (generator.seaLevel - blockY) / 3;
		int leavesBottom = blockY + treeSegment;
		int treeTop = blockY + treeSegment + chunkOdds.getRandomInt(treeSegment * 2);
		
		// more trunk!
		blocks.setLogs(blockX, blockY, treeTop, blockZ, Material.LOG, TreeSpecies.GENERIC, BlockFace.UP);
		
		// now add the fern bit
		setLeaf(blocks, blockX, treeTop + 1, blockZ, lightLeaves);
		setLeaf(blocks, blockX, treeTop, blockZ, darkLeaves);
		int width = 1;
		for (int y = treeTop - 1; y >= leavesBottom; y = y - 2) {
			setLeaves(blocks, blockX - width, blockX + width + 1, 	y, 		blockZ, blockZ + 1, lightLeaves);
			setLeaves(blocks, blockX - width, blockX + width + 1, 	y - 1, 	blockZ, blockZ + 1, darkLeaves);

			setLeaves(blocks, blockX, blockX + 1, 					y, 		blockZ - width, blockZ + width + 1, lightLeaves);
			setLeaves(blocks, blockX, blockX + 1, 					y - 1, 	blockZ - width, blockZ + width + 1, darkLeaves);
			
			width++;
		}
	}
}
