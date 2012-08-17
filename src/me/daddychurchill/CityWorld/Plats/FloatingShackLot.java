package me.daddychurchill.CityWorld.Plats;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class FloatingShackLot extends ConstructLot {

	private int groundLevel;
	
	public FloatingShackLot(PlatMap platmap, int chunkX, int chunkZ, int floatingAt) {
		super(platmap, chunkX, chunkZ);
		
		style = LotStyle.NATURE;
		groundLevel = floatingAt;
		//CityWorld.log.info("FloatingShackAt = " + floatingAt);
	}

	private final static byte platformId = (byte) Material.SMOOTH_BRICK.getId();
	private final static byte dirtId = (byte) Material.DIRT.getId();
	private final static byte grassId = (byte) Material.GRASS.getId();

	@Override
	protected void generateActualChunk(WorldGenerator generator,
			PlatMap platmap, ByteChunk chunk, BiomeGrid biomes,
			DataContext context, int platX, int platZ) {
		
		// build a little box
		chunk.setBlocks(1, 15, groundLevel - 2, groundLevel - 1, 1, 15, platformId);
		chunk.setWalls(0, 16, groundLevel - 1, groundLevel + 1, 0, 16, platformId);
		chunk.setBlocks(1, 15, groundLevel - 1, groundLevel, 1, 15, dirtId);
		chunk.setBlocks(1, 15, groundLevel, groundLevel + 1, 1, 15, grassId);
	}

	@Override
	protected void generateActualBlocks(WorldGenerator generator,
			PlatMap platmap, RealChunk chunk, DataContext context, int platX,
			int platZ) {

		// now make a shack
		int floors = generator.houseProvider.generateShack(chunk, context, chunkRandom, groundLevel + 1, 4);
		
		// not a happy place?
		if (generator.settings.includeDecayedBuildings) {
			destroyBuilding(generator, chunk, groundLevel + 1, floors);
		}

		// add balloons on the corners
		attachBalloon(generator, chunk, context, 2, 2);
		attachBalloon(generator, chunk, context, 2, 13);
		attachBalloon(generator, chunk, context, 13, 2);
		attachBalloon(generator, chunk, context, 13, 13);
		
		// add to the surface
		generateSurface(generator, chunk, true);
		
	}
	
	private void attachBalloon(WorldGenerator generator, RealChunk chunk, DataContext context, int x, int z) {
		
		// if the corner still exists
		if (!chunk.isEmpty(x, groundLevel, z)) {
			chunk.setBlock(x, groundLevel + 1, z, platformId);
			generator.balloonProvider.generateBalloon(generator, chunk, context, x, groundLevel + 2, z, chunkRandom);
		}
	}
}
