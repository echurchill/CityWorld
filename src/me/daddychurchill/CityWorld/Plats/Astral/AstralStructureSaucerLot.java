package me.daddychurchill.CityWorld.Plats.Astral;

import org.bukkit.Material;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class AstralStructureSaucerLot extends AstralStructureLot {

	public AstralStructureSaucerLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

//		platmap.generator.reportMessage("Ship @ " + chunkX + ", " + chunkZ);
	}

	@Override
	protected void generateActualBlocks(WorldGenerator generator,
			PlatMap platmap, RealChunk chunk, DataContext context,
			int platX, int platZ) {
		
		int y = chunkOdds.getRandomInt(generator.seaLevel + 32, 64);
		drawSaucer(generator, chunk, y);
	}
	
	public static void drawLandedSaucer(WorldGenerator generator, RealChunk chunk, int y) {
		drawSaucer(generator, chunk, y + 2);
		
		// now the legs
		chunk.setBlocks(3, y, y + 2, 3, Material.QUARTZ_BLOCK);
		chunk.setBlocks(10, y, y + 2, 3, Material.QUARTZ_BLOCK);
		chunk.setBlocks(3, y, y + 2, 10, Material.QUARTZ_BLOCK);
		chunk.setBlocks(10, y, y + 2, 10, Material.QUARTZ_BLOCK);
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
