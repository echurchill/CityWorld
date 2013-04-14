package me.daddychurchill.CityWorld.Buildings.Populators;

import me.daddychurchill.CityWorld.Buildings.Rooms.StorageDoubleBooksRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.StorageDoubleShelvesRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.StorageFilledChestsRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class WarehouseWithRandom extends RoomProvider {

	public WarehouseWithRandom() {
		roomTypes.add(new StorageDoubleShelvesRoom());
		roomTypes.add(new StorageDoubleBooksRoom());
		roomTypes.add(new StorageFilledChestsRoom());
		// shelves (some with chests)
		// tables
		// workbench
	}
}
