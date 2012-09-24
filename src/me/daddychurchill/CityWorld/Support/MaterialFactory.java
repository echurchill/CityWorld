package me.daddychurchill.CityWorld.Support;

import org.bukkit.Material;

public abstract class MaterialFactory {

	public enum SkipStyles {RANDOM, SINGLE, DOUBLE, RAISED_RANDOM, RAISED_SINGLE, RAISED_DOUBLE};
	public SkipStyles style;
	protected Boolean decayed;
	protected Odds odds;
	
	protected double oddsOfDecay = 0.30;
	protected byte airId = (byte) Material.AIR.getId();
	
	public MaterialFactory(Odds odds, boolean decayed) {
		super();
		this.odds = odds;
		this.decayed = decayed;
		style = pickSkipStyle();
	}
	
	public MaterialFactory(MaterialFactory other) {
		super();
		odds = other.odds;
		decayed = other.decayed;
		style = other.style;
	}
	
	protected SkipStyles pickSkipStyle() {
		switch (odds.getRandomInt(6)) {
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
			return odds.flipCoin() ? primaryId : secondaryId;
		}
	}
	
	protected void decayMaterial(ByteChunk chunk, int x, int y1, int y2, int z) {
		if (decayed && odds.playOdds(oddsOfDecay)) {
			int range = Math.max(1, y2 - y1);
			chunk.setBlock(x, y1 + odds.getRandomInt(range), z, airId);
		}
	}
	
	public abstract void placeMaterial(ByteChunk chunk, byte primaryId, byte secondaryId, int x, int y1, int y2, int z);
}
