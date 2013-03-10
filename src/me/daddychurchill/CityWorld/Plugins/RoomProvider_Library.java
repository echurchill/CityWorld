package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.Rooms.DividedEllRoom;
import me.daddychurchill.CityWorld.Rooms.DividedSingleRoom;
import me.daddychurchill.CityWorld.Rooms.EmptyRoom;
import me.daddychurchill.CityWorld.Rooms.LibraryDoubleRoom;
import me.daddychurchill.CityWorld.Rooms.LibrarySingleRoom;
import me.daddychurchill.CityWorld.Rooms.LibraryStudyRoom;
import me.daddychurchill.CityWorld.Rooms.LoungeChairsRoom;
import me.daddychurchill.CityWorld.Rooms.LoungeEllCouchRoom;
import me.daddychurchill.CityWorld.Rooms.LoungeGameRoom;

public class RoomProvider_Library extends RoomProvider {

	public RoomProvider_Library() {
		
		roomTypes.add(new EmptyRoom());

		roomTypes.add(new DividedSingleRoom());
		roomTypes.add(new DividedEllRoom());
		
		roomTypes.add(new LibrarySingleRoom());
		roomTypes.add(new LibrarySingleRoom());
		roomTypes.add(new LibraryDoubleRoom());
		roomTypes.add(new LibraryDoubleRoom());
		roomTypes.add(new LibraryStudyRoom());
		roomTypes.add(new LibraryStudyRoom());
		
		roomTypes.add(new LoungeEllCouchRoom());
		roomTypes.add(new LoungeChairsRoom());
		roomTypes.add(new LoungeGameRoom());
	}

}
