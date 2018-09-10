package me.daddychurchill.CityWorld.Support;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.SandstoneType;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.Colorable;
import org.bukkit.material.Directional;
import org.bukkit.Material.BIRCH_LEAVES;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Sandstone;
import org.bukkit.material.Sapling;
import org.bukkit.material.Stairs;
import org.bukkit.material.TexturedMaterial;
import org.bukkit.Material.SPRUCE_WOOD;
import org.bukkit.Material.WHITE_WOOL;

import me.daddychurchill.CityWorld.Support.Odds.ColorSet;
import me.daddychurchill.CityWorld.Support.Materials.Carpet;
import me.daddychurchill.CityWorld.Support.Materials.Concrete;
import me.daddychurchill.CityWorld.Support.Materials.Concrete_Powder;
import me.daddychurchill.CityWorld.Support.Materials.Dirt;
import me.daddychurchill.CityWorld.Support.Materials.Stone;
import me.daddychurchill.CityWorld.Support.Materials.Dirt.DirtType;
import me.daddychurchill.CityWorld.Support.Materials.Prismarine.PrismarineType;
import me.daddychurchill.CityWorld.Support.Materials.Double_Slab;
import me.daddychurchill.CityWorld.Support.Materials.Log;
import me.daddychurchill.CityWorld.Support.Materials.Prismarine;
import me.daddychurchill.CityWorld.Support.Materials.Red_Sandstone;
import me.daddychurchill.CityWorld.Support.Materials.Sand;
import me.daddychurchill.CityWorld.Support.Materials.Sand.SandType;
import me.daddychurchill.CityWorld.Support.Materials.Slab;
import me.daddychurchill.CityWorld.Support.Materials.Slab.SlabType;
import me.daddychurchill.CityWorld.Support.Materials.SmoothBrick;
import me.daddychurchill.CityWorld.Support.Materials.SmoothBrick.SmoothBrickType;
import me.daddychurchill.CityWorld.Support.Materials.Sponge;
import me.daddychurchill.CityWorld.Support.Materials.Sponge.SpongeType;
import me.daddychurchill.CityWorld.Support.Materials.Stained_Clay;
import me.daddychurchill.CityWorld.Support.Materials.Stained_Glass;
import me.daddychurchill.CityWorld.Support.Materials.Stained_Glass_Pane;
import me.daddychurchill.CityWorld.Support.Materials.Stone.StoneType;
import me.daddychurchill.CityWorld.Support.Materials.Wood_Double_Slab;
import me.daddychurchill.CityWorld.Support.Materials.Wood_Slab;

public class RealMaterial {
	
	// List of materials
	public final static MaterialData AIR = define(false, false, Material.AIR);

	public final static MaterialData STONE = define(true, false, new Stone(StoneType.STONE));
	public final static MaterialData GRANITE = define(true, false, new Stone(StoneType.GRANITE));
	public final static MaterialData DIORITE = define(true, false, new Stone(StoneType.DIORITE));
	public final static MaterialData ANDESITE = define(true, false, new Stone(StoneType.ANDESITE));
	public final static MaterialData POLISHED_GRANITE = define(true, false, new Stone(StoneType.POLISHED_GRANITE));
	public final static MaterialData POLISHED_DIORITE = define(true, false, new Stone(StoneType.POLISHED_DIORITE));
	public final static MaterialData POLISHED_ANDESITE = define(true, false, new Stone(StoneType.POLISHED_ANDESITE));

	public final static MaterialData GRASS = define(false, false, Material.GRASS_BLOCK);

	public final static MaterialData DIRT = define(true, false, new Dirt(DirtType.DIRT));
	public final static MaterialData COARSE_DIRT = define(true, false, new Dirt(DirtType.COARSE_DIRT));
	public final static MaterialData PODZOL = define(true, false, new Dirt(DirtType.PODZOL));

	public final static MaterialData COBBLESTONE = define(true, false, Material.COBBLESTONE);

	@Deprecated
	public final static MaterialData WOOD = define(false, true, Material.SPRUCE_WOOD);
	@Deprecated
	public final static MaterialData REDWOOD_WOOD = define(true, true, new Wood(TreeSpecies.REDWOOD));
	public final static MaterialData OAK_WOOD = define(true, false, new Wood(TreeSpecies.GENERIC));
	public final static MaterialData SPRUCE_WOOD = define(true, false, new Wood(TreeSpecies.REDWOOD));
	public final static MaterialData BIRCH_WOOD = define(true, false, new Wood(TreeSpecies.BIRCH));
	public final static MaterialData JUNGLE_WOOD = define(true, false, new Wood(TreeSpecies.JUNGLE));
	public final static MaterialData ACACIA_WOOD = define(true, false, new Wood(TreeSpecies.ACACIA));
	public final static MaterialData DARK_OAK_WOOD = define(true, false, new Wood(TreeSpecies.DARK_OAK));

	@Deprecated
	public final static MaterialData SAPLING = define(false, true, Material.SAPLING);
	@Deprecated
	public final static MaterialData REDWOOD_SAPLING = define(true, true, new Wood(TreeSpecies.REDWOOD));
	public final static MaterialData OAK_SAPLING = define(false, false, new Sapling(TreeSpecies.GENERIC));
	public final static MaterialData SPRUCE_SAPLING = define(false, false, new Sapling(TreeSpecies.REDWOOD));
	public final static MaterialData BIRCH_SAPLING = define(false, false, new Sapling(TreeSpecies.BIRCH));
	public final static MaterialData JUNGLE_SAPLING = define(false, false, new Sapling(TreeSpecies.JUNGLE));
	public final static MaterialData ACACIA_SAPLING = define(false, false, new Sapling(TreeSpecies.ACACIA));
	public final static MaterialData DARK_OAK_SAPLING = define(false, false, new Sapling(TreeSpecies.DARK_OAK));

	public final static MaterialData BEDROCK = define(true, false, Material.BEDROCK);

	@Deprecated
	public final static MaterialData WATER = define(false, true, Material.WATER);
	public final static MaterialData FLOWING_WATER = define(false, false, Material.WATER);
	public final static MaterialData STATIONARY_WATER = define(false, false, Material.WATER);

	@Deprecated
	public final static MaterialData LAVA = define(false, true, Material.LAVA);
	public final static MaterialData FLOWING_LAVA = define(false, false, Material.LAVA);
	public final static MaterialData STATIONARY_LAVA = define(false, false, Material.LAVA);

	public final static MaterialData SAND = define(true, false, new Sand(SandType.SAND)); /* SHOULD BE DEPRECATED */
	public final static MaterialData RED_SAND = define(true, false, new Sand(SandType.RED));

	public final static MaterialData GRAVEL = define(true, false, Material.GRAVEL);
	public final static MaterialData GOLD_ORE = define(true, false, Material.GOLD_ORE);
	public final static MaterialData IRON_ORE = define(true, false, Material.IRON_ORE);
	public final static MaterialData COAL_ORE = define(true, false, Material.COAL_ORE);

	@Deprecated
	public final static MaterialData LOG = define(false, true, Material.SPRUCE_LOG);
	@Deprecated
	public final static MaterialData REDWOOD_LOG = define(true, true, new Log(TreeSpecies.REDWOOD));
	public final static MaterialData OAK_LOG = define(true, false, new Log(TreeSpecies.GENERIC));
	public final static MaterialData SPRUCE_LOG = define(true, false, new Log(TreeSpecies.REDWOOD));
	public final static MaterialData BIRCH_LOG = define(true, false, new Log(TreeSpecies.BIRCH));
	public final static MaterialData JUNGLE_LOG = define(true, false, new Log(TreeSpecies.JUNGLE));
	public final static MaterialData ACACIA_LOG = define(true, false, new Log(TreeSpecies.ACACIA));
	public final static MaterialData DARK_OAK_LOG = define(true, false, new Log(TreeSpecies.DARK_OAK));

	@Deprecated
	public final static MaterialData LEAVES = define(false, true, Material.BIRCH_LEAVES);
	@Deprecated
	public final static MaterialData REDWOOD_LEAVES = define(false, true, new Leaves(TreeSpecies.REDWOOD));
	public final static MaterialData OAK_LEAVES = define(false, false, new Leaves(TreeSpecies.GENERIC));
	public final static MaterialData SPRUCE_LEAVES = define(false, false, new Leaves(TreeSpecies.REDWOOD));
	public final static MaterialData BIRCH_LEAVES = define(false, false, new Leaves(TreeSpecies.BIRCH));
	public final static MaterialData JUNGLE_LEAVES = define(false, false, new Leaves(TreeSpecies.JUNGLE));
	public final static MaterialData ACACIA_LEAVES = define(false, false, new Leaves(TreeSpecies.ACACIA));
	public final static MaterialData DARK_OAK_LEAVES = define(false, false, new Leaves(TreeSpecies.DARK_OAK));

	@Deprecated
	public final static MaterialData SPONGE = define(false, true, Material.SPONGE);
	public final static MaterialData WET_SPONGE = define(true, false, new Sponge(SpongeType.WET));
	public final static MaterialData DRY_SPONGE = define(true, false, new Sponge(SpongeType.DRY));

	public final static MaterialData GLASS = define(true, false, Material.GLASS);
	public final static MaterialData LAPIS_ORE = define(true, false, Material.LAPIS_ORE);
	public final static MaterialData LAPIS_BLOCK = define(true, false, Material.LAPIS_BLOCK);
	public final static MaterialData DISPENSER = define(false, false, Material.DISPENSER);

	public final static MaterialData SANDSTONE = define(true, false, new Sandstone(SandstoneType.CRACKED)); /* SHOULD BE DEPRECATED */
	public final static MaterialData GLYPHED_SANDSTONE = define(true, false, new Sandstone(SandstoneType.GLYPHED));
	public final static MaterialData SMOOTH_SANDSTONE = define(true, false, new Sandstone(SandstoneType.SMOOTH));

	public final static MaterialData NOTE_BLOCK = define(false, false, Material.NOTE_BLOCK);
	public final static MaterialData BED_BLOCK = define(false, false, Material.BED_BLOCK);
	public final static MaterialData POWERED_RAIL = define(false, false, Material.POWERED_RAIL);
	public final static MaterialData DETECTOR_RAIL = define(false, false, Material.DETECTOR_RAIL);
	public final static MaterialData PISTON_STICKY_BASE = define(false, false, Material.PISTON_STICKY_BASE);
	public final static MaterialData WEB = define(false, false, Material.COBWEB);
	public final static MaterialData LONG_GRASS = define(false, false, Material.LONG_GRASS);
	public final static MaterialData DEAD_BUSH = define(false, false, Material.DEAD_BUSH);
	public final static MaterialData PISTON_BASE = define(false, false, Material.PISTON);
	public final static MaterialData PISTON_EXTENSION = define(false, false, Material.PISTON_EXTENSION);

	@Deprecated
	public final static MaterialData WOOL = define(false, true, Material.WHITE_WOOL);
	public final static MaterialData WHITE_WOOL = define(true, false, new Wool(DyeColor.WHITE));
	public final static MaterialData ORANGE_WOOL = define(true, false, new Wool(DyeColor.ORANGE));
	public final static MaterialData MAGENTA_WOOL = define(true, false, new Wool(DyeColor.MAGENTA));
	public final static MaterialData LIGHT_BLUE_WOOL = define(true, false, new Wool(DyeColor.LIGHT_BLUE));
	public final static MaterialData YELLOW_WOOL = define(true, false, new Wool(DyeColor.YELLOW));
	public final static MaterialData LIME_WOOL = define(true, false, new Wool(DyeColor.LIME));
	public final static MaterialData PINK_WOOL = define(true, false, new Wool(DyeColor.PINK));
	public final static MaterialData GRAY_WOOL = define(true, false, new Wool(DyeColor.GRAY));
	public final static MaterialData SILVER_WOOL = define(true, false, new Wool(DyeColor.LIGHT_GRAY));
	public final static MaterialData CYAN_WOOL = define(true, false, new Wool(DyeColor.CYAN));
	public final static MaterialData PURPLE_WOOL = define(true, false, new Wool(DyeColor.PURPLE));
	public final static MaterialData BLUE_WOOL = define(true, false, new Wool(DyeColor.BLUE));
	public final static MaterialData BROWN_WOOL = define(true, false, new Wool(DyeColor.BROWN));
	public final static MaterialData GREEN_WOOL = define(true, false, new Wool(DyeColor.GREEN));
	public final static MaterialData RED_WOOL = define(true, false, new Wool(DyeColor.RED));
	public final static MaterialData BLACK_WOOL = define(true, false, new Wool(DyeColor.BLACK));

	public final static MaterialData PISTON_MOVING_PIECE = define(false, false, Material.PISTON_MOVING_PIECE);

	public final static MaterialData YELLOW_FLOWER = define(false, false, Material.YELLOW_FLOWER); //@@@
	public final static MaterialData RED_ROSE = define(false, false, Material.RED_ROSE); //@@@

	public final static MaterialData BROWN_MUSHROOM = define(false, false, Material.BROWN_MUSHROOM);
	public final static MaterialData RED_MUSHROOM = define(false, false, Material.RED_MUSHROOM);
	public final static MaterialData GOLD_BLOCK = define(true, false, Material.GOLD_BLOCK);
	public final static MaterialData IRON_BLOCK = define(true, false, Material.IRON_BLOCK);

	@Deprecated
	public final static MaterialData DOUBLE_STEP = define(false, true, Material.DOUBLE_STEP);
	public final static MaterialData STONE_DOUBLE_SLAB = define(true, false, new Double_Slab(SlabType.STONE));
	public final static MaterialData SANDSTONE_DOUBLE_SLAB = define(true, false, new Double_Slab(SlabType.SANDSTONE));
	public final static MaterialData WOODSTONE_DOUBLE_SLAB = define(true, false, new Double_Slab(SlabType.WOODSTONE));
	public final static MaterialData COBBLESTONE_DOUBLE_SLAB = define(true, false, new Double_Slab(SlabType.COBBLESTONE));
	public final static MaterialData BRICK_DOUBLE_SLAB = define(true, false, new Double_Slab(SlabType.BRICK));
	public final static MaterialData SMOOTH_BRICK_DOUBLE_SLAB = define(true, false, new Double_Slab(SlabType.SMOOTH_BRICK));
	public final static MaterialData NETHER_BRICK_DOUBLE_SLAB = define(true, false, new Double_Slab(SlabType.NETHER_BRICK));
	public final static MaterialData QUARTZ_DOUBLE_SLAB = define(true, false, new Double_Slab(SlabType.QUARTZ));

	@Deprecated
	public final static MaterialData STEP = define(false, true, Material.STONE_SLAB);
	public final static MaterialData STONE_SLAB = define(false, false, new Slab(SlabType.STONE));
	public final static MaterialData SANDSTONE_SLAB = define(false, false, new Slab(SlabType.SANDSTONE));
	public final static MaterialData WOODSTONE_SLAB = define(false, false, new Slab(SlabType.WOODSTONE));
	public final static MaterialData COBBLESTONE_SLAB = define(false, false, new Slab(SlabType.COBBLESTONE));
	public final static MaterialData BRICK_SLAB = define(false, false, new Slab(SlabType.BRICK));
	public final static MaterialData SMOOTH_BRICK_SLAB = define(false, false, new Slab(SlabType.SMOOTH_BRICK));
	public final static MaterialData NETHER_BRICK_SLAB = define(false, false, new Slab(SlabType.NETHER_BRICK));
	public final static MaterialData QUARTZ_SLAB = define(false, false, new Slab(SlabType.QUARTZ));

	public final static MaterialData BRICK = define(true, false, Material.BRICK);
	public final static MaterialData TNT = define(false, false, Material.TNT);
	public final static MaterialData BOOKSHELF = define(false, false, Material.BOOKSHELF);
	public final static MaterialData MOSSY_COBBLESTONE = define(true, false, Material.MOSSY_COBBLESTONE);
	public final static MaterialData OBSIDIAN = define(true, false, Material.OBSIDIAN);
	public final static MaterialData TORCH = define(false, false, Material.TORCH);
	public final static MaterialData FIRE = define(false, false, Material.FIRE);
	public final static MaterialData MOB_SPAWNER = define(false, false, Material.MOB_SPAWNER);
	public final static MaterialData WOOD_STAIRS = define(false, false, new Stairs(Material.BIRCH_STAIRS));
	public final static MaterialData CHEST = define(false, false, Material.CHEST);
	public final static MaterialData REDSTONE_WIRE = define(false, false, Material.REDSTONE_WIRE);
	public final static MaterialData DIAMOND_ORE = define(true, false, Material.DIAMOND_ORE);
	public final static MaterialData DIAMOND_BLOCK = define(true, false, Material.DIAMOND_BLOCK);
	public final static MaterialData WORKBENCH = define(false, false, Material.CRAFTING_TABLE);
	public final static MaterialData CROPS = define(false, false, Material.CROPS);
	public final static MaterialData SOIL = define(false, false, Material.FARMLAND);
	public final static MaterialData FURNACE = define(false, false, Material.FURNACE);
	public final static MaterialData BURNING_FURNACE = define(false, false, Material.BURNING_FURNACE);
	public final static MaterialData SIGN_POST = define(false, false, Material.SIGN_POST);
	public final static MaterialData WOODEN_DOOR = define(false, false, Material.OAK_DOOR);
	public final static MaterialData LADDER = define(false, false, Material.LADDER);
	public final static MaterialData RAILS = define(false, false, Material.RAILS);
	public final static MaterialData COBBLESTONE_STAIRS = define(false, false,  new Stairs(Material.COBBLESTONE_STAIRS));
	public final static MaterialData WALL_SIGN = define(false, false, Material.WALL_SIGN);
	public final static MaterialData LEVER = define(false, false, Material.LEVER);
	public final static MaterialData STONE_PRESSURE_PLATE = define(false, false, Material.STONE_PRESSURE_PLATE);
	public final static MaterialData IRON_DOOR_BLOCK = define(false, false, Material.IRON_DOOR_BLOCK);
	public final static MaterialData WOOD_PLATE = define(false, false, Material.BIRCH_PRESSURE_PLATE);
	public final static MaterialData REDSTONE_ORE = define(true, false, Material.REDSTONE_ORE);
	public final static MaterialData GLOWING_REDSTONE_ORE = define(true, false, Material.GLOWING_REDSTONE_ORE);
	public final static MaterialData REDSTONE_TORCH_OFF = define(false, false, Material.REDSTONE_TORCH_OFF);
	public final static MaterialData REDSTONE_TORCH_ON = define(false, false, Material.REDSTONE_TORCH_ON);
	public final static MaterialData STONE_BUTTON = define(false, false, Material.STONE_BUTTON);
	public final static MaterialData SNOW = define(false, false, Material.SNOW);
	public final static MaterialData ICE = define(false, false, Material.ICE);
	public final static MaterialData SNOW_BLOCK = define(false, false, Material.SNOW_BLOCK);
	public final static MaterialData CACTUS = define(false, false, Material.CACTUS);
	public final static MaterialData CLAY = define(true, false, Material.CLAY);
	public final static MaterialData SUGAR_CANE_BLOCK = define(false, false, Material.SUGAR_CANE_BLOCK);
	public final static MaterialData JUKEBOX = define(false, false, Material.JUKEBOX);
	public final static MaterialData FENCE = define(false, false, Material.SPRUCE_FENCE);
	public final static MaterialData PUMPKIN = define(false, false, Material.PUMPKIN);
	public final static MaterialData NETHERRACK = define(true, false, Material.NETHERRACK);
	public final static MaterialData SOUL_SAND = define(true, false, Material.SOUL_SAND);
	public final static MaterialData GLOWSTONE = define(true, false, Material.GLOWSTONE);
	public final static MaterialData PORTAL = define(false, false, Material.PORTAL);
	public final static MaterialData JACK_O_LANTERN = define(false, false, Material.JACK_O_LANTERN);
	public final static MaterialData CAKE_BLOCK = define(false, false, Material.CAKE_BLOCK);
	public final static MaterialData DIODE_BLOCK_OFF = define(false, false, Material.DIODE_BLOCK_OFF);
	public final static MaterialData DIODE_BLOCK_ON = define(false, false, Material.DIODE_BLOCK_ON);

	@Deprecated
	public final static MaterialData STAINED_GLASS = define(false, true, Material.WHITE_STAINED_GLASS);
	public final static MaterialData WHITE_GLASS = define(true, false, new Stained_Glass(DyeColor.WHITE));
	public final static MaterialData ORANGE_GLASS = define(true, false, new Stained_Glass(DyeColor.ORANGE));
	public final static MaterialData MAGENTA_GLASS = define(true, false, new Stained_Glass(DyeColor.MAGENTA));
	public final static MaterialData LIGHT_BLUE_GLASS = define(true, false, new Stained_Glass(DyeColor.LIGHT_BLUE));
	public final static MaterialData YELLOW_GLASS = define(true, false, new Stained_Glass(DyeColor.YELLOW));
	public final static MaterialData LIME_GLASS = define(true, false, new Stained_Glass(DyeColor.LIME));
	public final static MaterialData PINK_GLASS = define(true, false, new Stained_Glass(DyeColor.PINK));
	public final static MaterialData GRAY_GLASS = define(true, false, new Stained_Glass(DyeColor.GRAY));
	public final static MaterialData SILVER_GLASS = define(true, false, new Stained_Glass(DyeColor.LIGHT_GRAY));
	public final static MaterialData CYAN_GLASS = define(true, false, new Stained_Glass(DyeColor.CYAN));
	public final static MaterialData PURPLE_GLASS = define(true, false, new Stained_Glass(DyeColor.PURPLE));
	public final static MaterialData BLUE_GLASS = define(true, false, new Stained_Glass(DyeColor.BLUE));
	public final static MaterialData BROWN_GLASS = define(true, false, new Stained_Glass(DyeColor.BROWN));
	public final static MaterialData GREEN_GLASS = define(true, false, new Stained_Glass(DyeColor.GREEN));
	public final static MaterialData RED_GLASS = define(true, false, new Stained_Glass(DyeColor.RED));
	public final static MaterialData BLACK_GLASS = define(true, false, new Stained_Glass(DyeColor.BLACK));

	public final static MaterialData TRAP_DOOR = define(false, false, Material.TRAP_DOOR);
	public final static MaterialData MONSTER_EGGS = define(false, false, Material.MONSTER_EGGS);

	public final static MaterialData SMOOTH_BRICK = define(true, false, new SmoothBrick(SmoothBrickType.SMOOTH));
	public final static MaterialData CRACKED_BRICK = define(true, false, new SmoothBrick(SmoothBrickType.CRACKED));
	public final static MaterialData MOSSY_BRICK = define(true, false, new SmoothBrick(SmoothBrickType.MOSSY));
	public final static MaterialData CHISELED_BRICK = define(true, false, new SmoothBrick(SmoothBrickType.CHISELED));

	//TODO I need to figure out how to deal with HUGE_MUSHROOM
	public final static MaterialData HUGE_MUSHROOM_1 = define(false, false, Material.BROWN_MUSHROOM_BLOCK);
	public final static MaterialData HUGE_MUSHROOM_2 = define(false, false, Material.RED_MUSHROOM_BLOCK);

	public final static MaterialData IRON_FENCE = define(false, false, Material.IRON_BARS);
	public final static MaterialData THIN_GLASS = define(true, false, Material.GLASS_PANE);

	public final static MaterialData MELON_BLOCK = define(false, false, Material.MELON_BLOCK);
	public final static MaterialData PUMPKIN_STEM = define(false, false, Material.PUMPKIN_STEM);
	public final static MaterialData MELON_STEM = define(false, false, Material.MELON_STEM);
	public final static MaterialData VINE = define(false, false, Material.VINE);
	public final static MaterialData FENCE_GATE = define(false, false, Material.SPRUCE_FENCE_GATE);
	public final static MaterialData BRICK_STAIRS = define(false, false,  new Stairs(Material.BRICK_STAIRS));
	public final static MaterialData STONE_BRICK_STAIRS = define(false, false, new Stairs(Material.STONE_BRICK_STAIRS));
	public final static MaterialData MYCEL = define(true, false, Material.MYCELIUM);
	public final static MaterialData WATER_LILY = define(false, false, Material.WATER_LILY);
	public final static MaterialData NETHER_BRICK = define(true, false, Material.NETHER_BRICK);
	public final static MaterialData NETHER_FENCE = define(false, false, Material.NETHER_FENCE);
	public final static MaterialData NETHER_BRICK_STAIRS = define(false, false, new Stairs(Material.NETHER_BRICK_STAIRS));
	public final static MaterialData NETHER_WARTS = define(false, false, Material.NETHER_WARTS);
	public final static MaterialData ENCHANTMENT_TABLE = define(false, false, Material.ENCHANTMENT_TABLE);
	public final static MaterialData BREWING_STAND = define(false, false, Material.BREWING_STAND);
	public final static MaterialData CAULDRON = define(false, false, Material.CAULDRON);
	public final static MaterialData ENDER_PORTAL = define(false, false, Material.ENDER_PORTAL);
	public final static MaterialData ENDER_PORTAL_FRAME = define(false, false, Material.ENDER_PORTAL_FRAME);
	public final static MaterialData ENDER_STONE = define(false, false, Material.END_STONE);
	public final static MaterialData DRAGON_EGG = define(false, false, Material.DRAGON_EGG);
	public final static MaterialData REDSTONE_LAMP_OFF = define(false, false, Material.REDSTONE_LAMP);
	public final static MaterialData REDSTONE_LAMP_ON = define(false, false, Material.REDSTONE_LAMP_ON);

	@Deprecated
	public final static MaterialData WOOD_DOUBLE_STEP = define(false, true, Material.WOOD_DOUBLE_STEP);
	@Deprecated
	public final static MaterialData REDWOOD_DOUBLE_SLAB = define(true, true, new Wood_Double_Slab(TreeSpecies.REDWOOD));
	public final static MaterialData OAK_DOUBLE_SLAB = define(true, false, new Wood_Double_Slab(TreeSpecies.GENERIC));
	public final static MaterialData SPRUCE_DOUBLE_SLAB = define(true, false, new Wood_Double_Slab(TreeSpecies.REDWOOD));
	public final static MaterialData BIRCH_DOUBLE_SLAB = define(true, false, new Wood_Double_Slab(TreeSpecies.BIRCH));
	public final static MaterialData JUNGLE_DOUBLE_SLAB = define(true, false, new Wood_Double_Slab(TreeSpecies.JUNGLE));
	public final static MaterialData ACACIA_DOUBLE_SLAB = define(true, false, new Wood_Double_Slab(TreeSpecies.ACACIA));
	public final static MaterialData DARK_OAK_DOUBLE_SLAB = define(true, false, new Wood_Double_Slab(TreeSpecies.DARK_OAK));

	@Deprecated
	public final static MaterialData WOOD_STEP = define(false, true, Material.WOOD_STEP);
	@Deprecated
	public final static MaterialData REDWOOD_SLAB = define(false, true, new Wood_Slab(TreeSpecies.REDWOOD));
	public final static MaterialData OAK_SLAB = define(false, false, new Wood_Slab(TreeSpecies.GENERIC));
	public final static MaterialData SPRUCE_SLAB = define(false, false, new Wood_Slab(TreeSpecies.REDWOOD));
	public final static MaterialData BIRCH_SLAB = define(false, false, new Wood_Slab(TreeSpecies.BIRCH));
	public final static MaterialData JUNGLE_SLAB = define(false, false, new Wood_Slab(TreeSpecies.JUNGLE));
	public final static MaterialData ACACIA_SLAB = define(false, false, new Wood_Slab(TreeSpecies.ACACIA));
	public final static MaterialData DARK_OAK_SLAB = define(false, false, new Wood_Slab(TreeSpecies.DARK_OAK));

	public final static MaterialData COCOA = define(false, false, Material.COCOA);
	public final static MaterialData SANDSTONE_STAIRS = define(false, false, new Stairs(Material.SANDSTONE_STAIRS));
	public final static MaterialData EMERALD_ORE = define(true, false, Material.EMERALD_ORE);

	public final static MaterialData ENDER_CHEST = define(false, false, Material.ENDER_CHEST);
	public final static MaterialData TRIPWIRE_HOOK = define(false, false, Material.TRIPWIRE_HOOK);
	public final static MaterialData TRIPWIRE = define(false, false, Material.TRIPWIRE);
	public final static MaterialData EMERALD_BLOCK = define(true, false, Material.EMERALD_BLOCK);
	public final static MaterialData SPRUCE_WOOD_STAIRS = define(false, false, new Stairs(Material.SPRUCE_WOOD_STAIRS));
	public final static MaterialData BIRCH_WOOD_STAIRS = define(false, false, new Stairs(Material.BIRCH_WOOD_STAIRS));
	public final static MaterialData JUNGLE_WOOD_STAIRS = define(false, false, new Stairs(Material.JUNGLE_WOOD_STAIRS));
	public final static MaterialData COMMAND = define(false, false, Material.COMMAND);
	public final static MaterialData BEACON = define(false, false, Material.BEACON);
	public final static MaterialData COBBLE_WALL = define(false, false, Material.COBBLESTONE_WALL);
	public final static MaterialData FLOWER_POT = define(false, false, Material.FLOWER_POT);
	public final static MaterialData CARROT = define(false, false, Material.CARROT);
	public final static MaterialData POTATO = define(false, false, Material.POTATO);
	public final static MaterialData WOOD_BUTTON = define(false, false, Material.WOOD_BUTTON);
	public final static MaterialData SKULL = define(false, false, Material.SKULL);
	public final static MaterialData ANVIL = define(false, false, Material.ANVIL);
	public final static MaterialData TRAPPED_CHEST = define(false, false, Material.TRAPPED_CHEST);
	public final static MaterialData GOLD_PLATE = define(false, false, Material.GOLD_PLATE);
	public final static MaterialData IRON_PLATE = define(false, false, Material.IRON_PLATE);
	public final static MaterialData REDSTONE_COMPARATOR_OFF = define(false, false, Material.REDSTONE_COMPARATOR_OFF);
	public final static MaterialData REDSTONE_COMPARATOR_ON = define(false, false, Material.REDSTONE_COMPARATOR_ON);
	public final static MaterialData DAYLIGHT_DETECTOR = define(false, false, Material.DAYLIGHT_DETECTOR);
	public final static MaterialData REDSTONE_BLOCK = define(true, false, Material.REDSTONE_BLOCK);
	public final static MaterialData QUARTZ_ORE = define(true, false, Material.QUARTZ_ORE);
	public final static MaterialData HOPPER = define(false, false, Material.HOPPER);
	public final static MaterialData QUARTZ_BLOCK = define(true, false, Material.QUARTZ_BLOCK);
	public final static MaterialData QUARTZ_STAIRS = define(false, false, new Stairs(Material.QUARTZ_STAIRS));
	public final static MaterialData ACTIVATOR_RAIL = define(false, false, Material.ACTIVATOR_RAIL);
	public final static MaterialData DROPPER = define(false, false, Material.DROPPER);

	@Deprecated
	public final static MaterialData STAINED_CLAY = define(false, true, Material.WHITE_TERRACOTTA);
	public final static MaterialData WHITE_CLAY = define(true, false, new Stained_Clay(DyeColor.WHITE));
	public final static MaterialData ORANGE_CLAY = define(true, false, new Stained_Clay(DyeColor.ORANGE));
	public final static MaterialData MAGENTA_CLAY = define(true, false, new Stained_Clay(DyeColor.MAGENTA));
	public final static MaterialData LIGHT_BLUE_CLAY = define(true, false, new Stained_Clay(DyeColor.LIGHT_BLUE));
	public final static MaterialData YELLOW_CLAY = define(true, false, new Stained_Clay(DyeColor.YELLOW));
	public final static MaterialData LIME_CLAY = define(true, false, new Stained_Clay(DyeColor.LIME));
	public final static MaterialData PINK_CLAY = define(true, false, new Stained_Clay(DyeColor.PINK));
	public final static MaterialData GRAY_CLAY = define(true, false, new Stained_Clay(DyeColor.GRAY));
	public final static MaterialData SILVER_CLAY = define(true, false, new Stained_Clay(DyeColor.LIGHT_GRAY));
	public final static MaterialData CYAN_CLAY = define(true, false, new Stained_Clay(DyeColor.CYAN));
	public final static MaterialData PURPLE_CLAY = define(true, false, new Stained_Clay(DyeColor.PURPLE));
	public final static MaterialData BLUE_CLAY = define(true, false, new Stained_Clay(DyeColor.BLUE));
	public final static MaterialData BROWN_CLAY = define(true, false, new Stained_Clay(DyeColor.BROWN));
	public final static MaterialData GREEN_CLAY = define(true, false, new Stained_Clay(DyeColor.GREEN));
	public final static MaterialData RED_CLAY = define(true, false, new Stained_Clay(DyeColor.RED));
	public final static MaterialData BLACK_CLAY = define(true, false, new Stained_Clay(DyeColor.BLACK));

	@Deprecated
	public final static MaterialData STAINED_GLASS_PANE = define(false, true, Material.WHITE_STAINED_GLASS_PANE);
	public final static MaterialData WHITE_GLASS_PANE = define(true, false, new Stained_Glass_Pane(DyeColor.WHITE));
	public final static MaterialData ORANGE_GLASS_PANE = define(true, false, new Stained_Glass_Pane(DyeColor.ORANGE));
	public final static MaterialData MAGENTA_GLASS_PANE = define(true, false, new Stained_Glass_Pane(DyeColor.MAGENTA));
	public final static MaterialData LIGHT_BLUE_GLASS_PANE = define(true, false, new Stained_Glass_Pane(DyeColor.LIGHT_BLUE));
	public final static MaterialData YELLOW_GLASS_PANE = define(true, false, new Stained_Glass_Pane(DyeColor.YELLOW));
	public final static MaterialData LIME_GLASS_PANE = define(true, false, new Stained_Glass_Pane(DyeColor.LIME));
	public final static MaterialData PINK_GLASS_PANE = define(true, false, new Stained_Glass_Pane(DyeColor.PINK));
	public final static MaterialData GRAY_GLASS_PANE = define(true, false, new Stained_Glass_Pane(DyeColor.GRAY));
	public final static MaterialData SILVER_GLASS_PANE = define(true, false, new Stained_Glass_Pane(DyeColor.LIGHT_GRAY));
	public final static MaterialData CYAN_GLASS_PANE = define(true, false, new Stained_Glass_Pane(DyeColor.CYAN));
	public final static MaterialData PURPLE_GLASS_PANE = define(true, false, new Stained_Glass_Pane(DyeColor.PURPLE));
	public final static MaterialData BLUE_GLASS_PANE = define(true, false, new Stained_Glass_Pane(DyeColor.BLUE));
	public final static MaterialData BROWN_GLASS_PANE = define(true, false, new Stained_Glass_Pane(DyeColor.BROWN));
	public final static MaterialData GREEN_GLASS_PANE = define(true, false, new Stained_Glass_Pane(DyeColor.GREEN));
	public final static MaterialData RED_GLASS_PANE = define(true, false, new Stained_Glass_Pane(DyeColor.RED));
	public final static MaterialData BLACK_GLASS_PANE = define(true, false, new Stained_Glass_Pane(DyeColor.BLACK));

	@Deprecated
	public final static MaterialData LEAVES_2 = define(false, true, Material.LEAVES_2);
	@Deprecated
	public final static MaterialData LOG_2 = define(false, true, Material.SPRUCE_LOG_2);

	public final static MaterialData ACACIA_STAIRS = define(false, false, new Stairs(Material.ACACIA_STAIRS));
	public final static MaterialData DARK_OAK_STAIRS = define(false, false, new Stairs(Material.DARK_OAK_STAIRS));
	public final static MaterialData SLIME_BLOCK = define(false, false, Material.SLIME_BLOCK);
	public final static MaterialData BARRIER = define(false, false, Material.BARRIER);
	public final static MaterialData IRON_TRAPDOOR = define(false, false, Material.IRON_TRAPDOOR);

	public final static MaterialData PRISMARINE = define(true, false, new Prismarine(PrismarineType.SMOOTH));
	public final static MaterialData PRISMARINE_BRICK = define(true, false, new Prismarine(PrismarineType.BRICK));
	public final static MaterialData DARK_PRISMARINE = define(true, false, new Prismarine(PrismarineType.DARK));

	public final static MaterialData SEA_LANTERN = define(true, false, Material.SEA_LANTERN);
	public final static MaterialData HAY_BLOCK = define(false, false, Material.HAY_BLOCK);

	@Deprecated
	public final static MaterialData CARPET = define(false, true, Material.CARPET);
	public final static MaterialData WHITE_CARPET = define(false, false, new Carpet(DyeColor.WHITE));
	public final static MaterialData ORANGE_CARPET = define(false, false, new Carpet(DyeColor.ORANGE));
	public final static MaterialData MAGENTA_CARPET = define(false, false, new Carpet(DyeColor.MAGENTA));
	public final static MaterialData LIGHT_BLUE_CARPET = define(false, false, new Carpet(DyeColor.LIGHT_BLUE));
	public final static MaterialData YELLOW_CARPET = define(false, false, new Carpet(DyeColor.YELLOW));
	public final static MaterialData LIME_CARPET = define(false, false, new Carpet(DyeColor.LIME));
	public final static MaterialData PINK_CARPET = define(false, false, new Carpet(DyeColor.PINK));
	public final static MaterialData GRAY_CARPET = define(false, false, new Carpet(DyeColor.GRAY));
	public final static MaterialData SILVER_CARPET = define(false, false, new Carpet(DyeColor.LIGHT_GRAY));
	public final static MaterialData CYAN_CARPET = define(false, false, new Carpet(DyeColor.CYAN));
	public final static MaterialData PURPLE_CARPET = define(false, false, new Carpet(DyeColor.PURPLE));
	public final static MaterialData BLUE_CARPET = define(false, false, new Carpet(DyeColor.BLUE));
	public final static MaterialData BROWN_CARPET = define(false, false, new Carpet(DyeColor.BROWN));
	public final static MaterialData GREEN_CARPET = define(false, false, new Carpet(DyeColor.GREEN));
	public final static MaterialData RED_CARPET = define(false, false, new Carpet(DyeColor.RED));
	public final static MaterialData BLACK_CARPET = define(false, false, new Stained_Clay(DyeColor.BLACK));

	public final static MaterialData HARD_CLAY = define(true, false, Material.TERRACOTTA);
	public final static MaterialData COAL_BLOCK = define(true, false, Material.COAL_BLOCK);
	public final static MaterialData PACKED_ICE = define(false, false, Material.PACKED_ICE);
	public final static MaterialData DOUBLE_PLANT = define(false, false, Material.DOUBLE_PLANT);
	public final static MaterialData STANDING_BANNER = define(false, false, Material.STANDING_BANNER);
	public final static MaterialData WALL_BANNER = define(false, false, Material.WALL_BANNER);
	public final static MaterialData DAYLIGHT_DETECTOR_INVERTED = define(false, false, Material.DAYLIGHT_DETECTOR_INVERTED);

	public final static MaterialData RED_SANDSTONE = define(false, false, Material.RED_SANDSTONE);
	public final static MaterialData CRACKED_RED_SANDSTONE = define(true, false, new Red_Sandstone(SandstoneType.CRACKED));
	public final static MaterialData GLYPHED_RED_SANDSTONE = define(true, false, new Red_Sandstone(SandstoneType.GLYPHED));

	public final static MaterialData RED_SANDSTONE_STAIRS = define(false, false, new Stairs(Material.RED_SANDSTONE_STAIRS));

	@Deprecated
	public final static MaterialData DOUBLE_STONE_SLAB2 = define(false, true, Material.DOUBLE_STONE_SLAB2);
	@Deprecated
	public final static MaterialData STONE_SLAB2 = define(false, true, Material.STONE_SLAB2);

	public final static MaterialData SPRUCE_FENCE_GATE = define(false, false, Material.SPRUCE_FENCE_GATE);
	public final static MaterialData BIRCH_FENCE_GATE = define(false, false, Material.BIRCH_FENCE_GATE);
	public final static MaterialData JUNGLE_FENCE_GATE = define(false, false, Material.JUNGLE_FENCE_GATE);
	public final static MaterialData DARK_OAK_FENCE_GATE = define(false, false, Material.DARK_OAK_FENCE_GATE);
	public final static MaterialData ACACIA_FENCE_GATE = define(false, false, Material.ACACIA_FENCE_GATE);
	public final static MaterialData SPRUCE_FENCE = define(false, false, Material.SPRUCE_FENCE);
	public final static MaterialData BIRCH_FENCE = define(false, false, Material.BIRCH_FENCE);
	public final static MaterialData JUNGLE_FENCE = define(false, false, Material.JUNGLE_FENCE);
	public final static MaterialData DARK_OAK_FENCE = define(false, false, Material.DARK_OAK_FENCE);
	public final static MaterialData ACACIA_FENCE = define(false, false, Material.ACACIA_FENCE);
	public final static MaterialData SPRUCE_DOOR = define(false, false, Material.SPRUCE_DOOR);
	public final static MaterialData BIRCH_DOOR = define(false, false, Material.BIRCH_DOOR);
	public final static MaterialData JUNGLE_DOOR = define(false, false, Material.JUNGLE_DOOR);
	public final static MaterialData ACACIA_DOOR = define(false, false, Material.ACACIA_DOOR);
	public final static MaterialData DARK_OAK_DOOR = define(false, false, Material.DARK_OAK_DOOR);
	public final static MaterialData END_ROD = define(false, false, Material.END_ROD);
	public final static MaterialData CHORUS_PLANT = define(false, false, Material.CHORUS_PLANT);
	public final static MaterialData CHORUS_FLOWER = define(false, false, Material.CHORUS_FLOWER);
	public final static MaterialData PURPUR_BLOCK = define(true, false, Material.PURPUR_BLOCK);
	public final static MaterialData PURPUR_PILLAR = define(true, false, Material.PURPUR_PILLAR);
	public final static MaterialData PURPUR_STAIRS = define(false, false, new Stairs(Material.PURPUR_STAIRS));
	public final static MaterialData PURPUR_DOUBLE_SLAB = define(true, false, Material.PURPUR_DOUBLE_SLAB);
	public final static MaterialData PURPUR_SLAB = define(false, false, Material.PURPUR_SLAB);

	@Deprecated
	public final static MaterialData END_BRICKS = define(false, true, Material.END_STONE_BRICKS);
	public final static MaterialData END_BRICK = define(true, false, Material.END_STONE_BRICKS);

	public final static MaterialData BEETROOT_BLOCK = define(false, false, Material.BEETROOT_BLOCK);
	public final static MaterialData GRASS_PATH = define(false, false, Material.GRASS_PATH);
	public final static MaterialData END_GATEWAY = define(false, false, Material.END_GATEWAY);
	public final static MaterialData COMMAND_REPEATING = define(false, false, Material.COMMAND_REPEATING);
	public final static MaterialData COMMAND_CHAIN = define(false, false, Material.COMMAND_CHAIN);
	public final static MaterialData FROSTED_ICE = define(false, false, Material.FROSTED_ICE);
	public final static MaterialData MAGMA = define(true, false, Material.MAGMA);
	public final static MaterialData NETHER_WART_BLOCK = define(false, false, Material.NETHER_WART_BLOCK);
	public final static MaterialData RED_NETHER_BRICK = define(false, false, Material.RED_NETHER_BRICK);
	public final static MaterialData BONE_BLOCK = define(true, false, Material.BONE_BLOCK);
	public final static MaterialData STRUCTURE_VOID = define(false, false, Material.STRUCTURE_VOID);
	public final static MaterialData OBSERVER = define(false, false, Material.OBSERVER);
	public final static MaterialData WHITE_SHULKER_BOX = define(false, false, Material.WHITE_SHULKER_BOX);
	public final static MaterialData ORANGE_SHULKER_BOX = define(false, false, Material.ORANGE_SHULKER_BOX);
	public final static MaterialData MAGENTA_SHULKER_BOX = define(false, false, Material.MAGENTA_SHULKER_BOX);
	public final static MaterialData LIGHT_BLUE_SHULKER_BOX = define(false, false, Material.LIGHT_BLUE_SHULKER_BOX);
	public final static MaterialData YELLOW_SHULKER_BOX = define(false, false, Material.YELLOW_SHULKER_BOX);
	public final static MaterialData LIME_SHULKER_BOX = define(false, false, Material.LIME_SHULKER_BOX);
	public final static MaterialData PINK_SHULKER_BOX = define(false, false, Material.PINK_SHULKER_BOX);
	public final static MaterialData GRAY_SHULKER_BOX = define(false, false, Material.GRAY_SHULKER_BOX);
	public final static MaterialData SILVER_SHULKER_BOX = define(false, false, Material.SILVER_SHULKER_BOX);
	public final static MaterialData CYAN_SHULKER_BOX = define(false, false, Material.CYAN_SHULKER_BOX);
	public final static MaterialData PURPLE_SHULKER_BOX = define(false, false, Material.PURPLE_SHULKER_BOX);
	public final static MaterialData BLUE_SHULKER_BOX = define(false, false, Material.BLUE_SHULKER_BOX);
	public final static MaterialData BROWN_SHULKER_BOX = define(false, false, Material.BROWN_SHULKER_BOX);
	public final static MaterialData GREEN_SHULKER_BOX = define(false, false, Material.GREEN_SHULKER_BOX);
	public final static MaterialData RED_SHULKER_BOX = define(false, false, Material.RED_SHULKER_BOX);
	public final static MaterialData BLACK_SHULKER_BOX = define(false, false, Material.BLACK_SHULKER_BOX);

	public final static MaterialData WHITE_GLAZED_TERRACOTTA = define(true, false, Material.WHITE_GLAZED_TERRACOTTA);
	public final static MaterialData ORANGE_GLAZED_TERRACOTTA = define(true, false, Material.ORANGE_GLAZED_TERRACOTTA);
	public final static MaterialData MAGENTA_GLAZED_TERRACOTTA = define(true, false, Material.MAGENTA_GLAZED_TERRACOTTA);
	public final static MaterialData LIGHT_BLUE_GLAZED_TERRACOTTA = define(true, false, Material.LIGHT_BLUE_GLAZED_TERRACOTTA);
	public final static MaterialData YELLOW_GLAZED_TERRACOTTA = define(true, false, Material.YELLOW_GLAZED_TERRACOTTA);
	public final static MaterialData LIME_GLAZED_TERRACOTTA = define(true, false, Material.LIME_GLAZED_TERRACOTTA);
	public final static MaterialData PINK_GLAZED_TERRACOTTA = define(true, false, Material.PINK_GLAZED_TERRACOTTA);
	public final static MaterialData GRAY_GLAZED_TERRACOTTA = define(true, false, Material.GRAY_GLAZED_TERRACOTTA);
	public final static MaterialData SILVER_GLAZED_TERRACOTTA = define(true, false, Material.SILVER_GLAZED_TERRACOTTA);
	public final static MaterialData CYAN_GLAZED_TERRACOTTA = define(true, false, Material.CYAN_GLAZED_TERRACOTTA);
	public final static MaterialData PURPLE_GLAZED_TERRACOTTA = define(true, false, Material.PURPLE_GLAZED_TERRACOTTA);
	public final static MaterialData BLUE_GLAZED_TERRACOTTA = define(true, false, Material.BLUE_GLAZED_TERRACOTTA);
	public final static MaterialData BROWN_GLAZED_TERRACOTTA = define(true, false, Material.BROWN_GLAZED_TERRACOTTA);
	public final static MaterialData GREEN_GLAZED_TERRACOTTA = define(true, false, Material.GREEN_GLAZED_TERRACOTTA);
	public final static MaterialData RED_GLAZED_TERRACOTTA = define(true, false, Material.RED_GLAZED_TERRACOTTA);
	public final static MaterialData BLACK_GLAZED_TERRACOTTA = define(true, false, Material.BLACK_GLAZED_TERRACOTTA);

	@Deprecated
	public final static MaterialData CONCRETE = define(false, true, Material.CONCRETE);
	public final static MaterialData WHITE_CONCRETE = define(true, false, new Concrete(DyeColor.WHITE));
	public final static MaterialData ORANGE_CONCRETE = define(true, false, new Concrete(DyeColor.ORANGE));
	public final static MaterialData MAGENTA_CONCRETE = define(true, false, new Concrete(DyeColor.MAGENTA));
	public final static MaterialData LIGHT_BLUE_CONCRETE = define(true, false, new Concrete(DyeColor.LIGHT_BLUE));
	public final static MaterialData YELLOW_CONCRETE = define(true, false, new Concrete(DyeColor.YELLOW));
	public final static MaterialData LIME_CONCRETE = define(true, false, new Concrete(DyeColor.LIME));
	public final static MaterialData PINK_CONCRETE = define(true, false, new Concrete(DyeColor.PINK));
	public final static MaterialData GRAY_CONCRETE = define(true, false, new Concrete(DyeColor.GRAY));
	public final static MaterialData SILVER_CONCRETE = define(true, false, new Concrete(DyeColor.LIGHT_GRAY));
	public final static MaterialData CYAN_CONCRETE = define(true, false, new Concrete(DyeColor.CYAN));
	public final static MaterialData PURPLE_CONCRETE = define(true, false, new Concrete(DyeColor.PURPLE));
	public final static MaterialData BLUE_CONCRETE = define(true, false, new Concrete(DyeColor.BLUE));
	public final static MaterialData BROWN_CONCRETE = define(true, false, new Concrete(DyeColor.BROWN));
	public final static MaterialData GREEN_CONCRETE = define(true, false, new Concrete(DyeColor.GREEN));
	public final static MaterialData RED_CONCRETE = define(true, false, new Concrete(DyeColor.RED));
	public final static MaterialData BLACK_CONCRETE = define(true, false, new Concrete(DyeColor.BLACK));

	@Deprecated
	public final static MaterialData CONCRETE_POWDER = define(false, true, Material.CONCRETE_POWDER);
	public final static MaterialData WHITE_CONCRETE_POWDER = define(true, false, new Concrete_Powder(DyeColor.WHITE));
	public final static MaterialData ORANGE_CONCRETE_POWDER = define(true, false, new Concrete_Powder(DyeColor.ORANGE));
	public final static MaterialData MAGENTA_CONCRETE_POWDER = define(true, false, new Concrete_Powder(DyeColor.MAGENTA));
	public final static MaterialData LIGHT_BLUE_CONCRETE_POWDER = define(true, false, new Concrete_Powder(DyeColor.LIGHT_BLUE));
	public final static MaterialData YELLOW_CONCRETE_POWDER = define(true, false, new Concrete_Powder(DyeColor.YELLOW));
	public final static MaterialData LIME_CONCRETE_POWDER = define(true, false, new Concrete_Powder(DyeColor.LIME));
	public final static MaterialData PINK_CONCRETE_POWDER = define(true, false, new Concrete_Powder(DyeColor.PINK));
	public final static MaterialData GRAY_CONCRETE_POWDER = define(true, false, new Concrete_Powder(DyeColor.GRAY));
	public final static MaterialData SILVER_CONCRETE_POWDER = define(true, false, new Concrete_Powder(DyeColor.LIGHT_GRAY));
	public final static MaterialData CYAN_CONCRETE_POWDER = define(true, false, new Concrete_Powder(DyeColor.CYAN));
	public final static MaterialData PURPLE_CONCRETE_POWDER = define(true, false, new Concrete_Powder(DyeColor.PURPLE));
	public final static MaterialData BLUE_CONCRETE_POWDER = define(true, false, new Concrete_Powder(DyeColor.BLUE));
	public final static MaterialData BROWN_CONCRETE_POWDER = define(true, false, new Concrete_Powder(DyeColor.BROWN));
	public final static MaterialData GREEN_CONCRETE_POWDER = define(true, false, new Concrete_Powder(DyeColor.GREEN));
	public final static MaterialData RED_CONCRETE_POWDER = define(true, false, new Concrete_Powder(DyeColor.RED));
	public final static MaterialData BLACK_CONCRETE_POWDER = define(true, false, new Concrete_Powder(DyeColor.BLACK));

	public final static MaterialData STRUCTURE_BLOCK = define(false, false, Material.STRUCTURE_BLOCK);

	// List of items
	public final static MaterialData IRON_SPADE = define(false, false,Material.IRON_SHOVEL);
	public final static MaterialData IRON_PICKAXE = define(false, false, Material.IRON_PICKAXE);
	public final static MaterialData IRON_AXE = define(false, false, Material.IRON_AXE);
	public final static MaterialData FLINT_AND_STEEL = define(false, false, Material.FLINT_AND_STEEL);
	public final static MaterialData APPLE = define(false, false, Material.APPLE);
	public final static MaterialData BOW = define(false, false, Material.BOW);
	public final static MaterialData ARROW = define(false, false, Material.ARROW);
	public final static MaterialData COAL = define(false, false, Material.COAL);
	public final static MaterialData DIAMOND = define(false, false, Material.DIAMOND);
	public final static MaterialData IRON_INGOT = define(false, false, Material.IRON_INGOT);
	public final static MaterialData GOLD_INGOT = define(false, false, Material.GOLD_INGOT);
	public final static MaterialData IRON_SWORD = define(false, false, Material.IRON_SWORD);
	public final static MaterialData WOOD_SWORD = define(false, false, Material.WOOD_SWORD);
	public final static MaterialData WOOD_SPADE = define(false, false, Material.WOOD_SPADE);
	public final static MaterialData WOOD_PICKAXE = define(false, false, Material.WOOD_PICKAXE);
	public final static MaterialData WOOD_AXE = define(false, false, Material.WOOD_AXE);
	public final static MaterialData STONE_SWORD = define(false, false, Material.STONE_SWORD);
	public final static MaterialData STONE_SPADE = define(false, false,Material.STONE_SHOVEL);
	public final static MaterialData STONE_PICKAXE = define(false, false, Material.STONE_PICKAXE);
	public final static MaterialData STONE_AXE = define(false, false, Material.STONE_AXE);
	public final static MaterialData DIAMOND_SWORD = define(false, false, Material.DIAMOND_SWORD);
	public final static MaterialData DIAMOND_SPADE = define(false, false, Material.DIAMOND_SPADE);
	public final static MaterialData DIAMOND_PICKAXE = define(false, false, Material.DIAMOND_PICKAXE);
	public final static MaterialData DIAMOND_AXE = define(false, false, Material.DIAMOND_AXE);
	public final static MaterialData STICK = define(false, false, Material.STICK);
	public final static MaterialData BOWL = define(false, false, Material.BOWL);
	public final static MaterialData MUSHROOM_SOUP = define(false, false, Material.MUSHROOM_STEW);
	public final static MaterialData GOLD_SWORD = define(false, false, Material.GOLD_SWORD);
	public final static MaterialData GOLD_SPADE = define(false, false, Material.GOLD_SPADE);
	public final static MaterialData GOLD_PICKAXE = define(false, false, Material.GOLD_PICKAXE);
	public final static MaterialData GOLD_AXE = define(false, false, Material.GOLD_AXE);
	public final static MaterialData STRING = define(false, false, Material.STRING);
	public final static MaterialData FEATHER = define(false, false, Material.FEATHER);
	public final static MaterialData SULPHUR = define(false, false, Material.GUNPOWDER);
	public final static MaterialData WOOD_HOE = define(false, false, Material.WOOD_HOE);
	public final static MaterialData STONE_HOE = define(false, false, Material.STONE_HOE);
	public final static MaterialData IRON_HOE = define(false, false, Material.IRON_HOE);
	public final static MaterialData DIAMOND_HOE = define(false, false, Material.DIAMOND_HOE);
	public final static MaterialData GOLD_HOE = define(false, false, Material.GOLD_HOE);
	public final static MaterialData SEEDS = define(false, false, Material.WHEAT_SEEDS);
	public final static MaterialData WHEAT = define(false, false, Material.WHEAT);
	public final static MaterialData BREAD = define(false, false, Material.BREAD);
	public final static MaterialData LEATHER_HELMET = define(false, false, Material.LEATHER_HELMET);
	public final static MaterialData LEATHER_CHESTPLATE = define(false, false, Material.LEATHER_CHESTPLATE);
	public final static MaterialData LEATHER_LEGGINGS = define(false, false, Material.LEATHER_LEGGINGS);
	public final static MaterialData LEATHER_BOOTS = define(false, false, Material.LEATHER_BOOTS);
	public final static MaterialData CHAINMAIL_HELMET = define(false, false, Material.CHAINMAIL_HELMET);
	public final static MaterialData CHAINMAIL_CHESTPLATE = define(false, false, Material.CHAINMAIL_CHESTPLATE);
	public final static MaterialData CHAINMAIL_LEGGINGS = define(false, false, Material.CHAINMAIL_LEGGINGS);
	public final static MaterialData CHAINMAIL_BOOTS = define(false, false, Material.CHAINMAIL_BOOTS);
	public final static MaterialData IRON_HELMET = define(false, false, Material.IRON_HELMET);
	public final static MaterialData IRON_CHESTPLATE = define(false, false, Material.IRON_CHESTPLATE);
	public final static MaterialData IRON_LEGGINGS = define(false, false, Material.IRON_LEGGINGS);
	public final static MaterialData IRON_BOOTS = define(false, false, Material.IRON_BOOTS);
	public final static MaterialData DIAMOND_HELMET = define(false, false, Material.DIAMOND_HELMET);
	public final static MaterialData DIAMOND_CHESTPLATE = define(false, false, Material.DIAMOND_CHESTPLATE);
	public final static MaterialData DIAMOND_LEGGINGS = define(false, false, Material.DIAMOND_LEGGINGS);
	public final static MaterialData DIAMOND_BOOTS = define(false, false, Material.DIAMOND_BOOTS);
	public final static MaterialData GOLD_HELMET = define(false, false, Material.GOLD_HELMET);
	public final static MaterialData GOLD_CHESTPLATE = define(false, false, Material.GOLD_CHESTPLATE);
	public final static MaterialData GOLD_LEGGINGS = define(false, false, Material.GOLD_LEGGINGS);
	public final static MaterialData GOLD_BOOTS = define(false, false, Material.GOLD_BOOTS);
	public final static MaterialData FLINT = define(false, false, Material.FLINT);
	public final static MaterialData PORK = define(false, false, Material.PORKCHOP);
	public final static MaterialData GRILLED_PORK = define(false, false, Material.COOKED_PORKCHOP);
	public final static MaterialData PAINTING = define(false, false, Material.PAINTING);
	public final static MaterialData GOLDEN_APPLE = define(false, false, Material.GOLDEN_APPLE);
	public final static MaterialData SIGN = define(false, false, Material.SIGN);
	public final static MaterialData WOOD_DOOR = define(false, false, Material.WOOD_DOOR);
	public final static MaterialData BUCKET = define(false, false, Material.BUCKET);
	public final static MaterialData WATER_BUCKET = define(false, false, Material.WATER_BUCKET);
	public final static MaterialData LAVA_BUCKET = define(false, false, Material.LAVA_BUCKET);
	public final static MaterialData MINECART = define(false, false, Material.MINECART);
	public final static MaterialData SADDLE = define(false, false, Material.SADDLE);
	public final static MaterialData IRON_DOOR = define(false, false, Material.IRON_DOOR);
	public final static MaterialData REDSTONE = define(false, false, Material.REDSTONE);
	public final static MaterialData SNOW_BALL = define(false, false, Material.SNOW_BALL);
	public final static MaterialData BOAT = define(false, false, Material.BOAT);
	public final static MaterialData LEATHER = define(false, false, Material.LEATHER);
	public final static MaterialData MILK_BUCKET = define(false, false, Material.MILK_BUCKET);
	public final static MaterialData CLAY_BRICK = define(false, false, Material.CLAY_BRICK);
	public final static MaterialData CLAY_BALL = define(false, false, Material.CLAY_BALL);
	public final static MaterialData SUGAR_CANE = define(false, false, Material.SUGAR_CANE);
	public final static MaterialData PAPER = define(false, false, Material.PAPER);
	public final static MaterialData BOOK = define(false, false, Material.BOOK);
	public final static MaterialData SLIME_BALL = define(false, false, Material.SLIME_BALL);
	public final static MaterialData STORAGE_MINECART = define(false, false, Material.STORAGE_MINECART);
	public final static MaterialData POWERED_MINECART = define(false, false, Material.POWERED_MINECART);
	public final static MaterialData EGG = define(false, false, Material.EGG);
	public final static MaterialData COMPASS = define(false, false, Material.COMPASS);
	public final static MaterialData FISHING_ROD = define(false, false, Material.FISHING_ROD);
	public final static MaterialData WATCH = define(false, false, Material.WATCH);
	public final static MaterialData GLOWSTONE_DUST = define(false, false, Material.GLOWSTONE_DUST);
	public final static MaterialData RAW_FISH = define(false, false, Material.COD);
	public final static MaterialData COOKED_FISH = define(false, false, Material.COOKED_COD);
	public final static MaterialData INK_SACK = define(false, false, Material.INK_SACK);
	public final static MaterialData BONE = define(false, false, Material.BONE);
	public final static MaterialData SUGAR = define(false, false, Material.SUGAR);
	public final static MaterialData CAKE = define(false, false, Material.CAKE);
	public final static MaterialData BED = define(false, false, Material.BED);
	public final static MaterialData DIODE = define(false, false, Material.DIODE);
	public final static MaterialData COOKIE = define(false, false, Material.COOKIE);
	public final static MaterialData MAP = define(false, false, Material.MAP);
	public final static MaterialData SHEARS = define(false, false, Material.SHEARS);
	public final static MaterialData MELON = define(false, false, Material.MELON);
	public final static MaterialData PUMPKIN_SEEDS = define(false, false, Material.PUMPKIN_SEEDS);
	public final static MaterialData MELON_SEEDS = define(false, false, Material.MELON_SEEDS);
	public final static MaterialData RAW_BEEF = define(false, false, Material.BEEF);
	public final static MaterialData COOKED_BEEF = define(false, false, Material.COOKED_BEEF);
	public final static MaterialData RAW_CHICKEN = define(false, false, Material.CHICKEN);
	public final static MaterialData COOKED_CHICKEN = define(false, false, Material.COOKED_CHICKEN);
	public final static MaterialData ROTTEN_FLESH = define(false, false, Material.ROTTEN_FLESH);
	public final static MaterialData ENDER_PEARL = define(false, false, Material.ENDER_PEARL);
	public final static MaterialData BLAZE_ROD = define(false, false, Material.BLAZE_ROD);
	public final static MaterialData GHAST_TEAR = define(false, false, Material.GHAST_TEAR);
	public final static MaterialData GOLD_NUGGET = define(false, false, Material.GOLD_NUGGET);
	public final static MaterialData NETHER_STALK = define(false, false, Material.NETHER_STALK);
	public final static MaterialData POTION = define(false, false, Material.POTION);
	public final static MaterialData GLASS_BOTTLE = define(false, false, Material.GLASS_BOTTLE);
	public final static MaterialData SPIDER_EYE = define(false, false, Material.SPIDER_EYE);
	public final static MaterialData FERMENTED_SPIDER_EYE = define(false, false, Material.FERMENTED_SPIDER_EYE);
	public final static MaterialData BLAZE_POWDER = define(false, false, Material.BLAZE_POWDER);
	public final static MaterialData MAGMA_CREAM = define(false, false, Material.MAGMA_CREAM);
	public final static MaterialData BREWING_STAND_ITEM = define(false, false, Material.BREWING_STAND_ITEM);
	public final static MaterialData CAULDRON_ITEM = define(false, false, Material.CAULDRON_ITEM);
	public final static MaterialData EYE_OF_ENDER = define(false, false, Material.EYE_OF_ENDER);
	public final static MaterialData SPECKLED_MELON = define(false, false, Material.GLISTERING_MELON_SLICE);
	public final static MaterialData MONSTER_EGG = define(false, false, Material.MONSTER_EGG);
	public final static MaterialData EXP_BOTTLE = define(false, false, Material.EXP_BOTTLE);
	public final static MaterialData FIREBALL = define(false, false, Material.FIREBALL);
	public final static MaterialData BOOK_AND_QUILL = define(false, false, Material.BOOK_AND_QUILL);
	public final static MaterialData WRITTEN_BOOK = define(false, false, Material.WRITTEN_BOOK);
	public final static MaterialData EMERALD = define(false, false, Material.EMERALD);
	public final static MaterialData ITEM_FRAME = define(false, false, Material.ITEM_FRAME);
	public final static MaterialData FLOWER_POT_ITEM = define(false, false, Material.FLOWER_POT_ITEM);
	public final static MaterialData CARROT_ITEM = define(false, false, Material.CARROT);
	public final static MaterialData POTATO_ITEM = define(false, false, Material.POTATO);
	public final static MaterialData BAKED_POTATO = define(false, false, Material.BAKED_POTATO);
	public final static MaterialData POISONOUS_POTATO = define(false, false, Material.POISONOUS_POTATO);
	public final static MaterialData EMPTY_MAP = define(false, false, Material.EMPTY_MAP);
	public final static MaterialData GOLDEN_CARROT = define(false, false, Material.GOLDEN_CARROT);
	public final static MaterialData SKULL_ITEM = define(false, false, Material.SKULL_ITEM);
	public final static MaterialData CARROT_STICK = define(false, false, Material.CARROT_ON_A_STICK);
	public final static MaterialData NETHER_STAR = define(false, false, Material.NETHER_STAR);
	public final static MaterialData PUMPKIN_PIE = define(false, false, Material.PUMPKIN_PIE);
	public final static MaterialData FIREWORK = define(false, false, Material.FIREWORK);
	public final static MaterialData FIREWORK_CHARGE = define(false, false, Material.FIREWORK_CHARGE);
	public final static MaterialData ENCHANTED_BOOK = define(false, false, Material.ENCHANTED_BOOK);
	public final static MaterialData REDSTONE_COMPARATOR = define(false, false, Material.REDSTONE_COMPARATOR);
	public final static MaterialData NETHER_BRICK_ITEM = define(false, false, Material.NETHER_BRICK_ITEM);
	public final static MaterialData QUARTZ = define(false, false, Material.QUARTZ);
	public final static MaterialData EXPLOSIVE_MINECART = define(false, false, Material.EXPLOSIVE_MINECART);
	public final static MaterialData HOPPER_MINECART = define(false, false, Material.HOPPER_MINECART);
	public final static MaterialData PRISMARINE_SHARD = define(false, false, Material.PRISMARINE_SHARD);
	public final static MaterialData PRISMARINE_CRYSTALS = define(false, false, Material.PRISMARINE_CRYSTALS);
	public final static MaterialData RABBIT = define(false, false, Material.RABBIT);
	public final static MaterialData COOKED_RABBIT = define(false, false, Material.COOKED_RABBIT);
	public final static MaterialData RABBIT_STEW = define(false, false, Material.RABBIT_STEW);
	public final static MaterialData RABBIT_FOOT = define(false, false, Material.RABBIT_FOOT);
	public final static MaterialData RABBIT_HIDE = define(false, false, Material.RABBIT_HIDE);
	public final static MaterialData ARMOR_STAND = define(false, false, Material.ARMOR_STAND);
	public final static MaterialData IRON_BARDING = define(false, false, Material.IRON_BARDING);
	public final static MaterialData GOLD_BARDING = define(false, false, Material.GOLD_BARDING);
	public final static MaterialData DIAMOND_BARDING = define(false, false, Material.DIAMOND_BARDING);
	public final static MaterialData LEASH = define(false, false, Material.LEAD);
	public final static MaterialData NAME_TAG = define(false, false, Material.NAME_TAG);
	public final static MaterialData COMMAND_MINECART = define(false, false, Material.COMMAND_MINECART);
	public final static MaterialData MUTTON = define(false, false, Material.MUTTON);
	public final static MaterialData COOKED_MUTTON = define(false, false, Material.COOKED_MUTTON);
	public final static MaterialData BANNER = define(false, false, Material.BANNER);
	public final static MaterialData END_CRYSTAL = define(false, false, Material.END_CRYSTAL);
	public final static MaterialData SPRUCE_DOOR_ITEM = define(false, false, Material.SPRUCE_DOOR_ITEM);
	public final static MaterialData BIRCH_DOOR_ITEM = define(false, false, Material.BIRCH_DOOR_ITEM);
	public final static MaterialData JUNGLE_DOOR_ITEM = define(false, false, Material.JUNGLE_DOOR_ITEM);
	public final static MaterialData ACACIA_DOOR_ITEM = define(false, false, Material.ACACIA_DOOR_ITEM);
	public final static MaterialData DARK_OAK_DOOR_ITEM = define(false, false, Material.DARK_OAK_DOOR_ITEM);
	public final static MaterialData CHORUS_FRUIT = define(false, false, Material.CHORUS_FRUIT);
	public final static MaterialData CHORUS_FRUIT_POPPED = define(false, false, Material.CHORUS_FRUIT_POPPED);
	public final static MaterialData BEETROOT = define(false, false, Material.BEETROOT);
	public final static MaterialData BEETROOT_SEEDS = define(false, false, Material.BEETROOT_SEEDS);
	public final static MaterialData BEETROOT_SOUP = define(false, false, Material.BEETROOT_SOUP);
	public final static MaterialData DRAGONS_BREATH = define(false, false, Material.DRAGONS_BREATH);
	public final static MaterialData SPLASH_POTION = define(false, false, Material.SPLASH_POTION);
	public final static MaterialData SPECTRAL_ARROW = define(false, false, Material.SPECTRAL_ARROW);
	public final static MaterialData TIPPED_ARROW = define(false, false, Material.TIPPED_ARROW);
	public final static MaterialData LINGERING_POTION = define(false, false, Material.LINGERING_POTION);
	public final static MaterialData SHIELD = define(false, false, Material.SHIELD);
	public final static MaterialData ELYTRA = define(false, false, Material.ELYTRA);
	public final static MaterialData BOAT_SPRUCE = define(false, false, Material.BOAT_SPRUCE);
	public final static MaterialData BOAT_BIRCH = define(false, false, Material.BOAT_BIRCH);
	public final static MaterialData BOAT_JUNGLE = define(false, false, Material.BOAT_JUNGLE);
	public final static MaterialData BOAT_ACACIA = define(false, false, Material.BOAT_ACACIA);
	public final static MaterialData BOAT_DARK_OAK = define(false, false, Material.BOAT_DARK_OAK);
	public final static MaterialData TOTEM = define(false, false, Material.TOTEM);
	public final static MaterialData SHULKER_SHELL = define(false, false, Material.SHULKER_SHELL);
	public final static MaterialData IRON_NUGGET = define(false, false, Material.IRON_NUGGET);
	public final static MaterialData KNOWLEDGE_BOOK = define(false, false, Material.KNOWLEDGE_BOOK);
	public final static MaterialData GOLD_RECORD = define(false, false, Material.GOLD_RECORD);
	public final static MaterialData GREEN_RECORD = define(false, false, Material.GREEN_RECORD);
	public final static MaterialData RECORD_3 = define(false, false, Material.RECORD_3);
	public final static MaterialData RECORD_4 = define(false, false, Material.RECORD_4);
	public final static MaterialData RECORD_5 = define(false, false, Material.RECORD_5);
	public final static MaterialData RECORD_6 = define(false, false, Material.RECORD_6);
	public final static MaterialData RECORD_7 = define(false, false, Material.RECORD_7);
	public final static MaterialData RECORD_8 = define(false, false, Material.RECORD_8);
	public final static MaterialData RECORD_9 = define(false, false, Material.RECORD_9);
	public final static MaterialData RECORD_10 = define(false, false, Material.RECORD_10);
	public final static MaterialData RECORD_11 = define(false, false, Material.RECORD_11);
	public final static MaterialData RECORD_12 = define(false, false, Material.RECORD_12);
	
//	private static Map<Integer, MaterialEntry> entries = new HashMap<Integer, MaterialEntry>();
//	
//	private static class MaterialEntry {
//		private final String ident;
//		private final boolean stackable;
//		private final MaterialData data;
//		
//		private MaterialEntry(String ident, boolean stackable, MaterialData data) {
//			this.ident = ident;
//			this.stackable = stackable;
//			this.data = data;
//		}
//		
//		// TODO figure out a more elegant way to do this
//		private static int maxTypeId = 4096;
//		
//		@SuppressWarnings("deprecation")
//		private static int calcHash(MaterialData data) {
//			return data.getItemTypeId() * maxTypeId + data.getData();
//		}
//
//		@SuppressWarnings("deprecation")
//		private static int calcHash(Material data) {
//			return data.getId() * maxTypeId;
//		}
//	}
	
	// PUBLIC APIs
	private final static MaterialData define(boolean buildable, boolean deprecated, Material material) {
		return(define(buildable, deprecated, new MaterialData(material)));
	}
	
	private final static MaterialData define(boolean buildable, boolean deprecated, MaterialData data) {
//		MaterialEntry entry = new MaterialEntry(ident, buildable, data);
//		entries.put(MaterialEntry.calcHash(data), entry);

		//TODO: Figure out deprecate to non-deprecate mapping
		
		return(data);
	}
	
	@Deprecated
	public final static MaterialData asData(Material type) {
		return new MaterialData(type);
	}
	
//	public final static MaterialData find(String ident) {
//		Set<Map.Entry<Integer, MaterialEntry>> entrySet = entries.entrySet();
//		for (Entry<Integer, MaterialEntry> entry : entrySet) {
//			if (entry.getValue().ident.equals(ident))
//				return entry.getValue().data;
//		}
//		return null;
//	}
//	
//	public final static String find(MaterialData data) {
//		MaterialEntry entry = entries.get(MaterialEntry.calcHash(data));
//		assert(entry != null);
//		return entry.ident;
//	}
	
	public final static boolean isType(Block block, MaterialData ... materials) {
		MaterialData blockData = block.getState().getData();
		for (MaterialData material : materials)
			if (material.equals(blockData))
				return true;
		return false;
	}
	
	public final static boolean isSimilarType(MaterialData original, MaterialData test) {
		if (original.getItemType() == test.getItemType())
			return true;
		//TODO this should also test for stairs, leave vs. leave2, etc.
		return false;
	}
	
	public final static Block setBlock(Block block, MaterialData material) {
		return setBlock(block, material, true);
	}
	
	public final static Block setBlock(Block block, MaterialData material, boolean update) {
		BlockState state = block.getState();
		assert(state != null);
		
		state.setType(material.getItemType());
		state.setData(material);
		state.update(update);
		return block;
	}
	
//	public final static boolean isStackable(MaterialData data) {
//		MaterialEntry entry = entries.get(MaterialEntry.calcHash(data));
//		assert(entry != null);
//		
//		return entry.stackable;
//	}
//	
//	@Deprecated
//	public final static boolean isStackable(Material data) {
//		MaterialEntry entry = entries.get(MaterialEntry.calcHash(data));
//		assert(entry != null);
//		
//		return entry.stackable;
//	}
//	
//	public final static boolean isStackable(Block block) {
//		MaterialEntry entry = entries.get(MaterialEntry.calcHash(block.getType()));
//		assert(entry != null);
//		
//		return entry.stackable;
//	}
	
	public final static MaterialData adjust(MaterialData material, DyeColor color) {
		MaterialData result = null;
		result = material.clone();
		assert(result != null);
		
		if (result instanceof Colorable) {
			((Colorable)result).setColor(color);
		} 
		return result;
	}
	
	public final static MaterialData adjust(MaterialData material, Material texture) {
		MaterialData result = null;
		result = material.clone();
		assert(result != null);
		
		if (result instanceof TexturedMaterial) {
			((TexturedMaterial)result).setMaterial(texture);
		} 
		return result;
	}
	
	public final static MaterialData adjust(MaterialData material, BlockFace facing) {
		MaterialData result = null;
		result = material.clone();
		assert(result != null);
		
		if (result instanceof Directional) {
			((Directional)result).setFacingDirection(facing);
		} 
		return result;
	}

	public final static Stairs adjust(Stairs material, BlockFace face, boolean invert) {
		Stairs result = null;
		result = material.clone();
		assert(result != null);
		
		result.setFacingDirection(face);
		result.setInverted(invert);
		return result;
	}
	
	public final static MaterialData getRandomStainedGlass(Odds odds) {
		return getRandomStainedGlass(odds, ColorSet.ALL);
	}
	
	public final static MaterialData getRandomStainedGlass(Odds odds, ColorSet color) {
		return adjust(RealMaterial.WHITE_GLASS, odds.getRandomColor(color));
	}
	
	public final static MaterialData getRandomStainedGlassPane(Odds odds) {
		return getRandomStainedGlassPane(odds, ColorSet.ALL);
	}
	
	public final static MaterialData getRandomStainedGlassPane(Odds odds, ColorSet color) {
		return adjust(RealMaterial.WHITE_GLASS_PANE, odds.getRandomColor(color));
	}
	
	// validator the ensures that I have all the ones that is in this version of Minecraft
	{
		//TODO I need to create an initializer that verifies that we include everything that is in the Material list
	}
}
