package me.daddychurchill.CityWorld.Buildings.Populators;

import me.daddychurchill.CityWorld.Buildings.Rooms.EmptyRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

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
