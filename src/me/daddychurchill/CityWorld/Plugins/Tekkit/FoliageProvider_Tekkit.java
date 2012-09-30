package me.daddychurchill.CityWorld.Plugins.Tekkit;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plugins.FoliageProvider_Normal;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;

public class FoliageProvider_Tekkit extends FoliageProvider_Normal {

	public FoliageProvider_Tekkit(Odds odds) {
		super(odds);
	}
	
	private final static double oddsOfRubberSapling = DataContext.oddsNearlyNeverHappens;
	
	@Override
	public boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, LigneousType ligneousType) {
		if (ligneousType == LigneousType.OAK && odds.playOdds(oddsOfRubberSapling)) {
			//TODO what is a rubber tree made of? 
			//return generateTree(chunk, random, x, y, z, treeType, trunkId, leavesId1, leavesId2);
			if (likelyFlora(generator, odds)) {
				chunk.setBlock(x, y - 1, z, Material.GRASS);
				chunk.setBlock(x, y, z, TekkitMaterial.RUBBER_SAPLING, (byte) 0, true);
			}
			return true;
		} else
			return super.generateTree(generator, chunk, x, y, z, ligneousType);
	}
}
