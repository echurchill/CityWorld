package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.BlackMagic;
import me.daddychurchill.CityWorld.Support.SupportChunk;

import org.bukkit.CropState;
import org.bukkit.GrassSpecies;
import org.bukkit.Material;
import org.bukkit.NetherWartsState;
import org.bukkit.TreeSpecies;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.material.Crops;
import org.bukkit.material.LongGrass;
import org.bukkit.material.NetherWarts;
import org.bukkit.material.Tree;

public abstract class CoverProvider extends Provider {
	
	public enum LigneousType {MINI_OAK, SHORT_OAK, OAK, TALL_OAK, 
							  MINI_PINE, SHORT_PINE, PINE, TALL_PINE, 
							  MINI_BIRCH, SHORT_BIRCH, BIRCH, TALL_BIRCH, 
							  MINI_JUNGLE, SHORT_JUNGLE, JUNGLE, TALL_JUNGLE,
//							  BROWN_MUSHROOM, RED_MUSHROOM,
							  SWAMP, ACACIA};
	public enum CoverageType {
		GRASS, FERN, DEAD_GRASS, DANDELION, DEAD_BUSH, 
		
		POPPY, BLUE_ORCHID, ALLIUM, AZURE_BLUET, OXEYE_DAISY,
		RED_TULIP, ORANGE_TULIP, WHITE_TULIP, PINK_TULIP,

		SUNFLOWER, LILAC, TALL_GRASS, TALL_FERN, ROSE_BUSH, PEONY,
		
		CACTUS, REED, EMERALD_GREEN, 
		
		OAK_SAPLING, SPRUCE_SAPLING, BIRCH_SAPLING, 
		JUNGLE_SAPLING, ACACIA_SAPLING, DARK_OAK_SAPLING,
		
		SHORT_OAK_TREE, OAK_TREE, TALL_OAK_TREE, 
		SHORT_PINE_TREE, PINE_TREE, TALL_PINE_TREE, 
		SHORT_BIRCH_TREE, BIRCH_TREE, TALL_BIRCH_TREE, 
		SHORT_JUNGLE_TREE, JUNGLE_TREE, TALL_JUNGLE_TREE,
		SWAMP_TREE, ACACIA_TREE,
//		TALL_BROWN_MUSHROOM, TALL_RED_MUSHROOM,
		  
		WHEAT, CARROTS, POTATO, MELON, PUMPKIN, 

		BROWN_MUSHROOM, RED_MUSHROOM, NETHERWART,
		FIRE};
	
	public enum FoliageSets {SHORT_FLOWERS, TALL_FLOWERS, ALL_FLOWERS,
		SHORT_PLANTS, TALL_PLANTS, ALL_PLANTS,
		GENERAL_SAPLINGS, ALL_SAPLINGS, 
		OAK_TREES, PINE_TREES, BIRCH_TREES, 
		JUNGLE_TREES, ACACIA_TREES, SWAMP_TREES, 
		EDIBLE_PLANTS, SHORT_MUSHROOMS, 
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
		CoverageType.EMERALD_GREEN};
	
	private final static CoverageType[] AllPlants = {
		CoverageType.GRASS, CoverageType.FERN,
		CoverageType.CACTUS, CoverageType.REED,
		CoverageType.TALL_GRASS, CoverageType.TALL_FERN,
		CoverageType.EMERALD_GREEN};

	private final static CoverageType[] EdiblePlants = {
		CoverageType.WHEAT, CoverageType.CARROTS,
		CoverageType.POTATO, CoverageType.MELON,
		CoverageType.PUMPKIN};

	private final static CoverageType[] GeneralSaplings = {
		CoverageType.OAK_SAPLING, CoverageType.SPRUCE_SAPLING,
		CoverageType.BIRCH_SAPLING, CoverageType.DARK_OAK_SAPLING};
	
	private final static CoverageType[] AllSaplings = {
		CoverageType.OAK_SAPLING, CoverageType.SPRUCE_SAPLING,
		CoverageType.BIRCH_SAPLING, CoverageType.DARK_OAK_SAPLING,
		CoverageType.JUNGLE_SAPLING, CoverageType.ACACIA_SAPLING};

	private final static CoverageType[] OakTrees = {
		CoverageType.OAK_SAPLING, CoverageType.DARK_OAK_SAPLING, 
		CoverageType.SHORT_OAK_TREE, CoverageType.OAK_TREE, 
		CoverageType.TALL_OAK_TREE};
	
	private final static CoverageType[] PineTrees = {
		CoverageType.SPRUCE_SAPLING, CoverageType.SHORT_PINE_TREE, 
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
	
	private final static CoverageType[] ShortMushrooms = {
		CoverageType.BROWN_MUSHROOM, CoverageType.RED_MUSHROOM};

	private final static CoverageType[] NetherPlants = {
		CoverageType.BROWN_MUSHROOM, CoverageType.RED_MUSHROOM,
		CoverageType.NETHERWART, CoverageType.DEAD_BUSH,
		CoverageType.FIRE};
	
	private final static CoverageType[] DecayPlants = {
		CoverageType.BROWN_MUSHROOM, CoverageType.RED_MUSHROOM,
		CoverageType.DEAD_BUSH};
	
	protected final static double oddsOfDarkCover = DataContext.oddsLikely;
	protected Odds odds;
	
	public CoverProvider(Odds odds) {
		super();
		this.odds = odds;
	}
	
	public abstract boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, LigneousType treeType);
	public abstract boolean generateCoverage(WorldGenerator generator, RealChunk chunk, int x, int y, int z, CoverageType coverageType);
	
	public void setCoverage(SupportChunk chunk, int x, int y, int z, CoverageType ... types) {
		setCoverage(chunk, x, y, z, getRandomCoverage(types));
	}
	
	public CoverageType getRandomCoverage(CoverageType ... types) {
		return types[odds.getRandomInt(types.length)];
	}
	
	public void setCoverage(SupportChunk chunk, int x, int y, int z, FoliageSets coverageSet) {
		switch (coverageSet) {
		case ALL_FLOWERS:
			setCoverage(chunk, x, y, z, AllFlowers);
			break;
		case ALL_PLANTS:
			setCoverage(chunk, x, y, z, AllPlants);
			break;
		case ALL_SAPLINGS:
			setCoverage(chunk, x, y, z, AllSaplings);
			break;
		case EDIBLE_PLANTS:
			setCoverage(chunk, x, y, z, EdiblePlants);
			break;
		case GENERAL_SAPLINGS:
			setCoverage(chunk, x, y, z, GeneralSaplings);
			break;
		case OAK_TREES:
			setCoverage(chunk, x, y, z, OakTrees);
			break;
		case PINE_TREES:
			setCoverage(chunk, x, y, z, PineTrees);
			break;
		case BIRCH_TREES:
			setCoverage(chunk, x, y, z, BirchTrees);
			break;
		case JUNGLE_TREES:
			setCoverage(chunk, x, y, z, JungleTrees);
			break;
		case ACACIA_TREES:
			setCoverage(chunk, x, y, z, AcaciaTrees);
			break;
		case SWAMP_TREES:
			setCoverage(chunk, x, y, z, SwampTrees);
			break;
		case NETHER_PLANTS:
			setCoverage(chunk, x, y, z, NetherPlants);
			break;
		case DECAY_PLANTS:
			setCoverage(chunk, x, y, z, DecayPlants);
			break;
		case SHORT_FLOWERS:
			setCoverage(chunk, x, y, z, ShortFlowers);
			break;
		case SHORT_PLANTS:
			setCoverage(chunk, x, y, z, ShortPlants);
			break;
		case SHORT_MUSHROOMS:
			setCoverage(chunk, x, y, z, ShortMushrooms);
			break;
		case TALL_FLOWERS:
			setCoverage(chunk, x, y, z, TallFlowers);
			break;
		case TALL_PLANTS:
			setCoverage(chunk, x, y, z, TallPlants);
			break;
		}
	}
	
	public void setCoverage(SupportChunk chunk, int x, int y, int z, CoverageType coverageType) {
		switch (coverageType) {
		case GRASS:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL);
			chunk.setBlock(x, y, z, Material.LONG_GRASS, new LongGrass(GrassSpecies.NORMAL)); //TODO: Bukkit type mismatch/missing
			break;
		case FERN:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL);
			chunk.setBlock(x, y, z, Material.LONG_GRASS, new LongGrass(GrassSpecies.FERN_LIKE)); 
			break;
		case DEAD_GRASS:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL);
			chunk.setBlock(x, y, z, Material.LONG_GRASS, new LongGrass(GrassSpecies.DEAD));
			break;
		case CACTUS:
			chunk.setBlockIfNot(x, y - 1, z, Material.SAND);
			chunk.setBlocks(x, y, y + odds.getRandomInt(3) + 2, z, Material.CACTUS);
			break;
		case REED:
			chunk.setBlockIfNot(x, y - 1, z, Material.SAND, Material.GRASS, Material.DIRT, Material.SOIL);
			chunk.setBlocks(x, y, y + odds.getRandomInt(2) + 2, z, Material.SUGAR_CANE_BLOCK);
			break;
		case DANDELION:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL);
			chunk.setBlock(x, y, z, Material.YELLOW_FLOWER);
			break;
		case POPPY:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL);
			BlackMagic.setBlock(chunk, x, y, z, Material.RED_ROSE, 0);
			break;
		case BLUE_ORCHID:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL);
			BlackMagic.setBlock(chunk, x, y, z, Material.RED_ROSE, 1);
			break;
		case ALLIUM:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL);
			BlackMagic.setBlock(chunk, x, y, z, Material.RED_ROSE, 2);
			break;
		case AZURE_BLUET:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL);
			BlackMagic.setBlock(chunk, x, y, z, Material.RED_ROSE, 3);
			break;
		case OXEYE_DAISY:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL);
			BlackMagic.setBlock(chunk, x, y, z, Material.RED_ROSE, 8);
			break;
		case RED_TULIP:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL);
			BlackMagic.setBlock(chunk, x, y, z, Material.RED_ROSE, 4);
			break;
		case ORANGE_TULIP:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL);
			BlackMagic.setBlock(chunk, x, y, z, Material.RED_ROSE, 5);
			break;
		case WHITE_TULIP:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL);
			BlackMagic.setBlock(chunk, x, y, z, Material.RED_ROSE, 6);
			break;
		case PINK_TULIP:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL);
			BlackMagic.setBlock(chunk, x, y, z, Material.RED_ROSE, 7);
			break;
		case SUNFLOWER:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL);
			BlackMagic.setBlock(chunk, x, y, z, Material.DOUBLE_PLANT, 0);
			BlackMagic.setBlock(chunk, x, y + 1, z, Material.DOUBLE_PLANT, 8);
			break;
		case LILAC:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL);
			BlackMagic.setBlock(chunk, x, y, z, Material.DOUBLE_PLANT, 1);
			BlackMagic.setBlock(chunk, x, y + 1, z, Material.DOUBLE_PLANT, 8);
			break;
		case TALL_GRASS:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL);
			BlackMagic.setBlock(chunk, x, y, z, Material.DOUBLE_PLANT, 2);
			BlackMagic.setBlock(chunk, x, y + 1, z, Material.DOUBLE_PLANT, 8);
			break;
		case TALL_FERN:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL);
			BlackMagic.setBlock(chunk, x, y, z, Material.DOUBLE_PLANT, 3);
			BlackMagic.setBlock(chunk, x, y + 1, z, Material.DOUBLE_PLANT, 8);
			break;
		case ROSE_BUSH:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL);
			BlackMagic.setBlock(chunk, x, y, z, Material.DOUBLE_PLANT, 4);
			BlackMagic.setBlock(chunk, x, y + 1, z, Material.DOUBLE_PLANT, 8);
			break;
		case PEONY:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL);
			BlackMagic.setBlock(chunk, x, y, z, Material.DOUBLE_PLANT, 5);
			BlackMagic.setBlock(chunk, x, y + 1, z, Material.DOUBLE_PLANT, 8);
			break;
		case EMERALD_GREEN:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT, Material.SOIL);
			chunk.setBlock(x, y, z, Material.LOG, new Tree(TreeSpecies.JUNGLE));
//			chunk.setBlocks(x, y + 1, y + odds.getRandomInt(2, 4), z, Material.LEAVES, new Leaves()); //TODO: Jungle + NoDecay
			BlackMagic.setBlocks(chunk, x, y + 1, y + odds.getRandomInt(2, 4), z, Material.LEAVES, 3 + 4); //TODO: Jungle + NoDecay
			break;
		case OAK_SAPLING:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT);
			chunk.setBlock(x, y, z, Material.SAPLING, new Tree(TreeSpecies.GENERIC)); //TODO: Bukkit type mismatch/missing
			break;
		case BIRCH_SAPLING:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT);
			chunk.setBlock(x, y, z, Material.SAPLING, new Tree(TreeSpecies.BIRCH));
			break;
		case SPRUCE_SAPLING:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT);
			chunk.setBlock(x, y, z, Material.SAPLING, new Tree(TreeSpecies.REDWOOD)); //TODO: Bukkit type mismatch/missing
			break;
		case JUNGLE_SAPLING:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT);
			chunk.setBlock(x, y, z, Material.SAPLING, new Tree(TreeSpecies.JUNGLE));
			break;
		case ACACIA_SAPLING:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT);
			chunk.setBlock(x, y, z, Material.SAPLING, new Tree(TreeSpecies.ACACIA));
			break;
		case DARK_OAK_SAPLING:
			chunk.setBlockIfNot(x, y - 1, z, Material.GRASS, Material.DIRT);
			chunk.setBlockIfNot(x + 1, y - 1, z, Material.GRASS, Material.DIRT);
			chunk.setBlockIfNot(x, y - 1, z + 1, Material.GRASS, Material.DIRT);
			chunk.setBlockIfNot(x + 1, y - 1, z + 1, Material.GRASS, Material.DIRT);
			chunk.setBlock(x, y, z, Material.SAPLING, new Tree(TreeSpecies.DARK_OAK));
			chunk.setBlock(x + 1, y, z, Material.SAPLING, new Tree(TreeSpecies.DARK_OAK));
			chunk.setBlock(x, y, z + 1, Material.SAPLING, new Tree(TreeSpecies.DARK_OAK));
			chunk.setBlock(x + 1, y, z + 1, Material.SAPLING, new Tree(TreeSpecies.DARK_OAK));
			break;
		case SHORT_OAK_TREE:
			generateTree(chunk, x, y, z, LigneousType.SHORT_OAK);
			break;
		case OAK_TREE:
			generateTree(chunk, x, y, z, LigneousType.OAK);
			break;
		case TALL_OAK_TREE:
			generateTree(chunk, x, y, z, LigneousType.TALL_OAK);
			break;
		case SHORT_PINE_TREE:
			generateTree(chunk, x, y, z, LigneousType.SHORT_PINE);
			break;
		case PINE_TREE:
			generateTree(chunk, x, y, z, LigneousType.PINE);
			break;
		case TALL_PINE_TREE:
			generateTree(chunk, x, y, z, LigneousType.TALL_PINE);
			break;
		case SHORT_BIRCH_TREE:
			generateTree(chunk, x, y, z, LigneousType.SHORT_BIRCH);
			break;
		case BIRCH_TREE:
			generateTree(chunk, x, y, z, LigneousType.BIRCH);
			break;
		case TALL_BIRCH_TREE:
			generateTree(chunk, x, y, z, LigneousType.TALL_BIRCH);
			break;
		case SHORT_JUNGLE_TREE:
			generateTree(chunk, x, y, z, LigneousType.SHORT_JUNGLE);
			break;
		case JUNGLE_TREE:
			generateTree(chunk, x, y, z, LigneousType.JUNGLE);
			break;
		case TALL_JUNGLE_TREE:
			generateTree(chunk, x, y, z, LigneousType.TALL_JUNGLE);
			break;
		case SWAMP_TREE:
			generateTree(chunk, x, y, z, LigneousType.SWAMP);
			break;
		case ACACIA_TREE:
			generateTree(chunk, x, y, z, LigneousType.ACACIA);
			break;
		case WHEAT:
			chunk.setBlockIfNot(x, y - 1, z, Material.SOIL);
			chunk.setBlock(x, y, z, Material.CROPS, new Crops(getRandomWheatGrowth())); //TODO: Bukkit type mismatch/missing
			break;
		case CARROTS:
			chunk.setBlockIfNot(x, y - 1, z, Material.SOIL);
			BlackMagic.setBlock(chunk, x, y, z, Material.CARROT, getRandomCarrotGrowth()); //TODO: Bukkit missing proper MaterialData
			break;
		case POTATO:
			chunk.setBlockIfNot(x, y - 1, z, Material.SOIL);
			BlackMagic.setBlock(chunk, x, y, z, Material.POTATO, getRandomPotatoGrowth()); //TODO: Bukkit missing proper MaterialData
			break;
		case MELON:
			chunk.setBlockIfNot(x, y - 1, z, Material.SOIL);
			BlackMagic.setBlock(chunk, x, y, z, Material.MELON_STEM, getRandomMelonGrowth()); //TODO: Bukkit missing proper MaterialData
			break;
		case PUMPKIN:
			chunk.setBlockIfNot(x, y - 1, z, Material.SOIL);
			BlackMagic.setBlock(chunk, x, y, z, Material.PUMPKIN_STEM, getRandomPumpkinGrowth()); //TODO: Bukkit missing proper MaterialData
			break;
		case DEAD_BUSH:
			chunk.setBlockIfNot(x, y - 1, z, Material.SAND, Material.DIRT, Material.HARD_CLAY);
			chunk.setBlock(x, y, z, Material.DEAD_BUSH);
			break;
		case BROWN_MUSHROOM:
			if (chunk.getActualBlock(x, y - 1, z).isLiquid())
				chunk.setBlock(x, y - 1, z, Material.MYCEL);
			chunk.setBlock(x, y, z, Material.BROWN_MUSHROOM); 
			break;
		case RED_MUSHROOM:
			if (chunk.getActualBlock(x, y - 1, z).isLiquid())
				chunk.setBlock(x, y - 1, z, Material.MYCEL);
			chunk.setBlock(x, y, z, Material.RED_MUSHROOM); 
			break;
		case NETHERWART:
			chunk.setBlockIfNot(x, y - 1, z, Material.SOUL_SAND);
			chunk.setBlock(x, y, z, Material.NETHER_WARTS, new NetherWarts(getRandomNetherWartGrowth())); //TODO: Bukkit type mismatch/missing
			break;
		case FIRE:
			chunk.setBlockIfNot(x, y - 1, z, Material.NETHERRACK);
			chunk.setBlock(x, y, z, Material.FIRE);
			break;
		}
	}
	
	private CropState getRandomWheatGrowth() {
		return CropState.values()[odds.getRandomInt(CropState.values().length)];
	}
	
	private int getRandomCarrotGrowth() {
		return odds.getRandomInt(8);
	}
	
	private int getRandomPotatoGrowth() {
		return odds.getRandomInt(8);
	}
	
	private int getRandomMelonGrowth() {
		return odds.getRandomInt(8);
	}
	
	private int getRandomPumpkinGrowth() {
		return odds.getRandomInt(8);
	}
	
	private NetherWartsState getRandomNetherWartGrowth() {
		return NetherWartsState.values()[odds.getRandomInt(NetherWartsState.values().length)];
	}
	
	protected boolean likelyCover(WorldGenerator generator) {
		return !generator.settings.darkEnvironment || odds.playOdds(oddsOfDarkCover);
	}
	
	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	public static CoverProvider loadProvider(WorldGenerator generator, Odds odds) {

		CoverProvider provider = null;
		
//		// need something like PhatLoot but for coverage
//		provider = FoliageProvider_PhatFoliage.loadPhatFoliage();
		if (provider == null) {
			
			switch (generator.worldStyle) {
			case FLOODED:
				provider = new CoverProvider_Flooded(odds);
				break;
			default:
//				if (generator.settings.includeTekkitMaterials) {
//					generator.reportMessage("[FoliageProvider] Found ForgeTekkit, enabling its coverage");
//	
//					//TODO provide nether, theend and decayed variants of Tekkit
//					provider = new FoliageProvider_Tekkit(odds);
//				} else {
//					
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
//				}
				break;
			}
		}
	
		return provider;
	}
	
	public boolean isPlantable(WorldGenerator generator, RealChunk chunk, int x, int y, int z) {
		
		// only if the spot above is empty
		if (!chunk.isEmpty(x, y + 1, z))
			return false;
		
		// depends on the block's type and what the world is like
		if (!generator.settings.includeAbovegroundFluids && y <= generator.seaLevel)
			return chunk.isType(x, y, z, Material.SAND);
		else
			return chunk.isPlantable(x, y, z);
	}
	
	private int maxTries = 3;

	protected boolean generateTree(SupportChunk chunk, int x, int y, int z, LigneousType type) {
		switch (type) {
		case MINI_BIRCH:
			return generateMiniTree(chunk, x, y, z, TreeType.BIRCH);
		case SHORT_BIRCH:
			return generateNormalTree(chunk, x, y, z, TreeType.BIRCH); // BUKKIT: there isn't a smaller than normal Birch tree
		case BIRCH:
			return generateNormalTree(chunk, x, y, z, TreeType.BIRCH);
		case TALL_BIRCH:
			return generateNormalTree(chunk, x, y, z, TreeType.TALL_BIRCH);

		case MINI_PINE:
			return generateMiniTree(chunk, x, y, z, TreeType.REDWOOD);
		case SHORT_PINE:
			return generateNormalTree(chunk, x, y, z, TreeType.REDWOOD);
		case PINE:
			return generateNormalTree(chunk, x, y, z, TreeType.TALL_REDWOOD);
		case TALL_PINE:
			return generateNormalTree(chunk, x, y, z, TreeType.MEGA_REDWOOD);

		case MINI_OAK:
			return generateMiniTree(chunk, x, y, z, TreeType.TREE);
		case SHORT_OAK:
			return generateNormalTree(chunk, x, y, z, TreeType.TREE);
		case OAK:
			return generateNormalTree(chunk, x, y, z, TreeType.BIG_TREE);
		case TALL_OAK:
			return generateNormalTree(chunk, x, y, z, TreeType.DARK_OAK);

		case MINI_JUNGLE:
			return generateMiniTree(chunk, x, y, z, TreeType.JUNGLE);
		case SHORT_JUNGLE:
			return generateNormalTree(chunk, x, y, z, TreeType.JUNGLE_BUSH);
		case JUNGLE:
			return generateNormalTree(chunk, x, y, z, TreeType.SMALL_JUNGLE);
		case TALL_JUNGLE:
			return generateNormalTree(chunk, x, y, z, TreeType.JUNGLE);

		case SWAMP:
			return generateNormalTree(chunk, x, y, z, TreeType.SWAMP);
		case ACACIA:
			return generateNormalTree(chunk, x, y, z, TreeType.ACACIA);
//		case BROWN_MUSHROOM:
//			return generateNormalTree(chunk, x, y, z, TreeType.BROWN_MUSHROOM, trunk, leaves1, leaves2);
//		case RED_MUSHROOM:
//			return generateNormalTree(chunk, x, y, z, TreeType.RED_MUSHROOM, trunk, leaves1, leaves2);
		default:
			return false;
		}
	}
	
	protected boolean generateTrunk(RealChunk chunk, int x, int y, int z, 
			LigneousType type) {
		
		Material trunkMaterial = Material.LOG;
		int treeHeight;
		int treeData;
		
		switch (type) {
		case SHORT_OAK:
			treeHeight = 3;
			treeData = 0;
			break;
		default:
		case OAK:
			treeHeight = 5;
			treeData = 0;
			break;
		case TALL_OAK:
			treeHeight = 7;
			treeData = 0;
			break;
			
		case SHORT_PINE:
			treeHeight = 3;
			treeData = 1;
			break;
		case PINE:
			treeHeight = 5;
			treeData = 1;
			break;
		case TALL_PINE:
			treeHeight = 9;
			treeData = 1;
			break;
			
		case SHORT_BIRCH:
			treeHeight = 3;
			treeData = 2;
			break;
		case BIRCH: 
			treeHeight = 5;
			treeData = 2;
			break;
		case TALL_BIRCH:
			treeHeight = 7;
			treeData = 2;
			break;
			
		case SWAMP:
		case SHORT_JUNGLE:
			treeHeight = 1;
			treeData = 3;
			break;
		case JUNGLE: 
			treeHeight = 5;
			treeData = 3;
			break;
		case TALL_JUNGLE:
			treeHeight = 9;
			treeData = 3;
			break;
			
		case ACACIA:
			trunkMaterial = Material.LOG_2;
			treeHeight = 4;
			treeData = 0;
			break;
		}
		BlackMagic.setBlocks(chunk, x, y, y + treeHeight, z, trunkMaterial, treeData);
		
		return true;
	}
	
	protected boolean generateMiniTree(SupportChunk chunk, int x, int y, int z, TreeType treeType) {
		Material trunk = Material.LOG;
		Material leaves = Material.LEAVES;
		int treeData = 0;
		int treeHeight = 2;
		
		// Figure out the height
		switch (treeType) {
		case TREE:
		case REDWOOD:
		case BIRCH:
		case JUNGLE_BUSH:
			treeHeight = 2;
			break;

		case BIG_TREE:
		case TALL_REDWOOD:
		case TALL_BIRCH:
		case SMALL_JUNGLE:
		case ACACIA:
			treeHeight = 3;
			break;
		
		case DARK_OAK:
		case MEGA_REDWOOD:
		case JUNGLE:
			treeHeight = 4;
			break;
			
		case BROWN_MUSHROOM: //TODO: We don't do these yet
		case RED_MUSHROOM:
		case SWAMP:
		default:
			return false;
		}

		// Figure out the material data
		switch (treeType) {
		default:
		case TREE:
		case BIG_TREE:
		case DARK_OAK:
			treeData = 0;
			break;
			
		case REDWOOD:
		case TALL_REDWOOD:
		case MEGA_REDWOOD:
			treeData = 1;
			break;
			
		case BIRCH:
		case TALL_BIRCH:
			treeData = 2;
			break;
			
		case JUNGLE_BUSH:
		case SMALL_JUNGLE:
		case JUNGLE:
			treeData = 3;
			break;
		case ACACIA:
			trunk = Material.LOG_2;
			leaves = Material.LEAVES_2;
			treeData = 0;
			break;
		}

		int trunkHeight = treeHeight - 1;
		BlackMagic.setBlocks(chunk, x, y, y + treeHeight, z, trunk, treeData);
		BlackMagic.setBlock(chunk, x - 1, y + trunkHeight, z, leaves, treeData);
		BlackMagic.setBlock(chunk, x + 1, y + trunkHeight, z, leaves, treeData);
		BlackMagic.setBlock(chunk, x, y + trunkHeight, z - 1, leaves, treeData);
		BlackMagic.setBlock(chunk, x, y + trunkHeight, z + 1, leaves, treeData);
		BlackMagic.setBlock(chunk, x, y + treeHeight, z, leaves, treeData);
		
		return true;
	}
	
//	protected boolean generateNormalTree(RealChunk chunk, Odds odds, int x, int y, int z, 
//			TreeType treeType, Material trunk, Material leaves1, Material leaves2) {
	protected boolean generateNormalTree(SupportChunk chunk, int x, int y, int z, 
			TreeType treeType) {
		boolean result = false;
//		boolean customTree = trunk != log || leaves1 != leaves || leaves2 != leaves;
		
		// where do we start?
		int bottomY = y;
		int trunkWidth = 1;
		Block base = chunk.getActualBlock(x, y - 1, z);
		Material baseMaterial = base.getType();
		byte baseData = BlackMagic.getMaterialData(base);
		if (treeType == TreeType.DARK_OAK)
			trunkWidth = 2;
		
		try {
			int tries = 0;
			
			// keep moving up till we get a tree
			while (tries < maxTries) {
				
				// a place to plant
				chunk.setBlocks(x, x + trunkWidth, y - 1, z, z + trunkWidth, Material.DIRT);
				
				// did we make a tree?
//TODO: THIS WAS NERFED FOR 1.7.2, DO NOT USE
//				if (customTree)
//					result = chunk.world.generateTree(chunk.getBlockLocation(x, y, z), treeType, 
//							getCustomTreeDelegate(chunk, odds, trunk, leaves1, leaves2));
//				else
//					result = chunk.world.generateTree(chunk.getBlockLocation(x, y, z), treeType, 
//							getVanillaTreeDelegate(chunk));
				result = chunk.world.generateTree(chunk.getBlockLocation(x, y, z), treeType);
				
				// did it finally work?
				if (result) {
					
					// copy the trunk down a bit
					Block root = chunk.getActualBlock(x, y, z);
					BlackMagic.setBlocks(chunk, x, x + trunkWidth, bottomY, y, z, z + trunkWidth, 
							root.getType(), BlackMagic.getMaterialData(root));
					
					// all done
					break;
					
				// on failure move a bit more
				} else {
					y++;
				}
				
				// and again?
				tries++;
			}
		} finally {
			
			// if we actually failed remove all that dirt we made
			if (!result)
				chunk.setBlocks(x, x + trunkWidth, bottomY, y, z, z + trunkWidth, Material.AIR);
 			
			// set the base back to what it was originally
			BlackMagic.setBlocks(chunk, x, x + trunkWidth, bottomY - 1, z, z + trunkWidth, baseMaterial, baseData);
		}
		return result;
	}
	
//	public final static Material log = Material.LOG;
//	public final static byte logId = BlackMagic.getMaterialId(log);
//	public final static Material leaves = Material.LEAVES;
//	public final static byte leavesId = BlackMagic.getMaterialId(leaves);
//	
//	protected BlockChangeDelegate getCustomTreeDelegate(RealChunk chunk, Odds odds, 
//			Material trunk, Material leaves1, Material leaves2) {
//		return new TreeCustomDelegate(chunk, odds, trunk, leaves1, leaves2);
//	}
//	
//	protected BlockChangeDelegate getVanillaTreeDelegate(RealChunk chunk) {
//		return new TreeVanillaDelegate(chunk);
//	}
}
