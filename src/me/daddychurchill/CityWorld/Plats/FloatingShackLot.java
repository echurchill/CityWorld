package me.daddychurchill.CityWorld.Plats;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class FloatingShackLot extends ConstructLot {

//	private groundLevel;
	
	public FloatingShackLot(PlatMap platmap, int chunkX, int chunkZ, int floatingAt) {
		super(platmap, chunkX, chunkZ);
		
		style = LotStyle.NATURE;
//		groundLevel = floatingAt;
	}

//	private final static byte retainingWallId = (byte) Material.SMOOTH_BRICK.getId();

	@Override
	protected void generateActualChunk(WorldGenerator generator,
			PlatMap platmap, ByteChunk chunk, BiomeGrid biomes,
			DataContext context, int platX, int platZ) {
		
//		chunk.
	}

	@Override
	protected void generateActualBlocks(WorldGenerator generator,
			PlatMap platmap, RealChunk chunk, DataContext context, int platX,
			int platZ) {
		// TODO Auto-generated method stub

	}

}
