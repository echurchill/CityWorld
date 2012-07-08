package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class OreProvider_Tekkit extends OreProvider {

	public OreProvider_Tekkit() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Populates the world with ores.
	 *
	 * @author Nightgunner5
	 * @author Markus Persson
	 * modified by simplex
	 * wildly modified by daddychurchill
	 * tekkit support by gunre
	 */
	
	private static final int[] ore_types_tekkit = new int[] {Material.WATER.getId(),
		  Material.GRAVEL.getId(), 
		  Material.COAL_ORE.getId(),
		  Material.IRON_ORE.getId(), 
		  Material.GOLD_ORE.getId(), 
		  Material.LAPIS_ORE.getId(),
		  Material.REDSTONE_ORE.getId(),
		  Material.DIAMOND_ORE.getId(),
		  TekkitMaterial.RUBY_ORE,
		  TekkitMaterial.EMERALD_ORE,
		  TekkitMaterial.SAPPHIRE_ORE,
		  TekkitMaterial.SILVER_ORE,
		  TekkitMaterial.TIN_ORE,
		  TekkitMaterial.COPPER_ORE,
		  TekkitMaterial.TUNGSTEN_ORE,
		  TekkitMaterial.NIKOLITE_ORE,
		  TekkitMaterial.MARBLE_BLOCK,
		  TekkitMaterial.BASALT_BLOCK,
		  TekkitMaterial.URANIUM_ORE
		  };
	
	//                                                   		      WATER   GRAV   COAL   IRON   GOLD  LAPIS  REDST   DIAM   RUBY   EMER   SAPP   SILV    TIN  COPPR   TUNG   NIKO   MARB   BASA   URAN  
	private static final int[] ore_iterations_tekkit = new int[]    {     4,    20,    15,     6,     2,     2,     4,     1,     1,     1,     1,     3,     6,     6,     2,     2,    30,    20,     1};
	private static final int[] ore_amountToDo_tekkit = new int[]    {     1,     6,     8,     6,     3,     2,     4,     2,     2,     2,     2,     4,     6,     6,     3,     8,    20,     8,     1};
	private static final int[] ore_maxY_tekkit = new int[]          {   128,    96,   128,    68,    34,    30,    17,    16,    16,    16,    16,    34,    68,    96,    34,    68,   128,    10,    16};
	private static final int[] ore_minY_tekkit = new int[]          {     8,    40,    16,    16,     5,     5,     8,     1,     2,     2,     2,     5,    16,    16,     2,     2,    40,     1,     2};
	private static final boolean[] ore_upper_tekkit = new boolean[] {  true, false,  true,  true,  true,  true,  true, false, false, false, false,  true,  true,  true,  true,  true,  true, false, false};

	@Override
	public void sprinkleOres(WorldGenerator generator, RealChunk chunk, String name) {

		// we could/should do a different ore distribution based on the string passed in... for now we just have this
		Random random = chunk.random;
		
		// do it!
		for (int typeNdx = 0; typeNdx < ore_types_tekkit.length; typeNdx++) {
			int range = ore_maxY_tekkit[typeNdx] - ore_minY_tekkit[typeNdx];
			for (int iter = 0; iter < ore_iterations_tekkit[typeNdx]; iter++) {
				sprinkleOres_iterate(generator, chunk, random, 
						random.nextInt(16), random.nextInt(range) + ore_minY_tekkit[typeNdx], random.nextInt(16), 
						ore_amountToDo_tekkit[typeNdx], ore_types_tekkit[typeNdx]);
				if (ore_upper_tekkit[typeNdx])
					sprinkleOres_iterate(generator, chunk, random, 
						random.nextInt(16), (generator.seaLevel + generator.landRange) - ore_minY_tekkit[typeNdx] - random.nextInt(range), random.nextInt(16), 
						ore_amountToDo_tekkit[typeNdx], ore_types_tekkit[typeNdx]);
			}
		}
	}
	
	@Override
	protected boolean sprinkleOres_placeThing(RealChunk chunk, Random random, int x, int y, int z, int typeId, boolean physics) {
		if (random.nextDouble() < 0.35) {
			Block block = chunk.getActualBlock(x, y, z);
			if (block.getTypeId() == stoneId) {
				
				//TODO I like this idea, generalize it!
				if (typeId > TekkitMaterial.typeScale) {
					int blockId = typeId / TekkitMaterial.typeScale;
					byte dataVal = (byte) (typeId % TekkitMaterial.typeScale);
					block.setTypeIdAndData(blockId, dataVal, physics);
				} else {
					block.setTypeId(typeId, physics);
				}
				return true;
			}
		}
		return false;
	}
}
