package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Colors;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

class TreeProvider_Crystal extends TreeProvider {

	private final Odds odds;

	public TreeProvider_Crystal(CityWorldGenerator generator) {
		super(generator);
		odds = new Odds();
	}

	@Override
	protected void generateTrunkBlock(SupportBlocks chunk, int x, int y, int z, int w, int h, Material material) {
		if (odds.playOdds(Odds.oddsSomewhatLikely))
			material = Material.GLOWSTONE;

		super.generateTrunkBlock(chunk, x, y, z, w, h, material);
	}

	@Override
	protected void generateLeafBlock(SupportBlocks chunk, int x, int y, int z, Material material, Colors colors) {
		if (material == Material.ACACIA_LEAVES) {
			if (chunk.isEmpty(x, y, z))
				if (odds.playOdds(Odds.oddsExtremelyLikely))
					chunk.setBlock(x, y, z, Material.GLASS_PANE);
				else
					chunk.setBlock(x, y, z, Material.GLASS);
		} else if (material == Material.BIRCH_LEAVES) {
			if (chunk.isEmpty(x, y - 1, z))
				chunk.setBlock(x, y - 1, z, colors.getCarpet());
		} else if (material == Material.DARK_OAK_LEAVES) {
			if (chunk.isEmpty(x, y, z))
				if (odds.playOdds(Odds.oddsExtremelyLikely))
					chunk.setBlock(x, y, z, colors.getGlass());
				else
					chunk.setBlock(x, y, z, colors.getGlassPane());
		} else if (material == Material.JUNGLE_LEAVES) {
			if (chunk.isEmpty(x, y, z))
				if (odds.playOdds(Odds.oddsExtremelyLikely))
					chunk.setBlock(x, y, z, colors.getGlassPane());
				else
					chunk.setBlock(x, y, z, colors.getGlass());
		} else {
			if (chunk.isEmpty(x, y, z))
				if (odds.playOdds(Odds.oddsExtremelyLikely))
					chunk.setBlock(x, y, z, Material.GLASS);
				else
					chunk.setBlock(x, y, z, Material.GLASS_PANE);
		}
	}
}
