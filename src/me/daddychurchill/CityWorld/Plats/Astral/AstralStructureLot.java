package me.daddychurchill.CityWorld.Plats.Astral;

import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public abstract class AstralStructureLot extends AstralLot {

	public AstralStructureLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ, Odds.oddsAlwaysGoingToHappen);
		
		this.style = LotStyle.STRUCTURE;
	}

}
