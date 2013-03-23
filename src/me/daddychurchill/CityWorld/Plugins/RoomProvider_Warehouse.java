package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.Rooms.EmptyRoom;

public class RoomProvider_Warehouse extends RoomProvider {

	public RoomProvider_Warehouse() {
//		roomTypes.add(new DebugRoom());
		
		roomTypes.add(new EmptyRoom());

		// shelves (some with chests)
		// tables
		// workbench
	}

}
