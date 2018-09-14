package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

import org.bukkit.DyeColor;
import org.bukkit.Material;

public class TreeProvider_Crystal extends TreeProvider {

	private Odds odds;
	
	public TreeProvider_Crystal() {
		
		odds = new Odds();
	}

	@Override
	protected void generateTrunkBlock(SupportBlocks chunk, int x, int y, int z, int w, int h, Material material) {
		if (odds.playOdds(Odds.oddsSomewhatLikely))
			material = Material.GLOWSTONE;
		
		super.generateTrunkBlock(chunk, x, y, z, w, h, material);
	}
	
	@Override
	protected void generateLeavesBlock(SupportBlocks chunk, int x, int y, int z, Material material, DyeColor specialColor) {
		if (material == Material.ACACIA_LEAVES) {
			if (chunk.isEmpty(x, y, z))
				if (odds.playOdds(Odds.oddsExtremelyLikely))
					chunk.setBlock(x, y, z, Material.GLASS_PANE);
				else
					chunk.setBlock(x, y, z, Material.GLASS);
		} else if (material == Material.BIRCH_LEAVES) {
			if (chunk.isEmpty(x, y - 1, z))
				chunk.setBlock(x, y - 1, z, odds.getColoredCarpet(specialColor));
		} else if (material == Material.DARK_OAK_LEAVES) {
			if (chunk.isEmpty(x, y, z))
				if (odds.playOdds(Odds.oddsExtremelyLikely))
					chunk.setBlock(x, y, z, odds.getColoredGlass(specialColor));
				else
					chunk.setBlock(x, y, z, odds.getColoredPane(specialColor));
		} else if (material == Material.JUNGLE_LEAVES) {
			if (chunk.isEmpty(x, y, z))
				if (odds.playOdds(Odds.oddsExtremelyLikely))
					chunk.setBlock(x, y, z, odds.getColoredPane(specialColor));
				else
					chunk.setBlock(x, y, z, odds.getColoredGlass(specialColor));
		} else {
			if (chunk.isEmpty(x, y, z))
				if (odds.playOdds(Odds.oddsExtremelyLikely))
					chunk.setBlock(x, y, z, Material.GLASS);
				else
					chunk.setBlock(x, y, z, Material.GLASS_PANE);
		} 
	}
}
