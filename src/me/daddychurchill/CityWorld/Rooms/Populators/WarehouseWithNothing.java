package me.daddychurchill.CityWorld.Rooms.Populators;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.EmptyRoom;
import me.daddychurchill.CityWorld.Rooms.StorageDoubleShelvesRoom;

public class WarehouseWithNothing extends RoomProvider {

	public WarehouseWithNothing() {
		super();

		roomTypes.add(new EmptyRoom());
		roomTypes.add(new StorageDoubleShelvesRoom());
	}

}
