package me.daddychurchill.CityWorld.Support;

import java.util.Random;

public abstract class MaterialFactory {

	public enum SkipStyles {RANDOM, SINGLE, DOUBLE};
	public SkipStyles style;
	protected Random rand;
	
	public MaterialFactory() {
		super();
		rand = null;
		style = SkipStyles.SINGLE;
	}

	public MaterialFactory(Random rand) {
		super();
		this.rand = rand;
		style = pickSkipStyle();
	}
	
	public MaterialFactory(Random rand, SkipStyles astyle) {
		super();
		this.rand = rand;
		style = astyle;
	}
	
	protected SkipStyles pickSkipStyle() {
		switch (rand.nextInt(3)) {
		case 1:
			return SkipStyles.SINGLE;
		case 2:
			return SkipStyles.DOUBLE;
		default:
			return SkipStyles.RANDOM;
		}		
	}
	
	public abstract byte pickMaterial(byte primaryId, byte secondaryId, int x, int z);
}
