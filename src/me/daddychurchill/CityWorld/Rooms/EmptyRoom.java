package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.BadMagic.Facing;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class EmptyRoom extends PlatRoom {

	public EmptyRoom() {
		
		// nothing to do in this case
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, 
			Odds odds, int floor, int x, int y, int z, int width, 
			int height, int depth, Facing sideWithWall, Material materialWall, Material materialGlass) {

		// nothing to do in this case
		
	}

}
