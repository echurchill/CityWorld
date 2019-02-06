package me.daddychurchill.CityWorld.Plugins;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Rooms.PlatRoom;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public abstract class RoomProvider extends Provider {

	protected final List<PlatRoom> roomTypes;

	protected RoomProvider() {
		super();
		roomTypes = new ArrayList<>();
	}

	private PlatRoom getRandomRoomGenerator(Odds odds) {
		int index = odds.getRandomInt(roomTypes.size());
		return roomTypes.get(index);
	}

	public void drawFixtures(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y, int z,
			int width, int height, int depth, BlockFace sideWithWall, Material materialWall, Material materialGlass) {

		PlatRoom roomGen = getRandomRoomGenerator(odds);
		if (roomGen != null)
			roomGen.drawFixture(generator, chunk, odds, floor, x, y, z, width, height, depth, sideWithWall,
					materialWall, materialGlass);
	}
}
