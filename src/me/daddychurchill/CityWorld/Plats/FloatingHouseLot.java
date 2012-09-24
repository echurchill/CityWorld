package me.daddychurchill.CityWorld.Plats;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class FloatingHouseLot extends ConstructLot {

	private int groundLevel;
	
	public FloatingHouseLot(PlatMap platmap, int chunkX, int chunkZ, int floatingAt) {
		super(platmap, chunkX, chunkZ);
		
		style = LotStyle.NATURE;
		groundLevel = floatingAt;
	}

	private final static byte platformId = (byte) Material.SMOOTH_BRICK.getId();
	private final static byte dirtId = (byte) Material.DIRT.getId();
	private final static byte grassId = (byte) Material.GRASS.getId();

	@Override
	public int getBottomY(WorldGenerator generator) {
		return groundLevel - 1;
	}
	
	@Override
	protected void generateActualChunk(WorldGenerator generator,
			PlatMap platmap, ByteChunk chunk, BiomeGrid biomes,
			DataContext context, int platX, int platZ) {
		
		// build a little box
		chunk.setBlocks(1, 15, groundLevel - 2, groundLevel - 1, 1, 15, platformId);
		chunk.setWalls(0, 16, groundLevel - 1, groundLevel + 1, 0, 16, platformId);
		chunk.setBlocks(1, 15, groundLevel - 1, groundLevel, 1, 15, dirtId);
		chunk.setBlocks(1, 15, groundLevel, groundLevel + 1, 1, 15, grassId);
		
		// supports for the balloons
		chunk.setBlock(2, groundLevel + 1, 2, platformId);
		chunk.setBlock(2, groundLevel + 1, 13, platformId);
		chunk.setBlock(13, groundLevel + 1, 2, platformId);
		chunk.setBlock(13, groundLevel + 1, 13, platformId);
	}

	@Override
	protected void generateActualBlocks(WorldGenerator generator,
			PlatMap platmap, RealChunk chunk, DataContext context, int platX,
			int platZ) {

		// now make a house
		int floors = generator.houseProvider.generateHouse(generator, chunk, context, chunkOdds, groundLevel + 1, 1, 4);
		
		// not a happy place?
		if (generator.settings.includeDecayedBuildings)
			destroyBuilding(generator, groundLevel + 1, floors);

		// add balloons on the corners
		generator.balloonProvider.generateBalloon(generator, chunk, context, 2, groundLevel + 2, 2, chunkOdds);
		generator.balloonProvider.generateBalloon(generator, chunk, context, 2, groundLevel + 2, 13, chunkOdds);
		generator.balloonProvider.generateBalloon(generator, chunk, context, 13, groundLevel + 2, 2, chunkOdds);
		generator.balloonProvider.generateBalloon(generator, chunk, context, 13, groundLevel + 2, 13, chunkOdds);
		
		// add to the surface
		generateSurface(generator, chunk, true);
		
	}
}
