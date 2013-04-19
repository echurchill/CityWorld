package me.daddychurchill.CityWorld.Buildings.Populators;

import me.daddychurchill.CityWorld.Buildings.Rooms.LoungeGameRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.LoungeKitchenetteRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.LoungeQuadRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.LoungeTrioRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.MeetingForSixRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class BusinessWithCafe extends RoomProvider {

	public BusinessWithCafe() {
		super();

		roomTypes.add(new LoungeGameRoom());
		roomTypes.add(new LoungeGameRoom());
		roomTypes.add(new LoungeQuadRoom());
		roomTypes.add(new LoungeTrioRoom());

		roomTypes.add(new MeetingForSixRoom());
		roomTypes.add(new MeetingForSixRoom());
		roomTypes.add(new MeetingForSixRoom());
		roomTypes.add(new MeetingForSixRoom());
		
		roomTypes.add(new LoungeKitchenetteRoom());
		
		// two wide table with chair and a book stand (with possible flower pot)
	}

}
