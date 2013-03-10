package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.Rooms.ClosetRoom;
import me.daddychurchill.CityWorld.Rooms.DeskAdminRoom;
import me.daddychurchill.CityWorld.Rooms.DeskCornerRoom;
import me.daddychurchill.CityWorld.Rooms.DeskCubbyRoom;
import me.daddychurchill.CityWorld.Rooms.DeskExecutiveRoom;
import me.daddychurchill.CityWorld.Rooms.DeskForTwoRoom;
import me.daddychurchill.CityWorld.Rooms.DeskInternsRoom;
import me.daddychurchill.CityWorld.Rooms.DividedEllRoom;
import me.daddychurchill.CityWorld.Rooms.DividedSingleRoom;
import me.daddychurchill.CityWorld.Rooms.EmptyRoom;
import me.daddychurchill.CityWorld.Rooms.LoungeTVRoom;
import me.daddychurchill.CityWorld.Rooms.MeetingForSixRoom;
import me.daddychurchill.CityWorld.Rooms.MeetingForFourRoom;
import me.daddychurchill.CityWorld.Rooms.LoungeCouchRoom;
import me.daddychurchill.CityWorld.Rooms.LoungeTableRoom;

public class RoomProvider_Office extends RoomProvider {

	public RoomProvider_Office() {
		super();

//		roomTypes.add(new DebugRoom());
		
		roomTypes.add(new EmptyRoom());

		roomTypes.add(new ClosetRoom());

		roomTypes.add(new DividedSingleRoom());
		roomTypes.add(new DividedEllRoom());
		
		roomTypes.add(new LoungeCouchRoom());
		roomTypes.add(new LoungeTableRoom());
		roomTypes.add(new LoungeTVRoom());

		roomTypes.add(new MeetingForSixRoom());
		roomTypes.add(new MeetingForFourRoom());
		
		roomTypes.add(new DeskCubbyRoom());
		roomTypes.add(new DeskForTwoRoom());
		roomTypes.add(new DeskExecutiveRoom());
		roomTypes.add(new DeskAdminRoom());
		roomTypes.add(new DeskCornerRoom());
		roomTypes.add(new DeskCornerRoom());
		roomTypes.add(new DeskInternsRoom());
		roomTypes.add(new DeskInternsRoom());
		
		
		// kitchette
		// two wide table with chair and a book stand (with possible flower pot)
	}

}
