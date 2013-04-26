package me.daddychurchill.CityWorld.Buildings.Populators;

import me.daddychurchill.CityWorld.Buildings.Rooms.EmptyRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class EmptyWithNothing extends RoomProvider {

	public EmptyWithNothing() {
		super();
		
		roomTypes.add(new EmptyRoom());
	}

}
