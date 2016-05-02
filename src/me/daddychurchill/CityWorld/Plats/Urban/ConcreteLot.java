package me.daddychurchill.CityWorld.Plats.Urban;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.BuildingLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class ConcreteLot extends BuildingLot {

	// flat shallow pond, inverted pyramid pond, water maze, dented in quiet zone, checkered water, water labyrinth 
//	private enum CenterStyle {EMPTY, QUIET_ZONE, ART_ZONE, SHALLOW_POND, PYRAMID_POND, WATER_MAZE, WATER_CHECKER, WATER_CIRCLE, WATER_LABYRINTH};
	private enum CenterStyle {EMPTY, SHALLOW_POND, ROUND_POND, PYRAMID_POND};
	private CenterStyle centerStyle;
	
	public ConcreteLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		height = 1;
		depth = 0;
		trulyIsolated = true;
		centerStyle = getRandomCenterStyle();
	}

	private CenterStyle getRandomCenterStyle() {
		if (chunkOdds.playOdds(Odds.oddsSomewhatLikely))
			return CenterStyle.EMPTY;
		else {
			CenterStyle[] values = CenterStyle.values();
			return values[chunkOdds.getRandomInt(values.length)];
		}
	}
	
	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new ConcreteLot(platmap, chunkX, chunkZ);
	}

	@Override
	protected void generateActualChunk(CityWorldGenerator generator,
			PlatMap platmap, InitialBlocks chunk, BiomeGrid biomes,
			DataContext context, int platX, int platZ) {
		int sidewalkLevel = getSidewalkLevel(generator);
		Material sidewalkMaterial = getSidewalkMaterial();

		// top it off
		chunk.setLayer(sidewalkLevel, sidewalkMaterial);
	}

	@Override
	protected void generateActualBlocks(CityWorldGenerator generator,
			PlatMap platmap, RealBlocks chunk, DataContext context, int platX,
			int platZ) {
		int sidewalkLevel = getSidewalkLevel(generator);
		Material fluid = generator.oreProvider.fluidMaterial;
		Material atmosphere = generator.shapeProvider.findAtmosphereMaterialAt(generator, sidewalkLevel);
		Material underneath = generator.settings.materials.itemsSelectMaterial_BuildingWalls.getRandomMaterial(chunkOdds);
		
		switch (centerStyle) {
		default:
		case EMPTY:
			// nothing needed here
			break;
//		case QUIET_ZONE:
//			// 
//			break;
//		case ART_ZONE:
//			// quiet zone with art/fountain
//			break;
		case SHALLOW_POND:
			chunk.setLayer(sidewalkLevel - 2, 2, underneath);
			chunk.setBlocks(2, 14, sidewalkLevel, 2, 14, atmosphere);
			chunk.setBlocks(4, 12, sidewalkLevel - 1, sidewalkLevel, 4, 12, fluid);
			randomFountain(chunk, 6, sidewalkLevel, 6, fluid);
			randomFountain(chunk, 6, sidewalkLevel, 9, fluid);
			randomFountain(chunk, 9, sidewalkLevel, 6, fluid);
			randomFountain(chunk, 9, sidewalkLevel, 9, fluid);
			break;
		case PYRAMID_POND:
			int y = sidewalkLevel;
			for (int i = 0; i < 6; i++) {
				if (i == 0) {
					chunk.setBlocks(2 + i, 14 - i, y, 2 + i, 14 - i, atmosphere);
				} else {
					chunk.setWalls(1 + i, 15 - i, y, y + 1, 1 + i, 15 - i, underneath);
					chunk.setBlocks(2 + i, 14 - i, y, 2 + i, 14 - i, fluid);
					if (i == 5)
						chunk.setBlocks(3 + i, 13 - i, y - 1, 3 + i, 13 - i, underneath);
				}
				y--;
			}
			randomFountain(chunk, 6, sidewalkLevel, 6, fluid);
			randomFountain(chunk, 6, sidewalkLevel, 9, fluid);
			randomFountain(chunk, 9, sidewalkLevel, 6, fluid);
			randomFountain(chunk, 9, sidewalkLevel, 9, fluid);
			break;
		case ROUND_POND:
			chunk.setLayer(sidewalkLevel - 2, 2, underneath);
			chunk.setCircle(8, 8, 5, sidewalkLevel, atmosphere, true);
			chunk.setCircle(8, 8, 3, sidewalkLevel - 1, fluid, true);
			randomFountain(chunk, 6, sidewalkLevel, 6, fluid);
			randomFountain(chunk, 6, sidewalkLevel, 9, fluid);
			randomFountain(chunk, 9, sidewalkLevel, 6, fluid);
			randomFountain(chunk, 9, sidewalkLevel, 9, fluid);
			break;
//		case WATER_CHECKER:
//			chunk.setLayer(sidewalkLevel - 2, 2, underneath);
//			int z = 2;
//			for (int i = 2; i < 14; i += 2) {
//				chunk.setBlock(i, sidewalkLevel, z, atmosphere);
//				chunk.setBlock(i, sidewalkLevel - 1, z, fluid);
//				chunk.setBlock(i + 1, sidewalkLevel, z + 1, atmosphere);
//				chunk.setBlock(i + 1, sidewalkLevel - 1, z + 1, fluid);
//				z += 2;
//				if (z > 14)
//					break;
//			}
//			break;
//		case WATER_LABYRINTH:
//			break;
//		case WATER_MAZE:
//			break;
		}
		
		// it looked so nice for a moment... but the moment has passed
		if (generator.settings.includeDecayedBuildings)
			destroyLot(generator, sidewalkLevel, sidewalkLevel + 4);
	}

	@Override
	public int getBottomY(CityWorldGenerator generator) {
		return generator.streetLevel;
	}

	@Override
	public int getTopY(CityWorldGenerator generator) {
		return generator.streetLevel + DataContext.FloorHeight * 2 + 1;
	}

	private void randomFountain(RealBlocks chunk, int x, int y, int z, Material fluid) {
		if (chunkOdds.flipCoin())
			chunk.setBlock(x, y + 2, z, fluid);
	}
}
