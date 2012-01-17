package me.daddychurchill.CityWorld.PlatMaps;

import java.util.Random;

import me.daddychurchill.CityWorld.Context.ContextUrban;
import me.daddychurchill.CityWorld.Plats.PlatBiome;
import me.daddychurchill.CityWorld.Plats.PlatLot;

import org.bukkit.World;

public class PlatMapVanilla extends PlatMap {

	public PlatMapVanilla(World world, Random random, ContextUrban context, int platX, int platZ) {
		super(world, random, context, platX, platZ);

		for (int x = 0; x < Width; x++) {
			for (int z = 0; z < Width; z++) {
				PlatLot current = platLots[x][z];
				if (current == null) {
					platLots[x][z] = new PlatBiome(random, context);
				}
			}
		}
	}

}
