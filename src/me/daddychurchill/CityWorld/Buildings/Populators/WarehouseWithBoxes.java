package me.daddychurchill.CityWorld.Buildings.Populators;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.Buildings.Rooms.StorageDoubleRowRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class WarehouseWithBoxes extends RoomProvider {

	public WarehouseWithBoxes() {
		super();

		roomTypes.add(new StorageDoubleRowRoom(Material.PISTON_BASE));
	}

}
