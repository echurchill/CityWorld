package me.daddychurchill.CityWorld.Support;

import org.bukkit.Material;

public final class Mapper {

	public static final Material getStairsFor(Material material) {
		switch (material) {
		case BRICK_STAIRS:
		case BRICK_SLAB:
		case BRICKS:
		default:
			return Material.BRICK_STAIRS;
			
		case ACACIA_STAIRS:
		case ACACIA_FENCE:
		case ACACIA_LOG:
		case ACACIA_PLANKS:
		case ACACIA_SLAB:
		case ACACIA_WOOD:
		case STRIPPED_ACACIA_LOG:
		case STRIPPED_ACACIA_WOOD:
			return Material.ACACIA_STAIRS;
			
		case BIRCH_STAIRS:
		case BIRCH_FENCE:
		case BIRCH_LOG:
		case BIRCH_PLANKS:
		case BIRCH_SLAB:
		case BIRCH_WOOD:
		case STRIPPED_BIRCH_LOG:
		case STRIPPED_BIRCH_WOOD:
			return Material.BIRCH_STAIRS;
			
		case DARK_OAK_STAIRS:
		case DARK_OAK_FENCE:
		case DARK_OAK_LOG:
		case DARK_OAK_PLANKS:
		case DARK_OAK_SLAB:
		case DARK_OAK_WOOD:
		case STRIPPED_DARK_OAK_LOG:
		case STRIPPED_DARK_OAK_WOOD:
			return Material.DARK_OAK_STAIRS;
			
		case JUNGLE_STAIRS:
		case JUNGLE_FENCE:
		case JUNGLE_LOG:
		case JUNGLE_PLANKS:
		case JUNGLE_SLAB:
		case JUNGLE_WOOD:
		case STRIPPED_JUNGLE_LOG:
		case STRIPPED_JUNGLE_WOOD:
			return Material.JUNGLE_STAIRS;
			
		case OAK_STAIRS:
		case OAK_FENCE:
		case OAK_LOG:
		case OAK_PLANKS:
		case OAK_SLAB:
		case OAK_WOOD:
		case STRIPPED_OAK_LOG:
		case STRIPPED_OAK_WOOD:
			return Material.OAK_STAIRS;
			
		case SPRUCE_STAIRS:
		case SPRUCE_FENCE:
		case SPRUCE_LOG:
		case SPRUCE_PLANKS:
		case SPRUCE_SLAB:
		case SPRUCE_WOOD:
		case STRIPPED_SPRUCE_LOG:
		case STRIPPED_SPRUCE_WOOD:
			return Material.SPRUCE_STAIRS;
			
		case COBBLESTONE_STAIRS:
		case COBBLESTONE_SLAB:
		case COBBLESTONE_WALL:
		case MOSSY_COBBLESTONE_WALL:
		case COBBLESTONE:
		case INFESTED_COBBLESTONE:
		case MOSSY_COBBLESTONE:
			return Material.COBBLESTONE_STAIRS;
			
		case PRISMARINE_STAIRS:
		case PRISMARINE:
		case PRISMARINE_SLAB:
			return Material.PRISMARINE_STAIRS;
		
		case PRISMARINE_BRICK_STAIRS:
		case PRISMARINE_BRICKS:
		case PRISMARINE_BRICK_SLAB:
			return Material.PRISMARINE_BRICK_STAIRS;
		
		case DARK_PRISMARINE_STAIRS:
		case DARK_PRISMARINE:
		case DARK_PRISMARINE_SLAB:
			return Material.DARK_PRISMARINE_STAIRS;
			
		case PURPUR_STAIRS:
		case PURPUR_BLOCK:
		case PURPUR_PILLAR:
		case PURPUR_SLAB:
			return Material.PURPUR_STAIRS;

		case NETHERRACK:
		case NETHER_BRICK_STAIRS:
		case NETHER_BRICK_FENCE:
		case NETHER_BRICK_SLAB:
		case NETHER_BRICKS:
		case RED_NETHER_BRICKS:
			return Material.NETHER_BRICK_STAIRS;

		case RED_SANDSTONE_STAIRS:
		case CHISELED_RED_SANDSTONE:
		case CUT_RED_SANDSTONE:
		case RED_SANDSTONE:
		case RED_SANDSTONE_SLAB:
		case SMOOTH_RED_SANDSTONE:
			return Material.RED_SANDSTONE_STAIRS;
		
		case STONE_BRICK_STAIRS:
		case CHISELED_STONE_BRICKS:
		case CRACKED_STONE_BRICKS:
		case END_STONE_BRICKS:
		case INFESTED_CHISELED_STONE_BRICKS:
		case INFESTED_CRACKED_STONE_BRICKS:
		case INFESTED_MOSSY_STONE_BRICKS:
		case INFESTED_STONE_BRICKS:
		case MOSSY_STONE_BRICKS:
		case STONE_BRICK_SLAB:
		case STONE_BRICKS:
			return Material.STONE_BRICK_STAIRS;
		
		case QUARTZ_STAIRS:
		case CHISELED_QUARTZ_BLOCK:
		case NETHER_QUARTZ_ORE:
		case QUARTZ_BLOCK:
		case QUARTZ_PILLAR:
		case QUARTZ_SLAB:
		case SMOOTH_QUARTZ:
			return Material.QUARTZ_STAIRS;
			
		case SANDSTONE_STAIRS:
		case CHISELED_SANDSTONE:
		case CUT_SANDSTONE:
		case SANDSTONE:
		case SANDSTONE_SLAB:
		case SMOOTH_SANDSTONE:
			return Material.SANDSTONE_STAIRS;
		}
	}

	public static final Material getStairWallFor(Material material) {
		switch (material) {
		case BRICK_STAIRS:
		case BRICK_SLAB:
		case BRICKS:
		default:
			return Material.BRICKS;
			
		case ACACIA_STAIRS:
		case ACACIA_FENCE:
		case ACACIA_LOG:
		case ACACIA_PLANKS:
		case ACACIA_SLAB:
		case ACACIA_WOOD:
		case STRIPPED_ACACIA_LOG:
		case STRIPPED_ACACIA_WOOD:
			return Material.ACACIA_WOOD;
			
		case BIRCH_STAIRS:
		case BIRCH_FENCE:
		case BIRCH_LOG:
		case BIRCH_PLANKS:
		case BIRCH_SLAB:
		case BIRCH_WOOD:
		case STRIPPED_BIRCH_LOG:
		case STRIPPED_BIRCH_WOOD:
			return Material.BIRCH_WOOD;
			
		case DARK_OAK_STAIRS:
		case DARK_OAK_FENCE:
		case DARK_OAK_LOG:
		case DARK_OAK_PLANKS:
		case DARK_OAK_SLAB:
		case DARK_OAK_WOOD:
		case STRIPPED_DARK_OAK_LOG:
		case STRIPPED_DARK_OAK_WOOD:
			return Material.DARK_OAK_WOOD;
			
		case JUNGLE_STAIRS:
		case JUNGLE_FENCE:
		case JUNGLE_LOG:
		case JUNGLE_PLANKS:
		case JUNGLE_SLAB:
		case JUNGLE_WOOD:
		case STRIPPED_JUNGLE_LOG:
		case STRIPPED_JUNGLE_WOOD:
			return Material.JUNGLE_WOOD;
			
		case OAK_STAIRS:
		case OAK_FENCE:
		case OAK_LOG:
		case OAK_PLANKS:
		case OAK_SLAB:
		case OAK_WOOD:
		case STRIPPED_OAK_LOG:
		case STRIPPED_OAK_WOOD:
			return Material.OAK_WOOD;
			
		case SPRUCE_STAIRS:
		case SPRUCE_FENCE:
		case SPRUCE_LOG:
		case SPRUCE_PLANKS:
		case SPRUCE_SLAB:
		case SPRUCE_WOOD:
		case STRIPPED_SPRUCE_LOG:
		case STRIPPED_SPRUCE_WOOD:
			return Material.SPRUCE_WOOD;
			
		case COBBLESTONE_STAIRS:
		case COBBLESTONE_SLAB:
		case COBBLESTONE_WALL:
		case MOSSY_COBBLESTONE_WALL:
		case COBBLESTONE:
		case INFESTED_COBBLESTONE:
		case MOSSY_COBBLESTONE:
			return Material.COBBLESTONE;
			
		case PRISMARINE_STAIRS:
		case PRISMARINE:
		case PRISMARINE_SLAB:
			return Material.PRISMARINE;
		
		case PRISMARINE_BRICK_STAIRS:
		case PRISMARINE_BRICKS:
		case PRISMARINE_BRICK_SLAB:
			return Material.PRISMARINE_BRICKS;
		
		case DARK_PRISMARINE_STAIRS:
		case DARK_PRISMARINE:
		case DARK_PRISMARINE_SLAB:
			return Material.DARK_PRISMARINE;
			
		case PURPUR_STAIRS:
		case PURPUR_BLOCK:
		case PURPUR_PILLAR:
		case PURPUR_SLAB:
			return Material.PURPUR_BLOCK;

		case NETHERRACK:
		case NETHER_BRICK_STAIRS:
		case NETHER_BRICK_FENCE:
		case NETHER_BRICK_SLAB:
		case NETHER_BRICKS:
		case RED_NETHER_BRICKS:
			return Material.NETHER_BRICKS;

		case RED_SANDSTONE_STAIRS:
		case CHISELED_RED_SANDSTONE:
		case CUT_RED_SANDSTONE:
		case RED_SANDSTONE:
		case RED_SANDSTONE_SLAB:
		case SMOOTH_RED_SANDSTONE:
			return Material.RED_SANDSTONE;
		
		case STONE_BRICK_STAIRS:
		case CHISELED_STONE_BRICKS:
		case CRACKED_STONE_BRICKS:
		case END_STONE_BRICKS:
		case INFESTED_CHISELED_STONE_BRICKS:
		case INFESTED_CRACKED_STONE_BRICKS:
		case INFESTED_MOSSY_STONE_BRICKS:
		case INFESTED_STONE_BRICKS:
		case MOSSY_STONE_BRICKS:
		case STONE_BRICK_SLAB:
		case STONE_BRICKS:
			return Material.STONE_BRICKS;
		
		case QUARTZ_STAIRS:
		case CHISELED_QUARTZ_BLOCK:
		case NETHER_QUARTZ_ORE:
		case QUARTZ_BLOCK:
		case QUARTZ_PILLAR:
		case QUARTZ_SLAB:
		case SMOOTH_QUARTZ:
			return Material.QUARTZ_BLOCK;
			
		case SANDSTONE_STAIRS:
		case CHISELED_SANDSTONE:
		case CUT_SANDSTONE:
		case SANDSTONE:
		case SANDSTONE_SLAB:
		case SMOOTH_SANDSTONE:
			return Material.SANDSTONE;
		}
	}

	public static final Material getStairPlatformFor(Material material) {
		switch (material) {
		case BRICK_STAIRS:
		case BRICK_SLAB:
		case BRICKS:
		default:
			return Material.BRICKS;
			
		case ACACIA_STAIRS:
		case ACACIA_FENCE:
		case ACACIA_LOG:
		case ACACIA_PLANKS:
		case ACACIA_SLAB:
		case ACACIA_WOOD:
		case STRIPPED_ACACIA_LOG:
		case STRIPPED_ACACIA_WOOD:
			return Material.ACACIA_WOOD;
			
		case BIRCH_STAIRS:
		case BIRCH_FENCE:
		case BIRCH_LOG:
		case BIRCH_PLANKS:
		case BIRCH_SLAB:
		case BIRCH_WOOD:
		case STRIPPED_BIRCH_LOG:
		case STRIPPED_BIRCH_WOOD:
			return Material.BIRCH_WOOD;
			
		case DARK_OAK_STAIRS:
		case DARK_OAK_FENCE:
		case DARK_OAK_LOG:
		case DARK_OAK_PLANKS:
		case DARK_OAK_SLAB:
		case DARK_OAK_WOOD:
		case STRIPPED_DARK_OAK_LOG:
		case STRIPPED_DARK_OAK_WOOD:
			return Material.DARK_OAK_WOOD;
			
		case JUNGLE_STAIRS:
		case JUNGLE_FENCE:
		case JUNGLE_LOG:
		case JUNGLE_PLANKS:
		case JUNGLE_SLAB:
		case JUNGLE_WOOD:
		case STRIPPED_JUNGLE_LOG:
		case STRIPPED_JUNGLE_WOOD:
			return Material.JUNGLE_WOOD;
			
		case OAK_STAIRS:
		case OAK_FENCE:
		case OAK_LOG:
		case OAK_PLANKS:
		case OAK_SLAB:
		case OAK_WOOD:
		case STRIPPED_OAK_LOG:
		case STRIPPED_OAK_WOOD:
			return Material.OAK_WOOD;
			
		case SPRUCE_STAIRS:
		case SPRUCE_FENCE:
		case SPRUCE_LOG:
		case SPRUCE_PLANKS:
		case SPRUCE_SLAB:
		case SPRUCE_WOOD:
		case STRIPPED_SPRUCE_LOG:
		case STRIPPED_SPRUCE_WOOD:
			return Material.SPRUCE_WOOD;
			
		case COBBLESTONE_STAIRS:
		case COBBLESTONE_SLAB:
		case COBBLESTONE_WALL:
		case MOSSY_COBBLESTONE_WALL:
		case COBBLESTONE:
		case INFESTED_COBBLESTONE:
		case MOSSY_COBBLESTONE:
			return Material.COBBLESTONE;
			
		case PRISMARINE_STAIRS:
		case PRISMARINE:
		case PRISMARINE_SLAB:
			return Material.PRISMARINE;
		
		case PRISMARINE_BRICK_STAIRS:
		case PRISMARINE_BRICKS:
		case PRISMARINE_BRICK_SLAB:
			return Material.PRISMARINE_BRICKS;
		
		case DARK_PRISMARINE_STAIRS:
		case DARK_PRISMARINE:
		case DARK_PRISMARINE_SLAB:
			return Material.DARK_PRISMARINE;
			
		case PURPUR_STAIRS:
		case PURPUR_BLOCK:
		case PURPUR_PILLAR:
		case PURPUR_SLAB:
			return Material.PURPUR_STAIRS;

		case NETHERRACK:
		case NETHER_BRICK_STAIRS:
		case NETHER_BRICK_FENCE:
		case NETHER_BRICK_SLAB:
		case NETHER_BRICKS:
		case RED_NETHER_BRICKS:
			return Material.NETHER_BRICKS;

		case RED_SANDSTONE_STAIRS:
		case CHISELED_RED_SANDSTONE:
		case CUT_RED_SANDSTONE:
		case RED_SANDSTONE:
		case RED_SANDSTONE_SLAB:
		case SMOOTH_RED_SANDSTONE:
			return Material.RED_SANDSTONE;
		
		case STONE_BRICK_STAIRS:
		case CHISELED_STONE_BRICKS:
		case CRACKED_STONE_BRICKS:
		case END_STONE_BRICKS:
		case INFESTED_CHISELED_STONE_BRICKS:
		case INFESTED_CRACKED_STONE_BRICKS:
		case INFESTED_MOSSY_STONE_BRICKS:
		case INFESTED_STONE_BRICKS:
		case MOSSY_STONE_BRICKS:
		case STONE_BRICK_SLAB:
		case STONE_BRICKS:
			return Material.STONE_BRICKS;
		
		case QUARTZ_STAIRS:
		case CHISELED_QUARTZ_BLOCK:
		case NETHER_QUARTZ_ORE:
		case QUARTZ_BLOCK:
		case QUARTZ_PILLAR:
		case QUARTZ_SLAB:
		case SMOOTH_QUARTZ:
			return Material.QUARTZ_BLOCK;
			
		case SANDSTONE_STAIRS:
		case CHISELED_SANDSTONE:
		case CUT_SANDSTONE:
		case SANDSTONE:
		case SANDSTONE_SLAB:
		case SMOOTH_SANDSTONE:
			return Material.SANDSTONE;
		}
	}

	public static final Material getDoorsFor(Material material) {
		switch (material) {
		default:
			return Material.BIRCH_DOOR;
			
		case ACACIA_STAIRS:
		case ACACIA_FENCE:
		case ACACIA_LOG:
		case ACACIA_PLANKS:
		case ACACIA_SLAB:
		case ACACIA_WOOD:
		case STRIPPED_ACACIA_LOG:
		case STRIPPED_ACACIA_WOOD:
			return Material.ACACIA_DOOR;
			
		case BIRCH_STAIRS:
		case BIRCH_FENCE:
		case BIRCH_LOG:
		case BIRCH_PLANKS:
		case BIRCH_SLAB:
		case BIRCH_WOOD:
		case STRIPPED_BIRCH_LOG:
		case STRIPPED_BIRCH_WOOD:
			return Material.BIRCH_DOOR;
			
		case DARK_OAK_STAIRS:
		case DARK_OAK_FENCE:
		case DARK_OAK_LOG:
		case DARK_OAK_PLANKS:
		case DARK_OAK_SLAB:
		case DARK_OAK_WOOD:
		case STRIPPED_DARK_OAK_LOG:
		case STRIPPED_DARK_OAK_WOOD:
			return Material.DARK_OAK_DOOR;
			
		case JUNGLE_STAIRS:
		case JUNGLE_FENCE:
		case JUNGLE_LOG:
		case JUNGLE_PLANKS:
		case JUNGLE_SLAB:
		case JUNGLE_WOOD:
		case STRIPPED_JUNGLE_LOG:
		case STRIPPED_JUNGLE_WOOD:
			return Material.JUNGLE_DOOR;
			
		case OAK_STAIRS:
		case OAK_FENCE:
		case OAK_LOG:
		case OAK_PLANKS:
		case OAK_SLAB:
		case OAK_WOOD:
		case STRIPPED_OAK_LOG:
		case STRIPPED_OAK_WOOD:
			return Material.OAK_DOOR;
			
		case SPRUCE_STAIRS:
		case SPRUCE_FENCE:
		case SPRUCE_LOG:
		case SPRUCE_PLANKS:
		case SPRUCE_SLAB:
		case SPRUCE_WOOD:
		case STRIPPED_SPRUCE_LOG:
		case STRIPPED_SPRUCE_WOOD:
			return Material.SPRUCE_DOOR;
		}
	}

	public static final Material getColumnFor(Material material) {
		switch (material) {
		default:
			return Material.COBBLESTONE_WALL;
			
		case ACACIA_STAIRS:
		case ACACIA_FENCE:
		case ACACIA_LOG:
		case ACACIA_PLANKS:
		case ACACIA_SLAB:
		case ACACIA_WOOD:
		case STRIPPED_ACACIA_LOG:
		case STRIPPED_ACACIA_WOOD:
			return Material.ACACIA_FENCE;
			
		case BIRCH_STAIRS:
		case BIRCH_FENCE:
		case BIRCH_LOG:
		case BIRCH_PLANKS:
		case BIRCH_SLAB:
		case BIRCH_WOOD:
		case STRIPPED_BIRCH_LOG:
		case STRIPPED_BIRCH_WOOD:
			return Material.BIRCH_FENCE;
			
		case DARK_OAK_STAIRS:
		case DARK_OAK_FENCE:
		case DARK_OAK_LOG:
		case DARK_OAK_PLANKS:
		case DARK_OAK_SLAB:
		case DARK_OAK_WOOD:
		case STRIPPED_DARK_OAK_LOG:
		case STRIPPED_DARK_OAK_WOOD:
			return Material.DARK_OAK_FENCE;
			
		case JUNGLE_STAIRS:
		case JUNGLE_FENCE:
		case JUNGLE_LOG:
		case JUNGLE_PLANKS:
		case JUNGLE_SLAB:
		case JUNGLE_WOOD:
		case STRIPPED_JUNGLE_LOG:
		case STRIPPED_JUNGLE_WOOD:
			return Material.JUNGLE_FENCE;
			
		case OAK_STAIRS:
		case OAK_FENCE:
		case OAK_LOG:
		case OAK_PLANKS:
		case OAK_SLAB:
		case OAK_WOOD:
		case STRIPPED_OAK_LOG:
		case STRIPPED_OAK_WOOD:
			return Material.OAK_FENCE;
			
		case SPRUCE_STAIRS:
		case SPRUCE_FENCE:
		case SPRUCE_LOG:
		case SPRUCE_PLANKS:
		case SPRUCE_SLAB:
		case SPRUCE_WOOD:
		case STRIPPED_SPRUCE_LOG:
		case STRIPPED_SPRUCE_WOOD:
			return Material.SPRUCE_FENCE;
			
		case COBBLESTONE_STAIRS:
		case COBBLESTONE_SLAB:
		case COBBLESTONE_WALL:
		case MOSSY_COBBLESTONE_WALL:
		case COBBLESTONE:
		case INFESTED_COBBLESTONE:
		case MOSSY_COBBLESTONE:
			return Material.COBBLESTONE_WALL;
			
		case PURPUR_STAIRS:
		case PURPUR_BLOCK:
		case PURPUR_PILLAR:
		case PURPUR_SLAB:
			return Material.PURPUR_PILLAR;

		case NETHERRACK:
		case NETHER_BRICK_STAIRS:
		case NETHER_BRICK_FENCE:
		case NETHER_BRICK_SLAB:
		case NETHER_BRICKS:
		case RED_NETHER_BRICKS:
			return Material.NETHER_BRICK_FENCE;

		case QUARTZ_STAIRS:
		case CHISELED_QUARTZ_BLOCK:
		case NETHER_QUARTZ_ORE:
		case QUARTZ_BLOCK:
		case QUARTZ_PILLAR:
		case QUARTZ_SLAB:
		case SMOOTH_QUARTZ:
			return Material.QUARTZ_PILLAR;
		}
	}
}
