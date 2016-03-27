package me.daddychurchill.CityWorld.Plats.Urban;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.IsolatedLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.CoverProvider.CoverageSets;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class RoundaboutCenterLot extends IsolatedLot {

	private enum StatueBase { WATER, GRASS, PEDESTAL };
	
	private final static Material curbMaterial = Material.DOUBLE_STEP;
	private final static Material brickMaterial = Material.SMOOTH_BRICK;
//	private final static Material fenceMaterial = Material.FENCE;
	private final static Material baseMaterial = Material.QUARTZ_BLOCK;
	
	private StatueBase statueBase;
	
	public RoundaboutCenterLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		style = LotStyle.ROUNDABOUT;
		statueBase = randomBase();
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new RoundaboutCenterLot(platmap, chunkX, chunkZ);
	}

	@Override
	public boolean isPlaceableAt(CityWorldGenerator generator, int chunkX, int chunkZ) {
		return generator.settings.inRoadRange(chunkX, chunkZ);
	}
	
	@Override
	public int getBottomY(CityWorldGenerator generator) {
		return generator.streetLevel + 1;
	}
	
	@Override
	public int getTopY(CityWorldGenerator generator) {
		return generator.streetLevel + DataContext.FloorHeight * 3 + 1;
	}
	
	@Override
	public boolean isValidStrataY(CityWorldGenerator generator, int blockX, int blockY, int blockZ) {
		return blockY < generator.streetLevel - 5;
	}

	@Override
	protected boolean isShaftableLevel(CityWorldGenerator generator, int blockY) {
		return blockY < generator.streetLevel - 5;
	}
	
	@Override
	protected void generateActualChunk(CityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {
		
		// where to start?
		int y1 = generator.streetLevel + 1;
		chunk.setLayer(y1, curbMaterial);
		
		// what to build?
		switch (statueBase) {
		case WATER:
			
			// bottom of the fountain... 
			chunk.setCircle(8, 8, 5, y1, brickMaterial, false);
			for (int x = 0; x < 10; x++)
				for (int z = 0; z < 10; z++)
					chunk.setBlock(x + 3, y1, z + 3, brickMaterial);
			
			// the plain bit... later we will take care of the fancy bit
			y1++;
			chunk.setCircle(8, 8, 6, y1, brickMaterial, false);
			
			// fill with water
			if (generator.settings.includeAbovegroundFluids)
				chunk.setCircle(8, 8, 5, y1, generator.oreProvider.fluidFluidMaterial, true);
			break;
		case GRASS:
			
			// outer edge
			y1++;
			chunk.setCircle(8, 8, 6, y1, brickMaterial, false);
			
			// backfill with grass
			chunk.setCircle(8, 8, 5, y1 - 1, generator.oreProvider.surfaceMaterial, false);
			chunk.setBlocks(3, 13, y1 - 1, y1, 4, 12, generator.oreProvider.surfaceMaterial);
			chunk.setBlocks(4, 12, y1 - 1, y1, 3, 13, generator.oreProvider.surfaceMaterial);
			break;
		case PEDESTAL:
			
			// pedestal, imagine that!
			y1++;
			chunk.setCircle(8, 8, 4, y1, brickMaterial, false);
			chunk.setCircle(8, 8, 3, y1, brickMaterial, false);
			chunk.setCircle(8, 8, 3, y1 + 1, brickMaterial, false);
//			chunk.setCircle(8, 8, 3, y1 + 2, fenceMaterial, false);
			chunk.setBlocks(5, 11, y1, y1 + 2, 5, 11, brickMaterial);
			break;
		}
	}
	
	@Override
	protected void generateActualBlocks(CityWorldGenerator generator, PlatMap platmap, RealBlocks chunk, DataContext context, int platX, int platZ) {
		
		// something got stolen?
		boolean somethingInTheCenter = chunkOdds.playOdds(context.oddsOfArt);
		
		// where to start?
		int y1 = generator.streetLevel + 2;
		
		// making a fountain?
		switch (statueBase) {
		case WATER:
			
			// add some water to the mix
			if (generator.settings.includeAbovegroundFluids) {
				Material liquid = Material.WATER;
				if (generator.settings.includeDecayedNature)
					liquid = Material.LAVA;
				
				// four little fountains?
				if (chunkOdds.flipCoin()) {
					chunk.setBlocks(5, y1, y1 + 1 + chunkOdds.getRandomInt(3), 5, liquid);
					chunk.setBlocks(5, y1, y1 + 1 + chunkOdds.getRandomInt(3), 10, liquid);
					chunk.setBlocks(10, y1, y1 + 1 + chunkOdds.getRandomInt(3), 5, liquid);
					chunk.setBlocks(10, y1, y1 + 1 + chunkOdds.getRandomInt(3), 10, liquid);
				}
				
				// water can be art too, you know?
				if (somethingInTheCenter && chunkOdds.playOdds(context.oddsOfNaturalArt)) {
					chunk.setBlocks(7, 9, y1, y1 + 4 + chunkOdds.getRandomInt(4), 7, 9, liquid);
					somethingInTheCenter = false;
				}
			}
			
			break;
		case GRASS:
			
			// tree can be art too, you know!
			if (somethingInTheCenter && chunkOdds.playOdds(context.oddsOfNaturalArt)) {
				generator.coverProvider.generateCoverage(generator, chunk, 7, y1, 7, CoverageSets.TALL_TREES);
				somethingInTheCenter = false;
			}
			
			// backfill with grass
			for (int x = 3; x < 13; x++) {
				for (int z = 3; z < 13; z++) {
					if (generator.coverProvider.isPlantable(generator, chunk, x, y1, z))
						generator.coverProvider.generateCoverage(generator, chunk, x, y1, z, CoverageSets.PRARIE_PLANTS);
				}
			}
//			for (int x = 4; x < 12; x++) {
//				for (int z = 4; z < 12; z++) {
//					if (chunkOdds.playOdds(0.40)) {
//						generator.coverProvider.generateCoverage(generator, chunk, x, y1, z, CoverageType.GRASS);
//					}
//				}
//			}
			
			break;
		case PEDESTAL:
			
			y1 = y1 + 2;
			break;
		}
		
		// if we have not placed something in the center... place a "ART!" like thingy
		if (somethingInTheCenter && !generator.settings.includeDecayedRoads) {
			
			// simple glass or colored blocks?
			boolean woolArt = chunkOdds.playOdds(Odds.oddsUnlikely);
			DyeColor solidColor = chunkOdds.getRandomColor();
			boolean multiColorArt = chunkOdds.playOdds(Odds.oddsLikely);
			
			// base art
			if (chunkOdds.flipCoin() || woolArt) {
			
				// now the "art"
				for (int x = 6; x < 10; x++) 
					for (int y = y1 + 4; y < y1 + 8; y++) 
						for (int z = 6; z < 10; z++) {
							
							// pick a color
							DyeColor blockColor = multiColorArt ? chunkOdds.getRandomColor() : solidColor;
							
							// place a block
							if (chunkOdds.flipCoin())
								chunk.setThinGlass(x, y, z, blockColor);
							else
								if (woolArt)
									chunk.setWool(x, y, z, blockColor);
								else
									chunk.setGlass(x, y, z, blockColor);
						}
				
				// now put the base in
				chunk.setBlocks(7, 9, y1 - 1, y1 + 5, 7, 9, baseMaterial);
			
			// else spire art
			} else {
				// now put the base in
				chunk.setBlocks(6, 10, y1 - 1, y1, 6, 10, baseMaterial);
			
				// now for the art
				for (int x = 6; x < 10; x++) 
					for (int z = 6; z < 10; z++) {
						
						// height
						int y2 = y1 + chunkOdds.getRandomInt(5) + 1;
						int y3 = y2 + chunkOdds.getRandomInt(5) + 1;
						
						// pick a color
						DyeColor blockColor = multiColorArt ? chunkOdds.getRandomColor() : solidColor;
						
						// place a block
						for (int y = y1; y < y2; y++)
							chunk.setGlass(x, y, z, blockColor);
						for (int y = y2; y < y3; y++)
							chunk.setThinGlass(x, y, z, blockColor);
					}
			}
		}
	}
	
	private StatueBase randomBase() {
		switch (chunkOdds.getRandomInt(StatueBase.values().length)) {
		case 0:
			return StatueBase.WATER;
		case 1:
			return StatueBase.GRASS;
		default:
			return StatueBase.PEDESTAL;
		}
	}

}
