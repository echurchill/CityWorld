package me.daddychurchill.CityWorld.Support;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;

public class Trees {
	
	public static Material[] setAllLeaves = {				
			Material.ACACIA_LEAVES, 
			Material.BIRCH_LEAVES, 
			Material.DARK_OAK_LEAVES,
			Material.JUNGLE_LEAVES, 
			Material.SPRUCE_LEAVES, 
			Material.OAK_LEAVES};
	
	private Odds odds;
	public Trees(Odds odds) {
		this.odds = odds;
	}
	
	public TreeSpecies getRandomSpecies() {
		TreeSpecies[] values = TreeSpecies.values();
		return values[odds.getRandomInt(values.length)];
	}
	
	public Material getRandomWoodLog() {
		return getRandomWoodLog(odds.getRandomWoodSpecies());
	}
	
	public static Material getRandomWoodLog(TreeSpecies species) {
		switch (species) {
		default:
		case ACACIA:
			return Material.ACACIA_LOG;
		case BIRCH:
			return Material.BIRCH_LOG;
		case DARK_OAK:
			return Material.DARK_OAK_LOG;
		case GENERIC:
			return Material.OAK_LOG;
		case JUNGLE:
			return Material.JUNGLE_LOG;
		case REDWOOD:
			return Material.SPRUCE_LOG;
		}
	}
	
	public Material getRandomWoodStairs() {
		return getRandomWoodStairs(odds.getRandomWoodSpecies());
	}
	
	public static Material getRandomWoodStairs(TreeSpecies species) {
		switch (species) {
		default:
		case ACACIA:
			return Material.ACACIA_STAIRS;
		case BIRCH:
			return Material.BIRCH_STAIRS;
		case DARK_OAK:
			return Material.DARK_OAK_STAIRS;
		case GENERIC:
			return Material.OAK_STAIRS;
		case JUNGLE:
			return Material.JUNGLE_STAIRS;
		case REDWOOD:
			return Material.SPRUCE_STAIRS;
		}
	}
	
	public Material getRandomWoodSlab() {
		return getRandomWoodSlab(odds.getRandomWoodSpecies());
	}
	
	public static Material getRandomWoodSlab(TreeSpecies species) {
		switch (species) {
		default:
		case ACACIA:
			return Material.ACACIA_SLAB;
		case BIRCH:
			return Material.BIRCH_SLAB;
		case DARK_OAK:
			return Material.DARK_OAK_SLAB;
		case GENERIC:
			return Material.OAK_SLAB;
		case JUNGLE:
			return Material.JUNGLE_SLAB;
		case REDWOOD:
			return Material.SPRUCE_SLAB;
		}
	}
	
	public Material getRandomWoodDoor() {
		return getRandomWoodDoor(odds.getRandomWoodSpecies());
	}
	
	public static Material getRandomWoodDoor(TreeSpecies species) {
		switch (species) {
		default:
		case ACACIA:
			return Material.ACACIA_DOOR;
		case BIRCH:
			return Material.BIRCH_DOOR;
		case DARK_OAK:
			return Material.DARK_OAK_DOOR;
		case GENERIC:
			return Material.OAK_DOOR;
		case JUNGLE:
			return Material.JUNGLE_DOOR;
		case REDWOOD:
			return Material.SPRUCE_DOOR;
		}
	}
	
	public Material getRandomWoodTrapDoor() {
		return getRandomWoodTrapDoor(odds.getRandomWoodSpecies());
	}
	
	public static Material getRandomWoodTrapDoor(TreeSpecies species) {
		switch (species) {
		default:
		case ACACIA:
			return Material.ACACIA_TRAPDOOR;
		case BIRCH:
			return Material.BIRCH_TRAPDOOR;
		case DARK_OAK:
			return Material.DARK_OAK_TRAPDOOR;
		case GENERIC:
			return Material.OAK_TRAPDOOR;
		case JUNGLE:
			return Material.JUNGLE_TRAPDOOR;
		case REDWOOD:
			return Material.SPRUCE_TRAPDOOR;
		}
	}
	
	public Material getRandomWoodLeaves() {
		return getRandomWoodLeaves(odds.getRandomWoodSpecies());
	}
	
	public static Material getRandomWoodLeaves(TreeSpecies species) {
		switch (species) {
		default:
		case ACACIA:
			return Material.ACACIA_LEAVES;
		case BIRCH:
			return Material.BIRCH_LEAVES;
		case DARK_OAK:
			return Material.DARK_OAK_LEAVES;
		case GENERIC:
			return Material.OAK_LEAVES;
		case JUNGLE:
			return Material.JUNGLE_LEAVES;
		case REDWOOD:
			return Material.SPRUCE_LEAVES;
		}
	}
	
	public Material getRandomWoodFence() {
		return getRandomWoodFence(odds.getRandomWoodSpecies());
	}
	
	public static Material getRandomWoodFence(TreeSpecies species) {
		switch (species) {
		default:
		case ACACIA:
			return Material.ACACIA_FENCE;
		case BIRCH:
			return Material.BIRCH_FENCE;
		case DARK_OAK:
			return Material.DARK_OAK_FENCE;
		case GENERIC:
			return Material.OAK_FENCE;
		case JUNGLE:
			return Material.JUNGLE_FENCE;
		case REDWOOD:
			return Material.SPRUCE_FENCE;
		}
	}
	
}
