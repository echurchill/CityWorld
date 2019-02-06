package me.daddychurchill.CityWorld.Support;

import java.util.Map;
import java.util.TreeMap;

import org.bukkit.Material;

public final class Mapper {

	public static Material getStairsFor(Material material) {
		MapperEntry entry = getMaterialMapping(material);
		if (entry != null)
			return entry.stairs;
		else
			return Material.STONE_BRICK_STAIRS;
	}

	public static Material getStairWallFor(Material material) {
		MapperEntry entry = getMaterialMapping(material);
		if (entry != null)
			return entry.stairWalls;
		else
			return Material.STONE_BRICKS;
	}

	public static Material getStairPlatformFor(Material material) {
		MapperEntry entry = getMaterialMapping(material);
		if (entry != null)
			return entry.stairPlatform;
		else
			return Material.STONE;
	}

	public static Material getDoorsFor(Material material) {
		MapperEntry entry = getMaterialMapping(material);
		if (entry != null)
			return entry.door;
		else
			return Material.BIRCH_DOOR;
	}

	public static Material getColumnFor(Material material) {
		MapperEntry entry = getMaterialMapping(material);
		if (entry != null)
			return entry.columns;
		else
			return Material.COBBLESTONE_WALL;
	}

	private static class MapperEntry {
		Material columns;
		Material door;
		Material stairPlatform;
		Material stairs;
		Material stairWalls;

		MapperEntry(Material aColumns, Material aDoor, Material aStairPlatform, Material aStairs, Material aStairWalls) {
			assert (aColumns.isBlock());
			assert (aDoor.isBlock());
			assert (aStairPlatform.isBlock());
			assert (aStairs.isBlock());
			assert (aStairWalls.isBlock());
			columns = aColumns;
			door = aDoor;
			stairPlatform = aStairPlatform;
			stairs = aStairs;
			stairWalls = aStairWalls;
		}
	}

	private static final Map<Material, MapperEntry> mapping = new TreeMap<>();

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
					else if (name.contains("STONE"))
						mapping.put(material, new MapperEntry(Material.IRON_BARS, Material.OAK_DOOR, Material.STONE, Material.STONE_BRICK_STAIRS, Material.STONE));
					else if (name.contains("BRICK"))
						mapping.put(material, new MapperEntry(Material.COBBLESTONE_WALL, Material.BIRCH_DOOR, Material.BRICKS, Material.BRICK_STAIRS, Material.BRICKS));
					else //if (name.contains("STONE"))
						mapping.put(material, new MapperEntry(Material.COBBLESTONE_WALL, Material.DARK_OAK_DOOR, Material.STONE, Material.DARK_OAK_STAIRS, Material.STONE));
				}
			}
		}
		return mapping.get(lookup);
	}
}
