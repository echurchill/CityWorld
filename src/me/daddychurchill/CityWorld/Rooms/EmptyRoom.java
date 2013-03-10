package me.daddychurchill.CityWorld.Rooms;

import me.daddychurchill.CityWorld.Support.Direction.Facing;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class EmptyRoom extends PlatRoom {

	public EmptyRoom() {
		
		// nothing to do in this case
	}

	@Override
	public void Draw(RealChunk chunk, Odds odds, 
			int x, int y, int z, int width, int height, int depth, 
			Facing sideWithWall, byte wallId, byte glassId) {

		// nothing to do in this case
		
	}

}
