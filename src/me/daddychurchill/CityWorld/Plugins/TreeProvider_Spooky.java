package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealMaterial;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

import org.bukkit.DyeColor;
import org.bukkit.Material;

public class TreeProvider_Spooky extends TreeProvider {

	public TreeProvider_Spooky() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void generateLeavesBlock(SupportBlocks chunk, int x, int y, int z, Material material, int data, DyeColor specialColor) {
		switch (data) {
		case 1:
			if (chunk.isEmpty(x, y, z))
				chunk.setBlock(x, y, z, Material.WEB);
			break;
		case 2:
			if (chunk.isEmpty(x, y, z))
				chunk.setBlock(x, y, z, Material.IRON_FENCE);
			break;
		case 3:
			if (chunk.isEmpty(x, y, z))
				if (odds.playOdds(Odds.oddsLikely))
					chunk.setBlock(x, y, z, RealMaterial.SPONGE);
				else
					chunk.setBlock(x, y, z, RealMaterial.SPONGE_WET);
			break;
		case 4:
			if (chunk.isEmpty(x, y - 1, z))
				chunk.setBlockTypeAndColor(x, y - 1, z, Material.CARPET, specialColor);
			break;
		default:
			//chunk.setBlock(x, y, z, Material.AIR);
			break;
		}
	}
}
