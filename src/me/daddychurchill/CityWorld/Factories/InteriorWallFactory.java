package me.daddychurchill.CityWorld.Factories;

import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.Odds;

public class InteriorWallFactory extends MaterialFactory {

	public InteriorWallFactory(Odds odds, boolean decayed) {
		super(odds, decayed);
	}

	public InteriorWallFactory(MaterialFactory other) {
		super(other);
	}

	@Override
	protected VerticalStyle pickVerticalStyle() {
		switch (odds.getRandomInt(4)) {
		default:
			return VerticalStyle.GGGG;
		case 1:
			return VerticalStyle.WGGW;
		case 2:
			return VerticalStyle.WGGG;
		case 3:
			return VerticalStyle.WWWW;
		}		
	}
	
	@Override
	protected HorizontalStyle pickHorizontalStyle() {
		return HorizontalStyle.GGGG;
	}
	
	@Override
	public void placeMaterial(ByteChunk chunk, byte primaryId, byte secondaryId, int x, int y1, int y2, int z) {
		super.placeMaterial(chunk, primaryId, secondaryId, pickMaterial(primaryId, secondaryId, x), x, y1, y2, z);
	}
}
