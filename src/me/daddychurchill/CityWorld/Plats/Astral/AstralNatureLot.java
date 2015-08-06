package me.daddychurchill.CityWorld.Plats.Astral;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class AstralNatureLot extends AstralLot {

	public AstralNatureLot(PlatMap platmap, int chunkX, int chunkZ, double populationChance) {
		super(platmap, chunkX, chunkZ, populationChance);
		
		style = LotStyle.NATURE;
	}

	private static double oddsOfBuriedSaucer = Odds.oddsEnormouslyUnlikely;

	@Override
	protected void generateActualBlocks(CityWorldGenerator generator,
			PlatMap platmap, RealBlocks chunk, DataContext context, int platX,
			int platZ) {
		
		if (blockYs.averageHeight > 40 && chunkOdds.playOdds(oddsOfBuriedSaucer)) {
			int y = chunkOdds.calcRandomRange(20, blockYs.averageHeight - 10);
			AstralStructureSaucerLot.drawSaucer(generator, chunk, y);
		}
	}

}
