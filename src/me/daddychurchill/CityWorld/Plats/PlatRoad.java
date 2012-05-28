package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import me.daddychurchill.CityWorld.PlatMap;

public abstract class PlatRoad extends PlatConnected {
	
	public static final int PlatMapRoadInset = 3;

	public PlatRoad(Random random, PlatMap platmap) {
		super(random, platmap);
		
		style = lotStyle.ROAD;
	}
}
