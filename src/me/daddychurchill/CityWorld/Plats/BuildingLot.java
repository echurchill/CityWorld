package me.daddychurchill.CityWorld.Plats;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.CurvedWallFactory;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.Direction.Stair;
import me.daddychurchill.CityWorld.Support.Direction.StairWell;
import me.daddychurchill.CityWorld.Support.MaterialFactory;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.OutsideWEWallFactory;
import me.daddychurchill.CityWorld.Support.OutsideNSWallFactory;
import me.daddychurchill.CityWorld.Support.InteriorWallFactory;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SurroundingFloors;
import me.daddychurchill.CityWorld.Support.Direction.Door;
import me.daddychurchill.CityWorld.Support.Surroundings;

import org.bukkit.Material;

public abstract class BuildingLot extends ConnectedLot {
	
	protected boolean neighborsHaveIdenticalHeights;
	protected double neighborsHaveSimilarHeightsOdds;
	protected double neighborsHaveSimilarRoundedOdds;
	protected int height; // floors up
	protected int depth; // floors down
	protected boolean needStairsUp;
	protected boolean needStairsDown;
	protected boolean rounded; // rounded corners if possible? (only if the insets match)
	protected MaterialFactory wallsWE;
	protected MaterialFactory wallsNS;
	protected MaterialFactory wallsInterior;
	protected MaterialFactory wallsCurved;
	protected int aboveFloorHeight;
	protected int basementFloorHeight;
	
	protected final static byte airId = (byte) Material.AIR.getId();
	protected final static byte antennaBaseId = (byte) Material.CLAY.getId();
	protected final static byte antennaId = (byte) Material.FENCE.getId();
	protected final static byte conditionerId = (byte) Material.DOUBLE_STEP.getId();
	protected final static byte conditionerTrimId = (byte) Material.STONE_PLATE.getId();
	protected final static byte conditionerGrillId = (byte) Material.RAILS.getId();
	protected final static byte ductId = (byte) Material.STEP.getId();
	protected final static Material tileMaterial = Material.STEP;
	
	public enum RoofStyle {FLATTOP, EDGED, PEAK, TENTNS, TENTEW};
	public enum RoofFeature {ANTENNAS, CONDITIONERS, TILE};
	protected RoofStyle roofStyle;
	protected RoofFeature roofFeature;
	protected int roofScale;
	
	public enum StairStyle {STUDIO_A, CROSSED, LANDING, CORNER};
	protected StairStyle stairStyle;
	protected Direction.Stair stairDirection;
	
	protected int navLightX = 0;
	protected int navLightY = 0;
	protected int navLightZ = 0;
	
	private final static Material fenceMaterial = Material.IRON_FENCE;
	private final static int fenceHeight = 3;
	
	public BuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		style = LotStyle.STRUCTURE;
		
		DataContext context = platmap.context;
		
		neighborsHaveIdenticalHeights = chunkOdds.playOdds(context.oddsOfIdenticalBuildingHeights);
		neighborsHaveSimilarHeightsOdds = context.oddsOfSimilarBuildingHeights;
		neighborsHaveSimilarRoundedOdds = context.oddsOfSimilarBuildingRounding;
		aboveFloorHeight = DataContext.FloorHeight;
		basementFloorHeight = DataContext.FloorHeight;
		height = 1 + chunkOdds.getRandomInt(context.maximumFloorsAbove);
		depth = 0;
		if (platmap.generator.settings.includeBasements)
			depth = 1 + chunkOdds.getRandomInt(context.maximumFloorsBelow);
		needStairsDown = true;
		needStairsUp = true;
		rounded = chunkOdds.playOdds(context.oddsOfSimilarBuildingRounding);
		roofStyle = pickRoofStyle(chunkOdds);
		roofFeature = pickRoofFeature(chunkOdds);
		roofScale = 1 + chunkOdds.getRandomInt(2);
		stairStyle = pickStairStyle(chunkOdds);
		stairDirection = pickStairDirection(chunkOdds);
		wallsWE = new OutsideWEWallFactory(chunkOdds, platmap.generator.settings.includeDecayedBuildings);
		wallsNS = new OutsideNSWallFactory(wallsWE);
		wallsCurved = new CurvedWallFactory(wallsWE);
		wallsInterior = new InteriorWallFactory(chunkOdds, platmap.generator.settings.includeDecayedBuildings);
	}

	static public Stair pickStairDirection(Odds chunkOdds) {
		switch (chunkOdds.getRandomInt(4)) {
		case 1:
			return Direction.Stair.NORTH;
		case 2:
			return Direction.Stair.SOUTH;
		case 3:
			return Direction.Stair.WEST;
		default:
			return Direction.Stair.EAST;
		}
	}

	static public StairStyle pickStairStyle(Odds chunkOdds) {
		switch (chunkOdds.getRandomInt(3)) {//4)) {
		case 1:
			return StairStyle.CORNER;
		case 2:
			return StairStyle.CROSSED;
		case 3:
			return StairStyle.STUDIO_A;
		default:
			return StairStyle.LANDING;
		}
	}

	@Override
	protected boolean isShaftableLevel(WorldGenerator generator, int y) {
		return y >= 0 && y < generator.streetLevel - basementFloorHeight * depth - 2 - 16;	
	}

	static public RoofStyle pickRoofStyle(Odds chunkOdds) {
		switch (chunkOdds.getRandomInt(5)) {
		case 1:
			return RoofStyle.EDGED;
		case 2:
			return RoofStyle.PEAK;
		case 3:
			return RoofStyle.TENTNS;
		case 4:
			return RoofStyle.TENTEW;
		default:
			return RoofStyle.FLATTOP;
		}
	}
	
	static public RoofFeature pickRoofFeature(Odds chunkOdds) {
		switch (chunkOdds.getRandomInt(3)) {
		case 1:
			return RoofFeature.ANTENNAS;
		case 2:
			return RoofFeature.CONDITIONERS;
		default:
			return RoofFeature.TILE;
		}
	}
	
	static public Material pickGlassMaterial(Odds chunkOdds) {
		switch (chunkOdds.getRandomInt(2)) {
		case 1:
			return Material.THIN_GLASS;
		default:
			return Material.GLASS;
		}
	}
	
	@Override
	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);
		
		// other bits
		if (result && relative instanceof BuildingLot) {
			BuildingLot relativebuilding = (BuildingLot) relative;

			neighborsHaveIdenticalHeights = relativebuilding.neighborsHaveIdenticalHeights;
			if (neighborsHaveIdenticalHeights || chunkOdds.playOdds(neighborsHaveSimilarHeightsOdds)) {
				height = relativebuilding.height;
				depth = relativebuilding.depth;
			}
			
			if (chunkOdds.playOdds(neighborsHaveSimilarRoundedOdds))
				rounded = relativebuilding.rounded;
			
			// any other bits
			roofStyle = relativebuilding.roofStyle;
			roofFeature = relativebuilding.roofFeature;
			roofScale = relativebuilding.roofScale;
//			stairStyle = relativebuilding.stairStyle;
			wallsWE = relativebuilding.wallsWE;
			wallsNS = relativebuilding.wallsNS;
			wallsCurved = relativebuilding.wallsCurved;
			wallsInterior = relativebuilding.wallsInterior;
			
			// do we need stairs?
			relativebuilding.needStairsDown = relativebuilding.depth > depth;
			relativebuilding.needStairsUp = relativebuilding.height > height;
		}
		return result;
	}
	
	protected SurroundingFloors getNeighboringFloorCounts(PlatMap platmap, int platX, int platZ) {
		SurroundingFloors neighborBuildings = new SurroundingFloors();
		
		// get a list of qualified neighbors
		PlatLot[][] neighborChunks = getNeighborPlatLots(platmap, platX, platZ, true);
		for (int x = 0; x < 3; x++) {
			for (int z = 0; z < 3; z++) {
				if (neighborChunks[x][z] == null) {
					neighborBuildings.floors[x][z] = 0;
				} else {
					
					// in order for this building to be connected to our building they would have to be the same type
					neighborBuildings.floors[x][z] = ((BuildingLot) neighborChunks[x][z]).height;
				}
			}
		}
		neighborBuildings.update();
		
		return neighborBuildings;
	}
	
	protected SurroundingFloors getNeighboringBasementCounts(PlatMap platmap, int platX, int platZ) {
		SurroundingFloors neighborBuildings = new SurroundingFloors();
		
		// get a list of qualified neighbors
		PlatLot[][] neighborChunks = getNeighborPlatLots(platmap, platX, platZ, true);
		for (int x = 0; x < 3; x++) {
			for (int z = 0; z < 3; z++) {
				if (neighborChunks[x][z] == null) {
					neighborBuildings.floors[x][z] = 0;
				} else {
					
					// in order for this building to be connected to our building they would have to be the same type
					neighborBuildings.floors[x][z] = ((BuildingLot) neighborChunks[x][z]).depth;
				}
			}
		}
		neighborBuildings.update();
		
		return neighborBuildings;
	}
	
	protected void drawCeilings(ByteChunk byteChunk, DataContext context, int y1, int height, 
			int insetNS, int insetEW, boolean allowRounded, 
			Material material, Surroundings heights) {
		
		// precalculate
		byte materialId = (byte) material.getId();
		int y2 = y1 + height;
		boolean stillNeedCeiling = true;
		
		// rounded and square inset and there are exactly two neighbors?
		if (allowRounded && rounded) { // already know that... && insetNS == insetEW && heights.getNeighborCount() == 2
			int innerCorner = (byteChunk.width - insetNS * 2) + insetNS;
			if (heights.toNorth()) {
				if (heights.toEast()) {
					byteChunk.setArcNorthEast(insetNS, y1, y2, materialId, true);
					if (!heights.toNorthEast()) 
						byteChunk.setArcNorthEast(innerCorner, y1, y2, airId, true);
					stillNeedCeiling = false;
				} else if (heights.toWest()) {
					byteChunk.setArcNorthWest(insetNS, y1, y2, materialId, true);
					if (!heights.toNorthWest())
						byteChunk.setArcNorthWest(innerCorner, y1, y2, airId, true);
					stillNeedCeiling = false;
				}
			} else if (heights.toSouth()) {
				if (heights.toEast()) {
					byteChunk.setArcSouthEast(insetNS, y1, y2, materialId, true);
					if (!heights.toSouthEast())
						byteChunk.setArcSouthEast(innerCorner, y1, y2, airId, true);
					stillNeedCeiling = false;
				} else if (heights.toWest()) {
					byteChunk.setArcSouthWest(insetNS, y1, y2, materialId, true);
					if (!heights.toSouthWest())
						byteChunk.setArcSouthWest(innerCorner, y1, y2, airId, true);
					stillNeedCeiling = false;
				}
			}
		}
		
		// still need to do something?
		if (stillNeedCeiling) {

			// center part
			byteChunk.setBlocks(insetNS, byteChunk.width - insetNS, y1, y2, insetEW, byteChunk.width - insetEW, materialId);
			
		}
		
		// only if we are inset
		if (insetNS > 0 || insetEW > 0) {
			
			// cardinal bits
			if (heights.toWest())
				byteChunk.setBlocks(0, insetNS, y1, y2, insetEW, byteChunk.width - insetEW, materialId);
			if (heights.toEast())
				byteChunk.setBlocks(byteChunk.width - insetNS, byteChunk.width, y1, y2, insetEW, byteChunk.width - insetEW, materialId);
			if (heights.toNorth())
				byteChunk.setBlocks(insetNS, byteChunk.width - insetNS, y1, y2, 0, insetEW, materialId);
			if (heights.toSouth())
				byteChunk.setBlocks(insetNS, byteChunk.width - insetNS, y1, y2, byteChunk.width - insetEW, byteChunk.width, materialId);

			// corner bits
			if (heights.toNorthWest())
				byteChunk.setBlocks(0, insetNS, y1, y2, 0, insetEW, materialId);
			if (heights.toSouthWest())
				byteChunk.setBlocks(0, insetNS, y1, y2, byteChunk.width - insetEW, byteChunk.width, materialId);
			if (heights.toNorthEast())
				byteChunk.setBlocks(byteChunk.width - insetNS, byteChunk.width, y1, y2, 0, insetEW, materialId);
			if (heights.toSouthEast())
				byteChunk.setBlocks(byteChunk.width - insetNS, byteChunk.width, y1, y2, byteChunk.width - insetEW, byteChunk.width, materialId);
		}
	}
	
	protected void drawExteriorWalls(ByteChunk byteChunk, DataContext context, int y1, int height, 
			int insetNS, int insetEW, boolean allowRounded, 
			Material materialWall, Material materialGlass, Surroundings heights) {
		
		// precalculate
		byte wallId = (byte) materialWall.getId();
		byte glassId = (byte) materialGlass.getId();
		int y2 = y1 + height;
		boolean stillNeedWalls = true;
		
		// rounded and square inset and there are exactly two neighbors?
		if (allowRounded && rounded) { 
			
			// hack the glass material if needed
			if (materialGlass == Material.THIN_GLASS)
				glassId = (byte) Material.GLASS.getId();
			
			// do the sides
			if (heights.toSouth()) {
				if (heights.toWest()) {
					byteChunk.setArcSouthWest(insetNS, y1, y2, wallId, glassId, wallsCurved);
					if (!heights.toSouthWest()) {
						byteChunk.setBlocks(insetNS, y1, y2, byteChunk.width - insetEW - 1, wallId, glassId, wallsCurved);
					}
					stillNeedWalls = false;
				} else if (heights.toEast()) {
					byteChunk.setArcSouthEast(insetNS, y1, y2, wallId, glassId, wallsCurved);
					if (!heights.toSouthEast()) {
						byteChunk.setBlocks(byteChunk.width - insetNS - 1, y1, y2, byteChunk.width - insetEW - 1, wallId, glassId, wallsCurved);
					}
					stillNeedWalls = false;
				}
			} else if (heights.toNorth()) {
				if (heights.toWest()) {
					byteChunk.setArcNorthWest(insetNS, y1, y2, wallId, glassId, wallsCurved);
					if (!heights.toNorthWest()) {
						byteChunk.setBlocks(insetNS, y1, y2, insetEW, wallId, glassId, wallsCurved);
					}
					stillNeedWalls = false;
				} else if (heights.toEast()) {
					byteChunk.setArcNorthEast(insetNS, y1, y2, wallId, glassId, wallsCurved);
					if (!heights.toNorthEast()) {
						byteChunk.setBlocks(byteChunk.width - insetNS - 1, y1, y2, insetEW, wallId, glassId, wallsCurved);
					}
					stillNeedWalls = false;
				}
			}
		}
		
		// still need to do something?
		if (stillNeedWalls) {
			
			// corner columns
			if (!heights.toNorthWest())
				byteChunk.setBlocks(insetNS, y1, y2, insetEW, wallId);
			if (!heights.toSouthWest())
				byteChunk.setBlocks(insetNS, y1, y2, byteChunk.width - insetEW - 1, wallId);
			if (!heights.toNorthEast())
				byteChunk.setBlocks(byteChunk.width - insetNS - 1, y1, y2, insetEW, wallId);
			if (!heights.toSouthEast())
				byteChunk.setBlocks(byteChunk.width - insetNS - 1, y1, y2, byteChunk.width - insetEW - 1, wallId);
			
			// cardinal walls
			if (!heights.toWest())
				byteChunk.setBlocks(insetNS,  insetNS + 1, y1, y2, insetEW + 1, byteChunk.width - insetEW - 1, wallId, glassId, wallsNS);
			if (!heights.toEast())
				byteChunk.setBlocks(byteChunk.width - insetNS - 1,  byteChunk.width - insetNS, y1, y2, insetEW + 1, byteChunk.width - insetEW - 1, wallId, glassId, wallsNS);
			if (!heights.toNorth())
				byteChunk.setBlocks(insetNS + 1, byteChunk.width - insetNS - 1, y1, y2, insetEW, insetEW + 1, wallId, glassId, wallsWE);
			if (!heights.toSouth())
				byteChunk.setBlocks(insetNS + 1, byteChunk.width - insetNS - 1, y1, y2, byteChunk.width - insetEW - 1, byteChunk.width - insetEW, wallId, glassId, wallsWE);
			
		}
			
		// only if there are insets
		if (insetNS > 0) {
			if (heights.toWest()) {
				if (!heights.toNorthWest())
					byteChunk.setBlocks(0, insetNS, y1, y2, insetEW, insetEW + 1, wallId, glassId, wallsWE);
				if (!heights.toSouthWest())
					byteChunk.setBlocks(0, insetNS, y1, y2, byteChunk.width - insetEW - 1, byteChunk.width - insetEW, wallId, glassId, wallsWE);
			}
			if (heights.toEast()) {
				if (!heights.toNorthEast())
					byteChunk.setBlocks(byteChunk.width - insetNS, byteChunk.width, y1, y2, insetEW, insetEW + 1, wallId, glassId, wallsWE);
				if (!heights.toSouthEast())
					byteChunk.setBlocks(byteChunk.width - insetNS, byteChunk.width, y1, y2, byteChunk.width - insetEW - 1, byteChunk.width - insetEW, wallId, glassId, wallsWE);
			}
		}
		if (insetEW > 0) {
			if (heights.toNorth()) {
				if (!heights.toNorthWest())
					byteChunk.setBlocks(insetNS, insetNS + 1, y1, y2, 0, insetEW, wallId, glassId, wallsNS);
				if (!heights.toNorthEast())
					byteChunk.setBlocks(byteChunk.width - insetNS - 1, byteChunk.width - insetNS, y1, y2, 0, insetEW, wallId, glassId, wallsNS);
			}
			if (heights.toSouth()) {
				if (!heights.toSouthWest())
					byteChunk.setBlocks(insetNS, insetNS + 1, y1, y2, byteChunk.width - insetEW, byteChunk.width, wallId, glassId, wallsNS);
				if (!heights.toSouthEast())
					byteChunk.setBlocks(byteChunk.width - insetNS - 1, byteChunk.width - insetNS, y1, y2, byteChunk.width - insetEW, byteChunk.width, wallId, glassId, wallsNS);
			}
		}
	}
	
	protected void drawInteriorWalls(RealChunk chunk, DataContext context, int y1, int height, 
			int insetNS, int insetEW, 
			Material materialWall, Material materialGlass, 
			StairWell where, Surroundings heights) {
		
		//TODO inner corners are drawing walls (NW, NE, SW, SE shouldn't draw interior walls 
		//TODO random "rooms within" these rooms
		//TODO Atrium in the middle of 2x2
		
		// only if stairs not centered
//		if (where != StairWell.CENTER) {
		
			// precalculate
			byte wallId = (byte) materialWall.getId();
			byte glassId = (byte) materialGlass.getId();
			int y2 = y1 + height;
			int x1 = heights.toWest() ? 0 : insetNS + 1;
			int x2 = chunk.width - (heights.toEast() ? 0 : (insetNS + 1));
			int z1 = heights.toNorth() ? 0 : insetEW + 1;
			int z2 = chunk.width - (heights.toSouth() ? 0 : (insetEW + 1));
			
			// NW corner
			if (where != StairWell.NORTHWEST || where != StairWell.WEST || where != StairWell.NORTH) {
				if (heights.toNorthWest()) {
					if (heights.toNorth())
						drawInteriorNSWall(chunk, 4, y1, y2, 0, wallId, glassId);
					if (heights.toWest()) {
						drawInteriorWEWall(chunk, 0, y1, y2, 4, wallId, glassId);
						drawInteriorWEDoors(chunk, 2, y1, y2, 4, materialWall);
					}
				} else {
					if (!heights.toNorth() && heights.toSouth() && heights.toWest()) {
						drawInteriorNSWall(chunk, 4, y1, y2, z1, 8, wallId, glassId);
					} else if (!heights.toWest() && heights.toEast() && heights.toNorth()) {
						drawInteriorWEWall(chunk, x1, 8, y1, y2, 4, wallId, glassId);
						//drawInteriorWEDoors(chunk, 2, y1, y2, 4, materialWall);
					}
				}
			}
			
			// NE corner
			if (where != StairWell.NORTHEAST || where != StairWell.EAST || where != StairWell.NORTH) {
				if (heights.toNorthEast()) {
					if (heights.toEast())
						drawInteriorWEWall(chunk, 8, y1, y2, 4, wallId, glassId);
					if (heights.toNorth()) {
						drawInteriorNSWall(chunk, 11, y1, y2, 0, wallId, glassId);
						drawInteriorNSDoors(chunk, 11, y1, y2, 2, materialWall);
					}
				} else {
					if (!heights.toNorth() && heights.toSouth() && heights.toEast()) {
						drawInteriorNSWall(chunk, 11, y1, y2, z1, 8, wallId, glassId);
						//drawInteriorNSDoors(chunk, 2, y1, y2, 4, materialWall);
					} else if (!heights.toEast() && heights.toWest() && heights.toNorth()) {
						drawInteriorWEWall(chunk, 8, x2, y1, y2, 4, wallId, glassId);
					}
				}
			}
			
			// SW corner
			if (where != StairWell.SOUTHWEST || where != StairWell.WEST || where != StairWell.SOUTH) {
				if (heights.toSouthWest()) {
					if (heights.toWest())
						drawInteriorWEWall(chunk, 0, y1, y2, 11, wallId, glassId);
					if (heights.toSouth()) {
						drawInteriorNSWall(chunk, 4, y1, y2, 8, wallId, glassId);
						drawInteriorNSDoors(chunk, 4, y1, y2, 9, materialWall);
					}
				} else {
					if (!heights.toSouth() && heights.toNorth() && heights.toWest()) {
						drawInteriorNSWall(chunk, 4, y1, y2, 8, z2, wallId, glassId);
						//drawInteriorNSDoors(chunk, 2, y1, y2, 4, materialWall);
					} else if (!heights.toWest() && heights.toEast() && heights.toSouth()) {
						drawInteriorWEWall(chunk, x1, 8, y1, y2, 11, wallId, glassId);
					}
				}
			}
			
			// SE corner
			if (where != StairWell.SOUTHEAST || where != StairWell.EAST || where != StairWell.SOUTH) {
				if (heights.toSouthEast()) {
					if (heights.toSouth()) 
						drawInteriorNSWall(chunk, 11, y1, y2, 8, wallId, glassId);
					if (heights.toEast()) {
						drawInteriorWEWall(chunk, 8, y1, y2, 11, wallId, glassId);
						drawInteriorWEDoors(chunk, 9, y1, y2, 11, materialWall);
					}
				} else {
					if (!heights.toSouth() && heights.toNorth() && heights.toEast()) {
						drawInteriorNSWall(chunk, 11, y1, y2, 8, z2, wallId, glassId);
					} else if (!heights.toEast() && heights.toWest() && heights.toSouth()) {
						drawInteriorWEWall(chunk, 8, x2, y1, y2, 11, wallId, glassId);
						//drawInteriorWEDoors(chunk, 2, y1, y2, 4, materialWall);
					}
				}
			}
//		}
	}
	
	private void drawInteriorNSWall(RealChunk chunk, int x, int y1, int y2, int z, byte wallId, byte glassId) {
//		chunk.setBlocks(x, x + 1, y1, y2, z, z + 8, wallId, glassId, wallsInterior);
		chunk.setBlocks(x, x + 1, y1, y1 + 1, z, z + 8, Material.GOLD_BLOCK);
	}
	
	private void drawInteriorWEWall(RealChunk chunk, int x, int y1, int y2, int z, byte wallId, byte glassId) {
//		chunk.setBlocks(x, x + 8, y1, y2, z, z + 1, wallId, glassId, wallsInterior);
		chunk.setBlocks(x, x + 8, y1, y1 + 1, z, z + 1, Material.IRON_BLOCK);
	}
	
	private void drawInteriorNSWall(RealChunk chunk, int x, int y1, int y2, int z1, int z2, byte wallId, byte glassId) {
//		chunk.setBlocks(x, x + 1, y1, y2, z1, z2, wallId, glassId, wallsInterior);
		chunk.setBlocks(x, x + 1, y1, y1 + 1, z1, z2, Material.LAPIS_BLOCK);
	}
	
	private void drawInteriorWEWall(RealChunk chunk, int x1, int x2, int y1, int y2, int z, byte wallId, byte glassId) {
//		chunk.setBlocks(x1, x2, y1, y2, z, z + 1, wallId, glassId, wallsInterior);
		chunk.setBlocks(x1, x2, y1, y1 + 1, z, z + 1, Material.DIAMOND_BLOCK);
	}
	
	private void drawInteriorNSDoors(RealChunk chunk, int x, int y1, int y2, int z, Material wall) {
		chunk.setBlock(x, y1, z + 1, Material.AIR);
		chunk.setBlock(x, y1, z + 3, Material.AIR);
//		drawDoor(chunk, x, x, x, y1, y2, z, z + 1, z + 2, Door.WESTBYNORTHWEST, wall);
//		drawDoor(chunk, x, x, x, y1, y2, z + 2, z + 3, z + 4, Door.EASTBYSOUTHEAST, wall);
	}
	
	private void drawInteriorWEDoors(RealChunk chunk, int x, int y1, int y2, int z, Material wall) {
		chunk.setBlock(x + 1, y1, z, Material.AIR);
		chunk.setBlock(x + 3, y1, z, Material.AIR);
//		drawDoor(chunk, x, x + 1, x + 2, y1, y2, z, z, z, Door.NORTHBYNORTHWEST, wall);
//		drawDoor(chunk, x + 2, x + 3, x + 4, y1, y2, z, z, z, Door.SOUTHBYSOUTHEAST, wall);
	}
	
	//TODO roof fixtures (peak, helipad, air conditioning, stairwells access, penthouse, castle trim, etc.
	protected void drawRoof(ByteChunk chunk, DataContext context, int y1, 
			int insetEW, int insetNS, boolean allowRounded, 
			Material material, Surroundings heights) {
		switch (roofStyle) {
		case PEAK:
			if (heights.getNeighborCount() == 0) { 
				for (int i = 0; i < aboveFloorHeight; i++) {
					if (i == aboveFloorHeight - 1)
						drawCeilings(chunk, context, y1 + i * roofScale, roofScale, insetEW + i, insetNS + i, allowRounded, material, heights);
					else
						drawExteriorWalls(chunk, context, y1 + i * roofScale, roofScale, insetEW + i, insetNS + i, allowRounded, material, material, heights);
				}
			} else
				drawEdgedRoof(chunk, context, y1, insetEW, insetNS, allowRounded, material, true, heights);
			break;
		case TENTNS:
			if (heights.getNeighborCount() == 0) { 
				for (int i = 0; i < aboveFloorHeight; i++) {
					if (i == aboveFloorHeight - 1)
						drawCeilings(chunk, context, y1 + i * roofScale, roofScale, insetEW + i, insetNS, allowRounded, material, heights);
					else
						drawExteriorWalls(chunk, context, y1 + i * roofScale, roofScale, insetEW + i, insetNS, allowRounded, material, material, heights);
				}
			} else
				drawEdgedRoof(chunk, context, y1, insetEW, insetNS, allowRounded, material, true, heights);
			break;
		case TENTEW:
			if (heights.getNeighborCount() == 0) { 
				for (int i = 0; i < aboveFloorHeight; i++) {
					if (i == aboveFloorHeight - 1)
						drawCeilings(chunk, context, y1 + i * roofScale, roofScale, insetEW, insetNS + i, allowRounded, material, heights);
					else
						drawExteriorWalls(chunk, context, y1 + i * roofScale, roofScale, insetEW, insetNS + i, allowRounded, material, material, heights);
				}
			} else
				drawEdgedRoof(chunk, context, y1, insetEW, insetNS, allowRounded, material, true, heights);
			break;
		case EDGED:
			drawEdgedRoof(chunk, context, y1, insetEW, insetNS, allowRounded, material, true, heights);
			break;
		case FLATTOP:
			drawEdgedRoof(chunk, context, y1, insetEW, insetNS, allowRounded, material, false, heights);
			break;
		}
	}
	
	private void drawEdgedRoof(ByteChunk chunk, DataContext context, int y1, 
			int insetEW, int insetNS, boolean allowRounded, 
			Material material, boolean doEdge, Surroundings heights) {
		
		// a little bit of edge 
		if (doEdge)
			drawExteriorWalls(chunk, context, y1, 1, insetEW, insetNS, allowRounded, material, material, heights);
		
		// add the special features
		switch (roofFeature) {
		case ANTENNAS:
			if (heights.getNeighborCount() == 0) {
				drawAntenna(chunk, 6, y1, 6);
				drawAntenna(chunk, 6, y1, 9);
				drawAntenna(chunk, 9, y1, 6);
				drawAntenna(chunk, 9, y1, 9);
				break;
			} // else fall into the conditioners block
		case CONDITIONERS:
			drawConditioner(chunk, 6, y1, 6);
			drawConditioner(chunk, 6, y1, 9);
			drawConditioner(chunk, 9, y1, 6);
			drawConditioner(chunk, 9, y1, 9);
			break;
		case TILE:
			drawCeilings(chunk, context, y1, 1, insetEW + 1, insetNS + 1, allowRounded, tileMaterial, heights);
			break;
		}
	}
	
	private void drawAntenna(ByteChunk chunk, int x, int y, int z) {
		
		if (chunkOdds.flipCoin()) {
			int y2 = y + 8 + chunkOdds.getRandomInt(8);
			chunk.setBlocks(x, y, y + 3, z, antennaBaseId);
			chunk.setBlocks(x, y + 2, y2, z, antennaId);
			if (y2 >= navLightY) {
				navLightX = x;
				navLightY = y2 - 1;
				navLightZ = z;
			}
		}
	}
	
	protected void drawNavLight(RealChunk chunk, DataContext context) {
		if (navLightY > 0)
			chunk.setTorch(navLightX, navLightY, navLightZ, context.torchMat, Direction.Torch.FLOOR);
	}
	
	private void drawConditioner(ByteChunk chunk, int x, int y, int z) {
		
		//TODO air conditioner tracks are not round
		if (chunkOdds.flipCoin()) {
			chunk.setBlock(x, y, z, conditionerId);
			chunk.setBlock(x, y + 1, z, conditionerTrimId);
			if (chunkOdds.flipCoin()) {
				chunk.setBlockIfAir(x - 1, y, z, ductId);
				chunk.setBlockIfAir(x - 2, y, z, ductId);
			}
			if (chunkOdds.flipCoin()) {
				chunk.setBlockIfAir(x + 1, y, z, ductId);
				chunk.setBlockIfAir(x + 2, y, z, ductId);
			}
			if (chunkOdds.flipCoin()) {
				chunk.setBlockIfAir(x, y, z - 1, ductId);
				chunk.setBlockIfAir(x, y, z - 2, ductId);
			}
			if (chunkOdds.flipCoin()) {
				chunk.setBlockIfAir(x, y, z + 1, ductId);
				chunk.setBlockIfAir(x, y, z + 2, ductId);
			}
			if (chunkOdds.flipCoin()) {
				chunk.setBlock(x, y, z, conditionerId);
				chunk.setBlock(x, y + 1, z, conditionerTrimId);
//			} else {
//				chunk.setBlocks(x, x + 2, y, z, z + 2, conditionerId);
//				chunk.setBlocks(x, x + 2, y + 1, z, z + 2, conditionerGrillId);
			}
		}
	}
	
	private void drawDoor(RealChunk chunk, int x1, int x2, int x3, int y1, int y2, int z1, int z2, int z3, 
			Direction.Door direction, Material wallMaterial) {
		
		// frame the door
		chunk.setBlocks(x1, y1, y2, z1, wallMaterial);
		chunk.setBlocks(x2, y1 + 2, y2, z2, wallMaterial);
		chunk.setBlocks(x3, y1, y2, z3, wallMaterial);
		
		// place the door
		chunk.setWoodenDoor(x2, y1, z2, direction);
	}
	
	protected void drawDoors(RealChunk chunk, int y1, int floorHeight, 
			int insetNS, int insetEW, StairWell where, 
			Surroundings heights, Material wallMaterial) {
		
		int center = chunk.width / 2;
		int w1 = chunk.width - 1;
		int w2 = chunk.width - 2;
		int w3 = chunk.width - 3;
		int x1 = insetNS;
		int x2 = w1 - insetNS;
		int z1 = insetEW;
		int z2 = w1 - insetEW;
		int y2 = y1 + floorHeight - 1;
		
		switch (where) {
		case NORTHWEST:
			if (chunkOdds.flipCoin())
				drawDoor(chunk, x1, x1, x1, y1, y2, 0, 1, 2, Door.WESTBYNORTHWEST, wallMaterial); 
			if (chunkOdds.flipCoin())
				drawDoor(chunk, w1, w2, w3, y1, y2, z2, z2, z2, Door.SOUTHBYSOUTHEAST, wallMaterial); 
			break;
		case NORTH:	
			if (!heights.toNorth() && chunkOdds.flipCoin())
				drawDoor(chunk, center - 1, center, center + 1, 
						y1, y2, 
						z1, z1, z1, 
						Door.NORTHBYNORTHEAST, wallMaterial);
			break;
		case NORTHEAST:
			if (chunkOdds.flipCoin())
				drawDoor(chunk, w1, w2, w3, y1, y2, z1, z1, z1, Door.NORTHBYNORTHEAST, wallMaterial); 
			if (chunkOdds.flipCoin())
				drawDoor(chunk, x1, x1, x1, y1, y2, w1, w2, w3, Door.WESTBYSOUTHWEST, wallMaterial); 
			break;
		case EAST:
			if (!heights.toEast() && chunkOdds.flipCoin())
				drawDoor(chunk, x2, x2, x2, 
						y1, y2, 
						center - 1, center, center + 1, 
						Door.EASTBYSOUTHEAST, wallMaterial);
			break;
		case SOUTHEAST:
			if (chunkOdds.flipCoin())
				drawDoor(chunk, 0, 1, 2, y1, y2, z1, z1, z1, Door.NORTHBYNORTHWEST, wallMaterial); 
			if (chunkOdds.flipCoin())
				drawDoor(chunk, x2, x2, x2, y1, y2, w1, w2, w3, Door.EASTBYSOUTHEAST, wallMaterial); 
			break;
		case SOUTH:
			if (!heights.toSouth() && chunkOdds.flipCoin())
				drawDoor(chunk, center - 1, center, center + 1, 
						y1, y2, 
						z2, z2, z2, 
						Door.SOUTHBYSOUTHWEST, wallMaterial);
			break;
		case SOUTHWEST:
			if (chunkOdds.flipCoin())
				drawDoor(chunk, x2, x2, x2, y1, y2, 0, 1, 2, Door.EASTBYNORTHEAST, wallMaterial); 
			if (chunkOdds.flipCoin())
				drawDoor(chunk, 0, 1, 2, y1, y2, z2, z2, z2, Door.SOUTHBYSOUTHWEST, wallMaterial); 
			break;
		case WEST:
			if (!heights.toWest() && chunkOdds.flipCoin())
				drawDoor(chunk, x1, x1, x1, 
						y1, y2, 
						center - 1, center, center + 1, 
						Door.WESTBYNORTHWEST, wallMaterial);
			break;
		case CENTER:
			if (!heights.toWest() && chunkOdds.flipCoin())
				drawDoor(chunk, x1, x1, x1, 
						y1, y2, 
						center - 1, center, center + 1, 
						Door.WESTBYNORTHWEST, wallMaterial);
			if (!heights.toEast() && chunkOdds.flipCoin())
				drawDoor(chunk, x2, x2, x2, 
						y1, y2, 
						center - 1, center, center + 1, 
						Door.EASTBYSOUTHEAST, wallMaterial);
			if (!heights.toNorth() && chunkOdds.flipCoin())
				drawDoor(chunk, center - 1, center, center + 1, 
						y1, y2, 
						z1, z1, z1, 
						Door.NORTHBYNORTHEAST, wallMaterial);
			if (!heights.toSouth() && chunkOdds.flipCoin())
				drawDoor(chunk, center - 1, center, center + 1, 
						y1, y2, 
						z2, z2, z2, 
						Door.SOUTHBYSOUTHWEST, wallMaterial);
			break;
		}
	}

	//TODO These might go too far by one
	static class StairAt {
		public int X = 0;
		public int Z = 0;
		
		private static final int stairWidth = 4;
		private static final int centerX = 8;
		private static final int centerZ = 8;
		
		public StairAt(RealChunk chunk, int stairLength, int insetNS, int insetEW, StairWell where) {
			switch (where) {
			case NORTHWEST:
				X = centerX - stairLength;
				Z = centerZ - stairWidth;
				break;
			case NORTH:
				X = centerX - stairLength / 2;
				Z = centerZ - stairWidth;
				break;
			case NORTHEAST:
				X = centerX;
				Z = centerZ - stairWidth;
				break;
			case EAST:
				X = centerX;
				Z = centerZ - stairWidth / 2;
				break;
			case SOUTHEAST:
				X = centerX;
				Z = centerZ;
				break;
			case SOUTH:
				X = centerX - stairLength / 2;
				Z = centerZ;
				break;
			case SOUTHWEST:
				X = centerX - stairLength;
				Z = centerZ;
				break;
			case WEST:
				X = centerX - stairLength;
				Z = centerZ - stairWidth / 2;
				break;
			case CENTER:
				X = centerX - stairLength / 2;
				Z = centerZ - stairWidth / 2;
				break;
			}
		}
	}

	public StairWell getStairWellLocation(boolean allowRounded, Surroundings heights) {
		if (heights.toNorth() && heights.toWest() && !heights.toSouth() && !heights.toEast())
			return StairWell.NORTHWEST;
		else if (heights.toNorth() && heights.toEast() && !heights.toSouth() && !heights.toWest())
			return StairWell.NORTHEAST;
		else if (heights.toSouth() && heights.toWest() && !heights.toNorth() && !heights.toEast())
			return StairWell.SOUTHWEST;
		else if (heights.toSouth() && heights.toEast() && !heights.toNorth() && !heights.toWest())
			return StairWell.SOUTHEAST;
		else if (heights.toNorth() && heights.toWest() && heights.toEast() && !heights.toSouth())
			return StairWell.NORTH;
		else if (heights.toSouth() && heights.toWest() && heights.toEast() && !heights.toNorth())
			return StairWell.SOUTH;
		else if (heights.toWest() && heights.toNorth() && heights.toSouth() && !heights.toEast())
			return StairWell.WEST;
		else if (heights.toEast() && heights.toNorth() && heights.toSouth() && !heights.toWest())
			return StairWell.EAST;
		else
			return StairWell.CENTER;
			
//		if (allowRounded && rounded) {
//			if (heights.toWest()) {
//				if (heights.toNorth()) {
//					return StairWell.SOUTHWEST;
//				} else if (heights.toSouth()) {
//					return StairWell.SOUTHEAST;
//				}
//			} else if (heights.toEast()) {
//				if (heights.toNorth()) {
//					return StairWell.NORTHWEST;
//				} else if (heights.toSouth()) {
//					return StairWell.NORTHEAST;
//				}
//			}
//		}
//		return StairWell.CENTER;
	}
	
	protected void drawStairs(RealChunk chunk, int y1, int floorHeight, 
			int insetNS, int insetEW, StairWell where, 
			Material stairMaterial, Material platformMaterial) {
		StairAt at = new StairAt(chunk, floorHeight, insetNS, insetEW, where);
		int y2 = y1 + floorHeight - 1;
		switch (stairStyle) {
		case CROSSED:
			if (floorHeight == 4) {
				switch (stairDirection) {
				case NORTH:
				case SOUTH:
					chunk.setStair(at.X + 1, y1, at.Z + 3, stairMaterial, Stair.NORTH);
					chunk.setStair(at.X + 1, y1 + 1, at.Z + 2, stairMaterial, Stair.NORTH);
					chunk.setStair(at.X + 1, y1 + 2, at.Z + 1, stairMaterial, Stair.NORTH);
					chunk.setStair(at.X + 1, y1 + 3, at.Z, stairMaterial, Stair.NORTH);
					chunk.setStair(at.X + 2, y1, at.Z, stairMaterial, Stair.SOUTH);
					chunk.setStair(at.X + 2, y1 + 1, at.Z + 1, stairMaterial, Stair.SOUTH);
					chunk.setStair(at.X + 2, y1 + 2, at.Z + 2, stairMaterial, Stair.SOUTH);
					chunk.setStair(at.X + 2, y1 + 3, at.Z + 3, stairMaterial, Stair.SOUTH);
					break;
				default:
					chunk.setStair(at.X + 3, y1, at.Z + 1, stairMaterial, Stair.WEST);
					chunk.setStair(at.X + 2, y1 + 1, at.Z + 1, stairMaterial, Stair.WEST);
					chunk.setStair(at.X + 1, y1 + 2, at.Z + 1, stairMaterial, Stair.WEST);
					chunk.setStair(at.X, y1 + 3, at.Z + 1, stairMaterial, Stair.WEST);
					chunk.setStair(at.X, y1, at.Z + 2, stairMaterial, Stair.EAST);
					chunk.setStair(at.X + 1, y1 + 1, at.Z + 2, stairMaterial, Stair.EAST);
					chunk.setStair(at.X + 2, y1 + 2, at.Z + 2, stairMaterial, Stair.EAST);
					chunk.setStair(at.X + 3, y1 + 3, at.Z + 2, stairMaterial, Stair.EAST);
					break;
				}
				
				return;
			}
			break;
		case LANDING:
			if (floorHeight == 4) {
				switch (stairDirection) {
				case NORTH:
					chunk.setStair(at.X + 1, y1, 	 at.Z, stairMaterial, Stair.SOUTH);
					chunk.setStair(at.X + 1, y1 + 1, at.Z + 1, stairMaterial, Stair.SOUTH);
					chunk.setBlock(at.X + 1, y1 + 1, at.Z + 2, platformMaterial);
					chunk.setBlock(at.X + 2, y1 + 1, at.Z + 2, platformMaterial);
					chunk.setStair(at.X + 2, y1 + 2, at.Z + 1, stairMaterial, Stair.NORTH);
					chunk.setStair(at.X + 2, y1 + 3, at.Z, stairMaterial, Stair.NORTH);
					break;
				case SOUTH:
					chunk.setStair(at.X + 2, y1, 	 at.Z + 3, stairMaterial, Stair.NORTH);
					chunk.setStair(at.X + 2, y1 + 1, at.Z + 2, stairMaterial, Stair.NORTH);
					chunk.setBlock(at.X + 2, y1 + 1, at.Z + 1, platformMaterial);
					chunk.setBlock(at.X + 1, y1 + 1, at.Z + 1, platformMaterial);
					chunk.setStair(at.X + 1, y1 + 2, at.Z + 2, stairMaterial, Stair.SOUTH);
					chunk.setStair(at.X + 1, y1 + 3, at.Z + 3, stairMaterial, Stair.SOUTH);
					break;
				case WEST:
					chunk.setStair(at.X, 	 y1, 	 at.Z + 2, stairMaterial, Stair.EAST);
					chunk.setStair(at.X + 1, y1 + 1, at.Z + 2, stairMaterial, Stair.EAST);
					chunk.setBlock(at.X + 2, y1 + 1, at.Z + 2, platformMaterial);
					chunk.setBlock(at.X + 2, y1 + 1, at.Z + 1, platformMaterial);
					chunk.setStair(at.X + 1, y1 + 2, at.Z + 1, stairMaterial, Stair.WEST);
					chunk.setStair(at.X	   , y1 + 3, at.Z + 1, stairMaterial, Stair.WEST);
					break;
				default: // EAST
					chunk.setStair(at.X + 3, y1, 	 at.Z + 1, stairMaterial, Stair.WEST);
					chunk.setStair(at.X + 2, y1 + 1, at.Z + 1, stairMaterial, Stair.WEST);
					chunk.setBlock(at.X + 1, y1 + 1, at.Z + 1, platformMaterial);
					chunk.setBlock(at.X + 1, y1 + 1, at.Z + 2, platformMaterial);
					chunk.setStair(at.X + 2, y1 + 2, at.Z + 2, stairMaterial, Stair.EAST);
					chunk.setStair(at.X + 3, y1 + 3, at.Z + 2, stairMaterial, Stair.EAST);
					break;
				}

				return;
			}	
			break;
		case CORNER:
			if (floorHeight == 4) {
				switch (stairDirection) {
				case NORTH:
					chunk.setStair(at.X + 3,     y1, at.Z + 1, stairMaterial, Stair.WEST);
					chunk.setStair(at.X + 2, y1 + 1, at.Z + 1, stairMaterial, Stair.WEST);
					chunk.setBlock(at.X + 1, y1 + 1, at.Z + 1, platformMaterial);
					chunk.setStair(at.X + 1, y1 + 2, at.Z + 2, stairMaterial, Stair.SOUTH);
					chunk.setStair(at.X + 1, y1 + 3, at.Z + 3, stairMaterial, Stair.SOUTH);
					break;
				case SOUTH:
					chunk.setStair(at.X,     y1,     at.Z + 2, stairMaterial, Stair.EAST);
					chunk.setStair(at.X + 1, y1 + 1, at.Z + 2, stairMaterial, Stair.EAST);
					chunk.setBlock(at.X + 2, y1 + 1, at.Z + 2, platformMaterial);
					chunk.setStair(at.X + 2, y1 + 2, at.Z + 1, stairMaterial, Stair.NORTH);
					chunk.setStair(at.X + 2, y1 + 3, at.Z,     stairMaterial, Stair.NORTH);
					break;
				case WEST:
					chunk.setStair(at.X + 1,     y1, at.Z,     stairMaterial, Stair.SOUTH);
					chunk.setStair(at.X + 1, y1 + 1, at.Z + 1, stairMaterial, Stair.SOUTH);
					chunk.setBlock(at.X + 1, y1 + 1, at.Z + 2, platformMaterial);
					chunk.setStair(at.X + 2, y1 + 2, at.Z + 2, stairMaterial, Stair.EAST);
					chunk.setStair(at.X + 3, y1 + 3, at.Z + 2, stairMaterial, Stair.EAST);
					break;
				default:
					chunk.setStair(at.X + 2,     y1, at.Z + 3, stairMaterial, Stair.NORTH);
					chunk.setStair(at.X + 2, y1 + 1, at.Z + 2, stairMaterial, Stair.NORTH);
					chunk.setBlock(at.X + 2, y1 + 1, at.Z + 1, platformMaterial);
					chunk.setStair(at.X + 1, y1 + 2, at.Z + 1, stairMaterial, Stair.WEST);
					chunk.setStair(at.X,     y1 + 3, at.Z + 1, stairMaterial, Stair.WEST);
					break;
				}

				return;
			}	
			break;
		case STUDIO_A:
			break;
		}
		
		// now take really take care of the simple case
		for (int i = 0; i < floorHeight; i++) {
			chunk.setBlock(at.X + i, y2, at.Z + 1, Material.AIR);
			chunk.setBlock(at.X + i, y2, at.Z + 2, Material.AIR);
			chunk.setBlock(at.X + i, y1 + i, at.Z + 1, stairMaterial);
			chunk.setBlock(at.X + i, y1 + i, at.Z + 2, stairMaterial);
		}
	}

	protected void drawStairsWalls(RealChunk chunk, int y1, int floorHeight, 
			int insetNS, int insetEW, StairWell where, 
			Material wallMaterial, boolean isTopFloor, boolean isBottomFloor) {
		StairAt at = new StairAt(chunk, floorHeight, insetNS, insetEW, where);
		int y2 = y1 + floorHeight - 1;
		int yClear = y2 + (isTopFloor ? 0 : 1);
		switch (stairStyle) {
		case CROSSED:
			if (floorHeight == 4) {
				switch (stairDirection) {
				case NORTH:
				case SOUTH:
					chunk.setBlocks(at.X + 1, at.X + 3, y1, yClear, at.Z, at.Z + 4, Material.AIR);
					chunk.setBlocks(at.X, at.X + 1, y1, y2, at.Z, at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X + 3, at.X + 4, y1, y2, at.Z, at.Z + 4, wallMaterial);
//					chunk.setBlock(at.X + 1, y2 - 1, at.Z, wallMaterial);
//					chunk.setBlock(at.X + 2, y2 - 1, at.Z + 3, wallMaterial);
					if (isTopFloor) {
						chunk.setBlocks(at.X + 2, y1, y2, at.Z, wallMaterial);
						chunk.setBlocks(at.X + 1, y1, y2, at.Z + 3, wallMaterial);
					}
					break;
				default: // WEST/EAST
					chunk.setBlocks(at.X, at.X + 4, y1, yClear, at.Z + 1, at.Z + 3, Material.AIR);
					chunk.setBlocks(at.X, at.X + 4, y1, y2, at.Z, at.Z + 1, wallMaterial);
					chunk.setBlocks(at.X, at.X + 4, y1, y2, at.Z + 3, at.Z + 4, wallMaterial);
//					chunk.setBlock(at.X, y2 - 1, at.Z + 1, wallMaterial);
//					chunk.setBlock(at.X + 3, y2 - 1, at.Z + 2, wallMaterial);
					if (isTopFloor) {
						chunk.setBlocks(at.X, y1, y2, at.Z + 2, wallMaterial);
						chunk.setBlocks(at.X + 3, y1, y2, at.Z + 1, wallMaterial);
					}
					break;
				}
				
				return;
			}
			break;
		case LANDING:
			if (floorHeight == 4) {
				switch (stairDirection) {
				case NORTH:
					chunk.setBlocks(at.X + 1, at.X + 3, y1, yClear, at.Z,     at.Z + 3, Material.AIR);
					chunk.setBlocks(at.X,     at.X + 1, y1, y2,     at.Z,     at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X + 3, at.X + 4, y1, y2,     at.Z,     at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X + 1, at.X + 3, y1, y2,     at.Z + 3, at.Z + 4, wallMaterial);
//					chunk.setBlock(at.X + 2, y2 - 1, at.Z, wallMaterial);
					if (isTopFloor)
						chunk.setBlocks(at.X + 1, y1, y2, at.Z, wallMaterial);
					
					break;
				case SOUTH:
					chunk.setBlocks(at.X + 1, at.X + 3, y1, yClear, at.Z + 1, at.Z + 4, Material.AIR);
					chunk.setBlocks(at.X,     at.X + 1, y1, y2, 	at.Z,     at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X + 3, at.X + 4, y1, y2, 	at.Z,     at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X + 1, at.X + 3, y1, y2, 	at.Z,     at.Z + 1, wallMaterial);
//					chunk.setBlock(at.X + 1, y2 - 1, at.Z + 3, wallMaterial);
					if (isTopFloor)
						chunk.setBlocks(at.X + 2, y1, y2, at.Z + 3, wallMaterial);
					break;
				case WEST:
					chunk.setBlocks(at.X,     at.X + 3, y1, yClear, at.Z + 1, at.Z + 3, Material.AIR);
					chunk.setBlocks(at.X,     at.X + 4, y1, y2, 	at.Z,     at.Z + 1, wallMaterial);
					chunk.setBlocks(at.X,     at.X + 4, y1, y2, 	at.Z + 3, at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X + 3, at.X + 4, y1, y2, 	at.Z + 1, at.Z + 3, wallMaterial);
//					chunk.setBlock(at.X, y2 - 1, at.Z + 1, wallMaterial);
					if (isTopFloor)
						chunk.setBlocks(at.X, y1, y2, at.Z + 2, wallMaterial);
					break;
				default: // EAST
					chunk.setBlocks(at.X + 1, at.X + 4, y1, yClear, at.Z + 1, at.Z + 3, Material.AIR);
					chunk.setBlocks(at.X,     at.X + 4, y1, y2, 	at.Z,     at.Z + 1, wallMaterial);
					chunk.setBlocks(at.X,     at.X + 4, y1, y2, 	at.Z + 3, at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X,     at.X + 1, y1, y2, 	at.Z + 1, at.Z + 3, wallMaterial);
//					chunk.setBlock(at.X + 3, y2 - 1, at.Z + 2, wallMaterial);
					if (isTopFloor)
						chunk.setBlocks(at.X + 3, y1, y2, at.Z + 1, wallMaterial);
					break;
				}

				return;
			}	
			break;
		case CORNER:
			if (floorHeight == 4) {
				chunk.setBlocks(at.X, at.X + 4, y1, y2, at.Z, at.Z + 4, wallMaterial);
				switch (stairDirection) {
				case NORTH:
					chunk.setBlocks(at.X + 1, at.X + 4, y1, yClear, at.Z + 1, at.Z + 2, Material.AIR);
					chunk.setBlocks(at.X + 1, at.X + 2, y1, yClear, at.Z + 2, at.Z + 4, Material.AIR);
//					chunk.setBlock(at.X + 1, y2 - 1, at.Z + 3, wallMaterial);
					if (isTopFloor)
						chunk.setBlocks(at.X + 3, y1, y2, at.Z + 1, wallMaterial);
					break;
				case SOUTH:
					chunk.setBlocks(at.X,     at.X + 3, y1, yClear, at.Z + 2, at.Z + 3, Material.AIR);
					chunk.setBlocks(at.X + 2, at.X + 3, y1, yClear, at.Z,     at.Z + 2, Material.AIR);
//					chunk.setBlock(at.X + 2, y2 - 1, at.Z, wallMaterial);
					if (isTopFloor)
						chunk.setBlocks(at.X, y1, y2, at.Z + 2, wallMaterial);
					break;
				case WEST:
					chunk.setBlocks(at.X + 1, at.X + 2, y1, yClear, at.Z,     at.Z + 3, Material.AIR);
					chunk.setBlocks(at.X + 2, at.X + 4, y1, yClear, at.Z + 2, at.Z + 3, Material.AIR);
//					chunk.setBlock(at.X + 3, y2 - 1, at.Z + 2, wallMaterial);
					if (isTopFloor)
						chunk.setBlocks(at.X + 1, y1, y2, at.Z, wallMaterial);
					break;
				default: // EAST
					chunk.setBlocks(at.X,     at.X + 3, y1, yClear, at.Z + 1, at.Z + 2, Material.AIR);
					chunk.setBlocks(at.X + 2, at.X + 3, y1, yClear, at.Z + 2, at.Z + 4, Material.AIR);
//					chunk.setBlock(at.X, y2 - 1, at.Z + 1, wallMaterial);
					if (isTopFloor)
						chunk.setBlocks(at.X + 2, y1, y2, at.Z + 3, wallMaterial);
					break;
				}
				return;
			}	
			break;
		case STUDIO_A:
			break;
		}
		
		chunk.setBlocks(at.X, at.X + floorHeight, y1, y2, at.Z, at.Z + 1, wallMaterial);
		chunk.setBlocks(at.X, at.X + floorHeight, y1, y2, at.Z + 3, at.Z + 4, wallMaterial);
		if (isTopFloor)
			chunk.setBlocks(at.X - 1, at.X, y1, y2, at.Z, at.Z + 4, wallMaterial);
		if (isBottomFloor)
			chunk.setBlocks(at.X + floorHeight, at.X + floorHeight + 1, y1, y2, at.Z, at.Z + 4, wallMaterial);
	};

	protected void drawOtherPillars(RealChunk chunk, int y1, int floorHeight,
			StairWell where, Material wallMaterial) {
		int y2 = y1 + floorHeight - 1;
		if (where != StairWell.SOUTHWEST)
			chunk.setBlocks(3, 5, y1, y2 , 3, 5, wallMaterial);
		if (where != StairWell.SOUTHEAST)
			chunk.setBlocks(3, 5, y1, y2, 11, 13, wallMaterial);
		if (where != StairWell.NORTHWEST)
			chunk.setBlocks(11, 13, y1, y2, 3, 5, wallMaterial);
		if (where != StairWell.NORTHEAST)
			chunk.setBlocks(11, 13, y1, y2, 11, 13, wallMaterial);
	}

	protected void drawFence(WorldGenerator generator, ByteChunk chunk, DataContext context, int inset, int y1, Surroundings neighbors) {
		
		// actual fence
		drawExteriorWalls(chunk, context, y1, fenceHeight, inset, inset, false,
				fenceMaterial, fenceMaterial, neighbors);
		
		// holes in fence
		int i = 4 + chunkOdds.getRandomInt(chunk.width / 2);
		int y2 = y1 + fenceHeight;
		if (chunkOdds.flipCoin() && !neighbors.toWest())
			chunk.setBlocks(inset, y1, y2, i, airId);
		if (chunkOdds.flipCoin() && !neighbors.toEast())
			chunk.setBlocks(chunk.width - 1 - inset, y1, y2, i, airId);
		if (chunkOdds.flipCoin() && !neighbors.toNorth())
			chunk.setBlocks(i, y1, y2, inset, airId);
		if (chunkOdds.flipCoin() && !neighbors.toSouth())
			chunk.setBlocks(i, y1, y2, chunk.width - 1 - inset, airId);
	}
}
