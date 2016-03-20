package me.daddychurchill.CityWorld.Plats.Nature;

import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.ConstructLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.StructureInAirProvider;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class HotairBalloonLot extends ConstructLot {

	public HotairBalloonLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		trulyIsolated = true;
	}
	
	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new HotairBalloonLot(platmap, chunkX, chunkZ);
	}

	@Override
	public int getBottomY(CityWorldGenerator generator) {
		return blockYs.maxHeight + 20;
	}
	
	@Override
	protected void generateActualChunk(CityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			BiomeGrid biomes, DataContext context, int platX, int platZ) {
		//TODO what?
	}

	@Override
	protected void generateActualBlocks(CityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ) {

		// place snow
		generateSurface(generator, chunk, false);
		
		// where is the surface?
		int atY = getBottomY(generator);
		
		// saucer?
		if (atY < chunk.height / 2 && chunkOdds.playOdds(Odds.oddsTremendouslyUnlikely)) {
			reportLocation(generator, "Flying Saucer", chunk);
			generator.structureInAirProvider.generateSaucer(generator, chunk, (chunk.height - 32) + chunkOdds.getRandomInt(16), false);
		
		// or hot air balloon
		} else {
			reportLocation(generator, "Hot Air Balloon", chunk);
			int rangeY = chunk.height - StructureInAirProvider.hotairBalloonHeight - atY;
			if (rangeY > 1)
				generator.structureInAirProvider.generateHotairBalloon(generator, chunk, context, atY + chunkOdds.getRandomInt(rangeY), chunkOdds);
		}
	}
}
