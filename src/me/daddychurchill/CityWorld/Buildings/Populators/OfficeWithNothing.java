package me.daddychurchill.CityWorld.Buildings.Populators;

import me.daddychurchill.CityWorld.Buildings.Rooms.EmptyRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class OfficeWithNothing extends RoomProvider {

	public OfficeWithNothing() {
		super();

		roomTypes.add(new EmptyRoom());
	}

}
