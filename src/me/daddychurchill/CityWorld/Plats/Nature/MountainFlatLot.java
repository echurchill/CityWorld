package me.daddychurchill.CityWorld.Plats.Nature;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.ConstructLot;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.PlatMap;

import org.bukkit.Material;

public abstract class MountainFlatLot extends ConstructLot {

	private final static Material retainingWallMaterial = Material.SMOOTH_BRICK;
	
	public MountainFlatLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		style = LotStyle.NATURE;
		trulyIsolated = true;
	}

	protected void generateRetainerLot(CityWorldGenerator generator, InitialBlocks chunk, DataContext context) {
		
		// flatten things out a bit
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {
				int y = getBlockY(x, z);
				
				// add the retaining walls
				if (x == 0 || x == chunk.width - 1 || z == 0 || z == chunk.width - 1) {
					if (y <= blockYs.averageHeight) {
						chunk.setBlocks(x, y - 2, blockYs.averageHeight + 1, z, retainingWallMaterial);
					} else if (y > blockYs.averageHeight) {
						chunk.setBlocks(x, blockYs.averageHeight - 2, y + 1, z, retainingWallMaterial);
					}
				
				// backfill
				} else {
					if (generator.settings.includeDecayedNature) {
						chunk.setBlocks(x, y - 2, blockYs.averageHeight + 1, z, Material.SAND);
					} else {
						chunk.setBlocks(x, y - 2, blockYs.averageHeight, z, generator.oreProvider.subsurfaceMaterial);
						chunk.setBlock(x, blockYs.averageHeight, z, generator.oreProvider.surfaceMaterial); 
					}
					chunk.airoutBlocks(generator, x, blockYs.averageHeight + 1, blockYs.maxHeight + 1, z, true);
				}
			}
		}
	}
	
	private final static int bevelInset = 2;

	protected void generateSmoothedLot(CityWorldGenerator generator, InitialBlocks chunk, DataContext context) {
		
		// blend the edges
		for (int i = 0; i < bevelInset; i++)
			generateSmoothedLotBevel(generator, chunk, context, i);
		
		// flatten things out the center bit
		for (int x = bevelInset; x < chunk.width - bevelInset; x++) {
			for (int z = bevelInset; z < chunk.width - bevelInset; z++) {
				int y = getBlockY(x, z);
				
				if (generator.settings.includeDecayedNature) {
					chunk.setBlocks(x, y - 2, blockYs.averageHeight + 1, z, Material.SAND);
				} else {
					chunk.setBlocks(x, y - 2, blockYs.averageHeight, z, generator.oreProvider.subsurfaceMaterial);
					chunk.setBlock(x, blockYs.averageHeight, z, generator.oreProvider.surfaceMaterial); 
				}
				chunk.airoutBlocks(generator, x, blockYs.averageHeight + 1, blockYs.maxHeight + 1, z, true);
			}
		}
	}

	private void generateSmoothedLotBevel(CityWorldGenerator generator, InitialBlocks chunk, DataContext context, int inset) {

		// Xwise
		for (int x = inset; x < chunk.width - inset; x++) {
			generateBevelBlock(generator, chunk, context, inset, x, inset);
			generateBevelBlock(generator, chunk, context, inset, x, chunk.width - inset - 1);
		}

		// Zwise
		for (int z = inset + 1; z < chunk.width - inset - 1; z++) {
			generateBevelBlock(generator, chunk, context, inset, inset, z);
			generateBevelBlock(generator, chunk, context, inset, chunk.width - inset - 1, z);
		}
	}
	
	private void generateBevelBlock(CityWorldGenerator generator, InitialBlocks chunk, DataContext context, int inset, int x, int z) {
		int y = getBlockY(x, z);
		int y1 = y;
		if (y < blockYs.averageHeight) {
			// build up
			y1 = (blockYs.averageHeight - y) / 2 + y;
			chunk.setBlocks(x, y - 1, y1, z, generator.oreProvider.subsurfaceMaterial);
			chunk.setBlock(x, y1, z, generator.oreProvider.surfaceMaterial);
		} else if (y > blockYs.averageHeight) {
			// trim down
			y1 = (y - blockYs.averageHeight) / 2 + blockYs.averageHeight;
			chunk.setBlocks(x, blockYs.averageHeight - 1, y1, z, generator.oreProvider.subsurfaceMaterial);
			chunk.setBlock(x, y1, z, generator.oreProvider.surfaceMaterial);
			chunk.airoutBlocks(generator, x, y1 + 1, y + 1, z, true);
		}
	}
		
	@Override
	public int getBottomY(CityWorldGenerator generator) {
		return blockYs.averageHeight + 1;
	}
	
	@Override
	public int getTopY(CityWorldGenerator generator) {
		return generator.streetLevel + DataContext.FloorHeight * 2 + 1;
	}

}
