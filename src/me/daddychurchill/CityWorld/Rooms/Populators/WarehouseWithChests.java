package me.daddychurchill.CityWorld.Rooms.Populators;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.StorageEmptyChestsRoom;
import me.daddychurchill.CityWorld.Rooms.StorageFilledChestsRoom;

public class WarehouseWithChests extends RoomProvider {

	public WarehouseWithChests() {
		super();

		roomTypes.add(new StorageFilledChestsRoom());
		roomTypes.add(new StorageFilledChestsRoom());
		roomTypes.add(new StorageFilledChestsRoom());
		roomTypes.add(new StorageEmptyChestsRoom());
	}

}
