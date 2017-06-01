package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plugins.LootProvider;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import me.daddychurchill.CityWorld.Support.BadMagic.Facing;
import me.daddychurchill.CityWorld.Support.BadMagic.Stair;
import me.daddychurchill.CityWorld.Support.Odds.ColorSet;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.material.Chest;
import org.bukkit.material.Colorable;
import org.bukkit.material.Directional;
import org.bukkit.material.Ladder;
import org.bukkit.material.Leaves;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Step;
import org.bukkit.material.TexturedMaterial;
import org.bukkit.material.Tree;
import org.bukkit.material.Vine;
import org.bukkit.material.WoodenStep;

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
			block.setType(material);
	}
	
	public final void setBlockIfAir(int x, int y, int z, Material material, MaterialData data) {
		Block block = getActualBlock(x, y, z);
		if (block.isEmpty() && !getActualBlock(x, y - 1, z).isEmpty())
			setBlock(block, material, data);
	}
	
	public final void setBlock(Block block, Material material, MaterialData data) {
		BlockState state = block.getState();
		state.setType(material);
		state.setData(data);
		state.update(true, doPhysics);
	}
	
	public final Block getActualBlock(int x, int y, int z, MaterialData data) {
		Block block = getActualBlock(x, y, z);
		setBlock(block, data.getItemType(), data);
		return block;
	}
	
	@Override
	public final void setBlock(int x, int y, int z, Material material) {
		if (doClearData) {
			BlockState state = getActualBlock(x, y, z).getState();
//			if (state.getType() != material) {
				state.setType(material);
				state.setData(new MaterialData(material));
				state.update(true, doPhysics);
//			}
		} else {
			getActualBlock(x, y, z).setType(material);
//			BlockState state = getActualBlock(x, y, z).getState();
//			if (state.getType() != material) {
//				state.setType(material);
//				state.update(true, doPhysics);
//			}
		}
	}
	
	@Override
	public void setBlock(int x, int y, int z, MaterialData material) {
		setBlock(getActualBlock(x, y, z), material.getItemType(), material);
	}

	public final void setBlock(int x, int y, int z, Material material, MaterialData data) {
		setBlock(getActualBlock(x, y, z), material, data);
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
		return isOfTypes(x, y, z, Material.STATIONARY_WATER, Material.WATER);
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

	public final void setBlocks(int x, int y1, int y2, int z, Material material, MaterialData data) {
		for (int y = y1; y < y2; y++)
			setBlock(x, y, z, material, data);
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

	public final void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material, MaterialData data) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					setBlock(x, y, z, material, data);
				}
			}
		}
	}

	public final void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material, 
			Odds odds, MaterialData data1, MaterialData data2) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					if (odds.playOdds(Odds.oddsPrettyLikely))
						setBlock(x, y, z, material, data1);
					else
						setBlock(x, y, z, material, data2);
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

	public final void setBlocks(int x1, int x2, int y, int z1, int z2, Material material, MaterialData data) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				setBlock(x, y, z, material, data);
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
	
	public final void setWalls(int x1, int x2, int y, int z1, int z2, Material material, MaterialData data) {
		setWalls(x1, x2, y, y + 1, z1, z2, material, data);
	}
	
	public final void setWalls(int x1, int x2, int y1, int y2, int z1, int z2, Material material, MaterialData data) {
		setBlocks(x1, x2, y1, y2, z1, z1 + 1, material, data);
		setBlocks(x1, x2, y1, y2, z2 - 1, z2, material, data);
		setBlocks(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, material, data);
		setBlocks(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, material, data);
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
	
	private void drawCircleBlocks(int cx, int cz, int x, int z, int y, Material material, DyeColor color) {
		// Ref: Notes/BCircle.PDF
		setBlockTypeAndColor(cx + x, y, cz + z, material, color); // point in octant 1
		setBlockTypeAndColor(cx + z, y, cz + x, material, color); // point in octant 2
		setBlockTypeAndColor(cx - z - 1, y, cz + x, material, color); // point in octant 3
		setBlockTypeAndColor(cx - x - 1, y, cz + z, material, color); // point in octant 4
		setBlockTypeAndColor(cx - x - 1, y, cz - z - 1, material, color); // point in octant 5
		setBlockTypeAndColor(cx - z - 1, y, cz - x - 1, material, color); // point in octant 6
		setBlockTypeAndColor(cx + z, y, cz - x - 1, material, color); // point in octant 7
		setBlockTypeAndColor(cx + x, y, cz - z - 1, material, color); // point in octant 8
	}
	
	private void drawCircleBlocks(int cx, int cz, int x, int z, int y1, int y2, Material material, DyeColor color) {
		for (int y = y1; y < y2; y++) {
			drawCircleBlocks(cx, cz, x, z, y, material, color);
		}
	}
	
	private void fillCircleBlocks(int cx, int cz, int x, int z, int y, Material material, DyeColor color) {
		// Ref: Notes/BCircle.PDF
		setBlocksTypeAndColor(cx - x - 1, cx - x, y, cz - z - 1, cz + z + 1, material, color); // point in octant 5
		setBlocksTypeAndColor(cx - z - 1, cx - z, y, cz - x - 1, cz + x + 1, material, color); // point in octant 6
		setBlocksTypeAndColor(cx + z, cx + z + 1, y, cz - x - 1, cz + x + 1, material, color); // point in octant 7
		setBlocksTypeAndColor(cx + x, cx + x + 1, y, cz - z - 1, cz + z + 1, material, color); // point in octant 8
	}
	
	private void fillCircleBlocks(int cx, int cz, int x, int z, int y1, int y2, Material material, DyeColor color) {
		for (int y = y1; y < y2; y++) {
			fillCircleBlocks(cx, cz, x, z, y, material, color);
		}
	}
	
	public final void setCircle(int cx, int cz, int r, int y, Material material, DyeColor color) {
		setCircle(cx, cz, r, y, y + 1, material, color, false);
	}
	
	public final void setCircle(int cx, int cz, int r, int y, Material material, DyeColor color, boolean fill) {
		setCircle(cx, cz, r, y, y + 1, material, color, fill);
	}
	
	public final void setCircle(int cx, int cz, int r, int y1, int y2, Material material, DyeColor color) {
		setCircle(cx, cz, r, y1, y2, material, color, false);
	}
	
	public final void setCircle(int cx, int cz, int r, int y1, int y2, Material material, DyeColor color, boolean fill) {
		// Ref: Notes/BCircle.PDF
		int x = r;
		int z = 0;
		int xChange = 1 - 2 * r;
		int zChange = 1;
		int rError = 0;
		
		while (x >= z) {
			if (fill)
				fillCircleBlocks(cx, cz, x, z, y1, y2, material, color);
			else
				drawCircleBlocks(cx, cz, x, z, y1, y2, material, color);
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
	
	public final boolean isNonstackableBlock(Block block) { // either because it really isn't or it just doesn't look good
		switch (block.getType()) {
		default: 
			return true;
		case STONE:
		case GRASS:
		case DIRT:
		case COBBLESTONE:
		case WOOD:
		case SAND:
		case GRAVEL:
		case COAL_ORE:
		case DIAMOND_ORE:
		case EMERALD_ORE:
		case GOLD_ORE:
		case IRON_ORE:
		case LAPIS_ORE:
		case QUARTZ_ORE:
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
		case LOG:
		case LOG_2:
		case SPONGE:
		case SANDSTONE:
		case RED_SANDSTONE:
		case SOUL_SAND:
		case STAINED_CLAY:
		case HARD_CLAY:
		case CLAY:
		case WOOL:
		case DOUBLE_STEP:
		case WOOD_DOUBLE_STEP:
		case DOUBLE_STONE_SLAB2:
		case PURPUR_DOUBLE_SLAB:
		case BRICK:
		case END_BRICKS:
		case NETHER_BRICK:
		case SMOOTH_BRICK:
		case BOOKSHELF:
		case MOSSY_COBBLESTONE:
		case OBSIDIAN:
		case SOIL:
		case ICE:
		case PACKED_ICE:
		case FROSTED_ICE:
		case NETHERRACK:
		case ENDER_STONE:
		case MYCEL:
		case PRISMARINE:
		case GRASS_PATH:
		case BEDROCK:
			return false;
		}
//		return isType(block, Material.STEP, Material.WOOD_STEP, 
//				 			 Material.GLASS, Material.THIN_GLASS,
//							 Material.SNOW, Material.CARPET, Material.SIGN, 
//							 Material.WOOD_DOOR, Material.TRAP_DOOR, 
//							 Material.STAINED_GLASS, Material.STAINED_GLASS_PANE,
//							 Material.FENCE, Material.FENCE_GATE,
//							 Material.STONE_PLATE, Material.WOOD_PLATE,
//							 Material.TRIPWIRE, Material.TRIPWIRE_HOOK,
//							 Material.IRON_DOOR_BLOCK, Material.IRON_FENCE);
	}
	
	public final boolean isNonstackableBlock(int x, int y, int z) {
		return isNonstackableBlock(getActualBlock(x, y, z));
	}
	
	private boolean isColorable(Material material) {
		switch (material) {
		default:
			return false;
		case STAINED_CLAY:
		case STAINED_GLASS:
		case STAINED_GLASS_PANE:
		case WOOL:
		case CARPET:
		case CONCRETE:
		case CONCRETE_POWDER:
			return true;
		}
	}
	
	public void setBlockTypeAndColor(int x, int y, int z, Material material, DyeColor color) {
		BlockState state = getActualBlock(x, y, z).getState();
		state.setType(material);
		if (isColorable(material)) {
			MaterialData data = state.getData();
			if (data instanceof Colorable)
				((Colorable)state.getData()).setColor(color);
			else
				BlackMagic.setBlockStateColor(state, color); //BUKKIT: none of the newly colorable blocks materials are colorable
			state.update(true, doPhysics);
		}
	}
	
	public void setBlockIfTypeThenColor(int x, int y, int z, Material material, DyeColor color) {
		BlockState state = getActualBlock(x, y, z).getState();
		if (state.getType() == material && isColorable(material)) {
			MaterialData data = state.getData();
			if (data instanceof Colorable)
				((Colorable)state.getData()).setColor(color);
			else
				BlackMagic.setBlockStateColor(state, color); //BUKKIT: none of the newly colorable blocks materials are colorable
			state.update(true, doPhysics);
		}
	}
	
	public void setBlocksTypeAndColor(int x, int y1, int y2, int z, Material material, DyeColor color) {
		if (isColorable(material))
			for (int y = y1; y < y2; y++)
				setBlockTypeAndColor(x, y, z, material, color);
		else
			setBlocks(x, y1, y2, z, material);
	}
	
	public void setBlocksTypeAndColor(int x1, int x2, int y, int z1, int z2, Material material, DyeColor color) {
		if (isColorable(material))
			for (int x = x1; x < x2; x++) {
				for (int z = z1; z < z2; z++) {
					setBlockTypeAndColor(x, y, z, material, color);
				}
			}
		else
			setBlocks(x1, x2, y, z1, z2, material);
	}
	
	public void setBlocksTypeAndColor(int x1, int x2, int y1, int y2, int z1, int z2, Material material, DyeColor color) {
		if (isColorable(material))
			for (int x = x1; x < x2; x++) {
				for (int y = y1; y < y2; y++) {
					for (int z = z1; z < z2; z++) {
						setBlockTypeAndColor(x, y, z, material, color);
					}
				}
			}
		else
			setBlocks(x1, x2, y1, y2, z1, z2, material);
	}
	
	public void setBlockTypeAndDirection(int x, int y, int z, Material material, BlockFace facing) {
		BlockState state = getActualBlock(x, y, z).getState();
		state.setType(material);
		MaterialData data = state.getData();
		if (data instanceof Directional)
			((Directional)state.getData()).setFacingDirection(facing);
		state.update(true, doPhysics);
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
		state.update(true, doPhysics);
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
		setBlockTypeAndColor(x, y, z, Material.STAINED_CLAY, color);
	}
	
	public final void setClay(int x, int y1, int y2, int z, DyeColor color) {
		setBlocksTypeAndColor(x, x + 1, y1, y2, z, z + 1, Material.STAINED_CLAY, color);
	}
	
	public final void setClay(int x1, int x2, int y, int z1, int z2, DyeColor color) {
		setBlocksTypeAndColor(x1, x2, y, y + 1, z1, z2, Material.STAINED_CLAY, color);
	}
	
	public final void setClay(int x1, int x2, int y1, int y2, int z1, int z2, DyeColor color) {
		setBlocksTypeAndColor(x1, x2, y1, y2, z1, z2, Material.STAINED_CLAY, color);
	}
	
	public final void setClayWalls(int x1, int x2, int y1, int y2, int z1, int z2, DyeColor color) {
		setBlocksTypeAndColor(x1, x2, y1, y2, z1, z1 + 1, Material.STAINED_CLAY, color);
		setBlocksTypeAndColor(x1, x2, y1, y2, z2 - 1, z2, Material.STAINED_CLAY, color);
		setBlocksTypeAndColor(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, Material.STAINED_CLAY, color);
		setBlocksTypeAndColor(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, Material.STAINED_CLAY, color);
	}
	
	public final void camoClay(int x1, int x2, int y1, int y2, int z1, int z2, Odds odds, ColorSet colors) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					setBlockIfTypeThenColor(x, y, z, Material.STAINED_CLAY, odds.getRandomColor(colors));
				}
			}
		}
	}
	
	public final void setGlass(int x, int y, int z, DyeColor color) {
		setBlockTypeAndColor(x, y, z, Material.STAINED_GLASS, color);
	}
	
	public final void setGlass(int x1, int x2, int y1, int y2, int z1, int z2, DyeColor color) {
		setBlocksTypeAndColor(x1, x2, y1, y2, z1, z2, Material.STAINED_GLASS, color);
	}
	
	public final void setGlassWalls(int x1, int x2, int y1, int y2, int z1, int z2, DyeColor color) {
		setBlocksTypeAndColor(x1, x2, y1, y2, z1, z1 + 1, Material.STAINED_GLASS, color);
		setBlocksTypeAndColor(x1, x2, y1, y2, z2 - 1, z2, Material.STAINED_GLASS, color);
		setBlocksTypeAndColor(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, Material.STAINED_GLASS, color);
		setBlocksTypeAndColor(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, Material.STAINED_GLASS, color);
	}
	
	public final void setThinGlass(int x, int y, int z, DyeColor color) {
		setBlockTypeAndColor(x, y, z, Material.STAINED_GLASS_PANE, color);
	}
	
	public final void setThinGlass(int x1, int x2, int y1, int y2, int z1, int z2, DyeColor color) {
		setBlocksTypeAndColor(x1, x2, y1, y2, z1, z2, Material.STAINED_GLASS_PANE, color);
	}
	
	public final void setThinGlassWalls(int x1, int x2, int y1, int y2, int z1, int z2, DyeColor color) {
		setBlocksTypeAndColor(x1, x2, y1, y2, z1, z1 + 1, Material.STAINED_GLASS_PANE, color);
		setBlocksTypeAndColor(x1, x2, y1, y2, z2 - 1, z2, Material.STAINED_GLASS_PANE, color);
		setBlocksTypeAndColor(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, Material.STAINED_GLASS_PANE, color);
		setBlocksTypeAndColor(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, Material.STAINED_GLASS_PANE, color);
	}
	
	public final void setVines(int x, int y1, int y2, int z, BlockFace... faces) {
		Vine data = new Vine(faces);
		for (int y = y1; y < y2; y++) {
			Block block = getActualBlock(x, y, z);
			if (block.getType() == Material.VINE) {
				BlockState state = block.getState();
				Vine vines = (Vine)(state.getData());
				for (BlockFace face: faces)
					vines.putOnFace(face);
				state.update();
			} else
				setBlock(getActualBlock(x, y, z), Material.VINE, data);
		}
	}
	
	public final void setSlab(int x, int y, int z, Material material, boolean inverted) {
		Step data = new Step(material);
		data.setInverted(inverted);
		setBlock(x, y, z, Material.STEP, data);
	}
	
	public final void setSlabs(int x1, int x2, int y, int z1, int z2, Material material, boolean inverted) {
		Step data = new Step(material);
		data.setInverted(inverted);
		setBlocks(x1, x2, y, z1, z2, Material.STEP, data);
	}
	
	public final void setSlabs(int x1, int x2, int y1, int y2, int z1, int z2, Material material, boolean inverted) {
		Step data = new Step(material);
		data.setInverted(inverted);
		setBlocks(x1, x2, y1, y2, z1, z2, Material.STEP, data);
	}
	
	public final void setSlab(int x, int y, int z, TreeSpecies species, boolean inverted) {
		WoodenStep data = new WoodenStep(species);
		data.setInverted(inverted);
		setBlock(x, y, z, Material.STEP, data);
	}
	
	public final void setSlabs(int x1, int x2, int y, int z1, int z2, TreeSpecies species, boolean inverted) {
		WoodenStep data = new WoodenStep(species);
		data.setInverted(inverted);
		setBlocks(x1, x2, y, z1, z2, Material.WOOD_STEP, data);
	}
	
	public final void setSlabs(int x1, int x2, int y1, int y2, int z1, int z2, TreeSpecies species, boolean inverted) {
		WoodenStep data = new WoodenStep(species);
		data.setInverted(inverted);
		setBlocks(x1, x2, y1, y2, z1, z2, Material.WOOD_STEP, data);
	}
	
	public final void setWood(int x, int y, int z, TreeSpecies species) {
		Tree data = new Tree(species);
		setBlock(x, y, z, Material.WOOD, data);
	}
	
	public final void setLog(int x, int y, int z, Material material, TreeSpecies species, BlockFace facing) {
		Tree data = new Tree(species, facing);
		setBlock(x, y, z, material, data);
	}
	
	public final void setLogs(int x, int y1, int y2, int z, Material material, TreeSpecies species, BlockFace facing) {
		Tree data = new Tree(species, facing);
		setBlocks(x, x + 1, y1, y2, z, z + 1, material, data);
	}
	
	public final void setLogs(int x1, int x2, int y1, int y2, int z1, int z2, Material material, TreeSpecies species, BlockFace facing) {
		Tree data = new Tree(species, facing);
		setBlocks(x1, x2, y1, y2, z1, z2, material, data);
	}
	
	public final void setLeave(int x, int y, int z, Material material, TreeSpecies species) {
		Leaves data = new Leaves(species);
		setBlock(x, y, z, material, data);
	}
	
	public final void setLeaves(int x, int y1, int y2, int z, Material material, TreeSpecies species) {
		Leaves data = new Leaves(species);
		setBlocks(x, x + 1, y1, y2, z, z + 1, material, data);
	}
	
	public final void setLeaves(int x1, int x2, int y1, int y2, int z1, int z2, Material material, TreeSpecies species) {
		Leaves data = new Leaves(species);
		setBlocks(x1, x2, y1, y2, z1, z2, material, data);
	}
	
	public final void drawCrane(DataContext context, Odds odds, int x, int y, int z) {
		
		// vertical bit
		setBlocks(x, y, y + 8, z, Material.IRON_FENCE);
		setBlocks(x - 1, y, y + 8, z, Material.IRON_FENCE); // 1.9 shows iron fences very thin now
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
	
	public void setDoor(int x, int y, int z, Material material, BadMagic.Door direction) {
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
		BlackMagic.setBlockType(getActualBlock(x, y    , z), material, orentation, true, false);
		BlackMagic.setBlockType(getActualBlock(x, y + 1, z), material, hinge, true, true);
	}

	public final void setWoodenDoor(int x, int y, int z, BadMagic.Door direction) {
		setDoor(x, y, z, Material.WOODEN_DOOR, direction);
	}
	
	public final void setIronDoor(int x, int y, int z, BadMagic.Door direction) {
		setDoor(x, y, z, Material.IRON_DOOR_BLOCK, direction);
	}

	public final void setTrapDoor(int x, int y, int z, BadMagic.TrapDoor direction) {
		BlackMagic.setBlock(this, x, y, z, Material.TRAP_DOOR, direction.getData());
	}

	public final void setStoneSlab(int x, int y, int z, BadMagic.StoneSlab direction) {
		BlackMagic.setBlock(this, x, y, z, Material.STEP, direction.getData());
	}

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
				Ladder data = new Ladder();
				data.setFacingDirection(direction);
				setBlock(x, y, z, Material.LADDER, data);
			}
		}
	}

	public final void setStair(int x, int y, int z, Material material, BadMagic.Stair direction) {
		BlackMagic.setBlock(this, x, y, z, material, direction.getData());
	}

	public final void setStairs(int x1, int x2, int y, int z1, int z2, Material material, BadMagic.Stair direction) {
		for (int x = x1; x < x2; x++)
			for (int z = z1; z < z2; z++)
				setStair(x, y, z, material, direction);
	}
	
	public static final Material filterStairMaterial(Material material) {
		switch (material) {
		case BRICK:
		case BRICK_STAIRS:
			return Material.BRICK_STAIRS;
		case COBBLESTONE:
		case MOSSY_COBBLESTONE:
		case COBBLESTONE_STAIRS:
			return Material.COBBLESTONE_STAIRS;
		case NETHERRACK:
		case NETHER_BRICK:
		case NETHER_BRICK_STAIRS:
			return Material.NETHER_BRICK_STAIRS;
		case PURPUR_BLOCK:
		case PURPUR_SLAB:
		case PURPUR_DOUBLE_SLAB:
		case PURPUR_STAIRS:
			return Material.PURPUR_STAIRS;
		case RED_SANDSTONE:
		case RED_SANDSTONE_STAIRS:
			return Material.RED_SANDSTONE_STAIRS;
		case SAND:
		case SANDSTONE:
		case SANDSTONE_STAIRS:
			return Material.SANDSTONE_STAIRS;
		case SMOOTH_BRICK:
		case SMOOTH_STAIRS:
			return Material.SMOOTH_STAIRS;
		case WOOL: // it is white too!
		case QUARTZ_BLOCK:
		case QUARTZ_STAIRS:
			return Material.QUARTZ_STAIRS;
		default:
//		case WOOD:
			return Material.WOOD_STAIRS;
		}
	}

	public final void setVine(int x, int y, int z, BadMagic.Vine direction) {
		BlackMagic.setBlock(this, x, y, z, Material.VINE, direction.getData());
	}

	public final void setTorch(int x, int y, int z, Material material, BadMagic.Torch direction) {
		BlackMagic.setBlock(this, x, y, z, material, direction.getData());
	}
	
	public final void setFurnace(int x, int y, int z, BadMagic.General direction) {
		BlackMagic.setBlock(this, x, y, z, Material.FURNACE, direction.getData());
	}

	public final void setChest(CityWorldGenerator generator, int x, int y, int z, BadMagic.General direction, Odds odds, LootProvider lootProvider, LootLocation lootLocation) {
		Block block = getActualBlock(x, y, z);
		if (BlackMagic.setBlockType(block, Material.CHEST, direction.getData())) {
			if (block.getType() == Material.CHEST) {
				lootProvider.setLoot(generator, odds, world.getName(), lootLocation, block);
			}
		}
	}
	
//	public final void setVine(int x, int y, int z, BlockFace ... facing) {
//		setBlock(x, y, z, new Vine(facing));
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
		
		
		Block block = getActualBlock(x, y, z, new Chest(facing));
		if (isType(block, Material.CHEST))
			lootProvider.setLoot(generator, odds, world.getName(), lootLocation, block);
	}
	
	public final void setDoubleChest(CityWorldGenerator generator, int x, int y, int z, BadMagic.General direction, Odds odds, LootProvider lootProvider, LootLocation lootLocation) {
		switch (direction) {
		case EAST:
		case WEST:
			setChest(generator, x, y, z, direction, odds, lootProvider, lootLocation);
			setChest(generator, x, y, z + 1, direction, odds, lootProvider, lootLocation);
			break;
		case NORTH:
		case SOUTH:
			setChest(generator, x, y, z, direction, odds, lootProvider, lootLocation);
			setChest(generator, x + 1, y, z, direction, odds, lootProvider, lootLocation);
			break;
		}
	}

	public final void setWallSign(int x, int y, int z, BadMagic.General direction, String[] text) {
		Block block = getActualBlock(x, y, z);
		if (BlackMagic.setBlockType(block, Material.WALL_SIGN, direction.getData())) {
			if (block.getType() == Material.WALL_SIGN) {
				Sign sign = (Sign) block.getState();
				for (int i = 0; i < text.length && i < 4; i++) 
					sign.setLine(i, text[i]);
				sign.update(true);
			}
		}
	}

// WE SHOULD BE USING THIS, INSTEAD OF setWallSign
//	public final void setSignPost(int x, int y, int z, BlockFace direction, String ... text) {
//		Block block = getActualBlock(x, y, z);
//		block.setType(Material.SIGN_POST);
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
//	}
	
	public final void setBed(int x, int y, int z, Facing direction) {
		switch (direction) {
		case EAST:
			BlackMagic.setBlockType(getActualBlock(x, y, z), Material.BED_BLOCK, (byte)(0x1 + 0x8), true, false);
			BlackMagic.setBlockType(getActualBlock(x + 1, y, z), Material.BED_BLOCK, (byte)(0x1), true, true);
			break;
		case SOUTH:
			BlackMagic.setBlockType(getActualBlock(x, y, z), Material.BED_BLOCK, (byte)(0x2 + 0x8), true, false);
			BlackMagic.setBlockType(getActualBlock(x, y, z + 1), Material.BED_BLOCK, (byte)(0x2), true, true);
			break;
		case WEST:
			BlackMagic.setBlockType(getActualBlock(x, y, z), Material.BED_BLOCK, (byte)(0x3 + 0x8), true, false);
			BlackMagic.setBlockType(getActualBlock(x + 1, y, z), Material.BED_BLOCK, (byte)(0x3), true, true);
			break;
		case NORTH:
			BlackMagic.setBlockType(getActualBlock(x, y, z), Material.BED_BLOCK, (byte)(0x0 + 0x8), true, false);
			BlackMagic.setBlockType(getActualBlock(x, y, z + 1), Material.BED_BLOCK, (byte)(0x0), true, true);
			break;
		}
	}
	
}
