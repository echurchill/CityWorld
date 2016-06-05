package me.daddychurchill.CityWorld.Plats.Rural;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.IsolatedLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class HouseLot extends IsolatedLot {

	public HouseLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		style = LotStyle.STRUCTURE;
	}
	
	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new HouseLot(platmap, chunkX, chunkZ);
	}

	@Override
	public int getBottomY(CityWorldGenerator generator) {
		return generator.streetLevel;
	}

	@Override
	public int getTopY(CityWorldGenerator generator) {
		return generator.streetLevel + DataContext.FloorHeight * 3 + 1;
	}

	@Override
	protected void generateActualChunk(CityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {
		// make room
		chunk.airoutLayer(generator, generator.streetLevel + 1, DataContext.FloorHeight * 2, 2, true);
		
		// ground please
		if (generator.settings.includeDecayedNature)
			chunk.setLayer(generator.streetLevel, Material.SAND);
		else
			chunk.setLayer(generator.streetLevel, generator.oreProvider.surfaceMaterial);
	}
	
	@Override
	protected void generateActualBlocks(CityWorldGenerator generator, PlatMap platmap, RealBlocks chunk, DataContext context, int platX, int platZ) {
		// now make a house
		int atY = generator.streetLevel + 1;
		int floors = generator.structureOnGroundProvider.generateHouse(generator, chunk, context, chunkOdds, atY, 2);
		
		// not a happy place?
		if (generator.settings.includeDecayedBuildings)
			destroyBuilding(generator, atY, floors);
		else
			generateSurface(generator, chunk, false);
		generator.spawnProvider.spawnBeing(generator, chunk, chunkOdds, 5, atY, 5);
	}

}
