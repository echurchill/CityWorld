package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

abstract class StorageTypeRoom extends StorageRoom {

	private final Material materialType;

	StorageTypeRoom(Material type) {
		super();
		materialType = type;
	}

	void setStorageBlocks(CityWorldGenerator generator, SupportBlocks chunk, Odds odds, int x, int y1, int y2,
			int z) {
		if (materialType == Material.PISTON) {
			chunk.setBlocks(x, x + 1, y1, y2, z, z + 1, materialType, BlockFace.UP);
		} else {
			chunk.setBlocks(x, x + 1, y1, y2, z, z + 1, materialType);
		}
	}

}
