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
	
	@Deprecated
	public int getRandomByte() {
		return (byte) random.nextInt();
	}
	
	@Deprecated
	public byte getRandomByte(byte range) {
		return (byte) random.nextInt(range);
	}
	
	@Deprecated
	public byte getRandomByte(byte min, byte range) {
		return (byte) (min + random.nextInt(range));
	}
	
	private DyeColor getRandomColor(int start, int count) {
		switch (getRandomInt(start, count)) {
		case 0:
			return DyeColor.CYAN;
		case 1:
			return DyeColor.LIGHT_BLUE;
		case 2:
			return DyeColor.LIME;
		case 3:
			return DyeColor.PINK;
		case 4:
			return DyeColor.SILVER;
		case 5:
			return DyeColor.WHITE;
		case 6:
			return DyeColor.YELLOW;
		case 7:
			return DyeColor.BLACK;
		case 8:
			return DyeColor.BLUE;
		case 9:
			return DyeColor.BROWN;
		case 10:
			return DyeColor.GRAY;
		case 11:
			return DyeColor.GREEN;
		case 12:
			return DyeColor.MAGENTA;
		case 13:
			return DyeColor.ORANGE;
		case 14:
			return DyeColor.PURPLE;
		default:
			return DyeColor.RED;
		}
	}
	
	public DyeColor getRandomColor() {
		return getRandomColor(0, 16);
	}
	
	public DyeColor getRandomLightColor() {
		return getRandomColor(0, 7);
	}
	
	public DyeColor getRandomDarkColor() {
		return getRandomColor(7, 9);
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
