package me.daddychurchill.CityWorld.Buildings.Populators;

import me.daddychurchill.CityWorld.Buildings.Rooms.StorageStacksRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class WarehouseWithStacks extends RoomProvider {

	public WarehouseWithStacks() {
		roomTypes.add(new StorageStacksRoom());
	}

}
