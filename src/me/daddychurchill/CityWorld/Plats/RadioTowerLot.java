package me.daddychurchill.CityWorld.Plats;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class RadioTowerLot extends ConstructLot {

	private boolean building;
	
	public RadioTowerLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		building = chunkRandom.nextDouble() < 0.90;
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
	public int getBottomY(WorldGenerator generator) {
		return maxHeight + 2;
	}
	
	@Override
	protected void generateActualChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {
		
		// compute offset to start of chunk
		int platformOffset = platformWidth / 2;
		int originX = Math.min(platformOffset, Math.max(chunk.width - platformOffset - 1, maxHeightX));
		int originZ = Math.min(platformOffset, Math.max(chunk.width - platformOffset - 1, maxHeightZ));
		int platformY = getBottomY(generator);
		
		// base
		for (int x = originX + 1; x < originX + platformWidth - 1; x++) {
			for (int z = originZ + 1; z < originZ + platformWidth - 1; z++) {
				for (int y = platformY - 2; y > minHeight; y--) {
					if (!chunk.setEmptyBlock(x, y, z, supportId)) {
						chunk.setBlocks(x, y - 3, y + 1, z, supportId);
						break;
					}
				}
			}
		}
		
		// top it off
		chunk.setBlocks(originX, originX + platformWidth, platformY - 1, originZ, originZ + platformWidth, platformId);
		
		// base
		if (minHeight > generator.evergreenLevel)
			generator.oreProvider.sprinkleSnow(generator, chunk, chunkRandom, originX, originX + platformWidth, platformY, originZ, originZ + platformWidth);
		
		// building
		if (building) {
			chunk.setBlocks(originX + 2, originX + platformWidth - 2, platformY, platformY + 2, originZ + 2, originZ + platformWidth - 2, wallId);
			chunk.setBlocks(originX + 3, originX + platformWidth - 3, platformY, platformY + 2, originZ + 3, originZ + platformWidth - 3, airId);
			chunk.setBlocks(originX + 2, originX + platformWidth - 2, platformY + 2, platformY + 3, originZ + 2, originZ + platformWidth - 2, roofId);
		}
	}
	
	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, DataContext context, int platX, int platZ) {

		// compute offset to start of chunk
		int platformOffset = platformWidth / 2;
		int originX = Math.min(platformOffset, Math.max(chunk.width - platformOffset - 1, maxHeightX));
		int originZ = Math.min(platformOffset, Math.max(chunk.width - platformOffset - 1, maxHeightZ));
		int platformY = maxHeight + 2;
		
		// place snow
		generateSurface(generator, chunk, false);
		
		// place a door
		if (building)
			chunk.setWoodenDoor(originX + 2, platformY, originZ + 3, Direction.Door.WESTBYNORTHWEST);
		
		// blow it all up?
		if (generator.settings.includeDecayedBuildings) {
			destroyWithin(generator, chunk, originX, originX + platformWidth, platformY - 2, platformY + 3, originZ, originZ + platformWidth);
			
		} else {
			
			// place the ladder
			int ladderBase = platformY - 2;
			while (chunk.isEmpty(originX, ladderBase, originZ + 4)) {
				ladderBase--;
			}
			chunk.setLadder(originX, ladderBase, platformY, originZ + 4, Direction.Ladder.WEST);
			chunk.setBlock(originX, platformY, originZ + 4, airMaterial);
			
			// place antennas
			generateAntenna(chunk, context, originX + 1, platformY, originZ + 1, false);
			generateAntenna(chunk, context, originX + 1, platformY, originZ + platformWidth - 2, false);
			generateAntenna(chunk, context, originX + platformWidth - 2, platformY, originZ + 1, false);
			generateAntenna(chunk, context, originX + platformWidth - 2, platformY, originZ + platformWidth - 2, true);
		}
	}
	
	private void generateAntenna(RealChunk chunk, DataContext context, int x, int y, int z, boolean lastChance) {
		
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
				chunk.setTorch(x, y + 2 + antennaHeight + 1, z, context.torchMat, Direction.Torch.FLOOR);
			} else
				chunk.setBlock(x, y + 2 + antennaHeight, z, capTinyMat);
		}
	}
}
