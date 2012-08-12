package me.daddychurchill.CityWorld.Plats;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Support.BalloonFactory;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.HouseFactory;
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
		int floors = HouseFactory.generateSmallShack(chunk, context, chunkRandom, groundLevel + 1);
		
		// not a happy place?
		if (generator.settings.includeDecayedBuildings) {
			destroyBuilding(generator, chunk, groundLevel + 1, floors);
		}

		// add balloons on the corners
		attachBalloon(generator, chunk, 2, 2);
		attachBalloon(generator, chunk, 2, 13);
		attachBalloon(generator, chunk, 13, 2);
		attachBalloon(generator, chunk, 13, 13);
	}
	
	private void attachBalloon(WorldGenerator generator, RealChunk chunk, int x, int z) {
		
		// if the corner still exists
		if (!chunk.isEmpty(x, groundLevel, z)) {
			chunk.setBlock(x, groundLevel + 1, z, platformId);
			BalloonFactory.generateBalloon(generator, chunk, x, groundLevel + 2, z, chunkRandom);
		}
	}
}
