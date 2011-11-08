package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import me.daddychurchill.CityWorld.PlatMaps.PlatMap;
import me.daddychurchill.CityWorld.Support.Chunk;
import me.daddychurchill.CityWorld.Support.SurroundingRoads;

import org.bukkit.Material;

public class PlatRoadPaved extends PlatRoad {
	//TODO Lines on the road
	
	protected static long connectedkeyForPavedRoads = 0;
	
	protected final static int oddsOfPlumbingConnection = 2;
	protected final static int oddsOfPlumbingTreasure = 4;
	
	protected final static int sidewalkWidth = 3;
	protected final static int lightpostHeight = 3;
	protected final static int sidewalkLevel = PlatMap.StreetLevel + 1;
	protected final static int crossDitchEdge = 7;
	protected final static int vaultWidth = 5;
	protected final static int vaultDoorOffset = 2;
	protected final static int waterOffset = 3;
	
	protected final static byte airId = (byte) Material.AIR.getId();
	protected final static byte sewerFloorId = (byte) Material.COBBLESTONE.getId();
	protected final static byte sewerWallId = (byte) Material.MOSSY_COBBLESTONE.getId();
	protected final static byte plumbingId = (byte) Material.OBSIDIAN.getId();
	protected final static byte brickId = (byte) Material.BRICK.getId();
	protected final static byte waterId = (byte) Material.WATER.getId();
	protected final static byte pavementId = (byte) Material.STONE.getId();
	protected final static byte sidewalkId = (byte) Material.STEP.getId();
	protected final static byte lightpostbaseId = (byte) Material.DOUBLE_STEP.getId();
	protected final static byte lightpostId = (byte) Material.FENCE.getId();
	protected final static byte lightId = (byte) Material.GLOWSTONE.getId();
	protected final static byte manholeId = (byte) Material.TRAP_DOOR.getId();
	protected final static byte manpipeId = (byte) Material.OBSIDIAN.getId();
	protected final static byte ladderId = (byte) Material.LADDER.getId();

	public PlatRoadPaved(Random rand) {
		super(rand);

		// if the master key for paved roads isn't calculated then do it
		if (connectedkeyForPavedRoads == 0) {
			connectedkeyForPavedRoads = rand.nextLong();
		}

		// all paved roads are interconnected
		connectedkey = connectedkeyForPavedRoads;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateChunk(PlatMap platmap, Chunk chunk, int platX, int platZ) {
		
		// where do we start
		int base1Y = PlatMap.StreetLevel - PlatMap.FloorHeight * 3 + 1;
		int sewerY = base1Y + 1;
		int base2Y = base1Y + PlatMap.FloorHeight + 1;
		int plumbingY = base2Y + 1;
		int base3Y = PlatMap.StreetLevel - 1;

		// starting with the bottom
		generateBedrock(chunk, base1Y);
		
		// look around
		SurroundingRoads roads = new SurroundingRoads(platmap, platX, platZ);
		
		// draw the floor of the sewer
		chunk.setLayer(sewerY - 1, sewerFloorId);
		chunk.setBlocks(crossDitchEdge, Chunk.Width - crossDitchEdge, 
						sewerY - 1, sewerY, 
						crossDitchEdge, Chunk.Width - crossDitchEdge, airId);

		// draw/fill vaults and ceiling inset
		generateVault(chunk, 0, vaultWidth, 
				sewerY, 
				0, vaultWidth, true, true);
		generateVault(chunk, 0, vaultWidth, 
				sewerY, 
				Chunk.Width - vaultWidth, Chunk.Width, true, true);
		generateVault(chunk, Chunk.Width - vaultWidth, Chunk.Width, 
				sewerY, 
				0, vaultWidth, true, true);
		generateVault(chunk, Chunk.Width - vaultWidth, Chunk.Width, 
				sewerY, 
				Chunk.Width - vaultWidth, Chunk.Width, true, true);
		generateCeilingInset(chunk, vaultWidth, Chunk.Width - vaultWidth,
				sewerY,
				vaultWidth, Chunk.Width - vaultWidth, 
				!roads.toSouth(), !roads.toNorth(), !roads.toWest(), !roads.toEast());

		// now cardinal water, vaults and insets
		if (roads.toSouth()) {
			chunk.setBlocks(0, crossDitchEdge, 
							sewerY - 1, sewerY, 
							crossDitchEdge, Chunk.Width - crossDitchEdge, airId);
			generateCeilingInset(chunk, 0, vaultWidth,
					sewerY, 
					vaultWidth, Chunk.Width - vaultWidth, false, false, true, true);
			chunk.setBlock(waterOffset, sewerY - 1, crossDitchEdge, waterId);
		} else {
			generateVault(chunk, 0, vaultWidth,
					sewerY, 
					vaultWidth, Chunk.Width - vaultWidth, false, true);
		}
		if (roads.toNorth()) {
			chunk.setBlocks(Chunk.Width - crossDitchEdge, Chunk.Width, 
							sewerY - 1, sewerY, 
							crossDitchEdge, Chunk.Width - crossDitchEdge, airId);
			generateCeilingInset(chunk, Chunk.Width - vaultWidth, Chunk.Width,
					sewerY, 
					vaultWidth, Chunk.Width - vaultWidth, false, false, true, true);
			chunk.setBlock(Chunk.Width - waterOffset - 1, sewerY - 1, Chunk.Width - crossDitchEdge - 1, waterId);
		} else {
			generateVault(chunk, Chunk.Width - vaultWidth, Chunk.Width,
					sewerY, 
					vaultWidth, Chunk.Width - vaultWidth, false, true);
		}
		if (roads.toWest()) {
			chunk.setBlocks(crossDitchEdge, Chunk.Width - crossDitchEdge, 
							sewerY - 1, sewerY, 
							0, crossDitchEdge, airId);
			generateCeilingInset(chunk, vaultWidth, Chunk.Width - vaultWidth,
					sewerY,
					0, vaultWidth, true, true, false, false);
			chunk.setBlock(crossDitchEdge, sewerY - 1, waterOffset, waterId);
		} else {
			generateVault(chunk, vaultWidth, Chunk.Width - vaultWidth,
					sewerY, 
					0, vaultWidth, true, false);
		}
		if (roads.toEast()) {
			chunk.setBlocks(crossDitchEdge, Chunk.Width - crossDitchEdge, 
							sewerY - 1, sewerY, 
							Chunk.Width - crossDitchEdge, Chunk.Width, airId);
			generateCeilingInset(chunk, vaultWidth, Chunk.Width - vaultWidth,
					sewerY, 
					Chunk.Width - vaultWidth, Chunk.Width, true, true, false, false);
			chunk.setBlock(Chunk.Width - crossDitchEdge - 1, sewerY - 1, Chunk.Width - waterOffset - 1, waterId);
		} else {
			generateVault(chunk, vaultWidth, Chunk.Width - vaultWidth,
					sewerY, 
					Chunk.Width - vaultWidth, Chunk.Width, true, false);
		}
		
		// draw plumbing floor
		chunk.setLayer(base2Y, bedrockId);
		
		// draw plumbing
		for (int x = 0; x < Chunk.Width - 1; x = x + 2) {
			for (int z = 0; z < Chunk.Width - 1; z = z + 2) {
				chunk.setBlocks(x + 1, plumbingY, plumbingY + 4, z + 1, plumbingId);
				if (rand.nextInt(oddsOfPlumbingConnection) == 0)
					chunk.setBlocks(x + 1, plumbingY, plumbingY + 4, z, plumbingId);
				if (rand.nextInt(oddsOfPlumbingConnection) == 0)
					chunk.setBlocks(x, plumbingY, plumbingY + 4, z + 1, plumbingId);
				if (rand.nextInt(oddsOfPlumbingTreasure) == 0) {
					byte treasureId = (byte) pickPlumbingTreasure().getId();
					chunk.setBlocks(x, plumbingY, plumbingY + 1, z, treasureId);
					if (treasureId == waterId &&
						x >= crossDitchEdge && x < Chunk.Width - crossDitchEdge &&
						z >= crossDitchEdge && z < Chunk.Width - crossDitchEdge)
						chunk.setBlock(x, plumbingY - 1, z, airId);
				} else if (rand.nextInt(oddsOfPlumbingConnection) == 0)
					chunk.setBlocks(x, plumbingY + 2, plumbingY + 4, z, plumbingId);
			}
		}
		
		// draw plumbing ceiling
		chunk.setLayer(base3Y, bedrockId);
		
		// draw pavement
		chunk.setLayer(PlatMap.StreetLevel, pavementId);
		
		// sidewalk corners
		chunk.setBlocks(0, sidewalkWidth, sidewalkLevel, sidewalkLevel + 1, 0, sidewalkWidth, sidewalkId);
		chunk.setBlocks(0, sidewalkWidth, sidewalkLevel, sidewalkLevel + 1, Chunk.Width - sidewalkWidth, Chunk.Width, sidewalkId);
		chunk.setBlocks(Chunk.Width - sidewalkWidth, Chunk.Width, sidewalkLevel, sidewalkLevel + 1, 0, sidewalkWidth, sidewalkId);
		chunk.setBlocks(Chunk.Width - sidewalkWidth, Chunk.Width, sidewalkLevel, sidewalkLevel + 1, Chunk.Width - sidewalkWidth, Chunk.Width, sidewalkId);
		
		// sidewalk edges
		if (!roads.toSouth())
			chunk.setBlocks(0, sidewalkWidth, sidewalkLevel, sidewalkLevel + 1, sidewalkWidth, Chunk.Width - sidewalkWidth, sidewalkId);
		if (!roads.toNorth())
			chunk.setBlocks(Chunk.Width - sidewalkWidth, Chunk.Width, sidewalkLevel, sidewalkLevel + 1, sidewalkWidth, Chunk.Width - sidewalkWidth, sidewalkId);
		if (!roads.toWest())
			chunk.setBlocks(sidewalkWidth, Chunk.Width - sidewalkWidth, sidewalkLevel, sidewalkLevel + 1, 0, sidewalkWidth, sidewalkId);
		if (!roads.toEast())
			chunk.setBlocks(sidewalkWidth, Chunk.Width - sidewalkWidth, sidewalkLevel, sidewalkLevel + 1, Chunk.Width - sidewalkWidth, Chunk.Width, sidewalkId);
		
		// round things out
		if (!roads.toSouth() && roads.toNorth() && !roads.toWest() && roads.toEast())
			generateRoundedOut(chunk, sidewalkWidth, sidewalkWidth, 
					false, false);
		if (!roads.toSouth() && roads.toNorth() && roads.toWest() && !roads.toEast())
			generateRoundedOut(chunk, sidewalkWidth, Chunk.Width - sidewalkWidth - 4, 
					false, true);
		if (roads.toSouth() && !roads.toNorth() && !roads.toWest() && roads.toEast())
			generateRoundedOut(chunk, Chunk.Width - sidewalkWidth - 4, sidewalkWidth, 
					true, false);
		if (roads.toSouth() && !roads.toNorth() && roads.toWest() && !roads.toEast())
			generateRoundedOut(chunk, Chunk.Width - sidewalkWidth - 4, Chunk.Width - sidewalkWidth - 4, 
					true, true);
		
		// light posts
		generateLightPost(chunk, sidewalkWidth - 1, sidewalkWidth - 1);
		generateLightPost(chunk, Chunk.Width - sidewalkWidth, Chunk.Width - sidewalkWidth);
		
		// drill down
		if (roads.toNorth() && roads.toWest())
			generateManhole(chunk, Chunk.Width - sidewalkWidth, 
							base2Y,
							sidewalkLevel,
							Chunk.Width - sidewalkWidth - 1);
			
	}
	
	protected void generateManhole(Chunk chunk, int x, int y1, int y2, int z) {
		chunk.setBlock(x, y2, z, manholeId);
		chunk.setBlocks(x - 1, x + 2, y1 + 1, y2 - 1, z - 1, z + 2, manpipeId);
		chunk.setBlocks(x, y1, y2, z, airId);
		chunk.setBlocks(x - 1, x + vaultWidth - 3, y1 - PlatMap.FloorHeight, y1, z, z + vaultWidth - 2, airId);
	}
	
	protected void generateLightPost(Chunk chunk, int x, int z) {
		chunk.setBlock(x, sidewalkLevel, z, lightpostbaseId);
		chunk.setBlocks(x, sidewalkLevel + 1, sidewalkLevel + lightpostHeight + 1, z, lightpostId);
		chunk.setBlock(x, sidewalkLevel + lightpostHeight + 1, z, lightId);
	}
	
	protected void generateRoundedOut(Chunk chunk, int x, int z, boolean toNorth, boolean toEast) {
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
	
	protected void generateCeilingInset(Chunk chunk, int x1, int x2, int y1, int z1, int z2, 
			boolean insetS, boolean insetN, boolean insetE, boolean insetW) {
		y1 = y1 + PlatMap.FloorHeight - 1;
		int y2 = y1 + 1;
		
		if (insetS || insetN)
			for (int z = z1; z < z2; z++) {
				if (insetS) {
					chunk.setBlocks(x1, y1, y2, z, sewerWallId);
				}
				if (insetN) {
					chunk.setBlocks(x2 - 1, y1, y2, z, sewerWallId);
				}
			}
		if (insetE || insetW)
			for (int x = x1; x < x2; x++) {
				if (insetE) {
					chunk.setBlocks(x, y1, y2, z1, sewerWallId);
				}
				if (insetW) {
					chunk.setBlocks(x, y1, y2, z2 - 1, sewerWallId);
				}
			}
	}
	
	protected void generateVault(Chunk chunk, int x1, int x2, int y1, int z1, int z2, boolean doorNS, boolean doorEW) {
		int y2 = y1 + PlatMap.FloorHeight;
		
		// place the walls
		for (int x = x1; x < x2; x++) {
			chunk.setBlocks(x, y1, y2, z1, sewerWallId);
			chunk.setBlocks(x, y1, y2, z2 - 1, sewerWallId);
		}
		for (int z = z1; z < z2; z++) {
			chunk.setBlocks(x1, y1, y2, z, sewerWallId);
			chunk.setBlocks(x2 - 1, y1, y2, z, sewerWallId);
		}
		
		// fill the vault
		byte vaultId = (byte) pickVaultContent().getId();
		byte doorId = vaultId == airId ? airId : brickId;
		chunk.setBlocks(x1 + 1, x2 - 1, y1, y2 - rand.nextInt(3) - 1, z1 + 1, z2 - 1, vaultId);
		
		// place the doors, if the vault is empty then just "leave the door" open
		if (doorNS) {
			chunk.setBlocks(x1    , y1, y2 - 2, z1 + vaultDoorOffset, doorId);
			chunk.setBlocks(x2 - 1, y1, y2 - 2, z1 + vaultDoorOffset, doorId);
		}
		if (doorEW) {
			chunk.setBlocks(x1 + vaultDoorOffset, y1, y2 - 2, z1    , doorId);
			chunk.setBlocks(x1 + vaultDoorOffset, y1, y2 - 2, z2 - 1, doorId);
		}
	}
	
	protected Material pickVaultContent() {
		switch (rand.nextInt(100)) {
		
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
	
	protected Material pickPlumbingTreasure() {
		switch (rand.nextInt(20)) {
		
		// random junk
		case 0:
		case 1:
		case 2:
		case 3:
			return Material.IRON_BLOCK;
		case 4:
		case 5:
		case 6:
			return Material.GOLD_BLOCK;
		case 7:
		case 8:
			return Material.LAPIS_BLOCK;
		case 9:
			return Material.DIAMOND_BLOCK;
		
		// the rest of the time it is just flooded
		default:
			return Material.WATER;
        }
	}
}
