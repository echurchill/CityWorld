package me.daddychurchill.CityWorld.Rooms.Populators;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.EmptyRoom;

public class HouseLivingRooms extends RoomProvider {

	public HouseLivingRooms() {
		super();

		roomTypes.add(new EmptyRoom());

	}

}
