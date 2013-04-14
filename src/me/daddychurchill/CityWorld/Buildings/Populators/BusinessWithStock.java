package me.daddychurchill.CityWorld.Buildings.Populators;

import me.daddychurchill.CityWorld.Buildings.Rooms.EmptyRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class BusinessWithStock extends RoomProvider {

	public BusinessWithStock() {
		roomTypes.add(new EmptyRoom());

		// shelves
		// cash register

	}

}
