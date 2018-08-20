package me.daddychurchill.CityWorld.Support.Materials;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Tree;

public class Log extends Tree {

	public Log()
	{
		super(Material.SPRUCE_LOG);
	}

    public Log(TreeSpecies species) {
        super(species);
    }

    public Log(TreeSpecies species, BlockFace dir) {
        super(species, dir);
    }
    
	@Override
	public Log clone() {
		return (Log)super.clone();
	}

}
