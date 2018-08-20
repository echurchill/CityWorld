package me.daddychurchill.CityWorld.Rooms.Populators;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.EmptyRoom;
import me.daddychurchill.CityWorld.Rooms.StorageDoubleRowRoom;
import me.daddychurchill.CityWorld.Rooms.StorageDoubleShelvesRoom;
import me.daddychurchill.CityWorld.Rooms.StorageSingleRowRoom;
import me.daddychurchill.CityWorld.Rooms.StorageSingleShelvesRoom;
import me.daddychurchill.CityWorld.Rooms.StorageStacksRoom;

public class FactoryWithStuff extends RoomProvider {

	public FactoryWithStuff() {
		super();

		roomTypes.add(new EmptyRoom());
		roomTypes.add(new StorageSingleShelvesRoom());
		roomTypes.add(new StorageDoubleShelvesRoom());
		roomTypes.add(new StorageSingleRowRoom(Material.BOOKSHELF));
		roomTypes.add(new StorageSingleRowRoom(Material.PISTON));
		roomTypes.add(new StorageSingleRowRoom(Material.CRAFTING_TABLE));
		roomTypes.add(new StorageDoubleRowRoom(Material.BOOKSHELF));
		roomTypes.add(new StorageDoubleRowRoom(Material.PISTON));
		roomTypes.add(new StorageDoubleRowRoom(Material.CRAFTING_TABLE));
		roomTypes.add(new StorageStacksRoom(Material.BOOKSHELF));
		roomTypes.add(new StorageStacksRoom(Material.PISTON));
		roomTypes.add(new StorageStacksRoom(Material.CRAFTING_TABLE));
	}

}
