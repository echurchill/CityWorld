package me.daddychurchill.CityWorld.Support;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.configuration.ConfigurationSection;

import me.daddychurchill.CityWorld.CityWorldGenerator;

public class EntityList {

	public EntityList(String name) {
		super();
		listName = name;
	}

	public String listName;
	private List<EntityType> items;
	
	public EntityList(String name, EntityType ... entities) {
		super();
		listName = name;
		add(entities);
	}

	private void init(boolean clear) {
		if (items == null)
			items = new ArrayList<EntityType>();
		else if (clear)
			items.clear();
	}
	
	public void add(EntityType ... entities) {
		init(false);
		for (EntityType entity : entities) {
			if (entity.isAlive())
				items.add(entity);
		}
	}
	
	public void add(EntityType entity) {
		init(false);
		items.add(entity);
	}
	
	public void remove(EntityType entity) {
		if (items != null)
			for (int i = items.size() - 1; i >= 0; i--)
				if (items.get(i) == entity)
					items.remove(i);
	}
	
	public int count() {
		return items == null ? 0 : items.size();
	}
	
	public int getHerdSize(EntityType entity) {
		return 1;
	}
	
	public EntityType getRandomEntity(Odds odds) {
		return getRandomEntity(odds, EntityType.UNKNOWN);
	}
	
	public EntityType getRandomEntity(Odds odds, EntityType defaultEntity) {
		if (items == null || count() == 0)
			return defaultEntity;
		else
			return items.get(odds.getRandomInt(count()));
	}

	public void write(CityWorldGenerator generator, ConfigurationSection section) {
		List<String> names = new ArrayList<String>();
		if (items != null) {
			for (EntityType item : items) {
				names.add(item.name());
			}
		}
		section.set(listName, names);
	}
	
	public void read(CityWorldGenerator generator, ConfigurationSection section) {
		if (section.isList(listName)) {
			init(true);
			List<String> names = section.getStringList(listName);
			EntityType[] entities = EntityType.values();
			for (String name : names) {
				EntityType entity = null;
				try {
					
					// look through our list of possibilities
					for (int i = 0; i < entities.length; i++)
						if (entities[i].name().equalsIgnoreCase(name)) {
							
							// if the one found is one that is alive then great!
							if (entities[i].isAlive())
								entity = entities[i];
							else
								generator.reportMessage("Ignoring " + generator.worldName + ".Entities." + listName + ": " + name + ", it is nonliving");
							break;
						}
					
					// still nothing, so comment about it
					if (entity == null)
						generator.reportMessage("Ignoring " + generator.worldName + ".Entities." + listName + ": " + name + ", is not known");
				} catch (Exception e) {
					generator.reportException("Reading " + generator.worldName + ".Entities." + listName + ": " + name, e);
					entity = null;
				}
				
				if (entity != null)
					add(entity);
			}
		}
	}
}
