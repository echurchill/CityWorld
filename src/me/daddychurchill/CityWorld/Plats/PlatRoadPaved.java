package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import me.daddychurchill.CityWorld.CityWorld;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.Context.PlatMapContext;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.Direction.Chest;
import me.daddychurchill.CityWorld.Support.Direction.Ladder;
import me.daddychurchill.CityWorld.Support.Direction.TrapDoor;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.SurroundingRoads;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import org.bukkit.inventory.ItemStack;

public class PlatRoadPaved extends PlatRoad {
	//TODO Lines on the road
	
	protected final static int sidewalkWidth = 3;
	protected final static int lightpostHeight = 3;
	protected final static int crossDitchEdge = 7;
	protected final static int vaultWidth = 5;
	protected final static int vaultDoorOffset = 2;
	protected final static int waterOffset = 3;
	protected final static int tunnelHeight = 10;
	protected final static int fenceHeight = 2;
	
	protected final static Material airMaterial = Material.AIR;
	protected final static Material lightpostbaseMaterial = Material.DOUBLE_STEP;
	protected final static Material lightpostMaterial = Material.FENCE;
	protected final static Material lightMaterial = Material.GLOWSTONE;
	protected final static Material manpipeMaterial = Material.STONE;
	protected final static Material sewerWallMaterial = Material.MOSSY_COBBLESTONE;
	protected final static Material vineMaterial = Material.VINE;

	protected final static byte airId = (byte) airMaterial.getId();
	protected final static byte sewerFloorId = (byte) Material.COBBLESTONE.getId();
	protected final static byte sewerWallId = (byte) sewerWallMaterial.getId();
	protected final static byte sewerCeilingId = sewerFloorId;
	protected final static byte doorBrickId = (byte) Material.BRICK.getId();
	protected final static byte doorIronId = (byte) Material.IRON_FENCE.getId();
	protected final static byte waterId = (byte) Material.WATER.getId();
	protected final static byte pavementId = (byte) Material.STONE.getId();
	protected final static byte sidewalkId = (byte) Material.STEP.getId();
	protected final static byte retainingWallId = (byte) Material.DOUBLE_STEP.getId();
	protected final static byte fenceId = (byte) Material.IRON_FENCE.getId();
	
	public PlatRoadPaved(Random random, PlatMap platmap, long globalconnectionkey) {
		super(random, platmap);

		// all paved roads are interconnected
		connectedkey = globalconnectionkey;
	}

	@Override
	public void generateChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, PlatMapContext context, int platX, int platZ) {
		super.generateChunk(generator, platmap, chunk, biomes, context, platX, platZ);
	
		// compute offset to start of chunk
		int originX = chunk.getOriginX();
		int originZ = chunk.getOriginZ();
		
		// where do we start
		int base1Y = context.streetLevel - PlatMapContext.FloorHeight * 2 + 1;
		int sewerY = base1Y + 1;
		int base2Y = base1Y + PlatMapContext.FloorHeight + 1;
		int sidewalkLevel = context.streetLevel + 1;
		boolean doSewer = context.doSewer;
		
		// look around
		SurroundingRoads roads = new SurroundingRoads(platmap, platX, platZ);
		
		// easy stuff
		//CityWorld.log.info("Avg = " + averageHeight + " Min/Max = " + minHeight + "/" + maxHeight + " vs. sidewalk = " + sidewalkLevel);
		chunk.setLayer(context.streetLevel + 1, tunnelHeight, airId);

		// ok, deep enough for a bridge
//		if (averageHeight < sidewalkLevel - 2) {
		if (maxHeight < sidewalkLevel - 2) {
			doSewer = false;

			// draw a bridge
			chunk.setLayer(sidewalkLevel - 1, (byte) Material.LAPIS_BLOCK.getId());
		
		} else {
			
			// draw pavement
			chunk.setLayer(context.streetLevel, pavementId);
			
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
				chunk.setLayer(sidewalkLevel - 1, (byte) Material.DIAMOND_BLOCK.getId());
				
				// tunnel to the east/west
				if (roads.toWest() && roads.toEast()) {
					
				} else if (roads.toNorth() && roads.toSouth()) {
					
				}
				
			// retaining walls please
			} else if (maxHeight > sidewalkLevel) {
				
				// wall to the east/west
				if (roads.toWest() && roads.toEast()) {
					for (int x = 0; x < chunk.width; x++) {
						placeRetainingWall(chunk, x, 0, sidewalkLevel, generator.findBlockY(originX + x, originZ - 1));
						placeRetainingWall(chunk, x, 15, sidewalkLevel, generator.findBlockY(originX + x, originZ + 16));
					}
				} else if (roads.toNorth() && roads.toSouth()) {
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
			
			// draw/fill vaults and ceiling inset
			generateVault(chunk, context, 0, vaultWidth, 
					sewerY, 
					0, vaultWidth, true, true);
			generateVault(chunk, context, 0, vaultWidth, 
					sewerY, 
					chunk.width - vaultWidth, chunk.width, true, true);
			generateVault(chunk, context, chunk.width - vaultWidth, chunk.width, 
					sewerY, 
					0, vaultWidth, true, true);
			generateVault(chunk, context, chunk.width - vaultWidth, chunk.width, 
					sewerY, 
					chunk.width - vaultWidth, chunk.width, true, true);
			generateCeilingInset(chunk, context, vaultWidth, chunk.width - vaultWidth,
					sewerY,
					vaultWidth, chunk.width - vaultWidth, 
					!roads.toWest(), !roads.toEast(), !roads.toNorth(), !roads.toSouth());
		
			// now cardinal water, vaults and insets
			if (roads.toWest()) {
				chunk.setBlocks(0, crossDitchEdge, 
								sewerY - 1, sewerY, 
								crossDitchEdge, chunk.width - crossDitchEdge, airId);
				generateCeilingInset(chunk, context, 0, vaultWidth,
						sewerY, 
						vaultWidth, chunk.width - vaultWidth, false, false, true, true);
				placeWater(chunk, waterOffset, sewerY - 1, crossDitchEdge);
			} else {
				generateVault(chunk, context, 0, vaultWidth,
						sewerY, 
						vaultWidth, chunk.width - vaultWidth, false, true);
			}
			if (roads.toEast()) {
				chunk.setBlocks(chunk.width - crossDitchEdge, chunk.width, 
								sewerY - 1, sewerY, 
								crossDitchEdge, chunk.width - crossDitchEdge, airId);
				generateCeilingInset(chunk, context, chunk.width - vaultWidth, chunk.width,
						sewerY, 
						vaultWidth, chunk.width - vaultWidth, false, false, true, true);
				placeWater(chunk, chunk.width - waterOffset - 1, sewerY - 1, chunk.width - crossDitchEdge - 1);
			} else {
				generateVault(chunk, context, chunk.width - vaultWidth, chunk.width,
						sewerY, 
						vaultWidth, chunk.width - vaultWidth, false, true);
			}
			if (roads.toNorth()) {
				chunk.setBlocks(crossDitchEdge, chunk.width - crossDitchEdge, 
								sewerY - 1, sewerY, 
								0, crossDitchEdge, airId);
				generateCeilingInset(chunk, context, vaultWidth, chunk.width - vaultWidth,
						sewerY,
						0, vaultWidth, true, true, false, false);
				placeWater(chunk, crossDitchEdge, sewerY - 1, waterOffset);
			} else {
				generateVault(chunk, context, vaultWidth, chunk.width - vaultWidth,
						sewerY, 
						0, vaultWidth, true, false);
			}
			if (roads.toSouth()) {
				chunk.setBlocks(crossDitchEdge, chunk.width - crossDitchEdge, 
								sewerY - 1, sewerY, 
								chunk.width - crossDitchEdge, chunk.width, airId);
				generateCeilingInset(chunk, context, vaultWidth, chunk.width - vaultWidth,
						sewerY, 
						chunk.width - vaultWidth, chunk.width, true, true, false, false);
				placeWater(chunk, chunk.width - crossDitchEdge - 1, sewerY - 1, chunk.width - waterOffset - 1);
			} else {
				generateVault(chunk, context, vaultWidth, chunk.width - vaultWidth,
						sewerY, 
						chunk.width - vaultWidth, chunk.width, true, false);
			}
			
			// ceiling please
			chunk.setLayer(base2Y, 2, sewerCeilingId);
		}
	}
	
	private void placeRetainingWall(ByteChunk chunk, int x, int z, int baseY, int topY) {
		chunk.setBlocks(x, baseY, topY + 1, z, retainingWallId);
		if (topY > baseY + fenceHeight)
			chunk.setBlocks(x, topY + 1, topY + fenceHeight + 1, z, fenceId);
	}
	
	private void placeWater(ByteChunk chunk, int x, int y, int z) {
		chunk.setBlock(x, y, z, waterId);
	}
	
	@Override
	public void generateBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, PlatMapContext context, int platX, int platZ) {

		// where do we start
		int base1Y = context.streetLevel - PlatMapContext.FloorHeight * 2 + 1;
		int sewerY = base1Y + 1;
		int base2Y = base1Y + PlatMapContext.FloorHeight + 1;
		int sidewalkLevel = context.streetLevel + 1;
		boolean doSewer = context.doSewer;
		
		// look around
		SurroundingRoads roads = new SurroundingRoads(platmap, platX, platZ);
		
		// what are we making?
		if (maxHeight < sidewalkLevel - 2) {
			doSewer = false;

			// draw a bridge bits
		
		} else {
			
			// what we do regardless
			
			// tunnel please
			if (maxHeight > sidewalkLevel + tunnelHeight) {
				doSewer = false;
				
				// tunnel to the east/west
				if (roads.toWest() && roads.toEast()) {
					
				} else if (roads.toNorth() && roads.toSouth()) {
					
				}
				
//			// retaining walls 
//			} else if (maxHeight > sidewalkLevel) {
//				
//							
			// stuff that only can happen outside of tunnels and bridges
			} else {
				
				// light posts
				generateLightPost(chunk, context, sidewalkWidth - 1, sidewalkWidth - 1);
				generateLightPost(chunk, context, chunk.width - sidewalkWidth, chunk.width - sidewalkWidth);
			}
		}
		
		// sewer?
		if (doSewer) {
			
			// drill down
			if (roads.toEast() && roads.toNorth())
				generateManhole(chunk, chunk.width - sidewalkWidth, 
								base2Y,
								sidewalkLevel,
								chunk.width - sidewalkWidth - 1);
			
			// ok, then make it a normal vault
			else
				populateVault(chunk, context, chunk.width - vaultWidth, chunk.width, 
						sewerY, 
						chunk.width - vaultWidth, chunk.width);
			
			// draw/fill vaults and ceiling inset
			populateVault(chunk, context, 0, vaultWidth, 
					sewerY, 
					0, vaultWidth);
			populateVault(chunk, context, 0, vaultWidth, 
					sewerY, 
					chunk.width - vaultWidth, chunk.width);
			populateVault(chunk, context, chunk.width - vaultWidth, chunk.width, 
					sewerY, 
					0, vaultWidth);
		
			// now cardinal water, vaults and insets
			if (!roads.toWest()) {
				populateVault(chunk, context, 0, vaultWidth,
						sewerY, 
						vaultWidth, chunk.width - vaultWidth);
			}
			if (!roads.toEast()) {
				populateVault(chunk, context, chunk.width - vaultWidth, chunk.width,
						sewerY, 
						vaultWidth, chunk.width - vaultWidth);
			}
			if (!roads.toNorth()) {
				populateVault(chunk, context, vaultWidth, chunk.width - vaultWidth,
						sewerY, 
						0, vaultWidth);
			}
			if (!roads.toSouth()) {
				populateVault(chunk, context, vaultWidth, chunk.width - vaultWidth,
						sewerY, 
						chunk.width - vaultWidth, chunk.width);
			}
			
//			// now cardinal water, vaults and insets
//			if (roads.toSouth()) {
//				generateCeilingVines(chunk, context, 0, vaultWidth,
//						sewerY, 
//						vaultWidth, chunk.width - vaultWidth, false, false, true, true);
//			}
//			if (roads.toNorth()) {
//				generateCeilingVines(chunk, context, chunk.width - vaultWidth, chunk.width,
//						sewerY, 
//						vaultWidth, chunk.width - vaultWidth, false, false, true, true);
//			}
//			if (roads.toWest()) {
//				generateCeilingVines(chunk, context, vaultWidth, chunk.width - vaultWidth,
//						sewerY,
//						0, vaultWidth, true, true, false, false);
//			}
//			if (roads.toEast()) {
//				generateCeilingVines(chunk, context, vaultWidth, chunk.width - vaultWidth,
//						sewerY, 
//						chunk.width - vaultWidth, chunk.width, true, true, false, false);
//			}
		}
	}
	
//	protected void generateCeilingVines(RealChunk chunk, PlatMapContext context, int x1, int x2, int y1, int z1, int z2, 
//			boolean insetS, boolean insetN, boolean insetE, boolean insetW) {
//		int y = y1 + PlatMap.FloorHeight - 1;
//		
//		if (insetS || insetN)
//			for (int z = z1; z < z2; z++) {
//				if (insetS) {
//					if (rand.nextInt(context.oddsOfSewerVines) == 0)
//						chunk.setBlock(x1 + 1, y, z, vineMaterial);
//				}
//				if (insetN) {
//					if (rand.nextInt(context.oddsOfSewerVines) == 0)
//						chunk.setBlock(x2 - 2, y, z, vineMaterial);
//				}
//			}
//		if (insetE || insetW)
//			for (int x = x1; x < x2; x++) {
//				if (insetE) {
//					if (rand.nextInt(context.oddsOfSewerVines) == 0)
//						chunk.setBlock(x, y, z1 + 1, vineMaterial);
//				}
//				if (insetW) {
//					if (rand.nextInt(context.oddsOfSewerVines) == 0)
//						chunk.setBlock(x, y, z2 - 2, vineMaterial);
//				}
//			}
//	}
	
	protected void generateLightPost(RealChunk chunk, PlatMapContext context, int x, int z) {
		int sidewalkLevel = context.streetLevel + 1;
		chunk.setBlock(x, sidewalkLevel, z, lightpostbaseMaterial);
		chunk.setBlocks(x, sidewalkLevel + 1, sidewalkLevel + lightpostHeight + 1, z, lightpostMaterial);
		chunk.setBlock(x, sidewalkLevel + lightpostHeight + 1, z, lightMaterial, true);
	}
	
	protected void generateManhole(RealChunk chunk, int x, int y1, int y2, int z) {
		// place the manhole
		chunk.setTrapDoor(x, y2, z, TrapDoor.SOUTH);
		
		// make a tube of material going down
		chunk.setBlocks(x - 1, x + 2, y1 + 1, y2 - 1, z - 1, z + 2, manpipeMaterial);
		
		// empty the vault and fix up the doors
		chunk.setBlocks(x - 1, x + vaultWidth - 3, y1 - PlatMapContext.FloorHeight, y1, z, z + vaultWidth - 2, airMaterial);
		chunk.setBlocks(x, y1 - PlatMapContext.FloorHeight, y1 - PlatMapContext.FloorHeight + 2, z - 1, sewerWallMaterial);
		chunk.setWoodenDoor(x + 1, y1 - PlatMapContext.FloorHeight, z - 1, Direction.Door.NORTHBYNORTHEAST);
		
		// ladder
		chunk.setLadder(x, y1 - PlatMapContext.FloorHeight, y2, z, Ladder.SOUTH);
	}
	
	protected void generateRoundedOut(ByteChunk chunk, PlatMapContext context, int x, int z, boolean toNorth, boolean toEast) {
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
	
	protected void generateCeilingInset(ByteChunk chunk, PlatMapContext context, int x1, int x2, int y1, int z1, int z2, 
			boolean insetS, boolean insetN, boolean insetE, boolean insetW) {
		int y = y1 + PlatMapContext.FloorHeight - 1;
		
		if (insetS || insetN)
			for (int z = z1; z < z2; z++) {
				if (insetS) {
					chunk.setBlock(x1, y, z, sewerWallId);
				}
				if (insetN) {
					chunk.setBlock(x2 - 1, y, z, sewerWallId);
				}
			}
		if (insetE || insetW)
			for (int x = x1; x < x2; x++) {
				if (insetE) {
					chunk.setBlock(x, y, z1, sewerWallId);
				}
				if (insetW) {
					chunk.setBlock(x, y, z2 - 1, sewerWallId);
				}
			}
	}
	
	protected void generateVault(ByteChunk chunk, PlatMapContext context, int x1, int x2, int y1, int z1, int z2, boolean doorNS, boolean doorEW) {
		Random random = chunk.random;
		int y2 = y1 + PlatMapContext.FloorHeight;
		
		// place the walls
		for (int x = x1; x < x2; x++) {
			chunk.setBlocks(x, y1, y2, z1, sewerWallId);
			chunk.setBlocks(x, y1, y2, z2 - 1, sewerWallId);
		}
		for (int z = z1; z < z2; z++) {
			chunk.setBlocks(x1, y1, y2, z, sewerWallId);
			chunk.setBlocks(x2 - 1, y1, y2, z, sewerWallId);
		}
		
		// is the vault empty?
		byte doorId = random.nextBoolean() ? doorIronId : doorBrickId;
		
		// place the doors, if the vault is empty then just "leave the door" open
		if (doorNS) {
			chunk.setBlocks(x1    , y1, y2 - 2, z1 + vaultDoorOffset, doorId);
			chunk.setBlocks(x2 - 1, y1, y2 - 2, z1 + vaultDoorOffset, doorId);
		}
		if (doorEW) {
			chunk.setBlocks(x1 + vaultDoorOffset, y1, y2 - 2, z1    , doorId);
			chunk.setBlocks(x1 + vaultDoorOffset, y1, y2 - 2, z2 - 1, doorId);
		}
		
		// if it's bricked up... maybe this is a 
		if (doorId == doorBrickId && context.doOresInSewer && random.nextInt(context.oddsOfSewerOres) == 0) {
			byte vaultId = (byte) pickVaultContent(random).getId();
			chunk.setBlocks(x1 + 1, x2 - 1, y1, y2 - random.nextInt(3) - 1, z1 + 1, z2 - 1, vaultId);
		}
	}

	protected Material pickVaultContent(Random random) {
		switch (random.nextInt(100)) {
		// random junk
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
			return Material.DIRT;
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
			return Material.SAND;
		case 15:
		case 16:
		case 17:
		case 18:
		case 19:
			return Material.GRAVEL;
		case 20:
		case 21:
		case 22:
		case 23:
		case 24:
			return Material.CLAY;

		// raw ores
		case 25:
		case 26:
		case 27:
		case 28:
		case 29:
			return Material.IRON_ORE;
		case 30:
		case 31:
		case 32:
		case 33:
		case 34:
			return Material.COAL_ORE;
		case 35:
		case 36:
		case 37:
			return Material.GOLD_ORE;
		case 38:
		case 39:
		case 40:
			return Material.LAPIS_ORE;
		case 41:
		case 42:
			return Material.DIAMOND_ORE;
		case 43:
		case 44:
			return Material.REDSTONE_ORE;
		
		// pure ores
		case 45:
		case 46:
		case 47:
		case 48:
			return Material.IRON_BLOCK;
		case 49:
		case 50:
			return Material.GOLD_BLOCK;
		case 51:
		case 52:
			return Material.LAPIS_BLOCK;
		case 53:
			return Material.DIAMOND_BLOCK;
		
		// odd items
		case 54:
			return Material.TNT;
		case 55:
			return Material.SPONGE;
		case 56:
			return Material.SOUL_SAND;
		case 57:
			return Material.NETHERRACK;
		case 58:
		case 59:
		case 60:
			return Material.LAVA;
		case 61:
			return Material.SNOW_BLOCK;
		case 62:
			return Material.ICE;
		case 63:
		case 64:
		case 65:
			return Material.WATER;
		
		// the rest of the time it is empty
		default:
			return Material.AIR;
		}
	}
	
	private int minTreasureId = Material.IRON_SPADE.getId();
	private int maxTreasureId = Material.ROTTEN_FLESH.getId();
	private int countTreasureIds = maxTreasureId - minTreasureId;
	
	protected void populateVault(RealChunk chunk, PlatMapContext context, int x1, int x2, int y1, int z1, int z2) {
		Random random = chunk.random;
		//int y2 = y1 + PlatMap.FloorHeight;
		
		// fill the vault
		if (context.doTreasureInSewer) {
			
			// trick or treat?
			if (random.nextInt(context.oddsOfSewerTreasure) == 0) {

				// where is it?
				int xC = (x2 - x1) / 2 + x1;
				int zC = (z2 - z1) / 2 + z1;
				
				// only if there is nothing there yet
				if (chunk.getBlock(xC, y1, zC) == Material.AIR) {
				
					// trick or treat?
					if (context.doSpawnerInSewer && random.nextInt(context.oddsOfSewerTrick) == 0) {
						chunk.setSpawner(xC, y1, zC, pickTrick(random));
					} else {
						
						// fabricate the treasures
						int treasureCount = random.nextInt(context.maxTreasureCount) + 1;
						ItemStack[] items = new ItemStack[treasureCount];
						for (int i = 0; i < treasureCount; i++) {
							items[i] = new ItemStack(random.nextInt(countTreasureIds) + minTreasureId, random.nextInt(2) + 1);
						}
						
						// make a chest and stuff the stuff into it
						chunk.setChest(xC, y1, zC, Chest.NORTH, items);
					}
				}
			}
		}
	}
	
	protected EntityType pickTrick(Random random) {
		switch (random.nextInt(8)) {
		case 1:
			return EntityType.CREEPER;
		case 2:
			return EntityType.PIG_ZOMBIE;
		case 3:
			return EntityType.SKELETON;
		case 4:
			return EntityType.SPIDER;
		case 5:
			return EntityType.ZOMBIE;
		case 6:
			return EntityType.CAVE_SPIDER;
		case 7:
			return EntityType.SILVERFISH;
		default:
			return EntityType.ENDERMAN;
		}
	}
}
