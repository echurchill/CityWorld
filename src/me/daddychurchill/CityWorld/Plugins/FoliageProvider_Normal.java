package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import org.bukkit.Material;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class FoliageProvider_Normal extends FoliageProvider {

	public FoliageProvider_Normal(Random random) {
		super(random);
	}
	
	@Override
	public boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, LigneousType ligneousType) {
		if (likelyFlora(generator, random)) {
			return generateTree(chunk, random, x, y, z, ligneousType, log, leaves, leaves);
		} else
			return false;
	}

	@Override
	public boolean generateFlora(WorldGenerator generator, RealChunk chunk, int x, int y, int z, HerbaceousType herbaceousType) {
		if (likelyFlora(generator, random)) {
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
				chunk.setBlocks(x, y, y + random.nextInt(3) + 2, z, Material.CACTUS);
				break;
			case COVER:
				generator.oreProvider.dropSnow(generator, chunk, x, y + 5, z);
				break;
			}
		}
		return true;
	}
}
