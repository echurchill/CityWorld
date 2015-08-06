package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

public class CoverProvider_Normal extends CoverProvider {

	public CoverProvider_Normal(Odds odds) {
		super(odds);
	}
	
	@Override
	public boolean generateCoverage(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z, CoverageType coverageType) {
		if (likelyCover(generator))
			setCoverage(generator, chunk, x, y, z, coverageType);
		return true;
	}

}
