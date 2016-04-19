package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class ShapeProvider_Nature extends ShapeProvider_Normal {

	public ShapeProvider_Nature(CityWorldGenerator generator, Odds odds) {
		super(generator, odds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public DataContext getContext(PlatMap platmap) {
		return natureContext;
	}
}
