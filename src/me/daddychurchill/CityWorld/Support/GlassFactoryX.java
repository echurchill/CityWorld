package me.daddychurchill.CityWorld.Support;

import java.util.Random;

public class GlassFactoryX extends MaterialFactory {

	public GlassFactoryX(Random rand) {
		// TODO Auto-generated constructor stub
	}

	public GlassFactoryX(Random rand, SkipStyles style) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte pickMaterial(byte primaryId, byte secondaryId, int x, int z) {
		switch (style) {
		case SINGLE: 
			return x % 2 == 0 ? primaryId : secondaryId;
		case DOUBLE: 
			return x % 3 == 0 ? primaryId : secondaryId;
		default:	 
			return rand.nextInt(2) == 0 ? primaryId : secondaryId;
		}
	}
}
