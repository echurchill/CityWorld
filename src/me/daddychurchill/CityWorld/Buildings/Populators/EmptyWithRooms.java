package me.daddychurchill.CityWorld.Buildings.Populators;

import me.daddychurchill.CityWorld.Buildings.Rooms.DividedEllRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.DividedSingleRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.EmptyRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class EmptyWithRooms extends RoomProvider {

	public EmptyWithRooms() {
		super();
		
		roomTypes.add(new EmptyRoom());

		roomTypes.add(new DividedSingleRoom());
		roomTypes.add(new DividedEllRoom());
	}
	
}
