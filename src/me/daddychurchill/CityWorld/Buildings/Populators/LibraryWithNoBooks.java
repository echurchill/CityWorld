package me.daddychurchill.CityWorld.Buildings.Populators;

import me.daddychurchill.CityWorld.Buildings.Rooms.StorageDoubleShelvesRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class LibraryWithNoBooks extends RoomProvider {

	public LibraryWithNoBooks() {
		super();

		roomTypes.add(new StorageDoubleShelvesRoom());
	}

}
