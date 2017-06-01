package me.daddychurchill.CityWorld.Plugins;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.MaterialList;

public class MaterialProvider {

	public final static String tagRandomMaterials_BunkerChests = "Random_Materials_For_Bunker_Chests";
	public MaterialList itemsRandomMaterials_BunkerChests = createList(tagRandomMaterials_BunkerChests, 
			Material.IRON_PICKAXE, 
			Material.IRON_SWORD,
			Material.IRON_SPADE,

			Material.IRON_BOOTS,
			Material.IRON_CHESTPLATE,
			Material.IRON_HELMET,
			Material.IRON_LEGGINGS,
			Material.CHAINMAIL_BOOTS,
			Material.CHAINMAIL_CHESTPLATE,
			Material.CHAINMAIL_HELMET,
			Material.CHAINMAIL_LEGGINGS,
			
			Material.TORCH,
			Material.BUCKET,
			Material.WATER_BUCKET,
			Material.LAVA_BUCKET,
			
			Material.REDSTONE);
	
	public final static String tagRandomMaterials_MineChests = "Random_Materials_For_Mine_Chests";
	public MaterialList itemsRandomMaterials_MineChests = createList(tagRandomMaterials_MineChests, 
			Material.STONE_PICKAXE,
			Material.STONE_SWORD,
			Material.STONE_SPADE,
			Material.IRON_PICKAXE, 
			Material.IRON_SWORD,
			Material.IRON_SPADE,

			Material.LEATHER_BOOTS,
			Material.LEATHER_CHESTPLATE,
			Material.LEATHER_HELMET,
			Material.LEATHER_LEGGINGS,
			
			Material.TORCH,	
			Material.COMPASS,
			Material.WATCH,
			Material.FLINT,
			Material.FLINT_AND_STEEL, 
			Material.BUCKET,
			Material.WATER_BUCKET,
			Material.LAVA_BUCKET,
			Material.SULPHUR,
			Material.SAND,

			Material.BOOK,
			Material.COAL,
			Material.DIAMOND,
			Material.IRON_INGOT,
			Material.GOLD_INGOT,
			
			Material.APPLE,
			Material.ROTTEN_FLESH);
	
	public final static String tagRandomMaterials_SewerChests = "Random_Materials_For_Sewer_Chests";
	public MaterialList itemsRandomMaterials_SewerChests = createList(tagRandomMaterials_SewerChests, 
			Material.WOOD_PICKAXE, 
			Material.WOOD_SWORD,
			Material.WOOD_SPADE,
			Material.STONE_PICKAXE,	
			Material.STONE_SWORD,
			Material.STONE_SPADE,
			
			Material.LEATHER_BOOTS,
			Material.LEATHER_CHESTPLATE,
			Material.LEATHER_HELMET,
			Material.LEATHER_LEGGINGS,
			
			Material.TORCH,	
			Material.COMPASS,
			Material.WATCH,
			Material.FLINT_AND_STEEL,
			Material.FIREWORK,

			Material.COAL,

			Material.APPLE,
			Material.ROTTEN_FLESH);
	
	public final static String tagRandomMaterials_BuildingChests = "Random_Materials_For_Building_Chests";
	public MaterialList itemsRandomMaterials_BuildingChests = createList(tagRandomMaterials_BuildingChests, 
			
			Material.STRING,
			Material.TORCH,	
			Material.COMPASS,
			Material.WATCH,
			Material.SHEARS,
			
			Material.BOWL,
			Material.BUCKET,
			Material.GLASS_BOTTLE,
			Material.FLOWER_POT,
			
			Material.PAINTING,
			Material.ITEM_FRAME,
			Material.BANNER,
			
			Material.FEATHER,
			Material.PAPER,
			Material.BOOK,
			Material.BOOK_AND_QUILL,
			Material.EMPTY_MAP,
			Material.NAME_TAG,
			
			Material.ROTTEN_FLESH);
	
	public final static String tagRandomMaterials_WarehouseChests = "Random_Materials_For_Warehouse_Chests";
	public MaterialList itemsRandomMaterials_WarehouseChests = createList(tagRandomMaterials_WarehouseChests, 
			
			Material.FLINT_AND_STEEL,
			
			Material.RABBIT_FOOT,
			Material.RABBIT_HIDE,
			Material.LEATHER,

			Material.LEATHER_BOOTS,
			Material.LEATHER_CHESTPLATE,
			Material.LEATHER_HELMET,
			Material.LEATHER_LEGGINGS,
			Material.CHAINMAIL_BOOTS,
			Material.CHAINMAIL_CHESTPLATE,
			Material.CHAINMAIL_HELMET,
			Material.CHAINMAIL_LEGGINGS,
			Material.IRON_BOOTS,
			Material.IRON_CHESTPLATE,
			Material.IRON_HELMET,
			Material.IRON_LEGGINGS,
			
			Material.STONE_HOE,
			Material.STONE_AXE,
			Material.STONE_PICKAXE,
			Material.STONE_SWORD,
			
			Material.LEASH,
			Material.CARROT_STICK,
			Material.FISHING_ROD,
			Material.TOTEM,
			
			Material.GREEN_RECORD,
			Material.GOLD_RECORD,
			Material.RECORD_3,
			Material.RECORD_4,
			Material.RECORD_5,
			Material.RECORD_6,
			Material.RECORD_7,
			Material.RECORD_8,
			Material.RECORD_9,
			Material.RECORD_10,
			Material.RECORD_11,
			Material.RECORD_12,
			
			Material.ROTTEN_FLESH);
	
	public final static String tagRandomMaterials_FoodChests = "Random_Materials_For_Food_Chests";
	public MaterialList itemsRandomMaterials_FoodChests = createList(tagRandomMaterials_FoodChests, 
			
			Material.SUGAR,
			Material.CAKE,
			Material.COOKIE,
			Material.EGG,
			Material.APPLE,
			Material.MELON,
			Material.SPECKLED_MELON,
			Material.CARROT,
			Material.BREAD,
			
			Material.RAW_BEEF,
			Material.RAW_CHICKEN,
			Material.RAW_FISH,
			Material.MUTTON,
			Material.RABBIT,
			Material.POTATO,
			Material.POISONOUS_POTATO,
			Material.PUMPKIN,
			Material.BROWN_MUSHROOM,
			Material.RED_MUSHROOM,
			Material.BEETROOT,
			
			Material.COOKED_BEEF,
			Material.COOKED_CHICKEN,
			Material.COOKED_FISH,
			Material.COOKED_MUTTON,
			Material.COOKED_RABBIT,
			Material.BAKED_POTATO,
			Material.PUMPKIN_PIE,
			Material.MUSHROOM_SOUP,
			Material.BEETROOT_SOUP,
			
			Material.GOLDEN_CARROT,
			Material.GOLDEN_APPLE,
			Material.ROTTEN_FLESH);
	
	public final static String tagRandomMaterials_StorageShedChests = "Random_Materials_For_Storage_Shed_Chests";
	public MaterialList itemsRandomMaterials_StorageShedChests = createList(tagRandomMaterials_StorageShedChests,
			Material.WOOD_AXE, 
			Material.WOOD_SPADE, 
			Material.WOOD_PICKAXE, 
			Material.IRON_AXE, 
			Material.IRON_SPADE, 
			Material.IRON_PICKAXE,	
			
			Material.IRON_HELMET, 

			Material.TORCH,	
			Material.PAPER,
			Material.BOOK,
			
			Material.COOKED_MUTTON,
			Material.BOWL,
			Material.MUSHROOM_SOUP);

	public final static String tagRandomMaterials_FarmChests = "Random_Materials_For_Farm_Chests";
	public MaterialList itemsRandomMaterials_FarmChests = createList(tagRandomMaterials_FarmChests,
			Material.WOOD_SPADE, 
			Material.WOOD_PICKAXE, 
			Material.WOOD_HOE,
			Material.IRON_SPADE, 
			Material.IRON_PICKAXE,	
			Material.IRON_HOE, 
			
			Material.LEATHER_BOOTS,
			Material.LEATHER_CHESTPLATE,
			Material.LEATHER_HELMET,
			Material.LEATHER_LEGGINGS,
			
			Material.BOW,
			Material.ARROW,
			
			Material.PAPER,
			Material.BOOK,
			Material.TORCH,
			Material.FISHING_ROD,
			Material.SHEARS,
			Material.BUCKET,
			Material.WATER_BUCKET,
			Material.MILK_BUCKET,
			Material.BONE,
			Material.BOWL,
			
			Material.COOKIE,
			Material.SUGAR,
			Material.GRILLED_PORK,
			Material.COOKED_BEEF,
			Material.COOKED_CHICKEN,
			Material.COOKED_MUTTON,
			Material.COOKED_RABBIT,
			Material.BAKED_POTATO,
			Material.MELON,
			Material.PUMPKIN_PIE,
			Material.MUSHROOM_SOUP);

	public final static String tagRandomMaterials_FarmOutputChests = "Random_Materials_For_Farm_Output_Chests";
	public MaterialList itemsRandomMaterials_FarmOutputChests = createList(tagRandomMaterials_FarmOutputChests,
			Material.LEATHER,
			Material.PORK,
			Material.RAW_BEEF,
			Material.RAW_CHICKEN,
			Material.RABBIT,
			Material.MUTTON,
			Material.SEEDS,
			Material.WOOL,
			Material.POTATO_ITEM,
			Material.CARROT_ITEM,
			Material.PUMPKIN_SEEDS,
			Material.MELON_SEEDS,
			Material.CARROT_ITEM,
			Material.POTATO_ITEM,
			Material.SUGAR_CANE,
			Material.APPLE);
	
	public final static String tagRandomMaterials_LumberChests = "Random_Materials_For_Lumber_Chests";
	public MaterialList itemsRandomMaterials_LumberChests = createList(tagRandomMaterials_LumberChests,
			Material.WOOD_AXE, 
			Material.WOOD_SPADE, 
			Material.WOOD_PICKAXE,
			Material.IRON_AXE, 
			Material.IRON_SPADE, 
			Material.IRON_PICKAXE,	
			
			Material.IRON_HELMET, 

			Material.FLINT_AND_STEEL,
			Material.COAL,
			Material.TORCH,	
			
			Material.GRILLED_PORK,
			Material.COOKED_CHICKEN,
			Material.COOKIE);
	
	public final static String tagRandomMaterials_LumberOutputChests = "Random_Materials_For_Lumber_Output_Chests";
	public MaterialList itemsRandomMaterials_LumberOutputChests = createList(tagRandomMaterials_LumberOutputChests,
			Material.WOOD, // simple but cheesy way to increase the odds for this one
			Material.WOOD,
			Material.WOOD,
			Material.WOOD,
			Material.WOOD,
			Material.WOOD,
			Material.WOOD,
			Material.WOOD,
			Material.WOOD,
			Material.WOOD,
			Material.LOG,
			Material.LOG,
			Material.LOG,
			Material.STICK,
			Material.STICK,
			Material.STICK,

			Material.WOOD_STEP,
			Material.WOODEN_DOOR,
			Material.FENCE,
			Material.FENCE_GATE,
			Material.WOOD_STAIRS,
			Material.TRAP_DOOR,
			Material.WOOD_PLATE,
			Material.SIGN,
			
			Material.SPRUCE_DOOR_ITEM,
			Material.SPRUCE_FENCE,
			Material.SPRUCE_FENCE_GATE,
			Material.SPRUCE_WOOD_STAIRS,
			
			Material.BIRCH_DOOR_ITEM,
			Material.BIRCH_FENCE,
			Material.BIRCH_FENCE_GATE,
			Material.BIRCH_WOOD_STAIRS,
			
			Material.JUNGLE_DOOR_ITEM,
			Material.JUNGLE_FENCE,
			Material.JUNGLE_FENCE_GATE,
			Material.JUNGLE_WOOD_STAIRS,
			
			Material.ACACIA_DOOR_ITEM,
			Material.ACACIA_FENCE,
			Material.ACACIA_FENCE_GATE,
			Material.ACACIA_STAIRS,
			
			Material.DARK_OAK_DOOR_ITEM,
			Material.DARK_OAK_FENCE,
			Material.DARK_OAK_FENCE_GATE,
			Material.DARK_OAK_STAIRS);
	
	public final static String tagRandomMaterials_QuaryChests = "Random_Materials_For_Quary_Chests";
	public MaterialList itemsRandomMaterials_QuaryChests = createList(tagRandomMaterials_QuaryChests,
			Material.STONE_SPADE, 
			Material.STONE_PICKAXE,
			Material.IRON_SPADE, 
			Material.IRON_PICKAXE,
			
			Material.IRON_HELMET, 

			Material.FLINT_AND_STEEL,
			Material.TORCH,
			Material.BUCKET,

			Material.COOKED_BEEF,
			Material.COOKED_CHICKEN);

	public final static String tagRandomMaterials_QuaryOutputChests = "Random_Materials_For_Quary_Output_Chests";
	public MaterialList itemsRandomMaterials_QuaryOutputChests = createList(tagRandomMaterials_QuaryOutputChests,
			Material.IRON_INGOT, // easy but stupid way to increase odds of some of these happening
			Material.IRON_INGOT,
			Material.IRON_INGOT,
			Material.IRON_INGOT,
			Material.IRON_INGOT,
			Material.IRON_INGOT,
			Material.IRON_INGOT,
			Material.COAL,
			Material.COAL,
			Material.COAL,
			Material.COAL,
			Material.COAL,
			Material.COAL,
			Material.COAL,
			Material.GOLD_INGOT,
			Material.GOLD_INGOT,
			Material.GOLD_INGOT,
			Material.REDSTONE,
			Material.REDSTONE,
			Material.REDSTONE,
			Material.DIAMOND,
			Material.EMERALD);
	
	
	public final static String tagSelectMaterial_BuildingWalls = "Materials_For_BuildingWalls";
	public MaterialList itemsSelectMaterial_BuildingWalls = createList(tagSelectMaterial_BuildingWalls,
			Material.COBBLESTONE,
			Material.SAND,
			Material.GRAVEL,
			Material.WOOD,
			Material.SANDSTONE,
			Material.WOOL,
			Material.DOUBLE_STEP,
			Material.BRICK,
			Material.MOSSY_COBBLESTONE,
			Material.CLAY,
			Material.NETHERRACK,
			Material.SOUL_SAND,
			Material.SMOOTH_BRICK,
			Material.NETHER_BRICK,
			Material.QUARTZ_BLOCK,
			Material.HARD_CLAY,
			Material.STAINED_CLAY,
			Material.COAL_BLOCK,
			Material.ENDER_STONE,
			Material.STONE,
			Material.RED_SANDSTONE,
			Material.PRISMARINE,
			Material.PURPUR_BLOCK,
			Material.PURPUR_PILLAR,
			Material.END_BRICKS,
			Material.DOUBLE_STONE_SLAB2,
			Material.CONCRETE_POWDER,
			Material.CONCRETE);

	public final static String tagSelectMaterial_BuildingFoundation = "Materials_For_BuildingFoundation";
	public MaterialList itemsSelectMaterial_BuildingFoundation = createList(tagSelectMaterial_BuildingFoundation,
			Material.COBBLESTONE,
			Material.WOOD,
			Material.SANDSTONE,
			Material.WOOL,
			Material.DOUBLE_STEP,
			Material.BRICK,
			Material.MOSSY_COBBLESTONE,
			Material.CLAY,
			Material.NETHERRACK,
//			Material.SOUL_SAND,
			Material.SMOOTH_BRICK,
			Material.NETHER_BRICK,
			Material.QUARTZ_BLOCK,
			Material.HARD_CLAY,
			Material.STAINED_CLAY,
			Material.COAL_BLOCK,
			Material.ENDER_STONE,
			Material.STONE,
			Material.RED_SANDSTONE,
			Material.PRISMARINE,
			Material.PURPUR_BLOCK,
			Material.PURPUR_PILLAR,
			Material.END_BRICKS,
			Material.DOUBLE_STONE_SLAB2,
			Material.CONCRETE);

	public final static String tagSelectMaterial_BuildingCeilings = "Materials_For_BuildingCeilings";
	public MaterialList itemsSelectMaterial_BuildingCeilings = createList(tagSelectMaterial_BuildingCeilings,
			Material.COBBLESTONE,
			Material.WOOD,
			Material.SANDSTONE,
			Material.WOOL,
			Material.DOUBLE_STEP,
			Material.BRICK,
			Material.MOSSY_COBBLESTONE,
			Material.CLAY,
			Material.NETHERRACK,
//			Material.SOUL_SAND,
			Material.SMOOTH_BRICK,
			Material.NETHER_BRICK,
			Material.QUARTZ_BLOCK,
			Material.HARD_CLAY,
			Material.STAINED_CLAY,
			Material.COAL_BLOCK,
			Material.ENDER_STONE,
			Material.STONE,
			Material.RED_SANDSTONE,
			Material.PRISMARINE,
			Material.PURPUR_BLOCK,
			Material.PURPUR_PILLAR,
			Material.END_BRICKS,
			Material.DOUBLE_STONE_SLAB2,
			Material.CONCRETE);

	public final static String tagSelectMaterial_BuildingRoofs = "Materials_For_BuildingRoofs";
	public MaterialList itemsSelectMaterial_BuildingRoofs = createList(tagSelectMaterial_BuildingRoofs,
			Material.COBBLESTONE,
			Material.WOOD,
			Material.SANDSTONE,
			Material.WOOL,
			Material.DOUBLE_STEP,
			Material.BRICK,
			Material.MOSSY_COBBLESTONE,
			Material.CLAY,
			Material.NETHERRACK,
//			Material.SOUL_SAND,
			Material.SMOOTH_BRICK,
			Material.NETHER_BRICK,
			Material.QUARTZ_BLOCK,
			Material.HARD_CLAY,
			Material.STAINED_CLAY,
			Material.COAL_BLOCK,
			Material.ENDER_STONE,
			Material.STONE,
			Material.RED_SANDSTONE,
			Material.PRISMARINE,
			Material.PURPUR_BLOCK,
			Material.PURPUR_PILLAR,
			Material.END_BRICKS,
			Material.DOUBLE_STONE_SLAB2,
			Material.CONCRETE);

	public final static String tagSelectMaterial_UnfinishedBuildings = "Materials_For_UnfinishedBuildings";
	public MaterialList itemsSelectMaterial_UnfinishedBuildings = createList(tagSelectMaterial_UnfinishedBuildings,
			Material.CLAY,
			Material.HARD_CLAY,
			Material.STAINED_CLAY,
			Material.STONE,
			Material.CONCRETE);

	public final static String tagSelectMaterial_GovernmentWalls = "Materials_For_GovernmentWalls";
	public MaterialList itemsSelectMaterial_GovernmentWalls = createList(tagSelectMaterial_GovernmentWalls,
			Material.QUARTZ_BLOCK,
			Material.WOOL);
	
	public final static String tagSelectMaterial_GovernmentFoundations = "Materials_For_GovernmentFoundations";
	public MaterialList itemsSelectMaterial_GovernmentFoundations = createList(tagSelectMaterial_GovernmentFoundations,
			Material.QUARTZ_BLOCK,
			Material.WOOL);
	
	public final static String tagSelectMaterial_GovernmentCeilings = "Materials_For_GovernmentCeilings";
	public MaterialList itemsSelectMaterial_GovernmentCeilings = createList(tagSelectMaterial_GovernmentCeilings,
			Material.QUARTZ_BLOCK,
			Material.WOOL);
	
	public final static String tagSelectMaterial_HouseWalls = "Materials_For_HouseWalls";
	public MaterialList itemsSelectMaterial_HouseWalls = createList(tagSelectMaterial_HouseWalls,
			Material.COBBLESTONE,
			Material.MOSSY_COBBLESTONE,
			Material.STONE,
			Material.SMOOTH_BRICK,
			Material.SANDSTONE,
			Material.RED_SANDSTONE,
			Material.NETHER_BRICK,
			Material.BRICK,
			Material.CLAY,
			Material.PRISMARINE,
			Material.PURPUR_BLOCK,
			Material.END_BRICKS,
			Material.WOOD,
			Material.CONCRETE);
	
	public final static String tagSelectMaterial_HouseFloors = "Materials_For_HouseFloors";
	public MaterialList itemsSelectMaterial_HouseFloors = createList(tagSelectMaterial_HouseFloors,
			Material.COBBLESTONE,
			Material.COBBLESTONE,
			Material.COBBLESTONE,
			Material.STONE,
			Material.STONE,
			Material.STONE,
			Material.WOOL,
			Material.WOOL,
			Material.WOOL,
			Material.WOOD,
			Material.WOOD,
			Material.WOOD,
			Material.BLACK_GLAZED_TERRACOTTA,
			Material.BLUE_GLAZED_TERRACOTTA,
			Material.BROWN_GLAZED_TERRACOTTA,
			Material.CYAN_GLAZED_TERRACOTTA,
			Material.GRAY_GLAZED_TERRACOTTA,
			Material.GREEN_GLAZED_TERRACOTTA,
			Material.LIGHT_BLUE_GLAZED_TERRACOTTA,
			Material.LIME_GLAZED_TERRACOTTA,
			Material.MAGENTA_GLAZED_TERRACOTTA,
			Material.ORANGE_GLAZED_TERRACOTTA,
			Material.PINK_GLAZED_TERRACOTTA,
			Material.PURPLE_GLAZED_TERRACOTTA,
			Material.RED_GLAZED_TERRACOTTA,
			Material.SILVER_GLAZED_TERRACOTTA,
			Material.WHITE_GLAZED_TERRACOTTA,
			Material.YELLOW_GLAZED_TERRACOTTA);
	
	public final static String tagSelectMaterial_HouseCeilings = "Materials_For_HouseCeilings";
	public MaterialList itemsSelectMaterial_HouseCeilings = createList(tagSelectMaterial_HouseCeilings,
			Material.COBBLESTONE,
			Material.STONE,
			Material.SMOOTH_BRICK,
			Material.SANDSTONE,
			Material.RED_SANDSTONE,
			Material.WOOD);
	
	public final static String tagSelectMaterial_HouseRoofs = "Materials_For_HouseRoofs";
	public MaterialList itemsSelectMaterial_HouseRoofs = createList(tagSelectMaterial_HouseRoofs,
			Material.COBBLESTONE,
			Material.MOSSY_COBBLESTONE,
			Material.STONE,
			Material.SMOOTH_BRICK,
			Material.SANDSTONE,
			Material.RED_SANDSTONE,
			Material.WOOD);
	
	public final static String tagSelectMaterial_ShackWalls = "Materials_For_ShackWalls";
	public MaterialList itemsSelectMaterial_ShackWalls = createList(tagSelectMaterial_ShackWalls,
			Material.WOOD,
			Material.WOOD,
			Material.WOOD,
			Material.WOOD,
			Material.MOSSY_COBBLESTONE,
			Material.RED_SANDSTONE,
			Material.NETHER_BRICK,
			Material.BRICK);
	
	public final static String tagSelectMaterial_ShackRoofs = "Materials_For_ShackRoofs";
	public MaterialList itemsSelectMaterial_ShackRoofs = createList(tagSelectMaterial_ShackRoofs,
			Material.WOOD,
			Material.DOUBLE_STEP,
			Material.DOUBLE_STONE_SLAB2);
	
	public final static String tagSelectMaterial_ShedWalls = "Materials_For_ShedWalls";
	public MaterialList itemsSelectMaterial_ShedWalls = createList(tagSelectMaterial_ShedWalls,
			Material.STONE,
			Material.SANDSTONE,
			Material.RED_SANDSTONE,
			Material.WOOD,
			Material.COBBLESTONE,
			Material.BRICK,
			Material.SMOOTH_BRICK);
	
	public final static String tagSelectMaterial_ShedRoofs = "Materials_For_ShedRoofs";
	public MaterialList itemsSelectMaterial_ShedRoofs = createList(tagSelectMaterial_ShedRoofs,
			Material.STEP,
			Material.WOOD_STEP);

	public final static String tagSelectMaterial_StoneWorksPiles = "Materials_For_QuaryPiles";
	public MaterialList itemsSelectMaterial_QuaryPiles = createList(tagSelectMaterial_StoneWorksPiles,
			Material.GRAVEL, // easy but stupid way to increase odds of some of these happening
			Material.GRAVEL,
			Material.GRAVEL,
			Material.GRAVEL,
			Material.GRAVEL,
			Material.CONCRETE_POWDER,
			Material.CONCRETE_POWDER,
			Material.CONCRETE_POWDER,
			Material.COAL_ORE,
			Material.COAL_ORE,
			Material.COAL_ORE,
			Material.COAL_ORE,
			Material.IRON_ORE,
			Material.IRON_ORE,
			Material.IRON_ORE,
			Material.CONCRETE,
			Material.CONCRETE,
			Material.GOLD_ORE,
			Material.LAPIS_ORE,
			Material.REDSTONE_ORE,
			Material.DIAMOND_ORE,
			Material.EMERALD_ORE);
	
	public final static String tagSelectMaterial_Castles = "Materials_For_Castles";
	public MaterialList itemsSelectMaterial_Castles = createList(tagSelectMaterial_Castles,
//			Material.STONE,
			Material.COBBLESTONE,
			Material.MOSSY_COBBLESTONE,
//			Material.ENDER_STONE,
//			Material.DOUBLE_STEP,
//			Material.DOUBLE_STONE_SLAB2,
			Material.SMOOTH_BRICK);

	public final static String tagSelectMaterial_WaterTowers = "Materials_For_WaterTowers";
	public MaterialList itemsSelectMaterial_WaterTowers = createList(tagSelectMaterial_WaterTowers,
			Material.CLAY,
			Material.STAINED_CLAY,
			Material.STAINED_CLAY,
			Material.CONCRETE);

	public final static String tagSelectMaterial_FactoryInsides = "Materials_For_FactoryInsides";
	public MaterialList itemsSelectMaterial_FactoryInsides = createList(tagSelectMaterial_FactoryInsides,
			Material.STONE,
			Material.SMOOTH_BRICK,
			Material.DOUBLE_STEP,
			Material.DOUBLE_STONE_SLAB2,
			Material.QUARTZ_BLOCK,
			Material.CLAY,
			Material.STAINED_CLAY,
			Material.STONE,
			Material.CONCRETE);
	
	public final static String tagSelectMaterial_FactoryTanks = "Materials_For_FactoryTanks";
	public MaterialList itemsSelectMaterial_FactoryTanks = createList(tagSelectMaterial_FactoryTanks,
			Material.STATIONARY_LAVA,
			Material.ICE,
			Material.PACKED_ICE,
			Material.SNOW_BLOCK,
			Material.SLIME_BLOCK,
			Material.COAL_BLOCK,
			Material.SAND,
			Material.GLASS,
			Material.STAINED_CLAY,
			Material.STAINED_GLASS,
			Material.STATIONARY_WATER,
			Material.CONCRETE_POWDER);

	public final static String tagSelectMaterial_BunkerBuildings = "Materials_For_BunkerBuildings";
	public MaterialList itemsSelectMaterial_BunkerBuildings = createList(tagSelectMaterial_BunkerBuildings,
			Material.CLAY,
			Material.STAINED_CLAY,
			Material.CONCRETE);

	public final static String tagSelectMaterial_BunkerPlatforms = "Materials_For_BunkerPlatforms";
	public MaterialList itemsSelectMaterial_BunkerPlatforms = createList(tagSelectMaterial_BunkerPlatforms,
			Material.CLAY,
			Material.QUARTZ_BLOCK,
			Material.CONCRETE);

	public final static String tagSelectMaterial_BunkerBilge = "Materials_For_BunkerBilge";
	public MaterialList itemsSelectMaterial_BunkerBilge = createList(tagSelectMaterial_BunkerBilge,
			Material.AIR,
			Material.STATIONARY_LAVA,
			Material.STATIONARY_WATER,
			Material.ICE,
			Material.PACKED_ICE);

	public final static String tagSelectMaterial_BunkerTanks = "Materials_For_BunkerTanks";
	public MaterialList itemsSelectMaterial_BunkerTanks = createList(tagSelectMaterial_BunkerTanks,
			Material.STATIONARY_LAVA,
			Material.PACKED_ICE,
			Material.SNOW_BLOCK,
			Material.SPONGE,
			Material.REDSTONE_BLOCK,
			Material.COAL_BLOCK,
			Material.HARD_CLAY,
			Material.ENDER_STONE,
			Material.EMERALD_BLOCK,
			Material.STATIONARY_WATER,
			Material.CONCRETE_POWDER,
			Material.CONCRETE);

	public final static String tagSelectMaterial_AstralTowerLight = "Materials_For_AstralTowerLight";
	public MaterialList itemsSelectMaterial_AstralTowerLight = createList(tagSelectMaterial_AstralTowerLight,
			Material.ENDER_STONE);

	public final static String tagSelectMaterial_AstralTowerDark = "Materials_For_AstralTowerDark";
	public MaterialList itemsSelectMaterial_AstralTowerDark = createList(tagSelectMaterial_AstralTowerDark,
			Material.OBSIDIAN);

	public final static String tagSelectMaterial_AstralTowerOres = "Materials_For_AstralTowerOres";
	public MaterialList itemsSelectMaterial_AstralTowerOres = createList(tagSelectMaterial_AstralTowerOres,
			Material.LAVA, 
			Material.WATER, 
			Material.MONSTER_EGG,
			Material.COAL_ORE, 
			Material.DIAMOND_ORE, 
			Material.EMERALD_ORE, 
			Material.GOLD_ORE,
			Material.IRON_ORE, 
			Material.LAPIS_ORE, 
			Material.QUARTZ_ORE, 
			Material.REDSTONE_ORE);

	public final static String tagSelectMaterial_AstralTowerHalls = "Materials_For_AstralTowerHalls";
	public MaterialList itemsSelectMaterial_AstralTowerHalls = createList(tagSelectMaterial_AstralTowerHalls,
			Material.OBSIDIAN, 
			Material.STONE, 
			Material.BRICK, 
			Material.COBBLESTONE, 
			Material.SMOOTH_BRICK, 
			Material.MOSSY_COBBLESTONE);

	public final static String tagSelectMaterial_AstralTowerTrim = "Materials_For_AstralTowerTrim";
	public MaterialList itemsSelectMaterial_AstralTowerTrim = createList(tagSelectMaterial_AstralTowerTrim,
			Material.AIR, 
			Material.GLOWSTONE);

	public final static String tagSelectMaterial_AstralCubeOres = "Materials_For_AstralCubeOres";
	public MaterialList itemsSelectMaterial_AstralCubeOres = createList(tagSelectMaterial_AstralCubeOres,
			Material.DIRT, 
			Material.STONE, 
			Material.COBBLESTONE, 
			Material.WOOD, 
			Material.IRON_BLOCK, 
			Material.COAL_BLOCK, 
			Material.DIAMOND_BLOCK,
			Material.REDSTONE_BLOCK, 
			Material.QUARTZ_BLOCK, 
			Material.MONSTER_EGG);

	
	public final static String tagMaterialListFor_MazeWalls = "Materials_List_For_MazeWalls";
	public MaterialList itemsMaterialListFor_MazeWalls = createList(tagMaterialListFor_MazeWalls,
			Material.OBSIDIAN, // Walls
			Material.OBSIDIAN);// Underlayment

	public final static String tagMaterialListFor_Roads = "Materials_List_For_Roads";
	public MaterialList itemsMaterialListFor_Roads = createList(tagMaterialListFor_Roads,
			Material.STAINED_CLAY, // Pavement
			Material.QUARTZ_BLOCK, // Lines
			Material.STEP,		   // Sidewalks
			Material.DIRT,		   // Dirt roads
			Material.DIRT);		   // Dirt sidewalks
	
	public final static String tagMaterialListFor_NormalOres = "Materials_List_For_NormalOres";
	public MaterialList itemsMaterialListFor_NormalOres = createList(tagMaterialListFor_NormalOres,
			Material.WATER, 		// liquid ore
			Material.LAVA,			// alt liquid ore
			Material.GRAVEL,		// gravel ore
			Material.COAL_ORE,		// most frequent ore
			Material.IRON_ORE,
			
			Material.GOLD_ORE,
			Material.LAPIS_ORE,
			Material.REDSTONE_ORE,
			Material.DIAMOND_ORE,
			Material.EMERALD_ORE);	// least frequent ore

	public final static String tagMaterialListFor_NetherOres = "Materials_List_For_NetherOres";
	public MaterialList itemsMaterialListFor_NetherOres = createList(tagMaterialListFor_NetherOres,
			Material.LAVA,			// liquid ore
			Material.LAVA,			// alt liquid ore
			Material.SOUL_SAND,		// SHOULD BE MAGMA // gravel ore 
			Material.SOUL_SAND,		// most frequent ore
			Material.GLOWSTONE,

			Material.GLOWSTONE,
			Material.QUARTZ_ORE,
			Material.SOUL_SAND,		// SHOULD BE MAGMA 
			Material.SOUL_SAND,
			Material.OBSIDIAN);		// least frequent ore

	public final static String tagMaterialListFor_TheEndOres = "Materials_List_For_TheEndOres";
	public MaterialList itemsMaterialListFor_TheEndOres = createList(tagMaterialListFor_TheEndOres,
			Material.WATER, 		// liquid ore
			Material.LAVA,			// alt liquid ore
			Material.GRAVEL,		// gravel ore
			Material.QUARTZ_BLOCK,	// most frequent ore
			Material.GLOWSTONE,

			Material.PURPUR_BLOCK,
			Material.GOLD_ORE,
			Material.LAPIS_ORE,
			Material.DIAMOND_ORE,
			Material.OBSIDIAN);		// least frequent ore
	
	private List<MaterialList> listOfLists;
	
	public MaterialProvider(CityWorldGenerator generator) {
	}
	
	private MaterialList createList(String name, Material ... materials) {
		
		// create the list and add all of the goodies
		MaterialList list = new MaterialList(name, materials);
		
		// add it to the big list so we can generically remember it
		if (listOfLists == null)
			listOfLists = new ArrayList<MaterialList>();
		listOfLists.add(list);
		
		// return it so we can specifically remember it
		return list;
	}
	
	public void read(CityWorldGenerator generator, ConfigurationSection section) {
		for (MaterialList materialList : listOfLists) {
			materialList.read(generator, section);
		}
	}

	public void write(CityWorldGenerator generator, ConfigurationSection section) {
		for (MaterialList materialList : listOfLists) {
			materialList.write(generator, section);
		}
	}

}
