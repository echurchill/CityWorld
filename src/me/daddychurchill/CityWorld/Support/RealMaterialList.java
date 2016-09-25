package me.daddychurchill.CityWorld.Support;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.material.MaterialData;

import me.daddychurchill.CityWorld.CityWorldGenerator;

public class RealMaterialList {

	public String listName;
	private List<RealMaterial> items;
	
	public RealMaterialList(String name) {
		super();
		listName = name;
	}

	public RealMaterialList(String name, RealMaterial ... materials) {
		super();
		listName = name;
		add(materials);
	}

	private void init(boolean clear) {
		if (items == null)
			items = new ArrayList<RealMaterial>();
		else if (clear)
			items.clear();
	}
	
	public void add(RealMaterial ... materials) {
		init(false);
		for (RealMaterial material : materials) {
			items.add(material);
		}
	}
	
	public void add(RealMaterial material) {
		init(false);
		items.add(material);
	}
	
//	public void remove(RealMaterial material) {
//		if (items != null)
//			for (int i = items.size() - 1; i >= 0; i--)
//				if (items.get(i).getType() == material)
//					items.remove(i);
//	}
	
	public int count() {
		return items == null ? 0 : items.size();
	}
	
	public MaterialData getRandomMaterial(Odds odds) {
		return getRandomMaterial(odds, RealMaterial.AIR);
	}
	
	public MaterialData getRandomMaterial(Odds odds, RealMaterial defaultMaterial) {
		if (items == null || count() == 0)
			return defaultMaterial.getData();
		else
			return items.get(odds.getRandomInt(count())).getData();
	}

	public MaterialData getNthMaterial(int index, RealMaterial defaultMaterial) {
		if (items == null || count() == 0 || index > count() - 1)
			return defaultMaterial.getData();
		else
			return items.get(index).getData();
	}

	public void write(CityWorldGenerator generator, ConfigurationSection section) {
		List<String> names = new ArrayList<String>();
		if (items != null) {
			for (RealMaterial item : items) {
				names.add(item.name());
			}
		}
		section.set(listName, names);
	}
	
	public void read(CityWorldGenerator generator, ConfigurationSection section) {
		if (section.isList(listName)) {
			init(true);
			List<String> names = section.getStringList(listName);
			for (String name : names) {
				RealMaterial material = null;
				try {
					material = RealMaterial.valueOf(name);

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
