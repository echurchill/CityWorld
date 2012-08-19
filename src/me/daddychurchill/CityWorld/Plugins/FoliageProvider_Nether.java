package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;

public class FoliageProvider_Nether extends FoliageProvider_Decayed {

	public FoliageProvider_Nether(Random random) {
		super(random);
	}
	
	private final static Material air = Material.AIR;
	private final static Material glow = Material.GLOWSTONE;
	private final static Material glass = Material.GLASS;
	private final static Material pane = Material.THIN_GLASS;
	private final static Material obsidian = Material.OBSIDIAN;
	private final static Material clay = Material.CLAY;
	private final static Material iron = Material.IRON_FENCE;
	
	@Override
	public boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, LigneousType ligneousType) {
		if (likelyFlora(generator, random)) {
			Material trunk = log;
			Material leaves1 = air;
			Material leaves2 = air;
			switch (ligneousType) {
			case OAK:
			case SHORT_OAK:
			case TALL_OAK:
				//leave trunk alone
				if (!generator.settings.includeDecayedNature && random.nextDouble() < 0.10) {
					leaves1 = iron;
					leaves2 = iron;
					if (random.nextDouble() < 0.10) {
						trunk = glow;
					}
				} 
				break;
			case BIRCH:
			case SHORT_BIRCH:
			case TALL_BIRCH:
				trunk = clay;
				if (!generator.settings.includeDecayedNature && random.nextDouble() < 0.20) {
					leaves1 = iron;
					leaves2 = iron;
					if (random.nextDouble() < 0.10) {
						trunk = glow;
					}
				} 
				break;
			case PINE:
			case SHORT_PINE:
			case TALL_PINE:
				trunk = obsidian;
				if (!generator.settings.includeDecayedNature && random.nextDouble() < 0.10) {
					leaves1 = pane;
					if (random.nextDouble() < 0.10)
						leaves2 = glow;
					else
						leaves2 = glass;
				}
				break;
			default:
				break;
			}
			return generateTree(chunk, random, x, y, z, ligneousType, trunk, leaves1, leaves2);
		} else
			return false;
	}

	@Override
	public boolean generateFlora(WorldGenerator generator, RealChunk chunk, int x, int y, int z, HerbaceousType herbaceousType) {
		if (likelyFlora(generator, random)) {
				
			// icky things in the nether
			switch (herbaceousType) {
			case FLOWER_RED:
				chunk.setBlock(x, y, z, Material.RED_MUSHROOM);
				break;
			case FLOWER_YELLOW:
				chunk.setBlock(x, y, z, Material.BROWN_MUSHROOM);
				break;
			case GRASS:
			case FERN:
				if (random.nextDouble() < 0.05) {
					chunk.setBlock(x, y - 1, z, Material.NETHERRACK);
					chunk.setBlock(x, y, z, Material.FIRE);
				} else {
					chunk.setBlock(x, y - 1, z, Material.SOUL_SAND);
					if (random.nextDouble() < 0.05)
						chunk.setBlock(x, y, z, Material.NETHER_WARTS, (byte) random.nextInt(4));
				}
				break;
			case COVER:
				y = chunk.findLastEmptyBelow(x, y + 1, z);
				if (chunk.isEmpty(x, y, z) && chunk.isType(x, y - 1, z, Material.NETHERRACK))
					chunk.setBlock(x, y, z, Material.FIRE);
				break;
			case CACTUS:
			default:
				break;
			}
			return true;
		}
		return false;
	}
}
