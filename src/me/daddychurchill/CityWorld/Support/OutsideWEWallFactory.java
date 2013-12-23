package me.daddychurchill.CityWorld.Support;

import org.bukkit.Material;

public class OutsideWEWallFactory extends MaterialFactory {

	public OutsideWEWallFactory(Odds odds, boolean decayed) {
		super(odds, decayed);
	}

	public OutsideWEWallFactory(MaterialFactory other) {
		super(other);
	}

	@Override
	public void placeMaterial(SupportChunk chunk, Material primaryMaterial, Material secondaryMaterial, int x, int y1, int y2, int z) {
		super.placeMaterial(chunk, primaryMaterial, secondaryMaterial, pickMaterial(primaryMaterial, secondaryMaterial, x), x, y1, y2, z);
	}
}
