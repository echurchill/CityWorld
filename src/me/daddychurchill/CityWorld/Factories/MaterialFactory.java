package me.daddychurchill.CityWorld.Factories;

import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.Odds;

public abstract class MaterialFactory {

	public enum VerticalStyle {GGGG, WGGG, WGGW, WWWW};
	public enum HorizontalStyle {WG, WGG, WGGG, WWG, WWGG, GGGG, RANDOM};
	public VerticalStyle verticalStyle;
	public HorizontalStyle horizontalStyle;
	protected Boolean decayed;
	protected Odds odds;
	
	protected double oddsOfDecay = DataContext.oddsSomewhatLikely;
	
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
		default:
			return VerticalStyle.GGGG;
		case 1:
			return VerticalStyle.WGGW;
		case 2:
			return VerticalStyle.WGGG;
//		case 3:
//			return VerticalStyle.WWWW;
		}		
	}
	
	protected HorizontalStyle pickHorizontalStyle() {
		switch (odds.getRandomInt(7)) {
		default:
			return HorizontalStyle.RANDOM;
		case 1:
			return HorizontalStyle.WG;
		case 2:
			return HorizontalStyle.WGG;
		case 3:
			return HorizontalStyle.WGGG;
		case 4:
			return HorizontalStyle.WWG;
		case 5:
			return HorizontalStyle.WWGG;
		case 6:
			return HorizontalStyle.GGGG;
		}		
	}
	
	protected byte pickMaterial(byte primaryId, byte secondaryId, int i) {
		switch (horizontalStyle) {
		case WG: 
			return i % 2 == 0 ? primaryId : secondaryId;
		case WGG: 
			return i % 3 == 0 ? primaryId : secondaryId;
		case WGGG: 
			return i % 4 == 0 ? primaryId : secondaryId;
		case WWG: 
			return ((i % 3 == 0) || (i % 3 == 1)) ? primaryId : secondaryId;
		case WWGG: 
			return ((i % 4 == 0) || (i % 4 == 1)) ? primaryId : secondaryId;
		case GGGG:
			return secondaryId;
		default: //case RANDOM:
			return odds.flipCoin() ? primaryId : secondaryId;
		}
	}
	
	protected void decayMaterial(ByteChunk chunk, int x, int y1, int y2, int z) {
		if (decayed && odds.playOdds(oddsOfDecay)) {
			int range = Math.max(1, y2 - y1);
			chunk.setBlock(x, y1 + odds.getRandomInt(range), z, ByteChunk.AIR);
		}
	}
	
	public abstract void placeMaterial(ByteChunk chunk, byte primaryId, byte secondaryId, 
			int x, int y1, int y2, int z);
	
	protected void placeMaterial(ByteChunk chunk, byte primaryId, byte secondaryId, 
			byte glassId, int x, int y1, int y2, int z) {
		switch (verticalStyle) {
		case WGGG:
			chunk.setBlocks(x, y1, y1 + 1, z, primaryId);
			chunk.setBlocks(x, y1 + 1, y2, z, glassId);
			if (glassId == secondaryId)
				decayMaterial(chunk, x, y1 + 1, y2, z);
			break;
		case WGGW:
			chunk.setBlocks(x, y1, y1 + 1, z, primaryId);
			chunk.setBlocks(x, y1 + 1, y2 - 1, z, glassId);
			chunk.setBlocks(x, y2 - 1, y2, z, primaryId);
			if (glassId == secondaryId)
				decayMaterial(chunk, x, y1 + 1, y2 - 1, z);
			break;
		case GGGG:
			chunk.setBlocks(x, y1, y2, z, glassId);
			if (glassId == secondaryId)
				decayMaterial(chunk, x, y1, y2, z);
			break;
		case WWWW:
			chunk.setBlocks(x, y1, y2, z, primaryId);
			break;
		}
	}
}
