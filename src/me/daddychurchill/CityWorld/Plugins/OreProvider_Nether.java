package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;
import org.bukkit.block.Biome;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class OreProvider_Nether extends OreProvider {
	
//	public final static byte stillLavaId = (byte) Material.STATIONARY_LAVA.getId();
//	public final static byte netherrackId = (byte) Material.NETHERRACK.getId();
	
	public OreProvider_Nether(CityWorldGenerator generator) {
		super(generator);
		
		surfaceMaterial = Material.NETHERRACK;
		subsurfaceMaterial = Material.NETHERRACK;
		stratumMaterial = Material.NETHERRACK;
		
		fluidMaterial = Material.STATIONARY_LAVA;
		fluidSubsurfaceMaterial = Material.NETHERRACK;
		fluidSurfaceMaterial = Material.NETHERRACK;
	}

	@Override
	public String getCollectionName() {
		return "Nether";
	}

	@Override
	public Biome remapBiome(Biome biome) {
		return Biome.HELL;
	}

	/**
	 * Populates the world with ores.
	 *
	 * original authors Nightgunner5, Markus Persson
	 * modified by simplex
	 * wildly modified by daddychurchill
	 */
	
	private static final Material[] ore_types = new Material[] {Material.LAVA,
																Material.GRAVEL,
																Material.SOUL_SAND,
																Material.GLOWSTONE};
														
	//                                                          LAVA   GRAV   SOUL   GLOW  
	private static final int[] ore_iterations = new int[]    {    12,    20,    40,    20};
	private static final int[] ore_amountToDo = new int[]    {     2,    16,    16,    10};
	private static final int[] ore_maxY = new int[]          {   128,    96,   128,   128};
	private static final int[] ore_minY = new int[]          {     8,    40,    16,    16};
	private static final boolean[] ore_upper = new boolean[] {  true,  true,  true,  true};
	private static final boolean[] ore_liquid = new boolean[] { true, false, false, false};
	
	@Override
	public void sprinkleOres(CityWorldGenerator generator, PlatLot lot, SupportChunk chunk, CachedYs blockYs, Odds odds, OreLocation location) {
		
		for (int typeNdx = 0; typeNdx < ore_types.length; typeNdx++) {
			sprinkleOre(generator, lot, chunk, blockYs,
					odds, ore_types[typeNdx], ore_maxY[typeNdx], 
					ore_minY[typeNdx], ore_iterations[typeNdx], 
					ore_amountToDo[typeNdx], ore_upper[typeNdx], ore_liquid[typeNdx]);
		}
	}
	
	@Override
	public void dropSnow(CityWorldGenerator generator, SupportChunk chunk, int x, int y, int z, int level) {
		
		// do nothing
	}
}
