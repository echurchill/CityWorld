package me.daddychurchill.CityWorld.Buildings.Populators;

import me.daddychurchill.CityWorld.Buildings.Rooms.LibraryDoubleRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class LibraryWithAllBooks extends RoomProvider {

	public LibraryWithAllBooks() {
		super();

		roomTypes.add(new LibraryDoubleRoom());
	}

}
