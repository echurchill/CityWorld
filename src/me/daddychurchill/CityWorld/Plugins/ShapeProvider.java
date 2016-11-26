package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Context.RoadContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public abstract class ShapeProvider extends Provider {
	
	public abstract int getWorldHeight();
	public abstract int getStreetLevel();
	public abstract int getSeaLevel();
	public abstract int getLandRange();
	public abstract int getSeaRange();
	public abstract int getConstuctMin();
	public abstract int getConstuctRange();
	
	public abstract double findPerciseY(CityWorldGenerator generator, int blockX, int blockZ);

	public abstract void preGenerateChunk(CityWorldGenerator generator, PlatLot lot, InitialBlocks chunk, BiomeGrid biomes, CachedYs blockYs);
	public abstract void postGenerateChunk(CityWorldGenerator generator, PlatLot lot, InitialBlocks chunk, CachedYs blockYs);
	public abstract void preGenerateBlocks(CityWorldGenerator generator, PlatLot lot, RealBlocks chunk, CachedYs blockYs);
	public abstract void postGenerateBlocks(CityWorldGenerator generator, PlatLot lot, RealBlocks chunk, CachedYs blockYs);
	
	protected abstract Biome remapBiome(CityWorldGenerator generator, PlatLot lot, Biome biome);
	protected abstract void allocateContexts(CityWorldGenerator generator);
	public abstract String getCollectionName();
	
	protected abstract void validateLots(CityWorldGenerator generator, PlatMap platmap);

	public abstract DataContext getContext(int originX, int originZ);
	public abstract DataContext getContext(PlatMap platmap);

	public CachedYs getCachedYs(CityWorldGenerator generator, int chunkX, int chunkZ) {
		return new CachedYs(generator, chunkX, chunkZ);
	}
	
	public void populateLots(CityWorldGenerator generator, PlatMap platmap) {
		try {
			allocateContexts(generator);

			// assume everything is natural for the moment
			platmap.context = natureContext;
			natureContext.populateMap(generator, platmap);
			natureContext.validateMap(generator, platmap);
			
			// place and validate the roads
			if (generator.settings.includeRoads) {
				platmap.context = getContext(platmap);
				platmap.populateRoads(); // this will see the platmap's context as natural since it hasn't been re-set yet, see below
				platmap.validateRoads();
	
				// place the buildings
				if (generator.settings.includeBuildings) {
		
					// recalculate the context based on the "natural-ness" of the platmap
//					platmap.context = getContext(platmap);
					platmap.context.populateMap(generator, platmap);
					platmap.context.validateMap(generator, platmap);
				}
				
				// one last check
				validateLots(generator, platmap);
			}
		} catch (Exception e) {
			generator.reportException("ShapeProvider.populateLots FAILED", e);

		} 
	}
	
	protected boolean contextInitialized = false;
	public DataContext natureContext;
	public RoadContext roadContext;

	private SimplexNoiseGenerator macroShape;
	private SimplexNoiseGenerator microShape;
	protected Odds odds;
	
	public int getStructureLevel() {
		return getStreetLevel();
	}
	
	public int findBlockY(CityWorldGenerator generator, int blockX, int blockZ) {
		return NoiseGenerator.floor(findPerciseY(generator, blockX, blockZ));
	}
	
	public int findGroundY(CityWorldGenerator generator, int blockX, int blockZ) {
		return findBlockY(generator, blockX, blockZ);
	}
	
	public double findPerciseFloodY(CityWorldGenerator generator, int blockX, int blockZ) {
		return getSeaLevel();
	}
	
	public int findFloodY(CityWorldGenerator generator, int blockX, int blockZ) {
		return getSeaLevel();
	}
	
	public int findHighestFloodY(CityWorldGenerator generator) {
		return getSeaLevel();
	}
	
	public int findLowestFloodY(CityWorldGenerator generator) {
		return getSeaLevel();
	}
	
//	public byte findAtmosphereIdAt(WorldGenerator generator, int blockY) {
//		return BlackMagic.airId;
//	}
	
	public boolean clearAtmosphere(CityWorldGenerator generator) {
		return true;
	}
	
	public Material findAtmosphereMaterialAt(CityWorldGenerator generator, int blockY) {
		return Material.AIR;
	}
	
//	public byte findGroundCoverIdAt(WorldGenerator generator, int blockY) {
//		return BlackMagic.airId;
//	}
	
	public Material findGroundCoverMaterialAt(CityWorldGenerator generator, int blockY) {
		return Material.AIR;
	}
	
	public PlatLot createNaturalLot(CityWorldGenerator generator, PlatMap platmap, int x, int z) {
		return natureContext.createNaturalLot(generator, platmap, x, z);
	}
	
	public PlatLot createRoadLot(CityWorldGenerator generator, PlatMap platmap, int x, int z, boolean roundaboutPart, PlatLot oldLot)  {
		return roadContext.createRoadLot(generator, platmap, x, z, roundaboutPart, oldLot);
	}

	public PlatLot createRoundaboutStatueLot(CityWorldGenerator generator, PlatMap platmap, int x, int z) {
		return roadContext.createRoundaboutStatueLot(generator, platmap, x, z);
	}

	public ShapeProvider(CityWorldGenerator generator, Odds odds) {
		super();
		this.odds = odds;
		long seed = generator.getWorldSeed();
		
		macroShape = new SimplexNoiseGenerator(seed + 2);
		microShape = new SimplexNoiseGenerator(seed + 3);
		
	}

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	public static ShapeProvider loadProvider(CityWorldGenerator generator, Odds odds) {
		
		ShapeProvider provider = null;

		switch (generator.worldStyle) {
		case FLOATING:
			provider = new ShapeProvider_Floating(generator, odds);
			break;
		case FLOODED:
			provider = new ShapeProvider_Flooded(generator, odds);
			break;
		case SANDDUNES:
			provider = new ShapeProvider_SandDunes(generator, odds);
			break;
		case SNOWDUNES:
			provider = new ShapeProvider_SnowDunes(generator, odds);
			break;
		case ASTRAL:
			provider = new ShapeProvider_Astral(generator, odds);
			break;
		case MAZE:
			provider = new ShapeProvider_Maze(generator, odds);
			break;
		case NATURE:
			provider = new ShapeProvider_Nature(generator, odds);
			break;
		case METRO:
			provider = new ShapeProvider_Metro(generator, odds);
			break;
		case DESTROYED:
		case NORMAL:
			provider = new ShapeProvider_Normal(generator, odds);
			break;
		}
		
		return provider;
	}
	
	private static int bottomOfWorld = 0;
	
	protected void actualGenerateStratas(CityWorldGenerator generator, PlatLot lot, InitialBlocks chunk, int x, int z, Material substratumMaterial, Material stratumMaterial,
			int stratumY, Material subsurfaceMaterial, int subsurfaceY, Material surfaceMaterial,
			boolean surfaceCaves) {

		// make the base
		chunk.setBlock(x, bottomOfWorld, z, substratumMaterial);
		chunk.setBlock(x, bottomOfWorld + 1, z, stratumMaterial);

		// compute the world block coordinates
		int blockX = chunk.sectionX * chunk.width + x;
		int blockZ = chunk.sectionZ * chunk.width + z;
		
		// stony bits
		for (int y = bottomOfWorld + 2; y < stratumY; y++)
			if (lot.isValidStrataY(generator, blockX, y, blockZ) && generator.shapeProvider.notACave(generator, blockX, y, blockZ))
				chunk.setBlock(x, y, z, stratumMaterial);
			else if (y <= OreProvider.lavaFieldLevel && generator.settings.includeLavaFields)
				chunk.setBlock(x, y, z, Material.STATIONARY_LAVA);

		// aggregate bits
		for (int y = stratumY; y < subsurfaceY - 1; y++)
			if (lot.isValidStrataY(generator, blockX, y, blockZ) && (!surfaceCaves || generator.shapeProvider.notACave(generator, blockX, y, blockZ)))
				chunk.setBlock(x, y, z, subsurfaceMaterial);

		// icing for the cake
		if (!surfaceCaves || generator.shapeProvider.notACave(generator, blockX, subsurfaceY, blockZ)) {
			if (lot.isValidStrataY(generator, blockX, subsurfaceY - 1, blockZ)) 
				chunk.setBlock(x, subsurfaceY - 1, z, subsurfaceMaterial);
			if (lot.isValidStrataY(generator, blockX, subsurfaceY, blockZ)) 
				chunk.setBlock(x, subsurfaceY, z, surfaceMaterial);
		}
	}

	protected void generateStratas(CityWorldGenerator generator, PlatLot lot, InitialBlocks chunk, int x, int z, Material substratumMaterial, Material stratumMaterial,
			int stratumY, Material subsurfaceMaterial, int subsurfaceY, Material surfaceMaterial,
			boolean surfaceCaves) {
	
		// a little crust please?
		actualGenerateStratas(generator, lot, chunk, x, z, substratumMaterial, stratumMaterial, stratumY, 
				subsurfaceMaterial, subsurfaceY, surfaceMaterial, surfaceCaves);
	}

	protected void generateStratas(CityWorldGenerator generator, PlatLot lot, InitialBlocks chunk, int x, int z, Material substratumMaterial, Material stratumMaterial,
			int stratumY, Material subsurfaceMaterial, int subsurfaceY, Material surfaceMaterial,
			int coverY, Material coverMaterial, boolean surfaceCaves) {

		// a little crust please?
		actualGenerateStratas(generator, lot, chunk, x, z, substratumMaterial, stratumMaterial, stratumY, 
				subsurfaceMaterial, subsurfaceY, surfaceMaterial, surfaceCaves);

		// cover it up
		for (int y = subsurfaceY + 1; y <= coverY; y++)
			chunk.setBlock(x, y, z, coverMaterial);
	}

	//TODO refactor these over to UndergroundProvider (which should include PlatLot's mines generator code)
	//TODO rename these to ifSoAndSo
	public abstract boolean isHorizontalNSShaft(int chunkX, int chunkY, int chunkZ);
	public abstract boolean isHorizontalWEShaft(int chunkX, int chunkY, int chunkZ);
	public abstract boolean isVerticalShaft(int chunkX, int chunkY, int chunkZ);
	
	//TODO refactor this so that it is a positive (maybe ifCave) instead of a negative
	public abstract boolean notACave(CityWorldGenerator generator, int blockX, int blockY, int blockZ);
	
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
	
	private double getMicroNoiseAt(double x, double z, int a) {
		return microShape.noise(x * microScale, z * microScale, a);
	}
	
	private double getMacroNoiseAt(double x, double z, int a) {
		return macroShape.noise(x * macroScale, z * macroScale, a);
	}
	
//	private int macroValueAt(double chunkX, double chunkZ, int slot, int scale) {
//		return NoiseGenerator.floor(macroScaleAt(chunkX, chunkZ, slot) * scale);
//	}
//
//	private int microValueAt(double chunkX, double chunkZ, int slot, int scale) {
//		return NoiseGenerator.floor(microScaleAt(chunkX, chunkZ, slot) * scale);
//	}
//
//	private double macroScaleAt(double chunkX, double chunkZ, int slot) {
//		return (getMacroNoiseAt(chunkX, chunkZ, slot) + 1.0) / 2.0;
//	}

	private double microScaleAt(double chunkX, double chunkZ, int slot) {
		return (getMicroNoiseAt(chunkX, chunkZ, slot) + 1.0) / 2.0;
	}
	
	private boolean macroBooleanAt(double chunkX, double chunkZ, int slot) {
		return getMacroNoiseAt(chunkX, chunkZ, slot) >= 0.0;
	}
	
	private boolean microBooleanAt(double chunkX, double chunkZ, int slot) {
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

	public boolean isSurfaceCaveAt(double chunkX, double chunkZ) {
		return microBooleanAt(chunkX, chunkZ, microSurfaceCaveSlot);
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
	
}
