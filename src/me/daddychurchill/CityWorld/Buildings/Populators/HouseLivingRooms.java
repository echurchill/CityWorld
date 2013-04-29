package me.daddychurchill.CityWorld.Buildings.Populators;

import me.daddychurchill.CityWorld.Buildings.Rooms.EmptyRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class HouseLivingRooms extends RoomProvider {

	public HouseLivingRooms() {
		super();

		roomTypes.add(new EmptyRoom());

	}

}
