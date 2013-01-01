package me.daddychurchill.CityWorld.Support;

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
		case 1:
			return VerticalStyle.INSET_GLASS;
		case 2:
			return VerticalStyle.RAISED_GLASS;
		case 3:
			return VerticalStyle.ONLY_WALL;
		default:
			return VerticalStyle.ONLY_GLASS;
		}		
	}
	
	@Override
	protected HorizontalStyle pickHorizontalStyle() {
		return HorizontalStyle.SOLID;
	}
	
	@Override
	public void placeMaterial(ByteChunk chunk, byte primaryId, byte secondaryId, int x, int y1, int y2, int z) {
		super.placeMaterial(chunk, primaryId, secondaryId, pickMaterial(primaryId, secondaryId, x), x, y1, y2, z);
	}
}
