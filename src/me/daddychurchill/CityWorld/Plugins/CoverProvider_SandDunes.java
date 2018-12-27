package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.Support.Colors.ColorSet;
import me.daddychurchill.CityWorld.Support.Odds;

public class CoverProvider_SandDunes extends CoverProvider_Decayed {

	public CoverProvider_SandDunes(Odds odds) {
		super(odds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ColorSet getColorSet() {
		return ColorSet.TAN;
	}

}
