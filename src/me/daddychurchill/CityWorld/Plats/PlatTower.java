package me.daddychurchill.CityWorld.Plats;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.Direction.Door;
import me.daddychurchill.CityWorld.Support.Direction.Torch;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class PlatTower extends PlatIsolated {

	public PlatTower(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

	}
	
	private final static int platformWidth = 8;
	private final static int heightRange = 15;
	private final static int heightShortest = 10;
	private final static int heightTallest = heightShortest + heightRange;
	private boolean antennaBuilt = false;
	private boolean tallestBuilt = false;

	private final static byte platformId = (byte) Material.SMOOTH_BRICK.getId();
	private final static byte supportId = (byte) Material.COBBLESTONE.getId();
	private final static byte wallId = (byte) Material.DOUBLE_STEP.getId();
	private final static byte roofId = (byte) Material.STEP.getId();
	private final static Material baseMat = Material.CLAY;
	private final static Material antennaMat = Material.IRON_FENCE;
	private final static Material capBigMat = Material.DOUBLE_STEP;
	private final static Material capTinyMat = Material.STEP;
	
	@Override
	protected void generateActualChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, ContextData context, int platX, int platZ) {
		
		// compute offset to start of chunk
		int platformOffset = platformWidth / 2;
		int originX = Math.min(platformOffset, Math.max(chunk.width - platformOffset - 1, maxHeightX));
		int originZ = Math.min(platformOffset, Math.max(chunk.width - platformOffset - 1, maxHeightZ));
		
		// base
		for (int x = originX + 1; x < originX + platformWidth - 1; x++) {
			for (int z = originZ + 1; z < originZ + platformWidth - 1; z++) {
				for (int y = maxHeight - 1; y > minHeight; y--) {
					if (!chunk.setEmptyBlock(x, y, z, supportId)) {
						chunk.setBlocks(x, y - 3, y + 1, z, supportId);
						break;
					}
				}
			}
		}
//		chunk.setBlocks(originX + 1, originX + platformWidth - 1, minHeight, maxHeight, originZ + 1, originZ + platformWidth - 1, supportId);
		
		// top it off
		chunk.setBlocks(originX, originX + platformWidth, maxHeight, maxHeight + 1, originZ, originZ + platformWidth, platformId);
		
		// building
		chunk.setWalls(originX + 2, originX + platformWidth - 2, maxHeight + 1, maxHeight + 3, originZ + 2, originZ + platformWidth - 2, wallId);
		chunk.setBlocks(originX + 2, originX + platformWidth - 2, maxHeight + 3, maxHeight + 4, originZ + 2, originZ + platformWidth - 2, roofId);
	}
	
	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, ContextData context, int platX, int platZ) {

		// compute offset to start of chunk
		int platformOffset = platformWidth / 2;
		int originX = Math.min(platformOffset, Math.max(chunk.width - platformOffset - 1, maxHeightX));
		int originZ = Math.min(platformOffset, Math.max(chunk.width - platformOffset - 1, maxHeightZ));
		
		// place a door
		chunk.setWoodenDoor(originX + 2, maxHeight + 1, originZ + 3, Door.WESTBYNORTHWEST);
		
		// place antennas
		generateAntenna(chunk, context, originX + 1, maxHeight + 1, originZ + 1, false);
		generateAntenna(chunk, context, originX + 1, maxHeight + 1, originZ + platformWidth - 2, false);
		generateAntenna(chunk, context, originX + platformWidth - 2, maxHeight + 1, originZ + 1, false);
		generateAntenna(chunk, context, originX + platformWidth - 2, maxHeight + 1, originZ + platformWidth - 2, true);
	}
	
	private void generateAntenna(RealChunk chunk, ContextData context, int x, int y, int z, boolean lastChance) {
		
		// build an antenna?
		if ((lastChance && !antennaBuilt) || chunkRandom.nextBoolean()) {
			chunk.setBlocks(x, y, y + 2, z, baseMat);
			
			// how tall?
			int antennaHeight = heightShortest;
			if (!tallestBuilt && (lastChance || chunkRandom.nextBoolean())) {
				antennaHeight = heightTallest;
				tallestBuilt = true;
			} else
				antennaHeight += chunkRandom.nextInt(heightRange);
			
			// actually build the antenna
			chunk.setBlocks(x, y + 2, y + 2 + antennaHeight, z, antennaMat);
			
			// do a fancy middle?
			if (chunkRandom.nextBoolean()) {
				int yPoint = y + 2 + antennaHeight - 5;
				chunk.setBlocks(x - 2, x + 3, yPoint, yPoint + 1, z, z + 1, antennaMat);
				chunk.setBlocks(x, x + 1, yPoint, yPoint + 1, z - 2, z + 3, antennaMat);
			}
			
			// do a fancy top?
			if (chunkRandom.nextBoolean()) {
				int yPoint = y + 2 + antennaHeight - 1;
				chunk.setBlocks(x - 2, x + 3, yPoint, yPoint + 1, z, z + 1, antennaMat);
				chunk.setBlocks(x, x + 1, yPoint, yPoint + 1, z - 2, z + 3, antennaMat);
			}
			
			// top of the tallest one?
			if (antennaHeight == heightTallest) {
				chunk.setBlock(x, y + 2 + antennaHeight, z, capBigMat);
				chunk.setTorch(x, y + 2 + antennaHeight + 1, z, context.torchMat, Torch.FLOOR);
			} else
				chunk.setBlock(x, y + 2 + antennaHeight, z, capTinyMat);
		}
	}
}
