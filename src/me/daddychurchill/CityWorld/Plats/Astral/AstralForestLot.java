package me.daddychurchill.CityWorld.Plats.Astral;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.BlackMagic;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.WorldBlocks;

public abstract class AstralForestLot extends AstralNatureLot {

	public AstralForestLot(PlatMap platmap, int chunkX, int chunkZ,
			double populationChance) {
		super(platmap, chunkX, chunkZ, populationChance);
		// TODO Auto-generated constructor stub
	}

	final static int maxTrees = 9;
	final static int maxHeight = 18;
	final static int minHeight = maxHeight / 2;
	
	protected abstract void plantTree(CityWorldGenerator generator, WorldBlocks blocks,
			int blockX, int blockY, int blockZ, int snowY);
	
	@Override
	protected void generateActualBlocks(CityWorldGenerator generator,
			PlatMap platmap, RealBlocks chunk, DataContext context, int platX,
			int platZ) {
		
		WorldBlocks blocks = new WorldBlocks(generator, chunkOdds);
		for (int i = 0; i < maxTrees; i++) {
			if (chunkOdds.playOdds(populationChance)) {
				int x = chunkOdds.getRandomInt(4) * 4;
				int z = chunkOdds.getRandomInt(4) * 4;
				int y = getSurfaceAtY(x, z);
				
				if (y > 0) {
					int blockY = y;
					
					// go up until we get just past the stratum
					while (chunk.isType(x, blockY, z, generator.oreProvider.subsurfaceMaterial)) {
						blockY++;
					}
					
					// go down until we get to the stratum
					while (!chunk.isType(x, blockY, z, generator.oreProvider.subsurfaceMaterial)) {
						blockY--;
						if (blockY == 1 || blockY < y - 5)
							break;
					}
					
					// move up one little bit
					blockY++;
					
					// now count how much snow is sitting on top
					int snowY = 0;
					while (chunk.isType(x, blockY + snowY, z, Material.SNOW_BLOCK))
						snowY++;
					
					// too far?
					if (blockY + snowY + maxHeight <= generator.seaLevel) {
						int blockX = chunk.getBlockX(x);
						int blockZ = chunk.getBlockZ(z);
					
						plantTree(generator, blocks, blockX, blockY, blockZ, snowY);
					}
				}
			}
		}
	}

	protected void setLeaves(WorldBlocks blocks, int x1, int x2, int y, int z1, int z2, int data) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				setLeaf(blocks, x, y, z, data);
			}
		}
	}
	
	protected void setLeaf(WorldBlocks blocks, int x, int y, int z, int data) {
		if (blocks.isEmpty(x, y, z))
			BlackMagic.setBlock(blocks, x, y, z, Material.LEAVES, data);
	}
}
