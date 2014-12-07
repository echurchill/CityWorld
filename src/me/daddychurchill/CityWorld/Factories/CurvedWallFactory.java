package me.daddychurchill.CityWorld.Factories;

import me.daddychurchill.CityWorld.Support.ShortChunk;
import me.daddychurchill.CityWorld.Support.Odds;

public class CurvedWallFactory extends MaterialFactory {

	public CurvedWallFactory(Odds odds, boolean decayed) {
		super(odds, decayed);
	}

	public CurvedWallFactory(MaterialFactory other) {
		super(other);
	}

	@Override
	public void placeMaterial(ShortChunk chunk, short primaryId, short secondaryId, int x, int y1, int y2, int z) {
		super.placeMaterial(chunk, primaryId, secondaryId, pickMaterial(primaryId, secondaryId, x * z), x, y1, y2, z);
	}
}
