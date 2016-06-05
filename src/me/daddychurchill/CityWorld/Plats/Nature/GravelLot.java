package me.daddychurchill.CityWorld.Plats.Nature;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.ConstructLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.BadMagic.Stair;

public abstract class GravelLot extends ConstructLot {

	public GravelLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void generateActualChunk(CityWorldGenerator generator,
			PlatMap platmap, InitialBlocks chunk, BiomeGrid biomes,
			DataContext context, int platX, int platZ) {
		// TODO Auto-generated method stub
		
	}

	protected static void generateTailings(CityWorldGenerator generator, Odds odds, RealBlocks chunk, int x1, int x2, int z1, int z2) {
		generateTailings(generator, odds, chunk, x1, x2, generator.streetLevel, z1, z2);
	}
	
	protected static void generateTailings(CityWorldGenerator generator, Odds odds, RealBlocks chunk, int x1, int x2, int y, int z1, int z2) {
		
		// clear out some room above the tailings
		if (x1 + 1 < x2 - 1 && z1 + 1 < z2 - 1)
			chunk.setBlocks(x1 + 1, x2 - 1, y, z1 + 1, z2 - 1, Material.AIR);
		if (x1 + 2 < x2 - 2 && z1 + 2 < z2 - 2)
			chunk.setBlocks(x1 + 2, x2 - 2, y - 1, z1 + 2, z2 - 2, Material.AIR);
		
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				int yOffset;
				if (x == x1 || z == z1 || x == x2 - 1 || z == z2 - 1)
					yOffset = 0;
				else if (x == x1 + 1 || z == z1 + 1 || x == x2 - 2 || z == z2 - 2)
					yOffset = 1;
				else
					yOffset = 2;
				
				switch (odds.getRandomInt(3)) {
				case 0:
					chunk.setSlab(x, y - yOffset, z, Material.COBBLESTONE, false);
					chunk.setBlock(x, y - 1 - yOffset, z, Material.COBBLESTONE);
					break;
				case 1:
					chunk.setSlab(x, y + 1 - yOffset, z, Material.COBBLESTONE, false);
					chunk.setBlock(x, y - yOffset, z, Material.COBBLESTONE);
					chunk.setBlock(x, y - 1 - yOffset, z, Material.COBBLESTONE);
					break;
				default:
					chunk.setBlock(x, y - yOffset, z, Material.COBBLESTONE);
					break;
				}
			}
		}
	}
	
	protected void generateBase(CityWorldGenerator generator, RealBlocks chunk) {
		chunk.setBlocks(1, 15, generator.streetLevel, 1, 15, Material.COBBLESTONE);
		for (int i = 0; i < 10; i++) {
			if (chunkOdds.flipCoin())
				chunk.setBlock(i + 2, generator.streetLevel, 0, Material.COBBLESTONE);
			if (chunkOdds.flipCoin())
				chunk.setBlock(15, generator.streetLevel, i + 3, Material.COBBLESTONE);
			if (chunkOdds.flipCoin())
				chunk.setBlock(13 - i, generator.streetLevel, 15, Material.COBBLESTONE);
			if (chunkOdds.flipCoin())
				chunk.setBlock(0, generator.streetLevel, 13 - i, Material.COBBLESTONE);
		}
	}

	public static void generatePile(CityWorldGenerator generator, Odds odds, RealBlocks chunk, int x, int z, int width) {
		Material specialMaterial = generator.materialProvider.itemsSelectMaterial_QuaryPiles.getRandomMaterial(odds, Material.COBBLESTONE);
		int y = generator.streetLevel + 1;
		if (odds.playOdds(Odds.oddsPrettyLikely)) {
			for (int a = 0; a < width; a++) {
				for (int b = 0; b < width; b++) {
					int base = 2;
					if (a == 0 || a == width - 1)
						base--;
					if (b == 0 || b == width - 1)
						base--;
					int height = odds.getRandomInt(base, 3);
					for (int c = 0; c < height; c++)
						chunk.setBlock(x + a, y + c, z + b, odds.playOdds(Odds.oddsVeryUnlikely) ? specialMaterial : Material.COBBLESTONE);
				}
			}
		}
	}
	
	public static void generateHole(CityWorldGenerator generator, Odds odds, RealBlocks chunk, int highestY, int width, int lowestY) {
		generateHole(generator, odds, chunk, highestY, width, lowestY, true);
	}
	
	public static void generateHole(CityWorldGenerator generator, Odds odds, RealBlocks chunk, int highestY, int width, int lowestY, boolean doTailings) {
		width = (width / 2) * 2; // make sure width is even
		
		// get ready to dig
		int xz = (chunk.width - width) / 2;
		int depth = chunk.width - (xz * 2) - 1;
		int sectionTop = highestY;
		int y = sectionTop;
		
		// while the hole is wide enough
		int origin = xz;
		while (depth > 3) {
			
			// ***** steps
			for (int i = 0; i < depth - 1; i++) {
				
				// north/south sides
				generateStep(chunk, origin + i, y, sectionTop, origin, Material.COBBLESTONE_STAIRS, Stair.WEST, Stair.EASTFLIP);
				generateStep(chunk, origin + depth - i, y, sectionTop, origin + depth, Material.COBBLESTONE_STAIRS, Stair.EAST, Stair.WESTFLIP);
				
				// east/west sides
				generateStep(chunk, origin + depth, y, sectionTop, origin + i, Material.COBBLESTONE_STAIRS, Stair.NORTH, Stair.SOUTHFLIP);
				generateStep(chunk, origin, y, sectionTop, origin + depth - i, Material.COBBLESTONE_STAIRS, Stair.SOUTH, Stair.NORTHFLIP);
				
				// next level down
				y = y - 1;
			}
			
			// ***** landings
			// north/south sides
			generateLanding(chunk, origin + depth - 1, y, sectionTop, origin, Material.COBBLESTONE);
			generateLanding(chunk, origin + 1, y, sectionTop, origin + depth, Material.COBBLESTONE);
			
			// east/west sides
			generateLanding(chunk, origin + depth, y, sectionTop, origin + depth - 1, Material.COBBLESTONE);
			generateLanding(chunk, origin, y, sectionTop, origin + 1, Material.COBBLESTONE);
			
			// clear out the in between space
			chunk.setBlocks(origin + 1, origin + depth, y, sectionTop + 1, origin + 1, origin + depth, Material.AIR);
			
			// too far?
			if (y <= lowestY) {
				
				// rough up the bottom
				if (doTailings)
					generateTailings(generator, odds, chunk, origin + 1, origin + depth, y, origin + 1, origin + depth);
				
				// all done
				break;
			
			// more to do?
			} else {
				
				// increment/decrement to do the next level
				origin = origin + 1;
				depth = depth - 2;
				sectionTop = y;
				
				// last one?
				if (depth <= 3) {
					
					// north/south sides
					chunk.setStair(origin, y, origin, Material.COBBLESTONE_STAIRS, Stair.WEST);
					chunk.setStair(origin + depth, y, origin + depth, Material.COBBLESTONE_STAIRS, Stair.EAST);
					
					// east/west sides
					chunk.setStair(origin + depth, y, origin, Material.COBBLESTONE_STAIRS, Stair.NORTH);
					chunk.setStair(origin, y, origin + depth, Material.COBBLESTONE_STAIRS, Stair.SOUTH);
					
					// all done
					break;
				}
			}
		}
		
		// top it off
//		y = generator.streetLevel + 1;
//		generateTailings(generator, chunk, 0, 16, 0, 1);
//		generateTailings(generator, chunk, 0, 16, 15, 16);
//		generateTailings(generator, chunk, 0, 1, 1, 15);
//		generateTailings(generator, chunk, 15, 16, 1, 15);
	}
	
	private static void generateStep(RealBlocks chunk, int x, int y1, int y2, int z, Material step, Stair directionTop, Stair directionBottom) {
		chunk.setStair(x, y1, z, step, directionTop);
		if (chunk.isEmpty(x, y1 - 1, z))
			chunk.setStair(x, y1 - 1, z, step, directionBottom);
		chunk.setBlocks(x, y1 + 1, y2 + 1, z, Material.AIR);
	}

	private static void generateLanding(RealBlocks chunk, int x, int y1, int y2, int z, Material landing) {
		chunk.setBlock(x, y1, z, landing);
		chunk.setBlocks(x, y1 + 1, y2 + 1, z, Material.AIR);
	}
	
}
