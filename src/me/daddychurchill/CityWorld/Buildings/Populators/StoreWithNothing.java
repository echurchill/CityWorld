package me.daddychurchill.CityWorld.Buildings.Populators;

import me.daddychurchill.CityWorld.Buildings.Rooms.EmptyRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.RegisterRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.StorageDoubleShelvesRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class StoreWithNothing extends RoomProvider {

	public StoreWithNothing() {
		super();

		roomTypes.add(new StorageDoubleShelvesRoom());
		roomTypes.add(new StorageDoubleShelvesRoom());
		roomTypes.add(new StorageDoubleShelvesRoom());
		roomTypes.add(new StorageDoubleShelvesRoom());
		roomTypes.add(new EmptyRoom());
		roomTypes.add(new EmptyRoom());
		roomTypes.add(new EmptyRoom());
		roomTypes.add(new EmptyRoom());
		roomTypes.add(new RegisterRoom());
	}

}
