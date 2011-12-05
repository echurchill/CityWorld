package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import me.daddychurchill.CityWorld.PlatMaps.PlatMap;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.Direction.Door;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SurroundingFloors;

import org.bukkit.Material;

public class PlatOfficeBuilding extends PlatBuilding {

	protected final static int FloorHeight = PlatMap.FloorHeight;
	protected final static int stairWallMaterialIsWallMaterialOdds = 2; // stair walls are the same as walls 1/n of the time
	
	protected Material wallMaterial;
	protected Material ceilingMaterial;
	protected Material glassMaterial;
	protected Material stairMaterial;
	protected Material stairWallMaterial;
	protected Material doorMaterial;
	private boolean hasStairsBelow;
	private boolean hasStairsAbove;
	
	//TODO columns height
	//TODO size reduction both, NS, EW, N/S/E/W only
	//TODO girders unfinished buildings (all or starting at some floor) ???
	//TODO rounded building corners (if connected N and E and have a quarter arc of a building)

	protected int insetWallNS;
	protected int insetWallEW;
	protected int insetCeilingNS;
	protected int insetCeilingEW;

	public PlatOfficeBuilding(Random rand, int maxHeight, int maxDepth, 
			int overallIdenticalHeightsOdds, 
			int overallSimilarHeightsOdds,
			int overallSimilarRoundedOdds) {
		super(rand, maxHeight, maxDepth, overallIdenticalHeightsOdds, 
				overallSimilarHeightsOdds, overallSimilarRoundedOdds);

		// our bits
		insetWallNS = rand.nextInt(2) + 2;
		insetWallEW = rand.nextInt(2) + 2;
		insetCeilingNS = insetWallNS + rand.nextInt(3) - 1;
		insetCeilingEW = insetWallEW + rand.nextInt(3) - 1;
		hasStairsBelow = false;
		hasStairsAbove = false;
		
		// what is it made of?
		wallMaterial = pickWallMaterial(rand);
		ceilingMaterial = pickCeilingMaterial(rand);
		glassMaterial = pickGlassMaterial(rand);
		stairMaterial = pickStairMaterial(wallMaterial);
		doorMaterial = Material.WOOD_DOOR;
		
		if (rand.nextInt(stairWallMaterialIsWallMaterialOdds) == 0)
			stairWallMaterial = wallMaterial;
		else
			stairWallMaterial = pickStairWallMaterial(wallMaterial);

		// Fix up any material issues
		// thin glass should not be used with ceiling inset, it looks goofy
		// thin glass should not be used with double-step walls, the glass does not align correctly
		if (glassMaterial == Material.THIN_GLASS) {
			insetCeilingNS = Math.min(insetCeilingNS, insetWallNS);
			insetCeilingEW = Math.min(insetCeilingEW, insetWallEW);
			if (wallMaterial == Material.DOUBLE_STEP)
				glassMaterial = Material.GLASS;
		}
	}

	public void makeConnected(Random rand, PlatLot relative) {
		super.makeConnected(rand, relative);

		// other bits
		if (relative instanceof PlatOfficeBuilding) {
			PlatOfficeBuilding relativebuilding = (PlatOfficeBuilding) relative;

			// our bits
			insetWallNS = relativebuilding.insetWallNS;
			insetWallEW = relativebuilding.insetWallEW;
			insetCeilingNS = relativebuilding.insetCeilingNS;
			insetCeilingEW = relativebuilding.insetCeilingEW;
			
			// what is it made of?
			wallMaterial = relativebuilding.wallMaterial;
			ceilingMaterial = relativebuilding.ceilingMaterial;
			glassMaterial = relativebuilding.glassMaterial;
			stairMaterial = relativebuilding.stairMaterial;
			stairWallMaterial = relativebuilding.stairWallMaterial;
			doorMaterial = relativebuilding.doorMaterial;
		}
	}

	@Override
	public void generateChunk(PlatMap platmap, ByteChunk chunk, int platX, int platZ) {
		double throwOfDice = rand.nextDouble();
		boolean allowRounded = rounded && insetWallNS == insetWallEW && insetCeilingNS == insetCeilingEW;
		
		// starting with the bottom
		int lowestY = PlatMap.StreetLevel - FloorHeight * (depth - 1) - 3;
		generateBedrock(chunk, lowestY);
		
		// bottom most floor
		chunk.setBlocks(0, ByteChunk.Width, lowestY, lowestY + 1, 0, ByteChunk.Width, (byte) ceilingMaterial.getId());
		
		// below ground
		SurroundingFloors neighborBasements = getNeighboringBasementCounts(platmap, platX, platZ);
		for (int floor = 0; floor < depth; floor++) {
			drawWalls(chunk, PlatMap.StreetLevel - FloorHeight * floor - 2, FloorHeight - 1, 
					0, 0, false,
					wallMaterial, wallMaterial, neighborBasements);
			drawCeilings(chunk, PlatMap.StreetLevel - FloorHeight * floor - 2 + FloorHeight - 1, 1, 
					0, 0, false,
					ceilingMaterial, neighborBasements);
			
			// see if stairs are needed
			hasStairsBelow = hasStairsBelow || stairsHere(neighborBasements, throwOfDice);
			
			// one down, more to go
			neighborBasements.decrement();
		}

		// above ground
		SurroundingFloors neighborFloors = getNeighboringFloorCounts(platmap, platX, platZ);
		for (int floor = 0; floor < height; floor++) {
			drawWalls(chunk, PlatMap.StreetLevel + FloorHeight * floor + 2, FloorHeight - 1, 
					insetWallNS, insetWallEW, allowRounded,
					wallMaterial, glassMaterial, neighborFloors);
			drawCeilings(chunk, PlatMap.StreetLevel + FloorHeight * floor + 2 + FloorHeight - 1, 1, 
					insetCeilingNS, insetCeilingEW, allowRounded, 
					ceilingMaterial, neighborFloors);

			// see if stairs are needed
			hasStairsAbove = hasStairsAbove || stairsHere(neighborFloors, throwOfDice);
			
			// one down, more to go
			neighborFloors.decrement();
		}
	}
	
	@Override
	public void generateBlocks(PlatMap platmap, RealChunk chunk, int platX, int platZ) {
		// check out the neighbors
		//SurroundingFloors neighborBasements = getNeighboringBasementCounts(platmap, platX, platZ);
		SurroundingFloors neighborFloors = getNeighboringFloorCounts(platmap, platX, platZ);
		
		// check for rounding errors
		boolean allowRounded = rounded && insetWallNS == insetWallEW && insetCeilingNS == insetCeilingEW;
		boolean isRounded = allowRounded && neighborFloors.getNeighborCount() == 2;
		
		// stairs when not rounded
		if (!isRounded) {
		
			// if there is going to be stairs here, lets place some matching doors
			if (hasStairsAbove) {
				drawDoors(chunk, insetWallNS, RealChunk.Width - insetWallNS - 1, 
						  insetWallEW, RealChunk.Width - insetWallEW - 1,
						  PlatMap.StreetLevel + 2,
						  !neighborFloors.toSouth(), !neighborFloors.toNorth(), 
						  !neighborFloors.toWest(), !neighborFloors.toEast());
			}
			
			// work on the basement stairs first
			if (hasStairsBelow) {
				for (int floor = 0; floor < depth; floor++) {
					
					// place the stairs and such
					drawStairs(chunk, PlatMap.StreetLevel - FloorHeight * floor - 2);
						
					// plain walls please
					drawStairsWalls(chunk, PlatMap.StreetLevel - FloorHeight * floor - 2, wallMaterial, false, floor == depth - 1);
				}
			}
			
			// now the above ground floors
			if (hasStairsAbove) {
				for (int floor = 0; floor < height; floor++) {
					
					// more stairs and such
					if (floor < height - 1)
						drawStairs(chunk, PlatMap.StreetLevel + FloorHeight * floor + 2);
					
					// fancy walls... maybe
					drawStairsWalls(chunk, PlatMap.StreetLevel + FloorHeight * floor + 2, stairWallMaterial, floor == height - 1, false);
				}
			}
		}
	}
	
	protected void drawSingleDoor(RealChunk chunk, int x1, int x2, int x3, int z1, int z2, int z3, int y, Direction.Door direction) {
		chunk.setBlocks(x1, y, y + FloorHeight - 1, z1, wallMaterial);
		chunk.setWoodenDoor(x2, y, z2, direction);
		chunk.setBlocks(x3, y, y + FloorHeight - 1, z3, wallMaterial);
	}
	
	protected void drawDoors(RealChunk chunk, int x1, int x2, int z1, int z2, int y,
			boolean doorToSouth, boolean doorToNorth,
			boolean doorToWest, boolean doorToEast) {
		int center = RealChunk.Width / 2;
		if (doorToSouth)
			drawSingleDoor(chunk, x1, x1, x1, center - 1, center, center + 1, y, Door.SOUTHBYSOUTHWEST);
		if (doorToNorth)
			drawSingleDoor(chunk, x2, x2, x2, center - 1, center, center + 1, y, Door.NORTHBYNORTHEAST);
		if (doorToWest)
			drawSingleDoor(chunk, center - 1, center, center + 1, z1, z1, z1, y, Door.WESTBYNORTHWEST);
		if (doorToEast)
			drawSingleDoor(chunk, center - 1, center, center + 1, z2, z2, z2, y, Door.EASTBYSOUTHEAST);
	}
	
	protected boolean stairsHere(SurroundingFloors neighbors, double throwOfDice) {
		return (throwOfDice < 1.0 - ((double) neighbors.getNeighborCount() / 4.0));
	}
	
	protected void drawStairs(RealChunk chunk, int y) {
		int x = (RealChunk.Width - FloorHeight) / 2;
		int z = (RealChunk.Width - 4) / 2; // room for a two walls and two wide stairs
		for (int i = 0; i < FloorHeight; i++) {
			chunk.setBlock(x + i, y + FloorHeight - 1, z + 1, Material.AIR);
			chunk.setBlock(x + i, y + FloorHeight - 1, z + 2, Material.AIR);
			chunk.setBlock(x + i, y + i, z + 1, stairMaterial);
			chunk.setBlock(x + i, y + i, z + 2, stairMaterial);
		}
	}

	protected void drawStairsWalls(RealChunk chunk, int y, Material material, boolean drawStartcap, boolean drawEndcap) {
		int x = (RealChunk.Width - FloorHeight) / 2;
		int z = (RealChunk.Width - 4) / 2; // room for a two walls and two wide stairs
		chunk.setBlocks(x, x + FloorHeight, y, y + FloorHeight - 1, z, z + 1, material);
		chunk.setBlocks(x, x + FloorHeight, y, y + FloorHeight - 1, z + 3, z + 4, material);
		if (drawStartcap)
			chunk.setBlocks(x - 1, x, y, y + FloorHeight - 1, z, z + 4, material);
		if (drawEndcap)
			chunk.setBlocks(x + FloorHeight, x + FloorHeight + 1, y, y + FloorHeight - 1, z, z + 4, material);
	}

	static protected Material pickWallMaterial(Random rand) {
		switch (rand.nextInt(12)) {
		case 1:
			return Material.COBBLESTONE;
		case 2:
			return Material.STONE;
		case 3:
			return Material.SMOOTH_BRICK;
		case 4:
			return Material.CLAY;
		case 5:
			return Material.IRON_BLOCK;
		case 6:
			return Material.BRICK;
		case 7:
			return Material.MOSSY_COBBLESTONE;
		case 8:
			return Material.DOUBLE_STEP;
		case 9:
			return Material.SANDSTONE;
		case 10:
			return Material.WOOD;
		case 11:
			return Material.WOOL;
		default:
			return Material.SAND;
		}
	}

	static protected Material pickStairMaterial(Material wall) {
		switch (wall) {
		case COBBLESTONE:
		case MOSSY_COBBLESTONE:
			return Material.COBBLESTONE_STAIRS;

		case STONE:
		case SMOOTH_BRICK:
		case CLAY:
		case IRON_BLOCK:
		case DOUBLE_STEP:
			return Material.SMOOTH_STAIRS;

		case WOOL:
		case BRICK:
			return Material.BRICK_STAIRS;
		
		default: // SANDSTONE, WOOD, SAND
			return Material.WOOD_STAIRS;
		}
	}

	static protected Material pickStairWallMaterial(Material wall) {
		switch (wall) {
		case COBBLESTONE:
		case MOSSY_COBBLESTONE:
		case STONE:
		case SMOOTH_BRICK:
			return Material.IRON_FENCE;

		case IRON_BLOCK:
		case DOUBLE_STEP:
		case WOOL:
		case BRICK:
			return Material.THIN_GLASS;
		
		default: // SANDSTONE, WOOD, SAND, CLAY
			return Material.FENCE;
		}
	}

	static protected Material pickCeilingMaterial(Random rand) {
		switch (rand.nextInt(11)) {
		case 1:
			return Material.COBBLESTONE;
		case 2:
			return Material.STONE;
		case 3:
			return Material.SMOOTH_BRICK;
		case 4:
			return Material.CLAY;
		case 5:
			return Material.IRON_BLOCK;
		case 6:
			return Material.BRICK;
		case 7:
			return Material.MOSSY_COBBLESTONE;
		case 8:
			return Material.DOUBLE_STEP;
		case 9:
			return Material.SANDSTONE;
		case 10:
			return Material.WOOD;
		default:
			return Material.WOOL;
		}
	}
}
