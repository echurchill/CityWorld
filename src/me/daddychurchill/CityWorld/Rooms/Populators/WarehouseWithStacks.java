package me.daddychurchill.CityWorld.Rooms.Populators;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.StorageStacksRoom;

public class WarehouseWithStacks extends RoomProvider {

	public WarehouseWithStacks() {
		super();

		roomTypes.add(new StorageStacksRoom(Material.BOOKSHELF));
		roomTypes.add(new StorageStacksRoom(Material.PISTON));
		roomTypes.add(new StorageStacksRoom(Material.CRAFTING_TABLE));
	}

}
