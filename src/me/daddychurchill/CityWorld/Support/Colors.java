package me.daddychurchill.CityWorld.Support;

import org.bukkit.DyeColor;
import org.bukkit.Material;

public final class Colors {

	public enum ColorSet {
		ALL, GREEN, WHITE, TAN, PINK, NETHER, THEEND, DARK, LIGHT, RANDOM
	}

	private static final DyeColor[] setAll = { DyeColor.WHITE, DyeColor.ORANGE, DyeColor.MAGENTA, DyeColor.LIGHT_BLUE,
			DyeColor.YELLOW, DyeColor.LIME, DyeColor.PINK, DyeColor.GRAY, DyeColor.LIGHT_GRAY, DyeColor.CYAN,
			DyeColor.PURPLE, DyeColor.BLUE, DyeColor.BROWN, DyeColor.GREEN, DyeColor.RED, DyeColor.BLACK };

	private static final DyeColor[] setGreen = { DyeColor.BROWN, DyeColor.GREEN, DyeColor.GRAY };

	private static final DyeColor[] setDark = { DyeColor.GRAY, DyeColor.CYAN, DyeColor.PURPLE, DyeColor.BLUE, DyeColor.BROWN,
			DyeColor.GREEN, DyeColor.RED, DyeColor.BLACK };

	private static final DyeColor[] setLight = { DyeColor.WHITE, DyeColor.ORANGE, DyeColor.MAGENTA, DyeColor.LIGHT_BLUE,
			DyeColor.YELLOW, DyeColor.LIME, DyeColor.PINK, DyeColor.LIGHT_GRAY };

	private static final DyeColor[] setNether = { DyeColor.RED, DyeColor.BROWN, DyeColor.PURPLE, DyeColor.BLACK,
			DyeColor.GRAY };

	private static final DyeColor[] setTan = { DyeColor.ORANGE, DyeColor.YELLOW };

	private static final DyeColor[] setPink = { DyeColor.PINK, DyeColor.LIGHT_GRAY, DyeColor.RED };

	private static final DyeColor[] setWhite = { DyeColor.WHITE, DyeColor.LIGHT_GRAY };

	private static final DyeColor[] setEnd = { DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.PINK };

	private DyeColor[] colors;
	private final Odds odds;

	public Colors(Odds odds) {
		this.odds = odds;
		setColors(setAll);
	}

//	public Colors(Odds odds, DyeColor ... dyeColors) {
//		this.odds = odds;
//		assert(dyeColors.length > 0);
//		setColors(dyeColors);
//	}

	public Colors(Odds odds, ColorSet set) {
		this.odds = odds;
		setColors(set);
	}

	public void setColors(ColorSet set) {

		// do something special for random
		if (set == ColorSet.RANDOM) {
			ColorSet[] all = ColorSet.values();
			set = all[odds.getRandomInt(all.length)];
			if (set == ColorSet.RANDOM)
				set = ColorSet.ALL;
		}

		// now do it
		switch (set) {
		default:
		case ALL:
			setColors(setAll);
			break;
		case DARK:
			setColors(setDark);
			break;
		case GREEN:
			setColors(setGreen);
			break;
		case LIGHT:
			setColors(setLight);
			break;
		case NETHER:
			setColors(setNether);
			break;
		case PINK:
			setColors(setPink);
			break;
		case TAN:
			setColors(setTan);
			break;
		case THEEND:
			setColors(setEnd);
			break;
		case WHITE:
			setColors(setWhite);
			break;
		}
	}

	private void setColors(DyeColor... dyeColors) {
		colors = dyeColors;
	}

	public void fixColor() {
		if (colors.length > 1)
			setColors(getRandomColor());
	}

	public DyeColor getRandomColor() {
		int count = colors.length;
		if (count == 1)
			return colors[0];
		else
			return colors[odds.getRandomInt(count)];
	}

	public Material getGlass() {
		return getGlass(getRandomColor());
	}

	private static Material getGlass(DyeColor color) {
		switch (color) {
		default:
		case BLACK:
			return Material.BLACK_STAINED_GLASS;
		case BLUE:
			return Material.BLUE_STAINED_GLASS;
		case BROWN:
			return Material.BROWN_STAINED_GLASS;
		case CYAN:
			return Material.CYAN_STAINED_GLASS;
		case GRAY:
			return Material.GRAY_STAINED_GLASS;
		case GREEN:
			return Material.GREEN_STAINED_GLASS;
		case LIGHT_BLUE:
			return Material.LIGHT_BLUE_STAINED_GLASS;
		case LIGHT_GRAY:
			return Material.LIGHT_GRAY_STAINED_GLASS;
		case LIME:
			return Material.LIME_STAINED_GLASS;
		case MAGENTA:
			return Material.MAGENTA_STAINED_GLASS;
		case ORANGE:
			return Material.ORANGE_STAINED_GLASS;
		case PINK:
			return Material.PINK_STAINED_GLASS;
		case PURPLE:
			return Material.PURPLE_STAINED_GLASS;
		case RED:
			return Material.RED_STAINED_GLASS;
		case WHITE:
			return Material.WHITE_STAINED_GLASS;
		case YELLOW:
			return Material.YELLOW_STAINED_GLASS;
		}
	}

	public Material getGlassPane() {
		return getGlass(getRandomColor());
	}

	public static Material getGlassPane(DyeColor color) {
		switch (color) {
		default:
		case BLACK:
			return Material.BLACK_STAINED_GLASS_PANE;
		case BLUE:
			return Material.BLUE_STAINED_GLASS_PANE;
		case BROWN:
			return Material.BROWN_STAINED_GLASS_PANE;
		case CYAN:
			return Material.CYAN_STAINED_GLASS_PANE;
		case GRAY:
			return Material.GRAY_STAINED_GLASS_PANE;
		case GREEN:
			return Material.GREEN_STAINED_GLASS_PANE;
		case LIGHT_BLUE:
			return Material.LIGHT_BLUE_STAINED_GLASS_PANE;
		case LIGHT_GRAY:
			return Material.LIGHT_GRAY_STAINED_GLASS_PANE;
		case LIME:
			return Material.LIME_STAINED_GLASS_PANE;
		case MAGENTA:
			return Material.MAGENTA_STAINED_GLASS_PANE;
		case ORANGE:
			return Material.ORANGE_STAINED_GLASS_PANE;
		case PINK:
			return Material.PINK_STAINED_GLASS_PANE;
		case PURPLE:
			return Material.PURPLE_STAINED_GLASS_PANE;
		case RED:
			return Material.RED_STAINED_GLASS_PANE;
		case WHITE:
			return Material.WHITE_STAINED_GLASS_PANE;
		case YELLOW:
			return Material.YELLOW_STAINED_GLASS_PANE;
		}
	}

	public Material getCarpet() {
		return getCarpet(getRandomColor());
	}

	private static Material getCarpet(DyeColor color) {
		switch (color) {
		default:
		case BLACK:
			return Material.BLACK_CARPET;
		case BLUE:
			return Material.BLUE_CARPET;
		case BROWN:
			return Material.BROWN_CARPET;
		case CYAN:
			return Material.CYAN_CARPET;
		case GRAY:
			return Material.GRAY_CARPET;
		case GREEN:
			return Material.GREEN_CARPET;
		case LIGHT_BLUE:
			return Material.LIGHT_BLUE_CARPET;
		case LIGHT_GRAY:
			return Material.LIGHT_GRAY_CARPET;
		case LIME:
			return Material.LIME_CARPET;
		case MAGENTA:
			return Material.MAGENTA_CARPET;
		case ORANGE:
			return Material.ORANGE_CARPET;
		case PINK:
			return Material.PINK_CARPET;
		case PURPLE:
			return Material.PURPLE_CARPET;
		case RED:
			return Material.RED_CARPET;
		case WHITE:
			return Material.WHITE_CARPET;
		case YELLOW:
			return Material.YELLOW_CARPET;
		}
	}

	public Material getWool() {
		return getWool(getRandomColor());
	}

	private static Material getWool(DyeColor color) {
		switch (color) {
		default:
		case BLACK:
			return Material.BLACK_WOOL;
		case BLUE:
			return Material.BLUE_WOOL;
		case BROWN:
			return Material.BROWN_WOOL;
		case CYAN:
			return Material.CYAN_WOOL;
		case GRAY:
			return Material.GRAY_WOOL;
		case GREEN:
			return Material.GREEN_WOOL;
		case LIGHT_BLUE:
			return Material.LIGHT_BLUE_WOOL;
		case LIGHT_GRAY:
			return Material.LIGHT_GRAY_WOOL;
		case LIME:
			return Material.LIME_WOOL;
		case MAGENTA:
			return Material.MAGENTA_WOOL;
		case ORANGE:
			return Material.ORANGE_WOOL;
		case PINK:
			return Material.PINK_WOOL;
		case PURPLE:
			return Material.PURPLE_WOOL;
		case RED:
			return Material.RED_WOOL;
		case WHITE:
			return Material.WHITE_WOOL;
		case YELLOW:
			return Material.YELLOW_WOOL;
		}
	}

	public Material getBed() {
		return getBed(getRandomColor());
	}

	private static Material getBed(DyeColor color) {
		switch (color) {
		default:
		case BLACK:
			return Material.BLACK_BED;
		case BLUE:
			return Material.BLUE_BED;
		case BROWN:
			return Material.BROWN_BED;
		case CYAN:
			return Material.CYAN_BED;
		case GRAY:
			return Material.GRAY_BED;
		case GREEN:
			return Material.GREEN_BED;
		case LIGHT_BLUE:
			return Material.LIGHT_BLUE_BED;
		case LIGHT_GRAY:
			return Material.LIGHT_GRAY_BED;
		case LIME:
			return Material.LIME_BED;
		case MAGENTA:
			return Material.MAGENTA_BED;
		case ORANGE:
			return Material.ORANGE_BED;
		case PINK:
			return Material.PINK_BED;
		case PURPLE:
			return Material.PURPLE_BED;
		case RED:
			return Material.RED_BED;
		case WHITE:
			return Material.WHITE_BED;
		case YELLOW:
			return Material.YELLOW_BED;
		}
	}

	public Material getTerracotta() {
		return getTerracotta(getRandomColor());
	}

	private static Material getTerracotta(DyeColor color) {
		switch (color) {
		default:
		case BLACK:
			return Material.BLACK_TERRACOTTA;
		case BLUE:
			return Material.BLUE_TERRACOTTA;
		case BROWN:
			return Material.BROWN_TERRACOTTA;
		case CYAN:
			return Material.CYAN_TERRACOTTA;
		case GRAY:
			return Material.GRAY_TERRACOTTA;
		case GREEN:
			return Material.GREEN_TERRACOTTA;
		case LIGHT_BLUE:
			return Material.LIGHT_BLUE_TERRACOTTA;
		case LIGHT_GRAY:
			return Material.LIGHT_GRAY_TERRACOTTA;
		case LIME:
			return Material.LIME_TERRACOTTA;
		case MAGENTA:
			return Material.MAGENTA_TERRACOTTA;
		case ORANGE:
			return Material.ORANGE_TERRACOTTA;
		case PINK:
			return Material.PINK_TERRACOTTA;
		case PURPLE:
			return Material.PURPLE_TERRACOTTA;
		case RED:
			return Material.RED_TERRACOTTA;
		case WHITE:
			return Material.WHITE_TERRACOTTA;
		case YELLOW:
			return Material.YELLOW_TERRACOTTA;
		}
	}

	public Material getGlazedTerracotta() {
		return getGlazedTerracotta(getRandomColor());
	}

	private static Material getGlazedTerracotta(DyeColor color) {
		switch (color) {
		default:
		case BLACK:
			return Material.BLACK_GLAZED_TERRACOTTA;
		case BLUE:
			return Material.BLUE_GLAZED_TERRACOTTA;
		case BROWN:
			return Material.BROWN_GLAZED_TERRACOTTA;
		case CYAN:
			return Material.CYAN_GLAZED_TERRACOTTA;
		case GRAY:
			return Material.GRAY_GLAZED_TERRACOTTA;
		case GREEN:
			return Material.GREEN_GLAZED_TERRACOTTA;
		case LIGHT_BLUE:
			return Material.LIGHT_BLUE_GLAZED_TERRACOTTA;
		case LIGHT_GRAY:
			return Material.LIGHT_GRAY_GLAZED_TERRACOTTA;
		case LIME:
			return Material.LIME_GLAZED_TERRACOTTA;
		case MAGENTA:
			return Material.MAGENTA_GLAZED_TERRACOTTA;
		case ORANGE:
			return Material.ORANGE_GLAZED_TERRACOTTA;
		case PINK:
			return Material.PINK_GLAZED_TERRACOTTA;
		case PURPLE:
			return Material.PURPLE_GLAZED_TERRACOTTA;
		case RED:
			return Material.RED_GLAZED_TERRACOTTA;
		case WHITE:
			return Material.WHITE_GLAZED_TERRACOTTA;
		case YELLOW:
			return Material.YELLOW_GLAZED_TERRACOTTA;
		}
	}

	public Material getConcrete() {
		return getConcrete(getRandomColor());
	}

	private static Material getConcrete(DyeColor color) {
		switch (color) {
		default:
		case BLACK:
			return Material.BLACK_CONCRETE;
		case BLUE:
			return Material.BLUE_CONCRETE;
		case BROWN:
			return Material.BROWN_CONCRETE;
		case CYAN:
			return Material.CYAN_CONCRETE;
		case GRAY:
			return Material.GRAY_CONCRETE;
		case GREEN:
			return Material.GREEN_CONCRETE;
		case LIGHT_BLUE:
			return Material.LIGHT_BLUE_CONCRETE;
		case LIGHT_GRAY:
			return Material.LIGHT_GRAY_CONCRETE;
		case LIME:
			return Material.LIME_CONCRETE;
		case MAGENTA:
			return Material.MAGENTA_CONCRETE;
		case ORANGE:
			return Material.ORANGE_CONCRETE;
		case PINK:
			return Material.PINK_CONCRETE;
		case PURPLE:
			return Material.PURPLE_CONCRETE;
		case RED:
			return Material.RED_CONCRETE;
		case WHITE:
			return Material.WHITE_CONCRETE;
		case YELLOW:
			return Material.YELLOW_CONCRETE;
		}
	}

	public Material getConcretePowder() {
		return getConcretePowder(getRandomColor());
	}

	private static Material getConcretePowder(DyeColor color) {
		switch (color) {
		default:
		case BLACK:
			return Material.BLACK_CONCRETE_POWDER;
		case BLUE:
			return Material.BLUE_CONCRETE_POWDER;
		case BROWN:
			return Material.BROWN_CONCRETE_POWDER;
		case CYAN:
			return Material.CYAN_CONCRETE_POWDER;
		case GRAY:
			return Material.GRAY_CONCRETE_POWDER;
		case GREEN:
			return Material.GREEN_CONCRETE_POWDER;
		case LIGHT_BLUE:
			return Material.LIGHT_BLUE_CONCRETE_POWDER;
		case LIGHT_GRAY:
			return Material.LIGHT_GRAY_CONCRETE_POWDER;
		case LIME:
			return Material.LIME_CONCRETE_POWDER;
		case MAGENTA:
			return Material.MAGENTA_CONCRETE_POWDER;
		case ORANGE:
			return Material.ORANGE_CONCRETE_POWDER;
		case PINK:
			return Material.PINK_CONCRETE_POWDER;
		case PURPLE:
			return Material.PURPLE_CONCRETE_POWDER;
		case RED:
			return Material.RED_CONCRETE_POWDER;
		case WHITE:
			return Material.WHITE_CONCRETE_POWDER;
		case YELLOW:
			return Material.YELLOW_CONCRETE_POWDER;
		}
	}
}
