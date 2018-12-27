package me.daddychurchill.CityWorld.Support;

import org.bukkit.entity.EntityType;

public final class BeingList extends AbstractEntityList {

	public BeingList(String name) {
		super(name);
	}

	public BeingList(String name, EntityType[] entities) {
		super(name, entities);
	}

}
