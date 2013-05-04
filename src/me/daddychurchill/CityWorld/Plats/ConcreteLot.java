package me.daddychurchill.CityWorld.Plats;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class ConcreteLot extends BuildingLot {

	public ConcreteLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		height = 1;
		depth = 0;
		trulyIsolated = true;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new ConcreteLot(platmap, chunkX, chunkZ);
	}

	@Override
	protected void generateActualChunk(WorldGenerator generator,
			PlatMap platmap, ByteChunk chunk, BiomeGrid biomes,
			DataContext context, int platX, int platZ) {
		int groundY = getBottomY(generator);

		// top it off
		chunk.setLayer(groundY, generator.oreProvider.subsurfaceId);
		chunk.setLayer(groundY + 1, RoadLot.sidewalkId);
	}

	@Override
	protected void generateActualBlocks(WorldGenerator generator,
			PlatMap platmap, RealChunk chunk, DataContext context, int platX,
			int platZ) {
		int groundY = getBottomY(generator);

		// it looked so nice for a moment... but the moment has passed
		if (generator.settings.includeDecayedBuildings)
			destroyLot(generator, groundY, groundY + 4);
	}

	@Override
	public int getBottomY(WorldGenerator generator) {
		return generator.streetLevel;
	}

}
