package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class FoliageProvider_Normal extends FoliageProvider {

	public FoliageProvider_Normal(Odds odds) {
		super(odds);
	}
	
	@Override
	public boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, LigneousType ligneousType) {
		if (likelyFlora(generator, odds)) {
			return generateTree(chunk, odds, x, y, z, ligneousType, log, leaves, leaves);
		} else
			return false;
	}

	@Override
	public boolean generateFlora(WorldGenerator generator, RealChunk chunk, int x, int y, int z, HerbaceousType herbaceousType) {
		if (likelyFlora(generator, odds)) {
			switch (herbaceousType) {
			case FLOWER_RED:
				chunk.setBlock(x, y, z, Material.RED_ROSE);
				break;
			case FLOWER_YELLOW:
				chunk.setBlock(x, y, z, Material.YELLOW_FLOWER);
				break;
			case GRASS:
				chunk.setBlock(x, y, z, Material.LONG_GRASS, (byte) 1);
				break;
			case FERN:
				chunk.setBlock(x, y, z, Material.LONG_GRASS, (byte) 2);
				break;
			case CACTUS:
				chunk.setBlock(x, y - 1, z, Material.SAND);
				chunk.setBlocks(x, y, y + odds.getRandomInt(3) + 2, z, Material.CACTUS);
				break;
			case REED:
				chunk.setBlocks(x, y, y + odds.getRandomInt(2) + 2, z, Material.SUGAR_CANE_BLOCK);
				break;
			case COVER:
				generator.oreProvider.dropSnow(generator, chunk, x, y + 5, z);
				break;
			}
		}
		return true;
	}
}
