package me.daddychurchill.CityWorld.Plats.Urban;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.FinishedBuildingLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.Populators.OfficeWithCubicles;
import me.daddychurchill.CityWorld.Rooms.Populators.OfficeWithLounges;
import me.daddychurchill.CityWorld.Rooms.Populators.OfficeWithNothing;
import me.daddychurchill.CityWorld.Rooms.Populators.OfficeWithRandom;
import me.daddychurchill.CityWorld.Rooms.Populators.OfficeWithRooms;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

public class OfficeBuildingLot extends FinishedBuildingLot {

	private static RoomProvider contentsEmpty = new OfficeWithNothing();
	private static RoomProvider contentsRandom = new OfficeWithRandom();
	private static RoomProvider contentsCubes = new OfficeWithCubicles();
	private static RoomProvider contentsRooms = new OfficeWithRooms();
	private static RoomProvider contentsLounges = new OfficeWithLounges();
	
	public enum ContentStyle {RANDOM, EMPTY, OFFICES, CUBICLES};
	protected ContentStyle contentStyle;

	public OfficeBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

		rounded = false;
		contentStyle = pickContentStyle();
	}

	protected ContentStyle pickContentStyle() {
		switch (chunkOdds.getRandomInt(10)) {
		case 1:
		case 2:
		case 3:
			return ContentStyle.OFFICES;
		case 4:
		case 5:
		case 6:
			return ContentStyle.CUBICLES;
		case 7:
		case 8:
		case 9:
			return ContentStyle.RANDOM;
		default: 
			return ContentStyle.EMPTY;
		}
	}
	
	@Override
	public RoomProvider roomProviderForFloor(CityWorldGenerator generator, SupportBlocks chunk, int floor, int floorY) {
		switch (contentStyle) {
		case OFFICES:
			switch (chunkOdds.getRandomInt(10)) {
			case 1:
			case 2:
				return contentsLounges;
			case 3:
				return contentsEmpty;
			default:
				return contentsRooms;
			}
		case CUBICLES:
			switch (chunkOdds.getRandomInt(10)) {
			case 1:
			case 2:
				return contentsLounges;
			case 3:
				return contentsEmpty;
			default:
				return contentsCubes;
			}
		case RANDOM:
			switch (chunkOdds.getRandomInt(10)) {
			case 1:
			case 2:
			case 3:
				return contentsCubes;
			case 4:
			case 5:
			case 6:
				return contentsRooms;
			case 7:
				return contentsLounges;
			case 8:
				return contentsEmpty;
			default:
				return contentsRandom;
			}
		default:
			return contentsEmpty;
		}
	}
	
	@Override
	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);
		
		// other bits
		if (result && relative instanceof OfficeBuildingLot) {
			OfficeBuildingLot relativebuilding = (OfficeBuildingLot) relative;

			// any other bits
			contentStyle = relativebuilding.contentStyle;
		}
		
		return result;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new OfficeBuildingLot(platmap, chunkX, chunkZ);
	}

}
