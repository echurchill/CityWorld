package me.daddychurchill.CityWorld.Rooms.Populators;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.StorageDoubleRowRoom;

public class WarehouseWithBoxes extends RoomProvider {

	public WarehouseWithBoxes() {
		super();

		roomTypes.add(new StorageDoubleRowRoom(Material.PISTON));
	}

}
