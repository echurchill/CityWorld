package me.daddychurchill.CityWorld.Plats.Nature;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.RoadLot;
import me.daddychurchill.CityWorld.Plats.Nature.BunkerLot.BilgeType;
import me.daddychurchill.CityWorld.Plats.Nature.BunkerLot.BunkerType;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class RoadThroughBunkerLot extends RoadLot {

	protected int bottomOfBunker;
	protected int topOfBunker;
	protected BilgeType bilgeType;
	
	public final static Material wallMaterial = tunnelWallMaterial;
	
	public RoadThroughBunkerLot(PlatMap platmap, int chunkX, int chunkZ,
			long globalconnectionkey, boolean roundaboutPart, 
			BunkerLot originalLot) {
		super(platmap, chunkX, chunkZ, globalconnectionkey, roundaboutPart);
		
		this.bottomOfBunker = originalLot.bottomOfBunker;
		this.topOfBunker = originalLot.topOfBunker;
		this.bilgeType = originalLot.bilgeType;
	}

	@Override
	public boolean isValidStrataY(WorldGenerator generator, int blockX, int blockY, int blockZ) {
		return BunkerLot.bunkerIsValidStrataY(generator, blockX, blockY, blockZ, bottomOfBunker, topOfBunker);
	}
	
	@Override
	protected boolean isShaftableLevel(WorldGenerator generator, int blockY) {
		return BunkerLot.bunkerIsShaftableLevel(generator, blockY, bottomOfBunker, topOfBunker)&&
			   super.isShaftableLevel(generator, blockY);
	}
	
	@Override
	public int getBottomY(WorldGenerator generator) {
		return bottomOfBunker;
	}
	
	@Override
	public int getTopY(WorldGenerator generator) {
		return topOfBunker;
	}

	@Override
	protected void generateActualChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {
		super.generateActualChunk(generator, platmap, chunk, biomes, context, platX, platZ);
	}
	
	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, DataContext context, int platX, int platZ) {
		
		// draw the road
		super.generateActualBlocks(generator, platmap, chunk, context, platX, platZ);
		
		// do it!
		BunkerLot.generateBunker(generator, platmap, chunk, chunkOdds, context, platX, platZ, averageHeight,
				bottomOfBunker, topOfBunker, bilgeType, BunkerType.ROAD);
	}
}
