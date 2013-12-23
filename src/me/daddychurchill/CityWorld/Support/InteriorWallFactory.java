package me.daddychurchill.CityWorld.Support;

import org.bukkit.Material;

public class InteriorWallFactory extends MaterialFactory {

	public InteriorWallFactory(Odds odds, boolean decayed) {
		super(odds, decayed);
	}

	public InteriorWallFactory(MaterialFactory other) {
		super(other);
	}

	@Override
	protected VerticalStyle pickVerticalStyle() {
		switch (odds.getRandomInt(4)) {
		default:
			return VerticalStyle.GGGG;
		case 1:
			return VerticalStyle.WGGW;
		case 2:
			return VerticalStyle.WGGG;
		case 3:
			return VerticalStyle.WWWW;
		}		
	}
	
	@Override
	protected HorizontalStyle pickHorizontalStyle() {
		return HorizontalStyle.GGGG;
	}
	
	@Override
	public void placeMaterial(SupportChunk chunk, Material primaryMaterial, Material secondaryMaterial, int x, int y1, int y2, int z) {
		super.placeMaterial(chunk, primaryMaterial, secondaryMaterial, pickMaterial(primaryMaterial, secondaryMaterial, x), x, y1, y2, z);
	}
}
