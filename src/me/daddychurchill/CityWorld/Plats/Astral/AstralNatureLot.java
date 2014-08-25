package me.daddychurchill.CityWorld.Plats.Astral;

import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.IsolatedLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class AstralNatureLot extends IsolatedLot {

	public AstralNatureLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		style = LotStyle.NATURE;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new AstralNatureLot(platmap, chunkX, chunkZ);
	}

	@Override
	public int getBottomY(WorldGenerator generator) {
		return 0;
	}

	@Override
	public int getTopY(WorldGenerator generator) {
		return generator.streetLevel;
	}

	@Override
	protected void generateActualChunk(WorldGenerator generator,
			PlatMap platmap, ByteChunk chunk, BiomeGrid biomes,
			DataContext context, int platX, int platZ) {

	}
	
	private static double oddsOfBuriedSaucer = Odds.oddsEnormouslyUnlikely;

	@Override
	protected void generateActualBlocks(WorldGenerator generator,
			PlatMap platmap, RealChunk chunk, DataContext context, int platX,
			int platZ) {
		
		if (blockYs.averageHeight > 40 && chunkOdds.playOdds(oddsOfBuriedSaucer)) {
			int y = chunkOdds.calcRandomRange(20, blockYs.averageHeight - 10);
			AstralShipLot.drawSaucer(generator, chunk, y);
		}
	}

	@Override
	public void generateMines(WorldGenerator generator, ByteChunk chunk) {
		
	}

}
