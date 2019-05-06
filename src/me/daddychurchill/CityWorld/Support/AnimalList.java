package me.daddychurchill.CityWorld.Support;

import org.bukkit.entity.EntityType;

public final class AnimalList extends AbstractEntityList {

	public AnimalList(String name) {
		super(name);
	}

	public AnimalList(String name, EntityType... entities) {
		super(name, entities);
	}

	@Override
	public int getHerdSize(Odds odds, EntityType entity) {
		switch (entity) {
		default:
			return odds.getRandomInt(1, 6);
		case WOLF:
		case OCELOT:
		case CAT:
		case FOX:
			return odds.getRandomInt(1, 2);
		case HORSE:
		case DONKEY:
		case LLAMA:
		case COW:
		case MUSHROOM_COW:
		case SHEEP:
		case PIG:
			return odds.getRandomInt(1, 3);
		}
	}

	public enum EntityAffilation {
		HOSTILE, NEUTRAL, FRIENDLY, OTHER
	}

}
