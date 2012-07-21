package me.daddychurchill.CityWorld.Support;

import java.util.Random;

import org.bukkit.Material;

public abstract class MaterialFactory {

	public enum SkipStyles {RANDOM, SINGLE, DOUBLE, RAISED_RANDOM, RAISED_SINGLE, RAISED_DOUBLE};
	public SkipStyles style;
	protected Boolean decayed;
	protected Random random;
	
	protected double decayOdds = 0.30;
	protected byte airId = (byte) Material.AIR.getId();
	
	public MaterialFactory(Random rand, boolean decay) {
		super();
		random = rand;
		decayed = decay;
		style = pickSkipStyle();
	}
	
	public MaterialFactory(MaterialFactory other) {
		super();
		random = other.random;
		decayed = other.decayed;
		style = other.style;
	}
	
	protected SkipStyles pickSkipStyle() {
		switch (random.nextInt(6)) {
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
			return random.nextInt(2) == 0 ? primaryId : secondaryId;
		}
	}
	
	protected void decayMaterial(ByteChunk chunk, int x, int y1, int y2, int z) {
		if (decayed && random.nextDouble() < decayOdds) {
			int range = Math.max(1, y2 - y1);
			chunk.setBlock(x, random.nextInt(range) + y1, z, airId);
		}
	}
	
	public abstract void placeMaterial(ByteChunk chunk, byte primaryId, byte secondaryId, int x, int y1, int y2, int z);
}
