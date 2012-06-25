package me.daddychurchill.CityWorld.Support;

import java.util.Random;

public abstract class MaterialFactory {

	public enum SkipStyles {RANDOM, SINGLE, DOUBLE, RAISED_RANDOM, RAISED_SINGLE, RAISED_DOUBLE};
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
		switch (rand.nextInt(6)) {
		case 1:
			return SkipStyles.SINGLE;
		case 2:
			return SkipStyles.DOUBLE;
		case 3:
			return SkipStyles.RAISED_RANDOM;
		case 4:
			return SkipStyles.RAISED_SINGLE;
		case 5:
			return SkipStyles.RAISED_DOUBLE;
		default:
			return SkipStyles.RANDOM;
		}		
	}
	
	protected byte pickMaterial(byte primaryId, byte secondaryId, int i) {
		switch (style) {
		case SINGLE: 
		case RAISED_SINGLE: 
			return i % 2 == 0 ? primaryId : secondaryId;
		case DOUBLE: 
		case RAISED_DOUBLE: 
			return i % 3 == 0 ? primaryId : secondaryId;
		default:	 
			return rand.nextInt(2) == 0 ? primaryId : secondaryId;
		}
	}
	
	public abstract void placeMaterial(ByteChunk chunk, byte primaryId, byte secondaryId, int x, int y1, int y2, int z);
}
