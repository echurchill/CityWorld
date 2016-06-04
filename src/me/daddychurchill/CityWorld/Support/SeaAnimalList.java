package me.daddychurchill.CityWorld.Support;

import org.bukkit.entity.EntityType;

public class SeaAnimalList extends EntityList {

	public SeaAnimalList(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public SeaAnimalList(String name, EntityType... entities) {
		super(name, entities);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getHerdSize(EntityType entity) {
		switch (entity) {
		default:
			return super.getHerdSize(entity);
		case GUARDIAN:
			return 1;
		case SQUID:
			return 4;
		}
	}
}
