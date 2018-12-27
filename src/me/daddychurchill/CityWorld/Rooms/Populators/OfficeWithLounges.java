package me.daddychurchill.CityWorld.Rooms.Populators;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.EmptyRoom;
import me.daddychurchill.CityWorld.Rooms.LoungeCouchRoom;
import me.daddychurchill.CityWorld.Rooms.LoungeKitchenetteRoom;
import me.daddychurchill.CityWorld.Rooms.LoungeQuadRoom;
import me.daddychurchill.CityWorld.Rooms.LoungeTVRoom;
import me.daddychurchill.CityWorld.Rooms.LoungeTableRoom;
import me.daddychurchill.CityWorld.Rooms.LoungeTrioRoom;

public class OfficeWithLounges extends RoomProvider {

	public OfficeWithLounges() {
		super();

//		roomTypes.add(new DebugRoom());

		roomTypes.add(new EmptyRoom());

		roomTypes.add(new LoungeCouchRoom());
		roomTypes.add(new LoungeTableRoom());
		roomTypes.add(new LoungeTVRoom());
		roomTypes.add(new LoungeQuadRoom());
		roomTypes.add(new LoungeTrioRoom());
		roomTypes.add(new LoungeKitchenetteRoom());
	}

}
