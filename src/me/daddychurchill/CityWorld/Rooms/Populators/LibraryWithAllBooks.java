package me.daddychurchill.CityWorld.Rooms.Populators;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.LibraryDoubleRoom;

public class LibraryWithAllBooks extends RoomProvider {

	public LibraryWithAllBooks() {
		super();

		roomTypes.add(new LibraryDoubleRoom());
	}

}
