package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

public abstract class ShapeProvider extends Provider {
	
	public abstract int getWorldHeight();
	public abstract int getStreetLevel();
	public abstract int getSeaLevel();
	public abstract int getLandRange();
	public abstract int getSeaRange();
	public abstract int getConstuctMin();
	public abstract int getConstuctRange();
	
	public abstract double findPerciseY(WorldGenerator generator, int blockX, int blockZ);

	public abstract void preGenerateChunk(WorldGenerator generator, PlatLot lot, ByteChunk chunk, BiomeGrid biomes, CachedYs blockYs);
	public abstract void postGenerateChunk(WorldGenerator generator, PlatLot lot, ByteChunk chunk, CachedYs blockYs);
	public abstract void preGenerateBlocks(WorldGenerator generator, PlatLot lot, RealChunk chunk, CachedYs blockYs);
	public abstract void postGenerateBlocks(WorldGenerator generator, PlatLot lot, RealChunk chunk, CachedYs blockYs);
	
	private SimplexNoiseGenerator macroShape;
	private SimplexNoiseGenerator microShape;
	protected Odds odds;
	
	public int getStructureLevel() {
		return getStreetLevel();
	}
	
	public int findBlockY(WorldGenerator generator, int blockX, int blockZ) {
		return NoiseGenerator.floor(findPerciseY(generator, blockX, blockZ));
	}
	
	public int findGroundY(WorldGenerator generator, int blockX, int blockZ) {
		return findBlockY(generator, blockX, blockZ);
	}

	public ShapeProvider(WorldGenerator generator, Odds odds) {
		super();
		this.odds = odds;
		long seed = generator.getWorldSeed();
		
		macroShape = new SimplexNoiseGenerator(seed + 2);
		microShape = new SimplexNoiseGenerator(seed + 3);
	}

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	public static ShapeProvider loadProvider(WorldGenerator generator, Odds odds) {

		switch (generator.worldStyle) {
		case FLOATING:
			return new ShapeProvider_Floating(generator, odds);
		//case UNDERGROUND
		//case FLOODED:
		//case LUNAR: // curved surface?
		default: // NORMAL
			return new ShapeProvider_Normal(generator, odds);
		}
	}
	
	public abstract PlatMap createPlatMap(WorldGenerator generator, int platX, int platZ);
	public abstract String getCollectionName();

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
			else if (y <= OreProvider.lavaFieldLevel && generator.settings.includeLavaFields)
				chunk.setBlock(x, y, z, OreProvider.stillLavaId);

		// aggregate bits
		for (int y = stratumY; y < subsurfaceY - 1; y++)
			if (lot.isValidStrataY(generator, blockX, y, blockZ) && (!surfaceCaves || generator.shapeProvider.notACave(generator, blockX, y, blockZ)))
				chunk.setBlock(x, y, z, subsurfaceId);

		// icing for the cake
		if (!surfaceCaves || generator.shapeProvider.notACave(generator, blockX, subsurfaceY, blockZ)) {
			if (lot.isValidStrataY(generator, blockX, subsurfaceY - 1, blockZ)) 
				chunk.setBlock(x, subsurfaceY - 1, z, subsurfaceId);
			if (lot.isValidStrataY(generator, blockX, subsurfaceY, blockZ)) 
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

	//TODO refactor these over to UndergroundProvider (which should include PlatLot's mines generator code)
	//TODO rename these to ifSoAndSo
	public abstract boolean isHorizontalNSShaft(int chunkX, int chunkY, int chunkZ);
	public abstract boolean isHorizontalWEShaft(int chunkX, int chunkY, int chunkZ);
	public abstract boolean isVerticalShaft(int chunkX, int chunkY, int chunkZ);
	
	//TODO refactor this so that it is a positive (maybe ifCave) instead of a negative
	public abstract boolean notACave(WorldGenerator generator, int blockX, int blockY, int blockZ);
	
	// macro slots
	private final static int macroRandomGeneratorSlot = 0;
	protected final static int macroNSBridgeSlot = 1; 
	
	// micro slots
	private final static int microRandomGeneratorSlot = 0;
	protected final static int microRoundaboutSlot = 1; 
	protected final static int microSurfaceCaveSlot = 2; 
	protected final static int microIsolatedLotSlot = 3;
	protected final static int microIsolatedConstructSlot = 4;
	
	private double macroScale = 1.0 / 384.0;
	private double microScale = 2.0;
	
	public double getMicroNoiseAt(double x, double z, int a) {
		return microShape.noise(x * microScale, z * microScale, a);
	}
	
	public double getMacroNoiseAt(double x, double z, int a) {
		return macroShape.noise(x * macroScale, z * macroScale, a);
	}
	
	public boolean macroBooleanAt(double chunkX, double chunkZ, int slot) {
		return getMacroNoiseAt(chunkX, chunkZ, slot) >= 0.0;
	}
	
	public boolean microBooleanAt(double chunkX, double chunkZ, int slot) {
		return getMicroNoiseAt(chunkX, chunkZ, slot) >= 0.0;
	}
	
	public Odds getMicroOddsGeneratorAt(int x, int z) {
		return new Odds((long) (getMicroNoiseAt(x, z, microRandomGeneratorSlot) * Long.MAX_VALUE));
	}
	
	public Odds getMacroOddsGeneratorAt(int x, int z) {
		return new Odds((long) (getMacroNoiseAt(x, z, macroRandomGeneratorSlot) * Long.MAX_VALUE));
	}
	
	public boolean getBridgePolarityAt(double chunkX, double chunkZ) {
		return macroBooleanAt(chunkX, chunkZ, macroNSBridgeSlot);
	}

	public boolean isRoundaboutAt(double chunkX, double chunkZ, double oddsOfRoundabouts) {
		return microScaleAt(chunkX, chunkZ, microRoundaboutSlot) < oddsOfRoundabouts;
	}
	
	public boolean isIsolatedConstructAt(double chunkX, double chunkZ, double oddsOfIsolatedConstruct) {
		return microScaleAt(chunkX, chunkZ, microIsolatedConstructSlot) < oddsOfIsolatedConstruct;
	}
	
	public boolean isIsolatedLotAt(double chunkX, double chunkZ, double oddsOfIsolatedLots) {
		return microScaleAt(chunkX, chunkZ, microIsolatedLotSlot) < oddsOfIsolatedLots;
	}
	
	protected int macroValueAt(double chunkX, double chunkZ, int slot, int scale) {
		return NoiseGenerator.floor(macroScaleAt(chunkX, chunkZ, slot) * scale);
	}
	
	protected int microValueAt(double chunkX, double chunkZ, int slot, int scale) {
		return NoiseGenerator.floor(microScaleAt(chunkX, chunkZ, slot) * scale);
	}
	
	protected double macroScaleAt(double chunkX, double chunkZ, int slot) {
		return (getMacroNoiseAt(chunkX, chunkZ, slot) + 1.0) / 2.0;
	}

	protected double microScaleAt(double chunkX, double chunkZ, int slot) {
		return (getMicroNoiseAt(chunkX, chunkZ, slot) + 1.0) / 2.0;
	}
	
}
