package me.daddychurchill.CityWorld.Rooms.Populators;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.MeetingForFourRoom;
import me.daddychurchill.CityWorld.Rooms.MeetingForSixRoom;

public class HouseDiningRooms extends RoomProvider {

	public HouseDiningRooms() {
		super();

		roomTypes.add(new MeetingForFourRoom());
		roomTypes.add(new MeetingForSixRoom());

	}

}
