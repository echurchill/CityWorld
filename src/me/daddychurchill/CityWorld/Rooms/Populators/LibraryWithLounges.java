package me.daddychurchill.CityWorld.Rooms.Populators;

import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.LibraryDoubleRoom;
import me.daddychurchill.CityWorld.Rooms.LibrarySingleRoom;
import me.daddychurchill.CityWorld.Rooms.LibraryStudyRoom;
import me.daddychurchill.CityWorld.Rooms.LoungeChairsRoom;
import me.daddychurchill.CityWorld.Rooms.LoungeCouchRoom;
import me.daddychurchill.CityWorld.Rooms.LoungeEllCouchRoom;
import me.daddychurchill.CityWorld.Rooms.LoungeGameRoom;
import me.daddychurchill.CityWorld.Rooms.LoungeQuadRoom;
import me.daddychurchill.CityWorld.Rooms.LoungeTrioRoom;

public class LibraryWithLounges extends RoomProvider {

	public LibraryWithLounges() {
		super();

		roomTypes.add(new LibrarySingleRoom());
		roomTypes.add(new LibraryDoubleRoom());
		roomTypes.add(new LibraryStudyRoom());

		roomTypes.add(new LoungeEllCouchRoom());
		roomTypes.add(new LoungeTrioRoom());
		roomTypes.add(new LoungeQuadRoom());
		roomTypes.add(new LoungeChairsRoom());
		roomTypes.add(new LoungeGameRoom());
		roomTypes.add(new LoungeCouchRoom());
	}

}
