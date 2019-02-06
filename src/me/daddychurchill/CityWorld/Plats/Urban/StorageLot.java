package me.daddychurchill.CityWorld.Plats.Urban;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.BuildingLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import me.daddychurchill.CityWorld.Support.AbstractCachedYs;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.SurroundingLots;

public class StorageLot extends BuildingLot {

	public StorageLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		height = 1;
		depth = 0;
		trulyIsolated = true;
		contentType = pickContentType();
	}

	private enum ContentType {
		EMPTY, SHED, TANK
	}

	private final ContentType contentType;

	private ContentType pickContentType() {
		ContentType[] values = ContentType.values();
		return values[chunkOdds.getRandomInt(values.length)];
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new StorageLot(platmap, chunkX, chunkZ);
	}

	@Override
	protected void generateActualChunk(CityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			BiomeGrid biomes, DataContext context, int platX, int platZ) {
		int groundY = getBottomY(generator);

		// look around
		SurroundingLots neighbors = new SurroundingLots(platmap, platX, platZ);

		// different things
		switch (contentType) {
		case EMPTY:
		case SHED:
			drawFence(generator, chunk, context, 1, groundY + 2, 0, neighbors, Material.IRON_BARS, 3);
			break;
		case TANK:
			break;
		}

		// top it off
		Material floorMat = generator.materialProvider.itemsSelectMaterial_FactoryInsides.getRandomMaterial(chunkOdds,
				Material.SMOOTH_STONE);
		chunk.setLayer(groundY, 2, floorMat);
//		chunk.setLayer(groundY + 1, RoadLot.pavementId);

	}

	@Override
	protected void generateActualBlocks(CityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ) {
		int groundY = getBottomY(generator) + 2;
		int topY = getTopY(generator);

//		// look around
//		SurroundingLots neighbors = new SurroundingLots(platmap, platX, platZ);

		// different things
		switch (contentType) {
		case EMPTY:
			break;
		case SHED:
			generator.structureOnGroundProvider.generateShed(generator, chunk, context, chunkOdds, 7, groundY, 7,
					2 + chunkOdds.getRandomInt(2), LootLocation.STORAGE_SHED);
			break;
		case TANK:
			Material wallMat = generator.materialProvider.itemsSelectMaterial_FactoryInsides
					.getRandomMaterial(chunkOdds, Material.SMOOTH_STONE);
			Material fluidMat = generator.materialProvider.itemsSelectMaterial_FactoryTanks.getRandomMaterial(chunkOdds,
					Material.WATER);

			chunk.setCircle(8, 8, 6, groundY, groundY + 2 + chunkOdds.getRandomInt(6), fluidMat, true);
			chunk.setCircle(8, 8, 6, groundY, topY - 3, wallMat);
			chunk.setCircle(8, 8, 6, topY - 3, wallMat, true);
			chunk.setCircle(8, 8, 4, topY - 3, Material.AIR, true);
			chunk.setCircle(8, 8, 5, topY - 2, wallMat, true);
			break;
		}

		// it looked so nice for a moment... but the moment has passed
		if (generator.getSettings().includeDecayedBuildings)
			destroyLot(generator, groundY, groundY + 4);
		generator.spawnProvider.spawnBeing(generator, chunk, chunkOdds, 7, groundY, 7);
	}

	@Override
	public int getBottomY(CityWorldGenerator generator) {
		return generator.streetLevel;
	}

	@Override
	public int getTopY(CityWorldGenerator generator, AbstractCachedYs blockYs, int x, int z) {
		return getTopY(generator);
	}

	private int getTopY(CityWorldGenerator generator) {
		return generator.streetLevel + DataContext.FloorHeight * 3 + 1;
	}

}
