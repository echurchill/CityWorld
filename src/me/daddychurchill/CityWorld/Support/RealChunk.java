package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plugins.LootProvider;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import me.daddychurchill.CityWorld.Support.Direction.Facing;
import me.daddychurchill.CityWorld.Support.Direction.Stair;
import me.daddychurchill.CityWorld.Support.Direction.Torch;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;

public class RealChunk extends SupportChunk {
	private Chunk chunk;

	private boolean doPhysics;

	public RealChunk(WorldGenerator generator, Chunk aChunk) {
		super(generator);
		
		chunk = aChunk;
		chunkX = chunk.getX();
		chunkZ = chunk.getZ();
		worldX = chunkX * width;
		worldZ = chunkZ * width;
		doPhysics = false;
	}
	
	public boolean getDoPhysics() {
		return doPhysics;
	}
	
	public void setDoPhysics(boolean dophysics) {
		doPhysics = dophysics;
	}

	public Location getBlockLocation(int x, int y, int z) {
		return chunk.getBlock(x, y, z).getLocation();
	}
	
	@Override
	public byte getBlockType(int x, int y, int z) {
		return ByteChunk.getMaterialId(getBlock(x, y, z));
	}

	public Material getBlock(int x, int y, int z) {
		return chunk.getBlock(x, y, z).getType();
	}
	
	public Block getActualBlock(int x, int y, int z) {
		return chunk.getBlock(x, y, z);
	}

	@Override
	public void clearBlock(int x, int y, int z) {
		chunk.getBlock(x, y, z).setType(Material.AIR);
	}

	@Override
	public void clearBlocks(int x, int y1, int y2, int z) {
		for (int y = y1; y < y2; y++)
			setBlockType(chunk.getBlock(x, y, z), airId, doPhysics);
	}

	@Override
	public void clearBlocks(int x1, int x2, int y1, int y2, int z1, int z2) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					setBlockType(chunk.getBlock(x, y, z), airId, doPhysics);
				}
			}
		}
	}

	@Override
	public void setBlock(int x, int y, int z, byte materialId) {
		setBlockType(chunk.getBlock(x, y, z), materialId, doPhysics);
	}

	public void setBlock(int x, int y, int z, Material material) {
		setBlockType(chunk.getBlock(x, y, z), material, doPhysics);
	}

	public void setBlock(int x, int y, int z, byte type, byte data) {
		setBlockType(chunk.getBlock(x, y, z), type, data, doPhysics);
	}
	
	public void setBlock(int x, int y, int z, Material material, byte data) {
		setBlockType(chunk.getBlock(x, y, z), material, data, doPhysics);
	}

	public void setBlock(int x, int y, int z, Material material, boolean aDoPhysics) {
		setBlockType(chunk.getBlock(x, y, z), material, aDoPhysics);
	}

	public void setBlock(int x, int y, int z, byte type, byte data, boolean aDoPhysics) {
		setBlockType(chunk.getBlock(x, y, z), type, data, aDoPhysics);
	}
	
	public void setBlock(int x, int y, int z, Material material, byte data, boolean aDoPhysics) {
		setBlockType(chunk.getBlock(x, y, z), material, data, aDoPhysics);
	}

	//================ x, y1, y2, z
	public void setBlocks(int x, int y1, int y2, int z, Material material) {
		setBlocks(x, y1, y2, z, getMaterialId(material), noData, doPhysics);
	}

	public void setBlocks(int x, int y1, int y2, int z, Material material, byte data) {
		setBlocks(x, y1, y2, z, getMaterialId(material), data, doPhysics);
	}

	public void setBlocks(int x, int y1, int y2, int z, Material material, byte data, boolean aDoPhysics) {
		setBlocks(x, y1, y2, z, getMaterialId(material), data, aDoPhysics);
	}

	@Override
	public void setBlocks(int x, int y1, int y2, int z, byte type) {
		setBlocks(x, y1, y2, z, type, noData, doPhysics);
	}

	public void setBlocks(int x, int y1, int y2, int z, byte type, boolean aDoPhysics) {
		setBlocks(x, y1, y2, z, type, noData, doPhysics);
	}

	public void setBlocks(int x, int y1, int y2, int z, byte type, byte data, boolean aDoPhysics) {
		for (int y = y1; y < y2; y++)
			setBlockType(chunk.getBlock(x, y, z), type, aDoPhysics);
	}

	//================ x1, x2, y1, y2, z1, z2
	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
		setBlocks(x1, x2, y1, y2, z1, z2, getMaterialId(material), noData);
	}

	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material, byte data) {
		setBlocks(x1, x2, y1, y2, z1, z2, getMaterialId(material), data, doPhysics);
	}

	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material, byte data, boolean aDoPhysics) {
		setBlocks(x1, x2, y1, y2, z1, z2, getMaterialId(material), data, aDoPhysics);
	}

	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, byte type, byte data) {
		setBlocks(x1, x2, y1, y2, z1, z2, type, data, doPhysics);
	}

	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, byte type, byte data, boolean aDoPhysics) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					setBlockType(chunk.getBlock(x, y, z), type, data, aDoPhysics);
				}
			}
		}
	}
	
	//================ x1, x2, y, z1, z2
	public void setBlocks(int x1, int x2, int y, int z1, int z2, Material material) {
		setBlocks(x1, x2, y, z1, z2, getMaterialId(material), noData, doPhysics);
	}

	public void setBlocks(int x1, int x2, int y, int z1, int z2, Material material, byte data) {
		setBlocks(x1, x2, y, z1, z2, getMaterialId(material), data, doPhysics);
	}

	public void setBlocks(int x1, int x2, int y, int z1, int z2, Material material, boolean aDoPhysics) {
		setBlocks(x1, x2, y, z1, z2, getMaterialId(material), noData, doPhysics);
	}

	public void setBlocks(int x1, int x2, int y, int z1, int z2, Material material, byte data, boolean aDoPhysics) {
		setBlocks(x1, x2, y, z1, z2, getMaterialId(material), data, aDoPhysics);
	}

	@Override
	public void setBlocks(int x1, int x2, int y, int z1, int z2, byte typeId) {
		setBlocks(x1, x2, y, z1, z2, typeId, noData, doPhysics);
	}

	public void setBlocks(int x1, int x2, int y, int z1, int z2, byte typeId, byte data) {
		setBlocks(x1, x2, y, z1, z2, typeId, data, doPhysics);
	}
	
	public void setBlocks(int x1, int x2, int y, int z1, int z2, byte typeId, byte data, boolean aDoPhysics) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				setBlockType(chunk.getBlock(x, y, z), typeId, data, aDoPhysics);
			}
		}
	}
	
	
	public void setWalls(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
		setBlocks(x1, x2, y1, y2, z1, z1 + 1, material);
		setBlocks(x1, x2, y1, y2, z2 - 1, z2, material);
		setBlocks(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, material);
		setBlocks(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, material);
	}
	
	public void setWalls(int x1, int x2, int y1, int y2, int z1, int z2, byte type, byte data) {
		setBlocks(x1, x2, y1, y2, z1, z1 + 1, type, data);
		setBlocks(x1, x2, y1, y2, z2 - 1, z2, type, data);
		setBlocks(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, type, data);
		setBlocks(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, type, data);
	}
	
	public boolean setEmptyBlock(int x, int y, int z, Material material) {
		Block block = chunk.getBlock(x, y, z);
		if (block.isEmpty()) {
			return setBlockType(block, material, doPhysics);
		} else
			return false;
	}

	public boolean setEmptyBlock(int x, int y, int z, byte type, byte data) {
		Block block = chunk.getBlock(x, y, z);
		if (block.isEmpty()) {
			return setBlockType(block, type, data, doPhysics);
		} else
			return false;
	}
	
	public boolean setEmptyBlock(int x, int y, int z, Material material, boolean aDoPhysics) {
		Block block = chunk.getBlock(x, y, z);
		if (block.isEmpty()) {
			return setBlockType(block, material, aDoPhysics);
		} else
			return false;
	}

	public boolean setEmptyBlock(int x, int y, int z, byte type, byte data, boolean aDoPhysics) {
		Block block = chunk.getBlock(x, y, z);
		if (block.isEmpty()) {
			return setBlockType(block, type, data, aDoPhysics);
		} else
			return false;
	}
	
	public void setEmptyBlocks(int x1, int x2, int y, int z1, int z2, Material material) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				Block block = chunk.getBlock(x, y, z);
				if (block.isEmpty())
					block.setType(material);
			}
		}
	}
	
	public void setEmptyBlocks(int x1, int x2, int y, int z1, int z2, byte type, byte data, boolean aDoPhysics) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				Block block = chunk.getBlock(x, y, z);
				if (block.isEmpty())
					setBlockType(block, type, data, aDoPhysics);
			}
		}
	}
	
	public int findFirstEmpty(int x, int y, int z) {
		if (chunk.getBlock(x, y, z).isEmpty())
			return findLastEmptyBelow(x, y, z);
		else
			return findFirstEmptyAbove(x, y, z);
	}
	
	public int findFirstEmptyAbove(int x, int y, int z) {
		int y1 = y;
		while (y1 < height - 1) {
			if (chunk.getBlock(x, y1, z).isEmpty())
				return y1;
			y1++;
		}
		return height - 1;
	}
	
	public int findLastEmptyAbove(int x, int y, int z) {
		int y1 = y;
		while (y1 < height - 1 && chunk.getBlock(x, y1 + 1, z).isEmpty()) {
			y1++;
		}
		return y1;
	}
	
	public int findLastEmptyBelow(int x, int y, int z) {
		int y1 = y;
		while (y1 > 0 && chunk.getBlock(x, y1 - 1, z).isEmpty()) {
			y1--;
		}
		return y1;
	}
	
	public int setLayer(int blocky, Material material) {
		setBlocks(0, width, blocky, blocky + 1, 0, width, material);
		return blocky + 1;
	}

	public int setLayer(int blocky, int height, Material material) {
		setBlocks(0, width, blocky, blocky + height, 0, width, material);
		return blocky + height;
	}

	public int setLayer(int blocky, int height, int inset, Material material) {
		setBlocks(inset, width - inset, blocky, blocky + height, inset, width - inset, material);
		return blocky + height;
	}
	
	private void drawCircleBlocks(int cx, int cz, int x, int z, int y, Material material, byte data) {
		// Ref: Notes/BCircle.PDF
		setBlock(cx + x, y, cz + z, material, data); // point in octant 1
		setBlock(cx + z, y, cz + x, material, data); // point in octant 2
		setBlock(cx - z - 1, y, cz + x, material, data); // point in octant 3
		setBlock(cx - x - 1, y, cz + z, material, data); // point in octant 4
		setBlock(cx - x - 1, y, cz - z - 1, material, data); // point in octant 5
		setBlock(cx - z - 1, y, cz - x - 1, material, data); // point in octant 6
		setBlock(cx + z, y, cz - x - 1, material, data); // point in octant 7
		setBlock(cx + x, y, cz - z - 1, material, data); // point in octant 8
	}
	
	private void drawCircleBlocks(int cx, int cz, int x, int z, int y1, int y2, Material material, byte data) {
		for (int y = y1; y < y2; y++) {
			drawCircleBlocks(cx, cz, x, z, y, material, data);
		}
	}
	
	private void fillCircleBlocks(int cx, int cz, int x, int z, int y, Material material, byte data) {
		// Ref: Notes/BCircle.PDF
		setBlocks(cx - x - 1, cx - x, y, cz - z - 1, cz + z + 1, material, data); // point in octant 5
		setBlocks(cx - z - 1, cx - z, y, cz - x - 1, cz + x + 1, material, data); // point in octant 6
		setBlocks(cx + z, cx + z + 1, y, cz - x - 1, cz + x + 1, material, data); // point in octant 7
		setBlocks(cx + x, cx + x + 1, y, cz - z - 1, cz + z + 1, material, data); // point in octant 8
	}
	
	private void fillCircleBlocks(int cx, int cz, int x, int z, int y1, int y2, Material material, byte data) {
		for (int y = y1; y < y2; y++) {
			fillCircleBlocks(cx, cz, x, z, y, material, data);
		}
	}
	
	public void setCircle(int cx, int cz, int r, int y, Material material, byte data, boolean fill) {
		setCircle(cx, cz, r, y, y + 1, material, data, fill);
	}
	
	public void setCircle(int cx, int cz, int r, int y1, int y2, Material material, byte data, boolean fill) {
		// Ref: Notes/BCircle.PDF
		int x = r;
		int z = 0;
		int xChange = 1 - 2 * r;
		int zChange = 1;
		int rError = 0;
		
		while (x >= z) {
			if (fill)
				fillCircleBlocks(cx, cz, x, z, y1, y2, material, data);
			else
				drawCircleBlocks(cx, cz, x, z, y1, y2, material, data);
			z++;
			rError += zChange;
			zChange += 2;
			if (2 * rError + xChange > 0) {
				x--;
				rError += xChange;
				xChange += 2;
			}
		}
	}
	
	public enum Color {
		WHITE(0), ORANGE(1), MAGENTA(2), LIGHTBLUE(3), YELLOW(4), LIME(5), PINK(6), GRAY(7),
		LIGHTGRAY(8), CYAN(9), PURPLE(10), BLUE(11), BROWN(12), GREEN(13), RED(14), BLACK(15);
		
		private byte data;
		private Color(int c) {
			data = (byte) c;
		}
		
		public static Color fromByte(byte b) {
			Color result = Color.WHITE;
			result.data = b;
			return result;
		}
		
		public byte getData() {
			return data;
		}
	};
	
	public void setWool(int x, int y, int z, Color color) {
		setBlock(x, y, z, Material.WOOL, (byte)color.ordinal());
	}
	
	private void setDoor(int x, int y, int z, Material material, Direction.Door direction) {
		byte orentation = 0;
		byte hinge = 0;
		
		// orientation
		switch (direction) {
		case NORTHBYNORTHEAST:
		case NORTHBYNORTHWEST:
			orentation = 1;
			break;
		case SOUTHBYSOUTHEAST:
		case SOUTHBYSOUTHWEST:
			orentation = 3;
			break;
		case WESTBYNORTHWEST:
		case WESTBYSOUTHWEST:
			orentation = 0;
			break;
		case EASTBYNORTHEAST:
		case EASTBYSOUTHEAST:
			orentation = 2;
			break;
		}
		
		// hinge?
		switch (direction) {
		case SOUTHBYSOUTHEAST:
		case NORTHBYNORTHWEST:
		case WESTBYSOUTHWEST:
		case EASTBYNORTHEAST:
			hinge = 8 + 0;
			break;
		case NORTHBYNORTHEAST:
		case SOUTHBYSOUTHWEST:
		case WESTBYNORTHWEST:
		case EASTBYSOUTHEAST:
			hinge = 8 + 1;
			break;
		}
		
		// set the door
		setBlockType(chunk.getBlock(x, y + 1, z), material, hinge, false);
		setBlockType(chunk.getBlock(x, y    , z), material, orentation, doPhysics);
	}

	public void setWoodenDoor(int x, int y, int z, Direction.Door direction) {
		setDoor(x, y, z, Material.WOODEN_DOOR, direction);
	}

	public void setIronDoor(int x, int y, int z, Direction.Door direction) {
		setDoor(x, y, z, Material.IRON_DOOR_BLOCK, direction);
	}

	public void setTrapDoor(int x, int y, int z, Direction.TrapDoor direction) {
		setBlock(x, y, z, Material.TRAP_DOOR, direction.getData());
	}

	public void setStoneSlab(int x, int y, int z, Direction.StoneSlab direction) {
		setBlock(x, y, z, Material.STEP, direction.getData());
	}

	public void setWoodSlab(int x, int y, int z, Direction.WoodSlab direction) {
		setBlock(x, y, z, Material.WOOD_STEP, direction.getData());
	}

	public void setLadder(int x, int y1, int y2, int z, Direction.General direction) {
		byte data = direction.getData();
		for (int y = y1; y < y2; y++)
			setBlock(x, y, z, Material.LADDER, data);
	}

	public void setStair(int x, int y, int z, Material material, Direction.Stair direction) {
		setBlock(x, y, z, material, direction.getData());
	}

	public void setStair(int x, int y, int z, byte type, Direction.Stair direction) {
		setBlock(x, y, z, type, direction.getData());
	}

	public void setVine(int x, int y, int z, Direction.Vine direction) {
		setBlock(x, y, z, Material.VINE, direction.getData());
	}

	public void setTorch(int x, int y, int z, Material material, Direction.Torch direction) {
		setBlock(x, y, z, material, direction.getData(), true);
	}
	
	public void setFurnace(int x, int y, int z, Direction.General direction) {
		setBlock(x, y, z, Material.FURNACE, direction.getData());
	}

	public void setChest(int x, int y, int z, Direction.General direction, Odds odds, LootProvider lootProvider, LootLocation lootLocation) {
		Block block = chunk.getBlock(x, y, z);
		if (setBlockType(block, Material.CHEST, direction.getData(), false)) {
			if (block.getType() == Material.CHEST) {
				lootProvider.setLoot(odds, world.getName(), lootLocation, block);
			}
		}
	}

	public void setSpawner(int x, int y, int z, EntityType aType) {
		Block block = chunk.getBlock(x, y, z);
		if (setBlockType(block, Material.MOB_SPAWNER, false)) {
			if (block.getType() == Material.MOB_SPAWNER) {
				CreatureSpawner spawner = (CreatureSpawner) block.getState();
				spawner.setSpawnedType(aType);
				spawner.update(true);
			}
		}
	}
	
	public void setWallSign(int x, int y, int z, Direction.General direction, String[] text) {
		Block block = chunk.getBlock(x, y, z);
		if (setBlockType(block, Material.WALL_SIGN, direction.getData(), false)) {
			if (block.getType() == Material.WALL_SIGN) {
				Sign sign = (Sign) block.getState();
				for (int i = 0; i < text.length && i < 4; i++) 
					sign.setLine(i, text[i]);
				sign.update(true);
			}
		}
	}
	
	public void drawCrane(DataContext context, Odds odds, int x, int y, int z) {
		
		// vertical bit
		setBlocks(x, y, y + 8, z, Material.IRON_FENCE);
		setBlocks(x, y + 8, y + 10, z, Material.DOUBLE_STEP);
		setBlocks(x - 1, y + 8, y + 10, z, Material.STEP);
		setTorch(x, y + 10, z, context.torchMat, Torch.FLOOR);
		
		// horizontal bit
		setBlock(x + 1, y + 8, z, Material.GLASS);
		setBlocks(x + 2, x + 11, y + 8, y + 9, z, z + 1, Material.IRON_FENCE);
		setBlocks(x + 1, x + 10, y + 9, y + 10, z, z + 1, Material.STEP);
		setStair(x + 10, y + 9, z, Material.SMOOTH_STAIRS, Stair.WEST);
		
		// counter weight
		setBlock(x - 2, y + 9, z, Material.STEP);
		setStair(x - 3, y + 9, z, Material.SMOOTH_STAIRS, Stair.EAST);
		setBlocks(x - 3, x - 1, y + 7, y + 9, z, z + 1, Material.WOOL, odds.getRandomColor().getData());
	}

	public void setTable(int x1, int x2, int y, int z1, int z2) {
		setTable(x1, x2, y, z1, z2, Material.STONE_PLATE);
	}
	
	public void setTable(int x, int y, int z) {
		setTable(x, y, z, Material.STONE_PLATE);
	}
	
	public void setTable(int x1, int x2, int y, int z1, int z2, Material tableTop) {
		setTable(x1, x2, y, z1, z2, Material.FENCE, tableTop);
	}
	
	public void setTable(int x, int y, int z, Material tableTop) {
		setTable(x, y, z, Material.FENCE, tableTop);
	}
	
	public void setTable(int x1, int x2, int y, int z1, int z2, Material tableLeg, Material tableTop) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				setTable(x, y, z, tableLeg, tableTop);
			}
		}
	}
	
	public void setTable(int x, int y, int z, Material tableLeg, Material tableTop) {
		setBlock(x, y, z, tableLeg);
		setBlock(x, y + 1, z, tableTop);
	}

	public void setBed(int x, int y, int z, Facing direction) {
		switch (direction) {
		case EAST:
			setBlock(x, y, z, Material.BED_BLOCK, (byte)(0x1 + 0x8));
			setBlock(x + 1, y, z, Material.BED_BLOCK, (byte)(0x1));
			break;
		case SOUTH:
			setBlock(x, y, z, Material.BED_BLOCK, (byte)(0x2 + 0x8));
			setBlock(x, y, z + 1, Material.BED_BLOCK, (byte)(0x2));
			break;
		case WEST:
			setBlock(x, y, z, Material.BED_BLOCK, (byte)(0x3 + 0x8));
			setBlock(x + 1, y, z, Material.BED_BLOCK, (byte)(0x3));
			break;
		case NORTH:
			setBlock(x, y, z, Material.BED_BLOCK, (byte)(0x0 + 0x8));
			setBlock(x, y, z + 1, Material.BED_BLOCK, (byte)(0x0));
			break;
		}
	}
}
