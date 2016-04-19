package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class ShapeProvider_Metro extends ShapeProvider_Normal {

	public ShapeProvider_Metro(CityWorldGenerator generator, Odds odds) {
		super(generator, odds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public DataContext getContext(PlatMap platmap) {
		
		Odds platmapOdds = platmap.getOddsGenerator();
		switch (platmapOdds.getRandomInt(15)) {
		default:
		case 0:
			return parkContext;
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
			return highriseContext;
		case 6:
		case 7:
			return constructionContext;
		case 8:
		case 9:
			return midriseContext;
		case 10:
		case 11:
			return municipalContext;
		case 12:
			return industrialContext;
		case 13:
			return lowriseContext;
		case 14:
			return neighborhoodContext;
		}
	}
	
	@Override
	public double findPerciseY(CityWorldGenerator generator, int blockX, int blockZ) {
		return generator.streetLevel;
	}
}
