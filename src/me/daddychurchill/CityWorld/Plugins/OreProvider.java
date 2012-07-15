package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;

public abstract class OreProvider {

	/**
	 * Populates the world with ores.
	 *
	 * @author Nightgunner5
	 * @author Markus Persson
	 * modified by simplex
	 * wildly modified by daddychurchill
	 */
	
	public final static String oresInCrust = "CityWorld_Ore_Crust";
	
	protected static final int stoneId = Material.STONE.getId();
	protected static final int iceTypeId = Material.ICE.getId();
	protected static final int waterTypeId = Material.WATER.getId();
	protected static final int lavaTypeId = Material.LAVA.getId();
	
	public abstract void sprinkleOres(WorldGenerator generator, RealChunk chunk, Random random, String name);

	protected void sprinkleOres_iterate(WorldGenerator generator, RealChunk chunk, Random random, int originX, int originY, int originZ, int amountToDo, int typeId) {
		int trysLeft = amountToDo * 2;
		int oresDone = 0;
		if (generator.findBlockY(chunk.getBlockX(originX), chunk.getBlockZ(originZ)) > originY + amountToDo / 4) {
			while (oresDone < amountToDo && trysLeft > 0) {
				
				// ore or not?
				if (typeId == waterTypeId) {
					if (generator.settings.includeUndergroundFluids)
						oresDone += sprinkleOres_placeFluid(generator, chunk, random, originX, originY, originZ);

				} else {
					
					// shimmy
					int x = originX + random.nextInt(Math.max(1, amountToDo / 2)) - amountToDo / 4;
					int y = originY + random.nextInt(Math.max(1, amountToDo / 4)) - amountToDo / 8;
					int z = originZ + random.nextInt(Math.max(1, amountToDo / 2)) - amountToDo / 4;
					
					// ore it is
					oresDone += sprinkleOres_placeOre(generator, chunk, random, x, y, z, amountToDo - oresDone, typeId);
				}
				
				// one less try to try
				trysLeft--;
			}
		}
	}
	
	protected int sprinkleOres_placeOre(WorldGenerator generator, RealChunk chunk, Random random, int centerX, int centerY, int centerZ, int oresToDo, int typeId) {
		int count = 0;
		if (centerY > 0 && centerY < chunk.height) {
			if (sprinkleOres_placeThing(chunk, random, centerX, centerY, centerZ, typeId, false)) {
				count++;
				if (count < oresToDo && centerX < 15 && sprinkleOres_placeThing(chunk, random, centerX + 1, centerY, centerZ, typeId, false))
					count++;
				if (count < oresToDo && centerX > 0 && sprinkleOres_placeThing(chunk, random, centerX - 1, centerY, centerZ, typeId, false))
					count++;
				if (count < oresToDo && centerZ < 15 && sprinkleOres_placeThing(chunk, random, centerX, centerY, centerZ + 1, typeId, false))
					count++;
				if (count < oresToDo && centerZ > 0 && sprinkleOres_placeThing(chunk, random, centerX, centerY, centerZ - 1, typeId, false))
					count++;
			}
		}
		return count;
	}
	
	protected int sprinkleOres_placeFluid(WorldGenerator generator, RealChunk chunk, Random random, int centerX, int centerY, int centerZ) {
		int count = 0;
		if (centerY > 0 && centerY < chunk.height) {

			// what type of fluid are we talking about?
			int fluidId;
			if (centerY < 24 || generator.settings.environment == Environment.NETHER)
				fluidId = lavaTypeId;
			else if (centerY > generator.snowLevel)
				fluidId = iceTypeId;
			else
				fluidId = waterTypeId;
			
			// odds are?
			if (sprinkleOres_placeThing(chunk, random, centerX, centerY, centerZ, fluidId, true))
				count++;
		}
		return count;
	}
	
	protected boolean sprinkleOres_placeThing(RealChunk chunk, Random random, int x, int y, int z, int typeId, boolean physics) {
		if (random.nextDouble() < 0.35) {
			Block block = chunk.getActualBlock(x, y, z);
			if (block.getTypeId() == stoneId) {
				block.setTypeId(typeId, physics);
				return true;
			}
		}
		return false;
	}

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	public static OreProvider loadProvider(WorldGenerator generator) {

		OreProvider provider = null;
		
//		// try need something like PhatLoot but for ores
//		provider = OreProvider_PhatOre.loadPhatOres();
		
		// default to stock OreProvider
		if (provider == null) {
			
			if (generator.settings.environment == Environment.NETHER)
				provider = new OreProvider_Nether();
			else if (generator.settings.includeTekkitMaterials)
				provider = new OreProvider_Tekkit();
			else
				provider = new OreProvider_Default();
		}
	
		return provider;
	}
}
