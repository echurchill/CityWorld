package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import org.bukkit.Material;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class OreProvider_Nether extends OreProvider {

	/**
	 * Populates the world with ores.
	 *
	 * @author Nightgunner5
	 * @author Markus Persson
	 * modified by simplex
	 * wildly modified by daddychurchill
	 */
	
	private static final int[] ore_types = new int[] {Material.LAVA.getId(), // since we are not using water, this doesn't trigger the placeFluid code
		  											  Material.GRAVEL.getId(), 
		  											  Material.SOUL_SAND.getId(), 
													  Material.COAL_ORE.getId(),
													  Material.IRON_ORE.getId(), 
													  Material.GOLD_ORE.getId(), 
													  Material.LAPIS_ORE.getId(),
													  Material.REDSTONE_ORE.getId(),
													  Material.DIAMOND_ORE.getId()}; 
	//                                                          LAVA   GRAV   SOUL   COAL   IRON   GOLD  LAPIS  REDST   DIAM  
	private static final int[] ore_iterations = new int[]    {     8,    40,    30,    20,    10,     4,     3,     6,     2};
	private static final int[] ore_amountToDo = new int[]    {     2,    16,    19,     4,     8,     3,     2,     4,     2};
	private static final int[] ore_maxY = new int[]          {   128,    96,   128,   128,    68,    34,    30,    17,    16};
	private static final int[] ore_minY = new int[]          {     8,    40,    16,    16,    16,     5,     5,     8,     1};
	private static final boolean[] ore_upper = new boolean[] {  true,  true,  true,  true,  true,  true,  true,  true, false};
	
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
