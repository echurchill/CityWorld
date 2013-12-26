package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;

public abstract class StorageTypeRoom extends StorageRoom {

	protected Material materialType;
	
	public StorageTypeRoom(Material type) {
		super();
		materialType = type;
	}

}
