package me.daddychurchill.CityWorld.Plats;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Support.BalloonFactory;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class FloatingBlimpLot extends IsolatedLot {

	protected final static byte baseId = (byte) Material.SMOOTH_BRICK.getId();
	protected final static byte pedestalId = (byte) Material.SANDSTONE.getId();
	
	boolean manyBalloons;
		
	public FloatingBlimpLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		manyBalloons = chunkRandom.nextBoolean();
	}

	@Override
	protected void generateActualChunk(WorldGenerator generator,
			PlatMap platmap, ByteChunk chunk, BiomeGrid biomes,
			DataContext context, int platX, int platZ) {
		
		boolean toNorth = platmap.isStructureLot(platX, platZ - 1);
		boolean toSouth = platmap.isStructureLot(platX, platZ + 1);
		boolean toWest = platmap.isStructureLot(platX - 1, platZ);
		boolean toEast = platmap.isStructureLot(platX + 1, platZ);
		
		chunk.setCircle(8, 8, 6, generator.streetLevel - 1, generator.streetLevel + 1, baseId, true);
		if (toNorth)
			chunk.setBlocks(0, 16, generator.streetLevel - 1, generator.streetLevel + 1, 0, 7, baseId);
		if (toSouth)
			chunk.setBlocks(0, 16, generator.streetLevel - 1, generator.streetLevel + 1, 8, 16, baseId);
		if (toWest)
			chunk.setBlocks(0, 7, generator.streetLevel - 1, generator.streetLevel + 1, 0, 16, baseId);
		if (toEast)
			chunk.setBlocks(8, 16, generator.streetLevel - 1, generator.streetLevel + 1, 0, 16, baseId);
		
		// what types of balloon?
		if (manyBalloons) {
			chunk.setBlocks(7, 9, generator.streetLevel + 1, generator.streetLevel + 5, 4, 12, pedestalId);
			chunk.setBlocks(4, 12, generator.streetLevel + 1, generator.streetLevel + 5, 7, 9, pedestalId);
		} else {
			chunk.setBlocks(7, 9, generator.streetLevel + 1, generator.streetLevel + 5, 1, 3, pedestalId);
			chunk.setBlocks(7, 9, generator.streetLevel + 1, generator.streetLevel + 5, 13, 15, pedestalId);
			chunk.setBlocks(1, 3, generator.streetLevel + 1, generator.streetLevel + 5, 7, 9, pedestalId);
			chunk.setBlocks(13, 15, generator.streetLevel + 1, generator.streetLevel + 5, 7, 9, pedestalId);
		}
	}

	@Override
	protected void generateActualBlocks(WorldGenerator generator,
			PlatMap platmap, RealChunk chunk, DataContext context, int platX,
			int platZ) {
		
		// what type of balloon?
		if (manyBalloons) {
			BalloonFactory.generateBalloon(generator, chunk, context, 7 + chunkRandom.nextInt(2), generator.streetLevel + 5, 4, chunkRandom);
			BalloonFactory.generateBalloon(generator, chunk, context, 7 + chunkRandom.nextInt(2), generator.streetLevel + 5, 11, chunkRandom);
			BalloonFactory.generateBalloon(generator, chunk, context, 4, generator.streetLevel + 5, 7 + chunkRandom.nextInt(2), chunkRandom);
			BalloonFactory.generateBalloon(generator, chunk, context, 11, generator.streetLevel + 5, 7 + chunkRandom.nextInt(2), chunkRandom);
		} else {
			BalloonFactory.generateBlimp(generator, chunk, context, generator.streetLevel + 5, chunkRandom);
		}
	}

}
