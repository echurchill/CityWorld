package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class FoliageProvider_Flooded extends FoliageProvider_Normal {

	public FoliageProvider_Flooded(Odds odds) {
		super(odds);
	}

	@Override
	public boolean isPlantable(WorldGenerator generator, RealChunk chunk, int x, int y, int z) {

		// only if the spot above is empty
		if (y < generator.shapeProvider.findCoverY(generator, x, z)) {
			if (!chunk.isWater(x, y + 1, z))
				return false;
		} else {
			if (!chunk.isEmpty(x, y + 1, z))
				return false;
		}
		
		// depends on the block's type and what the world is like
		if (!generator.settings.includeAbovegroundFluids && y <= generator.seaLevel)
			return chunk.getBlock(x, y, z) == Material.SAND;
		else
			return chunk.isPlantable(x, y, z);
	}
	
	@Override
	public boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, LigneousType ligneousType) {
		if (likelyFlora(generator, odds)) {
			if (y >= generator.shapeProvider.findCoverY(generator, x, z))
				return generateTree(chunk, odds, x, y, z, ligneousType, log, leaves, leaves);
			else {
//				chunk.setBlock(x, y, z, Material.REDSTONE_BLOCK);
//				return true;
				return generateTrunk(chunk, odds, x, y, z, ligneousType);
			}
		}
		return false;
	}

	@Override
	public boolean generateFlora(WorldGenerator generator, RealChunk chunk, int x, int y, int z, HerbaceousType herbaceousType) {
		if (y > generator.shapeProvider.findCoverY(generator, x, z))
			return super.generateFlora(generator, chunk, x, y, z, herbaceousType);
		return true;
	}
}
