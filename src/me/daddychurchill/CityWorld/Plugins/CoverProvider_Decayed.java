package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SupportBlocks;
import me.daddychurchill.CityWorld.Support.Odds.ColorSet;

public class CoverProvider_Decayed extends CoverProvider {
	
	private double oddsOfCrop = Odds.oddsLikely;
	
	public CoverProvider_Decayed(Odds odds) {
		super(odds);
	}
	
	@Override
	public ColorSet getDefaultColorSet() {
		return ColorSet.TAN;
	}
	
	@Override
	public boolean generateCoverage(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z, CoverageType coverageType) {
		if (likelyCover(generator))
			setCoverage(generator, chunk, x, y, z, coverageType);
		return true;
	}

	@Override
	protected void setCoverage(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z, CoverageType coverageType) {
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
		case REED:
		case OAK_SAPLING:
		case PINE_SAPLING:
		case BIRCH_SAPLING:
		case JUNGLE_SAPLING:
		case ACACIA_SAPLING:
		case DEAD_BUSH:
			if (odds.playOdds(oddsOfCrop))
				super.setCoverage(generator, chunk, x, y, z, CoverageType.DEAD_BUSH);
			break;
			
		case MINI_OAK_TREE:
		case MINI_OAK_TRUNK:
			super.setCoverage(generator, chunk, x, y, z, CoverageType.MINI_OAK_TRUNK);
			break;
		case MINI_PINE_TREE:
		case MINI_PINE_TRUNK:
			super.setCoverage(generator, chunk, x, y, z, CoverageType.MINI_PINE_TRUNK);
			break;
		case MINI_BIRCH_TREE:
		case MINI_BIRCH_TRUNK:
			super.setCoverage(generator, chunk, x, y, z, CoverageType.MINI_BIRCH_TRUNK);
			break;
		case MINI_JUNGLE_TREE:
		case MINI_JUNGLE_TRUNK:
			super.setCoverage(generator, chunk, x, y, z, CoverageType.MINI_JUNGLE_TRUNK);
			break;
		case MINI_ACACIA_TRUNK:
		case MINI_ACACIA_TREE:
			super.setCoverage(generator, chunk, x, y, z, CoverageType.MINI_JUNGLE_TRUNK);
			break;
		case MINI_SWAMP_TREE:
		case MINI_SWAMP_TRUNK:
			super.setCoverage(generator, chunk, x, y, z, CoverageType.MINI_SWAMP_TRUNK);
			break;
			
		case OAK_TRUNK:
		case OAK_TREE:
		case SHORT_OAK_TREE:
		case TALL_OAK_TREE:
			super.setCoverage(generator, chunk, x, y, z, CoverageType.OAK_TRUNK);
			break;

		case PINE_TRUNK:
		case PINE_TREE:
		case SHORT_PINE_TREE:
		case TALL_PINE_TREE:
			super.setCoverage(generator, chunk, x, y, z, CoverageType.PINE_TRUNK);
			break;

		case BIRCH_TRUNK:
		case BIRCH_TREE:
		case SHORT_BIRCH_TREE:
		case TALL_BIRCH_TREE:
			super.setCoverage(generator, chunk, x, y, z, CoverageType.BIRCH_TRUNK);
			break;
			
		case JUNGLE_TREE:
		case JUNGLE_TRUNK:
		case SHORT_JUNGLE_TREE:
		case TALL_JUNGLE_TREE:
			super.setCoverage(generator, chunk, x, y, z, CoverageType.JUNGLE_TRUNK);
			break;
		
		case ACACIA_TRUNK:
		case ACACIA_TREE:
			super.setCoverage(generator, chunk, x, y, z, CoverageType.ACACIA_TRUNK);
			break;
			
		case SWAMP_TREE:
		case SWAMP_TRUNK:
			super.setCoverage(generator, chunk, x, y, z, CoverageType.SWAMP_TRUNK);
			break;
			
		case BROWN_MUSHROOM:
		case RED_MUSHROOM:
		case NETHERWART:
		case FIRE:
		case CACTUS:
		case DEAD_GRASS:
			super.setCoverage(generator, chunk, x, y, z, coverageType);
			break;
		default:
			break;
		}
	}
	
	@Override
	public boolean isPlantable(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z) {
		
		// only if the spot above is empty
		if (!chunk.isEmpty(x, y + 1, z))
			return false;
		
		// depends on the block's type and what the world is like
		return !chunk.isEmpty(x, y, z);
	}
}
