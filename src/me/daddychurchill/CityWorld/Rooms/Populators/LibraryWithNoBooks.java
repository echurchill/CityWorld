package me.daddychurchill.CityWorld.Rooms.Populators;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.StorageDoubleShelvesRoom;

public class LibraryWithNoBooks extends RoomProvider {

	public LibraryWithNoBooks() {
		super();

		roomTypes.add(new StorageDoubleShelvesRoom());
	}

}
