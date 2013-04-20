package me.daddychurchill.CityWorld.Buildings.Populators;

import me.daddychurchill.CityWorld.Buildings.Rooms.DeskAdminRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.DeskCornerRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.DeskCubbyRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.DeskForTwoRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.DeskInternsRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.MeetingForFourRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.MeetingForSixRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

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
