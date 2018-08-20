package me.daddychurchill.CityWorld.Support.Materials;

import org.bukkit.DyeColor;
import org.bukkit.Material;

public class Stained_Glass_Pane extends Stained_Block {

	public Stained_Glass_Pane()
	{
		super(Material.WHITE_STAINED_GLASS_PANE);
	}

	public Stained_Glass_Pane(DyeColor color)
	{
		this();
		setColor(color);
	}

}
