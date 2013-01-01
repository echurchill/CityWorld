package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.Context.DataContext;

import org.bukkit.Material;

public abstract class MaterialFactory {

	public enum VerticalStyle {ONLY_GLASS, RAISED_GLASS, INSET_GLASS, ONLY_WALL};
	public enum HorizontalStyle {SINGLE, DOUBLE, TRIPLE, RANDOM, SOLID};
	public VerticalStyle verticalStyle;
	public HorizontalStyle horizontalStyle;
	protected Boolean decayed;
	protected Odds odds;
	
	protected double oddsOfDecay = DataContext.oddsSomewhatLikely;
	protected byte airId = (byte) Material.AIR.getId();
	
	public MaterialFactory(Odds odds, boolean decayed) {
		super();
		this.odds = odds;
		this.decayed = decayed;
		verticalStyle = pickVerticalStyle();
		horizontalStyle = pickHorizontalStyle();
	}
	
	public MaterialFactory(MaterialFactory other) {
		super();
		odds = other.odds;
		decayed = other.decayed;
		verticalStyle = other.verticalStyle;
		horizontalStyle = other.horizontalStyle;
	}
	
	protected VerticalStyle pickVerticalStyle() {
		switch (odds.getRandomInt(3)) {
		case 1:
			return VerticalStyle.INSET_GLASS;
		case 2:
			return VerticalStyle.RAISED_GLASS;
//		case 3:
//			return VerticalStyle.ONLY_WALL;
		default:
			return VerticalStyle.ONLY_GLASS;
		}		
	}
	
	protected HorizontalStyle pickHorizontalStyle() {
		switch (odds.getRandomInt(5)) {
		case 1:
			return HorizontalStyle.SINGLE;
		case 2:
			return HorizontalStyle.DOUBLE;
		case 3:
			return HorizontalStyle.TRIPLE;
		case 4:
			return HorizontalStyle.SOLID;
		default:
			return HorizontalStyle.RANDOM;
		}		
	}
	
	protected byte pickMaterial(byte primaryId, byte secondaryId, int i) {
		switch (horizontalStyle) {
		case SINGLE: 
			return i % 2 == 0 ? primaryId : secondaryId;
		case DOUBLE: 
			return i % 3 == 0 ? primaryId : secondaryId;
		case TRIPLE: 
			return i % 4 == 0 ? primaryId : secondaryId;
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
	
	protected void placeMaterial(ByteChunk chunk, byte primaryId, byte secondaryId, byte glassId, int x, int y1, int y2, int z) {
		switch (verticalStyle) {
		case RAISED_GLASS:
			chunk.setBlocks(x, y1, y1 + 1, z, primaryId);
			chunk.setBlocks(x, y1 + 1, y2, z, glassId);
			if (glassId == secondaryId)
				decayMaterial(chunk, x, y1 + 1, y2, z);
			break;
		case INSET_GLASS:
			chunk.setBlocks(x, y1, y1 + 1, z, primaryId);
			chunk.setBlocks(x, y1 + 1, y2 - 1, z, glassId);
			chunk.setBlocks(x, y2 - 1, y2, z, primaryId);
			if (glassId == secondaryId)
				decayMaterial(chunk, x, y1 + 1, y2 - 1, z);
			break;
		case ONLY_GLASS:
			chunk.setBlocks(x, y1, y2, z, glassId);
			if (glassId == secondaryId)
				decayMaterial(chunk, x, y1, y2, z);
			break;
		case ONLY_WALL:
			chunk.setBlocks(x, y1, y2, z, primaryId);
			break;
		}
	}
}
