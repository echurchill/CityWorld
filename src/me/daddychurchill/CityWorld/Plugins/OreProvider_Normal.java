package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.RealChunk;
import org.bukkit.Material;

public class OreProvider_Normal extends OreProvider {

	public OreProvider_Normal(WorldGenerator generator) {
		super(generator);
	}
	
	/**
	 * Populates the world with ores.
	 *
	 * @author Nightgunner5
	 * @author Markus Persson
	 * modified by simplex
	 * wildly modified by daddychurchill
	 */
	
	private static final int[] ore_types = new int[] {Material.WATER.getId(),
		  											  Material.GRAVEL.getId(), 
													  Material.COAL_ORE.getId(),
													  Material.IRON_ORE.getId(), 
													  Material.GOLD_ORE.getId(), 
													  Material.LAPIS_ORE.getId(),
													  Material.REDSTONE_ORE.getId(),
													  Material.DIAMOND_ORE.getId()}; 
	//                                                         WATER   GRAV   COAL   IRON   GOLD  LAPIS  REDST   DIAM  
	private static final int[] ore_iterations = new int[]    {     4,    40,    35,    12,     4,     3,     6,     2};
	private static final int[] ore_amountToDo = new int[]    {     1,     8,     8,     8,     3,     2,     4,     2};
	private static final int[] ore_maxY = new int[]          {   128,    96,   128,    68,    34,    30,    17,    16};
	private static final int[] ore_minY = new int[]          {     8,    40,    16,    16,     5,     5,     8,     1};
	private static final boolean[] ore_upper = new boolean[] {  true, false,  true,  true,  true,  true,  true, false};
	
	@Override
	public void sprinkleOres(WorldGenerator generator, RealChunk chunk, Random random, OreLocation location) {
		
		// do it!
		for (int typeNdx = 0; typeNdx < ore_types.length; typeNdx++) {
			int range = ore_maxY[typeNdx] - ore_minY[typeNdx];
			for (int iter = 0; iter < ore_iterations[typeNdx]; iter++) {
				sprinkleOres_iterate(generator, chunk, random, 
						random.nextInt(16), random.nextInt(range) + ore_minY[typeNdx], random.nextInt(16), 
						ore_amountToDo[typeNdx], ore_types[typeNdx]);
				if (ore_upper[typeNdx])
					sprinkleOres_iterate(generator, chunk, random, 
						random.nextInt(16), (generator.seaLevel + generator.landRange) - ore_minY[typeNdx] - random.nextInt(range), random.nextInt(16), 
						ore_amountToDo[typeNdx], ore_types[typeNdx]);
			}
		}
	}
}
