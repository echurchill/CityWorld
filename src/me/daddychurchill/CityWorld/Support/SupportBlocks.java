package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plugins.LootProvider;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.Rotatable;
import org.bukkit.block.data.type.Slab;
import org.bukkit.material.Vine;
import org.bukkit.util.noise.NoiseGenerator;

public abstract class SupportBlocks extends AbstractBlocks {
	
	private boolean doPhysics;
	private boolean doClearData;
	
	public SupportBlocks(CityWorldGenerator generator) {
		super(generator);
		
		doPhysics = false;
		doClearData = false;
	}
	
	public abstract Block getActualBlock(int x, int y, int z);

	public final boolean getDoPhysics() {
		return doPhysics;
	}
	
	public final void setDoPhysics(boolean dophysics) {
		doPhysics = dophysics;
	}

	public final boolean getDoClearData() {
		return doClearData;
	}
	
	public final void setDoClearData(boolean docleardata) {
		doClearData = docleardata;
	}

	@Override
	public final void setBlockIfEmpty(int x, int y, int z, Material material) {
		Block block = getActualBlock(x, y, z);
		if (block.isEmpty() && !getActualBlock(x, y - 1, z).isEmpty())
			setActualBlock(block, material);
	}
	
	private final void setActualBlock(Block block, Material material) {
		BlockState state = block.getState();
		state.setType(material);
		state.update(true, doPhysics);
	}
	
//	public final Block getActualBlock(int x, int y, int z, Material material) {
//		Block block = getActualBlock(x, y, z);
//		setActualBlock(block, material);
//		return block;
//	}
//	
	@Override
	public final void setBlock(int x, int y, int z, Material material) {
		setActualBlock(getActualBlock(x, y, z), material);
//		if (doClearData) {
//			BlockState state = getActualBlock(x, y, z).getState();
////			if (state.getType() != material) {
//				state.setType(material);
//				state.update(true, doPhysics);
////			}
//		} else {
//			getActualBlock(x, y, z).setType(material);
////			BlockState state = getActualBlock(x, y, z).getState();
////			if (state.getType() != material) {
////				state.setType(material);
////				state.update(true, doPhysics);
////			}
//		}
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
	
	public final void setBlockIfNot(int x, int y, int z, Material ... types) {
		if (!isOfTypes(x, y, z, types))
			setBlock(x, y, z, types[0]);
	}
	
	@Override
	public final boolean isEmpty(int x, int y, int z) {
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
	public final void clearBlock(int x, int y, int z) {
		getActualBlock(x, y, z).setType(Material.AIR);
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
	
//	private void drawCircleBlocks(int cx, int cz, int x, int z, int y, Material material, DyeColor color) {
//		// Ref: Notes/BCircle.PDF
//		setBlock(cx + x, y, cz + z, material, color); // point in octant 1
//		setBlock(cx + z, y, cz + x, material, color); // point in octant 2
//		setBlock(cx - z - 1, y, cz + x, material, color); // point in octant 3
//		setBlock(cx - x - 1, y, cz + z, material, color); // point in octant 4
//		setBlock(cx - x - 1, y, cz - z - 1, material, color); // point in octant 5
//		setBlock(cx - z - 1, y, cz - x - 1, material, color); // point in octant 6
//		setBlock(cx + z, y, cz - x - 1, material, color); // point in octant 7
//		setBlock(cx + x, y, cz - z - 1, material, color); // point in octant 8
//	}
//	
//	private void drawCircleBlocks(int cx, int cz, int x, int z, int y1, int y2, Material material, DyeColor color) {
//		for (int y = y1; y < y2; y++) {
//			drawCircleBlocks(cx, cz, x, z, y, material, color);
//		}
//	}
//	
//	private void fillCircleBlocks(int cx, int cz, int x, int z, int y, Material material, DyeColor color) {
//		// Ref: Notes/BCircle.PDF
//		setBlocks(cx - x - 1, cx - x, y, cz - z - 1, cz + z + 1, material, color); // point in octant 5
//		setBlocks(cx - z - 1, cx - z, y, cz - x - 1, cz + x + 1, material, color); // point in octant 6
//		setBlocks(cx + z, cx + z + 1, y, cz - x - 1, cz + x + 1, material, color); // point in octant 7
//		setBlocks(cx + x, cx + x + 1, y, cz - z - 1, cz + z + 1, material, color); // point in octant 8
//	}
//	
//	private void fillCircleBlocks(int cx, int cz, int x, int z, int y1, int y2, Material material, DyeColor color) {
//		for (int y = y1; y < y2; y++) {
//			fillCircleBlocks(cx, cz, x, z, y, material, color);
//		}
//	}
//	
//	public final void setCircle(int cx, int cz, int r, int y, Material material, DyeColor color) {
//		setCircle(cx, cz, r, y, y + 1, material, color, false);
//	}
//	
//	public final void setCircle(int cx, int cz, int r, int y, Material material, DyeColor color, boolean fill) {
//		setCircle(cx, cz, r, y, y + 1, material, color, fill);
//	}
//	
//	public final void setCircle(int cx, int cz, int r, int y1, int y2, Material material, DyeColor color) {
//		setCircle(cx, cz, r, y1, y2, material, color, false);
//	}
//	
//	public final void setCircle(int cx, int cz, int r, int y1, int y2, Material material, DyeColor color, boolean fill) {
//		// Ref: Notes/BCircle.PDF
//		int x = r;
//		int z = 0;
//		int xChange = 1 - 2 * r;
//		int zChange = 1;
//		int rError = 0;
//		
//		while (x >= z) {
//			if (fill)
//				fillCircleBlocks(cx, cz, x, z, y1, y2, material, color);
//			else
//				drawCircleBlocks(cx, cz, x, z, y1, y2, material, color);
//			z++;
//			rError += zChange;
//			zChange += 2;
//			if (2 * rError + xChange > 0) {
//				x--;
//				rError += xChange;
//				xChange += 2;
//			}
//		}
//	}
	
	//@@ I REALLY NEED TO FIGURE A DIFFERENT WAY TO DO THIS
	public final boolean isNonstackableBlock(Block block) { // either because it really isn't or it just doesn't look good
		switch (block.getType()) {
		default: 
			return true;
		case STONE:
		case GRASS:
		case DIRT:
		case COBBLESTONE:
//		case WOOD:
		case SAND:
		case GRAVEL:
		case COAL_ORE:
		case DIAMOND_ORE:
		case EMERALD_ORE:
		case GOLD_ORE:
		case IRON_ORE:
		case LAPIS_ORE:
//		case QUARTZ_ORE:
		case REDSTONE_ORE:
		case COAL_BLOCK:
		case DIAMOND_BLOCK:
		case EMERALD_BLOCK:
		case GOLD_BLOCK:
		case HAY_BLOCK:
		case IRON_BLOCK:
		case LAPIS_BLOCK:
		case PURPUR_BLOCK:
		case QUARTZ_BLOCK:
		case SLIME_BLOCK:
		case SNOW_BLOCK:
//		case LOG:
//		case LOG_2:
		case SPONGE:
		case SANDSTONE:
		case RED_SANDSTONE:
		case SOUL_SAND:
//		case STAINED_CLAY:
//		case HARD_CLAY:
		case CLAY:
//		case WOOL:
//		case DOUBLE_STEP:
//		case WOOD_DOUBLE_STEP:
//		case DOUBLE_STONE_SLAB2:
//		case PURPUR_DOUBLE_SLAB:
		case BRICK:
//		case END_BRICKS:
		case NETHER_BRICK:
//		case SMOOTH_BRICK:
		case BOOKSHELF:
		case MOSSY_COBBLESTONE:
		case OBSIDIAN:
//		case SOIL:
		case ICE:
		case PACKED_ICE:
		case FROSTED_ICE:
		case NETHERRACK:
//		case ENDER_STONE:
//		case MYCEL:
		case PRISMARINE:
		case GRASS_PATH:
		case BEDROCK:
			return false;
		}
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
	
//	public void setBlockTypeAndColor(int x, int y, int z, Material material, DyeColor color) {
//		BlockState state = getActualBlock(x, y, z).getState();
//		state.setType(material);
//		BlockData data = state.getBlockData();
//		if (data instanceof Colorable)
//			((Colorable)state.getData()).setColor(color);
//		state.update(true, doPhysics);
//	}
//	
//	public void setBlockIfTypeThenColor(int x, int y, int z, Material material, DyeColor color) {
//		BlockState state = getActualBlock(x, y, z).getState();
//		if (state.getType() == material) {
//			BlockData data = state.getBlockData();
//			if (data instanceof Colorable)
//				((Colorable)state.getData()).setColor(color);
//			state.update(true, doPhysics);
//		}
//	}
//	
//	public void setBlocksTypeAndColor(int x, int y1, int y2, int z, Material material, DyeColor color) {
//		for (int y = y1; y < y2; y++)
//			setBlockTypeAndColor(x, y, z, material, color);
//	}
//	
//	public void setBlocksTypeAndColor(int x1, int x2, int y, int z1, int z2, Material material, DyeColor color) {
//		for (int x = x1; x < x2; x++) {
//			for (int z = z1; z < z2; z++) {
//				setBlockTypeAndColor(x, y, z, material, color);
//			}
//		}
//	}
//	
//	public void setBlocksTypeAndColor(int x1, int x2, int y1, int y2, int z1, int z2, Material material, DyeColor color) {
//		for (int x = x1; x < x2; x++) {
//			for (int y = y1; y < y2; y++) {
//				for (int z = z1; z < z2; z++) {
//					setBlockTypeAndColor(x, y, z, material, color);
//				}
//			}
//		}
//	}
//	
//	public void setBlockTypeAndDirection(int x, int y, int z, Material material, BlockFace facing) {
//		BlockState state = getActualBlock(x, y, z).getState();
//		state.setType(material);
//		BlockData data = state.getBlockData();
//		if (data instanceof Directional)
//			((Directional)state.getData()).setFacingDirection(facing);
//		state.update(true, doPhysics);
//	}
//	
//	public void setBlocksTypeAndDirection(int x1, int x2, int y1, int y2, int z1, int z2, Material material, BlockFace facing) {
//		for (int x = x1; x < x2; x++) {
//			for (int y = y1; y < y2; y++) {
//				for (int z = z1; z < z2; z++) {
//					setBlockTypeAndDirection(x, y, z, material, facing);
//				}
//			}
//		}
//	}
//	
//	public void setBlockTypeAndTexture(int x, int y, int z, Material material, Material texture) {
//		BlockState state = getActualBlock(x, y, z).getState();
//		state.setType(material);
//		BlockData data = state.getBlockData();
//		if (data instanceof TexturedMaterial)
//			((TexturedMaterial)state.getData()).setMaterial(texture);
//		state.update(true, doPhysics);
//	}
	
	private int clamp(double value, int max) {
		return NoiseGenerator.floor((value - NoiseGenerator.floor(value)) * max);
	}
	
	public final Block setBlock(int x, int y, int z, Material material, double level) {
		Block block = getActualBlock(x, y, z);
		BlockState state = block.getState();
		try {
			state.setType(material);
			BlockData data = state.getBlockData();
			if (data instanceof Levelled) {
				Levelled levelled = (Levelled)data;
				levelled.setLevel(clamp(level, levelled.getMaximumLevel()));
			}
			state.setBlockData(data);
		} finally {
			state.update(true, doPhysics);
		}
		return block;
	}

////@@
//	public final void setCauldron(int x, int y, int z, int level) {
//		setBlock(x, y, z, Material.CAULDRON);
////		Block block = getActualBlock(x, y, z);
////		BlackMagic.setBlockType(block, Material.CAULDRON, clamp(level, BlackMagic.maxCauldronLevel));
//	}

	public final void setCauldron(int x, int y, int z, Odds odds) {
		setBlock(x, y, z, Material.CAULDRON, odds.getRandomDouble());
	}

//	public final void setWool(int x, int y, int z, DyeColor color) {
//		setBlockTypeAndColor(x, y, z, Material.WHITE_WOOL, color);
//	}
//	
//	public final void setWool(int x1, int x2, int y1, int y2, int z1, int z2, DyeColor color) {
//		setBlocksTypeAndColor(x1, x2, y1, y2, z1, z2, Material.WHITE_WOOL, color);
//	}
//	
//	public final void setClay(int x, int y, int z, DyeColor color) {
//		setBlockTypeAndColor(x, y, z, Material.WHITE_TERRACOTTA, color);
//	}
//	
//	public final void setClay(int x, int y1, int y2, int z, DyeColor color) {
//		setBlocksTypeAndColor(x, x + 1, y1, y2, z, z + 1, Material.WHITE_TERRACOTTA, color);
//	}
//	
//	public final void setClay(int x1, int x2, int y, int z1, int z2, DyeColor color) {
//		setBlocksTypeAndColor(x1, x2, y, y + 1, z1, z2, Material.WHITE_TERRACOTTA, color);
//	}
//	
//	public final void setClay(int x1, int x2, int y1, int y2, int z1, int z2, DyeColor color) {
//		setBlocksTypeAndColor(x1, x2, y1, y2, z1, z2, Material.WHITE_TERRACOTTA, color);
//	}
//	
//	public final void setClayWalls(int x1, int x2, int y1, int y2, int z1, int z2, DyeColor color) {
//		setBlocksTypeAndColor(x1, x2, y1, y2, z1, z1 + 1, Material.WHITE_TERRACOTTA, color);
//		setBlocksTypeAndColor(x1, x2, y1, y2, z2 - 1, z2, Material.WHITE_TERRACOTTA, color);
//		setBlocksTypeAndColor(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, Material.WHITE_TERRACOTTA, color);
//		setBlocksTypeAndColor(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, Material.WHITE_TERRACOTTA, color);
//	}
//	
//	public final void camoClay(int x1, int x2, int y1, int y2, int z1, int z2, Odds odds, ColorSet colors) {
//		for (int x = x1; x < x2; x++) {
//			for (int y = y1; y < y2; y++) {
//				for (int z = z1; z < z2; z++) {
//					setBlockIfTypeThenColor(x, y, z, Material.WHITE_TERRACOTTA, odds.getRandomColor(colors));
//				}
//			}
//		}
//	}
//	
//	public final void setGlass(int x, int y, int z, DyeColor color) {
//		setBlockTypeAndColor(x, y, z, Material.WHITE_STAINED_GLASS, color);
//	}
//	
//	public final void setGlass(int x1, int x2, int y1, int y2, int z1, int z2, DyeColor color) {
//		setBlocksTypeAndColor(x1, x2, y1, y2, z1, z2, Material.WHITE_STAINED_GLASS, color);
//	}
//	
//	public final void setGlassWalls(int x1, int x2, int y1, int y2, int z1, int z2, DyeColor color) {
//		setBlocksTypeAndColor(x1, x2, y1, y2, z1, z1 + 1, Material.WHITE_STAINED_GLASS, color);
//		setBlocksTypeAndColor(x1, x2, y1, y2, z2 - 1, z2, Material.WHITE_STAINED_GLASS, color);
//		setBlocksTypeAndColor(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, Material.WHITE_STAINED_GLASS, color);
//		setBlocksTypeAndColor(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, Material.WHITE_STAINED_GLASS, color);
//	}
//	
//	public final void setThinGlass(int x, int y, int z, DyeColor color) {
//		setBlockTypeAndColor(x, y, z, Material.WHITE_STAINED_GLASS_PANE, color);
//	}
//	
//	public final void setThinGlass(int x1, int x2, int y1, int y2, int z1, int z2, DyeColor color) {
//		setBlocksTypeAndColor(x1, x2, y1, y2, z1, z2, Material.WHITE_STAINED_GLASS_PANE, color);
//	}
//	
//	public final void setThinGlassWalls(int x1, int x2, int y1, int y2, int z1, int z2, DyeColor color) {
//		setBlocksTypeAndColor(x1, x2, y1, y2, z1, z1 + 1, Material.WHITE_STAINED_GLASS_PANE, color);
//		setBlocksTypeAndColor(x1, x2, y1, y2, z2 - 1, z2, Material.WHITE_STAINED_GLASS_PANE, color);
//		setBlocksTypeAndColor(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, Material.WHITE_STAINED_GLASS_PANE, color);
//		setBlocksTypeAndColor(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, Material.WHITE_STAINED_GLASS_PANE, color);
//	}

	public final void setVine(int x, int y, int z, BlockFace ... faces) {
		Block block = getActualBlock(x, y, z);
		BlockState state = block.getState();
		try {
			state.setType(Material.VINE);
			BlockData data = block.getBlockData();
			if (data instanceof Vine) {
				Vine vines = (Vine)data;
				for (BlockFace face: faces)
					vines.putOnFace(face);
			}
			state.setBlockData(data);
		} finally {
			state.update(false, doPhysics);
		}
	}

	public final void setVines(int x, int y1, int y2, int z, BlockFace... faces) {
		for (int y = y1; y < y2; y++)
			setVine(x, y, z, faces);
	}
	
	public final void setBlock(int x, int y, int z, Material material, Slab.Type type) {
		Block block = getActualBlock(x, y, z);
		BlockState state = block.getState();
		try {
			state.setType(material);
			BlockData data = block.getBlockData();
			if (data instanceof Slab) {
				((Slab) data).setType(type);
			}
			state.setBlockData(data);
		} finally {
			state.update(false, doPhysics);
		}
	}
	
	public final void setBlocks(int x1, int x2, int y, int z1, int z2, Material material, Slab.Type type) {
		setBlocks(x1, x2, y, y + 1, z1, z2, material, type);
	}
	
	public final void setBlocks(int x, int y1, int y2, int z, Material material, Slab.Type type) {
		for (int y = y1; y < y2; y++) {
			setBlock(x, y, z, material, type);
		}
	}
	
	public final void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material, Slab.Type type) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					setBlock(x, y, z, material, type);
				}
			}
		}
	}
	
	public final Block setBlock(int x, int y, int z, Material material, BlockFace facing) {
		Block block = getActualBlock(x, y, z);
		BlockState state = block.getState();
		try {
			state.setType(material);
			BlockData data = state.getBlockData();
			if (data instanceof Directional) {
				((Directional) data).setFacing(facing);
			}
			state.setBlockData(data);
		} finally {
			state.update(false, doPhysics);
		}
		return block;
	}
	
	public final void setBlocks(int x, int y1, int y2, int z, Material material, BlockFace facing) {
		setBlocks(x, x + 1, y1, y2, z, z + 1, material, facing);
	}
	
	public final void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material, BlockFace facing) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					setBlock(x, y, z, material, facing);
				}
			}
		}
	}
	
	public final void drawCrane(DataContext context, Odds odds, int x, int y, int z) {
		
		// vertical bit
		setBlocks(x, y, y + 8, z, Material.IRON_BARS);
		setBlocks(x - 1, y, y + 8, z, Material.IRON_BARS); // 1.9 shows iron fences very thin now
		setBlocks(x, y + 8, y + 10, z, Material.STONE);
		setBlocks(x - 1, y + 8, y + 10, z, Material.STONE_SLAB);
		setBlock(x, y + 10, z, context.torchMat, BlockFace.UP);
		
		// horizontal bit
		setBlock(x + 1, y + 8, z, Material.GLASS);
		setBlocks(x + 2, x + 11, y + 8, y + 9, z, z + 1, Material.IRON_BARS);
		setBlocks(x + 1, x + 10, y + 9, y + 10, z, z + 1, Material.STONE_SLAB);
		setBlock(x + 10, y + 9, z, Material.STONE_BRICK_STAIRS, BlockFace.WEST);
		
		// counter weight
		setBlock(x - 2, y + 9, z, Material.STONE_SLAB);
		setBlock(x - 3, y + 9, z, Material.STONE_BRICK_STAIRS, BlockFace.EAST);
		setBlocks(x - 3, x - 1, y + 7, y + 9, z, z + 1, odds.getRandomMaterial(Odds.allConcreteBlocks));
	}
	
	public final void setTable(int x1, int x2, int y, int z1, int z2) {
		setTable(x1, x2, y, z1, z2, Material.STONE_PRESSURE_PLATE);
	}
	
	public final void setTable(int x, int y, int z) {
		setTable(x, y, z, Material.STONE_PRESSURE_PLATE);
	}
	
	public final void setTable(int x1, int x2, int y, int z1, int z2, Material tableTop) {
		setTable(x1, x2, y, z1, z2, Material.SPRUCE_FENCE, tableTop);
	}
	
	public final void setTable(int x, int y, int z, Material tableTop) {
		setTable(x, y, z, Material.SPRUCE_FENCE, tableTop);
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
	
//@@	public void setDoor(int x, int y, int z, Material material, BadMagic.Door direction) {
	public void setDoor(int x, int y, int z, Material material, BlockFace direction) {
//		byte orentation = 0;
//		byte hinge = 0; // org.bukkit.block.data.type.Door (hinge), org.bukkit.block.data.Directional, org.bukkit.block.data.Bisected (top/bottom)
//		
//		// orientation
//		switch (direction) {
//		case NORTHBYNORTHEAST:
//		case NORTH_NORTH_WEST:
//			orentation = 1;
//			break;
//		case SOUTH_SOUTH_EAST:
//		case SOUTHBYSOUTHWEST:
//			orentation = 3;
//			break;
//		case WEST_NORTH_WEST:
//		case WESTBYSOUTHWEST:
//			orentation = 0;
//			break;
//		case EAST_NORTH_EAST:
//		case EASTBYSOUTHEAST:
//			orentation = 2;
//			break;
//		}
//		
//		// hinge?
//		switch (direction) {
//		case SOUTH_SOUTH_EAST:
//		case NORTH_NORTH_WEST:
//		case WESTBYSOUTHWEST:
//		case EAST_NORTH_EAST:
//			hinge = 8 + 0;
//			break;
//		case NORTHBYNORTHEAST:
//		case SOUTHBYSOUTHWEST:
//		case WEST_NORTH_WEST:
//		case EASTBYSOUTHEAST:
//			hinge = 8 + 1;
//			break;
//		}
//		
//		// set the door
//		BlackMagic.setBlockType(getActualBlock(x, y    , z), material, orentation, true, false);
//		BlackMagic.setBlockType(getActualBlock(x, y + 1, z), material, hinge, true, true);
	}

//	public final void setWoodenDoor(int x, int y, int z, BadMagic.Door direction) {
//		setDoor(x, y, z, Material.OAK_DOOR, direction);
//	}
//	
//	public final void setIronDoor(int x, int y, int z, BadMagic.Door direction) {
//		setDoor(x, y, z, Material.IRON_DOOR_BLOCK, direction);
//	}
//
//	public final void setTrapDoor(int x, int y, int z, BadMagic.TrapDoor direction) {
//		BlackMagic.setBlock(this, x, y, z, Material.TRAP_DOOR, direction.getData());
//	}
//
//	public final void setStoneSlab(int x, int y, int z, BadMagic.StoneSlab direction) {
//		BlackMagic.setBlock(this, x, y, z, Material.STONE_SLAB, direction.getData());
//	}
//
	public final void setLadder(int x, int y1, int y2, int z, BlockFace direction) {
		int offsetX = 0;
		int offsetZ = 0;
		switch (direction) {
		case WEST:
			offsetX = -1;
			break;
		case EAST:
			offsetX = 1;
			break;
		case NORTH:
			offsetZ = -1;
			break;
		case SOUTH:
		default:
			offsetZ = 1;
			break;
		}
		for (int y = y1; y < y2; y++) {
			if (!isEmpty(x + offsetX, y, z + offsetZ)) {
				setBlock(x, y, z, Material.LADDER, direction);
//				Ladder data = new Ladder();
//				data.setFacingDirection(direction);
//				setBlock(x, y, z, Material.LADDER, data);
			}
		}
	}

	public final void setBlock(int x, int y, int z, Material material, BlockFace facing, Half half) {
		Block block = getActualBlock(x, y, z);
		BlockState state = block.getState();
		try {
			state.setType(material);
			BlockData data = state.getBlockData();
			if (data instanceof Directional) {
				((Directional)data).setFacing(facing);
			}
			if (data instanceof Bisected) {
				((Bisected)data).setHalf(half);
			}
			state.setBlockData(data);
		} finally {
			state.update(false, doPhysics);
		}
	}

	public final void setBlocks(int x1, int x2, int y, int z1, int z2, Material material, BlockFace facing, Half half) {
		for (int x = x1; x < x2; x++)
			for (int z = z1; z < z2; z++)
				setBlock(x, y, z, material, facing, half);
	}

//	public static final Material filterStairMaterial(Material material) {
//		switch (material) {
//		case BRICK:
//		case BRICK_STAIRS:
//			return Material.BRICK_STAIRS;
//		case COBBLESTONE:
//		case MOSSY_COBBLESTONE:
//		case COBBLESTONE_STAIRS:
//			return Material.COBBLESTONE_STAIRS;
//		case NETHERRACK:
//		case NETHER_BRICK:
//		case NETHER_BRICK_STAIRS:
//			return Material.NETHER_BRICK_STAIRS;
//		case PURPUR_BLOCK:
//		case PURPUR_SLAB:
//		case PURPUR_DOUBLE_SLAB:
//		case PURPUR_STAIRS:
//			return Material.PURPUR_STAIRS;
//		case RED_SANDSTONE:
//		case RED_SANDSTONE_STAIRS:
//			return Material.RED_SANDSTONE_STAIRS;
//		case SAND:
//		case SANDSTONE:
//		case SANDSTONE_STAIRS:
//			return Material.SANDSTONE_STAIRS;
//		case SMOOTH_BRICK:
//		case STONE_BRICK_STAIRS:
//			return Material.STONE_BRICK_STAIRS;
//		case WOOL: // it is white too!
//		case QUARTZ_BLOCK:
//		case QUARTZ_STAIRS:
//			return Material.QUARTZ_STAIRS;
//		default:
////		case WOOD:
//			return Material.BIRCH_STAIRS;
//		}
//	}
//
//	public final void setVine(int x, int y, int z, BadMagic.Vine direction) {
//		BlackMagic.setBlock(this, x, y, z, Material.VINE, direction.getData());
//	}
//
//	public final void setTorch(int x, int y, int z, Material material, BadMagic.Torch direction) {
//		BlackMagic.setBlock(this, x, y, z, material, direction.getData());
//	}
//	
//	public final void setFurnace(int x, int y, int z, BadMagic.General direction) {
//		BlackMagic.setBlock(this, x, y, z, Material.FURNACE, direction.getData());
//	}
//
//	public final void setChest(CityWorldGenerator generator, int x, int y, int z, BadMagic.General direction, Odds odds, LootProvider lootProvider, LootLocation lootLocation) {
//		Block block = getActualBlock(x, y, z);
//		if (BlackMagic.setBlockType(block, Material.CHEST, direction.getData())) {
//			if (block.getType() == Material.CHEST) {
//				lootProvider.setLoot(generator, odds, world.getName(), lootLocation, block);
//			}
//		}
//	}
//	
//	public final void setTorch(int x, int y, int z, Material material, BlockFace facing) {
//		setBlock(x, y, z, new Torch(material, facing));
//	}
//	
//	public final void setFurnace(int x, int y, int z, BlockFace facing) {
//		setBlock(x, y, z, new Furnace(facing));
//	}

	public final void setChest(CityWorldGenerator generator, int x, int y, int z, BlockFace facing, Odds odds, LootProvider lootProvider, LootLocation lootLocation) {
		Block block = setBlock(x, y, z, Material.CHEST, facing);
		if (isType(block, Material.CHEST)) {
			lootProvider.setLoot(generator, odds, world.getName(), lootLocation, block);
		}
	}
	
	public final void setDoubleChest(CityWorldGenerator generator, int x, int y, int z, BlockFace facing, Odds odds, LootProvider lootProvider, LootLocation lootLocation) {
		switch (facing) {
		default:
		case EAST:
		case WEST:
			setChest(generator, x, y, z, facing, odds, lootProvider, lootLocation);
			setChest(generator, x, y, z + 1, facing, odds, lootProvider, lootLocation);
			break;
		case NORTH:
		case SOUTH:
			setChest(generator, x, y, z, facing, odds, lootProvider, lootLocation);
			setChest(generator, x + 1, y, z, facing, odds, lootProvider, lootLocation);
			break;
		}
	}

	public final void setWallSign(int x, int y, int z, BlockFace facing, String ... lines) {
		Block block = getActualBlock(x, y, z);
		BlockState state = block.getState();
		try {
			state.setType(Material.WALL_SIGN);
			BlockData data = state.getBlockData();
			if (data instanceof Directional) {
				((Directional)data).setFacing(facing);
			}
			if (state instanceof Sign) {
				Sign signState = (Sign)state;
				for (int i = 0; i < lines.length && i < 4; i++) 
					signState.setLine(i, lines[i]);
			}
			state.setBlockData(data);
		} finally {
			state.update(false, doPhysics);
		}
//		
//		Block block = getActualBlock(x, y, z);
//		if (BlackMagic.setBlockType(block, Material.WALL_SIGN, direction.getData())) {
//			if (block.getType() == Material.WALL_SIGN) {
//				Sign sign = (Sign) block.getState();
//				for (int i = 0; i < text.length && i < 4; i++) 
//					sign.setLine(i, text[i]);
//				sign.update(true);
//			}
//		}
	}


	public final void setSignPost(int x, int y, int z, BlockFace rotation, String ... lines) {
		Block block = getActualBlock(x, y, z);
		BlockState state = block.getState();
		try {
			state.setType(Material.SIGN);
			BlockData data = state.getBlockData();
			if (data instanceof Rotatable) {
				((Rotatable)data).setRotation(rotation);
			}
			if (state instanceof Sign) {
				Sign signState = (Sign)state;
				for (int i = 0; i < lines.length && i < 4; i++) 
					signState.setLine(i, lines[i]);
			}
			state.setBlockData(data);
		} finally {
			state.update(false, doPhysics);
		}
//		
//		
//		if (block.getType() == Material.SIGN_POST) {
//			Sign signState = (Sign) block.getState();
//			
//			org.bukkit.material.Sign signDirection = new org.bukkit.material.Sign();
//			signDirection.setFacingDirection(direction);
//			signState.setData(signDirection);
//			
//			for (int i = 0; i < text.length && i < 4; i++) 
//				signState.setLine(i, text[i]);
//
//			signState.update();
//		}
	}
	
	public final void setBed(int x, int y, int z, BlockFace facing) {
		switch (facing) {
		default:
		case EAST:
			setBlock(x, y, z, Material.BONE_BLOCK);
			setBlock(x + 1, y, z, Material.COAL_BLOCK);
//			BlackMagic.setBlockType(getActualBlock(x, y, z), Material.BED_BLOCK, (byte)(0x1 + 0x8), true, false);
//			BlackMagic.setBlockType(getActualBlock(x + 1, y, z), Material.BED_BLOCK, (byte)(0x1), true, true);
			break;
		case SOUTH:
			setBlock(x, y, z, Material.BONE_BLOCK);
			setBlock(x, y, z + 1, Material.COAL_BLOCK);
//			BlackMagic.setBlockType(getActualBlock(x, y, z), Material.BED_BLOCK, (byte)(0x2 + 0x8), true, false);
//			BlackMagic.setBlockType(getActualBlock(x, y, z + 1), Material.BED_BLOCK, (byte)(0x2), true, true);
			break;
		case WEST:
			setBlock(x, y, z, Material.COAL_BLOCK);
			setBlock(x + 1, y, z, Material.BONE_BLOCK);
//			BlackMagic.setBlockType(getActualBlock(x, y, z), Material.BED_BLOCK, (byte)(0x3 + 0x8), true, false);
//			BlackMagic.setBlockType(getActualBlock(x + 1, y, z), Material.BED_BLOCK, (byte)(0x3), true, true);
			break;
		case NORTH:
			setBlock(x, y, z, Material.COAL_BLOCK);
			setBlock(x, y, z + 1, Material.BONE_BLOCK);
//			BlackMagic.setBlockType(getActualBlock(x, y, z), Material.BED_BLOCK, (byte)(0x0 + 0x8), true, false);
//			BlackMagic.setBlockType(getActualBlock(x, y, z + 1), Material.BED_BLOCK, (byte)(0x0), true, true);
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
	
}
