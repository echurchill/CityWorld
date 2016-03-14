package me.daddychurchill.CityWorld.Plats;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.Urban.ConcreteLot;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.BadMagic.StairWell;
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
	protected boolean insetInverted;
	protected int insetInsetMidAt;
	protected int insetInsetHighAt;
	protected boolean outsetColumns;
	protected int outsetColumnsDivisor;
	
	protected int firstFloorHeight;
	protected int otherFloorHeight;
	
	protected CornerStyle cornerStyle;

	@Override
	public PlatLot validateLot(PlatMap platmap, int platX, int platZ) {
		
		// get connected lots
		SurroundingFloors neighborFloors = getNeighboringFloorCounts(platmap, platX, platZ);
		
		if (!neighborFloors.adjacentNeighbors() && height > 1) {
			
			// make sure we don't have needle buildings
			//platmap.generator.reportMessage("Found a skinny tall building");
			return new ConcreteLot(platmap, platmap.originX + platX, platmap.originZ + platZ);
			
		// if nothing to north/south or west/east then no insets for us
//		} else if ((!neighborFloors.toNorth() && !neighborFloors.toSouth()) ||
//				   (!neighborFloors.toWest() && !neighborFloors.toEast())) {
//
//			// clear insets
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
		wallMaterial = platmap.generator.settings.materials.itemsSelectMaterial_BuildingWalls.getRandomMaterial(chunkOdds, Material.COBBLESTONE);
		ceilingMaterial = platmap.generator.settings.materials.itemsSelectMaterial_BuildingCeilings.getRandomMaterial(chunkOdds, Material.COBBLESTONE);
		roofMaterial = platmap.generator.settings.materials.itemsSelectMaterial_BuildingRoofs.getRandomMaterial(chunkOdds, Material.COBBLESTONE);
		columnMaterial = pickColumnMaterial(wallMaterial);
		stairMaterial = pickStairMaterial(wallMaterial);
		doorMaterial = pickDoorMaterial(wallMaterial);
		stairPlatformMaterial = pickStairPlatformMaterial(stairMaterial);
		glassMaterial = pickGlassMaterial();
		
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
		int insetSegment = height / 4;
		insetInsetMidAt = 1;
		insetInsetHighAt = 1;
//		insetInsetted = height >= context.buildingWallInsettedMinLowPoint && chunkOdds.playOdds(context.oddsOfBuildingWallInset);
		insetInsetted = insetSegment > 1 && chunkOdds.playOdds(context.oddsOfBuildingWallInset);
		if (insetInsetted) {
//			insetInsetMidAt = Math.max(context.buildingWallInsettedMinMidPoint, 
//					chunkOdds.getRandomInt(context.buildingWallInsettedMinLowPoint));
//			insetInsetHighAt = Math.max(insetInsetMidAt + 1, chunkOdds.getRandomInt(context.buildingWallInsettedMinLowPoint));
			insetInsetMidAt = chunkOdds.getRandomInt(insetSegment, insetSegment * 2);
			insetInsetHighAt = chunkOdds.getRandomInt(insetInsetMidAt + 1, insetSegment * 3);
		}
		insetInverted = insetInsetted && chunkOdds.playOdds(Odds.oddsSomewhatLikely); 
		outsetColumns = chunkOdds.playOdds(Odds.oddsSomewhatLikely); 
		outsetColumnsDivisor = chunkOdds.getRandomInt(1, 5); 

		cornerStyle = pickCornerStyle();
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
			cornerStyle = CornerStyle.FILLED;
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
			insetInverted = relativebuilding.insetInverted;
			outsetColumns = relativebuilding.outsetColumns;
			outsetColumnsDivisor = relativebuilding.outsetColumnsDivisor;
			
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
			cornerStyle = relativebuilding.cornerStyle;
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
		chunk.setLayer(lowestY - 1, Material.STONE);
		drawCeilings(generator, chunk, context, lowestY, 1, 0, 0, false, /*ceilingMaterial*/ Material.DIAMOND_BLOCK, neighborBasements);
		//chunk.setBlocks(0, chunk.width, lowestY, lowestY + 1, 0, chunk.width, (byte) ceilingMaterial.getId());
		
		// below ground
		if (depth > 0) {
			for (int floor = 0; floor < depth; floor++) {
				int floorAt = generator.streetLevel - basementFloorHeight * floor - 2;
	
				// clear it out
				chunk.setLayer(floorAt, basementFloorHeight, getAirMaterial(generator, floorAt));
	
				// one floor please
				drawExteriorParts(generator, chunk, context, floorAt, basementFloorHeight - 1, 0, 0, floor,
						CornerStyle.FILLED, false, false, wallMaterial, wallMaterial, neighborBasements);
				drawCeilings(generator, chunk, context, floorAt + basementFloorHeight - 1, 1, 0, 0,
						false, ceilingMaterial, neighborBasements);
				
				// one down, more to go
				neighborBasements.decrement();
			}
//		} else {
//			chunk.setLayer(lowestY - 1, Material.GOLD_BLOCK);//ceilingMaterial);
		}

		// insetting the inset
		int localInsetWallWE = insetWallWE;
		int localInsetWallNS = insetWallNS;
		int localInsetCeilingWE = insetCeilingWE;
		int localInsetCeilingNS = insetCeilingNS;
		
		// inverted inset?
		if (insetInverted) {
			int inset = 0;
			if (insetInsetMidAt > 1)
				inset++;
			if (insetInsetHighAt > 1)
				inset++;
			
			localInsetWallWE += inset;
			localInsetWallNS += inset;
			localInsetCeilingWE += inset;
			localInsetCeilingNS += inset;
		}

		// above ground
		aboveFloorHeight = firstFloorHeight;
		for (int floor = 0; floor < height; floor++) {
			int floorAt = generator.streetLevel + aboveFloorHeight * floor + 2;
			allowRounded = allowRounded && neighborFloors.isRoundable();

			// breath in?
			if (insetInsetted) {
				if (floor == insetInsetMidAt || floor == insetInsetHighAt) {
					if (insetInverted) {
						localInsetWallWE--;
						localInsetWallNS--;
						localInsetCeilingWE--;
						localInsetCeilingNS--;
					} else {
						localInsetWallWE++;
						localInsetWallNS++;
						localInsetCeilingWE++;
						localInsetCeilingNS++;
					}
				}
			}
			
			// columns?
			boolean localOutsetColumns = outsetColumns && (localInsetCeilingNS > 0 || localInsetCeilingWE > 0);
			if (outsetColumnsDivisor > 1 && floor % outsetColumnsDivisor == 0)
				localOutsetColumns = false;
			
			// one floor please
			drawExteriorParts(generator, chunk, context, floorAt, aboveFloorHeight - 1, localInsetWallNS, localInsetWallWE, floor, 
					cornerStyle, allowRounded, localOutsetColumns, wallMaterial, glassMaterial, neighborFloors);
			drawCeilings(generator, chunk, context, floorAt + aboveFloorHeight - 1, 
					1, localInsetCeilingNS, 
					localInsetCeilingWE, allowRounded, ceilingMaterial, neighborFloors);
			
			// final floor is done... how about a roof then?
			if (floor == height - 1)
				drawRoof(generator, chunk, context, generator.streetLevel + aboveFloorHeight * (floor + 1) + 2, localInsetWallNS, localInsetWallWE, floor, 
						allowRounded, roofMaterial, neighborFloors);

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

		// inverted inset?
		if (insetInverted) {
			int inset = 0;
			if (insetInsetMidAt > 1)
				inset++;
			if (insetInsetHighAt > 1)
				inset++;
			
			localInsetWallWE += inset;
			localInsetWallNS += inset;
		}

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
					if (insetInverted) {
						localInsetWallWE--;
						localInsetWallNS--;
					} else {
						localInsetWallWE++;
						localInsetWallNS++;
					}
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
	
	protected Material pickColumnMaterial(Material wall) {
		if (chunkOdds.playOdds(Odds.oddsVeryLikely))
			return wall;
		else
			switch (wall) {
			case COBBLESTONE:
			case MOSSY_COBBLESTONE:
			case WOOL:
				return Material.COBBLE_WALL;
	
			case NETHERRACK:
			case BRICK:
			case NETHER_BRICK:
			case COAL_BLOCK:
				return Material.BIRCH_FENCE;
				
			case SAND:
			case SANDSTONE:
			case ENDER_STONE:
				return Material.NETHER_FENCE;
				
			case SOUL_SAND:
			case SMOOTH_BRICK:
			case QUARTZ_BLOCK:
				return Material.ACACIA_FENCE;
				
			case CLAY:
			case DOUBLE_STEP:
			case HARD_CLAY:
				return Material.DARK_OAK_FENCE;
				
			case GRAVEL:
			case WOOD:
				return Material.JUNGLE_FENCE;
	
			case STAINED_CLAY:
			case STONE:
				return Material.SPRUCE_FENCE;
	
			case RED_SANDSTONE:
			case DOUBLE_STONE_SLAB2:
			default:
					return Material.FENCE;
			}
	}

	protected Material pickDoorMaterial(Material wall) {
		switch (wall) {
		case COBBLESTONE:
		case MOSSY_COBBLESTONE:
		case CLAY:
		case COAL_BLOCK:
			return Material.DARK_OAK_DOOR;
			
		case QUARTZ_BLOCK:
		case DOUBLE_STEP:
		case HARD_CLAY:
			return Material.DARK_OAK_DOOR;
			
		case ENDER_STONE:
		case BRICK:
		case GRAVEL:
		case SOUL_SAND:
			return Material.ACACIA_DOOR;
			
		case SAND:
		case SANDSTONE:
		case WOOD:
		case WOOL:
			return Material.JUNGLE_DOOR;

		case NETHERRACK:
		case NETHER_BRICK:
		case STONE:
		case STAINED_CLAY:
			return Material.SPRUCE_DOOR;

		case RED_SANDSTONE:
		case DOUBLE_STONE_SLAB2:
		case SMOOTH_BRICK:
		default:
			return Material.WOOD_DOOR;
		}
	}

	protected Material pickStairMaterial(Material wall) {
		switch (wall) {
		case COBBLESTONE:
		case MOSSY_COBBLESTONE:
			return Material.COBBLESTONE_STAIRS;
			
		case NETHERRACK:
		case NETHER_BRICK:
			return Material.NETHER_BRICK_STAIRS;
			
		case SAND:
		case SANDSTONE:
			return Material.SANDSTONE_STAIRS;
			
		case ENDER_STONE:
		case BRICK:
			return Material.BRICK_STAIRS;
		
		case QUARTZ_BLOCK:
			return Material.QUARTZ_STAIRS;
			
		case CLAY:
		case COAL_BLOCK:
			return Material.BIRCH_WOOD_STAIRS;
			
		case DOUBLE_STEP:
		case HARD_CLAY:
			return Material.DARK_OAK_STAIRS;
			
		case GRAVEL:
		case SOUL_SAND:
			return Material.ACACIA_STAIRS;
			
		case WOOD:
		case WOOL:
			return Material.JUNGLE_WOOD_STAIRS;

		case STONE:
		case STAINED_CLAY:
			return Material.SPRUCE_WOOD_STAIRS;

		case RED_SANDSTONE:
		case DOUBLE_STONE_SLAB2:
			return Material.RED_SANDSTONE_STAIRS;

		case SMOOTH_BRICK:
		default:
			return Material.SMOOTH_STAIRS;
		}
	}

	private Material pickStairPlatformMaterial(Material stair) {
		switch (stair) {
		case COBBLESTONE_STAIRS:
			return Material.COBBLESTONE;
			
		case NETHER_BRICK_STAIRS:
			return Material.NETHERRACK;
			
		case SANDSTONE_STAIRS:
			return Material.SANDSTONE;
			
		case BRICK_STAIRS:
			return Material.BRICK;
		
		case QUARTZ_STAIRS:
			return Material.QUARTZ_BLOCK;
			
		case BIRCH_WOOD_STAIRS:
			return Material.CLAY;
			
		case DARK_OAK_STAIRS:
			return Material.DOUBLE_STEP;
			
		case ACACIA_STAIRS:
			return Material.GRAVEL;
			
		case JUNGLE_WOOD_STAIRS:
			return Material.WOOD;

		case SPRUCE_WOOD_STAIRS:
			return Material.STONE;

		case RED_SANDSTONE_STAIRS:
			return Material.RED_SANDSTONE;

		default:
			return Material.SMOOTH_BRICK;
		}
	}
	
	protected Material pickStairWallMaterial(Material wall) {
		if (chunkOdds.flipCoin())
			return pickColumnMaterial(wall);
		else
			switch (wall) {
			case COBBLESTONE:
			case MOSSY_COBBLESTONE:
			case STONE:
			case SMOOTH_BRICK:
			case SOUL_SAND:
			case QUARTZ_BLOCK:
			case SAND:
			case SANDSTONE:
			case ENDER_STONE:
				return Material.IRON_FENCE;
	
			case WOOL:
			case DOUBLE_STEP:
			case HARD_CLAY:
			case CLAY:
			case BRICK:
			case NETHERRACK:
			case NETHER_BRICK:
			case COAL_BLOCK:
				return Material.THIN_GLASS;
				
			case GRAVEL:
			case WOOD:
			case STAINED_CLAY:
			case RED_SANDSTONE:
			case DOUBLE_STONE_SLAB2:
			default:
					return Material.GLASS;
			}
	}
}
