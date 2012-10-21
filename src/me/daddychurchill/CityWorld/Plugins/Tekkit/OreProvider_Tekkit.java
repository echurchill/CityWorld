package me.daddychurchill.CityWorld.Plugins.Tekkit;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.OreProvider;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class OreProvider_Tekkit extends OreProvider {

	public OreProvider_Tekkit(WorldGenerator generator) {
		super(generator);
		
	}

	@Override
	public String getCollectionName() {
		return "Tekkit";
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
	
	private static final int[] ore_types = new int[] {Material.WATER.getId(),
													  Material.LAVA.getId(),
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
	
	//                                                   	   WATER   LAVA   GRAV   COAL   IRON   GOLD  LAPIS  REDST   DIAM   RUBY  TEMER   SAPP   SILV    TIN  COPPR   TUNG   NIKO   MARB   BASA   URAN  
	private static final int[] ore_iterations = new int[]    {     8,     4,    15,    15,     6,     2,     2,     4,     1,     1,     1,     1,     3,     6,     6,     2,     2,    20,    20,     1};
	private static final int[] ore_amountToDo = new int[]    {     1,     1,     6,     8,     6,     3,     2,     4,     2,     2,     2,     2,     4,     6,     6,     3,     8,    10,     8,     1};
	private static final int[] ore_maxY = new int[]          {   128,    32,    96,   128,    68,    34,    30,    17,    16,    16,    16,    16,    34,    68,    96,    34,    68,   128,    10,    16};
	private static final int[] ore_minY = new int[]          {    32,     2,    40,    16,    16,     5,     5,     8,     1,     2,     2,     2,     5,    16,    16,     2,     2,    40,     1,     2};
	private static final boolean[] ore_upper = new boolean[] {  true, false,  true,  true,  true,  true,  true, false, false, false, false,  true,  true,  true,  true,  true,  true, false, false, false};
	private static final boolean[] ore_physics = new boolean[] {true,  true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	private static final boolean[] ore_liquid = new boolean[] { true,  true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};

	@Override
	public void sprinkleOres(WorldGenerator generator, PlatLot lot, RealChunk chunk, CachedYs blockYs, Odds odds, OreLocation location) {
		for (int typeNdx = 0; typeNdx < ore_types.length; typeNdx++) {
			sprinkleOre(generator, lot, chunk, blockYs,
					odds, ore_types[typeNdx], ore_maxY[typeNdx], 
					ore_minY[typeNdx], ore_iterations[typeNdx], 
					ore_amountToDo[typeNdx], ore_upper[typeNdx], ore_physics[typeNdx], ore_liquid[typeNdx]);
		}
	}

	@Override
	protected boolean placeBlock(RealChunk chunk, me.daddychurchill.CityWorld.Support.Odds odds, int x, int y, int z, int typeId, boolean physics) {
		if (odds.playOdds(oreSprinkleOdds)) {
			Block block = chunk.getActualBlock(x, y, z);
			if (block.getTypeId() == stratumId) {
				
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
