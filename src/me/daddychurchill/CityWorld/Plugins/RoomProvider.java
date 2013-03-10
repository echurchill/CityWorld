package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Rooms.PlatRoom;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.Direction.Facing;

import java.util.ArrayList;
import java.util.List;

public abstract class RoomProvider {

	protected List<PlatRoom> roomTypes;
	
	public RoomProvider() {
		super();
		roomTypes = new ArrayList<PlatRoom>();
	}
	
	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	public static RoomProvider loadProvider(WorldGenerator generator) {

		RoomProvider provider = null;
		
//		// need something like PhatLoot but for room generators
		provider = new RoomProvider_Office();
	
		return provider;
	}
	
	private PlatRoom getRandomRoomGenerator(Odds odds) {
		int index = odds.getRandomInt(roomTypes.size());
		return roomTypes.get(index);
	}

	public void Draw(RealChunk chunk, Odds odds, 
			int x, int y, int z, int width, int height, int depth,
			Facing sideWithWall, byte wallId, byte glassId) {
		
		PlatRoom roomGen = getRandomRoomGenerator(odds);
		if (roomGen != null)
			roomGen.Draw(chunk, odds, x, y, z, width, height, depth, sideWithWall, wallId, glassId);
	}
}
