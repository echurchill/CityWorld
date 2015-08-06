package me.daddychurchill.CityWorld.Plats.Astral;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.WorldBlocks;

public class AstralMushroomsSpongeLot extends AstralNatureLot {

	public AstralMushroomsSpongeLot(PlatMap platmap, int chunkX, int chunkZ, double populationChance) {
		super(platmap, chunkX, chunkZ, populationChance);
		
	}

	final static int belowSurface = 15;
	
	@Override
	protected void generateActualBlocks(CityWorldGenerator generator,
			PlatMap platmap, RealBlocks chunk, DataContext context, int platX,
			int platZ) {
		
		// four please
		placeSponge(generator, chunk, 3, 3);
		placeSponge(generator, chunk, 11, 3);
		placeSponge(generator, chunk, 3, 11);
		placeSponge(generator, chunk, 11, 11);
	}
	
	private void placeSponge(CityWorldGenerator generator, RealBlocks chunk, int x, int z) {
		
		// do one here?
		if (chunkOdds.playOdds(populationChance)) {
			
			// ready what we need
			int topY = generator.seaLevel - 2;
			
			// shimmy a bit, maybe
			x += chunkOdds.getRandomInt(-1, 3);
			z += chunkOdds.getRandomInt(-1, 3);
			
			// down far enough?
			int y = getSurfaceAtY(x, z);
			if (y > 0 && y <= topY - belowSurface) {

				// a little more shimmy
				topY += chunkOdds.getRandomInt(-1, 2);
				
				// normalize
				int blockX = chunk.getBlockX(x);
				int blockZ = chunk.getBlockZ(z);
				WorldBlocks blocks = new WorldBlocks(generator, chunkOdds);
				
				// draw the disc bit
				placeTopDisc(blocks, blockX, topY, blockZ);
				
				// tall enough?
				if (topY - y > 8)
					placeMiddleDisc(blocks, blockX, topY - 4, blockZ);
				
				// really tall enough?
				if (topY - y > 11)
					placeBottomDisc(blocks, blockX, topY - 7, blockZ);
				
				// draw the stalk
				chunk.setBlocks(x, x + 2, y - 3, topY - 1, z, z + 2, Material.SPONGE);
				
				// sure it up
				chunk.setBlocks(x - 1, x + 3, topY - 1, topY + 1, z - 1, z + 3, Material.SPONGE);
				
				// top it off
				chunk.setBlocks(x, x + 2, topY, topY + 1, z, z + 2, Material.GLOWSTONE);
				chunk.setThinGlass(x, x + 2, topY + 1, topY + 2, z, z + 2, DyeColor.YELLOW);
			}
		}
	}
		
	private void placeTopDisc(WorldBlocks blocks, int x, int y, int z) {
		placeLine(blocks, x, x + 2, y, z - 4);
		placeLine(blocks, x - 2, x + 4, y, z - 3);

		placeLine(blocks, x - 3, x + 5, y, z - 2);
		placeLine(blocks, x - 3, x + 5, y, z - 1);

		placeLine(blocks, x - 4, x + 6, y, z);
		placeLine(blocks, x - 4, x + 6, y, z + 1);
		
		placeLine(blocks, x - 3, x + 5, y, z + 2);
		placeLine(blocks, x - 3, x + 5, y, z + 3);
		
		placeLine(blocks, x - 2, x + 4, y, z + 4);
		placeLine(blocks, x, x + 2, y, z + 5);
	}
	
	private void placeMiddleDisc(WorldBlocks blocks, int x, int y, int z) {
		placeLine(blocks, x - 1, x + 3, y, z - 3);
		placeLine(blocks, x - 2, x + 4, y, z - 2);
		placeLine(blocks, x - 3, x + 5, y, z - 1);

		placeLine(blocks, x - 3, x + 5, y, z);
		placeLine(blocks, x - 3, x + 5, y, z + 1);

		placeLine(blocks, x - 3, x + 5, y, z + 2);
		placeLine(blocks, x - 2, x + 4, y, z + 3);
		placeLine(blocks, x - 1, x + 3, y, z + 4);
	}
	
	private void placeBottomDisc(WorldBlocks blocks, int x, int y, int z) {
		placeLine(blocks, x - 1, x + 3, y, z - 2);
		placeLine(blocks, x - 2, x + 4, y, z - 1);

		placeLine(blocks, x - 2, x + 4, y, z);
		placeLine(blocks, x - 2, x + 4, y, z + 1);

		placeLine(blocks, x - 2, x + 4, y, z + 2);
		placeLine(blocks, x - 1, x + 3, y, z + 3);
	}
	
	private void placeLine(WorldBlocks blocks, int x1, int x2, int y, int z) {
		for (int x = x1; x < x2; x++) 
			placeBlock(blocks, x, y, z);
	}
	
	private void placeBlock(WorldBlocks blocks, int x, int y, int z) {
		switch (chunkOdds.getRandomInt(20)) {
		case 1:
			// some occasional holes
			break;
		case 2:
		case 3:
		case 4:
			blocks.setGlass(x, y, z, DyeColor.BROWN);
			break;
		case 5:
		case 6:
		case 7:
			blocks.setGlass(x, y, z, DyeColor.YELLOW);
			break;
		case 8:
		case 9:
		case 10:
			blocks.setGlass(x, y, z, DyeColor.ORANGE);
			break;
		default:
			blocks.setBlock(x, y, z, Material.SPONGE);
			break;
		}
	}
}
