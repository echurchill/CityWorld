package me.daddychurchill.CityWorld.Support;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MaterialList {

	public String name;
	private List<ItemStack> items;
	
	public MaterialList(String name) {
		super();
		this.name = name;
	}

	public MaterialList(String name, Material ... materials) {
		super();
		this.name = name;
		set(materials);
	}
	
	private void init(boolean clear) {
		if (items == null)
			items = new ArrayList<ItemStack>();
		else if (clear)
			items.clear();
	}
	
	public void set(Material ... materials) {
		init(true);
		for (int i = 0; i < materials.length; i++)
			items.add(new ItemStack(materials[i]));
	}
	
	public void add(Material ... materials) {
		init(false);
		for (int i = 0; i < materials.length; i++)
			items.add(new ItemStack(materials[i]));
	}
	
	public void add(Material material) {
		init(false);
		items.add(new ItemStack(material));
	}
	
	public void remove(Material material) {
		if (items != null)
			for (int i = items.size() - 1; i >= 0; i--)
				if (items.get(i).getType() == material)
					items.remove(i);
	}
	
	public int count() {
		return items == null ? 0 : items.size();
	}
	
	public Material getRandomMaterial(Odds odds) {
		return items == null ? Material.AIR : items.get(odds.getRandomInt(count())).getType();
	}
}
