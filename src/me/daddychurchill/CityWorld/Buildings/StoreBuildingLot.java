package me.daddychurchill.CityWorld.Buildings;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Buildings.Populators.StoreWithBooks;
import me.daddychurchill.CityWorld.Buildings.Populators.StoreWithNothing;
import me.daddychurchill.CityWorld.Buildings.Populators.StoreWithRandom;
import me.daddychurchill.CityWorld.Buildings.Populators.StoreWithRegisters;
import me.daddychurchill.CityWorld.Plats.FinishedBuildingLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class StoreBuildingLot extends FinishedBuildingLot {

	private static RoomProvider contentsRandom = new StoreWithRandom();
	private static RoomProvider contentsBooks = new StoreWithBooks();
	private static RoomProvider contentsEmpty = new StoreWithNothing();
	private static RoomProvider contentsRegisters = new StoreWithRegisters();
	
	public enum ContentStyle {RANDOM, BOOKS, EMPTY};
	private ContentStyle contentStyle;

	public StoreBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		contentStyle = pickContentStyle();
	}
	
	protected ContentStyle pickContentStyle() {
		switch (chunkOdds.getRandomInt(5)) {
		case 1:
			return ContentStyle.BOOKS;
		case 2:
			return ContentStyle.RANDOM;
		default: 
			return ContentStyle.EMPTY;
		}
	}

	@Override
	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);
		
		// other bits
		if (result && relative instanceof StoreBuildingLot) {
			StoreBuildingLot relativebuilding = (StoreBuildingLot) relative;

			// any other bits
			contentStyle = relativebuilding.contentStyle;
		}
		
		return result;
	}

	@Override
	protected InteriorStyle getFloorsInteriorStyle(int floor) {
		return InteriorStyle.COLUMNS_OFFICES;
	}

	@Override
	public RoomProvider roomProviderForFloor(WorldGenerator generator, int floor) {
		if (floor == 0)
			return contentsRegisters;
		else
			switch (contentStyle) {
			case BOOKS:
				return contentsBooks;
			case RANDOM:
				return contentsRandom;
			default:
				return contentsEmpty;
			}
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new StoreBuildingLot(platmap, chunkX, chunkZ);
	}

}
