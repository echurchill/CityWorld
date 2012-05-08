package me.daddychurchill.CityWorld;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import me.daddychurchill.CityWorld.Context.ContextAllPark;
import me.daddychurchill.CityWorld.Context.ContextCityCenter;
import me.daddychurchill.CityWorld.Context.ContextHighrise;
import me.daddychurchill.CityWorld.Context.ContextLowrise;
import me.daddychurchill.CityWorld.Context.ContextMall;
import me.daddychurchill.CityWorld.Context.ContextMidrise;
import me.daddychurchill.CityWorld.Context.ContextUnfinished;
import me.daddychurchill.CityWorld.Context.PlatMapContext;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class CityWorldChunkGenerator extends ChunkGenerator {

	private CityWorld plugin;
	private World world;
	private String worldname;
	private String worldstyle;

	public SimplexOctaveGenerator landShape;
	public SimplexOctaveGenerator seaShape;
	public SimplexOctaveGenerator noiseShape;
	public SimplexOctaveGenerator featureShape;
	public SimplexNoiseGenerator caveShape;
	public SimplexNoiseGenerator oreShape;

	public CityWorldChunkGenerator(CityWorld aPlugin, String aWorldname, String aWorldstyle) {
		plugin = aPlugin;
		worldname = aWorldname;
		worldstyle = aWorldstyle;
	}

	public CityWorld getPlugin() {
		return plugin;
	}

	public String getWorldname() {
		return worldname;
	}

	public String getWorldstyle() {
		return worldstyle;
	}

	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		return Arrays.asList((BlockPopulator) new CityWorldBlockPopulator(this));
	}
	
	private final static int landFlattening = 32;
	private final static int seaFlattening = 8;

	private final static double landFrequency = 1.00;
	private final static double landAmplitude = 4.00;
	private final static double landHorizontalScale = 1.0 / 512.0;

	private final static double seaFrequency = 1.00;
	private final static double seaAmplitude = 2.00;
	private final static double seaHorizontalScale = 1.0 / 384.0;

	private final static double noiseFrequency = 1.50;
	private final static double noiseAmplitude = 0.50;
	private final static double noiseHorizontalScale = 1.0 / 32.0;
	private final static int noiseVerticalScale = 3;

	private final static double featureFrequency = 1.50;
	private final static double featureAmplitude = 0.75;
	private final static double featureHorizontalScale = 1.0 / 64.0;
	private final static int featureVerticalScale = 15;

	private final static int fudgeVerticalScale = noiseVerticalScale + featureVerticalScale;

	private final static double caveScale = 1.0 / 32.0;
	private final static double caveScaleY = caveScale * 2;
	private final static double caveThreshold = 0.70;

	private final static double oreScale = 1.0 / 16.0;
	private final static double oreScaleY = oreScale * 2;
	private final static double oreThreshold = 0.90;

	private final static byte snowId = (byte) Material.SNOW_BLOCK.getId();
	private final static byte grassId = (byte) Material.GRASS.getId();
	private final static byte dirtId = (byte) Material.DIRT.getId();
	private final static byte stoneId = (byte) Material.STONE.getId();
	private final static byte waterId = (byte) Material.STATIONARY_WATER.getId();
	private final static byte sandId = (byte) Material.SAND.getId();
	private final static byte sandstoneId = (byte) Material.SANDSTONE.getId();
	private final static byte bedrockId = (byte) Material.BEDROCK.getId();
	private final static byte brickId = (byte) Material.SMOOTH_BRICK.getId();

	@Override
	public byte[][] generateBlockSections(World aWorld, Random random, int chunkX, int chunkZ, BiomeGrid biomes) {

		// initialize the shaping logic
		if (world == null) {
			world = aWorld;
			long seed = world.getSeed();

			landShape = new SimplexOctaveGenerator(seed, 4);
			landShape.setScale(landHorizontalScale);
			seaShape = new SimplexOctaveGenerator(seed, 8);
			seaShape.setScale(seaHorizontalScale);
			noiseShape = new SimplexOctaveGenerator(seed, 16);
			noiseShape.setScale(noiseHorizontalScale);
			featureShape = new SimplexOctaveGenerator(seed, 2);
			featureShape.setScale(featureHorizontalScale);
			caveShape = new SimplexNoiseGenerator(seed);
			oreShape = new SimplexNoiseGenerator(seed + 1);
		}

		// place to work
		ByteChunk byteChunk = new ByteChunk(world, chunkX, chunkZ);
		boolean buildableSpot = true;
		
		// get ranges
		double landRange = byteChunk.height - byteChunk.sealevel - fudgeVerticalScale + landFlattening;
		double seaRange = byteChunk.sealevel - fudgeVerticalScale + seaFlattening;

		// shape the world
		for (int x = 0; x < byteChunk.width; x++) {
			for (int z = 0; z < byteChunk.width; z++) {
				int y = 0;

				// make the base
				byteChunk.setBlock(x, 0, z, bedrockId);

				// compute the world block coordinates
				int blockX = chunkX * byteChunk.width + x;
				int blockZ = chunkZ * byteChunk.width + z;

				// shape the noise
				double noise = noiseShape.noise(blockX, blockZ, noiseFrequency, noiseAmplitude, true);
				double feature = featureShape.noise(blockX, blockZ, featureFrequency, featureAmplitude, true);

				// shape the shapes
				double land = landShape.noise(blockX, blockZ, landFrequency, landAmplitude, true);
				double sea = seaShape.noise(blockX, blockZ, seaFrequency, seaAmplitude, true);
				
				// calculate the Ys
				int landY = byteChunk.sealevel + NoiseGenerator.floor(land * landRange) + 
						NoiseGenerator.floor(noise * noiseVerticalScale + feature * featureVerticalScale) - landFlattening;
				int seaY = byteChunk.sealevel + NoiseGenerator.floor(sea * seaRange) + 
						NoiseGenerator.floor(noise * noiseVerticalScale) + seaFlattening;

				// land is below the sea
				if (landY <= byteChunk.sealevel) {

					// if seabed is too high... then we might be buildable
					if (seaY >= byteChunk.sealevel) {
						y = byteChunk.sealevel + 1;

						// if we are too near the sea then we must be on the beach
						if (seaY <= byteChunk.sealevel + 3) {
							y = byteChunk.sealevel;
						}

					// if land is higher than the seabed use land to smooth
					// out under water base of the mountains 
					} else if (landY > seaY) {
						y = Math.min(byteChunk.sealevel, landY);

						// otherwise just take the sea bed as is
					} else {
						y = Math.min(byteChunk.sealevel, seaY);
					}

					// must be a mountain then
				} else {
					y = Math.max(byteChunk.sealevel, landY);
				}

				// range validation
				y = Math.min(byteChunk.height - 3, Math.max(y, 3));

				// buildable?
				if (y == byteChunk.sealevel + 1) {
					generateCrust(byteChunk, x, z, stoneId, y - 2, dirtId, y, grassId, false);
					biomes.setBiome(x, z, Biome.PLAINS);
				} else {
					buildableSpot = false;

					// on the beach
					if (y == byteChunk.sealevel) {
						generateCrust(byteChunk, x, z, stoneId, y - 2, sandId, y, sandId, false);
						biomes.setBiome(x, z, Biome.BEACH);

						// we are in the water!
					} else if (y < byteChunk.sealevel) {
						generateCrust(byteChunk, x, z, stoneId, y - 2, sandstoneId, y, sandId, byteChunk.sealevel, waterId, true);
						biomes.setBiome(x, z, Biome.OCEAN);

						// we are in the mountains
					} else {

						// what treeline are we at?
						if (y < byteChunk.treelevel - fudgeVerticalScale) {
							generateCrust(byteChunk, x, z, stoneId, y - 2, dirtId, y, grassId, false);
							biomes.setBiome(x, z, Biome.FOREST_HILLS);

						} else if (y < byteChunk.snowlevel - fudgeVerticalScale) {
							generateCrust(byteChunk, x, z, stoneId, y - 2, stoneId, y, grassId, true);
							biomes.setBiome(x, z, Biome.EXTREME_HILLS);

						} else {
							generateCrust(byteChunk, x, z, stoneId, y - 1, grassId, y, snowId, true);
							biomes.setBiome(x, z, Biome.ICE_MOUNTAINS);
						}
					}
				}
			}
		}

		// TODO if everything is buildable then let's show it somehow
		if (buildableSpot) {
			byteChunk.setBlocks(1, 16, byteChunk.sealevel, byteChunk.sealevel + 4, 1, 16, brickId);
		}

		// figure out what everything looks like
		PlatMap platmap = getPlatMap(random, chunkX, chunkZ);
		if (platmap != null) {
			platmap.generateChunk(byteChunk, biomes);
		}

		return byteChunk.blocks;
	}

	private void generateCrust(ByteChunk byteChunk, int x, int z, byte baseId,
			int baseY, byte substrateId, int substrateY, byte surfaceId,
			boolean surfaceCaves) {

		// compute the world block coordinates
		int blockX = byteChunk.chunkX * byteChunk.width + x;
		int blockZ = byteChunk.chunkZ * byteChunk.width + z;

		// stony bits
		for (int y = 1; y < baseY; y++)
			if (notACave(blockX, y, blockZ))
				byteChunk.setBlock(x, y, z, getOre(byteChunk, blockX, y, blockZ, baseId));

		// aggregate bits
		for (int y = baseY; y < substrateY; y++)
			if (!surfaceCaves || notACave(blockX, y, blockZ))
				byteChunk.setBlock(x, y, z, substrateId);

		// icing for the cake
		if (!surfaceCaves || notACave(blockX, substrateY, blockZ))
			byteChunk.setBlock(x, substrateY, z, surfaceId);

	}

	private void generateCrust(ByteChunk byteChunk, int x, int z, byte baseId,
			int baseY, byte substrateId, int substrateY, byte surfaceId,
			int coverY, byte coverId, boolean surfaceCaves) {

		// a little crust please?
		generateCrust(byteChunk, x, z, baseId, baseY, substrateId, substrateY, surfaceId, surfaceCaves);

		// cover it up
		for (int y = substrateY + 1; y <= coverY; y++)
			byteChunk.setBlock(x, y, z, coverId);
	}

	private boolean notACave(int blockX, int blockY, int blockZ) {

		// cave or not?
		double cave = caveShape.noise(blockX * caveScale, blockY * caveScaleY, blockZ * caveScale);
		return !(cave > caveThreshold || cave < -caveThreshold);
	}

	private byte getOre(ByteChunk byteChunk, int blockX, int blockY,
			int blockZ, byte defaultId) {

		// ore or not?
		double ore = oreShape.noise(blockX * oreScale, blockY * oreScaleY, blockZ * oreScale);
		if (ore > oreThreshold || ore < -oreThreshold)
			return byteChunk.getOre(blockY);
		else
			return defaultId;
	}

	@Override
	public Location getFixedSpawnLocation(World world, Random random) {
		int spawnX = random.nextInt(200) - 100;
		int spawnZ = random.nextInt(200) - 100;
		
		// find the first non empty spot;
		int spawnY = 256;
		while ((spawnY > 0) && world.getBlockAt(spawnX, spawnY - 1, spawnZ).isEmpty()) {
			spawnY--;
		}
		
		// return the location
		return new Location(world, spawnX, spawnY, spawnZ);
	}

	// manager for handling the city plat maps collection
	private Hashtable<Long, PlatMap> platmaps;
	private PlatMap getPlatMap(Random random, int chunkX, int chunkZ) {

		// get the plat map collection
		if (platmaps == null)
			platmaps = new Hashtable<Long, PlatMap>();

		// find the origin for the plat
		int platX = calcOrigin(chunkX);
		int platZ = calcOrigin(chunkZ);

		// calculate the plat's key
		Long platkey = Long.valueOf(((long) platX << 32 + (long) platZ));

		// get the right plat
		PlatMap platmap = platmaps.get(platkey);

		// // doesn't exist? then make it!
		// if (platmap == null) {
		//
		// // what is the context for this one?
		// PlatMapContext context = getContext(chunkX, chunkZ);
		// platmap = new PlatMap(world, random, context, platX, platZ);
		//
		// // remember it for quicker look up
		// platmaps.put(platkey, platmap);
		// }

		// finally return the plat
		return platmap;
	}

	private PlatMapContext getContext(int chunkX, int chunkZ) {
		// TODO derive this from the CityNoise generator
		//
		// switch (random.nextInt(20)) {
		// case 0:
		// case 1:
		// case 2:
		// case 3:
		// return new ContextLowrise(plugin, world, random);
		// case 4:
		// case 5:
		// case 6:
		// case 7:
		// return new ContextMidrise(plugin, world, random);
		// case 8:
		// case 9:
		// case 10:
		// return new ContextHighrise(plugin, world, random);
		// case 11:
		// case 12:
		// return new ContextAllPark(plugin, world, random);
		// case 13:
		// case 14:
		// return new ContextMall(plugin, world, random);
		// case 15:
		// case 16:
		// case 17:
		// return new ContextCityCenter(plugin, world, random);
		// case 18:
		// case 19:
		// default:
		// return new ContextUnfinished(plugin, world, random);
		// }
		return null;
	}

	// Supporting code used by getPlatMap
	private int calcOrigin(int i) {
		if (i >= 0) {
			return i / PlatMap.Width * PlatMap.Width;
		} else {
			return -((Math.abs(i + 1) / PlatMap.Width * PlatMap.Width) + PlatMap.Width);
		}
	}

	private class CityWorldBlockPopulator extends BlockPopulator {

		private CityWorldChunkGenerator chunkGen;

		public CityWorldBlockPopulator(CityWorldChunkGenerator chunkGen) {
			this.chunkGen = chunkGen;
		}

		@Override
		public void populate(World aWorld, Random aRandom, Chunk aSource) {
			int chunkX = aSource.getX();
			int chunkZ = aSource.getZ();

			// place to work
			RealChunk chunk = new RealChunk(aWorld, aSource);

			// figure out what everything looks like
			PlatMap platmap = chunkGen.getPlatMap(aRandom, chunkX, chunkZ);
			if (platmap != null) {
				platmap.generateBlocks(chunk);
			}
		}
	}
}
