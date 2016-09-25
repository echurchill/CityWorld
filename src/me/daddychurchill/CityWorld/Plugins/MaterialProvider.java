package me.daddychurchill.CityWorld.Plugins;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.RealMaterial;
import me.daddychurchill.CityWorld.Support.RealMaterialList;

public class MaterialProvider {

	public final static String tagRandomMaterials_BunkerChests = "Random_Materials_For_Bunker_Chests";
	public RealMaterialList itemsRandomMaterials_BunkerChests = createList(tagRandomMaterials_BunkerChests, 
			RealMaterial.IRON_PICKAXE, 
			RealMaterial.IRON_SWORD,
			RealMaterial.IRON_SPADE,

			RealMaterial.IRON_BOOTS,
			RealMaterial.IRON_CHESTPLATE,
			RealMaterial.IRON_HELMET,
			RealMaterial.IRON_LEGGINGS,
			RealMaterial.CHAINMAIL_BOOTS,
			RealMaterial.CHAINMAIL_CHESTPLATE,
			RealMaterial.CHAINMAIL_HELMET,
			RealMaterial.CHAINMAIL_LEGGINGS,
			
			RealMaterial.TORCH,
			RealMaterial.BUCKET,
			RealMaterial.WATER_BUCKET,
			RealMaterial.LAVA_BUCKET,
			
			RealMaterial.REDSTONE);
	
	public final static String tagRandomMaterials_MineChests = "Random_Materials_For_Mine_Chests";
	public RealMaterialList itemsRandomMaterials_MineChests = createList(tagRandomMaterials_MineChests, 
			RealMaterial.STONE_PICKAXE,
			RealMaterial.STONE_SWORD,
			RealMaterial.STONE_SPADE,
			RealMaterial.IRON_PICKAXE, 
			RealMaterial.IRON_SWORD,
			RealMaterial.IRON_SPADE,

			RealMaterial.LEATHER_BOOTS,
			RealMaterial.LEATHER_CHESTPLATE,
			RealMaterial.LEATHER_HELMET,
			RealMaterial.LEATHER_LEGGINGS,
			
			RealMaterial.TORCH,	
			RealMaterial.COMPASS,
			RealMaterial.WATCH,
			RealMaterial.FLINT,
			RealMaterial.FLINT_AND_STEEL, 
			RealMaterial.BUCKET,
			RealMaterial.WATER_BUCKET,
			RealMaterial.LAVA_BUCKET,
			RealMaterial.SULPHUR,
			RealMaterial.SAND,

			RealMaterial.BOOK,
			RealMaterial.COAL,
			RealMaterial.DIAMOND,
			RealMaterial.IRON_INGOT,
			RealMaterial.GOLD_INGOT,
			
			RealMaterial.APPLE,
			RealMaterial.ROTTEN_FLESH);
	
	public final static String tagRandomMaterials_SewerChests = "Random_Materials_For_Sewer_Chests";
	public RealMaterialList itemsRandomMaterials_SewerChests = createList(tagRandomMaterials_SewerChests, 
			RealMaterial.WOOD_PICKAXE, 
			RealMaterial.WOOD_SWORD,
			RealMaterial.WOOD_SPADE,
			RealMaterial.STONE_PICKAXE,	
			RealMaterial.STONE_SWORD,
			RealMaterial.STONE_SPADE,
			
			RealMaterial.LEATHER_BOOTS,
			RealMaterial.LEATHER_CHESTPLATE,
			RealMaterial.LEATHER_HELMET,
			RealMaterial.LEATHER_LEGGINGS,
			
			RealMaterial.TORCH,	
			RealMaterial.COMPASS,
			RealMaterial.WATCH,
			RealMaterial.FLINT_AND_STEEL,

			RealMaterial.COAL,

			RealMaterial.APPLE,
			RealMaterial.ROTTEN_FLESH);
	
	public final static String tagRandomMaterials_StorageShedChests = "Random_Materials_For_Storage_Shed_Chests";
	public RealMaterialList itemsRandomMaterials_StorageShedChests = createList(tagRandomMaterials_StorageShedChests,
			RealMaterial.WOOD_AXE, 
			RealMaterial.WOOD_SPADE, 
			RealMaterial.WOOD_PICKAXE, 
			RealMaterial.IRON_AXE, 
			RealMaterial.IRON_SPADE, 
			RealMaterial.IRON_PICKAXE,	
			
			RealMaterial.IRON_HELMET, 

			RealMaterial.TORCH,	
			RealMaterial.PAPER,
			RealMaterial.BOOK,
			
			RealMaterial.COOKED_MUTTON,
			RealMaterial.BOWL,
			RealMaterial.MUSHROOM_SOUP);

	public final static String tagRandomMaterials_FarmChests = "Random_Materials_For_Farm_Chests";
	public RealMaterialList itemsRandomMaterials_FarmChests = createList(tagRandomMaterials_FarmChests,
			RealMaterial.WOOD_SPADE, 
			RealMaterial.WOOD_PICKAXE, 
			RealMaterial.WOOD_HOE,
			RealMaterial.IRON_SPADE, 
			RealMaterial.IRON_PICKAXE,	
			RealMaterial.IRON_HOE, 
			
			RealMaterial.LEATHER_BOOTS,
			RealMaterial.LEATHER_CHESTPLATE,
			RealMaterial.LEATHER_HELMET,
			RealMaterial.LEATHER_LEGGINGS,
			
			RealMaterial.BOW,
			RealMaterial.ARROW,
			
			RealMaterial.PAPER,
			RealMaterial.BOOK,
			RealMaterial.TORCH,
			RealMaterial.FISHING_ROD,
			RealMaterial.SHEARS,
			RealMaterial.BUCKET,
			RealMaterial.WATER_BUCKET,
			RealMaterial.MILK_BUCKET,
			RealMaterial.BONE,
			RealMaterial.BOWL,
			
			RealMaterial.COOKIE,
			RealMaterial.SUGAR,
			RealMaterial.GRILLED_PORK,
			RealMaterial.COOKED_BEEF,
			RealMaterial.COOKED_CHICKEN,
			RealMaterial.COOKED_MUTTON,
			RealMaterial.COOKED_RABBIT,
			RealMaterial.BAKED_POTATO,
			RealMaterial.MELON,
			RealMaterial.PUMPKIN_PIE,
			RealMaterial.MUSHROOM_SOUP);

	public final static String tagRandomMaterials_FarmOutputChests = "Random_Materials_For_Farm_Output_Chests";
	public RealMaterialList itemsRandomMaterials_FarmOutputChests = createList(tagRandomMaterials_FarmOutputChests,
			RealMaterial.LEATHER,
			RealMaterial.PORK,
			RealMaterial.RAW_BEEF,
			RealMaterial.RAW_CHICKEN,
			RealMaterial.RABBIT,
			RealMaterial.MUTTON,
			RealMaterial.SEEDS,
			RealMaterial.WOOL,
			RealMaterial.POTATO_ITEM,
			RealMaterial.CARROT_ITEM,
			RealMaterial.PUMPKIN_SEEDS,
			RealMaterial.MELON_SEEDS,
			RealMaterial.CARROT_ITEM,
			RealMaterial.POTATO_ITEM,
			RealMaterial.SUGAR_CANE,
			RealMaterial.APPLE);
	
	public final static String tagRandomMaterials_LumberChests = "Random_Materials_For_Lumber_Chests";
	public RealMaterialList itemsRandomMaterials_LumberChests = createList(tagRandomMaterials_LumberChests,
			RealMaterial.WOOD_AXE, 
			RealMaterial.WOOD_SPADE, 
			RealMaterial.WOOD_PICKAXE,
			RealMaterial.IRON_AXE, 
			RealMaterial.IRON_SPADE, 
			RealMaterial.IRON_PICKAXE,	
			
			RealMaterial.IRON_HELMET, 

			RealMaterial.FLINT_AND_STEEL,
			RealMaterial.COAL,
			RealMaterial.TORCH,	
			
			RealMaterial.GRILLED_PORK,
			RealMaterial.COOKED_CHICKEN,
			RealMaterial.COOKIE);
	
	public final static String tagRandomMaterials_LumberOutputChests = "Random_Materials_For_Lumber_Output_Chests";
	public RealMaterialList itemsRandomMaterials_LumberOutputChests = createList(tagRandomMaterials_LumberOutputChests,
			RealMaterial.WOOD, // simple but cheesy way to increase the odds for this one
			RealMaterial.WOOD,
			RealMaterial.WOOD,
			RealMaterial.WOOD,
			RealMaterial.WOOD,
			RealMaterial.WOOD,
			RealMaterial.WOOD,
			RealMaterial.WOOD,
			RealMaterial.WOOD,
			RealMaterial.WOOD,
			RealMaterial.LOG,
			RealMaterial.LOG,
			RealMaterial.LOG,
			RealMaterial.STICK,
			RealMaterial.STICK,
			RealMaterial.STICK,

			RealMaterial.WOOD_STEP,
			RealMaterial.WOODEN_DOOR,
			RealMaterial.FENCE,
			RealMaterial.FENCE_GATE,
			RealMaterial.WOOD_STAIRS,
			RealMaterial.TRAP_DOOR,
			RealMaterial.WOOD_PLATE,
			RealMaterial.SIGN,
			
			RealMaterial.SPRUCE_DOOR_ITEM,
			RealMaterial.SPRUCE_FENCE,
			RealMaterial.SPRUCE_FENCE_GATE,
			RealMaterial.SPRUCE_WOOD_STAIRS,
			
			RealMaterial.BIRCH_DOOR_ITEM,
			RealMaterial.BIRCH_FENCE,
			RealMaterial.BIRCH_FENCE_GATE,
			RealMaterial.BIRCH_WOOD_STAIRS,
			
			RealMaterial.JUNGLE_DOOR_ITEM,
			RealMaterial.JUNGLE_FENCE,
			RealMaterial.JUNGLE_FENCE_GATE,
			RealMaterial.JUNGLE_WOOD_STAIRS,
			
			RealMaterial.ACACIA_DOOR_ITEM,
			RealMaterial.ACACIA_FENCE,
			RealMaterial.ACACIA_FENCE_GATE,
			RealMaterial.ACACIA_STAIRS,
			
			RealMaterial.DARK_OAK_DOOR_ITEM,
			RealMaterial.DARK_OAK_FENCE,
			RealMaterial.DARK_OAK_FENCE_GATE,
			RealMaterial.DARK_OAK_STAIRS);
	
	public final static String tagRandomMaterials_QuaryChests = "Random_Materials_For_Quary_Chests";
	public RealMaterialList itemsRandomMaterials_QuaryChests = createList(tagRandomMaterials_QuaryChests,
			RealMaterial.STONE_SPADE, 
			RealMaterial.STONE_PICKAXE,
			RealMaterial.IRON_SPADE, 
			RealMaterial.IRON_PICKAXE,
			
			RealMaterial.IRON_HELMET, 

			RealMaterial.FLINT_AND_STEEL,
			RealMaterial.TORCH,
			RealMaterial.BUCKET,

			RealMaterial.COOKED_BEEF,
			RealMaterial.COOKED_CHICKEN);

	public final static String tagRandomMaterials_QuaryOutputChests = "Random_Materials_For_Quary_Output_Chests";
	public RealMaterialList itemsRandomMaterials_QuaryOutputChests = createList(tagRandomMaterials_QuaryOutputChests,
			RealMaterial.IRON_INGOT, // easy but stupid way to increase odds of some of these happening
			RealMaterial.IRON_INGOT,
			RealMaterial.IRON_INGOT,
			RealMaterial.IRON_INGOT,
			RealMaterial.IRON_INGOT,
			RealMaterial.IRON_INGOT,
			RealMaterial.IRON_INGOT,
			RealMaterial.COAL,
			RealMaterial.COAL,
			RealMaterial.COAL,
			RealMaterial.COAL,
			RealMaterial.COAL,
			RealMaterial.COAL,
			RealMaterial.COAL,
			RealMaterial.GOLD_INGOT,
			RealMaterial.GOLD_INGOT,
			RealMaterial.GOLD_INGOT,
			RealMaterial.REDSTONE,
			RealMaterial.REDSTONE,
			RealMaterial.REDSTONE,
			RealMaterial.DIAMOND,
			RealMaterial.EMERALD);
	
	
	public final static String tagSelectMaterial_BuildingWalls = "Materials_For_BuildingWalls";
	public RealMaterialList itemsSelectMaterial_BuildingWalls = createList(tagSelectMaterial_BuildingWalls,
			RealMaterial.COBBLESTONE,
			RealMaterial.SAND,
			RealMaterial.GRAVEL,
			RealMaterial.WOOD,
			RealMaterial.SANDSTONE,
			RealMaterial.WOOL,
			RealMaterial.DOUBLE_STEP,
			RealMaterial.BRICK,
			RealMaterial.MOSSY_COBBLESTONE,
			RealMaterial.CLAY,
			RealMaterial.NETHERRACK,
			RealMaterial.SOUL_SAND,
			RealMaterial.SMOOTH_BRICK,
			RealMaterial.NETHER_BRICK,
			RealMaterial.QUARTZ_BLOCK,
			RealMaterial.HARD_CLAY,
			RealMaterial.STAINED_CLAY,
			RealMaterial.COAL_BLOCK,
			RealMaterial.ENDER_STONE,
			RealMaterial.STONE,
			RealMaterial.RED_SANDSTONE,
			RealMaterial.PRISMARINE,
			RealMaterial.PURPUR_BLOCK,
			RealMaterial.PURPUR_PILLAR,
			RealMaterial.END_BRICKS,
			RealMaterial.DOUBLE_STONE_SLAB2);

	public final static String tagSelectMaterial_BuildingFoundation = "Materials_For_BuildingFoundation";
	public RealMaterialList itemsSelectMaterial_BuildingFoundation = createList(tagSelectMaterial_BuildingFoundation,
			RealMaterial.COBBLESTONE,
			RealMaterial.WOOD,
			RealMaterial.SANDSTONE,
			RealMaterial.WOOL,
			RealMaterial.DOUBLE_STEP,
			RealMaterial.BRICK,
			RealMaterial.MOSSY_COBBLESTONE,
			RealMaterial.CLAY,
			RealMaterial.NETHERRACK,
//			RealMaterial.SOUL_SAND,
			RealMaterial.SMOOTH_BRICK,
			RealMaterial.NETHER_BRICK,
			RealMaterial.QUARTZ_BLOCK,
			RealMaterial.HARD_CLAY,
			RealMaterial.STAINED_CLAY,
			RealMaterial.COAL_BLOCK,
			RealMaterial.ENDER_STONE,
			RealMaterial.STONE,
			RealMaterial.RED_SANDSTONE,
			RealMaterial.PRISMARINE,
			RealMaterial.PURPUR_BLOCK,
			RealMaterial.PURPUR_PILLAR,
			RealMaterial.END_BRICKS,
			RealMaterial.DOUBLE_STONE_SLAB2);

	public final static String tagSelectMaterial_BuildingCeilings = "Materials_For_BuildingCeilings";
	public RealMaterialList itemsSelectMaterial_BuildingCeilings = createList(tagSelectMaterial_BuildingCeilings,
			RealMaterial.COBBLESTONE,
			RealMaterial.WOOD,
			RealMaterial.SANDSTONE,
			RealMaterial.WOOL,
			RealMaterial.DOUBLE_STEP,
			RealMaterial.BRICK,
			RealMaterial.MOSSY_COBBLESTONE,
			RealMaterial.CLAY,
			RealMaterial.NETHERRACK,
//			RealMaterial.SOUL_SAND,
			RealMaterial.SMOOTH_BRICK,
			RealMaterial.NETHER_BRICK,
			RealMaterial.QUARTZ_BLOCK,
			RealMaterial.HARD_CLAY,
			RealMaterial.STAINED_CLAY,
			RealMaterial.COAL_BLOCK,
			RealMaterial.ENDER_STONE,
			RealMaterial.STONE,
			RealMaterial.RED_SANDSTONE,
			RealMaterial.PRISMARINE,
			RealMaterial.PURPUR_BLOCK,
			RealMaterial.PURPUR_PILLAR,
			RealMaterial.END_BRICKS,
			RealMaterial.DOUBLE_STONE_SLAB2);

	public final static String tagSelectMaterial_BuildingRoofs = "Materials_For_BuildingRoofs";
	public RealMaterialList itemsSelectMaterial_BuildingRoofs = createList(tagSelectMaterial_BuildingRoofs,
			RealMaterial.COBBLESTONE,
			RealMaterial.WOOD,
			RealMaterial.SANDSTONE,
			RealMaterial.WOOL,
			RealMaterial.DOUBLE_STEP,
			RealMaterial.BRICK,
			RealMaterial.MOSSY_COBBLESTONE,
			RealMaterial.CLAY,
			RealMaterial.NETHERRACK,
//			RealMaterial.SOUL_SAND,
			RealMaterial.SMOOTH_BRICK,
			RealMaterial.NETHER_BRICK,
			RealMaterial.QUARTZ_BLOCK,
			RealMaterial.HARD_CLAY,
			RealMaterial.STAINED_CLAY,
			RealMaterial.COAL_BLOCK,
			RealMaterial.ENDER_STONE,
			RealMaterial.STONE,
			RealMaterial.RED_SANDSTONE,
			RealMaterial.PRISMARINE,
			RealMaterial.PURPUR_BLOCK,
			RealMaterial.PURPUR_PILLAR,
			RealMaterial.END_BRICKS,
			RealMaterial.DOUBLE_STONE_SLAB2);

	public final static String tagSelectMaterial_UnfinishedBuildings = "Materials_For_UnfinishedBuildings";
	public RealMaterialList itemsSelectMaterial_UnfinishedBuildings = createList(tagSelectMaterial_UnfinishedBuildings,
			RealMaterial.CLAY,
			RealMaterial.CLAY,
			RealMaterial.HARD_CLAY,
			RealMaterial.HARD_CLAY,
			RealMaterial.STAINED_CLAY,
			RealMaterial.STAINED_CLAY,
			RealMaterial.STONE,
			RealMaterial.STONE,
			RealMaterial.WOOD);

	public final static String tagSelectMaterial_GovernmentWalls = "Materials_For_GovernmentWalls";
	public RealMaterialList itemsSelectMaterial_GovernmentWalls = createList(tagSelectMaterial_GovernmentWalls,
			RealMaterial.QUARTZ_BLOCK,
			RealMaterial.WOOL);
	
	public final static String tagSelectMaterial_GovernmentFoundations = "Materials_For_GovernmentFoundations";
	public RealMaterialList itemsSelectMaterial_GovernmentFoundations = createList(tagSelectMaterial_GovernmentFoundations,
			RealMaterial.QUARTZ_BLOCK,
			RealMaterial.WOOL);
	
	public final static String tagSelectMaterial_GovernmentCeilings = "Materials_For_GovernmentCeilings";
	public RealMaterialList itemsSelectMaterial_GovernmentCeilings = createList(tagSelectMaterial_GovernmentCeilings,
			RealMaterial.QUARTZ_BLOCK,
			RealMaterial.WOOL);
	
	public final static String tagSelectMaterial_HouseWalls = "Materials_For_HouseWalls";
	public RealMaterialList itemsSelectMaterial_HouseWalls = createList(tagSelectMaterial_HouseWalls,
			RealMaterial.COBBLESTONE,
			RealMaterial.MOSSY_COBBLESTONE,
			RealMaterial.STONE,
			RealMaterial.SMOOTH_BRICK,
			RealMaterial.SANDSTONE,
			RealMaterial.RED_SANDSTONE,
			RealMaterial.NETHER_BRICK,
			RealMaterial.BRICK,
			RealMaterial.CLAY,
			RealMaterial.PRISMARINE,
			RealMaterial.PURPUR_BLOCK,
			RealMaterial.END_BRICKS,
			RealMaterial.WOOD);
	
	public final static String tagSelectMaterial_HouseFloors = "Materials_For_HouseFloors";
	public RealMaterialList itemsSelectMaterial_HouseFloors = createList(tagSelectMaterial_HouseFloors,
			RealMaterial.COBBLESTONE,
			RealMaterial.STONE,
			RealMaterial.WOOL,
			RealMaterial.WOOD);
	
	public final static String tagSelectMaterial_HouseCeilings = "Materials_For_HouseCeilings";
	public RealMaterialList itemsSelectMaterial_HouseCeilings = createList(tagSelectMaterial_HouseCeilings,
			RealMaterial.COBBLESTONE,
			RealMaterial.STONE,
			RealMaterial.SMOOTH_BRICK,
			RealMaterial.SANDSTONE,
			RealMaterial.RED_SANDSTONE,
			RealMaterial.WOOD);
	
	public final static String tagSelectMaterial_HouseRoofs = "Materials_For_HouseRoofs";
	public RealMaterialList itemsSelectMaterial_HouseRoofs = createList(tagSelectMaterial_HouseRoofs,
			RealMaterial.COBBLESTONE,
			RealMaterial.MOSSY_COBBLESTONE,
			RealMaterial.STONE,
			RealMaterial.SMOOTH_BRICK,
			RealMaterial.SANDSTONE,
			RealMaterial.RED_SANDSTONE,
			RealMaterial.WOOD);
	
	public final static String tagSelectMaterial_ShackWalls = "Materials_For_ShackWalls";
	public RealMaterialList itemsSelectMaterial_ShackWalls = createList(tagSelectMaterial_ShackWalls,
			RealMaterial.WOOD,
			RealMaterial.WOOD,
			RealMaterial.WOOD,
			RealMaterial.WOOD,
			RealMaterial.MOSSY_COBBLESTONE,
			RealMaterial.RED_SANDSTONE,
			RealMaterial.NETHER_BRICK,
			RealMaterial.BRICK);
	
	public final static String tagSelectMaterial_ShackRoofs = "Materials_For_ShackRoofs";
	public RealMaterialList itemsSelectMaterial_ShackRoofs = createList(tagSelectMaterial_ShackRoofs,
			RealMaterial.WOOD,
			RealMaterial.DOUBLE_STEP,
			RealMaterial.DOUBLE_STONE_SLAB2);
	
	public final static String tagSelectMaterial_ShedWalls = "Materials_For_ShedWalls";
	public RealMaterialList itemsSelectMaterial_ShedWalls = createList(tagSelectMaterial_ShedWalls,
			RealMaterial.STONE,
			RealMaterial.SANDSTONE,
			RealMaterial.RED_SANDSTONE,
			RealMaterial.WOOD,
			RealMaterial.COBBLESTONE,
			RealMaterial.BRICK,
			RealMaterial.SMOOTH_BRICK);
	
	public final static String tagSelectMaterial_ShedRoofs = "Materials_For_ShedRoofs";
	public RealMaterialList itemsSelectMaterial_ShedRoofs = createList(tagSelectMaterial_ShedRoofs,
			RealMaterial.STEP,
			RealMaterial.WOOD_STEP);

	public final static String tagSelectMaterial_StoneWorksPiles = "Materials_For_QuaryPiles";
	public RealMaterialList itemsSelectMaterial_QuaryPiles = createList(tagSelectMaterial_StoneWorksPiles,
			RealMaterial.GRAVEL, // easy but stupid way to increase odds of some of these happening
			RealMaterial.GRAVEL,
			RealMaterial.GRAVEL,
			RealMaterial.GRAVEL,
			RealMaterial.GRAVEL,
			RealMaterial.COAL_ORE,
			RealMaterial.COAL_ORE,
			RealMaterial.COAL_ORE,
			RealMaterial.COAL_ORE,
			RealMaterial.IRON_ORE,
			RealMaterial.IRON_ORE,
			RealMaterial.IRON_ORE,
			RealMaterial.GOLD_ORE,
			RealMaterial.LAPIS_ORE,
			RealMaterial.REDSTONE_ORE,
			RealMaterial.DIAMOND_ORE,
			RealMaterial.EMERALD_ORE);
	
	public final static String tagSelectMaterial_Castles = "Materials_For_Castles";
	public RealMaterialList itemsSelectMaterial_Castles = createList(tagSelectMaterial_Castles,
//			RealMaterial.STONE,
			RealMaterial.COBBLESTONE,
			RealMaterial.MOSSY_COBBLESTONE,
//			RealMaterial.ENDER_STONE,
//			RealMaterial.DOUBLE_STEP,
//			RealMaterial.DOUBLE_STONE_SLAB2,
			RealMaterial.SMOOTH_BRICK);

	public final static String tagSelectMaterial_WaterTowers = "Materials_For_WaterTowers";
	public RealMaterialList itemsSelectMaterial_WaterTowers = createList(tagSelectMaterial_WaterTowers,
			RealMaterial.CLAY,
			RealMaterial.STAINED_CLAY,
			RealMaterial.STAINED_CLAY);

	public final static String tagSelectMaterial_FactoryInsides = "Materials_For_FactoryInsides";
	public RealMaterialList itemsSelectMaterial_FactoryInsides = createList(tagSelectMaterial_FactoryInsides,
			RealMaterial.STONE,
			RealMaterial.SMOOTH_BRICK,
			RealMaterial.DOUBLE_STEP,
			RealMaterial.DOUBLE_STONE_SLAB2,
			RealMaterial.QUARTZ_BLOCK,
			RealMaterial.CLAY,
			RealMaterial.STAINED_CLAY,
			RealMaterial.STONE);
	
	public final static String tagSelectMaterial_FactoryTanks = "Materials_For_FactoryTanks";
	public RealMaterialList itemsSelectMaterial_FactoryTanks = createList(tagSelectMaterial_FactoryTanks,
			RealMaterial.STATIONARY_LAVA,
			RealMaterial.ICE,
			RealMaterial.PACKED_ICE,
			RealMaterial.SNOW_BLOCK,
			RealMaterial.SLIME_BLOCK,
			RealMaterial.COAL_BLOCK,
			RealMaterial.SAND,
			RealMaterial.GLASS,
			RealMaterial.STAINED_CLAY,
			RealMaterial.STAINED_GLASS,
			RealMaterial.STATIONARY_WATER);

	public final static String tagSelectMaterial_BunkerBuildings = "Materials_For_BunkerBuildings";
	public RealMaterialList itemsSelectMaterial_BunkerBuildings = createList(tagSelectMaterial_BunkerBuildings,
			RealMaterial.CLAY,
			RealMaterial.STAINED_CLAY);

	public final static String tagSelectMaterial_BunkerPlatforms = "Materials_For_BunkerPlatforms";
	public RealMaterialList itemsSelectMaterial_BunkerPlatforms = createList(tagSelectMaterial_BunkerPlatforms,
			RealMaterial.CLAY,
			RealMaterial.QUARTZ_BLOCK);

	public final static String tagSelectMaterial_BunkerBilge = "Materials_For_BunkerBilge";
	public RealMaterialList itemsSelectMaterial_BunkerBilge = createList(tagSelectMaterial_BunkerBilge,
			RealMaterial.AIR,
			RealMaterial.STATIONARY_LAVA,
			RealMaterial.STATIONARY_WATER,
			RealMaterial.ICE,
			RealMaterial.PACKED_ICE);

	public final static String tagSelectMaterial_BunkerTanks = "Materials_For_BunkerTanks";
	public RealMaterialList itemsSelectMaterial_BunkerTanks = createList(tagSelectMaterial_BunkerTanks,
			RealMaterial.STATIONARY_LAVA,
			RealMaterial.PACKED_ICE,
			RealMaterial.SNOW_BLOCK,
			RealMaterial.SPONGE,
			RealMaterial.REDSTONE_BLOCK,
			RealMaterial.COAL_BLOCK,
			RealMaterial.HARD_CLAY,
			RealMaterial.ENDER_STONE,
			RealMaterial.EMERALD_BLOCK,
			RealMaterial.STATIONARY_WATER);

	public final static String tagSelectMaterial_AstralTowerLight = "Materials_For_AstralTowerLight";
	public RealMaterialList itemsSelectMaterial_AstralTowerLight = createList(tagSelectMaterial_AstralTowerLight,
			RealMaterial.ENDER_STONE);

	public final static String tagSelectMaterial_AstralTowerDark = "Materials_For_AstralTowerDark";
	public RealMaterialList itemsSelectMaterial_AstralTowerDark = createList(tagSelectMaterial_AstralTowerDark,
			RealMaterial.OBSIDIAN);

	public final static String tagSelectMaterial_AstralTowerOres = "Materials_For_AstralTowerOres";
	public RealMaterialList itemsSelectMaterial_AstralTowerOres = createList(tagSelectMaterial_AstralTowerOres,
			RealMaterial.LAVA, 
			RealMaterial.WATER, 
			RealMaterial.MONSTER_EGG,
			RealMaterial.COAL_ORE, 
			RealMaterial.DIAMOND_ORE, 
			RealMaterial.EMERALD_ORE, 
			RealMaterial.GOLD_ORE,
			RealMaterial.IRON_ORE, 
			RealMaterial.LAPIS_ORE, 
			RealMaterial.QUARTZ_ORE, 
			RealMaterial.REDSTONE_ORE);

	public final static String tagSelectMaterial_AstralTowerHalls = "Materials_For_AstralTowerHalls";
	public RealMaterialList itemsSelectMaterial_AstralTowerHalls = createList(tagSelectMaterial_AstralTowerHalls,
			RealMaterial.OBSIDIAN, 
			RealMaterial.STONE, 
			RealMaterial.BRICK, 
			RealMaterial.COBBLESTONE, 
			RealMaterial.SMOOTH_BRICK, 
			RealMaterial.MOSSY_COBBLESTONE);

	public final static String tagSelectMaterial_AstralTowerTrim = "Materials_For_AstralTowerTrim";
	public RealMaterialList itemsSelectMaterial_AstralTowerTrim = createList(tagSelectMaterial_AstralTowerTrim,
			RealMaterial.AIR, 
			RealMaterial.GLOWSTONE);

	public final static String tagSelectMaterial_AstralCubeOres = "Materials_For_AstralCubeOres";
	public RealMaterialList itemsSelectMaterial_AstralCubeOres = createList(tagSelectMaterial_AstralCubeOres,
			RealMaterial.DIRT, 
			RealMaterial.STONE, 
			RealMaterial.COBBLESTONE, 
			RealMaterial.WOOD, 
			RealMaterial.IRON_BLOCK, 
			RealMaterial.COAL_BLOCK, 
			RealMaterial.DIAMOND_BLOCK,
			RealMaterial.REDSTONE_BLOCK, 
			RealMaterial.QUARTZ_BLOCK, 
			RealMaterial.MONSTER_EGG);

	
	public final static String tagRealMaterialListFor_MazeWalls = "Materials_List_For_MazeWalls";
	public RealMaterialList itemsRealMaterialListFor_MazeWalls = createList(tagRealMaterialListFor_MazeWalls,
			RealMaterial.OBSIDIAN, // Walls
			RealMaterial.OBSIDIAN);// Underlayment

	public final static String tagRealMaterialListFor_Roads = "Materials_List_For_Roads";
	public RealMaterialList itemsRealMaterialListFor_Roads = createList(tagRealMaterialListFor_Roads,
			RealMaterial.STAINED_CLAY, // Pavement
			RealMaterial.QUARTZ_BLOCK, // Lines
			RealMaterial.STEP,		   // Sidewalks
			RealMaterial.DIRT,		   // Dirt roads
			RealMaterial.DIRT);		   // Dirt sidewalks
	
	public final static String tagRealMaterialListFor_NormalOres = "Materials_List_For_NormalOres";
	public RealMaterialList itemsRealMaterialListFor_NormalOres = createList(tagRealMaterialListFor_NormalOres,
			RealMaterial.WATER, 		// liquid ore
			RealMaterial.LAVA,			// alt liquid ore
			RealMaterial.GRAVEL,		// gravel ore
			RealMaterial.COAL_ORE,		// most frequent ore
			RealMaterial.IRON_ORE,
			
			RealMaterial.GOLD_ORE,
			RealMaterial.LAPIS_ORE,
			RealMaterial.REDSTONE_ORE,
			RealMaterial.DIAMOND_ORE,
			RealMaterial.EMERALD_ORE);	// least frequent ore

	public final static String tagRealMaterialListFor_NetherOres = "Materials_List_For_NetherOres";
	public RealMaterialList itemsRealMaterialListFor_NetherOres = createList(tagRealMaterialListFor_NetherOres,
			RealMaterial.LAVA,			// liquid ore
			RealMaterial.LAVA,			// alt liquid ore
			RealMaterial.SOUL_SAND,		// SHOULD BE MAGMA // gravel ore 
			RealMaterial.SOUL_SAND,		// most frequent ore
			RealMaterial.GLOWSTONE,

			RealMaterial.GLOWSTONE,
			RealMaterial.QUARTZ_ORE,
			RealMaterial.SOUL_SAND,		// SHOULD BE MAGMA 
			RealMaterial.SOUL_SAND,
			RealMaterial.OBSIDIAN);		// least frequent ore

	public final static String tagRealMaterialListFor_TheEndOres = "Materials_List_For_TheEndOres";
	public RealMaterialList itemsRealMaterialListFor_TheEndOres = createList(tagRealMaterialListFor_TheEndOres,
			RealMaterial.WATER, 		// liquid ore
			RealMaterial.LAVA,			// alt liquid ore
			RealMaterial.GRAVEL,		// gravel ore
			RealMaterial.QUARTZ_BLOCK,	// most frequent ore
			RealMaterial.GLOWSTONE,

			RealMaterial.PURPUR_BLOCK,
			RealMaterial.GOLD_ORE,
			RealMaterial.LAPIS_ORE,
			RealMaterial.DIAMOND_ORE,
			RealMaterial.OBSIDIAN);		// least frequent ore
	
	private List<RealMaterialList> listOfLists;
	
	public MaterialProvider(CityWorldGenerator generator) {
	}
	
	private RealMaterialList createList(String name, RealMaterial ... materials) {
		
		// create the list and add all of the goodies
		RealMaterialList list = new RealMaterialList(name, materials);
		
		// add it to the big list so we can generically remember it
		if (listOfLists == null)
			listOfLists = new ArrayList<RealMaterialList>();
		listOfLists.add(list);
		
		// return it so we can specifically remember it
		return list;
	}
	
	public void read(CityWorldGenerator generator, ConfigurationSection section) {
		for (RealMaterialList RealMaterialList : listOfLists) {
			RealMaterialList.read(generator, section);
		}
	}

	public void write(CityWorldGenerator generator, ConfigurationSection section) {
		for (RealMaterialList RealMaterialList : listOfLists) {
			RealMaterialList.write(generator, section);
		}
	}

}
