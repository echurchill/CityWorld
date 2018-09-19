package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.Colors.ColorSet;

public class CoverProvider_SnowDunes extends CoverProvider_Normal {

	public CoverProvider_SnowDunes(Odds odds) {
		super(odds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ColorSet getColorSet() {
		return ColorSet.WHITE;
	}
	
}
