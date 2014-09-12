package me.daddychurchill.CityWorld.Plats.Astral;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class AstralSaucerLot extends AstralStructureLot {

	public AstralSaucerLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

//		platmap.generator.reportMessage("Ship @ " + chunkX + ", " + chunkZ);
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void generateActualChunk(WorldGenerator generator,
			PlatMap platmap, ByteChunk chunk, BiomeGrid biomes,
			DataContext context, int platX, int platZ) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void generateActualBlocks(WorldGenerator generator,
			PlatMap platmap, RealChunk chunk, DataContext context,
			int platX, int platZ) {
		
		int y = chunkOdds.getRandomInt(generator.seaLevel + 32, 64);
		drawSaucer(generator, chunk, y);
	}

	@Override
	public int getBottomY(WorldGenerator generator) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTopY(WorldGenerator generator) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static void drawSaucer(WorldGenerator generator, RealChunk chunk, int y) {
		chunk.setCircle(7, 7, 5, y, Material.QUARTZ_BLOCK, true);
		chunk.setCircle(7, 7, 3, y, Material.GLASS, true);
		chunk.setCircle(7, 7, 6, y + 1, Material.QUARTZ_BLOCK, true);
		chunk.setCircle(7, 7, 2, y + 1, Material.GLASS, true);
		chunk.setCircle(7, 7, 5, y + 2, Material.REDSTONE_BLOCK, true);
		chunk.setCircle(7, 7, 3, y + 3, Material.QUARTZ_BLOCK, true);
		chunk.setCircle(7, 7, 2, y + 4, Material.GLASS, true);
	}

}
