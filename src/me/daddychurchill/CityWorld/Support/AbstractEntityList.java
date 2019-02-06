package me.daddychurchill.CityWorld.Support;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

import me.daddychurchill.CityWorld.CityWorldGenerator;

public abstract class AbstractEntityList {

	AbstractEntityList(String name) {
		super();
		listName = name;
	}

	private final String listName;
	private List<EntityType> items;

	AbstractEntityList(String name, EntityType... entities) {
		super();
		listName = name;
		add(entities);
	}

	private void init(boolean clear) {
		if (items == null)
			items = new ArrayList<>();
		else if (clear)
			items.clear();
	}

	private void add(EntityType... entities) {
		init(false);
		for (EntityType entity : entities) {
			if (entity.isAlive())
				items.add(entity);
		}
	}

	private void add(EntityType entity) {
		init(false);
		items.add(entity);
	}

	public void remove(EntityType entity) {
		if (items != null)
			for (int i = items.size() - 1; i >= 0; i--)
				if (items.get(i) == entity)
					items.remove(i);
	}

	private int count() {
		return items == null ? 0 : items.size();
	}

	public int getHerdSize(Odds odds, EntityType entity) {
		return 1;
	}

	private EntityType getFirstEntity() {
		if (items == null || count() == 0)
			return EntityType.UNKNOWN;
		else
			return items.get(0);
	}

	public EntityType getRandomEntity(Odds odds) {
		return getRandomEntity(odds, getFirstEntity());
	}

	private EntityType getRandomEntity(Odds odds, EntityType defaultEntity) {
		if (items == null || count() == 0)
			return defaultEntity;
		else
			return items.get(odds.getRandomInt(count()));
	}

	public void write(CityWorldGenerator generator, ConfigurationSection section) {
		List<String> names = new ArrayList<>();
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
					for (EntityType entity1 : entities)
						if (entity1.name().equalsIgnoreCase(name)) {

							// if the one found is one that is alive then great!
							if (entity1.isAlive())
								entity = entity1;
							else
								generator.reportMessage("Ignoring " + generator.worldName + ".Entities." + listName
										+ ": " + name + ", it is nonliving");
							break;
						}

					// still nothing, so comment about it
					if (entity == null)
						generator.reportMessage("Ignoring " + generator.worldName + ".Entities." + listName + ": "
								+ name + ", is not known");
				} catch (Exception e) {
					generator.reportException("Reading " + generator.worldName + ".Entities." + listName + ": " + name,
							e);
					entity = null;
				}

				if (entity != null)
					add(entity);
			}
		}
	}
}
