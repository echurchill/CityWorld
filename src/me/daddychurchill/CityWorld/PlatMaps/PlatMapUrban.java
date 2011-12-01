package me.daddychurchill.CityWorld.PlatMaps;

import java.util.Random;

import me.daddychurchill.CityWorld.Plats.PlatRoad;
import me.daddychurchill.CityWorld.Plats.PlatRoadPaved;
import me.daddychurchill.CityWorld.Plats.PlatStatue;

import org.bukkit.World;

public abstract class PlatMapUrban extends PlatMap {

	// Class Odds
	static protected int oddsOfMissingRoad = 2; // roads are missing 1/n of the time
	static protected int oddsOfRoundAbouts = 4; // roundabouts are created 1/n of the time
	
	public PlatMapUrban(World world, Random random, int platX, int platZ) {
		super(world, random, platX, platZ);

		//TODO rivers and railroads?
		
		// for each cardinal direction see if there is a road there
		boolean northroad = platRand.nextInt(oddsOfMissingRoad) == 0;
		boolean southroad = platRand.nextInt(oddsOfMissingRoad) == 0;
		boolean westroad = platRand.nextInt(oddsOfMissingRoad) == 0;
		boolean eastroad = platRand.nextInt(oddsOfMissingRoad) == 0;

		// calculate the roads
		for (int i = 0; i < Width; i++) {
			if (i < PlatRoad.PlatMapRoadInset || i >= Width - PlatRoad.PlatMapRoadInset) {
				platLots[i][PlatRoad.PlatMapRoadInset - 1] = new PlatRoadPaved(platRand);
				platLots[i][Width - PlatRoad.PlatMapRoadInset] = new PlatRoadPaved(platRand);
				platLots[PlatRoad.PlatMapRoadInset - 1][i] = new PlatRoadPaved(platRand);
				platLots[Width - PlatRoad.PlatMapRoadInset][i] = new PlatRoadPaved(platRand);
			} else {
				if (northroad)
					platLots[i][Width - PlatRoad.PlatMapRoadInset] = new PlatRoadPaved(platRand);
				if (southroad)
					platLots[i][PlatRoad.PlatMapRoadInset - 1] = new PlatRoadPaved(platRand);
				if (westroad)
					platLots[PlatRoad.PlatMapRoadInset - 1][i] = new PlatRoadPaved(platRand);
				if (eastroad)
					platLots[Width - PlatRoad.PlatMapRoadInset][i] = new PlatRoadPaved(platRand);
			}
		}
		
		// for each intersection see if a roundabout exists
		if (platRand.nextInt(oddsOfRoundAbouts) == 0)
			PlaceRoundAbout(PlatRoad.PlatMapRoadInset - 1, PlatRoad.PlatMapRoadInset - 1);
		if (platRand.nextInt(oddsOfRoundAbouts) == 0)
			PlaceRoundAbout(PlatRoad.PlatMapRoadInset - 1, Width - PlatRoad.PlatMapRoadInset);
		if (platRand.nextInt(oddsOfRoundAbouts) == 0)
			PlaceRoundAbout(Width - PlatRoad.PlatMapRoadInset, PlatRoad.PlatMapRoadInset - 1);
		if (platRand.nextInt(oddsOfRoundAbouts) == 0)
			PlaceRoundAbout(Width - PlatRoad.PlatMapRoadInset, Width - PlatRoad.PlatMapRoadInset);
	}
	
	private void PlaceRoundAbout(int x, int z) {
		platLots[x - 1][z - 1] = new PlatRoadPaved(platRand);
		platLots[x - 1][z    ] = new PlatRoadPaved(platRand);
		platLots[x - 1][z + 1] = new PlatRoadPaved(platRand);
		platLots[x    ][z - 1] = new PlatRoadPaved(platRand);
		
		platLots[x    ][z    ] = new PlatStatue(platRand);
		
		platLots[x    ][z + 1] = new PlatRoadPaved(platRand);
		platLots[x + 1][z - 1] = new PlatRoadPaved(platRand);
		platLots[x + 1][z    ] = new PlatRoadPaved(platRand);
		platLots[x + 1][z + 1] = new PlatRoadPaved(platRand);
	}
}
