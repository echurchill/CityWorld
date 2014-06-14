package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.util.noise.NoiseGenerator;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class SurfaceProvider_Astral extends SurfaceProvider_Normal {

	public SurfaceProvider_Astral(Odds odds) {
		super(odds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateSurfacePoint(WorldGenerator generator, PlatLot lot, RealChunk chunk, CoverProvider foliage, 
			int x, double perciseY, int z, boolean includeTrees) {
		OreProvider ores = generator.oreProvider;
		int y = NoiseGenerator.floor(perciseY);
		
//		// roll the dice
//		double primary = odds.getRandomDouble();
//		double secondary = odds.getRandomDouble();
		
		// top of the world?
		if (y >= generator.snowLevel) {
			ores.dropSnow(generator, chunk, x, y, z, (byte) NoiseGenerator.floor((perciseY - Math.floor(perciseY)) * 8.0));
		}
	}
}
