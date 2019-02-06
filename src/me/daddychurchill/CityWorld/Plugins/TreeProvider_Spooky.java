package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Colors;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

class TreeProvider_Spooky extends TreeProvider {

	public TreeProvider_Spooky(CityWorldGenerator generator) {
		super(generator);
	}

	@Override
	protected void generateLeafBlock(SupportBlocks chunk, int x, int y, int z, Material material, Colors colors) {
		if (material == Material.ACACIA_LEAVES) {
			if (chunk.isEmpty(x, y, z))
				chunk.setBlock(x, y, z, Material.COBWEB);
		} else if (material == Material.BIRCH_LEAVES) {
			if (chunk.isEmpty(x, y, z))
				chunk.setBlock(x, y, z, Material.IRON_BARS);
		} else if (material == Material.DARK_OAK_LEAVES) {
			if (chunk.isEmpty(x, y, z))
				if (odds.playOdds(Odds.oddsLikely))
					chunk.setBlock(x, y, z, Material.SPONGE);
				else
					chunk.setBlock(x, y, z, Material.WET_SPONGE);
		} else if (material == Material.JUNGLE_LEAVES) {
			if (chunk.isEmpty(x, y - 1, z))
				chunk.setBlock(x, y - 1, z, colors.getCarpet());
		} else {
			// chunk.setBlock(x, y, z, Material.AIR);
		}
	}
}
