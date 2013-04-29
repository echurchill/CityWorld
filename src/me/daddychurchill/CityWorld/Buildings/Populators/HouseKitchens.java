package me.daddychurchill.CityWorld.Buildings.Populators;

import me.daddychurchill.CityWorld.Buildings.Rooms.EmptyRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class HouseKitchens extends RoomProvider {

	public HouseKitchens() {
		super();

		roomTypes.add(new EmptyRoom());

	}

}
