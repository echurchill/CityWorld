package me.daddychurchill.CityWorld.Clipboard;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.IsolatedLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.AbstractBlocks;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;

import org.bukkit.block.BlockFace;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class ClipboardLot extends IsolatedLot {

	private Clipboard clip;
	private BlockFace facing;
	private int lotX, lotZ;
	private int depth;
	
	// a place for our bits
	private int edgeX1, edgeX2, edgeY1, edgeY2, edgeY3, edgeZ1, edgeZ2;
	
	public ClipboardLot(PlatMap platmap, int chunkX, int chunkZ, 
			Clipboard clip, BlockFace facing, 
			int lotX, int lotZ) {
		super(platmap, chunkX, chunkZ);
		
		style = LotStyle.STRUCTURE;
		this.clip = clip;
		this.facing = facing;
		this.lotX = lotX;
		this.lotZ = lotZ;
		depth = platmap.generator.streetLevel - clip.groundLevelY + clip.edgeRise;
		
		// bounding box for this operation
		edgeY1 = depth + 1;
		edgeY2 = edgeY1 + clip.groundLevelY - 1;
		edgeY3 = edgeY1 + clip.sizeY;
		
		// north side
		if (clip.chunkZ == 1) {
			edgeZ1 = clip.insetNorth;
			edgeZ2 = AbstractBlocks.sectionBlockWidth - clip.insetSouth;

		} else if (lotZ == 0) {
			edgeZ1 = clip.insetNorth;
			edgeZ2 = AbstractBlocks.sectionBlockWidth;
			
		// south side
		} else if (lotZ == clip.chunkZ - 1) {
			edgeZ1 = 0;
			edgeZ2 = AbstractBlocks.sectionBlockWidth - clip.insetSouth;
			
		// one of the middle bits
		} else {
			edgeZ1 = 0;
			edgeZ2 = AbstractBlocks.sectionBlockWidth;
		}

		// west side
		if (clip.chunkX == 1) {
			edgeX1 = clip.insetWest;
			edgeX2 = AbstractBlocks.sectionBlockWidth - clip.insetEast;

		} else if (lotX == 0) {
			edgeX1 = clip.insetWest;
			edgeX2 = AbstractBlocks.sectionBlockWidth;
			
		// east side
		} else if (lotX == clip.chunkX - 1) {
			edgeX1 = 0;
			edgeX2 = AbstractBlocks.sectionBlockWidth - clip.insetEast;
			
		// one of the middle bits
		} else {
			edgeX1 = 0;
			edgeX2 = AbstractBlocks.sectionBlockWidth;
		}
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new ClipboardLot(platmap, chunkX, chunkZ, clip, facing, lotX, lotZ);
	}

	@Override
	public boolean isPlaceableAt(CityWorldGenerator generator, int chunkX, int chunkZ) {
		return generator.settings.inCityRange(chunkX, chunkZ);
	}
	
	@Override
	public int getBottomY(CityWorldGenerator generator) {
		return depth;
	}
	
	@Override
	public int getTopY(CityWorldGenerator generator) {
		return depth + clip.sizeY;
	}
	
	@Override
	public boolean isValidStrataY(CityWorldGenerator generator, int blockX, int blockY, int blockZ) {
		return blockY <= edgeY1 || blockY > edgeY3;
	}

	@Override
	protected boolean isShaftableLevel(CityWorldGenerator generator, int y) {
		return (y < depth - 32 || y > edgeY3 + 16) && super.isShaftableLevel(generator, y);
	}
	
	@Override
	protected void generateActualChunk(CityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {
		
		// put a hole in the ground?
		if (clip.groundLevelY > 0) {

			// backfill a bit
			chunk.setBlocks(0, edgeX1, depth, edgeY2, 0, 16, generator.oreProvider.stratumMaterial);
			chunk.setBlocks(edgeX2, 16, depth, edgeY2, 0, 16, generator.oreProvider.stratumMaterial);
			chunk.setBlocks(edgeX1, edgeX2, depth, edgeY2, 0, edgeZ1, generator.oreProvider.stratumMaterial);
			chunk.setBlocks(edgeX1, edgeX2, depth, edgeY2, edgeZ2, 16, generator.oreProvider.stratumMaterial);
		}
	}
	
	@Override
	protected void generateActualBlocks(CityWorldGenerator generator, PlatMap platmap, RealBlocks chunk, DataContext context, int platX, int platZ) {
		
		// where do we start?
		int originX = chunk.getOriginX() - lotX * chunk.width + clip.insetWest;
		int originZ = chunk.getOriginZ() - lotZ * chunk.width + clip.insetNorth;
		
		// echo the location?
		if (clip.broadcastLocation && lotX == 0 && lotZ == 0)
			reportLocation(generator, clip.name, originX, originZ);
		
		// sub region calculation
		int subX1, subX2, subZ1, subZ2;
		if (lotX == 0) {
			subX1 = 0;
			subX2 = chunk.width - clip.insetWest;
		} else {
			subX1 = lotX * chunk.width - clip.insetWest;
			subX2 = subX1 + chunk.width;
		}
		if (lotZ == 0) {
			subZ1 = 0;
			subZ2 = chunk.width - clip.insetNorth;
		} else {
			subZ1 = lotZ * chunk.width - clip.insetNorth;
			subZ2 = subZ1 + chunk.width;
		}
		
		// don't go too far
		if (subX2 > clip.sizeX)
			subX2 = clip.sizeX;
		if (subZ2 > clip.sizeZ)
			subZ2 = clip.sizeZ;
				
		// paste it
		clip.paste(generator, chunk, facing, originX, depth, originZ, subX1, subX2, 0, clip.sizeY, subZ1, subZ2);

		// draw the edges
		chunk.setBlocks(0, edgeX1, edgeY2, 0, 16, clip.edgeMaterial);//, clip.edgeData);
		chunk.setBlocks(edgeX2, 16, edgeY2, 0, 16, clip.edgeMaterial);//, clip.edgeData);
		chunk.setBlocks(edgeX1, edgeX2, edgeY2, 0, edgeZ1, clip.edgeMaterial);//, clip.edgeData);
		chunk.setBlocks(edgeX1, edgeX2, edgeY2, edgeZ2, 16, clip.edgeMaterial);//, clip.edgeData);
		
		// mr. creeper says: that is a nice building you have there, too bad something bad has to happen to it
		if (clip.decayable && generator.settings.includeDecayedBuildings)
			destroyLot(generator, depth, edgeY3);
	}
	
	// Added by Sablednah
	// https://github.com/echurchill/CityWorld/pull/4
	public Clipboard getClip() {
		return clip;
	}
}
