package me.daddychurchill.CityWorld.Plugins;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.MaterialList;

public class MaterialProvider {
	// https://minecraft.gamepedia.com/1.13/Flattening

	private final static String tagRandomMaterials_BunkerChests = "Random_Materials_For_Bunker_Chests";
	public final MaterialList itemsRandomMaterials_BunkerChests = createList(tagRandomMaterials_BunkerChests,
			Material.IRON_PICKAXE, Material.IRON_SWORD, Material.IRON_SHOVEL, Material.IRON_BOOTS,
			Material.IRON_CHESTPLATE, Material.IRON_HELMET, Material.IRON_LEGGINGS, Material.CHAINMAIL_BOOTS,
			Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_HELMET, Material.CHAINMAIL_LEGGINGS, Material.TORCH,
			Material.BUCKET, Material.WATER_BUCKET, Material.LAVA_BUCKET, Material.REDSTONE);

	private final static String tagRandomMaterials_MineChests = "Random_Materials_For_Mine_Chests";
	public final MaterialList itemsRandomMaterials_MineChests = createList(tagRandomMaterials_MineChests,
			Material.STONE_PICKAXE, Material.STONE_SWORD, Material.STONE_SHOVEL, Material.IRON_PICKAXE,
			Material.IRON_SWORD, Material.IRON_SHOVEL, Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE,
			Material.LEATHER_HELMET, Material.LEATHER_LEGGINGS, Material.TORCH, Material.COMPASS, Material.CLOCK,
			Material.FLINT, Material.FLINT_AND_STEEL, Material.BUCKET, Material.WATER_BUCKET, Material.LAVA_BUCKET,
			Material.GUNPOWDER, Material.SAND, Material.BOOK, Material.COAL, Material.DIAMOND, Material.IRON_INGOT,
			Material.GOLD_INGOT, Material.APPLE, Material.ROTTEN_FLESH);

	private final static String tagRandomMaterials_SewerChests = "Random_Materials_For_Sewer_Chests";
	public final MaterialList itemsRandomMaterials_SewerChests = createList(tagRandomMaterials_SewerChests,
			Material.WOODEN_PICKAXE, Material.WOODEN_SWORD, Material.WOODEN_SHOVEL, Material.STONE_PICKAXE,
			Material.STONE_SWORD, Material.STONE_SHOVEL, Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE,
			Material.LEATHER_HELMET, Material.LEATHER_LEGGINGS, Material.TORCH, Material.COMPASS, Material.CLOCK,
			Material.FLINT_AND_STEEL, Material.FIREWORK_ROCKET, Material.COAL, Material.APPLE, Material.ROTTEN_FLESH);

	private final static String tagRandomMaterials_BuildingChests = "Random_Materials_For_Building_Chests";
	public final MaterialList itemsRandomMaterials_BuildingChests = createList(tagRandomMaterials_BuildingChests,
			Material.STRING, Material.TORCH, Material.COMPASS, Material.CLOCK, Material.SHEARS, Material.BOWL,
			Material.BUCKET, Material.GLASS_BOTTLE, Material.FLOWER_POT, Material.PAINTING, Material.ITEM_FRAME,
			Material.WHITE_BANNER, Material.FEATHER, Material.PAPER, Material.BOOK, Material.WRITABLE_BOOK,
			Material.MAP, Material.NAME_TAG, Material.ROTTEN_FLESH);

	private final static String tagRandomMaterials_WarehouseChests = "Random_Materials_For_Warehouse_Chests";
	public final MaterialList itemsRandomMaterials_WarehouseChests = createList(tagRandomMaterials_WarehouseChests,
			Material.FLINT_AND_STEEL, Material.RABBIT_FOOT, Material.RABBIT_HIDE, Material.LEATHER,
			Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET, Material.LEATHER_LEGGINGS,
			Material.CHAINMAIL_BOOTS, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_HELMET,
			Material.CHAINMAIL_LEGGINGS, Material.IRON_BOOTS, Material.IRON_CHESTPLATE, Material.IRON_HELMET,
			Material.IRON_LEGGINGS, Material.STONE_HOE, Material.STONE_AXE, Material.STONE_PICKAXE,
			Material.STONE_SWORD, Material.LEAD, Material.CARROT_ON_A_STICK, Material.FISHING_ROD,
			Material.TOTEM_OF_UNDYING, Material.MUSIC_DISC_11, Material.MUSIC_DISC_13, Material.MUSIC_DISC_BLOCKS,
			Material.MUSIC_DISC_CAT, Material.MUSIC_DISC_CHIRP, Material.MUSIC_DISC_FAR, Material.MUSIC_DISC_MALL,
			Material.MUSIC_DISC_MELLOHI, Material.MUSIC_DISC_STAL, Material.MUSIC_DISC_STRAD, Material.MUSIC_DISC_WAIT,
			Material.MUSIC_DISC_WARD, Material.ROTTEN_FLESH);

	private final static String tagRandomMaterials_FoodChests = "Random_Materials_For_Food_Chests";
	public final MaterialList itemsRandomMaterials_FoodChests = createList(tagRandomMaterials_FoodChests, Material.SUGAR,
			Material.CAKE, Material.COOKIE, Material.EGG, Material.APPLE, Material.MELON,
			Material.GLISTERING_MELON_SLICE, Material.CARROT, Material.BREAD, Material.BEEF, Material.CHICKEN,
			Material.COD, Material.MUTTON, Material.RABBIT, Material.POTATO, Material.POISONOUS_POTATO,
			Material.PUMPKIN, Material.BROWN_MUSHROOM, Material.RED_MUSHROOM, Material.BEETROOT, Material.COOKED_BEEF,
			Material.COOKED_CHICKEN, Material.COOKED_COD, Material.COOKED_MUTTON, Material.COOKED_RABBIT,
			Material.BAKED_POTATO, Material.PUMPKIN_PIE, Material.MUSHROOM_STEW, Material.BEETROOT_SOUP,
			Material.GOLDEN_CARROT, Material.GOLDEN_APPLE, Material.ROTTEN_FLESH);

	private final static String tagRandomMaterials_StorageShedChests = "Random_Materials_For_Storage_Shed_Chests";
	public final MaterialList itemsRandomMaterials_StorageShedChests = createList(tagRandomMaterials_StorageShedChests,
			Material.WOODEN_AXE, Material.WOODEN_SHOVEL, Material.WOODEN_PICKAXE, Material.IRON_AXE,
			Material.IRON_SHOVEL, Material.IRON_PICKAXE, Material.IRON_HELMET, Material.TORCH, Material.PAPER,
			Material.BOOK, Material.COOKED_MUTTON, Material.BOWL, Material.MUSHROOM_STEW);

	private final static String tagRandomMaterials_FarmChests = "Random_Materials_For_Farm_Chests";
	public final MaterialList itemsRandomMaterials_FarmChests = createList(tagRandomMaterials_FarmChests,
			Material.WOODEN_SHOVEL, Material.WOODEN_PICKAXE, Material.WOODEN_HOE, Material.IRON_SHOVEL,
			Material.IRON_PICKAXE, Material.IRON_HOE, Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE,
			Material.LEATHER_HELMET, Material.LEATHER_LEGGINGS, Material.BOW, Material.ARROW, Material.PAPER,
			Material.BOOK, Material.TORCH, Material.FISHING_ROD, Material.SHEARS, Material.BUCKET,
			Material.WATER_BUCKET, Material.MILK_BUCKET, Material.BONE, Material.BOWL, Material.COOKIE, Material.SUGAR,
			Material.COOKED_PORKCHOP, Material.COOKED_BEEF, Material.COOKED_CHICKEN, Material.COOKED_MUTTON,
			Material.COOKED_RABBIT, Material.BAKED_POTATO, Material.MELON, Material.PUMPKIN_PIE,
			Material.MUSHROOM_STEW);

	private final static String tagRandomMaterials_FarmOutputChests = "Random_Materials_For_Farm_Output_Chests";
	public final MaterialList itemsRandomMaterials_FarmOutputChests = createList(tagRandomMaterials_FarmOutputChests,
			Material.LEATHER, Material.PORKCHOP, Material.BEEF, Material.CHICKEN, Material.RABBIT, Material.MUTTON,
			Material.WHEAT_SEEDS, Material.WHITE_WOOL, Material.ORANGE_WOOL, Material.MAGENTA_WOOL,
			Material.LIGHT_BLUE_WOOL, Material.YELLOW_WOOL, Material.LIME_WOOL, Material.PINK_WOOL, Material.GRAY_WOOL,
			Material.LIGHT_GRAY_WOOL, Material.CYAN_WOOL, Material.PURPLE_WOOL, Material.BLUE_WOOL, Material.BROWN_WOOL,
			Material.GREEN_WOOL, Material.RED_WOOL, Material.BLACK_WOOL, Material.POTATO, Material.CARROT,
			Material.PUMPKIN_SEEDS, Material.MELON_SEEDS, Material.CARROT, Material.POTATO, Material.SUGAR_CANE,
			Material.APPLE);

	private final static String tagRandomMaterials_LumberChests = "Random_Materials_For_Lumber_Chests";
	public final MaterialList itemsRandomMaterials_LumberChests = createList(tagRandomMaterials_LumberChests,
			Material.WOODEN_AXE, Material.WOODEN_SHOVEL, Material.WOODEN_PICKAXE, Material.IRON_AXE,
			Material.IRON_SHOVEL, Material.IRON_PICKAXE, Material.IRON_HELMET, Material.FLINT_AND_STEEL, Material.COAL,
			Material.TORCH, Material.COOKED_PORKCHOP, Material.COOKED_CHICKEN, Material.COOKIE);

	private final static String tagRandomMaterials_LumberOutputChests = "Random_Materials_For_Lumber_Output_Chests";
	public final MaterialList itemsRandomMaterials_LumberOutputChests = createList(tagRandomMaterials_LumberOutputChests,
			Material.SPRUCE_PLANKS, // simple but cheesy way to increase the odds for this one
			Material.SPRUCE_PLANKS, Material.SPRUCE_PLANKS, Material.SPRUCE_PLANKS, Material.SPRUCE_PLANKS,
			Material.SPRUCE_PLANKS, Material.SPRUCE_PLANKS, Material.SPRUCE_PLANKS, Material.SPRUCE_PLANKS,
			Material.SPRUCE_PLANKS, Material.SPRUCE_LOG, Material.SPRUCE_LOG, Material.SPRUCE_LOG, Material.STICK,
			Material.STICK, Material.STICK, Material.OAK_DOOR, Material.OAK_FENCE, Material.OAK_FENCE_GATE,
			Material.OAK_STAIRS, Material.OAK_PRESSURE_PLATE, Material.OAK_TRAPDOOR, Material.OAK_SLAB,
			Material.OAK_BOAT, Material.OAK_LOG, Material.OAK_PLANKS, Material.OAK_WOOD, Material.SPRUCE_DOOR,
			Material.SPRUCE_FENCE, Material.SPRUCE_FENCE_GATE, Material.SPRUCE_STAIRS, Material.SPRUCE_PRESSURE_PLATE,
			Material.SPRUCE_TRAPDOOR, Material.SPRUCE_SLAB, Material.SPRUCE_BOAT, Material.SPRUCE_LOG,
			Material.SPRUCE_PLANKS, Material.SPRUCE_WOOD, Material.BIRCH_DOOR, Material.BIRCH_FENCE,
			Material.BIRCH_FENCE_GATE, Material.BIRCH_STAIRS, Material.BIRCH_PRESSURE_PLATE, Material.BIRCH_TRAPDOOR,
			Material.BIRCH_SLAB, Material.BIRCH_BOAT, Material.BIRCH_LOG, Material.BIRCH_PLANKS, Material.BIRCH_WOOD,
			Material.JUNGLE_DOOR, Material.JUNGLE_FENCE, Material.JUNGLE_FENCE_GATE, Material.JUNGLE_STAIRS,
			Material.JUNGLE_PRESSURE_PLATE, Material.JUNGLE_TRAPDOOR, Material.JUNGLE_SLAB, Material.JUNGLE_BOAT,
			Material.JUNGLE_LOG, Material.JUNGLE_PLANKS, Material.JUNGLE_WOOD, Material.ACACIA_DOOR,
			Material.ACACIA_FENCE, Material.ACACIA_FENCE_GATE, Material.ACACIA_STAIRS, Material.ACACIA_PRESSURE_PLATE,
			Material.ACACIA_TRAPDOOR, Material.ACACIA_SLAB, Material.ACACIA_BOAT, Material.ACACIA_LOG,
			Material.ACACIA_PLANKS, Material.ACACIA_WOOD, Material.DARK_OAK_DOOR, Material.DARK_OAK_FENCE,
			Material.DARK_OAK_FENCE_GATE, Material.DARK_OAK_STAIRS, Material.DARK_OAK_PRESSURE_PLATE,
			Material.DARK_OAK_TRAPDOOR, Material.DARK_OAK_SLAB, Material.DARK_OAK_BOAT, Material.DARK_OAK_LOG,
			Material.DARK_OAK_PLANKS, Material.DARK_OAK_WOOD, Material.SPRUCE_SIGN, Material.ACACIA_SIGN,
			Material.BIRCH_SIGN, Material.DARK_OAK_SIGN, Material.JUNGLE_SIGN, Material.OAK_SIGN,
			Material.SPRUCE_WALL_SIGN, Material.ACACIA_WALL_SIGN, Material.BIRCH_WALL_SIGN, Material.DARK_OAK_WALL_SIGN,
			Material.JUNGLE_WALL_SIGN, Material.OAK_WALL_SIGN);

	private final static String tagRandomMaterials_QuaryChests = "Random_Materials_For_Quary_Chests";
	public final MaterialList itemsRandomMaterials_QuaryChests = createList(tagRandomMaterials_QuaryChests,
			Material.STONE_SHOVEL, Material.STONE_PICKAXE, Material.IRON_SHOVEL, Material.IRON_PICKAXE,
			Material.IRON_HELMET, Material.FLINT_AND_STEEL, Material.TORCH, Material.BUCKET, Material.COOKED_BEEF,
			Material.COOKED_CHICKEN);

	private final static String tagRandomMaterials_QuaryOutputChests = "Random_Materials_For_Quary_Output_Chests";
	public final MaterialList itemsRandomMaterials_QuaryOutputChests = createList(tagRandomMaterials_QuaryOutputChests,
			Material.IRON_INGOT, // easy but stupid way to increase odds of some of these happening
			Material.IRON_INGOT, Material.IRON_INGOT, Material.IRON_INGOT, Material.IRON_INGOT, Material.IRON_INGOT,
			Material.IRON_INGOT, Material.COAL, Material.COAL, Material.COAL, Material.COAL, Material.COAL,
			Material.COAL, Material.COAL, Material.GOLD_INGOT, Material.GOLD_INGOT, Material.GOLD_INGOT,
			Material.REDSTONE, Material.REDSTONE, Material.REDSTONE, Material.DIAMOND, Material.EMERALD);

	private final static String tagSelectMaterial_BuildingWalls = "Materials_For_BuildingWalls";
	public final MaterialList itemsSelectMaterial_BuildingWalls = createList(tagSelectMaterial_BuildingWalls,
			Material.COBBLESTONE, Material.SANDSTONE, Material.BRICKS, Material.MOSSY_COBBLESTONE, Material.CLAY,
			Material.NETHERRACK, Material.SOUL_SAND, Material.STONE, Material.SMOOTH_STONE, Material.STONE_BRICKS,
			Material.NETHER_BRICKS, Material.QUARTZ_BLOCK, Material.CHISELED_STONE_BRICKS,
			Material.CRACKED_STONE_BRICKS, Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS,
			Material.JUNGLE_PLANKS, Material.OAK_PLANKS, Material.SPRUCE_PLANKS, Material.WHITE_WOOL,
			Material.ORANGE_WOOL, Material.MAGENTA_WOOL, Material.LIGHT_BLUE_WOOL, Material.YELLOW_WOOL,
			Material.LIME_WOOL, Material.PINK_WOOL, Material.GRAY_WOOL, Material.LIGHT_GRAY_WOOL, Material.CYAN_WOOL,
			Material.PURPLE_WOOL, Material.BLUE_WOOL, Material.BROWN_WOOL, Material.GREEN_WOOL, Material.RED_WOOL,
			Material.BLACK_WOOL, Material.TERRACOTTA, Material.WHITE_TERRACOTTA, Material.ORANGE_TERRACOTTA,
			Material.MAGENTA_TERRACOTTA, Material.LIGHT_BLUE_TERRACOTTA, Material.YELLOW_TERRACOTTA,
			Material.LIME_TERRACOTTA, Material.PINK_TERRACOTTA, Material.GRAY_TERRACOTTA,
			Material.LIGHT_GRAY_TERRACOTTA, Material.CYAN_TERRACOTTA, Material.PURPLE_TERRACOTTA,
			Material.BLUE_TERRACOTTA, Material.BROWN_TERRACOTTA, Material.GREEN_TERRACOTTA, Material.RED_TERRACOTTA,
			Material.BLACK_TERRACOTTA, Material.COAL_BLOCK, Material.RED_SANDSTONE, Material.RED_NETHER_BRICKS,
			Material.PRISMARINE, Material.PRISMARINE_BRICKS, Material.PURPUR_BLOCK, Material.PURPUR_PILLAR,
			Material.WHITE_CONCRETE, Material.ORANGE_CONCRETE, Material.MAGENTA_CONCRETE, Material.LIGHT_BLUE_CONCRETE,
			Material.YELLOW_CONCRETE, Material.LIME_CONCRETE, Material.PINK_CONCRETE, Material.GRAY_CONCRETE,
			Material.LIGHT_GRAY_CONCRETE, Material.CYAN_CONCRETE, Material.PURPLE_CONCRETE, Material.BLUE_CONCRETE,
			Material.BROWN_CONCRETE, Material.GREEN_CONCRETE, Material.RED_CONCRETE, Material.BLACK_CONCRETE,
			Material.END_STONE, Material.END_STONE_BRICKS);

	private final static String tagSelectMaterial_BuildingFoundation = "Materials_For_BuildingFoundation";
	public final MaterialList itemsSelectMaterial_BuildingFoundation = createList(tagSelectMaterial_BuildingFoundation,
			Material.COBBLESTONE, Material.SANDSTONE, Material.BRICKS, Material.MOSSY_COBBLESTONE, Material.CLAY,
			Material.NETHERRACK, Material.SMOOTH_STONE, Material.STONE_BRICKS, Material.NETHER_BRICKS,
			Material.QUARTZ_BLOCK, Material.CHISELED_STONE_BRICKS, Material.CRACKED_STONE_BRICKS,
			Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS,
			Material.OAK_PLANKS, Material.SPRUCE_PLANKS, Material.WHITE_WOOL, Material.ORANGE_WOOL,
			Material.MAGENTA_WOOL, Material.LIGHT_BLUE_WOOL, Material.YELLOW_WOOL, Material.LIME_WOOL,
			Material.PINK_WOOL, Material.GRAY_WOOL, Material.LIGHT_GRAY_WOOL, Material.CYAN_WOOL, Material.PURPLE_WOOL,
			Material.BLUE_WOOL, Material.BROWN_WOOL, Material.GREEN_WOOL, Material.RED_WOOL, Material.BLACK_WOOL,
			Material.TERRACOTTA, Material.WHITE_TERRACOTTA, Material.ORANGE_TERRACOTTA, Material.MAGENTA_TERRACOTTA,
			Material.LIGHT_BLUE_TERRACOTTA, Material.YELLOW_TERRACOTTA, Material.LIME_TERRACOTTA,
			Material.PINK_TERRACOTTA, Material.GRAY_TERRACOTTA, Material.LIGHT_GRAY_TERRACOTTA,
			Material.CYAN_TERRACOTTA, Material.PURPLE_TERRACOTTA, Material.BLUE_TERRACOTTA, Material.BROWN_TERRACOTTA,
			Material.GREEN_TERRACOTTA, Material.RED_TERRACOTTA, Material.BLACK_TERRACOTTA, Material.COAL_BLOCK,
			Material.RED_SANDSTONE, Material.RED_NETHER_BRICKS, Material.PRISMARINE, Material.PRISMARINE_BRICKS,
			Material.PURPUR_BLOCK, Material.PURPUR_PILLAR, Material.WHITE_CONCRETE, Material.ORANGE_CONCRETE,
			Material.MAGENTA_CONCRETE, Material.LIGHT_BLUE_CONCRETE, Material.YELLOW_CONCRETE, Material.LIME_CONCRETE,
			Material.PINK_CONCRETE, Material.GRAY_CONCRETE, Material.LIGHT_GRAY_CONCRETE, Material.CYAN_CONCRETE,
			Material.PURPLE_CONCRETE, Material.BLUE_CONCRETE, Material.BROWN_CONCRETE, Material.GREEN_CONCRETE,
			Material.RED_CONCRETE, Material.BLACK_CONCRETE, Material.END_STONE, Material.END_STONE_BRICKS);

	private final static String tagSelectMaterial_BuildingCeilings = "Materials_For_BuildingCeilings";
	public final MaterialList itemsSelectMaterial_BuildingCeilings = createList(tagSelectMaterial_BuildingCeilings,
			Material.COBBLESTONE, Material.SANDSTONE, Material.BRICKS, Material.MOSSY_COBBLESTONE, Material.CLAY,
			Material.NETHERRACK, Material.SMOOTH_STONE, Material.STONE_BRICKS, Material.NETHER_BRICKS,
			Material.QUARTZ_BLOCK, Material.CHISELED_STONE_BRICKS, Material.CRACKED_STONE_BRICKS,
			Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS,
			Material.OAK_PLANKS, Material.SPRUCE_PLANKS, Material.WHITE_WOOL, Material.ORANGE_WOOL,
			Material.MAGENTA_WOOL, Material.LIGHT_BLUE_WOOL, Material.YELLOW_WOOL, Material.LIME_WOOL,
			Material.PINK_WOOL, Material.GRAY_WOOL, Material.LIGHT_GRAY_WOOL, Material.CYAN_WOOL, Material.PURPLE_WOOL,
			Material.BLUE_WOOL, Material.BROWN_WOOL, Material.GREEN_WOOL, Material.RED_WOOL, Material.BLACK_WOOL,
			Material.TERRACOTTA, Material.WHITE_TERRACOTTA, Material.ORANGE_TERRACOTTA, Material.MAGENTA_TERRACOTTA,
			Material.LIGHT_BLUE_TERRACOTTA, Material.YELLOW_TERRACOTTA, Material.LIME_TERRACOTTA,
			Material.PINK_TERRACOTTA, Material.GRAY_TERRACOTTA, Material.LIGHT_GRAY_TERRACOTTA,
			Material.CYAN_TERRACOTTA, Material.PURPLE_TERRACOTTA, Material.BLUE_TERRACOTTA, Material.BROWN_TERRACOTTA,
			Material.GREEN_TERRACOTTA, Material.RED_TERRACOTTA, Material.BLACK_TERRACOTTA, Material.COAL_BLOCK,
			Material.RED_SANDSTONE, Material.RED_NETHER_BRICKS, Material.PRISMARINE, Material.PRISMARINE_BRICKS,
			Material.PURPUR_BLOCK, Material.PURPUR_PILLAR, Material.WHITE_CONCRETE, Material.ORANGE_CONCRETE,
			Material.MAGENTA_CONCRETE, Material.LIGHT_BLUE_CONCRETE, Material.YELLOW_CONCRETE, Material.LIME_CONCRETE,
			Material.PINK_CONCRETE, Material.GRAY_CONCRETE, Material.LIGHT_GRAY_CONCRETE, Material.CYAN_CONCRETE,
			Material.PURPLE_CONCRETE, Material.BLUE_CONCRETE, Material.BROWN_CONCRETE, Material.GREEN_CONCRETE,
			Material.RED_CONCRETE, Material.BLACK_CONCRETE, Material.END_STONE, Material.END_STONE_BRICKS);

	private final static String tagSelectMaterial_BuildingRoofs = "Materials_For_BuildingRoofs";
	public final MaterialList itemsSelectMaterial_BuildingRoofs = createList(tagSelectMaterial_BuildingRoofs,
			Material.COBBLESTONE, Material.SANDSTONE, Material.BRICKS, Material.MOSSY_COBBLESTONE, Material.CLAY,
			Material.NETHERRACK, Material.SMOOTH_STONE, Material.STONE_BRICKS, Material.NETHER_BRICKS,
			Material.QUARTZ_BLOCK, Material.CHISELED_STONE_BRICKS, Material.CRACKED_STONE_BRICKS,
			Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS,
			Material.OAK_PLANKS, Material.SPRUCE_PLANKS, Material.WHITE_WOOL, Material.ORANGE_WOOL,
			Material.MAGENTA_WOOL, Material.LIGHT_BLUE_WOOL, Material.YELLOW_WOOL, Material.LIME_WOOL,
			Material.PINK_WOOL, Material.GRAY_WOOL, Material.LIGHT_GRAY_WOOL, Material.CYAN_WOOL, Material.PURPLE_WOOL,
			Material.BLUE_WOOL, Material.BROWN_WOOL, Material.GREEN_WOOL, Material.RED_WOOL, Material.BLACK_WOOL,
			Material.TERRACOTTA, Material.WHITE_TERRACOTTA, Material.ORANGE_TERRACOTTA, Material.MAGENTA_TERRACOTTA,
			Material.LIGHT_BLUE_TERRACOTTA, Material.YELLOW_TERRACOTTA, Material.LIME_TERRACOTTA,
			Material.PINK_TERRACOTTA, Material.GRAY_TERRACOTTA, Material.LIGHT_GRAY_TERRACOTTA,
			Material.CYAN_TERRACOTTA, Material.PURPLE_TERRACOTTA, Material.BLUE_TERRACOTTA, Material.BROWN_TERRACOTTA,
			Material.GREEN_TERRACOTTA, Material.RED_TERRACOTTA, Material.BLACK_TERRACOTTA, Material.COAL_BLOCK,
			Material.RED_SANDSTONE, Material.RED_NETHER_BRICKS, Material.PRISMARINE, Material.PRISMARINE_BRICKS,
			Material.PURPUR_BLOCK, Material.PURPUR_PILLAR, Material.WHITE_CONCRETE, Material.ORANGE_CONCRETE,
			Material.MAGENTA_CONCRETE, Material.LIGHT_BLUE_CONCRETE, Material.YELLOW_CONCRETE, Material.LIME_CONCRETE,
			Material.PINK_CONCRETE, Material.GRAY_CONCRETE, Material.LIGHT_GRAY_CONCRETE, Material.CYAN_CONCRETE,
			Material.PURPLE_CONCRETE, Material.BLUE_CONCRETE, Material.BROWN_CONCRETE, Material.GREEN_CONCRETE,
			Material.RED_CONCRETE, Material.BLACK_CONCRETE, Material.END_STONE, Material.END_STONE_BRICKS);

	private final static String tagSelectMaterial_UnfinishedBuildings = "Materials_For_UnfinishedBuildings";
	public final MaterialList itemsSelectMaterial_UnfinishedBuildings = createList(tagSelectMaterial_UnfinishedBuildings,
			Material.CLAY, Material.WHITE_TERRACOTTA, Material.GRAY_TERRACOTTA, Material.LIGHT_GRAY_TERRACOTTA,
			Material.BLACK_TERRACOTTA, Material.WHITE_CONCRETE, Material.GRAY_CONCRETE, Material.LIGHT_GRAY_CONCRETE,
			Material.BLACK_CONCRETE);

	private final static String tagSelectMaterial_GovernmentWalls = "Materials_For_GovernmentWalls";
	public final MaterialList itemsSelectMaterial_GovernmentWalls = createList(tagSelectMaterial_GovernmentWalls,
			Material.WHITE_TERRACOTTA, Material.LIGHT_GRAY_TERRACOTTA, Material.QUARTZ_BLOCK, Material.QUARTZ_PILLAR,
			Material.CHISELED_QUARTZ_BLOCK, Material.END_STONE, Material.END_STONE_BRICKS, Material.WHITE_WOOL);

	private final static String tagSelectMaterial_GovernmentFoundations = "Materials_For_GovernmentFoundations";
	public final MaterialList itemsSelectMaterial_GovernmentFoundations = createList(tagSelectMaterial_GovernmentFoundations,
			Material.WHITE_TERRACOTTA, Material.LIGHT_GRAY_TERRACOTTA, Material.QUARTZ_BLOCK, Material.QUARTZ_PILLAR,
			Material.CHISELED_QUARTZ_BLOCK, Material.END_STONE, Material.END_STONE_BRICKS, Material.WHITE_WOOL);

	private final static String tagSelectMaterial_GovernmentCeilings = "Materials_For_GovernmentCeilings";
	public final MaterialList itemsSelectMaterial_GovernmentCeilings = createList(tagSelectMaterial_GovernmentCeilings,
			Material.WHITE_TERRACOTTA, Material.LIGHT_GRAY_TERRACOTTA, Material.QUARTZ_BLOCK, Material.QUARTZ_PILLAR,
			Material.CHISELED_QUARTZ_BLOCK, Material.END_STONE, Material.END_STONE_BRICKS, Material.WHITE_WOOL);

	private final static String tagSelectMaterial_HouseWalls = "Materials_For_HouseWalls";
	public final MaterialList itemsSelectMaterial_HouseWalls = createList(tagSelectMaterial_HouseWalls, Material.COBBLESTONE,
			Material.MOSSY_COBBLESTONE, Material.STONE, Material.SMOOTH_STONE, Material.SANDSTONE,
			Material.RED_SANDSTONE, Material.STONE_BRICKS, Material.NETHER_BRICKS, Material.BRICKS, Material.CLAY,
			Material.TERRACOTTA, Material.WHITE_TERRACOTTA, Material.ORANGE_TERRACOTTA, Material.MAGENTA_TERRACOTTA,
			Material.LIGHT_BLUE_TERRACOTTA, Material.YELLOW_TERRACOTTA, Material.LIME_TERRACOTTA,
			Material.PINK_TERRACOTTA, Material.GRAY_TERRACOTTA, Material.LIGHT_GRAY_TERRACOTTA,
			Material.CYAN_TERRACOTTA, Material.PURPLE_TERRACOTTA, Material.BLUE_TERRACOTTA, Material.BROWN_TERRACOTTA,
			Material.GREEN_TERRACOTTA, Material.RED_TERRACOTTA, Material.BLACK_TERRACOTTA, Material.PRISMARINE,
			Material.PURPUR_BLOCK, Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS,
			Material.JUNGLE_PLANKS, Material.OAK_PLANKS, Material.SPRUCE_PLANKS, Material.WHITE_CONCRETE,
			Material.ORANGE_CONCRETE, Material.MAGENTA_CONCRETE, Material.LIGHT_BLUE_CONCRETE, Material.YELLOW_CONCRETE,
			Material.LIME_CONCRETE, Material.PINK_CONCRETE, Material.GRAY_CONCRETE, Material.LIGHT_GRAY_CONCRETE,
			Material.CYAN_CONCRETE, Material.PURPLE_CONCRETE, Material.BLUE_CONCRETE, Material.BROWN_CONCRETE,
			Material.GREEN_CONCRETE, Material.RED_CONCRETE, Material.BLACK_CONCRETE, Material.END_STONE,
			Material.END_STONE_BRICKS);

	private final static String tagSelectMaterial_HouseFloors = "Materials_For_HouseFloors";
	public final MaterialList itemsSelectMaterial_HouseFloors = createList(tagSelectMaterial_HouseFloors,
			Material.COBBLESTONE, Material.COBBLESTONE, Material.COBBLESTONE, Material.STONE, Material.STONE,
			Material.STONE, Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS,
			Material.JUNGLE_PLANKS, Material.OAK_PLANKS, Material.SPRUCE_PLANKS, Material.WHITE_WOOL,
			Material.ORANGE_WOOL, Material.MAGENTA_WOOL, Material.LIGHT_BLUE_WOOL, Material.YELLOW_WOOL,
			Material.LIME_WOOL, Material.PINK_WOOL, Material.GRAY_WOOL, Material.LIGHT_GRAY_WOOL, Material.CYAN_WOOL,
			Material.PURPLE_WOOL, Material.BLUE_WOOL, Material.BROWN_WOOL, Material.GREEN_WOOL, Material.RED_WOOL,
			Material.BLACK_WOOL, Material.TERRACOTTA, Material.WHITE_TERRACOTTA, Material.ORANGE_TERRACOTTA,
			Material.MAGENTA_TERRACOTTA, Material.LIGHT_BLUE_TERRACOTTA, Material.YELLOW_TERRACOTTA,
			Material.LIME_TERRACOTTA, Material.PINK_TERRACOTTA, Material.GRAY_TERRACOTTA,
			Material.LIGHT_GRAY_TERRACOTTA, Material.CYAN_TERRACOTTA, Material.PURPLE_TERRACOTTA,
			Material.BLUE_TERRACOTTA, Material.BROWN_TERRACOTTA, Material.GREEN_TERRACOTTA, Material.RED_TERRACOTTA,
			Material.BLACK_TERRACOTTA, Material.BLACK_GLAZED_TERRACOTTA, Material.BLUE_GLAZED_TERRACOTTA,
			Material.BROWN_GLAZED_TERRACOTTA, Material.CYAN_GLAZED_TERRACOTTA, Material.GRAY_GLAZED_TERRACOTTA,
			Material.GREEN_GLAZED_TERRACOTTA, Material.LIGHT_BLUE_GLAZED_TERRACOTTA, Material.LIME_GLAZED_TERRACOTTA,
			Material.MAGENTA_GLAZED_TERRACOTTA, Material.ORANGE_GLAZED_TERRACOTTA, Material.PINK_GLAZED_TERRACOTTA,
			Material.PURPLE_GLAZED_TERRACOTTA, Material.RED_GLAZED_TERRACOTTA, Material.LIGHT_GRAY_GLAZED_TERRACOTTA,
			Material.WHITE_GLAZED_TERRACOTTA, Material.YELLOW_GLAZED_TERRACOTTA);

	private final static String tagSelectMaterial_HouseCeilings = "Materials_For_HouseCeilings";
	public final MaterialList itemsSelectMaterial_HouseCeilings = createList(tagSelectMaterial_HouseCeilings,
			Material.COBBLESTONE, Material.SMOOTH_STONE, Material.SANDSTONE, Material.RED_SANDSTONE,
			Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS,
			Material.OAK_PLANKS, Material.SPRUCE_PLANKS, Material.STONE);

	private final static String tagSelectMaterial_HouseRoofs = "Materials_For_HouseRoofs";
	public final MaterialList itemsSelectMaterial_HouseRoofs = createList(tagSelectMaterial_HouseRoofs, Material.COBBLESTONE,
			Material.MOSSY_COBBLESTONE, Material.SMOOTH_STONE, Material.SANDSTONE, Material.RED_SANDSTONE,
			Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS,
			Material.OAK_PLANKS, Material.SPRUCE_PLANKS, Material.STONE);

	private final static String tagSelectMaterial_OilPlatformFloor = "Materials_For_OilPlatformFloor";
	public final MaterialList itemsSelectMaterial_OilPlatformFloor = createList(tagSelectMaterial_OilPlatformFloor,
			Material.WHITE_CONCRETE, Material.ORANGE_CONCRETE, Material.MAGENTA_CONCRETE, Material.LIGHT_BLUE_CONCRETE,
			Material.YELLOW_CONCRETE, Material.LIME_CONCRETE, Material.PINK_CONCRETE, Material.GRAY_CONCRETE,
			Material.LIGHT_GRAY_CONCRETE, Material.CYAN_CONCRETE, Material.PURPLE_CONCRETE, Material.BLUE_CONCRETE,
			Material.BROWN_CONCRETE, Material.GREEN_CONCRETE, Material.RED_CONCRETE, Material.BLACK_CONCRETE,
			Material.STONE);

	private final static String tagSelectMaterial_OilPlatformColumn = "Materials_For_OilPlatformColumn";
	public final MaterialList itemsSelectMaterial_OilPlatformColumn = createList(tagSelectMaterial_OilPlatformColumn,
			Material.WHITE_CONCRETE, Material.ORANGE_CONCRETE, Material.MAGENTA_CONCRETE, Material.LIGHT_BLUE_CONCRETE,
			Material.YELLOW_CONCRETE, Material.LIME_CONCRETE, Material.PINK_CONCRETE, Material.GRAY_CONCRETE,
			Material.LIGHT_GRAY_CONCRETE, Material.CYAN_CONCRETE, Material.PURPLE_CONCRETE, Material.BLUE_CONCRETE,
			Material.BROWN_CONCRETE, Material.GREEN_CONCRETE, Material.RED_CONCRETE, Material.BLACK_CONCRETE,
			Material.STONE);

	private final static String tagSelectMaterial_ShackWalls = "Materials_For_ShackWalls";
	public final MaterialList itemsSelectMaterial_ShackWalls = createList(tagSelectMaterial_ShackWalls,
			Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS,
			Material.OAK_PLANKS, Material.SPRUCE_PLANKS, Material.MOSSY_COBBLESTONE, Material.RED_SANDSTONE,
			Material.STONE_BRICKS, Material.NETHER_BRICKS, Material.BRICKS, Material.STONE);

	private final static String tagSelectMaterial_ShackRoofs = "Materials_For_ShackRoofs";
	public final MaterialList itemsSelectMaterial_ShackRoofs = createList(tagSelectMaterial_ShackRoofs,
			Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS,
			Material.OAK_PLANKS, Material.SPRUCE_PLANKS, Material.STONE);

	private final static String tagSelectMaterial_ShedWalls = "Materials_For_ShedWalls";
	public final MaterialList itemsSelectMaterial_ShedWalls = createList(tagSelectMaterial_ShedWalls, Material.SANDSTONE,
			Material.RED_SANDSTONE, Material.SPRUCE_PLANKS, Material.COBBLESTONE, Material.BRICKS,
			Material.SMOOTH_STONE, Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS,
			Material.JUNGLE_PLANKS, Material.OAK_PLANKS, Material.SPRUCE_PLANKS, Material.STONE);

	private final static String tagSelectMaterial_ShedRoofs = "Materials_For_ShedRoofs";
	public final MaterialList itemsSelectMaterial_ShedRoofs = createList(tagSelectMaterial_ShedRoofs, Material.STONE_SLAB,
			Material.BIRCH_SLAB);

	private final static String tagSelectMaterial_StoneWorksPiles = "Materials_For_QuaryPiles";
	public final MaterialList itemsSelectMaterial_QuaryPiles = createList(tagSelectMaterial_StoneWorksPiles, Material.GRAVEL,
			Material.GRAVEL, Material.GRAVEL, Material.GRAVEL, Material.GRAVEL, Material.COAL_ORE, Material.COAL_ORE,
			Material.COAL_ORE, Material.COAL_ORE, Material.IRON_ORE, Material.IRON_ORE, Material.IRON_ORE,
			Material.GOLD_ORE, Material.LAPIS_ORE, Material.REDSTONE_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE,
			Material.WHITE_CONCRETE_POWDER, Material.ORANGE_CONCRETE_POWDER, Material.MAGENTA_CONCRETE_POWDER,
			Material.LIGHT_BLUE_CONCRETE_POWDER, Material.YELLOW_CONCRETE_POWDER, Material.LIME_CONCRETE_POWDER,
			Material.PINK_CONCRETE_POWDER, Material.GRAY_CONCRETE_POWDER, Material.LIGHT_GRAY_CONCRETE_POWDER,
			Material.CYAN_CONCRETE_POWDER, Material.PURPLE_CONCRETE_POWDER, Material.BLUE_CONCRETE_POWDER,
			Material.BROWN_CONCRETE_POWDER, Material.GREEN_CONCRETE_POWDER, Material.RED_CONCRETE_POWDER,
			Material.BLACK_CONCRETE_POWDER);

	private final static String tagSelectMaterial_Castles = "Materials_For_Castles";
	public final MaterialList itemsSelectMaterial_Castles = createList(tagSelectMaterial_Castles, Material.COBBLESTONE,
			Material.MOSSY_COBBLESTONE, Material.CRACKED_STONE_BRICKS, Material.CHISELED_STONE_BRICKS,
			Material.QUARTZ_PILLAR, Material.CHISELED_QUARTZ_BLOCK);

	private final static String tagSelectMaterial_WaterTowers = "Materials_For_WaterTowers";
	public final MaterialList itemsSelectMaterial_WaterTowers = createList(tagSelectMaterial_WaterTowers, Material.CLAY,
			Material.WHITE_TERRACOTTA, Material.ORANGE_TERRACOTTA, Material.MAGENTA_TERRACOTTA,
			Material.LIGHT_BLUE_TERRACOTTA, Material.YELLOW_TERRACOTTA, Material.LIME_TERRACOTTA,
			Material.PINK_TERRACOTTA, Material.GRAY_TERRACOTTA, Material.LIGHT_GRAY_TERRACOTTA,
			Material.CYAN_TERRACOTTA, Material.PURPLE_TERRACOTTA, Material.BLUE_TERRACOTTA, Material.BROWN_TERRACOTTA,
			Material.GREEN_TERRACOTTA, Material.RED_TERRACOTTA, Material.BLACK_TERRACOTTA, Material.WHITE_CONCRETE,
			Material.ORANGE_CONCRETE, Material.MAGENTA_CONCRETE, Material.LIGHT_BLUE_CONCRETE, Material.YELLOW_CONCRETE,
			Material.LIME_CONCRETE, Material.PINK_CONCRETE, Material.GRAY_CONCRETE, Material.LIGHT_GRAY_CONCRETE,
			Material.CYAN_CONCRETE, Material.PURPLE_CONCRETE, Material.BLUE_CONCRETE, Material.BROWN_CONCRETE,
			Material.GREEN_CONCRETE, Material.RED_CONCRETE, Material.BLACK_CONCRETE);

	private final static String tagSelectMaterial_FactoryInsides = "Materials_For_FactoryInsides";
	public final MaterialList itemsSelectMaterial_FactoryInsides = createList(tagSelectMaterial_FactoryInsides,
			Material.STONE, Material.SMOOTH_STONE, Material.QUARTZ_BLOCK, Material.CLAY, Material.WHITE_CONCRETE,
			Material.ORANGE_CONCRETE, Material.MAGENTA_CONCRETE, Material.LIGHT_BLUE_CONCRETE, Material.YELLOW_CONCRETE,
			Material.LIME_CONCRETE, Material.PINK_CONCRETE, Material.GRAY_CONCRETE, Material.LIGHT_GRAY_CONCRETE,
			Material.CYAN_CONCRETE, Material.PURPLE_CONCRETE, Material.BLUE_CONCRETE, Material.BROWN_CONCRETE,
			Material.GREEN_CONCRETE, Material.RED_CONCRETE, Material.BLACK_CONCRETE);

	private final static String tagSelectMaterial_FactoryTanks = "Materials_For_FactoryTanks";
	public final MaterialList itemsSelectMaterial_FactoryTanks = createList(tagSelectMaterial_FactoryTanks, Material.LAVA,
			Material.ICE, Material.PACKED_ICE, Material.SNOW_BLOCK, Material.SLIME_BLOCK, Material.COAL_BLOCK,
			Material.SAND, Material.WATER, Material.GLASS, Material.WHITE_STAINED_GLASS, Material.ORANGE_STAINED_GLASS,
			Material.MAGENTA_STAINED_GLASS, Material.LIGHT_BLUE_STAINED_GLASS, Material.YELLOW_STAINED_GLASS,
			Material.LIME_STAINED_GLASS, Material.PINK_STAINED_GLASS, Material.GRAY_STAINED_GLASS,
			Material.LIGHT_GRAY_STAINED_GLASS, Material.CYAN_STAINED_GLASS, Material.PURPLE_STAINED_GLASS,
			Material.BLUE_STAINED_GLASS, Material.BROWN_STAINED_GLASS, Material.GREEN_STAINED_GLASS,
			Material.RED_STAINED_GLASS, Material.BLACK_STAINED_GLASS, Material.TERRACOTTA, Material.WHITE_TERRACOTTA,
			Material.ORANGE_TERRACOTTA, Material.MAGENTA_TERRACOTTA, Material.LIGHT_BLUE_TERRACOTTA,
			Material.YELLOW_TERRACOTTA, Material.LIME_TERRACOTTA, Material.PINK_TERRACOTTA, Material.GRAY_TERRACOTTA,
			Material.LIGHT_GRAY_TERRACOTTA, Material.CYAN_TERRACOTTA, Material.PURPLE_TERRACOTTA,
			Material.BLUE_TERRACOTTA, Material.BROWN_TERRACOTTA, Material.GREEN_TERRACOTTA, Material.RED_TERRACOTTA,
			Material.BLACK_TERRACOTTA, Material.WHITE_CONCRETE_POWDER, Material.ORANGE_CONCRETE_POWDER,
			Material.MAGENTA_CONCRETE_POWDER, Material.LIGHT_BLUE_CONCRETE_POWDER, Material.YELLOW_CONCRETE_POWDER,
			Material.LIME_CONCRETE_POWDER, Material.PINK_CONCRETE_POWDER, Material.GRAY_CONCRETE_POWDER,
			Material.LIGHT_GRAY_CONCRETE_POWDER, Material.CYAN_CONCRETE_POWDER, Material.PURPLE_CONCRETE_POWDER,
			Material.BLUE_CONCRETE_POWDER, Material.BROWN_CONCRETE_POWDER, Material.GREEN_CONCRETE_POWDER,
			Material.RED_CONCRETE_POWDER, Material.BLACK_CONCRETE_POWDER);

	private final static String tagSelectMaterial_BunkerBuildings = "Materials_For_BunkerBuildings";
	public final MaterialList itemsSelectMaterial_BunkerBuildings = createList(tagSelectMaterial_BunkerBuildings,
			Material.CLAY, Material.QUARTZ_BLOCK, Material.TERRACOTTA, Material.WHITE_TERRACOTTA,
			Material.ORANGE_TERRACOTTA, Material.MAGENTA_TERRACOTTA, Material.LIGHT_BLUE_TERRACOTTA,
			Material.YELLOW_TERRACOTTA, Material.LIME_TERRACOTTA, Material.PINK_TERRACOTTA, Material.GRAY_TERRACOTTA,
			Material.LIGHT_GRAY_TERRACOTTA, Material.CYAN_TERRACOTTA, Material.PURPLE_TERRACOTTA,
			Material.BLUE_TERRACOTTA, Material.BROWN_TERRACOTTA, Material.GREEN_TERRACOTTA, Material.RED_TERRACOTTA,
			Material.BLACK_TERRACOTTA, Material.WHITE_TERRACOTTA, Material.WHITE_CONCRETE, Material.ORANGE_CONCRETE,
			Material.MAGENTA_CONCRETE, Material.LIGHT_BLUE_CONCRETE, Material.YELLOW_CONCRETE, Material.LIME_CONCRETE,
			Material.PINK_CONCRETE, Material.GRAY_CONCRETE, Material.LIGHT_GRAY_CONCRETE, Material.CYAN_CONCRETE,
			Material.PURPLE_CONCRETE, Material.BLUE_CONCRETE, Material.BROWN_CONCRETE, Material.GREEN_CONCRETE,
			Material.RED_CONCRETE, Material.BLACK_CONCRETE);

	private final static String tagSelectMaterial_BunkerPlatforms = "Materials_For_BunkerPlatforms";
	public final MaterialList itemsSelectMaterial_BunkerPlatforms = createList(tagSelectMaterial_BunkerPlatforms,
			Material.CLAY, Material.QUARTZ_BLOCK, Material.QUARTZ_PILLAR, Material.CHISELED_QUARTZ_BLOCK,
			Material.TERRACOTTA, Material.WHITE_TERRACOTTA, Material.ORANGE_TERRACOTTA, Material.MAGENTA_TERRACOTTA,
			Material.LIGHT_BLUE_TERRACOTTA, Material.YELLOW_TERRACOTTA, Material.LIME_TERRACOTTA,
			Material.PINK_TERRACOTTA, Material.GRAY_TERRACOTTA, Material.LIGHT_GRAY_TERRACOTTA,
			Material.CYAN_TERRACOTTA, Material.PURPLE_TERRACOTTA, Material.BLUE_TERRACOTTA, Material.BROWN_TERRACOTTA,
			Material.GREEN_TERRACOTTA, Material.RED_TERRACOTTA, Material.BLACK_TERRACOTTA, Material.WHITE_TERRACOTTA,
			Material.WHITE_CONCRETE, Material.ORANGE_CONCRETE, Material.MAGENTA_CONCRETE, Material.LIGHT_BLUE_CONCRETE,
			Material.YELLOW_CONCRETE, Material.LIME_CONCRETE, Material.PINK_CONCRETE, Material.GRAY_CONCRETE,
			Material.LIGHT_GRAY_CONCRETE, Material.CYAN_CONCRETE, Material.PURPLE_CONCRETE, Material.BLUE_CONCRETE,
			Material.BROWN_CONCRETE, Material.GREEN_CONCRETE, Material.RED_CONCRETE, Material.BLACK_CONCRETE);

	private final static String tagSelectMaterial_BunkerBilge = "Materials_For_BunkerBilge";
	public final MaterialList itemsSelectMaterial_BunkerBilge = createList(tagSelectMaterial_BunkerBilge, Material.AIR,
			Material.LAVA, Material.WATER, Material.ICE, Material.PACKED_ICE);

	private final static String tagSelectMaterial_BunkerTanks = "Materials_For_BunkerTanks";
	public final MaterialList itemsSelectMaterial_BunkerTanks = createList(tagSelectMaterial_BunkerTanks, Material.SPONGE,
			Material.REDSTONE_BLOCK, Material.END_STONE, Material.EMERALD_BLOCK, Material.LAVA, Material.ICE,
			Material.PACKED_ICE, Material.SNOW_BLOCK, Material.SLIME_BLOCK, Material.COAL_BLOCK, Material.SAND,
			Material.WATER, Material.GLASS, Material.WHITE_STAINED_GLASS, Material.ORANGE_STAINED_GLASS,
			Material.MAGENTA_STAINED_GLASS, Material.LIGHT_BLUE_STAINED_GLASS, Material.YELLOW_STAINED_GLASS,
			Material.LIME_STAINED_GLASS, Material.PINK_STAINED_GLASS, Material.GRAY_STAINED_GLASS,
			Material.LIGHT_GRAY_STAINED_GLASS, Material.CYAN_STAINED_GLASS, Material.PURPLE_STAINED_GLASS,
			Material.BLUE_STAINED_GLASS, Material.BROWN_STAINED_GLASS, Material.GREEN_STAINED_GLASS,
			Material.RED_STAINED_GLASS, Material.BLACK_STAINED_GLASS, Material.TERRACOTTA, Material.WHITE_TERRACOTTA,
			Material.ORANGE_TERRACOTTA, Material.MAGENTA_TERRACOTTA, Material.LIGHT_BLUE_TERRACOTTA,
			Material.YELLOW_TERRACOTTA, Material.LIME_TERRACOTTA, Material.PINK_TERRACOTTA, Material.GRAY_TERRACOTTA,
			Material.LIGHT_GRAY_TERRACOTTA, Material.CYAN_TERRACOTTA, Material.PURPLE_TERRACOTTA,
			Material.BLUE_TERRACOTTA, Material.BROWN_TERRACOTTA, Material.GREEN_TERRACOTTA, Material.RED_TERRACOTTA,
			Material.BLACK_TERRACOTTA, Material.WHITE_CONCRETE_POWDER, Material.ORANGE_CONCRETE_POWDER,
			Material.MAGENTA_CONCRETE_POWDER, Material.LIGHT_BLUE_CONCRETE_POWDER, Material.YELLOW_CONCRETE_POWDER,
			Material.LIME_CONCRETE_POWDER, Material.PINK_CONCRETE_POWDER, Material.GRAY_CONCRETE_POWDER,
			Material.LIGHT_GRAY_CONCRETE_POWDER, Material.CYAN_CONCRETE_POWDER, Material.PURPLE_CONCRETE_POWDER,
			Material.BLUE_CONCRETE_POWDER, Material.BROWN_CONCRETE_POWDER, Material.GREEN_CONCRETE_POWDER,
			Material.RED_CONCRETE_POWDER, Material.BLACK_CONCRETE_POWDER);

	private final static String tagSelectMaterial_AstralTowerLight = "Materials_For_AstralTowerLight";
	public final MaterialList itemsSelectMaterial_AstralTowerLight = createList(tagSelectMaterial_AstralTowerLight,
			Material.END_STONE, Material.END_STONE_BRICKS);

	private final static String tagSelectMaterial_AstralTowerDark = "Materials_For_AstralTowerDark";
	public final MaterialList itemsSelectMaterial_AstralTowerDark = createList(tagSelectMaterial_AstralTowerDark,
			Material.OBSIDIAN, Material.BLACK_CONCRETE);

	private final static String tagSelectMaterial_AstralTowerOres = "Materials_For_AstralTowerOres";
	public final MaterialList itemsSelectMaterial_AstralTowerOres = createList(tagSelectMaterial_AstralTowerOres,
			Material.LAVA, Material.WATER, Material.STONE, Material.INFESTED_STONE, Material.COAL_ORE,
			Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.GOLD_ORE, Material.IRON_ORE, Material.LAPIS_ORE,
			Material.NETHER_QUARTZ_ORE, Material.REDSTONE_ORE);

	private final static String tagSelectMaterial_AstralTowerHalls = "Materials_For_AstralTowerHalls";
	public final MaterialList itemsSelectMaterial_AstralTowerHalls = createList(tagSelectMaterial_AstralTowerHalls,
			Material.OBSIDIAN, Material.STONE, Material.BRICKS, Material.COBBLESTONE, Material.SMOOTH_STONE,
			Material.MOSSY_COBBLESTONE);

	private final static String tagSelectMaterial_AstralTowerTrim = "Materials_For_AstralTowerTrim";
	public final MaterialList itemsSelectMaterial_AstralTowerTrim = createList(tagSelectMaterial_AstralTowerTrim,
			Material.AIR, Material.GLOWSTONE);

	private final static String tagSelectMaterial_AstralCubeOres = "Materials_For_AstralCubeOres";
	public final MaterialList itemsSelectMaterial_AstralCubeOres = createList(tagSelectMaterial_AstralCubeOres, Material.DIRT,
			Material.STONE, Material.INFESTED_STONE, Material.INFESTED_CHISELED_STONE_BRICKS,
			Material.INFESTED_COBBLESTONE, Material.INFESTED_CRACKED_STONE_BRICKS, Material.INFESTED_STONE_BRICKS,
			Material.COBBLESTONE, Material.SPRUCE_PLANKS, Material.IRON_BLOCK, Material.COAL_BLOCK,
			Material.DIAMOND_BLOCK, Material.REDSTONE_BLOCK, Material.QUARTZ_BLOCK);

	private final static String tagMaterialListFor_MazeWalls = "Materials_List_For_MazeWalls";
	public final MaterialList itemsMaterialListFor_MazeWalls = createList(tagMaterialListFor_MazeWalls,

			// ORDER MATTERS IN THIS CASE
			Material.OBSIDIAN, // Walls
			Material.OBSIDIAN); // Underlayment

	private final static String tagMaterialListFor_Roads = "Materials_List_For_Roads";
	public final MaterialList itemsMaterialListFor_Roads = createList(tagMaterialListFor_Roads,

			// ORDER MATTERS IN THIS CASE
			Material.WHITE_TERRACOTTA, // Pavement
			Material.QUARTZ_BLOCK, // Lines
			Material.STONE_SLAB, // Sidewalks
			Material.GRASS_PATH, // Dirt roads
			Material.GRASS_PATH); // Dirt sidewalks

	private final static String tagMaterialListFor_NormalOres = "Materials_List_For_NormalOres";
	public final MaterialList itemsMaterialListFor_NormalOres = createList(tagMaterialListFor_NormalOres,

			// ORDER MATTERS IN THIS CASE
			Material.WATER, // liquid ore
			Material.LAVA, // alt liquid ore
			Material.GRAVEL, // gravel ore
			Material.COAL_ORE, // most frequent ore
			Material.IRON_ORE, //
			Material.GOLD_ORE, //
			Material.LAPIS_ORE, //
			Material.REDSTONE_ORE, // be
			Material.DIAMOND_ORE, //
			Material.EMERALD_ORE); // least frequent ore

	private final static String tagMaterialListFor_NetherOres = "Materials_List_For_NetherOres";
	public final MaterialList itemsMaterialListFor_NetherOres = createList(tagMaterialListFor_NetherOres,

			// ORDER MATTERS IN THIS CASE
			Material.LAVA, // liquid ore
			Material.LAVA, // alt liquid ore
			Material.MAGMA_BLOCK, // gravel ore
			Material.SOUL_SAND, // most frequent ore
			Material.GLOWSTONE, //
			Material.GLOWSTONE, //
			Material.NETHER_QUARTZ_ORE, //
			Material.MAGMA_BLOCK, //
			Material.SOUL_SAND, //
			Material.OBSIDIAN); // least frequent ore

	private final static String tagMaterialListFor_TheEndOres = "Materials_List_For_TheEndOres";
	public final MaterialList itemsMaterialListFor_TheEndOres = createList(tagMaterialListFor_TheEndOres,

			// ORDER MATTERS IN THIS CASE
			Material.WATER, // liquid ore
			Material.LAVA, // alt liquid ore
			Material.GRAVEL, // gravel ore
			Material.QUARTZ_BLOCK, // most frequent ore
			Material.GLOWSTONE, //
			Material.PURPUR_BLOCK, //
			Material.GOLD_ORE, //
			Material.LAPIS_ORE, //
			Material.DIAMOND_ORE, //
			Material.OBSIDIAN); // least frequent ore

	private List<MaterialList> listOfLists;

	public MaterialProvider(CityWorldGenerator generator) {
	}

	private MaterialList createList(String name, Material... materials) {

		// create the list and add all of the goodies
		MaterialList list = new MaterialList(name, materials);

		// add it to the big list so we can generically remember it
		if (listOfLists == null)
			listOfLists = new ArrayList<>();
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
