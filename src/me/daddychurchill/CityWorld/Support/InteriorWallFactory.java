package me.daddychurchill.CityWorld.Support;

public class InteriorWallFactory extends MaterialFactory {

	public InteriorWallFactory(Odds odds, boolean decayed) {
		super(odds, decayed);
	}

	public InteriorWallFactory(MaterialFactory other) {
		super(other);
	}

	@Override
	public void placeMaterial(ByteChunk chunk, byte primaryId, byte secondaryId, int x, int y1, int y2, int z) {
		switch (style) {
		case SINGLE:
			chunk.setBlocks(x, y1, y2 - 1, z, primaryId);
			chunk.setBlocks(x, y2 - 1, y2, z, secondaryId);
			break;
		case DOUBLE:
			chunk.setBlocks(x, y1, y2 - 2, z, primaryId);
			chunk.setBlocks(x, y2 - 2, y2, z, secondaryId);
			break;
		case RAISED_RANDOM:
			if (x % 2 == 0 || z % 2 == 0) {
				chunk.setBlocks(x, y1, y2 - 1, z, secondaryId);
				chunk.setBlocks(x, y2 - 1, y2, z, primaryId);
			} else {
				chunk.setBlocks(x, y1, y2 - 2, z, secondaryId);
				chunk.setBlocks(x, y2 - 2, y2, z, primaryId);
			}
			break;
		case RAISED_SINGLE:
			chunk.setBlocks(x, y1, y2 - 1, z, secondaryId);
			chunk.setBlocks(x, y2 - 1, y2, z, primaryId);
			break;
		case RAISED_DOUBLE:
			chunk.setBlocks(x, y1, y2 - 2, z, secondaryId);
			chunk.setBlocks(x, y2 - 2, y2, z, primaryId);
			break;
		case RANDOM:
			if (x % 2 == 0 || z % 2 == 0) {
				chunk.setBlocks(x, y1, y2 - 1, z, primaryId);
				chunk.setBlocks(x, y2 - 1, y2, z, secondaryId);
			} else {
				chunk.setBlocks(x, y1, y2 - 2, z, primaryId);
				chunk.setBlocks(x, y2 - 2, y2, z, secondaryId);
			}
			break;
		}
		if (decayed)
			decayMaterial(chunk, x, y1, y2, z);
	}

}
