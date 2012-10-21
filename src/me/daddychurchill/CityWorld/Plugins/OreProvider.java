package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.Tekkit.OreProvider_Tekkit;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SupportChunk;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;

public abstract class OreProvider extends Provider {

	public final static byte stoneId = (byte) Material.STONE.getId();
	public final static byte dirtId = (byte) Material.DIRT.getId();
	public final static byte grassId = (byte) Material.GRASS.getId();
	public final static byte sandId = (byte) Material.SAND.getId();
	public final static byte sandstoneId = (byte) Material.SANDSTONE.getId();
	public final static byte stillWaterId = (byte) Material.STATIONARY_WATER.getId();
	public final static byte stillLavaId = (byte) Material.STATIONARY_LAVA.getId();
	public final static byte snowId = (byte) Material.SNOW.getId();
	public final static byte snowBlockId = (byte) Material.SNOW_BLOCK.getId();
	public final static byte bedrockId = (byte) Material.BEDROCK.getId();
	
	public final static int lavaFluidLevel = 24;
	public final static int lavaFieldLevel = 12;
	protected final static double oreSprinkleOdds = 0.40;
	protected final static double snowSplinkleOdds = 0.60;
	
	public byte surfaceId;
	public byte subsurfaceId;
	public byte stratumId;
	public byte substratumId;
	
	public byte fluidId;
	public byte fluidSurfaceId;
	public byte fluidSubsurfaceId;
	public byte fluidFrozenId;
	
	public OreProvider(WorldGenerator generator) {
		super();
		
		surfaceId = grassId;
		subsurfaceId = dirtId;
		stratumId = stoneId;
		substratumId = bedrockId;
		
		fluidId = stillWaterId;
		fluidSurfaceId = sandId;
		fluidSubsurfaceId = sandstoneId;
		fluidFrozenId = snowBlockId;
	}
	
	/**
	 * Populates the world with ores.
	 *
	 * @author Nightgunner5
	 * @author Markus Persson
	 * modified by simplex
	 * wildly modified by daddychurchill
	 */
	
	protected static final int iceTypeId = Material.ICE.getId();
	protected static final int waterTypeId = Material.WATER.getId();
	protected static final int lavaTypeId = Material.LAVA.getId();
	protected static final int snowTypeId = Material.SNOW_BLOCK.getId();
	
	public enum OreLocation {CRUST};
	
	public abstract void sprinkleOres(WorldGenerator generator, PlatLot lot, RealChunk chunk, CachedYs blockYs, Odds odds, OreLocation location);
	public abstract String getCollectionName();

	protected void sprinkleOre(WorldGenerator generator, PlatLot lot, RealChunk chunk, CachedYs blockYs,
			Odds odds, int typeId, int maxY, int minY, int iterations, int amount, boolean mirror, boolean physics, boolean liquid) {
		
		// do we do this one?
		if ((liquid && generator.settings.includeUndergroundFluids) ||
			(!liquid && generator.settings.includeOres)) {
			
			// sprinkle it around!
			int range = maxY - minY;
			for (int iter = 0; iter < iterations; iter++) {
				int x = odds.getRandomInt(16);
				int y = odds.getRandomInt(range) + minY;
				int z = odds.getRandomInt(16);
				if (y < blockYs.getBlockY(x, z))
					growVein(generator, lot, chunk, blockYs, odds, x, y, z, amount, typeId, physics);
				if (mirror) {
					y = (generator.seaLevel + generator.landRange) - minY - odds.getRandomInt(range);
					if (y < blockYs.getBlockY(x, z))
						growVein(generator, lot, chunk, blockYs, odds, x, y, z, amount, typeId, physics);
				}
			}
		}
	}
	
	private void growVein(WorldGenerator generator, PlatLot lot, RealChunk chunk, CachedYs blockYs, 
			Odds odds, int originX, int originY, int originZ, int amountToDo, int typeId, boolean physics) {
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
				oresDone += placeOre(generator, chunk, odds, x, y, z, amountToDo - oresDone, typeId, physics);
				
				// one less try to try
				trysLeft--;
			}
		}
	}
	
	private int placeOre(WorldGenerator generator, RealChunk chunk, Odds odds, 
			int centerX, int centerY, int centerZ, int oresToDo, int typeId, boolean physics) {
		int count = 0;
		if (centerY > 0 && centerY < chunk.height) {
			if (placeBlock(chunk, odds, centerX, centerY, centerZ, typeId, physics)) {
				count++;
				if (count < oresToDo && centerX < 15 && placeBlock(chunk, odds, centerX + 1, centerY, centerZ, typeId, physics))
					count++;
				if (count < oresToDo && centerX > 0 && placeBlock(chunk, odds, centerX - 1, centerY, centerZ, typeId, physics))
					count++;
				if (count < oresToDo && centerZ < 15 && placeBlock(chunk, odds, centerX, centerY, centerZ + 1, typeId, physics))
					count++;
				if (count < oresToDo && centerZ > 0 && placeBlock(chunk, odds, centerX, centerY, centerZ - 1, typeId, physics))
					count++;
			}
		}
		return count;
	}
	
	protected boolean placeBlock(RealChunk chunk, Odds odds, int x, int y, int z, int typeId, boolean physics) {
		if (odds.playOdds(oreSprinkleOdds)) {
			Block block = chunk.getActualBlock(x, y, z);
			if (block.getTypeId() == stratumId) {
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

			if (generator.settings.includeTekkitMaterials) {
				generator.reportMessage("[OreProvider] Found ForgeTekkit, enabling its ores");
				
				//TODO provide nether, theend and decayed variants of Tekkit
				provider = new OreProvider_Tekkit(generator);
			} else {
				
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

	public void sprinkleSnow(WorldGenerator generator, SupportChunk chunk, Odds odds, int x1, int x2, int y, int z1, int z2) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				if (odds.playOdds(snowSplinkleOdds))
					chunk.setBlock(x, y, z, snowId);
			}
		}
	}
	
	public void dropSnow(WorldGenerator generator, RealChunk chunk, int x, int y, int z) {
		dropSnow(generator, chunk, x, y, z, (byte) 0);
	}
	
	public void dropSnow(WorldGenerator generator, RealChunk chunk, int x, int y, int z, byte level) {
		y = chunk.findLastEmptyBelow(x, y + 1, z);
		if (chunk.isEmpty(x, y, z))
			chunk.setBlock(x, y, z, snowId, level, false);
	}
}
