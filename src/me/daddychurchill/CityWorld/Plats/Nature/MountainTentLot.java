package me.daddychurchill.CityWorld.Plats.Nature;

import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class MountainTentLot extends MountainFlatLot {

	public MountainTentLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
	}
	
	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new MountainTentLot(platmap, chunkX, chunkZ);
	}

	@Override
	protected void generateActualChunk(CityWorldGenerator generator,
			PlatMap platmap, InitialBlocks chunk, BiomeGrid biomes,
			DataContext context, int platX, int platZ) {
		generateSmoothedLot(generator, chunk, context);
	}

	@Override
	protected void generateActualBlocks(CityWorldGenerator generator, PlatMap platmap, RealBlocks chunk, DataContext context, int platX, int platZ) {
		reportLocation(generator, "Campground", chunk);
		
		// place snow
		generateSurface(generator, chunk, false);
		
		// now make a tent
		generator.structureOnGroundProvider.generateCampground(generator, chunk, context, chunkOdds, 
				blockYs.averageHeight + 1);
	}

}
