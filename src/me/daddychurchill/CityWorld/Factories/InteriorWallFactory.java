package me.daddychurchill.CityWorld.Factories;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.Support.AbstractBlocks;
import me.daddychurchill.CityWorld.Support.Odds;

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
	public void placeMaterial(AbstractBlocks blocks, Material primary, Material secondary, int x, int y1, int y2, int z) {
		super.placeMaterial(blocks, primary, secondary, pickMaterial(primary, secondary, x), x, y1, y2, z);
	}
}
