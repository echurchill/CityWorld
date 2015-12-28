package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.BadMagic.Facing;

public abstract class PlatRoom {

	public PlatRoom() {
		// TODO Auto-generated constructor stub
	}

	public abstract void drawFixture(CityWorldGenerator generator, RealBlocks chunk, 
			Odds odds, int floor, int x, int y, int z, int width, 
			int height, int depth, Facing sideWithWall, Material materialWall, Material materialGlass);
}
