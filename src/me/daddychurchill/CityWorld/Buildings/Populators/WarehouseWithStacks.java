package me.daddychurchill.CityWorld.Buildings.Populators;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.Buildings.Rooms.StorageStacksRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class WarehouseWithStacks extends RoomProvider {

	public WarehouseWithStacks() {
		super();

		roomTypes.add(new StorageStacksRoom(Material.BOOKSHELF));
		roomTypes.add(new StorageStacksRoom(Material.PISTON_BASE));
		roomTypes.add(new StorageStacksRoom(Material.WORKBENCH));
	}

}
