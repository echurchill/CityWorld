package me.daddychurchill.CityWorld.Buildings.Populators;

import me.daddychurchill.CityWorld.Buildings.Rooms.StorageEmptyChestsRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.StorageFilledChestsRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class WarehouseWithChests extends RoomProvider {

	public WarehouseWithChests() {
		super();

		roomTypes.add(new StorageFilledChestsRoom());
		roomTypes.add(new StorageEmptyChestsRoom());
	}

}
