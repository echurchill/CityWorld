package me.daddychurchill.CityWorld.PlatMaps;

import java.util.Random;

import me.daddychurchill.CityWorld.Context.PlatMapContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.PlatOfficeBuilding;
import me.daddychurchill.CityWorld.Plats.PlatPark;
import me.daddychurchill.CityWorld.Plats.PlatUnfinishedBuilding;

import org.bukkit.World;

public class PlatMapCity extends PlatMapUrban {

	// Instance Constants
	//public int floorsMaximumAbove;
	//public int floorsMaximumBelow;
	
	public PlatMapCity(World world, Random random, PlatMapContext context, int platX, int platZ) {
		super(world, random, context, platX, platZ);

		// backfill with buildings and parks
		for (int x = 0; x < Width; x++) {
			for (int z = 0; z < Width; z++) {
				PlatLot current = platLots[x][z];
				if (current == null) {

					//TODO I need to come up with a more elegant way of doing this!
					// what to build?
					if (platRand.nextInt(context.oddsOfParks) == 0)
						current = new PlatPark(platRand, context);
					else if (platRand.nextInt(context.oddsOfUnfinishedBuildings) == 0)
						current = new PlatUnfinishedBuilding(platRand, context);
					else
						current = new PlatOfficeBuilding(platRand, context);
					
					/* for each plot
					 *   randomly pick a plattype
					 *   see if the "previous chunk" is the same type
					 *     if so make the new plattype connected to the previous type
					 *   if the new plot is shorter than the previous plot or
					 *   if the new plot is shallower than the previous slot
					 *     mark the previous plot to have stairs
					 *   if plot does not have neighbors yet
					 *     mark the plot to have stairs
					 */

					// see if the previous chunk is the same type
					PlatLot previous = null;
					if (x > 0 && current.isConnectable(platLots[x - 1][z])) {
						previous = platLots[x - 1][z];
					} else if (z > 0 && current.isConnectable(platLots[x][z - 1])) {
						previous = platLots[x][z - 1];
					}
					
					// if there was a similar previous one then copy it... maybe
					if (previous != null && !previous.isIsolatedLot(context.oddsOfIsolatedLots)) {
						current.makeConnected(platRand, previous);
					}

					// remember what we did
					platLots[x][z] = current;
				}
			}
		}
	}
}
