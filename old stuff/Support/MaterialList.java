package me.daddychurchill.CityWorld.Support;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import me.daddychurchill.CityWorld.CityWorldGenerator;

public class MaterialList {

	public String listName;
	private List<ItemStack> items;
	
	public MaterialList(String name) {
		super();
		listName = name;
	}

	public MaterialList(String name, Material ... materials) {
		super();
		listName = name;
		add(materials);
	}

	private void init(boolean clear) {
		if (items == null)
			items = new ArrayList<ItemStack>();
		else if (clear)
			items.clear();
	}
	
	public void add(Material ... materials) {
		init(false);
		for (Material material : materials) {
			items.add(new ItemStack(material));
		}
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
		return getRandomMaterial(odds, Material.AIR);
	}
	
	public Material getRandomMaterial(Odds odds, Material defaultMaterial) {
		if (items == null || count() == 0)
			return defaultMaterial;
		else
			return items.get(odds.getRandomInt(count())).getType();
	}

	public Material getNthMaterial(int index, Material defaultMaterial) {
		if (items == null || count() == 0 || index > count() - 1)
			return defaultMaterial;
		else
			return items.get(index).getType();
	}

	public void write(CityWorldGenerator generator, ConfigurationSection section) {
		List<String> names = new ArrayList<String>();
		if (items != null) {
			for (ItemStack item : items) {
				names.add(item.getType().name());
			}
		}
		section.set(listName, names);
	}
	
	public void read(CityWorldGenerator generator, ConfigurationSection section) {
		if (section.isList(listName)) {
			init(true);
			List<String> names = section.getStringList(listName);
			for (String name : names) {
				Material material = null;
				try {
					material = Material.matchMaterial(name);

					// still nothing, so comment about it
					if (material == null)
						generator.reportMessage("Ignoring " + generator.worldName + ".Materials." + listName + ": " + name + ", is not known");
				} catch (Exception e) {
					generator.reportException("Reading " + generator.worldName + ".Materials." + listName + ": " + name, e);
					material = null;
				}
				
				if (material != null)
					add(material);
			}
		}
	}
}
