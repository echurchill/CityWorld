package me.daddychurchill.CityWorld.Support;

import org.bukkit.Axis;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Slab.Type;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import me.daddychurchill.CityWorld.CityWorldGenerator;

public final class InitialBlocks extends AbstractBlocks {
	public final ChunkData chunkData;

	public InitialBlocks(CityWorldGenerator aGenerator, ChunkData chunk, int sectionX, int sectionZ) {
		super(aGenerator);

		this.sectionX = sectionX;
		this.sectionZ = sectionZ;
		this.chunkData = chunk;
	}

//	public short getBlockType(int x, int y, int z) {
//		return chunkData.
//		return getBlock(x, y, z);
//	}

	public boolean isType(int x, int y, int z, Material material) {
		return chunkData.getType(x, y, z).equals(material);
	}

	public boolean isType(int x, int y, int z, Material... materials) {
		Material block = chunkData.getType(x, y, z);
		for (Material material : materials)
			if (block.equals(material))
				return true;
		return false;
	}

	@Override
	public boolean isEmpty(int x, int y, int z) {
		return chunkData.getType(x, y, z).equals(Material.AIR);
	}

	@Override
	public void setAtmosphereBlock(int x, int y, int z, Material material) {
		chunkData.setBlock(x, y, z, material);
		BlockData blockData = null;

		// West
		if (x > 0) {
			try {
				blockData = chunkData.getBlockData(x - 1, y, z);
				if (blockData instanceof MultipleFacing) {
					((MultipleFacing) blockData).setFace(BlockFace.EAST, false);
					chunkData.setBlock(x - 1, y, z, blockData);
				}
			} catch (Exception ignored) {

			}
		}
		// East
		if (x < 15) {
			try {
				blockData = chunkData.getBlockData(x + 1, y, z);
				if (blockData instanceof MultipleFacing) {
					((MultipleFacing) blockData).setFace(BlockFace.WEST, false);
					chunkData.setBlock(x + 1, y, z, blockData);
				}
			} catch (Exception ignored) {

			}
		}
		// North
		if (z > 0) {
			try {
				blockData = chunkData.getBlockData(x, y, z - 1);
				if (blockData instanceof MultipleFacing) {
					((MultipleFacing) blockData).setFace(BlockFace.SOUTH, false);
					chunkData.setBlock(x, y, z - 1, blockData);
				}
			} catch (Exception ignored) {

			}
		}
		// South
		if (z < 15) {
			try {
				blockData = chunkData.getBlockData(x, y, z + 1);
				if (blockData instanceof MultipleFacing) {
					((MultipleFacing) blockData).setFace(BlockFace.NORTH, false);
					chunkData.setBlock(x, y, z + 1, blockData);
				}
			} catch (Exception ignored) {

			}
		}
	}

	public Material getBlock(int x, int y, int z) {
		return chunkData.getType(x, y, z);
	}

	@Override
	public void setBlock(int x, int y, int z, Material material) {
		chunkData.setBlock(x, y, z, material);
	}

	@Override
	public final void setBlock(int x, int y, int z, Material material, Type type) {
		BlockData blockData = material.createBlockData();
		try {
			if (blockData instanceof Slab)
				((Slab) blockData).setType(type);
		} finally {
			chunkData.setBlock(x, y, z, blockData);
		}
	}

	@Override
	public final void setBlock(int x, int y, int z, Material material, BlockFace facing) {
		BlockData blockData = material.createBlockData();
		try {
			if (blockData instanceof Directional) {
				((Directional) blockData).setFacing(facing);
			} else if (blockData instanceof MultipleFacing) {
				((MultipleFacing) blockData).setFace(facing, true);
			} else if (blockData instanceof Orientable) {
				switch (facing) {
				case NORTH:
				case SOUTH:
					((Orientable) blockData).setAxis(Axis.Z);
					break;
				case EAST:
				case WEST:
					((Orientable) blockData).setAxis(Axis.X);
					break;
				default:
					((Orientable) blockData).setAxis(Axis.Y);
				}
			}
		} finally {
			chunkData.setBlock(x, y, z, blockData);
		}
	}

	@Override
	public void setBlock(int x, int y, int z, Material material, BlockFace... facing) {
		BlockData blockData = material.createBlockData();
		if (blockData instanceof MultipleFacing) {
			for (BlockFace face : facing) {
				((MultipleFacing) blockData).setFace(face, true);
			}
		}
		chunkData.setBlock(x, y, z, blockData);
	}

	@Override
	public void setBlockIfEmpty(int x, int y, int z, Material material) {
		if (isEmpty(x, y, z) && !isEmpty(x, y - 1, z))
			chunkData.setBlock(x, y, z, material);
	}

	@Override
	public void clearBlock(int x, int y, int z) {
		chunkData.setBlock(x, y, z, Material.AIR);
	}

	// ================ Walls
	@Override
	public void setWalls(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
		setBlocks(x1, x2, y1, y2, z1, z1 + 1, material);
		setBlocks(x1, x2, y1, y2, z2 - 1, z2, material);
		setBlocks(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, material);
		setBlocks(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, material);
	}

//	public void setWalls(int x1, int x2, int y1, int y2, int z1, int z2, MaterialData material) {
//		setBlocks(x1, x2, y1, y2, z1, z1 + 1, material);
//		setBlocks(x1, x2, y1, y2, z2 - 1, z2, material);
//		setBlocks(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, material);
//		setBlocks(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, material);
//	}
//	
//	@Override
//	public void setBlock(int x, int y, int z, MaterialData material) {
//		chunkData.setBlock(x, y, z, material);
//	}
//	
//	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, MaterialData material) {
//		chunkData.setRegion(x1, y1, z1, x2, y2, z2, material);
//	}
//	
//	@Override
//	public void setBlockTypeAndColor(int x, int y, int z, Material material, DyeColor color) {
//		BlackMagic.setBlockTypeAndColor(chunkData, x, y, z, material, color);
//	}
//	
//	@Override
//	public final void setStair(int x, int y, int z, Material material, BadMagic.Stair direction) {
//		BlackMagic.setBlockTypeAndData(chunkData, x, y, z, material, direction.getData());
//	}

	// ================ Layers
	@Override
	public int setLayer(int blocky, Material material) {
		setBlocks(0, width, blocky, blocky + 1, 0, width, material);
		return blocky + 1;
	}

	@Override
	public int setLayer(int blocky, int height, Material material) {
		setBlocks(0, width, blocky, blocky + height, 0, width, material);
		return blocky + height;
	}

	@Override
	public int setLayer(int blocky, int height, int inset, Material material) {
		setBlocks(inset, width - inset, blocky, blocky + height, inset, width - inset, material);
		return blocky + height;
	}

	@Override
	public void setDoor(int x, int y, int z, Material material, BlockFace facing) {
		clearBlock(x, y, z);
		clearBlock(x, y + 1, z);

		BlockData dataBottom = material.createBlockData();
		BlockData dataTop = material.createBlockData();

		facing = fixFacing(facing);
		facing = facing.getOppositeFace();

		try {
			if (dataBottom instanceof Directional)
				((Directional) dataBottom).setFacing(facing);
			if (dataTop instanceof Directional)
				((Directional) dataTop).setFacing(facing);

			if (dataBottom instanceof Bisected)
				((Bisected) dataBottom).setHalf(Bisected.Half.BOTTOM);
			if (dataTop instanceof Bisected)
				((Bisected) dataTop).setHalf(Bisected.Half.TOP);
		} finally {
			chunkData.setBlock(x, y, z, dataBottom);
			chunkData.setBlock(x, y + 1, z, dataTop);
		}
	}
}
