package me.daddychurchill.CityWorld.Plats.Astral;

import me.daddychurchill.CityWorld.Plats.IsolatedLot;
import me.daddychurchill.CityWorld.Support.PlatMap;

public abstract class AstralStructureLot extends IsolatedLot {

	public AstralStructureLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		this.style = LotStyle.STRUCTURE;
	}

}
