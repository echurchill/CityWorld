package me.daddychurchill.CityWorld.Plats.Astral;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class AstralStructureSaucerLot extends AstralStructureLot {

	public AstralStructureSaucerLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

//		platmap.generator.reportMessage("Ship @ " + chunkX + ", " + chunkZ);
	}

	@Override
	protected void generateActualBlocks(CityWorldGenerator generator,
			PlatMap platmap, RealBlocks chunk, DataContext context,
			int platX, int platZ) {
		
		int y = chunkOdds.getRandomInt(generator.seaLevel + 32, 64);
		drawSaucer(generator, chunk, y);
	}
	
	public static void drawLandedSaucer(CityWorldGenerator generator, RealBlocks chunk, int y) {
		generator.structureInAirProvider.generateSaucer(generator, chunk, y, true);
	}

	public static void drawSaucer(CityWorldGenerator generator, RealBlocks chunk, int y) {
		generator.structureInAirProvider.generateSaucer(generator, chunk, y, false);
	}

}
