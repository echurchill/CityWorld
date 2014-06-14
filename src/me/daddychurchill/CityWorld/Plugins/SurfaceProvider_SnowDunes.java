package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;
import org.bukkit.util.noise.NoiseGenerator;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class SurfaceProvider_SnowDunes extends SurfaceProvider_Normal {

	public SurfaceProvider_SnowDunes(Odds odds) {
		super(odds);
		// TODO Auto-generated constructor stub
	}
	
	private final static double snowmanOdds = DataContext.oddsPracticallyNeverHappens;
	
	@Override
	public void generateSurfacePoint(WorldGenerator generator, PlatLot lot, RealChunk chunk, CoverProvider foliage, 
			int x, double perciseY, int z, boolean includeTrees) {
		int y = NoiseGenerator.floor(generator.shapeProvider.findFloodY(generator, x, z));
		
		// roll the dice
		double primary = odds.getRandomDouble();
		
		// snowman?
		if (primary < snowmanOdds) {
			if (chunk.isType(x, y, z, Material.SNOW_BLOCK)) {
				
				// find the top of the snow dune
				while (!chunk.isEmpty(x, y, z))
					y++;
				
				// ok create a snowman
				chunk.setBlock(x, y + 1, z, Material.SNOW_BLOCK);
				chunk.setBlock(x, y + 2, z, Material.SNOW_BLOCK);
				chunk.setBlock(x, y + 3, z, Material.PUMPKIN);
			}			
		}
	}
}
