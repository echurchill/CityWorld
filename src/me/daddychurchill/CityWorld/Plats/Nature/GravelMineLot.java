package me.daddychurchill.CityWorld.Plats.Nature;

import org.bukkit.Material;
import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.Direction.Stair;

public class GravelMineLot extends GravelLot {

	public GravelMineLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		trulyIsolated = true;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new GravelMineLot(platmap, chunkX, chunkZ);
	}
	
	@Override
	protected void generateActualBlocks(CityWorldGenerator generator,
			PlatMap platmap, RealChunk chunk, DataContext context, int platX,
			int platZ) {
		
		// initialize
		int width = 13;
		int origin = 1;
		int sectionTop = generator.streetLevel;
		int y = sectionTop;
		
		// while the hole is wide enough
		while (width > 3) {
			
			// ***** steps
			for (int i = 0; i < width - 1; i++) {
				
				// north/south sides
				generateStep(chunk, origin + i, y, sectionTop, origin, Material.COBBLESTONE_STAIRS, Stair.WEST, Stair.EASTFLIP);
				generateStep(chunk, origin + width - i, y, sectionTop, origin + width, Material.COBBLESTONE_STAIRS, Stair.EAST, Stair.WESTFLIP);
				
				// east/west sides
				generateStep(chunk, origin + width, y, sectionTop, origin + i, Material.COBBLESTONE_STAIRS, Stair.NORTH, Stair.SOUTHFLIP);
				generateStep(chunk, origin, y, sectionTop, origin + width - i, Material.COBBLESTONE_STAIRS, Stair.SOUTH, Stair.NORTHFLIP);
				
				// next level down
				y = y - 1;
			}
			
			// ***** landings
			// north/south sides
			generateLanding(chunk, origin + width - 1, y, sectionTop, origin, Material.COBBLESTONE);
			generateLanding(chunk, origin + 1, y, sectionTop, origin + width, Material.COBBLESTONE);
			
			// east/west sides
			generateLanding(chunk, origin + width, y, sectionTop, origin + width - 1, Material.COBBLESTONE);
			generateLanding(chunk, origin, y, sectionTop, origin + 1, Material.COBBLESTONE);
			
			// clear out the in between space
			chunk.setBlocks(origin + 1, origin + width, y, sectionTop + 1, origin + 1, origin + width, Material.AIR);
			
			// increment/decrement to do the next level
			origin = origin + 1;
			width = width - 2;
			sectionTop = y;
			
			// last one?
			if (width <= 3) {
				// north/south sides
				chunk.setStair(origin, y, origin, Material.COBBLESTONE_STAIRS, Stair.WEST);
				chunk.setStair(origin + width, y, origin + width, Material.COBBLESTONE_STAIRS, Stair.EAST);
				
				// east/west sides
				chunk.setStair(origin + width, y, origin, Material.COBBLESTONE_STAIRS, Stair.NORTH);
				chunk.setStair(origin, y, origin + width, Material.COBBLESTONE_STAIRS, Stair.SOUTH);
			}
		}
		
		// top it off
		y = generator.streetLevel + 1;
		generateTailings(generator, chunk, 0, 16, 0, 1);
		generateTailings(generator, chunk, 0, 16, 15, 16);
		generateTailings(generator, chunk, 0, 1, 1, 15);
		generateTailings(generator, chunk, 15, 16, 1, 15);
	}

	private void generateStep(RealChunk chunk, int x, int y1, int y2, int z, Material step, Stair directionTop, Stair directionBottom) {
		chunk.setStair(x, y1, z, step, directionTop);
		if (chunk.isEmpty(x, y1 - 1, z))
			chunk.setStair(x, y1 - 1, z, step, directionBottom);
		chunk.setBlocks(x, y1 + 1, y2 + 1, z, Material.AIR);
	}

	private void generateLanding(RealChunk chunk, int x, int y1, int y2, int z, Material landing) {
		chunk.setBlock(x, y1, z, landing);
		chunk.setBlocks(x, y1 + 1, y2 + 1, z, Material.AIR);
	}
	
	@Override
	public int getBottomY(CityWorldGenerator generator) {
		return 0;
	}

	@Override
	public int getTopY(CityWorldGenerator generator) {
		return generator.streetLevel;
	}

}
