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
	protected void generateTrunkBlock(SupportBlocks chunk, int x, int y, int z, int w, int h, Material material, int data) {
		if (odds.playOdds(Odds.oddsSomewhatLikely)) {
			material = Material.GLOWSTONE;
			data = 0;
		}
		super.generateTrunkBlock(chunk, x, y, z, w, h, material, data);
	}
	
	@Override
	protected void generateLeavesBlock(SupportBlocks chunk, int x, int y, int z, Material material, int data, DyeColor specialColor) {
		switch (data) {
		case 1:
			if (chunk.isEmpty(x, y, z))
				if (odds.playOdds(Odds.oddsExtremelyLikely))
					chunk.setBlock(x, y, z, Material.THIN_GLASS);
				else
					chunk.setBlock(x, y, z, Material.GLASS);
			break;
		case 2:
			if (chunk.isEmpty(x, y - 1, z))
				chunk.setBlockTypeAndColor(x, y - 1, z, Material.CARPET, specialColor);
			break;
		case 3:
			if (chunk.isEmpty(x, y, z))
				if (odds.playOdds(Odds.oddsExtremelyLikely))
					chunk.setBlockTypeAndColor(x, y, z, Material.STAINED_GLASS, specialColor);
				else
					chunk.setBlockTypeAndColor(x, y, z, Material.STAINED_GLASS_PANE, specialColor);
			break;
		case 4:
			if (chunk.isEmpty(x, y, z))
				if (odds.playOdds(Odds.oddsExtremelyLikely))
					chunk.setBlockTypeAndColor(x, y, z, Material.STAINED_GLASS_PANE, specialColor);
				else
					chunk.setBlockTypeAndColor(x, y, z, Material.STAINED_GLASS, specialColor);
			break;
		default:
			if (chunk.isEmpty(x, y, z))
				if (odds.playOdds(Odds.oddsExtremelyLikely))
					chunk.setBlock(x, y, z, Material.GLASS);
				else
					chunk.setBlock(x, y, z, Material.THIN_GLASS);
			break;
		}
	}
}
