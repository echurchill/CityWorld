package me.daddychurchill.CityWorld.Rooms.Populators;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.DeskAdminRoom;
import me.daddychurchill.CityWorld.Rooms.DeskCornerRoom;
import me.daddychurchill.CityWorld.Rooms.DeskCubbyRoom;
import me.daddychurchill.CityWorld.Rooms.DeskForTwoRoom;
import me.daddychurchill.CityWorld.Rooms.DeskInternsRoom;
import me.daddychurchill.CityWorld.Rooms.MeetingForFourRoom;
import me.daddychurchill.CityWorld.Rooms.MeetingForSixRoom;

public class OfficeWithCubicles extends RoomProvider {

	public OfficeWithCubicles() {
		super();

//		roomTypes.add(new DebugRoom());

		roomTypes.add(new MeetingForSixRoom());
		roomTypes.add(new MeetingForFourRoom());

		roomTypes.add(new DeskCubbyRoom());
		roomTypes.add(new DeskForTwoRoom());
		roomTypes.add(new DeskAdminRoom());
		roomTypes.add(new DeskCornerRoom());
		roomTypes.add(new DeskCornerRoom());
		roomTypes.add(new DeskInternsRoom());
		roomTypes.add(new DeskInternsRoom());

		// single table
		// double table
		// workbench
		// anvil
		// chest
	}

}
