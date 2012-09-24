package me.daddychurchill.CityWorld.Support;

public class OutsideWEWallFactory extends MaterialFactory {

	public OutsideWEWallFactory(Odds odds, boolean decayed) {
		super(odds, decayed);
	}

	public OutsideWEWallFactory(MaterialFactory other) {
		super(other);
	}

	@Override
	public void placeMaterial(ByteChunk chunk, byte primaryId, byte secondaryId, int x, int y1, int y2, int z) {
		byte matId = pickMaterial(primaryId, secondaryId, x);
		switch (style) {
		case RAISED_RANDOM:
		case RAISED_SINGLE:
		case RAISED_DOUBLE:
			chunk.setBlocks(x, y1, y1 + 1, z, primaryId);
			chunk.setBlocks(x, y1 + 1, y2, z, matId);
			if (matId == secondaryId)
				decayMaterial(chunk, x, y1 + 1, y2, z);
			break;
		default:
			chunk.setBlocks(x, y1, y2, z, matId);
			if (matId == secondaryId)
				decayMaterial(chunk, x, y1, y2, z);
			break;
		}
	}
}
