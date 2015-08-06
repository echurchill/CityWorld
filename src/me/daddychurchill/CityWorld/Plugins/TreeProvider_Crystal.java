package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.Support.SupportBlocks;

import org.bukkit.Material;

public class TreeProvider_Crystal extends TreeProvider {

	public TreeProvider_Crystal() {
		// TODO Auto-generated constructor stub
	}

	protected void generateLeavesBlock(SupportBlocks chunk, int x, int y, int z, Material material, int data) {
		if (chunk.isEmpty(x, y, z))
			switch (data) {
			case 1:
				chunk.setBlock(x, y, z, Material.THIN_GLASS);
				break;
			case 2:
				chunk.setBlock(x, y, z, Material.GLOWSTONE);
				break;
			case 3:
				chunk.setBlock(x, y, z, Material.STAINED_GLASS);
				break;
			default:
				chunk.setBlock(x, y, z, Material.GLASS);
				break;
			}
	}
}
