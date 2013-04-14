package me.daddychurchill.CityWorld.Buildings.Populators;

import me.daddychurchill.CityWorld.Buildings.Rooms.StorageDoubleShelvesRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.StorageSingleShelvesRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class WarehouseWithNothing extends RoomProvider  {

	public WarehouseWithNothing() {
		roomTypes.add(new StorageSingleShelvesRoom());
		roomTypes.add(new StorageDoubleShelvesRoom());
	}

}
