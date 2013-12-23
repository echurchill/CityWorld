package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.CityWorld;

import org.bukkit.Material;
import org.bukkit.block.Block;

@SuppressWarnings("deprecation")
public abstract class BlackMagic {

	public static final Material getMaterial(byte id) {
		return Material.getMaterial(id);
	}
	
	public static final Material getMaterial(int id) {
		return Material.getMaterial(id);
	}
	
	public static final byte getMaterialId(Material material) {
		return (byte) material.getId();
	}
	
	public static final byte getMaterialId(Block block) {
		if (block == null) {
			CityWorld.log.info("getMaterialId(Block block): block is null");
			return noData;
		} else
			return (byte) block.getTypeId();
	}
	
	public static final byte getMaterialData(Block block) {
		if (block == null) {
			CityWorld.log.info("getMaterialData(Block block): block is null");
			return noData;
		} else
			return (byte) block.getData();
	}
	
	public static final boolean setBlockType(Block block, Material material, boolean doPhysics) {
//		return block.setTypeId(getMaterialId(material), doPhysics);
		if (block == null) {
			CityWorld.log.info("setBlockType(Block block, Material material, boolean doPhysics): block is null");
			return false;
		} else
			return block.setTypeIdAndData(getMaterialId(material), noData, doPhysics);
	}

	public static final boolean setBlockType(Block block, byte typeId, boolean doPhysics) {
//		return block.setTypeId(typeId, doPhysics);
		if (block == null) {
			CityWorld.log.info("setBlockType(Block block, byte typeId, boolean doPhysics): block is null");
			return false;
		} else
			return block.setTypeIdAndData(typeId & 0xFF, noData, doPhysics);
	}

	public static final boolean setBlockType(Block block, Material material, byte data, boolean doPhysics) {
//		CityWorld.log.info("BlackMagic...");
		if (block == null) {
			CityWorld.log.info("setBlockType(Block block, Material material, byte data, boolean doPhysics): block is null");
			return false;
		} else
			return block.setTypeIdAndData(getMaterialId(material), data, doPhysics);
	}

	public static final boolean setBlockType(Block block, byte typeId, byte data, boolean doPhysics) {
		if (block == null) {
			CityWorld.log.info("setBlockType(Block block, byte typeId, byte data, boolean doPhysics): block is null");
			return false;
		} else
			return block.setTypeIdAndData(typeId & 0xFF, data, doPhysics);
	}
	
	public static final boolean setBlockType(Block block, int typeId, int data, boolean doPhysics) {
		if (block == null) {
			CityWorld.log.info("setBlockType(Block block, int typeId, int data, boolean doPhysics): block is null");
			return false;
		} else
			return block.setTypeIdAndData((byte) (typeId & 0xFF), (byte) data, doPhysics);
	}
	
	public static final byte noData = (byte) 0;
		
//	public static final byte airId = getMaterialId(Material.AIR);
//	public static final byte bedrockId = getMaterialId(Material.BEDROCK);
//	public static final byte stoneId = getMaterialId(Material.STONE);
//	public static final byte gravelId = getMaterialId(Material.GRAVEL);
//	public static final byte dirtId = getMaterialId(Material.DIRT);
//	public static final byte grassId = getMaterialId(Material.GRASS);
//	public static final byte glassId = getMaterialId(Material.GLASS);
//
//	public static final byte coalId = getMaterialId(Material.COAL_ORE); //TODO: change these to coalOreId
//	public static final byte ironId = getMaterialId(Material.IRON_ORE);
//	public static final byte goldId = getMaterialId(Material.GOLD_ORE);
//	public static final byte lapisId = getMaterialId(Material.LAPIS_ORE);
//	public static final byte redstoneId = getMaterialId(Material.REDSTONE_ORE);
//	public static final byte diamondId = getMaterialId(Material.DIAMOND_ORE);
//	public static final byte emeraldId = getMaterialId(Material.EMERALD_ORE);
//	
//	public static final byte stepStoneId = getMaterialId(Material.STEP);
//	public static final byte stepWoodId = getMaterialId(Material.WOOD_STEP);
//	public static final byte sandId = getMaterialId(Material.SAND);
//	public static final byte sandstoneId = getMaterialId(Material.SANDSTONE); //TODO: change this to sandStone
//	public static final byte snowBlockId = getMaterialId(Material.SNOW_BLOCK);
//	public static final byte snowCoverId = getMaterialId(Material.SNOW);
//	public static final byte iceId = getMaterialId(Material.ICE); // the fluid type
//	
//	public static final byte fluidWaterId = getMaterialId(Material.WATER); // the fluid type
//	public static final byte fluidLavaId = getMaterialId(Material.LAVA); // the fluid type
//	public static final byte stillWaterId = getMaterialId(Material.STATIONARY_WATER); // the fluid type
//	public static final byte stillLavaId = getMaterialId(Material.STATIONARY_LAVA); // the fluid type
//	public static final byte plateStoneId = getMaterialId(Material.STONE_PLATE);
//	public static final byte plateWoodId = getMaterialId(Material.WOOD_PLATE);
//	
//	public static final byte endstoneId = getMaterialId(Material.ENDER_STONE);
//	public static final byte netherrackId = getMaterialId(Material.NETHERRACK);
//	public static final byte soulsandId = getMaterialId(Material.SOUL_SAND);
//	public static final byte glowstoneId = getMaterialId(Material.GLOWSTONE);
	
}
