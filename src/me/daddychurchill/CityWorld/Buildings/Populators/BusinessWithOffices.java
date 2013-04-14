package me.daddychurchill.CityWorld.Buildings.Populators;

import me.daddychurchill.CityWorld.Buildings.Rooms.ClosetRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.DeskAdminRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.DeskCornerRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.DeskCubbyRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.DeskExecutiveRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.DeskForTwoRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.DeskInternsRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.DividedEllRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.DividedSingleRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.EmptyRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.LoungeCouchRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.LoungeKitchenetteRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.LoungeQuadRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.LoungeTVRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.LoungeTableRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.LoungeTrioRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.MeetingForFourRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.MeetingForSixRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class BusinessWithOffices extends RoomProvider {

	public BusinessWithOffices() {
		super();

//		roomTypes.add(new DebugRoom());
		
		roomTypes.add(new EmptyRoom());

		roomTypes.add(new ClosetRoom());

		roomTypes.add(new DividedSingleRoom());
		roomTypes.add(new DividedEllRoom());
		
		roomTypes.add(new LoungeCouchRoom());
		roomTypes.add(new LoungeTableRoom());
		roomTypes.add(new LoungeTVRoom());
		roomTypes.add(new LoungeQuadRoom());
		roomTypes.add(new LoungeTrioRoom());
		roomTypes.add(new LoungeKitchenetteRoom());

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
		
		
		// two wide table with chair and a book stand (with possible flower pot)
	}

}
