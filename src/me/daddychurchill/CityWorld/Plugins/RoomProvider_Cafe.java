package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.Rooms.LoungeGameRoom;
import me.daddychurchill.CityWorld.Rooms.LoungeKitchenetteRoom;
import me.daddychurchill.CityWorld.Rooms.MeetingForSixRoom;

public class RoomProvider_Cafe extends RoomProvider {

	public RoomProvider_Cafe() {

		roomTypes.add(new LoungeGameRoom());
		roomTypes.add(new LoungeGameRoom());
		roomTypes.add(new LoungeGameRoom());
		roomTypes.add(new LoungeGameRoom());

		roomTypes.add(new MeetingForSixRoom());
		roomTypes.add(new MeetingForSixRoom());
		roomTypes.add(new MeetingForSixRoom());
		roomTypes.add(new MeetingForSixRoom());
		
		roomTypes.add(new LoungeKitchenetteRoom());
		
		// two wide table with chair and a book stand (with possible flower pot)
	}

}
