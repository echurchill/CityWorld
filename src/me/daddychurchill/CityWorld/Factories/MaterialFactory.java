package me.daddychurchill.CityWorld.Factories;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.Support.AbstractBlocks;
import me.daddychurchill.CityWorld.Support.Odds;

public abstract class MaterialFactory {

	public enum VerticalStyle {GGGG, WGGG, WGGW, WWWW};
	public enum HorizontalStyle {WG, WGG, WGGG, WWG, WWGG, GGGG, RANDOM};
	public VerticalStyle verticalStyle;
	public HorizontalStyle horizontalStyle;
	protected Boolean decayed;
	protected Odds odds;
	
	protected double oddsOfDecay = Odds.oddsSomewhatLikely;
	
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
	
	public boolean isSame(MaterialFactory other) {
		return verticalStyle == other.verticalStyle && horizontalStyle == other.horizontalStyle;
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
	
	protected Material pickMaterial(Material primary, Material secondary, int i) {
		switch (horizontalStyle) {
		case WG: 
			return i % 2 == 0 ? primary : secondary;
		case WGG: 
			return i % 3 == 0 ? primary : secondary;
		case WGGG: 
			return i % 4 == 0 ? primary : secondary;
		case WWG: 
			return ((i % 3 == 0) || (i % 3 == 1)) ? primary : secondary;
		case WWGG: 
			return ((i % 4 == 0) || (i % 4 == 1)) ? primary : secondary;
		case GGGG:
			return secondary;
		default: //case RANDOM:
			return odds.flipCoin() ? primary : secondary;
		}
	}
	
	protected void decayMaterial(AbstractBlocks blocks, int x, int y1, int y2, int z) {
		if (decayed && odds.playOdds(oddsOfDecay)) {
			int range = Math.max(1, y2 - y1);
			blocks.clearBlock(x, y1 + odds.getRandomInt(range), z);
		}
	}
	
	public abstract void placeMaterial(AbstractBlocks blocks, Material primary, Material secondary, int x, int y1, int y2, int z);
	
	protected void placeMaterial(AbstractBlocks blocks, Material primary, Material secondary, Material glass, int x, int y1, int y2, int z) {
		switch (verticalStyle) {
		case WGGG:
			blocks.setBlocks(x, y1, y1 + 1, z, primary);
			blocks.setBlocks(x, y1 + 1, y2, z, glass);
			if (glass == secondary)
				decayMaterial(blocks, x, y1 + 1, y2, z);
			break;
		case WGGW:
			blocks.setBlocks(x, y1, y1 + 1, z, primary);
			blocks.setBlocks(x, y1 + 1, y2 - 1, z, glass);
			blocks.setBlocks(x, y2 - 1, y2, z, primary);
			if (glass == secondary)
				decayMaterial(blocks, x, y1 + 1, y2 - 1, z);
			break;
		case GGGG:
			blocks.setBlocks(x, y1, y2, z, glass);
			if (glass == secondary)
				decayMaterial(blocks, x, y1, y2, z);
			break;
		case WWWW:
			blocks.setBlocks(x, y1, y2, z, primary);
			break;
		}
	}
}
