package me.daddychurchill.CityWorld.Plats;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.Urban.ConcreteLot;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.Direction.StairWell;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.SurroundingFloors;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public abstract class FinishedBuildingLot extends BuildingLot {

	protected Material wallMaterial;
	protected Material ceilingMaterial;
	protected Material glassMaterial;
	protected Material stairMaterial;
	protected Material stairPlatformMaterial;
	protected Material stairWallMaterial;
	protected Material doorMaterial;
	protected Material roofMaterial;
	
	//TODO columns height
	protected int insetWallWE;
	protected int insetWallNS;
	protected int insetCeilingWE;
	protected int insetCeilingNS;
	protected boolean insetInsetted;
	protected int insetInsetMidAt;
	protected int insetInsetHighAt;
	
	protected int firstFloorHeight;
	protected int otherFloorHeight;

	@Override
	public PlatLot validateLot(PlatMap platmap, int platX, int platZ) {
		
		// get connected lots
		SurroundingFloors neighborFloors = getNeighboringFloorCounts(platmap, platX, platZ);
		
		if (!neighborFloors.adjacentNeighbors() && height > 1) {
			
			// make sure we don't have needle buildings
			//platmap.generator.reportMessage("Found a skinny tall building");
			return new ConcreteLot(platmap, platmap.originX + platX, platmap.originZ + platZ);
			
		// if nothing to north/south or west/east then no insets for us
		} else if ((!neighborFloors.toNorth() && !neighborFloors.toSouth()) ||
				   (!neighborFloors.toWest() && !neighborFloors.toEast())) {

			// clear insets
//			insetInsetted = false;
		}
		
		return null;
	}

	public FinishedBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		DataContext context = platmap.context;

		// calculate the defaults
		calculateOptions(context);
		
		// floorheight
		firstFloorHeight = aboveFloorHeight;
		otherFloorHeight = aboveFloorHeight;
		
		// what is it made of?
		wallMaterial = pickWallMaterial();
		ceilingMaterial = pickCeilingMaterial();
		glassMaterial = pickGlassMaterial();
		columnMaterial = pickColumnMaterial(wallMaterial);
		stairMaterial = pickStairMaterial(wallMaterial);
		stairPlatformMaterial = pickStairPlatformMaterial(stairMaterial);
		doorMaterial = Material.WOOD_DOOR;
		roofMaterial = pickRoofMaterial();
		
		// what are the walls of the stairs made of?
		if (chunkOdds.playOdds(context.oddsOfStairWallMaterialIsWallMaterial))
			stairWallMaterial = wallMaterial;
		else
			stairWallMaterial = pickStairWallMaterial(wallMaterial);

		// final validation
		validateOptions();
	}
	
	protected void calculateOptions(DataContext context) {
		
		// how do the walls inset?
		insetWallWE = chunkOdds.getRandomInt(context.rangeOfWallInset) + 1; // 1 or 2
		insetWallNS = chunkOdds.getRandomInt(context.rangeOfWallInset) + 1;
		
		// what about the ceiling?
		if (chunkOdds.playOdds(context.oddsOfFlatWalledBuildings)) {
			insetCeilingWE = insetWallWE;
			insetCeilingNS = insetWallNS;
		} else {
			insetCeilingWE = insetWallWE + chunkOdds.getRandomInt(3) - 1; // -1, 0 or 1 -> 0, 1, 2
			insetCeilingNS = insetWallNS + chunkOdds.getRandomInt(3) - 1;
		}
		
		// make the buildings have a better chance at being round
		if (chunkOdds.playOdds(context.oddsOfSimilarInsetBuildings)) {
			insetWallNS = insetWallWE;
			insetCeilingNS = insetCeilingWE;
		}
		
		// nudge in a bit more as we go up
		insetInsetMidAt = 1;
		insetInsetHighAt = 1;
		insetInsetted = height >= context.buildingWallInsettedMinLowPoint && chunkOdds.playOdds(context.oddsOfBuildingWallInset);
		if (insetInsetted) {
			insetInsetMidAt = Math.max(context.buildingWallInsettedMinMidPoint, 
					chunkOdds.getRandomInt(context.buildingWallInsettedMinLowPoint));
			insetInsetHighAt = Math.max(insetInsetMidAt + 1, chunkOdds.getRandomInt(context.buildingWallInsettedMinLowPoint));
		}
	}
	
	protected void validateOptions() {
		// Fix up any material issues
		// thin glass should not be used with ceiling inset, it looks goofy
		// thin glass should not be used with double-step walls, the glass does not align correctly
		if (glassMaterial == Material.THIN_GLASS) {
			insetCeilingWE = Math.min(insetCeilingWE, insetWallWE);
			insetCeilingNS = Math.min(insetCeilingNS, insetWallNS);
			if (wallMaterial == Material.DOUBLE_STEP)
				glassMaterial = Material.GLASS;
		}
	}

	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);

		// other bits
		if (result && relative instanceof FinishedBuildingLot) {
			FinishedBuildingLot relativebuilding = (FinishedBuildingLot) relative;

			// nudge in a bit
			insetWallWE = relativebuilding.insetWallWE;
			insetWallNS = relativebuilding.insetWallNS;
			insetCeilingWE = relativebuilding.insetCeilingWE;
			insetCeilingNS = relativebuilding.insetCeilingNS;
			
			// nudge in a bit more as we go up
			insetInsetted = relativebuilding.insetInsetted;
			insetInsetMidAt = relativebuilding.insetInsetMidAt;
			insetInsetHighAt = relativebuilding.insetInsetHighAt;
			
			// what is it made of?
			wallMaterial = relativebuilding.wallMaterial;
			ceilingMaterial = relativebuilding.ceilingMaterial;
			glassMaterial = relativebuilding.glassMaterial;
			//stairStyle can be different for each chunk
			stairMaterial = relativebuilding.stairMaterial;
			stairWallMaterial = relativebuilding.stairWallMaterial;
			stairPlatformMaterial = relativebuilding.stairPlatformMaterial;
			doorMaterial = relativebuilding.doorMaterial;
			roofMaterial = relativebuilding.roofMaterial;
		}
		return result;
	}

	@Override
	public int getBottomY(CityWorldGenerator generator) {
		int result = generator.streetLevel;
		if (depth > 0)
			result -= basementFloorHeight * (depth - 1) + 3;
		return result;
	}
	
	@Override
	public int getTopY(CityWorldGenerator generator) {
		return generator.streetLevel + firstFloorHeight + (height * aboveFloorHeight);
	}
	
	@Override
	protected void generateActualChunk(CityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {

		// check out the neighbors
		SurroundingFloors neighborBasements = getNeighboringBasementCounts(platmap, platX, platZ);
		SurroundingFloors neighborFloors = getNeighboringFloorCounts(platmap, platX, platZ);

		// is rounding allowed?
		boolean allowRounded = rounded && 
							   insetWallWE == insetWallNS && 
							   insetCeilingWE == insetCeilingNS &&
							   neighborFloors.isRoundable();
		
		// starting with the bottom
		int lowestY = getBottomY(generator);
		
		// bottom most floor
		drawCeilings(generator, chunk, context, lowestY, 1, 0, 0, false, ceilingMaterial, neighborBasements);
		//chunk.setBlocks(0, chunk.width, lowestY, lowestY + 1, 0, chunk.width, (byte) ceilingMaterial.getId());
		
		// below ground
		if (depth > 0) {
			for (int floor = 0; floor < depth; floor++) {
				int floorAt = generator.streetLevel - basementFloorHeight * floor - 2;
	
				// clear it out
				chunk.setLayer(floorAt, basementFloorHeight, getAirMaterial(generator, floorAt));
	
				// one floor please
				drawExteriorParts(generator, chunk, context, floorAt, basementFloorHeight - 1, 0, 0,
						false, wallMaterial, wallMaterial, neighborBasements);
				drawCeilings(generator, chunk, context, floorAt + basementFloorHeight - 1, 1, 0, 0,
						false, ceilingMaterial, neighborBasements);
				
				// one down, more to go
				neighborBasements.decrement();
			}
		} else {
			chunk.setLayer(lowestY + 1, ceilingMaterial);
		}

		// insetting the inset
		int localInsetWallWE = insetWallWE;
		int localInsetWallNS = insetWallNS;
		int localInsetCeilingWE = insetCeilingWE;
		int localInsetCeilingNS = insetCeilingNS;

		// above ground
		aboveFloorHeight = firstFloorHeight;
		for (int floor = 0; floor < height; floor++) {
			int floorAt = generator.streetLevel + aboveFloorHeight * floor + 2;
			allowRounded = allowRounded && neighborFloors.isRoundable();

			// breath in?
			if (insetInsetted) {
				if (floor == insetInsetMidAt || floor == insetInsetHighAt) {
					localInsetWallWE++;
					localInsetWallNS++;
					localInsetCeilingWE++;
					localInsetCeilingNS++;
				}
			}
			
			// one floor please
			drawExteriorParts(generator, chunk, context, floorAt, 
					aboveFloorHeight - 1, localInsetWallNS,  
					localInsetWallWE, allowRounded, wallMaterial, 
					glassMaterial, neighborFloors);
			drawCeilings(generator, chunk, context, floorAt + aboveFloorHeight - 1, 
					1, localInsetCeilingNS, 
					localInsetCeilingWE, allowRounded, ceilingMaterial, neighborFloors);
			
			// final floor is done... how about a roof then?
			if (floor == height - 1)
				drawRoof(generator, chunk, context, generator.streetLevel + aboveFloorHeight * (floor + 1) + 2, localInsetWallNS, localInsetWallWE, allowRounded, roofMaterial, neighborFloors);

			// one down, more to go
			neighborFloors.decrement();
			aboveFloorHeight = otherFloorHeight;
		}
	}
	
	@Override
	protected void generateActualBlocks(CityWorldGenerator generator, PlatMap platmap, RealBlocks chunk, DataContext context, int platX, int platZ) {

		// check out the neighbors
		//SurroundingFloors neighborBasements = getNeighboringBasementCounts(platmap, platX, platZ);
		SurroundingFloors neighborFloors = getNeighboringFloorCounts(platmap, platX, platZ);
		
		// is rounding allowed and where are the stairs
		boolean allowRounded = rounded && 
				insetWallWE == insetWallNS && 
				insetCeilingWE == insetCeilingNS &&
				neighborFloors.isRoundable();
		
		StairWell stairLocation = getStairWellLocation(allowRounded, neighborFloors);
		if (!needStairsUp)
			stairLocation = StairWell.NONE;
		
		// work on the basement stairs first
		for (int floor = 0; floor < depth; floor++) {
			int floorAt = generator.streetLevel - basementFloorHeight * floor - 2;
			
			// stairs?
			if (needStairsDown) {
				
				// top is special... but only if there are no stairs up
				if (floor == 0 && !needStairsUp) {
					drawStairsWalls(generator, chunk, floorAt, 
							basementFloorHeight, stairLocation, stairWallMaterial, true, false);
				
				// all the rest of those lovely stairs
				} else {

					// plain walls please
					drawStairsWalls(generator, chunk, floorAt, basementFloorHeight, 
							stairLocation, wallMaterial, false, floor == depth - 1);

					// place the stairs and such
					drawStairs(generator, chunk, floorAt, basementFloorHeight, 
							stairLocation, stairMaterial, stairPlatformMaterial);
						
					// pillars if no stairs here
					drawOtherPillars(chunk, floorAt, basementFloorHeight, 
							stairLocation, wallMaterial);
				}
			
			// if no stairs then
			} else {
				drawOtherPillars(chunk, floorAt, basementFloorHeight, 
						StairWell.CENTER, wallMaterial);
			}
		}
		
		// insetting the inset
		int localInsetWallWE = insetWallWE;
		int localInsetWallNS = insetWallNS;

		// now the above ground floors
		aboveFloorHeight = firstFloorHeight;
		for (int floor = 0; floor < height; floor++) {
			int floorAt = generator.streetLevel + aboveFloorHeight * floor + 2;
//			allowRounded = allowRounded && neighborFloors.isRoundable();
//			stairLocation = getStairWellLocation(allowRounded, neighborFloors);
//			if (!needStairsUp || floor == height - 1)
//				stairLocation = StairWell.NONE;
			
			// breath in?
			if (insetInsetted) {
				if (floor == insetInsetMidAt || floor == insetInsetHighAt) {
					localInsetWallWE++;
					localInsetWallNS++;
				}
			}
			
			// inside walls
			drawInteriorParts(generator, chunk, context, 
					roomProviderForFloor(generator, chunk, floor, floorAt), floor, floorAt, 
					aboveFloorHeight - 1, localInsetWallNS, localInsetWallWE, 
					allowRounded, wallMaterial, glassMaterial, 
					stairLocation, stairMaterial, stairWallMaterial, stairPlatformMaterial,
					needStairsUp && (floor > 0 || (floor == 0 && (depth > 0 || height > 1))), 
					needStairsUp && (floor < height - 1), 
					floor == height - 1, floor == 0 && depth == 0,
					neighborFloors);
				
//			// stairs?
//			if (needStairsUp) {
//				
//				// fancy walls... maybe
//				if (floor > 0 || (floor == 0 && (depth > 0 || height > 1))) {
//					drawStairsWalls(chunk, floorAt, aboveFloorHeight, stairLocation, 
//							stairWallMaterial, floor == height - 1, floor == 0 && depth == 0);
//				}
//				
//				// more stairs and such
//				if (floor < height - 1)
//					drawStairs(chunk, floorAt, aboveFloorHeight, stairLocation, 
//							stairMaterial, stairPlatformMaterial);
//			}
			
			// one down, more to go
			neighborFloors.decrement();
			aboveFloorHeight = otherFloorHeight;
		}
		
		// happy place?
		if (!generator.settings.includeDecayedBuildings) {
		
			// maybe draw a navlight?
			drawNavLight(chunk, context);
			
		// nope, let's destroy our work!
		} else {
			int y1 = generator.streetLevel + 2;
			int y2 = y1 + aboveFloorHeight * height;
			switch (roofStyle) {
			case EDGED:
			case FLATTOP:
				if (roofFeature == RoofFeature.ANTENNAS)
					y2 -= aboveFloorHeight;
				break;
			case PEAK:
			case TENT_WESTEAST:
			case TENT_NORTHSOUTH:
				y2 += aboveFloorHeight;
				break;
			}
			destroyLot(generator, y1, y2);
		}
	}
	
	//TODO make the material settable by users
	protected Material pickWallMaterial() {
		switch (chunkOdds.getRandomInt(20)) {
		case 1:
			return Material.COBBLESTONE;
		case 2:
			return Material.SAND;
		case 3:
			return Material.GRAVEL;
		case 4:
			return Material.WOOD;
		case 5:
			return Material.SANDSTONE;
		case 6:
			return Material.WOOL;
		case 7:
			return Material.DOUBLE_STEP;
		case 8:
			return Material.BRICK;
		case 9:
			return Material.MOSSY_COBBLESTONE;
		case 10:
			return Material.CLAY;
		case 11:
			return Material.NETHERRACK;
		case 12:
			return Material.SOUL_SAND;
		case 13:
			return Material.SMOOTH_BRICK;
		case 14:
			return Material.NETHER_BRICK;
		case 15:
			return Material.QUARTZ_BLOCK;
		case 16:
			return Material.HARD_CLAY;
		case 17:
			return Material.STAINED_CLAY;
		case 18:
			return Material.COAL_BLOCK;
		case 19:
			return Material.ENDER_STONE;
		default:
			return Material.STONE;
		}
	}

	protected Material pickRoofMaterial() {
		switch (chunkOdds.getRandomInt(17)) {
		case 1:
			return Material.COBBLESTONE;
		case 2:
			return Material.WOOD;
		case 3:
			return Material.SANDSTONE;
		case 4:
			return Material.WOOL;
		case 5:
			return Material.DOUBLE_STEP;
		case 6:
			return Material.BRICK;
		case 7:
			return Material.MOSSY_COBBLESTONE;
		case 8:
			return Material.CLAY;
		case 9:
			return Material.NETHERRACK;
		case 10:
			return Material.SMOOTH_BRICK;
		case 11:
			return Material.NETHER_BRICK;
		case 12:
			return Material.QUARTZ_BLOCK;
		case 13:
			return Material.HARD_CLAY;
		case 14:
			return Material.STAINED_CLAY;
		case 15:
			return Material.COAL_BLOCK;
		case 16:
			return Material.ENDER_STONE;
		default:
			return Material.STONE;
		}
	}

	protected Material pickColumnMaterial(Material wall) {
		switch (wall) {
		case COBBLESTONE:
		case MOSSY_COBBLESTONE:
		case WOOL:
			return Material.COBBLE_WALL;

		case NETHERRACK:
			return Material.NETHER_FENCE;
			
		default: 
			if (chunkOdds.playOdds(Odds.oddsSomewhatUnlikely))
				return Material.FENCE;
			else
				return wall;
		}
	}

	protected Material pickStairMaterial(Material wall) {
		switch (wall) {
		case COBBLESTONE:
		case MOSSY_COBBLESTONE:
			return Material.COBBLESTONE_STAIRS;

		case STONE:
		case SMOOTH_BRICK:
		case CLAY:
		case DOUBLE_STEP:
			return Material.SMOOTH_STAIRS;

		case WOOL:
		case BRICK:
			return Material.BRICK_STAIRS;
		
		case NETHERRACK:
		case NETHER_BRICK:
			return Material.NETHER_BRICK_STAIRS;
			
		case SANDSTONE:
		case SAND:
			return Material.SANDSTONE_STAIRS;
			
		case QUARTZ_BLOCK:
		case COAL_BLOCK:
			return Material.QUARTZ_STAIRS;
		
		default: // all other materials
			return Material.WOOD_STAIRS;
		}
	}

	private Material pickStairPlatformMaterial(Material stair) {
		switch (stair) {
		case COBBLESTONE_STAIRS:
			return Material.COBBLESTONE;
		case SMOOTH_STAIRS:
			return Material.SMOOTH_BRICK;
		case BRICK_STAIRS:
			return Material.BRICK;
		case NETHER_BRICK_STAIRS:
			return Material.NETHER_BRICK;
		case SANDSTONE_STAIRS:
			return Material.SANDSTONE;
		case QUARTZ_STAIRS:
			return Material.QUARTZ_BLOCK;
		default:
			return Material.WOOD;
		}
	}
	
	protected Material pickStairWallMaterial(Material wall) {
		switch (wall) {
		case COBBLESTONE:
		case MOSSY_COBBLESTONE:
		case STONE:
		case SMOOTH_BRICK:
			return Material.IRON_FENCE;

		case DOUBLE_STEP:
		case WOOL:
		case BRICK:
		case COAL_BLOCK:
			return Material.THIN_GLASS;
		
		case NETHERRACK:
		case NETHER_BRICK:
			return Material.NETHER_FENCE;
		
		default: // SANDSTONE, WOOD, SAND, CLAY, HARD_CLAY, STAINED_CLAY
			if (chunkOdds.playOdds(Odds.oddsSomewhatUnlikely))
				return Material.GLASS;
			else
				return Material.FENCE;
		}
	}

	protected Material pickCeilingMaterial() {
		switch (chunkOdds.getRandomInt(16)) {
		case 1:
			return Material.COBBLESTONE;
		case 2:
			return Material.WOOD;
		case 3:
			return Material.SANDSTONE;
		case 4:
			return Material.WOOL;
		case 5:
			return Material.DOUBLE_STEP;
		case 6:
			return Material.BRICK;
		case 7:
			return Material.MOSSY_COBBLESTONE;
		case 8:
			return Material.CLAY;
		case 9:
			return Material.NETHERRACK;
		case 10:
			return Material.SMOOTH_BRICK;
		case 11:
			return Material.NETHER_BRICK;
		case 12:
			return Material.QUARTZ_BLOCK;
		case 13:
			return Material.HARD_CLAY;
		case 14:
			return Material.STAINED_CLAY;
		case 15:
			return Material.COAL_BLOCK;
		default:
			return Material.STONE;
		}
	}
}
