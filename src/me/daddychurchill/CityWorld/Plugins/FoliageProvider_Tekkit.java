package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.TekkitMaterial;

import org.bukkit.Material;

public class FoliageProvider_Tekkit extends FoliageProvider_Normal {

	public FoliageProvider_Tekkit(Random random) {
		super(random);
	}
	
	private final static double oddsOfRubberSapling = 0.01;
	
	@Override
	public boolean generateTree(WorldGenerator generator, RealChunk chunk, int x, int y, int z, LigneousType ligneousType) {
		if (ligneousType == LigneousType.OAK && random.nextDouble() < oddsOfRubberSapling) {
			//TODO what is a rubber tree made of? 
			//return generateTree(chunk, random, x, y, z, treeType, trunkId, leavesId1, leavesId2);
			if (likelyFlora(generator, random)) {
				chunk.setBlock(x, y - 1, z, Material.GRASS);
				chunk.setBlock(x, y, z, TekkitMaterial.RUBBER_SAPLING, (byte) 0, true);
			}
			return true;
		} else
			return super.generateTree(generator, chunk, x, y, z, ligneousType);
	}
}
