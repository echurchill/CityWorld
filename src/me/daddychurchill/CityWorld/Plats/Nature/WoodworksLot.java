package me.daddychurchill.CityWorld.Plats.Nature;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.ConstructLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.CoverProvider.CoverageType;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import me.daddychurchill.CityWorld.Support.BlackMagic;
import me.daddychurchill.CityWorld.Support.BadMagic.General;
import me.daddychurchill.CityWorld.Support.BadMagic.Stair;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.InitialBlocks;

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
	protected void generateActualChunk(CityWorldGenerator generator,
			PlatMap platmap, InitialBlocks chunk, BiomeGrid biomes,
			DataContext context, int platX, int platZ) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void generateActualBlocks(CityWorldGenerator generator,
			PlatMap platmap, RealBlocks chunk, DataContext context, int platX,
			int platZ) {
		
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
			generator.coverProvider.generateCoverage(generator, chunk, 
					x + chunkOdds.getRandomInt(1, 4), y, z, CoverageType.SHORT_OAK_TREE);
			break;
		case 2:
		case 3:
			generator.coverProvider.generateCoverage(generator, chunk, 
					x + chunkOdds.getRandomInt(1, 4), y, z, CoverageType.OAK_TRUNK);
			break;
		case 4:
		case 5:
			int logL = chunkOdds.getRandomInt(3, 3);
			int logX = x + 6 - logL;
			if (chunkOdds.flipCoin())
				BlackMagic.setBlocks(chunk, logX, logX + logL, y, z - 1, z, Material.LOG, 4);
			BlackMagic.setBlocks(chunk, logX, logX + logL, y, z, z + 1, Material.LOG, 4);
			if (chunkOdds.flipCoin())
				BlackMagic.setBlocks(chunk, logX, logX + logL, y, z + 1, z + 2, Material.LOG, 4);
			break;
		case 6:
		case 7:
			if (chunkOdds.flipCoin())
				chunk.setBlocks(x + 1, x + 5, y, y + chunkOdds.getRandomInt(1, 2), z - 1, z, Material.WOOD);
			chunk.setBlocks(x + 1, x + 5, y, y + chunkOdds.getRandomInt(1, 2), z, z + 1, Material.WOOD);
			if (chunkOdds.flipCoin())
				chunk.setBlocks(x + 1, x + 5, y, y + chunkOdds.getRandomInt(1, 2), z + 1, z + 2, Material.WOOD);
			break;
		case 8:
			if (chunkOdds.flipCoin())
				chunk.setChest(generator, x + 1, y, z, General.SOUTH, chunkOdds, 
						generator.lootProvider, LootLocation.WOODWORKS);
			if (chunkOdds.flipCoin())
				chunk.setBlock(x + 3, y, z, Material.WORKBENCH);
			if (chunkOdds.flipCoin())
				chunk.setBlock(x + 4, y, z, Material.FURNACE);
			break;
		case 9:
			General direction = General.NORTH;
			if (chunkOdds.flipCoin()) {
				if (chunkOdds.flipCoin())
					direction = General.SOUTH;
				chunk.setDoubleChest(generator, chunkOdds.calcRandomRange(x + 1, x + 4), y, z, direction, chunkOdds, 
							generator.lootProvider, LootLocation.WOODWORKSOUTPUT);
			} else {
				direction = General.WEST;
				if (chunkOdds.flipCoin())
					direction = General.EAST;
				chunk.setDoubleChest(generator, x, y, chunkOdds.calcRandomRange(z + 1, z + 4), direction, chunkOdds, 
						generator.lootProvider, LootLocation.WOODWORKSOUTPUT);
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
		}
		
		//TODO add decay
	}
	
	protected final static int sectionWidth = 5;
	protected final static int floorHeight = 4;

	protected void generateSection(RealBlocks chunk, int x, int y, int z) {
		chunk.setBlocks(x, x + sectionWidth + 1, y, y + 1, z, z + sectionWidth + 1, Material.WOOD_STEP);
		
		generateColumn(chunk, x, y, z);
		generateColumn(chunk, x + sectionWidth, y, z);
		generateColumn(chunk, x, y, z + sectionWidth);
		generateColumn(chunk, x + sectionWidth, y, z + sectionWidth);
	}
	
	private void generateColumn(RealBlocks chunk, int x, int y, int z) {
//		if (chunk.isEmpty(x, y - 1, z)) {
			chunk.setBlock(x, y - floorHeight, z, Material.WOOD);
			chunk.setBlocks(x, y - floorHeight + 1, y, z, Material.FENCE);
//		}
	}
	
	protected void generateStairs(RealBlocks chunk, int x, int y, int z) {
		chunk.setBlocks(x, x + 1, y, z, z + 3, Material.AIR);
		chunk.setStair(x, y - 1, z    , Material.WOOD_STAIRS, Stair.NORTH);
		chunk.setStair(x, y - 2, z + 1, Material.WOOD_STAIRS, Stair.NORTH);
		chunk.setStair(x, y - 3, z + 2, Material.WOOD_STAIRS, Stair.NORTH);
		chunk.setStair(x, y - 4, z + 3, Material.WOOD_STAIRS, Stair.NORTH);
	}
}
