package me.daddychurchill.CityWorld.Factories;

import me.daddychurchill.CityWorld.Support.ShortChunk;
import me.daddychurchill.CityWorld.Support.Odds;

public class OutsideWEWallFactory extends MaterialFactory {

	public OutsideWEWallFactory(Odds odds, boolean decayed) {
		super(odds, decayed);
	}

	public OutsideWEWallFactory(MaterialFactory other) {
		super(other);
	}

	@Override
	public void placeMaterial(ShortChunk chunk, short primaryId, short secondaryId, int x, int y1, int y2, int z) {
		super.placeMaterial(chunk, primaryId, secondaryId, pickMaterial(primaryId, secondaryId, x), x, y1, y2, z);
	}
}
