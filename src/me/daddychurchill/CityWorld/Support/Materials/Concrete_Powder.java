package me.daddychurchill.CityWorld.Support.Materials;

import org.bukkit.DyeColor;
import org.bukkit.Material;

public class Concrete_Powder extends Stained_Block {

	public Concrete_Powder()
	{
		super(Material.CONCRETE_POWDER);
	}

	public Concrete_Powder(DyeColor color)
	{
		this();
		setColor(color);
	}

}
