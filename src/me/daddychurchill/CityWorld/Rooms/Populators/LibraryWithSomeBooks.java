package me.daddychurchill.CityWorld.Rooms.Populators;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.LibraryDoubleRoom;
import me.daddychurchill.CityWorld.Rooms.StorageDoubleShelvesRoom;

public class LibraryWithSomeBooks extends RoomProvider {

	public LibraryWithSomeBooks() {
		super();

		roomTypes.add(new StorageDoubleShelvesRoom());
		roomTypes.add(new StorageDoubleShelvesRoom());
		roomTypes.add(new LibraryDoubleRoom());
		roomTypes.add(new LibraryDoubleRoom());
	}

}
