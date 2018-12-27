package me.daddychurchill.CityWorld.Rooms.Populators;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.EmptyRoom;

public class EmptyWithNothing extends RoomProvider {

	public EmptyWithNothing() {
		super();

		roomTypes.add(new EmptyRoom());
	}

}
