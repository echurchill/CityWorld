package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;
import org.bukkit.block.Biome;

import me.daddychurchill.CityWorld.WorldGenerator;
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
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.Odds;

public class ShapeProvider_Flooded extends ShapeProvider_Normal {

	public final static Material floodMat = Material.STATIONARY_WATER;
	public final static byte floodId = (byte) floodMat.getId();
	
	protected int floodY;
	
	public ShapeProvider_Flooded(WorldGenerator generator, Odds odds) {
		super(generator, odds);
		
		floodY = seaLevel + 20;
	}

	@Override
	public void allocateContexts(WorldGenerator generator) {
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
			
			
			contextInitialized = true;
		}
	}
	
	@Override
	public String getCollectionName() {
		return "Flooded";
	}
	
	@Override
	public int findFloodY(WorldGenerator generator, int blockX, int blockZ) {
		return floodY;
	}

	@Override
	public int findHighestFloodY(WorldGenerator generator) {
		return floodY;
	}

	@Override
	public int findLowestFloodY(WorldGenerator generator) {
		return floodY;
	}

	@Override
	protected Biome remapBiome(WorldGenerator generator, PlatLot lot, Biome biome) {
		return Biome.OCEAN;
	}

	@Override
	protected void generateStratas(WorldGenerator generator, PlatLot lot,
			ByteChunk chunk, int x, int z, byte substratumId, byte stratumId,
			int stratumY, byte subsurfaceId, int subsurfaceY, byte surfaceId,
			int coverY, byte coverId, boolean surfaceCaves) {

		// do the default bit
		actualGenerateStratas(generator, lot, chunk, x, z, substratumId, stratumId, stratumY, 
				subsurfaceId, subsurfaceY, surfaceId, surfaceCaves);
		
		// cover it up a bit
		actualGenerateFlood(generator, lot, chunk, x, z, subsurfaceY + 1);
	}
	
	@Override
	protected void generateStratas(WorldGenerator generator, PlatLot lot,
			ByteChunk chunk, int x, int z, byte substratumId, byte stratumId,
			int stratumY, byte subsurfaceId, int subsurfaceY, byte surfaceId,
			boolean surfaceCaves) {

		// do the default bit
		actualGenerateStratas(generator, lot, chunk, x, z, substratumId, stratumId, stratumY, 
				subsurfaceId, subsurfaceY, surfaceId, surfaceCaves);
		
		// cover it up a bit
		actualGenerateFlood(generator, lot, chunk, x, z, subsurfaceY + 1);
	}

	protected void actualGenerateFlood(WorldGenerator generator, PlatLot lot, ByteChunk chunk, int x, int z, int subsurfaceY) {
		int y = findFloodY(generator, chunk.getBlockX(x), chunk.getBlockZ(z));
		if (y > subsurfaceY) {
//			chunk.setBlocks(x, subsurfaceY, y - 2, z, subFloodId);
			chunk.setBlocks(x, subsurfaceY, y, z, floodId);
		}
	}
}
