package me.daddychurchill.CityWorld.Support;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.material.MaterialData;

public enum RealMaterial{

	GRANITE(Material.STONE, 1),
	GRANITE_POLISHED(Material.STONE, 2),
	DIORITE(Material.STONE, 3),
	DIORIDE_POLISHED(Material.STONE, 4),
	ANDESITE(Material.STONE, 5),
	ANDESIDE_POLISHED(Material.STONE, 6),
	
	DIRT_COARSE(Material.DIRT, 1),
	PODZOL(Material.DIRT, 2),
	
	SAND_RED(Material.SAND, 1),

	WOOD_OAK(Material.WOOD, TreeSpecies.GENERIC),
	WOOD_REDWOOD(Material.WOOD, TreeSpecies.REDWOOD),
	WOOD_BIRCH(Material.WOOD, TreeSpecies.BIRCH),
	WOOD_JUNGLE(Material.WOOD, TreeSpecies.JUNGLE),
	WOOD_ACACIA(Material.WOOD, TreeSpecies.ACACIA),
	WOOD_DARK_OAK(Material.WOOD, TreeSpecies.DARK_OAK),

	LEAVES_OAK(Material.LEAVES, TreeSpecies.GENERIC),
	LEAVES_REDWOOD(Material.LEAVES, TreeSpecies.REDWOOD),
	LEAVES_BIRCH(Material.LEAVES, TreeSpecies.BIRCH),
	LEAVES_JUNGLE(Material.LEAVES, TreeSpecies.JUNGLE),
	LEAVES_ACACIA(Material.LEAVES, TreeSpecies.ACACIA),
	LEAVES_DARK_OAK(Material.LEAVES, TreeSpecies.DARK_OAK),

	LOG_OAK(Material.LOG, TreeSpecies.GENERIC),
	LOG_REDWOOD(Material.LOG, TreeSpecies.REDWOOD),
	LOG_BIRCH(Material.LOG, TreeSpecies.BIRCH),
	LOG_JUNGLE(Material.LOG, TreeSpecies.JUNGLE),
	LOG_ACACIA(Material.LOG, TreeSpecies.ACACIA),
	LOG_DARK_OAK(Material.LOG, TreeSpecies.DARK_OAK),

	WOOD_STEP_OAK(Material.WOOD_STEP, TreeSpecies.GENERIC),
	WOOD_STEP_REDWOOD(Material.WOOD_STEP, TreeSpecies.REDWOOD),
	WOOD_STEP_BIRCH(Material.WOOD_STEP, TreeSpecies.BIRCH),
	WOOD_STEP_JUNGLE(Material.WOOD_STEP, TreeSpecies.JUNGLE),
	WOOD_STEP_ACACIA(Material.WOOD_STEP, TreeSpecies.ACACIA),
	WOOD_STEP_DARK_OAK(Material.WOOD_STEP, TreeSpecies.DARK_OAK),

	WOOD_DOUBLE_STEP_OAK(Material.WOOD_DOUBLE_STEP, TreeSpecies.GENERIC),
	WOOD_DOUBLE_STEP_REDWOOD(Material.WOOD_DOUBLE_STEP, TreeSpecies.REDWOOD),
	WOOD_DOUBLE_STEP_BIRCH(Material.WOOD_DOUBLE_STEP, TreeSpecies.BIRCH),
	WOOD_DOUBLE_STEP_JUNGLE(Material.WOOD_DOUBLE_STEP, TreeSpecies.JUNGLE),
	WOOD_DOUBLE_STEP_ACACIA(Material.WOOD_DOUBLE_STEP, TreeSpecies.ACACIA),
	WOOD_DOUBLE_STEP_DARK_OAK(Material.WOOD_DOUBLE_STEP, TreeSpecies.DARK_OAK),

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
	
	STAINED_CLAY_WHITE(Material.STAINED_CLAY ,DyeColor.WHITE),
	STAINED_CLAY_ORANGE(Material.STAINED_CLAY ,DyeColor.ORANGE),
	STAINED_CLAY_MAGENTA(Material.STAINED_CLAY ,DyeColor.MAGENTA),
	STAINED_CLAY_LIGHT_BLUE(Material.STAINED_CLAY ,DyeColor.LIGHT_BLUE),
	STAINED_CLAY_YELLOW(Material.STAINED_CLAY ,DyeColor.YELLOW),
	STAINED_CLAY_LIME(Material.STAINED_CLAY ,DyeColor.LIME),
	STAINED_CLAY_PINK(Material.STAINED_CLAY ,DyeColor.PINK),
	STAINED_CLAY_GRAY(Material.STAINED_CLAY ,DyeColor.GRAY),
	STAINED_CLAY_SILVER(Material.STAINED_CLAY ,DyeColor.SILVER),
	STAINED_CLAY_CYAN(Material.STAINED_CLAY ,DyeColor.CYAN),
	STAINED_CLAY_PURPLE(Material.STAINED_CLAY ,DyeColor.PURPLE),
	STAINED_CLAY_BLUE(Material.STAINED_CLAY ,DyeColor.BLUE),
	STAINED_CLAY_BROWN(Material.STAINED_CLAY ,DyeColor.BROWN),
	STAINED_CLAY_GREEN(Material.STAINED_CLAY ,DyeColor.GREEN),
	STAINED_CLAY_RED(Material.STAINED_CLAY ,DyeColor.RED),
	STAINED_CLAY_BLACK(Material.STAINED_CLAY ,DyeColor.BLACK),
	
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
	
	SANDSTONE_CHISELED(Material.RED_SANDSTONE, 1),
	SANDSTONE_SMOOTH(Material.RED_SANDSTONE, 2),

	SANDSTONE_RED_CHISELED(Material.RED_SANDSTONE, 1),
	SANDSTONE_RED_SMOOTH(Material.RED_SANDSTONE, 2),
	
	SMOOTH_BRICK_MOSSY(Material.SMOOTH_BRICK, 1),
	SMOOTH_BRICK_CRACKED(Material.SMOOTH_BRICK, 2),
	SMOOTH_BRICK_CHISELED(Material.SMOOTH_BRICK, 3),
	
	PRISMARINE_BRICKS(Material.PRISMARINE, 1),
	PRISMARINE_DARK(Material.PRISMARINE, 2),
	
	SPONGE_WET(Material.SPONGE, 1),
	
	COBBLE_WALL_MOSSY(Material.COBBLE_WALL, 1),
	
	QUARTZ_CHISELED(Material.QUARTZ, 1),
	QUARTZ_PILLAR(Material.QUARTZ, 2),
	QUARTZ_PILLAR_NS(Material.QUARTZ, 3),
	QUARTZ_PILLAR_EW(Material.QUARTZ, 4),
	
	EOL(Material.AIR);
	
	private final MaterialData data;
	
	private RealMaterial(Material material) {
		this.data = new MaterialData(material);
	}
	
	private RealMaterial(MaterialData data) {
		this.data = data;
	}
	
	@SuppressWarnings("deprecation")
	private RealMaterial(Material material, int data) {
		this.data = new MaterialData(material, (byte)data);
	}
	
	@SuppressWarnings("deprecation")
	private RealMaterial(Material material, DyeColor color) {
		this.data = new MaterialData(material, (byte)color.getData());
	}
	
	@SuppressWarnings("deprecation")
	private RealMaterial(Material material, TreeSpecies species) {
		switch (material) {
		case WOOD:
			switch (species) {
			case GENERIC:
				this.data = new MaterialData(Material.WOOD, (byte)0);
				break;
			case REDWOOD:
				this.data = new MaterialData(Material.WOOD, (byte)1);
				break;
			case BIRCH:
				this.data = new MaterialData(Material.WOOD, (byte)2);
				break;
			case JUNGLE:
				this.data = new MaterialData(Material.WOOD, (byte)3);
				break;
			case ACACIA:
				this.data = new MaterialData(Material.WOOD, (byte)4);
				break;
			case DARK_OAK:
				this.data = new MaterialData(Material.WOOD, (byte)5);
				break;
			default:
				this.data = null;
				break;
			}
			break;
		case LOG:
		case LOG_2:
			switch (species) {
			case GENERIC:
				this.data = new MaterialData(Material.LOG, (byte)0);
				break;
			case REDWOOD:
				this.data = new MaterialData(Material.LOG, (byte)1);
				break;
			case BIRCH:
				this.data = new MaterialData(Material.LOG, (byte)2);
				break;
			case JUNGLE:
				this.data = new MaterialData(Material.LOG, (byte)3);
				break;
			case ACACIA:
				this.data = new MaterialData(Material.LOG_2, (byte)0);
				break;
			case DARK_OAK:
				this.data = new MaterialData(Material.LOG_2, (byte)1);
				break;
			default:
				this.data = null;
				break;
			}
			break;
		case LEAVES:
		case LEAVES_2:
			switch (species) {
			case GENERIC:
				this.data = new MaterialData(Material.LEAVES, (byte)0);
				break;
			case REDWOOD:
				this.data = new MaterialData(Material.LEAVES, (byte)1);
				break;
			case BIRCH:
				this.data = new MaterialData(Material.LEAVES, (byte)2);
				break;
			case JUNGLE:
				this.data = new MaterialData(Material.LEAVES, (byte)3);
				break;
			case ACACIA:
				this.data = new MaterialData(Material.LEAVES_2, (byte)0);
				break;
			case DARK_OAK:
				this.data = new MaterialData(Material.LEAVES_2, (byte)1);
				break;
			default:
				this.data = null;
				break;
			}
			break;
		case WOOD_STEP:
		case STEP: // this will be converted to the above
			switch (species) {
			case GENERIC:
				this.data = new MaterialData(Material.WOOD_STEP, (byte)0);
				break;
			case REDWOOD:
				this.data = new MaterialData(Material.WOOD_STEP, (byte)1);
				break;
			case BIRCH:
				this.data = new MaterialData(Material.WOOD_STEP, (byte)2);
				break;
			case JUNGLE:
				this.data = new MaterialData(Material.WOOD_STEP, (byte)3);
				break;
			case ACACIA:
				this.data = new MaterialData(Material.WOOD_STEP, (byte)4);
				break;
			case DARK_OAK:
				this.data = new MaterialData(Material.WOOD_STEP, (byte)5);
				break;
			default:
				this.data = null;
				break;
			}
			break;
		case WOOD_DOUBLE_STEP:
		case DOUBLE_STEP: // this will be converted to the above
			switch (species) {
			case GENERIC:
				this.data = new MaterialData(Material.WOOD_DOUBLE_STEP, (byte)0);
				break;
			case REDWOOD:
				this.data = new MaterialData(Material.WOOD_DOUBLE_STEP, (byte)1);
				break;
			case BIRCH:
				this.data = new MaterialData(Material.WOOD_DOUBLE_STEP, (byte)2);
				break;
			case JUNGLE:
				this.data = new MaterialData(Material.WOOD_DOUBLE_STEP, (byte)3);
				break;
			case ACACIA:
				this.data = new MaterialData(Material.WOOD_DOUBLE_STEP, (byte)4);
				break;
			case DARK_OAK:
				this.data = new MaterialData(Material.WOOD_DOUBLE_STEP, (byte)5);
				break;
			default:
				this.data = null;
				break;
			}
			break;
		default:
			this.data = null;
		    throw new IllegalArgumentException("Invalid block type for tree species");
		}
	}
	
	public final MaterialData getData() {
		return this.data;
	}
	
	public final MaterialData getData(String name) {
		return null;
	}
}
