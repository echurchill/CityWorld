package me.daddychurchill.CityWorld.Plats;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected.Half;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Factories.MaterialFactory;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.Populators.EmptyWithNothing;
import me.daddychurchill.CityWorld.Support.CornerBlocks;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.SupportBlocks;
import me.daddychurchill.CityWorld.Support.SurroundingFloors;
import me.daddychurchill.CityWorld.Support.Surroundings;

public abstract class BuildingLot extends ConnectedLot {

	private static final RoomProvider contentsNothing = new EmptyWithNothing();
	private final static CornerBlocks cornerBlocks = new CornerBlocks();

	protected boolean neighborsHaveIdenticalHeights;
	private final double neighborsHaveSimilarHeightsOdds;
	final double neighborsHaveSimilarRoundedOdds;
	protected int height; // floors up
	protected int depth; // floors down
	protected int aboveFloorHeight;
	protected final int basementFloorHeight;
	protected boolean needStairsUp;
	protected boolean needStairsDown;

	final static Material antennaBase = Material.CLAY;
	final static Material antenna = Material.SPRUCE_FENCE;
	final static Material conditioner = Material.STONE;
	final static Material conditionerTrim = Material.STONE_PRESSURE_PLATE;
	protected final static Material conditionerGrill = Material.RAIL;
	final static Material duct = Material.STONE_SLAB;
	final static Material tileMaterial = Material.STONE_SLAB;

	public enum StairStyle {
		STUDIO_A, CROSSED, LANDING, CORNER
	}

	private final StairStyle stairStyle;
	private final StairFacing stairDirection;

	public enum StairWell {
		NONE, CENTER, NORTHWEST, NORTHEAST, SOUTHWEST, SOUTHEAST, NORTH, SOUTH, WEST, EAST
	}

	public enum StairFacing {
		NORTH, SOUTH, WEST, EAST
	}

	int cornerLotStyle;

	protected RoomProvider roomProviderForFloor(CityWorldGenerator generator, SupportBlocks chunk, int floor, int floorY) {
		return contentsNothing;
	}

	protected BuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
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

		stairStyle = pickStairStyle();
		stairDirection = pickStairDirection();
		needStairsDown = true;
		needStairsUp = true;

		cornerLotStyle = cornerBlocks.pickCornerStyle(chunkOdds);

		if (platmap.generator.getSettings().includeBasements)
			depth = 1 + chunkOdds.getRandomInt(context.maximumFloorsBelow);
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

			// do we need stairs?
			relativebuilding.needStairsDown = relativebuilding.depth > depth;
			relativebuilding.needStairsUp = relativebuilding.height > height;

			// round style?
			cornerLotStyle = relativebuilding.cornerLotStyle;
		}
		return result;
	}

	@Override
	public boolean isValidStrataY(CityWorldGenerator generator, int blockX, int blockY, int blockZ) {
		return blockY < generator.streetLevel - basementFloorHeight * depth;
	}

	@Override
	protected boolean isShaftableLevel(CityWorldGenerator generator, int blockY) {
		return blockY >= 0 && blockY < generator.streetLevel - basementFloorHeight * depth - 2 - 16;
	}

	private StairFacing pickStairDirection() {
		return StairFacing.values()[chunkOdds.getRandomInt(StairFacing.values().length)];
	}

	private StairStyle pickStairStyle() {
		return StairStyle.values()[chunkOdds.getRandomInt(StairStyle.values().length)];
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

					// in order for this building to be connected to our building they would have to
					// be the same type
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

					// in order for this building to be connected to our building they would have to
					// be the same type
					neighborBuildings.floors[x][z] = ((BuildingLot) neighborChunks[x][z]).depth;
				}
			}
		}
		neighborBuildings.update();

		return neighborBuildings;
	}

	static class StairAt {
		int X = 0;
		int Z = 0;

		private static final int stairWidth = 4;
		private static final int centerX = 8;
		private static final int centerZ = 8;

		StairAt(RealBlocks chunk, int stairLength, StairWell where) {
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
			case NONE:
				X = centerX - stairLength / 2;
				Z = centerZ - stairWidth / 2;
				break;
			}
		}
	}

	StairWell getStairWellLocation(boolean allowRounded, Surroundings heights) {
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
	}

	protected void drawStairs(CityWorldGenerator generator, RealBlocks chunk, int y1, int floorHeight, StairWell where,
			Material stairMaterial, Material platformMaterial) {
		StairAt at = new StairAt(chunk, floorHeight, where);
		switch (stairStyle) {
		case CROSSED:
			if (floorHeight == 4) {
				switch (stairDirection) {
				case NORTH:
				case SOUTH:
					chunk.setBlock(at.X + 1, y1, at.Z + 3, stairMaterial, BlockFace.NORTH);
					chunk.setBlock(at.X + 1, y1 + 1, at.Z + 2, stairMaterial, BlockFace.NORTH);
					chunk.setBlock(at.X + 1, y1 + 2, at.Z + 1, stairMaterial, BlockFace.NORTH);
					chunk.setBlock(at.X + 1, y1 + 3, at.Z, stairMaterial, BlockFace.NORTH);
					chunk.setBlock(at.X + 2, y1, at.Z, stairMaterial, BlockFace.SOUTH);
					chunk.setBlock(at.X + 2, y1 + 1, at.Z + 1, stairMaterial, BlockFace.SOUTH);
					chunk.setBlock(at.X + 2, y1 + 2, at.Z + 2, stairMaterial, BlockFace.SOUTH);
					chunk.setBlock(at.X + 2, y1 + 3, at.Z + 3, stairMaterial, BlockFace.SOUTH);
					break;
				case WEST:
				case EAST:
					chunk.setBlock(at.X + 3, y1, at.Z + 1, stairMaterial, BlockFace.WEST);
					chunk.setBlock(at.X + 2, y1 + 1, at.Z + 1, stairMaterial, BlockFace.WEST);
					chunk.setBlock(at.X + 1, y1 + 2, at.Z + 1, stairMaterial, BlockFace.WEST);
					chunk.setBlock(at.X, y1 + 3, at.Z + 1, stairMaterial, BlockFace.WEST);
					chunk.setBlock(at.X, y1, at.Z + 2, stairMaterial, BlockFace.EAST);
					chunk.setBlock(at.X + 1, y1 + 1, at.Z + 2, stairMaterial, BlockFace.EAST);
					chunk.setBlock(at.X + 2, y1 + 2, at.Z + 2, stairMaterial, BlockFace.EAST);
					chunk.setBlock(at.X + 3, y1 + 3, at.Z + 2, stairMaterial, BlockFace.EAST);
					break;
				}

				return;
			}
			break;
		case LANDING:
			if (floorHeight == 4) {
				switch (stairDirection) {
				case NORTH:
					chunk.setBlock(at.X + 1, y1, at.Z, stairMaterial, BlockFace.SOUTH);
					chunk.setBlock(at.X + 1, y1 + 1, at.Z + 1, stairMaterial, BlockFace.SOUTH);
					chunk.setBlock(at.X + 1, y1 + 1, at.Z + 2, platformMaterial);
					chunk.setBlock(at.X + 2, y1 + 1, at.Z + 2, platformMaterial);
					chunk.setBlock(at.X + 2, y1 + 2, at.Z + 1, stairMaterial, BlockFace.NORTH);
					chunk.setBlock(at.X + 2, y1 + 3, at.Z, stairMaterial, BlockFace.NORTH);
					break;
				case SOUTH:
					chunk.setBlock(at.X + 2, y1, at.Z + 3, stairMaterial, BlockFace.NORTH);
					chunk.setBlock(at.X + 2, y1 + 1, at.Z + 2, stairMaterial, BlockFace.NORTH);
					chunk.setBlock(at.X + 2, y1 + 1, at.Z + 1, platformMaterial);
					chunk.setBlock(at.X + 1, y1 + 1, at.Z + 1, platformMaterial);
					chunk.setBlock(at.X + 1, y1 + 2, at.Z + 2, stairMaterial, BlockFace.SOUTH);
					chunk.setBlock(at.X + 1, y1 + 3, at.Z + 3, stairMaterial, BlockFace.SOUTH);
					break;
				case WEST:
					chunk.setBlock(at.X, y1, at.Z + 2, stairMaterial, BlockFace.EAST);
					chunk.setBlock(at.X + 1, y1 + 1, at.Z + 2, stairMaterial, BlockFace.EAST);
					chunk.setBlock(at.X + 2, y1 + 1, at.Z + 2, platformMaterial);
					chunk.setBlock(at.X + 2, y1 + 1, at.Z + 1, platformMaterial);
					chunk.setBlock(at.X + 1, y1 + 2, at.Z + 1, stairMaterial, BlockFace.WEST);
					chunk.setBlock(at.X, y1 + 3, at.Z + 1, stairMaterial, BlockFace.WEST);
					break;
				case EAST:
					chunk.setBlock(at.X + 3, y1, at.Z + 1, stairMaterial, BlockFace.WEST);
					chunk.setBlock(at.X + 2, y1 + 1, at.Z + 1, stairMaterial, BlockFace.WEST);
					chunk.setBlock(at.X + 1, y1 + 1, at.Z + 1, platformMaterial);
					chunk.setBlock(at.X + 1, y1 + 1, at.Z + 2, platformMaterial);
					chunk.setBlock(at.X + 2, y1 + 2, at.Z + 2, stairMaterial, BlockFace.EAST);
					chunk.setBlock(at.X + 3, y1 + 3, at.Z + 2, stairMaterial, BlockFace.EAST);
					break;
				}

				return;
			}
			break;
		case CORNER:
			if (floorHeight == 4) {
				switch (stairDirection) {
				case NORTH:
					chunk.setBlock(at.X + 3, y1, at.Z + 1, stairMaterial, BlockFace.WEST);
					chunk.setBlock(at.X + 2, y1 + 1, at.Z + 1, stairMaterial, BlockFace.WEST);
					chunk.setBlock(at.X + 1, y1 + 1, at.Z + 1, platformMaterial);
					chunk.setBlock(at.X + 1, y1 + 2, at.Z + 2, stairMaterial, BlockFace.SOUTH);
					chunk.setBlock(at.X + 1, y1 + 3, at.Z + 3, stairMaterial, BlockFace.SOUTH);
					break;
				case SOUTH:
					chunk.setBlock(at.X, y1, at.Z + 2, stairMaterial, BlockFace.EAST);
					chunk.setBlock(at.X + 1, y1 + 1, at.Z + 2, stairMaterial, BlockFace.EAST);
					chunk.setBlock(at.X + 2, y1 + 1, at.Z + 2, platformMaterial);
					chunk.setBlock(at.X + 2, y1 + 2, at.Z + 1, stairMaterial, BlockFace.NORTH);
					chunk.setBlock(at.X + 2, y1 + 3, at.Z, stairMaterial, BlockFace.NORTH);
					break;
				case WEST:
					chunk.setBlock(at.X + 1, y1, at.Z, stairMaterial, BlockFace.SOUTH);
					chunk.setBlock(at.X + 1, y1 + 1, at.Z + 1, stairMaterial, BlockFace.SOUTH);
					chunk.setBlock(at.X + 1, y1 + 1, at.Z + 2, platformMaterial);
					chunk.setBlock(at.X + 2, y1 + 2, at.Z + 2, stairMaterial, BlockFace.EAST);
					chunk.setBlock(at.X + 3, y1 + 3, at.Z + 2, stairMaterial, BlockFace.EAST);
					break;
				case EAST:
					chunk.setBlock(at.X + 2, y1, at.Z + 3, stairMaterial, BlockFace.NORTH);
					chunk.setBlock(at.X + 2, y1 + 1, at.Z + 2, stairMaterial, BlockFace.NORTH);
					chunk.setBlock(at.X + 2, y1 + 1, at.Z + 1, platformMaterial);
					chunk.setBlock(at.X + 1, y1 + 2, at.Z + 1, stairMaterial, BlockFace.WEST);
					chunk.setBlock(at.X, y1 + 3, at.Z + 1, stairMaterial, BlockFace.WEST);
					break;
				}

				return;
			}
			break;
		case STUDIO_A:
			// fall through to the next generator, the one who can deal with variable
			// heights
			break;
		}

		// Studio_A
		int y2 = y1 + floorHeight - 1;
		switch (stairDirection) {
		case NORTH:
			for (int i = 0; i < floorHeight; i++) {
				emptyBlock(generator, chunk, at.X + 1, y2, at.Z + i);
				emptyBlock(generator, chunk, at.X + 2, y2, at.Z + i);
				chunk.setBlock(at.X + 1, y1 + i, at.Z + i, stairMaterial, BlockFace.SOUTH);
				chunk.setBlock(at.X + 2, y1 + i, at.Z + i, stairMaterial, BlockFace.SOUTH);
			}
			break;
		case SOUTH:
			for (int i = 0; i < floorHeight; i++) {
				emptyBlock(generator, chunk, at.X + 1, y2, at.Z + i);
				emptyBlock(generator, chunk, at.X + 2, y2, at.Z + i);
				chunk.setBlock(at.X + 1, y1 + i, at.Z + floorHeight - i - 1, stairMaterial, BlockFace.NORTH);
				chunk.setBlock(at.X + 2, y1 + i, at.Z + floorHeight - i - 1, stairMaterial, BlockFace.NORTH);
			}
			break;
		case WEST:
			for (int i = 0; i < floorHeight; i++) {
				emptyBlock(generator, chunk, at.X + i, y2, at.Z + 1);
				emptyBlock(generator, chunk, at.X + i, y2, at.Z + 2);
				chunk.setBlock(at.X + i, y1 + i, at.Z + 1, stairMaterial, BlockFace.EAST);
				chunk.setBlock(at.X + i, y1 + i, at.Z + 2, stairMaterial, BlockFace.EAST);
			}
			break;
		case EAST:
			for (int i = 0; i < floorHeight; i++) {
				emptyBlock(generator, chunk, at.X + i, y2, at.Z + 1);
				emptyBlock(generator, chunk, at.X + i, y2, at.Z + 2);
				chunk.setBlock(at.X + floorHeight - i - 1, y1 + i, at.Z + 1, stairMaterial, BlockFace.WEST);
				chunk.setBlock(at.X + floorHeight - i - 1, y1 + i, at.Z + 2, stairMaterial, BlockFace.WEST);
			}
			break;
		}
	}

	private void emptyBlock(CityWorldGenerator generator, RealBlocks chunk, int x, int y, int z) {
		chunk.airoutBlock(generator, x, y, z);
	}

	private void emptyBlocks(CityWorldGenerator generator, RealBlocks chunk, int x1, int x2, int y1, int y2, int z1,
			int z2) {
		for (int y = y1; y < y2; y++) {
			chunk.airoutBlocks(generator, x1, x2, y, y + 1, z1, z2);
		}
	}

	protected void drawStairsWalls(CityWorldGenerator generator, RealBlocks chunk, int y1, int floorHeight,
			StairWell where, Material wallMaterial, boolean isTopFloor, boolean isBottomFloor) {
		StairAt at = new StairAt(chunk, floorHeight, where);
		int y2 = y1 + floorHeight - 1;
		int yClear = y2 + (isTopFloor ? 0 : 1);
		switch (stairStyle) {
		case CROSSED:
			if (floorHeight == 4) {
				switch (stairDirection) {
				case NORTH:
				case SOUTH:
					emptyBlocks(generator, chunk, at.X + 1, at.X + 3, y1, yClear, at.Z, at.Z + 4);
					chunk.setBlocks(at.X, at.X + 1, y1, y2, at.Z, at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X + 3, at.X + 4, y1, y2, at.Z, at.Z + 4, wallMaterial);
					if (isTopFloor) {
						chunk.setBlock(at.X + 2, y1 - 1, at.Z, Material.BIRCH_TRAPDOOR, BlockFace.SOUTH, Half.TOP);
						chunk.setBlock(at.X + 1, y1 - 1, at.Z + 3, Material.BIRCH_TRAPDOOR, BlockFace.NORTH, Half.TOP);
						chunk.setBlocks(at.X + 2, y1, y2, at.Z, wallMaterial);
						chunk.setBlocks(at.X + 1, y1, y2, at.Z + 3, wallMaterial);
					}
					break;
				case WEST:
				case EAST:
					emptyBlocks(generator, chunk, at.X, at.X + 4, y1, yClear, at.Z + 1, at.Z + 3);
					chunk.setBlocks(at.X, at.X + 4, y1, y2, at.Z, at.Z + 1, wallMaterial);
					chunk.setBlocks(at.X, at.X + 4, y1, y2, at.Z + 3, at.Z + 4, wallMaterial);
					if (isTopFloor) {
						chunk.setBlock(at.X, y1 - 1, at.Z + 2, Material.BIRCH_TRAPDOOR, BlockFace.EAST, Half.TOP);
						chunk.setBlock(at.X + 3, y1 - 1, at.Z + 1, Material.BIRCH_TRAPDOOR, BlockFace.WEST, Half.TOP);
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
					emptyBlocks(generator, chunk, at.X + 1, at.X + 3, y1, yClear, at.Z, at.Z + 3);
					chunk.setBlocks(at.X, at.X + 1, y1, y2, at.Z, at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X + 3, at.X + 4, y1, y2, at.Z, at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X + 1, at.X + 3, y1, y2, at.Z + 3, at.Z + 4, wallMaterial);
					if (isTopFloor) {
						chunk.setBlock(at.X + 1, y1 - 1, at.Z, Material.BIRCH_TRAPDOOR, BlockFace.SOUTH, Half.TOP);
						chunk.setBlocks(at.X + 1, y1, y2, at.Z, wallMaterial);
					}
					break;
				case SOUTH:
					emptyBlocks(generator, chunk, at.X + 1, at.X + 3, y1, yClear, at.Z + 1, at.Z + 4);
					chunk.setBlocks(at.X, at.X + 1, y1, y2, at.Z, at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X + 3, at.X + 4, y1, y2, at.Z, at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X + 1, at.X + 3, y1, y2, at.Z, at.Z + 1, wallMaterial);
					if (isTopFloor) {
						chunk.setBlock(at.X + 2, y1 - 1, at.Z + 3, Material.BIRCH_TRAPDOOR, BlockFace.NORTH, Half.TOP);
						chunk.setBlocks(at.X + 2, y1, y2, at.Z + 3, wallMaterial);
					}
					break;
				case WEST:
					emptyBlocks(generator, chunk, at.X, at.X + 3, y1, yClear, at.Z + 1, at.Z + 3);
					chunk.setBlocks(at.X, at.X + 4, y1, y2, at.Z, at.Z + 1, wallMaterial);
					chunk.setBlocks(at.X, at.X + 4, y1, y2, at.Z + 3, at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X + 3, at.X + 4, y1, y2, at.Z + 1, at.Z + 3, wallMaterial);
					if (isTopFloor) {
						chunk.setBlock(at.X, y1 - 1, at.Z + 2, Material.BIRCH_TRAPDOOR, BlockFace.EAST, Half.TOP);
						chunk.setBlocks(at.X, y1, y2, at.Z + 2, wallMaterial);
					}
					break;
				case EAST:
					emptyBlocks(generator, chunk, at.X + 1, at.X + 4, y1, yClear, at.Z + 1, at.Z + 3);
					chunk.setBlocks(at.X, at.X + 4, y1, y2, at.Z, at.Z + 1, wallMaterial);
					chunk.setBlocks(at.X, at.X + 4, y1, y2, at.Z + 3, at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X, at.X + 1, y1, y2, at.Z + 1, at.Z + 3, wallMaterial);
					if (isTopFloor) {
						chunk.setBlock(at.X + 3, y1 - 1, at.Z + 1, Material.BIRCH_TRAPDOOR, BlockFace.WEST, Half.TOP);
						chunk.setBlocks(at.X + 3, y1, y2, at.Z + 1, wallMaterial);
					}
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
					emptyBlocks(generator, chunk, at.X + 1, at.X + 4, y1, yClear, at.Z + 1, at.Z + 2);
					emptyBlocks(generator, chunk, at.X + 1, at.X + 2, y1, yClear, at.Z + 2, at.Z + 4);
					if (isTopFloor) {
						chunk.setBlock(at.X + 3, y1 - 1, at.Z + 1, Material.BIRCH_TRAPDOOR, BlockFace.WEST, Half.TOP);
						chunk.setBlocks(at.X + 3, y1, y2, at.Z + 1, wallMaterial);
					}
					break;
				case SOUTH:
					emptyBlocks(generator, chunk, at.X, at.X + 3, y1, yClear, at.Z + 2, at.Z + 3);
					emptyBlocks(generator, chunk, at.X + 2, at.X + 3, y1, yClear, at.Z, at.Z + 2);
					if (isTopFloor) {
						chunk.setBlock(at.X, y1 - 1, at.Z + 2, Material.BIRCH_TRAPDOOR, BlockFace.EAST, Half.TOP);
						chunk.setBlocks(at.X, y1, y2, at.Z + 2, wallMaterial);
					}
					break;
				case WEST:
					emptyBlocks(generator, chunk, at.X + 1, at.X + 2, y1, yClear, at.Z, at.Z + 3);
					emptyBlocks(generator, chunk, at.X + 2, at.X + 4, y1, yClear, at.Z + 2, at.Z + 3);
					if (isTopFloor) {
						chunk.setBlock(at.X + 1, y1 - 1, at.Z, Material.BIRCH_TRAPDOOR, BlockFace.SOUTH, Half.TOP);
						chunk.setBlocks(at.X + 1, y1, y2, at.Z, wallMaterial);
					}
					break;
				case EAST:
					emptyBlocks(generator, chunk, at.X, at.X + 3, y1, yClear, at.Z + 1, at.Z + 2);
					emptyBlocks(generator, chunk, at.X + 2, at.X + 3, y1, yClear, at.Z + 2, at.Z + 4);
					if (isTopFloor) {
						chunk.setBlock(at.X + 2, y1 - 1, at.Z + 3, Material.BIRCH_TRAPDOOR, BlockFace.NORTH, Half.TOP);
						chunk.setBlocks(at.X + 2, y1, y2, at.Z + 3, wallMaterial);
					}
					break;
				}
				return;
			}
			break;
		case STUDIO_A:
			// fall through to the next generator, the one who can deal with variable
			// heights
			break;
		}

		// Studio_A
		switch (stairDirection) {
		case NORTH:
			emptyBlocks(generator, chunk, at.X + 1, at.X + 3, y1, y2, at.Z, at.Z + 1);
			emptyBlocks(generator, chunk, at.X + 1, at.X + 3, y1, y2, at.Z + floorHeight - 1, at.Z + floorHeight);
			chunk.setBlocks(at.X, at.X + 1, y1, y2, at.Z, at.Z + floorHeight, wallMaterial);
			chunk.setBlocks(at.X + 3, at.X + 4, y1, y2, at.Z, at.Z + floorHeight, wallMaterial);
			if (isTopFloor) {
				chunk.setBlock(at.X + 1, y1 - 1, at.Z, Material.BIRCH_TRAPDOOR, BlockFace.SOUTH, Half.TOP);
				chunk.setBlock(at.X + 2, y1 - 1, at.Z, Material.BIRCH_TRAPDOOR, BlockFace.SOUTH, Half.TOP);
				chunk.setBlocks(at.X + 1, at.X + 3, y1, y2, at.Z, at.Z + 1, wallMaterial);
			}
			if (isBottomFloor) {
				chunk.setBlocks(at.X + 1, at.X + 3, y1, y2, at.Z + floorHeight - 1, at.Z + floorHeight, wallMaterial);
			}
			break;
		case SOUTH:
			emptyBlocks(generator, chunk, at.X + 1, at.X + 3, y1, y2, at.Z, at.Z + 1);
			emptyBlocks(generator, chunk, at.X + 1, at.X + 3, y1, y2, at.Z + floorHeight - 1, at.Z + floorHeight);
			chunk.setBlocks(at.X, at.X + 1, y1, y2, at.Z, at.Z + floorHeight, wallMaterial);
			chunk.setBlocks(at.X + 3, at.X + 4, y1, y2, at.Z, at.Z + floorHeight, wallMaterial);
			if (isTopFloor) {
				chunk.setBlock(at.X + 1, y1 - 1, at.Z + floorHeight - 1, Material.BIRCH_TRAPDOOR, BlockFace.NORTH,
						Half.TOP);
				chunk.setBlock(at.X + 2, y1 - 1, at.Z + floorHeight - 1, Material.BIRCH_TRAPDOOR, BlockFace.NORTH,
						Half.TOP);
				chunk.setBlocks(at.X + 1, at.X + 3, y1, y2, at.Z + floorHeight - 1, at.Z + floorHeight, wallMaterial);
			}
			if (isBottomFloor) {
				chunk.setBlocks(at.X + 1, at.X + 3, y1, y2, at.Z, at.Z + 1, wallMaterial);
			}
			break;
		case WEST:
			emptyBlocks(generator, chunk, at.X, at.X + 1, y1, y2, at.Z + 1, at.Z + 3);
			emptyBlocks(generator, chunk, at.X + floorHeight - 1, at.X + floorHeight, y1, y2, at.Z + 1, at.Z + 3);
			chunk.setBlocks(at.X, at.X + floorHeight, y1, y2, at.Z, at.Z + 1, wallMaterial);
			chunk.setBlocks(at.X, at.X + floorHeight, y1, y2, at.Z + 3, at.Z + 4, wallMaterial);
			if (isTopFloor) {
				chunk.setBlock(at.X, y1 - 1, at.Z + 1, Material.BIRCH_TRAPDOOR, BlockFace.EAST, Half.TOP);
				chunk.setBlock(at.X, y1 - 1, at.Z + 2, Material.BIRCH_TRAPDOOR, BlockFace.EAST, Half.TOP);
				chunk.setBlocks(at.X, at.X + 1, y1, y2, at.Z + 1, at.Z + 3, wallMaterial);
			}
			if (isBottomFloor) {
				chunk.setBlocks(at.X + floorHeight - 1, at.X + floorHeight, y1, y2, at.Z + 1, at.Z + 3, wallMaterial);
			}
			break;
		case EAST:
			emptyBlocks(generator, chunk, at.X, at.X + 1, y1, y2, at.Z + 1, at.Z + 3);
			emptyBlocks(generator, chunk, at.X + floorHeight - 1, at.X + floorHeight, y1, y2, at.Z + 1, at.Z + 3);
			chunk.setBlocks(at.X, at.X + floorHeight, y1, y2, at.Z, at.Z + 1, wallMaterial);
			chunk.setBlocks(at.X, at.X + floorHeight, y1, y2, at.Z + 3, at.Z + 4, wallMaterial);
			if (isTopFloor) {
				chunk.setBlock(at.X + floorHeight - 1, y1 - 1, at.Z + 1, Material.BIRCH_TRAPDOOR, BlockFace.WEST,
						Half.TOP);
				chunk.setBlock(at.X + floorHeight - 1, y1 - 1, at.Z + 2, Material.BIRCH_TRAPDOOR, BlockFace.WEST,
						Half.TOP);
				chunk.setBlocks(at.X + floorHeight - 1, at.X + floorHeight, y1, y2, at.Z + 1, at.Z + 3, wallMaterial);
			}
			if (isBottomFloor) {
				chunk.setBlocks(at.X, at.X + 1, y1, y2, at.Z + 1, at.Z + 3, wallMaterial);
			}
			break;
		}
	}

	void drawOtherPillars(RealBlocks chunk, int y1, int floorHeight, StairWell where, Material wallMaterial) {
		int y2 = y1 + floorHeight - 1;
		if (where != StairWell.SOUTHWEST)
			chunk.setBlocks(3, 5, y1, y2, 3, 5, wallMaterial);
		if (where != StairWell.SOUTHEAST)
			chunk.setBlocks(3, 5, y1, y2, 11, 13, wallMaterial);
		if (where != StairWell.NORTHWEST)
			chunk.setBlocks(11, 13, y1, y2, 3, 5, wallMaterial);
		if (where != StairWell.NORTHEAST)
			chunk.setBlocks(11, 13, y1, y2, 11, 13, wallMaterial);
	}

	boolean willBeRounded(boolean allowRounded, Surroundings heights) {
		// rounded and square inset and there are exactly two neighbors?
		if (allowRounded) {// && rounded) {

			// do the sides
			if (heights.toSouth()) {
				if (heights.toWest()) {
					return true;
				} else return heights.toEast();
			} else if (heights.toNorth()) {
				if (heights.toWest()) {
					return true;
				} else return heights.toEast();
			}
		}
		return false;
	}

	protected void drawWallParts(CityWorldGenerator generator, InitialBlocks byteChunk, DataContext context, int y1,
			int height, int insetNS, int insetWE, int floor, boolean allowRounded, boolean outsetEffect, boolean onRoof,
			Material wallMaterial, Surroundings heights) {
		// precalculate
		int y2 = y1 + height;
		boolean stillNeedWalls = true;
		int inset = Math.max(insetNS, insetWE);

		// rounded and square inset and there are exactly two neighbors?
		if (allowRounded) {// && rounded) {

			// do the sides
			if (heights.toSouth()) {
				if (heights.toWest()) {
					drawCornerLotSouthWest(byteChunk, cornerLotStyle, inset, y1, y2, wallMaterial, wallMaterial,
							!heights.toSouthWest(), false, outsetEffect, onRoof);
					stillNeedWalls = false;
				} else if (heights.toEast()) {
					drawCornerLotSouthEast(byteChunk, cornerLotStyle, inset, y1, y2, wallMaterial, wallMaterial,
							!heights.toSouthEast(), false, outsetEffect, onRoof);
					stillNeedWalls = false;
				}
			} else if (heights.toNorth()) {
				if (heights.toWest()) {
					drawCornerLotNorthWest(byteChunk, cornerLotStyle, inset, y1, y2, wallMaterial, wallMaterial,
							!heights.toNorthWest(), false, outsetEffect, onRoof);
					stillNeedWalls = false;
				} else if (heights.toEast()) {
					drawCornerLotNorthEast(byteChunk, cornerLotStyle, inset, y1, y2, wallMaterial, wallMaterial,
							!heights.toNorthEast(), false, outsetEffect, onRoof);
					stillNeedWalls = false;
				}
			}
		}

		// outset stuff
		Material outsetMaterial = wallMaterial;
		if (outsetMaterial.hasGravity())
			outsetMaterial = Material.STONE;

		// still need to do something?
		if (stillNeedWalls) {

			// corner columns
			if (!heights.toNorthWest()) {
				if (heights.toNorth() || heights.toWest()) {
					if (heights.toNorth() && heights.toWest()) {
						drawCornerBit(byteChunk, insetWE, y1, y2, insetNS, wallMaterial, BlockFace.NORTH,
								BlockFace.WEST);
					} else if (heights.toNorth()) {
						drawCornerBit(byteChunk, insetWE, y1, y2, insetNS, wallMaterial, BlockFace.NORTH,
								BlockFace.SOUTH);
					} else if (heights.toWest()) {
						drawCornerBit(byteChunk, insetWE, y1, y2, insetNS, wallMaterial, BlockFace.EAST,
								BlockFace.WEST);
					}
					if (outsetEffect) {
						drawCornerBit(byteChunk, insetWE, y1, y2 + 1, insetNS - 1, outsetMaterial);
						drawCornerBit(byteChunk, insetWE - 1, y1, y2 + 1, insetNS, outsetMaterial);
					}
				} else
					drawCornerBit(byteChunk, insetWE, y1, y2, insetNS, wallMaterial, BlockFace.SOUTH, BlockFace.EAST);
			}
			if (!heights.toSouthWest()) {
				if (heights.toSouth() || heights.toWest()) {
					if (heights.toSouth() && heights.toWest()) {
						drawCornerBit(byteChunk, insetWE, y1, y2, byteChunk.width - insetNS - 1, wallMaterial,
								BlockFace.SOUTH, BlockFace.WEST);
					} else if (heights.toSouth()) {
						drawCornerBit(byteChunk, insetWE, y1, y2, byteChunk.width - insetNS - 1, wallMaterial,
								BlockFace.NORTH, BlockFace.SOUTH);
					} else if (heights.toWest()) {
						drawCornerBit(byteChunk, insetWE, y1, y2, byteChunk.width - insetNS - 1, wallMaterial,
								BlockFace.EAST, BlockFace.WEST);
					}
					if (outsetEffect) {
						drawCornerBit(byteChunk, insetWE, y1, y2 + 1, byteChunk.width - insetNS, outsetMaterial);
						drawCornerBit(byteChunk, insetWE - 1, y1, y2 + 1, byteChunk.width - insetNS - 1,
								outsetMaterial);
					}
				} else
					drawCornerBit(byteChunk, insetWE, y1, y2, byteChunk.width - insetNS - 1, wallMaterial,
							BlockFace.NORTH, BlockFace.EAST);
			}
			if (!heights.toNorthEast()) {
				if (heights.toNorth() || heights.toEast()) {
					if (heights.toNorth() && heights.toEast()) {
						drawCornerBit(byteChunk, byteChunk.width - insetWE - 1, y1, y2, insetNS, wallMaterial,
								BlockFace.NORTH, BlockFace.EAST);
					} else if (heights.toNorth()) {
						drawCornerBit(byteChunk, byteChunk.width - insetWE - 1, y1, y2, insetNS, wallMaterial,
								BlockFace.NORTH, BlockFace.SOUTH);
					} else if (heights.toEast()) {
						drawCornerBit(byteChunk, byteChunk.width - insetWE - 1, y1, y2, insetNS, wallMaterial,
								BlockFace.EAST, BlockFace.WEST);
					}
					if (outsetEffect) {
						drawCornerBit(byteChunk, byteChunk.width - insetWE - 1, y1, y2 + 1, insetNS - 1,
								outsetMaterial);
						drawCornerBit(byteChunk, byteChunk.width - insetWE, y1, y2 + 1, insetNS, outsetMaterial);
					}
				} else
					drawCornerBit(byteChunk, byteChunk.width - insetWE - 1, y1, y2, insetNS, wallMaterial,
							BlockFace.SOUTH, BlockFace.WEST);
			}
			if (!heights.toSouthEast()) {
				if (heights.toSouth() || heights.toEast()) {
					if (heights.toSouth() && heights.toEast()) {
						drawCornerBit(byteChunk, byteChunk.width - insetWE - 1, y1, y2, byteChunk.width - insetNS - 1,
								wallMaterial, BlockFace.SOUTH, BlockFace.EAST);
					} else if (heights.toSouth()) {
						drawCornerBit(byteChunk, byteChunk.width - insetWE - 1, y1, y2, byteChunk.width - insetNS - 1,
								wallMaterial, BlockFace.NORTH, BlockFace.SOUTH);
					} else if (heights.toEast()) {
						drawCornerBit(byteChunk, byteChunk.width - insetWE - 1, y1, y2, byteChunk.width - insetNS - 1,
								wallMaterial, BlockFace.EAST, BlockFace.WEST);
					}
					if (outsetEffect) {
						drawCornerBit(byteChunk, byteChunk.width - insetWE - 1, y1, y2 + 1, byteChunk.width - insetNS,
								outsetMaterial);
						drawCornerBit(byteChunk, byteChunk.width - insetWE, y1, y2 + 1, byteChunk.width - insetNS - 1,
								outsetMaterial);
					}
				} else
					drawCornerBit(byteChunk, byteChunk.width - insetWE - 1, y1, y2, byteChunk.width - insetNS - 1,
							wallMaterial, BlockFace.NORTH, BlockFace.WEST);
			}

			// cardinal walls
			if (!heights.toWest()) {
				if (!outsetEffect) {
					byteChunk.setBlocks(insetWE, insetWE + 1, y1, y2, insetNS + 1, byteChunk.width - insetNS - 1,
							wallMaterial, BlockFace.NORTH, BlockFace.SOUTH);
				} else {
					byteChunk.setBlocks(insetWE, insetWE + 1, y1, y2, insetNS + 1, byteChunk.width - insetNS - 1,
							wallMaterial, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST);
					byteChunk.setBlocks(insetWE - 1, insetWE, y1, y2 + 1, insetNS + 1, byteChunk.width - insetNS - 1,
							outsetMaterial, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST);
				}
			}
			if (!heights.toEast()) {
				if (!outsetEffect) {
					byteChunk.setBlocks(byteChunk.width - insetWE - 1, byteChunk.width - insetWE, y1, y2, insetNS + 1,
							byteChunk.width - insetNS - 1, wallMaterial, BlockFace.NORTH, BlockFace.SOUTH);
				} else {
					byteChunk.setBlocks(byteChunk.width - insetWE - 1, byteChunk.width - insetWE, y1, y2, insetNS + 1,
							byteChunk.width - insetNS - 1, wallMaterial, BlockFace.NORTH, BlockFace.SOUTH,
							BlockFace.EAST);
					byteChunk.setBlocks(byteChunk.width - insetWE, byteChunk.width - insetWE + 1, y1, y2 + 1,
							insetNS + 1, byteChunk.width - insetNS - 1, outsetMaterial, BlockFace.NORTH,
							BlockFace.SOUTH, BlockFace.WEST);
				}
			}
			if (!heights.toNorth()) {
				if (!outsetEffect) {
					byteChunk.setBlocks(insetWE + 1, byteChunk.width - insetWE - 1, y1, y2, insetNS, insetNS + 1,
							wallMaterial, BlockFace.EAST, BlockFace.WEST);
				} else {
					byteChunk.setBlocks(insetWE + 1, byteChunk.width - insetWE - 1, y1, y2, insetNS, insetNS + 1,
							wallMaterial, BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH);
					byteChunk.setBlocks(insetWE + 1, byteChunk.width - insetWE - 1, y1, y2 + 1, insetNS - 1, insetNS,
							outsetMaterial, BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH);
				}
			}
			if (!heights.toSouth()) {
				if (!outsetEffect) {
					byteChunk.setBlocks(insetWE + 1, byteChunk.width - insetWE - 1, y1, y2,
							byteChunk.width - insetNS - 1, byteChunk.width - insetNS, wallMaterial, BlockFace.EAST,
							BlockFace.WEST);
				} else {
					byteChunk.setBlocks(insetWE + 1, byteChunk.width - insetWE - 1, y1, y2,
							byteChunk.width - insetNS - 1, byteChunk.width - insetNS, wallMaterial, BlockFace.EAST,
							BlockFace.WEST, BlockFace.SOUTH);
					byteChunk.setBlocks(insetWE + 1, byteChunk.width - insetWE - 1, y1, y2 + 1,
							byteChunk.width - insetNS, byteChunk.width - insetNS + 1, outsetMaterial, BlockFace.EAST,
							BlockFace.WEST, BlockFace.NORTH);
				}
			}

		}

		// only if there are insets
		if (insetWE > 0) {
			if (heights.toWest()) {
				if (!heights.toNorthWest()) {
					byteChunk.setBlocks(0, insetWE, y1, y2, insetNS, insetNS + 1, wallMaterial);
					if (outsetEffect)
						byteChunk.setBlocks(0, insetWE, y1, y2 + 1, insetNS - 1, insetNS, outsetMaterial);
				}
				if (!heights.toSouthWest()) {
					byteChunk.setBlocks(0, insetWE, y1, y2, byteChunk.width - insetNS - 1, byteChunk.width - insetNS,
							wallMaterial);
					if (outsetEffect)
						byteChunk.setBlocks(0, insetWE, y1, y2 + 1, byteChunk.width - insetNS,
								byteChunk.width - insetNS + 1, outsetMaterial);
				}
			}
			if (heights.toEast()) {
				if (!heights.toNorthEast()) {
					byteChunk.setBlocks(byteChunk.width - insetWE, byteChunk.width, y1, y2, insetNS, insetNS + 1,
							wallMaterial);
					if (outsetEffect)
						byteChunk.setBlocks(byteChunk.width - insetWE, byteChunk.width, y1, y2 + 1, insetNS - 1,
								insetNS, outsetMaterial);
				}
				if (!heights.toSouthEast()) {
					byteChunk.setBlocks(byteChunk.width - insetWE, byteChunk.width, y1, y2,
							byteChunk.width - insetNS - 1, byteChunk.width - insetNS, wallMaterial);
					if (outsetEffect)
						byteChunk.setBlocks(byteChunk.width - insetWE, byteChunk.width, y1, y2 + 1,
								byteChunk.width - insetNS, byteChunk.width - insetNS + 1, outsetMaterial);
				}
			}
		}
		if (insetNS > 0) {
			if (heights.toNorth()) {
				if (!heights.toNorthWest()) {
					byteChunk.setBlocks(insetWE, insetWE + 1, y1, y2, 0, insetNS, wallMaterial);
					if (outsetEffect)
						byteChunk.setBlocks(insetWE - 1, insetWE, y1, y2 + 1, 0, insetNS, outsetMaterial);
				}
				if (!heights.toNorthEast()) {
					byteChunk.setBlocks(byteChunk.width - insetWE - 1, byteChunk.width - insetWE, y1, y2, 0, insetNS,
							wallMaterial);
					if (outsetEffect)
						byteChunk.setBlocks(byteChunk.width - insetWE, byteChunk.width - insetWE + 1, y1, y2 + 1, 0,
								insetNS, outsetMaterial);
				}
			}
			if (heights.toSouth()) {
				if (!heights.toSouthWest()) {
					byteChunk.setBlocks(insetWE, insetWE + 1, y1, y2, byteChunk.width - insetNS, byteChunk.width,
							wallMaterial);
					if (outsetEffect)
						byteChunk.setBlocks(insetWE - 1, insetWE, y1, y2 + 1, byteChunk.width - insetNS,
								byteChunk.width, outsetMaterial);
				}
				if (!heights.toSouthEast()) {
					byteChunk.setBlocks(byteChunk.width - insetWE - 1, byteChunk.width - insetWE, y1, y2,
							byteChunk.width - insetNS, byteChunk.width, wallMaterial);
					if (outsetEffect)
						byteChunk.setBlocks(byteChunk.width - insetWE, byteChunk.width - insetWE + 1, y1, y2 + 1,
								byteChunk.width - insetNS, byteChunk.width, outsetMaterial);
				}
			}
		}
	}

	private void drawCornerBit(InitialBlocks blocks, int x, int y1, int y2, int z, Material wallMaterial) {
		blocks.setBlocks(x, y1, y2, z, wallMaterial);
	}

	private void drawCornerBit(InitialBlocks blocks, int x, int y1, int y2, int z, Material wallMaterial,
			BlockFace... facing) {
		blocks.setBlocks(x, y1, y2, z, wallMaterial, facing);
	}

	protected void drawFoundation(CityWorldGenerator generator, InitialBlocks byteChunk, DataContext context, int y1,
			int height, boolean allowRounded, boolean outsetEffect, Material foundationMaterial, Surroundings heights) {
		byteChunk.setLayer(y1, height, foundationMaterial);
	}

	protected void drawCeilings(CityWorldGenerator generator, InitialBlocks byteChunk, DataContext context, int y1,
			int height, int insetNS, int insetWE, boolean allowRounded, boolean outsetEffect, boolean onRoof,
			Material ceilingMaterial, Surroundings heights) {
		// precalculate
		Material emptyMaterial = generator.shapeProvider.findAtmosphereMaterialAt(generator, y1);
		int y2 = y1 + height;
		boolean stillNeedCeiling = true;
		int inset = Math.max(insetNS, insetWE);

		// rounded and square inset and there are exactly two neighbors?
		if (allowRounded) {// && rounded) { // already know that... && insetNS == insetWE &&
			// heights.getNeighborCount() == 2
//			int innerCorner = (byteChunk.width - inset * 2) + inset;
			if (heights.toNorth()) {
				if (heights.toEast()) {
					drawCornerLotNorthEast(byteChunk, cornerLotStyle, inset, y1, y2, ceilingMaterial, emptyMaterial,
							!heights.toNorthEast(), true, outsetEffect, onRoof);
					stillNeedCeiling = false;
				} else if (heights.toWest()) {
					drawCornerLotNorthWest(byteChunk, cornerLotStyle, inset, y1, y2, ceilingMaterial, emptyMaterial,
							!heights.toNorthWest(), true, outsetEffect, onRoof);
					stillNeedCeiling = false;
				}
			} else if (heights.toSouth()) {
				if (heights.toEast()) {
					drawCornerLotSouthEast(byteChunk, cornerLotStyle, inset, y1, y2, ceilingMaterial, emptyMaterial,
							!heights.toSouthEast(), true, outsetEffect, onRoof);
					stillNeedCeiling = false;
				} else if (heights.toWest()) {
					drawCornerLotSouthWest(byteChunk, cornerLotStyle, inset, y1, y2, ceilingMaterial, emptyMaterial,
							!heights.toSouthWest(), true, outsetEffect, onRoof);
					stillNeedCeiling = false;
				}
			}
		}

		// still need to do something?
		if (stillNeedCeiling) {

			// center part
			byteChunk.setBlocks(insetWE, byteChunk.width - insetWE, y1, y2, insetNS, byteChunk.width - insetNS,
					ceilingMaterial);

		}

		// only if we are inset
		if (insetWE > 0 || insetNS > 0) {

			// cardinal bits
			if (heights.toWest())
				byteChunk.setBlocks(0, insetWE, y1, y2, insetNS, byteChunk.width - insetNS, ceilingMaterial);
			if (heights.toEast())
				byteChunk.setBlocks(byteChunk.width - insetWE, byteChunk.width, y1, y2, insetNS,
						byteChunk.width - insetNS, ceilingMaterial);
			if (heights.toNorth())
				byteChunk.setBlocks(insetWE, byteChunk.width - insetWE, y1, y2, 0, insetNS, ceilingMaterial);
			if (heights.toSouth())
				byteChunk.setBlocks(insetWE, byteChunk.width - insetWE, y1, y2, byteChunk.width - insetNS,
						byteChunk.width, ceilingMaterial);

			// corner bits
			if (heights.toNorthWest())
				byteChunk.setBlocks(0, insetWE, y1, y2, 0, insetNS, ceilingMaterial);
			if (heights.toSouthWest())
				byteChunk.setBlocks(0, insetWE, y1, y2, byteChunk.width - insetNS, byteChunk.width, ceilingMaterial);
			if (heights.toNorthEast())
				byteChunk.setBlocks(byteChunk.width - insetWE, byteChunk.width, y1, y2, 0, insetNS, ceilingMaterial);
			if (heights.toSouthEast())
				byteChunk.setBlocks(byteChunk.width - insetWE, byteChunk.width, y1, y2, byteChunk.width - insetNS,
						byteChunk.width, ceilingMaterial);
		}
	}

	protected void drawFence(CityWorldGenerator generator, InitialBlocks chunk, DataContext context, int inset, int y1,
			int floor, Surroundings neighbors, Material fenceMaterial, int fenceHeight) {

		// actual fence
		drawWallParts(generator, chunk, context, y1, fenceHeight, inset, inset, floor, false, false, false,
				fenceMaterial, neighbors);

		// holes in fence
		int i = 4 + chunkOdds.getRandomInt(chunk.width / 2);
		int y2 = y1 + fenceHeight;
		if (chunkOdds.flipCoin() && !neighbors.toWest())
			chunk.airoutBlocks(generator, inset, y1, y2, i);
		if (chunkOdds.flipCoin() && !neighbors.toEast())
			chunk.airoutBlocks(generator, chunk.width - 1 - inset, y1, y2, i);
		if (chunkOdds.flipCoin() && !neighbors.toNorth())
			chunk.airoutBlocks(generator, i, y1, y2, inset);
		if (chunkOdds.flipCoin() && !neighbors.toSouth())
			chunk.airoutBlocks(generator, i, y1, y2, chunk.width - 1 - inset);
	}

	private void drawCornerLotNorthWest(InitialBlocks chunk, int cornerLotStyle, int inset, int y1, int y2,
			Material primary, Material secondary, boolean doInnerWall, boolean doFill, boolean outsetEffect,
			boolean onRoof) {
		drawCornerLotNorthWest(chunk, cornerLotStyle, inset, y1, y2, primary, secondary, null, doInnerWall, doFill,
				outsetEffect, onRoof);
	}

	private void drawCornerLotSouthWest(InitialBlocks chunk, int cornerLotStyle, int inset, int y1, int y2,
			Material primary, Material secondary, boolean doInnerWall, boolean doFill, boolean outsetEffect,
			boolean onRoof) {
		drawCornerLotSouthWest(chunk, cornerLotStyle, inset, y1, y2, primary, secondary, null, doInnerWall, doFill,
				outsetEffect, onRoof);
	}

	private void drawCornerLotNorthEast(InitialBlocks chunk, int cornerLotStyle, int inset, int y1, int y2,
			Material primary, Material secondary, boolean doInnerWall, boolean doFill, boolean outsetEffect,
			boolean onRoof) {
		drawCornerLotNorthEast(chunk, cornerLotStyle, inset, y1, y2, primary, secondary, null, doInnerWall, doFill,
				outsetEffect, onRoof);
	}

	private void drawCornerLotSouthEast(InitialBlocks chunk, int cornerLotStyle, int inset, int y1, int y2,
			Material primary, Material secondary, boolean doInnerWall, boolean doFill, boolean outsetEffect,
			boolean onRoof) {
		drawCornerLotSouthEast(chunk, cornerLotStyle, inset, y1, y2, primary, secondary, null, doInnerWall, doFill,
				outsetEffect, onRoof);
	}

	void drawCornerLotNorthWest(InitialBlocks chunk, int cornerLotStyle, int inset, int y1, int y2,
			Material primary, Material secondary, MaterialFactory maker, boolean doInnerWall, boolean doFill,
			boolean outsetEffect, boolean onRoof) {
		if (cornerBlocks.isOldRoundedCorner(cornerLotStyle)) {
			if (doFill) {
				chunk.setArcNorthWest(inset, y1, y2, primary, true);
				if (doInnerWall)
					chunk.setArcNorthWest(16 - inset, y1, y2, secondary, true);
			} else if (maker == null) {
				chunk.setArcNorthWest(inset, y1, y2, primary, false);
				if (doInnerWall)
					chunk.setArcNorthWest(16 - inset, y1, y2, primary, false);
			} else {
				chunk.setArcNorthWest(inset, y1, y2, primary, secondary, maker, false);
				if (doInnerWall)
					chunk.setArcNorthWest(16 - inset, y1, y2, primary, secondary, maker, false);
			}
		} else {
			int xCenter = 16 - (CornerBlocks.CornerWidth + inset);
			int zCenter = 16 - (CornerBlocks.CornerWidth + inset);
			if (doFill) {
				chunk.setBlocks(inset, 16 - inset, y1, y2, 0, zCenter, primary);// 1
				chunk.setBlocks(0, xCenter, y1, y2, inset, 16 - inset, primary);// 2
				if (doInnerWall)
					chunk.setBlocks(0, inset, y1, y2, 0, inset, secondary);// 3
				cornerBlocks.drawNWHorizontals(cornerLotStyle, chunk, xCenter, y1, y2, zCenter, primary, secondary,
						outsetEffect, onRoof);
			} else {
				if (maker == null) {
					chunk.setBlocks(16 - inset - 1, 16 - inset, y1, y2, 0, zCenter, primary);// A
					chunk.setBlocks(0, xCenter, y1, y2, 16 - inset - 1, 16 - inset, primary);// B
					if (doInnerWall) {
						chunk.setBlocks(inset, inset + 1, y1, y2, 0, inset, primary);// C
						chunk.setBlocks(0, inset, y1, y2, inset, inset + 1, primary);// D
					}
				} else {
					chunk.setBlocks(16 - inset - 1, 16 - inset, y1, y2, 0, zCenter, primary, secondary, maker,
							BlockFace.NORTH, BlockFace.SOUTH);// A
					chunk.setBlocks(0, xCenter, y1, y2, 16 - inset - 1, 16 - inset, primary, secondary, maker,
							BlockFace.EAST, BlockFace.WEST);// B
					if (doInnerWall) {
						chunk.setBlocks(inset, inset + 1, y1, y2, 0, inset, primary, secondary, maker, BlockFace.NORTH,
								BlockFace.SOUTH);// C
						chunk.setBlocks(0, inset, y1, y2, inset, inset + 1, primary, secondary, maker, BlockFace.EAST,
								BlockFace.WEST);// D
					}
				}
				cornerBlocks.drawNWVerticals(cornerLotStyle, chunk, xCenter, y1, y2, zCenter, primary, secondary,
						outsetEffect, onRoof);
			}
		}
	}

	void drawCornerLotSouthWest(InitialBlocks chunk, int cornerLotStyle, int inset, int y1, int y2,
			Material primary, Material secondary, MaterialFactory maker, boolean doInnerWall, boolean doFill,
			boolean outsetEffect, boolean onRoof) {
		if (cornerBlocks.isOldRoundedCorner(cornerLotStyle)) {
			if (doFill) {
				chunk.setArcSouthWest(inset, y1, y2, primary, true);
				if (doInnerWall)
					chunk.setArcSouthWest(16 - inset, y1, y2, secondary, true);
			} else {
				chunk.setArcSouthWest(inset, y1, y2, primary, secondary, maker, false);
				if (doInnerWall)
					chunk.setArcSouthWest(16 - inset, y1, y2, primary, secondary, maker, false);
			}
		} else {
			int xCenter = 16 - (CornerBlocks.CornerWidth + inset);
			int zCenter = CornerBlocks.CornerWidth + inset;
			if (doFill) {
				chunk.setBlocks(inset, 16 - inset, y1, y2, zCenter, 16, primary);// 1
				chunk.setBlocks(0, xCenter, y1, y2, inset, 16 - inset, primary);// 2
				if (doInnerWall)
					chunk.setBlocks(0, inset, y1, y2, 16 - inset, 16, secondary);// 3
				cornerBlocks.drawSWHorizontals(cornerLotStyle, chunk, xCenter, y1, y2, inset, primary, secondary,
						outsetEffect, onRoof);
			} else {
				if (maker == null) {
					chunk.setBlocks(16 - inset - 1, 16 - inset, y1, y2, zCenter, 16, primary);// A
					chunk.setBlocks(0, xCenter, y1, y2, inset, inset + 1, primary);// B
					if (doInnerWall) {
						chunk.setBlocks(inset, inset + 1, y1, y2, 16 - inset, 16, primary);// C
						chunk.setBlocks(0, inset, y1, y2, 16 - inset - 1, 16 - inset, primary);// D
					}
				} else {
					chunk.setBlocks(16 - inset - 1, 16 - inset, y1, y2, zCenter, 16, primary, secondary, maker,
							BlockFace.NORTH, BlockFace.SOUTH);// A
					chunk.setBlocks(0, xCenter, y1, y2, inset, inset + 1, primary, secondary, maker, BlockFace.EAST,
							BlockFace.WEST);// B
					if (doInnerWall) {
						chunk.setBlocks(inset, inset + 1, y1, y2, 16 - inset, 16, primary, secondary, maker,
								BlockFace.NORTH, BlockFace.SOUTH);// C
						chunk.setBlocks(0, inset, y1, y2, 16 - inset - 1, 16 - inset, primary, secondary, maker,
								BlockFace.EAST, BlockFace.WEST);// D
					}
				}
				cornerBlocks.drawSWVerticals(cornerLotStyle, chunk, xCenter, y1, y2, inset, primary, secondary,
						outsetEffect, onRoof);
			}
		}
	}

	void drawCornerLotNorthEast(InitialBlocks chunk, int cornerLotStyle, int inset, int y1, int y2,
			Material primary, Material secondary, MaterialFactory maker, boolean doInnerWall, boolean doFill,
			boolean outsetEffect, boolean onRoof) {
		if (cornerBlocks.isOldRoundedCorner(cornerLotStyle)) {
			if (doFill) {
				chunk.setArcNorthEast(inset, y1, y2, primary, true);
				if (doInnerWall)
					chunk.setArcNorthEast(16 - inset, y1, y2, secondary, true);
			} else {
				chunk.setArcNorthEast(inset, y1, y2, primary, secondary, maker, false);
				if (doInnerWall)
					chunk.setArcNorthEast(16 - inset, y1, y2, primary, secondary, maker, false);
			}
		} else {
			int xCenter = CornerBlocks.CornerWidth + inset;
			int zCenter = 16 - (CornerBlocks.CornerWidth + inset);
			if (doFill) {
				chunk.setBlocks(inset, 16 - inset, y1, y2, 0, zCenter, primary);// 1
				chunk.setBlocks(xCenter, 16, y1, y2, inset, 16 - inset, primary);// 2
				if (doInnerWall)
					chunk.setBlocks(16 - inset, 16, y1, y2, 0, inset, secondary);// 3
				cornerBlocks.drawNEHorizontals(cornerLotStyle, chunk, inset, y1, y2, zCenter, primary, secondary,
						outsetEffect, onRoof);
			} else {
				if (maker == null) {
					chunk.setBlocks(inset, inset + 1, y1, y2, 0, zCenter, primary);// A
					chunk.setBlocks(xCenter, 16, y1, y2, 16 - inset - 1, 16 - inset, primary);// B
					if (doInnerWall) {
						chunk.setBlocks(16 - inset - 1, 16 - inset, y1, y2, 0, inset, primary);// C
						chunk.setBlocks(16 - inset - 1, 16, y1, y2, inset, inset + 1, primary);// D
					}
				} else {
					chunk.setBlocks(inset, inset + 1, y1, y2, 0, zCenter, primary, secondary, maker, BlockFace.NORTH,
							BlockFace.SOUTH);// A
					chunk.setBlocks(xCenter, 16, y1, y2, 16 - inset - 1, 16 - inset, primary, secondary, maker);// B
					if (doInnerWall) {
						chunk.setBlocks(16 - inset - 1, 16 - inset, y1, y2, 0, inset, primary, secondary, maker,
								BlockFace.NORTH, BlockFace.SOUTH);// C
						chunk.setBlocks(16 - inset - 1, 16, y1, y2, inset, inset + 1, primary, secondary, maker,
								BlockFace.EAST, BlockFace.WEST);// D
					}
				}
				cornerBlocks.drawNEVerticals(cornerLotStyle, chunk, inset, y1, y2, zCenter, primary, secondary,
						outsetEffect, onRoof);
			}
		}
	}

	void drawCornerLotSouthEast(InitialBlocks chunk, int cornerLotStyle, int inset, int y1, int y2,
			Material primary, Material secondary, MaterialFactory maker, boolean doInnerWall, boolean doFill,
			boolean outsetEffect, boolean onRoof) {
		if (cornerBlocks.isOldRoundedCorner(cornerLotStyle)) {
			if (doFill) {
				chunk.setArcSouthEast(inset, y1, y2, primary, true);
				if (doInnerWall)
					chunk.setArcSouthEast(16 - inset, y1, y2, secondary, true);
			} else {
				chunk.setArcSouthEast(inset, y1, y2, primary, secondary, maker, false);
				if (doInnerWall)
					chunk.setArcSouthEast(16 - inset, y1, y2, primary, secondary, maker, false);
			}
		} else {
			int xCenter = CornerBlocks.CornerWidth + inset;
			int zCenter = CornerBlocks.CornerWidth + inset;
			if (doFill) {
				chunk.setBlocks(inset, 16 - inset, y1, y2, zCenter, 16, primary);// 1
				chunk.setBlocks(xCenter, 16, y1, y2, inset, 16 - inset, primary);// 2
				if (doInnerWall)
					chunk.setBlocks(16 - inset, 16, y1, y2, 16 - inset, 16, secondary);// 3
				cornerBlocks.drawSEHorizontals(cornerLotStyle, chunk, inset, y1, y2, inset, primary, secondary,
						outsetEffect, onRoof);
			} else {
				if (maker == null) {
					chunk.setBlocks(inset, inset + 1, y1, y2, zCenter, 16, primary);// A
					chunk.setBlocks(xCenter, 16, y1, y2, inset, inset + 1, primary);// B
					if (doInnerWall) {
						chunk.setBlocks(16 - inset - 1, 16 - inset, y1, y2, 16 - inset - 1, 16, primary);// C
						chunk.setBlocks(16 - inset - 1, 16, y1, y2, 16 - inset - 1, 16 - inset, primary);// D
					}
				} else {
					chunk.setBlocks(inset, inset + 1, y1, y2, zCenter, 16, primary, secondary, maker, BlockFace.NORTH,
							BlockFace.SOUTH);// A
					chunk.setBlocks(xCenter, 16, y1, y2, inset, inset + 1, primary, secondary, maker, BlockFace.EAST,
							BlockFace.WEST);// B
					if (doInnerWall) {
						chunk.setBlocks(16 - inset - 1, 16 - inset, y1, y2, 16 - inset - 1, 16, primary, secondary,
								maker, BlockFace.NORTH, BlockFace.SOUTH);// C
						chunk.setBlocks(16 - inset - 1, 16, y1, y2, 16 - inset - 1, 16 - inset, primary, secondary,
								maker, BlockFace.EAST, BlockFace.WEST);// D
					}
				}
				cornerBlocks.drawSEVerticals(cornerLotStyle, chunk, inset, y1, y2, inset, primary, secondary,
						outsetEffect, onRoof);
			}
		}
	}
}
