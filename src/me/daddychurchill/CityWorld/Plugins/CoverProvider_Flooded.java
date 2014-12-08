package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class CoverProvider_Flooded extends CoverProvider_Normal {

	public CoverProvider_Flooded(Odds odds) {
		super(odds);
	}

	@Override
	public boolean isPlantable(CityWorldGenerator generator, SupportChunk chunk, int x, int y, int z) {

		// only if the spot above is empty
		if (y < generator.shapeProvider.findFloodY(generator, x, z)) {
			if (!chunk.isWater(x, y + 1, z))
				return false;
		} else {
			if (!chunk.isEmpty(x, y + 1, z))
				return false;
		}
		
		// depends on the block's type and what the world is like
		if (!generator.settings.includeAbovegroundFluids && y <= generator.seaLevel)
			return chunk.isType(x, y, z, Material.SAND);
		else
			return chunk.isPlantable(x, y, z);
	}
	
	@Override
	public boolean generateCoverage(CityWorldGenerator generator, SupportChunk chunk, int x, int y, int z, CoverageType coverageType) {
		if (likelyCover(generator)) {
			int floodY = generator.shapeProvider.findFloodY(generator, x, z);
			
			// trees are special
			if (isATree(coverageType)) {
				
				// too far underwater... trunk only
				if (y < floodY)
					return super.generateCoverage(generator, chunk, x, y, z, convertToTrunk(coverageType));
				
				// otherwise just a normal tree
				else if (y >= floodY)
					return super.generateCoverage(generator, chunk, x, y, z, coverageType);
			
			// plants must go above the waterline
			} else if (y > floodY)
				return super.generateCoverage(generator, chunk, x, y, z, coverageType);
		}
		return true;
	}
}
