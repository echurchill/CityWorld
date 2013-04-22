package me.daddychurchill.CityWorld.Buildings.Populators;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.Buildings.Rooms.StorageDoubleRowRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.StorageDoubleShelvesRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.StorageFilledChestsRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class WarehouseWithRandom extends RoomProvider {

	public WarehouseWithRandom() {
		super();

		roomTypes.add(new StorageDoubleShelvesRoom());
		roomTypes.add(new StorageDoubleRowRoom(Material.BOOKSHELF));
		roomTypes.add(new StorageDoubleRowRoom(Material.PISTON_BASE));
		roomTypes.add(new StorageDoubleRowRoom(Material.WORKBENCH));
		roomTypes.add(new StorageFilledChestsRoom());
		// shelves (some with chests)
		// tables
		// workbench
	}
}
