package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;
import org.bukkit.TreeType;

public class FoliageProvider_TheEnd extends FoliageProvider_Normal {

	public FoliageProvider_TheEnd(Random random) {
		super(random);
	}
	
	private final static int glassId = Material.GLASS.getId();
	private final static int paneId = Material.THIN_GLASS.getId();
	
	@Override
	public boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, TreeType treeType) {
		if (likelyFlora(generator, random)) {
			if (treeType == TreeType.BIG_TREE)
				return generateTree(chunk, random, x, y, z, treeType, logId, paneId, glassId);
			else
				return generateTree(chunk, random, x, y, z, treeType, logId, leavesId, leavesId);
		} else
			return false;
	}
}
