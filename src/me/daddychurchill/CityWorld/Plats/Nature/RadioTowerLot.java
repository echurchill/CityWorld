package me.daddychurchill.CityWorld.Plats.Nature;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.ConstructLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.ShortChunk;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class RadioTowerLot extends ConstructLot {

	private boolean building;
	
	private final static double oddsOfBuilding = Odds.oddsExtremelyLikely;
	public RadioTowerLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		trulyIsolated = true;
		building = chunkOdds.playOdds(oddsOfBuilding);
	}
	
	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new RadioTowerLot(platmap, chunkX, chunkZ);
	}

	private final static int platformWidth = 8;
	private final static int heightRange = 15;
	private final static int heightShortest = 10;
	private final static int heightTallest = heightShortest + heightRange;
	private boolean antennaBuilt = false;
	private boolean tallestBuilt = false;

	private final static Material platformMaterial = Material.SMOOTH_BRICK;
	private final static Material supportMaterial = Material.COBBLESTONE;
	private final static Material wallMaterial = Material.DOUBLE_STEP;
	private final static Material roofMaterial = Material.STEP;
	private final static Material baseMaterial = Material.CLAY;
	private final static Material antennaMaterial = Material.IRON_FENCE;
	private final static Material capBigMaterial = Material.DOUBLE_STEP;
	private final static Material capTinyMaterial = Material.STEP;
	
	@Override
	public int getBottomY(CityWorldGenerator generator) {
		return blockYs.maxHeight + 2;
	}
	
	@Override
	public int getTopY(CityWorldGenerator generator) {
		return getBottomY(generator) + heightTallest;
	}

	@Override
	protected void generateActualChunk(CityWorldGenerator generator, PlatMap platmap, ShortChunk chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {
		
		// compute offset to start of chunk
		int platformOffset = platformWidth / 2;
		int originX = Math.min(platformOffset, Math.max(chunk.width - platformOffset - 1, blockYs.maxHeightX));
		int originZ = Math.min(platformOffset, Math.max(chunk.width - platformOffset - 1, blockYs.maxHeightZ));
		int platformY = getBottomY(generator);
		
		// base
		for (int x = originX + 1; x < originX + platformWidth - 1; x++) {
			for (int z = originZ + 1; z < originZ + platformWidth - 1; z++) {
				for (int y = platformY - 2; y > blockYs.minHeight; y--) {
					if (!chunk.setEmptyBlock(x, y, z, supportMaterial)) {
						chunk.setBlocks(x, y - 3, y + 1, z, supportMaterial);
						break;
					}
				}
			}
		}
		
		// top it off
		chunk.setBlocks(originX, originX + platformWidth, platformY - 1, originZ, originZ + platformWidth, platformMaterial);
		
//		// base
//		if (minHeight > generator.evergreenLevel)
//			generator.oreProvider.sprinkleSnow(generator, chunk, chunkOdds, originX, originX + platformWidth, platformY, originZ, originZ + platformWidth);
//		
		// building
		if (building) {
			chunk.setBlocks(originX + 2, originX + platformWidth - 2, platformY, platformY + 2, originZ + 2, originZ + platformWidth - 2, wallMaterial);
			chunk.setBlocks(originX + 3, originX + platformWidth - 3, platformY, platformY + 2, originZ + 3, originZ + platformWidth - 3, getAirMaterial(generator, platformY));
			chunk.setBlocks(originX + 2, originX + platformWidth - 2, platformY + 2, platformY + 3, originZ + 2, originZ + platformWidth - 2, roofMaterial);
		}
	}
	
	@Override
	protected void generateActualBlocks(CityWorldGenerator generator, PlatMap platmap, RealChunk chunk, DataContext context, int platX, int platZ) {
		reportLocation(generator, "Radio Tower", chunk.getOriginX(), chunk.getOriginZ());

		// compute offset to start of chunk
		int platformOffset = platformWidth / 2;
		int originX = Math.min(platformOffset, Math.max(chunk.width - platformOffset - 1, blockYs.maxHeightX));
		int originZ = Math.min(platformOffset, Math.max(chunk.width - platformOffset - 1, blockYs.maxHeightZ));
		int platformY = blockYs.maxHeight + 2;
		
		// place snow
		generateSurface(generator, chunk, false);
		
		// place a door
		if (building)
			chunk.setWoodenDoor(originX + 2, platformY, originZ + 3, Direction.Door.WESTBYNORTHWEST);
		
		// blow it all up?
		if (generator.settings.includeDecayedBuildings) {
			generator.decayBlocks.destroyWithin(originX, originX + platformWidth, platformY - 2, platformY + 3, originZ, originZ + platformWidth);
			
		} else {
			
			// place the ladder
			int ladderBase = platformY - 2;
			while (chunk.isEmpty(originX, ladderBase, originZ + 4)) {
				ladderBase--;
			}
			chunk.setLadder(originX, ladderBase, platformY, originZ + 4, Direction.General.WEST);
			chunk.setBlock(originX, platformY, originZ + 4, getAirMaterial(generator, platformY));
			
			// place antennas
			generateAntenna(chunk, context, originX + 1, platformY, originZ + 1, false);
			generateAntenna(chunk, context, originX + 1, platformY, originZ + platformWidth - 2, false);
			generateAntenna(chunk, context, originX + platformWidth - 2, platformY, originZ + 1, false);
			generateAntenna(chunk, context, originX + platformWidth - 2, platformY, originZ + platformWidth - 2, true);
		}
	}
	
	private void generateAntenna(RealChunk chunk, DataContext context, int x, int y, int z, boolean lastChance) {
		
		// build an antenna?
		if ((lastChance && !antennaBuilt) || chunkOdds.flipCoin()) {
			chunk.setBlocks(x, y, y + 2, z, baseMaterial);
			
			// how tall?
			int antennaHeight = heightShortest;
			if (!tallestBuilt && (lastChance || chunkOdds.flipCoin())) {
				antennaHeight = heightTallest;
				tallestBuilt = true;
			} else
				antennaHeight += chunkOdds.getRandomInt(heightRange);
			
			// actually build the antenna
			chunk.setBlocks(x, y + 2, y + 2 + antennaHeight, z, antennaMaterial);
			
			// do a fancy middle?
			if (chunkOdds.flipCoin()) {
				int yPoint = y + 2 + antennaHeight - 5;
				chunk.setBlocks(x - 2, x + 3, yPoint, yPoint + 1, z, z + 1, antennaMaterial);
				chunk.setBlocks(x, x + 1, yPoint, yPoint + 1, z - 2, z + 3, antennaMaterial);
			}
			
			// do a fancy top?
			if (chunkOdds.flipCoin()) {
				int yPoint = y + 2 + antennaHeight - 1;
				chunk.setBlocks(x - 2, x + 3, yPoint, yPoint + 1, z, z + 1, antennaMaterial);
				chunk.setBlocks(x, x + 1, yPoint, yPoint + 1, z - 2, z + 3, antennaMaterial);
			}
			
			// top of the tallest one?
			if (antennaHeight == heightTallest) {
				chunk.setBlock(x, y + 2 + antennaHeight, z, capBigMaterial);
				chunk.setTorch(x, y + 2 + antennaHeight + 1, z, context.torchMat, Direction.Torch.FLOOR);
			} else
				chunk.setBlock(x, y + 2 + antennaHeight, z, capTinyMaterial);
		}
	}
}
