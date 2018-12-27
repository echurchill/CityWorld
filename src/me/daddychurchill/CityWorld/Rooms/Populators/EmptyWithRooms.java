package me.daddychurchill.CityWorld.Rooms.Populators;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.DividedEllRoom;
import me.daddychurchill.CityWorld.Rooms.DividedSingleRoom;
import me.daddychurchill.CityWorld.Rooms.EmptyRoom;

public class EmptyWithRooms extends RoomProvider {

	public EmptyWithRooms() {
		super();

		roomTypes.add(new EmptyRoom());

		roomTypes.add(new DividedSingleRoom());
		roomTypes.add(new DividedEllRoom());
	}

}
