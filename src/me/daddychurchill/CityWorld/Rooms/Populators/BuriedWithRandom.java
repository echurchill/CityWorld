package me.daddychurchill.CityWorld.Rooms.Populators;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.DeskAdminRoom;
import me.daddychurchill.CityWorld.Rooms.DeskCornerRoom;
import me.daddychurchill.CityWorld.Rooms.DeskExecutiveRoom;
import me.daddychurchill.CityWorld.Rooms.DeskInternsRoom;
import me.daddychurchill.CityWorld.Rooms.LoungeCouchRoom;
import me.daddychurchill.CityWorld.Rooms.LoungeQuadRoom;
import me.daddychurchill.CityWorld.Rooms.LoungeTableRoom;
import me.daddychurchill.CityWorld.Rooms.LoungeTrioRoom;
import me.daddychurchill.CityWorld.Rooms.MeetingForFourRoom;
import me.daddychurchill.CityWorld.Rooms.MeetingForSixRoom;

public class BuriedWithRandom extends RoomProvider {

	public BuriedWithRandom() {
		super();

//		roomTypes.add(new DebugRoom());
		
//		roomTypes.add(new EmptyRoom());

		roomTypes.add(new LoungeCouchRoom());
		roomTypes.add(new LoungeTableRoom());
		roomTypes.add(new LoungeQuadRoom());
		roomTypes.add(new LoungeTrioRoom());

		roomTypes.add(new MeetingForSixRoom());
		roomTypes.add(new MeetingForFourRoom());
		
		roomTypes.add(new DeskExecutiveRoom());
		roomTypes.add(new DeskAdminRoom());
		roomTypes.add(new DeskCornerRoom());
		roomTypes.add(new DeskCornerRoom());
		roomTypes.add(new DeskInternsRoom());
		roomTypes.add(new DeskInternsRoom());
	}

}
