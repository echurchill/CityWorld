package me.daddychurchill.CityWorld.Buildings.Populators;

import me.daddychurchill.CityWorld.Buildings.Rooms.ClosetRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.DeskAdminRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.DeskCornerRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.DeskCubbyRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.DeskExecutiveRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.DeskForTwoRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.DividedEllRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.DividedSingleRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.EmptyRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class OfficeWithRooms extends RoomProvider {

	public OfficeWithRooms() {
		super();

//		roomTypes.add(new DebugRoom());
		
		roomTypes.add(new EmptyRoom());

		roomTypes.add(new ClosetRoom());

		roomTypes.add(new DividedSingleRoom());
		roomTypes.add(new DividedEllRoom());
		
		roomTypes.add(new DeskCubbyRoom());
		roomTypes.add(new DeskForTwoRoom());
		roomTypes.add(new DeskExecutiveRoom());
		roomTypes.add(new DeskAdminRoom());
		roomTypes.add(new DeskCornerRoom());
		
		// two wide table with chair and a book stand (with possible flower pot)
	}

}
