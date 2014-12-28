package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;

public class OreProvider_SnowDunes extends OreProvider_Normal {

	public OreProvider_SnowDunes(CityWorldGenerator generator) {
		super(generator);

		fluidMaterial = Material.PACKED_ICE;
		fluidFluidMaterial = Material.ICE;
	}

	@Override
	public String getCollectionName() {
		return "SnowDunes";
	}

}
