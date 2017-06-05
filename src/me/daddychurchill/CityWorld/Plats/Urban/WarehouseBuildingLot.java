package me.daddychurchill.CityWorld.Plats.Urban;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.Populators.WarehouseWithBooks;
import me.daddychurchill.CityWorld.Rooms.Populators.WarehouseWithBoxes;
import me.daddychurchill.CityWorld.Rooms.Populators.WarehouseWithChests;
import me.daddychurchill.CityWorld.Rooms.Populators.WarehouseWithNothing;
import me.daddychurchill.CityWorld.Rooms.Populators.WarehouseWithRandom;
import me.daddychurchill.CityWorld.Rooms.Populators.WarehouseWithStacks;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

public class WarehouseBuildingLot extends IndustrialBuildingLot {
	
	private static RoomProvider contentsRandom = new WarehouseWithRandom();
	private static RoomProvider contentsBooks = new WarehouseWithBooks();
	private static RoomProvider contentsBoxes = new WarehouseWithBoxes();
	private static RoomProvider contentsEmpty = new WarehouseWithNothing();
	private static RoomProvider contentsChests = new WarehouseWithChests();
	private static RoomProvider contentsStacks = new WarehouseWithStacks();
	
	public enum ContentStyle {RANDOM, BOOKS, BOXES, EMPTY, STACKS, CHESTS};
	private ContentStyle contentStyle;

	public WarehouseBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		contentStyle = pickContentStyle();
		firstFloorHeight = aboveFloorHeight * 2;
	}
	
	protected ContentStyle pickContentStyle() {
		ContentStyle[] values = ContentStyle.values();
		return values[chunkOdds.getRandomInt(values.length)];
	}
	
	@Override
	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);
		
		// other bits
		if (result && relative instanceof WarehouseBuildingLot) {
			WarehouseBuildingLot relativebuilding = (WarehouseBuildingLot) relative;

			// any other bits
			contentStyle = relativebuilding.contentStyle;
		}
		
		return result;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new WarehouseBuildingLot(platmap, chunkX, chunkZ);
	}

	@Override
	public RoomProvider roomProviderForFloor(CityWorldGenerator generator, SupportBlocks chunk, int floor, int floorY) {
		switch (contentStyle) {
		default:
		case RANDOM:
			return contentsRandom;
		case BOOKS:
			return contentsBooks;
		case BOXES:
			return contentsBoxes;
		case EMPTY:
			return contentsEmpty;
		case STACKS:
			return contentsStacks;
		case CHESTS:
			return contentsChests;
		}
	}
	
	@Override
	protected InteriorStyle pickInteriorStyle() {
		switch (chunkOdds.getRandomInt(10)) {
		case 1:
			return InteriorStyle.RANDOM;
		case 2:
		case 3:
			return InteriorStyle.COLUMNS_ONLY;
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		default:
			return InteriorStyle.COLUMNS_OFFICES;
		}
	}
}
