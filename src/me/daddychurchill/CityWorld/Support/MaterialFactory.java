package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.Context.DataContext;

import org.bukkit.Material;

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
	
	protected Material pickMaterial(Material primaryMaterial, Material secondaryMaterial, int i) {
		switch (horizontalStyle) {
		case WG: 
			return i % 2 == 0 ? primaryMaterial : secondaryMaterial;
		case WGG: 
			return i % 3 == 0 ? primaryMaterial : secondaryMaterial;
		case WGGG: 
			return i % 4 == 0 ? primaryMaterial : secondaryMaterial;
		case WWG: 
			return ((i % 3 == 0) || (i % 3 == 1)) ? primaryMaterial : secondaryMaterial;
		case WWGG: 
			return ((i % 4 == 0) || (i % 4 == 1)) ? primaryMaterial : secondaryMaterial;
		case GGGG:
			return secondaryMaterial;
		default: //case RANDOM:
			return odds.flipCoin() ? primaryMaterial : secondaryMaterial;
		}
	}
	
	protected void decayMaterial(SupportChunk chunk, int x, int y1, int y2, int z) {
		if (decayed && odds.playOdds(oddsOfDecay)) {
			int range = Math.max(1, y2 - y1);
			chunk.setBlock(x, y1 + odds.getRandomInt(range), z, Material.AIR);
		}
	}
	
	public abstract void placeMaterial(SupportChunk chunk, Material primaryMaterial, Material secondaryMaterial, 
			int x, int y1, int y2, int z);
	
	protected void placeMaterial(SupportChunk chunk, Material primaryMaterial, Material secondaryMaterial, 
			Material glassMaterial, int x, int y1, int y2, int z) {
		switch (verticalStyle) {
		case WGGG:
			chunk.setBlocks(x, y1, y1 + 1, z, primaryMaterial);
			chunk.setBlocks(x, y1 + 1, y2, z, glassMaterial);
			if (glassMaterial == secondaryMaterial)
				decayMaterial(chunk, x, y1 + 1, y2, z);
			break;
		case WGGW:
			chunk.setBlocks(x, y1, y1 + 1, z, primaryMaterial);
			chunk.setBlocks(x, y1 + 1, y2 - 1, z, glassMaterial);
			chunk.setBlocks(x, y2 - 1, y2, z, primaryMaterial);
			if (glassMaterial == secondaryMaterial)
				decayMaterial(chunk, x, y1 + 1, y2 - 1, z);
			break;
		case GGGG:
			chunk.setBlocks(x, y1, y2, z, glassMaterial);
			if (glassMaterial == secondaryMaterial)
				decayMaterial(chunk, x, y1, y2, z);
			break;
		case WWWW:
			chunk.setBlocks(x, y1, y2, z, primaryMaterial);
			break;
		}
	}
}
