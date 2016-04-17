package me.daddychurchill.CityWorld.Plats.Urban;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.SurroundingFloors;

public class FactoryBuildingLot extends IndustrialBuildingLot {
	
	private final static double oddsOfSimilarContent = Odds.oddsUnlikely;

	private enum ContentStyle {TANK, TANKS, PIT, SMOKESTACK, OFFICE}; 
	private ContentStyle contentStyle;
	
	public FactoryBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		firstFloorHeight = aboveFloorHeight * (chunkOdds.getRandomInt(3) + 3);
		insetWallNS = 1;
		insetWallWE = 1;
		insetCeilingNS = 1;
		insetCeilingWE = 1;
		
		contentStyle = pickContentStyle(chunkOdds);
	}
	
	public ContentStyle pickContentStyle(Odds odds) {
		ContentStyle[] values = ContentStyle.values();
		return values[odds.getRandomInt(values.length)];
	}
	
	@Override
	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);
		
		// other bits
		if (result && relative instanceof FactoryBuildingLot) {
			FactoryBuildingLot relativebuilding = (FactoryBuildingLot) relative;

			// any other bits
			firstFloorHeight = relativebuilding.firstFloorHeight;
			
			if (chunkOdds.playOdds(oddsOfSimilarContent))
				contentStyle = relativebuilding.contentStyle;
		}
		
		return result;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new FactoryBuildingLot(platmap, chunkX, chunkZ);
	}

	@Override
	protected InteriorStyle pickInteriorStyle() {
		return InteriorStyle.EMPTY;
	}
	
	@Override
	protected void generateActualBlocks(CityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ) {
		super.generateActualBlocks(generator, platmap, chunk, context, platX, platZ);
		SurroundingFloors neighborFloors = getNeighboringFloorCounts(platmap, platX, platZ);
		
		int groundY = generator.structureLevel + 2;
		int skywalkHeight = firstFloorHeight / 2;
		int skywalkAt = groundY + skywalkHeight;
		
		Material airMat = generator.shapeProvider.findAtmosphereMaterialAt(generator, groundY);
		Material wallMat = generator.settings.materials.itemsSelectMaterial_Factories.getRandomMaterial(chunkOdds, Material.SMOOTH_BRICK);
		Material officeMat = generator.settings.materials.itemsSelectMaterial_Factories.getRandomMaterial(chunkOdds, Material.SMOOTH_BRICK);
		Material supportMat = generator.settings.materials.itemsSelectMaterial_Factories.getRandomMaterial(chunkOdds, Material.CLAY);
		Material smokestackMat = generator.settings.materials.itemsSelectMaterial_Factories.getRandomMaterial(chunkOdds, Material.CLAY);
		Material fluidMat = generator.settings.materials.itemsSelectMaterial_FactoryTanks.getRandomMaterial(chunkOdds, Material.STATIONARY_WATER);
		int amountOfContent = chunkOdds.getRandomInt(1, 3);

		switch (contentStyle) {
		case OFFICE:
			chunk.setWalls(3, 13, groundY, groundY + 1, 3, 13, officeMat);
			chunk.setWalls(3, 13, groundY + 1, groundY + 2, 3, 13, Material.THIN_GLASS);
			chunk.setWalls(3, 13, groundY + 2, skywalkAt, 3, 13, officeMat);
			chunk.setBlocks(3, 13, skywalkAt, 3, 13, officeMat);
			generateOpenings(chunk, groundY);
			
			if (skywalkAt - groundY > aboveFloorHeight) {
				int secondY = groundY + aboveFloorHeight;
				chunk.setBlocks(3, 13, secondY, 3, 13, officeMat);
				chunk.setWalls(3, 13, secondY + 1, secondY + 2, 3, 13, Material.THIN_GLASS);
			}

			chunk.setWalls(3, 13, skywalkAt + 1, skywalkAt + 2, 3, 13, officeMat);
			chunk.setWalls(3, 13, skywalkAt + 2, skywalkAt + 3, 3, 13, Material.THIN_GLASS);
			chunk.setWalls(3, 13, skywalkAt + 3, skywalkAt + 4, 3, 13, officeMat);
			chunk.setBlocks(3, 13, skywalkAt + 4, 3, 13, officeMat);
			generateOpenings(chunk, skywalkAt + 1);
			
			chunk.setBlocks(5, groundY, skywalkAt + 2, 5, officeMat);
			chunk.setLadder(5, groundY, skywalkAt + 2, 6, BlockFace.NORTH);

			generateSkyWalkBits(generator, chunk, neighborFloors, skywalkAt);
			break;
		case PIT:
			int topOfPit = groundY + 3;
			int bottomOfPit = topOfPit - 8;
			
			chunk.setCircle(8, 8, 5, bottomOfPit - 1, wallMat, true);
			chunk.setCircle(8, 8, 5, bottomOfPit + amountOfContent, topOfPit + 1, airMat, true);
			chunk.setCircle(8, 8, 5, bottomOfPit, bottomOfPit + amountOfContent, fluidMat, true);
			chunk.setCircle(8, 8, 5, bottomOfPit, topOfPit + 1, wallMat); // put the wall up quick!

			generateSkyWalkCross(generator, chunk, neighborFloors, skywalkAt);
			break;
		case TANK:
			int bottomOfTank = groundY + 4;
			int topOfTank = skywalkAt + 2;
			
			chunk.setCircle(8, 8, 4, bottomOfTank - 1, wallMat, true);
			chunk.setCircle(8, 8, 4, bottomOfTank, bottomOfTank + amountOfContent, fluidMat, true);
			chunk.setCircle(8, 8, 4, bottomOfTank, topOfTank, wallMat); // put the wall up quick!

			chunk.setBlocks(4, 6, groundY, bottomOfTank + 1, 4, 6, supportMat);
			chunk.setBlocks(10, 12, groundY, bottomOfTank + 1, 4, 6, supportMat);
			chunk.setBlocks(4, 6, groundY, bottomOfTank + 1, 10, 12, supportMat);
			chunk.setBlocks(10, 12, groundY, bottomOfTank + 1, 10, 12, supportMat);
			
			generateSkyWalkBits(generator, chunk, neighborFloors, skywalkAt);
			break;
		case TANKS:

			chunk.setWool(3, 5, groundY, skywalkAt + chunkOdds.getRandomInt(5), 3, 5, chunkOdds.getRandomColor());
			chunk.setWool(11, 13, groundY, skywalkAt + chunkOdds.getRandomInt(5), 3, 5, chunkOdds.getRandomColor());
			chunk.setWool(3, 5, groundY, skywalkAt + chunkOdds.getRandomInt(5), 11, 13, chunkOdds.getRandomColor());
			chunk.setWool(11, 13, groundY, skywalkAt + chunkOdds.getRandomInt(5), 11, 13, chunkOdds.getRandomColor());
			
			if (!neighborFloors.toNorth())
				chunk.setWool(7, 9, groundY, skywalkAt + chunkOdds.getRandomInt(5), 3, 5, chunkOdds.getRandomColor());
			if (!neighborFloors.toSouth())
				chunk.setWool(7, 9, groundY, skywalkAt + chunkOdds.getRandomInt(5), 11, 13, chunkOdds.getRandomColor());
			if (!neighborFloors.toWest())
				chunk.setWool(3, 5, groundY, skywalkAt + chunkOdds.getRandomInt(5), 7, 9, chunkOdds.getRandomColor());
			if (!neighborFloors.toEast())
				chunk.setWool(11, 13, groundY, skywalkAt + chunkOdds.getRandomInt(5), 7, 9, chunkOdds.getRandomColor());
			
			
			generateSkyWalkCross(generator, chunk, neighborFloors, skywalkAt);
			break;
		case SMOKESTACK:
			chunk.setWalls(3, 13, groundY, skywalkAt + 3, 3, 13, officeMat);
			chunk.setBlocks(3, 13, skywalkAt + 3, 3, 13, officeMat);
			generateOpenings(chunk, groundY);

			int smokestackY1 = skywalkAt + aboveFloorHeight * chunkOdds.getRandomInt(1, 3);
			int smokestackY2 = smokestackY1 + aboveFloorHeight * chunkOdds.getRandomInt(1, 3);
			int smokestackY3 = smokestackY2 + aboveFloorHeight * chunkOdds.getRandomInt(1, 3);
			chunk.setCircle(8, 8, 2, groundY - 3, smokestackMat, true);
			chunk.setCircle(8, 8, 2, groundY - 2, smokestackY1, Material.AIR, true);
			chunk.setCircle(8, 8, 2, groundY - 2, groundY, smokestackMat);
			chunk.setCircle(8, 8, 1, groundY - 2, Material.NETHERRACK, true);
			chunk.setCircle(8, 8, 1, groundY - 1, Material.FIRE, true);
			chunk.setCircle(8, 8, 2, groundY, smokestackY1, smokestackMat);
			chunk.setWalls(6, 10, smokestackY1, smokestackY2, 6, 10, smokestackMat);
			chunk.setCircle(8, 8, 1, smokestackY2, smokestackY3, smokestackMat);
			
			chunk.pepperBlocks(7, 9, smokestackY2, smokestackY3 + 6, 7, 9, chunkOdds, Material.WEB);
			
			chunk.setGlass(8, groundY + 1, 5, DyeColor.RED);
			chunk.setGlass(7, groundY + 1, 10, DyeColor.RED);
			chunk.setGlass(5, groundY + 1, 8, DyeColor.RED);
			chunk.setGlass(10, groundY + 1, 7, DyeColor.RED);
			
			generateSkyWalkBits(generator, chunk, neighborFloors, skywalkAt);
			break;
		}
	}
	
	protected void generateOpenings(RealBlocks chunk, int y) {
		chunk.clearBlocks(7 + chunkOdds.getRandomInt(2), y, y + 2, 3);
		chunk.clearBlocks(7 + chunkOdds.getRandomInt(2), y, y + 2, 12);
		chunk.clearBlocks(3, y, y + 2, 7 + chunkOdds.getRandomInt(2));
		chunk.clearBlocks(12, y, y + 2, 7 + chunkOdds.getRandomInt(2));
	}
	
	protected void generateSkyWalkBits(CityWorldGenerator generator, RealBlocks chunk, SurroundingFloors neighbors, int skywalkAt) {
		boolean doNorthward = neighbors.toNorth();
		boolean doSouthward = neighbors.toSouth();
		boolean doWestward = neighbors.toWest();
		boolean doEastward = neighbors.toEast();
		
		if (doNorthward) {
			generateSkyWalkBitsNS(chunk, 6, 0, skywalkAt);
		}
		if (doSouthward) {
			generateSkyWalkBitsNS(chunk, 6, 13, skywalkAt);
		}
		if (doWestward) {
			generateSkyWalkBitsWE(chunk, 0, 6, skywalkAt);
		}
		if (doEastward) {
			generateSkyWalkBitsWE(chunk, 13, 6, skywalkAt);
		}
		
	}
			
	private void generateSkyWalkBitsNS(RealBlocks chunk, int x, int z, int skywalkAt) {
		chunk.setBlocks(x, x + 4, skywalkAt, z, z + 3, ceilingMaterial);
		chunk.setBlocks(x, x + 1, skywalkAt + 1, z, z + 3, Material.IRON_FENCE);
		chunk.setBlocks(x + 3, x + 4, skywalkAt + 1, z, z + 3, Material.IRON_FENCE);
	}

	private void generateSkyWalkBitsWE(RealBlocks chunk, int x, int z, int skywalkAt) {
		chunk.setBlocks(x, x + 3, skywalkAt, z, z + 4, ceilingMaterial);
		chunk.setBlocks(x, x + 3, skywalkAt + 1, z, z + 1, Material.IRON_FENCE);
		chunk.setBlocks(x, x + 3, skywalkAt + 1, z + 3, z + 4, Material.IRON_FENCE);
	}

	protected void generateSkyWalkCross(CityWorldGenerator generator, RealBlocks chunk, SurroundingFloors neighbors, 
			int skywalkAt) {
		boolean doNorthward = neighbors.toNorth();
		boolean doSouthward = neighbors.toSouth();
		boolean doWestward = neighbors.toWest();
		boolean doEastward = neighbors.toEast();
		
		if (doNorthward)
			generateSkyWalkNS(chunk, 6, 0, skywalkAt);
		if (doSouthward)
			generateSkyWalkNS(chunk, 6, 10, skywalkAt);
		if (doWestward)
			generateSkyWalkWE(chunk, 0, 6, skywalkAt);
		if (doEastward)
			generateSkyWalkWE(chunk, 10, 6, skywalkAt);
		
		chunk.setBlocks(6, 10, skywalkAt, 6, 10, ceilingMaterial);
		
		if (!doNorthward)
			chunk.setBlocks(7, 9, skywalkAt + 1, 6, 7, Material.IRON_FENCE);
		if (!doSouthward)
			chunk.setBlocks(7, 9, skywalkAt + 1, 9, 10, Material.IRON_FENCE);
		if (!doWestward)
			chunk.setBlocks(6, 7, skywalkAt + 1, 7, 9, Material.IRON_FENCE);
		if (!doEastward)
			chunk.setBlocks(9, 10, skywalkAt + 1, 7, 9, Material.IRON_FENCE);
		
		chunk.setBlocksUpward(6, skywalkAt + 1, 6, Material.IRON_FENCE);
		chunk.setBlocksUpward(6, skywalkAt + 1, 9, Material.IRON_FENCE);
		chunk.setBlocksUpward(9, skywalkAt + 1, 6, Material.IRON_FENCE);
		chunk.setBlocksUpward(9, skywalkAt + 1, 9, Material.IRON_FENCE);
	}
	
	private void generateSkyWalkNS(RealBlocks chunk, int x, int z, int skywalkAt) {
		chunk.setBlocks(x, x + 4, skywalkAt, z, z + 6, ceilingMaterial);
		chunk.setBlocks(x, x + 1, skywalkAt + 1, z, z + 6, Material.IRON_FENCE);
		chunk.setBlocks(x + 3, x + 4, skywalkAt + 1, z, z + 6, Material.IRON_FENCE);
		chunk.setBlocksUpward(x, skywalkAt + 2, z + 2, Material.IRON_FENCE);
		chunk.setBlocksUpward(x + 3, skywalkAt + 2, z + 2, Material.IRON_FENCE);
	}

	private void generateSkyWalkWE(RealBlocks chunk, int x, int z, int skywalkAt) {
		chunk.setBlocks(x, x + 6, skywalkAt, z, z + 4, ceilingMaterial);
		chunk.setBlocks(x, x + 6, skywalkAt + 1, z, z + 1, Material.IRON_FENCE);
		chunk.setBlocks(x, x + 6, skywalkAt + 1, z + 3, z + 4, Material.IRON_FENCE);
		chunk.setBlocksUpward(x + 2, skywalkAt + 2, z, Material.IRON_FENCE);
		chunk.setBlocksUpward(x + 2, skywalkAt + 2, z + 3, Material.IRON_FENCE);
	}
}
