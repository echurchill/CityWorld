package me.daddychurchill.CityWorld.Support.Materials;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.material.Colorable;
import org.bukkit.material.MaterialData;

public abstract class Stained_Block extends MaterialData implements Colorable {

	public Stained_Block(Material type) {
		super(type);
	}

	@SuppressWarnings("deprecation")
	public DyeColor getColor()
	{
		return DyeColor.getByDyeData(getData());
	}

	@SuppressWarnings("deprecation")
	public void setColor(DyeColor color)
	{
		setData(color.getDyeData());
	}

	public String toString()
	{
		return getColor() + "_" + super.toString();
	}

	public Stained_Block clone()
	{
		return (Stained_Block)super.clone();
	}
}
