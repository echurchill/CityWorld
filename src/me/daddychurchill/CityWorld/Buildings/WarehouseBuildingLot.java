package me.daddychurchill.CityWorld.Buildings;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Buildings.Populators.WarehouseWithBooks;
import me.daddychurchill.CityWorld.Buildings.Populators.WarehouseWithBoxes;
import me.daddychurchill.CityWorld.Buildings.Populators.WarehouseWithNothing;
import me.daddychurchill.CityWorld.Buildings.Populators.WarehouseWithRandom;
import me.daddychurchill.CityWorld.Buildings.Populators.WarehouseWithStacks;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.FinishedBuildingLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class WarehouseBuildingLot extends FinishedBuildingLot {
	
	private static RoomProvider contentsRandom = new WarehouseWithRandom();
	private static RoomProvider contentsBooks = new WarehouseWithBooks();
	private static RoomProvider contentsBoxes = new WarehouseWithBoxes();
	private static RoomProvider contentsEmpty = new WarehouseWithNothing();
//	private static RoomProvider contentsChests = new WarehouseWithChests();
	private static RoomProvider contentsStacks = new WarehouseWithStacks();
	
	public enum ContentStyle {RANDOM, BOOKS, BOXES, EMPTY, STACKS}; // CHESTS
	private ContentStyle contentStyle;

	public WarehouseBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		firstFloorHeight = firstFloorHeight * 2;
		height = 1;
		depth = 0;
		roofStyle = chunkOdds.flipCoin() ? RoofStyle.EDGED : RoofStyle.FLATTOP;
		roofFeature = roofFeature == RoofFeature.ANTENNAS ? RoofFeature.CONDITIONERS : roofFeature;
		
		contentStyle = pickContentStyle();
	}
	
	protected ContentStyle pickContentStyle() {
		switch (chunkOdds.getRandomInt(5)) {
		case 1:
			return ContentStyle.BOOKS;
		case 2:
			return ContentStyle.BOXES;
		case 3:
			return ContentStyle.STACKS;
		case 4:
			return ContentStyle.RANDOM;
		default: 
			return ContentStyle.EMPTY;
		}
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
	public RoomProvider roomProviderForFloor(WorldGenerator generator, int floor) {
		switch (contentStyle) {
		case BOOKS:
			return contentsBooks;
		case BOXES:
			return contentsBoxes;
		case STACKS:
			return contentsStacks;
		case RANDOM:
			return contentsRandom;
		default:
			return contentsEmpty;
		}
	}
	
	@Override
	protected void calculateOptions(DataContext context) {
		
		// how do the walls inset?
		insetWallWE = 1;
		insetWallNS = 1;
		
		// what about the ceiling?
		insetCeilingWE = insetWallWE;
		insetCeilingNS = insetWallNS;
		
		// nudge in a bit more as we go up
		insetInsetMidAt = 1;
		insetInsetHighAt = 1;
		insetInsetted = false;
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

	@Override
	protected RoofStyle pickRoofStyle() {
		switch (chunkOdds.getRandomInt(3)) {
		case 1:
			return RoofStyle.EDGED;
		default:
			return RoofStyle.FLATTOP;
		}
	}
	
	
}
