package me.daddychurchill.CityWorld.Plats;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import org.bukkit.material.MaterialData;
import org.bukkit.material.SmoothBrick;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import me.daddychurchill.CityWorld.Support.AbstractBlocks;
import me.daddychurchill.CityWorld.Support.BlackMagic;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.BadMagic;
import me.daddychurchill.CityWorld.Support.HeightInfo;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.SupportBlocks;
import me.daddychurchill.CityWorld.Support.SurroundingRoads;
import me.daddychurchill.CityWorld.Support.Surroundings;

public class RoadLot extends ConnectedLot {
	
	//TODO Lines on the road
	
	public static final int PlatMapRoadInset = 3;

	protected final static int sidewalkWidth = 3;
	protected final static int lightpostHeight = 3;
	protected final static int crossDitchEdge = 7;
	protected final static int tunnelHeight = 8;
	protected final static int fenceHeight = 2;
	
	protected final static Material lightpostbaseMaterial = Material.DOUBLE_STEP;
	protected final static Material lightpostMaterial = Material.FENCE;
	
	public final static Material sewerMaterial = Material.SMOOTH_BRICK;
	public final static MaterialData sewerFloorData = new SmoothBrick(Material.STONE);
	public final static MaterialData sewerWallData = new SmoothBrick(Material.MOSSY_COBBLESTONE);
	public final static MaterialData sewerCeilingData = new SmoothBrick(Material.COBBLESTONE);
	
	//protected final static Material vineMaterial = Material.VINE;
	
	protected final static Material retainingWallMaterial = Material.SMOOTH_BRICK;
	protected final static Material retainingFenceMaterial = Material.IRON_FENCE;

	protected final static Material tunnelWallMaterial = Material.SMOOTH_BRICK;
	protected final static Material tunnelTileMaterial = Material.SANDSTONE;
	protected final static Material tunnelCeilingMaterial = Material.GLASS;
	
	protected final static Material bridgePavement1Material = Material.WOOD_STEP;
	protected final static Material bridgePavement2Material = Material.WOOD_DOUBLE_STEP;
	protected final static Material bridgeSidewalk1Material = Material.STEP;
	protected final static Material bridgeSidewalk2Material = Material.DOUBLE_STEP;
	protected final static Material bridgeEdgeMaterial = Material.SMOOTH_BRICK;
	protected final static Material bridgeRailMaterial = Material.FENCE;
	
	protected Material pavementMat;
	protected Material linesMat;
	protected boolean pavementIsClay;
	protected final static DyeColor pavementColor = DyeColor.CYAN;
//	protected final static DyeColor crosswalkColor = DyeColor.YELLOW;
	protected Material dirtroadMat;
	protected final static DyeColor dirtroadColor = DyeColor.LIME;
	protected boolean dirtroadIsClay;
	
	protected boolean roundaboutRoad;
	private int bottomOfRoad;
	private int topOfRoad;
	public final static int sewerDepth = 32; 
	
	public RoadLot(PlatMap platmap, int chunkX, int chunkZ, long globalconnectionkey, boolean roundaboutPart) {
		super(platmap, chunkX, chunkZ);
		
		style = LotStyle.ROAD;
		connectedkey = globalconnectionkey;
		roundaboutRoad = roundaboutPart;
		
		bottomOfRoad = platmap.generator.streetLevel - 1;
//		if (generator.settings.includeSewers && cityRoad)
//			bottomOfRoad -= DataContext.FloorHeight * 2 + 1;
		topOfRoad = platmap.generator.streetLevel + 1;
		if (blockYs.maxHeight > topOfRoad + tunnelHeight)
			topOfRoad += tunnelHeight;
		
		pavementMat = platmap.generator.materialProvider.itemsMaterialListFor_Roads.getNthMaterial(0, Material.STAINED_CLAY);
		linesMat = platmap.generator.materialProvider.itemsMaterialListFor_Roads.getNthMaterial(1, Material.QUARTZ_BLOCK);
		// paved sidewalk is 2, read in PlatLot
		dirtroadMat = platmap.generator.materialProvider.itemsMaterialListFor_Roads.getNthMaterial(3, Material.DIRT);
		// dirt sidewalk is 4, read in PlatLot

		pavementIsClay = pavementMat == Material.STAINED_CLAY;
		dirtroadIsClay = dirtroadMat == Material.STAINED_CLAY;
	}
	
	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new RoadLot(platmap, chunkX, chunkZ, connectedkey, roundaboutRoad);
	}

	@Override
	public boolean isPlaceableAt(CityWorldGenerator generator, int chunkX, int chunkZ) {
		return generator.settings.inRoadRange(chunkX, chunkZ);
	}
	
	@Override
	public boolean isValidStrataY(CityWorldGenerator generator, int blockX, int blockY, int blockZ) {
		return blockY < bottomOfRoad || blockY > topOfRoad - 5;
	}

	@Override
	protected boolean isShaftableLevel(CityWorldGenerator generator, int blockY) {
		return (blockY < bottomOfRoad - sewerDepth || blockY > topOfRoad + 16 ) && super.isShaftableLevel(generator, blockY);
	}
	
	private boolean sewerCenterBit;
	private boolean sewerNorthWestBias;
	private boolean sewerNorthEastBias;
	private boolean sewerSouthWestBias;
	private boolean sewerSouthEastBias;

	// where are they?
	protected boolean crosswalkNorth = false;
	protected boolean crosswalkSouth = false;
	protected boolean crosswalkWest = false;
	protected boolean crosswalkEast = false;
	protected boolean crosswalksFound = false;
	
	// where are the crosswalks
	protected void calculateCrosswalks(Surroundings roads) {
		if (!crosswalksFound) {
			if (roundaboutRoad) {
				crosswalkNorth = roads.toNorth() && roads.toWest() && roads.toEast();
				crosswalkSouth = roads.toSouth() && roads.toWest() && roads.toEast();
				crosswalkWest = roads.toWest() && roads.toNorth() && roads.toSouth();
				crosswalkEast = roads.toEast() && roads.toNorth() && roads.toSouth();
			} else {
				
				// how many connecting roads are there?
				int roadways = (roads.toNorth() ? 1 : 0) + (roads.toSouth() ? 1 : 0) + (roads.toWest() ? 1 : 0) + (roads.toEast() ? 1 : 0);
				
				// crosswalks for intersections and turns
				boolean crosswalks = roadways == 4 || roadways == 3;
				if (roadways == 2)
					crosswalks = !((roads.toNorth() && roads.toSouth()) || (roads.toWest() && roads.toEast()));
					
				// finally draw the crosswalks
				crosswalkNorth = crosswalks && roads.toNorth();
				crosswalkSouth = crosswalks && roads.toSouth();
				crosswalkWest = crosswalks && roads.toWest();
				crosswalkEast = crosswalks && roads.toEast();
			}
			crosswalksFound = true;
		}
	}
	
	@Override
	public int getBottomY(CityWorldGenerator generator) {
		return generator.streetLevel; //TODO what about sewers? - (generator.settings.includeSewers && cityRoad) ? 
	}
	
	@Override
	public int getTopY(CityWorldGenerator generator) {
		return generator.streetLevel + DataContext.FloorHeight * 2 + 1; //TODO is this really right?
	}
	
	@Override
	protected void generateActualChunk(CityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {
		// moved to other chunk generator
	}
	
	private void placeEWBridgeCap(AbstractBlocks chunk, int x, int baseY, int topY) {
		chunk.setBlocks(x, x + 2, baseY, topY, 0, 16, retainingWallMaterial);
	}
	
	private void placeEWBridgePartA(AbstractBlocks chunk, int x, int baseY) {
		
		// cross beam
		chunk.setBlocks(x, x + 2, baseY - 1, baseY, 0, 16, bridgeEdgeMaterial);
		
		// edges
		chunk.setBlocks(x, x + 2, baseY, baseY + 1, 0, 1, bridgeEdgeMaterial);
		chunk.setBlocks(x, x + 2, baseY, baseY + 1, 15, 16, bridgeEdgeMaterial);
		
		// rails
		chunk.setBlocks(x, x + 2, baseY + 1, baseY + 2, 0, 1, bridgeRailMaterial);
		chunk.setBlocks(x, x + 2, baseY + 1, baseY + 2, 15, 16, bridgeRailMaterial);

		// sidewalks
		chunk.setBlocks(x, x + 2, baseY, baseY + 1, 1, 3, bridgeSidewalk2Material);
		chunk.setBlocks(x, x + 2, baseY, baseY + 1, 13, 15, bridgeSidewalk2Material);
		
		// pavement
		chunk.setBlocks(x, x + 2, baseY, baseY + 1, 3, 13, bridgePavement1Material);
	}
	
	private void placeEWBridgePartB(AbstractBlocks chunk, int x, int baseY) {

		// edges
		chunk.setBlocks(x, x + 2, baseY, baseY + 2, 0, 1, bridgeEdgeMaterial);
		chunk.setBlocks(x, x + 2, baseY, baseY + 2, 15, 16, bridgeEdgeMaterial);
		
		// rails
		chunk.setBlocks(x, x + 2, baseY + 2, baseY + 3, 0, 1, bridgeRailMaterial);
		chunk.setBlocks(x, x + 2, baseY + 2, baseY + 3, 15, 16, bridgeRailMaterial);

		// sidewalks
		chunk.setBlocks(x, x + 2, baseY, baseY + 1, 1, 3, bridgeSidewalk2Material);
		chunk.setBlocks(x, x + 2, baseY, baseY + 1, 13, 15, bridgeSidewalk2Material);
		chunk.setBlocks(x, x + 2, baseY + 1, baseY + 2, 1, 3, bridgeSidewalk1Material);
		chunk.setBlocks(x, x + 2, baseY + 1, baseY + 2, 13, 15, bridgeSidewalk1Material);
		
		// pavement
		chunk.setBlocks(x, x + 2, baseY, baseY + 1, 3, 13, bridgePavement2Material);
	}
	
	private void placeWBridgeColumns(AbstractBlocks chunk, int baseY) {
		chunk.setBlocks(0, 1, blockYs.minHeight, baseY, 2, 4, bridgeEdgeMaterial);
		chunk.setBlocks(0, 1, blockYs.minHeight, baseY, 12, 14, bridgeEdgeMaterial);
	}

	private void placeEBridgeColumns(AbstractBlocks chunk, int baseY) {
		chunk.setBlocks(15, 16, blockYs.minHeight, baseY, 2, 4, bridgeEdgeMaterial);
		chunk.setBlocks(15, 16, blockYs.minHeight, baseY, 12, 14, bridgeEdgeMaterial);
	}

	private void placeNSBridgeCap(AbstractBlocks chunk, int z, int baseY, int topY) {
		chunk.setBlocks(0, 16, baseY, topY - 1, z, z + 2, retainingWallMaterial);
	}
	
	private void placeNSBridgePartA(AbstractBlocks chunk, int z, int baseY) {
		
		// cross beam
		chunk.setBlocks(0, 16, baseY - 1, baseY, z, z + 2, bridgeEdgeMaterial);
		
		// edges
		chunk.setBlocks(0, 1, baseY, baseY + 1, z, z + 2, bridgeEdgeMaterial);
		chunk.setBlocks(15, 16, baseY, baseY + 1, z, z + 2, bridgeEdgeMaterial);
		
		// rails
		chunk.setBlocks(0, 1, baseY + 1, baseY + 2, z, z + 2, bridgeRailMaterial);
		chunk.setBlocks(15, 16, baseY + 1, baseY + 2, z, z + 2, bridgeRailMaterial);

		// sidewalks
		chunk.setBlocks(1, 3, baseY, baseY + 1, z, z + 2, bridgeSidewalk2Material);
		chunk.setBlocks(13, 15, baseY, baseY + 1, z, z + 2, bridgeSidewalk2Material);
		
		// pavement
		chunk.setBlocks(3, 13, baseY, baseY + 1, z, z + 2, bridgePavement1Material);
	}
	
	private void placeNSBridgePartB(AbstractBlocks chunk, int z, int baseY) {
		
		// edges
		chunk.setBlocks(0, 1, baseY, baseY + 2, z, z + 2, bridgeEdgeMaterial);
		chunk.setBlocks(15, 16, baseY, baseY + 2, z, z + 2, bridgeEdgeMaterial);
		
		// rails
		chunk.setBlocks(0, 1, baseY + 2, baseY + 3, z, z + 2, bridgeRailMaterial);
		chunk.setBlocks(15, 16, baseY + 2, baseY + 3, z, z + 2, bridgeRailMaterial);

		// sidewalks
		chunk.setBlocks(1, 3, baseY, baseY + 1, z, z + 2, bridgeSidewalk2Material);
		chunk.setBlocks(13, 15, baseY, baseY + 1, z, z + 2, bridgeSidewalk2Material);
		chunk.setBlocks(1, 3, baseY + 1, baseY + 2, z, z + 2, bridgeSidewalk1Material);
		chunk.setBlocks(13, 15, baseY + 1, baseY + 2, z, z + 2, bridgeSidewalk1Material);
		
		// pavement
		chunk.setBlocks(3, 13, baseY, baseY + 1, z, z + 2, bridgePavement2Material);
	}
	
	private void placeNBridgeColumns(AbstractBlocks chunk, int baseY) {
		chunk.setBlocks(2, 4, blockYs.minHeight, baseY, 0, 1, bridgeEdgeMaterial);
		chunk.setBlocks(12, 14, blockYs.minHeight, baseY, 0, 1, bridgeEdgeMaterial);
	}

	private void placeSBridgeColumns(AbstractBlocks chunk, int baseY) {
		chunk.setBlocks(2, 4, blockYs.minHeight, baseY, 15, 16, bridgeEdgeMaterial);
		chunk.setBlocks(12, 14, blockYs.minHeight, baseY, 15, 16, bridgeEdgeMaterial);
	}
	
	private void placeEWTunnelArch(CityWorldGenerator generator, AbstractBlocks chunk, int x, int baseY, 
			Material shellMaterial, Material tileMaterial, Material ceilingMaterial) {
		chunk.setBlocks(x, baseY - 2, baseY + 4, 0, shellMaterial);

		chunk.setBlocks(x, baseY, baseY + 3, 1, tileMaterial);
		chunk.setBlocks(x, baseY + 3, baseY + 6, 1, shellMaterial);
		
		chunk.setBlocks(x, baseY + 3, baseY + 5, 2, tileMaterial);
		chunk.setBlocks(x, baseY + 5, baseY + 7, 2, shellMaterial);

		chunk.setBlocks(x, baseY + 5, baseY + 6, 3, tileMaterial);
		chunk.setBlocks(x, baseY + 6, baseY + 8, 3, shellMaterial);
		
		chunk.setBlocks(x, x + 1, baseY + 6, baseY + 7, 4, 12, tileMaterial);
		chunk.setBlocks(x, x + 1, baseY + 7, baseY + 8, 4, 12, shellMaterial);
		chunk.setBlocks(x, x + 1, baseY + 8, baseY + 9, 5, 11, shellMaterial);
		
		if (shellMaterial != tileMaterial) {
			chunk.setBlock(x, baseY + 6, 5, ceilingMaterial);
			chunk.setBlock(x, baseY + 6, 10, ceilingMaterial);
			chunk.airoutBlocks(generator, x, x + 1, baseY + 7, baseY + 8, 5, 11);
		}
		
		chunk.setBlocks(x, baseY + 5, baseY + 6, 12, tileMaterial);
		chunk.setBlocks(x, baseY + 6, baseY + 8, 12, shellMaterial);
		
		chunk.setBlocks(x, baseY + 3, baseY + 5, 13, tileMaterial);
		chunk.setBlocks(x, baseY + 5, baseY + 7, 13, shellMaterial);
		
		chunk.setBlocks(x, baseY, baseY + 3, 14, tileMaterial);
		chunk.setBlocks(x, baseY + 3, baseY + 6, 14, shellMaterial);
		
		chunk.setBlocks(x, baseY - 2, baseY + 4, 15, shellMaterial);
	}
	
	private void placeNSTunnelArch(CityWorldGenerator generator, AbstractBlocks chunk, int z, int baseY, 
			Material shellMaterial, Material tileMaterial, Material ceilingMaterial) {
		chunk.setBlocks(0, baseY - 2, baseY + 4, z, shellMaterial);

		chunk.setBlocks(1, baseY, baseY + 3, z, tileMaterial);
		chunk.setBlocks(1, baseY + 3, baseY + 6, z, shellMaterial);
		
		chunk.setBlocks(2, baseY + 3, baseY + 5, z, tileMaterial);
		chunk.setBlocks(2, baseY + 5, baseY + 7, z, shellMaterial);

		chunk.setBlocks(3, baseY + 5, baseY + 6, z, tileMaterial);
		chunk.setBlocks(3, baseY + 6, baseY + 8, z, shellMaterial);
		
		chunk.setBlocks(4, 12, baseY + 6, baseY + 7, z, z + 1, tileMaterial);
		chunk.setBlocks(4, 12, baseY + 7, baseY + 8, z, z + 1, shellMaterial);
		chunk.setBlocks(5, 11, baseY + 8, baseY + 9, z, z + 1, shellMaterial);
		
		if (shellMaterial != tileMaterial) {
			chunk.setBlock(5, baseY + 6, z, ceilingMaterial);
			chunk.setBlock(10, baseY + 6, z, ceilingMaterial);
			chunk.airoutBlocks(generator, 5, 11, baseY + 7, baseY + 8, z, z + 1);
		}
		
		chunk.setBlocks(12, baseY + 5, baseY + 6, z, tileMaterial);
		chunk.setBlocks(12, baseY + 6, baseY + 8, z, shellMaterial);
		
		chunk.setBlocks(13, baseY + 3, baseY + 5, z, tileMaterial);
		chunk.setBlocks(13, baseY + 5, baseY + 7, z, shellMaterial);
		
		chunk.setBlocks(14, baseY, baseY + 3, z, tileMaterial);
		chunk.setBlocks(14, baseY + 3, baseY + 6, z, shellMaterial);
		
		chunk.setBlocks(15, baseY - 2, baseY + 4, z, shellMaterial);
	}
	
	private void placeRetainingWall(AbstractBlocks chunk, int x, int z, int baseY, int topY) {
		chunk.setBlocks(x, baseY, topY + 1, z, retainingWallMaterial);
		if (topY > baseY + fenceHeight)
			chunk.setBlocks(x, topY + 1, topY + fenceHeight + 1, z, retainingFenceMaterial);
	}
	
	@Override
	protected void generateActualBlocks(CityWorldGenerator generator, PlatMap platmap, RealBlocks chunk, DataContext context, int platX, int platZ) {

		// random bits
		sewerCenterBit = chunkOdds.flipCoin();
		sewerNorthWestBias = chunkOdds.flipCoin();
		sewerNorthEastBias = chunkOdds.flipCoin();
		sewerSouthWestBias = chunkOdds.flipCoin();
		sewerSouthEastBias = chunkOdds.flipCoin();
		
		// compute offset to start of chunk
		int originX = chunk.getOriginX();
		int originZ = chunk.getOriginZ();
		
		// where do we start
		int base1Y = generator.streetLevel - DataContext.FloorHeight * 2 + 1;
		int sewerY = base1Y + 1;
		int base2Y = base1Y + DataContext.FloorHeight + 1;
		int pavementLevel = generator.streetLevel;
		boolean doSewer = generator.settings.includeSewers && inACity;
		
		//chunk.setBlocks(6, 10, pavementLevel + 32, 6, 10, Material.GLOWSTONE);
		
		// look around
		SurroundingRoads roads = new SurroundingRoads(platmap, platX, platZ);
		
		// what are we making?
		if (HeightInfo.getHeightsFast(generator, originX, originZ).isSea()) {
			doSewer = false;

			// clear a little space
			chunk.airoutLayer(generator, pavementLevel, 4);
			
			// bridge to the east/west
			if (roads.toWest() && roads.toEast()) {
				
				// more bridge beside this one?
				boolean toWest = HeightInfo.getHeightsFast(generator, originX - chunk.width, originZ).isSea();
				boolean toEast = HeightInfo.getHeightsFast(generator, originX + chunk.width, originZ).isSea();
				
				if (toWest) {
					
					// tall span
					if (toEast) {
						placeWBridgeColumns(chunk, pavementLevel + 5);
						placeEWBridgePartA(chunk, 0, pavementLevel + 5);
						placeEWBridgePartA(chunk, 2, pavementLevel + 5);
						placeEWBridgePartA(chunk, 4, pavementLevel + 5);
						placeEWBridgePartA(chunk, 6, pavementLevel + 5);
						placeEWBridgePartA(chunk, 8, pavementLevel + 5);
						placeEWBridgePartA(chunk, 10, pavementLevel + 5);
						placeEWBridgePartA(chunk, 12, pavementLevel + 5);
						placeEWBridgePartA(chunk, 14, pavementLevel + 5);
						placeEBridgeColumns(chunk, pavementLevel + 5);
						
						
					// ramp down
					} else {
						placeEWBridgeCap(chunk, 14, base1Y, pavementLevel + 1);
						placeEWBridgePartA(chunk, 14, pavementLevel + 1);
						placeEWBridgePartB(chunk, 12, pavementLevel + 1);
						placeEWBridgePartA(chunk, 10, pavementLevel + 2);
						placeEWBridgePartB(chunk, 8, pavementLevel + 2);
						placeEWBridgePartA(chunk, 6, pavementLevel + 3);
						placeEWBridgePartB(chunk, 4, pavementLevel + 3);
						placeEWBridgePartA(chunk, 2, pavementLevel + 4);
						placeEWBridgePartB(chunk, 0, pavementLevel + 4);
						placeWBridgeColumns(chunk, pavementLevel + 4);
					}
						
					
				} else {
					
					// ramp up
					if (toEast) {
						placeEWBridgeCap(chunk, 0, base1Y, pavementLevel + 1);
						placeEWBridgePartA(chunk, 0, pavementLevel + 1);
						placeEWBridgePartB(chunk, 2, pavementLevel + 1);
						placeEWBridgePartA(chunk, 4, pavementLevel + 2);
						placeEWBridgePartB(chunk, 6, pavementLevel + 2);
						placeEWBridgePartA(chunk, 8, pavementLevel + 3);
						placeEWBridgePartB(chunk, 10, pavementLevel + 3);
						placeEWBridgePartA(chunk, 12, pavementLevel + 4);
						placeEWBridgePartB(chunk, 14, pavementLevel + 4);
						placeEBridgeColumns(chunk, pavementLevel + 4);
						
					// short span
					} else {
						placeEWBridgeCap(chunk, 0, base1Y, pavementLevel + 1);
						placeEWBridgePartA(chunk, 0, pavementLevel + 1);
						placeEWBridgePartB(chunk, 2, pavementLevel + 1);
						placeEWBridgePartA(chunk, 4, pavementLevel + 2);
						placeEWBridgePartA(chunk, 6, pavementLevel + 2);
						placeEWBridgePartA(chunk, 8, pavementLevel + 2);
						placeEWBridgePartA(chunk, 10, pavementLevel + 2);
						placeEWBridgePartB(chunk, 12, pavementLevel + 1);
						placeEWBridgePartA(chunk, 14, pavementLevel + 1);
						placeEWBridgeCap(chunk, 14, base1Y, pavementLevel + 1);
					}
				}
				
			} else if (roads.toNorth() && roads.toSouth()) {
				
				// more bridge beside this one?
				boolean toNorth = HeightInfo.getHeightsFast(generator, originX, originZ - chunk.width).isSea();
				boolean toSouth = HeightInfo.getHeightsFast(generator, originX, originZ + chunk.width).isSea();
				
				if (toNorth) {
					
					// tall span
					if (toSouth) {
						placeNBridgeColumns(chunk, pavementLevel + 5);
						placeNSBridgePartA(chunk, 0, pavementLevel + 5);
						placeNSBridgePartA(chunk, 2, pavementLevel + 5);
						placeNSBridgePartA(chunk, 4, pavementLevel + 5);
						placeNSBridgePartA(chunk, 6, pavementLevel + 5);
						placeNSBridgePartA(chunk, 8, pavementLevel + 5);
						placeNSBridgePartA(chunk, 10, pavementLevel + 5);
						placeNSBridgePartA(chunk, 12, pavementLevel + 5);
						placeNSBridgePartA(chunk, 14, pavementLevel + 5);
						placeSBridgeColumns(chunk, pavementLevel + 5);
						
					// ramp down
					} else {
						placeNSBridgeCap(chunk, 14, base1Y, pavementLevel + 1);
						placeNSBridgePartA(chunk, 14, pavementLevel + 1);
						placeNSBridgePartB(chunk, 12, pavementLevel + 1);
						placeNSBridgePartA(chunk, 10, pavementLevel + 2);
						placeNSBridgePartB(chunk, 8, pavementLevel + 2);
						placeNSBridgePartA(chunk, 6, pavementLevel + 3);
						placeNSBridgePartB(chunk, 4, pavementLevel + 3);
						placeNSBridgePartA(chunk, 2, pavementLevel + 4);
						placeNSBridgePartB(chunk, 0, pavementLevel + 4);
						placeNBridgeColumns(chunk, pavementLevel + 4);
					}
					
				} else {
					
					// ramp up
					if (toSouth) {
						placeNSBridgeCap(chunk, 0, base1Y, pavementLevel + 1);
						placeNSBridgePartA(chunk, 0, pavementLevel + 1);
						placeNSBridgePartB(chunk, 2, pavementLevel + 1);
						placeNSBridgePartA(chunk, 4, pavementLevel + 2);
						placeNSBridgePartB(chunk, 6, pavementLevel + 2);
						placeNSBridgePartA(chunk, 8, pavementLevel + 3);
						placeNSBridgePartB(chunk, 10, pavementLevel + 3);
						placeNSBridgePartA(chunk, 12, pavementLevel + 4);
						placeNSBridgePartB(chunk, 14, pavementLevel + 4);
						placeSBridgeColumns(chunk, pavementLevel + 4);
						
					// short span
					} else {
						placeNSBridgeCap(chunk, 0, base1Y, pavementLevel + 1);
						placeNSBridgePartA(chunk, 0, pavementLevel + 1);
						placeNSBridgePartB(chunk, 2, pavementLevel + 1);
						placeNSBridgePartA(chunk, 4, pavementLevel + 2);
						placeNSBridgePartA(chunk, 6, pavementLevel + 2);
						placeNSBridgePartA(chunk, 8, pavementLevel + 2);
						placeNSBridgePartA(chunk, 10, pavementLevel + 2);
						placeNSBridgePartB(chunk, 12, pavementLevel + 1);
						placeNSBridgePartA(chunk, 14, pavementLevel + 1);
						placeNSBridgeCap(chunk, 14, base1Y, pavementLevel + 1);
					}
				}
			}

			// draw a bridge bits
			// bridge to the east/west
			if (inACity) {
				if (roads.toWest() && roads.toEast()) {
					if (HeightInfo.getHeightsFast(generator, originX - chunk.width, originZ).isSea() &&
						HeightInfo.getHeightsFast(generator, originX + chunk.width, originZ).isSea()) {
						
						// lights please
						generateLightPost(generator, chunk, context, pavementLevel + 6, 7, 0);
						generateLightPost(generator, chunk, context, pavementLevel + 6, 8, 15);
					}
					
				} else if (roads.toNorth() && roads.toSouth()) {
					if (HeightInfo.getHeightsFast(generator, originX, originZ - chunk.width).isSea() && 
						HeightInfo.getHeightsFast(generator, originX, originZ + chunk.width).isSea()) {
						
						// lights please
						generateLightPost(generator, chunk, context, pavementLevel + 6, 0, 7);
						generateLightPost(generator, chunk, context, pavementLevel + 6, 15, 8);
					}
				}
			}
		
			// not a happy place?
			if (generator.settings.includeDecayedRoads)
				destroyLot(generator, pavementLevel + 5, pavementLevel + 7);
			
			
			// half as likely in the middle of the road
			generateEntities(generator, chunk, pavementLevel + 6);
			
		} else {
			int sidewalkLevel = getSidewalkLevel(generator);
			
			// clear out a bit and draw pavement
//			Material emptyMaterial = getAirMaterial(generator, sidewalkLevel);
			flattenLot(generator, chunk, 4);
			if (pavementLevel != sidewalkLevel)
				chunk.airoutLayer(generator, sidewalkLevel);
			paveRoadLot(generator, chunk, pavementLevel);
			
			// sidewalk corners
			paveSidewalk(generator, chunk, 0, sidewalkWidth, sidewalkLevel, 0, sidewalkWidth);
			paveSidewalk(generator, chunk, 0, sidewalkWidth, sidewalkLevel, chunk.width - sidewalkWidth, chunk.width);
			paveSidewalk(generator, chunk, chunk.width - sidewalkWidth, chunk.width, sidewalkLevel, 0, sidewalkWidth);
			paveSidewalk(generator, chunk, chunk.width - sidewalkWidth, chunk.width, sidewalkLevel, chunk.width - sidewalkWidth, chunk.width);
			
			// sidewalk edges
			if (!roads.toWest())
				paveSidewalk(generator, chunk, 0, sidewalkWidth, sidewalkLevel, sidewalkWidth, chunk.width - sidewalkWidth);
			if (!roads.toEast())
				paveSidewalk(generator, chunk, chunk.width - sidewalkWidth, chunk.width, sidewalkLevel, sidewalkWidth, chunk.width - sidewalkWidth);
			if (!roads.toNorth())
				paveSidewalk(generator, chunk, sidewalkWidth, chunk.width - sidewalkWidth, sidewalkLevel, 0, sidewalkWidth);
			if (!roads.toSouth())
				paveSidewalk(generator, chunk, sidewalkWidth, chunk.width - sidewalkWidth, sidewalkLevel, chunk.width - sidewalkWidth, chunk.width);
			
			// crosswalks?
			if (inACity) {
				calculateCrosswalks(roads);
				
				// draw the crosswalk bits
				generateNSCrosswalk(generator, chunk, sidewalkWidth, chunk.width - sidewalkWidth, pavementLevel, 0, sidewalkWidth, crosswalkNorth);
				generateNSCrosswalk(generator, chunk, sidewalkWidth, chunk.width - sidewalkWidth, pavementLevel, chunk.width - sidewalkWidth, chunk.width, crosswalkSouth);
				generateWECrosswalk(generator, chunk, 0, sidewalkWidth, pavementLevel, sidewalkWidth, chunk.width - sidewalkWidth, crosswalkWest);
				generateWECrosswalk(generator, chunk, chunk.width - sidewalkWidth, chunk.width, pavementLevel, sidewalkWidth, chunk.width - sidewalkWidth, crosswalkEast);
			}
			
			// tunnel walls please
			if (blockYs.maxHeight > pavementLevel + tunnelHeight + 1) {
				doSewer = false;
				
//				// draw pavement
//				chunk.setLayer(pavementLevel - 1, 2, pavementId);
				
				// tunnel to the east/west
				if (roads.toWest() && roads.toEast()) {
					
					// carve out the tunnel
					chunk.airoutBlocks(generator, 0, 16, pavementLevel + 2, pavementLevel + 7, 2, 14);
					
					// place the arches
					placeEWTunnelArch(generator, chunk, 0, pavementLevel + 1, tunnelWallMaterial, tunnelWallMaterial, tunnelWallMaterial);
					for (int x = 1; x < chunk.width - 1; x++) {
						placeEWTunnelArch(generator, chunk, x, pavementLevel + 1, tunnelWallMaterial, tunnelTileMaterial, tunnelCeilingMaterial);
					}
					placeEWTunnelArch(generator, chunk, 15, pavementLevel + 1, tunnelWallMaterial, tunnelWallMaterial, tunnelWallMaterial);
					
				} else if (roads.toNorth() && roads.toSouth()) {
					
					// carve out the tunnel
					chunk.airoutBlocks(generator, 2, 14, pavementLevel + 2, pavementLevel + 7, 0, 16);
					
					// place the arches
					placeNSTunnelArch(generator, chunk, 0, pavementLevel + 1, tunnelWallMaterial, tunnelWallMaterial, tunnelWallMaterial);
					for (int z = 1; z < chunk.width - 1; z++) {
						placeNSTunnelArch(generator, chunk, z, pavementLevel + 1, tunnelWallMaterial, tunnelTileMaterial, tunnelCeilingMaterial);
					}
					placeNSTunnelArch(generator, chunk, 15, pavementLevel + 1, tunnelWallMaterial, tunnelWallMaterial, tunnelWallMaterial);
				}
				
			// retaining walls please
			} else if (blockYs.maxHeight > pavementLevel + 1) {
				int offset = inACity ? 2 : 1;
				
				// wall to the east/west
				if (roads.toWest() && roads.toEast()) {
					
					// carve out the tunnel
					chunk.airoutBlocks(generator, 0, 16, pavementLevel + offset, pavementLevel + tunnelHeight + 2, 0, 16);
					
					// walls please, this will find the Y the hard way since we are looking at the next chunk over
					for (int x = 0; x < chunk.width; x++) {
						placeRetainingWall(chunk, x, 0, pavementLevel + 1, generator.getFarBlockY(originX + x, originZ - 1));
						placeRetainingWall(chunk, x, 15, pavementLevel + 1, generator.getFarBlockY(originX + x, originZ + 16));
					}
				} else if (roads.toNorth() && roads.toSouth()) {

					// carve out the tunnel
					chunk.airoutBlocks(generator, 0, 16, pavementLevel + offset, pavementLevel + tunnelHeight + 2, 0, 16);

					// walls please, this will find the Y the hard way since we are looking at the next chunk over
					for (int z = 0; z < chunk.width; z++) {
						placeRetainingWall(chunk, 0, z, pavementLevel + 1, generator.getFarBlockY(originX - 1, originZ + z));
						placeRetainingWall(chunk, 15, z, pavementLevel + 1, generator.getFarBlockY(originX + 16, originZ + z));
					}
				}
							
			// stuff that only can happen outside of tunnels and bridges
			} else {
				
				// round things out
				if (!roads.toWest() && roads.toEast() && !roads.toNorth() && roads.toSouth())
					generateRoundedOut(generator, context, chunk, sidewalkWidth, sidewalkWidth, 
							false, false);
				if (!roads.toWest() && roads.toEast() && roads.toNorth() && !roads.toSouth())
					generateRoundedOut(generator, context, chunk, sidewalkWidth, chunk.width - sidewalkWidth - 4, 
							false, true);
				if (roads.toWest() && !roads.toEast() && !roads.toNorth() && roads.toSouth())
					generateRoundedOut(generator, context, chunk, chunk.width - sidewalkWidth - 4, sidewalkWidth, 
							true, false);
				if (roads.toWest() && !roads.toEast() && roads.toNorth() && !roads.toSouth())
					generateRoundedOut(generator, context, chunk, chunk.width - sidewalkWidth - 4, chunk.width - sidewalkWidth - 4, 
							true, true);
			}

			// crosswalks?
			calculateCrosswalks(roads);
			
//			// center bit
//			chunk.setClay(sidewalkWidth, chunk.width - sidewalkWidth, pavementLevel, sidewalkWidth, chunk.width - sidewalkWidth, pavementColor);
		
			// draw the crosswalk bits
			generateNSCrosswalk(generator, chunk, sidewalkWidth, chunk.width - sidewalkWidth, pavementLevel, 0, sidewalkWidth, crosswalkNorth);
			generateNSCrosswalk(generator, chunk, sidewalkWidth, chunk.width - sidewalkWidth, pavementLevel, chunk.width - sidewalkWidth, chunk.width, crosswalkSouth);
			generateWECrosswalk(generator, chunk, 0, sidewalkWidth, pavementLevel, sidewalkWidth, chunk.width - sidewalkWidth, crosswalkWest);
			generateWECrosswalk(generator, chunk, chunk.width - sidewalkWidth, chunk.width, pavementLevel, sidewalkWidth, chunk.width - sidewalkWidth, crosswalkEast);
			
			// decay please
			if (generator.settings.includeDecayedRoads) {

				// center bit
				decayRoad(chunk, sidewalkWidth, chunk.width - sidewalkWidth, pavementLevel, sidewalkWidth, chunk.width - sidewalkWidth);
				
				// road to the whatever
				if (roads.toNorth())
					decayRoad(chunk, sidewalkWidth, chunk.width - sidewalkWidth, pavementLevel, 0, sidewalkWidth);
				if (roads.toSouth())
					decayRoad(chunk, sidewalkWidth, chunk.width - sidewalkWidth, pavementLevel, chunk.width - sidewalkWidth, chunk.width);
				if (roads.toWest())
					decayRoad(chunk, 0, sidewalkWidth, pavementLevel, sidewalkWidth, chunk.width - sidewalkWidth);
				if (roads.toEast())
					decayRoad(chunk, chunk.width - sidewalkWidth, chunk.width, pavementLevel, sidewalkWidth, chunk.width - sidewalkWidth);

				// sidewalk corners
				decaySidewalk(generator, chunk, 0, sidewalkWidth, sidewalkLevel, 0, sidewalkWidth);
				decaySidewalk(generator, chunk, 0, sidewalkWidth, sidewalkLevel, chunk.width - sidewalkWidth, chunk.width);
				decaySidewalk(generator, chunk, chunk.width - sidewalkWidth, chunk.width, sidewalkLevel, 0, sidewalkWidth);
				decaySidewalk(generator, chunk, chunk.width - sidewalkWidth, chunk.width, sidewalkLevel, chunk.width - sidewalkWidth, chunk.width);
				
				// sidewalk edges
				if (!roads.toWest())
					decaySidewalk(generator, chunk, 0, sidewalkWidth, sidewalkLevel, sidewalkWidth, chunk.width - sidewalkWidth);
				if (!roads.toEast())
					decaySidewalk(generator, chunk, chunk.width - sidewalkWidth, chunk.width, sidewalkLevel, sidewalkWidth, chunk.width - sidewalkWidth);
				if (!roads.toNorth())
					decaySidewalk(generator, chunk, sidewalkWidth, chunk.width - sidewalkWidth, sidewalkLevel, 0, sidewalkWidth);
				if (!roads.toSouth())
					decaySidewalk(generator, chunk, sidewalkWidth, chunk.width - sidewalkWidth, sidewalkLevel, chunk.width - sidewalkWidth, chunk.width);
				
			}
			
			// tunnel please
			if (blockYs.maxHeight > pavementLevel + tunnelHeight + 1) {
				doSewer = false;
				
				// tunnel to the east/west
				if (inACity) {
					if (roads.toWest() && roads.toEast()) {
						chunk.setBlock(3, pavementLevel + 8, 8, context.lightMat);
						chunk.setBlock(12, pavementLevel + 8, 7, context.lightMat);
					} else if (roads.toNorth() && roads.toSouth()) {
						chunk.setBlock(8, pavementLevel + 8, 3, context.lightMat);
						chunk.setBlock(7, pavementLevel + 8, 12, context.lightMat);
					}
				}
				
				// add nature on top
				generateSurface(generator, chunk, true);
				
				//TODO decay tunnels please!
				
			// stuff that only can happen outside of tunnels and bridges
			} else {
				
				// light posts
				if (inACity) {
					boolean lightPostNW = generateLightPost(generator, chunk, context, sidewalkLevel, sidewalkWidth - 1, sidewalkWidth - 1);
					boolean lightPostSE = generateLightPost(generator, chunk, context, sidewalkLevel, chunk.width - sidewalkWidth, chunk.width - sidewalkWidth);
					
					// put signs up?
					if (generator.settings.includeNamedRoads) {
						
						// if we haven't calculated crosswalks yet do so
						calculateCrosswalks(roads);
						
						// add the signs
						if (lightPostNW && (crosswalkNorth || crosswalkWest))
							generateStreetSign(generator, chunk, sidewalkLevel, sidewalkWidth - 1, sidewalkWidth - 1);
						if (lightPostSE && (crosswalkSouth || crosswalkEast))
							generateStreetSign(generator, chunk, sidewalkLevel, chunk.width - sidewalkWidth, chunk.width - sidewalkWidth);
					}
				}
				
				// half as likely in the middle of the road
				generateEntities(generator, chunk, sidewalkLevel);
			}
		}
		
		// sewer?
		if (doSewer) {
			
			// defaults
			boolean superConnected = chunkOdds.playOdds(Odds.oddsPrettyLikely);
			boolean vaultNorthWest = false;
			boolean vaultSouthWest = false;
			boolean vaultNorthEast = false;
			boolean vaultSouthEast = false;
			boolean centerNorth = !roads.toNorth();
			boolean centerSouth = !roads.toSouth();
			boolean centerWest = !roads.toWest();
			boolean centerEast = !roads.toEast();
			
			// empty out the sewer
			chunk.airoutLayer(generator, base1Y, base2Y - base1Y);
//			Material emptyMaterial = getAirMaterial(generator, sewerY - 1);
					
			// draw the floor of the sewer
			chunk.setBlocks(0, 16, sewerY - 1, sewerY, 0, 16, sewerMaterial, chunkOdds, sewerFloorData, sewerCeilingData);
			chunk.airoutBlocks(generator, crossDitchEdge, chunk.width - crossDitchEdge, 
										  sewerY - 1, sewerY, 
										  crossDitchEdge, chunk.width - crossDitchEdge);
			
			// corner bits
			chunk.setBlocks(0, 6, sewerY, base2Y, 0, 1, sewerMaterial, chunkOdds, sewerWallData, sewerFloorData);
			chunk.setBlocks(0, 1, sewerY, base2Y, 1, 6, sewerMaterial, chunkOdds, sewerWallData, sewerFloorData);
			chunk.setBlocks(10, 16, sewerY, base2Y, 0, 1, sewerMaterial, chunkOdds, sewerWallData, sewerFloorData);
			chunk.setBlocks(15, 16, sewerY, base2Y, 1, 6, sewerMaterial, chunkOdds, sewerWallData, sewerFloorData);
			chunk.setBlocks(0, 6, sewerY, base2Y, 15, 16, sewerMaterial, chunkOdds, sewerWallData, sewerFloorData);
			chunk.setBlocks(0, 1, sewerY, base2Y, 10, 15, sewerMaterial, chunkOdds, sewerWallData, sewerFloorData);
			chunk.setBlocks(10, 16, sewerY, base2Y, 15, 16, sewerMaterial, chunkOdds, sewerWallData, sewerFloorData);
			chunk.setBlocks(15, 16, sewerY, base2Y, 10, 15, sewerMaterial, chunkOdds, sewerWallData, sewerFloorData);
			
			// cross beams
			chunk.setBlocks(6, 10, base2Y - 1, base2Y, 0, 1, sewerMaterial, chunkOdds, sewerCeilingData, sewerWallData);
			chunk.setBlocks(6, 10, base2Y - 1, base2Y, 15, 16, sewerMaterial, chunkOdds, sewerCeilingData, sewerWallData);
			chunk.setBlocks(0, 1, base2Y - 1, base2Y, 6, 10, sewerMaterial, chunkOdds, sewerCeilingData, sewerWallData);
			chunk.setBlocks(15, 16, base2Y - 1, base2Y, 6, 10, sewerMaterial, chunkOdds, sewerCeilingData, sewerWallData);
			
			// cardinal directions known walls and ditches
			if (!superConnected && !roads.toNorth()) {
				chunk.setBlocks(5, 11, sewerY, base2Y, 0, 1, sewerMaterial, chunkOdds, sewerWallData, sewerFloorData);
				chunk.setBlocks(5, 11, base2Y - 1, base2Y, 1, 2, sewerMaterial, chunkOdds, sewerCeilingData, sewerWallData);
			} else {
				chunk.airoutBlocks(generator, 7, 9, sewerY - 1, sewerY, 0, 7);
			}
			if (!superConnected && !roads.toSouth()) {
				chunk.setBlocks(5, 11, sewerY, base2Y, 15, 16, sewerMaterial, chunkOdds, sewerWallData, sewerFloorData);
				chunk.setBlocks(5, 11, base2Y - 1, base2Y, 14, 15, sewerMaterial, chunkOdds, sewerCeilingData, sewerWallData);
			} else {
				chunk.airoutBlocks(generator, 7, 9, sewerY - 1, sewerY, 9, 16);
			}
			if (!superConnected && !roads.toWest()) {
				chunk.setBlocks(0, 1, sewerY, base2Y, 5, 11, sewerMaterial, chunkOdds, sewerWallData, sewerFloorData);
				chunk.setBlocks(1, 2, base2Y - 1, base2Y, 5, 11, sewerMaterial, chunkOdds, sewerCeilingData, sewerWallData);
			} else {
				chunk.airoutBlocks(generator, 0, 7, sewerY - 1, sewerY, 7, 9);
			}
			if (!superConnected && !roads.toEast()) {
				chunk.setBlocks(15, 16, sewerY, base2Y, 5, 11, sewerMaterial, chunkOdds, sewerWallData, sewerFloorData);
				chunk.setBlocks(14, 15, base2Y - 1, base2Y, 5, 11, sewerMaterial, chunkOdds, sewerCeilingData, sewerWallData);
			} else {
				chunk.airoutBlocks(generator, 9, 16, sewerY - 1, sewerY, 7, 9);
			}
			
			// show our bias
			if (roads.toNorth()) {
				vaultNorthWest = sewerNorthWestBias;
				vaultNorthEast = sewerNorthEastBias;
			}
			if (roads.toSouth()) {
				vaultSouthWest = sewerSouthWestBias;
				vaultSouthEast = sewerSouthEastBias;
			}
			if (roads.toWest()) {
				vaultNorthWest = sewerNorthWestBias;
				vaultSouthWest = sewerSouthWestBias;
			}
			if (roads.toEast()) {
				vaultNorthEast = sewerNorthEastBias;
				vaultSouthEast = sewerSouthEastBias;
			}
			
			// make sure there is a way down
			if (roads.toNorth() && roads.toWest()) {
				vaultNorthWest = true;
			}
			
			// figure out the center
			if (!(vaultNorthWest && vaultNorthEast && vaultSouthWest && vaultSouthEast)) {
				centerNorth = sewerCenterBit || (vaultNorthWest && vaultNorthEast);
				centerSouth = sewerCenterBit || (vaultSouthWest && vaultSouthEast);
				centerWest = sewerCenterBit || (vaultNorthWest && vaultSouthWest);
				centerEast = sewerCenterBit || (vaultNorthEast && vaultSouthEast);
			}
			
			// show the vaults
			if (vaultNorthWest) {
				chunk.setBlocks(4, 5, sewerY, base2Y - 1, 1, 4, sewerMaterial, chunkOdds, sewerWallData, sewerFloorData);
				chunk.setBlocks(1, 4, sewerY, base2Y - 1, 4, 5, sewerMaterial, chunkOdds, sewerWallData, sewerFloorData);
				chunk.setBlocks(1, 6, base2Y - 1, base2Y, 1, 6, sewerMaterial, chunkOdds, sewerCeilingData, sewerWallData);
			} else {
				chunk.setBlocks(1, 6, base2Y - 1, base2Y, 1, 2, sewerMaterial, chunkOdds, sewerCeilingData, sewerWallData);
				chunk.setBlocks(1, 2, base2Y - 1, base2Y, 2, 6, sewerMaterial, chunkOdds, sewerCeilingData, sewerWallData);
			}
			if (vaultSouthWest) {
				chunk.setBlocks(4, 5, sewerY, base2Y - 1, 12, 15, sewerMaterial, chunkOdds, sewerWallData, sewerFloorData);
				chunk.setBlocks(1, 4, sewerY, base2Y - 1, 11, 12, sewerMaterial, chunkOdds, sewerWallData, sewerFloorData);
				chunk.setBlocks(1, 6, base2Y - 1, base2Y, 10, 15, sewerMaterial, sewerCeilingData);
			} else {
				chunk.setBlocks(1, 6, base2Y - 1, base2Y, 14, 15, sewerMaterial, chunkOdds, sewerCeilingData, sewerWallData);
				chunk.setBlocks(1, 2, base2Y - 1, base2Y, 10, 14, sewerMaterial, chunkOdds, sewerCeilingData, sewerWallData);
			}
			if (vaultNorthEast) {
				chunk.setBlocks(11, 12, sewerY, base2Y - 1, 1, 4, sewerMaterial, chunkOdds, sewerWallData, sewerFloorData);
				chunk.setBlocks(12, 15, sewerY, base2Y - 1, 4, 5, sewerMaterial, chunkOdds, sewerWallData, sewerFloorData);
				chunk.setBlocks(10, 15, base2Y - 1, base2Y, 1, 6, sewerMaterial, chunkOdds, sewerCeilingData, sewerWallData);
			} else {
				chunk.setBlocks(10, 15, base2Y - 1, base2Y, 1, 2, sewerMaterial, chunkOdds, sewerCeilingData, sewerWallData);
				chunk.setBlocks(14, 15, base2Y - 1, base2Y, 2, 6, sewerMaterial, chunkOdds, sewerCeilingData, sewerWallData);
			}
			if (vaultSouthEast) {
				chunk.setBlocks(11, 12, sewerY, base2Y - 1, 12, 15, sewerMaterial, chunkOdds, sewerWallData, sewerFloorData);
				chunk.setBlocks(12, 15, sewerY, base2Y - 1, 11, 12, sewerMaterial, chunkOdds, sewerWallData, sewerFloorData);
				chunk.setBlocks(10, 15, base2Y - 1, base2Y, 10, 15, sewerMaterial, chunkOdds, sewerCeilingData, sewerWallData);
			} else {
				chunk.setBlocks(10, 15, base2Y - 1, base2Y, 14, 15, sewerMaterial, chunkOdds, sewerCeilingData, sewerWallData);
				chunk.setBlocks(14, 15, base2Y - 1, base2Y, 10, 14, sewerMaterial, chunkOdds, sewerCeilingData, sewerWallData);
			}
			
			// show the center center
			if (centerNorth) {
				chunk.setBlocks(4, 12, sewerY, base2Y - 1, 4, 5, sewerMaterial, chunkOdds, sewerWallData, sewerFloorData);
				chunk.setBlocks(3, 13, base2Y - 1, base2Y, 3, 6, sewerMaterial, chunkOdds, sewerCeilingData, sewerWallData);
			}
			if (centerSouth) {
				chunk.setBlocks(4, 12, sewerY, base2Y - 1, 11, 12, sewerMaterial, chunkOdds, sewerWallData, sewerFloorData);
				chunk.setBlocks(3, 13, base2Y - 1, base2Y, 10, 13, sewerMaterial, chunkOdds, sewerCeilingData, sewerWallData);
			} 
			if (centerWest) {
				chunk.setBlocks(4, 5, sewerY, base2Y - 1, 4, 12, sewerMaterial, chunkOdds, sewerWallData, sewerFloorData);
				chunk.setBlocks(3, 6, base2Y - 1, base2Y, 3, 13, sewerMaterial, chunkOdds, sewerCeilingData, sewerWallData);
			}
			if (centerEast) {
				chunk.setBlocks(11, 12, sewerY, base2Y - 1, 4, 12, sewerMaterial, chunkOdds, sewerWallData, sewerFloorData);
				chunk.setBlocks(10, 13, base2Y - 1, base2Y, 3, 13, sewerMaterial, chunkOdds, sewerCeilingData, sewerWallData);
			}
			
			// ceiling please
			chunk.setBlocks(0, 16, base2Y, base2Y + 1, 0, 16, sewerMaterial, chunkOdds, sewerCeilingData, sewerWallData);
			
			// show our bias
			if (roads.toNorth()) {
				vaultNorthWest = sewerNorthWestBias;
				vaultNorthEast = sewerNorthEastBias;
			}
			if (roads.toSouth()) {
				vaultSouthWest = sewerSouthWestBias;
				vaultSouthEast = sewerSouthEastBias;
			}
			if (roads.toWest()) {
				vaultNorthWest = sewerNorthWestBias;
				vaultSouthWest = sewerSouthWestBias;
			}
			if (roads.toEast()) {
				vaultNorthEast = sewerNorthEastBias;
				vaultSouthEast = sewerSouthEastBias;
			}
			
			// make sure there is a way down
			if (roads.toNorth() && roads.toWest()) {
				vaultNorthWest = true;
				
				// place the manhole
				chunk.setTrapDoor(3, pavementLevel, 2, BadMagic.TrapDoor.TOP_WEST);
				
				// ladder
				chunk.setLadder(3, sewerY, pavementLevel, 2, BlockFace.EAST);
			}
			
			// figure out the center
			if (!(vaultNorthWest && vaultNorthEast && vaultSouthWest && vaultSouthEast)) {
				centerNorth = sewerCenterBit || (vaultNorthWest && vaultNorthEast);
				centerSouth = sewerCenterBit || (vaultSouthWest && vaultSouthEast);
				centerWest = sewerCenterBit || (vaultNorthWest && vaultSouthWest);
				centerEast = sewerCenterBit || (vaultNorthEast && vaultSouthEast);
			}
			
			Material fluidMaterial = generator.oreProvider.fluidFluidMaterial;
			
			// cardinal directions known walls and ditches
			try {
				chunk.setDoPhysics(true);
				if (superConnected || roads.toNorth()) {
					chunk.setBlock(8, sewerY - 1, 2, fluidMaterial);
					chunk.setBlock(7, sewerY - 1, 3, fluidMaterial);
					chunk.setBlock(8, sewerY - 1, 4, fluidMaterial);
					generateEntryVines(chunk, base2Y - 1, BadMagic.Vine.NORTH, 6, 1, 7, 1, 8, 1, 9, 1);
				}
				if (superConnected || roads.toSouth()) {
					chunk.setBlock(7, sewerY - 1, 11, fluidMaterial);
					chunk.setBlock(8, sewerY - 1, 12, fluidMaterial);
					chunk.setBlock(7, sewerY - 1, 13, fluidMaterial);
					generateEntryVines(chunk, base2Y - 1, BadMagic.Vine.SOUTH, 6, 14, 7, 14, 8, 14, 9, 14);
				}
				if (superConnected || roads.toWest()) {
					chunk.setBlock(2, sewerY - 1, 7, fluidMaterial);
					chunk.setBlock(3, sewerY - 1, 8, fluidMaterial);
					chunk.setBlock(4, sewerY - 1, 7, fluidMaterial);
					generateEntryVines(chunk, base2Y - 1, BadMagic.Vine.WEST, 1, 6, 1, 7, 1, 8, 1, 9);
				}
				if (superConnected || roads.toEast()) {
					chunk.setBlock(11, sewerY - 1, 8, fluidMaterial);
					chunk.setBlock(12, sewerY - 1, 7, fluidMaterial);
					chunk.setBlock(13, sewerY - 1, 8, fluidMaterial);
					generateEntryVines(chunk, base2Y - 1, BadMagic.Vine.EAST, 14, 6, 14, 7, 14, 8, 14, 9);
				}
			} finally {
				chunk.setDoPhysics(false);
			}
			
			// add the various doors
			if (vaultNorthWest) {
				generateDoor(chunk, 4, sewerY, 1, BadMagic.Door.EASTBYNORTHEAST);
				generateDoor(chunk, 1, sewerY, 4, BadMagic.Door.SOUTHBYSOUTHWEST);
			}
			if (vaultNorthEast) {
				generateDoor(chunk, 11, sewerY, 1, BadMagic.Door.WESTBYNORTHWEST);
				generateDoor(chunk, 14, sewerY, 4, BadMagic.Door.SOUTHBYSOUTHEAST);
			}
			if (vaultSouthWest) {
				generateDoor(chunk, 1, sewerY, 11, BadMagic.Door.NORTHBYNORTHWEST);
				generateDoor(chunk, 4, sewerY, 14, BadMagic.Door.EASTBYSOUTHEAST);
			}
			if (vaultSouthEast) {
				generateDoor(chunk, 14, sewerY, 11, BadMagic.Door.NORTHBYNORTHEAST);
				generateDoor(chunk, 11, sewerY, 14, BadMagic.Door.WESTBYSOUTHWEST);
			}
			
			// we might put down a plank... or maybe not...
			boolean placedPlank = false;
			TreeSpecies species = chunkOdds.getRandomWoodSpecies();
			
			// fancy up the center walls?
			if (centerNorth) {
				generateDoor(chunk, 10, sewerY, 4, BadMagic.Door.NORTHBYNORTHEAST);
				chunk.setStoneSlab(7, sewerY, 4, BadMagic.StoneSlab.COBBLESTONEFLIP);
				chunk.setStoneSlab(8, sewerY, 4, BadMagic.StoneSlab.COBBLESTONEFLIP);
			} else if (!placedPlank && roads.toNorth() && chunkOdds.flipCoin()) {
				placedPlank = true;
				chunk.setSlabs(6, 10, sewerY, 5, 6, species, false); 
			}
			if (centerSouth) {
				generateDoor(chunk, 5, sewerY, 11, BadMagic.Door.SOUTHBYSOUTHWEST);
				chunk.setStoneSlab(7, sewerY, 11, BadMagic.StoneSlab.COBBLESTONEFLIP);
				chunk.setStoneSlab(8, sewerY, 11, BadMagic.StoneSlab.COBBLESTONEFLIP);
			} else if (!placedPlank && roads.toSouth() && chunkOdds.flipCoin()) {
				placedPlank = true;
				chunk.setSlabs(6, 10, sewerY, 10, 11, species, false);
			} 
			if (centerWest) {
				generateDoor(chunk, 4, sewerY, 5, BadMagic.Door.WESTBYNORTHWEST);
				chunk.setStoneSlab(4, sewerY, 7, BadMagic.StoneSlab.COBBLESTONEFLIP);
				chunk.setStoneSlab(4, sewerY, 8, BadMagic.StoneSlab.COBBLESTONEFLIP);
			} else if (!placedPlank && roads.toWest() && chunkOdds.flipCoin()) {
				placedPlank = true;
				chunk.setSlabs(5, 6, sewerY, 6, 10, species, false);
			}
			if (centerEast) { 
				generateDoor(chunk, 11, sewerY, 10, BadMagic.Door.EASTBYSOUTHEAST);
				chunk.setStoneSlab(11, sewerY, 7, BadMagic.StoneSlab.COBBLESTONEFLIP);
				chunk.setStoneSlab(11, sewerY, 8, BadMagic.StoneSlab.COBBLESTONEFLIP);
			} else if (!placedPlank && roads.toEast() && chunkOdds.flipCoin()) {
				placedPlank = true;
				chunk.setSlabs(10, 11, sewerY, 6, 10, species, false);
			}
			
			// populate the vaults
			if (vaultNorthWest) {
				if (!(roads.toNorth() && roads.toWest())) // special case for manholes
					generateTreat(generator, chunk, 2, sewerY, 2);
			}
			if (vaultNorthEast) {
				generateTreat(generator, chunk, 13, sewerY, 2);
			}
			if (vaultSouthWest) {
				generateTreat(generator, chunk, 2, sewerY, 13);
			}
			if (vaultSouthEast) {
				generateTreat(generator, chunk, 13, sewerY, 13);
			}
			if (centerNorth && centerSouth && centerWest && centerEast) {
				
				// look carefully, these are actually different
				switch(chunkOdds.getRandomInt(4)) {
				case 1:
					generateTreat(generator, chunk, 6, sewerY, 6);
					generateTrick(generator, chunk, 9, sewerY, 9);
					break;
				case 2:
					generateTreat(generator, chunk, 9, sewerY, 6);
					generateTrick(generator, chunk, 6, sewerY, 9);
					break;
				case 3:
					generateTreat(generator, chunk, 6, sewerY, 9);
					generateTrick(generator, chunk, 9, sewerY, 6);
					break;
				default:
					generateTreat(generator, chunk, 9, sewerY, 9);
					generateTrick(generator, chunk, 6, sewerY, 6);
					break;
				}
			} else {
				if (centerNorth) {
					if (vaultNorthWest && !vaultNorthEast)
						generateTrick(generator, chunk, 6, sewerY, 2);
					else if (vaultNorthEast && !vaultNorthWest)
						generateTrick(generator, chunk, 9, sewerY, 2);
				}
				if (centerSouth) {
					if (vaultSouthWest && !vaultSouthEast)
						generateTrick(generator, chunk, 6, sewerY, 13);
					else if (vaultSouthEast && !vaultSouthWest)
						generateTrick(generator, chunk, 9, sewerY, 13);
				}
				if (centerWest) {
					if (vaultNorthWest && !vaultSouthWest)
						generateTrick(generator, chunk, 2, sewerY, 6);
					else if (vaultSouthWest && !vaultNorthWest)
						generateTrick(generator, chunk, 2, sewerY, 9);
				}
				if (centerEast) {
					if (vaultNorthEast && !vaultSouthEast)
						generateTrick(generator, chunk, 13, sewerY, 6);
					else if (vaultSouthEast && !vaultNorthEast)
						generateTrick(generator, chunk, 13, sewerY, 9);
				}
			}
			
			// now the vines
			for (int i = 2; i < 14; i++) {
				generateHangingVine(chunk, base2Y - 1, BadMagic.Vine.NORTH, i, 2, i, 1);
//				generateHangingVine(chunk, base2Y - 1, Direction.Vine.NORTH, i, 5, i, 4);
//				generateHangingVine(chunk, base2Y - 1, Direction.Vine.SOUTH, i, 10, i, 11);
				generateHangingVine(chunk, base2Y - 1, BadMagic.Vine.SOUTH, i, 13, i, 14);
					
				generateHangingVine(chunk, base2Y - 1, BadMagic.Vine.WEST, 2, i, 1, i);
//				generateHangingVine(chunk, base2Y - 1, Direction.Vine.WEST, 5, i, 4, i);
//				generateHangingVine(chunk, base2Y - 1, Direction.Vine.EAST, 10, i, 11, i);
				generateHangingVine(chunk, base2Y - 1, BadMagic.Vine.EAST, 13, i, 14, i);
			}
		}
	}
	
	protected void paveRoadArea(CityWorldGenerator generator, SupportBlocks chunk, int x1, int x2, int y, int z1, int z2) {
		if (inACity)
			if (pavementIsClay)
				chunk.setClay(x1, x2, y, z1, z2, pavementColor);
			else
				chunk.setBlocks(x1, x2, y, z1, z2, pavementMat);
		else
			if (dirtroadIsClay)
				chunk.setClay(x1, x2, y, z1, z2, dirtroadColor);
			else if (dirtroadMat == Material.DIRT)
				BlackMagic.setBlocks(chunk, x1, x2, y, z1, z2, dirtroadMat, 2); // Podzol dirt
			else
				chunk.setBlocks(x1, x2, y, z1, z2, dirtroadMat);
	}
	
	protected void paveSidewalk(CityWorldGenerator generator, SupportBlocks chunk, int x1, int x2, int y, int z1, int z2) {
		if (inACity)
			chunk.setBlocks(x1, x2, y, z1, z2, pavementSidewalk);
		else
			if (dirtroadSidewalk == Material.DIRT)
				BlackMagic.setBlocks(chunk, x1, x2, y, z1, z2, dirtroadSidewalk, 1); // Coarse dirt
			else
				chunk.setBlocks(x1, x2, y, z1, z2, dirtroadSidewalk);
	}
	
	protected void paveSidewalk(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z) {
		if (inACity)
			chunk.setBlock(x, y, z, pavementSidewalk);
		else
			if (dirtroadSidewalk == Material.DIRT)
				BlackMagic.setBlock(chunk, x, y, z, dirtroadSidewalk, 1); // Coarse dirt
			else
				chunk.setBlock(x, y, z, dirtroadSidewalk);
	}
	
	protected void paveRoadLot(CityWorldGenerator generator, SupportBlocks chunk, int y) {
		paveRoadArea(generator, chunk, 0, 16, y - 1, 0, 16);
		paveRoadArea(generator, chunk, 0, 16, y, 0, 16);
	}
	
	protected void generateNSCrosswalk(CityWorldGenerator generator, RealBlocks chunk, int x1, int x2, int y, int z1, int z2, boolean crosswalk) {
		if (inACity) {
			paveRoadArea(generator, chunk, x1, x2, y, z1, z2);
			if (crosswalk) {
				chunk.setBlocks(x1 + 1, x1 + 2, y, z1, z2, linesMat);
				chunk.setBlocks(x1 + 3, x1 + 4, y, z1, z2, linesMat);
				chunk.setBlocks(x2 - 2, x2 - 1, y, z1, z2, linesMat);
				chunk.setBlocks(x2 - 4, x2 - 3, y, z1, z2, linesMat);
//				chunk.setClay(x1 + 1, x1 + 2, y, z1, z2, crosswalkColor);
//				chunk.setClay(x1 + 3, x1 + 4, y, z1, z2, crosswalkColor);
//				chunk.setClay(x2 - 2, x2 - 1, y, z1, z2, crosswalkColor);
//				chunk.setClay(x2 - 4, x2 - 3, y, z1, z2, crosswalkColor);
			}
		}
	}

	protected void generateWECrosswalk(CityWorldGenerator generator, RealBlocks chunk, int x1, int x2, int y, int z1, int z2, boolean crosswalk) {
		if (inACity) {
			paveRoadArea(generator, chunk, x1, x2, y, z1, z2);
			if (crosswalk) {
				chunk.setBlocks(x1, x2, y, z1 + 1, z1 + 2, linesMat);
				chunk.setBlocks(x1, x2, y, z1 + 3, z1 + 4, linesMat);
				chunk.setBlocks(x1, x2, y, z2 - 2, z2 - 1, linesMat);
				chunk.setBlocks(x1, x2, y, z2 - 4, z2 - 3, linesMat);
//				chunk.setClay(x1, x2, y, z1 + 1, z1 + 2, crosswalkColor);
//				chunk.setClay(x1, x2, y, z1 + 3, z1 + 4, crosswalkColor);
//				chunk.setClay(x1, x2, y, z2 - 2, z2 - 1, crosswalkColor);
//				chunk.setClay(x1, x2, y, z2 - 4, z2 - 3, crosswalkColor);
			}
		}
	}
	
	protected void generateEntities(CityWorldGenerator generator, RealBlocks chunk, int y) {
		int x = chunkOdds.calcRandomRange(sidewalkWidth, 15 - sidewalkWidth);
		int z = chunkOdds.calcRandomRange(sidewalkWidth, 15 - sidewalkWidth);
//		chunk.setBlock(x, y + 30, z, Material.GLOWSTONE);
		if (inACity)
			generator.spawnProvider.spawnBeings(generator, chunk, chunkOdds, x, y, z);
		else
			generator.spawnProvider.spawnVagrants(generator, chunk, chunkOdds, x, y, z);
	}
	
	protected void decayRoad(RealBlocks chunk, int x1, int x2, int y, int z1, int z2) {
		int amount = (x2 - x1) * (z2 - z1) / 10;
		while (amount > 0) {
			int x = x1 + chunkOdds.getRandomInt(x2 - x1);
			int z = z1 + chunkOdds.getRandomInt(z2 - z1);
			if (chunkOdds.flipCoin())
				chunk.setBlock(x, y, z, Material.COBBLESTONE);
			else
				BlackMagic.setBlock(chunk, x, y, z, Material.STEP, 3);
			amount--;
		}
	}
	
	protected void decaySidewalk(CityWorldGenerator generator, RealBlocks chunk, int x1, int x2, int y, int z1, int z2) {
		int amount = (x2 - x1) * (z2 - z1) / 10;
		while (amount > 0) {
			int x = x1 + chunkOdds.getRandomInt(x2 - x1);
			int z = z1 + chunkOdds.getRandomInt(z2 - z1);
			if (chunkOdds.flipCoin())
				chunk.airoutBlock(generator, x, y, z);
			else
				BlackMagic.setBlock(chunk, x, y, z, Material.STEP, 3);
			amount--;
		}
	}
	
	private void generateEntryVines(RealBlocks chunk, int y, BadMagic.Vine direction,
			int x1, int z1, int x2, int z2, int x3, int z3, int x4, int z4) {
		if (chunkOdds.flipCoin())
			chunk.setVine(x1, y, z1, direction);
		if (chunkOdds.flipCoin())
			chunk.setVine(x2, y, z2, direction);
		if (chunkOdds.flipCoin())
			chunk.setVine(x3, y, z3, direction);
		if (chunkOdds.flipCoin())
			chunk.setVine(x4, y, z4, direction);
	}
	
	private void generateHangingVine(RealBlocks chunk, int y, BadMagic.Vine direction, int x1, int z1, int x2, int z2) {
		if (chunkOdds.flipCoin() && chunk.isEmpty(x1, y, z1) && !chunk.isEmpty(x2, y, z2))
			chunk.setVine(x1, y, z1, direction);
	}
	
	protected boolean generateLightPost(CityWorldGenerator generator, RealBlocks chunk, DataContext context, int sidewalkLevel, int x, int z) {
		chunk.setBlock(x, sidewalkLevel, z, lightpostbaseMaterial);
		if (generator.settings.includeDecayedRoads) {
			int y = sidewalkLevel + 1;
			while (y < sidewalkLevel + lightpostHeight + 1) {
				if (chunkOdds.playOdds(0.25))
					break;
				chunk.setBlock(x, y, z, lightpostMaterial);
				y++;
			}
			if (y > sidewalkLevel + lightpostHeight) {
				if (chunkOdds.playOdds(0.75))
					chunk.setBlock(x, y, z, context.lightMat);
				return true;
			}
			return false;
		} else {
			chunk.setBlocks(x, sidewalkLevel + 1, sidewalkLevel + lightpostHeight + 1, z, lightpostMaterial);
			chunk.setBlock(x, sidewalkLevel + lightpostHeight + 1, z, context.lightMat);
			return true;
		}
	}
	
	private final static double oddsOfDecayedSign = Odds.oddsExtremelyLikely;
	protected void generateStreetSign(CityWorldGenerator generator, RealBlocks chunk, int sidewalkLevel, int x, int z) {
		int cx = chunk.sectionX;
		int cz = chunk.sectionZ;
		int y = sidewalkLevel + lightpostHeight;
		
		// decay or not?
		if (generator.settings.includeDecayedRoads) {
			
			// put the signs up
			if (chunkOdds.playOdds(oddsOfDecayedSign)) {
				String[] odonym = generator.odonymProvider.generateNorthSouthStreetOdonym(generator, cx, cz);
				generator.odonymProvider.decaySign(chunkOdds, odonym);
				chunk.setWallSign(x, y, z - 1, BadMagic.General.NORTH, odonym);
			}
			if (chunkOdds.playOdds(oddsOfDecayedSign)) {
				String[] odonym = generator.odonymProvider.generateNorthSouthStreetOdonym(generator, cx, cz);
				generator.odonymProvider.decaySign(chunkOdds, odonym);
				chunk.setWallSign(x, y, z + 1, BadMagic.General.SOUTH, odonym);
			}
			if (chunkOdds.playOdds(oddsOfDecayedSign)) {
				String[] odonym = generator.odonymProvider.generateWestEastStreetOdonym(generator, cx, cz);
				generator.odonymProvider.decaySign(chunkOdds, odonym);
				chunk.setWallSign(x - 1, y, z, BadMagic.General.WEST, odonym);
			}
			if (chunkOdds.playOdds(oddsOfDecayedSign)) {
				String[] odonym = generator.odonymProvider.generateWestEastStreetOdonym(generator, cx, cz);
				generator.odonymProvider.decaySign(chunkOdds, odonym);
				chunk.setWallSign(x + 1, y, z, BadMagic.General.EAST, odonym);
			}
		} else {
			
			// compute the name for the roads
			String[] odonymNorthSouth = generator.odonymProvider.generateNorthSouthStreetOdonym(generator, cx, cz);
			String[] odonymWestEast = generator.odonymProvider.generateWestEastStreetOdonym(generator, cx, cz);
			
			// put the signs up
			chunk.setWallSign(x, y, z - 1, BadMagic.General.NORTH, odonymNorthSouth);
			chunk.setWallSign(x, y, z + 1, BadMagic.General.SOUTH, odonymNorthSouth);
			chunk.setWallSign(x - 1, y, z, BadMagic.General.WEST, odonymWestEast);
			chunk.setWallSign(x + 1, y, z, BadMagic.General.EAST, odonymWestEast);
		}
	}
	
	private void generateDoor(RealBlocks chunk, int x, int y, int z, BadMagic.Door direction) {
		switch (chunkOdds.getRandomInt(5)) {
		case 1:
			chunk.setBlocks(x, y, y + 2, z, Material.BRICK);
			break;
		case 2:
			chunk.setBlocks(x, y, y + 2, z, Material.IRON_FENCE);
			break;
		case 3:
			chunk.setBlocks(x, y, y + 2, z, Material.AIR);
			break;
//		case 4:
//			chunk.setIronDoor(x, y, z, direction);
//			break;
		default:
			chunk.setWoodenDoor(x, y, z, direction);
			break;
		}
	}
	
	protected void generateRoundedOut(CityWorldGenerator generator, DataContext context, SupportBlocks chunk, int x, int z, boolean toNorth, boolean toEast) {
		int sidewalkLevel = getSidewalkLevel(generator);
		
		// long bits
		for (int i = 0; i < 4; i++) {
			paveSidewalk(generator, chunk, toNorth ? x + 3 : x, sidewalkLevel, z + i);
			paveSidewalk(generator, chunk, x + i, sidewalkLevel, toEast ? z + 3 : z);
		}
		
		// little notch
		paveSidewalk(generator, chunk, toNorth ? x + 2 : x + 1, sidewalkLevel, toEast ? z + 2 : z + 1); 
	}
	
	private void generateTreat(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z) {
		
		// cool stuff?
		if (generator.settings.treasuresInSewers && chunkOdds.playOdds(generator.settings.oddsOfTreasureInSewers)) {
			 chunk.setChest(generator, x, y, z, BadMagic.General.NORTH, chunkOdds, generator.lootProvider, LootLocation.SEWER);
		}
	}

	private void generateTrick(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z) {
		
		// not so cool stuff?
		generator.spawnProvider.setSpawnOrSpawner(generator, chunk, chunkOdds, x, y, z, 
				generator.settings.spawnersInSewers, generator.spawnProvider.itemsEntities_Sewers);
	}
}
