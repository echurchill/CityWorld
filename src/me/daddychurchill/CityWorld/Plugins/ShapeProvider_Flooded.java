package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;
import org.bukkit.block.Biome;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.Flooded.FloodedConstructionContext;
import me.daddychurchill.CityWorld.Context.Flooded.FloodedFarmContext;
import me.daddychurchill.CityWorld.Context.Flooded.FloodedHighriseContext;
import me.daddychurchill.CityWorld.Context.Flooded.FloodedLowriseContext;
import me.daddychurchill.CityWorld.Context.Flooded.FloodedMidriseContext;
import me.daddychurchill.CityWorld.Context.Flooded.FloodedNatureContext;
import me.daddychurchill.CityWorld.Context.Flooded.FloodedNeighborhoodContext;
import me.daddychurchill.CityWorld.Context.Flooded.FloodedParkContext;
import me.daddychurchill.CityWorld.Context.Flooded.FloodedRoadContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.Odds;

public class ShapeProvider_Flooded extends ShapeProvider_Normal {

	public final static Material floodMaterial = Material.STATIONARY_WATER;
	
	protected int floodY;
	
	public ShapeProvider_Flooded(CityWorldGenerator generator, Odds odds) {
		super(generator, odds);
		
		floodY = seaLevel + 20;
	}

	@Override
	public void allocateContexts(CityWorldGenerator generator) {
		if (!contextInitialized) {
			natureContext = new FloodedNatureContext(generator);
			roadContext = new FloodedRoadContext(generator);
			
			parkContext = new FloodedParkContext(generator);
			highriseContext = new FloodedHighriseContext(generator);
			constructionContext = new FloodedConstructionContext(generator);
			midriseContext = new FloodedMidriseContext(generator);
			municipalContext = midriseContext;
			lowriseContext = new FloodedLowriseContext(generator);
			industrialContext = lowriseContext;
			neighborhoodContext = new FloodedNeighborhoodContext(generator);
			farmContext = new FloodedFarmContext(generator);
			outlandContext = farmContext;
			
			contextInitialized = true;
		}
	}
	
	@Override
	public String getCollectionName() {
		return "Flooded";
	}
	
	@Override
	public int findFloodY(CityWorldGenerator generator, int blockX, int blockZ) {
		return floodY;
	}

	@Override
	public int findHighestFloodY(CityWorldGenerator generator) {
		return floodY;
	}

	@Override
	public int findLowestFloodY(CityWorldGenerator generator) {
		return floodY;
	}

	@Override
	public Material findAtmosphereMaterialAt(CityWorldGenerator generator, int blockY) {
		if (blockY < floodY)
			return floodMaterial;
		else
			return super.findAtmosphereMaterialAt(generator, blockY);
	}
	
	@Override
	protected Biome remapBiome(CityWorldGenerator generator, PlatLot lot, Biome biome) {
		return Biome.OCEAN;
	}

	@Override
	protected void generateStratas(CityWorldGenerator generator, PlatLot lot,
			InitialBlocks chunk, int x, int z, Material substratumMaterial, Material stratumMaterial,
			int stratumY, Material subsurfaceMaterial, int subsurfaceY, Material surfaceMaterial,
			int coverY, Material coverMaterial, boolean surfaceCaves) {

		// do the default bit
		actualGenerateStratas(generator, lot, chunk, x, z, substratumMaterial, stratumMaterial, stratumY, 
				subsurfaceMaterial, subsurfaceY, surfaceMaterial, surfaceCaves);
		
		// cover it up a bit
		actualGenerateFlood(generator, lot, chunk, x, z, subsurfaceY + 1);
	}
	
	@Override
	protected void generateStratas(CityWorldGenerator generator, PlatLot lot,
			InitialBlocks chunk, int x, int z, Material substratumMaterial, Material stratumMaterial,
			int stratumY, Material subsurfaceMaterial, int subsurfaceY, Material surfaceMaterial,
			boolean surfaceCaves) {

		// do the default bit
		actualGenerateStratas(generator, lot, chunk, x, z, substratumMaterial, stratumMaterial, stratumY, 
				subsurfaceMaterial, subsurfaceY, surfaceMaterial, surfaceCaves);
		
		// cover it up a bit
		actualGenerateFlood(generator, lot, chunk, x, z, subsurfaceY + 1);
	}

	protected void actualGenerateFlood(CityWorldGenerator generator, PlatLot lot, InitialBlocks chunk, int x, int z, int subsurfaceY) {
		int y = findFloodY(generator, chunk.getBlockX(x), chunk.getBlockZ(z));
		if (y > subsurfaceY) {
			chunk.setBlocks(x, subsurfaceY, y, z, floodMaterial);
		}
	}
}
