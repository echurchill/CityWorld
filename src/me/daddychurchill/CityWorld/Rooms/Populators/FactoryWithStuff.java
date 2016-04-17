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
		roomTypes.add(new StorageSingleRowRoom(Material.PISTON_BASE));
		roomTypes.add(new StorageSingleRowRoom(Material.WORKBENCH));
		roomTypes.add(new StorageDoubleRowRoom(Material.BOOKSHELF));
		roomTypes.add(new StorageDoubleRowRoom(Material.PISTON_BASE));
		roomTypes.add(new StorageDoubleRowRoom(Material.WORKBENCH));
		roomTypes.add(new StorageStacksRoom(Material.BOOKSHELF));
		roomTypes.add(new StorageStacksRoom(Material.PISTON_BASE));
		roomTypes.add(new StorageStacksRoom(Material.WORKBENCH));
	}

}
