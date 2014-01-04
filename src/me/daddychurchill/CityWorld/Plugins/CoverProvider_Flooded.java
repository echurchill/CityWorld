package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class CoverProvider_Flooded extends CoverProvider_Normal {

	public CoverProvider_Flooded(Odds odds) {
		super(odds);
	}

	@Override
	public boolean isPlantable(WorldGenerator generator, RealChunk chunk, int x, int y, int z) {

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
	public boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, LigneousType ligneousType) {
		if (likelyCover(generator)) {
			if (y >= generator.shapeProvider.findFloodY(generator, x, z))
				return generateTree(chunk, x, y, z, ligneousType);
			else {
//				chunk.setBlock(x, y, z, Material.REDSTONE_BLOCK);
//				return true;
				return generateTrunk(chunk, x, y, z, ligneousType);
			}
		}
		return false;
	}

	@Override
	public boolean generateCoverage(WorldGenerator generator, RealChunk chunk, int x, int y, int z, CoverageType coverageType) {
		if (y > generator.shapeProvider.findFloodY(generator, x, z))
			return super.generateCoverage(generator, chunk, x, y, z, coverageType);
		return true;
	}
}
