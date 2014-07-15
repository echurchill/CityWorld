package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.Support.SupportChunk;

import org.bukkit.Material;

public class TreeProvider_Spooky extends TreeProvider {

	public TreeProvider_Spooky() {
		// TODO Auto-generated constructor stub
	}

	protected void generateLeavesBlock(SupportChunk chunk, int x, int y, int z, Material material, int data) {
		if (chunk.isEmpty(x, y, z))
			switch (data) {
			case 1:
				chunk.setBlock(x, y, z, Material.WEB);
				break;
			case 2:
				chunk.setBlock(x, y, z, Material.IRON_FENCE);
				break;
			case 3:
				chunk.setBlock(x, y, z, Material.SPONGE);
				break;
			default:
				//chunk.setBlock(x, y, z, Material.AIR);
				break;
			}
	}
}
