package me.daddychurchill.CityWorld.Rooms;

import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.Direction.Facing;

public abstract class PlatRoom {

	public PlatRoom() {
		// TODO Auto-generated constructor stub
	}

	public abstract void Draw(RealChunk chunk, Odds odds, 
			int x, int y, int z, int width, int height, int depth, 
			Facing sideWithWall, byte wallId, byte glassId);
}
