package me.daddychurchill.CityWorld.Support;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MaterialStack {

	public String name;
	private ItemStack[] items;
	
	public MaterialStack(String name) {
		super();
		this.name = name;
	}

	public MaterialStack(String name, Material ... items) {
		super();
		this.name = name;
		this.items = new ItemStack[items.length];
		for (int i = 0; i < items.length; i++)
			this.items[i] = new ItemStack(items[i]);
	}
	
	public MaterialStack(String name, Material low, Material high) {
		super();
		this.name = name;
		int range = high.getId() - low.getId();
		this.items = new ItemStack[range];
		for (int i = 0; i < range; i++)
			this.items[i] = new ItemStack(low.getId() + i);
	}
	
	private int count() {
		return items == null ? 0 : items.length;
	}
	
	public Material getRandomMaterial(Odds odds) {
		return items == null ? Material.AIR : items[odds.getRandomInt(count())].getType();
	}

	public Byte getRandomTypeId(Odds odds) {
		return (byte) (items == null ? Material.AIR.getId() : items[odds.getRandomInt(count())].getTypeId());
	}
}
