package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plugins.FoliageProvider.HerbaceousType;
import me.daddychurchill.CityWorld.Plugins.FoliageProvider.LigneousType;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class RoundaboutStatueLot extends IsolatedLot {

	private enum StatueBase { WATER, GRASS, PEDESTAL };
	
	private final static byte curbId = (byte) Material.DOUBLE_STEP.getId();
	private final static byte brickId = (byte) Material.SMOOTH_BRICK.getId();
	
	private StatueBase statueBase;
	
	public RoundaboutStatueLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		style = LotStyle.ROUNDABOUT;
	}

	@Override
	public boolean isPlaceableAt(WorldGenerator generator, int chunkX, int chunkZ) {
		return generator.settings.inRoadRange(chunkX, chunkZ);
	}
	
	@Override
	public int getBottomY(WorldGenerator generator) {
		return generator.streetLevel + 1;
	}
	
	@Override
	protected void generateActualChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {
		
		// what is it build on?
		statueBase = randomBase(chunkRandom);

		// where to start?
		int y1 = generator.streetLevel + 1;
		chunk.setLayer(y1, curbId);
		
		// what to build?
		switch (statueBase) {
		case WATER:
			
			// bottom of the fountain... 
			chunk.setCircle(8, 8, 5, y1, brickId, false);
			for (int x = 0; x < 10; x++)
				for (int z = 0; z < 10; z++)
					chunk.setBlock(x + 3, y1, z + 3, brickId);
			
			//TODO the plain bit... later we will take care of the fancy bit
			y1++;
			chunk.setCircle(8, 8, 6, y1, brickId, false);
			
			// fill with water
			if (generator.settings.includeAbovegroundFluids) {
				if (generator.settings.includeDecayedNature)
					chunk.setCircle(8, 8, 5, y1, stillLavaId, true);
				else
					chunk.setCircle(8, 8, 5, y1, stillWaterId, true);
			}
			break;
		case GRASS:
			
			// outer edge
			y1++;
			chunk.setCircle(8, 8, 6, y1, brickId, false);
			
			// backfill with grass
			chunk.setCircle(8, 8, 5, y1 - 1, generator.oreProvider.surfaceId, false);
			chunk.setBlocks(3, 13, y1 - 1, y1, 4, 12, generator.oreProvider.surfaceId);
			chunk.setBlocks(4, 12, y1 - 1, y1, 3, 13, generator.oreProvider.surfaceId);
			break;
		case PEDESTAL:
			
			// pedestal, imagine that!
			y1++;
			chunk.setCircle(8, 8, 4, y1, brickId, false);
			chunk.setCircle(8, 8, 3, y1, brickId, false);
			chunk.setCircle(8, 8, 3, y1 + 1, brickId, false);
			chunk.setCircle(8, 8, 3, y1 + 2, fenceId, false);
			chunk.setBlocks(5, 11, y1, y1 + 2, 5, 11, brickId);
			break;
		}
	}
	
	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, DataContext context, int platX, int platZ) {
		// what is it build on?
		statueBase = randomBase(chunkRandom);
		
		// something got stolen?
		boolean somethingInTheCenter = chunkRandom.nextInt(context.oddsOfMissingArt) != 0;
		
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
				if (chunkRandom.nextBoolean()) {
					chunk.setBlocks(5, y1, y1 + chunkRandom.nextInt(3) + 1, 5, liquid);
					chunk.setBlocks(5, y1, y1 + chunkRandom.nextInt(3) + 1, 10, liquid);
					chunk.setBlocks(10, y1, y1 + chunkRandom.nextInt(3) + 1, 5, liquid);
					chunk.setBlocks(10, y1, y1 + chunkRandom.nextInt(3) + 1, 10, liquid);
				}
				
				// water can be art too, you know?
				if (chunkRandom.nextInt(context.oddsOfNaturalArt) == 0) {
					chunk.setBlocks(7, 9, y1, y1 + chunkRandom.nextInt(4) + 4, 7, 9, liquid);
					somethingInTheCenter = false;
				}
			}
			
			break;
		case GRASS:
			
			// backfill with grass
			for (int x = 4; x < 12; x++) {
				for (int z = 4; z < 12; z++) {
					if (chunkRandom.nextDouble() < 0.40) {
						generator.foliageProvider.generateFlora(generator, chunk, x, y1, z, HerbaceousType.GRASS);
					}
				}
			}
			
			// tree can be art too, you know!
			if (chunkRandom.nextInt(context.oddsOfNaturalArt) == 0) {
				generator.foliageProvider.generateTree(generator, chunk, 7, y1, 7, LigneousType.TALL_OAK);
				somethingInTheCenter = false;
			}
			
			break;
		case PEDESTAL:
			
			// no manholes for pedestals, it is just the wrong shape
			break;
		}
		
		// if we have not placed something in the center... place a "ART!" like thingy
		if (somethingInTheCenter) {
			
			// simple glass or colored blocks?
			boolean crystalArt = chunkRandom.nextBoolean();
			byte solidColor = (byte) chunkRandom.nextInt(17);
			boolean singleArt = solidColor != 16; // Whoops, 16 is too large, let's go random
			
			// now the "art"
			for (int x = 6; x < 10; x++) 
				for (int y = y1 + 4; y < y1 + 8; y++) 
					for (int z = 6; z < 10; z++) 
						if (chunkRandom.nextBoolean())
							chunk.setBlock(x, y, z, Material.THIN_GLASS);
						else
							if (crystalArt)
								chunk.setBlock(x, y, z, Material.GLASS);
							else
								chunk.setBlock(x, y, z, Material.WOOL.getId(), 
											   singleArt ? solidColor : (byte) chunkRandom.nextInt(9));
			
			// now put the base in
			chunk.setBlocks(7, 9, y1, y1 + 5, 7, 9, stoneMaterial);
		}
	}
	
	private StatueBase randomBase(Random random) {
		switch (random.nextInt(StatueBase.values().length)) {
		case 0:
			return StatueBase.WATER;
		case 1:
			return StatueBase.GRASS;
		default:
			return StatueBase.PEDESTAL;
		}
	}

}
