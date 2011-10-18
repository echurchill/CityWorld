package me.daddychurchill.CityWorld;

import java.util.Random;

public class GlassMakerX extends GlassMaker {

	public GlassMakerX(Random rand) {
		super(rand);
		// TODO Auto-generated constructor stub
	}

	public GlassMakerX(Random rand, Styles astyle) {
		super(rand, astyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public byte pickMaterial(byte wallId, byte glassId, int x, int z) {
		switch (style) {
		case SINGLE: 
			return x % 2 == 0 ? wallId : glassId;
		case DOUBLE: 
			return x % 3 == 0 ? wallId : glassId;
		default:	 
			return rand.nextInt(2) == 0 ? wallId : glassId;
		}
	}
}
