package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plugins.LootProvider;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import me.daddychurchill.CityWorld.Support.Direction.Facing;
import me.daddychurchill.CityWorld.Support.Direction.Stair;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.EntityType;
import org.bukkit.material.Colorable;
import org.bukkit.material.Directional;
import org.bukkit.material.MaterialData;
import org.bukkit.material.TexturedMaterial;

public abstract class SupportChunk extends AbstractChunk {
	
	private boolean doPhysics;
	
	public SupportChunk(WorldGenerator generator) {
		super(generator);
		
		doPhysics = false;
	}
	
	public abstract Block getActualBlock(int x, int y, int z);

	public final boolean getDoPhysics() {
		return doPhysics;
	}
	
	public final void setDoPhysics(boolean dophysics) {
		doPhysics = dophysics;
	}

	protected final void setBlock(Block block, Material material, MaterialData data) {
		BlockState state = block.getState();
		state.setType(material);
		state.setData(data);
		state.update(false, doPhysics);
	}
	
	protected final void setBlock(int x, int y, int z, Material material, MaterialData data) {
		setBlock(getActualBlock(x, y, z), material, data);
	}
	
	public final void setBlock(int x, int y, int z, Material material) {
		getActualBlock(x, y, z).setType(material);
	}

	protected final boolean isType(Block block, Material ... types) {
		Material type = block.getType();
		for (Material test : types)
			if (type == test)
				return true;
		return false;
	}

	public final boolean isType(int x, int y, int z, Material type) {
		return getActualBlock(x, y, z).getType() == type;
	}
	
	public final boolean isOfTypes(int x, int y, int z, Material ... types) {
		return isType(getActualBlock(x, y, z), types);
	}
	
	public final boolean isEmpty(int x, int y, int z) {
		return getActualBlock(x, y, z).isEmpty();
	}
	
	public final boolean isSurroundedByEmpty(int x, int y, int z) {
		return (x > 0 && x < 15 && z > 0 && z < 15) && 
			   (getActualBlock(x - 1, y, z).isEmpty() && 
				getActualBlock(x + 1, y, z).isEmpty() &&
				getActualBlock(x, y, z - 1).isEmpty() && 
				getActualBlock(x, y, z + 1).isEmpty());
	}
	
	public final boolean isPlantable(int x, int y, int z) {
		return isType(x, y, z, Material.GRASS);
	}
	
	public final boolean isWater(int x, int y, int z) {
		return getActualBlock(x, y, z).isLiquid();
	}

	public final boolean isSurroundedByWater(int x, int y, int z) {
		return (x > 0 && x < 15 && z > 0 && z < 15) && 
			   (isWater(x - 1, y, z) && 
				isWater(x + 1, y, z) &&
				isWater(x, y, z - 1) && 
				isWater(x, y, z + 1));
	}
	
	public final Location getBlockLocation(int x, int y, int z) {
		return getActualBlock(x, y, z).getLocation();
	}
	
	@Override
	public final void clearBlock(int x, int y, int z) {
		getActualBlock(x, y, z).setType(Material.AIR);
	}

	@Override
	public final void clearBlocks(int x, int y1, int y2, int z) {
		for (int y = y1; y < y2; y++)
			getActualBlock(x, y, z).setType(Material.AIR);
	}

	@Override
	public final void clearBlocks(int x1, int x2, int y1, int y2, int z1, int z2) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					getActualBlock(x, y, z).setType(Material.AIR);
				}
			}
		}
	}

	//================ x, y1, y2, z
	@Override
	public final void setBlocks(int x, int y1, int y2, int z, Material material) {
		for (int y = y1; y < y2; y++)
			setBlock(x, y, z, material);
	}

	//================ x1, x2, y1, y2, z1, z2
	@Override
	public final void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					setBlock(x, y, z, material);
				}
			}
		}
	}

	//================ x1, x2, y, z1, z2
	@Override
	public final void setBlocks(int x1, int x2, int y, int z1, int z2, Material material) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				setBlock(x, y, z, material);
			}
		}
	}

	@Override
	public final void setWalls(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
		setBlocks(x1, x2, y1, y2, z1, z1 + 1, material);
		setBlocks(x1, x2, y1, y2, z2 - 1, z2, material);
		setBlocks(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, material);
		setBlocks(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, material);
	}
	
	@Override
	public final int setLayer(int blocky, Material material) {
		setBlocks(0, width, blocky, blocky + 1, 0, width, material);
		return blocky + 1;
	}

	@Override
	public final int setLayer(int blocky, int height, Material material) {
		setBlocks(0, width, blocky, blocky + height, 0, width, material);
		return blocky + height;
	}

	@Override
	public final int setLayer(int blocky, int height, int inset, Material material) {
		setBlocks(inset, width - inset, blocky, blocky + height, inset, width - inset, material);
		return blocky + height;
	}
	
	@Override
	public final boolean setEmptyBlock(int x, int y, int z, Material material) {
		Block block = getActualBlock(x, y, z);
		if (block.isEmpty()) {
			block.setType(material);
			return true;
		} else
			return false;
	}

	@Override
	public final void setEmptyBlocks(int x1, int x2, int y, int z1, int z2, Material material) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				Block block = getActualBlock(x, y, z);
				if (block.isEmpty())
					block.setType(material);
			}
		}
	}
	
	@Override
	public final int findFirstEmpty(int x, int y, int z) {
		if (getActualBlock(x, y, z).isEmpty())
			return findLastEmptyBelow(x, y, z);
		else
			return findFirstEmptyAbove(x, y, z);
	}
	
	@Override
	public final int findFirstEmptyAbove(int x, int y, int z) {
		int y1 = y;
		while (y1 < height - 1) {
			if (getActualBlock(x, y1, z).isEmpty())
				return y1;
			y1++;
		}
		return height - 1;
	}
	
	@Override
	public final int findLastEmptyAbove(int x, int y, int z) {
		int y1 = y;
		while (y1 < height - 1 && getActualBlock(x, y1 + 1, z).isEmpty()) {
			y1++;
		}
		return y1;
	}
	
	@Override
	public final int findLastEmptyBelow(int x, int y, int z) {
		int y1 = y;
		while (y1 > 0 && getActualBlock(x, y1 - 1, z).isEmpty()) {
			y1--;
		}
		return y1;
	}
	
	private void drawCircleBlocks(int cx, int cz, int x, int z, int y, Material material) {
		// Ref: Notes/BCircle.PDF
		setBlock(cx + x, y, cz + z, material); // point in octant 1
		setBlock(cx + z, y, cz + x, material); // point in octant 2
		setBlock(cx - z - 1, y, cz + x, material); // point in octant 3
		setBlock(cx - x - 1, y, cz + z, material); // point in octant 4
		setBlock(cx - x - 1, y, cz - z - 1, material); // point in octant 5
		setBlock(cx - z - 1, y, cz - x - 1, material); // point in octant 6
		setBlock(cx + z, y, cz - x - 1, material); // point in octant 7
		setBlock(cx + x, y, cz - z - 1, material); // point in octant 8
	}
	
	private void drawCircleBlocks(int cx, int cz, int x, int z, int y1, int y2, Material material) {
		for (int y = y1; y < y2; y++) {
			drawCircleBlocks(cx, cz, x, z, y, material);
		}
	}
	
	private void fillCircleBlocks(int cx, int cz, int x, int z, int y, Material material) {
		// Ref: Notes/BCircle.PDF
		setBlocks(cx - x - 1, cx - x, y, cz - z - 1, cz + z + 1, material); // point in octant 5
		setBlocks(cx - z - 1, cx - z, y, cz - x - 1, cz + x + 1, material); // point in octant 6
		setBlocks(cx + z, cx + z + 1, y, cz - x - 1, cz + x + 1, material); // point in octant 7
		setBlocks(cx + x, cx + x + 1, y, cz - z - 1, cz + z + 1, material); // point in octant 8
	}
	
	private void fillCircleBlocks(int cx, int cz, int x, int z, int y1, int y2, Material material) {
		for (int y = y1; y < y2; y++) {
			fillCircleBlocks(cx, cz, x, z, y, material);
		}
	}
	
	public final void setCircle(int cx, int cz, int r, int y, Material material, boolean fill) {
		setCircle(cx, cz, r, y, y + 1, material, fill);
	}
	
	public final void setCircle(int cx, int cz, int r, int y1, int y2, Material material, boolean fill) {
		// Ref: Notes/BCircle.PDF
		int x = r;
		int z = 0;
		int xChange = 1 - 2 * r;
		int zChange = 1;
		int rError = 0;
		
		while (x >= z) {
			if (fill)
				fillCircleBlocks(cx, cz, x, z, y1, y2, material);
			else
				drawCircleBlocks(cx, cz, x, z, y1, y2, material);
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
	
	protected final boolean isPartialHeight(Block block) {
		return isType(block, Material.STEP, Material.WOOD_STEP, 
							 Material.STONE_PLATE, Material.WOOD_PLATE);
	}
	
	public final boolean isPartialHeight(int x, int y, int z) {
		return isPartialHeight(getActualBlock(x, y, z));
	}

	private void setBlockTypeAndColor(int x, int y, int z, Material material, DyeColor color) {
		BlockState state = getActualBlock(x, y, z).getState();
		state.setType(material);
		MaterialData data = state.getData();
		if (data instanceof Colorable)
			((Colorable)state.getData()).setColor(color);
		state.update(false, doPhysics);
	}
	
	private void setBlocksTypeAndColor(int x1, int x2, int y1, int y2, int z1, int z2, Material material, DyeColor color) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					setBlockTypeAndColor(x, y, z, material, color);
				}
			}
		}
	}
	
	public void setBlockTypeAndDirection(int x, int y, int z, Material material, BlockFace facing) {
		BlockState state = getActualBlock(x, y, z).getState();
		state.setType(material);
		MaterialData data = state.getData();
		if (data instanceof Directional)
			((Directional)state.getData()).setFacingDirection(facing);
		state.update(false, doPhysics);
	}
	
	public void setBlocksTypeAndDirection(int x1, int x2, int y1, int y2, int z1, int z2, Material material, BlockFace facing) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					setBlockTypeAndDirection(x, y, z, material, facing);
				}
			}
		}
	}
	
	public void setBlockTypeAndTexture(int x, int y, int z, Material material, Material texture) {
		BlockState state = getActualBlock(x, y, z).getState();
		state.setType(material);
		MaterialData data = state.getData();
		if (data instanceof TexturedMaterial)
			((TexturedMaterial)state.getData()).setMaterial(texture);
		state.update(false, doPhysics);
	}
	
	private int clamp(int value, int max) {
		return Math.max(Math.min(value, max), 0);
	}
	
	public final void setSnowCover(int x, int y, int z, int level) {
		Block block = getActualBlock(x, y, z);
		if (block.isEmpty())
			BlackMagic.setBlockType(block, Material.SNOW, clamp(level, BlackMagic.maxSnowLevel));
	}
	
	public final void setCauldron(int x, int y, int z, int level) {
		Block block = getActualBlock(x, y, z);
		BlackMagic.setBlockType(block, Material.CAULDRON, clamp(level, BlackMagic.maxCauldronLevel));
	}

	public final void setCauldron(int x, int y, int z, Odds odds) {
		setCauldron(x, y, z, odds.getCauldronLevel());
	}

	public final void setWool(int x, int y, int z, DyeColor color) {
		setBlockTypeAndColor(x, y, z, Material.WOOL, color);
	}
	
	public final void setWool(int x1, int x2, int y1, int y2, int z1, int z2, DyeColor color) {
		setBlocksTypeAndColor(x1, x2, y1, y2, z1, z2, Material.WOOL, color);
	}
	
	public final void setClay(int x, int y, int z, DyeColor color) {
		setBlockTypeAndColor(x, y, z, Material.HARD_CLAY, color);
	}
	
	public final void setClay(int x1, int x2, int y1, int y2, int z1, int z2, DyeColor color) {
		setBlocksTypeAndColor(x1, x2, y1, y2, z1, z2, Material.HARD_CLAY, color);
	}
	
	public final void drawCrane(DataContext context, Odds odds, int x, int y, int z) {
		
		// vertical bit
		setBlocks(x, y, y + 8, z, Material.IRON_FENCE);
		setBlocks(x, y + 8, y + 10, z, Material.DOUBLE_STEP);
		setBlocks(x - 1, y + 8, y + 10, z, Material.STEP);
		setBlockTypeAndDirection(x, y + 10, z, context.torchMat, BlockFace.UP);
		
		// horizontal bit
		setBlock(x + 1, y + 8, z, Material.GLASS);
		setBlocks(x + 2, x + 11, y + 8, y + 9, z, z + 1, Material.IRON_FENCE);
		setBlocks(x + 1, x + 10, y + 9, y + 10, z, z + 1, Material.STEP);
		setStair(x + 10, y + 9, z, Material.SMOOTH_STAIRS, Stair.WEST);
		
		// counter weight
		setBlock(x - 2, y + 9, z, Material.STEP);
		setStair(x - 3, y + 9, z, Material.SMOOTH_STAIRS, Stair.EAST);
		setWool(x - 3, x - 1, y + 7, y + 9, z, z + 1, odds.getRandomColor());
	}
	
	public final void setTable(int x1, int x2, int y, int z1, int z2) {
		setTable(x1, x2, y, z1, z2, Material.STONE_PLATE);
	}
	
	public final void setTable(int x, int y, int z) {
		setTable(x, y, z, Material.STONE_PLATE);
	}
	
	public final void setTable(int x1, int x2, int y, int z1, int z2, Material tableTop) {
		setTable(x1, x2, y, z1, z2, Material.FENCE, tableTop);
	}
	
	public final void setTable(int x, int y, int z, Material tableTop) {
		setTable(x, y, z, Material.FENCE, tableTop);
	}
	
	public final void setTable(int x1, int x2, int y, int z1, int z2, Material tableLeg, Material tableTop) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				setTable(x, y, z, tableLeg, tableTop);
			}
		}
	}
	
	public final void setTable(int x, int y, int z, Material tableLeg, Material tableTop) {
		setBlock(x, y, z, tableLeg);
		setBlock(x, y + 1, z, tableTop);
	}
	
	private void setDoor(int x, int y, int z, Material material, Direction.Door direction) {
//		byte orentation = 0;
//		byte hinge = 0;
//		
//		// orientation
//		switch (direction) {
//		case NORTHBYNORTHEAST:
//		case NORTHBYNORTHWEST:
//			orentation = 1;
//			break;
//		case SOUTHBYSOUTHEAST:
//		case SOUTHBYSOUTHWEST:
//			orentation = 3;
//			break;
//		case WESTBYNORTHWEST:
//		case WESTBYSOUTHWEST:
//			orentation = 0;
//			break;
//		case EASTBYNORTHEAST:
//		case EASTBYSOUTHEAST:
//			orentation = 2;
//			break;
//		}
//		
//		// hinge?
//		switch (direction) {
//		case SOUTHBYSOUTHEAST:
//		case NORTHBYNORTHWEST:
//		case WESTBYSOUTHWEST:
//		case EASTBYNORTHEAST:
//			hinge = 8 + 0;
//			break;
//		case NORTHBYNORTHEAST:
//		case SOUTHBYSOUTHWEST:
//		case WESTBYNORTHWEST:
//		case EASTBYSOUTHEAST:
//			hinge = 8 + 1;
//			break;
//		}
//		
//		// set the door
//		BlackMagic.setBlockType(chunk.getBlock(x, y + 1, z), material, hinge, false);
//		BlackMagic.setBlockType(chunk.getBlock(x, y    , z), material, orentation, doPhysics);
	}

	public final void setWoodenDoor(int x, int y, int z, Direction.Door direction) {
		setDoor(x, y, z, Material.WOODEN_DOOR, direction);
	}

	public final void setIronDoor(int x, int y, int z, Direction.Door direction) {
		setDoor(x, y, z, Material.IRON_DOOR_BLOCK, direction);
	}

	public final void setTrapDoor(int x, int y, int z, BlockFace facing) {
//		setBlock(x, y, z, Material.TRAP_DOOR, direction.getData());
	}

	public final void setStoneSlab(int x, int y, int z, Direction.StoneSlab direction) {
//		setBlock(x, y, z, Material.STEP, direction.getData());
	}

	public final void setWoodSlab(int x, int y, int z, Direction.WoodSlab direction) {
//		setBlock(x, y, z, Material.WOOD_STEP, direction.getData());
	}

	public final void setLadder(int x, int y1, int y2, int z, Direction.General direction) {
//		byte data = direction.getData();
//		for (int y = y1; y < y2; y++)
//			setBlock(x, y, z, Material.LADDER, data);
	}

	public final void setStair(int x, int y, int z, Material material, Direction.Stair direction) {
//		setBlock(x, y, z, material, direction.getData());
	}

	public final void setVine(int x, int y, int z, Direction.Vine direction) {
//		setBlock(x, y, z, Material.VINE, direction.getData());
	}

	public final void setTorch(int x, int y, int z, Material material, Direction.Torch direction) {
//		setBlock(x, y, z, material, direction.getData(), true);
	}
	
	public final void setFurnace(int x, int y, int z, Direction.General direction) {
//		setBlock(x, y, z, Material.FURNACE, direction.getData());
	}

	public final void setChest(int x, int y, int z, Direction.General direction, Odds odds, LootProvider lootProvider, LootLocation lootLocation) {
//		Block block = chunk.getBlock(x, y, z);
//		if (BlackMagic.setBlockType(block, Material.CHEST, direction.getData(), false)) {
//			if (block.getType() == Material.CHEST) {
//				lootProvider.setLoot(odds, world.getName(), lootLocation, block);
//			}
//		}
	}

	public final void setSpawner(int x, int y, int z, EntityType aType) {
//		Block block = chunk.getBlock(x, y, z);
//		if (BlackMagic.setBlockType(block, Material.MOB_SPAWNER, false)) {
//			if (block.getType() == Material.MOB_SPAWNER) {
//				CreatureSpawner spawner = (CreatureSpawner) block.getState();
//				spawner.setSpawnedType(aType);
//				spawner.update(true);
//			}
//		}
	}
	
	public final void setWallSign(int x, int y, int z, Direction.General direction, String[] text) {
//		Block block = chunk.getBlock(x, y, z);
//		if (BlackMagic.setBlockType(block, Material.WALL_SIGN, direction.getData(), false)) {
//			if (block.getType() == Material.WALL_SIGN) {
//				Sign sign = (Sign) block.getState();
//				for (int i = 0; i < text.length && i < 4; i++) 
//					sign.setLine(i, text[i]);
//				sign.update(true);
//			}
//		}
	}
	

	public final void setBed(int x, int y, int z, Facing direction) {
//		switch (direction) {
//		case EAST:
//			setBlock(x, y, z, Material.BED_BLOCK, (byte)(0x1 + 0x8));
//			setBlock(x + 1, y, z, Material.BED_BLOCK, (byte)(0x1));
//			break;
//		case SOUTH:
//			setBlock(x, y, z, Material.BED_BLOCK, (byte)(0x2 + 0x8));
//			setBlock(x, y, z + 1, Material.BED_BLOCK, (byte)(0x2));
//			break;
//		case WEST:
//			setBlock(x, y, z, Material.BED_BLOCK, (byte)(0x3 + 0x8));
//			setBlock(x + 1, y, z, Material.BED_BLOCK, (byte)(0x3));
//			break;
//		case NORTH:
//			setBlock(x, y, z, Material.BED_BLOCK, (byte)(0x0 + 0x8));
//			setBlock(x, y, z + 1, Material.BED_BLOCK, (byte)(0x0));
//			break;
//		}
	}
}
