package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.Rooms.EmptyRoom;

public class RoomProvider_Storage extends RoomProvider {

	public RoomProvider_Storage() {
//		roomTypes.add(new DebugRoom());
		
		roomTypes.add(new EmptyRoom());

		// single table
		// double table
		// workbench
		// anvil
		// chest
	}

}
