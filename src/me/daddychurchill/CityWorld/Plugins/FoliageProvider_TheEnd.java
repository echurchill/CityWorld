package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;

public class FoliageProvider_TheEnd extends FoliageProvider_Normal {

	public FoliageProvider_TheEnd(Random random) {
		super(random);
	}
	
	@Override
	public boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, LigneousType ligneousType) {
		if (likelyFlora(generator, random)) {
			switch (ligneousType) {
			case TALL_BIRCH:
			case TALL_OAK:
			case TALL_PINE:
				return generateTree(chunk, random, x, y, z, ligneousType, log, Material.THIN_GLASS, Material.GLASS);
			default:
				return generateTree(chunk, random, x, y, z, ligneousType, log, leaves, leaves);
			}
		} else
			return false;
	}
}
