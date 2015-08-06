package me.daddychurchill.CityWorld.Factories;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.Support.AbstractBlocks;
import me.daddychurchill.CityWorld.Support.Odds;

public class OutsideWEWallFactory extends MaterialFactory {

	public OutsideWEWallFactory(Odds odds, boolean decayed) {
		super(odds, decayed);
	}

	public OutsideWEWallFactory(MaterialFactory other) {
		super(other);
	}

	@Override
	public void placeMaterial(AbstractBlocks blocks, Material primary, Material secondary, int x, int y1, int y2, int z) {
		super.placeMaterial(blocks, primary, secondary, pickMaterial(primary, secondary, x), x, y1, y2, z);
	}
}
