package me.daddychurchill.CityWorld.Plats.Nature;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.ConstructLot;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.PlatMap;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public abstract class MountainBuildingLot extends ConstructLot {

	protected boolean retainingWalls = false;
	private final static byte retainingWallId = (byte) Material.SMOOTH_BRICK.getId();
	
	public MountainBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		style = LotStyle.NATURE;
		trulyIsolated = true;
	}

	@Override
	protected void generateActualChunk(WorldGenerator generator,
			PlatMap platmap, ByteChunk chunk, BiomeGrid biomes,
			DataContext context, int platX, int platZ) {
		
		// flatten things out a bit
		for (int x = 0; x < chunk.width; x++) {
			for (int z = 0; z < chunk.width; z++) {
				int y = getBlockY(x, z);
				
				// add the retaining walls
				if (x == 0 || x == chunk.width - 1 || z == 0 || z == chunk.width - 1) {
					if (y <= averageHeight) {
						chunk.setBlocks(x, y - 2, averageHeight + 1, z, retainingWallId);
					} else if (y > averageHeight) {
						chunk.setBlocks(x, averageHeight - 2, y + 1, z, retainingWallId);
					}
				
				// backfill
				} else {
					if (generator.settings.includeDecayedNature) {
						chunk.setBlocks(x, y - 2, averageHeight + 1, z, sandId);
					} else {
						chunk.setBlocks(x, y - 2, averageHeight, z, generator.oreProvider.subsurfaceId);
						chunk.setBlock(x, averageHeight, z, generator.oreProvider.surfaceId); 
					}
					chunk.setBlocks(x, averageHeight + 1, maxHeight + 1, z, airId);
				}
			}
		}
	}

	@Override
	public int getBottomY(WorldGenerator generator) {
		return averageHeight + 1;
	}
	
}
