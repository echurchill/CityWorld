package me.daddychurchill.CityWorld.Plats.Urban;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.ConstructLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.RealMaterial;

public class GovernmentMonumentLot extends ConstructLot {

	public GovernmentMonumentLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		botHeight = chunkOdds.calcRandomRange(2, 6);
		topHeight = chunkOdds.calcRandomRange(1, 3);
		monumentStyle = pickMonumentStyle();
		loadMaterials(platmap);
	}
	
	private int botHeight;
	private int topHeight;
	private int sectionHeight = 5;
	private Material foundationMaterial = Material.QUARTZ_BLOCK;
	private Material columnMaterial = Material.QUARTZ_BLOCK;

	protected void loadMaterials(PlatMap platmap) {

		// what is it made of?
		foundationMaterial = platmap.generator.materialProvider.itemsSelectMaterial_GovernmentFoundations.getRandomMaterial(chunkOdds, foundationMaterial);
		columnMaterial = platmap.generator.materialProvider.itemsSelectMaterial_GovernmentWalls.getRandomMaterial(chunkOdds, columnMaterial);
	}
	
	private enum MonumentStyle {COLUMN, PYRAMID, PEDESTAL, CHICKEN};
	private MonumentStyle monumentStyle;
		
	protected MonumentStyle pickMonumentStyle() {
		MonumentStyle[] values = MonumentStyle.values();
		return values[chunkOdds.getRandomInt(values.length)];
	}
	
	@Override
	protected void generateActualChunk(CityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			BiomeGrid biomes, DataContext context, int platX, int platZ) {
		int sidewalkLevel = getSidewalkLevel(generator);
		Material sidewalkMaterial = getSidewalkMaterial();
		
		chunk.setLayer(sidewalkLevel - 3, 3, foundationMaterial);
		chunk.setLayer(sidewalkLevel, sidewalkMaterial);
		
		switch (monumentStyle) {
		case COLUMN:
			drawColumn(generator, chunk, sidewalkLevel, chunkOdds.playOdds(Odds.oddsPrettyLikely));
			break;
		case PYRAMID:
			drawPyramid(generator, chunk, sidewalkLevel, chunkOdds.getRandomInt(2, 4));
			break;
		case PEDESTAL:
			drawPedestal(generator, chunk, sidewalkLevel, chunkOdds.playOdds(Odds.oddsPrettyLikely));
			break;
		case CHICKEN:
			drawChicken(generator, chunk, sidewalkLevel, chunkOdds.playOdds(Odds.oddsPrettyLikely));
			break;
		}
	}
	
	private void drawColumn(CityWorldGenerator generator, InitialBlocks chunk, int y1, boolean doTop) {
		int y2 = y1 + botHeight * sectionHeight;
		int y3 = y2 + topHeight * sectionHeight;
		chunk.setBlocks(5, 11, y1, y2, 5, 11, columnMaterial);
		chunk.setWalls(6, 10, y2, y3, 6, 10, RealMaterial.getStainedGlass(chunkOdds));
		if (chunkOdds.flipCoin())
			chunk.setBlocks(7, 9, y2, y3, 7, 9, columnMaterial);
		if (doTop) {
			chunk.setBlocks(7, 9, y3, 7, 9, columnMaterial);
		}
	}
	
	private void drawChicken(CityWorldGenerator generator, InitialBlocks chunk, int y1, boolean doTop) {
		int y2 = y1 + botHeight * sectionHeight;
		chunk.setBlocks(6, 10, y1, y2, 5, 11, columnMaterial);
		chunk.setBlocks(5, 6, y1, y2, 6, 10, columnMaterial);
		chunk.setBlocks(10, 11, y1, y2, 6, 10, columnMaterial);
		chunk.setBlocks(4, 12, y2, 4, 12, columnMaterial);
		if (doTop) {
			if (chunkOdds.playOdds(Odds.oddsSomewhatUnlikely))
				generator.thingProvider.generateChicken(chunk, 1, y2 + 1, 4);
			else
				chunk.setBlocks(5, 11, y2 + 1, y2 + chunkOdds.calcRandomRange(3, 8), 5, 11, RealMaterial.getStainedGlass(chunkOdds));
		}
	}
	
	private void drawPyramid(CityWorldGenerator generator, InitialBlocks chunk, int y, int scaleFactor) {
		chunk.setBlocks(2, 7, y, y + scaleFactor, 2, 7, columnMaterial);
		chunk.setBlocks(10, 15, y, y + scaleFactor, 2, 7, columnMaterial);
		chunk.setBlocks(2, 7, y, y + scaleFactor, 10, 15, columnMaterial);
		chunk.setBlocks(10, 15, y, y + scaleFactor, 10, 15, columnMaterial);
		
		for (int i = 1; i < 7; i++) {
			int xy1 = 2 + i;
			int xy2 = 15 - i;
			int y1 = y + i * scaleFactor;
			int y2 = y1 + (i == 6 ? 1 : scaleFactor);
			if (i < 6) {
				chunk.setWalls(xy1, xy2, y1, y2, xy1, xy2, columnMaterial);
				chunk.setWalls(xy1 + 1, xy2 - 1, y1, y2, xy1 + 1, xy2 - 1, columnMaterial);
			} else
				chunk.setBlocks(xy1, xy2, y1, y2, xy1, xy2, columnMaterial);
		}
	}
	
	private void drawPedestal(CityWorldGenerator generator, InitialBlocks chunk, int y1, boolean doTop) {
		int y2 = y1 + botHeight * sectionHeight;
		int y3 = y2 + topHeight * sectionHeight;
		chunk.setBlocks(5, 10, y1, y2, 5, 10, columnMaterial);
		if (chunkOdds.flipCoin()) {
			
			// corner supports?
			chunk.setBlocks(5, y2, y3, 5, columnMaterial);
			chunk.setBlocks(5, y2, y3, 9, columnMaterial);
			
			chunk.setBlocks(9, y2, y3, 5, columnMaterial);
			chunk.setBlocks(9, y2, y3, 9, columnMaterial);
			
			int yT = y3;
			if (chunkOdds.flipCoin())
				yT = yT - sectionHeight;
			
			// side supports?
			if (chunkOdds.flipCoin()) {
				chunk.setBlocks(5, y2, yT, 7, columnMaterial);
				
				chunk.setBlocks(7, y2, yT, 5, columnMaterial);
				chunk.setBlocks(7, y2, yT, 9, columnMaterial);
				
				chunk.setBlocks(9, y2, yT, 7, columnMaterial);
			}
			
			// center bit?
			if (chunkOdds.flipCoin()) {
				if (chunkOdds.flipCoin())
					chunk.setBlocks(7, y2, y3, 7, columnMaterial);
				else
					chunk.setBlocks(6, 9, y2, yT, 6, 9, RealMaterial.getStainedGlass(chunkOdds));
			}
			
		} else {
			chunk.setBlocks(6, 9, y2, y3, 6, 9, RealMaterial.getStainedGlass(chunkOdds));
		}
		chunk.setBlocks(5, 10, y3, 5, 10, columnMaterial);
		if (doTop) {
			generator.thingProvider.generateStatue(chunk, chunkOdds, 7, y3 + 1, 7);
		} else {
			chunk.setBlocks(6, 9, y3 + 1, 6, 9, columnMaterial);
		}
	}

	@Override
	protected void generateActualBlocks(CityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new GovernmentMonumentLot(platmap, chunkX, chunkZ);
	}
	
}
