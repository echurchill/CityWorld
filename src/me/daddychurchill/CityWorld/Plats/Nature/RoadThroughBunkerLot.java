package me.daddychurchill.CityWorld.Plats.Nature;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.RoadLot;
import me.daddychurchill.CityWorld.Plats.Nature.BunkerLot.BunkerType;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class RoadThroughBunkerLot extends RoadLot {

	protected int bottomOfBunker;
	protected int topOfBunker;
	
	public final static Material wallMaterial = tunnelWallMaterial;
	
	public RoadThroughBunkerLot(PlatMap platmap, int chunkX, int chunkZ,
			long globalconnectionkey, boolean roundaboutPart, 
			BunkerLot originalLot) {
		super(platmap, chunkX, chunkZ, globalconnectionkey, roundaboutPart);
		
		this.bottomOfBunker = originalLot.bottomOfBunker;
		this.topOfBunker = originalLot.topOfBunker;
	}

	@Override
	public boolean isValidStrataY(CityWorldGenerator generator, int blockX, int blockY, int blockZ) {
		return BunkerLot.bunkerIsValidStrataY(generator, blockX, blockY, blockZ, bottomOfBunker, topOfBunker);
	}
	
	@Override
	protected boolean isShaftableLevel(CityWorldGenerator generator, int blockY) {
		return BunkerLot.bunkerIsShaftableLevel(generator, blockY, bottomOfBunker, topOfBunker)&&
			   super.isShaftableLevel(generator, blockY);
	}
	
	@Override
	public int getBottomY(CityWorldGenerator generator) {
		return bottomOfBunker;
	}
	
	@Override
	public int getTopY(CityWorldGenerator generator) {
		return topOfBunker;
	}

	@Override
	protected void generateActualChunk(CityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {
		super.generateActualChunk(generator, platmap, chunk, biomes, context, platX, platZ);
	}
	
	@Override
	protected void generateActualBlocks(CityWorldGenerator generator, PlatMap platmap, RealBlocks chunk, DataContext context, int platX, int platZ) {
		
		// draw the road
		super.generateActualBlocks(generator, platmap, chunk, context, platX, platZ);
		
		// do it!
		BunkerLot.generateBunker(generator, platmap, chunk, chunkOdds, context, 
				platX, platZ, blockYs, bottomOfBunker, topOfBunker, BunkerType.ROAD);
	}
}
