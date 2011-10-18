package me.daddychurchill.CityWorld;

import java.util.Random;

import org.bukkit.Material;

public class PlatOfficeBuilding extends PlatBuilding {

	protected final static int MaterialWallIndex = 0;
	protected final static int MaterialCeilingIndex = 1;
	protected final static int MaterialGlassIndex = 2;

	// TODO columns height
	// TODO size reduction both, NS, EW, N/S/E/W only
	// TODO girders unfinished buildings (all or starting at some floor) ???
	// TODO rounded building corners (if connected N and E and have a quarter arc of a building)
	/*
	 * 
void drawCircle(int r)
{
	int x=0, y=r, d = 1-r;
	circlePoints(x, y); //This will the point on the circle
	while(x < y) {
		if (d < 0) {
			d = d+2*x+3;
			x = x+1;
		} else {
			d = d+2*(x-y)+5;
			x = x+1;
			y = y-1;
		}
		circlePoints(x,y); //This will the point on the circle
	}
}
	 */

	protected int insetWallNS;
	protected int insetWallEW;
	protected int insetCeilingNS;
	protected int insetCeilingEW;

	public PlatOfficeBuilding(Random rand, int maxHeight, int maxDepth) {
		super(rand, maxHeight, maxDepth);

		// what is it made of?
		setMaterials(pickWallMaterial(rand), pickCeilingMaterial(rand), GlassMaker.pickGlassMaterial(rand));

		// our bits
		insetWallNS = rand.nextInt(2) + 1;
		insetWallEW = rand.nextInt(2) + 1;
		insetCeilingNS = insetWallNS + rand.nextInt(3) - 1;
		insetCeilingEW = insetWallEW + rand.nextInt(3) - 1;
		
		//TODO: fix up material issues
		// thin glass should not be used with ceiling inset, it looks goofy
		// thin glass should not be used with double-step walls, the glass does not align correctly
		if (materials[MaterialGlassIndex] == Material.THIN_GLASS) {
			insetCeilingNS = Math.min(insetCeilingNS, insetWallNS);
			insetCeilingEW = Math.min(insetCeilingEW, insetWallEW);
			if (materials[MaterialWallIndex] == Material.DOUBLE_STEP)
				materials[MaterialGlassIndex] = Material.GLASS;
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
		}
	}

	@Override
	public void generateChunk(PlatMap platmap, Chunk chunk, int platX, int platZ) {

		// starting with the bottom
		generateBedrock(chunk, PlatMap.StreetLevel);

		// TODO add basements and such
		chunk.setLayer(PlatMap.StreetLevel, Material.DOUBLE_STEP);

		SurroundingHeights neighborFloors = getNeighboringFloorCounts(platmap,
				platX, platZ);

		for (int floor = 0; floor < height; floor++) {

			// TODO make this better
			drawWalls(chunk, PlatMap.StreetLevel + PlatMap.FloorHeight * floor + 1, PlatMap.FloorHeight - 1, 
					insetWallNS, insetWallEW,
					materials[MaterialWallIndex], materials[MaterialGlassIndex], 
					neighborFloors);
			drawCeilings(chunk, PlatMap.StreetLevel + PlatMap.FloorHeight * floor + 1 + PlatMap.FloorHeight - 1, 1, 
					insetCeilingNS, insetCeilingEW, 
					materials[MaterialCeilingIndex],
					neighborFloors);

			// one down, more to go
			neighborFloors.decrement();
		}
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

	static protected Material pickCeilingMaterial(Random rand) {
		// TODO we should validate these against the wall materials
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
