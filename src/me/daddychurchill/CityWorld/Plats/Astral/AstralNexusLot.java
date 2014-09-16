package me.daddychurchill.CityWorld.Plats.Astral;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class AstralNexusLot extends AstralStructureLot {

	public enum NexusSegment { NORTHWEST, NORTHEAST, SOUTHWEST, SOUTHEAST };
	private NexusSegment segment;
	
	public AstralNexusLot(PlatMap platmap, int chunkX, int chunkZ, NexusSegment segment) {
		super(platmap, chunkX, chunkZ);
		
		this.segment = segment;
	}
	
	private final static int nexusY1 = 64 + 16;
	private final static int nexusY2 = nexusY1 + 16;

	@Override
	protected void generateActualBlocks(WorldGenerator generator,
			PlatMap platmap, RealChunk chunk, DataContext context, int platX,
			int platZ) {
		
		switch (segment) {
		case NORTHEAST:
			generateNorthEast(chunk);
			break;
		case NORTHWEST:
			generateNorthWest(chunk);
			break;
		case SOUTHEAST:
			generateSouthEast(chunk);
			break;
		case SOUTHWEST:
			generateSouthWest(chunk);
			break;
		}
	}
	
	//TODO Map room (the local area)
	//TODO Transportation room (railroads to everywhere)
	//TODO Supply room (closets of happiness)
	//TODO Control room (prelay TNT for a fun time)
	
	private void generateNorthEast(RealChunk chunk) {
		generatePlaceholder(chunk, true, false, false, true, "Map room");
	}

	private void generateNorthWest(RealChunk chunk) {
		generatePlaceholder(chunk, true, false, true, false, "Transport room");
	}

	private void generateSouthEast(RealChunk chunk) {
		generatePlaceholder(chunk, false, true, false, true, "Supply room");
	}
	
	private void generateSouthWest(RealChunk chunk) {
		generatePlaceholder(chunk, false, true, true, false, "Control room");
	}
	
	private void generatePlaceholder(RealChunk chunk, boolean northWall, boolean southWall, boolean westWall, boolean eastWall, String message) {
		chunk.setBlocks(0, 16, nexusY1, 0, 16, Material.QUARTZ_BLOCK);

		String[] messages = {message, "under", "contruction"};
		
		chunk.setSignPost(8, nexusY1 + 1, 8, BlockFace.SOUTH, messages);
		
		if (northWall)
			chunk.setBlocks(0, 16, nexusY1 + 1, nexusY2, 0, 1, Material.GLASS);
		if (southWall)
			chunk.setBlocks(0, 16, nexusY1 + 1, nexusY2, 15, 16, Material.GLASS);
		if (westWall)
			chunk.setBlocks(0, 1, nexusY1 + 1, nexusY2, 0, 16, Material.GLASS);
		if (eastWall)
			chunk.setBlocks(15, 16, nexusY1 + 1, nexusY2, 0, 16, Material.GLASS);
		
		chunk.setBlocks(0, 16, nexusY2, 0, 16, Material.GLASS);
	}
}
