package me.daddychurchill.CityWorld.Support;

import java.util.Random;

public class GlassFactoryZ extends MaterialFactory {

	public GlassFactoryZ(Random rand) {
		super();
	}

	public GlassFactoryZ(Random rand, SkipStyles style) {
		super();
	}

	@Override
	public byte pickMaterial(byte primaryId, byte secondaryId, int x, int z) {
		switch (style) {
		case SINGLE: 
			return z % 2 == 0 ? primaryId : secondaryId;
		case DOUBLE: 
			return z % 3 == 0 ? primaryId : secondaryId;
		default:	 
			return rand.nextInt(2) == 0 ? primaryId : secondaryId;
		}
	}
}
