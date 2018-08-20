package me.daddychurchill.CityWorld.Support.Materials;

import org.bukkit.Material;
import org.bukkit.SandstoneType;
import org.bukkit.material.Sandstone;

public class Red_Sandstone extends Sandstone {
	
	public Red_Sandstone() {
		super(Material.RED_SANDSTONE);
	}
	
	public Red_Sandstone(SandstoneType type) {
		super();
		setType(type);
	}

}
