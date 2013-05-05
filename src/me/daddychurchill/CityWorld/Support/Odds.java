package me.daddychurchill.CityWorld.Support;

import java.util.Random;

import me.daddychurchill.CityWorld.Support.RealChunk.Color;

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
	
//	public int getAltitude(int min, int range) {
//		return random.nextInt(range) + min;
//	}
	
//	public int getInt(int min, int range) {
//		return random.nextInt(range) + min;
//	}
	
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
	
	public int getRandomByte() {
		return (byte) random.nextInt();
	}
	
	public byte getRandomByte(byte range) {
		return (byte) random.nextInt(range);
	}
	
	public byte getRandomByte(byte min, byte range) {
		return (byte) (min + random.nextInt(range));
	}
	
	public Color getRandomColor() {
		return Color.fromByte(getRandomByte((byte)16));
	}
	
	public Color getRandomLightColor() {
		return Color.fromByte(getRandomByte((byte)7));
	}
	
	public Color getRandomDarkColor() {
		return Color.fromByte(getRandomByte((byte)7, (byte)9));
	}
	
	public byte getRandomCauldronLevel() {
		return getRandomByte((byte)4);
	}
	
	public byte getRandomWoodType() {
		return getRandomByte((byte)4);
	}
	
	public byte getRandomNetherWartGrowth() {
		return getRandomByte((byte)4);
	}
	
	public long getRandomLong() {
		return random.nextLong();
	}
	
	public Direction.Facing getFacing() {
		switch (random.nextInt(4)) {
		case 0:
			return Direction.Facing.SOUTH;
		case 1:
			return Direction.Facing.WEST;
		case 2:
			return Direction.Facing.NORTH;
		default:
			return Direction.Facing.EAST;
		}
	}
}
