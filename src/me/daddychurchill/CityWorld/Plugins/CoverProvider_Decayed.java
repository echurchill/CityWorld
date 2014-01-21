package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class CoverProvider_Decayed extends CoverProvider {
	
	private double oddsOfCrop = DataContext.oddsLikely;
	
	public CoverProvider_Decayed(Odds odds) {
		super(odds);
	}
	
	@Override
	public boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, LigneousType ligneousType) {
		if (likelyCover(generator)) {
			return generateTrunk(chunk, x, y, z, ligneousType);
		} else
			return false;
	}

	@Override
	public boolean generateCoverage(WorldGenerator generator, RealChunk chunk, int x, int y, int z, CoverageType coverageType) {
		if (likelyCover(generator))
			setCoverage(chunk, x, y, z, coverageType);
		return true;
	}

	@Override
	public void setCoverage(SupportChunk chunk, int x, int y, int z, CoverageType coverageType) {
		switch (coverageType) {
		case GRASS:
		case DANDELION:
		case POPPY:
		case BLUE_ORCHID:
		case ALLIUM:
		case AZURE_BLUET:
		case OXEYE_DAISY:
		case RED_TULIP:
		case ORANGE_TULIP:
		case WHITE_TULIP:
		case PINK_TULIP:
		case SUNFLOWER:
		case LILAC:
		case TALL_GRASS:
		case TALL_FERN:
		case ROSE_BUSH:
		case PEONY:
		case EMERALD_GREEN:
		case WHEAT:
		case CARROTS:
		case POTATO:
		case MELON:
		case PUMPKIN:
		case FERN:
		case DEAD_GRASS:
		case CACTUS:
		case REED:
		case OAK_SAPLING:
		case BIRCH_SAPLING:
		case SPRUCE_SAPLING:
		case JUNGLE_SAPLING:
		case ACACIA_SAPLING:
		case DARK_OAK_SAPLING:
		case ACACIA_TREE:
		case BIRCH_TREE:
		case JUNGLE_TREE:
		case OAK_TREE:
		case PINE_TREE:
		case SHORT_BIRCH_TREE:
		case SHORT_JUNGLE_TREE:
		case SHORT_OAK_TREE:
		case SHORT_PINE_TREE:
		case SWAMP_TREE:
		case TALL_BIRCH_TREE:
		case TALL_JUNGLE_TREE:
		case TALL_OAK_TREE:
		case TALL_PINE_TREE:
		case DEAD_BUSH:
			if (odds.playOdds(oddsOfCrop))
				super.setCoverage(chunk, x, y, z, CoverageType.DEAD_BUSH);
			break;
		case BROWN_MUSHROOM:
		case RED_MUSHROOM:
		case NETHERWART:
		case FIRE:
			super.setCoverage(chunk, x, y, z, coverageType);
			break;
		}
	}
	
	@Override
	public boolean isPlantable(WorldGenerator generator, RealChunk chunk, int x, int y, int z) {
		
		// only if the spot above is empty
		if (!chunk.isEmpty(x, y + 1, z))
			return false;
		
		// depends on the block's type and what the world is like
		return !chunk.isEmpty(x, y, z);
	}
}
