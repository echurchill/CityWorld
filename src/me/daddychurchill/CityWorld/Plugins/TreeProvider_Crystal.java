package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

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
	protected void generateLeavesBlock(SupportBlocks chunk, int x, int y, int z, Material material, int data) {
		if (chunk.isEmpty(x, y, z))
			switch (data) {
			case 1:
				chunk.setBlock(x, y, z, Material.THIN_GLASS);
				break;
			case 2:
				chunk.setBlockTypeAndColor(x, y, z, Material.CARPET, odds.getRandomLightColor());
				break;
			case 3:
				chunk.setBlockTypeAndColor(x, y, z, Material.STAINED_GLASS, odds.getRandomColor());
				break;
			case 4:
				chunk.setBlockTypeAndColor(x, y, z, Material.STAINED_GLASS_PANE, odds.getRandomColor());
				break;
			default:
				chunk.setBlock(x, y, z, Material.GLASS);
				break;
			}
	}
}
