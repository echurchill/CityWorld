package me.daddychurchill.CityWorld.Buildings.Populators;

import me.daddychurchill.CityWorld.Buildings.Rooms.StorageDoubleBooksRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.StorageSingleBooksRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class WarehouseWithBooks extends RoomProvider {

	public WarehouseWithBooks() {
		roomTypes.add(new StorageSingleBooksRoom());
		roomTypes.add(new StorageDoubleBooksRoom());
	}

}
