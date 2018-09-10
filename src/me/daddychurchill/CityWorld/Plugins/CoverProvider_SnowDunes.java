package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.Support.Odds;

public class CoverProvider_SnowDunes extends CoverProvider_Normal {

	public CoverProvider_SnowDunes(Odds odds) {
		super(odds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Material[] getDefaultWoolSet() {
		return Odds.allWhiteWoolBlocks;
	}
	
	@Override
	public Material[] getDefaultTerracottaSet() {
		return Odds.allWhiteTerracottaBlocks;
	}
	
}
