package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.Support.Odds;

public class CoverProvider_SandDunes extends CoverProvider_Decayed {

	public CoverProvider_SandDunes(Odds odds) {
		super(odds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Material[] getDefaultWoolSet() {
		return Odds.allTanWoolBlocks;
	}
	
	@Override
	public Material[] getDefaultTerracottaSet() {
		return Odds.allTanTerracottaBlocks;
	}
	
}
