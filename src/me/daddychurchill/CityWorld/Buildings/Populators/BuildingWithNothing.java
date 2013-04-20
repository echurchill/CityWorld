package me.daddychurchill.CityWorld.Buildings.Populators;

import me.daddychurchill.CityWorld.Buildings.Rooms.EmptyRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class BuildingWithNothing extends RoomProvider {

	public BuildingWithNothing() {
		super();

		roomTypes.add(new EmptyRoom());
	}

}
