package me.daddychurchill.CityWorld.Support;

import java.util.Random;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.BlockFace;

public class Odds {

	// see PlatMaps.xlsx for more info on this Fibonacci variant
	public final static double oddsAlwaysGoingToHappen =		 1.0;          // 100.00%
	public final static double oddsNearlyAlwaysGoingToHappen =	88.0 /  89.00; //  98.88%
	public final static double oddsEffinLikely =				54.0 /  55.00; //  98.18%
	public final static double oddsTremendouslyLikely =			33.0 /  34.00; //  97.06%
	public final static double oddsExceedinglyLikely =			20.0 /  21.00; //  95.24%
	public final static double oddsEnormouslyLikely =			12.0 /  13.00; //  92.31%
	public final static double oddsExtremelyLikely =			 7.0 /   8.00; //  87.50%
	public final static double oddsPrettyLikely =				 4.0 /   5.00; //  80.00%
	public final static double oddsVeryLikely =					 2.0 /   3.00; //  66.67%
	public final static double oddsLikely =						 1.0 /   2.00; //  50.00%
	public final static double oddsSomewhatLikely =				 1.0 /   3.00; //  33.33%
	public final static double oddsSomewhatUnlikely =			 1.0 /   5.00; //  20.00%
	public final static double oddsUnlikely =					 1.0 /   8.00; //  12.50%
	public final static double oddsVeryUnlikely =				 1.0 /  13.00; //   7.69%
	public final static double oddsPrettyUnlikely =				 1.0 /  21.00; //   4.76%
	public final static double oddsExtremelyUnlikely =			 1.0 /  34.00; //   2.94%
	public final static double oddsEnormouslyUnlikely =			 1.0 /  55.00; //   1.82%
	public final static double oddsExceedinglyUnlikely =		 1.0 /  89.00; //   1.12%
	public final static double oddsTremendouslyUnlikely =		 1.0 / 144.00; //   0.69%
	public final static double oddsEffinUnlikely =				 1.0 / 233.00; //   0.43%
	public final static double oddsNearlyNeverGoingToHappen =	 1.0 / 377.00; //   0.27%
	public final static double oddsNeverGoingToHappen =			 0.0;          //   0.00%
	
	public final static double oddsThricedSomewhatUnlikely = 	oddsSomewhatUnlikely * 3; // 60.0%
	public final static double oddsHalvedPrettyLikely = 		oddsPrettyLikely / 2;     // 40.0%
	
	public enum ColorSet {ALL, GREEN, WHITE, TAN, PINK, NETHER, THEEND, DARK, LIGHT};
	
	public Odds() {
		super();
		random = new Random();
	}

	public Odds(long seed) {
		super();
		random = new Random(seed);
	}
	
	private Random random;
	
	public boolean playOdds(double chances) {
		return random.nextDouble() < chances;
	}
	
	public boolean flipCoin() {
		return random.nextBoolean();
	}
	
	public int rollDice() {
		return random.nextInt(6);
	}
	
	public boolean rollDice(int want) {
		return rollDice() == want;
	}
	
	public double getRandomDouble() {
		return random.nextDouble();
	}
	
	public int getRandomInt() {
		return random.nextInt();
	}
	
	public int getRandomInt(int range) {
		return random.nextInt(range);
	}
	
	public int getRandomInt(int min, int range) {
		return min + random.nextInt(range);
	}
	
	public int calcRandomRange(int min, int max) {
		return min + random.nextInt(max - min + 1);
	}
	
	public double calcRandomRange(double min, double max) {
		return min + random.nextDouble() * (max - min + 1);
	}
	
	public int getShimmy() {
		return getShimmy(1);
	}
	
	public int getShimmy(int max) {
		return -max + random.nextInt(max * 2 + 1);
	}
	
	public Material getRandomMaterial(Material ... items) {
		return items[getRandomInt(items.length)];
	}
	
	private DyeColor getRandomColor(DyeColor... colors) {
		return colors[getRandomInt(colors.length)];
	}
	
	public ColorSet getRandomColorSet() {
		ColorSet[] values = ColorSet.values();
		return values[getRandomInt(values.length)];
	}
	
	public DyeColor getRandomColor() {
		return getRandomColor(ColorSet.ALL);
	}
	
	public DyeColor getRandomLightColor() {
		return getRandomColor(ColorSet.LIGHT);
	}
	
	public DyeColor getRandomDarkColor() {
		return getRandomColor(ColorSet.DARK);
	}
	
	public DyeColor getRandomCamoColor() {
		return getRandomColor(ColorSet.GREEN);
	}
	
	public DyeColor getRandomColor(ColorSet set) {
		switch (set) {
		case ALL:
		default:
			return getRandomColor(
					DyeColor.WHITE, DyeColor.ORANGE, DyeColor.MAGENTA, DyeColor.LIGHT_BLUE,
					DyeColor.YELLOW, DyeColor.LIME, DyeColor.PINK, DyeColor.GRAY, 
					DyeColor.SILVER, DyeColor.CYAN, DyeColor.PURPLE, DyeColor.BLUE,
					DyeColor.BROWN, DyeColor.GREEN, DyeColor.RED, DyeColor.BLACK);
		case GREEN:
			return getRandomColor(
					DyeColor.BROWN, DyeColor.GREEN, DyeColor.GRAY);
		case DARK:
			return getRandomColor(
					DyeColor.GRAY, DyeColor.CYAN, DyeColor.PURPLE, DyeColor.BLUE,
					DyeColor.BROWN, DyeColor.GREEN, DyeColor.RED, DyeColor.BLACK);
		case LIGHT:
			return getRandomColor(
					DyeColor.WHITE, DyeColor.ORANGE, DyeColor.MAGENTA, DyeColor.LIGHT_BLUE,
					DyeColor.YELLOW, DyeColor.LIME, DyeColor.PINK, DyeColor.SILVER);
		case NETHER:
			return getRandomColor(
					DyeColor.RED, DyeColor.BROWN, DyeColor.PURPLE, DyeColor.BLACK, DyeColor.GRAY);
		case TAN:
			return getRandomColor(
					DyeColor.ORANGE, DyeColor.YELLOW);
		case PINK:
			return getRandomColor(
					DyeColor.PINK, DyeColor.SILVER, DyeColor.RED);
		case WHITE:
			return getRandomColor(
					DyeColor.WHITE, DyeColor.SILVER);
		case THEEND:
			return getRandomColor(
					DyeColor.WHITE, DyeColor.SILVER, DyeColor.PINK);
		}
	}
	
	public int getCauldronLevel() {
		return getRandomInt(BlackMagic.maxCauldronLevel + 1);
	}
	
	public int getRandomWoodType() {
		return getRandomInt(4);
	}
	
	public TreeSpecies getRandomWoodSpecies() {
		TreeSpecies[] values = TreeSpecies.values();
		return values[getRandomInt(values.length)];
	}
	
	public Material getRandomWoodenDoorType() {
		switch (getRandomInt(6)) {
		case 1:
			return Material.ACACIA_DOOR;
		case 2:
			return Material.BIRCH_DOOR;
		case 3:
			return Material.DARK_OAK_DOOR;
		case 4:
			return Material.JUNGLE_DOOR;
		case 5:
			return Material.SPRUCE_DOOR;
		case 0:
		default:
			return Material.WOODEN_DOOR;
		}
	}
	
	public int getRandomNetherWartGrowth() {
		return getRandomInt(4);
	}
	
	public long getRandomLong() {
		return random.nextLong();
	}
	
	public BlockFace getRandomFacing() {
		switch (random.nextInt(4)) {
		case 0:
			return BlockFace.SOUTH;
		case 1:
			return BlockFace.WEST;
		case 2:
			return BlockFace.NORTH;
		default:
			return BlockFace.EAST;
		}
	}
}
