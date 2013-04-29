package me.daddychurchill.CityWorld.Buildings.Populators;

import me.daddychurchill.CityWorld.Buildings.Rooms.EmptyRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class HouseBedrooms extends RoomProvider {

	public HouseBedrooms() {
		super();

		roomTypes.add(new EmptyRoom());

	}

}
