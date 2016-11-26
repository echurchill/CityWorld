package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.Factories.MaterialFactory;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.material.MaterialData;

@SuppressWarnings("deprecation")
public abstract class BlackMagic {

	public static final int maxSnowLevel = 7;
	public static final int maxCauldronLevel = 3;
	
	public static final Material getMaterial(int id) {
		return Material.getMaterial(id);
	}
	
	public static final int getMaterialIdAsInt(Material material) {
		return material.getId();
	}
	
	public static final short getMaterialId(Material material) {
		return (short) material.getId();
	}
	
	public static final short getMaterialId(Block block) {
		return (short) block.getTypeId();
	}

	public static final short getMaterialData(Block block) {
		return (short) block.getData();
	}

	public static final short getMaterialData(MaterialData data) {
		return data.getData();
	}
	
	public static final void setBlockType(Block block, int typeId) {
		block.setTypeId(typeId);
	}
	
	public static final void setBlockStateColor(BlockState state, DyeColor color) {
		state.setRawData(color.getWoolData());
	}
	
	public static final boolean setBlockType(Block block, int typeId, int rawdata) {
		BlockState state = block.getState();
		state.setTypeId(typeId);
		if (rawdata != 0)
			state.setRawData((byte) (rawdata & 0xff));
		return state.update(true);
	}
	
	public static final boolean setBlockType(Block block, Material material) {
		return setBlockType(block, material, 0);
	}
	
	public static final boolean setBlockType(Block block, Material material, int rawdata) {
		return setBlockType(block, material, rawdata, true, true);
	}
	
	public static final boolean setBlockType(Block block, Material material, int rawdata, boolean update, boolean physics) {
		BlockState state = block.getState();
		state.setType(material);
		if (rawdata != 0)
			state.setRawData((byte) (rawdata & 0xff));
		return state.update(update, physics);
	}
	
	public static final boolean setBlock(SupportBlocks chunk, int x, int y, int z, Material material, int data) {
		return setBlockType(chunk.getActualBlock(x, y, z), material, data);
	}
	
	public static final void setBlockTypeAndData(ChunkData chunk, int x, int y, int z, Material material, byte data) {
		chunk.setBlock(x, y, z, material.getId(), data);
	}
	
	public static final void setBlockTypeAndColor(ChunkData chunk, int x, int y, int z, Material material, DyeColor color) {
		setBlockTypeAndData(chunk, x, y, z, material, color.getDyeData());
	}
	
	public static final void setBlock(InitialBlocks chunk, int x, int y, int z, Material material, int data) {
		chunk.chunkData.setBlock(x, y, z, material.getId(), (byte)data);
	}
	
	public static final void setBlocks(SupportBlocks chunk, int x, int y1, int y2, int z, Material material, int data) {
		for (int y = y1; y < y2; y++) {
			setBlockType(chunk.getActualBlock(x, y, z), material, data);
		}
	}
	
	public static final void setBlocks(SupportBlocks chunk, int x, int y1, int y2, int z, int typeId, int data) {
		for (int y = y1; y < y2; y++) {
			setBlockType(chunk.getActualBlock(x, y, z), typeId, data);
		}
	}
	
	public static final void setBlocks(SupportBlocks chunk, int x1, int x2, int y, int z1, int z2, Material material, int data) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				setBlockType(chunk.getActualBlock(x, y, z), material, data);
			}
		}
	}
	
	public static final void setBlocks(SupportBlocks chunk, int x1, int x2, int y, int z1, int z2, int typeId, int data) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				setBlockType(chunk.getActualBlock(x, y, z), typeId, data);
			}
		}
	}
	
	public static final void setBlocks(SupportBlocks chunk, int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					setBlockType(chunk.getActualBlock(x, y, z), material);
				}
			}
		}
	}
	
	public static final void setBlocks(SupportBlocks chunk, int x1, int x2, int y1, int y2, int z1, int z2, Material material, int data) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					setBlockType(chunk.getActualBlock(x, y, z), material, data);
				}
			}
		}
	}
	
	public static final void setBlocks(SupportBlocks chunk, int x1, int x2, int y1, int y2, int z1, int z2, Block block) {
		Material material = block.getType();
		short data = block.getData();
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					setBlockType(chunk.getActualBlock(x, y, z), material, data);
				}
			}
		}
	}
	
	public static final void setBlocks(SupportBlocks chunk, int x1, int x2, int y1, int y2, int z1, int z2, int typeId, int data) {
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					setBlockType(chunk.getActualBlock(x, y, z), typeId, data);
				}
			}
		}
	}

	public static void setBlocks(SupportBlocks chunk, int x, int y1, int y2, int z, Material primary, Material secondary, MaterialFactory maker) {
//		setBlocks(x, y1, y2, z, BlackMagic.getMaterialId(primary), BlackMagic.getMaterialId(secondary), maker);
	}

	public static void setBlocks(SupportBlocks chunk, int x1, int x2, int y1, int y2, int z1, int z2, Material primary, Material secondary, MaterialFactory maker) {
//		setBlocks(x1, x2, y1, y2, z1, z2, BlackMagic.getMaterialId(primary), BlackMagic.getMaterialId(secondary), maker);
	}

	
	
//	public static final boolean setBlockType(Block block, Material material, boolean doPhysics) {
////		return block.setTypeId(getMaterialId(material), doPhysics);
//		if (block == null) {
//			CityWorld.log.info("setBlockType(Block block, Material material, boolean doPhysics): block is null");
//			return false;
//		} else
//			return block.setTypeIdAndData(getMaterialId(material), noData, doPhysics);
//	}
//
//	public static final boolean setBlockType(Block block, short typeId, boolean doPhysics) {
////		return block.setTypeId(typeId, doPhysics);
//		if (block == null) {
//			CityWorld.log.info("setBlockType(Block block, short typeId, boolean doPhysics): block is null");
//			return false;
//		} else
//			return block.setTypeIdAndData(typeId & 0xFF, noData, doPhysics);
//	}
//
//	public static final boolean setBlockType(Block block, Material material, short data, boolean doPhysics) {
////		CityWorld.log.info("BlackMagic...");
//		if (block == null) {
//			CityWorld.log.info("setBlockType(Block block, Material material, short data, boolean doPhysics): block is null");
//			return false;
//		} else
//			return block.setTypeIdAndData(getMaterialId(material), data, doPhysics);
//	}
//
//	public static final boolean setBlockType(Block block, short typeId, short data, boolean doPhysics) {
//		if (block == null) {
//			CityWorld.log.info("setBlockType(Block block, short typeId, short data, boolean doPhysics): block is null");
//			return false;
//		} else
//			return block.setTypeIdAndData(typeId & 0xFF, data, doPhysics);
//	}
//	
//	
//	public static final short noData = (short) 0;
//		
	
}
