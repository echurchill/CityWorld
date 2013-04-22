package me.daddychurchill.CityWorld.Buildings.Populators;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.Buildings.Rooms.StorageDoubleRowRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.StorageSingleRowRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class WarehouseWithBooks extends RoomProvider {

	public WarehouseWithBooks() {
		super();

		roomTypes.add(new StorageSingleRowRoom(Material.BOOKSHELF));
		roomTypes.add(new StorageDoubleRowRoom(Material.BOOKSHELF));
	}

}
