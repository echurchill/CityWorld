package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class CoverProvider_Normal extends CoverProvider {

	public CoverProvider_Normal(Odds odds) {
		super(odds);
	}
	
	@Override
	public boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, LigneousType ligneousType) {
		if (likelyCover(generator)) {
			return generateTree(chunk, x, y, z, ligneousType);
		} else
			return false;
	}

	@Override
	public boolean generateCoverage(WorldGenerator generator, RealChunk chunk, int x, int y, int z, CoverageType coverageType) {
		if (likelyCover(generator))
			setCoverage(chunk, x, y, z, coverageType);
		return true;
	}

}
