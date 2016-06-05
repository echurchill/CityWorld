package me.daddychurchill.CityWorld.Plats.Floating;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.ConstructLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class FloatingHouseLot extends ConstructLot {

	private int groundLevel;
	
	public FloatingHouseLot(PlatMap platmap, int chunkX, int chunkZ, int floatingAt) {
		super(platmap, chunkX, chunkZ);
		
		style = LotStyle.NATURE;
		trulyIsolated = true;
		groundLevel = floatingAt;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new FloatingHouseLot(platmap, chunkX, chunkZ, groundLevel);
	}

	private final static Material platform = Material.SMOOTH_BRICK;
	private final static Material dirt = Material.DIRT;
	private final static Material grass = Material.GRASS;

	@Override
	public int getBottomY(CityWorldGenerator generator) {
		return groundLevel - 1;
	}
	
	@Override
	public int getTopY(CityWorldGenerator generator) {
		return groundLevel + DataContext.FloorHeight * 2 + 1;
	}

	@Override
	protected void generateActualChunk(CityWorldGenerator generator,
			PlatMap platmap, InitialBlocks chunk, BiomeGrid biomes,
			DataContext context, int platX, int platZ) {
		
		// build a little box
		chunk.setBlocks(1, 15, groundLevel - 2, groundLevel - 1, 1, 15, platform);
		chunk.setWalls(0, 16, groundLevel - 1, groundLevel + 1, 0, 16, platform);
		chunk.setBlocks(1, 15, groundLevel - 1, groundLevel, 1, 15, dirt);
		chunk.setBlocks(1, 15, groundLevel, groundLevel + 1, 1, 15, grass);
		
		// round it a bit
		chunk.clearBlocks(0, groundLevel - 1, groundLevel + 1, 0);
		chunk.clearBlocks(15, groundLevel - 1, groundLevel + 1, 0);
		chunk.clearBlocks(0, groundLevel - 1, groundLevel + 1, 15);
		chunk.clearBlocks(15, groundLevel - 1, groundLevel + 1, 15);
		
		// supports for the balloons
		chunk.setBlock(2, groundLevel + 1, 2, platform);
		chunk.setBlock(2, groundLevel + 1, 13, platform);
		chunk.setBlock(13, groundLevel + 1, 2, platform);
		chunk.setBlock(13, groundLevel + 1, 13, platform);
	}

	@Override
	protected void generateActualBlocks(CityWorldGenerator generator,
			PlatMap platmap, RealBlocks chunk, DataContext context, int platX,
			int platZ) {

		// now make a house
		int floors = generator.structureOnGroundProvider.generateHouse(generator, chunk, context, chunkOdds, groundLevel + 1, 1, 4);
		
		// not a happy place?
		if (generator.settings.includeDecayedBuildings)
			destroyBuilding(generator, groundLevel + 1, floors);
		else
			generateSurface(generator, chunk, false);
		generator.spawnProvider.spawnBeing(generator, chunk, chunkOdds, 5, groundLevel + 1, 5);

		// add balloons on the corners
		generator.structureInAirProvider.generateBalloon(generator, chunk, context, 2, groundLevel + 2, 2, chunkOdds);
		generator.structureInAirProvider.generateBalloon(generator, chunk, context, 2, groundLevel + 2, 13, chunkOdds);
		generator.structureInAirProvider.generateBalloon(generator, chunk, context, 13, groundLevel + 2, 2, chunkOdds);
		generator.structureInAirProvider.generateBalloon(generator, chunk, context, 13, groundLevel + 2, 13, chunkOdds);
		
	}
}
