package me.daddychurchill.CityWorld.Support;

import org.bukkit.Axis;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.data.*;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.Rail.Shape;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.Bed.Part;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Door.Hinge;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Slab.Type;
import org.bukkit.block.data.type.Snow;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.util.noise.NoiseGenerator;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plugins.LootProvider;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;

public abstract class SupportBlocks extends AbstractBlocks {

	private boolean doPhysics;
	private boolean doClearData;

	SupportBlocks(CityWorldGenerator generator) {
		super(generator);

		doPhysics = false;
		doClearData = false;
	}

	public abstract Block getActualBlock(int x, int y, int z);

	public final boolean getDoPhysics() {
		return doPhysics;
	}

	public final boolean setDoPhysics(boolean dophysics) {
		boolean was = doPhysics;
		doPhysics = dophysics;
		return was;
	}

	public final boolean getDoClearData() {
		return doClearData;
	}

	public final void setDoClearData(boolean docleardata) {
		boolean was = doClearData;
		doClearData = docleardata;
	}

	@Override
	public final void setBlockIfEmpty(int x, int y, int z, Material material) {
		Block block = getActualBlock(x, y, z);
		if (isEmpty(block) && !isEmpty(x, y - 1, z))
			setActualBlock(block, material, getDoPhysics(x, z));
	}

	private boolean getDoPhysics(int x, int z) {
		boolean thisDoPhysics = doPhysics;
		if (thisDoPhysics)
			thisDoPhysics = !onEdgeXZ(x, z);
		return thisDoPhysics;
	}

	private void setActualBlock(Block block, Material material, boolean thisDoPhysics) {
		block.setType(material, thisDoPhysics);
	}

	@Override
	public final void setBlock(int x, int y, int z, Material material) {
		setActualBlock(getActualBlock(x, y, z), material, getDoPhysics(x, z));
	}

	private boolean isType(Block block, Material... types) {
		Material type = block.getType();
		for (Material test : types)
			if (type == test)
				return true;
		return false;
	}

	public final boolean isType(int x, int y, int z, Material type) {
		return getActualBlock(x, y, z).getType() == type;
	}

	public final boolean isOfTypes(int x, int y, int z, Material... types) {
		return isType(getActualBlock(x, y, z), types);
	}

	public final void setBlockIfNot(int x, int y, int z, Material... types) {
		if (!isOfTypes(x, y, z, types))
			setBlock(x, y, z, types[0]);
	}

	private boolean isEmpty(Block block) {
		if (onEdgeXZ(block.getX(), block.getZ()))
			return block.getType() == Material.AIR;
		else
			return block.isEmpty();
	}

	//NOTE when used as the default world generator (via bukkit.yml), testing via .isEmpty() on or near the edge (0, 1, 14, 15) causes exceptions
	@Override
	public final boolean isEmpty(int x, int y, int z) {
		if (onEdgeXZ(x, z))
			return isType(x, y, z, Material.AIR);
		else
			return getActualBlock(x, y, z).isEmpty();
	}

	public final boolean isPartiallyEmpty(int x, int y1, int y2, int z) {
		for (int y = y1; y < y2; y++) {
			if (isEmpty(x, y, z))
				return true;
		}
		return false;
	}

	public final boolean isPartiallyEmpty(int x1, int x2, int y1, int y2, int z1, int z2) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					if (isEmpty(x, y, z))
						return true;
				}
			}
		}
		return false;
	}

	public abstract boolean isSurroundedByEmpty(int x, int y, int z);

	public final boolean isWater(int x, int y, int z) {
		return isOfTypes(x, y, z, Material.WATER);
//		return getActualBlock(x, y, z).isLiquid();
	}

	public abstract boolean isByWater(int x, int y, int z);

	public final Location getBlockLocation(int x, int y, int z) {
		return getActualBlock(x, y, z).getLocation();
	}

	@Override
	public final void setAtmosphereBlock(int x, int y, int z, Material material) {
		setBlock(x, y, z, material);
		BlockData blockData;
		// West
		if (x > 0) {
			try {
				blockData = getActualBlock(x - 1, y, z).getBlockData();
				if (blockData instanceof MultipleFacing) {
					((MultipleFacing) blockData).setFace(BlockFace.EAST, false);
					getActualBlock(x - 1, y, z).setBlockData(blockData, false);
				}
			} catch (Exception ignored) {

			}
		}
		// East
		if (x < 15) {
			try {
				blockData = getActualBlock(x + 1, y, z).getBlockData();
				if (blockData instanceof MultipleFacing) {
					((MultipleFacing) blockData).setFace(BlockFace.WEST, false);
					getActualBlock(x + 1, y, z).setBlockData(blockData, false);
				}
			} catch (Exception ignored) {

			}
		}
		// North
		if (z > 0) {
			try {
				blockData = getActualBlock(x, y, z - 1).getBlockData();
				if (blockData instanceof MultipleFacing) {
					((MultipleFacing) blockData).setFace(BlockFace.SOUTH, false);
					getActualBlock(x, y, z - 1).setBlockData(blockData, false);
				}
			} catch (Exception ignored) {

			}
		}
		// South
		if (z < 15) {
			try {
				blockData = getActualBlock(x, y, z + 1).getBlockData();
				if (blockData instanceof MultipleFacing) {
					((MultipleFacing) blockData).setFace(BlockFace.NORTH, false);
					getActualBlock(x, y, z + 1).setBlockData(blockData, false);
				}
			} catch (Exception ignored) {

			}
		}
	}

	@Override
	public final void clearBlock(int x, int y, int z) {
		getActualBlock(x, y, z).setType(Material.AIR, getDoPhysics(x, z));
	}

	@Override
	public final void setWalls(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
		if (material.createBlockData() instanceof MultipleFacing) {
			setBlocks(x1 + 1, x2 - 1, y1, y2, z1, z1 + 1, material, BlockFace.EAST, BlockFace.WEST); // N
			setBlocks(x1 + 1, x2 - 1, y1, y2, z2 - 1, z2, material, BlockFace.EAST, BlockFace.WEST); // S
			setBlocks(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, material, BlockFace.SOUTH, BlockFace.NORTH); // W
			setBlocks(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, material, BlockFace.SOUTH, BlockFace.NORTH); // E
			setBlocks(x1, y1, y2, z1, material, BlockFace.SOUTH, BlockFace.EAST); // NW
			setBlocks(x1, y1, y2, z2 - 1, material, BlockFace.NORTH, BlockFace.EAST); // SW
			setBlocks(x2 - 1, y1, y2, z1, material, BlockFace.SOUTH, BlockFace.WEST); // NE
			setBlocks(x2 - 1, y1, y2, z2 - 1, material, BlockFace.NORTH, BlockFace.WEST); // SE
		} else {
			setBlocks(x1, x2, y1, y2, z1, z1 + 1, material); // N
			setBlocks(x1, x2, y1, y2, z2 - 1, z2, material); // S
			setBlocks(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, material); // W
			setBlocks(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, material); // E
		}
	}

	public final void fillBlocks(int x1, int x2, int y, int z1, int z2, Material material) {
		fillBlocks(x1, x2, y, y + 1, z1, z2, material);
	}

	private void fillBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
		if (!(material.createBlockData() instanceof MultipleFacing)) {
			setBlocks(x1, x2, y1, y2, z1, z2, material);
		}
		setBlocks(x1, x2, y1, y2, z1, z2, material, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST);
		setBlocks(x1 + 1, x2 - 1, y1, y2, z1, z1 + 1, material, BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH); // N
		setBlocks(x1 + 1, x2 - 1, y1, y2, z2 - 1, z2, material, BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH); // S
		setBlocks(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, material, BlockFace.SOUTH, BlockFace.NORTH, BlockFace.EAST); // W
		setBlocks(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, material, BlockFace.SOUTH, BlockFace.NORTH, BlockFace.WEST); // E
		setBlocks(x1, y1, y2, z1, material, BlockFace.SOUTH, BlockFace.EAST); // NW
		setBlocks(x1, y1, y2, z2 - 1, material, BlockFace.NORTH, BlockFace.EAST); // SW
		setBlocks(x2 - 1, y1, y2, z1, material, BlockFace.SOUTH, BlockFace.WEST); // NE
		setBlocks(x2 - 1, y1, y2, z2 - 1, material, BlockFace.NORTH, BlockFace.WEST); // SE
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

	// @@ I REALLY NEED TO FIGURE A DIFFERENT WAY TO DO THIS
	final boolean isNonstackableBlock(Block block) { // either because it really isn't or it just doesn't look
		// good
		return !block.getType().isOccluding();
//		switch (block.getType()) {
//		default: 
//			return true;
//		case STONE:
//		case GRASS:
//		case DIRT:
//		case COBBLESTONE:
////		case WOOD:
//		case SAND:
//		case GRAVEL:
//		case COAL_ORE:
//		case DIAMOND_ORE:
//		case EMERALD_ORE:
//		case GOLD_ORE:
//		case IRON_ORE:
//		case LAPIS_ORE:
////		case QUARTZ_ORE:
//		case REDSTONE_ORE:
//		case COAL_BLOCK:
//		case DIAMOND_BLOCK:
//		case EMERALD_BLOCK:
//		case GOLD_BLOCK:
//		case HAY_BLOCK:
//		case IRON_BLOCK:
//		case LAPIS_BLOCK:
//		case PURPUR_BLOCK:
//		case QUARTZ_BLOCK:
//		case SLIME_BLOCK:
//		case SNOW_BLOCK:
////		case LOG:
////		case LOG_2:
//		case SPONGE:
//		case SANDSTONE:
//		case RED_SANDSTONE:
//		case SOUL_SAND:
////		case STAINED_CLAY:
////		case HARD_CLAY:
//		case CLAY:
////		case WOOL:
////		case DOUBLE_STEP:
////		case WOOD_DOUBLE_STEP:
////		case DOUBLE_STONE_SLAB2:
////		case PURPUR_DOUBLE_SLAB:
//		case BRICK:
////		case END_BRICKS:
//		case NETHER_BRICK:
////		case SMOOTH_BRICK:
//		case BOOKSHELF:
//		case MOSSY_COBBLESTONE:
//		case OBSIDIAN:
////		case SOIL:
//		case ICE:
//		case PACKED_ICE:
//		case FROSTED_ICE:
//		case NETHERRACK:
////		case ENDER_STONE:
////		case MYCEL:
//		case PRISMARINE:
//		case GRASS_PATH:
//		case BEDROCK:
//			return false;
//		}
//		return isType(block, Material.STONE_SLAB, Material.WOOD_STEP, 
//				 			 Material.GLASS, Material.GLASS_PANE,
//							 Material.SNOW, Material.CARPET, Material.SIGN, 
//							 Material.WOOD_DOOR, Material.TRAP_DOOR, 
//							 Material.WHITE_STAINED_GLASS, Material.WHITE_STAINED_GLASS_PANE,
//							 Material.SPRUCE_FENCE, Material.SPRUCE_FENCE_GATE,
//							 Material.STONE_PRESSURE_PLATE, Material.BIRCH_PRESSURE_PLATE,
//							 Material.TRIPWIRE, Material.TRIPWIRE_HOOK,
//							 Material.IRON_DOOR_BLOCK, Material.IRON_BARS);
	}

	public final boolean isNonstackableBlock(int x, int y, int z) {
		return isNonstackableBlock(getActualBlock(x, y, z));
	}

	private int clamp(double value, int max) {
		return clamp(value, 0, max);
	}

	private int clamp(double value, int min, int max) {
		return NoiseGenerator.floor((value - NoiseGenerator.floor(value)) * (max - min)) + min;
	}

	public final void setBlock(int x, int y, int z, Material material, boolean light) {
		Block block = getActualBlock(x, y, z);
		block.setType(material, false);
		BlockData data = block.getBlockData();
		try {
			if (data instanceof Lightable) {
				Lightable lightable = (Lightable) data;
				lightable.setLit(light);
			}
		} finally {
			block.setBlockData(data, getDoPhysics(x, z));
		}
	}

	public final void setBlock(int x, int y, int z, Material material, double level) {
		Block block = getActualBlock(x, y, z);
		block.setType(material, false);
		BlockData data = block.getBlockData();
		try {
			if (data instanceof Ageable) {
				Ageable ageable = (Ageable) data;
				ageable.setAge(clamp(level, ageable.getMaximumAge()));
			} else if (data instanceof Levelled) {
				Levelled levelled = (Levelled) data;
				levelled.setLevel(clamp(level, levelled.getMaximumLevel()));
			} else if (data instanceof Snow) {
				Snow snow = (Snow) data;
				snow.setLayers(clamp(level, snow.getMinimumLayers(), snow.getMaximumLayers()));
			}
		} finally {
			block.setBlockData(data, getDoPhysics(x, z));
		}
	}

	public final void setCauldron(int x, int y, int z, Odds odds) {
		setBlock(x, y, z, Material.CAULDRON, odds.getRandomDouble());
	}

	public final void colorizeBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material find, Colors colors) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					if (isType(x, y, z, find))
						setBlock(x, y, z, colors.getTerracotta());
				}
			}
		}
	}

	public final void setBlockRandomly(int x, int y, int z, Odds odds, Material... materials) {
		setBlock(x, y, z, odds.getRandomMaterial(materials));
	}

	public final void setVine(int x, int y, int z, BlockFace... faces) {
		Block block = getActualBlock(x, y, z);
		block.setType(Material.VINE, false);
		BlockData data = block.getBlockData();
		try {
			if (data instanceof MultipleFacing) {
				MultipleFacing vines = (MultipleFacing) data;
				for (BlockFace face : faces)
					vines.setFace(face, true);
			}
		} finally {
			block.setBlockData(data, getDoPhysics(x, z));
		}
	}

	public final void setVines(int x, int y1, int y2, int z, BlockFace... faces) {
		for (int y = y1; y < y2; y++)
			setVine(x, y, z, faces);
	}

	public final void setBlock(int x, int y, int z, Material material, Shape shape, boolean powered) {
		Block block = getActualBlock(x, y, z);
		block.setType(material, false);
		BlockData data = block.getBlockData();
		try {
			if (data instanceof Rail)
				((Rail) data).setShape(shape);
			if (data instanceof Powerable)
				((Powerable) data).setPowered(powered);
		} finally {
			block.setBlockData(data, getDoPhysics(x, z));
		}
	}

	@Override
	public final void setBlock(int x, int y, int z, Material material, Type type) {
		Block block = getActualBlock(x, y, z);
		block.setType(material, false);
		BlockData data = block.getBlockData();
		try {
			if (data instanceof Slab)
				((Slab) data).setType(type);
		} finally {
			block.setBlockData(data, getDoPhysics(x, z));
		}
	}

	@Override
	public final void setBlock(int x, int y, int z, Material material, BlockFace facing) {
		Block block = getActualBlock(x, y, z);
		block.setType(material, false);
		BlockData data = block.getBlockData();
		try {
			if (data instanceof Directional) {
				((Directional) data).setFacing(facing);
			} else if (data instanceof MultipleFacing) {
				((MultipleFacing) data).setFace(facing, true);
			} else if (data instanceof Orientable) {
				switch (facing) {
				case NORTH:
				case SOUTH:
					((Orientable) data).setAxis(Axis.Z);
					break;
				case EAST:
				case WEST:
					((Orientable) data).setAxis(Axis.X);
					break;
				default:
					((Orientable) data).setAxis(Axis.Y);
				}
			}
		} finally {
			block.setBlockData(data, getDoPhysics(x, z));
		}
	}

	@Override
	public final void setBlock(int x, int y, int z, Material material, BlockFace... facing) {
		Block block = getActualBlock(x, y, z);
		block.setType(material, false);
		BlockData data = block.getBlockData();
		try {
			if (data instanceof MultipleFacing) {
				for (BlockFace face : facing) {
					((MultipleFacing) data).setFace(face, true);
				}
			}
		} finally {
			block.setBlockData(data, getDoPhysics(x, z));
		}
	}

	public final void setStair(int x, int y, int z, Material material, BlockFace facing) {
		setStair(x, y, z, material, facing, Stairs.Shape.STRAIGHT);
	}

	public final void setStair(int x, int y, int z, Material material, BlockFace facing, Stairs.Shape shape) {
		Block block = getActualBlock(x, y, z);
		block.setType(material, false);
		BlockData data = block.getBlockData();
		try {
			if (data instanceof Directional) {
				((Directional) data).setFacing(facing);
			}
			if (data instanceof Stairs) {
				((Stairs) data).setShape(shape);
			}
		} finally {
			block.setBlockData(data, getDoPhysics(x, z));
		}
	}

	public final void drawCrane(DataContext context, Odds odds, int x, int y, int z) {
		Colors colors = new Colors(odds);

		// vertical bit
		setBlocks(x, y, y + 8, z, Material.IRON_BARS, BlockFace.WEST);
		setBlocks(x - 1, y, y + 8, z, Material.IRON_BARS, BlockFace.EAST); // 1.9 shows iron fences very thin now
		setBlocks(x, y + 8, y + 10, z, Material.STONE);
		setBlocks(x - 1, y + 8, y + 10, z, Material.STONE_SLAB);
		setBlock(x, y + 10, z, context.torchMat, BlockFace.UP);

		// horizontal bit
		setBlock(x + 1, y + 8, z, Material.GLASS);
		setBlocks(x + 2, x + 10, y + 8, y + 9, z, z + 1, Material.IRON_BARS, BlockFace.EAST, BlockFace.WEST);
		setBlock(x + 10, y + 8, z, Material.IRON_BARS, BlockFace.WEST);
		setBlocks(x + 1, x + 10, y + 9, y + 10, z, z + 1, Material.STONE_SLAB);
		setBlock(x + 10, y + 9, z, Material.STONE_BRICK_STAIRS, BlockFace.WEST);

		// counter weight
		setBlock(x - 2, y + 9, z, Material.STONE_SLAB);
		setBlock(x - 3, y + 9, z, Material.STONE_BRICK_STAIRS, BlockFace.EAST);
		setBlocks(x - 3, x - 1, y + 7, y + 9, z, z + 1, colors.getConcrete());
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

	private void setDoorBlock(int x, int y, int z, Material material, BlockFace facing, Half half, Hinge hinge,
			boolean doPhysics) {
		Block block = getActualBlock(x, y, z);
		block.setType(material, false);
		BlockData data = block.getBlockData();
		try {
			if (data instanceof Door) {
				((Door) data).setHalf(half);
				((Door) data).setFacing(facing);
				((Door) data).setHinge(hinge);
			}
		} finally {
			block.setBlockData(data, getDoPhysics(x, z));
		}
	}

	//@@	public void setDoor(int x, int y, int z, Material material, BadMagic.Door direction) {
	public void setDoor(int x, int y, int z, Material material, BlockFace facing) {
		clearBlock(x, y, z);
		clearBlock(x, y + 1, z);

		Hinge hinge = Hinge.LEFT;

		facing = fixFacing(facing);
		facing = facing.getOppositeFace();

		setDoorBlock(x, y, z, material, facing, Half.BOTTOM, hinge, false);
		setDoorBlock(x, y + 1, z, material, facing, Half.TOP, hinge, true);
	}

	public void setFenceDoor(int x, int y1, int y2, int z, Material material, BlockFace facing) {

		facing = fixFacing(facing);

		if (facing == BlockFace.NORTH || facing == BlockFace.SOUTH) {
			setBlocks(x, y1, y2, z, material, BlockFace.EAST, BlockFace.WEST);
		} else if (facing == BlockFace.EAST || facing == BlockFace.WEST) {
			setBlocks(x, y1, y2, z, material, BlockFace.NORTH, BlockFace.SOUTH);
		}
	}

	public final void setLadder(int x, int y1, int y2, int z, BlockFace direction) {

		// this calculates which wall the ladder is on
		int offsetX = 0;
		int offsetZ = 0;
		switch (direction) {
		case EAST:
			offsetX = -1;
			break;
		case WEST:
			offsetX = 1;
			break;
		case SOUTH:
			offsetZ = -1;
			break;
		case NORTH:
		default:
			offsetZ = 1;
			break;
		}

		// only put the ladder on the wall (see above) if there is actually a wall
		for (int y = y1; y < y2; y++) {
			if (!isEmpty(x + offsetX, y, z + offsetZ)) {
				setBlock(x, y, z, Material.LADDER, direction);
			}
		}
	}

	public final void setTallBlock(int x, int y, int z, Material material) {
		setBlock(x, y, z, material, Half.BOTTOM);
		setBlock(x, y + 1, z, material, Half.TOP);
	}

	private void setBlock(int x, int y, int z, Material material, Half half) {
		Block block = getActualBlock(x, y, z);
		block.setType(material, false);
		BlockData data = block.getBlockData();
		try {
			if (data instanceof Bisected)
				((Bisected) data).setHalf(half);
		} finally {
			block.setBlockData(data, getDoPhysics(x, z));
		}
	}

	public final void setBlock(int x, int y, int z, Material material, BlockFace facing, Half half) {
		Block block = getActualBlock(x, y, z);
		block.setType(material, false);
		BlockData data = block.getBlockData();
		try {
			if (data instanceof Directional)
				((Directional) data).setFacing(facing);
			if (data instanceof Bisected)
				((Bisected) data).setHalf(half);
		} finally {
			block.setBlockData(data, getDoPhysics(x, z));
		}
	}

	public final void setBlocks(int x1, int x2, int y, int z1, int z2, Material material, BlockFace facing) {
		for (int x = x1; x < x2; x++)
			for (int z = z1; z < z2; z++)
				setBlock(x, y, z, material, facing);
	}

	public final void setBlocks(int x1, int x2, int y, int z1, int z2, Material material, BlockFace... facing) {
		for (int x = x1; x < x2; x++)
			for (int z = z1; z < z2; z++)
				setBlock(x, y, z, material, facing);
	}

	public final void setBlocks(int x1, int x2, int y, int z1, int z2, Material material, BlockFace facing, Half half) {
		for (int x = x1; x < x2; x++)
			for (int z = z1; z < z2; z++)
				setBlock(x, y, z, material, facing, half);
	}

	public final void setBlocks(int x1, int x2, int y, int z1, int z2, Material material, Half half) {
		for (int x = x1; x < x2; x++)
			for (int z = z1; z < z2; z++)
				setBlock(x, y, z, material, half);
	}

	public final void setChest(CityWorldGenerator generator, int x, int y, int z, Odds odds,
			LootProvider lootProvider, LootLocation lootLocation) {
		if (!onEdgeXZ(x, z)) {
			BlockFace facing = BlockFace.NORTH;
			if (isEmpty(x - 1, y, z))
				facing = BlockFace.WEST;
			else if (isEmpty(x - 1, y, z))
				facing = BlockFace.EAST;
			else if (isEmpty(x, y, z + 1))
				facing = BlockFace.SOUTH;
			setChest(generator, x, y, z, facing, odds, lootProvider, lootLocation);
		}
	}

	public final void setChest(CityWorldGenerator generator, int x, int y, int z, BlockFace facing, Odds odds,
			LootProvider lootProvider, LootLocation lootLocation) {
		if (!onNearEdgeXZ(x, z)) {
//			generator.reportFormatted("CHEST AT %d, %d, %d", x, y, z);
			setBlock(x, y, z, Material.CHEST, facing);
			Block block = getActualBlock(x, y, z);
			connectDoubleChest(x, y, z, facing);
			if (isType(block, Material.CHEST))
				lootProvider.setLoot(generator, odds, world.getName(), lootLocation, block);
		}
//		else
//			generator.reportFormatted("SKIPPED CHEST AT %d, %d, %d", x, y, z);
	}

	public final void setDoubleChest(CityWorldGenerator generator, int x, int y, int z, BlockFace facing, Odds odds,
			LootProvider lootProvider, LootLocation lootLocation) {
		switch (facing) {
		default:
		case EAST:
		case WEST:
			if (z == 15) // Whoops, too far
				z = 14;
			setChest(generator, x, y, z, facing, odds, lootProvider, lootLocation);
			setChest(generator, x, y, z + 1, facing, odds, lootProvider, lootLocation);
			break;
		case NORTH:
		case SOUTH:
			if (x == 15) // Whoops, too far
				x = 14;
			setChest(generator, x, y, z, facing, odds, lootProvider, lootLocation);
			setChest(generator, x + 1, y, z, facing, odds, lootProvider, lootLocation);
			break;
		}
	}

	public final void setWallSign(int x, int y, int z, BlockFace facing, String... lines) {
		setWallSign(x, y, z, Material.BIRCH_WALL_SIGN, facing, lines);
	}

	public final void setWallSign(int x, int y, int z, Material sign, BlockFace facing, String... lines) {
		Block block = getActualBlock(x, y, z);
		block.setType(sign, false);
		BlockData data = block.getBlockData();
		try {
			if (data instanceof Directional)
				((Directional) data).setFacing(facing);

			BlockState state = block.getState();
			if (state instanceof Sign) {
				Sign signState = (Sign) state;
				for (int i = 0; i < lines.length && i < 4; i++)
					signState.setLine(i, lines[i]);
				state.update();
			}
		} finally {
			block.setBlockData(data, getDoPhysics(x, z));
		}
	}

	public final void setSignPost(int x, int y, int z, BlockFace rotation, String... lines) {
		setSignPost(x, y, z, Material.BIRCH_SIGN, rotation, lines);
	}

	public final void setSignPost(int x, int y, int z, Material sign, BlockFace rotation, String... lines) {
		Block block = getActualBlock(x, y, z);
		block.setType(sign, false);
		BlockData data = block.getBlockData();
		try {
			if (data instanceof Rotatable)
				((Rotatable) data).setRotation(rotation);

			BlockState state = block.getState();
			if (state instanceof Sign) {
				Sign signState = (Sign) state;
				for (int i = 0; i < lines.length && i < 4; i++)
					signState.setLine(i, lines[i]);
				state.update();
			}
		} finally {
			block.setBlockData(data, getDoPhysics(x, z));
		}
	}

//	private int lastDistance = -1;

	public final void setLeaf(int x, int y, int z, Material material, boolean isPersistent) {
		Block block = getActualBlock(x, y, z);
		block.setType(material, false);
		BlockData data = block.getBlockData();
		try {
			if (data instanceof Leaves)
				((Leaves) data).setPersistent(isPersistent);

		} finally {
			block.setBlockData(data, getDoPhysics(x, z));
//			if (data instanceof Leaves) {
//				int distance = ((Leaves) data).getDistance();
//				if (distance != lastDistance) {
//					CityWorld.log.info("@@ Leaves with " + distance + " for distance");
//					lastDistance = distance;
//				}
//			}
		}
	}

	public final void setLeaves(int x, int y1, int y2, int z, Material material, boolean isPersistent) {
		for (int y = y1; y < y2; y++)
			setLeaf(x, y, z, material, isPersistent);
	}

	public final void setLeaves(int x1, int x2, int y1, int y2, int z1, int z2, Material material,
			boolean isPersistent) {
		for (int x = x1; x < x2; x++)
			for (int y = y1; y < y2; y++)
				for (int z = z1; z < z2; z++)
					setLeaf(x, y, z, material, isPersistent);
	}

	public final void setLeafWalls(int x1, int x2, int y1, int y2, int z1, int z2, Material material,
			boolean isPersistent) {
		setLeaves(x1, x2, y1, y2, z1, z1 + 1, material, isPersistent); // N
		setLeaves(x1, x2, y1, y2, z2 - 1, z2, material, isPersistent); // S
		setLeaves(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, material, isPersistent); // W
		setLeaves(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, material, isPersistent); // E
	}

	private void setBedBlock(int x, int y, int z, Material material, BlockFace facing, Part part,
			boolean doPhysics) {
		Block block = getActualBlock(x, y, z);
		block.setType(material, false);
		BlockData data = block.getBlockData();
		try {
			if (data instanceof Bed) {
				((Bed) data).setFacing(facing);
				((Bed) data).setPart(part);
			}
		} finally {
			block.setBlockData(data, getDoPhysics(x, z));
		}
	}

	public final void setBed(int x, int y, int z, Material material, BlockFace facing) {
		switch (facing) {
		default:
		case NORTH:
			setBedBlock(x, y, z, material, BlockFace.SOUTH, Part.FOOT, false);
			setBedBlock(x, y, z + 1, material, BlockFace.SOUTH, Part.HEAD, true);
//			BlackMagic.setBlockType(getActualBlock(x, y, z), Material.BED_BLOCK, (byte)(0x0 + 0x8), true, false);
//			BlackMagic.setBlockType(getActualBlock(x, y, z + 1), Material.BED_BLOCK, (byte)(0x0), true, true);
			break;
		case SOUTH:
			setBedBlock(x, y, z + 1, material, BlockFace.NORTH, Part.FOOT, false);
			setBedBlock(x, y, z, material, BlockFace.NORTH, Part.HEAD, true);
//			BlackMagic.setBlockType(getActualBlock(x, y, z), Material.BED_BLOCK, (byte)(0x2 + 0x8), true, false);
//			BlackMagic.setBlockType(getActualBlock(x, y, z + 1), Material.BED_BLOCK, (byte)(0x2), true, true);
			break;
		case EAST:
			setBedBlock(x + 1, y, z, material, BlockFace.WEST, Part.FOOT, false);
			setBedBlock(x, y, z, material, BlockFace.WEST, Part.HEAD, true);
//@@			BlackMagic.setBlockType(getActualBlock(x, y, z), Material.BED_BLOCK, (byte)(0x1 + 0x8), true, false);
//			BlackMagic.setBlockType(getActualBlock(x + 1, y, z), Material.BED_BLOCK, (byte)(0x1), true, true);
			break;
		case WEST:
			setBedBlock(x, y, z, material, BlockFace.EAST, Part.FOOT, false);
			setBedBlock(x + 1, y, z, material, BlockFace.EAST, Part.HEAD, true);
//			BlackMagic.setBlockType(getActualBlock(x, y, z), Material.BED_BLOCK, (byte)(0x3 + 0x8), true, false);
//			BlackMagic.setBlockType(getActualBlock(x + 1, y, z), Material.BED_BLOCK, (byte)(0x3), true, true);
			break;
		}
//		switch (facing) {
//		case EAST:
//			BlackMagic.setBlockType(getActualBlock(x, y, z), Material.BED_BLOCK, (byte)(0x1 + 0x8), true, false);
//			BlackMagic.setBlockType(getActualBlock(x + 1, y, z), Material.BED_BLOCK, (byte)(0x1), true, true);
//			break;
//		case SOUTH:
//			BlackMagic.setBlockType(getActualBlock(x, y, z), Material.BED_BLOCK, (byte)(0x2 + 0x8), true, false);
//			BlackMagic.setBlockType(getActualBlock(x, y, z + 1), Material.BED_BLOCK, (byte)(0x2), true, true);
//			break;
//		case WEST:
//			BlackMagic.setBlockType(getActualBlock(x, y, z), Material.BED_BLOCK, (byte)(0x3 + 0x8), true, false);
//			BlackMagic.setBlockType(getActualBlock(x + 1, y, z), Material.BED_BLOCK, (byte)(0x3), true, true);
//			break;
//		case NORTH:
//			BlackMagic.setBlockType(getActualBlock(x, y, z), Material.BED_BLOCK, (byte)(0x0 + 0x8), true, false);
//			BlackMagic.setBlockType(getActualBlock(x, y, z + 1), Material.BED_BLOCK, (byte)(0x0), true, true);
//			break;
//		}
	}

	private void connectDoubleChest(int x, int y, int z, BlockFace facing) {
		Block block = getActualBlock(x, y, z);
		if (!isType(block, Material.CHEST)) {
			return;
		}
		if (((Chest) block.getBlockData()).getType() != Chest.Type.SINGLE) {
			return;
		}
		Block checkLeftBlock, checkRightBlock;
		switch (facing) {
		default:
		case EAST:
			checkLeftBlock = z > 0 ? getActualBlock(x, y, z - 1) : null;
			checkRightBlock = z < 15 ? getActualBlock(x, y, z + 1) : null;
			break;
		case SOUTH:
			checkLeftBlock = x < 15 ? getActualBlock(x + 1, y, z) : null;
			checkRightBlock = x > 0 ? getActualBlock(x - 1, y, z) : null;
			break;
		case WEST:
			checkLeftBlock = z < 15 ? getActualBlock(x, y, z + 1) : null;
			checkRightBlock = z > 0 ? getActualBlock(x, y, z - 1) : null;
			break;
		case NORTH:
			checkLeftBlock = x > 0 ? getActualBlock(x - 1, y, z) : null;
			checkRightBlock = x < 15 ? getActualBlock(x + 1, y, z) : null;
			break;
		}
		Chest blockData = null;
		if (checkLeftBlock != null && isType(checkLeftBlock, Material.CHEST)
				&& ((Chest) checkLeftBlock.getBlockData()).getFacing() == facing) {
			blockData = (Chest) block.getBlockData();
			Chest checkLeftBlockData = (Chest) checkLeftBlock.getBlockData();
			blockData.setType(Chest.Type.RIGHT);
			checkLeftBlockData.setType(Chest.Type.LEFT);
			block.setBlockData(blockData);
			checkLeftBlock.setBlockData(checkLeftBlockData);

		} else if (checkRightBlock != null && isType(checkRightBlock, Material.CHEST)
				&& ((Chest) checkRightBlock.getBlockData()).getFacing() == facing) {
			blockData = (Chest) block.getBlockData();
			Chest checkRightBlockData = (Chest) checkRightBlock.getBlockData();
			blockData.setType(Chest.Type.LEFT);
			checkRightBlockData.setType(Chest.Type.RIGHT);
			block.setBlockData(blockData);
			checkRightBlock.setBlockData(checkRightBlockData);
		}
	}

	public final void setGate(int x, int y, int z, Material material, BlockFace facing, boolean isOpen) {
		Block block = getActualBlock(x, y, z);
		block.setType(material, false);
		BlockData data = block.getBlockData();
		try {
			if (data instanceof Directional) {
				((Directional) data).setFacing(facing);
			}
			if (data instanceof Openable) {
				((Openable) data).setOpen(isOpen);
			}
		} finally {
			block.setBlockData(data, getDoPhysics(x, z));
		}
	}

	public final void setWaterLoggedBlocks(int x, int y1, int y2, int z, Material material) {
		for (int y = y1; y < y2; y++)
			setWaterLoggedBlock(x, y, z, material);
	}

	private void setWaterLoggedBlock(int x, int y, int z, Material material) {
		Block block = getActualBlock(x, y, z);
		block.setType(material, false);
		BlockData data = block.getBlockData();
		try {
			if (data instanceof Waterlogged) {
				((Waterlogged) data).setWaterlogged(true);
			}
		} finally {
			block.setBlockData(data, getDoPhysics(x, z));
		}
	}
}
