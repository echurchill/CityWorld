package me.daddychurchill.CityWorld.Plats;

import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.RealChunk;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class PlatMineEntrance extends PlatIsolated {

	public PlatMineEntrance(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

	}
	
	@Override
	protected void generateActualChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, ContextData context, int platX, int platZ) {
		
	}
	
	private int shaftY = 0;
	private int surfaceY = 0;
	
	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, ContextData context, int platX, int platZ) {

		// find the bottom of the world
		shaftY = findHighestShaftableLevel(generator, context, chunk);
		shaftY = Math.max(2, shaftY); // make sure we don't go down too far
		
		// compute offset to start of chunk
		int originX = chunk.getOriginX();
		int originZ = chunk.getOriginZ();
		
		// where is the surface?
		surfaceY = generator.findBlockY(originX - 1, originZ - 1);
		surfaceY = Math.min(surfaceY, generator.findBlockY(originX + 4, originZ - 1));
		surfaceY = Math.min(surfaceY, generator.findBlockY(originX - 1, originZ + 4));
		surfaceY = Math.min(surfaceY, generator.findBlockY(originX + 4, originZ + 4));
//		surfaceY--;
//
//		CityWorld.log.info("Bottom of the shaft = " + shaftY + ", top of the shaft = " + surfaceY);
		
		// drill down
		chunk.setBlocks(0, 4, shaftY, surfaceY + ContextData.FloorHeight + 1, 0, 4, Material.AIR);
		
		// make the surface bits
		chunk.setBlocks(0, 4, surfaceY, 0, 4, Material.COBBLESTONE);
//		chunk.setBlocks(1, 3, surfaceY + 1, surfaceY + ContextData.FloorHeight + 2, 1, 3, Material.COBBLESTONE);
//		chunk.setBlocks(0, 4, surfaceY + ContextData.FloorHeight, 0, 4, Material.COBBLESTONE);
		
		// core bits
		switch (chunkRandom.nextInt(6)) {
		case 1:
			chunk.setBlocks(1, 3, shaftY, surfaceY, 1, 3, Material.IRON_FENCE);
			break;
		case 2:
			chunk.setBlocks(1, 3, shaftY, surfaceY, 1, 3, Material.FENCE);
			break;
		case 3:
			chunk.setBlocks(1, 3, shaftY, surfaceY, 1, 3, Material.COBBLESTONE);
			break;
		default:
			// else air will do
			break;
		}
		
		// connect to the minecraft
		chunk.setBlocks(0, 4, shaftY - 1, 0, 4, Material.COBBLESTONE);
		chunk.setBlocks(2, 4, shaftY - 1, 4, 6, Material.COBBLESTONE);
		chunk.setBlocks(2, 4, shaftY, shaftY + 2, 4, 6, Material.AIR);
		
		// now do the stair
		do {
			if (generateStairs(generator, chunk, 3, 2, Direction.Stair.NORTH, Direction.Stair.SOUTHFLIP)) break;
			if (generateStairs(generator, chunk, 3, 1, Direction.Stair.NORTH, Direction.Stair.EASTFLIP)) break;
			generateLanding(generator, chunk, 3, 0, Direction.Stair.EASTFLIP);
			if (generateStairs(generator, chunk, 2, 0, Direction.Stair.WEST, Direction.Stair.EASTFLIP)) break;
			if (generateStairs(generator, chunk, 1, 0, Direction.Stair.WEST, Direction.Stair.NORTHFLIP)) break;
			generateLanding(generator, chunk, 0, 0, Direction.Stair.NORTHFLIP);
			if (generateStairs(generator, chunk, 0, 1, Direction.Stair.SOUTH, Direction.Stair.NORTHFLIP)) break;
			if (generateStairs(generator, chunk, 0, 2, Direction.Stair.SOUTH, Direction.Stair.WESTFLIP)) break;
			generateLanding(generator, chunk, 0, 3, Direction.Stair.WESTFLIP);
			if (generateStairs(generator, chunk, 1, 3, Direction.Stair.EAST, Direction.Stair.WESTFLIP)) break;
			if (generateStairs(generator, chunk, 2, 3, Direction.Stair.EAST, Direction.Stair.SOUTHFLIP)) break;
			generateLanding(generator, chunk, 3, 3, Direction.Stair.SOUTHFLIP);
		} while (shaftY <= surfaceY);
		
		//TODO remove this flag!
		chunk.setBlocks(2, surfaceY + 5, maxHeight + 20, 1, Material.GLOWSTONE);
		
		// place snow
		generateSurface(generator, platmap, chunk, context, platX, platZ, false);
	}
	
	private boolean generateStairs(WorldGenerator generator, RealChunk chunk, int x, int z, 
			Direction.Stair direction, Direction.Stair underdirection) {
		chunk.setBlocks(x, shaftY + 1, shaftY + 4, z, Material.AIR);
		chunk.setStair(x, shaftY, z, Material.COBBLESTONE_STAIRS, direction);
		if (chunk.isEmpty(x, shaftY - 1, z))
			chunk.setStair(x, shaftY - 1, z, Material.COBBLESTONE_STAIRS, underdirection);
		
		// moving on up
		shaftY++;
		
		// far enough?
		return shaftY > surfaceY;
	}
	
	private void generateLanding(WorldGenerator generator, RealChunk chunk, int x, int z, 
			Direction.Stair underdirection) {
		chunk.setBlocks(x, shaftY, shaftY + 3, z, Material.AIR);
		chunk.setBlock(x, shaftY - 1, z, Material.COBBLESTONE);
		if (chunk.isEmpty(x, shaftY - 2, z))
			chunk.setStair(x, shaftY - 2, z, Material.COBBLESTONE_STAIRS, underdirection);
	}
}
