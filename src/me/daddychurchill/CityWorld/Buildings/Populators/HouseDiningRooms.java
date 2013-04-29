package me.daddychurchill.CityWorld.Buildings.Populators;

import me.daddychurchill.CityWorld.Buildings.Rooms.MeetingForFourRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.MeetingForSixRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class HouseDiningRooms extends RoomProvider {

	public HouseDiningRooms() {
		super();

		roomTypes.add(new MeetingForFourRoom());
		roomTypes.add(new MeetingForSixRoom());

	}

}
