package me.daddychurchill.CityWorld.Support;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.material.MaterialData;

public enum RealMaterial2{

	// THESE REALLY SHOULD FOLLOW A BETTER NAMING SCHEME
	// <Material>[_<MaterialModifier>[[<FormModifier>_]_<Form>]]
	
	AIR(Material.AIR),

	STONE(Material.STONE),
	GRANITE(Material.STONE, 1),
	GRANITE_POLISHED(Material.STONE, 2),
	DIORITE(Material.STONE, 3),
	DIORIDE_POLISHED(Material.STONE, 4),
	ANDESITE(Material.STONE, 5),
	ANDESIDE_POLISHED(Material.STONE, 6),

	GRASS(Material.GRASS),

	DIRT(Material.DIRT),
	DIRT_COARSE(Material.DIRT, 1),
	PODZOL(Material.DIRT, 2),

	COBBLESTONE(Material.COBBLESTONE),

	WOOD(Material.WOOD),
	WOOD_OAK(Material.WOOD, TreeSpecies.GENERIC),
	WOOD_REDWOOD(Material.WOOD, TreeSpecies.REDWOOD),
	WOOD_BIRCH(Material.WOOD, TreeSpecies.BIRCH),
	WOOD_JUNGLE(Material.WOOD, TreeSpecies.JUNGLE),
	WOOD_ACACIA(Material.WOOD, TreeSpecies.ACACIA),
	WOOD_DARK_OAK(Material.WOOD, TreeSpecies.DARK_OAK),

	SAPLING(Material.SAPLING),
	SAPLING_OAK(Material.SAPLING, TreeSpecies.GENERIC),
	SAPLING_REDWOOD(Material.SAPLING, TreeSpecies.REDWOOD),
	SAPLING_BIRCH(Material.SAPLING, TreeSpecies.BIRCH),
	SAPLING_JUNGLE(Material.SAPLING, TreeSpecies.JUNGLE),
	SAPLING_ACACIA(Material.SAPLING, TreeSpecies.ACACIA),
	SAPLING_DARK_OAK(Material.SAPLING, TreeSpecies.DARK_OAK),

	BEDROCK(Material.BEDROCK),

	FLOWING_WATER(Material.WATER, 0), //TODO: wrong name; data variant
	WATER(Material.STATIONARY_WATER, 0), //TODO: wrong name; data variant
	FLOWING_LAVA(Material.LAVA, 0), //TODO: wrong name; data variant
	LAVA(Material.STATIONARY_LAVA, 0), //TODO: wrong name; data variant

	SAND(Material.SAND),
	SAND_RED(Material.SAND, 1),

	GRAVEL(Material.GRAVEL),

	GOLD_ORE(Material.GOLD_ORE),
	IRON_ORE(Material.IRON_ORE),
	COAL_ORE(Material.COAL_ORE),

	LOG(Material.LOG),
	LOG_OAK(Material.LOG, TreeSpecies.GENERIC),
	LOG_REDWOOD(Material.LOG, TreeSpecies.REDWOOD),
	LOG_BIRCH(Material.LOG, TreeSpecies.BIRCH),
	LOG_JUNGLE(Material.LOG, TreeSpecies.JUNGLE),
	LOG_ACACIA(Material.LOG, TreeSpecies.ACACIA),
	LOG_DARK_OAK(Material.LOG, TreeSpecies.DARK_OAK),

	LEAVES(Material.LEAVES),
	LEAVES_OAK(Material.LEAVES, TreeSpecies.GENERIC),
	LEAVES_REDWOOD(Material.LEAVES, TreeSpecies.REDWOOD),
	LEAVES_BIRCH(Material.LEAVES, TreeSpecies.BIRCH),
	LEAVES_JUNGLE(Material.LEAVES, TreeSpecies.JUNGLE),
	LEAVES_ACACIA(Material.LEAVES, TreeSpecies.ACACIA),
	LEAVES_DARK_OAK(Material.LEAVES, TreeSpecies.DARK_OAK),

	SPONGE(Material.SPONGE),
	SPONGE_WET(Material.SPONGE, 1),

	GLASS(Material.GLASS),
	LAPIS_ORE(Material.LAPIS_ORE),
	LAPIS_BLOCK(Material.LAPIS_BLOCK),

	DISPENSER(Material.DISPENSER, 0), //TODO: data variant

	SANDSTONE(Material.SANDSTONE),
	SANDSTONE_CHISELED(Material.RED_SANDSTONE, 1),
	SANDSTONE_SMOOTH(Material.RED_SANDSTONE, 2),

	NOTE_BLOCK(Material.NOTE_BLOCK), //TODO: wrong name

	BED(Material.BED, 0), //TODO: data variant
	POWERED_RAIL(Material.POWERED_RAIL, 0), //TODO: data variant
	DETECTOR_RAIL(Material.DETECTOR_RAIL, 0), //TODO: data variant
	STICKY_PISTON_BASE(Material.PISTON_STICKY_BASE, 0), //TODO: data variant

	WEB(Material.WEB),

	@Deprecated // Use TALL_GRASS instead
	LONG_GRASS(Material.LONG_GRASS),
	SHRUB(Material.LONG_GRASS, 0),
	TALL_GRASS(Material.LONG_GRASS, 1),
	FERN(Material.LONG_GRASS, 2),

	DEAD_BUSH(Material.DEAD_BUSH),

	PISTON_BASE(Material.PISTON_BASE, 0), //TODO: wrong name; data variant
	PISTON_EXTENSION(Material.PISTON_EXTENSION, 0), //TODO: wrong name; data variant

	WOOL(Material.WOOL),
	WOOL_WHITE(Material.WOOL ,DyeColor.WHITE),
	WOOL_ORANGE(Material.WOOL ,DyeColor.ORANGE),
	WOOL_MAGENTA(Material.WOOL ,DyeColor.MAGENTA),
	WOOL_LIGHT_BLUE(Material.WOOL ,DyeColor.LIGHT_BLUE),
	WOOL_YELLOW(Material.WOOL ,DyeColor.YELLOW),
	WOOL_LIME(Material.WOOL ,DyeColor.LIME),
	WOOL_PINK(Material.WOOL ,DyeColor.PINK),
	WOOL_GRAY(Material.WOOL ,DyeColor.GRAY),
	WOOL_SILVER(Material.WOOL ,DyeColor.SILVER),
	WOOL_CYAN(Material.WOOL ,DyeColor.CYAN),
	WOOL_PURPLE(Material.WOOL ,DyeColor.PURPLE),
	WOOL_BLUE(Material.WOOL ,DyeColor.BLUE),
	WOOL_BROWN(Material.WOOL ,DyeColor.BROWN),
	WOOL_GREEN(Material.WOOL ,DyeColor.GREEN),
	WOOL_RED(Material.WOOL ,DyeColor.RED),
	WOOL_BLACK(Material.WOOL ,DyeColor.BLACK),

	PISTON_MOVING_PIECE(Material.PISTON_MOVING_PIECE, 0), //TODO: wrong name; data variant
	
	@Deprecated // Use FLOWER_DANDELION instead
	YELLOW_FLOWER(Material.YELLOW_FLOWER),
	FLOWER_DANDELION(Material.YELLOW_FLOWER),

	@Deprecated // Use FLOWER_POPPY instead
	RED_ROSE(Material.RED_ROSE),
	FLOWER_POPPY(Material.RED_ROSE, 0),
	FLOWER_BLUE_ORCHID(Material.RED_ROSE, 1),
	FLOWER_ALLIUM(Material.RED_ROSE, 2),
	FLOWER_AZURE_BLUET(Material.RED_ROSE, 3),
	FLOWER_RED_TULIP(Material.RED_ROSE, 4),
	FLOWER_ORANGE_TULIP(Material.RED_ROSE, 5),
	FLOWER_WHITE_TULIP(Material.RED_ROSE, 6),
	FLOWER_PINK_TULIP(Material.RED_ROSE, 7),
	FLOWER_OXEYE_DAISY(Material.RED_ROSE, 8),
	
	@Deprecated // Use MUSHROOM_BROWN instead
	BROWN_MUSHROOM(Material.BROWN_MUSHROOM),
	MUSHROOM_BROWN(Material.BROWN_MUSHROOM),
	@Deprecated // Use MUSHROOM_RED instead
	RED_MUSHROOM(Material.RED_MUSHROOM),
	MUSHROOM_RED(Material.RED_MUSHROOM),
	GOLD_BLOCK(Material.GOLD_BLOCK),
	IRON_BLOCK(Material.IRON_BLOCK),
	
	@Deprecated // Use STONE_DOUBLE_SLAB instead
	DOUBLE_STEP(Material.DOUBLE_STEP, 0), //TODO: wrong name; data variant
	STONE_DOUBLE_SLAB(Material.DOUBLE_STEP, 0),
	SANDSTONE_DOUBLE_SLAB(Material.DOUBLE_STEP, 1),
	STONEWOOD_DOUBLE_SLAB(Material.DOUBLE_STEP, 2),
	COBBLESTONE_DOUBLE_SLAB(Material.DOUBLE_STEP, 3),
	BRICK_DOUBLE_SLAB(Material.DOUBLE_STEP, 4),
	SMOOTH_BRICK_DOUBLE_SLAB(Material.DOUBLE_STEP, 5),
	NETHER_BRICK_DOUBLE_SLAB(Material.DOUBLE_STEP, 6),
	QUARTZ_DOUBLE_SLAB(Material.DOUBLE_STEP, 7),
	SANDSTONE_RED_DOUBLE_SLAB(Material.DOUBLE_STONE_SLAB2, 0),
	
	@Deprecated // Use STONE_SLAB instead
	STEP(Material.STEP, 0), //TODO: wrong name; data variant
	STONE_SLAB(Material.STEP, 0),
	SANDSTONE_SLAB(Material.STEP, 1),
	STONEWOOD_SLAB(Material.STEP, 2),
	COBBLESTONE_SLAB(Material.STEP, 3),
	BRICK_SLAB(Material.STEP, 4),
	SMOOTH_BRICK_SLAB(Material.STEP, 5),
	NETHER_BRICK_SLAB(Material.STEP, 6),
	QUARTZ_SLAB(Material.STEP, 7),
	SANDSTONE_RED_SLAB(Material.STONE_SLAB2, 0),
	STONE_UPPER_SLAB(Material.STEP, 0 + 8),
	SANDSTONE_UPPER_SLAB(Material.STEP, 1 + 8),
	STONEWOOD_UPPER_SLAB(Material.STEP, 2 + 8),
	COBBLESTONE_UPPER_SLAB(Material.STEP, 3 + 8),
	BRICK_UPPER_SLAB(Material.STEP, 4 + 8),
	SMOOTH_BRICK_UPPER_SLAB(Material.STEP, 5 + 8),
	NETHER_BRICK_UPPER_SLAB(Material.STEP, 6 + 8),
	QUARTZ_UPPER_SLAB(Material.STEP, 7 + 8),
	SANDSTONE_RED_UPPER_SLAB(Material.STONE_SLAB2, 0 + 8),

	BRICK(Material.BRICK), //TODO: wrong name
	TNT(Material.TNT),
	BOOKSHELF(Material.BOOKSHELF),

	MOSSY_COBBLESTONE(Material.MOSSY_COBBLESTONE, 0), //TODO: wrong name; data variant
	OBSIDIAN(Material.OBSIDIAN),
	TORCH(Material.TORCH, 0), //TODO: data variant
	FIRE(Material.FIRE, 0), //TODO: data variant
	MOB_SPAWNER(Material.MOB_SPAWNER, 0), //TODO: data variant
	WOOD_STAIRS(Material.WOOD_STAIRS, 0), //TODO: wrong name; data variant
	CHEST(Material.CHEST, 0), //TODO: data variant
	REDSTONE_WIRE(Material.REDSTONE_WIRE, 0), //TODO: data variant
	
	DIAMOND_ORE(Material.DIAMOND_ORE),
	DIAMOND_BLOCK(Material.DIAMOND_BLOCK),
	WORKBENCH(Material.WORKBENCH), //TODO: wrong name
	CROPS(Material.CROPS, 0), //TODO: wrong name; data variant
	SOIL(Material.SOIL, 0), //TODO: wrong name; data variant
	FURNACE(Material.FURNACE, 0), //TODO: data variant
	BURNING_FURNACE(Material.BURNING_FURNACE, 0), //TODO: wrong name; data variant
	SIGN_POST(Material.SIGN_POST, 0), //TODO: wrong name; data variant
	WOODEN_DOOR(Material.WOODEN_DOOR, 0), //TODO: data variant
	LADDER(Material.LADDER, 0), //TODO: data variant
	RAILS(Material.RAILS, 0), //TODO: wrong name; data variant
	COBBLESTONE_STAIRS(Material.COBBLESTONE_STAIRS, 0), //TODO: wrong name; data variant
	WALL_SIGN(Material.WALL_SIGN, 0), //TODO: data variant
	LEVER(Material.LEVER, 0), //TODO: data variant
	STONE_PLATE(Material.STONE_PLATE, 0), //TODO: wrong name; data variant
	IRON_DOOR_BLOCK(Material.IRON_DOOR_BLOCK, 0), //TODO: wrong name; data variant
	WOODEN_PLATE(Material.WOOD_PLATE, 0), //TODO: wrong name; data variant
	REDSTONE_ORE(Material.REDSTONE_ORE),
	GLOWING_REDSTONE_ORE(Material.GLOWING_REDSTONE_ORE), //TODO: wrong name
	REDSTONE_TORCH_OFF(Material.REDSTONE_TORCH_OFF, 0), //TODO: wrong name; data variant
	REDSTONE_TORCH_ON(Material.REDSTONE_TORCH_ON, 0), //TODO: wrong name; data variant
	STONE_BUTTON(Material.STONE_BUTTON, 0), //TODO: wrong name; data variant
	SNOW(Material.SNOW, 0), //TODO: wrong name; data variant
	ICE(Material.ICE),
	SNOW_BLOCK(Material.SNOW_BLOCK), //TODO: wrong name
	CACTUS(Material.CACTUS, 0), //TODO: data variant
	CLAY(Material.CLAY),
	SUGAR_CANE_BLOCK(Material.SUGAR_CANE_BLOCK, 0), //TODO: wrong name; data variant
	JUKEBOX(Material.JUKEBOX, 0), //TODO: data variant
	FENCE(Material.FENCE),
	PUMPKIN(Material.PUMPKIN, 0), //TODO: data variant
	NETHERRACK(Material.NETHERRACK),
	SOUL_SAND(Material.SOUL_SAND, 0), //TODO: data variant
	GLOWSTONE(Material.GLOWSTONE),
	PORTAL(Material.PORTAL),
	JACK_O_LATERN(Material.JACK_O_LANTERN, 0), //TODO: wrong name; data variant
	CAKE_BLOCK(Material.CAKE_BLOCK, 0), //TODO: wrong name; data variant
	DIODE_BLOCK_OFF(Material.DIODE_BLOCK_OFF, 0), //TODO: wrong name; data variant
	DIODE_BLOCK_ON(Material.DIODE_BLOCK_ON, 0), //TODO: wrong name; data variant
	
	STAINED_GLASS(Material.STAINED_GLASS),
	STAINED_GLASS_WHITE(Material.STAINED_GLASS ,DyeColor.WHITE),
	STAINED_GLASS_ORANGE(Material.STAINED_GLASS ,DyeColor.ORANGE),
	STAINED_GLASS_MAGENTA(Material.STAINED_GLASS ,DyeColor.MAGENTA),
	STAINED_GLASS_LIGHT_BLUE(Material.STAINED_GLASS ,DyeColor.LIGHT_BLUE),
	STAINED_GLASS_YELLOW(Material.STAINED_GLASS ,DyeColor.YELLOW),
	STAINED_GLASS_LIME(Material.STAINED_GLASS ,DyeColor.LIME),
	STAINED_GLASS_PINK(Material.STAINED_GLASS ,DyeColor.PINK),
	STAINED_GLASS_GRAY(Material.STAINED_GLASS ,DyeColor.GRAY),
	STAINED_GLASS_SILVER(Material.STAINED_GLASS ,DyeColor.SILVER),
	STAINED_GLASS_CYAN(Material.STAINED_GLASS ,DyeColor.CYAN),
	STAINED_GLASS_PURPLE(Material.STAINED_GLASS ,DyeColor.PURPLE),
	STAINED_GLASS_BLUE(Material.STAINED_GLASS ,DyeColor.BLUE),
	STAINED_GLASS_BROWN(Material.STAINED_GLASS ,DyeColor.BROWN),
	STAINED_GLASS_GREEN(Material.STAINED_GLASS ,DyeColor.GREEN),
	STAINED_GLASS_RED(Material.STAINED_GLASS ,DyeColor.RED),
	STAINED_GLASS_BLACK(Material.STAINED_GLASS ,DyeColor.BLACK),
	
	TRAP_DOOR(Material.TRAP_DOOR, 0), //TODO: wrong name; data variant
	MONSTER_EGGS(Material.MONSTER_EGGS, 0), //TODO: wrong name; data variant

	SMOOTH_BRICK(Material.SMOOTH_BRICK),
	SMOOTH_BRICK_MOSSY(Material.SMOOTH_BRICK, 1),
	SMOOTH_BRICK_CRACKED(Material.SMOOTH_BRICK, 2),
	SMOOTH_BRICK_CHISELED(Material.SMOOTH_BRICK, 3),

	HUGE_MUSHROOM_1(Material.HUGE_MUSHROOM_1, 0), //TODO: wrong name; data variant
	HUGE_MUSHROOM_2(Material.HUGE_MUSHROOM_2, 0), //TODO: wrong name; data variant
	IRON_FENCE(Material.IRON_FENCE), //TODO: wrong name
	THIN_GLASS(Material.THIN_GLASS), //TODO: wrong name
	MELON_BLOCK(Material.MELON_BLOCK),
	PUMPKIN_STEM(Material.PUMPKIN_STEM, 0), //TODO: data variant
	MELON_STEM(Material.MELON_STEM, 0), //TODO: data variant
	VINE(Material.VINE, 0), //TODO: data variant
	FENCE_GATE(Material.FENCE_GATE, 0), //TODO: data variant
	BRICK_STAIRS(Material.BRICK_STAIRS, 0), //TODO: data variant
	SMOOTH_STAIRS(Material.SMOOTH_STAIRS, 0), //TODO: wrong name; data variant
	MYCEL(Material.MYCEL), //TODO: wrong name
	WATER_LILY(Material.WATER_LILY), //TODO: wrong name
	NETHER_BRICK(Material.NETHER_BRICK),
	NETHER_FENCE(Material.NETHER_FENCE), //TODO: wrong name
	NETHER_BRICK_STAIRS(Material.NETHER_BRICK_STAIRS, 0), //TODO: data variant
	NETHER_WARTS(Material.NETHER_WARTS, 0), //TODO: wrong name; data variant
	ENCHANTMENT_TABLE(Material.ENCHANTMENT_TABLE), //TODO: wrong name
	BREWING_STAND(Material.BREWING_STAND, 0), //TODO: data variant
	CAULDRON(Material.CAULDRON, 0), //TODO: data variant
	ENDER_PORTAL(Material.ENDER_PORTAL), //TODO: wrong name
	ENDER_PORTAL_FRAME(Material.ENDER_PORTAL_FRAME, 0), //TODO: wrong name; data variant
	ENDER_STONE(Material.ENDER_STONE, 0), //TODO: wrong name; data variant
	DRAGON_EGG(Material.DRAGON_EGG),
	REDSTONE_LAMP_OFF(Material.REDSTONE_LAMP_OFF), //TODO: wrong name
	REDSTONE_LAMP_ON(Material.REDSTONE_LAMP_ON), //TODO: wrong name

	@Deprecated // Use WOOD_OAK_DOUBLE_SLAB instead
	WOOD_DOUBLE_STEP(Material.WOOD_DOUBLE_STEP),
	//WOOD_DOUBLE_SLAB(Material.WOOD_DOUBLE_STEP),
	WOOD_OAK_DOUBLE_SLAB(Material.WOOD_DOUBLE_STEP, TreeSpecies.GENERIC),
	WOOD_REDWOOD_DOUBLE_SLAB(Material.WOOD_DOUBLE_STEP, TreeSpecies.REDWOOD),
	WOOD_BIRCH_DOUBLE_SLAB(Material.WOOD_DOUBLE_STEP, TreeSpecies.BIRCH),
	WOOD_JUNGLE_DOUBLE_SLAB(Material.WOOD_DOUBLE_STEP, TreeSpecies.JUNGLE),
	WOOD_ACACIA_DOUBLE_SLAB(Material.WOOD_DOUBLE_STEP, TreeSpecies.ACACIA),
	WOOD_DARK_OAK_DOUBLE_SLAB(Material.WOOD_DOUBLE_STEP, TreeSpecies.DARK_OAK),

	@Deprecated // Use WOOD_OAK_SLAB instead
	WOOD_STEP(Material.WOOD_STEP),
	//WOOD_SLAB(Material.WOOD_STEP),
	WOOD_OAK_SLAB(Material.WOOD_STEP, TreeSpecies.GENERIC),
	WOOD_REDWOOD_SLAB(Material.WOOD_STEP, TreeSpecies.REDWOOD),
	WOOD_BIRCH_SLAB(Material.WOOD_STEP, TreeSpecies.BIRCH),
	WOOD_JUNGLE_SLAB(Material.WOOD_STEP, TreeSpecies.JUNGLE),
	WOOD_ACACIA_SLAB(Material.WOOD_STEP, TreeSpecies.ACACIA),
	WOOD_DARK_OAK_SLAB(Material.WOOD_STEP, TreeSpecies.DARK_OAK),

	COCOA(Material.COCOA, 0), //TODO: wrong name; data variant
	SANDSTONE_STAIRS(Material.SANDSTONE_STAIRS, 0), //TODO: data variant
	EMERALD_ORE(Material.EMERALD_ORE),
	ENDER_CHEST(Material.ENDER_CHEST, 0), //TODO: data variant
	TRIPWIRE_HOOK(Material.TRIPWIRE_HOOK, 0), //TODO: data variant
	TRIPWIRE(Material.TRIPWIRE, 0), //TODO: data variant
	EMERALD_BLOCK(Material.EMERALD_BLOCK),
	SPRUCE_WOOD_STAIRS(Material.SPRUCE_WOOD_STAIRS, 0), //TODO: wrong name; data variant
	BIRCH_WOOD_STAIRS(Material.BIRCH_WOOD_STAIRS, 0), //TODO: wrong name; data variant
	JUNGLE_WOOD_STAIRS(Material.JUNGLE_WOOD_STAIRS, 0), //TODO: wrong name; data variant
	COMMAND(Material.COMMAND), //TODO: wrong name
	BEACON(Material.BEACON),

	COBBLE_WALL(Material.COBBLE_WALL),
	COBBLE_WALL_MOSSY(Material.COBBLE_WALL, 1),

	FLOWER_POT(Material.FLOWER_POT, 0), //TODO: data variant
	CARROT(Material.CARROT, 0), //TODO: wrong name; data variant
	POTATO(Material.POTATO, 0), //TODO: wrong name; data variant
	WOOD_BUTTON(Material.WOOD_BUTTON, 0), //TODO: wrong name; data variant
	SKULL(Material.SKULL, 0), //TODO: data variant
	ANVIL(Material.ANVIL, 0), //TODO: data variant
	TRAPPED_CHEST(Material.TRAPPED_CHEST, 0), //TODO: data variant
	GOLD_PLATE(Material.GOLD_PLATE, 0), //TODO: wrong name; data variant
	IRON_PLATE(Material.IRON_PLATE, 0), //TODO: wrong name; data variant
	REDSTONE_COMPARATOR_OFF(Material.REDSTONE_COMPARATOR_OFF, 0), //TODO: wrong name; data variant
	REDSTONE_COMPARATOR_ON(Material.REDSTONE_COMPARATOR_OFF, 0), //TODO: wrong name; data variant
	DAYLIGHT_DETECTOR(Material.DAYLIGHT_DETECTOR, 0), //TODO: data variant
	REDSTONE_BLOCK(Material.REDSTONE_BLOCK),
	QUARTZ_ORE(Material.QUARTZ_ORE),
	HOPPER(Material.HOPPER, 0), //TODO: data variant

	QUARTZ_BLOCK(Material.QUARTZ_BLOCK),
	QUARTZ_CHISELED(Material.QUARTZ_BLOCK, 1),
	QUARTZ_PILLAR(Material.QUARTZ_BLOCK, 2),
	QUARTZ_PILLAR_NS(Material.QUARTZ_BLOCK, 3),
	QUARTZ_PILLAR_EW(Material.QUARTZ_BLOCK, 4),

	QUARTZ_STAIRS(Material.QUARTZ_STAIRS, 0), //TODO: data variant
	ACTIVATOR_RAIL(Material.ACTIVATOR_RAIL, 0), //TODO: data variant
	DROPPER(Material.DROPPER, 0), //TODO: data variant

	STAINED_CLAY(Material.STAINED_CLAY), //TODO: wrong name
	STAINED_CLAY_WHITE(Material.STAINED_CLAY ,DyeColor.WHITE), //TODO: wrong name
	STAINED_CLAY_ORANGE(Material.STAINED_CLAY ,DyeColor.ORANGE), //TODO: wrong name
	STAINED_CLAY_MAGENTA(Material.STAINED_CLAY ,DyeColor.MAGENTA), //TODO: wrong name
	STAINED_CLAY_LIGHT_BLUE(Material.STAINED_CLAY ,DyeColor.LIGHT_BLUE), //TODO: wrong name
	STAINED_CLAY_YELLOW(Material.STAINED_CLAY ,DyeColor.YELLOW), //TODO: wrong name
	STAINED_CLAY_LIME(Material.STAINED_CLAY ,DyeColor.LIME), //TODO: wrong name
	STAINED_CLAY_PINK(Material.STAINED_CLAY ,DyeColor.PINK), //TODO: wrong name
	STAINED_CLAY_GRAY(Material.STAINED_CLAY ,DyeColor.GRAY), //TODO: wrong name
	STAINED_CLAY_SILVER(Material.STAINED_CLAY ,DyeColor.SILVER), //TODO: wrong name
	STAINED_CLAY_CYAN(Material.STAINED_CLAY ,DyeColor.CYAN), //TODO: wrong name
	STAINED_CLAY_PURPLE(Material.STAINED_CLAY ,DyeColor.PURPLE), //TODO: wrong name
	STAINED_CLAY_BLUE(Material.STAINED_CLAY ,DyeColor.BLUE), //TODO: wrong name
	STAINED_CLAY_BROWN(Material.STAINED_CLAY ,DyeColor.BROWN), //TODO: wrong name
	STAINED_CLAY_GREEN(Material.STAINED_CLAY ,DyeColor.GREEN), //TODO: wrong name
	STAINED_CLAY_RED(Material.STAINED_CLAY ,DyeColor.RED), //TODO: wrong name
	STAINED_CLAY_BLACK(Material.STAINED_CLAY ,DyeColor.BLACK), //TODO: wrong name

	STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE),
	STAINED_GLASS_PANE_WHITE(Material.STAINED_GLASS_PANE ,DyeColor.WHITE),
	STAINED_GLASS_PANE_ORANGE(Material.STAINED_GLASS_PANE ,DyeColor.ORANGE),
	STAINED_GLASS_PANE_MAGENTA(Material.STAINED_GLASS_PANE ,DyeColor.MAGENTA),
	STAINED_GLASS_PANE_LIGHT_BLUE(Material.STAINED_GLASS_PANE ,DyeColor.LIGHT_BLUE),
	STAINED_GLASS_PANE_YELLOW(Material.STAINED_GLASS_PANE ,DyeColor.YELLOW),
	STAINED_GLASS_PANE_LIME(Material.STAINED_GLASS_PANE ,DyeColor.LIME),
	STAINED_GLASS_PANE_PINK(Material.STAINED_GLASS_PANE ,DyeColor.PINK),
	STAINED_GLASS_PANE_GRAY(Material.STAINED_GLASS_PANE ,DyeColor.GRAY),
	STAINED_GLASS_PANE_SILVER(Material.STAINED_GLASS_PANE ,DyeColor.SILVER),
	STAINED_GLASS_PANE_CYAN(Material.STAINED_GLASS_PANE ,DyeColor.CYAN),
	STAINED_GLASS_PANE_PURPLE(Material.STAINED_GLASS_PANE ,DyeColor.PURPLE),
	STAINED_GLASS_PANE_BLUE(Material.STAINED_GLASS_PANE ,DyeColor.BLUE),
	STAINED_GLASS_PANE_BROWN(Material.STAINED_GLASS_PANE ,DyeColor.BROWN),
	STAINED_GLASS_PANE_GREEN(Material.STAINED_GLASS_PANE ,DyeColor.GREEN),
	STAINED_GLASS_PANE_RED(Material.STAINED_GLASS_PANE ,DyeColor.RED),
	STAINED_GLASS_PANE_BLACK(Material.STAINED_GLASS_PANE ,DyeColor.BLACK),
	
	@Deprecated
	LEAVES_2(Material.LEAVES_2),
	@Deprecated
	LOG_2(Material.LOG_2),
	
	ACACIA_STAIRS(Material.ACACIA_STAIRS, 0), //TODO: data variant
	DARK_OAK_STAIRS(Material.DARK_OAK_STAIRS, 0), //TODO: data variant
	SLIME_BLOCK(Material.SLIME_BLOCK), //TODO: wrong name
	BARRIER(Material.BARRIER),
	IRON_TRAPDOOR(Material.IRON_TRAPDOOR, 0), //TODO: data variant

	PRISMARINE(Material.PRISMARINE),
	PRISMARINE_BRICKS(Material.PRISMARINE, 1),
	PRISMARINE_DARK(Material.PRISMARINE, 2),

	SEA_LANTERN(Material.SEA_LANTERN),
	HAY_BLOCK(Material.HAY_BLOCK, 0), //TODO: data variant

	CARPET(Material.CARPET),
	CARPET_WHITE(Material.CARPET ,DyeColor.WHITE),
	CARPET_ORANGE(Material.CARPET ,DyeColor.ORANGE),
	CARPET_MAGENTA(Material.CARPET ,DyeColor.MAGENTA),
	CARPET_LIGHT_BLUE(Material.CARPET ,DyeColor.LIGHT_BLUE),
	CARPET_YELLOW(Material.CARPET ,DyeColor.YELLOW),
	CARPET_LIME(Material.CARPET ,DyeColor.LIME),
	CARPET_PINK(Material.CARPET ,DyeColor.PINK),
	CARPET_GRAY(Material.CARPET ,DyeColor.GRAY),
	CARPET_SILVER(Material.CARPET ,DyeColor.SILVER),
	CARPET_CYAN(Material.CARPET ,DyeColor.CYAN),
	CARPET_PURPLE(Material.CARPET ,DyeColor.PURPLE),
	CARPET_BLUE(Material.CARPET ,DyeColor.BLUE),
	CARPET_BROWN(Material.CARPET ,DyeColor.BROWN),
	CARPET_GREEN(Material.CARPET ,DyeColor.GREEN),
	CARPET_RED(Material.CARPET ,DyeColor.RED),
	CARPET_BLACK(Material.CARPET ,DyeColor.BLACK),

	HARD_CLAY(Material.HARD_CLAY), //TODO: wrong name
	COAL_BLOCK(Material.COAL_BLOCK),
	PACKED_ICE(Material.PACKED_ICE),
	DOUBLE_PLANT(Material.DOUBLE_PLANT, 0), //TODO: data variant
	STANDING_BANNER(Material.STANDING_BANNER, 0), //TODO: data variant
	WALL_BANNER(Material.WALL_BANNER, 0), //TODO: data variant
	DAYLIGHT_DETECTOR_INVERTED(Material.DAYLIGHT_DETECTOR_INVERTED, 0), //TODO: data variant
	
	RED_SANDSTONE(Material.RED_SANDSTONE),
	RED_SANDSTONE_CHISELED(Material.RED_SANDSTONE, 1),
	RED_SANDSTONE_SMOOTH(Material.RED_SANDSTONE, 2),

	RED_SANDSTONE_STAIRS(Material.RED_SANDSTONE_STAIRS, 0), //TODO: data variant
	DOUBLE_STONE_SLAB2(Material.DOUBLE_STONE_SLAB2, 0), //TODO: data variant
	STONE_SLAB2(Material.STONE_SLAB2, 0), //TODO: data variant
	SPRUCE_FENCE_GATE(Material.SPRUCE_FENCE_GATE),
	BIRCH_FENCE_GATE(Material.BIRCH_FENCE_GATE),
	JUNGLE_FENCE_GATE(Material.JUNGLE_FENCE_GATE),
	DARK_OAK_FENCE_GATE(Material.DARK_OAK_FENCE_GATE),
	ACACIA_FENCE_GATE(Material.ACACIA_FENCE_GATE),
	SPRUCE_FENCE(Material.SPRUCE_FENCE),
	BIRCH_FENCE(Material.BIRCH_FENCE),
	JUNGLE_FENCE(Material.JUNGLE_FENCE),
	DARK_OAK_FENCE(Material.DARK_OAK_FENCE),
	ACACIA_FENCE(Material.ACACIA_FENCE),
	SPRUCE_DOOR(Material.SPRUCE_DOOR, 0), //TODO: data variant
	BIRCH_DOOR(Material.BIRCH_DOOR, 0), //TODO: data variant
	JUNGLE_DOOR(Material.JUNGLE_DOOR, 0), //TODO: data variant
	ACACIA_DOOR(Material.ACACIA_DOOR, 0), //TODO: data variant
	DARK_OAK_DOOR(Material.DARK_OAK_DOOR, 0), //TODO: data variant

	END_ROD(Material.END_ROD),
	CHORUS_PLANT(Material.CHORUS_PLANT),
	CHORUS_FLOWER(Material.CHORUS_FLOWER),
	PURPUR_BLOCK(Material.PURPUR_BLOCK),
	PURPUR_PILLAR(Material.PURPUR_PILLAR),
	PURPUR_STAIRS(Material.PURPUR_STAIRS, 0), //TODO: data variant
	PURPUR_DOUBLE_SLAB(Material.PURPUR_DOUBLE_SLAB, 0), //TODO: data variant
	PURPUR_SLAB(Material.PURPUR_SLAB, 0), //TODO: data variant
	END_BRICKS(Material.END_BRICKS, 0), //TODO: data variant
	BEETROOT_BLOCK(Material.BEETROOT_BLOCK, 0), //TODO: wrong name; data variant
	GRASS_PATH(Material.GRASS_PATH),
	END_GATEWAY(Material.END_GATEWAY),
	COMMAND_REPEATING(Material.COMMAND_REPEATING), //TODO: wrong name
	COMMAND_CHAIN(Material.COMMAND_CHAIN), //TODO: wrong name
	FROSTED_ICE(Material.FROSTED_ICE, 0), //TODO: data variant
	MAGMA(Material.MAGMA),
	NETHER_WART_BLOCK(Material.NETHER_WART_BLOCK),
	RED_NETHER_BRICK(Material.RED_NETHER_BRICK),
	BONE_BLOCK(Material.BONE_BLOCK),
	STRUCTURE_VOID(Material.STRUCTURE_VOID),
	STRUCTURE_BLOCK(Material.STRUCTURE_BLOCK, 0); //TODO: data variant

	@SuppressWarnings("deprecation")
	private static MaterialData createData(Material material, int data) {
		assert(material.getId() > 255);
		return new MaterialData(material, (byte)data);
	}
	
	private RealMaterial2(Material material) {
		this.data = createData(material, 0);
	}
	
	private RealMaterial2(Material material, int data) {
		this.data = createData(material, data);
	}
	
	@SuppressWarnings("deprecation")
	private RealMaterial2(Material material, DyeColor color) {
		this.data = createData(material, color.getData());
	}
	
	private RealMaterial2(Material material, TreeSpecies species) {
		this.data = decodeTrees(material, species);
	}
	
	private static MaterialData decodeTrees(Material material, TreeSpecies species) {
		switch (material) {
		case WOOD:
			switch (species) {
			case GENERIC:
				return createData(Material.WOOD, 0);
			case REDWOOD:
				return createData(Material.WOOD, 1);
			case BIRCH:
				return createData(Material.WOOD, 2);
			case JUNGLE:
				return createData(Material.WOOD, 3);
			case ACACIA:
				return createData(Material.WOOD, 4);
			case DARK_OAK:
				return createData(Material.WOOD, 5);
			default:
				return null;
			}
		case LOG:
		case LOG_2:
			switch (species) {
			case GENERIC:
				return createData(Material.LOG, 0);
			case REDWOOD:
				return createData(Material.LOG, 1);
			case BIRCH:
				return createData(Material.LOG, 2);
			case JUNGLE:
				return createData(Material.LOG, 3);
			case ACACIA:
				return createData(Material.LOG_2, 0);
			case DARK_OAK:
				return createData(Material.LOG_2, 1);
			default:
				return null;
			}
		case LEAVES:
		case LEAVES_2:
			switch (species) {
			case GENERIC:
				return createData(Material.LEAVES, 0);
			case REDWOOD:
				return createData(Material.LEAVES, 1);
			case BIRCH:
				return createData(Material.LEAVES, 2);
			case JUNGLE:
				return createData(Material.LEAVES, 3);
			case ACACIA:
				return createData(Material.LEAVES_2, 0);
			case DARK_OAK:
				return createData(Material.LEAVES_2, 1);
			default:
				return null;
			}
		case WOOD_STEP:
		case STEP: // this will be converted to the above
			switch (species) {
			case GENERIC:
				return createData(Material.WOOD_STEP, 0);
			case REDWOOD:
				return createData(Material.WOOD_STEP, 1);
			case BIRCH:
				return createData(Material.WOOD_STEP, 2);
			case JUNGLE:
				return createData(Material.WOOD_STEP, 3);
			case ACACIA:
				return createData(Material.WOOD_STEP, 4);
			case DARK_OAK:
				return createData(Material.WOOD_STEP, 5);
			default:
				return null;
			}
		case WOOD_DOUBLE_STEP:
		case DOUBLE_STEP: // this will be converted to the above
			switch (species) {
			case GENERIC:
				return createData(Material.WOOD_DOUBLE_STEP, 0);
			case REDWOOD:
				return createData(Material.WOOD_DOUBLE_STEP, 1);
			case BIRCH:
				return createData(Material.WOOD_DOUBLE_STEP, 2);
			case JUNGLE:
				return createData(Material.WOOD_DOUBLE_STEP, 3);
			case ACACIA:
				return createData(Material.WOOD_DOUBLE_STEP, 4);
			case DARK_OAK:
				return createData(Material.WOOD_DOUBLE_STEP, 5);
			default:
				return null;
			}
		default:
		    throw new IllegalArgumentException("Invalid block type for tree species");
		}
	}

	private MaterialData data;
	public final MaterialData getData() {
		return this.data;
	}
	
	
//	public final MaterialData getData() {
//		this.getClass().getDeclaredFields()[0].getType().is
//		this.getClass().getDeclaredMethods()[0].getReturnType() == MaterialData;
//		this.getClass().getDeclaredMethods()[0].getParameterCount() == 0;
//		return this.data;
//	}
	
//	private static final Map<String, MaterialData> list;
	
	public final MaterialData getMaterialData(String name) {
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
		return null;
	}

//	static {
//		get
//		int count = materials.length;
//		for (int i = 0; i < count; i++) {
//			list.put(materials[i].name(), materials[i]);
//		}
//		
//	}
}
