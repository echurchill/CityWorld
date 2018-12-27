package me.daddychurchill.CityWorld.Rooms.Populators;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.ClosetRoom;
import me.daddychurchill.CityWorld.Rooms.DeskAdminRoom;
import me.daddychurchill.CityWorld.Rooms.DeskCornerRoom;
import me.daddychurchill.CityWorld.Rooms.DeskCubbyRoom;
import me.daddychurchill.CityWorld.Rooms.DeskExecutiveRoom;
import me.daddychurchill.CityWorld.Rooms.DeskForTwoRoom;
import me.daddychurchill.CityWorld.Rooms.DividedEllRoom;
import me.daddychurchill.CityWorld.Rooms.DividedSingleRoom;
import me.daddychurchill.CityWorld.Rooms.EmptyRoom;

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
