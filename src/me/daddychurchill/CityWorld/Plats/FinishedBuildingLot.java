package me.daddychurchill.CityWorld.Plats;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.CityWorldGenerator.WorldStyle;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Factories.CurvedWallFactory;
import me.daddychurchill.CityWorld.Factories.InteriorWallFactory;
import me.daddychurchill.CityWorld.Factories.MaterialFactory;
import me.daddychurchill.CityWorld.Factories.MaterialFactory.HorizontalStyle;
import me.daddychurchill.CityWorld.Factories.OutsideNSWallFactory;
import me.daddychurchill.CityWorld.Factories.OutsideWEWallFactory;
import me.daddychurchill.CityWorld.Plats.Urban.ConcreteLot;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Plugins.StructureInAirProvider;
import me.daddychurchill.CityWorld.Support.BadMagic;
import me.daddychurchill.CityWorld.Support.BlackMagic;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.BadMagic.Door;
import me.daddychurchill.CityWorld.Support.BadMagic.Facing;
import me.daddychurchill.CityWorld.Support.BadMagic.StairWell;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.SurroundingFloors;
import me.daddychurchill.CityWorld.Support.Surroundings;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public abstract class FinishedBuildingLot extends BuildingLot {

	protected Material wallMaterial;
	protected Material foundationMaterial;
	protected Material ceilingMaterial;
	protected Material glassMaterial;
	protected Material stairMaterial;
	protected Material stairPlatformMaterial;
	protected Material stairWallMaterial;
	protected Material doorMaterial;
	protected Material roofMaterial;

	private MaterialFactory wallsWE;
	private MaterialFactory wallsNS;
	private MaterialFactory wallsCurved;
	private MaterialFactory wallsWEAlt;
	private MaterialFactory wallsNSAlt;
	private MaterialFactory wallsCurvedAlt;
	
	private enum WallStyle {SAME, ALTFLOORS, ALTINDENT, RANDOM, PENTHOUSE};
	private WallStyle wallStyle;
	
	private MaterialFactory wallsInterior;
	
	protected enum RoofStyle {FLATTOP, EDGED, PEAK, PEAKS, DUPLOS, BOXTOPS, BOXED, TENT_NORTHSOUTH, TENT_WESTEAST, 
		INSET_BOX, INSET_BOXES, RAISED_BOX, RAISED_BOXES, OUTSET_BOX, INSET_RIDGEBOX, INSET_RIDGEBOXES};
		//, SLANT_NORTH, SLANT_SOUTH, SLANT_WEST, SLANT_EAST};
	protected enum RoofFeature {PLAIN, ANTENNAS, CONDITIONERS, TILE, SKYLIGHT, SKYPEAK, ALTPEAK, ALTPEAK2, 
		SKYLIGHT_NS, SKYLIGHT_WE, SKYLIGHT_BOX, SKYLIGHT_TINY, SKYLIGHT_CHECKERS, SKYLIGHT_CROSS};
	protected RoofStyle roofStyle;
	protected RoofFeature roofFeature;
	protected int roofScale;
	
	protected enum InteriorStyle {EMPTY, COLUMNS_ONLY, WALLS_ONLY, COLUMNS_OFFICES, WALLS_OFFICES, RANDOM};
	protected InteriorStyle interiorStyle;
	protected double oddsOfAnInteriorDoor = Odds.oddsExtremelyLikely;
//	protected double oddsOfAnExteriorDoor = Odds.oddsHalvedPrettyLikely;
	protected Material columnMaterial;
	protected boolean forceNarrowInteriorMode = false;
	protected double differentInteriorModes = Odds.oddsUnlikely;
	protected Material interiorDoorMaterial;
	protected Material exteriorDoorMaterial;
	
	protected enum CornerWallStyle {EMPTY, FILLED, WOODCOLUMN, STONECOLUMN, FILLEDTHENEMPTY, WOODTHENFILLED, STONETHENFILLED};
	protected CornerWallStyle cornerWallStyle;
	
	protected int navLightX = 0;
	protected int navLightY = 0;
	protected int navLightZ = 0;
	
	//TODO columns height
	protected boolean rounded; // rounded corners if possible? (only if the insets match)
	protected int insetWallWE;
	protected int insetWallNS;
	protected int insetCeilingWE;
	protected int insetCeilingNS;
	protected enum InsetStyle {STRAIGHT, BIGTOSMALL, SMALLTOBIG, UNDULATEIN, UNDULATEOUT}; // combinations like SmallToUndulating
	protected InsetStyle insetStyle;
	protected int insetInsetMidAt;
	protected int insetInsetHighAt;
	protected boolean outsetEffects;
	protected int outsetEffectDivisor;
	
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
		
		loadMaterials(platmap);

		// calculate the defaults
		calculateOptions(context);
		
		wallsWE = new OutsideWEWallFactory(chunkOdds, platmap.generator.settings.includeDecayedBuildings);
		wallsNS = new OutsideNSWallFactory(wallsWE);
		wallsCurved = new CurvedWallFactory(wallsWE);
		
		wallsWEAlt = new OutsideWEWallFactory(chunkOdds, platmap.generator.settings.includeDecayedBuildings);
		wallsNSAlt = new OutsideNSWallFactory(wallsWEAlt);
		wallsCurvedAlt = new CurvedWallFactory(wallsWEAlt);
		wallStyle = pickWallStyle();
		
		wallsInterior = new InteriorWallFactory(chunkOdds, platmap.generator.settings.includeDecayedBuildings);

		// final validation
		validateOptions();
	}
	
	protected void loadMaterials(PlatMap platmap) {

		// what is it made of?
		wallMaterial = platmap.generator.materialProvider.itemsSelectMaterial_BuildingWalls.getRandomMaterial(chunkOdds, Material.COBBLESTONE);
		foundationMaterial = platmap.generator.materialProvider.itemsSelectMaterial_BuildingFoundation.getRandomMaterial(chunkOdds, Material.COBBLESTONE);
		ceilingMaterial = platmap.generator.materialProvider.itemsSelectMaterial_BuildingCeilings.getRandomMaterial(chunkOdds, Material.COBBLESTONE);
		roofMaterial = platmap.generator.materialProvider.itemsSelectMaterial_BuildingRoofs.getRandomMaterial(chunkOdds, Material.COBBLESTONE);
		columnMaterial = platmap.generator.materialProvider.itemsSelectMaterial_BuildingWalls.getRandomMaterial(chunkOdds, pickColumnMaterial(wallMaterial));
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
		int insetSegment = height / 4;
		insetStyle = InsetStyle.STRAIGHT;
		if (insetSegment > 1 && chunkOdds.playOdds(context.oddsOfBuildingWallInset)) {
			insetInsetMidAt = chunkOdds.calcRandomRange(insetSegment, insetSegment * 2);
			insetInsetHighAt = chunkOdds.calcRandomRange(insetInsetMidAt + 1, insetSegment * 3);
			if (chunkOdds.flipCoin())
				insetStyle = InsetStyle.BIGTOSMALL; // 50% of the time
			else if (chunkOdds.flipCoin())
				insetStyle = InsetStyle.SMALLTOBIG; // 25%
			else if (chunkOdds.flipCoin())
				insetStyle = InsetStyle.UNDULATEIN; // 12.5%
			else
				insetStyle = InsetStyle.UNDULATEOUT; // 12.5%
			
		}
		outsetEffects = chunkOdds.playOdds(Odds.oddsSomewhatLikely); 
		outsetEffectDivisor = chunkOdds.getRandomInt(1, 5); 

		cornerWallStyle = pickCornerStyle();

		// floorheight
		firstFloorHeight = aboveFloorHeight;
		otherFloorHeight = aboveFloorHeight;
		
		stairMaterial = pickStairMaterial(wallMaterial);
		doorMaterial = pickDoorMaterial(wallMaterial);
		stairPlatformMaterial = pickStairPlatformMaterial(stairMaterial);
		glassMaterial = pickGlassMaterial();
		
		// what are the walls of the stairs made of?
		if (chunkOdds.playOdds(context.oddsOfStairWallMaterialIsWallMaterial))
			stairWallMaterial = wallMaterial;
		else
			stairWallMaterial = pickStairWallMaterial(wallMaterial);

		rounded = chunkOdds.playOdds(context.oddsOfRoundedBuilding);
		
		roofStyle = pickRoofStyle();
		roofFeature = pickRoofFeature();
		roofScale = 1 + chunkOdds.getRandomInt(2);
		
		interiorStyle = pickInteriorStyle();
		
		forceNarrowInteriorMode = chunkOdds.playOdds(context.oddsOfForcedNarrowInteriorMode);
		differentInteriorModes = context.oddsOfDifferentInteriorModes;
		interiorDoorMaterial = chunkOdds.getRandomWoodenDoorType();
		exteriorDoorMaterial = chunkOdds.getRandomWoodenDoorType();
		
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
			cornerWallStyle = CornerWallStyle.FILLED;
		}
	}

	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);

		// other bits
		if (result && relative instanceof FinishedBuildingLot) {
			FinishedBuildingLot relativebuilding = (FinishedBuildingLot) relative;

			if (chunkOdds.playOdds(neighborsHaveSimilarRoundedOdds))
				rounded = relativebuilding.rounded;
			
			// any other bits
			roofStyle = relativebuilding.roofStyle;
			roofFeature = relativebuilding.roofFeature;
			roofScale = relativebuilding.roofScale;
//			stairStyle = relativebuilding.stairStyle; // commented out because different parts of the building can have different stair styles
			interiorStyle = relativebuilding.interiorStyle;
			columnMaterial = relativebuilding.columnMaterial;
			wallsWE = relativebuilding.wallsWE;
			wallsNS = relativebuilding.wallsNS;
			wallsCurved = relativebuilding.wallsCurved;
			wallsWEAlt = relativebuilding.wallsWEAlt;
			wallsNSAlt = relativebuilding.wallsNSAlt;
			wallsCurvedAlt = relativebuilding.wallsCurvedAlt;
			wallStyle = relativebuilding.wallStyle;
			wallsInterior = relativebuilding.wallsInterior;
			if (!chunkOdds.playOdds(differentInteriorModes))
				forceNarrowInteriorMode = relativebuilding.forceNarrowInteriorMode;
			
			// nudge in a bit
			insetWallWE = relativebuilding.insetWallWE;
			insetWallNS = relativebuilding.insetWallNS;
			insetCeilingWE = relativebuilding.insetCeilingWE;
			insetCeilingNS = relativebuilding.insetCeilingNS;
			
			// nudge in a bit more as we go up
			insetStyle = relativebuilding.insetStyle;
			insetInsetMidAt = relativebuilding.insetInsetMidAt;
			insetInsetHighAt = relativebuilding.insetInsetHighAt;
			outsetEffects = relativebuilding.outsetEffects;
			outsetEffectDivisor = relativebuilding.outsetEffectDivisor;
			
			// what is it made of?
			wallMaterial = relativebuilding.wallMaterial;
			foundationMaterial = relativebuilding.foundationMaterial;
			ceilingMaterial = relativebuilding.ceilingMaterial;
			glassMaterial = relativebuilding.glassMaterial;
			//stairStyle can be different for each chunk
			//stairDirection can be different for each chunk
			stairMaterial = relativebuilding.stairMaterial;
			stairWallMaterial = relativebuilding.stairWallMaterial;
			stairPlatformMaterial = relativebuilding.stairPlatformMaterial;
			doorMaterial = relativebuilding.doorMaterial;
			roofMaterial = relativebuilding.roofMaterial;
			exteriorDoorMaterial = relativebuilding.exteriorDoorMaterial;
			interiorDoorMaterial = relativebuilding.interiorDoorMaterial;
			cornerWallStyle = relativebuilding.cornerWallStyle;
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
	
	private static int minimumInset = 0;
	private static int maximumInset = 2;
	
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
		
		// insetting the inset
		int localInsetWallWE = insetWallWE;
		int localInsetWallNS = insetWallNS;
		int localInsetCeilingWE = insetCeilingWE;
		int localInsetCeilingNS = insetCeilingNS;
		int currentInset = minimumInset;
		int deltaInset = 1;
		
		// make it small initially?
		switch (insetStyle) {
		case SMALLTOBIG:
		case UNDULATEOUT:
			if (insetInsetMidAt > 1)
				currentInset++;
			if (insetInsetHighAt > 1)
				currentInset++;
			
			localInsetWallWE += currentInset;
			localInsetWallNS += currentInset;
			localInsetCeilingWE += currentInset;
			localInsetCeilingNS += currentInset;
			deltaInset = -1;
			break;
		default:
			break;
		}
		
		// validate the materials
		if (localInsetWallNS > 0 || localInsetWallWE > 0)
			if (wallMaterial.hasGravity())
				wallMaterial = Material.STONE;
		if (ceilingMaterial.hasGravity())
			ceilingMaterial = Material.STONE;

		// starting with the bottom
		int lowestY = getBottomY(generator);
		
		// bottom most floor
		chunk.setLayer(lowestY - 1, Material.STONE);
		drawFoundation(generator, chunk, context, lowestY, 1, false, false, foundationMaterial, neighborBasements);
		//chunk.setBlocks(0, chunk.width, lowestY, lowestY + 1, 0, chunk.width, (byte) ceilingMaterial.getId());
		
		// below ground
		if (depth > 0) {
			for (int floor = 0; floor < depth; floor++) {
				int floorAt = generator.streetLevel - basementFloorHeight * floor - 2;
	
				// clear it out
				chunk.airoutLayer(generator, floorAt, basementFloorHeight);
	
				// one floor please
				drawWallParts(generator, chunk, context, floorAt, basementFloorHeight - 1, 0, 0, floor, 
						false, false, false, wallMaterial, neighborBasements);
				drawCeilings(generator, chunk, context, floorAt + basementFloorHeight - 1, 1, 0, 0,
						false, false, false, ceilingMaterial, neighborBasements);
				
				// one down, more to go
				neighborBasements.decrement();
			}
		} else {
			drawFoundation(generator, chunk, context, lowestY + 1, 1, false, false, foundationMaterial, neighborBasements);
//			chunk.setLayer(lowestY + 1, ceilingMaterial);
		}

		// above ground
		aboveFloorHeight = firstFloorHeight;
		for (int floor = 0; floor < height; floor++) {
			int floorAt = generator.streetLevel + aboveFloorHeight * floor + 2;
			boolean onTopFloor = floor == height - 1;
			boolean inMiddleSection = floor >= insetInsetMidAt && floor < insetInsetHighAt;
			allowRounded = allowRounded && neighborFloors.isRoundable();

			// inset?
			switch (insetStyle) {
			case STRAIGHT:
				break;
			case BIGTOSMALL:
				if (floor == insetInsetMidAt || floor == insetInsetHighAt) {
					localInsetWallWE++;
					localInsetWallNS++;
					localInsetCeilingWE++;
					localInsetCeilingNS++;
				}
				break;
			case SMALLTOBIG:
				if (floor == insetInsetMidAt || floor == insetInsetHighAt) {
					localInsetWallWE--;
					localInsetWallNS--;
					localInsetCeilingWE--;
					localInsetCeilingNS--;
				}
				break;
			case UNDULATEIN:
			case UNDULATEOUT:
				if (floor > 0) {
					if (currentInset <= minimumInset)
						deltaInset = 1;
					else if (currentInset >= maximumInset)
						deltaInset = -1;
					currentInset = currentInset + deltaInset;
					localInsetWallWE = insetWallWE + currentInset;
					localInsetWallNS = insetWallNS + currentInset;
					localInsetCeilingWE = insetCeilingWE + currentInset;
					localInsetCeilingNS = insetCeilingNS + currentInset;
				}
				break;
			}
			boolean needOutsetEffect = localInsetWallWE != localInsetCeilingWE || localInsetWallNS != localInsetCeilingNS;
			
			// columns?
			boolean localOutsetEffect = outsetEffects && (localInsetCeilingNS > 0 || localInsetCeilingWE > 0);
			if (outsetEffectDivisor > 1 && floor % outsetEffectDivisor == 0)
				localOutsetEffect = false;
			
			// one floor please
			drawExteriorParts(generator, chunk, context, floorAt, aboveFloorHeight - 1, localInsetWallNS, localInsetWallWE, floor, onTopFloor, inMiddleSection,
					cornerWallStyle, allowRounded, localOutsetEffect, wallMaterial, glassMaterial, neighborFloors);
			
			// final floor is done... how about a roof then?
			if (onTopFloor) {
				drawCeilings(generator, chunk, context, floorAt + aboveFloorHeight - 1, 
						1, localInsetCeilingNS, localInsetCeilingWE, allowRounded, needOutsetEffect, true, ceilingMaterial, neighborFloors);
				drawRoof(generator, chunk, context, generator.streetLevel + aboveFloorHeight * (floor + 1) + 2, localInsetWallNS, localInsetWallWE, floor, 
						allowRounded, roofMaterial, neighborFloors);
			} else {
				drawCeilings(generator, chunk, context, floorAt + aboveFloorHeight - 1, 
						1, localInsetCeilingNS, localInsetCeilingWE, allowRounded, needOutsetEffect, false, ceilingMaterial, neighborFloors);
			}

			// one down, more to go
			neighborFloors.decrement();
			aboveFloorHeight = otherFloorHeight;
		}
	}
	
	protected void drawExteriorParts(CityWorldGenerator generator, InitialBlocks byteChunk, DataContext context, 
			int y1, int height, int insetNS, int insetWE, int floor, boolean onTopFloor, boolean inMiddleSection,
			CornerWallStyle cornerStyle, boolean allowRounded, boolean outsetEffect, Material wallMaterial, Material glassMaterial, Surroundings heights) {
		
		// precalculate
		int y2 = y1 + height;
		boolean stillNeedWalls = true;
		int inset = Math.max(insetNS, insetWE);
		
		// figure out how the walls are supposed to be done
		MaterialFactory floorWallsWE = wallsWE;
		MaterialFactory floorWallsNS = wallsNS;
		MaterialFactory floorWallsCurved = wallsCurved;
		switch (wallStyle) {
		default:
		case SAME:
			break; // all is good
		case RANDOM:
			if (chunkOdds.flipCoin()) {
				floorWallsWE = wallsWEAlt;
				floorWallsNS = wallsNSAlt;
				floorWallsCurved = wallsCurvedAlt;
			}
			break;
		case PENTHOUSE:
			if (onTopFloor) {
				floorWallsWE = wallsWEAlt;
				floorWallsNS = wallsNSAlt;
				floorWallsCurved = wallsCurvedAlt;
			}
			break;
		case ALTFLOORS:
			if (floor % 2 != 0) {
				floorWallsWE = wallsWEAlt;
				floorWallsNS = wallsNSAlt;
				floorWallsCurved = wallsCurvedAlt;
			}
			break;
		case ALTINDENT:
			if (inMiddleSection) {
				floorWallsWE = wallsWEAlt;
				floorWallsNS = wallsNSAlt;
				floorWallsCurved = wallsCurvedAlt;
			}
			break;
		}
		
		// the first floor is different, it can't support random horizontal styles
		if (floor == 0 && 
			(floorWallsWE.horizontalStyle == HorizontalStyle.RANDOM || floorWallsNS.horizontalStyle == HorizontalStyle.RANDOM))
			outsetEffect = false;
			
		
		// rounded and square inset and there are exactly two neighbors?
		if (allowRounded) {// && rounded) { 
			
			// hack the glass material if needed
			if (glassMaterial == Material.THIN_GLASS)
				glassMaterial = Material.GLASS;
			
			// do the sides
			if (heights.toSouth()) {
				if (heights.toWest()) {
					drawCornerLotSouthWest(byteChunk, cornerLotStyle, inset, y1, y2, wallMaterial, glassMaterial, floorWallsCurved, !heights.toSouthWest(), false, false, false);
					stillNeedWalls = false;
				} else if (heights.toEast()) {
					drawCornerLotSouthEast(byteChunk, cornerLotStyle, inset, y1, y2, wallMaterial, glassMaterial, floorWallsCurved, !heights.toSouthEast(), false, false, false);
					stillNeedWalls = false;
				}
			} else if (heights.toNorth()) {
				if (heights.toWest()) {
					drawCornerLotNorthWest(byteChunk, cornerLotStyle, inset, y1, y2, wallMaterial, glassMaterial, floorWallsCurved, !heights.toNorthWest(), false, false, false);
					stillNeedWalls = false;
				} else if (heights.toEast()) {
					drawCornerLotNorthEast(byteChunk, cornerLotStyle, inset, y1, y2, wallMaterial, glassMaterial, floorWallsCurved, !heights.toNorthEast(), false, false, false);
					stillNeedWalls = false;
				}
			}
		}
		
		// outset stuff
		Material outsetMaterial = wallMaterial;
		Material outsetBackfill = Material.AIR;
		if (outsetMaterial.hasGravity())
			outsetMaterial = Material.STONE;
		
		// still need to do something?
		if (stillNeedWalls) {
			
			// corner columns
			if (!heights.toNorthWest()) {
				if (heights.toNorth() || heights.toWest()) {
					drawCornerBit(byteChunk, CornerWallStyle.FILLED, insetWE, y1, y2, insetNS, floor, wallMaterial);
					if (outsetEffect) {
						drawCornerBit(byteChunk, CornerWallStyle.FILLED, insetWE, y1, y2 + 1, insetNS - 1, floor, outsetMaterial);
						drawCornerBit(byteChunk, CornerWallStyle.FILLED, insetWE - 1, y1, y2 + 1, insetNS, floor, outsetMaterial);
					}
				} else
					drawCornerBit(byteChunk, cornerStyle, insetWE, y1, y2, insetNS, floor, wallMaterial);
			}
			if (!heights.toSouthWest()) {
				if (heights.toSouth() || heights.toWest()) {
					drawCornerBit(byteChunk, CornerWallStyle.FILLED, insetWE, y1, y2, byteChunk.width - insetNS - 1, floor, wallMaterial);
					if (outsetEffect) {
						drawCornerBit(byteChunk, CornerWallStyle.FILLED, insetWE, y1, y2 + 1, byteChunk.width - insetNS, floor, outsetMaterial);
						drawCornerBit(byteChunk, CornerWallStyle.FILLED, insetWE - 1, y1, y2 + 1, byteChunk.width - insetNS - 1, floor, outsetMaterial);
					}
				} else
					drawCornerBit(byteChunk, cornerStyle, insetWE, y1, y2, byteChunk.width - insetNS - 1, floor, wallMaterial);
			}
			if (!heights.toNorthEast()) {
				if (heights.toNorth() || heights.toEast()) {
					drawCornerBit(byteChunk, CornerWallStyle.FILLED, byteChunk.width - insetWE - 1, y1, y2, insetNS, floor, wallMaterial);
					if (outsetEffect) {
						drawCornerBit(byteChunk, CornerWallStyle.FILLED, byteChunk.width - insetWE - 1, y1, y2 + 1, insetNS - 1, floor, outsetMaterial);
						drawCornerBit(byteChunk, CornerWallStyle.FILLED, byteChunk.width - insetWE, y1, y2 + 1, insetNS, floor, outsetMaterial);
					}
				} else
					drawCornerBit(byteChunk, cornerStyle, byteChunk.width - insetWE - 1, y1, y2, insetNS, floor, wallMaterial);
			}
			if (!heights.toSouthEast()) {
				if (heights.toSouth() || heights.toEast()) {
					drawCornerBit(byteChunk, CornerWallStyle.FILLED, byteChunk.width - insetWE - 1, y1, y2, byteChunk.width - insetNS - 1, floor, wallMaterial);
					if (outsetEffect) {
						drawCornerBit(byteChunk, CornerWallStyle.FILLED, byteChunk.width - insetWE - 1, y1, y2 + 1, byteChunk.width - insetNS, floor, outsetMaterial);
						drawCornerBit(byteChunk, CornerWallStyle.FILLED, byteChunk.width - insetWE, y1, y2 + 1, byteChunk.width - insetNS - 1, floor, outsetMaterial);
					}
				} else
					drawCornerBit(byteChunk, cornerStyle, byteChunk.width - insetWE - 1, y1, y2, byteChunk.width - insetNS - 1, floor, wallMaterial);
			}
			
			// cardinal walls
			if (!heights.toWest()) {
				byteChunk.setBlocks(insetWE,  insetWE + 1, y1, y2, insetNS + 1, byteChunk.width - insetNS - 1, wallMaterial, glassMaterial, floorWallsNS);
				if (outsetEffect)
					byteChunk.setBlocks(insetWE - 1,  insetWE, y1, y2 + 1, insetNS + 1, byteChunk.width - insetNS - 1, outsetMaterial, outsetBackfill, floorWallsNS);
			}
			if (!heights.toEast()) {
				byteChunk.setBlocks(byteChunk.width - insetWE - 1,  byteChunk.width - insetWE, y1, y2, insetNS + 1, byteChunk.width - insetNS - 1, wallMaterial, glassMaterial, floorWallsNS);
				if (outsetEffect)
					byteChunk.setBlocks(byteChunk.width - insetWE,  byteChunk.width - insetWE + 1, y1, y2 + 1, insetNS + 1, byteChunk.width - insetNS - 1, outsetMaterial, outsetBackfill, floorWallsNS);
			}
			if (!heights.toNorth()) {
				byteChunk.setBlocks(insetWE + 1, byteChunk.width - insetWE - 1, y1, y2, insetNS, insetNS + 1, wallMaterial, glassMaterial, floorWallsWE);
				if (outsetEffect)
					byteChunk.setBlocks(insetWE + 1, byteChunk.width - insetWE - 1, y1, y2 + 1, insetNS - 1, insetNS, outsetMaterial, outsetBackfill, floorWallsWE);
			}
			if (!heights.toSouth()) {
				byteChunk.setBlocks(insetWE + 1, byteChunk.width - insetWE - 1, y1, y2, byteChunk.width - insetNS - 1, byteChunk.width - insetNS, wallMaterial, glassMaterial, floorWallsWE);
				if (outsetEffect)
					byteChunk.setBlocks(insetWE + 1, byteChunk.width - insetWE - 1, y1, y2 + 1, byteChunk.width - insetNS, byteChunk.width - insetNS + 1, outsetMaterial, outsetBackfill, floorWallsWE);
			}
			
		}
			
		// only if there are insets
		if (insetWE > 0) {
			if (heights.toWest()) {
				if (!heights.toNorthWest()) {
					byteChunk.setBlocks(0, insetWE, y1, y2, insetNS, insetNS + 1, wallMaterial, glassMaterial, floorWallsWE);
					if (outsetEffect)
						byteChunk.setBlocks(0, insetWE, y1, y2 + 1, insetNS - 1, insetNS, outsetMaterial, outsetBackfill, floorWallsWE);
				}
				if (!heights.toSouthWest()) {
					byteChunk.setBlocks(0, insetWE, y1, y2, byteChunk.width - insetNS - 1, byteChunk.width - insetNS, wallMaterial, glassMaterial, floorWallsWE);
					if (outsetEffect)
						byteChunk.setBlocks(0, insetWE, y1, y2 + 1, byteChunk.width - insetNS, byteChunk.width - insetNS + 1, outsetMaterial, outsetBackfill, floorWallsWE);
				}
			}
			if (heights.toEast()) {
				if (!heights.toNorthEast()) {
					byteChunk.setBlocks(byteChunk.width - insetWE, byteChunk.width, y1, y2, insetNS, insetNS + 1, wallMaterial, glassMaterial, floorWallsWE);
					if (outsetEffect)
						byteChunk.setBlocks(byteChunk.width - insetWE, byteChunk.width, y1, y2 + 1, insetNS - 1, insetNS, outsetMaterial, outsetBackfill, floorWallsWE);
				}
				if (!heights.toSouthEast()) {
					byteChunk.setBlocks(byteChunk.width - insetWE, byteChunk.width, y1, y2, byteChunk.width - insetNS - 1, byteChunk.width - insetNS, wallMaterial, glassMaterial, floorWallsWE);
					if (outsetEffect)
						byteChunk.setBlocks(byteChunk.width - insetWE, byteChunk.width, y1, y2 + 1, byteChunk.width - insetNS, byteChunk.width - insetNS + 1, outsetMaterial, outsetBackfill, floorWallsWE);
				}
			}
		}
		if (insetNS > 0) {
			if (heights.toNorth()) {
				if (!heights.toNorthWest()) {
					byteChunk.setBlocks(insetWE, insetWE + 1, y1, y2, 0, insetNS, wallMaterial, glassMaterial, floorWallsNS);
					if (outsetEffect)
						byteChunk.setBlocks(insetWE - 1, insetWE, y1, y2 + 1, 0, insetNS, outsetMaterial, outsetBackfill, floorWallsNS);
				}
				if (!heights.toNorthEast()) {
					byteChunk.setBlocks(byteChunk.width - insetWE - 1, byteChunk.width - insetWE, y1, y2, 0, insetNS, wallMaterial, glassMaterial, floorWallsNS);
					if (outsetEffect)
						byteChunk.setBlocks(byteChunk.width - insetWE, byteChunk.width - insetWE + 1, y1, y2 + 1, 0, insetNS, outsetMaterial, outsetBackfill, floorWallsNS);
				}
			}
			if (heights.toSouth()) {
				if (!heights.toSouthWest()) {
					byteChunk.setBlocks(insetWE, insetWE + 1, y1, y2, byteChunk.width - insetNS, byteChunk.width, wallMaterial, glassMaterial, floorWallsNS);
					if (outsetEffect)
						byteChunk.setBlocks(insetWE - 1, insetWE, y1, y2 + 1, byteChunk.width - insetNS, byteChunk.width, outsetMaterial, outsetBackfill, floorWallsNS);
				}
				if (!heights.toSouthEast()) {
					byteChunk.setBlocks(byteChunk.width - insetWE - 1, byteChunk.width - insetWE, y1, y2, byteChunk.width - insetNS, byteChunk.width, wallMaterial, glassMaterial, floorWallsNS);
					if (outsetEffect)
						byteChunk.setBlocks(byteChunk.width - insetWE, byteChunk.width - insetWE + 1, y1, y2 + 1, byteChunk.width - insetNS, byteChunk.width, outsetMaterial, outsetBackfill, floorWallsNS);
				}
			}
		}
	}
	
	private void drawCornerBit(InitialBlocks blocks, CornerWallStyle style, int x, int y1, int y2, int z, int floor, Material wallMaterial) {
		boolean firstFloor = floor == 0;
		switch (style) {
		case FILLEDTHENEMPTY:
			if (firstFloor)
				drawCornerBit(blocks, CornerWallStyle.FILLED, x, y1, y2, z, floor, wallMaterial);
			else
				drawCornerBit(blocks, CornerWallStyle.EMPTY, x, y1, y2, z, floor, wallMaterial);
			break;
		case STONETHENFILLED:
			if (firstFloor)
				drawCornerBit(blocks, CornerWallStyle.STONECOLUMN, x, y1, y2, z, floor, wallMaterial);
			else
				drawCornerBit(blocks, CornerWallStyle.FILLED, x, y1, y2, z, floor, wallMaterial);
			break;
		case WOODTHENFILLED:
			if (firstFloor)
				drawCornerBit(blocks, CornerWallStyle.WOODCOLUMN, x, y1, y2, z, floor, wallMaterial);
			else
				drawCornerBit(blocks, CornerWallStyle.FILLED, x, y1, y2, z, floor, wallMaterial);
			break;
		case EMPTY:
			break;
		case WOODCOLUMN:
			blocks.setBlocks(x, y1, y2, z, Material.FENCE);
			break;
		case STONECOLUMN:
			blocks.setBlocks(x, y1, y2, z, Material.COBBLE_WALL);
			break;
		case FILLED:
		default:
			blocks.setBlocks(x, y1, y2, z, wallMaterial);
			break;
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
		
		// insetting the inset
		int localInsetWallWE = insetWallWE;
		int localInsetWallNS = insetWallNS;
		int currentInset = minimumInset;
		int deltaInset = 1;
		
		// make it small initially?
		switch (insetStyle) {
		case SMALLTOBIG:
		case UNDULATEOUT:
			if (insetInsetMidAt > 1)
				currentInset++;
			if (insetInsetHighAt > 1)
				currentInset++;
			
			localInsetWallWE += currentInset;
			localInsetWallNS += currentInset;
			deltaInset = -1;
			break;
		default:
			break;
		}
		
		// validate the materials
		if (localInsetWallNS > 0 || localInsetWallWE > 0)
			if (wallMaterial.hasGravity())
				wallMaterial = Material.STONE;
		if (ceilingMaterial.hasGravity())
			ceilingMaterial = Material.STONE;

		// where are the stairs?
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
		
		// now the above ground floors
		aboveFloorHeight = firstFloorHeight;
		for (int floor = 0; floor < height; floor++) {
			int floorAt = generator.streetLevel + aboveFloorHeight * floor + 2;
//			allowRounded = allowRounded && neighborFloors.isRoundable();
//			stairLocation = getStairWellLocation(allowRounded, neighborFloors);
//			if (!needStairsUp || floor == height - 1)
//				stairLocation = StairWell.NONE;
			
			// inset?
			switch (insetStyle) {
			case STRAIGHT:
				break;
			case BIGTOSMALL:
				if (floor == insetInsetMidAt || floor == insetInsetHighAt) {
					localInsetWallWE++;
					localInsetWallNS++;
				}
				break;
			case SMALLTOBIG:
				if (floor == insetInsetMidAt || floor == insetInsetHighAt) {
					localInsetWallWE--;
					localInsetWallNS--;
				}
				break;
			case UNDULATEIN:
			case UNDULATEOUT:
				if (floor > 0) {
					if (currentInset <= minimumInset)
						deltaInset = 1;
					else if (currentInset >= maximumInset)
						deltaInset = -1;
					currentInset = currentInset + deltaInset;
					localInsetWallWE = insetWallWE + currentInset;
					localInsetWallNS = insetWallNS + currentInset;
				}
				break;
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
			
			// add more stuff on top?
			drawRoof(generator, chunk, context, generator.streetLevel + aboveFloorHeight * height + 2, localInsetWallNS, localInsetWallWE, height, 
					false, allowRounded, roofMaterial, neighborFloors, roofStyle, roofFeature);
			
		// nope, let's destroy our work!
		} else {
			int y1 = generator.streetLevel + 2;
			int y2 = y1 + firstFloorHeight + aboveFloorHeight * (height - 1);
			switch (roofStyle) {
			case EDGED:
			case FLATTOP:
				if (roofFeature == RoofFeature.ANTENNAS)
					y2 -= aboveFloorHeight;
				break;
			case PEAK:
			case PEAKS:
			case DUPLOS:
			case BOXTOPS:
			case TENT_WESTEAST:
			case TENT_NORTHSOUTH:
			case BOXED:
			case INSET_BOX:
			case INSET_BOXES:
			case RAISED_BOX:
			case RAISED_BOXES:
			case INSET_RIDGEBOX:
			case INSET_RIDGEBOXES:
			case OUTSET_BOX:
				y2 += aboveFloorHeight;
				break;
			}
			destroyLot(generator, y1, y2);
		}
	}

	protected void drawInteriorParts(CityWorldGenerator generator, RealBlocks chunk, 
			DataContext context, RoomProvider rooms,
			int floor, int floorAt, int floorHeight, 
			int insetNS, int insetWE, boolean allowRounded,
			Material materialWall, Material materialGlass, 
			StairWell stairLocation, 
			Material materialStair, Material materialStairWall, Material materialPlatform,
			boolean drawStairWall, boolean drawStairs, 
			boolean topFloor, boolean singleFloor,
			Surroundings heights) {
		
		drawInteriorParts(generator, chunk, context, getFloorsInteriorStyle(floor), 
				rooms, floor, floorAt, floorHeight, insetNS, insetWE, 
				allowRounded, materialWall, materialGlass, stairLocation, 
				materialStair, materialStairWall, materialPlatform,
				drawStairWall, drawStairs, topFloor, singleFloor,
				heights);
	}
	
	public enum DoorStyle {NONE, HOLE, WOOD};
	private void drawInteriorParts(CityWorldGenerator generator, RealBlocks chunk, 
			DataContext context, InteriorStyle style, RoomProvider rooms, 
			int floor, int floorAt, int floorHeight, 
			int insetNS, int insetWE, boolean allowRounded,
			Material materialWall, Material materialGlass, 
			StairWell stairLocation, 
			Material materialStair, Material materialStairWall, Material materialPlatform,
			boolean drawStairWall, boolean drawStairs, 
			boolean topFloor, boolean singleFloor,
			Surroundings heights) {
		
		// calculate initial door state
		DoorStyle drawInteriorDoors = DoorStyle.NONE;
		
		// need to do more stuff?
		boolean drawNarrowInteriors = forceNarrowInteriorMode || 
									  (!heights.toNorthWest() && 
									   !heights.toNorthEast() && 
									   !heights.toSouthWest() && 
									   !heights.toSouthEast());
		if (drawNarrowInteriors && (insetNS > 1 || insetWE > 1))
			drawNarrowInteriors = false;
		
		// let's do it!
		switch (style) {
		case EMPTY:
			break;
		case COLUMNS_ONLY:
			drawInteriorColumns(generator, chunk, context, drawNarrowInteriors,
					floor, floorAt, floorHeight, insetNS, insetWE, 
					allowRounded, materialWall, materialGlass, 
					stairLocation, heights);
			break;
		case COLUMNS_OFFICES:
			drawInteriorColumns(generator, chunk, context, drawNarrowInteriors,
					floor, floorAt, floorHeight, insetNS, insetWE, 
					allowRounded, materialWall, materialGlass, 
					stairLocation, heights);
			drawInteriorRooms(generator, chunk, context, drawNarrowInteriors,
					rooms, floor, floorAt, floorHeight, insetNS, insetWE, 
					allowRounded, materialWall, materialGlass, 
					stairLocation, heights);
			break;
		case WALLS_ONLY:
			drawInteriorWalls(generator, chunk, context, drawNarrowInteriors,
					floor, floorAt, floorHeight, insetNS, insetWE, 
					allowRounded, materialWall, materialGlass, 
					stairLocation, heights);
			drawInteriorDoors = DoorStyle.HOLE;
			break;
		case WALLS_OFFICES:
			drawInteriorWalls(generator, chunk, context, drawNarrowInteriors,
					floor, floorAt, floorHeight, insetNS, insetWE, 
					allowRounded, materialWall, materialGlass, 
					stairLocation, heights);
			drawInteriorRooms(generator, chunk, context, drawNarrowInteriors,
					rooms, floor, floorAt, floorHeight, insetNS, insetWE, 
					allowRounded, materialWall, materialGlass, 
					stairLocation, heights);
			drawInteriorDoors = DoorStyle.WOOD;
			break;
		case RANDOM:
			if (chunkOdds.flipCoin())
				drawInteriorParts(generator, chunk, context, 
						InteriorStyle.COLUMNS_OFFICES, rooms, floor, floorAt, floorHeight, 
						insetNS, insetWE, allowRounded, 
						materialWall, materialGlass, stairLocation, 
						materialStair, materialStairWall, materialPlatform,
						drawStairWall, drawStairs, topFloor, singleFloor,
						heights);
			else
				drawInteriorParts(generator, chunk, context, 
						InteriorStyle.WALLS_OFFICES, rooms, floor, floorAt, floorHeight, 
						insetNS, insetWE, allowRounded, 
						materialWall, materialGlass, stairLocation, 
						materialStair, materialStairWall, materialPlatform,
						drawStairWall, drawStairs, topFloor, singleFloor,
						heights);
			
			// all done, don't do anymore
			return;
		}
		
		// fancy walls... maybe
		if (drawStairWall) {
			drawStairsWalls(generator, chunk, floorAt, aboveFloorHeight, 
					stairLocation, materialStairWall, 
					floor == height - 1, floor == 0 && depth == 0);
		}
		
		// put up more doors?
		if (drawInteriorDoors != DoorStyle.NONE) {
			drawInteriorDoors(generator, chunk, context, drawInteriorDoors, drawNarrowInteriors,
					floor, floorAt, floorHeight, insetNS, insetWE, 
					allowRounded, materialWall, materialGlass, stairLocation, heights);
		}
		
		// more stairs and such
		if (drawStairs)
			drawStairs(generator, chunk, floorAt, aboveFloorHeight, 
					stairLocation, materialStair, materialPlatform);
		
		drawExteriorDoors(generator, chunk, context, 
					floor, floorAt, floorHeight, insetNS, insetWE, 
					allowRounded, materialWall, materialGlass, 
					stairLocation, heights);
	}	
	
	// outside 
	protected void drawExteriorDoors(CityWorldGenerator generator, RealBlocks chunk, 
			DataContext context, int floor, int floorAt, int floorHeight, 
			int insetNS, int insetWE, boolean allowRounded,
			Material materialWall, Material materialGlass, 
			StairWell stairLocation, Surroundings heights) {
		
		DoorStyle drawExteriorDoors = floor == 0 ? DoorStyle.WOOD : DoorStyle.NONE;
		if (drawExteriorDoors == DoorStyle.WOOD && generator.settings.includeDecayedBuildings)
			drawExteriorDoors = chunkOdds.flipCoin() ? DoorStyle.HOLE : DoorStyle.WOOD;
		
		if (drawExteriorDoors != DoorStyle.NONE)
			drawExteriorDoors(generator, chunk, context, drawExteriorDoors,
					floor, floorAt, floorHeight, insetNS, insetWE, 
					allowRounded, materialWall, materialGlass, 
					stairLocation, heights);
	}
		
	private void drawInteriorColumns(CityWorldGenerator generator, RealBlocks chunk, DataContext context, 
			boolean drawNarrowInteriors, int floor, int y1, int floorHeight, 
			int insetNS, int insetWE, boolean allowRounded, 
			Material materialWall, Material materialGlass, 
			StairWell stairLocation, Surroundings heights) {

		// precalculate
		int y2 = y1 + floorHeight;
		
		// first try the narrow logic (single column in the middle)
		if (drawNarrowInteriors) {
			chunk.setBlocks(7, 9, y1, y2, 7, 9, columnMaterial);
			
		// if the narrow logic doesn't handle it, try to use the wide logic (four columns in the middle)
		} else {
			
			if (heights.toNorthWest())
				chunk.setBlocks(4, y1, y2, 4, columnMaterial);
			if (heights.toNorthEast())
				chunk.setBlocks(11, y1, y2, 4, columnMaterial);
			if (heights.toSouthWest())
				chunk.setBlocks(4, y1, y2, 11, columnMaterial);
			if (heights.toSouthEast())
				chunk.setBlocks(11, y1, y2, 11, columnMaterial);
		}
	}
	
	private void drawInteriorWalls(CityWorldGenerator generator, RealBlocks chunk, DataContext context, 
			boolean drawNarrowInteriors, int floor, int y1, int floorHeight, 
			int insetNS, int insetWE, boolean allowRounded,
			Material wallMaterial, Material glassMaterial, 
			StairWell stairsLocation, Surroundings heights) {
		
		//TODO Atrium in the middle of 2x2
		
		// precalculate
		int y2 = y1 + floorHeight;
		int x1 = heights.toWest() ? 0 : insetWE + 1;
		int x2 = chunk.width - (heights.toEast() ? 0 : (insetWE + 1));
		int z1 = heights.toNorth() ? 0 : insetNS + 1;
		int z2 = chunk.width - (heights.toSouth() ? 0 : (insetNS + 1));

		// first try the narrow logic (single wall in the middle)
		if (drawNarrowInteriors) {
			
			// Northward
			if (heights.toNorth()) {
//				materialWall = Material.COBBLESTONE;
//				wallId = (byte) materialWall.getId();
				
				// draw out
				if (stairsLocation == StairWell.NONE) {
					drawInteriorNSWall(chunk, 7, y1, y2, 4, 7, wallMaterial, glassMaterial);
					chunk.setBlocks(7, y1, y2, 7, wallMaterial);
				}
				
				// draw cap
				drawInteriorNSWall(chunk, 7, y1, y2, 1, 4, wallMaterial, glassMaterial);
				drawInteriorWEWall(chunk, x1, 8, y1, y2, 0, wallMaterial, glassMaterial);

			} else if (stairsLocation == StairWell.NONE && !allowRounded) {
//				materialWall = Material.BEDROCK;
//				wallMaterial = (byte) materialWall.getMaterial();
				
				// draw short wall
				drawInteriorNSWall(chunk, 7, y1, y2, z1, 8, wallMaterial, glassMaterial);
			}
				
			// Eastward
			if (heights.toEast()) {
//				materialWall = Material.CLAY;
//				wallMaterial = (byte) materialWall.getMaterial();
				
				// draw out
				if (stairsLocation == StairWell.NONE) {
					drawInteriorWEWall(chunk, 9, 12, y1, y2, 7, wallMaterial, glassMaterial);
					chunk.setBlocks(8, y1, y2, 7, wallMaterial);
				}
				
				// draw cap
				drawInteriorWEWall(chunk, 12, 15, y1, y2, 7, wallMaterial, glassMaterial);
				drawInteriorNSWall(chunk, 15, y1, y2, z1, 8, wallMaterial, glassMaterial);
				
			} else if (stairsLocation == StairWell.NONE && !allowRounded) {
//				materialWall = Material.SAND;
//				wallMaterial = (byte) materialWall.getMaterial();
				
				// draw short wall
				drawInteriorWEWall(chunk, 8, x2, y1, y2, 7, wallMaterial, glassMaterial);
			}
			
			// Westward
			if (heights.toWest()) {
//				materialWall = Material.IRON_BLOCK;
//				wallMaterial = (byte) materialWall.getMaterial();
				
				// draw out
				if (stairsLocation == StairWell.NONE) {
					drawInteriorWEWall(chunk, 4, 7, y1, y2, 8, wallMaterial, glassMaterial);
					chunk.setBlocks(7, y1, y2, 8, wallMaterial);
				}
				
				// draw cap
				drawInteriorWEWall(chunk, 1, 4, y1, y2, 8, wallMaterial, glassMaterial);
				drawInteriorNSWall(chunk, 0, y1, y2, 8, z2, wallMaterial, glassMaterial);
				
			} else if (stairsLocation == StairWell.NONE && !allowRounded) {
//				materialWall = Material.GOLD_BLOCK;
//				wallMaterial = (byte) materialWall.getMaterial();
				
				// draw short wall
				drawInteriorWEWall(chunk, x1, 8, y1, y2, 8, wallMaterial, glassMaterial);
			}
			
			// Southward
			if (heights.toSouth()) {
//				materialWall = Material.DIAMOND_BLOCK;
//				wallMaterial = (byte) materialWall.getMaterial();
				
				// draw out
				if (stairsLocation == StairWell.NONE) {
					drawInteriorNSWall(chunk, 8, 13, y1, y2, 15, wallMaterial, glassMaterial);
					chunk.setBlocks(8, y1, y2, 8, wallMaterial);
				}
				
				// draw cap
				drawInteriorNSWall(chunk, 8, y1, y2, 12, 15, wallMaterial, glassMaterial);
				drawInteriorWEWall(chunk, 8, x2, y1, y2, 15, wallMaterial, glassMaterial);

			} else if (stairsLocation == StairWell.NONE && !allowRounded) {
//				materialWall = Material.LAPIS_BLOCK;
//				wallMaterial = (byte) materialWall.getMaterial();
				
				// draw short wall
				drawInteriorNSWall(chunk, 8, y1, y2, 8, z2, wallMaterial, glassMaterial);
			}
			
		// if the narrow logic doesn't handle it, try to use the wide logic (two walls in the middle)
		} else {
		
			// NW corner
			if (heights.toNorthWest()) {
//				wallMaterial = (byte) Material.QUARTZ_ORE.getMaterial();
				if (heights.toNorth()) {
					drawInteriorNSWall(chunk, 4, y1, y2, 0, wallMaterial, glassMaterial);
				}
				if (heights.toWest()) {
					drawInteriorWEWall(chunk, 0, y1, y2, 4, wallMaterial, glassMaterial);
				}
				
			} else {
//				wallMaterial = (byte) Material.BEDROCK.getMaterial();
				if (!heights.toNorth() && heights.toSouth() && heights.toWest()) {
					drawInteriorNSWall(chunk, 4, y1, y2, z1, 8, wallMaterial, glassMaterial);
				} else if (!heights.toWest() && heights.toEast() && heights.toNorth()) {
					drawInteriorWEWall(chunk, x1, 8, y1, y2, 4, wallMaterial, glassMaterial);
				}
			}
			
			// NE corner
			if (heights.toNorthEast()) {
//				wallMaterial = (byte) Material.CLAY.getMaterial();
				if (heights.toEast()) {
					drawInteriorWEWall(chunk, 8, y1, y2, 4, wallMaterial, glassMaterial);
				}
				if (heights.toNorth()) {
					drawInteriorNSWall(chunk, 11, y1, y2, 0, wallMaterial, glassMaterial);
				}
				
			} else {
//				wallMaterial = (byte) Material.SAND.getMaterial();
				if (!heights.toNorth() && heights.toSouth() && heights.toEast()) {
					drawInteriorNSWall(chunk, 11, y1, y2, z1, 8, wallMaterial, glassMaterial);
				} else if (!heights.toEast() && heights.toWest() && heights.toNorth()) {
					drawInteriorWEWall(chunk, 8, x2, y1, y2, 4, wallMaterial, glassMaterial);
				}
			}
			
			// SW corner
			if (heights.toSouthWest()) {
//				wallMaterial = (byte) Material.IRON_BLOCK.getMaterial();
				if (heights.toWest()) {
					drawInteriorWEWall(chunk, 0, y1, y2, 11, wallMaterial, glassMaterial);
				}
				if (heights.toSouth()) {
					drawInteriorNSWall(chunk, 4, y1, y2, 8, wallMaterial, glassMaterial);
				}
				
			} else {
//				wallMaterial = (byte) Material.GOLD_BLOCK.getMaterial();
				if (!heights.toSouth() && heights.toNorth() && heights.toWest()) {
					drawInteriorNSWall(chunk, 4, y1, y2, 8, z2, wallMaterial, glassMaterial);
				} else if (!heights.toWest() && heights.toEast() && heights.toSouth()) {
					drawInteriorWEWall(chunk, x1, 8, y1, y2, 11, wallMaterial, glassMaterial);
				}
			}
			
			// SE corner
			if (heights.toSouthEast()) {
//				wallMaterial = (byte) Material.DIAMOND_BLOCK.getMaterial();
				if (heights.toSouth()) {
					drawInteriorNSWall(chunk, 11, y1, y2, 8, wallMaterial, glassMaterial);
				}
				if (heights.toEast()) {
					drawInteriorWEWall(chunk, 8, y1, y2, 11, wallMaterial, glassMaterial);
				}
			} else {
//				wallMaterial = (byte) Material.LAPIS_BLOCK.getMaterial();
				if (!heights.toSouth() && heights.toNorth() && heights.toEast()) {
					drawInteriorNSWall(chunk, 11, y1, y2, 8, z2, wallMaterial, glassMaterial);
				} else if (!heights.toEast() && heights.toWest() && heights.toSouth()) {
					drawInteriorWEWall(chunk, 8, x2, y1, y2, 11, wallMaterial, glassMaterial);
				}
			}
		}
	}
	
	private void drawInteriorDoors(CityWorldGenerator generator, RealBlocks chunk, DataContext context, 
			DoorStyle doorStyle, boolean drawNarrowInteriors, int floor, int y1, int floorHeight, 
			int insetNS, int insetWE, boolean allowRounded,
			Material materialWall, Material materialGlass, 
			StairWell stairsLocation, Surroundings heights) {
		
		// precalculate
		int y2 = y1 + floorHeight;

		// first try the narrow logic (single wall in the middle)
		if (drawNarrowInteriors) {
			
			// Northward
			if (heights.toNorth()) {
//				materialWall = Material.COBBLESTONE;
				//2
				if (stairsLocation == StairWell.NONE)
					drawInteriorNSDoor(chunk, 7, y1, y2, 4, doorStyle, materialWall);
				//1
				drawInteriorWEDoor(chunk, 5, y1, y2, 0, doorStyle, materialWall);

			} else if (stairsLocation == StairWell.NONE && !allowRounded) {
//				materialWall = Material.BEDROCK;
				//a
				drawInteriorNSDoor(chunk, 7, y1, y2, 5, doorStyle, materialWall);
			}
				
			// Eastward
			if (heights.toEast()) {
//				materialWall = Material.CLAY;
				//3
				if (stairsLocation == StairWell.NONE)
					drawInteriorWEDoor(chunk, 9, y1, y2, 7, doorStyle, materialWall);
				//4
				drawInteriorNSDoor(chunk, 15, y1, y2, 5, doorStyle, materialWall);
				
			} else if (stairsLocation == StairWell.NONE && !allowRounded) {
//				materialWall = Material.SAND;
				//b
				drawInteriorWEDoor(chunk, 8, y1, y2, 7, doorStyle, materialWall);
			}
			
			// Westward
			if (heights.toWest()) {
//				materialWall = Material.IRON_BLOCK;
				//7
				if (stairsLocation == StairWell.NONE)
					drawInteriorWEDoor(chunk, 4, y1, y2, 8, doorStyle, materialWall);
				//8
				drawInteriorNSDoor(chunk, 0, y1, y2, 8, doorStyle, materialWall);
				
			} else if (stairsLocation == StairWell.NONE && !allowRounded) {
//				materialWall = Material.GOLD_BLOCK;
				//d
				drawInteriorWEDoor(chunk, 5, y1, y2, 8, doorStyle, materialWall);
			}
			
			// Southward
			if (heights.toSouth()) {
//				materialWall = Material.DIAMOND_BLOCK;
				//6
				if (stairsLocation == StairWell.NONE)
					drawInteriorNSDoor(chunk, 8, y1, y2, 9, doorStyle, materialWall);
				//5
				drawInteriorWEDoor(chunk, 8, y1, y2, 15, doorStyle, materialWall);

			} else if (stairsLocation == StairWell.NONE && !allowRounded) {
//				materialWall = Material.LAPIS_BLOCK;
				//c
				drawInteriorNSDoor(chunk, 8, y1, y2, 8, doorStyle, materialWall);
			}
			
		// if the narrow logic doesn't handle it, try to use the wide logic (two walls in the middle)
		} else {
		
			// NW corner
//			materialWall = Material.QUARTZ_ORE;
			if (heights.toNorthWest()) {
				if (heights.toNorth()) //1
					drawInteriorNSDoor(chunk, 4, y1, y2, 2, doorStyle, materialWall);
				if (heights.toWest()) //2
					drawInteriorWEDoor(chunk, 2, y1, y2, 4, doorStyle, materialWall);
				if (stairsLocation != StairWell.NORTHWEST) {
					//8,8
					drawInteriorWEDoor(chunk, 4, y1, y2, 4, doorStyle, materialWall);
					drawInteriorNSDoor(chunk, 4, y1, y2, 4, doorStyle, materialWall);
				}
//			} else {
//				if (stairsLocation == StairWell.NORTHEAST) //8
//					drawInteriorWEDoor(chunk, 5, y1, y2, 4, doorStyle, materialWall);
//				else if (stairsLocation == StairWell.SOUTHWEST) //8
//					drawInteriorNSDoor(chunk, 4, y1, y2, 5, doorStyle, materialWall);
			}
			
			// NE corner
//			materialWall = Material.IRON_ORE;
			if (heights.toNorthEast()) {
				if (heights.toNorth()) //3
					drawInteriorNSDoor(chunk, 11, y1, y2, 2, doorStyle, materialWall);
				if (heights.toEast()) //4
					drawInteriorWEDoor(chunk, 11, y1, y2, 4, doorStyle, materialWall);
				if (stairsLocation != StairWell.NORTHEAST) {
					//9,10
					drawInteriorWEDoor(chunk, 9, y1, y2, 4, doorStyle, materialWall);
					drawInteriorNSDoor(chunk, 11, y1, y2, 4, doorStyle, materialWall);
				}
//			} else {
//				if (stairsLocation == StairWell.NORTHWEST) //9
//					drawInteriorWEDoor(chunk, 8, y1, y2, 4, doorStyle, materialWall);
//				else if (stairsLocation == StairWell.SOUTHEAST) //10
//					drawInteriorNSDoor(chunk, 11, y1, y2, 5, doorStyle, materialWall);
			}
			
			// SW corner
//			materialWall = Material.GOLD_ORE;
			if (heights.toSouthWest()) {
				if (heights.toSouth()) //6
					drawInteriorNSDoor(chunk, 4, y1, y2, 11, doorStyle, materialWall);
				if (heights.toWest()) //5
					drawInteriorWEDoor(chunk, 2, y1, y2, 11, doorStyle, materialWall);
				if (stairsLocation != StairWell.SOUTHWEST) {
					//13,14
					drawInteriorWEDoor(chunk, 4, y1, y2, 11, doorStyle, materialWall);
					drawInteriorNSDoor(chunk, 4, y1, y2, 9, doorStyle, materialWall);
				}
//			} else {
//				if (stairsLocation == StairWell.NORTHWEST) //14
//					drawInteriorNSDoor(chunk, 4, y1, y2, 8, doorStyle, materialWall);
//				else if (stairsLocation == StairWell.SOUTHEAST) //13
//					drawInteriorWEDoor(chunk, 5, y1, y2, 11, doorStyle, materialWall);
			}
			
			// SE corner
//			materialWall = Material.DIAMOND_ORE;
			if (heights.toSouthEast()) {
				if (heights.toSouth()) //7
					drawInteriorNSDoor(chunk, 11, y1, y2, 11, doorStyle, materialWall);
				if (heights.toEast()) //7
					drawInteriorWEDoor(chunk, 11, y1, y2, 11, doorStyle, materialWall);
				if (stairsLocation != StairWell.SOUTHEAST) {
					//11,12
					drawInteriorWEDoor(chunk, 9, y1, y2, 11, doorStyle, materialWall);
					drawInteriorNSDoor(chunk, 11, y1, y2, 9, doorStyle, materialWall);
				}
//			} else {
//				if (stairsLocation == StairWell.SOUTHWEST) //12
//					drawInteriorWEDoor(chunk, 8, y1, y2, 11, doorStyle, materialWall);
//				else if (stairsLocation == StairWell.NORTHEAST) //11
//					drawInteriorNSDoor(chunk, 11, y1, y2, 8, doorStyle, materialWall);
			}
			
			// backfill with doors near stairs
			switch (stairsLocation) {
			case NORTHWEST:
//				materialWall = Material.DIAMOND_ORE;
				if (!heights.toEast()) //15
					drawInteriorWEDoor(chunk, 7, y1, y2, 4, doorStyle, materialWall);
//				materialWall = Material.DIAMOND_BLOCK;
				if (!heights.toSouth()) //16
					drawInteriorNSDoor(chunk, 4, y1, y2, 7, doorStyle, materialWall);
				break;
			case SOUTHEAST: 
//				materialWall = Material.REDSTONE_ORE;
				if (!heights.toNorth()) //17
					drawInteriorNSDoor(chunk, 11, y1, y2, 6, doorStyle, materialWall);
//				materialWall = Material.REDSTONE_BLOCK;
				if (!heights.toWest()) //18
					drawInteriorWEDoor(chunk, 6, y1, y2, 11, doorStyle, materialWall);
				break;
			case NORTHEAST: 
//				materialWall = Material.EMERALD_ORE;
				if (!heights.toWest()) //19
					drawInteriorWEDoor(chunk, 6, y1, y2, 4, doorStyle, materialWall);
//				materialWall = Material.EMERALD_BLOCK;
				if (!heights.toSouth()) //20
					drawInteriorNSDoor(chunk, 11, y1, y2, 7, doorStyle, materialWall);
				break;
			case SOUTHWEST: 
//				materialWall = Material.GOLD_ORE;
				if (!heights.toNorth()) //21
					drawInteriorNSDoor(chunk, 4, y1, y2, 6, doorStyle, materialWall);
//				materialWall = Material.GOLD_BLOCK;
				if (!heights.toEast()) //22
					drawInteriorWEDoor(chunk, 7, y1, y2, 11, doorStyle, materialWall);
				break;
			default:
				// nothing to draw here
			}
		}
	}
	
	private void drawExteriorDoors(CityWorldGenerator generator, RealBlocks chunk, DataContext context, 
			DoorStyle doorStyle, int floor, int y1, int height, 
			int insetNS, int insetWE, boolean allowRounded,
			Material materialWall, Material materialGlass, 
			StairWell stairsLocation, Surroundings heights) {
		
		// precalculate
		int y2 = y1 + height;
		int x1 = heights.toWest() ? 0 : insetWE + 1;
		int x2 = chunk.width - (heights.toEast() ? 0 : (insetWE + 1));
		int z1 = heights.toNorth() ? 0 : insetNS + 1;
		int z2 = chunk.width - (heights.toSouth() ? 0 : (insetNS + 1));

		// rounded potential?
		if (allowRounded) {
			if (heights.toEast() && heights.toSouth())
				drawExteriorWEDoor(chunk, 13, y1, y2, z1 - 1, doorStyle, materialWall);
			if (heights.toWest() && heights.toNorth())
				drawExteriorEWDoor(chunk, 0, y1, y2, z2, doorStyle, materialWall);
			if (heights.toWest() && heights.toSouth())
				drawExteriorNSDoor(chunk, x2, y1, y2, 13, doorStyle, materialWall);
			if (heights.toEast() && heights.toNorth())
				drawExteriorSNDoor(chunk, x1 - 1, y1, y2, 0, doorStyle, materialWall);
		} else {
			if (!heights.toNorth())
				drawExteriorWEDoor(chunk, 5, y1, y2, z1 - 1, doorStyle, materialWall);
			if (!heights.toSouth())
				drawExteriorEWDoor(chunk, 8, y1, y2, z2, doorStyle, materialWall);
			if (!heights.toWest())
				drawExteriorNSDoor(chunk, x1 - 1, y1, y2, 8, doorStyle, materialWall);
			if (!heights.toEast())
				drawExteriorSNDoor(chunk, x2, y1, y2, 5, doorStyle, materialWall);
		}
	}

	private static int maxInsetForRooms = 2;
	
	private void drawInteriorRooms(CityWorldGenerator generator, RealBlocks chunk, DataContext context, 
			boolean drawNarrowInteriors, RoomProvider rooms, int floor, int y1, int height, 
			int insetNS, int insetWE, boolean allowRounded,
			Material materialWall, Material materialGlass, 
			StairWell stairsLocation, Surroundings heights) {
		
		// skip the rooms?
		if (!generator.settings.includeBuildingInteriors)
			return;
		
		// outer rooms?
		boolean includeOuterRooms = insetNS <= maxInsetForRooms || insetWE <= maxInsetForRooms;
		
		// first try the narrow logic (single wall in the middle)
		if (drawNarrowInteriors) {
			
			// don't do these for rounded cases
			if (!allowRounded) {

				// Northward
				if (heights.toNorth())
					drawInteriorRoom(generator, chunk, rooms, floor, 3, y1, 1, height, 
							Facing.NORTH, materialWall, materialGlass); //1
				if (insetNS < 2)
					drawInteriorRoom(generator, chunk, rooms, floor, 8, y1, 2, height, 
							Facing.WEST, materialWall, materialGlass); //2
					
				// Eastward
				if (heights.toEast())
					drawInteriorRoom(generator, chunk, rooms, floor, 12, y1, 3, height, 
							Facing.EAST, materialWall, materialGlass); //3
				if (insetWE < 2)
					drawInteriorRoom(generator, chunk, rooms, floor, 11, y1, 8, height, 
							Facing.NORTH, materialWall, materialGlass); //4
				
				// Southward
				if (heights.toSouth())
					drawInteriorRoom(generator, chunk, rooms, floor, 11, y1, 12, height, 
							Facing.SOUTH, materialWall, materialGlass); //5
				if (insetNS < 2)
					drawInteriorRoom(generator, chunk, rooms, floor, 5, y1, 11, height, 
							Facing.EAST, materialWall, materialGlass); //6
				
				// Westward
				if (heights.toWest())
					drawInteriorRoom(generator, chunk, rooms, floor, 1, y1, 10, height, 
							Facing.WEST, materialWall, materialGlass); //7
				if (insetWE < 2)
					drawInteriorRoom(generator, chunk, rooms, floor, 2, y1, 5, height, 
							Facing.SOUTH, materialWall, materialGlass); //8
			}
			
		// if the narrow logic doesn't handle it, try to use the wide logic (two walls in the middle)
		} else {
		
			// NW corner
			if (heights.toNorthWest()) {
				if (heights.toNorth() && heights.toWest()) {
					//1 & 2
					drawInteriorRoom(generator, chunk, rooms, floor, 1, y1, 0, height, 
							Facing.EAST, materialWall, materialGlass);
					drawInteriorRoom(generator, chunk, rooms, floor, 5, y1, 0, height, 
							Facing.WEST, materialWall, materialGlass);
				}
			} else if (includeOuterRooms) {
				if (heights.toNorth()) {
					if (heights.toWest()) {
						//none
					} else if (stairsLocation != StairWell.NORTHWEST ||
							stairsLocation != StairWell.WEST) {
						//n & m
						drawInteriorRoom(generator, chunk, rooms, floor, 4, y1, 1, height, 
								Facing.SOUTH, materialWall, materialGlass);
						drawInteriorRoom(generator, chunk, rooms, floor, 4, y1, 5, height, 
								Facing.NORTH, materialWall, materialGlass);
					}
				} else {
					if (heights.toWest() && 
								(stairsLocation != StairWell.NORTHWEST ||
								 stairsLocation != StairWell.NORTH)) {
						//c & d
						drawInteriorRoom(generator, chunk, rooms, floor, 1, y1, 4, height, 
								Facing.EAST, materialWall, materialGlass);
//						drawInteriorRoom(generator, chunk, rooms, floor, 5, y1, 4, height, 
//								Facing.WEST, materialWall, materialGlass);
					} else if (!allowRounded && stairsLocation == StairWell.SOUTHEAST) {
						//q
						drawInteriorRoom(generator, chunk, rooms, floor, 4, y1, 4, height, 
								Facing.EAST, materialWall, materialGlass);
					}
				}
			}
			
			// NE corner
			if (heights.toNorthEast()) {
				if (heights.toNorth() && heights.toEast()) {
					//3 & 4
					drawInteriorRoom(generator, chunk, rooms, floor, 13, y1, 1, height, 
							Facing.SOUTH, materialWall, materialGlass);
					drawInteriorRoom(generator, chunk, rooms, floor, 13, y1, 5, height, 
							Facing.NORTH, materialWall, materialGlass);
				}
			} else if (includeOuterRooms) {
				if (heights.toNorth()) {
					if (heights.toEast()) {
						//none
					} else if (stairsLocation != StairWell.NORTHEAST ||
							stairsLocation != StairWell.EAST) {
						//g & h
						drawInteriorRoom(generator, chunk, rooms, floor, 9, y1, 1, height, 
								Facing.SOUTH, materialWall, materialGlass);
//						drawInteriorRoom(generator, chunk, rooms, floor, 9, y1, 5, height, 
//								Facing.NORTH, materialWall, materialGlass);
					}
				} else {
					if (heights.toEast() && 
							(stairsLocation != StairWell.NORTHEAST ||
							 stairsLocation != StairWell.NORTH)) {
						//a & b
						drawInteriorRoom(generator, chunk, rooms, floor, 8, y1, 4, height, 
								Facing.EAST, materialWall, materialGlass);
						drawInteriorRoom(generator, chunk, rooms, floor, 12, y1, 4, height, 
								Facing.WEST, materialWall, materialGlass);
					} else if (!allowRounded && stairsLocation == StairWell.SOUTHWEST) {
						//r
						drawInteriorRoom(generator, chunk, rooms, floor, 9, y1, 4, height, 
								Facing.SOUTH, materialWall, materialGlass);
					}
				}
			}
			
			// SW corner
			if (heights.toSouthWest()) {
				if (heights.toSouth() && heights.toWest()) {
					//5 & 6
					drawInteriorRoom(generator, chunk, rooms, floor, 0, y1, 12, height, 
							Facing.NORTH, materialWall, materialGlass);
					drawInteriorRoom(generator, chunk, rooms, floor, 0, y1, 8, height, 
							Facing.SOUTH, materialWall, materialGlass);
				}
			} else if (includeOuterRooms) {
				if (heights.toSouth()) {
					if (heights.toWest()) {
						//none
					} else if (stairsLocation != StairWell.SOUTHWEST ||
							stairsLocation != StairWell.WEST) {
						//p & o
//						drawInteriorRoom(generator, chunk, rooms, floor, 4, y1, 8, height, 
//								Facing.SOUTH, materialWall, materialGlass);
						drawInteriorRoom(generator, chunk, rooms, floor, 4, y1, 12, height, 
								Facing.NORTH, materialWall, materialGlass);
					}
				} else {
					if (heights.toWest() && 
							(stairsLocation != StairWell.SOUTHWEST ||
							 stairsLocation != StairWell.SOUTH)) {

						//j & i
						drawInteriorRoom(generator, chunk, rooms, floor, 1, y1, 9, height, 
								Facing.EAST, materialWall, materialGlass);
						drawInteriorRoom(generator, chunk, rooms, floor, 5, y1, 9, height, 
								Facing.WEST, materialWall, materialGlass);
					} else if (!allowRounded && stairsLocation == StairWell.NORTHEAST) {
						//t
						drawInteriorRoom(generator, chunk, rooms, floor, 4, y1, 9, height, 
								Facing.NORTH, materialWall, materialGlass);
					}
				}
			}
			
			// SE corner
			if (heights.toSouthEast()) {
				if (heights.toSouth() && heights.toEast()) {
					//7 & 8
					drawInteriorRoom(generator, chunk, rooms, floor, 12, y1, 13, height, 
							Facing.WEST, materialWall, materialGlass);
					drawInteriorRoom(generator, chunk, rooms, floor, 8, y1, 13, height, 
							Facing.EAST, materialWall, materialGlass);
				}
			} else if (includeOuterRooms) {
				if (heights.toSouth()) {
					if (heights.toEast()) {
						//none
					} else if (stairsLocation != StairWell.SOUTHEAST ||
							stairsLocation != StairWell.EAST) {
						//e & f
						drawInteriorRoom(generator, chunk, rooms, floor, 9, y1, 8, height, 
								Facing.SOUTH, materialWall, materialGlass);
						drawInteriorRoom(generator, chunk, rooms, floor, 9, y1, 12, height, 
								Facing.NORTH, materialWall, materialGlass);
					}
				} else {
					if (heights.toEast() && 
							(stairsLocation != StairWell.SOUTHEAST ||
							 stairsLocation != StairWell.SOUTH)) {

						//l & k
//						drawInteriorRoom(generator, chunk, rooms, floor, 8, y1, 9, height, 
//								Facing.EAST, materialWall, materialGlass);
						drawInteriorRoom(generator, chunk, rooms, floor, 12, y1, 9, height, 
								Facing.WEST, materialWall, materialGlass);
					} else if (!allowRounded && stairsLocation == StairWell.NORTHWEST) {
						//s
						drawInteriorRoom(generator, chunk, rooms, floor, 9, y1, 9, height, 
								Facing.WEST, materialWall, materialGlass);
					}
				}
			}
		}
	}
	
	//TODO I need to actually make this dynamic based on how much room there is
	private static int roomWidth = 3;
	private static int roomDepth = 3;
	
	private void drawInteriorRoom(CityWorldGenerator generator, RealBlocks chunk, 
			RoomProvider rooms, int floor, int x, int y, int z, int height, 
			Facing sideWithWall, Material materialWall, Material materialGlass) {

		rooms.drawFixtures(generator, chunk, chunkOdds, floor, x, y, z, 
				roomWidth, height, roomDepth, sideWithWall, materialWall, materialGlass);
	}
	
	private void drawInteriorNSWall(RealBlocks chunk, int x, int y1, int y2, int z, Material wallMaterial, Material glassMaterial) {
		drawInteriorNSWall(chunk, x, y1, y2, z, z + 8, wallMaterial, glassMaterial);
	}
	
	private void drawInteriorWEWall(RealBlocks chunk, int x, int y1, int y2, int z, Material wallMaterial, Material glassMaterial) {
		drawInteriorWEWall(chunk, x, x + 8, y1, y2, z, wallMaterial, glassMaterial);
	}
	
	private void drawInteriorNSWall(RealBlocks chunk, int x, int y1, int y2, int z1, int z2, Material wallMaterial, Material glassMaterial) {
		BlackMagic.setBlocks(chunk, x, x + 1, y1, y2, z1, z2, wallMaterial, glassMaterial, wallsInterior);
	}
	
	private void drawInteriorWEWall(RealBlocks chunk, int x1, int x2, int y1, int y2, int z, Material wallMaterial, Material glassMaterial) {
		BlackMagic.setBlocks(chunk, x1, x2, y1, y2, z, z + 1, wallMaterial, glassMaterial, wallsInterior);
	}
	
	private void drawInteriorNSDoor(RealBlocks chunk, int x, int y1, int y2, int z, DoorStyle doorStyle, Material wall) {
		if (chunkOdds.playOdds(oddsOfAnInteriorDoor))
			drawDoor(chunk, x, x, x, y1, y2, z, z + 1, z + 2, Door.WESTBYNORTHWEST, doorStyle, wall, interiorDoorMaterial);
	}
	
	private void drawInteriorWEDoor(RealBlocks chunk, int x, int y1, int y2, int z, DoorStyle doorStyle, Material wall) {
		if (chunkOdds.playOdds(oddsOfAnInteriorDoor))
			drawDoor(chunk, x, x + 1, x + 2, y1, y2, z, z, z, Door.NORTHBYNORTHWEST, doorStyle, wall, interiorDoorMaterial);
	}
	
	private boolean doExteriorDoor(RealBlocks chunk, Door direction) {
		boolean chunkXEven = getChunkX() % 2 == 0;
		boolean chunkZEven = getChunkZ() % 2 == 0;
		
		switch (direction) {
		default:
		case WESTBYNORTHWEST:
		case EASTBYNORTHEAST:
			return (chunkXEven && chunkZEven) || (chunkXEven && !chunkZEven);
		case NORTHBYNORTHWEST:
		case SOUTHBYSOUTHEAST:
			return (!chunkXEven && chunkZEven) || (!chunkXEven && !chunkZEven);
		}
		
	}
	
	private void drawExteriorNSDoor(RealBlocks chunk, int x, int y1, int y2, int z, DoorStyle doorStyle, Material wall) {
		if (doExteriorDoor(chunk, Door.WESTBYNORTHWEST)) {
//			if (chunkOdds.playOdds(oddsOfAnExteriorDoor)) {
			drawDoor(chunk, x, x, x, y1, y2, z, z + 1, z + 2, Door.WESTBYNORTHWEST, doorStyle, wall, exteriorDoorMaterial);
			chunk.clearBlocks(x + 1, y1, y1 + 2, z + 1);
			chunk.clearBlocks(x - 1, y1, y1 + 2, z + 1);
		}
	}
	
	private void drawExteriorSNDoor(RealBlocks chunk, int x, int y1, int y2, int z, DoorStyle doorStyle, Material wall) {
		if (doExteriorDoor(chunk, Door.EASTBYNORTHEAST)) {
//			if (chunkOdds.playOdds(oddsOfAnExteriorDoor)) {
			drawDoor(chunk, x, x, x, y1, y2, z, z + 1, z + 2, Door.EASTBYNORTHEAST, doorStyle, wall, exteriorDoorMaterial);
			chunk.clearBlocks(x + 1, y1, y1 + 2, z + 1);
			chunk.clearBlocks(x - 1, y1, y1 + 2, z + 1);
		}
	}

	private void drawExteriorWEDoor(RealBlocks chunk, int x, int y1, int y2, int z, DoorStyle doorStyle, Material wall) {
		if (doExteriorDoor(chunk, Door.NORTHBYNORTHWEST)) {
//			if (chunkOdds.playOdds(oddsOfAnExteriorDoor)) {
			drawDoor(chunk, x, x + 1, x + 2, y1, y2, z, z, z, Door.NORTHBYNORTHWEST, doorStyle, wall, exteriorDoorMaterial);
			chunk.clearBlocks(x + 1, y1, y1 + 2, z + 1);
			chunk.clearBlocks(x + 1, y1, y1 + 2, z - 1);
		}
	}
	
	private void drawExteriorEWDoor(RealBlocks chunk, int x, int y1, int y2, int z, DoorStyle doorStyle, Material wall) {
		if (doExteriorDoor(chunk, Door.SOUTHBYSOUTHEAST)) {
//			if (chunkOdds.playOdds(oddsOfAnExteriorDoor)) {
			drawDoor(chunk, x, x + 1, x + 2, y1, y2, z, z, z, Door.SOUTHBYSOUTHEAST, doorStyle, wall, exteriorDoorMaterial);
			chunk.clearBlocks(x + 1, y1, y1 + 2, z + 1);
			chunk.clearBlocks(x + 1, y1, y1 + 2, z - 1);
		}
	}
	
	//TODO roof fixtures (peak, helipad, air conditioning, stairwells access, penthouse, castle trim, etc.
	protected void drawRoof(CityWorldGenerator generator, InitialBlocks chunk, DataContext context, 
			int y1, int insetNS, int insetWE, int floor, 
			boolean allowRounded, Material material, Surroundings heights) {
		drawRoof(generator, chunk, context, y1, insetNS, insetWE, floor, allowRounded, false, material, heights, roofStyle);
	}
	
	protected void drawRoof(CityWorldGenerator generator, InitialBlocks chunk, DataContext context, 
			int y1, int insetNS, int insetWE, int floor, boolean allowRounded, boolean outsetEffect,
			Material material, Surroundings heights, RoofStyle thisStyle) {
		
		// protect ourselves from really tall floors
		int maxHeight = Math.min(6, aboveFloorHeight);
		boolean isRounded = willBeRounded(allowRounded, heights);
		int inset = Math.max(insetNS, insetWE);
		
		// what type of roof are we talking about?
		switch (thisStyle) {
		case EDGED:
			drawEdgedRoof(generator, chunk, context, y1, insetNS, insetWE, floor, roofFeature, allowRounded, outsetEffect, material, true, heights);
			break;
		case FLATTOP:
			drawEdgedRoof(generator, chunk, context, y1, insetNS, insetWE, floor, roofFeature, allowRounded, outsetEffect, material, false, heights);
			break;
		case PEAK:
			if (heights.getNeighborCount() == 0) { 
				for (int i = 0; i < maxHeight; i++) {
					if (i == maxHeight - 2)
						drawCeilings(generator, chunk, context, y1 + i * roofScale, roofScale, insetNS + i, insetWE + i, allowRounded, outsetEffect, true, material, heights);
					else
						drawWallParts(generator, chunk, context, y1 + i * roofScale, roofScale, insetNS + i, insetWE + i, floor, 
								allowRounded, true, true, material, heights);
				}
			} else {
				drawEdgedRoof(generator, chunk, context, y1, insetNS, insetWE, floor, roofFeature, allowRounded, outsetEffect, material, false, heights);
				for (int i = 0; i < 4; i++)
					drawWallParts(generator, chunk, context, y1 + i, 1, insetNS + i, insetWE + i, floor, 
							allowRounded, true, true, material, heights);
			}
			break;
		case PEAKS:
			drawEdgedRoof(generator, chunk, context, y1, insetNS, insetWE, floor, roofFeature, allowRounded, outsetEffect, material, false, heights);
			if (!isRounded) {
				for (int i = 0; i < 3; i++)
					chunk.setWalls(inset + 1 + i, 16 - inset - 1 - i, y1 + i, y1 + 1 + i, inset + 1 + i, 16 - inset - 1 - i, material);
			}
			break;
		case DUPLOS:
			drawEdgedRoof(generator, chunk, context, y1, insetNS, insetWE, floor, RoofFeature.SKYLIGHT, allowRounded, outsetEffect, material, false, heights);
			if (!isRounded) {
				chunk.setWalls(inset + 1, 16 - inset - 1, y1    , y1 + 3, inset + 1, 16 - inset - 1, material);
				chunk.setWalls(inset + 2, 16 - inset - 2, y1 + 1, y1 + 3, inset + 2, 16 - inset - 2, material);
				chunk.setWalls(inset + 3, 16 - inset - 3, y1 + 2, y1 + 3, inset + 3, 16 - inset - 3, material);
			}
			break;
		case BOXTOPS:
			drawEdgedRoof(generator, chunk, context, y1, insetNS, insetWE, floor, RoofFeature.SKYLIGHT, allowRounded, outsetEffect, material, false, heights);
			if (!isRounded) {
				chunk.setWalls(inset,     16 - inset    , y1    , y1 + 3, inset    , 16 - inset    , material);
				chunk.setWalls(inset + 1, 16 - inset - 1, y1 + 2, y1 + 3, inset + 1, 16 - inset - 1, material);
				chunk.setWalls(inset + 2, 16 - inset - 2, y1 + 2, y1 + 3, inset + 2, 16 - inset - 2, material);
			}
			break;
		case TENT_NORTHSOUTH:
			if (heights.getNeighborCount() == 0) { 
				for (int i = 0; i < maxHeight; i++) {
					if (i == maxHeight - 2)
						drawCeilings(generator, chunk, context, y1 + i * roofScale, roofScale, insetNS + i, insetWE, allowRounded, outsetEffect, true, material, heights);
					else
						drawWallParts(generator, chunk, context, y1 + i * roofScale, roofScale, insetNS + i, insetWE, floor, 
								allowRounded, true, true, material, heights);
				}
			} else {
				drawRoof(generator, chunk, context, y1, insetNS, insetWE, floor, allowRounded, outsetEffect, material, heights, RoofStyle.INSET_BOX);
//				drawEdgedRoof(generator, chunk, context, y1, insetNS, insetWE, floor, allowRounded, material, false, heights);
//				for (int i = 0; i < 3; i++)
//					drawExteriorParts(generator, chunk, context, y1 + i, 1, insetNS + i, insetWE, floor, 
//							CornerStyle.FILLED, allowRounded, false, material, material, heights);
			}
			break;
		case TENT_WESTEAST:
			if (heights.getNeighborCount() == 0) { 
				for (int i = 0; i < maxHeight; i++) {
					if (i == maxHeight - 2)
						drawCeilings(generator, chunk, context, y1 + i * roofScale, roofScale, insetNS, insetWE + i, allowRounded, outsetEffect, true, material, heights);
					else
						drawWallParts(generator, chunk, context, y1 + i * roofScale, roofScale, insetNS, insetWE + i, floor, 
								allowRounded, true, true, material, heights);
				}
			} else {
				drawRoof(generator, chunk, context, y1, insetNS, insetWE, floor, allowRounded, outsetEffect, material, heights, RoofStyle.RAISED_BOX);
//				drawEdgedRoof(generator, chunk, context, y1, insetNS, insetWE, floor, allowRounded, material, false, heights);
//				for (int i = 0; i < 3; i++)
//					drawExteriorParts(generator, chunk, context, y1 + i, 1, insetNS, insetWE + i, floor, 
//							CornerStyle.FILLED, allowRounded, false, material, material, heights);
			}
			break;
		case BOXED:
			drawEdgedRoof(generator, chunk, context, y1, insetNS, insetWE, floor, roofFeature, allowRounded, outsetEffect, material, false, heights);
			drawWallParts(generator, chunk, context, y1, 2, insetNS, insetWE, floor, 
					allowRounded, true, true, material, heights);
			break;
		case INSET_BOX:
			drawEdgedRoof(generator, chunk, context, y1, insetNS, insetWE, floor, roofFeature, allowRounded, outsetEffect, material, false, heights);
			drawWallParts(generator, chunk, context, y1, 1, insetNS, insetWE, floor, 
					allowRounded, true, true, material, heights);
			drawWallParts(generator, chunk, context, y1 + 1, 2, insetNS + 1, insetWE + 1, floor, 
					allowRounded, true, true, material, heights);
			break;
		case INSET_BOXES:
			drawEdgedRoof(generator, chunk, context, y1, insetNS, insetWE, floor, roofFeature, allowRounded, outsetEffect, material, false, heights);
			if (!isRounded) {
				chunk.setWalls(inset + 1, 16 - inset - 1, y1, y1 + 2, inset + 1, 16 - inset - 1, material);
			}
			break;
		case RAISED_BOX:
			drawEdgedRoof(generator, chunk, context, y1, insetNS, insetWE, floor, roofFeature, allowRounded, outsetEffect, material, false, heights);
			drawWallParts(generator, chunk, context, y1, 1, insetNS + 1, insetWE + 1, floor, 
					allowRounded, true, true, material, heights);
			drawWallParts(generator, chunk, context, y1 + 1, 2, insetNS, insetWE, floor, 
					allowRounded, true, true, material, heights);
			break;
		case RAISED_BOXES:
			drawEdgedRoof(generator, chunk, context, y1, insetNS, insetWE, floor, roofFeature, allowRounded, outsetEffect, material, false, heights);
			if (!isRounded) {
				chunk.setWalls(inset + 1, 16 - inset - 1, y1, y1 + 1, inset + 1, 16 - inset - 1, material);
				chunk.setWalls(inset, 16 - inset, y1 + 1, y1 + 3, inset, 16 - inset, material);
			}
			break;
		case INSET_RIDGEBOX:
			drawEdgedRoof(generator, chunk, context, y1, insetNS, insetWE, floor, roofFeature, allowRounded, outsetEffect, material, false, heights);
			drawWallParts(generator, chunk, context, y1, 1, insetNS, insetWE, floor, 
					allowRounded, true, true, material, heights);
			drawWallParts(generator, chunk, context, y1, 1, insetNS + 1, insetWE + 1, floor, 
					allowRounded, true, true, material, heights);
			drawWallParts(generator, chunk, context, y1 + 1, 3, insetNS + 2, insetWE + 2, floor, 
					allowRounded, true, true, material, heights);
			drawWallParts(generator, chunk, context, y1 + 3, 1, insetNS + 1, insetWE + 1, floor, 
					allowRounded, true, true, material, heights);
			break;
		case INSET_RIDGEBOXES:
			drawEdgedRoof(generator, chunk, context, y1, insetNS, insetWE, floor, roofFeature, allowRounded, outsetEffect, material, false, heights);
			if (!isRounded) {
				chunk.setWalls(inset + 2, 16 - inset - 2, y1, y1 + 3, inset + 2, 16 - inset - 2, material);
				chunk.setWalls(inset + 1, 16 - inset - 1, y1 + 2, y1 + 3, inset + 1, 16 - inset - 1, material);
			}
			break;
		case OUTSET_BOX:
			drawEdgedRoof(generator, chunk, context, y1, insetNS - 1, insetWE - 1, floor, roofFeature, allowRounded, outsetEffect, material, true, heights);
			drawWallParts(generator, chunk, context, y1, 2, insetNS - 1, insetWE - 1, floor, 
					allowRounded, true, true, material, heights);
			break;
		}
	}
	
	protected void drawRoof(CityWorldGenerator generator, RealBlocks chunk, DataContext context, 
			int y1, int insetNS, int insetWE, int floor, boolean allowRounded, boolean outsetEffect,
			Material material, Surroundings heights, RoofStyle thisStyle, RoofFeature thisFeature) {
		
		// protect ourselves from really tall floors
		boolean isRounded = willBeRounded(allowRounded, heights);
		int inset = Math.max(insetNS, insetWE);
		
		// add some balloons?
		if (generator.worldStyle == WorldStyle.FLOATING) {
			switch (thisStyle) {
			case FLATTOP:
			case EDGED:
			case BOXED:
			case BOXTOPS:
			case DUPLOS:
			case INSET_BOX:
			case INSET_BOXES:
			case INSET_RIDGEBOX:
			case INSET_RIDGEBOXES:
			case RAISED_BOX:
			case RAISED_BOXES:
			case OUTSET_BOX:
				switch (roofFeature) {
				case CONDITIONERS:
				case PLAIN:
				case SKYLIGHT:
				case SKYLIGHT_BOX:
				case SKYLIGHT_CHECKERS:
				case SKYLIGHT_CROSS:
				case SKYLIGHT_NS:
				case SKYLIGHT_TINY:
				case SKYLIGHT_WE:
					StructureInAirProvider balloons = generator.structureInAirProvider;
					int y2 = y1;
					if (isRounded) {
						balloons.generateBalloon(generator, chunk, context, 7, y2, 7, chunkOdds);
					} else {
						if (chunkOdds.flipCoin())
							balloons.generateBigBalloon(generator, chunk, context, y2, chunkOdds);
						else {
							if (chunkOdds.playOdds(Odds.oddsPrettyLikely))
								balloons.generateBalloon(generator, chunk, context, inset + 2, y2, inset + 2, chunkOdds);
							if (chunkOdds.playOdds(Odds.oddsPrettyLikely))
								balloons.generateBalloon(generator, chunk, context, inset + 2, y2, 16 - inset - 2, chunkOdds);
							if (chunkOdds.playOdds(Odds.oddsPrettyLikely))
								balloons.generateBalloon(generator, chunk, context, 16 - inset - 2, y2, inset + 2, chunkOdds);
							if (chunkOdds.playOdds(Odds.oddsPrettyLikely))
								balloons.generateBalloon(generator, chunk, context, 16 - inset - 2, y2, 16 - inset - 2, chunkOdds);
						}
					}
					break;
				default:
					// for the rest of them do nothing
					break;
				}
				break;
			default:
				// for the rest of them do nothing
				break;
			}
		}
	}
	
	private void drawEdgedRoof(CityWorldGenerator generator, InitialBlocks chunk, DataContext context, 
			int y1, int insetNS, int insetWE, int floor, RoofFeature features,
			boolean allowRounded, boolean outsetEffect, Material material, boolean doEdge, Surroundings heights) {
		
		// a little bit of edge 
		if (doEdge)
			drawWallParts(generator, chunk, context, y1, 1, insetNS, insetWE, floor, 
					allowRounded, true, true, material, heights);
		if (features == RoofFeature.ANTENNAS && heights.getCompleteNeighborCount() != 0)
			features = RoofFeature.CONDITIONERS;
		
		// add the special features
		switch (features) {
		default:
		case PLAIN:
			break;
		case ANTENNAS:
			drawAntenna(chunk, 6, y1, 6);
			drawAntenna(chunk, 6, y1, 9);
			drawAntenna(chunk, 9, y1, 6);
			drawAntenna(chunk, 9, y1, 9);
			break;
		case CONDITIONERS:
			drawConditioner(chunk, 6, y1, 6);
			drawConditioner(chunk, 6, y1, 9);
			drawConditioner(chunk, 9, y1, 6);
			drawConditioner(chunk, 9, y1, 9);
			break;
		case TILE:
			drawCeilings(generator, chunk, context, y1, 1, insetNS + 1, insetWE + 1, allowRounded, outsetEffect, true, tileMaterial, heights);
			break;
		case SKYLIGHT:
			chunk.setBlocks(5, 6, y1 - 1, 6, 10, Material.GLASS);
			chunk.setBlocks(10, 11, y1 - 1, 6, 10, Material.GLASS);
			chunk.setBlocks(6, 10, y1 - 1, 5, 11, Material.GLASS);
			break;
		case SKYPEAK:
			chunk.setBlocks(5, 6, y1 - 1, 6, 10, Material.GLASS);
			chunk.setBlocks(10, 11, y1 - 1, 6, 10, Material.GLASS);
			chunk.setBlocks(6, 10, y1 - 1, 5, 6, Material.GLASS);
			chunk.setBlocks(6, 10, y1 - 1, 10, 11, Material.GLASS);
			chunk.setBlocks(6, 10, y1 - 1, 6, 10, Material.AIR);

			chunk.setWalls(6, 10, y1, y1 + 1, 6, 10, Material.GLASS);
			chunk.setBlocks(7, 9, y1 + 1, y1 + 2, 7, 9, Material.GLASS);
			break;
		case ALTPEAK:
			chunk.setBlocks(5, 6, y1 - 1, 6, 10, Material.GLASS);
			chunk.setBlocks(10, 11, y1 - 1, 6, 10, Material.GLASS);
			chunk.setBlocks(6, 10, y1 - 1, 5, 6, Material.GLASS);
			chunk.setBlocks(6, 10, y1 - 1, 10, 11, Material.GLASS);
			chunk.setBlocks(6, 10, y1 - 1, 6, 10, Material.AIR);

			chunk.setWalls(6, 10, y1, y1 + 1, 6, 10, material);
			chunk.setBlocks(7, 9, y1 + 1, y1 + 2, 7, 9, Material.GLASS);
			break;
		case ALTPEAK2:
			chunk.setBlocks(5, 6, y1 - 1, 6, 10, material);
			chunk.setBlocks(10, 11, y1 - 1, 6, 10, material);
			chunk.setBlocks(6, 10, y1 - 1, 5, 6, material);
			chunk.setBlocks(6, 10, y1 - 1, 10, 11, material);
			chunk.setBlocks(6, 10, y1 - 1, 6, 10, Material.AIR);

			chunk.setWalls(6, 10, y1, y1 + 1, 6, 10, material);
			chunk.setBlocks(7, 9, y1 + 1, y1 + 2, 7, 9, Material.GLASS);
			break;
		case SKYLIGHT_NS:
			chunk.setBlocks(6, 10, y1 - 1, 6, 7, Material.GLASS); //TODO these should only create class if the surrounding blocks are set to something
			chunk.setBlocks(6, 10, y1 - 1, 9, 10, Material.GLASS);
			break;
		case SKYLIGHT_WE:
			chunk.setBlocks(6, 7, y1 - 1, 6, 10, Material.GLASS);
			chunk.setBlocks(9, 10, y1 - 1, 6, 10, Material.GLASS);
			break;
		case SKYLIGHT_BOX:
			chunk.setBlocks(5, 6, y1 - 1, 6, 10, Material.GLASS);
			chunk.setBlocks(10, 11, y1 - 1, 6, 10, Material.GLASS);
			chunk.setBlocks(6, 10, y1 - 1, 5, 6, Material.GLASS);
			chunk.setBlocks(6, 10, y1 - 1, 10, 11, Material.GLASS);
			break;
		case SKYLIGHT_TINY:
			chunk.setBlocks(7, 9, y1 - 1, 7, 9, Material.GLASS);
			break;
		case SKYLIGHT_CHECKERS:
			chunk.setBlock(6, y1 - 1, 6, Material.GLASS);
			chunk.setBlock(8, y1 - 1, 6, Material.GLASS);
			chunk.setBlock(7, y1 - 1, 7, Material.GLASS);
			chunk.setBlock(9, y1 - 1, 7, Material.GLASS);
			chunk.setBlock(6, y1 - 1, 8, Material.GLASS);
			chunk.setBlock(8, y1 - 1, 8, Material.GLASS);
			chunk.setBlock(7, y1 - 1, 9, Material.GLASS);
			chunk.setBlock(9, y1 - 1, 9, Material.GLASS);
			break;
		case SKYLIGHT_CROSS:
			chunk.setBlocks(7, 9, y1 - 1, 6, 10, Material.GLASS);
			chunk.setBlocks(6, 10, y1 - 1, 7, 9, Material.GLASS);
			break;
		}
	}
	
	private void drawAntenna(InitialBlocks chunk, int x, int y, int z) {
		
		if (chunkOdds.flipCoin()) {
			int y2 = y + 8 + chunkOdds.getRandomInt(8);
			chunk.setBlocks(x, y, y + 3, z, antennaBase);
			chunk.setBlocks(x, y + 2, y2, z, antenna);
			if (y2 >= navLightY) {
				navLightX = x;
				navLightY = y2 - 1;
				navLightZ = z;
			}
		}
	}
	
	protected void drawNavLight(RealBlocks chunk, DataContext context) {
		if (navLightY > 0)
//			chunk.setTorch(navLightX, navLightY, navLightZ, context.torchMat, BadMagic.Torch.FLOOR);
			chunk.setBlock(navLightX, navLightY, navLightZ, Material.END_ROD);
	}
	
	private void drawConditioner(InitialBlocks chunk, int x, int y, int z) {
		
		//TODO air conditioner tracks are not round
		if (chunkOdds.flipCoin()) {
			chunk.setBlock(x, y, z, conditioner);
			chunk.setBlock(x, y + 1, z, conditionerTrim);
			if (chunkOdds.flipCoin()) {
				chunk.setBlockIfEmpty(x - 1, y, z, duct);
				chunk.setBlockIfEmpty(x - 2, y, z, duct);
			}
			if (chunkOdds.flipCoin()) {
				chunk.setBlockIfEmpty(x + 1, y, z, duct);
				chunk.setBlockIfEmpty(x + 2, y, z, duct);
			}
			if (chunkOdds.flipCoin()) {
				chunk.setBlockIfEmpty(x, y, z - 1, duct);
				chunk.setBlockIfEmpty(x, y, z - 2, duct);
			}
			if (chunkOdds.flipCoin()) {
				chunk.setBlockIfEmpty(x, y, z + 1, duct);
				chunk.setBlockIfEmpty(x, y, z + 2, duct);
			}
			if (chunkOdds.flipCoin()) {
				chunk.setBlock(x, y, z, conditioner);
				chunk.setBlock(x, y + 1, z, conditionerTrim);
//			} else {
//				chunk.setBlocks(x, x + 2, y, z, z + 2, conditioner);
//				chunk.setBlocks(x, x + 2, y + 1, z, z + 2, conditionerGrill);
			}
		}
	}
	
	private void drawDoor(RealBlocks chunk, int x1, int x2, int x3, int y1, int y2, int z1, int z2, int z3, 
			BadMagic.Door direction, DoorStyle doorStyle, Material wallMaterial, Material doorMaterial) {
		
		// frame the door
		chunk.setBlocks(x1, y1, y2, z1, wallMaterial);
		chunk.setBlocks(x2, y1 + 2, y2, z2, wallMaterial);
		chunk.setBlocks(x3, y1, y2, z3, wallMaterial);
		
		// place the door
		switch (doorStyle) {
		case HOLE:
			chunk.clearBlocks(x2, y1, y1 + 2, z2);
			break;
		case WOOD:
			chunk.setDoor(x2, y1, z2, doorMaterial, direction);
			break;
		case NONE:
			break;
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
			
		case END_BRICKS:
		case BRICK:
			return Material.BRICK_STAIRS;
		
		case ENDER_STONE:
			return Material.PURPUR_STAIRS;
			
		case QUARTZ_BLOCK:
			return Material.QUARTZ_STAIRS;
			
		case CLAY:
		case COAL_BLOCK:
			return Material.BIRCH_WOOD_STAIRS;
			
		case PURPUR_BLOCK:
		case DOUBLE_STEP:
		case HARD_CLAY:
			return Material.DARK_OAK_STAIRS;
			
		case GRAVEL:
		case SOUL_SAND:
		case PURPUR_PILLAR:
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

	protected Material pickStairPlatformMaterial(Material stair) {
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
//			return Material.GRAVEL;
//			
		case JUNGLE_WOOD_STAIRS:
			return Material.WOOD;

		case SPRUCE_WOOD_STAIRS:
			return Material.STONE;

		case RED_SANDSTONE_STAIRS:
			return Material.RED_SANDSTONE;
			
		case PURPUR_STAIRS:
			return Material.PURPUR_BLOCK;

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

	protected WallStyle pickWallStyle() {
		if (wallsWE.isSame(wallsWEAlt))
			return WallStyle.SAME;
		else {
			switch (chunkOdds.getRandomInt(4)) {
			default:
			case 0:
				return WallStyle.ALTFLOORS;
			case 1:
				return WallStyle.ALTINDENT;
			case 2:
				return WallStyle.RANDOM;
			case 3:
				return WallStyle.PENTHOUSE;
			}
		}
	}

	protected RoofStyle pickRoofStyle() {
//		return RoofStyle.BOXED;
		RoofStyle[] values = RoofStyle.values();
		return values[chunkOdds.getRandomInt(values.length)];
	}
	
	protected RoofFeature pickRoofFeature() {
//		return RoofFeature.TILE;
		RoofFeature[] values = RoofFeature.values();
		return values[chunkOdds.getRandomInt(values.length)];
	}
	
	protected Material pickGlassMaterial() {
		switch (chunkOdds.getRandomInt(2)) {
		case 0:
		default:
			return Material.GLASS;
		case 1:
			if (chunkOdds.playOdds(Odds.oddsExceedinglyUnlikely))
				return Material.IRON_FENCE;
			else
				return Material.THIN_GLASS;
		}
	}
	
	protected InteriorStyle pickInteriorStyle() {
		switch (chunkOdds.getRandomInt(10)) {
		case 0:
			return InteriorStyle.RANDOM;
		case 1:
			return InteriorStyle.WALLS_ONLY;
		case 2:
			return InteriorStyle.COLUMNS_ONLY;
		case 3:
		case 4:
			return InteriorStyle.COLUMNS_OFFICES;
		default:
			return InteriorStyle.WALLS_OFFICES;
		}
	}
	
	protected CornerWallStyle pickCornerStyle() {
		switch (chunkOdds.getRandomInt(16)) {
		case 0:
			return CornerWallStyle.WOODCOLUMN;
		case 1:
			return CornerWallStyle.WOODTHENFILLED;
		case 2:
			return CornerWallStyle.STONECOLUMN;
		case 3:
			return CornerWallStyle.STONETHENFILLED;
		case 4:
			return CornerWallStyle.FILLEDTHENEMPTY;
		case 5:
		case 6:
		case 7:
		case 8:
			return CornerWallStyle.EMPTY;
		default:
			return CornerWallStyle.FILLED;
		}
	}
	
	// descendants can override this to do something special
	protected InteriorStyle getFloorsInteriorStyle(int floor) {
		return interiorStyle;
	}

}
