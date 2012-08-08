package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.block.Biome;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public abstract class ShapeProvider {
	
	public abstract int getWorldHeight();
	public abstract int getStreetLevel();
	public abstract int getSeaLevel();
	public abstract int getLandRange();
	public abstract int getSeaRange();
	
	public abstract double findPerciseY(WorldGenerator generator, int blockX, int blockZ);
	public abstract Biome generateCrust(WorldGenerator generator, PlatLot lot, ByteChunk chunk, int x, int y, int z, boolean surfaceCaves);

	//TODO refactor these over to UndergroundProvider (which should include PlatLot's mines generator code)
	//TODO rename these to ifSoAndSo
	public abstract boolean isHorizontalNSShaft(int chunkX, int chunkY, int chunkZ);
	public abstract boolean isHorizontalWEShaft(int chunkX, int chunkY, int chunkZ);
	public abstract boolean isVerticalShaft(int chunkX, int chunkY, int chunkZ);
	
	//TODO refactor this so that it is a positive (maybe ifCave) instead of a negative
	public abstract boolean notACave(WorldGenerator generator, int blockX, int blockY, int blockZ);
	
	private SimplexNoiseGenerator macroShape;
	private SimplexNoiseGenerator microShape;
	
	private double macroScale = 1.0 / 384.0;
	private double microScale = 2.0;
	
	public double getMicroNoiseAt(double x, double z, int a) {
		return microShape.noise(x * microScale, z * microScale, a);
	}
	
	public double getMacroNoiseAt(double x, double z, int a) {
		return macroShape.noise(x * macroScale, z * macroScale, a);
	}
	
	public ShapeProvider(WorldGenerator generator) {
		super();
		long seed = generator.getWorldSeed();
		
		macroShape = new SimplexNoiseGenerator(seed + 2);
		microShape = new SimplexNoiseGenerator(seed + 3);
	}

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	public static ShapeProvider loadProvider(WorldGenerator generator) {

		switch (generator.settings.mapStyle) {
		case FLOATING:
			return new ShapeProvider_Floating(generator);
		//case UNDERGROUND
		//case FLOODED:
		//case LUNAR: // curved surface?
		default: // NORMAL
			return new ShapeProvider_Normal(generator);
		}
	}
	
	public abstract PlatMap createPlatMap(WorldGenerator generator, SupportChunk cornerChunk, int platX, int platZ);

	protected void generateStratas(WorldGenerator generator, PlatLot lot, ByteChunk chunk, int x, int z, byte substratumId, byte stratumId,
			int stratumY, byte subsurfaceId, int subsurfaceY, byte surfaceId,
			boolean surfaceCaves) {

		// make the base
		chunk.setBlock(x, 0, z, substratumId);
		chunk.setBlock(x, 1, z, stratumId);

		// compute the world block coordinates
		int blockX = chunk.chunkX * chunk.width + x;
		int blockZ = chunk.chunkZ * chunk.width + z;
		
		// stony bits
		for (int y = 2; y < stratumY; y++)
			if (lot.isValidStrataY(generator, blockX, y, blockZ) && generator.shapeProvider.notACave(generator, blockX, y, blockZ))
				chunk.setBlock(x, y, z, stratumId);

		// aggregate bits
		for (int y = stratumY; y < subsurfaceY - 1; y++)
			if (!surfaceCaves || generator.shapeProvider.notACave(generator, blockX, y, blockZ))
				chunk.setBlock(x, y, z, subsurfaceId);

		// icing for the cake
		if (!surfaceCaves || generator.shapeProvider.notACave(generator, blockX, subsurfaceY, blockZ)) {
			chunk.setBlock(x, subsurfaceY - 1, z, subsurfaceId);
			chunk.setBlock(x, subsurfaceY, z, surfaceId);
		}
	}

	protected void generateStratas(WorldGenerator generator, PlatLot lot, ByteChunk chunk, int x, int z, byte substratumId, byte statumId,
			int stratumY, byte subsurfaceId, int subsurfaceY, byte surfaceId,
			int coverY, byte coverId, boolean surfaceCaves) {

		// a little crust please?
		generateStratas(generator, lot, chunk, x, z, substratumId, statumId, stratumY, subsurfaceId, subsurfaceY, surfaceId, surfaceCaves);

		// cover it up
		for (int y = subsurfaceY + 1; y <= coverY; y++)
			chunk.setBlock(x, y, z, coverId);
	}
}
