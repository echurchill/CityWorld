package me.daddychurchill.CityWorld.Support.Materials;

import org.bukkit.DyeColor;
import org.bukkit.Material;

public class Stained_Glass extends Stained_Block {

	public Stained_Glass()
	{
		super(Material.WHITE_STAINED_GLASS);
	}

	public Stained_Glass(DyeColor color)
	{
		this();
		setColor(color);
	}

}
