package me.daddychurchill.CityWorld.Support;

import java.util.Map;
import java.util.TreeMap;

import org.bukkit.Material;

public final class Mapper {
	
	public static final Material getStairsFor(Material material) {
		MapperEntry entry = getMaterialMapping(material);
		if (entry != null)
			return entry.stairs;
		else
			return Material.BRICK_STAIRS;
	}

	public static final Material getStairWallFor(Material material) {
		MapperEntry entry = getMaterialMapping(material);
		if (entry != null)
			return entry.stairWalls;
		else
			return Material.BRICKS;
	}

	public static final Material getStairPlatformFor(Material material) {
		MapperEntry entry = getMaterialMapping(material);
		if (entry != null)
			return entry.stairPlatform;
		else
			return Material.BRICKS;
	}

	public static final Material getDoorsFor(Material material) {
		MapperEntry entry = getMaterialMapping(material);
		if (entry != null)
			return entry.door;
		else
			return Material.BIRCH_DOOR;
	}

	public static final Material getColumnFor(Material material) {
		MapperEntry entry = getMaterialMapping(material);
		if (entry != null)
			return entry.columns;
		else
			return Material.COBBLESTONE_WALL;
	}
	
	private static class MapperEntry {
		protected Material columns;
		protected Material door;
		protected Material stairPlatform;
		protected Material stairs;
		protected Material stairWalls;
		
		public MapperEntry(Material aColumns, Material aDoor, Material aStairPlatform, Material aStairs, Material aStairWalls) {
			assert(aColumns.isBlock());
			assert(aDoor.isBlock());
			assert(aStairPlatform.isBlock());
			assert(aStairs.isBlock());
			assert(aStairWalls.isBlock());
			columns = aColumns;
			door = aDoor;
			stairPlatform = aStairPlatform;
			stairs = aStairs;
			stairWalls = aStairWalls;
		}
	}

	private static Map<Material, MapperEntry> mapping = new TreeMap<Material, MapperEntry>();
	private static MapperEntry getMaterialMapping(Material lookup) {
		if (mapping.isEmpty()) {
			Material[] materials = Material.values();
//			CityWorld.log.info("@@ Mapping " + materials.length + " materials");
			for (Material material : materials) {
				if (material.isBlock()) {
					String name = material.name(); // we are looking at the raw enum name here
					if (name.contains("ACACIA"))
						mapping.put(material, new MapperEntry(Material.ACACIA_FENCE, Material.ACACIA_DOOR, Material.ACACIA_PLANKS, Material.ACACIA_STAIRS, Material.ACACIA_PLANKS));
					else if (name.contains("BIRCH"))
						mapping.put(material, new MapperEntry(Material.BIRCH_FENCE, Material.BIRCH_DOOR, Material.BIRCH_PLANKS, Material.BIRCH_STAIRS, Material.BIRCH_PLANKS));
					else if (name.contains("DARK_OAK"))
						mapping.put(material, new MapperEntry(Material.DARK_OAK_FENCE, Material.DARK_OAK_DOOR, Material.DARK_OAK_PLANKS, Material.DARK_OAK_STAIRS, Material.DARK_OAK_PLANKS));
					else if (name.contains("OAK"))
						mapping.put(material, new MapperEntry(Material.OAK_FENCE, Material.OAK_DOOR, Material.OAK_PLANKS, Material.OAK_STAIRS, Material.OAK_PLANKS));
					else if (name.contains("JUNGLE"))
						mapping.put(material, new MapperEntry(Material.JUNGLE_FENCE, Material.JUNGLE_DOOR, Material.JUNGLE_PLANKS, Material.JUNGLE_STAIRS, Material.JUNGLE_PLANKS));
					else if (name.contains("SPRUCE"))
						mapping.put(material, new MapperEntry(Material.SPRUCE_FENCE, Material.SPRUCE_DOOR, Material.SPRUCE_PLANKS, Material.SPRUCE_STAIRS, Material.SPRUCE_PLANKS));
					else if (name.contains("PRISMARINE_BRICK"))
						mapping.put(material, new MapperEntry(Material.IRON_BARS, Material.BIRCH_DOOR, Material.PRISMARINE_BRICKS, Material.PRISMARINE_BRICK_STAIRS, Material.PRISMARINE_BRICKS));
					else if (name.contains("DARK_PRISMARINE"))
						mapping.put(material, new MapperEntry(Material.IRON_BARS, Material.BIRCH_DOOR, Material.DARK_PRISMARINE, Material.DARK_PRISMARINE_STAIRS, Material.DARK_PRISMARINE));
					else if (name.contains("PRISMARINE"))
						mapping.put(material, new MapperEntry(Material.IRON_BARS, Material.BIRCH_DOOR, Material.PRISMARINE, Material.PRISMARINE_STAIRS, Material.PRISMARINE));
					else if (name.contains("PURPUR"))
						mapping.put(material, new MapperEntry(Material.PURPUR_PILLAR, Material.BIRCH_DOOR, Material.PURPUR_BLOCK, Material.PURPUR_STAIRS, Material.PURPUR_BLOCK));
					else if (name.contains("NETHER"))
						mapping.put(material, new MapperEntry(Material.NETHER_BRICK_FENCE, Material.BIRCH_DOOR, Material.NETHER_BRICKS, Material.NETHER_BRICK_STAIRS, Material.NETHER_BRICKS));
					else if (name.contains("RED_SANDSTONE"))
						mapping.put(material, new MapperEntry(Material.IRON_BARS, Material.BIRCH_DOOR, Material.RED_SANDSTONE, Material.RED_SANDSTONE_STAIRS, Material.RED_SANDSTONE));
					else if (name.contains("SANDSTONE"))
						mapping.put(material, new MapperEntry(Material.IRON_BARS, Material.BIRCH_DOOR, Material.SANDSTONE, Material.SANDSTONE_STAIRS, Material.SANDSTONE));
					else if (name.contains("QUARTZ"))
						mapping.put(material, new MapperEntry(Material.QUARTZ_PILLAR, Material.BIRCH_DOOR, Material.QUARTZ_BLOCK, Material.QUARTZ_STAIRS, Material.QUARTZ_BLOCK));
					else if (name.contains("STONE_BRICK"))
						mapping.put(material, new MapperEntry(Material.IRON_BARS, Material.BIRCH_DOOR, Material.STONE_BRICKS, Material.STONE_BRICK_STAIRS, Material.STONE_BRICKS));
					else if (name.contains("COBBLESTONE"))
						mapping.put(material, new MapperEntry(Material.COBBLESTONE_WALL, Material.BIRCH_DOOR, Material.COBBLESTONE, Material.COBBLESTONE_STAIRS, Material.COBBLESTONE));
					else //if (name.contains("BRICK"))
						mapping.put(material, new MapperEntry(Material.COBBLESTONE_WALL, Material.BIRCH_DOOR, Material.BRICKS, Material.BRICK_STAIRS, Material.BRICKS));
				}
			}
		}
		return mapping.get(lookup);
	}

// OLD VERSION
//		switch (material) {
//		case BRICK_STAIRS:
//		case BRICK_SLAB:
//		case BRICKS:
//		default:
//			return Material.BRICK_STAIRS;
//
//		case ACACIA_STAIRS:
//		case ACACIA_FENCE:
//		case ACACIA_LOG:
//		case ACACIA_PLANKS:
//		case ACACIA_SLAB:
//		case ACACIA_WOOD:
//		case STRIPPED_ACACIA_LOG:
//		case STRIPPED_ACACIA_WOOD:
//			return Material.ACACIA_STAIRS;
//
//		case BIRCH_STAIRS:
//		case BIRCH_FENCE:
//		case BIRCH_LOG:
//		case BIRCH_PLANKS:
//		case BIRCH_SLAB:
//		case BIRCH_WOOD:
//		case STRIPPED_BIRCH_LOG:
//		case STRIPPED_BIRCH_WOOD:
//
//		case DARK_OAK_STAIRS:
//		case DARK_OAK_FENCE:
//		case DARK_OAK_LOG:
//		case DARK_OAK_PLANKS:
//		case DARK_OAK_SLAB:
//		case DARK_OAK_WOOD:
//		case STRIPPED_DARK_OAK_LOG:
//		case STRIPPED_DARK_OAK_WOOD:
//
//		case JUNGLE_STAIRS:
//		case JUNGLE_FENCE:
//		case JUNGLE_LOG:
//		case JUNGLE_PLANKS:
//		case JUNGLE_SLAB:
//		case JUNGLE_WOOD:
//		case STRIPPED_JUNGLE_LOG:
//		case STRIPPED_JUNGLE_WOOD:
//			return Material.JUNGLE_STAIRS;
//
//		case OAK_STAIRS:
//		case OAK_FENCE:
//		case OAK_LOG:
//		case OAK_PLANKS:
//		case OAK_SLAB:
//		case OAK_WOOD:
//		case STRIPPED_OAK_LOG:
//		case STRIPPED_OAK_WOOD:
//			return Material.OAK_STAIRS;
//
//		case SPRUCE_STAIRS:
//		case SPRUCE_FENCE:
//		case SPRUCE_LOG:
//		case SPRUCE_PLANKS:
//		case SPRUCE_SLAB:
//		case SPRUCE_WOOD:
//		case STRIPPED_SPRUCE_LOG:
//		case STRIPPED_SPRUCE_WOOD:
//			return Material.SPRUCE_STAIRS;
//
//		case COBBLESTONE_STAIRS:
//		case COBBLESTONE_SLAB:
//		case COBBLESTONE_WALL:
//		case MOSSY_COBBLESTONE_WALL:
//		case COBBLESTONE:
//		case INFESTED_COBBLESTONE:
//		case MOSSY_COBBLESTONE:
//			return Material.COBBLESTONE_STAIRS;
//
//		case PRISMARINE_STAIRS:
//		case PRISMARINE:
//		case PRISMARINE_SLAB:
//			return Material.PRISMARINE_STAIRS;
//
//		case PRISMARINE_BRICK_STAIRS:
//		case PRISMARINE_BRICKS:
//		case PRISMARINE_BRICK_SLAB:
//			return Material.PRISMARINE_BRICK_STAIRS;
//
//		case DARK_PRISMARINE_STAIRS:
//		case DARK_PRISMARINE:
//		case DARK_PRISMARINE_SLAB:
//			return Material.DARK_PRISMARINE_STAIRS;
//
//		case PURPUR_STAIRS:
//		case PURPUR_BLOCK:
//		case PURPUR_PILLAR:
//		case PURPUR_SLAB:
//			return Material.PURPUR_STAIRS;
//
//		case NETHERRACK:
//		case NETHER_BRICK_STAIRS:
//		case NETHER_BRICK_FENCE:
//		case NETHER_BRICK_SLAB:
//		case NETHER_BRICKS:
//		case RED_NETHER_BRICKS:
//			return Material.NETHER_BRICK_STAIRS;
//
//		case RED_SANDSTONE_STAIRS:
//		case CHISELED_RED_SANDSTONE:
//		case CUT_RED_SANDSTONE:
//		case RED_SANDSTONE:
//		case RED_SANDSTONE_SLAB:
//		case SMOOTH_RED_SANDSTONE:
//			return Material.RED_SANDSTONE_STAIRS;
//
//		case STONE_BRICK_STAIRS:
//		case CHISELED_STONE_BRICKS:
//		case CRACKED_STONE_BRICKS:
//		case END_STONE_BRICKS:
//		case INFESTED_CHISELED_STONE_BRICKS:
//		case INFESTED_CRACKED_STONE_BRICKS:
//		case INFESTED_MOSSY_STONE_BRICKS:
//		case INFESTED_STONE_BRICKS:
//		case MOSSY_STONE_BRICKS:
//		case STONE_BRICK_SLAB:
//		case STONE_BRICKS:
//			return Material.STONE_BRICK_STAIRS;
//
//		case QUARTZ_STAIRS:
//		case CHISELED_QUARTZ_BLOCK:
//		case NETHER_QUARTZ_ORE:
//		case QUARTZ_BLOCK:
//		case QUARTZ_PILLAR:
//		case QUARTZ_SLAB:
//		case SMOOTH_QUARTZ:
//			return Material.QUARTZ_STAIRS;
//
//		case SANDSTONE_STAIRS:
//		case CHISELED_SANDSTONE:
//		case CUT_SANDSTONE:
//		case SANDSTONE:
//		case SANDSTONE_SLAB:
//		case SMOOTH_SANDSTONE:
//			return Material.SANDSTONE_STAIRS;
//		}
//	}
//
//	public static final Material getStairWallFor(Material material) {
//		switch (material) {
//		case BRICK_STAIRS:
//		case BRICK_SLAB:
//		case BRICKS:
//		default:
//			return Material.BRICKS;
//
//		case ACACIA_STAIRS:
//		case ACACIA_FENCE:
//		case ACACIA_LOG:
//		case ACACIA_PLANKS:
//		case ACACIA_SLAB:
//		case ACACIA_WOOD:
//		case STRIPPED_ACACIA_LOG:
//		case STRIPPED_ACACIA_WOOD:
//			return Material.ACACIA_PLANKS;
//
//		case BIRCH_STAIRS:
//		case BIRCH_FENCE:
//		case BIRCH_LOG:
//		case BIRCH_PLANKS:
//		case BIRCH_SLAB:
//		case BIRCH_WOOD:
//		case STRIPPED_BIRCH_LOG:
//		case STRIPPED_BIRCH_WOOD:
//			return Material.BIRCH_PLANKS;
//
//		case DARK_OAK_STAIRS:
//		case DARK_OAK_FENCE:
//		case DARK_OAK_LOG:
//		case DARK_OAK_PLANKS:
//		case DARK_OAK_SLAB:
//		case DARK_OAK_WOOD:
//		case STRIPPED_DARK_OAK_LOG:
//		case STRIPPED_DARK_OAK_WOOD:
//			return Material.DARK_OAK_PLANKS;
//
//		case JUNGLE_STAIRS:
//		case JUNGLE_FENCE:
//		case JUNGLE_LOG:
//		case JUNGLE_PLANKS:
//		case JUNGLE_SLAB:
//		case JUNGLE_WOOD:
//		case STRIPPED_JUNGLE_LOG:
//		case STRIPPED_JUNGLE_WOOD:
//			return Material.JUNGLE_PLANKS;
//
//		case OAK_STAIRS:
//		case OAK_FENCE:
//		case OAK_LOG:
//		case OAK_PLANKS:
//		case OAK_SLAB:
//		case OAK_WOOD:
//		case STRIPPED_OAK_LOG:
//		case STRIPPED_OAK_WOOD:
//			return Material.OAK_PLANKS;
//
//		case SPRUCE_STAIRS:
//		case SPRUCE_FENCE:
//		case SPRUCE_LOG:
//		case SPRUCE_PLANKS:
//		case SPRUCE_SLAB:
//		case SPRUCE_WOOD:
//		case STRIPPED_SPRUCE_LOG:
//		case STRIPPED_SPRUCE_WOOD:
//			return Material.SPRUCE_PLANKS;
//
//		case COBBLESTONE_STAIRS:
//		case COBBLESTONE_SLAB:
//		case COBBLESTONE_WALL:
//		case MOSSY_COBBLESTONE_WALL:
//		case COBBLESTONE:
//		case INFESTED_COBBLESTONE:
//		case MOSSY_COBBLESTONE:
//			return Material.COBBLESTONE;
//
//		case PRISMARINE_STAIRS:
//		case PRISMARINE:
//		case PRISMARINE_SLAB:
//			return Material.PRISMARINE;
//
//		case PRISMARINE_BRICK_STAIRS:
//		case PRISMARINE_BRICKS:
//		case PRISMARINE_BRICK_SLAB:
//			return Material.PRISMARINE_BRICKS;
//
//		case DARK_PRISMARINE_STAIRS:
//		case DARK_PRISMARINE:
//		case DARK_PRISMARINE_SLAB:
//			return Material.DARK_PRISMARINE;
//
//		case PURPUR_STAIRS:
//		case PURPUR_BLOCK:
//		case PURPUR_PILLAR:
//		case PURPUR_SLAB:
//			return Material.PURPUR_BLOCK;
//
//		case NETHERRACK:
//		case NETHER_BRICK_STAIRS:
//		case NETHER_BRICK_FENCE:
//		case NETHER_BRICK_SLAB:
//		case NETHER_BRICKS:
//		case RED_NETHER_BRICKS:
//			return Material.NETHER_BRICKS;
//
//		case RED_SANDSTONE_STAIRS:
//		case CHISELED_RED_SANDSTONE:
//		case CUT_RED_SANDSTONE:
//		case RED_SANDSTONE:
//		case RED_SANDSTONE_SLAB:
//		case SMOOTH_RED_SANDSTONE:
//			return Material.RED_SANDSTONE;
//
//		case STONE_BRICK_STAIRS:
//		case CHISELED_STONE_BRICKS:
//		case CRACKED_STONE_BRICKS:
//		case END_STONE_BRICKS:
//		case INFESTED_CHISELED_STONE_BRICKS:
//		case INFESTED_CRACKED_STONE_BRICKS:
//		case INFESTED_MOSSY_STONE_BRICKS:
//		case INFESTED_STONE_BRICKS:
//		case MOSSY_STONE_BRICKS:
//		case STONE_BRICK_SLAB:
//		case STONE_BRICKS:
//			return Material.STONE_BRICKS;
//
//		case QUARTZ_STAIRS:
//		case CHISELED_QUARTZ_BLOCK:
//		case NETHER_QUARTZ_ORE:
//		case QUARTZ_BLOCK:
//		case QUARTZ_PILLAR:
//		case QUARTZ_SLAB:
//		case SMOOTH_QUARTZ:
//			return Material.QUARTZ_BLOCK;
//
//		case SANDSTONE_STAIRS:
//		case CHISELED_SANDSTONE:
//		case CUT_SANDSTONE:
//		case SANDSTONE:
//		case SANDSTONE_SLAB:
//		case SMOOTH_SANDSTONE:
//			return Material.SANDSTONE;
//		}
//	}
//
//	public static final Material getStairPlatformFor(Material material) {
//		switch (material) {
//		case BRICK_STAIRS:
//		case BRICK_SLAB:
//		case BRICKS:
//		default:
//			return Material.BRICKS;
//
//		case ACACIA_STAIRS:
//		case ACACIA_FENCE:
//		case ACACIA_LOG:
//		case ACACIA_PLANKS:
//		case ACACIA_SLAB:
//		case ACACIA_WOOD:
//		case STRIPPED_ACACIA_LOG:
//		case STRIPPED_ACACIA_WOOD:
//			return Material.ACACIA_PLANKS;
//
//		case BIRCH_STAIRS:
//		case BIRCH_FENCE:
//		case BIRCH_LOG:
//		case BIRCH_PLANKS:
//		case BIRCH_SLAB:
//		case BIRCH_WOOD:
//		case STRIPPED_BIRCH_LOG:
//		case STRIPPED_BIRCH_WOOD:
//			return Material.BIRCH_PLANKS;
//
//		case DARK_OAK_STAIRS:
//		case DARK_OAK_FENCE:
//		case DARK_OAK_LOG:
//		case DARK_OAK_PLANKS:
//		case DARK_OAK_SLAB:
//		case DARK_OAK_WOOD:
//		case STRIPPED_DARK_OAK_LOG:
//		case STRIPPED_DARK_OAK_WOOD:
//			return Material.DARK_OAK_PLANKS;
//
//		case JUNGLE_STAIRS:
//		case JUNGLE_FENCE:
//		case JUNGLE_LOG:
//		case JUNGLE_PLANKS:
//		case JUNGLE_SLAB:
//		case JUNGLE_WOOD:
//		case STRIPPED_JUNGLE_LOG:
//		case STRIPPED_JUNGLE_WOOD:
//			return Material.JUNGLE_PLANKS;
//
//		case OAK_STAIRS:
//		case OAK_FENCE:
//		case OAK_LOG:
//		case OAK_PLANKS:
//		case OAK_SLAB:
//		case OAK_WOOD:
//		case STRIPPED_OAK_LOG:
//		case STRIPPED_OAK_WOOD:
//			return Material.OAK_PLANKS;
//
//		case SPRUCE_STAIRS:
//		case SPRUCE_FENCE:
//		case SPRUCE_LOG:
//		case SPRUCE_PLANKS:
//		case SPRUCE_SLAB:
//		case SPRUCE_WOOD:
//		case STRIPPED_SPRUCE_LOG:
//		case STRIPPED_SPRUCE_WOOD:
//			return Material.SPRUCE_PLANKS;
//
//		case COBBLESTONE_STAIRS:
//		case COBBLESTONE_SLAB:
//		case COBBLESTONE_WALL:
//		case MOSSY_COBBLESTONE_WALL:
//		case COBBLESTONE:
//		case INFESTED_COBBLESTONE:
//		case MOSSY_COBBLESTONE:
//			return Material.COBBLESTONE;
//
//		case PRISMARINE_STAIRS:
//		case PRISMARINE:
//		case PRISMARINE_SLAB:
//			return Material.PRISMARINE;
//
//		case PRISMARINE_BRICK_STAIRS:
//		case PRISMARINE_BRICKS:
//		case PRISMARINE_BRICK_SLAB:
//			return Material.PRISMARINE_BRICKS;
//
//		case DARK_PRISMARINE_STAIRS:
//		case DARK_PRISMARINE:
//		case DARK_PRISMARINE_SLAB:
//			return Material.DARK_PRISMARINE;
//
//		case PURPUR_STAIRS:
//		case PURPUR_BLOCK:
//		case PURPUR_PILLAR:
//		case PURPUR_SLAB:
//			return Material.PURPUR_STAIRS;
//
//		case NETHERRACK:
//		case NETHER_BRICK_STAIRS:
//		case NETHER_BRICK_FENCE:
//		case NETHER_BRICK_SLAB:
//		case NETHER_BRICKS:
//		case RED_NETHER_BRICKS:
//			return Material.NETHER_BRICKS;
//
//		case RED_SANDSTONE_STAIRS:
//		case CHISELED_RED_SANDSTONE:
//		case CUT_RED_SANDSTONE:
//		case RED_SANDSTONE:
//		case RED_SANDSTONE_SLAB:
//		case SMOOTH_RED_SANDSTONE:
//			return Material.RED_SANDSTONE;
//
//		case STONE_BRICK_STAIRS:
//		case CHISELED_STONE_BRICKS:
//		case CRACKED_STONE_BRICKS:
//		case END_STONE_BRICKS:
//		case INFESTED_CHISELED_STONE_BRICKS:
//		case INFESTED_CRACKED_STONE_BRICKS:
//		case INFESTED_MOSSY_STONE_BRICKS:
//		case INFESTED_STONE_BRICKS:
//		case MOSSY_STONE_BRICKS:
//		case STONE_BRICK_SLAB:
//		case STONE_BRICKS:
//			return Material.STONE_BRICKS;
//
//		case QUARTZ_STAIRS:
//		case CHISELED_QUARTZ_BLOCK:
//		case NETHER_QUARTZ_ORE:
//		case QUARTZ_BLOCK:
//		case QUARTZ_PILLAR:
//		case QUARTZ_SLAB:
//		case SMOOTH_QUARTZ:
//			return Material.QUARTZ_BLOCK;
//
//		case SANDSTONE_STAIRS:
//		case CHISELED_SANDSTONE:
//		case CUT_SANDSTONE:
//		case SANDSTONE:
//		case SANDSTONE_SLAB:
//		case SMOOTH_SANDSTONE:
//			return Material.SANDSTONE;
//		}
//	}
//
//	public static final Material getDoorsFor(Material material) {
//		switch (material) {
//		default:
//			return Material.BIRCH_DOOR;
//
//		case ACACIA_STAIRS:
//		case ACACIA_FENCE:
//		case ACACIA_LOG:
//		case ACACIA_PLANKS:
//		case ACACIA_SLAB:
//		case ACACIA_WOOD:
//		case STRIPPED_ACACIA_LOG:
//		case STRIPPED_ACACIA_WOOD:
//			return Material.ACACIA_DOOR;
//
//		case BIRCH_STAIRS:
//		case BIRCH_FENCE:
//		case BIRCH_LOG:
//		case BIRCH_PLANKS:
//		case BIRCH_SLAB:
//		case BIRCH_WOOD:
//		case STRIPPED_BIRCH_LOG:
//		case STRIPPED_BIRCH_WOOD:
//			return Material.BIRCH_DOOR;
//
//		case DARK_OAK_STAIRS:
//		case DARK_OAK_FENCE:
//		case DARK_OAK_LOG:
//		case DARK_OAK_PLANKS:
//		case DARK_OAK_SLAB:
//		case DARK_OAK_WOOD:
//		case STRIPPED_DARK_OAK_LOG:
//		case STRIPPED_DARK_OAK_WOOD:
//			return Material.DARK_OAK_DOOR;
//
//		case JUNGLE_STAIRS:
//		case JUNGLE_FENCE:
//		case JUNGLE_LOG:
//		case JUNGLE_PLANKS:
//		case JUNGLE_SLAB:
//		case JUNGLE_WOOD:
//		case STRIPPED_JUNGLE_LOG:
//		case STRIPPED_JUNGLE_WOOD:
//			return Material.JUNGLE_DOOR;
//
//		case OAK_STAIRS:
//		case OAK_FENCE:
//		case OAK_LOG:
//		case OAK_PLANKS:
//		case OAK_SLAB:
//		case OAK_WOOD:
//		case STRIPPED_OAK_LOG:
//		case STRIPPED_OAK_WOOD:
//			return Material.OAK_DOOR;
//
//		case SPRUCE_STAIRS:
//		case SPRUCE_FENCE:
//		case SPRUCE_LOG:
//		case SPRUCE_PLANKS:
//		case SPRUCE_SLAB:
//		case SPRUCE_WOOD:
//		case STRIPPED_SPRUCE_LOG:
//		case STRIPPED_SPRUCE_WOOD:
//			return Material.SPRUCE_DOOR;
//		}
//	}
//
//	public static final Material getColumnFor(Material material) {
//		switch (material) {
//		default:
//			return Material.COBBLESTONE_WALL;
//
//		case ACACIA_STAIRS:
//		case ACACIA_FENCE:
//		case ACACIA_LOG:
//		case ACACIA_PLANKS:
//		case ACACIA_SLAB:
//		case ACACIA_WOOD:
//		case STRIPPED_ACACIA_LOG:
//		case STRIPPED_ACACIA_WOOD:
//			return Material.ACACIA_FENCE;
//
//		case BIRCH_STAIRS:
//		case BIRCH_FENCE:
//		case BIRCH_LOG:
//		case BIRCH_PLANKS:
//		case BIRCH_SLAB:
//		case BIRCH_WOOD:
//		case STRIPPED_BIRCH_LOG:
//		case STRIPPED_BIRCH_WOOD:
//			return Material.BIRCH_FENCE;
//
//		case DARK_OAK_STAIRS:
//		case DARK_OAK_FENCE:
//		case DARK_OAK_LOG:
//		case DARK_OAK_PLANKS:
//		case DARK_OAK_SLAB:
//		case DARK_OAK_WOOD:
//		case STRIPPED_DARK_OAK_LOG:
//		case STRIPPED_DARK_OAK_WOOD:
//			return Material.DARK_OAK_FENCE;
//
//		case JUNGLE_STAIRS:
//		case JUNGLE_FENCE:
//		case JUNGLE_LOG:
//		case JUNGLE_PLANKS:
//		case JUNGLE_SLAB:
//		case JUNGLE_WOOD:
//		case STRIPPED_JUNGLE_LOG:
//		case STRIPPED_JUNGLE_WOOD:
//			return Material.JUNGLE_FENCE;
//
//		case OAK_STAIRS:
//		case OAK_FENCE:
//		case OAK_LOG:
//		case OAK_PLANKS:
//		case OAK_SLAB:
//		case OAK_WOOD:
//		case STRIPPED_OAK_LOG:
//		case STRIPPED_OAK_WOOD:
//			return Material.OAK_FENCE;
//
//		case SPRUCE_STAIRS:
//		case SPRUCE_FENCE:
//		case SPRUCE_LOG:
//		case SPRUCE_PLANKS:
//		case SPRUCE_SLAB:
//		case SPRUCE_WOOD:
//		case STRIPPED_SPRUCE_LOG:
//		case STRIPPED_SPRUCE_WOOD:
//			return Material.SPRUCE_FENCE;
//
//		case COBBLESTONE_STAIRS:
//		case COBBLESTONE_SLAB:
//		case COBBLESTONE_WALL:
//		case MOSSY_COBBLESTONE_WALL:
//		case COBBLESTONE:
//		case INFESTED_COBBLESTONE:
//		case MOSSY_COBBLESTONE:
//			return Material.COBBLESTONE_WALL;
//
//		case PURPUR_STAIRS:
//		case PURPUR_BLOCK:
//		case PURPUR_PILLAR:
//		case PURPUR_SLAB:
//			return Material.PURPUR_PILLAR;
//
//		case NETHERRACK:
//		case NETHER_BRICK_STAIRS:
//		case NETHER_BRICK_FENCE:
//		case NETHER_BRICK_SLAB:
//		case NETHER_BRICKS:
//		case RED_NETHER_BRICKS:
//			return Material.NETHER_BRICK_FENCE;
//
//		case QUARTZ_STAIRS:
//		case CHISELED_QUARTZ_BLOCK:
//		case NETHER_QUARTZ_ORE:
//		case QUARTZ_BLOCK:
//		case QUARTZ_PILLAR:
//		case QUARTZ_SLAB:
//		case SMOOTH_QUARTZ:
//			return Material.QUARTZ_PILLAR;
//		}
//	}
	
}
