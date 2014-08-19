package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class OreProvider_Astral extends OreProvider {

	public OreProvider_Astral(WorldGenerator generator) {
		super(generator);
		
		surfaceMaterial = Material.SNOW_BLOCK;
		subsurfaceMaterial = Material.ICE;
		stratumMaterial = Material.PACKED_ICE;
		substratumMaterial = Material.BEDROCK;
		
		fluidMaterial = Material.STATIONARY_LAVA;
		fluidFluidMaterial = Material.LAVA;
		fluidSurfaceMaterial = Material.GOLD_BLOCK;
		fluidSubsurfaceMaterial = Material.DIAMOND_BLOCK;
		fluidFrozenMaterial = Material.IRON_BLOCK;
	}

	@Override
	public String getCollectionName() {
		return "Astral";
	}

	@Override
	public void sprinkleSnow(WorldGenerator generator, SupportChunk chunk, Odds odds, int x1, int x2, int y, int z1, int z2) {
	}
	
	@Override
	public void dropSnow(WorldGenerator generator, SupportChunk chunk, int x, int y, int z) {
	}
	
	@Override
	public void dropSnow(WorldGenerator generator, SupportChunk chunk, int x, int y, int z, int level) {
	}

	/**
	 * Populates the world with ores.
	 *
	 * original authors Nightgunner5, Markus Persson
	 * modified by simplex
	 * wildly modified by daddychurchill
	 */
	
	private static final Material[] ore_types = new Material[] {Material.WATER,
																Material.COAL_BLOCK,
																Material.IRON_BLOCK,
																Material.GOLD_BLOCK,
																Material.LAPIS_BLOCK,
																Material.REDSTONE_BLOCK,
																Material.DIAMOND_BLOCK,
																Material.EMERALD_BLOCK}; 
	
	//                                                         WATER   LAVA   GRAV   COAL   IRON   GOLD  LAPIS  REDST   DIAM   EMER  
	private static final int[] ore_iterations = new int[]    {     8,     6,    40,    30,    12,     4,     2,     4,     2,    10};
	private static final int[] ore_amountToDo = new int[]    {     1,     1,    12,     8,     8,     3,     3,    10,     3,     1};
	private static final int[] ore_maxY = new int[]          {   128,    32,   111,   128,    61,    29,    25,    16,    15,    32};
	private static final int[] ore_minY = new int[]          {    32,     2,    40,    16,    10,     8,     8,     6,     2,     2};
	private static final boolean[] ore_upper = new boolean[] {  true, false, false,  true,  true,  true,  true,  true, false, false};
	private static final boolean[] ore_liquid = new boolean[] { true,  true, false, false, false, false, false, false, false, false};
	
	@Override
	public void sprinkleOres(WorldGenerator generator, PlatLot lot, SupportChunk chunk, CachedYs blockYs, Odds odds, OreLocation location) {
		
		// do it!
		for (int typeNdx = 0; typeNdx < ore_types.length; typeNdx++) {
			sprinkleOre(generator, lot, chunk, blockYs,
					odds, ore_types[typeNdx], ore_maxY[typeNdx], 
					ore_minY[typeNdx], ore_iterations[typeNdx], 
					ore_amountToDo[typeNdx], ore_upper[typeNdx], ore_liquid[typeNdx]);
		}
	}
}
