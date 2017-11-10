package me.daddychurchill.CityWorld.Support;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.SandstoneType;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.Colorable;
import org.bukkit.material.Directional;
import org.bukkit.material.Leaves;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Sandstone;
import org.bukkit.material.Sapling;
import org.bukkit.material.Stairs;
import org.bukkit.material.TexturedMaterial;
import org.bukkit.material.Wood;
import org.bukkit.material.Wool;

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
	public final static MaterialData AIR = define("AIR", false, false, Material.AIR);

	public final static MaterialData STONE = define("STONE", true, false, new Stone(StoneType.STONE));
	public final static MaterialData GRANITE = define("GRANITE", true, false, new Stone(StoneType.GRANITE));
	public final static MaterialData DIORITE = define("DIORITE", true, false, new Stone(StoneType.DIORITE));
	public final static MaterialData ANDESITE = define("ANDESITE", true, false, new Stone(StoneType.ANDESITE));
	public final static MaterialData POLISHED_GRANITE = define("POLISHED_GRANITE", true, false, new Stone(StoneType.POLISHED_GRANITE));
	public final static MaterialData POLISHED_DIORITE = define("POLISHED_DIORITE", true, false, new Stone(StoneType.POLISHED_DIORITE));
	public final static MaterialData POLISHED_ANDESITE = define("POLISHED_ANDESITE", true, false, new Stone(StoneType.POLISHED_ANDESITE));

	public final static MaterialData GRASS = define("GRASS", false, false, Material.GRASS);

	public final static MaterialData DIRT = define("DIRT", true, false, new Dirt(DirtType.DIRT));
	public final static MaterialData COARSE_DIRT = define("COARSE_DIRT", true, false, new Dirt(DirtType.COARSE_DIRT));
	public final static MaterialData PODZOL = define("PODZOL", true, false, new Dirt(DirtType.PODZOL));

	public final static MaterialData COBBLESTONE = define("COBBLESTONE", true, false, Material.COBBLESTONE);

	@Deprecated
	public final static MaterialData WOOD = define("WOOD", false, true, Material.WOOD);
	@Deprecated
	public final static MaterialData REDWOOD_WOOD = define("REDWOOD_WOOD", true, true, new Wood(TreeSpecies.REDWOOD));
	public final static MaterialData OAK_WOOD = define("OAK_WOOD", true, false, new Wood(TreeSpecies.GENERIC));
	public final static MaterialData SPRUCE_WOOD = define("SPRUCE_WOOD", true, false, new Wood(TreeSpecies.REDWOOD));
	public final static MaterialData BIRCH_WOOD = define("BIRCH_WOOD", true, false, new Wood(TreeSpecies.BIRCH));
	public final static MaterialData JUNGLE_WOOD = define("JUNGLE_WOOD", true, false, new Wood(TreeSpecies.JUNGLE));
	public final static MaterialData ACACIA_WOOD = define("ACACIA_WOOD", true, false, new Wood(TreeSpecies.ACACIA));
	public final static MaterialData DARK_OAK_WOOD = define("DARK_OAK_WOOD", true, false, new Wood(TreeSpecies.DARK_OAK));

	@Deprecated
	public final static MaterialData SAPLING = define("SAPLING", false, true, Material.SAPLING);
	@Deprecated
	public final static MaterialData REDWOOD_SAPLING = define("REDWOOD_SAPLING", true, true, new Wood(TreeSpecies.REDWOOD));
	public final static MaterialData OAK_SAPLING = define("OAK_SAPLING", false, false, new Sapling(TreeSpecies.GENERIC));
	public final static MaterialData SPRUCE_SAPLING = define("SPRUCE_SAPLING", false, false, new Sapling(TreeSpecies.REDWOOD));
	public final static MaterialData BIRCH_SAPLING = define("BIRCH_SAPLING", false, false, new Sapling(TreeSpecies.BIRCH));
	public final static MaterialData JUNGLE_SAPLING = define("JUNGLE_SAPLING", false, false, new Sapling(TreeSpecies.JUNGLE));
	public final static MaterialData ACACIA_SAPLING = define("ACACIA_SAPLING", false, false, new Sapling(TreeSpecies.ACACIA));
	public final static MaterialData DARK_OAK_SAPLING = define("DARK_OAK_SAPLING", false, false, new Sapling(TreeSpecies.DARK_OAK));

	public final static MaterialData BEDROCK = define("BEDROCK", true, false, Material.BEDROCK);
	
	@Deprecated
	public final static MaterialData WATER = define("WATER", false, true, Material.WATER);
	public final static MaterialData FLOWING_WATER = define("FLOWING_WATER", false, false, Material.WATER);
	public final static MaterialData STATIONARY_WATER = define("STATIONARY_WATER", false, false, Material.STATIONARY_WATER);

	@Deprecated
	public final static MaterialData LAVA = define("LAVA", false, true, Material.LAVA);
	public final static MaterialData FLOWING_LAVA = define("FLOWING_LAVA", false, false, Material.LAVA);
	public final static MaterialData STATIONARY_LAVA = define("STATIONARY_LAVA", false, false, Material.STATIONARY_LAVA);

	public final static MaterialData SAND = define("SAND", true, false, new Sand(SandType.SAND)); /* SHOULD BE DEPRECATED */
	public final static MaterialData RED_SAND = define("RED_SAND", true, false, new Sand(SandType.RED));

	public final static MaterialData GRAVEL = define("GRAVEL", true, false, Material.GRAVEL);
	public final static MaterialData GOLD_ORE = define("GOLD_ORE", true, false, Material.GOLD_ORE);
	public final static MaterialData IRON_ORE = define("IRON_ORE", true, false, Material.IRON_ORE);
	public final static MaterialData COAL_ORE = define("COAL_ORE", true, false, Material.COAL_ORE);

	@Deprecated
	public final static MaterialData LOG = define("LOG", false, true, Material.LOG);
	@Deprecated
	public final static MaterialData REDWOOD_LOG = define("REDWOOD_LOG", true, true, new Log(TreeSpecies.REDWOOD));
	public final static MaterialData OAK_LOG = define("OAK_LOG", true, false, new Log(TreeSpecies.GENERIC));
	public final static MaterialData SPRUCE_LOG = define("SPRUCE_LOG", true, false, new Log(TreeSpecies.REDWOOD));
	public final static MaterialData BIRCH_LOG = define("BIRCH_LOG", true, false, new Log(TreeSpecies.BIRCH));
	public final static MaterialData JUNGLE_LOG = define("JUNGLE_LOG", true, false, new Log(TreeSpecies.JUNGLE));
	public final static MaterialData ACACIA_LOG = define("ACACIA_LOG", true, false, new Log(TreeSpecies.ACACIA));
	public final static MaterialData DARK_OAK_LOG = define("DARK_OAK_LOG", true, false, new Log(TreeSpecies.DARK_OAK));

	@Deprecated
	public final static MaterialData LEAVES = define("LEAVES", false, true, Material.LEAVES);
	@Deprecated
	public final static MaterialData REDWOOD_LEAVES = define("REDWOOD_LEAVES", false, true, new Leaves(TreeSpecies.REDWOOD));
	public final static MaterialData OAK_LEAVES = define("OAK_LEAVES", false, false, new Leaves(TreeSpecies.GENERIC));
	public final static MaterialData SPRUCE_LEAVES = define("SPRUCE_LEAVES", false, false, new Leaves(TreeSpecies.REDWOOD));
	public final static MaterialData BIRCH_LEAVES = define("BIRCH_LEAVES", false, false, new Leaves(TreeSpecies.BIRCH));
	public final static MaterialData JUNGLE_LEAVES = define("JUNGLE_LEAVES", false, false, new Leaves(TreeSpecies.JUNGLE));
	public final static MaterialData ACACIA_LEAVES = define("ACACIA_LEAVES", false, false, new Leaves(TreeSpecies.ACACIA));
	public final static MaterialData DARK_OAK_LEAVES = define("DARK_OAK_LEAVES", false, false, new Leaves(TreeSpecies.DARK_OAK));

	@Deprecated
	public final static MaterialData SPONGE = define("SPONGE", false, true, Material.SPONGE);
	public final static MaterialData WET_SPONGE = define("WET_SPONGE", true, false, new Sponge(SpongeType.WET));
	public final static MaterialData DRY_SPONGE = define("DRY_SPONGE", true, false, new Sponge(SpongeType.DRY));

	public final static MaterialData GLASS = define("GLASS", true, false, Material.GLASS);
	public final static MaterialData LAPIS_ORE = define("LAPIS_ORE", true, false, Material.LAPIS_ORE);
	public final static MaterialData LAPIS_BLOCK = define("LAPIS_BLOCK", true, false, Material.LAPIS_BLOCK);
	public final static MaterialData DISPENSER = define("DISPENSER", false, false, Material.DISPENSER);

	public final static MaterialData SANDSTONE = define("SANDSTONE", true, false, new Sandstone(SandstoneType.CRACKED)); /* SHOULD BE DEPRECATED */
	public final static MaterialData GLYPHED_SANDSTONE = define("GLYPHED_SANDSTONE", true, false, new Sandstone(SandstoneType.GLYPHED));
	public final static MaterialData SMOOTH_SANDSTONE = define("SMOOTH_SANDSTONE", true, false, new Sandstone(SandstoneType.SMOOTH));

	public final static MaterialData NOTE_BLOCK = define("NOTE_BLOCK", false, false, Material.NOTE_BLOCK);
	public final static MaterialData BED_BLOCK = define("BED_BLOCK", false, false, Material.BED_BLOCK);
	public final static MaterialData POWERED_RAIL = define("POWERED_RAIL", false, false, Material.POWERED_RAIL);
	public final static MaterialData DETECTOR_RAIL = define("DETECTOR_RAIL", false, false, Material.DETECTOR_RAIL);
	public final static MaterialData PISTON_STICKY_BASE = define("PISTON_STICKY_BASE", false, false, Material.PISTON_STICKY_BASE);
	public final static MaterialData WEB = define("WEB", false, false, Material.WEB);
	public final static MaterialData LONG_GRASS = define("LONG_GRASS", false, false, Material.LONG_GRASS);
	public final static MaterialData DEAD_BUSH = define("DEAD_BUSH", false, false, Material.DEAD_BUSH);
	public final static MaterialData PISTON_BASE = define("PISTON_BASE", false, false, Material.PISTON_BASE);
	public final static MaterialData PISTON_EXTENSION = define("PISTON_EXTENSION", false, false, Material.PISTON_EXTENSION);

	@Deprecated
	public final static MaterialData WOOL = define("WOOL", false, true, Material.WOOL);
	public final static MaterialData WHITE_WOOL = define("WHITE_WOOL", true, false, new Wool(DyeColor.WHITE));
	public final static MaterialData ORANGE_WOOL = define("ORANGE_WOOL", true, false, new Wool(DyeColor.ORANGE));
	public final static MaterialData MAGENTA_WOOL = define("MAGENTA_WOOL", true, false, new Wool(DyeColor.MAGENTA));
	public final static MaterialData LIGHT_BLUE_WOOL = define("LIGHT_BLUE_WOOL", true, false, new Wool(DyeColor.LIGHT_BLUE));
	public final static MaterialData YELLOW_WOOL = define("YELLOW_WOOL", true, false, new Wool(DyeColor.YELLOW));
	public final static MaterialData LIME_WOOL = define("LIME_WOOL", true, false, new Wool(DyeColor.LIME));
	public final static MaterialData PINK_WOOL = define("PINK_WOOL", true, false, new Wool(DyeColor.PINK));
	public final static MaterialData GRAY_WOOL = define("GRAY_WOOL", true, false, new Wool(DyeColor.GRAY));
	public final static MaterialData SILVER_WOOL = define("SILVER_WOOL", true, false, new Wool(DyeColor.SILVER));
	public final static MaterialData CYAN_WOOL = define("CYAN_WOOL", true, false, new Wool(DyeColor.CYAN));
	public final static MaterialData PURPLE_WOOL = define("PURPLE_WOOL", true, false, new Wool(DyeColor.PURPLE));
	public final static MaterialData BLUE_WOOL = define("BLUE_WOOL", true, false, new Wool(DyeColor.BLUE));
	public final static MaterialData BROWN_WOOL = define("BROWN_WOOL", true, false, new Wool(DyeColor.BROWN));
	public final static MaterialData GREEN_WOOL = define("GREEN_WOOL", true, false, new Wool(DyeColor.GREEN));
	public final static MaterialData RED_WOOL = define("RED_WOOL", true, false, new Wool(DyeColor.RED));
	public final static MaterialData BLACK_WOOL = define("BLACK_WOOL", true, false, new Wool(DyeColor.BLACK));

	public final static MaterialData PISTON_MOVING_PIECE = define("PISTON_MOVING_PIECE", false, false, Material.PISTON_MOVING_PIECE);

	public final static MaterialData YELLOW_FLOWER = define("YELLOW_FLOWER", false, false, Material.YELLOW_FLOWER); //@@@
	public final static MaterialData RED_ROSE = define("RED_ROSE", false, false, Material.RED_ROSE); //@@@

	public final static MaterialData BROWN_MUSHROOM = define("BROWN_MUSHROOM", false, false, Material.BROWN_MUSHROOM);
	public final static MaterialData RED_MUSHROOM = define("RED_MUSHROOM", false, false, Material.RED_MUSHROOM);
	public final static MaterialData GOLD_BLOCK = define("GOLD_BLOCK", true, false, Material.GOLD_BLOCK);
	public final static MaterialData IRON_BLOCK = define("IRON_BLOCK", true, false, Material.IRON_BLOCK);

	@Deprecated
	public final static MaterialData DOUBLE_STEP = define("DOUBLE_STEP", false, true, Material.DOUBLE_STEP);
	public final static MaterialData STONE_DOUBLE_SLAB = define("STONE_DOUBLE_SLAB", true, false, new Double_Slab(SlabType.STONE));
	public final static MaterialData SANDSTONE_DOUBLE_SLAB = define("SANDSTONE_DOUBLE_SLAB", true, false, new Double_Slab(SlabType.SANDSTONE));
	public final static MaterialData WOODSTONE_DOUBLE_SLAB = define("WOODSTONE_DOUBLE_SLAB", true, false, new Double_Slab(SlabType.WOODSTONE));
	public final static MaterialData COBBLESTONE_DOUBLE_SLAB = define("COBBLESTONE_DOUBLE_SLAB", true, false, new Double_Slab(SlabType.COBBLESTONE));
	public final static MaterialData BRICK_DOUBLE_SLAB = define("BRICK_DOUBLE_SLAB", true, false, new Double_Slab(SlabType.BRICK));
	public final static MaterialData SMOOTH_BRICK_DOUBLE_SLAB = define("SMOOTH_BRICK_DOUBLE_SLAB", true, false, new Double_Slab(SlabType.SMOOTH_BRICK));
	public final static MaterialData NETHER_BRICK_DOUBLE_SLAB = define("NETHER_BRICK_DOUBLE_SLAB", true, false, new Double_Slab(SlabType.NETHER_BRICK));
	public final static MaterialData QUARTZ_DOUBLE_SLAB = define("QUARTZ_DOUBLE_SLAB", true, false, new Double_Slab(SlabType.QUARTZ));

	@Deprecated
	public final static MaterialData STEP = define("STEP", false, true, Material.STEP);
	public final static MaterialData STONE_SLAB = define("STONE_SLAB", false, false, new Slab(SlabType.STONE));
	public final static MaterialData SANDSTONE_SLAB = define("SANDSTONE_SLAB", false, false, new Slab(SlabType.SANDSTONE));
	public final static MaterialData WOODSTONE_SLAB = define("WOODSTONE_SLAB", false, false, new Slab(SlabType.WOODSTONE));
	public final static MaterialData COBBLESTONE_SLAB = define("COBBLESTONE_SLAB", false, false, new Slab(SlabType.COBBLESTONE));
	public final static MaterialData BRICK_SLAB = define("BRICK_SLAB", false, false, new Slab(SlabType.BRICK));
	public final static MaterialData SMOOTH_BRICK_SLAB = define("SMOOTH_BRICK_SLAB", false, false, new Slab(SlabType.SMOOTH_BRICK));
	public final static MaterialData NETHER_BRICK_SLAB = define("NETHER_BRICK_SLAB", false, false, new Slab(SlabType.NETHER_BRICK));
	public final static MaterialData QUARTZ_SLAB = define("QUARTZ_SLAB", false, false, new Slab(SlabType.QUARTZ));

	public final static MaterialData BRICK = define("BRICK", true, false, Material.BRICK);
	public final static MaterialData TNT = define("TNT", false, false, Material.TNT);
	public final static MaterialData BOOKSHELF = define("BOOKSHELF", false, false, Material.BOOKSHELF);
	public final static MaterialData MOSSY_COBBLESTONE = define("MOSSY_COBBLESTONE", true, false, Material.MOSSY_COBBLESTONE);
	public final static MaterialData OBSIDIAN = define("OBSIDIAN", true, false, Material.OBSIDIAN);
	public final static MaterialData TORCH = define("TORCH", false, false, Material.TORCH);
	public final static MaterialData FIRE = define("FIRE", false, false, Material.FIRE);
	public final static MaterialData MOB_SPAWNER = define("MOB_SPAWNER", false, false, Material.MOB_SPAWNER);
	public final static MaterialData WOOD_STAIRS = define("WOOD_STAIRS", false, false, new Stairs(Material.WOOD_STAIRS));
	public final static MaterialData CHEST = define("CHEST", false, false, Material.CHEST);
	public final static MaterialData REDSTONE_WIRE = define("REDSTONE_WIRE", false, false, Material.REDSTONE_WIRE);
	public final static MaterialData DIAMOND_ORE = define("DIAMOND_ORE", true, false, Material.DIAMOND_ORE);
	public final static MaterialData DIAMOND_BLOCK = define("DIAMOND_BLOCK", true, false, Material.DIAMOND_BLOCK);
	public final static MaterialData WORKBENCH = define("WORKBENCH", false, false, Material.WORKBENCH);
	public final static MaterialData CROPS = define("CROPS", false, false, Material.CROPS);
	public final static MaterialData SOIL = define("SOIL", false, false, Material.SOIL);
	public final static MaterialData FURNACE = define("FURNACE", false, false, Material.FURNACE);
	public final static MaterialData BURNING_FURNACE = define("BURNING_FURNACE", false, false, Material.BURNING_FURNACE);
	public final static MaterialData SIGN_POST = define("SIGN_POST", false, false, Material.SIGN_POST);
	public final static MaterialData WOODEN_DOOR = define("WOODEN_DOOR", false, false, Material.WOODEN_DOOR);
	public final static MaterialData LADDER = define("LADDER", false, false, Material.LADDER);
	public final static MaterialData RAILS = define("RAILS", false, false, Material.RAILS);
	public final static MaterialData COBBLESTONE_STAIRS = define("COBBLESTONE_STAIRS", false, false,  new Stairs(Material.COBBLESTONE_STAIRS));
	public final static MaterialData WALL_SIGN = define("WALL_SIGN", false, false, Material.WALL_SIGN);
	public final static MaterialData LEVER = define("LEVER", false, false, Material.LEVER);
	public final static MaterialData STONE_PLATE = define("STONE_PLATE", false, false, Material.STONE_PLATE);
	public final static MaterialData IRON_DOOR_BLOCK = define("IRON_DOOR_BLOCK", false, false, Material.IRON_DOOR_BLOCK);
	public final static MaterialData WOOD_PLATE = define("WOOD_PLATE", false, false, Material.WOOD_PLATE);
	public final static MaterialData REDSTONE_ORE = define("REDSTONE_ORE", true, false, Material.REDSTONE_ORE);
	public final static MaterialData GLOWING_REDSTONE_ORE = define("GLOWING_REDSTONE_ORE", true, false, Material.GLOWING_REDSTONE_ORE);
	public final static MaterialData REDSTONE_TORCH_OFF = define("REDSTONE_TORCH_OFF", false, false, Material.REDSTONE_TORCH_OFF);
	public final static MaterialData REDSTONE_TORCH_ON = define("REDSTONE_TORCH_ON", false, false, Material.REDSTONE_TORCH_ON);
	public final static MaterialData STONE_BUTTON = define("STONE_BUTTON", false, false, Material.STONE_BUTTON);
	public final static MaterialData SNOW = define("SNOW", false, false, Material.SNOW);
	public final static MaterialData ICE = define("ICE", false, false, Material.ICE);
	public final static MaterialData SNOW_BLOCK = define("SNOW_BLOCK", false, false, Material.SNOW_BLOCK);
	public final static MaterialData CACTUS = define("CACTUS", false, false, Material.CACTUS);
	public final static MaterialData CLAY = define("CLAY", true, false, Material.CLAY);
	public final static MaterialData SUGAR_CANE_BLOCK = define("SUGAR_CANE_BLOCK", false, false, Material.SUGAR_CANE_BLOCK);
	public final static MaterialData JUKEBOX = define("JUKEBOX", false, false, Material.JUKEBOX);
	public final static MaterialData FENCE = define("FENCE", false, false, Material.FENCE);
	public final static MaterialData PUMPKIN = define("PUMPKIN", false, false, Material.PUMPKIN);
	public final static MaterialData NETHERRACK = define("NETHERRACK", true, false, Material.NETHERRACK);
	public final static MaterialData SOUL_SAND = define("SOUL_SAND", true, false, Material.SOUL_SAND);
	public final static MaterialData GLOWSTONE = define("GLOWSTONE", true, false, Material.GLOWSTONE);
	public final static MaterialData PORTAL = define("PORTAL", false, false, Material.PORTAL);
	public final static MaterialData JACK_O_LANTERN = define("JACK_O_LANTERN", false, false, Material.JACK_O_LANTERN);
	public final static MaterialData CAKE_BLOCK = define("CAKE_BLOCK", false, false, Material.CAKE_BLOCK);
	public final static MaterialData DIODE_BLOCK_OFF = define("DIODE_BLOCK_OFF", false, false, Material.DIODE_BLOCK_OFF);
	public final static MaterialData DIODE_BLOCK_ON = define("DIODE_BLOCK_ON", false, false, Material.DIODE_BLOCK_ON);

	@Deprecated
	public final static MaterialData STAINED_GLASS = define("STAINED_GLASS", false, true, Material.STAINED_GLASS);
	public final static MaterialData WHITE_GLASS = define("WHITE_GLASS", true, false, new Stained_Glass(DyeColor.WHITE));
	public final static MaterialData ORANGE_GLASS = define("ORANGE_GLASS", true, false, new Stained_Glass(DyeColor.ORANGE));
	public final static MaterialData MAGENTA_GLASS = define("MAGENTA_GLASS", true, false, new Stained_Glass(DyeColor.MAGENTA));
	public final static MaterialData LIGHT_BLUE_GLASS = define("LIGHT_BLUE_GLASS", true, false, new Stained_Glass(DyeColor.LIGHT_BLUE));
	public final static MaterialData YELLOW_GLASS = define("YELLOW_GLASS", true, false, new Stained_Glass(DyeColor.YELLOW));
	public final static MaterialData LIME_GLASS = define("LIME_GLASS", true, false, new Stained_Glass(DyeColor.LIME));
	public final static MaterialData PINK_GLASS = define("PINK_GLASS", true, false, new Stained_Glass(DyeColor.PINK));
	public final static MaterialData GRAY_GLASS = define("GRAY_GLASS", true, false, new Stained_Glass(DyeColor.GRAY));
	public final static MaterialData SILVER_GLASS = define("SILVER_GLASS", true, false, new Stained_Glass(DyeColor.SILVER));
	public final static MaterialData CYAN_GLASS = define("CYAN_GLASS", true, false, new Stained_Glass(DyeColor.CYAN));
	public final static MaterialData PURPLE_GLASS = define("PURPLE_GLASS", true, false, new Stained_Glass(DyeColor.PURPLE));
	public final static MaterialData BLUE_GLASS = define("BLUE_GLASS", true, false, new Stained_Glass(DyeColor.BLUE));
	public final static MaterialData BROWN_GLASS = define("BROWN_GLASS", true, false, new Stained_Glass(DyeColor.BROWN));
	public final static MaterialData GREEN_GLASS = define("GREEN_GLASS", true, false, new Stained_Glass(DyeColor.GREEN));
	public final static MaterialData RED_GLASS = define("RED_GLASS", true, false, new Stained_Glass(DyeColor.RED));
	public final static MaterialData BLACK_GLASS = define("BLACK_GLASS", true, false, new Stained_Glass(DyeColor.BLACK));

	public final static MaterialData TRAP_DOOR = define("TRAP_DOOR", false, false, Material.TRAP_DOOR);
	public final static MaterialData MONSTER_EGGS = define("MONSTER_EGGS", false, false, Material.MONSTER_EGGS);

	public final static MaterialData SMOOTH_BRICK = define("SMOOTH_BRICK", true, false, new SmoothBrick(SmoothBrickType.SMOOTH));
	public final static MaterialData CRACKED_BRICK = define("CRACKED_BRICK", true, false, new SmoothBrick(SmoothBrickType.CRACKED));
	public final static MaterialData MOSSY_BRICK = define("MOSSY_BRICK", true, false, new SmoothBrick(SmoothBrickType.MOSSY));
	public final static MaterialData CHISELED_BRICK = define("CHISELED_BRICK", true, false, new SmoothBrick(SmoothBrickType.CHISELED));

	//TODO I need to figure out how to deal with HUGE_MUSHROOM
	public final static MaterialData HUGE_MUSHROOM_1 = define("HUGE_MUSHROOM_1", false, false, Material.HUGE_MUSHROOM_1);
	public final static MaterialData HUGE_MUSHROOM_2 = define("HUGE_MUSHROOM_2", false, false, Material.HUGE_MUSHROOM_2);

	public final static MaterialData IRON_FENCE = define("IRON_FENCE", false, false, Material.IRON_FENCE);
	public final static MaterialData THIN_GLASS = define("THIN_GLASS", true, false, Material.THIN_GLASS);

	public final static MaterialData MELON_BLOCK = define("MELON_BLOCK", false, false, Material.MELON_BLOCK);
	public final static MaterialData PUMPKIN_STEM = define("PUMPKIN_STEM", false, false, Material.PUMPKIN_STEM);
	public final static MaterialData MELON_STEM = define("MELON_STEM", false, false, Material.MELON_STEM);
	public final static MaterialData VINE = define("VINE", false, false, Material.VINE);
	public final static MaterialData FENCE_GATE = define("FENCE_GATE", false, false, Material.FENCE_GATE);
	public final static MaterialData BRICK_STAIRS = define("BRICK_STAIRS", false, false,  new Stairs(Material.BRICK_STAIRS));
	public final static MaterialData SMOOTH_STAIRS = define("SMOOTH_STAIRS", false, false, new Stairs(Material.SMOOTH_STAIRS));
	public final static MaterialData MYCEL = define("MYCEL", true, false, Material.MYCEL);
	public final static MaterialData WATER_LILY = define("WATER_LILY", false, false, Material.WATER_LILY);
	public final static MaterialData NETHER_BRICK = define("NETHER_BRICK", true, false, Material.NETHER_BRICK);
	public final static MaterialData NETHER_FENCE = define("NETHER_FENCE", false, false, Material.NETHER_FENCE);
	public final static MaterialData NETHER_BRICK_STAIRS = define("NETHER_BRICK_STAIRS", false, false, new Stairs(Material.NETHER_BRICK_STAIRS));
	public final static MaterialData NETHER_WARTS = define("NETHER_WARTS", false, false, Material.NETHER_WARTS);
	public final static MaterialData ENCHANTMENT_TABLE = define("ENCHANTMENT_TABLE", false, false, Material.ENCHANTMENT_TABLE);
	public final static MaterialData BREWING_STAND = define("BREWING_STAND", false, false, Material.BREWING_STAND);
	public final static MaterialData CAULDRON = define("CAULDRON", false, false, Material.CAULDRON);
	public final static MaterialData ENDER_PORTAL = define("ENDER_PORTAL", false, false, Material.ENDER_PORTAL);
	public final static MaterialData ENDER_PORTAL_FRAME = define("ENDER_PORTAL_FRAME", false, false, Material.ENDER_PORTAL_FRAME);
	public final static MaterialData ENDER_STONE = define("ENDER_STONE", false, false, Material.ENDER_STONE);
	public final static MaterialData DRAGON_EGG = define("DRAGON_EGG", false, false, Material.DRAGON_EGG);
	public final static MaterialData REDSTONE_LAMP_OFF = define("REDSTONE_LAMP_OFF", false, false, Material.REDSTONE_LAMP_OFF);
	public final static MaterialData REDSTONE_LAMP_ON = define("REDSTONE_LAMP_ON", false, false, Material.REDSTONE_LAMP_ON);

	@Deprecated
	public final static MaterialData WOOD_DOUBLE_STEP = define("WOOD_DOUBLE_STEP", false, true, Material.WOOD_DOUBLE_STEP);
	@Deprecated
	public final static MaterialData REDWOOD_DOUBLE_SLAB = define("REDWOOD_DOUBLE_SLAB", true, true, new Wood_Double_Slab(TreeSpecies.REDWOOD));
	public final static MaterialData OAK_DOUBLE_SLAB = define("OAK_DOUBLE_SLAB", true, false, new Wood_Double_Slab(TreeSpecies.GENERIC));
	public final static MaterialData SPRUCE_DOUBLE_SLAB = define("SPRUCE_DOUBLE_SLAB", true, false, new Wood_Double_Slab(TreeSpecies.REDWOOD));
	public final static MaterialData BIRCH_DOUBLE_SLAB = define("BIRCH_DOUBLE_SLAB", true, false, new Wood_Double_Slab(TreeSpecies.BIRCH));
	public final static MaterialData JUNGLE_DOUBLE_SLAB = define("JUNGLE_DOUBLE_SLAB", true, false, new Wood_Double_Slab(TreeSpecies.JUNGLE));
	public final static MaterialData ACACIA_DOUBLE_SLAB = define("ACACIA_DOUBLE_SLAB", true, false, new Wood_Double_Slab(TreeSpecies.ACACIA));
	public final static MaterialData DARK_OAK_DOUBLE_SLAB = define("DARK_OAK_DOUBLE_SLAB", true, false, new Wood_Double_Slab(TreeSpecies.DARK_OAK));

	@Deprecated
	public final static MaterialData WOOD_STEP = define("WOOD_STEP", false, true, Material.WOOD_STEP);
	@Deprecated
	public final static MaterialData REDWOOD_SLAB = define("REDWOOD_SLAB", false, true, new Wood_Slab(TreeSpecies.REDWOOD));
	public final static MaterialData OAK_SLAB = define("OAK_SLAB", false, false, new Wood_Slab(TreeSpecies.GENERIC));
	public final static MaterialData SPRUCE_SLAB = define("SPRUCE_SLAB", false, false, new Wood_Slab(TreeSpecies.REDWOOD));
	public final static MaterialData BIRCH_SLAB = define("BIRCH_SLAB", false, false, new Wood_Slab(TreeSpecies.BIRCH));
	public final static MaterialData JUNGLE_SLAB = define("JUNGLE_SLAB", false, false, new Wood_Slab(TreeSpecies.JUNGLE));
	public final static MaterialData ACACIA_SLAB = define("ACACIA_SLAB", false, false, new Wood_Slab(TreeSpecies.ACACIA));
	public final static MaterialData DARK_OAK_SLAB = define("DARK_OAK_SLAB", false, false, new Wood_Slab(TreeSpecies.DARK_OAK));

	public final static MaterialData COCOA = define("COCOA", false, false, Material.COCOA);
	public final static MaterialData SANDSTONE_STAIRS = define("SANDSTONE_STAIRS", false, false, new Stairs(Material.SANDSTONE_STAIRS));
	public final static MaterialData EMERALD_ORE = define("EMERALD_ORE", true, false, Material.EMERALD_ORE);

	public final static MaterialData ENDER_CHEST = define("ENDER_CHEST", false, false, Material.ENDER_CHEST);
	public final static MaterialData TRIPWIRE_HOOK = define("TRIPWIRE_HOOK", false, false, Material.TRIPWIRE_HOOK);
	public final static MaterialData TRIPWIRE = define("TRIPWIRE", false, false, Material.TRIPWIRE);
	public final static MaterialData EMERALD_BLOCK = define("EMERALD_BLOCK", true, false, Material.EMERALD_BLOCK);
	public final static MaterialData SPRUCE_WOOD_STAIRS = define("SPRUCE_WOOD_STAIRS", false, false, new Stairs(Material.SPRUCE_WOOD_STAIRS));
	public final static MaterialData BIRCH_WOOD_STAIRS = define("BIRCH_WOOD_STAIRS", false, false, new Stairs(Material.BIRCH_WOOD_STAIRS));
	public final static MaterialData JUNGLE_WOOD_STAIRS = define("JUNGLE_WOOD_STAIRS", false, false, new Stairs(Material.JUNGLE_WOOD_STAIRS));
	public final static MaterialData COMMAND = define("COMMAND", false, false, Material.COMMAND);
	public final static MaterialData BEACON = define("BEACON", false, false, Material.BEACON);
	public final static MaterialData COBBLE_WALL = define("COBBLE_WALL", false, false, Material.COBBLE_WALL);
	public final static MaterialData FLOWER_POT = define("FLOWER_POT", false, false, Material.FLOWER_POT);
	public final static MaterialData CARROT = define("CARROT", false, false, Material.CARROT);
	public final static MaterialData POTATO = define("POTATO", false, false, Material.POTATO);
	public final static MaterialData WOOD_BUTTON = define("WOOD_BUTTON", false, false, Material.WOOD_BUTTON);
	public final static MaterialData SKULL = define("SKULL", false, false, Material.SKULL);
	public final static MaterialData ANVIL = define("ANVIL", false, false, Material.ANVIL);
	public final static MaterialData TRAPPED_CHEST = define("TRAPPED_CHEST", false, false, Material.TRAPPED_CHEST);
	public final static MaterialData GOLD_PLATE = define("GOLD_PLATE", false, false, Material.GOLD_PLATE);
	public final static MaterialData IRON_PLATE = define("IRON_PLATE", false, false, Material.IRON_PLATE);
	public final static MaterialData REDSTONE_COMPARATOR_OFF = define("REDSTONE_COMPARATOR_OFF", false, false, Material.REDSTONE_COMPARATOR_OFF);
	public final static MaterialData REDSTONE_COMPARATOR_ON = define("REDSTONE_COMPARATOR_ON", false, false, Material.REDSTONE_COMPARATOR_ON);
	public final static MaterialData DAYLIGHT_DETECTOR = define("DAYLIGHT_DETECTOR", false, false, Material.DAYLIGHT_DETECTOR);
	public final static MaterialData REDSTONE_BLOCK = define("REDSTONE_BLOCK", true, false, Material.REDSTONE_BLOCK);
	public final static MaterialData QUARTZ_ORE = define("QUARTZ_ORE", true, false, Material.QUARTZ_ORE);
	public final static MaterialData HOPPER = define("HOPPER", false, false, Material.HOPPER);
	public final static MaterialData QUARTZ_BLOCK = define("QUARTZ_BLOCK", true, false, Material.QUARTZ_BLOCK);
	public final static MaterialData QUARTZ_STAIRS = define("QUARTZ_STAIRS", false, false, new Stairs(Material.QUARTZ_STAIRS));
	public final static MaterialData ACTIVATOR_RAIL = define("ACTIVATOR_RAIL", false, false, Material.ACTIVATOR_RAIL);
	public final static MaterialData DROPPER = define("DROPPER", false, false, Material.DROPPER);

	@Deprecated
	public final static MaterialData STAINED_CLAY = define("STAINED_CLAY", false, true, Material.STAINED_CLAY);
	public final static MaterialData WHITE_CLAY = define("WHITE_CLAY", true, false, new Stained_Clay(DyeColor.WHITE));
	public final static MaterialData ORANGE_CLAY = define("ORANGE_CLAY", true, false, new Stained_Clay(DyeColor.ORANGE));
	public final static MaterialData MAGENTA_CLAY = define("MAGENTA_CLAY", true, false, new Stained_Clay(DyeColor.MAGENTA));
	public final static MaterialData LIGHT_BLUE_CLAY = define("LIGHT_BLUE_CLAY", true, false, new Stained_Clay(DyeColor.LIGHT_BLUE));
	public final static MaterialData YELLOW_CLAY = define("YELLOW_CLAY", true, false, new Stained_Clay(DyeColor.YELLOW));
	public final static MaterialData LIME_CLAY = define("LIME_CLAY", true, false, new Stained_Clay(DyeColor.LIME));
	public final static MaterialData PINK_CLAY = define("PINK_CLAY", true, false, new Stained_Clay(DyeColor.PINK));
	public final static MaterialData GRAY_CLAY = define("GRAY_CLAY", true, false, new Stained_Clay(DyeColor.GRAY));
	public final static MaterialData SILVER_CLAY = define("SILVER_CLAY", true, false, new Stained_Clay(DyeColor.SILVER));
	public final static MaterialData CYAN_CLAY = define("CYAN_CLAY", true, false, new Stained_Clay(DyeColor.CYAN));
	public final static MaterialData PURPLE_CLAY = define("PURPLE_CLAY", true, false, new Stained_Clay(DyeColor.PURPLE));
	public final static MaterialData BLUE_CLAY = define("BLUE_CLAY", true, false, new Stained_Clay(DyeColor.BLUE));
	public final static MaterialData BROWN_CLAY = define("BROWN_CLAY", true, false, new Stained_Clay(DyeColor.BROWN));
	public final static MaterialData GREEN_CLAY = define("GREEN_CLAY", true, false, new Stained_Clay(DyeColor.GREEN));
	public final static MaterialData RED_CLAY = define("RED_CLAY", true, false, new Stained_Clay(DyeColor.RED));
	public final static MaterialData BLACK_CLAY = define("BLACK_CLAY", true, false, new Stained_Clay(DyeColor.BLACK));

	@Deprecated
	public final static MaterialData STAINED_GLASS_PANE = define("STAINED_GLASS_PANE", false, true, Material.STAINED_GLASS_PANE);
	public final static MaterialData WHITE_GLASS_PANE = define("WHITE_GLASS_PANE", true, false, new Stained_Glass_Pane(DyeColor.WHITE));
	public final static MaterialData ORANGE_GLASS_PANE = define("ORANGE_GLASS_PANE", true, false, new Stained_Glass_Pane(DyeColor.ORANGE));
	public final static MaterialData MAGENTA_GLASS_PANE = define("MAGENTA_GLASS_PANE", true, false, new Stained_Glass_Pane(DyeColor.MAGENTA));
	public final static MaterialData LIGHT_BLUE_GLASS_PANE = define("LIGHT_BLUE_GLASS_PANE", true, false, new Stained_Glass_Pane(DyeColor.LIGHT_BLUE));
	public final static MaterialData YELLOW_GLASS_PANE = define("YELLOW_GLASS_PANE", true, false, new Stained_Glass_Pane(DyeColor.YELLOW));
	public final static MaterialData LIME_GLASS_PANE = define("LIME_GLASS_PANE", true, false, new Stained_Glass_Pane(DyeColor.LIME));
	public final static MaterialData PINK_GLASS_PANE = define("PINK_GLASS_PANE", true, false, new Stained_Glass_Pane(DyeColor.PINK));
	public final static MaterialData GRAY_GLASS_PANE = define("GRAY_GLASS_PANE", true, false, new Stained_Glass_Pane(DyeColor.GRAY));
	public final static MaterialData SILVER_GLASS_PANE = define("SILVER_GLASS_PANE", true, false, new Stained_Glass_Pane(DyeColor.SILVER));
	public final static MaterialData CYAN_GLASS_PANE = define("CYAN_GLASS_PANE", true, false, new Stained_Glass_Pane(DyeColor.CYAN));
	public final static MaterialData PURPLE_GLASS_PANE = define("PURPLE_GLASS_PANE", true, false, new Stained_Glass_Pane(DyeColor.PURPLE));
	public final static MaterialData BLUE_GLASS_PANE = define("BLUE_GLASS_PANE", true, false, new Stained_Glass_Pane(DyeColor.BLUE));
	public final static MaterialData BROWN_GLASS_PANE = define("BROWN_GLASS_PANE", true, false, new Stained_Glass_Pane(DyeColor.BROWN));
	public final static MaterialData GREEN_GLASS_PANE = define("GREEN_GLASS_PANE", true, false, new Stained_Glass_Pane(DyeColor.GREEN));
	public final static MaterialData RED_GLASS_PANE = define("RED_GLASS_PANE", true, false, new Stained_Glass_Pane(DyeColor.RED));
	public final static MaterialData BLACK_GLASS_PANE = define("BLACK_GLASS_PANE", true, false, new Stained_Glass_Pane(DyeColor.BLACK));

	@Deprecated
	public final static MaterialData LEAVES_2 = define("LEAVES_2", false, true, Material.LEAVES_2);
	@Deprecated
	public final static MaterialData LOG_2 = define("LOG_2", false, true, Material.LOG_2);

	public final static MaterialData ACACIA_STAIRS = define("ACACIA_STAIRS", false, false, new Stairs(Material.ACACIA_STAIRS));
	public final static MaterialData DARK_OAK_STAIRS = define("DARK_OAK_STAIRS", false, false, new Stairs(Material.DARK_OAK_STAIRS));
	public final static MaterialData SLIME_BLOCK = define("SLIME_BLOCK", false, false, Material.SLIME_BLOCK);
	public final static MaterialData BARRIER = define("BARRIER", false, false, Material.BARRIER);
	public final static MaterialData IRON_TRAPDOOR = define("IRON_TRAPDOOR", false, false, Material.IRON_TRAPDOOR);

	public final static MaterialData PRISMARINE = define("PRISMARINE", true, false, new Prismarine(PrismarineType.SMOOTH));
	public final static MaterialData PRISMARINE_BRICK = define("PRISMARINE_BRICK", true, false, new Prismarine(PrismarineType.BRICK));
	public final static MaterialData DARK_PRISMARINE = define("DARK_PRISMARINE", true, false, new Prismarine(PrismarineType.DARK));

	public final static MaterialData SEA_LANTERN = define("SEA_LANTERN", true, false, Material.SEA_LANTERN);
	public final static MaterialData HAY_BLOCK = define("HAY_BLOCK", false, false, Material.HAY_BLOCK);

	@Deprecated
	public final static MaterialData CARPET = define("CARPET", false, true, Material.CARPET);
	public final static MaterialData WHITE_CARPET = define("WHITE_CARPET", false, false, new Carpet(DyeColor.WHITE));
	public final static MaterialData ORANGE_CARPET = define("ORANGE_CARPET", false, false, new Carpet(DyeColor.ORANGE));
	public final static MaterialData MAGENTA_CARPET = define("MAGENTA_CARPET", false, false, new Carpet(DyeColor.MAGENTA));
	public final static MaterialData LIGHT_BLUE_CARPET = define("LIGHT_BLUE_CARPET", false, false, new Carpet(DyeColor.LIGHT_BLUE));
	public final static MaterialData YELLOW_CARPET = define("YELLOW_CARPET", false, false, new Carpet(DyeColor.YELLOW));
	public final static MaterialData LIME_CARPET = define("LIME_CARPET", false, false, new Carpet(DyeColor.LIME));
	public final static MaterialData PINK_CARPET = define("PINK_CARPET", false, false, new Carpet(DyeColor.PINK));
	public final static MaterialData GRAY_CARPET = define("GRAY_CARPET", false, false, new Carpet(DyeColor.GRAY));
	public final static MaterialData SILVER_CARPET = define("SILVER_CARPET", false, false, new Carpet(DyeColor.SILVER));
	public final static MaterialData CYAN_CARPET = define("CYAN_CARPET", false, false, new Carpet(DyeColor.CYAN));
	public final static MaterialData PURPLE_CARPET = define("PURPLE_CARPET", false, false, new Carpet(DyeColor.PURPLE));
	public final static MaterialData BLUE_CARPET = define("BLUE_CARPET", false, false, new Carpet(DyeColor.BLUE));
	public final static MaterialData BROWN_CARPET = define("BROWN_CARPET", false, false, new Carpet(DyeColor.BROWN));
	public final static MaterialData GREEN_CARPET = define("GREEN_CARPET", false, false, new Carpet(DyeColor.GREEN));
	public final static MaterialData RED_CARPET = define("RED_CARPET", false, false, new Carpet(DyeColor.RED));
	public final static MaterialData BLACK_CARPET = define("BLACK_CARPET", false, false, new Stained_Clay(DyeColor.BLACK));

	public final static MaterialData HARD_CLAY = define("HARD_CLAY", true, false, Material.HARD_CLAY);
	public final static MaterialData COAL_BLOCK = define("COAL_BLOCK", true, false, Material.COAL_BLOCK);
	public final static MaterialData PACKED_ICE = define("PACKED_ICE", false, false, Material.PACKED_ICE);
	public final static MaterialData DOUBLE_PLANT = define("DOUBLE_PLANT", false, false, Material.DOUBLE_PLANT);
	public final static MaterialData STANDING_BANNER = define("STANDING_BANNER", false, false, Material.STANDING_BANNER);
	public final static MaterialData WALL_BANNER = define("WALL_BANNER", false, false, Material.WALL_BANNER);
	public final static MaterialData DAYLIGHT_DETECTOR_INVERTED = define("DAYLIGHT_DETECTOR_INVERTED", false, false, Material.DAYLIGHT_DETECTOR_INVERTED);

	public final static MaterialData RED_SANDSTONE = define("RED_SANDSTONE", false, false, Material.RED_SANDSTONE);
	public final static MaterialData CRACKED_RED_SANDSTONE = define("CRACKED_RED_SANDSTONE", true, false, new Red_Sandstone(SandstoneType.CRACKED));
	public final static MaterialData GLYPHED_RED_SANDSTONE = define("GLYPHED_RED_SANDSTONE", true, false, new Red_Sandstone(SandstoneType.GLYPHED));

	public final static MaterialData RED_SANDSTONE_STAIRS = define("RED_SANDSTONE_STAIRS", false, false, new Stairs(Material.RED_SANDSTONE_STAIRS));

	@Deprecated
	public final static MaterialData DOUBLE_STONE_SLAB2 = define("DOUBLE_STONE_SLAB2", false, true, Material.DOUBLE_STONE_SLAB2);
	@Deprecated
	public final static MaterialData STONE_SLAB2 = define("STONE_SLAB2", false, true, Material.STONE_SLAB2);

	public final static MaterialData SPRUCE_FENCE_GATE = define("SPRUCE_FENCE_GATE", false, false, Material.SPRUCE_FENCE_GATE);
	public final static MaterialData BIRCH_FENCE_GATE = define("BIRCH_FENCE_GATE", false, false, Material.BIRCH_FENCE_GATE);
	public final static MaterialData JUNGLE_FENCE_GATE = define("JUNGLE_FENCE_GATE", false, false, Material.JUNGLE_FENCE_GATE);
	public final static MaterialData DARK_OAK_FENCE_GATE = define("DARK_OAK_FENCE_GATE", false, false, Material.DARK_OAK_FENCE_GATE);
	public final static MaterialData ACACIA_FENCE_GATE = define("ACACIA_FENCE_GATE", false, false, Material.ACACIA_FENCE_GATE);
	public final static MaterialData SPRUCE_FENCE = define("SPRUCE_FENCE", false, false, Material.SPRUCE_FENCE);
	public final static MaterialData BIRCH_FENCE = define("BIRCH_FENCE", false, false, Material.BIRCH_FENCE);
	public final static MaterialData JUNGLE_FENCE = define("JUNGLE_FENCE", false, false, Material.JUNGLE_FENCE);
	public final static MaterialData DARK_OAK_FENCE = define("DARK_OAK_FENCE", false, false, Material.DARK_OAK_FENCE);
	public final static MaterialData ACACIA_FENCE = define("ACACIA_FENCE", false, false, Material.ACACIA_FENCE);
	public final static MaterialData SPRUCE_DOOR = define("SPRUCE_DOOR", false, false, Material.SPRUCE_DOOR);
	public final static MaterialData BIRCH_DOOR = define("BIRCH_DOOR", false, false, Material.BIRCH_DOOR);
	public final static MaterialData JUNGLE_DOOR = define("JUNGLE_DOOR", false, false, Material.JUNGLE_DOOR);
	public final static MaterialData ACACIA_DOOR = define("ACACIA_DOOR", false, false, Material.ACACIA_DOOR);
	public final static MaterialData DARK_OAK_DOOR = define("DARK_OAK_DOOR", false, false, Material.DARK_OAK_DOOR);
	public final static MaterialData END_ROD = define("END_ROD", false, false, Material.END_ROD);
	public final static MaterialData CHORUS_PLANT = define("CHORUS_PLANT", false, false, Material.CHORUS_PLANT);
	public final static MaterialData CHORUS_FLOWER = define("CHORUS_FLOWER", false, false, Material.CHORUS_FLOWER);
	public final static MaterialData PURPUR_BLOCK = define("PURPUR_BLOCK", true, false, Material.PURPUR_BLOCK);
	public final static MaterialData PURPUR_PILLAR = define("PURPUR_PILLAR", true, false, Material.PURPUR_PILLAR);
	public final static MaterialData PURPUR_STAIRS = define("PURPUR_STAIRS", false, false, new Stairs(Material.PURPUR_STAIRS));
	public final static MaterialData PURPUR_DOUBLE_SLAB = define("PURPUR_DOUBLE_SLAB", true, false, Material.PURPUR_DOUBLE_SLAB);
	public final static MaterialData PURPUR_SLAB = define("PURPUR_SLAB", false, false, Material.PURPUR_SLAB);

	@Deprecated
	public final static MaterialData END_BRICKS = define("END_BRICKS", false, true, Material.END_BRICKS);
	public final static MaterialData END_BRICK = define("END_BRICK", true, false, Material.END_BRICKS);

	public final static MaterialData BEETROOT_BLOCK = define("BEETROOT_BLOCK", false, false, Material.BEETROOT_BLOCK);
	public final static MaterialData GRASS_PATH = define("GRASS_PATH", false, false, Material.GRASS_PATH);
	public final static MaterialData END_GATEWAY = define("END_GATEWAY", false, false, Material.END_GATEWAY);
	public final static MaterialData COMMAND_REPEATING = define("COMMAND_REPEATING", false, false, Material.COMMAND_REPEATING);
	public final static MaterialData COMMAND_CHAIN = define("COMMAND_CHAIN", false, false, Material.COMMAND_CHAIN);
	public final static MaterialData FROSTED_ICE = define("FROSTED_ICE", false, false, Material.FROSTED_ICE);
	public final static MaterialData MAGMA = define("MAGMA", true, false, Material.MAGMA);
	public final static MaterialData NETHER_WART_BLOCK = define("NETHER_WART_BLOCK", false, false, Material.NETHER_WART_BLOCK);
	public final static MaterialData RED_NETHER_BRICK = define("RED_NETHER_BRICK", false, false, Material.RED_NETHER_BRICK);
	public final static MaterialData BONE_BLOCK = define("BONE_BLOCK", true, false, Material.BONE_BLOCK);
	public final static MaterialData STRUCTURE_VOID = define("STRUCTURE_VOID", false, false, Material.STRUCTURE_VOID);
	public final static MaterialData OBSERVER = define("OBSERVER", false, false, Material.OBSERVER);
	public final static MaterialData WHITE_SHULKER_BOX = define("WHITE_SHULKER_BOX", false, false, Material.WHITE_SHULKER_BOX);
	public final static MaterialData ORANGE_SHULKER_BOX = define("ORANGE_SHULKER_BOX", false, false, Material.ORANGE_SHULKER_BOX);
	public final static MaterialData MAGENTA_SHULKER_BOX = define("MAGENTA_SHULKER_BOX", false, false, Material.MAGENTA_SHULKER_BOX);
	public final static MaterialData LIGHT_BLUE_SHULKER_BOX = define("LIGHT_BLUE_SHULKER_BOX", false, false, Material.LIGHT_BLUE_SHULKER_BOX);
	public final static MaterialData YELLOW_SHULKER_BOX = define("YELLOW_SHULKER_BOX", false, false, Material.YELLOW_SHULKER_BOX);
	public final static MaterialData LIME_SHULKER_BOX = define("LIME_SHULKER_BOX", false, false, Material.LIME_SHULKER_BOX);
	public final static MaterialData PINK_SHULKER_BOX = define("PINK_SHULKER_BOX", false, false, Material.PINK_SHULKER_BOX);
	public final static MaterialData GRAY_SHULKER_BOX = define("GRAY_SHULKER_BOX", false, false, Material.GRAY_SHULKER_BOX);
	public final static MaterialData SILVER_SHULKER_BOX = define("SILVER_SHULKER_BOX", false, false, Material.SILVER_SHULKER_BOX);
	public final static MaterialData CYAN_SHULKER_BOX = define("CYAN_SHULKER_BOX", false, false, Material.CYAN_SHULKER_BOX);
	public final static MaterialData PURPLE_SHULKER_BOX = define("PURPLE_SHULKER_BOX", false, false, Material.PURPLE_SHULKER_BOX);
	public final static MaterialData BLUE_SHULKER_BOX = define("BLUE_SHULKER_BOX", false, false, Material.BLUE_SHULKER_BOX);
	public final static MaterialData BROWN_SHULKER_BOX = define("BROWN_SHULKER_BOX", false, false, Material.BROWN_SHULKER_BOX);
	public final static MaterialData GREEN_SHULKER_BOX = define("GREEN_SHULKER_BOX", false, false, Material.GREEN_SHULKER_BOX);
	public final static MaterialData RED_SHULKER_BOX = define("RED_SHULKER_BOX", false, false, Material.RED_SHULKER_BOX);
	public final static MaterialData BLACK_SHULKER_BOX = define("BLACK_SHULKER_BOX", false, false, Material.BLACK_SHULKER_BOX);

	public final static MaterialData WHITE_GLAZED_TERRACOTTA = define("WHITE_GLAZED_TERRACOTTA", true, false, Material.WHITE_GLAZED_TERRACOTTA);
	public final static MaterialData ORANGE_GLAZED_TERRACOTTA = define("ORANGE_GLAZED_TERRACOTTA", true, false, Material.ORANGE_GLAZED_TERRACOTTA);
	public final static MaterialData MAGENTA_GLAZED_TERRACOTTA = define("MAGENTA_GLAZED_TERRACOTTA", true, false, Material.MAGENTA_GLAZED_TERRACOTTA);
	public final static MaterialData LIGHT_BLUE_GLAZED_TERRACOTTA = define("LIGHT_BLUE_GLAZED_TERRACOTTA", true, false, Material.LIGHT_BLUE_GLAZED_TERRACOTTA);
	public final static MaterialData YELLOW_GLAZED_TERRACOTTA = define("YELLOW_GLAZED_TERRACOTTA", true, false, Material.YELLOW_GLAZED_TERRACOTTA);
	public final static MaterialData LIME_GLAZED_TERRACOTTA = define("LIME_GLAZED_TERRACOTTA", true, false, Material.LIME_GLAZED_TERRACOTTA);
	public final static MaterialData PINK_GLAZED_TERRACOTTA = define("PINK_GLAZED_TERRACOTTA", true, false, Material.PINK_GLAZED_TERRACOTTA);
	public final static MaterialData GRAY_GLAZED_TERRACOTTA = define("GRAY_GLAZED_TERRACOTTA", true, false, Material.GRAY_GLAZED_TERRACOTTA);
	public final static MaterialData SILVER_GLAZED_TERRACOTTA = define("SILVER_GLAZED_TERRACOTTA", true, false, Material.SILVER_GLAZED_TERRACOTTA);
	public final static MaterialData CYAN_GLAZED_TERRACOTTA = define("CYAN_GLAZED_TERRACOTTA", true, false, Material.CYAN_GLAZED_TERRACOTTA);
	public final static MaterialData PURPLE_GLAZED_TERRACOTTA = define("PURPLE_GLAZED_TERRACOTTA", true, false, Material.PURPLE_GLAZED_TERRACOTTA);
	public final static MaterialData BLUE_GLAZED_TERRACOTTA = define("BLUE_GLAZED_TERRACOTTA", true, false, Material.BLUE_GLAZED_TERRACOTTA);
	public final static MaterialData BROWN_GLAZED_TERRACOTTA = define("BROWN_GLAZED_TERRACOTTA", true, false, Material.BROWN_GLAZED_TERRACOTTA);
	public final static MaterialData GREEN_GLAZED_TERRACOTTA = define("GREEN_GLAZED_TERRACOTTA", true, false, Material.GREEN_GLAZED_TERRACOTTA);
	public final static MaterialData RED_GLAZED_TERRACOTTA = define("RED_GLAZED_TERRACOTTA", true, false, Material.RED_GLAZED_TERRACOTTA);
	public final static MaterialData BLACK_GLAZED_TERRACOTTA = define("BLACK_GLAZED_TERRACOTTA", true, false, Material.BLACK_GLAZED_TERRACOTTA);

	@Deprecated
	public final static MaterialData CONCRETE = define("CONCRETE", false, true, Material.CONCRETE);
	public final static MaterialData WHITE_CONCRETE = define("WHITE_CONCRETE", true, false, new Concrete(DyeColor.WHITE));
	public final static MaterialData ORANGE_CONCRETE = define("ORANGE_CONCRETE", true, false, new Concrete(DyeColor.ORANGE));
	public final static MaterialData MAGENTA_CONCRETE = define("MAGENTA_CONCRETE", true, false, new Concrete(DyeColor.MAGENTA));
	public final static MaterialData LIGHT_BLUE_CONCRETE = define("LIGHT_BLUE_CONCRETE", true, false, new Concrete(DyeColor.LIGHT_BLUE));
	public final static MaterialData YELLOW_CONCRETE = define("YELLOW_CONCRETE", true, false, new Concrete(DyeColor.YELLOW));
	public final static MaterialData LIME_CONCRETE = define("LIME_CONCRETE", true, false, new Concrete(DyeColor.LIME));
	public final static MaterialData PINK_CONCRETE = define("PINK_CONCRETE", true, false, new Concrete(DyeColor.PINK));
	public final static MaterialData GRAY_CONCRETE = define("GRAY_CONCRETE", true, false, new Concrete(DyeColor.GRAY));
	public final static MaterialData SILVER_CONCRETE = define("SILVER_CONCRETE", true, false, new Concrete(DyeColor.SILVER));
	public final static MaterialData CYAN_CONCRETE = define("CYAN_CONCRETE", true, false, new Concrete(DyeColor.CYAN));
	public final static MaterialData PURPLE_CONCRETE = define("PURPLE_CONCRETE", true, false, new Concrete(DyeColor.PURPLE));
	public final static MaterialData BLUE_CONCRETE = define("BLUE_CONCRETE", true, false, new Concrete(DyeColor.BLUE));
	public final static MaterialData BROWN_CONCRETE = define("BROWN_CONCRETE", true, false, new Concrete(DyeColor.BROWN));
	public final static MaterialData GREEN_CONCRETE = define("GREEN_CONCRETE", true, false, new Concrete(DyeColor.GREEN));
	public final static MaterialData RED_CONCRETE = define("RED_CONCRETE", true, false, new Concrete(DyeColor.RED));
	public final static MaterialData BLACK_CONCRETE = define("BLACK_CONCRETE", true, false, new Concrete(DyeColor.BLACK));

	@Deprecated
	public final static MaterialData CONCRETE_POWDER = define("CONCRETE_POWDER", false, true, Material.CONCRETE_POWDER);
	public final static MaterialData WHITE_CONCRETE_POWDER = define("WHITE_CONCRETE_POWDER", true, false, new Concrete_Powder(DyeColor.WHITE));
	public final static MaterialData ORANGE_CONCRETE_POWDER = define("ORANGE_CONCRETE_POWDER", true, false, new Concrete_Powder(DyeColor.ORANGE));
	public final static MaterialData MAGENTA_CONCRETE_POWDER = define("MAGENTA_CONCRETE_POWDER", true, false, new Concrete_Powder(DyeColor.MAGENTA));
	public final static MaterialData LIGHT_BLUE_CONCRETE_POWDER = define("LIGHT_BLUE_CONCRETE_POWDER", true, false, new Concrete_Powder(DyeColor.LIGHT_BLUE));
	public final static MaterialData YELLOW_CONCRETE_POWDER = define("YELLOW_CONCRETE_POWDER", true, false, new Concrete_Powder(DyeColor.YELLOW));
	public final static MaterialData LIME_CONCRETE_POWDER = define("LIME_CONCRETE_POWDER", true, false, new Concrete_Powder(DyeColor.LIME));
	public final static MaterialData PINK_CONCRETE_POWDER = define("PINK_CONCRETE_POWDER", true, false, new Concrete_Powder(DyeColor.PINK));
	public final static MaterialData GRAY_CONCRETE_POWDER = define("GRAY_CONCRETE_POWDER", true, false, new Concrete_Powder(DyeColor.GRAY));
	public final static MaterialData SILVER_CONCRETE_POWDER = define("SILVER_CONCRETE_POWDER", true, false, new Concrete_Powder(DyeColor.SILVER));
	public final static MaterialData CYAN_CONCRETE_POWDER = define("CYAN_CONCRETE_POWDER", true, false, new Concrete_Powder(DyeColor.CYAN));
	public final static MaterialData PURPLE_CONCRETE_POWDER = define("PURPLE_CONCRETE_POWDER", true, false, new Concrete_Powder(DyeColor.PURPLE));
	public final static MaterialData BLUE_CONCRETE_POWDER = define("BLUE_CONCRETE_POWDER", true, false, new Concrete_Powder(DyeColor.BLUE));
	public final static MaterialData BROWN_CONCRETE_POWDER = define("BROWN_CONCRETE_POWDER", true, false, new Concrete_Powder(DyeColor.BROWN));
	public final static MaterialData GREEN_CONCRETE_POWDER = define("GREEN_CONCRETE_POWDER", true, false, new Concrete_Powder(DyeColor.GREEN));
	public final static MaterialData RED_CONCRETE_POWDER = define("RED_CONCRETE_POWDER", true, false, new Concrete_Powder(DyeColor.RED));
	public final static MaterialData BLACK_CONCRETE_POWDER = define("BLACK_CONCRETE_POWDER", true, false, new Concrete_Powder(DyeColor.BLACK));

	public final static MaterialData STRUCTURE_BLOCK = define("STRUCTURE_BLOCK", false, false, Material.STRUCTURE_BLOCK);

	// List of items
	public final static MaterialData IRON_SPADE = define("IRON_SPADE", false, false, Material.IRON_SPADE);
	public final static MaterialData IRON_PICKAXE = define("IRON_PICKAXE", false, false, Material.IRON_PICKAXE);
	public final static MaterialData IRON_AXE = define("IRON_AXE", false, false, Material.IRON_AXE);
	public final static MaterialData FLINT_AND_STEEL = define("FLINT_AND_STEEL", false, false, Material.FLINT_AND_STEEL);
	public final static MaterialData APPLE = define("APPLE", false, false, Material.APPLE);
	public final static MaterialData BOW = define("BOW", false, false, Material.BOW);
	public final static MaterialData ARROW = define("ARROW", false, false, Material.ARROW);
	public final static MaterialData COAL = define("COAL", false, false, Material.COAL);
	public final static MaterialData DIAMOND = define("DIAMOND", false, false, Material.DIAMOND);
	public final static MaterialData IRON_INGOT = define("IRON_INGOT", false, false, Material.IRON_INGOT);
	public final static MaterialData GOLD_INGOT = define("GOLD_INGOT", false, false, Material.GOLD_INGOT);
	public final static MaterialData IRON_SWORD = define("IRON_SWORD", false, false, Material.IRON_SWORD);
	public final static MaterialData WOOD_SWORD = define("WOOD_SWORD", false, false, Material.WOOD_SWORD);
	public final static MaterialData WOOD_SPADE = define("WOOD_SPADE", false, false, Material.WOOD_SPADE);
	public final static MaterialData WOOD_PICKAXE = define("WOOD_PICKAXE", false, false, Material.WOOD_PICKAXE);
	public final static MaterialData WOOD_AXE = define("WOOD_AXE", false, false, Material.WOOD_AXE);
	public final static MaterialData STONE_SWORD = define("STONE_SWORD", false, false, Material.STONE_SWORD);
	public final static MaterialData STONE_SPADE = define("STONE_SPADE", false, false, Material.STONE_SPADE);
	public final static MaterialData STONE_PICKAXE = define("STONE_PICKAXE", false, false, Material.STONE_PICKAXE);
	public final static MaterialData STONE_AXE = define("STONE_AXE", false, false, Material.STONE_AXE);
	public final static MaterialData DIAMOND_SWORD = define("DIAMOND_SWORD", false, false, Material.DIAMOND_SWORD);
	public final static MaterialData DIAMOND_SPADE = define("DIAMOND_SPADE", false, false, Material.DIAMOND_SPADE);
	public final static MaterialData DIAMOND_PICKAXE = define("DIAMOND_PICKAXE", false, false, Material.DIAMOND_PICKAXE);
	public final static MaterialData DIAMOND_AXE = define("DIAMOND_AXE", false, false, Material.DIAMOND_AXE);
	public final static MaterialData STICK = define("STICK", false, false, Material.STICK);
	public final static MaterialData BOWL = define("BOWL", false, false, Material.BOWL);
	public final static MaterialData MUSHROOM_SOUP = define("MUSHROOM_SOUP", false, false, Material.MUSHROOM_SOUP);
	public final static MaterialData GOLD_SWORD = define("GOLD_SWORD", false, false, Material.GOLD_SWORD);
	public final static MaterialData GOLD_SPADE = define("GOLD_SPADE", false, false, Material.GOLD_SPADE);
	public final static MaterialData GOLD_PICKAXE = define("GOLD_PICKAXE", false, false, Material.GOLD_PICKAXE);
	public final static MaterialData GOLD_AXE = define("GOLD_AXE", false, false, Material.GOLD_AXE);
	public final static MaterialData STRING = define("STRING", false, false, Material.STRING);
	public final static MaterialData FEATHER = define("FEATHER", false, false, Material.FEATHER);
	public final static MaterialData SULPHUR = define("SULPHUR", false, false, Material.SULPHUR);
	public final static MaterialData WOOD_HOE = define("WOOD_HOE", false, false, Material.WOOD_HOE);
	public final static MaterialData STONE_HOE = define("STONE_HOE", false, false, Material.STONE_HOE);
	public final static MaterialData IRON_HOE = define("IRON_HOE", false, false, Material.IRON_HOE);
	public final static MaterialData DIAMOND_HOE = define("DIAMOND_HOE", false, false, Material.DIAMOND_HOE);
	public final static MaterialData GOLD_HOE = define("GOLD_HOE", false, false, Material.GOLD_HOE);
	public final static MaterialData SEEDS = define("SEEDS", false, false, Material.SEEDS);
	public final static MaterialData WHEAT = define("WHEAT", false, false, Material.WHEAT);
	public final static MaterialData BREAD = define("BREAD", false, false, Material.BREAD);
	public final static MaterialData LEATHER_HELMET = define("LEATHER_HELMET", false, false, Material.LEATHER_HELMET);
	public final static MaterialData LEATHER_CHESTPLATE = define("LEATHER_CHESTPLATE", false, false, Material.LEATHER_CHESTPLATE);
	public final static MaterialData LEATHER_LEGGINGS = define("LEATHER_LEGGINGS", false, false, Material.LEATHER_LEGGINGS);
	public final static MaterialData LEATHER_BOOTS = define("LEATHER_BOOTS", false, false, Material.LEATHER_BOOTS);
	public final static MaterialData CHAINMAIL_HELMET = define("CHAINMAIL_HELMET", false, false, Material.CHAINMAIL_HELMET);
	public final static MaterialData CHAINMAIL_CHESTPLATE = define("CHAINMAIL_CHESTPLATE", false, false, Material.CHAINMAIL_CHESTPLATE);
	public final static MaterialData CHAINMAIL_LEGGINGS = define("CHAINMAIL_LEGGINGS", false, false, Material.CHAINMAIL_LEGGINGS);
	public final static MaterialData CHAINMAIL_BOOTS = define("CHAINMAIL_BOOTS", false, false, Material.CHAINMAIL_BOOTS);
	public final static MaterialData IRON_HELMET = define("IRON_HELMET", false, false, Material.IRON_HELMET);
	public final static MaterialData IRON_CHESTPLATE = define("IRON_CHESTPLATE", false, false, Material.IRON_CHESTPLATE);
	public final static MaterialData IRON_LEGGINGS = define("IRON_LEGGINGS", false, false, Material.IRON_LEGGINGS);
	public final static MaterialData IRON_BOOTS = define("IRON_BOOTS", false, false, Material.IRON_BOOTS);
	public final static MaterialData DIAMOND_HELMET = define("DIAMOND_HELMET", false, false, Material.DIAMOND_HELMET);
	public final static MaterialData DIAMOND_CHESTPLATE = define("DIAMOND_CHESTPLATE", false, false, Material.DIAMOND_CHESTPLATE);
	public final static MaterialData DIAMOND_LEGGINGS = define("DIAMOND_LEGGINGS", false, false, Material.DIAMOND_LEGGINGS);
	public final static MaterialData DIAMOND_BOOTS = define("DIAMOND_BOOTS", false, false, Material.DIAMOND_BOOTS);
	public final static MaterialData GOLD_HELMET = define("GOLD_HELMET", false, false, Material.GOLD_HELMET);
	public final static MaterialData GOLD_CHESTPLATE = define("GOLD_CHESTPLATE", false, false, Material.GOLD_CHESTPLATE);
	public final static MaterialData GOLD_LEGGINGS = define("GOLD_LEGGINGS", false, false, Material.GOLD_LEGGINGS);
	public final static MaterialData GOLD_BOOTS = define("GOLD_BOOTS", false, false, Material.GOLD_BOOTS);
	public final static MaterialData FLINT = define("FLINT", false, false, Material.FLINT);
	public final static MaterialData PORK = define("PORK", false, false, Material.PORK);
	public final static MaterialData GRILLED_PORK = define("GRILLED_PORK", false, false, Material.GRILLED_PORK);
	public final static MaterialData PAINTING = define("PAINTING", false, false, Material.PAINTING);
	public final static MaterialData GOLDEN_APPLE = define("GOLDEN_APPLE", false, false, Material.GOLDEN_APPLE);
	public final static MaterialData SIGN = define("SIGN", false, false, Material.SIGN);
	public final static MaterialData WOOD_DOOR = define("WOOD_DOOR", false, false, Material.WOOD_DOOR);
	public final static MaterialData BUCKET = define("BUCKET", false, false, Material.BUCKET);
	public final static MaterialData WATER_BUCKET = define("WATER_BUCKET", false, false, Material.WATER_BUCKET);
	public final static MaterialData LAVA_BUCKET = define("LAVA_BUCKET", false, false, Material.LAVA_BUCKET);
	public final static MaterialData MINECART = define("MINECART", false, false, Material.MINECART);
	public final static MaterialData SADDLE = define("SADDLE", false, false, Material.SADDLE);
	public final static MaterialData IRON_DOOR = define("IRON_DOOR", false, false, Material.IRON_DOOR);
	public final static MaterialData REDSTONE = define("REDSTONE", false, false, Material.REDSTONE);
	public final static MaterialData SNOW_BALL = define("SNOW_BALL", false, false, Material.SNOW_BALL);
	public final static MaterialData BOAT = define("BOAT", false, false, Material.BOAT);
	public final static MaterialData LEATHER = define("LEATHER", false, false, Material.LEATHER);
	public final static MaterialData MILK_BUCKET = define("MILK_BUCKET", false, false, Material.MILK_BUCKET);
	public final static MaterialData CLAY_BRICK = define("CLAY_BRICK", false, false, Material.CLAY_BRICK);
	public final static MaterialData CLAY_BALL = define("CLAY_BALL", false, false, Material.CLAY_BALL);
	public final static MaterialData SUGAR_CANE = define("SUGAR_CANE", false, false, Material.SUGAR_CANE);
	public final static MaterialData PAPER = define("PAPER", false, false, Material.PAPER);
	public final static MaterialData BOOK = define("BOOK", false, false, Material.BOOK);
	public final static MaterialData SLIME_BALL = define("SLIME_BALL", false, false, Material.SLIME_BALL);
	public final static MaterialData STORAGE_MINECART = define("STORAGE_MINECART", false, false, Material.STORAGE_MINECART);
	public final static MaterialData POWERED_MINECART = define("POWERED_MINECART", false, false, Material.POWERED_MINECART);
	public final static MaterialData EGG = define("EGG", false, false, Material.EGG);
	public final static MaterialData COMPASS = define("COMPASS", false, false, Material.COMPASS);
	public final static MaterialData FISHING_ROD = define("FISHING_ROD", false, false, Material.FISHING_ROD);
	public final static MaterialData WATCH = define("WATCH", false, false, Material.WATCH);
	public final static MaterialData GLOWSTONE_DUST = define("GLOWSTONE_DUST", false, false, Material.GLOWSTONE_DUST);
	public final static MaterialData RAW_FISH = define("RAW_FISH", false, false, Material.RAW_FISH);
	public final static MaterialData COOKED_FISH = define("COOKED_FISH", false, false, Material.COOKED_FISH);
	public final static MaterialData INK_SACK = define("INK_SACK", false, false, Material.INK_SACK);
	public final static MaterialData BONE = define("BONE", false, false, Material.BONE);
	public final static MaterialData SUGAR = define("SUGAR", false, false, Material.SUGAR);
	public final static MaterialData CAKE = define("CAKE", false, false, Material.CAKE);
	public final static MaterialData BED = define("BED", false, false, Material.BED);
	public final static MaterialData DIODE = define("DIODE", false, false, Material.DIODE);
	public final static MaterialData COOKIE = define("COOKIE", false, false, Material.COOKIE);
	public final static MaterialData MAP = define("MAP", false, false, Material.MAP);
	public final static MaterialData SHEARS = define("SHEARS", false, false, Material.SHEARS);
	public final static MaterialData MELON = define("MELON", false, false, Material.MELON);
	public final static MaterialData PUMPKIN_SEEDS = define("PUMPKIN_SEEDS", false, false, Material.PUMPKIN_SEEDS);
	public final static MaterialData MELON_SEEDS = define("MELON_SEEDS", false, false, Material.MELON_SEEDS);
	public final static MaterialData RAW_BEEF = define("RAW_BEEF", false, false, Material.RAW_BEEF);
	public final static MaterialData COOKED_BEEF = define("COOKED_BEEF", false, false, Material.COOKED_BEEF);
	public final static MaterialData RAW_CHICKEN = define("RAW_CHICKEN", false, false, Material.RAW_CHICKEN);
	public final static MaterialData COOKED_CHICKEN = define("COOKED_CHICKEN", false, false, Material.COOKED_CHICKEN);
	public final static MaterialData ROTTEN_FLESH = define("ROTTEN_FLESH", false, false, Material.ROTTEN_FLESH);
	public final static MaterialData ENDER_PEARL = define("ENDER_PEARL", false, false, Material.ENDER_PEARL);
	public final static MaterialData BLAZE_ROD = define("BLAZE_ROD", false, false, Material.BLAZE_ROD);
	public final static MaterialData GHAST_TEAR = define("GHAST_TEAR", false, false, Material.GHAST_TEAR);
	public final static MaterialData GOLD_NUGGET = define("GOLD_NUGGET", false, false, Material.GOLD_NUGGET);
	public final static MaterialData NETHER_STALK = define("NETHER_STALK", false, false, Material.NETHER_STALK);
	public final static MaterialData POTION = define("POTION", false, false, Material.POTION);
	public final static MaterialData GLASS_BOTTLE = define("GLASS_BOTTLE", false, false, Material.GLASS_BOTTLE);
	public final static MaterialData SPIDER_EYE = define("SPIDER_EYE", false, false, Material.SPIDER_EYE);
	public final static MaterialData FERMENTED_SPIDER_EYE = define("FERMENTED_SPIDER_EYE", false, false, Material.FERMENTED_SPIDER_EYE);
	public final static MaterialData BLAZE_POWDER = define("BLAZE_POWDER", false, false, Material.BLAZE_POWDER);
	public final static MaterialData MAGMA_CREAM = define("MAGMA_CREAM", false, false, Material.MAGMA_CREAM);
	public final static MaterialData BREWING_STAND_ITEM = define("BREWING_STAND_ITEM", false, false, Material.BREWING_STAND_ITEM);
	public final static MaterialData CAULDRON_ITEM = define("CAULDRON_ITEM", false, false, Material.CAULDRON_ITEM);
	public final static MaterialData EYE_OF_ENDER = define("EYE_OF_ENDER", false, false, Material.EYE_OF_ENDER);
	public final static MaterialData SPECKLED_MELON = define("SPECKLED_MELON", false, false, Material.SPECKLED_MELON);
	public final static MaterialData MONSTER_EGG = define("MONSTER_EGG", false, false, Material.MONSTER_EGG);
	public final static MaterialData EXP_BOTTLE = define("EXP_BOTTLE", false, false, Material.EXP_BOTTLE);
	public final static MaterialData FIREBALL = define("FIREBALL", false, false, Material.FIREBALL);
	public final static MaterialData BOOK_AND_QUILL = define("BOOK_AND_QUILL", false, false, Material.BOOK_AND_QUILL);
	public final static MaterialData WRITTEN_BOOK = define("WRITTEN_BOOK", false, false, Material.WRITTEN_BOOK);
	public final static MaterialData EMERALD = define("EMERALD", false, false, Material.EMERALD);
	public final static MaterialData ITEM_FRAME = define("ITEM_FRAME", false, false, Material.ITEM_FRAME);
	public final static MaterialData FLOWER_POT_ITEM = define("FLOWER_POT_ITEM", false, false, Material.FLOWER_POT_ITEM);
	public final static MaterialData CARROT_ITEM = define("CARROT_ITEM", false, false, Material.CARROT_ITEM);
	public final static MaterialData POTATO_ITEM = define("POTATO_ITEM", false, false, Material.POTATO_ITEM);
	public final static MaterialData BAKED_POTATO = define("BAKED_POTATO", false, false, Material.BAKED_POTATO);
	public final static MaterialData POISONOUS_POTATO = define("POISONOUS_POTATO", false, false, Material.POISONOUS_POTATO);
	public final static MaterialData EMPTY_MAP = define("EMPTY_MAP", false, false, Material.EMPTY_MAP);
	public final static MaterialData GOLDEN_CARROT = define("GOLDEN_CARROT", false, false, Material.GOLDEN_CARROT);
	public final static MaterialData SKULL_ITEM = define("SKULL_ITEM", false, false, Material.SKULL_ITEM);
	public final static MaterialData CARROT_STICK = define("CARROT_STICK", false, false, Material.CARROT_STICK);
	public final static MaterialData NETHER_STAR = define("NETHER_STAR", false, false, Material.NETHER_STAR);
	public final static MaterialData PUMPKIN_PIE = define("PUMPKIN_PIE", false, false, Material.PUMPKIN_PIE);
	public final static MaterialData FIREWORK = define("FIREWORK", false, false, Material.FIREWORK);
	public final static MaterialData FIREWORK_CHARGE = define("FIREWORK_CHARGE", false, false, Material.FIREWORK_CHARGE);
	public final static MaterialData ENCHANTED_BOOK = define("ENCHANTED_BOOK", false, false, Material.ENCHANTED_BOOK);
	public final static MaterialData REDSTONE_COMPARATOR = define("REDSTONE_COMPARATOR", false, false, Material.REDSTONE_COMPARATOR);
	public final static MaterialData NETHER_BRICK_ITEM = define("NETHER_BRICK_ITEM", false, false, Material.NETHER_BRICK_ITEM);
	public final static MaterialData QUARTZ = define("QUARTZ", false, false, Material.QUARTZ);
	public final static MaterialData EXPLOSIVE_MINECART = define("EXPLOSIVE_MINECART", false, false, Material.EXPLOSIVE_MINECART);
	public final static MaterialData HOPPER_MINECART = define("HOPPER_MINECART", false, false, Material.HOPPER_MINECART);
	public final static MaterialData PRISMARINE_SHARD = define("PRISMARINE_SHARD", false, false, Material.PRISMARINE_SHARD);
	public final static MaterialData PRISMARINE_CRYSTALS = define("PRISMARINE_CRYSTALS", false, false, Material.PRISMARINE_CRYSTALS);
	public final static MaterialData RABBIT = define("RABBIT", false, false, Material.RABBIT);
	public final static MaterialData COOKED_RABBIT = define("COOKED_RABBIT", false, false, Material.COOKED_RABBIT);
	public final static MaterialData RABBIT_STEW = define("RABBIT_STEW", false, false, Material.RABBIT_STEW);
	public final static MaterialData RABBIT_FOOT = define("RABBIT_FOOT", false, false, Material.RABBIT_FOOT);
	public final static MaterialData RABBIT_HIDE = define("RABBIT_HIDE", false, false, Material.RABBIT_HIDE);
	public final static MaterialData ARMOR_STAND = define("ARMOR_STAND", false, false, Material.ARMOR_STAND);
	public final static MaterialData IRON_BARDING = define("IRON_BARDING", false, false, Material.IRON_BARDING);
	public final static MaterialData GOLD_BARDING = define("GOLD_BARDING", false, false, Material.GOLD_BARDING);
	public final static MaterialData DIAMOND_BARDING = define("DIAMOND_BARDING", false, false, Material.DIAMOND_BARDING);
	public final static MaterialData LEASH = define("LEASH", false, false, Material.LEASH);
	public final static MaterialData NAME_TAG = define("NAME_TAG", false, false, Material.NAME_TAG);
	public final static MaterialData COMMAND_MINECART = define("COMMAND_MINECART", false, false, Material.COMMAND_MINECART);
	public final static MaterialData MUTTON = define("MUTTON", false, false, Material.MUTTON);
	public final static MaterialData COOKED_MUTTON = define("COOKED_MUTTON", false, false, Material.COOKED_MUTTON);
	public final static MaterialData BANNER = define("BANNER", false, false, Material.BANNER);
	public final static MaterialData END_CRYSTAL = define("END_CRYSTAL", false, false, Material.END_CRYSTAL);
	public final static MaterialData SPRUCE_DOOR_ITEM = define("SPRUCE_DOOR_ITEM", false, false, Material.SPRUCE_DOOR_ITEM);
	public final static MaterialData BIRCH_DOOR_ITEM = define("BIRCH_DOOR_ITEM", false, false, Material.BIRCH_DOOR_ITEM);
	public final static MaterialData JUNGLE_DOOR_ITEM = define("JUNGLE_DOOR_ITEM", false, false, Material.JUNGLE_DOOR_ITEM);
	public final static MaterialData ACACIA_DOOR_ITEM = define("ACACIA_DOOR_ITEM", false, false, Material.ACACIA_DOOR_ITEM);
	public final static MaterialData DARK_OAK_DOOR_ITEM = define("DARK_OAK_DOOR_ITEM", false, false, Material.DARK_OAK_DOOR_ITEM);
	public final static MaterialData CHORUS_FRUIT = define("CHORUS_FRUIT", false, false, Material.CHORUS_FRUIT);
	public final static MaterialData CHORUS_FRUIT_POPPED = define("CHORUS_FRUIT_POPPED", false, false, Material.CHORUS_FRUIT_POPPED);
	public final static MaterialData BEETROOT = define("BEETROOT", false, false, Material.BEETROOT);
	public final static MaterialData BEETROOT_SEEDS = define("BEETROOT_SEEDS", false, false, Material.BEETROOT_SEEDS);
	public final static MaterialData BEETROOT_SOUP = define("BEETROOT_SOUP", false, false, Material.BEETROOT_SOUP);
	public final static MaterialData DRAGONS_BREATH = define("DRAGONS_BREATH", false, false, Material.DRAGONS_BREATH);
	public final static MaterialData SPLASH_POTION = define("SPLASH_POTION", false, false, Material.SPLASH_POTION);
	public final static MaterialData SPECTRAL_ARROW = define("SPECTRAL_ARROW", false, false, Material.SPECTRAL_ARROW);
	public final static MaterialData TIPPED_ARROW = define("TIPPED_ARROW", false, false, Material.TIPPED_ARROW);
	public final static MaterialData LINGERING_POTION = define("LINGERING_POTION", false, false, Material.LINGERING_POTION);
	public final static MaterialData SHIELD = define("SHIELD", false, false, Material.SHIELD);
	public final static MaterialData ELYTRA = define("ELYTRA", false, false, Material.ELYTRA);
	public final static MaterialData BOAT_SPRUCE = define("BOAT_SPRUCE", false, false, Material.BOAT_SPRUCE);
	public final static MaterialData BOAT_BIRCH = define("BOAT_BIRCH", false, false, Material.BOAT_BIRCH);
	public final static MaterialData BOAT_JUNGLE = define("BOAT_JUNGLE", false, false, Material.BOAT_JUNGLE);
	public final static MaterialData BOAT_ACACIA = define("BOAT_ACACIA", false, false, Material.BOAT_ACACIA);
	public final static MaterialData BOAT_DARK_OAK = define("BOAT_DARK_OAK", false, false, Material.BOAT_DARK_OAK);
	public final static MaterialData TOTEM = define("TOTEM", false, false, Material.TOTEM);
	public final static MaterialData SHULKER_SHELL = define("SHULKER_SHELL", false, false, Material.SHULKER_SHELL);
	public final static MaterialData IRON_NUGGET = define("IRON_NUGGET", false, false, Material.IRON_NUGGET);
	public final static MaterialData KNOWLEDGE_BOOK = define("KNOWLEDGE_BOOK", false, false, Material.KNOWLEDGE_BOOK);
	public final static MaterialData GOLD_RECORD = define("GOLD_RECORD", false, false, Material.GOLD_RECORD);
	public final static MaterialData GREEN_RECORD = define("GREEN_RECORD", false, false, Material.GREEN_RECORD);
	public final static MaterialData RECORD_3 = define("RECORD_3", false, false, Material.RECORD_3);
	public final static MaterialData RECORD_4 = define("RECORD_4", false, false, Material.RECORD_4);
	public final static MaterialData RECORD_5 = define("RECORD_5", false, false, Material.RECORD_5);
	public final static MaterialData RECORD_6 = define("RECORD_6", false, false, Material.RECORD_6);
	public final static MaterialData RECORD_7 = define("RECORD_7", false, false, Material.RECORD_7);
	public final static MaterialData RECORD_8 = define("RECORD_8", false, false, Material.RECORD_8);
	public final static MaterialData RECORD_9 = define("RECORD_9", false, false, Material.RECORD_9);
	public final static MaterialData RECORD_10 = define("RECORD_10", false, false, Material.RECORD_10);
	public final static MaterialData RECORD_11 = define("RECORD_11", false, false, Material.RECORD_11);
	public final static MaterialData RECORD_12 = define("RECORD_12", false, false, Material.RECORD_12);
	
	private static Map<Integer, MaterialEntry> entries = new HashMap<Integer, MaterialEntry>();
	
	private static class MaterialEntry {
		private final String ident;
		private final boolean stackable;
		private final MaterialData data;
		
		private MaterialEntry(String ident, boolean stackable, MaterialData data) {
			this.ident = ident;
			this.stackable = stackable;
			this.data = data;
		}
		
		// TODO figure out a more elegant way to do this
		private static int maxTypeId = 4096;
		
		@SuppressWarnings("deprecation")
		private static int calcHash(MaterialData data) {
			return data.getItemTypeId() * maxTypeId + data.getData();
		}

		@SuppressWarnings("deprecation")
		private static int calcHash(Material data) {
			return data.getId() * maxTypeId;
		}
	}
	
	// PUBLIC APIs
	private final static MaterialData define(String ident, boolean buildable, boolean deprecated, Material material) {
		return(define(ident, buildable, deprecated, new MaterialData(material)));
	}
	
	private final static MaterialData define(String ident, boolean buildable, boolean deprecated, MaterialData data) {
		MaterialEntry entry = new MaterialEntry(ident, buildable, data);
		entries.put(MaterialEntry.calcHash(data), entry);

		//TODO: Figure out deprecate to non-deprecate mapping
		
		return(data);
	}
	
	@Deprecated
	public final static MaterialData asData(Material type) {
		return new MaterialData(type);
	}
	
	public final static MaterialData find(String ident) {
		Set<Map.Entry<Integer, MaterialEntry>> entrySet = entries.entrySet();
		for (Entry<Integer, MaterialEntry> entry : entrySet) {
			if (entry.getValue().ident.equals(ident))
				return entry.getValue().data;
		}
		return null;
	}
	
	public final static String find(MaterialData data) {
		MaterialEntry entry = entries.get(MaterialEntry.calcHash(data));
		assert(entry != null);
		return entry.ident;
	}
	
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
		state.setType(material.getItemType());
		state.setData(material);
		state.update(update);
		return block;
	}
	
	public final static boolean isStackable(MaterialData data) {
		MaterialEntry entry = entries.get(MaterialEntry.calcHash(data));
		assert(entry != null);
		return entry.stackable;
	}
	
	@Deprecated
	public final static boolean isStackable(Material data) {
		MaterialEntry entry = entries.get(MaterialEntry.calcHash(data));
		assert(entry != null);
		return entry.stackable;
	}
	
	public final static boolean isStackable(Block block) {
		MaterialEntry entry = entries.get(MaterialEntry.calcHash(block.getType()));
		assert(entry != null);
		return entry.stackable;
	}
	
	public final static MaterialData adjust(MaterialData material, DyeColor color) {
		MaterialData result = null;
		if (material instanceof Colorable) {
			result = material.clone();
			((Colorable)result).setColor(color);
		} 
		assert(result != null);
		return result;
	}
	
	public final static MaterialData adjust(MaterialData material, BlockFace facing) {
		MaterialData result = null;
		if (material instanceof Directional) {
			result = material.clone();
			((Directional)result).setFacingDirection(facing);
		} 
		assert(result != null);
		return result;
	}

	public final static MaterialData adjust(MaterialData material, Material texture) {
		MaterialData result = null;
		if (material instanceof TexturedMaterial) {
			result = material.clone();
			((TexturedMaterial)result).setMaterial(texture);
		} 
		assert(result != null);
		return result;
	}
	
	public final static MaterialData adjust(MaterialData material, BlockFace face, boolean invert) {
		MaterialData result = null;
		if (material instanceof Stairs) {
			result = material.clone();
			((Stairs)result).setFacingDirection(face);
			((Stairs)result).setInverted(invert);
		} 
		assert(result != null);
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
