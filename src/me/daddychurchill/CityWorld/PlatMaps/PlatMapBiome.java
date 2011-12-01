package me.daddychurchill.CityWorld.PlatMaps;

import java.util.Random;

import me.daddychurchill.CityWorld.Plats.PlatBiome;
import me.daddychurchill.CityWorld.Plats.PlatLot;

import org.bukkit.World;

public class PlatMapBiome extends PlatMap {

	public PlatMapBiome(World world, Random random, int platX, int platZ) {
		super(world, random, platX, platZ);

		for (int x = 0; x < Width; x++) {
			for (int z = 0; z < Width; z++) {
				PlatLot current = platLots[x][z];
				if (current == null) {
					platLots[x][z] = new PlatBiome(random);
				}
			}
		}
	}

}
