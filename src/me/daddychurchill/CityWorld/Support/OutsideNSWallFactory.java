package me.daddychurchill.CityWorld.Support;

import java.util.Random;

public class OutsideNSWallFactory extends MaterialFactory {

	public OutsideNSWallFactory(Random rand) {
		super(rand);
	}

	public OutsideNSWallFactory(Random rand, SkipStyles style) {
		super(rand, style);
	}

	@Override
	public void placeMaterial(ByteChunk chunk, byte primaryId, byte secondaryId, int x, int y1, int y2, int z) {
		switch (style) {
		case RAISED_RANDOM:
		case RAISED_SINGLE:
		case RAISED_DOUBLE:
			chunk.setBlocks(x, y1, y1 + 1, z, primaryId);
			chunk.setBlocks(x, y1 + 1, y2, z, pickMaterial(primaryId, secondaryId, z));
			break;
		default:
			chunk.setBlocks(x, y1, y2, z, pickMaterial(primaryId, secondaryId, z));
			break;
		}
	}
}
