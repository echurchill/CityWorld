package me.daddychurchill.CityWorld.Buildings;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Buildings.Populators.LibraryWithAllBooks;
import me.daddychurchill.CityWorld.Buildings.Populators.LibraryWithMostlyBooks;
import me.daddychurchill.CityWorld.Buildings.Populators.LibraryWithLounges;
import me.daddychurchill.CityWorld.Buildings.Populators.LibraryWithNoBooks;
import me.daddychurchill.CityWorld.Buildings.Populators.LibraryWithRandom;
import me.daddychurchill.CityWorld.Buildings.Populators.LibraryWithSomeBooks;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plats.FinishedBuildingLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class LibraryLot extends FinishedBuildingLot {

	private static RoomProvider contentsRandom = new LibraryWithRandom();
	private static RoomProvider contentsNoBooks = new LibraryWithNoBooks();
	private static RoomProvider contentsSomeBooks = new LibraryWithSomeBooks();
	private static RoomProvider contentsMostlyBooks = new LibraryWithMostlyBooks();
	private static RoomProvider contentsAllBooks = new LibraryWithAllBooks();
	private static RoomProvider contentsLounges = new LibraryWithLounges();
	
	public LibraryLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RoomProvider roomProviderForFloor(WorldGenerator generator, int floor) {
		if (floor == 0)
			return contentsLounges;
		else switch (chunkOdds.getRandomInt(5)) {
		case 1:
			return contentsNoBooks;
		case 2:
			return contentsSomeBooks;
		case 3:
			return contentsMostlyBooks;
		case 4:
			return contentsAllBooks;
		default:
			return contentsRandom;
		}
	}
	
	@Override
	protected InteriorStyle getFloorsInteriorStyle(int floor) {
		if (floor == 0)
			return InteriorStyle.COLUMNS_OFFICES;
		else
			return super.getFloorsInteriorStyle(floor);
	}

	@Override
	protected InteriorStyle pickInteriorStyle() {
		switch (chunkOdds.getRandomInt(10)) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
			return InteriorStyle.COLUMNS_ONLY;
		case 8:
		case 9:
		default:
			return InteriorStyle.COLUMNS_OFFICES;
		}
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new LibraryLot(platmap, chunkX, chunkZ);
	}

}
