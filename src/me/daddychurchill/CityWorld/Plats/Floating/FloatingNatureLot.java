package me.daddychurchill.CityWorld.Plats.Floating;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.IsolatedLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.InitSection;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealSection;

import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class FloatingNatureLot extends IsolatedLot {

	public FloatingNatureLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		style = LotStyle.NATURE;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new FloatingNatureLot(platmap, chunkX, chunkZ);
	}

	@Override
	public int getBottomY(CityWorldGenerator generator) {
		return 0;
	}

	@Override
	public int getTopY(CityWorldGenerator generator) {
		return generator.streetLevel;
	}

	@Override
	protected void generateActualChunk(CityWorldGenerator generator,
			PlatMap platmap, InitSection chunk, BiomeGrid biomes,
			DataContext context, int platX, int platZ) {

	}

	@Override
	protected void generateActualBlocks(CityWorldGenerator generator,
			PlatMap platmap, RealSection chunk, DataContext context, int platX,
			int platZ) {
		
	}

}
