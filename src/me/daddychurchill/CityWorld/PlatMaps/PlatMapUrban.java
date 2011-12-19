package me.daddychurchill.CityWorld.PlatMaps;

import java.util.Random;

import me.daddychurchill.CityWorld.Context.ContextUrban;
import me.daddychurchill.CityWorld.Plats.PlatRoad;
import me.daddychurchill.CityWorld.Plats.PlatRoadPaved;
import me.daddychurchill.CityWorld.Plats.PlatStatue;

import org.bukkit.World;

public abstract class PlatMapUrban extends PlatMap {

	public PlatMapUrban(World world, Random random, ContextUrban context, int platX, int platZ) {
		super(world, random, context, platX, platZ);

		//TODO rivers and railroads?
		
		// for each cardinal direction see if there is a road there
		boolean northroad = platRand.nextInt(context.oddsOfMissingRoad) != 0;
		boolean southroad = platRand.nextInt(context.oddsOfMissingRoad) != 0;
		boolean westroad = platRand.nextInt(context.oddsOfMissingRoad) != 0;
		boolean eastroad = platRand.nextInt(context.oddsOfMissingRoad) != 0;

		// calculate the roads
		for (int i = 0; i < Width; i++) {
			if (i < PlatRoad.PlatMapRoadInset || i >= Width - PlatRoad.PlatMapRoadInset) {
				platLots[i][PlatRoad.PlatMapRoadInset - 1] = new PlatRoadPaved(platRand, context);
				platLots[i][Width - PlatRoad.PlatMapRoadInset] = new PlatRoadPaved(platRand, context);
				platLots[PlatRoad.PlatMapRoadInset - 1][i] = new PlatRoadPaved(platRand, context);
				platLots[Width - PlatRoad.PlatMapRoadInset][i] = new PlatRoadPaved(platRand, context);
			} else {
				if (northroad)
					platLots[i][Width - PlatRoad.PlatMapRoadInset] = new PlatRoadPaved(platRand, context);
				if (southroad)
					platLots[i][PlatRoad.PlatMapRoadInset - 1] = new PlatRoadPaved(platRand, context);
				if (westroad)
					platLots[PlatRoad.PlatMapRoadInset - 1][i] = new PlatRoadPaved(platRand, context);
				if (eastroad)
					platLots[Width - PlatRoad.PlatMapRoadInset][i] = new PlatRoadPaved(platRand, context);
			}
		}
		
		// for each intersection see if a roundabout exists
		if (platRand.nextInt(context.oddsOfRoundAbouts) == 0)
			PlaceRoundAbout(PlatRoad.PlatMapRoadInset - 1, PlatRoad.PlatMapRoadInset - 1);
		if (platRand.nextInt(context.oddsOfRoundAbouts) == 0)
			PlaceRoundAbout(PlatRoad.PlatMapRoadInset - 1, Width - PlatRoad.PlatMapRoadInset);
		if (platRand.nextInt(context.oddsOfRoundAbouts) == 0)
			PlaceRoundAbout(Width - PlatRoad.PlatMapRoadInset, PlatRoad.PlatMapRoadInset - 1);
		if (platRand.nextInt(context.oddsOfRoundAbouts) == 0)
			PlaceRoundAbout(Width - PlatRoad.PlatMapRoadInset, Width - PlatRoad.PlatMapRoadInset);
	}
	
	private void PlaceRoundAbout(int x, int z) {
		platLots[x - 1][z - 1] = new PlatRoadPaved(platRand, context);
		platLots[x - 1][z    ] = new PlatRoadPaved(platRand, context);
		platLots[x - 1][z + 1] = new PlatRoadPaved(platRand, context);
		platLots[x    ][z - 1] = new PlatRoadPaved(platRand, context);
		
		platLots[x    ][z    ] = new PlatStatue(platRand, context);
		
		platLots[x    ][z + 1] = new PlatRoadPaved(platRand, context);
		platLots[x + 1][z - 1] = new PlatRoadPaved(platRand, context);
		platLots[x + 1][z    ] = new PlatRoadPaved(platRand, context);
		platLots[x + 1][z + 1] = new PlatRoadPaved(platRand, context);
	}
}
