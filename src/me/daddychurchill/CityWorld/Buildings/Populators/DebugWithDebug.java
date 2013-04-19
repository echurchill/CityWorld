package me.daddychurchill.CityWorld.Buildings.Populators;

import me.daddychurchill.CityWorld.Buildings.Rooms.DebugRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class DebugWithDebug extends RoomProvider {

	public DebugWithDebug() {
		super();

		roomTypes.add(new DebugRoom());
	}

}
