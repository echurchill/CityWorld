package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Biome;

public abstract class OreProvider extends Provider {

//	public final static int lavaFluidLevel = 24;
	public final static int lavaFieldLevel = 12;
	protected final static double oreSprinkleOdds = Odds.oddsHalvedPrettyLikely;
	protected final static double snowSprinkleOdds = Odds.oddsThricedSomewhatUnlikely;
	
	protected List<Material> ore_types = new ArrayList<Material>();
	
	public Material surfaceMaterial;
	public Material subsurfaceMaterial;
	public Material stratumMaterial;
	public Material substratumMaterial;
	
	public Material fluidMaterial;
	public Material fluidFluidMaterial;
	public Material fluidSurfaceMaterial;
	public Material fluidSubsurfaceMaterial;
	public Material fluidFrozenMaterial;
	
	public OreProvider(CityWorldGenerator generator) {
		super();
		
		surfaceMaterial = Material.GRASS;
		subsurfaceMaterial = Material.DIRT;
		stratumMaterial = Material.STONE;
		substratumMaterial = Material.BEDROCK;
		
		fluidMaterial = Material.STATIONARY_WATER;
		fluidFluidMaterial = Material.WATER;
		fluidSurfaceMaterial = Material.SAND;
		fluidSubsurfaceMaterial = Material.GRAVEL;
		fluidFrozenMaterial = Material.PACKED_ICE;
	}
	
	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	public static OreProvider loadProvider(CityWorldGenerator generator) {

		OreProvider provider = null;
		
		// default to stock OreProvider
		if (provider == null) {

			switch (generator.worldStyle) {
			case ASTRAL:
				provider = new OreProvider_Astral(generator);
				break;
			case SNOWDUNES:
				provider = new OreProvider_SnowDunes(generator);
				break;
			case SANDDUNES:
				provider = new OreProvider_SandDunes(generator);
				break;
			case FLOODED:
			case FLOATING:
			case DESTROYED:
			case MAZE:
			case NATURE:
			case METRO:
			case NORMAL:
				switch (generator.worldEnvironment) {
				case NETHER:
					provider = new OreProvider_Nether(generator);
					break;
				case THE_END:
					provider = new OreProvider_TheEnd(generator);
					break;
				case NORMAL:
					if (generator.settings.includeDecayedNature)
						provider = new OreProvider_Decayed(generator);
					else
						provider = new OreProvider_Normal(generator);
					break;
				}
			}
		}
	
		return provider;
	}
	
	public Biome remapBiome(Biome biome) {
		return biome;
	}

	public void sprinkleSnow(CityWorldGenerator generator, SupportBlocks chunk, Odds odds, int x1, int x2, int y, int z1, int z2) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				if (odds.playOdds(snowSprinkleOdds))
					chunk.setBlock(x, y, z, Material.SNOW_BLOCK);
			}
		}
	}
	
	public void dropSnow(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z) {
		dropSnow(generator, chunk, x, y, z, 0);
	}
	
	public void dropSnow(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z, int level) {
		y = chunk.findLastEmptyBelow(x, y + 1, z, y - 6);
		if (!chunk.isOfTypes(x, y - 1, z, Material.AIR, Material.SNOW))
			chunk.setSnowCover(x, y, z, level);
	}

	/**
	 * Populates the world with ores.
	 *
	 * original authors Nightgunner5, Markus Persson
	 * modified by simplex
	 * wildly modified by daddychurchill
	 */
	
	//                                                         WATER   LAVA   GRAV   COAL   IRON   GOLD  LAPIS  REDST   DIAM   EMER  
	static final int[] ore_iterations = new int[]    {     8,     6,    40,    30,    12,     4,     2,     4,     2,    10};
	static final int[] ore_amountToDo = new int[]    {     1,     1,    12,     8,     8,     3,     3,    10,     3,     1};
	static final int[] ore_maxY = new int[]          {   128,    32,   111,   128,    61,    29,    25,    16,    15,    32};
	static final int[] ore_minY = new int[]          {    32,     2,    40,    16,    10,     8,     8,     6,     2,     2};
	static final boolean[] ore_upper = new boolean[] {  true, false, false,  true,  true,  true,  true,  true, false, false};
	static final boolean[] ore_liquid = new boolean[] { true,  true, false, false, false, false, false, false, false, false};
	
	public void sprinkleOres(CityWorldGenerator generator, PlatLot lot, SupportBlocks chunk, CachedYs blockYs, Odds odds) {
		
		// do it... maybe!
		int oreCount = Math.min(ore_types.size(), ore_iterations.length); 
		for (int typeNdx = 0; typeNdx < oreCount; typeNdx++) {
			sprinkleOre(generator, lot, chunk, blockYs,
					odds, ore_types.get(typeNdx), ore_maxY[typeNdx], 
					ore_minY[typeNdx], ore_iterations[typeNdx], 
					ore_amountToDo[typeNdx], ore_upper[typeNdx], ore_liquid[typeNdx]);
		}
	}
	
	private void sprinkleOre(CityWorldGenerator generator, PlatLot lot, SupportBlocks chunk, CachedYs blockYs,
			Odds odds, Material material, int maxY, int minY, 
			int iterations, int amount, boolean mirror, boolean liquid) {
		
		// do we do this one?
		if ((liquid && generator.settings.includeUndergroundFluids) ||
			(!liquid && generator.settings.includeOres)) {
			if (material != stratumMaterial) {
				
				// sprinkle it around!
				int range = maxY - minY;
				for (int iter = 0; iter < iterations; iter++) {
					int x = odds.getRandomInt(16);
					int y = odds.getRandomInt(range) + minY;
					int z = odds.getRandomInt(16);
					if (y < blockYs.getBlockY(x, z))
						growVein(generator, lot, chunk, blockYs, odds, x, y, z, amount, material);
					if (mirror) {
						y = (generator.seaLevel + generator.landRange) - minY - odds.getRandomInt(range);
						if (y < blockYs.getBlockY(x, z))
							growVein(generator, lot, chunk, blockYs, odds, x, y, z, amount, material);
					}
				}
			}
		}
	}
	
	private void growVein(CityWorldGenerator generator, PlatLot lot, SupportBlocks chunk, CachedYs blockYs, 
			Odds odds, int originX, int originY, int originZ, int amountToDo, Material material) {
		int trysLeft = amountToDo * 2;
		int oresDone = 0;
		if (lot.isValidStrataY(generator, originX, originY, originZ) && 
			blockYs.getBlockY(originX, originZ) > originY + amountToDo / 4) {
			while (oresDone < amountToDo && trysLeft > 0) {
				
				// shimmy
				int x = originX + odds.getRandomInt(Math.max(1, amountToDo / 2)) - amountToDo / 4;
				int y = originY + odds.getRandomInt(Math.max(1, amountToDo / 4)) - amountToDo / 8;
				int z = originZ + odds.getRandomInt(Math.max(1, amountToDo / 2)) - amountToDo / 4;
				
				// ore it is
				oresDone += placeOre(generator, chunk, odds, x, y, z, amountToDo - oresDone, material);
				
				// one less try to try
				trysLeft--;
			}
		}
	}
	
	private int placeOre(CityWorldGenerator generator, SupportBlocks chunk, Odds odds, 
			int centerX, int centerY, int centerZ, int oresToDo, Material material) {
		int count = 0;
		if (centerY > 0 && centerY < chunk.height) {
			if (placeBlock(chunk, odds, centerX, centerY, centerZ, material)) {
				count++;
				if (count < oresToDo && centerX < 15 && placeBlock(chunk, odds, centerX + 1, centerY, centerZ, material))
					count++;
				if (count < oresToDo && centerX > 0 && placeBlock(chunk, odds, centerX - 1, centerY, centerZ, material))
					count++;
				if (count < oresToDo && centerZ < 15 && placeBlock(chunk, odds, centerX, centerY, centerZ + 1, material))
					count++;
				if (count < oresToDo && centerZ > 0 && placeBlock(chunk, odds, centerX, centerY, centerZ - 1, material))
					count++;
			}
		}
		return count;
	}
	
	private boolean placeBlock(SupportBlocks chunk, Odds odds, int x, int y, int z, Material material) {
		if (odds.playOdds(oreSprinkleOdds))
			if (chunk.isType(x, y, z, stratumMaterial)) {
				chunk.setBlock(x, y, z, material);
				return true;
			}
		return false;
	}

}
