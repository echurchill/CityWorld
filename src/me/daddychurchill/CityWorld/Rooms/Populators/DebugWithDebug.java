package me.daddychurchill.CityWorld.Rooms.Populators;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.DebugRoom;

public class DebugWithDebug extends RoomProvider {

	public DebugWithDebug() {
		super();

		roomTypes.add(new DebugRoom());
	}

}
