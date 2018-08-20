package me.daddychurchill.CityWorld.Support.Materials;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.Material.SPRUCE_WOOD;

public class Wood_Slab extends Wood {
	
	public Wood_Slab() {
		super(Material.WOOD_STEP);
	}
	
	public Wood_Slab(TreeSpecies species) {
		super(Material.WOOD_STEP, species);
	}
	
	public Wood_Slab(TreeSpecies species, boolean inv) {
		this(species);
		setInverted(inv);
	}
	
    @SuppressWarnings("deprecation")
    public boolean isInverted() {
        return ((getData() & 0x8) != 0);
    }

    @SuppressWarnings("deprecation")
    public void setInverted(boolean inv) {
        int dat = getData() & 0x7;
        if (inv) {
            dat |= 0x8;
        }
        setData((byte) dat);
    }

	
    @Override
	public Wood_Slab clone() {
		return (Wood_Slab)super.clone();
	}

    @Override
    public String toString() {
        return super.toString() + " " + getSpecies() + (isInverted() ? " inverted" : "");
    }
}
