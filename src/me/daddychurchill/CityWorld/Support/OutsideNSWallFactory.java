package me.daddychurchill.CityWorld.Support;

public class OutsideNSWallFactory extends MaterialFactory {

	public OutsideNSWallFactory(Odds odds, boolean decayed) {
		super(odds, decayed);
	}

	public OutsideNSWallFactory(MaterialFactory other) {
		super(other);
	}

	@Override
	public void placeMaterial(ByteChunk chunk, byte primaryId, byte secondaryId, int x, int y1, int y2, int z) {
		super.placeMaterial(chunk, primaryId, secondaryId, pickMaterial(primaryId, secondaryId, z), x, y1, y2, z);
	}
}
