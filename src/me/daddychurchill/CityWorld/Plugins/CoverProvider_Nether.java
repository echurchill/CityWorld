package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class CoverProvider_Nether extends CoverProvider_Decayed {

	private double oddsOfCrop = Odds.oddsLikely;
	private double oddsOfFire = Odds.oddsSomewhatUnlikely;
	
	public CoverProvider_Nether(Odds odds) {
		super(odds);
	}
	
//NERFED for 1.7.2
//	private final static Material air = Material.AIR;
//	private final static Material glow = Material.GLOWSTONE;
//	private final static Material glass = Material.GLASS;
//	private final static Material pane = Material.THIN_GLASS;
//	private final static Material obsidian = Material.OBSIDIAN;
//	private final static Material clay = Material.CLAY;
//	private final static Material iron = Material.IRON_FENCE;
//	
//	@Override
//	public boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, LigneousType ligneousType) {
//		if (likelyCover(generator)) {
//			Material trunk = log;
//			Material leaves1 = air;
//			Material leaves2 = air;
//			switch (ligneousType) {
//			case OAK:
//			case SHORT_OAK:
//			case TALL_OAK:
//				//leave trunk alone
//				if (!generator.settings.includeDecayedNature && odds.playOdds(0.10)) {
//					leaves1 = iron;
//					leaves2 = iron;
//					if (odds.playOdds(0.10)) {
//						trunk = glow;
//					}
//				} 
//				break;
//			case BIRCH:
//			case SHORT_BIRCH:
//			case TALL_BIRCH:
//				trunk = clay;
//				if (!generator.settings.includeDecayedNature && odds.playOdds(0.20)) {
//					leaves1 = iron;
//					leaves2 = iron;
//					if (odds.playOdds(0.10)) {
//						trunk = glow;
//					}
//				} 
//				break;
//			case PINE:
//			case SHORT_PINE:
//			case TALL_PINE:
//				trunk = obsidian;
//				if (!generator.settings.includeDecayedNature && odds.playOdds(0.10)) {
//					leaves1 = pane;
//					if (odds.playOdds(0.10))
//						leaves2 = glow;
//					else
//						leaves2 = glass;
//				}
//				break;
//			default:
//				break;
//			}
//			return generateTree(chunk, x, y, z, ligneousType, trunk, leaves1, leaves2);
//		} else
//			return false;
//	}

	@Override
	protected void setCoverage(CityWorldGenerator generator, SupportChunk chunk, int x, int y, int z, CoverageType coverageType) {
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
		case PINE_SAPLING:
		case BIRCH_SAPLING:
		case JUNGLE_SAPLING:
		case ACACIA_SAPLING:
		case OAK_TREE:
		case PINE_TREE:
		case BIRCH_TREE:
		case JUNGLE_TREE:
		case SWAMP_TREE:
		case ACACIA_TREE:
		case SHORT_OAK_TREE:
		case SHORT_PINE_TREE:
		case SHORT_BIRCH_TREE:
		case SHORT_JUNGLE_TREE:
		case TALL_OAK_TREE:
		case TALL_PINE_TREE:
		case TALL_BIRCH_TREE:
		case TALL_JUNGLE_TREE:
		case MINI_ACACIA_TREE:
		case MINI_BIRCH_TREE:
		case MINI_JUNGLE_TREE:
		case MINI_OAK_TREE:
		case MINI_PINE_TREE:
		case MINI_SWAMP_TREE:
			if (odds.playOdds(oddsOfFire))
				super.setCoverage(generator, chunk, x, y, z, CoverageType.FIRE);
			else if (odds.playOdds(oddsOfCrop))
				super.setCoverage(generator, chunk, x, y, z, CoverageType.NETHERWART);
			break;
			
		case OAK_TRUNK:
		case PINE_TRUNK:
		case BIRCH_TRUNK:
		case JUNGLE_TRUNK:
		case ACACIA_TRUNK:
		case MINI_ACACIA_TRUNK:
		case MINI_BIRCH_TRUNK:
		case MINI_JUNGLE_TRUNK:
		case MINI_OAK_TRUNK:
		case MINI_PINE_TRUNK:
		case MINI_SWAMP_TRUNK:
		case SWAMP_TRUNK:
		case DEAD_BUSH:
		case BROWN_MUSHROOM:
		case RED_MUSHROOM:
		case NETHERWART:
		case FIRE:
			super.setCoverage(generator, chunk, x, y, z, coverageType);
			break;
		}
	}
}
