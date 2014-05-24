package me.daddychurchill.CityWorld.Rooms.Populators;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.EmptyRoom;

public class OfficeWithNothing extends RoomProvider {

	public OfficeWithNothing() {
		super();

		roomTypes.add(new EmptyRoom());
	}

}
