package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.Odds.ColorSet;
import me.daddychurchill.CityWorld.Support.BlackMagic;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

import org.bukkit.CropState;
import org.bukkit.GrassSpecies;
import org.bukkit.Material;
import org.bukkit.NetherWartsState;
import org.bukkit.TreeSpecies;
import org.bukkit.TreeType;
import org.bukkit.material.Crops;
import org.bukkit.material.Leaves;
import org.bukkit.material.LongGrass;
import org.bukkit.material.NetherWarts;
import org.bukkit.material.Sapling;
import org.bukkit.material.Tree;

public abstract class CoverProvider extends Provider {
	
	public enum CoverageType {
		NOTHING, GRASS, FERN, DEAD_GRASS, DANDELION, DEAD_BUSH, 
		
		POPPY, BLUE_ORCHID, ALLIUM, AZURE_BLUET, OXEYE_DAISY,
		RED_TULIP, ORANGE_TULIP, WHITE_TULIP, PINK_TULIP,

		SUNFLOWER, LILAC, TALL_GRASS, TALL_FERN, ROSE_BUSH, PEONY,
		
		CACTUS, REED, EMERALD_GREEN, 
		
		OAK_SAPLING, PINE_SAPLING, BIRCH_SAPLING, 
		JUNGLE_SAPLING, ACACIA_SAPLING,
		
		MINI_OAK_TREE, SHORT_OAK_TREE, OAK_TREE, TALL_OAK_TREE, 
		MINI_PINE_TREE, SHORT_PINE_TREE, PINE_TREE, TALL_PINE_TREE, 
		MINI_BIRCH_TREE, SHORT_BIRCH_TREE, BIRCH_TREE, TALL_BIRCH_TREE, 
		MINI_JUNGLE_TREE, SHORT_JUNGLE_TREE, JUNGLE_TREE, TALL_JUNGLE_TREE,
		MINI_SWAMP_TREE, SWAMP_TREE,
		MINI_ACACIA_TREE, ACACIA_TREE,
		  
		MINI_OAK_TRUNK, OAK_TRUNK, TALL_OAK_TRUNK, 
		MINI_PINE_TRUNK, PINE_TRUNK, TALL_PINE_TRUNK, 
		MINI_BIRCH_TRUNK, BIRCH_TRUNK, TALL_BIRCH_TRUNK, 
		MINI_JUNGLE_TRUNK, JUNGLE_TRUNK, TALL_JUNGLE_TRUNK,
		MINI_SWAMP_TRUNK, SWAMP_TRUNK, TALL_SWAMP_TRUNK,
		MINI_ACACIA_TRUNK, ACACIA_TRUNK, TALL_ACACIA_TRUNK,
		
		WHEAT, CARROTS, POTATO, MELON, PUMPKIN, BEETROOT,

//		TALL_BROWN_MUSHROOM, TALL_RED_MUSHROOM,
		BROWN_MUSHROOM, RED_MUSHROOM, NETHERWART,
		FIRE};
	
	public enum CoverageSets {SHORT_FLOWERS, TALL_FLOWERS, ALL_FLOWERS,
		SHORT_PLANTS, TALL_PLANTS, ALL_PLANTS,
		GENERAL_SAPLINGS, ALL_SAPLINGS, 
		OAK_TREES, PINE_TREES, BIRCH_TREES, 
		JUNGLE_TREES, ACACIA_TREES, SWAMP_TREES, 
		SHORT_TREES, MEDIUM_TREES, TALL_TREES, ALL_TREES,
		PRARIE_PLANTS, EDIBLE_PLANTS, SHORT_MUSHROOMS, 
		NETHER_PLANTS, DECAY_PLANTS};
								
	private final static CoverageType[] ShortFlowers = {
		CoverageType.DANDELION, CoverageType.POPPY, 
		CoverageType.BLUE_ORCHID, CoverageType.ALLIUM, 
		CoverageType.AZURE_BLUET, CoverageType.OXEYE_DAISY,
		CoverageType.RED_TULIP, CoverageType.ORANGE_TULIP, 
		CoverageType.WHITE_TULIP, CoverageType.PINK_TULIP};
	
	private final static CoverageType[] TallFlowers = {
		CoverageType.SUNFLOWER, CoverageType.LILAC,
		CoverageType.ROSE_BUSH, CoverageType.PEONY};
	
	private final static CoverageType[] AllFlowers = {
		CoverageType.DANDELION, CoverageType.POPPY, 
		CoverageType.BLUE_ORCHID, CoverageType.ALLIUM, 
		CoverageType.AZURE_BLUET, CoverageType.OXEYE_DAISY,
		CoverageType.RED_TULIP, CoverageType.ORANGE_TULIP, 
		CoverageType.WHITE_TULIP, CoverageType.PINK_TULIP,
		CoverageType.SUNFLOWER, CoverageType.LILAC,
		CoverageType.ROSE_BUSH, CoverageType.PEONY};
	
	private final static CoverageType[] ShortPlants = {
		CoverageType.GRASS, CoverageType.FERN};
	
	private final static CoverageType[] TallPlants = {
		CoverageType.CACTUS, CoverageType.REED,
		CoverageType.TALL_GRASS, CoverageType.TALL_FERN,
		CoverageType.SUNFLOWER, CoverageType.LILAC,
		CoverageType.EMERALD_GREEN};
	
	private final static CoverageType[] AllPlants = {
		CoverageType.GRASS, CoverageType.FERN,
		CoverageType.CACTUS, CoverageType.REED,
		CoverageType.TALL_GRASS, CoverageType.TALL_FERN,
		CoverageType.ROSE_BUSH, CoverageType.PEONY,
		CoverageType.EMERALD_GREEN};

	private final static CoverageType[] PrariePlants = {
		CoverageType.GRASS, CoverageType.GRASS,
		CoverageType.GRASS, CoverageType.GRASS,
		CoverageType.DANDELION, CoverageType.POPPY, 
		CoverageType.GRASS, CoverageType.GRASS,
//		CoverageType.BLUE_ORCHID, CoverageType.ALLIUM, 
		CoverageType.GRASS, CoverageType.GRASS,
//		CoverageType.AZURE_BLUET, CoverageType.OXEYE_DAISY,
		CoverageType.GRASS, CoverageType.GRASS,
		CoverageType.RED_TULIP, CoverageType.ORANGE_TULIP, 
		CoverageType.GRASS, CoverageType.GRASS,
		CoverageType.WHITE_TULIP, CoverageType.PINK_TULIP,
		CoverageType.GRASS, CoverageType.GRASS,
		CoverageType.GRASS, CoverageType.GRASS};
			
	private final static CoverageType[] EdiblePlants = {
		CoverageType.WHEAT, CoverageType.CARROTS,
		CoverageType.POTATO, CoverageType.BEETROOT,
		CoverageType.MELON, CoverageType.PUMPKIN};

	private final static CoverageType[] GeneralSaplings = {
		CoverageType.OAK_SAPLING, CoverageType.PINE_SAPLING,
		CoverageType.BIRCH_SAPLING};
	
	private final static CoverageType[] AllSaplings = {
		CoverageType.OAK_SAPLING, CoverageType.PINE_SAPLING,
		CoverageType.BIRCH_SAPLING, CoverageType.JUNGLE_SAPLING, 
		CoverageType.ACACIA_SAPLING};
	
	private final static CoverageType[] OakTrees = {
		CoverageType.OAK_SAPLING, CoverageType.SHORT_OAK_TREE, 
		CoverageType.OAK_TREE, CoverageType.TALL_OAK_TREE};
	
	private final static CoverageType[] PineTrees = {
		CoverageType.PINE_SAPLING, CoverageType.SHORT_PINE_TREE, 
		CoverageType.PINE_TREE, CoverageType.TALL_PINE_TREE};
	
	private final static CoverageType[] BirchTrees = {
		CoverageType.BIRCH_SAPLING, CoverageType.SHORT_BIRCH_TREE, 
		CoverageType.BIRCH_TREE, CoverageType.TALL_BIRCH_TREE};
	
	private final static CoverageType[] JungleTrees = {
		CoverageType.JUNGLE_SAPLING, CoverageType.SHORT_JUNGLE_TREE, 
		CoverageType.JUNGLE_TREE, CoverageType.TALL_JUNGLE_TREE};
	
	private final static CoverageType[] AcaciaTrees = {
		CoverageType.ACACIA_SAPLING, CoverageType.ACACIA_TREE};
	
	private final static CoverageType[] SwampTrees = {
			CoverageType.SWAMP_TREE};
		
	private final static CoverageType[] ShortTrees = {
		CoverageType.SHORT_BIRCH_TREE, CoverageType.SHORT_JUNGLE_TREE,
		CoverageType.SHORT_OAK_TREE, CoverageType.SHORT_PINE_TREE};
	
	private final static CoverageType[] MediumTrees = {
		CoverageType.BIRCH_TREE, CoverageType.JUNGLE_TREE,
		CoverageType.OAK_TREE,CoverageType.PINE_TREE};
		
	private final static CoverageType[] TallTrees = {
		CoverageType.TALL_BIRCH_TREE, CoverageType.TALL_JUNGLE_TREE,
		CoverageType.TALL_OAK_TREE,CoverageType.TALL_PINE_TREE,
		CoverageType.ACACIA_TREE, CoverageType.SWAMP_TREE};
		
	private final static CoverageType[] AllTrees = {
		CoverageType.SHORT_BIRCH_TREE, CoverageType.SHORT_JUNGLE_TREE,
		CoverageType.SHORT_OAK_TREE, CoverageType.SHORT_PINE_TREE,
		CoverageType.BIRCH_TREE, CoverageType.JUNGLE_TREE,
		CoverageType.OAK_TREE,CoverageType.PINE_TREE,
		CoverageType.TALL_BIRCH_TREE, CoverageType.TALL_JUNGLE_TREE,
		CoverageType.TALL_OAK_TREE,CoverageType.TALL_PINE_TREE,
		CoverageType.ACACIA_TREE, CoverageType.SWAMP_TREE};
		
	private final static CoverageType[] ShortMushrooms = {
		CoverageType.BROWN_MUSHROOM, CoverageType.RED_MUSHROOM};

	private final static CoverageType[] NetherPlants = {
		CoverageType.BROWN_MUSHROOM, CoverageType.RED_MUSHROOM,
		CoverageType.NETHERWART, CoverageType.DEAD_BUSH,
		CoverageType.FIRE};
	
	private final static CoverageType[] DecayPlants = {
		CoverageType.BROWN_MUSHROOM, CoverageType.RED_MUSHROOM,
		CoverageType.DEAD_BUSH};
	
	protected final static double oddsOfDarkCover = Odds.oddsLikely;
	protected Odds odds;
	
	public CoverProvider(Odds odds) {
		super();
		this.odds = odds;
	}
	
	public abstract boolean generateCoverage(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z, CoverageType coverageType);
	
	private CoverageType getRandomCoverage(CoverageType ... types) {
		return types[odds.getRandomInt(types.length)];
	}
	
	public void generateRandomCoverage(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z, CoverageType ... types) {
		setCoverage(generator, chunk, x, y, z, getRandomCoverage(types));
	}
	
	public void generateCoverage(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z, CoverageSets coverageSet) {
		switch (coverageSet) {
		case ALL_FLOWERS:
			generateRandomCoverage(generator, chunk, x, y, z, AllFlowers);
			break;
		case ALL_PLANTS:
			generateRandomCoverage(generator, chunk, x, y, z, AllPlants);
			break;
		case ALL_SAPLINGS:
			generateRandomCoverage(generator, chunk, x, y, z, AllSaplings);
			break;
		case PRARIE_PLANTS:
			generateRandomCoverage(generator, chunk, x, y, z, PrariePlants);
			break;
		case EDIBLE_PLANTS:
			generateRandomCoverage(generator, chunk, x, y, z, EdiblePlants);
			break;
		case GENERAL_SAPLINGS:
			generateRandomCoverage(generator, chunk, x, y, z, GeneralSaplings);
			break;
		case OAK_TREES:
			generateRandomCoverage(generator, chunk, x, y, z, OakTrees);
			break;
		case PINE_TREES:
			generateRandomCoverage(generator, chunk, x, y, z, PineTrees);
			break;
		case BIRCH_TREES:
			generateRandomCoverage(generator, chunk, x, y, z, BirchTrees);
			break;
		case JUNGLE_TREES:
			generateRandomCoverage(generator, chunk, x, y, z, JungleTrees);
			break;
		case ACACIA_TREES:
			generateRandomCoverage(generator, chunk, x, y, z, AcaciaTrees);
			break;
		case SWAMP_TREES:
			generateRandomCoverage(generator, chunk, x, y, z, SwampTrees);
			break;
		case SHORT_TREES:
			generateRandomCoverage(generator, chunk, x, y, z, ShortTrees);
			break;
		case MEDIUM_TREES:
			generateRandomCoverage(generator, chunk, x, y, z, MediumTrees);
			break;
		case TALL_TREES:
			generateRandomCoverage(generator, chunk, x, y, z, TallTrees);
			break;
		case ALL_TREES:
			generateRandomCoverage(generator, chunk, x, y, z, AllTrees);
			break;
			
		case NETHER_PLANTS:
			generateRandomCoverage(generator, chunk, x, y, z, NetherPlants);
			break;
		case DECAY_PLANTS:
			generateRandomCoverage(generator, chunk, x, y, z, DecayPlants);
			break;
		case SHORT_FLOWERS:
			generateRandomCoverage(generator, chunk, x, y, z, ShortFlowers);
			break;
		case SHORT_PLANTS:
			generateRandomCoverage(generator, chunk, x, y, z, ShortPlants);
			break;
		case SHORT_MUSHROOMS:
			generateRandomCoverage(generator, chunk, x, y, z, ShortMushrooms);
			break;
		case TALL_FLOWERS:
			generateRandomCoverage(generator, chunk, x, y, z, TallFlowers);
			break;
		case TALL_PLANTS:
			generateRandomCoverage(generator, chunk, x, y, z, TallPlants);
			break;
		}
	}
	
	protected void setCoverage(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z, CoverageType coverageType) {
		Material topsoil = generator.oreProvider.surfaceMaterial;
		switch (coverageType) {
		case NOTHING:
			break;
		case GRASS:
			if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL))
				chunk.setBlock(x, y, z, Material.LONG_GRASS, new LongGrass(GrassSpecies.NORMAL));
			break;
		case FERN:
			if (chunk.isOfTypes(x, y - 1, z, topsoil, Material.GRASS, Material.DIRT, Material.SOIL))
				chunk.setBlock(x, y, z, Material.LONG_GRASS, new LongGrass(GrassSpecies.FERN_LIKE)); 
			break;
		case DEAD_GRASS:
			if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL))
				chunk.setBlock(x, y, z, Material.LONG_GRASS, new LongGrass(GrassSpecies.DEAD));
			
			// fine, try the other one that looks like this
			else if (chunk.isOfTypes(x, y - 1, z, Material.SAND, Material.HARD_CLAY, Material.STAINED_CLAY))
				chunk.setBlock(x, y, z, Material.DEAD_BUSH);
			break;
		case DEAD_BUSH:
			if (chunk.isOfTypes(x, y - 1, z, Material.SAND, Material.DIRT, Material.HARD_CLAY, Material.STAINED_CLAY))
				chunk.setBlock(x, y, z, Material.DEAD_BUSH);
			
			// fine, try the other one that looks like this
			else if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.SOIL))
				chunk.setBlock(x, y, z, Material.LONG_GRASS, new LongGrass(GrassSpecies.DEAD));
			break;
		case CACTUS:
			chunk.setBlockIfNot(x, y - 1, z, Material.SAND);
			chunk.setBlocks(x, y, y + odds.getRandomInt(3) + 2, z, Material.CACTUS);
			break;
		case WHEAT:
			chunk.setBlockIfNot(x, y - 1, z, Material.SOIL);
			chunk.setBlock(x, y, z, Material.CROPS, getRandomCropState());
			break;
		case CARROTS:
			chunk.setBlockIfNot(x, y - 1, z, Material.SOIL);
			chunk.setBlock(x, y, z, Material.CARROT, getRandomCropState());
			break;
		case POTATO:
			chunk.setBlockIfNot(x, y - 1, z, Material.SOIL);
			chunk.setBlock(x, y, z, Material.POTATO, getRandomCropState());
			break;
		case MELON:
			chunk.setBlockIfNot(x, y - 1, z, Material.SOIL);
//			chunk.setBlock(x, y, z, Material.MELON_STEM, getRandomCxropState());
			BlackMagic.setBlock(chunk, x, y, z, Material.MELON_STEM, odds.getRandomInt(8));
			break;
		case PUMPKIN:
			chunk.setBlockIfNot(x, y - 1, z, Material.SOIL);
//			chunk.setBlock(x, y, z, Material.PUMPKIN_STEM, getRandoxmCropState());
			BlackMagic.setBlock(chunk, x, y, z, Material.PUMPKIN_STEM, odds.getRandomInt(8));
			break;
		case BEETROOT:
			chunk.setBlockIfNot(x, y - 1, z, Material.SOIL);
			chunk.setBlock(x, y, z, Material.BEETROOT_BLOCK, getRandomCropState(4));
//			BlackMagic.setBlock(chunk, x, y, z, Material.BEETROOT_BLOCK, odds.getRandomInt(4));
			break;
		case REED:
			if (chunk.isByWater(x, y, z)) {
			    chunk.setBlock(x, y - 1, z, Material.SAND);
				chunk.setBlocks(x, y, y + odds.getRandomInt(2) + 2, z, Material.SUGAR_CANE_BLOCK);
			}
			break;
		case DANDELION:
			if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL))
				chunk.setBlock(x, y, z, Material.YELLOW_FLOWER);
			break;
		case POPPY:
			if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL))
				BlackMagic.setBlock(chunk, x, y, z, Material.RED_ROSE, 0);
			break;
		case BLUE_ORCHID:
			if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL))
				BlackMagic.setBlock(chunk, x, y, z, Material.RED_ROSE, 1);
			break;
		case ALLIUM:
			if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL))
				BlackMagic.setBlock(chunk, x, y, z, Material.RED_ROSE, 2);
			break;
		case AZURE_BLUET:
			if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL))
				BlackMagic.setBlock(chunk, x, y, z, Material.RED_ROSE, 3);
			break;
		case OXEYE_DAISY:
			if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL))
				BlackMagic.setBlock(chunk, x, y, z, Material.RED_ROSE, 8);
			break;
		case RED_TULIP:
			if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL))
				BlackMagic.setBlock(chunk, x, y, z, Material.RED_ROSE, 4);
			break;
		case ORANGE_TULIP:
			if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL))
				BlackMagic.setBlock(chunk, x, y, z, Material.RED_ROSE, 5);
			break;
		case WHITE_TULIP:
			if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL))
				BlackMagic.setBlock(chunk, x, y, z, Material.RED_ROSE, 6);
			break;
		case PINK_TULIP:
			if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL))
				BlackMagic.setBlock(chunk, x, y, z, Material.RED_ROSE, 7);
			break;
		case SUNFLOWER:
			if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL)) {
				BlackMagic.setBlock(chunk, x, y, z, Material.DOUBLE_PLANT, 0);
				BlackMagic.setBlock(chunk, x, y + 1, z, Material.DOUBLE_PLANT, 8);
			}
			break;
		case LILAC:
			if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL)) {
				BlackMagic.setBlock(chunk, x, y, z, Material.DOUBLE_PLANT, 1);
				BlackMagic.setBlock(chunk, x, y + 1, z, Material.DOUBLE_PLANT, 8);
			}
			break;
		case TALL_GRASS:
			if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL)) {
				BlackMagic.setBlock(chunk, x, y, z, Material.DOUBLE_PLANT, 2);
				BlackMagic.setBlock(chunk, x, y + 1, z, Material.DOUBLE_PLANT, 8);
			}
			break;
		case TALL_FERN:
			if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL)) {
				BlackMagic.setBlock(chunk, x, y, z, Material.DOUBLE_PLANT, 3);
				BlackMagic.setBlock(chunk, x, y + 1, z, Material.DOUBLE_PLANT, 8);
			}
			break;
		case ROSE_BUSH:
			if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL)) {
				BlackMagic.setBlock(chunk, x, y, z, Material.DOUBLE_PLANT, 4);
				BlackMagic.setBlock(chunk, x, y + 1, z, Material.DOUBLE_PLANT, 8);
			}
			break;
		case PEONY:
			if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL)) {
				BlackMagic.setBlock(chunk, x, y, z, Material.DOUBLE_PLANT, 5);
				BlackMagic.setBlock(chunk, x, y + 1, z, Material.DOUBLE_PLANT, 8);
			}
			break;
		case EMERALD_GREEN:
			if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL)) {
				chunk.setBlock(x, y, z, Material.LOG, new Tree(TreeSpecies.JUNGLE));
				Leaves leafData = new Leaves(TreeSpecies.JUNGLE);
				leafData.setDecayable(false);
				chunk.setBlocks(x, y + 1, y + odds.getRandomInt(2, 4), z, Material.LEAVES, leafData);
			}
			break;
		case OAK_SAPLING:
			if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL))
				chunk.setBlock(x, y, z, Material.SAPLING, new Sapling(TreeSpecies.GENERIC));
			break;
		case BIRCH_SAPLING:
			if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL))
				chunk.setBlock(x, y, z, Material.SAPLING, new Sapling(TreeSpecies.BIRCH));
			break;
		case PINE_SAPLING:
			if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL))
				chunk.setBlock(x, y, z, Material.SAPLING, new Sapling(TreeSpecies.REDWOOD));
			break;
		case JUNGLE_SAPLING:
			if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL))
				chunk.setBlock(x, y, z, Material.SAPLING, new Sapling(TreeSpecies.JUNGLE));
			break;
		case ACACIA_SAPLING:
			if (chunk.isOfTypes(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL))
				chunk.setBlock(x, y, z, Material.SAPLING, new Sapling(TreeSpecies.ACACIA));
			break;
			
		case BROWN_MUSHROOM:
			if (chunk.isWater(x, y - 1, z))
				chunk.setBlock(x, y - 1, z, Material.MYCEL);
			if (chunk.isOfTypes(x, y - 1, z, Material.MYCEL))
				chunk.setBlock(x, y, z, Material.BROWN_MUSHROOM); 
			break;
		case RED_MUSHROOM:
			if (chunk.isWater(x, y - 1, z))
				chunk.setBlock(x, y - 1, z, Material.MYCEL);
			if (chunk.isOfTypes(x, y - 1, z, Material.MYCEL))
				chunk.setBlock(x, y, z, Material.RED_MUSHROOM); 
			break;
		case NETHERWART:
			chunk.setBlockIfNot(x, y - 1, z, Material.SOUL_SAND);
			chunk.setBlock(x, y, z, Material.NETHER_WARTS, getRandomNetherWartState()); 
			break;
		case FIRE:
			chunk.setBlockIfNot(x, y - 1, z, Material.NETHERRACK);
			chunk.setBlock(x, y, z, Material.FIRE);
			break;
			
		default:
			if (odds.playOdds(generator.settings.spawnTrees))
				switch (coverageType) {
				case MINI_OAK_TRUNK:
					generator.treeProvider.generateMiniTrunk(generator, chunk, x, y, z, TreeType.TREE);
					break;
				case OAK_TRUNK:
					generator.treeProvider.generateNormalTrunk(generator, chunk, x, y, z, TreeType.TREE);
					break;
				case TALL_OAK_TRUNK:
					generator.treeProvider.generateNormalTrunk(generator, chunk, x, y, z, TreeType.BIG_TREE);
					break;
				case MINI_PINE_TRUNK:
					generator.treeProvider.generateMiniTrunk(generator, chunk, x, y, z, TreeType.REDWOOD);
					break;
				case PINE_TRUNK:
					generator.treeProvider.generateNormalTrunk(generator, chunk, x, y, z, TreeType.REDWOOD);
					break;
				case TALL_PINE_TRUNK:
					generator.treeProvider.generateNormalTrunk(generator, chunk, x, y, z, TreeType.TALL_REDWOOD);
					break;
				case MINI_BIRCH_TRUNK:
					generator.treeProvider.generateMiniTrunk(generator, chunk, x, y, z, TreeType.BIRCH);
					break;
				case BIRCH_TRUNK:
					generator.treeProvider.generateNormalTrunk(generator, chunk, x, y, z, TreeType.BIRCH);
					break;
				case TALL_BIRCH_TRUNK:
					generator.treeProvider.generateNormalTrunk(generator, chunk, x, y, z, TreeType.TALL_BIRCH);
					break;
				case MINI_JUNGLE_TRUNK:
					generator.treeProvider.generateNormalTrunk(generator, chunk, x, y, z, TreeType.JUNGLE);
					break;
				case JUNGLE_TRUNK:
					generator.treeProvider.generateNormalTrunk(generator, chunk, x, y, z, TreeType.SMALL_JUNGLE);
					break;
				case TALL_JUNGLE_TRUNK:
					generator.treeProvider.generateNormalTrunk(generator, chunk, x, y, z, TreeType.JUNGLE);
					break;
				case MINI_SWAMP_TRUNK:
					generator.treeProvider.generateMiniTrunk(generator, chunk, x, y, z, TreeType.SWAMP);
					break;
				case SWAMP_TRUNK:
				case TALL_SWAMP_TRUNK:
					generator.treeProvider.generateNormalTrunk(generator, chunk, x, y, z, TreeType.SWAMP);
					break;
				case MINI_ACACIA_TRUNK:
					generator.treeProvider.generateMiniTrunk(generator, chunk, x, y, z, TreeType.ACACIA);
					break;
				case ACACIA_TRUNK:
				case TALL_ACACIA_TRUNK:
					generator.treeProvider.generateNormalTrunk(generator, chunk, x, y, z, TreeType.ACACIA);
					break;
					
				case MINI_OAK_TREE:
					generator.treeProvider.generateMiniTree(generator, chunk, x, y, z, TreeType.TREE);
					break;
				case SHORT_OAK_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.TREE);
					break;
				case OAK_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.BIG_TREE);
					break;
				case TALL_OAK_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.DARK_OAK);
					break;
				case MINI_PINE_TREE:
					generator.treeProvider.generateMiniTree(generator, chunk, x, y, z, TreeType.REDWOOD);
					break;
				case SHORT_PINE_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.REDWOOD);
					break;
				case PINE_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.TALL_REDWOOD);
					break;
				case TALL_PINE_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.MEGA_REDWOOD);
					break;
				case MINI_BIRCH_TREE:
					generator.treeProvider.generateMiniTree(generator, chunk, x, y, z, TreeType.BIRCH);
					break;
				case SHORT_BIRCH_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.BIRCH);
					break;
				case BIRCH_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.BIRCH);
					break;
				case TALL_BIRCH_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.TALL_BIRCH);
					break;
				case MINI_JUNGLE_TREE:
					generator.treeProvider.generateMiniTree(generator, chunk, x, y, z, TreeType.JUNGLE);
					break;
				case SHORT_JUNGLE_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.JUNGLE_BUSH);
					break;
				case JUNGLE_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.SMALL_JUNGLE);
					break;
				case TALL_JUNGLE_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.JUNGLE);
					break;
				case MINI_SWAMP_TREE:
					generator.treeProvider.generateMiniTree(generator, chunk, x, y, z, TreeType.SWAMP);
					break;
				case SWAMP_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.SWAMP);
					break;
				case MINI_ACACIA_TREE:
					generator.treeProvider.generateMiniTree(generator, chunk, x, y, z, TreeType.ACACIA);
					break;
				case ACACIA_TREE:
					generator.treeProvider.generateNormalTree(generator, chunk, x, y, z, TreeType.ACACIA);
					break;
				default:
					break;
				}
		}
	}
	
	private Crops getRandomCropState() {
		return getRandomCropState(CropState.values().length);
	}
	
	private Crops getRandomCropState(int max) {
		return new Crops(CropState.values()[odds.getRandomInt(max)]);
	}
	
	private NetherWarts getRandomNetherWartState() {
		return new NetherWarts(NetherWartsState.values()[odds.getRandomInt(NetherWartsState.values().length)]);
	}
	
	protected boolean likelyCover(CityWorldGenerator generator) {
		return !generator.settings.darkEnvironment || odds.playOdds(oddsOfDarkCover);
	}
	
	public ColorSet getDefaultColorSet() {
		return ColorSet.GREEN;
	}
	
	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	public static CoverProvider loadProvider(CityWorldGenerator generator, Odds odds) {

		CoverProvider provider = null;
		
//		// need something like PhatLoot but for coverage
//		provider = FoliageProvider_PhatFoliage.loadPhatFoliage();
		if (provider == null) {
			
			switch (generator.worldStyle) {
			case FLOODED:
				provider = new CoverProvider_Flooded(odds);
				break;
			case SANDDUNES:
				provider = new CoverProvider_SandDunes(odds);
				break;
			case SNOWDUNES:
				provider = new CoverProvider_SnowDunes(odds);
				break;
			default:
				switch (generator.worldEnvironment) {
				case NETHER:
					provider = new CoverProvider_Nether(odds);
					break;
				case THE_END:
					provider = new CoverProvider_TheEnd(odds);
					break;
				default:
					if (generator.settings.includeDecayedNature)
						provider = new CoverProvider_Decayed(odds);
					else
						provider = new CoverProvider_Normal(odds);
					break;
				}
				break;
			}
		}
	
		return provider;
	}
	
	public boolean isPlantable(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z) {
		
		// only if the spot is empty and the spot above is empty
		return chunk.isEmpty(x, y + 1, z) && !chunk.isEmpty(x, y, z);
//		if (!chunk.isEmpty(x, y + 1, z))
//			return false;
//		
//		// depends on the block's type and what the world is like
//		if (!generator.settings.includeAbovegroundFluids && y <= generator.seaLevel)
//			return chunk.isType(x, y, z, Material.SAND);
//		else
//			return chunk.isPlantable(x, y, z);
	}
	
	@Deprecated
	protected boolean isATree(CoverageType coverageType) {
		switch (coverageType) {
		case MINI_OAK_TREE:
		case SHORT_OAK_TREE:
		case OAK_TREE:
		case TALL_OAK_TREE:
			
		case MINI_PINE_TREE:
		case SHORT_PINE_TREE:
		case PINE_TREE:
		case TALL_PINE_TREE:
		
		case MINI_BIRCH_TREE:
		case SHORT_BIRCH_TREE:
		case BIRCH_TREE:
		case TALL_BIRCH_TREE:
		
		case MINI_JUNGLE_TREE:
		case SHORT_JUNGLE_TREE:
		case JUNGLE_TREE:
		case TALL_JUNGLE_TREE:
		
		case MINI_SWAMP_TREE:
		case SWAMP_TREE:
		
		case MINI_ACACIA_TREE:
		case ACACIA_TREE:
			return true;
		
		default:
			return false;
		}
	}
	
	@Deprecated
	protected CoverageType convertToTreeTrunk(CoverageType coverageType) {
		switch (coverageType) {
		case MINI_OAK_TREE:
			return CoverageType.MINI_OAK_TRUNK;
		case SHORT_OAK_TREE:
		case OAK_TREE:
		case TALL_OAK_TREE:
			return CoverageType.OAK_TRUNK;
			
		case MINI_PINE_TREE:
			return CoverageType.MINI_PINE_TRUNK;
		case SHORT_PINE_TREE:
		case PINE_TREE:
		case TALL_PINE_TREE:
			return CoverageType.PINE_TRUNK;
		
		case MINI_BIRCH_TREE:
			return CoverageType.MINI_BIRCH_TRUNK;
		case SHORT_BIRCH_TREE:
		case BIRCH_TREE:
		case TALL_BIRCH_TREE:
			return CoverageType.BIRCH_TRUNK;
		
		case MINI_JUNGLE_TREE:
			return CoverageType.MINI_JUNGLE_TRUNK;
		case SHORT_JUNGLE_TREE:
		case JUNGLE_TREE:
		case TALL_JUNGLE_TREE:
			return CoverageType.JUNGLE_TRUNK;
		
		case MINI_SWAMP_TREE:
			return CoverageType.MINI_SWAMP_TRUNK;
		case SWAMP_TREE:
			return CoverageType.SWAMP_TRUNK;
		
		case MINI_ACACIA_TREE:
			return CoverageType.MINI_ACACIA_TRUNK;
		case ACACIA_TREE:
			return CoverageType.ACACIA_TRUNK;
		
		default:
			return CoverageType.NOTHING;
		}
	}
}
