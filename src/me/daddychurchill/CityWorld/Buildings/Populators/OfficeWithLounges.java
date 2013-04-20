package me.daddychurchill.CityWorld.Buildings.Populators;

import me.daddychurchill.CityWorld.Buildings.Rooms.EmptyRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.LoungeCouchRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.LoungeKitchenetteRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.LoungeQuadRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.LoungeTVRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.LoungeTableRoom;
import me.daddychurchill.CityWorld.Buildings.Rooms.LoungeTrioRoom;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

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
