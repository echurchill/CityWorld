package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.util.noise.NoiseGenerator;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

public class SurfaceProvider_SnowDunes extends SurfaceProvider_Flooded {

	public SurfaceProvider_SnowDunes(Odds odds) {
		super(odds);
		// TODO Auto-generated constructor stub
	}
	
	private final static double snowmanOdds = Odds.oddsNearlyNeverGoingToHappen;
	
	@Override
	protected void generateNormalPoint(CityWorldGenerator generator, PlatLot lot, SupportBlocks chunk,
			CoverProvider foliage, int x, double perciseY, int z, boolean includeTrees) {
		
		super.generateNormalPoint(generator, lot, chunk, foliage, x, perciseY, z, includeTrees);

		int y = NoiseGenerator.floor(perciseY);
		if (!chunk.isOfTypes(x, y, z, Material.SNOW, Material.SNOW_BLOCK))
			generator.oreProvider.dropSnow(generator, chunk, x, NoiseGenerator.floor(perciseY) + 15, z);
	}
	
	@Override
	protected void generateFloodedPoint(CityWorldGenerator generator, PlatLot lot, SupportBlocks chunk, 
			CoverProvider foliage, int x, int y, int z, int floodY) {
		
		// snowman?
		if (odds.playOdds(snowmanOdds)) {
				
			// ok create a snowman above the snow
			int manY = chunk.findFirstEmptyAbove(x, floodY - 1, z);
			if (chunk.isType(x, manY - 1, z, Material.SNOW_BLOCK))
				chunk.spawnBuddy(generator, odds, x, manY + 1, z, EntityType.SNOWMAN);
		}			
	}
}
