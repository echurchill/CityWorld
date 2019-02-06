package me.daddychurchill.CityWorld.Support;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public final class Odds {

	// see PlatMaps.xlsx for more info on this Fibonacci variant
	public final static double oddsAlwaysGoingToHappen = 1.0; // 100.00%
	public final static double oddsNearlyAlwaysGoingToHappen = 88.0 / 89.00; // 98.88%
	public final static double oddsEffinLikely = 54.0 / 55.00; // 98.18%
	public final static double oddsTremendouslyLikely = 33.0 / 34.00; // 97.06%
	public final static double oddsExceedinglyLikely = 20.0 / 21.00; // 95.24%
	public final static double oddsEnormouslyLikely = 12.0 / 13.00; // 92.31%
	public final static double oddsExtremelyLikely = 7.0 / 8.00; // 87.50%
	public final static double oddsPrettyLikely = 4.0 / 5.00; // 80.00%
	public final static double oddsVeryLikely = 2.0 / 3.00; // 66.67%
	public final static double oddsLikely = 1.0 / 2.00; // 50.00%
	public final static double oddsSomewhatLikely = 1.0 / 3.00; // 33.33%
	public final static double oddsSomewhatUnlikely = 1.0 / 5.00; // 20.00%
	public final static double oddsUnlikely = 1.0 / 8.00; // 12.50%
	public final static double oddsVeryUnlikely = 1.0 / 13.00; // 7.69%
	public final static double oddsPrettyUnlikely = 1.0 / 21.00; // 4.76%
	public final static double oddsExtremelyUnlikely = 1.0 / 34.00; // 2.94%
	public final static double oddsEnormouslyUnlikely = 1.0 / 55.00; // 1.82%
	public final static double oddsExceedinglyUnlikely = 1.0 / 89.00; // 1.12%
	public final static double oddsTremendouslyUnlikely = 1.0 / 144.00; // 0.69%
	public final static double oddsEffinUnlikely = 1.0 / 233.00; // 0.43%
	public final static double oddsNearlyNeverGoingToHappen = 1.0 / 377.00; // 0.27%
	public final static double oddsNeverGoingToHappen = 0.0; // 0.00%

	public final static double oddsMoreLikely = oddsSomewhatUnlikely * 3; // 60.0%
	public final static double oddsLessLikely = oddsSomewhatUnlikely * 2; // 40.0%

	public Odds() {
		super();
		random = new Random();
	}

	public Odds(long seed) {
		super();
		random = new Random(seed);
	}

	private final Random random;

	public boolean playOdds(double chances) {
		return random.nextDouble() < chances;
	}

	public boolean flipCoin() {
		return random.nextBoolean();
	}

	private int rollDice() {
		return random.nextInt(6);
	}

	public boolean rollDice(int want) {
		return rollDice() == want;
	}

	public double getRandomDouble() {
		return random.nextDouble();
	}

	public long getRandomLong() {
		return random.nextLong();
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
		return min + (random.nextDouble() * (max - min + 1));
	}

	public Vector getRandomVelocity() {
		return getRandomVelocity(1.0);
	}

	private Vector getRandomVelocity(double delta) {
		return new Vector((random.nextDouble() * delta * 2) - delta, 0, (random.nextDouble() * delta * 2) - delta);
	}

	public int getShimmy() {
		return getShimmy(1);
	}

	private int getShimmy(int max) {
		return -max + random.nextInt(max * 2 + 1);
	}

	public Material getRandomMaterial(Material... items) {
		return items[getRandomInt(items.length)];
	}

	public TreeSpecies getRandomWoodSpecies() {
		TreeSpecies[] values = TreeSpecies.values();
		return values[getRandomInt(values.length)];
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
