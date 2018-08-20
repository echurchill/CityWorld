package me.daddychurchill.CityWorld.Support.Materials;

import org.bukkit.DyeColor;
import org.bukkit.Material;

public class Carpet extends Stained_Block {

	public Carpet()
	{
		super(Material.CARPET);
	}

	public Carpet(DyeColor color)
	{
		this();
		setColor(color);
	}
	
	@Override
	public Carpet clone() {
		return (Carpet)super.clone();
	}

}
