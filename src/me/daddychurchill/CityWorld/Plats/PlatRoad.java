package me.daddychurchill.CityWorld.Plats;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Plugins.LootProvider;
import me.daddychurchill.CityWorld.Plugins.SpawnProvider;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.HeightInfo;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SurroundingRoads;
import me.daddychurchill.CityWorld.Support.Direction.Ladder;
import me.daddychurchill.CityWorld.Support.Direction.TrapDoor;

public class PlatRoad extends PlatConnected {
	
	//TODO Asphalt the road
	//TODO Lines on the road
	
	public static final int PlatMapRoadInset = 3;

	private final static int sidewalkWidth = 3;
	private final static int lightpostHeight = 3;
	private final static int crossDitchEdge = 7;
	private final static int tunnelHeight = 8;
	private final static int fenceHeight = 2;
	
	private final static Material airMaterial = Material.AIR;
	private final static Material lightpostbaseMaterial = Material.DOUBLE_STEP;
	private final static Material lightpostMaterial = Material.FENCE;
	private final static Material sewerWallMaterial = Material.MOSSY_COBBLESTONE;
	//private final static Material vineMaterial = Material.VINE;

	private final static byte airId = (byte) airMaterial.getId();
	private final static byte sewerFloorId = (byte) Material.COBBLESTONE.getId();
	private final static byte sewerWallId = (byte) sewerWallMaterial.getId();
	private final static byte sewerCeilingId = sewerFloorId;
	private final static byte pavementId = (byte) Material.STONE.getId();
	private final static byte sidewalkId = (byte) Material.STEP.getId();
	private final static Material sewerPlankMaterial = Material.STEP; //TODO should be Material.WOODSTEP (or whatever it is called)
	private final static byte sewerPlankData = 2;
	
	private final static byte retainingWallId = (byte) Material.SMOOTH_BRICK.getId();
	private final static byte retainingFenceId = (byte) Material.IRON_FENCE.getId();

	private final static byte tunnelWallId = (byte) Material.SMOOTH_BRICK.getId();
	private final static byte tunnelTileId = (byte) Material.SANDSTONE.getId();
	private final static byte tunnelCeilingId = (byte) Material.GLASS.getId();
	
	private final static byte bridgePavement1Id = (byte) Material.STEP.getId(); //TODO WOOD_STEP
	private final static byte bridgePavement2Id = (byte) Material.DOUBLE_STEP.getId(); //TODO WOOD_DOUBLESTEP
	private final static byte bridgeSidewalk1Id = (byte) Material.STEP.getId();
	private final static byte bridgeSidewalk2Id = (byte) Material.DOUBLE_STEP.getId();
	private final static byte bridgeEdgeId = (byte) Material.SMOOTH_BRICK.getId();
	private final static byte bridgeRailId = (byte) Material.FENCE.getId();
	
	public PlatRoad(PlatMap platmap, int chunkX, int chunkZ, long globalconnectionkey) {
		super(platmap, chunkX, chunkZ);
		
		style = LotStyle.ROAD;
		connectedkey = globalconnectionkey;
	}
	
	@Override
	protected Biome getChunkBiome() {
		return Biome.SWAMPLAND;
	}

	@Override
	protected boolean isShaftableLevel(WorldGenerator generator, ContextData context, int y) {
		return (y < generator.sidewalkLevel - ContextData.FloorHeight * 2 - 16 || y >= generator.sidewalkLevel + tunnelHeight + 1 + 16 ) &&
				super.isShaftableLevel(generator, context, y);	
	}
	
	private boolean sewerCenterBit;
	private boolean sewerNorthWestBias;
	private boolean sewerNorthEastBias;
	private boolean sewerSouthWestBias;
	private boolean sewerSouthEastBias;

	@Override
	protected void generateActualChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, ContextData context, int platX, int platZ) {
		
		// random bits
		sewerCenterBit = chunkRandom.nextBoolean();
		sewerNorthWestBias = chunkRandom.nextBoolean();
		sewerNorthEastBias = chunkRandom.nextBoolean();
		sewerSouthWestBias = chunkRandom.nextBoolean();
		sewerSouthEastBias = chunkRandom.nextBoolean();
		
		// compute offset to start of chunk
		int originX = chunk.getOriginX();
		int originZ = chunk.getOriginZ();
		
		// where do we start
		int base1Y = context.streetLevel - ContextData.FloorHeight * 2 + 1;
		int sewerY = base1Y + 1;
		int base2Y = base1Y + ContextData.FloorHeight + 1;
		int sidewalkLevel = context.streetLevel + 1;
		boolean doSewer = generator.settings.includeSewers;
		
		// look around
		SurroundingRoads roads = new SurroundingRoads(platmap, platX, platZ);
		
		// ok, deep enough for a bridge
		if (HeightInfo.getHeightsFast(generator, originX, originZ).isSea()) {
			doSewer = false;

			// clear a little space
			chunk.setLayer(sidewalkLevel, 2, airId);
			
			// bridge to the east/west
			if (roads.toWest() && roads.toEast()) {
				
				// more bridge beside this one?
				boolean toWest = HeightInfo.getHeightsFast(generator, originX - chunk.width, originZ).isSea();
				boolean toEast = HeightInfo.getHeightsFast(generator, originX + chunk.width, originZ).isSea();
				
				if (toWest) {
					
					// tall span
					if (toEast) {
						placeWBridgeColumns(chunk, sidewalkLevel + 4);
						placeEWBridgePartA(chunk, 0, sidewalkLevel + 4);
						placeEWBridgePartA(chunk, 2, sidewalkLevel + 4);
						placeEWBridgePartA(chunk, 4, sidewalkLevel + 4);
						placeEWBridgePartA(chunk, 6, sidewalkLevel + 4);
						placeEWBridgePartA(chunk, 8, sidewalkLevel + 4);
						placeEWBridgePartA(chunk, 10, sidewalkLevel + 4);
						placeEWBridgePartA(chunk, 12, sidewalkLevel + 4);
						placeEWBridgePartA(chunk, 14, sidewalkLevel + 4);
						placeEBridgeColumns(chunk, sidewalkLevel + 4);
						
						
					// ramp down
					} else {
						placeEWBridgeCap(chunk, 14, base1Y, sidewalkLevel);
						placeEWBridgePartA(chunk, 14, sidewalkLevel);
						placeEWBridgePartB(chunk, 12, sidewalkLevel);
						placeEWBridgePartA(chunk, 10, sidewalkLevel + 1);
						placeEWBridgePartB(chunk, 8, sidewalkLevel + 1);
						placeEWBridgePartA(chunk, 6, sidewalkLevel + 2);
						placeEWBridgePartB(chunk, 4, sidewalkLevel + 2);
						placeEWBridgePartA(chunk, 2, sidewalkLevel + 3);
						placeEWBridgePartB(chunk, 0, sidewalkLevel + 3);
						placeWBridgeColumns(chunk, sidewalkLevel + 3);
					}
						
					
				} else {
					
					// ramp up
					if (toEast) {
						placeEWBridgeCap(chunk, 0, base1Y, sidewalkLevel);
						placeEWBridgePartA(chunk, 0, sidewalkLevel);
						placeEWBridgePartB(chunk, 2, sidewalkLevel);
						placeEWBridgePartA(chunk, 4, sidewalkLevel + 1);
						placeEWBridgePartB(chunk, 6, sidewalkLevel + 1);
						placeEWBridgePartA(chunk, 8, sidewalkLevel + 2);
						placeEWBridgePartB(chunk, 10, sidewalkLevel + 2);
						placeEWBridgePartA(chunk, 12, sidewalkLevel + 3);
						placeEWBridgePartB(chunk, 14, sidewalkLevel + 3);
						placeEBridgeColumns(chunk, sidewalkLevel + 3);
						
					// short span
					} else {
						placeEWBridgeCap(chunk, 0, base1Y, sidewalkLevel);
						placeEWBridgePartA(chunk, 0, sidewalkLevel);
						placeEWBridgePartB(chunk, 2, sidewalkLevel);
						placeEWBridgePartA(chunk, 4, sidewalkLevel + 1);
						placeEWBridgePartA(chunk, 6, sidewalkLevel + 1);
						placeEWBridgePartA(chunk, 8, sidewalkLevel + 1);
						placeEWBridgePartA(chunk, 10, sidewalkLevel + 1);
						placeEWBridgePartB(chunk, 12, sidewalkLevel);
						placeEWBridgePartA(chunk, 14, sidewalkLevel);
						placeEWBridgeCap(chunk, 14, base1Y, sidewalkLevel);
					}
				}
				
			} else if (roads.toNorth() && roads.toSouth()) {
				
				// more bridge beside this one?
				boolean toNorth = HeightInfo.getHeightsFast(generator, originX, originZ - chunk.width).isSea();
				boolean toSouth = HeightInfo.getHeightsFast(generator, originX, originZ + chunk.width).isSea();
				
				if (toNorth) {
					
					// tall span
					if (toSouth) {
						placeNBridgeColumns(chunk, sidewalkLevel + 4);
						placeNSBridgePartA(chunk, 0, sidewalkLevel + 4);
						placeNSBridgePartA(chunk, 2, sidewalkLevel + 4);
						placeNSBridgePartA(chunk, 4, sidewalkLevel + 4);
						placeNSBridgePartA(chunk, 6, sidewalkLevel + 4);
						placeNSBridgePartA(chunk, 8, sidewalkLevel + 4);
						placeNSBridgePartA(chunk, 10, sidewalkLevel + 4);
						placeNSBridgePartA(chunk, 12, sidewalkLevel + 4);
						placeNSBridgePartA(chunk, 14, sidewalkLevel + 4);
						placeSBridgeColumns(chunk, sidewalkLevel + 4);
						
					// ramp down
					} else {
						placeNSBridgeCap(chunk, 14, base1Y, sidewalkLevel);
						placeNSBridgePartA(chunk, 14, sidewalkLevel);
						placeNSBridgePartB(chunk, 12, sidewalkLevel);
						placeNSBridgePartA(chunk, 10, sidewalkLevel + 1);
						placeNSBridgePartB(chunk, 8, sidewalkLevel + 1);
						placeNSBridgePartA(chunk, 6, sidewalkLevel + 2);
						placeNSBridgePartB(chunk, 4, sidewalkLevel + 2);
						placeNSBridgePartA(chunk, 2, sidewalkLevel + 3);
						placeNSBridgePartB(chunk, 0, sidewalkLevel + 3);
						placeNBridgeColumns(chunk, sidewalkLevel + 3);
					}
					
				} else {
					
					// ramp up
					if (toSouth) {
						placeNSBridgeCap(chunk, 0, base1Y, sidewalkLevel);
						placeNSBridgePartA(chunk, 0, sidewalkLevel);
						placeNSBridgePartB(chunk, 2, sidewalkLevel);
						placeNSBridgePartA(chunk, 4, sidewalkLevel + 1);
						placeNSBridgePartB(chunk, 6, sidewalkLevel + 1);
						placeNSBridgePartA(chunk, 8, sidewalkLevel + 2);
						placeNSBridgePartB(chunk, 10, sidewalkLevel + 2);
						placeNSBridgePartA(chunk, 12, sidewalkLevel + 3);
						placeNSBridgePartB(chunk, 14, sidewalkLevel + 3);
						placeSBridgeColumns(chunk, sidewalkLevel + 3);
						
					// short span
					} else {
						placeNSBridgeCap(chunk, 0, base1Y, sidewalkLevel);
						placeNSBridgePartA(chunk, 0, sidewalkLevel);
						placeNSBridgePartB(chunk, 2, sidewalkLevel);
						placeNSBridgePartA(chunk, 4, sidewalkLevel + 1);
						placeNSBridgePartA(chunk, 6, sidewalkLevel + 1);
						placeNSBridgePartA(chunk, 8, sidewalkLevel + 1);
						placeNSBridgePartA(chunk, 10, sidewalkLevel + 1);
						placeNSBridgePartB(chunk, 12, sidewalkLevel);
						placeNSBridgePartA(chunk, 14, sidewalkLevel);
						placeNSBridgeCap(chunk, 14, base1Y, sidewalkLevel);
					}
				}
			}
			
		} else {
			
			// draw pavement and clear out a bit
			chunk.setLayer(sidewalkLevel - 1, pavementId);
			chunk.setLayer(sidewalkLevel, airId);
			
			// sidewalk corners
			chunk.setBlocks(0, sidewalkWidth, sidewalkLevel, sidewalkLevel + 1, 0, sidewalkWidth, sidewalkId);
			chunk.setBlocks(0, sidewalkWidth, sidewalkLevel, sidewalkLevel + 1, chunk.width - sidewalkWidth, chunk.width, sidewalkId);
			chunk.setBlocks(chunk.width - sidewalkWidth, chunk.width, sidewalkLevel, sidewalkLevel + 1, 0, sidewalkWidth, sidewalkId);
			chunk.setBlocks(chunk.width - sidewalkWidth, chunk.width, sidewalkLevel, sidewalkLevel + 1, chunk.width - sidewalkWidth, chunk.width, sidewalkId);
			
			// sidewalk edges
			if (!roads.toWest())
				chunk.setBlocks(0, sidewalkWidth, sidewalkLevel, sidewalkLevel + 1, sidewalkWidth, chunk.width - sidewalkWidth, sidewalkId);
			if (!roads.toEast())
				chunk.setBlocks(chunk.width - sidewalkWidth, chunk.width, sidewalkLevel, sidewalkLevel + 1, sidewalkWidth, chunk.width - sidewalkWidth, sidewalkId);
			if (!roads.toNorth())
				chunk.setBlocks(sidewalkWidth, chunk.width - sidewalkWidth, sidewalkLevel, sidewalkLevel + 1, 0, sidewalkWidth, sidewalkId);
			if (!roads.toSouth())
				chunk.setBlocks(sidewalkWidth, chunk.width - sidewalkWidth, sidewalkLevel, sidewalkLevel + 1, chunk.width - sidewalkWidth, chunk.width, sidewalkId);
			
			// tunnel walls please
			if (maxHeight > sidewalkLevel + tunnelHeight) {
				doSewer = false;
				
				// draw pavement
				chunk.setLayer(context.streetLevel - 2, 2, pavementId);
				
				// tunnel to the east/west
				if (roads.toWest() && roads.toEast()) {
					
					// carve out the tunnel
					chunk.setBlocks(0, 16, sidewalkLevel + 1, sidewalkLevel + 6, 2, 14, airId);
					
					// place the arches
					placeEWTunnelArch(chunk, 0, sidewalkLevel, tunnelWallId, tunnelWallId, tunnelWallId);
					for (int x = 1; x < chunk.width - 1; x++) {
						placeEWTunnelArch(chunk, x, sidewalkLevel, tunnelWallId, tunnelTileId, tunnelCeilingId);
					}
					placeEWTunnelArch(chunk, 15, sidewalkLevel, tunnelWallId, tunnelWallId, tunnelWallId);
					
				} else if (roads.toNorth() && roads.toSouth()) {
					
					// carve out the tunnel
					chunk.setBlocks(2, 14, sidewalkLevel + 1, sidewalkLevel + 6, 0, 16, airId);
					
					// place the arches
					placeNSTunnelArch(chunk, 0, sidewalkLevel, tunnelWallId, tunnelWallId, tunnelWallId);
					for (int z = 1; z < chunk.width - 1; z++) {
						placeNSTunnelArch(chunk, z, sidewalkLevel, tunnelWallId, tunnelTileId, tunnelCeilingId);
					}
					placeNSTunnelArch(chunk, 15, sidewalkLevel, tunnelWallId, tunnelWallId, tunnelWallId);
				}
				
			// retaining walls please
			} else if (maxHeight > sidewalkLevel) {
				
				// wall to the east/west
				if (roads.toWest() && roads.toEast()) {
					
					// carve out the tunnel
					chunk.setBlocks(0, 16, sidewalkLevel + 1, sidewalkLevel + tunnelHeight + 1, 0, 16, airId);
					
					// walls please
					for (int x = 0; x < chunk.width; x++) {
						placeRetainingWall(chunk, x, 0, sidewalkLevel, generator.findBlockY(originX + x, originZ - 1));
						placeRetainingWall(chunk, x, 15, sidewalkLevel, generator.findBlockY(originX + x, originZ + 16));
					}
				} else if (roads.toNorth() && roads.toSouth()) {

					// carve out the tunnel
					chunk.setBlocks(0, 16, sidewalkLevel + 1, sidewalkLevel + tunnelHeight + 1, 0, 16, airId);

					// walls please
					for (int z = 0; z < chunk.width; z++) {
						placeRetainingWall(chunk, 0, z, sidewalkLevel, generator.findBlockY(originX - 1, originZ + z));
						placeRetainingWall(chunk, 15, z, sidewalkLevel, generator.findBlockY(originX + 16, originZ + z));
					}
				}
							
			// stuff that only can happen outside of tunnels and bridges
			} else {
				
				// round things out
				if (!roads.toWest() && roads.toEast() && !roads.toNorth() && roads.toSouth())
					generateRoundedOut(chunk, context, sidewalkWidth, sidewalkWidth, 
							false, false);
				if (!roads.toWest() && roads.toEast() && roads.toNorth() && !roads.toSouth())
					generateRoundedOut(chunk, context, sidewalkWidth, chunk.width - sidewalkWidth - 4, 
							false, true);
				if (roads.toWest() && !roads.toEast() && !roads.toNorth() && roads.toSouth())
					generateRoundedOut(chunk, context, chunk.width - sidewalkWidth - 4, sidewalkWidth, 
							true, false);
				if (roads.toWest() && !roads.toEast() && roads.toNorth() && !roads.toSouth())
					generateRoundedOut(chunk, context, chunk.width - sidewalkWidth - 4, chunk.width - sidewalkWidth - 4, 
							true, true);
			}
		}
		
		// sewer or not?
		if (doSewer) {
			
			// empty out the sewer
			chunk.setLayer(base1Y, base2Y - base1Y, airId);
					
			// draw the floor of the sewer
			chunk.setLayer(sewerY - 2, 2, sewerFloorId);
			chunk.setBlocks(crossDitchEdge, chunk.width - crossDitchEdge, 
							sewerY - 1, sewerY, 
							crossDitchEdge, chunk.width - crossDitchEdge, airId);
			
			// corner bits
			chunk.setBlocks(0, 6, sewerY, base2Y, 0, 1, sewerWallId);
			chunk.setBlocks(0, 1, sewerY, base2Y, 1, 6, sewerWallId);
			chunk.setBlocks(10, 16, sewerY, base2Y, 0, 1, sewerWallId);
			chunk.setBlocks(15, 16, sewerY, base2Y, 1, 6, sewerWallId);
			chunk.setBlocks(0, 6, sewerY, base2Y, 15, 16, sewerWallId);
			chunk.setBlocks(0, 1, sewerY, base2Y, 10, 15, sewerWallId);
			chunk.setBlocks(10, 16, sewerY, base2Y, 15, 16, sewerWallId);
			chunk.setBlocks(15, 16, sewerY, base2Y, 10, 15, sewerWallId);
			
			// cross beams
			chunk.setBlocks(6, 10, base2Y - 1, base2Y, 0, 1, sewerCeilingId);
			chunk.setBlocks(6, 10, base2Y - 1, base2Y, 15, 16, sewerCeilingId);
			chunk.setBlocks(0, 1, base2Y - 1, base2Y, 6, 10, sewerCeilingId);
			chunk.setBlocks(15, 16, base2Y - 1, base2Y, 6, 10, sewerCeilingId);
			
			// cardinal directions known walls and ditches
			if (!roads.toNorth()) {
				chunk.setBlocks(5, 11, sewerY, base2Y, 0, 1, sewerWallId);
				chunk.setBlocks(5, 11, base2Y - 1, base2Y, 1, 2, sewerCeilingId);
			} else {
				chunk.setBlocks(7, 9, sewerY - 1, sewerY, 0, 7, airId);
			}
			if (!roads.toSouth()) {
				chunk.setBlocks(5, 11, sewerY, base2Y, 15, 16, sewerWallId);
				chunk.setBlocks(5, 11, base2Y - 1, base2Y, 14, 15, sewerCeilingId);
			} else {
				chunk.setBlocks(7, 9, sewerY - 1, sewerY, 9, 16, airId);
			}
			if (!roads.toWest()) {
				chunk.setBlocks(0, 1, sewerY, base2Y, 5, 11, sewerWallId);
				chunk.setBlocks(1, 2, base2Y - 1, base2Y, 5, 11, sewerCeilingId);
			} else {
				chunk.setBlocks(0, 7, sewerY - 1, sewerY, 7, 9, airId);
			}
			if (!roads.toEast()) {
				chunk.setBlocks(15, 16, sewerY, base2Y, 5, 11, sewerWallId);
				chunk.setBlocks(14, 15, base2Y - 1, base2Y, 5, 11, sewerCeilingId);
			} else {
				chunk.setBlocks(9, 16, sewerY - 1, sewerY, 7, 9, airId);
			}
			
			// defaults
			boolean vaultNorthWest = false;
			boolean vaultSouthWest = false;
			boolean vaultNorthEast = false;
			boolean vaultSouthEast = false;
			boolean centerNorth = !roads.toNorth();
			boolean centerSouth = !roads.toSouth();
			boolean centerWest = !roads.toWest();
			boolean centerEast = !roads.toEast();
			
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
				chunk.setBlocks(4, 5, sewerY, base2Y - 1, 1, 4, sewerWallId);
				chunk.setBlocks(1, 4, sewerY, base2Y - 1, 4, 5, sewerWallId);
				chunk.setBlocks(1, 6, base2Y - 1, base2Y, 1, 6, sewerCeilingId);
			} else {
				chunk.setBlocks(1, 6, base2Y - 1, base2Y, 1, 2, sewerCeilingId);
				chunk.setBlocks(1, 2, base2Y - 1, base2Y, 2, 6, sewerCeilingId);
			}
			if (vaultSouthWest) {
				chunk.setBlocks(4, 5, sewerY, base2Y - 1, 12, 15, sewerWallId);
				chunk.setBlocks(1, 4, sewerY, base2Y - 1, 11, 12, sewerWallId);
				chunk.setBlocks(1, 6, base2Y - 1, base2Y, 10, 15, sewerCeilingId);
			} else {
				chunk.setBlocks(1, 6, base2Y - 1, base2Y, 14, 15, sewerCeilingId);
				chunk.setBlocks(1, 2, base2Y - 1, base2Y, 10, 14, sewerCeilingId);
			}
			if (vaultNorthEast) {
				chunk.setBlocks(11, 12, sewerY, base2Y - 1, 1, 4, sewerWallId);
				chunk.setBlocks(12, 15, sewerY, base2Y - 1, 4, 5, sewerWallId);
				chunk.setBlocks(10, 15, base2Y - 1, base2Y, 1, 6, sewerCeilingId);
			} else {
				chunk.setBlocks(10, 15, base2Y - 1, base2Y, 1, 2, sewerCeilingId);
				chunk.setBlocks(14, 15, base2Y - 1, base2Y, 2, 6, sewerCeilingId);
			}
			if (vaultSouthEast) {
				chunk.setBlocks(11, 12, sewerY, base2Y - 1, 12, 15, sewerWallId);
				chunk.setBlocks(12, 15, sewerY, base2Y - 1, 11, 12, sewerWallId);
				chunk.setBlocks(10, 15, base2Y - 1, base2Y, 10, 15, sewerCeilingId);
			} else {
				chunk.setBlocks(10, 15, base2Y - 1, base2Y, 14, 15, sewerCeilingId);
				chunk.setBlocks(14, 15, base2Y - 1, base2Y, 10, 14, sewerCeilingId);
			}
			
			// show the center center
			if (centerNorth) {
				chunk.setBlocks(4, 12, sewerY, base2Y - 1, 4, 5, sewerWallId);
				chunk.setBlocks(3, 13, base2Y - 1, base2Y, 3, 6, sewerCeilingId);
			}
			if (centerSouth) {
				chunk.setBlocks(4, 12, sewerY, base2Y - 1, 11, 12, sewerWallId);
				chunk.setBlocks(3, 13, base2Y - 1, base2Y, 10, 13, sewerCeilingId);
			} 
			if (centerWest) {
				chunk.setBlocks(4, 5, sewerY, base2Y - 1, 4, 12, sewerWallId);
				chunk.setBlocks(3, 6, base2Y - 1, base2Y, 3, 13, sewerCeilingId);
			}
			if (centerEast) {
				chunk.setBlocks(11, 12, sewerY, base2Y - 1, 4, 12, sewerWallId);
				chunk.setBlocks(10, 13, base2Y - 1, base2Y, 3, 13, sewerCeilingId);
			}
			
			// ceiling please
			chunk.setLayer(base2Y, 2, sewerCeilingId);
		}
	}
	
	private void placeEWBridgeCap(ByteChunk chunk, int x, int baseY, int topY) {
		chunk.setBlocks(x, x + 2, baseY, topY, 0, 16, retainingWallId);
	}
	
	private void placeEWBridgePartA(ByteChunk chunk, int x, int baseY) {
		
		// cross beam
		chunk.setBlocks(x, x + 2, baseY - 1, baseY, 0, 16, bridgeEdgeId);
		
		// edges
		chunk.setBlocks(x, x + 2, baseY, baseY + 1, 0, 1, bridgeEdgeId);
		chunk.setBlocks(x, x + 2, baseY, baseY + 1, 15, 16, bridgeEdgeId);
		
		// rails
		chunk.setBlocks(x, x + 2, baseY + 1, baseY + 2, 0, 1, bridgeRailId);
		chunk.setBlocks(x, x + 2, baseY + 1, baseY + 2, 15, 16, bridgeRailId);

		// sidewalks
		chunk.setBlocks(x, x + 2, baseY, baseY + 1, 1, 3, bridgeSidewalk2Id);
		chunk.setBlocks(x, x + 2, baseY, baseY + 1, 13, 15, bridgeSidewalk2Id);
		
		// pavement
		chunk.setBlocks(x, x + 2, baseY, baseY + 1, 3, 13, bridgePavement1Id);
	}
	
	private void placeEWBridgePartB(ByteChunk chunk, int x, int baseY) {

		// edges
		chunk.setBlocks(x, x + 2, baseY, baseY + 2, 0, 1, bridgeEdgeId);
		chunk.setBlocks(x, x + 2, baseY, baseY + 2, 15, 16, bridgeEdgeId);
		
		// rails
		chunk.setBlocks(x, x + 2, baseY + 2, baseY + 3, 0, 1, bridgeRailId);
		chunk.setBlocks(x, x + 2, baseY + 2, baseY + 3, 15, 16, bridgeRailId);

		// sidewalks
		chunk.setBlocks(x, x + 2, baseY, baseY + 1, 1, 3, bridgeSidewalk2Id);
		chunk.setBlocks(x, x + 2, baseY, baseY + 1, 13, 15, bridgeSidewalk2Id);
		chunk.setBlocks(x, x + 2, baseY + 1, baseY + 2, 1, 3, bridgeSidewalk1Id);
		chunk.setBlocks(x, x + 2, baseY + 1, baseY + 2, 13, 15, bridgeSidewalk1Id);
		
		// pavement
		chunk.setBlocks(x, x + 2, baseY, baseY + 1, 3, 13, bridgePavement2Id);
	}
	
	private void placeWBridgeColumns(ByteChunk chunk, int baseY) {
		chunk.setBlocks(0, 1, minHeight, baseY, 2, 4, bridgeEdgeId);
		chunk.setBlocks(0, 1, minHeight, baseY, 12, 14, bridgeEdgeId);
	}

	private void placeEBridgeColumns(ByteChunk chunk, int baseY) {
		chunk.setBlocks(15, 16, minHeight, baseY, 2, 4, bridgeEdgeId);
		chunk.setBlocks(15, 16, minHeight, baseY, 12, 14, bridgeEdgeId);
	}

	private void placeNSBridgeCap(ByteChunk chunk, int z, int baseY, int topY) {
		chunk.setBlocks(0, 16, baseY, topY - 1, z, z + 2, retainingWallId);
	}
	
	private void placeNSBridgePartA(ByteChunk chunk, int z, int baseY) {
		
		// cross beam
		chunk.setBlocks(0, 16, baseY - 1, baseY, z, z + 2, bridgeEdgeId);
		
		// edges
		chunk.setBlocks(0, 1, baseY, baseY + 1, z, z + 2, bridgeEdgeId);
		chunk.setBlocks(15, 16, baseY, baseY + 1, z, z + 2, bridgeEdgeId);
		
		// rails
		chunk.setBlocks(0, 1, baseY + 1, baseY + 2, z, z + 2, bridgeRailId);
		chunk.setBlocks(15, 16, baseY + 1, baseY + 2, z, z + 2, bridgeRailId);

		// sidewalks
		chunk.setBlocks(1, 3, baseY, baseY + 1, z, z + 2, bridgeSidewalk2Id);
		chunk.setBlocks(13, 15, baseY, baseY + 1, z, z + 2, bridgeSidewalk2Id);
		
		// pavement
		chunk.setBlocks(3, 13, baseY, baseY + 1, z, z + 2, bridgePavement1Id);
	}
	
	private void placeNSBridgePartB(ByteChunk chunk, int z, int baseY) {
		
		// edges
		chunk.setBlocks(0, 1, baseY, baseY + 2, z, z + 2, bridgeEdgeId);
		chunk.setBlocks(15, 16, baseY, baseY + 2, z, z + 2, bridgeEdgeId);
		
		// rails
		chunk.setBlocks(0, 1, baseY + 2, baseY + 3, z, z + 2, bridgeRailId);
		chunk.setBlocks(15, 16, baseY + 2, baseY + 3, z, z + 2, bridgeRailId);

		// sidewalks
		chunk.setBlocks(1, 3, baseY, baseY + 1, z, z + 2, bridgeSidewalk2Id);
		chunk.setBlocks(13, 15, baseY, baseY + 1, z, z + 2, bridgeSidewalk2Id);
		chunk.setBlocks(1, 3, baseY + 1, baseY + 2, z, z + 2, bridgeSidewalk1Id);
		chunk.setBlocks(13, 15, baseY + 1, baseY + 2, z, z + 2, bridgeSidewalk1Id);
		
		// pavement
		chunk.setBlocks(3, 13, baseY, baseY + 1, z, z + 2, bridgePavement2Id);
	}
	
	private void placeNBridgeColumns(ByteChunk chunk, int baseY) {
		chunk.setBlocks(2, 4, minHeight, baseY, 0, 1, bridgeEdgeId);
		chunk.setBlocks(12, 14, minHeight, baseY, 0, 1, bridgeEdgeId);
	}

	private void placeSBridgeColumns(ByteChunk chunk, int baseY) {
		chunk.setBlocks(2, 4, minHeight, baseY, 15, 16, bridgeEdgeId);
		chunk.setBlocks(12, 14, minHeight, baseY, 15, 16, bridgeEdgeId);
	}
	
	private void placeEWTunnelArch(ByteChunk chunk, int x, int baseY, byte shellId, byte tileId, byte ceilingId) {
		chunk.setBlocks(x, baseY - 2, baseY + 4, 0, shellId);

		chunk.setBlocks(x, baseY, baseY + 3, 1, tileId);
		chunk.setBlocks(x, baseY + 3, baseY + 6, 1, shellId);
		
		chunk.setBlocks(x, baseY + 3, baseY + 5, 2, tileId);
		chunk.setBlocks(x, baseY + 5, baseY + 7, 2, shellId);

		chunk.setBlocks(x, baseY + 5, baseY + 6, 3, tileId);
		chunk.setBlocks(x, baseY + 6, baseY + 8, 3, shellId);
		
		chunk.setBlocks(x, x + 1, baseY + 6, baseY + 7, 4, 12, tileId);
		chunk.setBlocks(x, x + 1, baseY + 7, baseY + 8, 4, 12, shellId);
		chunk.setBlocks(x, x + 1, baseY + 8, baseY + 9, 5, 11, shellId);
		
		if (shellId != tileId) {
			chunk.setBlock(x, baseY + 6, 5, ceilingId);
			chunk.setBlock(x, baseY + 6, 10, ceilingId);
			chunk.setBlocks(x, x + 1, baseY + 7, baseY + 8, 5, 11, airId);
		}
		
		chunk.setBlocks(x, baseY + 5, baseY + 6, 12, tileId);
		chunk.setBlocks(x, baseY + 6, baseY + 8, 12, shellId);
		
		chunk.setBlocks(x, baseY + 3, baseY + 5, 13, tileId);
		chunk.setBlocks(x, baseY + 5, baseY + 7, 13, shellId);
		
		chunk.setBlocks(x, baseY, baseY + 3, 14, tileId);
		chunk.setBlocks(x, baseY + 3, baseY + 6, 14, shellId);
		
		chunk.setBlocks(x, baseY - 2, baseY + 4, 15, shellId);
	}
	
	private void placeNSTunnelArch(ByteChunk chunk, int z, int baseY, byte shellId, byte tileId, byte ceilingId) {
		chunk.setBlocks(0, baseY - 2, baseY + 4, z, shellId);

		chunk.setBlocks(1, baseY, baseY + 3, z, tileId);
		chunk.setBlocks(1, baseY + 3, baseY + 6, z, shellId);
		
		chunk.setBlocks(2, baseY + 3, baseY + 5, z, tileId);
		chunk.setBlocks(2, baseY + 5, baseY + 7, z, shellId);

		chunk.setBlocks(3, baseY + 5, baseY + 6, z, tileId);
		chunk.setBlocks(3, baseY + 6, baseY + 8, z, shellId);
		
		chunk.setBlocks(4, 12, baseY + 6, baseY + 7, z, z + 1, tileId);
		chunk.setBlocks(4, 12, baseY + 7, baseY + 8, z, z + 1, shellId);
		chunk.setBlocks(5, 11, baseY + 8, baseY + 9, z, z + 1, shellId);
		
		if (shellId != tileId) {
			chunk.setBlock(5, baseY + 6, z, ceilingId);
			chunk.setBlock(10, baseY + 6, z, ceilingId);
			chunk.setBlocks(5, 11, baseY + 7, baseY + 8, z, z + 1, airId);
		}
		
		chunk.setBlocks(12, baseY + 5, baseY + 6, z, tileId);
		chunk.setBlocks(12, baseY + 6, baseY + 8, z, shellId);
		
		chunk.setBlocks(13, baseY + 3, baseY + 5, z, tileId);
		chunk.setBlocks(13, baseY + 5, baseY + 7, z, shellId);
		
		chunk.setBlocks(14, baseY, baseY + 3, z, tileId);
		chunk.setBlocks(14, baseY + 3, baseY + 6, z, shellId);
		
		chunk.setBlocks(15, baseY - 2, baseY + 4, z, shellId);
	}
	
	private void placeRetainingWall(ByteChunk chunk, int x, int z, int baseY, int topY) {
		chunk.setBlocks(x, baseY, topY + 1, z, retainingWallId);
		if (topY > baseY + fenceHeight)
			chunk.setBlocks(x, topY + 1, topY + fenceHeight + 1, z, retainingFenceId);
	}
	
	@Override
	protected void generateActualBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, ContextData context, int platX, int platZ) {

		// random bits
		sewerCenterBit = chunkRandom.nextBoolean();
		sewerNorthWestBias = chunkRandom.nextBoolean();
		sewerNorthEastBias = chunkRandom.nextBoolean();
		sewerSouthWestBias = chunkRandom.nextBoolean();
		sewerSouthEastBias = chunkRandom.nextBoolean();
		
		// compute offset to start of chunk
		int originX = chunk.getOriginX();
		int originZ = chunk.getOriginZ();
		
		// where do we start
		int base1Y = context.streetLevel - ContextData.FloorHeight * 2 + 1;
		int sewerY = base1Y + 1;
		int base2Y = base1Y + ContextData.FloorHeight + 1;
		int sidewalkLevel = context.streetLevel + 1;
		boolean doSewer = generator.settings.includeSewers;
		
		// look around
		SurroundingRoads roads = new SurroundingRoads(platmap, platX, platZ);
		
		// what are we making?
		if (HeightInfo.getHeightsFast(generator, originX, originZ).isSea()) {
			doSewer = false;

			// draw a bridge bits
			// bridge to the east/west
			if (roads.toWest() && roads.toEast()) {
				if (HeightInfo.getHeightsFast(generator, originX - chunk.width, originZ).isSea() &&
					HeightInfo.getHeightsFast(generator, originX + chunk.width, originZ).isSea()) {
					
					// lights please
					generateLightPost(chunk, context, sidewalkLevel + 5, 7, 0);
					generateLightPost(chunk, context, sidewalkLevel + 5, 8, 15);
				}
				
			} else if (roads.toNorth() && roads.toSouth()) {
				if (HeightInfo.getHeightsFast(generator, originX, originZ - chunk.width).isSea() && 
					HeightInfo.getHeightsFast(generator, originX, originZ + chunk.width).isSea()) {
					
					// lights please
					generateLightPost(chunk, context, sidewalkLevel + 5, 0, 7);
					generateLightPost(chunk, context, sidewalkLevel + 5, 15, 8);
				}
			}
		
		} else {
			
			// tunnel please
			if (maxHeight > sidewalkLevel + tunnelHeight) {
				doSewer = false;
				
				// tunnel to the east/west
				if (roads.toWest() && roads.toEast()) {
					chunk.setBlock(3, sidewalkLevel + 7, 8, context.lightMat, true);
					chunk.setBlock(12, sidewalkLevel + 7, 7, context.lightMat, true);
				} else if (roads.toNorth() && roads.toSouth()) {
					chunk.setBlock(8, sidewalkLevel + 7, 3, context.lightMat, true);
					chunk.setBlock(7, sidewalkLevel + 7, 12, context.lightMat, true);
				}
				
				// add nature on top
				generateSurface(generator, platmap, chunk, context, platX, platZ, true);
				
			// stuff that only can happen outside of tunnels and bridges
			} else {
				
				// light posts
				generateLightPost(chunk, context, sidewalkLevel, sidewalkWidth - 1, sidewalkWidth - 1);
				generateLightPost(chunk, context, sidewalkLevel, chunk.width - sidewalkWidth, chunk.width - sidewalkWidth);
			}
		}
		
		// sewer?
		if (doSewer) {
			
			// defaults
			boolean vaultNorthWest = false;
			boolean vaultSouthWest = false;
			boolean vaultNorthEast = false;
			boolean vaultSouthEast = false;
			boolean centerNorth = !roads.toNorth();
			boolean centerSouth = !roads.toSouth();
			boolean centerWest = !roads.toWest();
			boolean centerEast = !roads.toEast();
			
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
				chunk.setTrapDoor(3, sidewalkLevel, 2, TrapDoor.WEST);
				
				// ladder
				chunk.setLadder(3, sewerY, sidewalkLevel, 2, Ladder.WEST);
			}
			
			// figure out the center
			if (!(vaultNorthWest && vaultNorthEast && vaultSouthWest && vaultSouthEast)) {
				centerNorth = sewerCenterBit || (vaultNorthWest && vaultNorthEast);
				centerSouth = sewerCenterBit || (vaultSouthWest && vaultSouthEast);
				centerWest = sewerCenterBit || (vaultNorthWest && vaultSouthWest);
				centerEast = sewerCenterBit || (vaultNorthEast && vaultSouthEast);
			}
			
			// cardinal directions known walls and ditches
			if (roads.toNorth()) {
				chunk.setBlock(8, sewerY - 1, 3, Material.WATER);
				generateEntryVines(chunk, base2Y - 1, Direction.Vine.NORTH, 6, 1, 7, 1, 8, 1, 9, 1);
			}
			if (roads.toSouth()) {
				chunk.setBlock(7, sewerY - 1, 12, Material.WATER);
				generateEntryVines(chunk, base2Y - 1, Direction.Vine.SOUTH, 6, 14, 7, 14, 8, 14, 9, 14);
			}
			if (roads.toWest()) {
				chunk.setBlock(3, sewerY - 1, 7, Material.WATER);
				generateEntryVines(chunk, base2Y - 1, Direction.Vine.WEST, 1, 6, 1, 7, 1, 8, 1, 9);
			}
			if (roads.toEast()) {
				chunk.setBlock(12, sewerY - 1, 8, Material.WATER);
				generateEntryVines(chunk, base2Y - 1, Direction.Vine.EAST, 14, 6, 14, 7, 14, 8, 14, 9);
			}
			
			// add the various doors
			if (vaultNorthWest) {
				generateDoor(chunk, 4, sewerY, 1, Direction.Door.EASTBYNORTHEAST);
				generateDoor(chunk, 1, sewerY, 4, Direction.Door.SOUTHBYSOUTHWEST);
			}
			if (vaultNorthEast) {
				generateDoor(chunk, 11, sewerY, 1, Direction.Door.WESTBYNORTHWEST);
				generateDoor(chunk, 14, sewerY, 4, Direction.Door.SOUTHBYSOUTHEAST);
			}
			if (vaultSouthWest) {
				generateDoor(chunk, 1, sewerY, 11, Direction.Door.NORTHBYNORTHWEST);
				generateDoor(chunk, 4, sewerY, 14, Direction.Door.EASTBYSOUTHEAST);
			}
			if (vaultSouthEast) {
				generateDoor(chunk, 14, sewerY, 11, Direction.Door.NORTHBYNORTHEAST);
				generateDoor(chunk, 11, sewerY, 14, Direction.Door.WESTBYSOUTHWEST);
			}
			
			// we might put down a plank... or maybe not...
			boolean placedPlank = false;
			
			// fancy up the center walls?
			if (centerNorth) {
				generateDoor(chunk, 10, sewerY, 4, Direction.Door.NORTHBYNORTHEAST);
				chunk.setStoneSlab(7, sewerY, 4, Direction.StoneSlab.COBBLESTONEFLIP);
				chunk.setStoneSlab(8, sewerY, 4, Direction.StoneSlab.COBBLESTONEFLIP);
			} else if (!placedPlank && roads.toNorth() && chunkRandom.nextBoolean()) {
				placedPlank = true;
				chunk.setBlocks(6, 10, sewerY, 5, 6, sewerPlankMaterial, sewerPlankData); 
			}
			if (centerSouth) {
				generateDoor(chunk, 5, sewerY, 11, Direction.Door.SOUTHBYSOUTHWEST);
				chunk.setStoneSlab(7, sewerY, 11, Direction.StoneSlab.COBBLESTONEFLIP);
				chunk.setStoneSlab(8, sewerY, 11, Direction.StoneSlab.COBBLESTONEFLIP);
			} else if (!placedPlank && roads.toSouth() && chunkRandom.nextBoolean()) {
				placedPlank = true;
				chunk.setBlocks(6, 10, sewerY, 10, 11, sewerPlankMaterial, sewerPlankData);
			} 
			if (centerWest) {
				generateDoor(chunk, 4, sewerY, 5, Direction.Door.WESTBYNORTHWEST);
				chunk.setStoneSlab(4, sewerY, 7, Direction.StoneSlab.COBBLESTONEFLIP);
				chunk.setStoneSlab(4, sewerY, 8, Direction.StoneSlab.COBBLESTONEFLIP);
			} else if (!placedPlank && roads.toWest() && chunkRandom.nextBoolean()) {
				placedPlank = true;
				chunk.setBlocks(5, 6, sewerY, 6, 10, sewerPlankMaterial, sewerPlankData);
			}
			if (centerEast) { 
				generateDoor(chunk, 11, sewerY, 10, Direction.Door.EASTBYSOUTHEAST);
				chunk.setStoneSlab(11, sewerY, 7, Direction.StoneSlab.COBBLESTONEFLIP);
				chunk.setStoneSlab(11, sewerY, 8, Direction.StoneSlab.COBBLESTONEFLIP);
			} else if (!placedPlank && roads.toEast() && chunkRandom.nextBoolean()) {
				placedPlank = true;
				chunk.setBlocks(10, 11, sewerY, 6, 10, sewerPlankMaterial, sewerPlankData);
			}
			
			// populate the vaults
			if (vaultNorthWest) {
				if (!(roads.toNorth() && roads.toWest())) // special case for manholes
					generateTreat(generator, context, chunk, 2, sewerY, 2);
			}
			if (vaultNorthEast) {
				generateTreat(generator, context, chunk, 13, sewerY, 2);
			}
			if (vaultSouthWest) {
				generateTreat(generator, context, chunk, 2, sewerY, 13);
			}
			if (vaultSouthEast) {
				generateTreat(generator, context, chunk, 13, sewerY, 13);
			}
			if (centerNorth && centerSouth && centerWest && centerEast) {
				
				// look carefully, these are actually different
				switch(chunkRandom.nextInt(4)) {
				case 1:
					generateTreat(generator, context, chunk, 6, sewerY, 6);
					generateTrick(generator, context, chunk, 9, sewerY, 9);
					break;
				case 2:
					generateTreat(generator, context, chunk, 9, sewerY, 6);
					generateTrick(generator, context, chunk, 6, sewerY, 9);
					break;
				case 3:
					generateTreat(generator, context, chunk, 6, sewerY, 9);
					generateTrick(generator, context, chunk, 9, sewerY, 6);
					break;
				default:
					generateTreat(generator, context, chunk, 9, sewerY, 9);
					generateTrick(generator, context, chunk, 6, sewerY, 6);
					break;
				}
			} else {
				if (centerNorth) {
					if (vaultNorthWest && !vaultNorthEast)
						generateTrick(generator, context, chunk, 6, sewerY, 2);
					else if (vaultNorthEast && !vaultNorthWest)
						generateTrick(generator, context, chunk, 9, sewerY, 2);
				}
				if (centerSouth) {
					if (vaultSouthWest && !vaultSouthEast)
						generateTrick(generator, context, chunk, 6, sewerY, 13);
					else if (vaultSouthEast && !vaultSouthWest)
						generateTrick(generator, context, chunk, 9, sewerY, 13);
				}
				if (centerWest) {
					if (vaultNorthWest && !vaultSouthWest)
						generateTrick(generator, context, chunk, 2, sewerY, 6);
					else if (vaultSouthWest && !vaultNorthWest)
						generateTrick(generator, context, chunk, 2, sewerY, 9);
				}
				if (centerEast) {
					if (vaultNorthEast && !vaultSouthEast)
						generateTrick(generator, context, chunk, 13, sewerY, 6);
					else if (vaultSouthEast && !vaultNorthEast)
						generateTrick(generator, context, chunk, 13, sewerY, 9);
				}
			}
			
			// now the vines
			for (int i = 2; i < 14; i++) {
				generateHangingVine(chunk, base2Y - 1, Direction.Vine.NORTH, i, 2, i, 1);
//				generateHangingVine(chunk, base2Y - 1, Direction.Vine.NORTH, i, 5, i, 4);
//				generateHangingVine(chunk, base2Y - 1, Direction.Vine.SOUTH, i, 10, i, 11);
				generateHangingVine(chunk, base2Y - 1, Direction.Vine.SOUTH, i, 13, i, 14);
					
				generateHangingVine(chunk, base2Y - 1, Direction.Vine.WEST, 2, i, 1, i);
//				generateHangingVine(chunk, base2Y - 1, Direction.Vine.WEST, 5, i, 4, i);
//				generateHangingVine(chunk, base2Y - 1, Direction.Vine.EAST, 10, i, 11, i);
				generateHangingVine(chunk, base2Y - 1, Direction.Vine.EAST, 13, i, 14, i);
			}
		}
	}
	
	private void generateEntryVines(RealChunk chunk, int y, Direction.Vine direction,
			int x1, int z1, int x2, int z2, int x3, int z3, int x4, int z4) {
		if (chunkRandom.nextBoolean())
			chunk.setVine(x1, y, z1, direction);
		if (chunkRandom.nextBoolean())
			chunk.setVine(x2, y, z2, direction);
		if (chunkRandom.nextBoolean())
			chunk.setVine(x3, y, z3, direction);
		if (chunkRandom.nextBoolean())
			chunk.setVine(x4, y, z4, direction);
	}
	
	private void generateHangingVine(RealChunk chunk, int y, Direction.Vine direction, int x1, int z1, int x2, int z2) {
		if (chunkRandom.nextBoolean() && chunk.isEmpty(x1, y, z1) && !chunk.isEmpty(x2, y, z2))
			chunk.setVine(x1, y, z1, direction);
	}
	
	private void generateLightPost(RealChunk chunk, ContextData context, int sidewalkLevel, int x, int z) {
		chunk.setBlock(x, sidewalkLevel, z, lightpostbaseMaterial);
		chunk.setBlocks(x, sidewalkLevel + 1, sidewalkLevel + lightpostHeight + 1, z, lightpostMaterial);
		chunk.setBlock(x, sidewalkLevel + lightpostHeight + 1, z, context.lightMat, true);
	}
	
	private void generateDoor(RealChunk chunk, int x, int y, int z, Direction.Door direction) {
		switch (chunkRandom.nextInt(5)) {
		case 1:
			chunk.setBlocks(x, y, y + 2, z, Material.BRICK);
			break;
		case 2:
			chunk.setBlocks(x, y, y + 2, z, Material.IRON_FENCE);
			break;
		case 3:
			chunk.setBlocks(x, y, y + 2, z, Material.AIR);
			break;
		case 4:
			chunk.setIronDoor(x, y, z, direction);
			break;
		default:
			chunk.setWoodenDoor(x, y, z, direction);
			break;
		}
	}
	
	private void generateRoundedOut(ByteChunk chunk, ContextData context, int x, int z, boolean toNorth, boolean toEast) {
		int sidewalkLevel = context.streetLevel + 1;
		
		// long bits
		for (int i = 0; i < 4; i++) {
			chunk.setBlock(toNorth ? x + 3 : x, sidewalkLevel, z + i, sidewalkId);
			chunk.setBlock(x + i, sidewalkLevel, toEast ? z + 3 : z, sidewalkId);
		}
		
		// little notch
		chunk.setBlock(toNorth ? x + 2 : x + 1, 
					   sidewalkLevel, 
					   toEast ? z + 2 : z + 1, 
					   sidewalkId);
	}
	
	private void generateTreat(WorldGenerator generator, ContextData context, RealChunk chunk, int x, int y, int z) {
		
		// cool stuff?
		if (generator.settings.treasuresInSewers && chunkRandom.nextDouble() <= context.oddsOfTreasureInSewers) {
			 chunk.setChest(x, y, z, Direction.Chest.NORTH, generator.getLootProvider().getItems(generator, LootProvider.chestInSewers));
		}
	}

	private void generateTrick(WorldGenerator generator, ContextData context, RealChunk chunk, int x, int y, int z) {
		
		// not so cool stuff?
		if (generator.settings.spawnersInSewers && chunkRandom.nextDouble() <= context.oddsOfSpawnerInSewers) {
			chunk.setSpawner(x, y, z, generator.getSpawnProvider().getEntity(generator, SpawnProvider.spawnerInSewers));
		}
	}
}
