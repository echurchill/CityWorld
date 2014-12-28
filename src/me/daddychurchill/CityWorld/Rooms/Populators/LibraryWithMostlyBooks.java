package me.daddychurchill.CityWorld.Rooms.Populators;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.LibraryDoubleRoom;
import me.daddychurchill.CityWorld.Rooms.StorageDoubleShelvesRoom;

public class LibraryWithMostlyBooks extends RoomProvider {

	public LibraryWithMostlyBooks() {
		super();

		roomTypes.add(new StorageDoubleShelvesRoom());
		roomTypes.add(new LibraryDoubleRoom());
		roomTypes.add(new LibraryDoubleRoom());
		roomTypes.add(new LibraryDoubleRoom());
	}

}
