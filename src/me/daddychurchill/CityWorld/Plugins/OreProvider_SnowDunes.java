package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;

public class OreProvider_SnowDunes extends OreProvider_Normal {

	public OreProvider_SnowDunes(WorldGenerator generator) {
		super(generator);

		fluidMaterial = Material.PACKED_ICE;
		fluidFluidMaterial = Material.ICE;
	}

	@Override
	public String getCollectionName() {
		return "SnowDunes";
	}

}
