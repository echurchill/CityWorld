package me.daddychurchill.CityWorld.Rooms;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import me.daddychurchill.CityWorld.Support.BadMagic;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class StorageEmptyChestsRoom extends StorageFilledChestsRoom {

	public StorageEmptyChestsRoom() {
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected void drawChest(CityWorldGenerator generator, RealBlocks chunk, Odds odds, BadMagic.General direction, int x, int y, int z) {
		chunk.setChest(generator, x, y, z, direction, odds, generator.lootProvider, LootLocation.EMPTY);
	}

}
