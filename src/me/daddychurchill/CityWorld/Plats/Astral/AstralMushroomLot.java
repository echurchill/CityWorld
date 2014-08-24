package me.daddychurchill.CityWorld.Plats.Astral;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.BlackMagic;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.WorldBlocks;

public class AstralMushroomLot extends AstralNatureLot {

	public AstralMushroomLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

	}
	
	protected Material trunkMaterial = Material.HUGE_MUSHROOM_1;
	protected Material outerMaterial = Material.HUGE_MUSHROOM_1;
	protected Material innerMaterial = Material.HUGE_MUSHROOM_2;

	@Override
	protected void generateActualChunk(WorldGenerator generator,
			PlatMap platmap, ByteChunk chunk, BiomeGrid biomes,
			DataContext context, int platX, int platZ) {

	}

	final static int maxMushrooms = 5;
	final static int maxHeight = 18;
	final static int minHeight = maxHeight / 3;
	
	@Override
	protected void generateActualBlocks(WorldGenerator generator,
			PlatMap platmap, RealChunk chunk, DataContext context, int platX,
			int platZ) {
		
		WorldBlocks blocks = new WorldBlocks(generator, chunkOdds);
		for (int i = 0; i < maxMushrooms; i++) {
			int x = chunkOdds.getRandomInt(4) * 4;
			int z = chunkOdds.getRandomInt(4) * 4;
			int y = getSurfaceAtY(x, z);
//			chunk.setBlocks(x - 1, x + 2, y - 1, y, z - 1, z + 2, Material.GLOWSTONE);
			
			if (y > 0) {
				int blockY = y;
				
				// go up until we get just past the stratum
				while (chunk.isType(x, blockY, z, generator.oreProvider.stratumMaterial)) {
					blockY++;
				}
				
				// go down until we get to the stratum
				while (!chunk.isType(x, blockY, z, generator.oreProvider.stratumMaterial)) {
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
				
					plantMushroom(generator, blocks, blockX, blockY, blockZ, snowY);
				}
			}
		}
	}
	
	private void plantMushroom(WorldGenerator generator, WorldBlocks blocks, int blockX, int blockY, int blockZ, int snowY) {
			
		// how tall?
		int heightY = chunkOdds.getRandomInt(Math.min(generator.seaLevel - blockY, maxHeight) - minHeight) + minHeight + snowY;
		
		// nothing here?
		if (blocks.isEmpty(blockX, blockY + snowY + 2, blockZ)) {
		
			// plant the trunk
			BlackMagic.setBlocks(blocks, blockX, blockY, blockY + heightY - 2, blockZ, trunkMaterial, 10);
			
			// spreadout
			drawMushroomLevel(generator, blocks, blockX, blockY + heightY, blockZ, 2, outerMaterial, 14);
			drawMushroomLevel(generator, blocks, blockX, blockY + heightY - 1, blockZ, 3, outerMaterial, 14);
			drawMushroomLevel(generator, blocks, blockX, blockY + heightY - 2, blockZ, 3, outerMaterial, 14);
			drawMushroomLevel(generator, blocks, blockX, blockY + heightY - 2, blockZ, 2, innerMaterial, 0);
		}
	}
	
	private void drawMushroomLevel(WorldGenerator generator, WorldBlocks blocks, int x, int y, int z, int r, Material material, int data) {
		BlackMagic.setBlocks(blocks, x - r + 1, x + r, y, y + 1, z - r, z - r + 1, material, data);
		
		BlackMagic.setBlocks(blocks, x - r, x + r + 1, y, y + 1, z - r + 1, z + r, material, data);

		BlackMagic.setBlocks(blocks, x - r + 1, x + r, y, y + 1, z + r, z + r + 1, material, data);
	}
}
