package me.daddychurchill.CityWorld.Support.Materials;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import me.daddychurchill.CityWorld.Support.Materials.Slab.SlabType;

public class Double_Slab extends MaterialData {
	
	public Double_Slab() {
		super(Material.DOUBLE_STEP);
	}

	public Double_Slab(SlabType type) {
		super(Material.DOUBLE_STEP);
		setType(type);
	}
	
	@SuppressWarnings("deprecation")
	public SlabType getType() {
		return SlabType.getByData(getData());
	}
	
	@SuppressWarnings("deprecation")
	protected void setType(SlabType type) {
		setData(type.getData());
	}
	
	public Double_Slab clone() {
		return (Double_Slab)super.clone();
	}
	
	public String toString() {
		return super.toString() + "." + getType();
	}

}
