package me.daddychurchill.CityWorld.Support;

import org.bukkit.entity.EntityType;

public class AnimalList extends EntityList {

	public AnimalList(String name) {
		super(name);
	}

	public AnimalList(String name, EntityType... entities) {
		super(name, entities);
	}

	@Override
	public int getHerdSize(EntityType entity) {
		switch (entity) {
		default:
			return super.getHerdSize(entity);
		case WOLF:
		case OCELOT:
			return 1;
		case HORSE:
		case COW:
		case MUSHROOM_COW:
		case SHEEP:
		case PIG:
			return 2;
		case RABBIT:
			return 4;
		case CHICKEN:
			return 6;
		}
	}
}
