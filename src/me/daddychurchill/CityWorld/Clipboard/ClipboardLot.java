package me.daddychurchill.CityWorld.Clipboard;

import me.daddychurchill.CityWorld.CityWorld;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plats.IsolatedLot;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.RealChunk;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class ClipboardLot extends IsolatedLot {

	Clipboard clip;
	Direction.Facing facing;
	int lotX, lotZ;
	
	
	public ClipboardLot(PlatMap platmap, int chunkX, int chunkZ, 
			Clipboard clip, Direction.Facing facing, 
			int lotX, int lotZ) {
		super(platmap, chunkX, chunkZ);
		
		style = LotStyle.ROUNDABOUT;
		this.clip = clip;
		this.facing = facing;
		this.lotX = lotX;
		this.lotZ = lotZ;
	}

	@Override
	public boolean isPlaceableAt(WorldGenerator generator, int chunkX, int chunkZ) {
		return generator.settings.inRoadRange(chunkX, chunkZ);
	}
	
	@Override
	public int getBottomY(WorldGenerator generator) {
		return generator.streetLevel + 1;
	}
	
	@Override
	protected void generateActualChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {
		
		// something to do?
		if (clip.groundLevelY > 0) {

			// a place for our bits
			int x1, x2, y1, y2, z1, z2;
			
			// bounding box for this operation
			y1 = generator.streetLevel - clip.groundLevelY + 1;
			y2 = y1 + clip.groundLevelY;
			
			// north side
			if (lotZ == 0) {
				z1 = clip.insetNorth;
				z2 = chunk.width;
				
			// south side
			} else if (lotZ == clip.chunkZ - 1) {
				z1 = 0;
				z2 = chunk.width - clip.insetSouth;
				
			// one of the middle bits
			} else {
				z1 = 0;
				z2 = chunk.width;
			}
	
			// west side
			if (lotX == 0) {
				x1 = clip.insetWest;
				x2 = chunk.width;
				
			// east side
			} else if (lotX == clip.chunkX - 1) {
				x1 = 0;
				x2 = chunk.width - clip.insetEast;
				
			// one of the middle bits
			} else {
				x1 = 0;
				x2 = chunk.width;
			}
			
			// empty things out
			CityWorld.reportMessage("Empty: " + x1 + "-" + x2 + ", " + y1 + "-" + y2 + ", " + z1 + "-" + z2);
			chunk.setBlocks(x1, x2, y1, y2, z1, z2, airId);
		}
	}
	
	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, DataContext context, int platX, int platZ) {
		
		// where do we start?
		int originX = chunk.getOriginX() - lotX * chunk.width + clip.insetWest;
		int originZ = chunk.getOriginZ() - lotZ * chunk.width + clip.insetNorth;
		int originY = generator.streetLevel - clip.groundLevelY;
		
		// sub region calculation
		int x1, x2, z1, z2;
		if (lotX == 0) {
			x1 = 0;
			x2 = chunk.width - clip.insetWest;
		} else {
			x1 = lotX * chunk.width - clip.insetWest;
			x2 = x1 + chunk.width;
			if (x2 > clip.sizeX)
				x2 = clip.sizeX;
		}
		if (lotZ == 0) {
			z1 = 0;
			z2 = chunk.width - clip.insetNorth;
		} else {
			z1 = lotZ * chunk.width - clip.insetNorth;
			z2 = z1 + chunk.width;
			if (z2 > clip.sizeZ)
				z2 = clip.sizeZ;
		}
		
		// paste it
		clip.paste(generator, chunk, facing, originX, originY, originZ, x1, x2, 0, clip.sizeY, z1, z2);
		
		// paste clip or at least part of it
		//clip.paste(generator, chunk, facing, blockX, blockY, blockZ, x1, x2, y1, y2, z1, z2);
//		CityWorld.reportMessage("Setting: clip = " + clip.name +
//				" size = " + clip.sizeX + ", " + clip.sizeZ + 
//				" lot = " + lotX + ", " + lotZ);
//				" origin = "+ blockX + ", " + blockY + ", " + blockZ + 
//				" min = " + x1 + ", "+ y1 + ", "+ z1 + 
//				" max = " + x2 + ", "+ y2 + ", "+ z2);
//		chunk.setBlocks(x1, x2, y1, y2, z1, z2, Material.STONE);
		
//		chunk.setBlocks(0, generator.streetLevel, generator.height / 2, 0, Material.BEDROCK);
	}
}
