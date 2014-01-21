package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class CoverProvider_TheEnd extends CoverProvider_Normal {

	public CoverProvider_TheEnd(Odds odds) {
		super(odds);
	}
	
	@Override
	public boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, LigneousType ligneousType) {
		if (likelyCover(generator)) {
			switch (ligneousType) {
//NERFED FOR 1.7.2
//			case TALL_BIRCH:
//			case TALL_OAK:
//			case TALL_PINE:
//				return generateTree(chunk, x, y, z, ligneousType, log, Material.THIN_GLASS, Material.GLASS);
			default:
				return generateTree(chunk, x, y, z, ligneousType);//, log, leaves, leaves);
			}
		} else
			return false;
	}
}
