package me.daddychurchill.CityWorld.Plats.Urban;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.BuildingLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.Odds.ColorSet;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.BadMagic.Stair;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class ConcreteLot extends BuildingLot {

	// flat shallow pond, inverted pyramid pond, water maze, dented in quiet zone, checkered water, water labyrinth 
//	private enum CenterStyle {EMPTY, QUIET_ZONE, ART_ZONE, SHALLOW_POND, PYRAMID_POND, WATER_MAZE, WATER_CHECKER, WATER_CIRCLE, WATER_LABYRINTH};
	public enum CenterStyle {EMPTY, QUIET_ZONE, ART_ZONE, CHECKER_ART, SHALLOW_POND, ROUND_POND, PYRAMID_POND, CHECKER_POND, UPWARD_POND, DOWNWARD_POND};
	private CenterStyle centerStyle;
	
	public ConcreteLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		height = 1;
		depth = 0;
		trulyIsolated = true;
		centerStyle = getRandomCenterStyle();
	}

	private CenterStyle getRandomCenterStyle() {
		if (chunkOdds.playOdds(Odds.oddsUnlikely))
			return CenterStyle.EMPTY;
		else {
			CenterStyle[] values = CenterStyle.values();
			CenterStyle result = values[chunkOdds.getRandomInt(values.length)];
			if (result == CenterStyle.CHECKER_ART) // reduce the chances of checker art
				result = values[chunkOdds.getRandomInt(values.length)];
			return result;
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
//		int sidewalkLevel = getSidewalkLevel(generator);
//		Material sidewalkMaterial = getSidewalkMaterial();

		// top it off
		flattenLot(generator, chunk, 4);
//		chunk.setLayer(sidewalkLevel - 3, 3, generator.oreProvider.surfaceMaterial);
//		chunk.setLayer(sidewalkLevel, sidewalkMaterial);
	}

	@Override
	protected void generateActualBlocks(CityWorldGenerator generator,
			PlatMap platmap, RealBlocks chunk, DataContext context, int platX,
			int platZ) {
		int sidewalkLevel = getSidewalkLevel(generator);
		Material sidewalkMaterial = getSidewalkMaterial();
		Material fluid = generator.oreProvider.fluidMaterial;
		Material atmosphere = generator.shapeProvider.findAtmosphereMaterialAt(generator, sidewalkLevel);
		Material underneath = generator.materialProvider.itemsSelectMaterial_BuildingCeilings.getRandomMaterial(chunkOdds);
		
		chunk.setLayer(sidewalkLevel - 3, 3, underneath);
		chunk.setLayer(sidewalkLevel, sidewalkMaterial);
		
//		chunk.setBlock(8, sidewalkLevel + 20, 8, Material.COBBLESTONE);
//		chunk.setSignPost(8, sidewalkLevel + 21, 8, BlockFace.NORTH, "Style", centerStyle.toString());
		
		switch (centerStyle) {
		default:
		case EMPTY:
			// nothing needed here
			break;
		case QUIET_ZONE:
			generateSittingArea(chunk, sidewalkLevel, atmosphere, underneath);
			chunk.setBlocks(5, 11, sidewalkLevel - 1, 5, 11, fluid);
			randomFountain(chunk, 6, sidewalkLevel, 6, fluid);
			randomFountain(chunk, 6, sidewalkLevel, 9, fluid);
			randomFountain(chunk, 9, sidewalkLevel, 6, fluid);
			randomFountain(chunk, 9, sidewalkLevel, 9, fluid);
			break;
		case ART_ZONE:
			generateSittingArea(chunk, sidewalkLevel, atmosphere, underneath);
			chunk.setBlocks(6, 10, sidewalkLevel, 6, 10, Material.QUARTZ_BLOCK);
			RoundaboutCenterLot.generateArt(chunk, chunkOdds, 6, sidewalkLevel, 6, Material.QUARTZ_BLOCK);
			break;
		case UPWARD_POND:
			chunk.setBlocks(3, 13, sidewalkLevel, 3, 13, Material.DOUBLE_STEP);
			chunk.setBlocks(4, 12, sidewalkLevel + 1, 4, 12, Material.STEP);
			chunk.setBlocks(5, 11, sidewalkLevel + 1, 5, 11, Material.DOUBLE_STEP);
			chunk.setBlocks(6, 10, sidewalkLevel + 2, 6, 10, Material.STEP);
			
			chunk.clearBlocks(7, 9, sidewalkLevel + 2, 7, 9);
			chunk.setBlocks(7, 9, sidewalkLevel + 1, 7, 9, fluid);
			break;
		case DOWNWARD_POND:
			chunk.setBlocks(3, 13, sidewalkLevel - 1, 3, 13, Material.DOUBLE_STEP);
			chunk.setBlocks(4, 12, sidewalkLevel - 1, 4, 12, Material.STEP);
			chunk.setBlocks(5, 11, sidewalkLevel - 2, 5, 11, Material.DOUBLE_STEP);
			chunk.setBlocks(6, 10, sidewalkLevel - 2, 6, 10, Material.STEP);
			
			chunk.clearBlocks(3, 13, sidewalkLevel, 3, 13);
			chunk.clearBlocks(5, 11, sidewalkLevel - 1, 5, 11);
			chunk.clearBlocks(7, 9, sidewalkLevel - 2, 7, 9);
			
			chunk.setBlocks(6, 10, sidewalkLevel - 4, sidewalkLevel - 2, 6, 10, underneath);
			chunk.setBlocks(7, 9, sidewalkLevel - 3, 7, 9, fluid);
			break;
		case SHALLOW_POND:
			chunk.setLayer(sidewalkLevel - 2, 2, underneath);
			chunk.setWalls(3, 13, sidewalkLevel, 3, 13, underneath);
			chunk.setBlocks(4, 12, sidewalkLevel, 4, 12, fluid);
			if (chunkOdds.playOdds(Odds.oddsPrettyLikely)) {
				randomFountain(chunk, 6, sidewalkLevel, 6, fluid);
				randomFountain(chunk, 6, sidewalkLevel, 9, fluid);
				randomFountain(chunk, 9, sidewalkLevel, 6, fluid);
				randomFountain(chunk, 9, sidewalkLevel, 9, fluid);
			} else {
				RoundaboutCenterLot.generateArt(chunk, chunkOdds, 6, sidewalkLevel, 6, Material.QUARTZ_BLOCK);
			}
			break;
		case PYRAMID_POND:
			int y = sidewalkLevel + 1;
			for (int i = 0; i < 6; i++) {
				if (i == 0) {
					chunk.setBlocks(2 + i, 14 - i, y, 2 + i, 14 - i, atmosphere);
				} else {
					chunk.setWalls(1 + i, 15 - i, y, 1 + i, 15 - i, underneath);
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
			chunk.setLayer(sidewalkLevel - 1, 1, underneath);
			chunk.setCircle(8, 8, 5, sidewalkLevel, underneath, true);
			chunk.setCircle(8, 8, 4, sidewalkLevel, fluid, true);
			if (chunkOdds.playOdds(Odds.oddsPrettyLikely)) {
				randomFountain(chunk, 6, sidewalkLevel, 6, fluid);
				randomFountain(chunk, 6, sidewalkLevel, 9, fluid);
				randomFountain(chunk, 9, sidewalkLevel, 6, fluid);
				randomFountain(chunk, 9, sidewalkLevel, 9, fluid);
			} else {
				RoundaboutCenterLot.generateArt(chunk, chunkOdds, 6, sidewalkLevel, 6, Material.QUARTZ_BLOCK);
			}
			break;
		case CHECKER_POND: 
			chunk.setLayer(sidewalkLevel - 2, 2, underneath);
			chunk.clearBlocks(2, 14, sidewalkLevel, 2, 14);
			boolean skip = false;
			for (int x = 2; x < 14; x += 3) {
				skip = !skip;
				for (int z = 2; z < 14; z += 3) {
					skip = !skip;
					if (!skip) {
						chunk.setBlocks(x, x + 3, sidewalkLevel - 1, z, z + 3, fluid);
						randomFountain(chunk, x + 1, sidewalkLevel - 1, z + 1, fluid);
					}
				}
			}
			break;
		case CHECKER_ART: 
			chunk.setLayer(sidewalkLevel - 2, 2, underneath);
			chunk.clearBlocks(2, 14, sidewalkLevel, 2, 14);
			boolean randomColor = chunkOdds.playOdds(Odds.oddsSomewhatUnlikely);
			ColorSet colors = chunkOdds.getRandomColorSet();
			DyeColor color = chunkOdds.getRandomColor(colors);
			int inset = 0;
			for (int z = 3; z < 13; z++) {
				inset = inset == 1 ? 0 : 1;
				for (int x = 3; x < 13; x += 2) {
					chunk.setGlass(x + inset, x + inset + 1, sidewalkLevel - 1, sidewalkLevel + chunkOdds.calcRandomRange(3, 5), z, z + 1, color);
					if (randomColor)
						color = chunkOdds.getRandomColor(colors);
				}
			}
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
		if (chunkOdds.playOdds(Odds.oddsNearlyAlwaysGoingToHappen))
			chunk.setBlocks(x, y, y + chunkOdds.calcRandomRange(2, 4), z, fluid);
	}
	
	private void generateSittingArea(RealBlocks chunk, int y, Material atmosphere, Material underneath) {
		chunk.setLayer(y - 2, 2, underneath);
		chunk.setBlocks(3, 13, y, y + 1, 3, 13, atmosphere);
		if (chunkOdds.flipCoin()) {
			for (int i = 5; i < 11; i++) {
				chunk.setStair(i, y, 3, Material.QUARTZ_STAIRS, Stair.NORTH);
				chunk.setStair(i, y, 12, Material.QUARTZ_STAIRS, Stair.SOUTH);
				chunk.setStair(3, y, i, Material.QUARTZ_STAIRS, Stair.WEST);
				chunk.setStair(12, y, i, Material.QUARTZ_STAIRS, Stair.EAST);
			}
		} else {
			for (int i = 3; i < 7; i++) {
				chunk.setStair(i, y, 3, Material.QUARTZ_STAIRS, Stair.NORTH);
				chunk.setStair(15 - i, y, 3, Material.QUARTZ_STAIRS, Stair.NORTH);
				chunk.setStair(i, y, 12, Material.QUARTZ_STAIRS, Stair.SOUTH);
				chunk.setStair(15 - i, y, 12, Material.QUARTZ_STAIRS, Stair.SOUTH);
				if (i != 3) {
					chunk.setStair(3, y, i, Material.QUARTZ_STAIRS, Stair.WEST);
					chunk.setStair(3, y, 15 - i, Material.QUARTZ_STAIRS, Stair.WEST);
					chunk.setStair(12, y, i, Material.QUARTZ_STAIRS, Stair.EAST);
					chunk.setStair(12, y, 15 - i, Material.QUARTZ_STAIRS, Stair.EAST);
				}
			}
		}
	}
}
