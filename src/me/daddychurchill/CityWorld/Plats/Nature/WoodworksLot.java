package me.daddychurchill.CityWorld.Plats.Nature;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.ConstructLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.CoverProvider.CoverageType;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class WoodworksLot extends ConstructLot {

	public WoodworksLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new WoodworksLot(platmap, chunkX, chunkZ);
	}

	@Override
	protected void generateActualChunk(CityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			BiomeGrid biomes, DataContext context, int platX, int platZ) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void generateActualBlocks(CityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ) {

		int y = generator.streetLevel + 1;
		generateSomething(generator, chunk, 2, y, 3);
		generateSomething(generator, chunk, 8, y, 6);
		generateSomething(generator, chunk, 2, y, 9);
		generateSomething(generator, chunk, 8, y, 12);

		// place snow
		generateSurface(generator, chunk, false);
	}

	private void generateSomething(CityWorldGenerator generator, RealBlocks chunk, int x, int y, int z) {
		switch (chunkOdds.getRandomInt(11)) {
		default:
		case 0:
		case 1:
			// shimmy
			generator.coverProvider.generateCoverage(generator, chunk, chunk.clampXZ(x + chunkOdds.getRandomInt(1, 4)),
					y, z, CoverageType.SHORT_OAK_TREE);
			break;
		case 2:
		case 3:
			generator.coverProvider.generateCoverage(generator, chunk, chunk.clampXZ(x + chunkOdds.getRandomInt(1, 4)),
					y, z, CoverageType.OAK_TRUNK);
			break;
		case 4:
		case 5:
			int logL = chunkOdds.getRandomInt(3, 3);
			int logX = x + 6 - logL;
			if (chunkOdds.flipCoin())
				chunk.setBlocks(logX, chunk.clampXZ(logX + logL), y, z - 1, z, Material.OAK_LOG, BlockFace.EAST);
			chunk.setBlocks(logX, logX + logL, y, z, z + 1, Material.OAK_LOG, BlockFace.EAST);
			if (chunkOdds.flipCoin())
				chunk.setBlocks(logX, chunk.clampXZ(logX + logL), y, z + 1, z + 2, Material.OAK_LOG, BlockFace.EAST);
			break;
		case 6:
		case 7:
			if (chunkOdds.flipCoin())
				chunk.setBlocks(x + 1, x + 5, y, y + chunkOdds.getRandomInt(1, 2), z - 1, z, Material.OAK_PLANKS);
			chunk.setBlocks(x + 1, x + 5, y, y + chunkOdds.getRandomInt(1, 2), z, z + 1, Material.OAK_PLANKS);
			if (chunkOdds.flipCoin())
				chunk.setBlocks(x + 1, x + 5, y, y + chunkOdds.getRandomInt(1, 2), z + 1, z + 2, Material.OAK_PLANKS);
			break;
		case 8:
			if (chunkOdds.flipCoin())
				chunk.setChest(generator, x + 1, y, z, chunkOdds, generator.lootProvider,
						LootLocation.WOODWORKS);
			if (chunkOdds.flipCoin())
				chunk.setBlock(x + 3, y, z, Material.CRAFTING_TABLE);
			if (chunkOdds.flipCoin())
				chunk.setBlock(x + 4, y, z, Material.FURNACE);
			break;
		case 9:
			BlockFace direction = BlockFace.NORTH;
			if (chunkOdds.flipCoin()) {
				if (chunkOdds.flipCoin())
					direction = BlockFace.SOUTH;
				chunk.setDoubleChest(generator, chunk.clampXZ(chunkOdds.calcRandomRange(x + 1, x + 4)), y, z, direction,
						chunkOdds, generator.lootProvider, LootLocation.WOODWORKS_OUTPUT);
			} else {
				direction = BlockFace.WEST;
				if (chunkOdds.flipCoin())
					direction = BlockFace.EAST;
				chunk.setDoubleChest(generator, x, y, chunk.clampXZ(chunkOdds.calcRandomRange(z + 1, z + 4)), direction,
						chunkOdds, generator.lootProvider, LootLocation.WOODWORKS_OUTPUT);
			}
			break;
		case 10:
			if (chunkOdds.flipCoin()) {
				x = x / 8 * 8;
				z = z / 8 * 8;
				generateSection(chunk, x, y + floorHeight, z);
				if (chunkOdds.flipCoin())
					generateStairs(chunk, x + 4, y + floorHeight, z + 1);
			}
			break;
		case 11:
			generator.structureOnGroundProvider.generateFirePit(generator, chunk, chunkOdds, x, y, z);
		}

		// TODO add decay
	}

	final static int sectionWidth = 5;
	final static int floorHeight = 4;

	void generateSection(RealBlocks chunk, int x, int y, int z) {
		chunk.setBlocks(x, x + sectionWidth + 1, y, y + 1, z, z + sectionWidth + 1, Material.BIRCH_SLAB);

		generateColumn(chunk, x, y, z);
		generateColumn(chunk, x + sectionWidth, y, z);
		generateColumn(chunk, x, y, z + sectionWidth);
		generateColumn(chunk, x + sectionWidth, y, z + sectionWidth);
	}

	private void generateColumn(RealBlocks chunk, int x, int y, int z) {
//		if (chunk.isEmpty(x, y - 1, z)) {
//		chunk.setBlock(x, y - floorHeight, z, Material.SPRUCE_PLANKS);
//		chunk.setBlocks(x, y - floorHeight + 1, y, z, Material.SPRUCE_FENCE);
		chunk.setBlocks(x, y - floorHeight, y, z, Material.SCAFFOLDING);
//		}
	}

	void generateStairs(RealBlocks chunk, int x, int y, int z) {
		chunk.setBlocks(x, x + 1, y, z, z + 3, Material.AIR);
		chunk.setBlock(x, y - 1, z, Material.BIRCH_STAIRS, BlockFace.NORTH);
		chunk.setBlock(x, y - 2, z + 1, Material.BIRCH_STAIRS, BlockFace.NORTH);
		chunk.setBlock(x, y - 3, z + 2, Material.BIRCH_STAIRS, BlockFace.NORTH);
		chunk.setBlock(x, y - 4, z + 3, Material.BIRCH_STAIRS, BlockFace.NORTH);
	}
}
