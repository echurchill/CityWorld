package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World.Environment;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class PlatStatue extends PlatIsolated {

	private enum StatueBase { WATER, GRASS, PEDESTAL };
	
	protected final static byte curbId = (byte) Material.DOUBLE_STEP.getId();
	protected final static byte goldId = (byte) Material.GOLD_ORE.getId();
	protected final static byte brickId = (byte) Material.SMOOTH_BRICK.getId();
	
	protected final static byte netherrackId = (byte) Material.NETHERRACK.getId();
	
	protected StatueBase statueBase;
	
	public PlatStatue(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		style = LotStyle.ROUNDABOUT;
	}

	@Override
	protected int getTopStrataY(WorldGenerator generator, int blockX, int blockZ) {
		return generator.sidewalkLevel - 1;
	}

	@Override
	protected void generateActualChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, ContextData context, int platX, int platZ) {
		// what is it build on?
		statueBase = randomBase(chunkRandom);

		// where to start?
		int y1 = generator.sidewalkLevel + 1;
		chunk.setLayer(y1, curbId);
		
		// what to build?
		switch (statueBase) {
		case WATER:
			
			// bottom of the fountain... any coins?
			chunk.setCircle(8, 8, 5, y1, stoneId, false);
			for (int x = 0; x < 10; x++)
				for (int z = 0; z < 10; z++)
					chunk.setBlock(x + 3, y1, z + 3, stoneId);
			
			//TODO the plain bit... later we will take care of the fancy bit
			y1++;
			chunk.setCircle(8, 8, 6, y1, brickId, false);
			
			// fill with water
			if (generator.settings.environment == Environment.NETHER)
				chunk.setCircle(8, 8, 5, y1, stillLavaId, true);
			else
				chunk.setCircle(8, 8, 5, y1, stillWaterId, true);
			break;
		case GRASS:
			
			// outer edge
			y1++;
			chunk.setCircle(8, 8, 6, y1, brickId, false);
			
			// backfill with grass
			if (generator.settings.environment == Environment.NETHER) {
				chunk.setCircle(8, 8, 5, y1 - 1, netherrackId, false);
				chunk.setBlocks(3, 13, y1 - 1, y1, 4, 12, netherrackId);
				chunk.setBlocks(4, 12, y1 - 1, y1, 3, 13, netherrackId);
			} else {
				chunk.setCircle(8, 8, 5, y1 - 1, grassId, false);
				chunk.setBlocks(3, 13, y1 - 1, y1, 4, 12, grassId);
				chunk.setBlocks(4, 12, y1 - 1, y1, 3, 13, grassId);
			}
			break;
		case PEDESTAL:
			
			// pedestal, imagine that!
			y1++;
			chunk.setCircle(8, 8, 4, y1, stoneId, false);
			chunk.setCircle(8, 8, 3, y1, stoneId, false);
			chunk.setCircle(8, 8, 3, y1 + 1, stoneId, false);
			chunk.setCircle(8, 8, 3, y1 + 2, fenceId, false);
			chunk.setBlocks(5, 11, y1, y1 + 2, 5, 11, stoneId);
			break;
		}
	}
	
	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, ContextData context, int platX, int platZ) {
		// what is it build on?
		statueBase = randomBase(chunkRandom);
		
		// something got stolen?
		boolean somethingInTheCenter = chunkRandom.nextInt(context.oddsOfMissingArt) != 0;
		
		// where to start?
		int y1 = generator.sidewalkLevel + 2;
		
		// making a fountain?
		switch (statueBase) {
		case WATER:
			
			Material liquid = Material.WATER;
			if (generator.settings.environment == Environment.NETHER)
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
			
			break;
		case GRASS:
			
			// backfill with grass
			for (int x = 4; x < 12; x++) {
				for (int z = 4; z < 12; z++) {
					if (chunkRandom.nextDouble() < 0.40) {
						if (generator.settings.environment == Environment.NETHER) {
							chunk.setBlock(x, y1, z, Material.FIRE);
						} else {
							chunk.setBlock(x, y1, z, grassMaterialId, (byte) 1);
						}
					}
				}
			}
			
			// tree can be art too, you know!
			if (generator.settings.environment != Environment.NETHER && chunkRandom.nextInt(context.oddsOfNaturalArt) == 0) {
				generateTree(generator, chunk, 7, y1, 7, TreeType.BIG_TREE);
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
