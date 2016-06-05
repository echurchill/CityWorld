package me.daddychurchill.CityWorld.Plats.Nature;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.ConstructLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.BadMagic;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class OilPlatformLot extends ConstructLot {

	public OilPlatformLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		trulyIsolated = true;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new OilPlatformLot(platmap, chunkX, chunkZ);
	}

	private final static Material platformMaterial = Material.DOUBLE_STEP;
	private final static Material headMaterial = Material.STEP;
	private final static Material railingMaterial = Material.IRON_FENCE;
	private final static Material drillMaterial = Material.NETHER_FENCE;
	private final static Material supportMaterial = Material.NETHER_BRICK;
	private final static Material topperMaterial = Material.NETHER_BRICK_STAIRS;
	
	private final static int aboveSea = 6;

	@Override
	public int getBottomY(CityWorldGenerator generator) {
		return generator.seaLevel + aboveSea;
	}
	
	@Override
	public int getTopY(CityWorldGenerator generator) {
		return getBottomY(generator) + DataContext.FloorHeight * 4 + 1;
	}

	@Override
	protected void generateActualChunk(CityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {
		
	}
	
	@Override
	protected void generateActualBlocks(CityWorldGenerator generator, PlatMap platmap, RealBlocks chunk, DataContext context, int platX, int platZ) {
		reportLocation(generator, "Oil Platform", chunk);

		// working levels
		int y0 = generator.seaLevel;
		int y1 = y0 + aboveSea;
		int y2 = y1 + DataContext.FloorHeight;
		int y3 = y2 + DataContext.FloorHeight;
		int y4 = y3 + DataContext.FloorHeight;
//		Material emptyMaterial = getAirMaterial(generator, y1);
		
		// access levels
		chunk.setBlocks(2, 6, y0, y0 + 1, 2, 6, platformMaterial);
		chunk.setBlocks(10, 14, y0, y0 + 1, 10, 14, platformMaterial);
		
		// lower level
		chunk.setLayer(y1, platformMaterial);
		chunk.setWalls(0, 16, y1 + 1, y1 + 2, 0, 16, railingMaterial);
		chunk.airoutBlocks(generator, 7, 9, y1, y1 + 1, 7, 9);
		chunk.setWalls(6, 10, y1 + 1, y1 + 2, 6, 10, railingMaterial);
		
		// upper level
		chunk.setLayer(y2, platformMaterial);
		chunk.setWalls(0, 16, y2 + 1, y2 + 2, 0, 16, railingMaterial);
		chunk.airoutBlocks(generator, 7, 9, y2, y2 + 1, 7, 9);
		chunk.setWalls(6, 10, y2 + 1, y2 + 2, 6, 10, railingMaterial);
		
		// put the balcony on top 
		chunk.setBlocks(2, 14, y3, y3 + 1, 2, 14, platformMaterial);
		chunk.setWalls(2, 14, y3 + 1, y3 + 2, 2, 14, railingMaterial);
		chunk.airoutBlocks(generator, 7, 9, y3, y3 + 1, 7, 9);
		chunk.setWalls(6, 10, y3 + 1, y3 + 2, 6, 10, railingMaterial);
		
		// drill head level
		chunk.setBlocks(6, 14, y4, y4 + 1, 6, 14, platformMaterial);
		chunk.setWalls(6, 14, y4 + 1, y4 + 2, 6, 14, railingMaterial);
		chunk.airoutBlocks(generator, 6, 9, y4, y4 + 2, 6, 9);
		chunk.setBlocks(9, y3 + 1, y4 + 2, 6, supportMaterial);
		chunk.setBlocks(6, y3 + 1, y4 + 2, 9, supportMaterial);
		
		// drill head itself
		chunk.setBlock(9, y4 + 2, 6, platformMaterial);
		chunk.setBlock(6, y4 + 2, 9, platformMaterial);
		chunk.setBlock(7, y4, 7, headMaterial);
		chunk.setBlock(7, y4, 8, headMaterial);
		chunk.setBlock(8, y4, 7, headMaterial);
		chunk.setBlock(9, y4 + 3, 6, headMaterial);
		chunk.setBlock(9, y4 + 3, 7, headMaterial);
		chunk.setBlock(9, y4 + 3, 8, headMaterial);
		chunk.setBlock(6, y4 + 3, 9, headMaterial);
		chunk.setBlock(7, y4 + 3, 9, headMaterial);
		chunk.setBlock(8, y4 + 3, 9, headMaterial);
		chunk.setBlock(8, y4 + 3, 8, headMaterial);

		// two big legs to hold up the various levels (a little bit deeper than needed, just to be safe)
		chunk.setBlocks(2, 4, blockYs.minHeight - 10, y4, 2, 4, supportMaterial);
		chunk.setBlocks(12, 14, blockYs.minHeight - 10, y4 + 3, 12, 14, supportMaterial);
		
		// two lesser legs to help the other two
		chunk.setBlocks(2, 4, blockYs.minHeight - 10, y3, 12, 14, supportMaterial);
		chunk.setBlocks(12, 14, blockYs.minHeight - 10, y3, 2, 4, supportMaterial);
		chunk.setBlocks(13, y3, y3 + 2, 2, supportMaterial);
		chunk.setBlocks(2, y3, y3 + 2, 13, supportMaterial);
		
		// drill down
		chunk.setBlocks(8, generator.deepseaLevel, y4 + 3, 8, drillMaterial); 
		
		// extra drill bits
		chunk.setBlocks(5, y2 + 2, y3 + 2, 1, drillMaterial);
		chunk.setBlocks(7, y2 + 2, y3 + 2, 1, drillMaterial);
		//chunk.setBlocks(9, y2 + 2, y3 + 2, 1, drillId);
		chunk.setBlocks(11, y2 + 2, y3 + 2, 1, drillMaterial);
		chunk.setBlocks(13, y4 + 4, y4 + 8, 2, drillMaterial); // bit hanging from the crane
		
		// ladder from access level to the balcony
		chunk.setLadder(3, y0 + 1, y4 - 2, 4, BlockFace.NORTH);
		chunk.setLadder(12, y0 + 1, y4 + 2, 11, BlockFace.SOUTH);
		
		// now draw the crane
		chunk.setStair(2, y4 - 2, 3, topperMaterial, BadMagic.Stair.EAST);
		chunk.clearBlock(2, y4 - 1, 2);
		chunk.clearBlock(2, y4 - 1, 3);
		chunk.setStair(3, y4 - 1, 3, topperMaterial, BadMagic.Stair.NORTH);
		chunk.setBlocks(2, y4 - 2, y4, 2, Material.IRON_FENCE);
		chunk.drawCrane(context, chunkOdds, 3, y4, 2);
		
		// bleed off
		chunk.setBlocks(12, 14, y4 + 3, y4 + 8, 12, 14, Material.IRON_FENCE);
		chunk.setBlocks(12, 14, y4 + 8, 12, 14, Material.NETHERRACK);
		chunk.setBlocks(12, 14, y4 + 9, 12, 14, Material.FIRE);

		// it looked so nice for a moment... but the moment has passed
		if (generator.settings.includeDecayedBuildings) {

			// do we take out a bit of it?
			decayEdge(generator, chunk.getBlockX(7) + chunkOdds.getRandomInt(3) - 1, y1, chunk.getBlockZ(0) + chunkOdds.getRandomInt(2));
			decayEdge(generator, chunk.getBlockX(7) + chunkOdds.getRandomInt(3) - 1, y2, chunk.getBlockZ(0) + chunkOdds.getRandomInt(2));
			decayEdge(generator, chunk.getBlockX(8) + chunkOdds.getRandomInt(3) - 1, y1, chunk.getBlockZ(15) - chunkOdds.getRandomInt(2));
			decayEdge(generator, chunk.getBlockX(8) + chunkOdds.getRandomInt(3) - 1, y2, chunk.getBlockZ(15) - chunkOdds.getRandomInt(2));
			decayEdge(generator, chunk.getBlockX(0) + chunkOdds.getRandomInt(2), y1, chunk.getBlockZ(7) + chunkOdds.getRandomInt(3) - 1);
			decayEdge(generator, chunk.getBlockX(0) + chunkOdds.getRandomInt(2), y2, chunk.getBlockZ(7) + chunkOdds.getRandomInt(3) - 1);
			decayEdge(generator, chunk.getBlockX(15) - chunkOdds.getRandomInt(2), y1, chunk.getBlockZ(8) + chunkOdds.getRandomInt(3) - 1);
			decayEdge(generator, chunk.getBlockX(15) - chunkOdds.getRandomInt(2), y2, chunk.getBlockZ(8) + chunkOdds.getRandomInt(3) - 1);
			decayEdge(generator, chunk.getBlockX(7), y4, chunk.getBlockZ(12));
		}
		generator.spawnProvider.spawnBeing(generator, chunk, chunkOdds, 5, y2 + 1, 5);
		generator.spawnProvider.spawnBeing(generator, chunk, chunkOdds, 5, y3 + 1, 5);
	}

	private final static double decayedEdgeOdds = 0.25;
	
	private void decayEdge(CityWorldGenerator generator, int x, int y, int z) {
		if (chunkOdds.playOdds(decayedEdgeOdds))
			
			// make it go away
			generator.decayBlocks.desperseArea(x, y, z, chunkOdds.getRandomInt(2) + 2);
	}
}
