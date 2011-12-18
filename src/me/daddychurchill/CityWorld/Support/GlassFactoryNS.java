package me.daddychurchill.CityWorld.Support;

import java.util.Random;

public class GlassFactoryNS extends MaterialFactory {

	public GlassFactoryNS(Random rand) {
		super(rand);
	}

	public GlassFactoryNS(Random rand, SkipStyles style) {
		super(rand, style);
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
