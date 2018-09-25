package me.daddychurchill.CityWorld.Support;

import java.util.Random;

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
	
	public int getShimmy() {
		return getShimmy(1);
	}
	
	public int getShimmy(int max) {
		return -max + random.nextInt(max * 2 + 1);
	}
	
	public Material getRandomMaterial(Material ... items) {
		return items[getRandomInt(items.length)];
	}
	
	public TreeSpecies getRandomWoodSpecies() {
		TreeSpecies[] values = TreeSpecies.values();
		return values[getRandomInt(values.length)];
	}
	
	public Material getRandomWoodLog() {
		return getRandomMaterial(
				Material.ACACIA_LOG, 
				Material.BIRCH_LOG, 
				Material.DARK_OAK_LOG,
				Material.JUNGLE_LOG, 
				Material.SPRUCE_LOG, 
				Material.OAK_LOG);
	}
	
	public Material getRandomWoodSlab() {
		return getRandomMaterial(
				Material.ACACIA_SLAB, 
				Material.BIRCH_SLAB, 
				Material.DARK_OAK_SLAB,
				Material.JUNGLE_SLAB, 
				Material.SPRUCE_SLAB, 
				Material.OAK_SLAB);
	}
	
	public Material getRandomWoodDoor() {
		return getRandomMaterial(
				Material.ACACIA_DOOR, 
				Material.BIRCH_DOOR, 
				Material.DARK_OAK_DOOR,
				Material.JUNGLE_DOOR, 
				Material.SPRUCE_DOOR, 
				Material.OAK_DOOR);
	}
	
	public Material getRandomWoodTrapDoor() {
		return getRandomMaterial(
				Material.ACACIA_TRAPDOOR, 
				Material.BIRCH_TRAPDOOR, 
				Material.DARK_OAK_TRAPDOOR,
				Material.JUNGLE_TRAPDOOR, 
				Material.SPRUCE_TRAPDOOR, 
				Material.OAK_TRAPDOOR);
	}
	
	public Material getRandomWoodLeaves() {
		return getRandomMaterial(
				Material.ACACIA_LEAVES, 
				Material.BIRCH_LEAVES, 
				Material.DARK_OAK_LEAVES,
				Material.JUNGLE_LEAVES, 
				Material.SPRUCE_LEAVES, 
				Material.OAK_LEAVES);
	}
	
	public Material getRandomWoodFence() {
		return getRandomMaterial(
				Material.ACACIA_FENCE, 
				Material.BIRCH_FENCE, 
				Material.DARK_OAK_FENCE,
				Material.JUNGLE_FENCE, 
				Material.SPRUCE_FENCE, 
				Material.OAK_FENCE);
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
