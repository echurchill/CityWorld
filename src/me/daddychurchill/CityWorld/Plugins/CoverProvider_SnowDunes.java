package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.Support.Colors.ColorSet;
import me.daddychurchill.CityWorld.Support.Odds;

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
