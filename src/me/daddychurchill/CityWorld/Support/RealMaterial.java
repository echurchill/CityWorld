package me.daddychurchill.CityWorld.Support;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public final class RealMaterial{

	// THESE REALLY SHOULD FOLLOW A BETTER NAMING SCHEME
	// <Material>[_<MaterialModifier>[[<FormModifier>_]_<Form>]]
	
//	public final static MaterialData AIR = define(Material.AIR);
//
//	public final static MaterialData STONE = define(Material.STONE);
//	public final static MaterialData GRANITE = define(Material.STONE, 1);
//	public final static MaterialData GRANITE_POLISHED = define(Material.STONE, 2);
//	public final static MaterialData DIORITE = define(Material.STONE, 3);
//	public final static MaterialData DIORIDE_POLISHED = define(Material.STONE, 4);
//	public final static MaterialData ANDESITE = define(Material.STONE, 5);
//	public final static MaterialData ANDESIDE_POLISHED = define(Material.STONE, 6);
//
//	public final static MaterialData GRASS = define(Material.GRASS);
//
//	public final static MaterialData DIRT = define(Material.DIRT);
//	public final static MaterialData DIRT_COARSE = define(Material.DIRT, 1);
//	public final static MaterialData PODZOL = define(Material.DIRT, 2);
//
//	public final static MaterialData COBBLESTONE = define(Material.COBBLESTONE);
//
//	public final static MaterialData WOOD = define(Material.WOOD);
//	public final static MaterialData WOOD_OAK = define(Material.WOOD, TreeSpecies.GENERIC);
//	public final static MaterialData WOOD_REDWOOD = define(Material.WOOD, TreeSpecies.REDWOOD);
//	public final static MaterialData WOOD_BIRCH = define(Material.WOOD, TreeSpecies.BIRCH);
//	public final static MaterialData WOOD_JUNGLE = define(Material.WOOD, TreeSpecies.JUNGLE);
//	public final static MaterialData WOOD_ACACIA = define(Material.WOOD, TreeSpecies.ACACIA);
//	public final static MaterialData WOOD_DARK_OAK = define(Material.WOOD, TreeSpecies.DARK_OAK);
//
//	public final static MaterialData SAPLING = define(Material.SAPLING);
//	public final static MaterialData SAPLING_OAK = define(Material.SAPLING, TreeSpecies.GENERIC);
//	public final static MaterialData SAPLING_REDWOOD = define(Material.SAPLING, TreeSpecies.REDWOOD);
//	public final static MaterialData SAPLING_BIRCH = define(Material.SAPLING, TreeSpecies.BIRCH);
//	public final static MaterialData SAPLING_JUNGLE = define(Material.SAPLING, TreeSpecies.JUNGLE);
//	public final static MaterialData SAPLING_ACACIA = define(Material.SAPLING, TreeSpecies.ACACIA);
//	public final static MaterialData SAPLING_DARK_OAK = define(Material.SAPLING, TreeSpecies.DARK_OAK);
//
//	public final static MaterialData BEDROCK = define(Material.BEDROCK);
//
//	public final static MaterialData FLOWING_WATER = define(Material.WATER, 0); //TODO: wrong name; data variant
//	public final static MaterialData WATER = define(Material.STATIONARY_WATER, 0); //TODO: wrong name; data variant
//	public final static MaterialData FLOWING_LAVA = define(Material.LAVA, 0); //TODO: wrong name; data variant
//	public final static MaterialData LAVA = define(Material.STATIONARY_LAVA, 0); //TODO: wrong name; data variant
//
//	public final static MaterialData SAND = define(Material.SAND);
//	public final static MaterialData SAND_RED = define(Material.SAND, 1);
//
//	public final static MaterialData GRAVEL = define(Material.GRAVEL);
//
//	public final static MaterialData GOLD_ORE = define(Material.GOLD_ORE);
//	public final static MaterialData IRON_ORE = define(Material.IRON_ORE);
//	public final static MaterialData COAL_ORE = define(Material.COAL_ORE);
//
//	public final static MaterialData LOG = define(Material.LOG);
//	public final static MaterialData LOG_OAK = define(Material.LOG, TreeSpecies.GENERIC);
//	public final static MaterialData LOG_REDWOOD = define(Material.LOG, TreeSpecies.REDWOOD);
//	public final static MaterialData LOG_BIRCH = define(Material.LOG, TreeSpecies.BIRCH);
//	public final static MaterialData LOG_JUNGLE = define(Material.LOG, TreeSpecies.JUNGLE);
//	public final static MaterialData LOG_ACACIA = define(Material.LOG, TreeSpecies.ACACIA);
//	public final static MaterialData LOG_DARK_OAK = define(Material.LOG, TreeSpecies.DARK_OAK);
//
//	public final static MaterialData LEAVES = define(Material.LEAVES);
//	public final static MaterialData LEAVES_OAK = define(Material.LEAVES, TreeSpecies.GENERIC);
//	public final static MaterialData LEAVES_REDWOOD = define(Material.LEAVES, TreeSpecies.REDWOOD);
//	public final static MaterialData LEAVES_BIRCH = define(Material.LEAVES, TreeSpecies.BIRCH);
//	public final static MaterialData LEAVES_JUNGLE = define(Material.LEAVES, TreeSpecies.JUNGLE);
//	public final static MaterialData LEAVES_ACACIA = define(Material.LEAVES, TreeSpecies.ACACIA);
//	public final static MaterialData LEAVES_DARK_OAK = define(Material.LEAVES, TreeSpecies.DARK_OAK);

	public final static MaterialData SPONGE = define(Material.SPONGE);
	public final static MaterialData SPONGE_WET = define(Material.SPONGE, 1);

//	public final static MaterialData GLASS = define(Material.GLASS);
//	public final static MaterialData LAPIS_ORE = define(Material.LAPIS_ORE);
//	public final static MaterialData LAPIS_BLOCK = define(Material.LAPIS_BLOCK);
//
//	public final static MaterialData DISPENSER = define(Material.DISPENSER, 0); //TODO: data variant
//
//	public final static MaterialData SANDSTONE = define(Material.SANDSTONE);
//	public final static MaterialData SANDSTONE_CHISELED = define(Material.RED_SANDSTONE, 1);
//	public final static MaterialData SANDSTONE_SMOOTH = define(Material.RED_SANDSTONE, 2);
//
//	public final static MaterialData NOTE_BLOCK = define(Material.NOTE_BLOCK); //TODO: wrong name
//
//	public final static MaterialData BED = define(Material.BED, 0); //TODO: data variant
//	public final static MaterialData POWERED_RAIL = define(Material.POWERED_RAIL, 0); //TODO: data variant
//	public final static MaterialData DETECTOR_RAIL = define(Material.DETECTOR_RAIL, 0); //TODO: data variant
//	public final static MaterialData STICKY_PISTON_BASE = define(Material.PISTON_STICKY_BASE, 0); //TODO: data variant
//
//	public final static MaterialData WEB = define(Material.WEB);
//
//	@Deprecated // Use TALL_GRASS instead
//	public final static MaterialData LONG_GRASS = define(Material.LONG_GRASS);
//	public final static MaterialData SHRUB = define(Material.LONG_GRASS, 0);
//	public final static MaterialData TALL_GRASS = define(Material.LONG_GRASS, 1);
//	public final static MaterialData FERN = define(Material.LONG_GRASS, 2);
//
//	public final static MaterialData DEAD_BUSH = define(Material.DEAD_BUSH);
//
//	public final static MaterialData PISTON_BASE = define(Material.PISTON_BASE, 0); //TODO: wrong name; data variant
//	public final static MaterialData PISTON_EXTENSION = define(Material.PISTON_EXTENSION, 0); //TODO: wrong name; data variant
//
//	public final static MaterialData WOOL = define(Material.WOOL);
	public final static MaterialData WOOL_WHITE = define(Material.WOOL ,DyeColor.WHITE);
	public final static MaterialData WOOL_ORANGE = define(Material.WOOL ,DyeColor.ORANGE);
	public final static MaterialData WOOL_MAGENTA = define(Material.WOOL ,DyeColor.MAGENTA);
	public final static MaterialData WOOL_LIGHT_BLUE = define(Material.WOOL ,DyeColor.LIGHT_BLUE);
	public final static MaterialData WOOL_YELLOW = define(Material.WOOL ,DyeColor.YELLOW);
	public final static MaterialData WOOL_LIME = define(Material.WOOL ,DyeColor.LIME);
	public final static MaterialData WOOL_PINK = define(Material.WOOL ,DyeColor.PINK);
	public final static MaterialData WOOL_GRAY = define(Material.WOOL ,DyeColor.GRAY);
	public final static MaterialData WOOL_SILVER = define(Material.WOOL ,DyeColor.SILVER);
	public final static MaterialData WOOL_CYAN = define(Material.WOOL ,DyeColor.CYAN);
	public final static MaterialData WOOL_PURPLE = define(Material.WOOL ,DyeColor.PURPLE);
	public final static MaterialData WOOL_BLUE = define(Material.WOOL ,DyeColor.BLUE);
	public final static MaterialData WOOL_BROWN = define(Material.WOOL ,DyeColor.BROWN);
	public final static MaterialData WOOL_GREEN = define(Material.WOOL ,DyeColor.GREEN);
	public final static MaterialData WOOL_RED = define(Material.WOOL ,DyeColor.RED);
	public final static MaterialData WOOL_BLACK = define(Material.WOOL ,DyeColor.BLACK);

//	public final static MaterialData PISTON_MOVING_PIECE = define(Material.PISTON_MOVING_PIECE, 0); //TODO: wrong name; data variant
//	
//	@Deprecated // Use FLOWER_DANDELION instead
//	public final static MaterialData YELLOW_FLOWER = define(Material.YELLOW_FLOWER);
//	public final static MaterialData FLOWER_DANDELION = define(Material.YELLOW_FLOWER);
//
//	@Deprecated // Use FLOWER_POPPY instead
//	public final static MaterialData RED_ROSE = define(Material.RED_ROSE);
//	public final static MaterialData FLOWER_POPPY = define(Material.RED_ROSE, 0);
//	public final static MaterialData FLOWER_BLUE_ORCHID = define(Material.RED_ROSE, 1);
//	public final static MaterialData FLOWER_ALLIUM = define(Material.RED_ROSE, 2);
//	public final static MaterialData FLOWER_AZURE_BLUET = define(Material.RED_ROSE, 3);
//	public final static MaterialData FLOWER_RED_TULIP = define(Material.RED_ROSE, 4);
//	public final static MaterialData FLOWER_ORANGE_TULIP = define(Material.RED_ROSE, 5);
//	public final static MaterialData FLOWER_WHITE_TULIP = define(Material.RED_ROSE, 6);
//	public final static MaterialData FLOWER_PINK_TULIP = define(Material.RED_ROSE, 7);
//	public final static MaterialData FLOWER_OXEYE_DAISY = define(Material.RED_ROSE, 8);
//	
//	@Deprecated // Use MUSHROOM_BROWN instead
//	public final static MaterialData BROWN_MUSHROOM = define(Material.BROWN_MUSHROOM);
//	public final static MaterialData MUSHROOM_BROWN = define(Material.BROWN_MUSHROOM);
//	@Deprecated // Use MUSHROOM_RED instead
//	public final static MaterialData RED_MUSHROOM = define(Material.RED_MUSHROOM);
//	public final static MaterialData MUSHROOM_RED = define(Material.RED_MUSHROOM);
//	public final static MaterialData GOLD_BLOCK = define(Material.GOLD_BLOCK);
//	public final static MaterialData IRON_BLOCK = define(Material.IRON_BLOCK);
//	
//	@Deprecated // Use STONE_DOUBLE_SLAB instead
//	public final static MaterialData DOUBLE_STEP = define(Material.DOUBLE_STEP, 0); //TODO: wrong name; data variant
//	public final static MaterialData STONE_DOUBLE_SLAB = define(Material.DOUBLE_STEP, 0);
//	public final static MaterialData SANDSTONE_DOUBLE_SLAB = define(Material.DOUBLE_STEP, 1);
//	public final static MaterialData STONEWOOD_DOUBLE_SLAB = define(Material.DOUBLE_STEP, 2);
//	public final static MaterialData COBBLESTONE_DOUBLE_SLAB = define(Material.DOUBLE_STEP, 3);
//	public final static MaterialData BRICK_DOUBLE_SLAB = define(Material.DOUBLE_STEP, 4);
//	public final static MaterialData SMOOTH_BRICK_DOUBLE_SLAB = define(Material.DOUBLE_STEP, 5);
//	public final static MaterialData NETHER_BRICK_DOUBLE_SLAB = define(Material.DOUBLE_STEP, 6);
//	public final static MaterialData QUARTZ_DOUBLE_SLAB = define(Material.DOUBLE_STEP, 7);
//	public final static MaterialData SANDSTONE_RED_DOUBLE_SLAB = define(Material.DOUBLE_STONE_SLAB2, 0);
//	
//	@Deprecated // Use STONE_SLAB instead
//	public final static MaterialData STEP = define(Material.STEP, 0); //TODO: wrong name; data variant
//	public final static MaterialData STONE_SLAB = define(Material.STEP, 0);
//	public final static MaterialData SANDSTONE_SLAB = define(Material.STEP, 1);
//	public final static MaterialData STONEWOOD_SLAB = define(Material.STEP, 2);
//	public final static MaterialData COBBLESTONE_SLAB = define(Material.STEP, 3);
//	public final static MaterialData BRICK_SLAB = define(Material.STEP, 4);
//	public final static MaterialData SMOOTH_BRICK_SLAB = define(Material.STEP, 5);
//	public final static MaterialData NETHER_BRICK_SLAB = define(Material.STEP, 6);
//	public final static MaterialData QUARTZ_SLAB = define(Material.STEP, 7);
//	public final static MaterialData SANDSTONE_RED_SLAB = define(Material.STONE_SLAB2, 0);
//	public final static MaterialData STONE_UPPER_SLAB = define(Material.STEP, 0 + 8);
//	public final static MaterialData SANDSTONE_UPPER_SLAB = define(Material.STEP, 1 + 8);
//	public final static MaterialData STONEWOOD_UPPER_SLAB = define(Material.STEP, 2 + 8);
//	public final static MaterialData COBBLESTONE_UPPER_SLAB = define(Material.STEP, 3 + 8);
//	public final static MaterialData BRICK_UPPER_SLAB = define(Material.STEP, 4 + 8);
//	public final static MaterialData SMOOTH_BRICK_UPPER_SLAB = define(Material.STEP, 5 + 8);
//	public final static MaterialData NETHER_BRICK_UPPER_SLAB = define(Material.STEP, 6 + 8);
//	public final static MaterialData QUARTZ_UPPER_SLAB = define(Material.STEP, 7 + 8);
//	public final static MaterialData SANDSTONE_RED_UPPER_SLAB = define(Material.STONE_SLAB2, 0 + 8);
//
//	public final static MaterialData BRICK = define(Material.BRICK); //TODO: wrong name
//	public final static MaterialData TNT = define(Material.TNT);
//	public final static MaterialData BOOKSHELF = define(Material.BOOKSHELF);
//
//	public final static MaterialData MOSSY_COBBLESTONE = define(Material.MOSSY_COBBLESTONE, 0); //TODO: wrong name; data variant
//	public final static MaterialData OBSIDIAN = define(Material.OBSIDIAN);
//	public final static MaterialData TORCH = define(Material.TORCH, 0); //TODO: data variant
//	public final static MaterialData FIRE = define(Material.FIRE, 0); //TODO: data variant
//	public final static MaterialData MOB_SPAWNER = define(Material.MOB_SPAWNER, 0); //TODO: data variant
//	public final static MaterialData WOOD_STAIRS = define(Material.WOOD_STAIRS, 0); //TODO: wrong name; data variant
//	public final static MaterialData CHEST = define(Material.CHEST, 0); //TODO: data variant
//	public final static MaterialData REDSTONE_WIRE = define(Material.REDSTONE_WIRE, 0); //TODO: data variant
//	
//	public final static MaterialData DIAMOND_ORE = define(Material.DIAMOND_ORE);
//	public final static MaterialData DIAMOND_BLOCK = define(Material.DIAMOND_BLOCK);
//	public final static MaterialData WORKBENCH = define(Material.WORKBENCH); //TODO: wrong name
//	public final static MaterialData CROPS = define(Material.CROPS, 0); //TODO: wrong name; data variant
//	public final static MaterialData SOIL = define(Material.SOIL, 0); //TODO: wrong name; data variant
//	public final static MaterialData FURNACE = define(Material.FURNACE, 0); //TODO: data variant
//	public final static MaterialData BURNING_FURNACE = define(Material.BURNING_FURNACE, 0); //TODO: wrong name; data variant
//	public final static MaterialData SIGN_POST = define(Material.SIGN_POST, 0); //TODO: wrong name; data variant
//	public final static MaterialData WOODEN_DOOR = define(Material.WOODEN_DOOR, 0); //TODO: data variant
//	public final static MaterialData LADDER = define(Material.LADDER, 0); //TODO: data variant
//	public final static MaterialData RAILS = define(Material.RAILS, 0); //TODO: wrong name; data variant
//	public final static MaterialData COBBLESTONE_STAIRS = define(Material.COBBLESTONE_STAIRS, 0); //TODO: wrong name; data variant
//	public final static MaterialData WALL_SIGN = define(Material.WALL_SIGN, 0); //TODO: data variant
//	public final static MaterialData LEVER = define(Material.LEVER, 0); //TODO: data variant
//	public final static MaterialData STONE_PLATE = define(Material.STONE_PLATE, 0); //TODO: wrong name; data variant
//	public final static MaterialData IRON_DOOR_BLOCK = define(Material.IRON_DOOR_BLOCK, 0); //TODO: wrong name; data variant
//	public final static MaterialData WOODEN_PLATE = define(Material.WOOD_PLATE, 0); //TODO: wrong name; data variant
//	public final static MaterialData REDSTONE_ORE = define(Material.REDSTONE_ORE);
//	public final static MaterialData GLOWING_REDSTONE_ORE = define(Material.GLOWING_REDSTONE_ORE); //TODO: wrong name
//	public final static MaterialData REDSTONE_TORCH_OFF = define(Material.REDSTONE_TORCH_OFF, 0); //TODO: wrong name; data variant
//	public final static MaterialData REDSTONE_TORCH_ON = define(Material.REDSTONE_TORCH_ON, 0); //TODO: wrong name; data variant
//	public final static MaterialData STONE_BUTTON = define(Material.STONE_BUTTON, 0); //TODO: wrong name; data variant
//	public final static MaterialData SNOW = define(Material.SNOW, 0); //TODO: wrong name; data variant
//	public final static MaterialData ICE = define(Material.ICE);
//	public final static MaterialData SNOW_BLOCK = define(Material.SNOW_BLOCK); //TODO: wrong name
//	public final static MaterialData CACTUS = define(Material.CACTUS, 0); //TODO: data variant
//	public final static MaterialData CLAY = define(Material.CLAY);
//	public final static MaterialData SUGAR_CANE_BLOCK = define(Material.SUGAR_CANE_BLOCK, 0); //TODO: wrong name; data variant
//	public final static MaterialData JUKEBOX = define(Material.JUKEBOX, 0); //TODO: data variant
//	public final static MaterialData FENCE = define(Material.FENCE);
//	public final static MaterialData PUMPKIN = define(Material.PUMPKIN, 0); //TODO: data variant
//	public final static MaterialData NETHERRACK = define(Material.NETHERRACK);
//	public final static MaterialData SOUL_SAND = define(Material.SOUL_SAND, 0); //TODO: data variant
//	public final static MaterialData GLOWSTONE = define(Material.GLOWSTONE);
//	public final static MaterialData PORTAL = define(Material.PORTAL);
//	public final static MaterialData JACK_O_LATERN = define(Material.JACK_O_LANTERN, 0); //TODO: wrong name; data variant
//	public final static MaterialData CAKE_BLOCK = define(Material.CAKE_BLOCK, 0); //TODO: wrong name; data variant
//	public final static MaterialData DIODE_BLOCK_OFF = define(Material.DIODE_BLOCK_OFF, 0); //TODO: wrong name; data variant
//	public final static MaterialData DIODE_BLOCK_ON = define(Material.DIODE_BLOCK_ON, 0); //TODO: wrong name; data variant
//	
//	public final static MaterialData STAINED_GLASS = define(Material.STAINED_GLASS);
//	public final static MaterialData STAINED_GLASS_WHITE = define(Material.STAINED_GLASS ,DyeColor.WHITE);
//	public final static MaterialData STAINED_GLASS_ORANGE = define(Material.STAINED_GLASS ,DyeColor.ORANGE);
//	public final static MaterialData STAINED_GLASS_MAGENTA = define(Material.STAINED_GLASS ,DyeColor.MAGENTA);
//	public final static MaterialData STAINED_GLASS_LIGHT_BLUE = define(Material.STAINED_GLASS ,DyeColor.LIGHT_BLUE);
//	public final static MaterialData STAINED_GLASS_YELLOW = define(Material.STAINED_GLASS ,DyeColor.YELLOW);
//	public final static MaterialData STAINED_GLASS_LIME = define(Material.STAINED_GLASS ,DyeColor.LIME);
//	public final static MaterialData STAINED_GLASS_PINK = define(Material.STAINED_GLASS ,DyeColor.PINK);
//	public final static MaterialData STAINED_GLASS_GRAY = define(Material.STAINED_GLASS ,DyeColor.GRAY);
//	public final static MaterialData STAINED_GLASS_SILVER = define(Material.STAINED_GLASS ,DyeColor.SILVER);
//	public final static MaterialData STAINED_GLASS_CYAN = define(Material.STAINED_GLASS ,DyeColor.CYAN);
//	public final static MaterialData STAINED_GLASS_PURPLE = define(Material.STAINED_GLASS ,DyeColor.PURPLE);
//	public final static MaterialData STAINED_GLASS_BLUE = define(Material.STAINED_GLASS ,DyeColor.BLUE);
//	public final static MaterialData STAINED_GLASS_BROWN = define(Material.STAINED_GLASS ,DyeColor.BROWN);
//	public final static MaterialData STAINED_GLASS_GREEN = define(Material.STAINED_GLASS ,DyeColor.GREEN);
//	public final static MaterialData STAINED_GLASS_RED = define(Material.STAINED_GLASS ,DyeColor.RED);
//	public final static MaterialData STAINED_GLASS_BLACK = define(Material.STAINED_GLASS ,DyeColor.BLACK);
//	
//	public final static MaterialData TRAP_DOOR = define(Material.TRAP_DOOR, 0); //TODO: wrong name; data variant
//	public final static MaterialData MONSTER_EGGS = define(Material.MONSTER_EGGS, 0); //TODO: wrong name; data variant
//
//	public final static MaterialData SMOOTH_BRICK = define(Material.SMOOTH_BRICK);
//	public final static MaterialData SMOOTH_BRICK_MOSSY = define(Material.SMOOTH_BRICK, 1);
//	public final static MaterialData SMOOTH_BRICK_CRACKED = define(Material.SMOOTH_BRICK, 2);
//	public final static MaterialData SMOOTH_BRICK_CHISELED = define(Material.SMOOTH_BRICK, 3);
//
//	public final static MaterialData HUGE_MUSHROOM_1 = define(Material.HUGE_MUSHROOM_1, 0); //TODO: wrong name; data variant
//	public final static MaterialData HUGE_MUSHROOM_2 = define(Material.HUGE_MUSHROOM_2, 0); //TODO: wrong name; data variant
//	public final static MaterialData IRON_FENCE = define(Material.IRON_FENCE); //TODO: wrong name
//	public final static MaterialData THIN_GLASS = define(Material.THIN_GLASS); //TODO: wrong name
//	public final static MaterialData MELON_BLOCK = define(Material.MELON_BLOCK);
//	public final static MaterialData PUMPKIN_STEM = define(Material.PUMPKIN_STEM, 0); //TODO: data variant
//	public final static MaterialData MELON_STEM = define(Material.MELON_STEM, 0); //TODO: data variant
//	public final static MaterialData VINE = define(Material.VINE, 0); //TODO: data variant
//	public final static MaterialData FENCE_GATE = define(Material.FENCE_GATE, 0); //TODO: data variant
//	public final static MaterialData BRICK_STAIRS = define(Material.BRICK_STAIRS, 0); //TODO: data variant
//	public final static MaterialData SMOOTH_STAIRS = define(Material.SMOOTH_STAIRS, 0); //TODO: wrong name; data variant
//	public final static MaterialData MYCEL = define(Material.MYCEL); //TODO: wrong name
//	public final static MaterialData WATER_LILY = define(Material.WATER_LILY); //TODO: wrong name
//	public final static MaterialData NETHER_BRICK = define(Material.NETHER_BRICK);
//	public final static MaterialData NETHER_FENCE = define(Material.NETHER_FENCE); //TODO: wrong name
//	public final static MaterialData NETHER_BRICK_STAIRS = define(Material.NETHER_BRICK_STAIRS, 0); //TODO: data variant
//	public final static MaterialData NETHER_WARTS = define(Material.NETHER_WARTS, 0); //TODO: wrong name; data variant
//	public final static MaterialData ENCHANTMENT_TABLE = define(Material.ENCHANTMENT_TABLE); //TODO: wrong name
//	public final static MaterialData BREWING_STAND = define(Material.BREWING_STAND, 0); //TODO: data variant
//	public final static MaterialData CAULDRON = define(Material.CAULDRON, 0); //TODO: data variant
//	public final static MaterialData ENDER_PORTAL = define(Material.ENDER_PORTAL); //TODO: wrong name
//	public final static MaterialData ENDER_PORTAL_FRAME = define(Material.ENDER_PORTAL_FRAME, 0); //TODO: wrong name; data variant
//	public final static MaterialData ENDER_STONE = define(Material.ENDER_STONE, 0); //TODO: wrong name; data variant
//	public final static MaterialData DRAGON_EGG = define(Material.DRAGON_EGG);
//	public final static MaterialData REDSTONE_LAMP_OFF = define(Material.REDSTONE_LAMP_OFF); //TODO: wrong name
//	public final static MaterialData REDSTONE_LAMP_ON = define(Material.REDSTONE_LAMP_ON); //TODO: wrong name
//
//	@Deprecated // Use WOOD_OAK_DOUBLE_SLAB instead
//	public final static MaterialData WOOD_DOUBLE_STEP = define(Material.WOOD_DOUBLE_STEP);
//	//public final static MaterialData WOOD_DOUBLE_SLAB = define(Material.WOOD_DOUBLE_STEP);
//	public final static MaterialData WOOD_OAK_DOUBLE_SLAB = define(Material.WOOD_DOUBLE_STEP, TreeSpecies.GENERIC);
//	public final static MaterialData WOOD_REDWOOD_DOUBLE_SLAB = define(Material.WOOD_DOUBLE_STEP, TreeSpecies.REDWOOD);
//	public final static MaterialData WOOD_BIRCH_DOUBLE_SLAB = define(Material.WOOD_DOUBLE_STEP, TreeSpecies.BIRCH);
//	public final static MaterialData WOOD_JUNGLE_DOUBLE_SLAB = define(Material.WOOD_DOUBLE_STEP, TreeSpecies.JUNGLE);
//	public final static MaterialData WOOD_ACACIA_DOUBLE_SLAB = define(Material.WOOD_DOUBLE_STEP, TreeSpecies.ACACIA);
//	public final static MaterialData WOOD_DARK_OAK_DOUBLE_SLAB = define(Material.WOOD_DOUBLE_STEP, TreeSpecies.DARK_OAK);
//
//	@Deprecated // Use WOOD_OAK_SLAB instead
//	public final static MaterialData WOOD_STEP = define(Material.WOOD_STEP);
//	//public final static MaterialData WOOD_SLAB = define(Material.WOOD_STEP);
//	public final static MaterialData WOOD_OAK_SLAB = define(Material.WOOD_STEP, TreeSpecies.GENERIC);
//	public final static MaterialData WOOD_REDWOOD_SLAB = define(Material.WOOD_STEP, TreeSpecies.REDWOOD);
//	public final static MaterialData WOOD_BIRCH_SLAB = define(Material.WOOD_STEP, TreeSpecies.BIRCH);
//	public final static MaterialData WOOD_JUNGLE_SLAB = define(Material.WOOD_STEP, TreeSpecies.JUNGLE);
//	public final static MaterialData WOOD_ACACIA_SLAB = define(Material.WOOD_STEP, TreeSpecies.ACACIA);
//	public final static MaterialData WOOD_DARK_OAK_SLAB = define(Material.WOOD_STEP, TreeSpecies.DARK_OAK);
//
//	public final static MaterialData COCOA = define(Material.COCOA, 0); //TODO: wrong name; data variant
//	public final static MaterialData SANDSTONE_STAIRS = define(Material.SANDSTONE_STAIRS, 0); //TODO: data variant
//	public final static MaterialData EMERALD_ORE = define(Material.EMERALD_ORE);
//	public final static MaterialData ENDER_CHEST = define(Material.ENDER_CHEST, 0); //TODO: data variant
//	public final static MaterialData TRIPWIRE_HOOK = define(Material.TRIPWIRE_HOOK, 0); //TODO: data variant
//	public final static MaterialData TRIPWIRE = define(Material.TRIPWIRE, 0); //TODO: data variant
//	public final static MaterialData EMERALD_BLOCK = define(Material.EMERALD_BLOCK);
//	public final static MaterialData SPRUCE_WOOD_STAIRS = define(Material.SPRUCE_WOOD_STAIRS, 0); //TODO: wrong name; data variant
//	public final static MaterialData BIRCH_WOOD_STAIRS = define(Material.BIRCH_WOOD_STAIRS, 0); //TODO: wrong name; data variant
//	public final static MaterialData JUNGLE_WOOD_STAIRS = define(Material.JUNGLE_WOOD_STAIRS, 0); //TODO: wrong name; data variant
//	public final static MaterialData COMMAND = define(Material.COMMAND); //TODO: wrong name
//	public final static MaterialData BEACON = define(Material.BEACON);
//
//	public final static MaterialData COBBLE_WALL = define(Material.COBBLE_WALL);
//	public final static MaterialData COBBLE_WALL_MOSSY = define(Material.COBBLE_WALL, 1);
//
//	public final static MaterialData FLOWER_POT = define(Material.FLOWER_POT, 0); //TODO: data variant
//	public final static MaterialData CARROT = define(Material.CARROT, 0); //TODO: wrong name; data variant
//	public final static MaterialData POTATO = define(Material.POTATO, 0); //TODO: wrong name; data variant
//	public final static MaterialData WOOD_BUTTON = define(Material.WOOD_BUTTON, 0); //TODO: wrong name; data variant
//	public final static MaterialData SKULL = define(Material.SKULL, 0); //TODO: data variant
//	public final static MaterialData ANVIL = define(Material.ANVIL, 0); //TODO: data variant
//	public final static MaterialData TRAPPED_CHEST = define(Material.TRAPPED_CHEST, 0); //TODO: data variant
//	public final static MaterialData GOLD_PLATE = define(Material.GOLD_PLATE, 0); //TODO: wrong name; data variant
//	public final static MaterialData IRON_PLATE = define(Material.IRON_PLATE, 0); //TODO: wrong name; data variant
//	public final static MaterialData REDSTONE_COMPARATOR_OFF = define(Material.REDSTONE_COMPARATOR_OFF, 0); //TODO: wrong name; data variant
//	public final static MaterialData REDSTONE_COMPARATOR_ON = define(Material.REDSTONE_COMPARATOR_OFF, 0); //TODO: wrong name; data variant
//	public final static MaterialData DAYLIGHT_DETECTOR = define(Material.DAYLIGHT_DETECTOR, 0); //TODO: data variant
//	public final static MaterialData REDSTONE_BLOCK = define(Material.REDSTONE_BLOCK);
//	public final static MaterialData QUARTZ_ORE = define(Material.QUARTZ_ORE);
//	public final static MaterialData HOPPER = define(Material.HOPPER, 0); //TODO: data variant
//
//	public final static MaterialData QUARTZ_BLOCK = define(Material.QUARTZ_BLOCK);
//	public final static MaterialData QUARTZ_CHISELED = define(Material.QUARTZ_BLOCK, 1);
//	public final static MaterialData QUARTZ_PILLAR = define(Material.QUARTZ_BLOCK, 2);
//	public final static MaterialData QUARTZ_PILLAR_NS = define(Material.QUARTZ_BLOCK, 3);
//	public final static MaterialData QUARTZ_PILLAR_EW = define(Material.QUARTZ_BLOCK, 4);
//
//	public final static MaterialData QUARTZ_STAIRS = define(Material.QUARTZ_STAIRS, 0); //TODO: data variant
//	public final static MaterialData ACTIVATOR_RAIL = define(Material.ACTIVATOR_RAIL, 0); //TODO: data variant
//	public final static MaterialData DROPPER = define(Material.DROPPER, 0); //TODO: data variant
//
//	public final static MaterialData STAINED_CLAY = define(Material.STAINED_CLAY); //TODO: wrong name
//	public final static MaterialData STAINED_CLAY_WHITE = define(Material.STAINED_CLAY ,DyeColor.WHITE); //TODO: wrong name
//	public final static MaterialData STAINED_CLAY_ORANGE = define(Material.STAINED_CLAY ,DyeColor.ORANGE); //TODO: wrong name
//	public final static MaterialData STAINED_CLAY_MAGENTA = define(Material.STAINED_CLAY ,DyeColor.MAGENTA); //TODO: wrong name
//	public final static MaterialData STAINED_CLAY_LIGHT_BLUE = define(Material.STAINED_CLAY ,DyeColor.LIGHT_BLUE); //TODO: wrong name
//	public final static MaterialData STAINED_CLAY_YELLOW = define(Material.STAINED_CLAY ,DyeColor.YELLOW); //TODO: wrong name
//	public final static MaterialData STAINED_CLAY_LIME = define(Material.STAINED_CLAY ,DyeColor.LIME); //TODO: wrong name
//	public final static MaterialData STAINED_CLAY_PINK = define(Material.STAINED_CLAY ,DyeColor.PINK); //TODO: wrong name
//	public final static MaterialData STAINED_CLAY_GRAY = define(Material.STAINED_CLAY ,DyeColor.GRAY); //TODO: wrong name
//	public final static MaterialData STAINED_CLAY_SILVER = define(Material.STAINED_CLAY ,DyeColor.SILVER); //TODO: wrong name
//	public final static MaterialData STAINED_CLAY_CYAN = define(Material.STAINED_CLAY ,DyeColor.CYAN); //TODO: wrong name
//	public final static MaterialData STAINED_CLAY_PURPLE = define(Material.STAINED_CLAY ,DyeColor.PURPLE); //TODO: wrong name
//	public final static MaterialData STAINED_CLAY_BLUE = define(Material.STAINED_CLAY ,DyeColor.BLUE); //TODO: wrong name
//	public final static MaterialData STAINED_CLAY_BROWN = define(Material.STAINED_CLAY ,DyeColor.BROWN); //TODO: wrong name
//	public final static MaterialData STAINED_CLAY_GREEN = define(Material.STAINED_CLAY ,DyeColor.GREEN); //TODO: wrong name
//	public final static MaterialData STAINED_CLAY_RED = define(Material.STAINED_CLAY ,DyeColor.RED); //TODO: wrong name
//	public final static MaterialData STAINED_CLAY_BLACK = define(Material.STAINED_CLAY ,DyeColor.BLACK); //TODO: wrong name
//
//	public final static MaterialData STAINED_GLASS_PANE = define(Material.STAINED_GLASS_PANE);
//	public final static MaterialData STAINED_GLASS_PANE_WHITE = define(Material.STAINED_GLASS_PANE ,DyeColor.WHITE);
//	public final static MaterialData STAINED_GLASS_PANE_ORANGE = define(Material.STAINED_GLASS_PANE ,DyeColor.ORANGE);
//	public final static MaterialData STAINED_GLASS_PANE_MAGENTA = define(Material.STAINED_GLASS_PANE ,DyeColor.MAGENTA);
//	public final static MaterialData STAINED_GLASS_PANE_LIGHT_BLUE = define(Material.STAINED_GLASS_PANE ,DyeColor.LIGHT_BLUE);
//	public final static MaterialData STAINED_GLASS_PANE_YELLOW = define(Material.STAINED_GLASS_PANE ,DyeColor.YELLOW);
//	public final static MaterialData STAINED_GLASS_PANE_LIME = define(Material.STAINED_GLASS_PANE ,DyeColor.LIME);
//	public final static MaterialData STAINED_GLASS_PANE_PINK = define(Material.STAINED_GLASS_PANE ,DyeColor.PINK);
//	public final static MaterialData STAINED_GLASS_PANE_GRAY = define(Material.STAINED_GLASS_PANE ,DyeColor.GRAY);
//	public final static MaterialData STAINED_GLASS_PANE_SILVER = define(Material.STAINED_GLASS_PANE ,DyeColor.SILVER);
//	public final static MaterialData STAINED_GLASS_PANE_CYAN = define(Material.STAINED_GLASS_PANE ,DyeColor.CYAN);
//	public final static MaterialData STAINED_GLASS_PANE_PURPLE = define(Material.STAINED_GLASS_PANE ,DyeColor.PURPLE);
//	public final static MaterialData STAINED_GLASS_PANE_BLUE = define(Material.STAINED_GLASS_PANE ,DyeColor.BLUE);
//	public final static MaterialData STAINED_GLASS_PANE_BROWN = define(Material.STAINED_GLASS_PANE ,DyeColor.BROWN);
//	public final static MaterialData STAINED_GLASS_PANE_GREEN = define(Material.STAINED_GLASS_PANE ,DyeColor.GREEN);
//	public final static MaterialData STAINED_GLASS_PANE_RED = define(Material.STAINED_GLASS_PANE ,DyeColor.RED);
//	public final static MaterialData STAINED_GLASS_PANE_BLACK = define(Material.STAINED_GLASS_PANE ,DyeColor.BLACK);
//	
//	@Deprecated
//	public final static MaterialData LEAVES_2 = define(Material.LEAVES_2);
//	@Deprecated
//	public final static MaterialData LOG_2 = define(Material.LOG_2);
//	
//	public final static MaterialData ACACIA_STAIRS = define(Material.ACACIA_STAIRS, 0); //TODO: data variant
//	public final static MaterialData DARK_OAK_STAIRS = define(Material.DARK_OAK_STAIRS, 0); //TODO: data variant
//	public final static MaterialData SLIME_BLOCK = define(Material.SLIME_BLOCK); //TODO: wrong name
//	public final static MaterialData BARRIER = define(Material.BARRIER);
//	public final static MaterialData IRON_TRAPDOOR = define(Material.IRON_TRAPDOOR, 0); //TODO: data variant
//
//	public final static MaterialData PRISMARINE = define(Material.PRISMARINE);
//	public final static MaterialData PRISMARINE_BRICKS = define(Material.PRISMARINE, 1);
//	public final static MaterialData PRISMARINE_DARK = define(Material.PRISMARINE, 2);
//
//	public final static MaterialData SEA_LANTERN = define(Material.SEA_LANTERN);
//	public final static MaterialData HAY_BLOCK = define(Material.HAY_BLOCK, 0); //TODO: data variant
//
//	public final static MaterialData CARPET = define(Material.CARPET);
//	public final static MaterialData CARPET_WHITE = define(Material.CARPET ,DyeColor.WHITE);
//	public final static MaterialData CARPET_ORANGE = define(Material.CARPET ,DyeColor.ORANGE);
//	public final static MaterialData CARPET_MAGENTA = define(Material.CARPET ,DyeColor.MAGENTA);
//	public final static MaterialData CARPET_LIGHT_BLUE = define(Material.CARPET ,DyeColor.LIGHT_BLUE);
//	public final static MaterialData CARPET_YELLOW = define(Material.CARPET ,DyeColor.YELLOW);
//	public final static MaterialData CARPET_LIME = define(Material.CARPET ,DyeColor.LIME);
//	public final static MaterialData CARPET_PINK = define(Material.CARPET ,DyeColor.PINK);
//	public final static MaterialData CARPET_GRAY = define(Material.CARPET ,DyeColor.GRAY);
//	public final static MaterialData CARPET_SILVER = define(Material.CARPET ,DyeColor.SILVER);
//	public final static MaterialData CARPET_CYAN = define(Material.CARPET ,DyeColor.CYAN);
//	public final static MaterialData CARPET_PURPLE = define(Material.CARPET ,DyeColor.PURPLE);
//	public final static MaterialData CARPET_BLUE = define(Material.CARPET ,DyeColor.BLUE);
//	public final static MaterialData CARPET_BROWN = define(Material.CARPET ,DyeColor.BROWN);
//	public final static MaterialData CARPET_GREEN = define(Material.CARPET ,DyeColor.GREEN);
//	public final static MaterialData CARPET_RED = define(Material.CARPET ,DyeColor.RED);
//	public final static MaterialData CARPET_BLACK = define(Material.CARPET ,DyeColor.BLACK);
//
//	public final static MaterialData HARD_CLAY = define(Material.HARD_CLAY); //TODO: wrong name
//	public final static MaterialData COAL_BLOCK = define(Material.COAL_BLOCK);
//	public final static MaterialData PACKED_ICE = define(Material.PACKED_ICE);
//	public final static MaterialData DOUBLE_PLANT = define(Material.DOUBLE_PLANT, 0); //TODO: data variant
//	public final static MaterialData STANDING_BANNER = define(Material.STANDING_BANNER, 0); //TODO: data variant
//	public final static MaterialData WALL_BANNER = define(Material.WALL_BANNER, 0); //TODO: data variant
//	public final static MaterialData DAYLIGHT_DETECTOR_INVERTED = define(Material.DAYLIGHT_DETECTOR_INVERTED, 0); //TODO: data variant
//	
//	public final static MaterialData RED_SANDSTONE = define(Material.RED_SANDSTONE);
//	public final static MaterialData RED_SANDSTONE_CHISELED = define(Material.RED_SANDSTONE, 1);
//	public final static MaterialData RED_SANDSTONE_SMOOTH = define(Material.RED_SANDSTONE, 2);
//
//	public final static MaterialData RED_SANDSTONE_STAIRS = define(Material.RED_SANDSTONE_STAIRS, 0); //TODO: data variant
//	public final static MaterialData DOUBLE_STONE_SLAB2 = define(Material.DOUBLE_STONE_SLAB2, 0); //TODO: data variant
//	public final static MaterialData STONE_SLAB2 = define(Material.STONE_SLAB2, 0); //TODO: data variant
//	public final static MaterialData SPRUCE_FENCE_GATE = define(Material.SPRUCE_FENCE_GATE);
//	public final static MaterialData BIRCH_FENCE_GATE = define(Material.BIRCH_FENCE_GATE);
//	public final static MaterialData JUNGLE_FENCE_GATE = define(Material.JUNGLE_FENCE_GATE);
//	public final static MaterialData DARK_OAK_FENCE_GATE = define(Material.DARK_OAK_FENCE_GATE);
//	public final static MaterialData ACACIA_FENCE_GATE = define(Material.ACACIA_FENCE_GATE);
//	public final static MaterialData SPRUCE_FENCE = define(Material.SPRUCE_FENCE);
//	public final static MaterialData BIRCH_FENCE = define(Material.BIRCH_FENCE);
//	public final static MaterialData JUNGLE_FENCE = define(Material.JUNGLE_FENCE);
//	public final static MaterialData DARK_OAK_FENCE = define(Material.DARK_OAK_FENCE);
//	public final static MaterialData ACACIA_FENCE = define(Material.ACACIA_FENCE);
//	public final static MaterialData SPRUCE_DOOR = define(Material.SPRUCE_DOOR, 0); //TODO: data variant
//	public final static MaterialData BIRCH_DOOR = define(Material.BIRCH_DOOR, 0); //TODO: data variant
//	public final static MaterialData JUNGLE_DOOR = define(Material.JUNGLE_DOOR, 0); //TODO: data variant
//	public final static MaterialData ACACIA_DOOR = define(Material.ACACIA_DOOR, 0); //TODO: data variant
//	public final static MaterialData DARK_OAK_DOOR = define(Material.DARK_OAK_DOOR, 0); //TODO: data variant
//
//	public final static MaterialData END_ROD = define(Material.END_ROD);
//	public final static MaterialData CHORUS_PLANT = define(Material.CHORUS_PLANT);
//	public final static MaterialData CHORUS_FLOWER = define(Material.CHORUS_FLOWER);
//	public final static MaterialData PURPUR_BLOCK = define(Material.PURPUR_BLOCK);
//	public final static MaterialData PURPUR_PILLAR = define(Material.PURPUR_PILLAR);
//	public final static MaterialData PURPUR_STAIRS = define(Material.PURPUR_STAIRS, 0); //TODO: data variant
//	public final static MaterialData PURPUR_DOUBLE_SLAB = define(Material.PURPUR_DOUBLE_SLAB, 0); //TODO: data variant
//	public final static MaterialData PURPUR_SLAB = define(Material.PURPUR_SLAB, 0); //TODO: data variant
//	public final static MaterialData END_BRICKS = define(Material.END_BRICKS, 0); //TODO: data variant
//	public final static MaterialData BEETROOT_BLOCK = define(Material.BEETROOT_BLOCK, 0); //TODO: wrong name; data variant
//	public final static MaterialData GRASS_PATH = define(Material.GRASS_PATH);
//	public final static MaterialData END_GATEWAY = define(Material.END_GATEWAY);
//	public final static MaterialData COMMAND_REPEATING = define(Material.COMMAND_REPEATING); //TODO: wrong name
//	public final static MaterialData COMMAND_CHAIN = define(Material.COMMAND_CHAIN); //TODO: wrong name
//	public final static MaterialData FROSTED_ICE = define(Material.FROSTED_ICE, 0); //TODO: data variant
//	public final static MaterialData MAGMA = define(Material.MAGMA);
//	public final static MaterialData NETHER_WART_BLOCK = define(Material.NETHER_WART_BLOCK);
//	public final static MaterialData RED_NETHER_BRICK = define(Material.RED_NETHER_BRICK);
//	public final static MaterialData BONE_BLOCK = define(Material.BONE_BLOCK);
//	public final static MaterialData STRUCTURE_VOID = define(Material.STRUCTURE_VOID);
//	public final static MaterialData STRUCTURE_BLOCK = define(Material.STRUCTURE_BLOCK, 0); //TODO: data variant

	@SuppressWarnings("deprecation")
	private final static MaterialData createData(Material material, int data) {
		assert(material.getId() > 255);
		return new MaterialData(material, (byte)data);
	}
	
	private static MaterialData define(Material material) {
		return createData(material, 0);
	}
	
	private static MaterialData define(Material material, int data) {
		return createData(material, (byte)data);
	}
	
	@SuppressWarnings("deprecation")
	private static MaterialData define(Material material, DyeColor color) {
		return createData(material, color.getDyeData());
	}
	
//	private static MaterialData define (Material material, TreeSpecies species) {
//		switch (material) {
//		case WOOD:
//		case SAPLING:
//			switch (species) {
//			case GENERIC:
//				return createData(Material.WOOD, 0);
//			case REDWOOD:
//				return createData(Material.WOOD, 1);
//			case BIRCH:
//				return createData(Material.WOOD, 2);
//			case JUNGLE:
//				return createData(Material.WOOD, 3);
//			case ACACIA:
//				return createData(Material.WOOD, 4);
//			case DARK_OAK:
//				return createData(Material.WOOD, 5);
//			default:
//				break;
//			}
//		case LOG:
//		case LOG_2:
//			switch (species) {
//			case GENERIC:
//				return createData(Material.LOG, 0);
//			case REDWOOD:
//				return createData(Material.LOG, 1);
//			case BIRCH:
//				return createData(Material.LOG, 2);
//			case JUNGLE:
//				return createData(Material.LOG, 3);
//			case ACACIA:
//				return createData(Material.LOG_2, 0);
//			case DARK_OAK:
//				return createData(Material.LOG_2, 1);
//			default:
//				break;
//			}
//		case LEAVES:
//		case LEAVES_2:
//			switch (species) {
//			case GENERIC:
//				return createData(Material.LEAVES, 0);
//			case REDWOOD:
//				return createData(Material.LEAVES, 1);
//			case BIRCH:
//				return createData(Material.LEAVES, 2);
//			case JUNGLE:
//				return createData(Material.LEAVES, 3);
//			case ACACIA:
//				return createData(Material.LEAVES_2, 0);
//			case DARK_OAK:
//				return createData(Material.LEAVES_2, 1);
//			default:
//				break;
//			}
//		case WOOD_STEP:
//		case STEP: // this will be converted to the above
//			switch (species) {
//			case GENERIC:
//				return createData(Material.WOOD_STEP, 0);
//			case REDWOOD:
//				return createData(Material.WOOD_STEP, 1);
//			case BIRCH:
//				return createData(Material.WOOD_STEP, 2);
//			case JUNGLE:
//				return createData(Material.WOOD_STEP, 3);
//			case ACACIA:
//				return createData(Material.WOOD_STEP, 4);
//			case DARK_OAK:
//				return createData(Material.WOOD_STEP, 5);
//			default:
//				break;
//			}
//		case WOOD_DOUBLE_STEP:
//		case DOUBLE_STEP: // this will be converted to the above
//			switch (species) {
//			case GENERIC:
//				return createData(Material.WOOD_DOUBLE_STEP, 0);
//			case REDWOOD:
//				return createData(Material.WOOD_DOUBLE_STEP, 1);
//			case BIRCH:
//				return createData(Material.WOOD_DOUBLE_STEP, 2);
//			case JUNGLE:
//				return createData(Material.WOOD_DOUBLE_STEP, 3);
//			case ACACIA:
//				return createData(Material.WOOD_DOUBLE_STEP, 4);
//			case DARK_OAK:
//				return createData(Material.WOOD_DOUBLE_STEP, 5);
//			default:
//				break;
//			}
//		default:
//			break;
//		}
//		
//		// if we got here, something went wrong
//	    throw new IllegalArgumentException("Invalid block type for tree species");
//	}
	
	public static MaterialData getStainedGlass(Odds odds) {
		return define(Material.STAINED_GLASS, odds.getRandomColor());
	}
	
//	public final MaterialData getData() {
//		this.getClass().getDeclaredFields()[0].getType().is
//		this.getClass().getDeclaredMethods()[0].getReturnType() == MaterialData;
//		this.getClass().getDeclaredMethods()[0].getParameterCount() == 0;
//		return this.data;
//	}
	
//	private static final Map<String, MaterialData> list;
	
//	public final MaterialData getMaterialData(String name) {
//		if (list == null) {
//			list = new TreeMap<String, MaterialData>();
//			Field[] fields = getClass().getDeclaredFields();
//			for (int i = 0; i < fields.length; i++) {
//				fields[i].getAnnotation(java.lang.);
//				list.put(fields[i].getName(), fields[i].)
//			}
//		}
//TODO: Open include standard ones?
//		Material material = Material.getMaterial(name);
//		if (material != null) {
//			return new MaterialData(material);
//		} else {
//			RealMaterial realMaterial = list.get(name);
//			if (realMaterial != null) {
//				return realMaterial.getData();
//			}
//		}
//		return null;
//	}

//	static {
//		get
//		int count = materials.length;
//		for (int i = 0; i < count; i++) {
//			list.put(materials[i].name(), materials[i]);
//		}
//		
//	}
}
