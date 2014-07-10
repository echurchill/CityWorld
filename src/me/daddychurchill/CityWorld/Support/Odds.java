package me.daddychurchill.CityWorld.Support;

import java.util.Random;

import org.bukkit.DyeColor;
import org.bukkit.block.BlockFace;

public class Odds {

	private Random random;
	
	public Odds(long seed) {
		super();
		random = new Random(seed);
	}
	
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
		return min + random.nextInt(max - min);
	}
	
	public double calcRandomRange(double min, double max) {
		return min + random.nextDouble() * (max - min);
	}
	
	private DyeColor getRandomColor(DyeColor... colors) {
		return colors[getRandomInt(colors.length)];
	}
	
	public DyeColor getRandomColor() {
		return getRandomColor(
				DyeColor.WHITE, DyeColor.ORANGE, DyeColor.MAGENTA, DyeColor.LIGHT_BLUE,
				DyeColor.YELLOW, DyeColor.LIME, DyeColor.PINK, DyeColor.GRAY, 
				DyeColor.SILVER, DyeColor.CYAN, DyeColor.PURPLE, DyeColor.BLUE,
				DyeColor.BROWN, DyeColor.GREEN, DyeColor.RED, DyeColor.BLACK);
	}
	
	public DyeColor getRandomLightColor() {
		return getRandomColor(
				DyeColor.WHITE, DyeColor.ORANGE, DyeColor.MAGENTA, DyeColor.LIGHT_BLUE,
				DyeColor.YELLOW, DyeColor.LIME, DyeColor.PINK, DyeColor.SILVER);
	}
	
	public DyeColor getRandomDarkColor() {
		return getRandomColor(
				DyeColor.GRAY, DyeColor.CYAN, DyeColor.PURPLE, DyeColor.BLUE,
				DyeColor.BROWN, DyeColor.GREEN, DyeColor.RED, DyeColor.BLACK);
	}
	
	public DyeColor getRandomCamoColor() {
		return getRandomColor(
				DyeColor.BROWN, DyeColor.GREEN, DyeColor.GRAY);
	}
	
	public int getCauldronLevel() {
		return getRandomInt(BlackMagic.maxCauldronLevel + 1);
	}
	
	public int getRandomWoodType() {
		return getRandomInt(4);
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
