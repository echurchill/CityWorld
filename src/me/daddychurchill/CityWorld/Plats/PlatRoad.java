package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import me.daddychurchill.CityWorld.PlatMap;

public abstract class PlatRoad extends PlatConnected {
	
	public static final int PlatMapRoadInset = 3;

	public PlatRoad(Random random, PlatMap platmap, int chunkX, int chunkZ) {
		super(random, platmap, chunkX, chunkZ);
		
		style = LotStyle.ROAD;
	}
}
