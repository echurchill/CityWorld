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
	
//	public MaterialStack(String name, Material low, Material high) {
//		super();
//		this.name = name;
//		int range = ByteChunk.getMaterialId(high) - ByteChunk.getMaterialId(low);
//		this.items = new ItemStack[range];
//		for (int i = 0; i < range; i++)
//			this.items[i] = new ItemStack(ByteChunk.getMaterialId(low) + i);
//	}
	
	private int count() {
		return items == null ? 0 : items.length;
	}
	
	public Material getRandomMaterial(Odds odds) {
		return items == null ? Material.AIR : items[odds.getRandomInt(count())].getType();
	}

	public Byte getRandomTypeId(Odds odds) {
		return (byte) (items == null ? BlackMagic.getMaterialId(Material.AIR) : 
									   BlackMagic.getMaterialId(items[odds.getRandomInt(count())].getType()));
	}
}
