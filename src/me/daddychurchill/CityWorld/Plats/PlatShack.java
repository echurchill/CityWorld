package me.daddychurchill.CityWorld.Plats;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.HouseFactory;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class PlatShack extends PlatIsolated {

	public PlatShack(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		style = LotStyle.NATURE;
	}
	
	@Override
	protected void generateRandomness() {
		// TODO Auto-generated method stub
		
	}
	
	private final static byte retainingWallId = (byte) Material.SMOOTH_BRICK.getId();
	private final static byte fillId = (byte) Material.DIRT.getId();
	private final static byte surfaceId = (byte) Material.GRASS.getId();

	@Override
	public void generateChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, ContextData context, int platX, int platZ) {
		
		// compute offset to start of chunk
		int blockX = chunk.chunkX * chunk.width;
		int blockZ = chunk.chunkZ * chunk.width;
		
		// flatten things out a bit
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {
				int y = generator.findBlockY(blockX + x, blockZ + z);
				
				// add the retaining walls
				if (x == 0 || x == chunk.width - 1 || z == 0 || z == chunk.width - 1) {
					if (y <= averageHeight) {
						chunk.setBlocks(x, y - 2, averageHeight + 1, z, retainingWallId);
					} else if (y > averageHeight) {
						chunk.setBlocks(x, averageHeight - 2, y + 1, z, retainingWallId);
					}
				
				// backfill
				} else {
					chunk.setBlocks(x, y - 2, averageHeight, z, fillId);
					chunk.setBlock(x, averageHeight, z, surfaceId);
					chunk.setBlocks(x, averageHeight + 1, maxHeight + 1, z, airId);
				}
			}
		}
	}
	
	@Override
	public void generateBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, ContextData context, int platX, int platZ) {

		// now make a shack
		HouseFactory.generateShack(chunk, context, chunkRandom, averageHeight + 1);
	}
}
