package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.CityWorldGenerator;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.material.MaterialData;

public final class InitialBlocks extends AbstractBlocks {
	public ChunkData chunkData;
	
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
	
	public boolean isType(int x, int y, int z, Material ... materials) {
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
	
	public Material getBlock(int x, int y, int z) {
		return chunkData.getType(x, y, z);
	}
	
	@Override
	public void setBlock(int x, int y, int z, Material material) {
		chunkData.setBlock(x, y, z, material);
	}

	public void setBlockIfEmpty(int x, int y, int z, Material material) {
		if (isEmpty(x, y, z) && !isEmpty(x, y - 1, z))
			chunkData.setBlock(x, y, z, material);
	}
	
	@Override
	public void clearBlock(int x, int y, int z) {
		chunkData.setBlock(x, y, z, Material.AIR);
	}

	//================ x, y1, y2, z
	@Override
	public void setBlocks(int x, int y1, int y2, int z, Material material) {
		for (int y = y1; y < y2; y++)
			setBlock(x, y, z, material);
	}
	
	//================ x1, x2, y1, y2, z1, z2
	@Override
	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				for (int y = y1; y < y2; y++)
					setBlock(x, y, z, material);
			}
		}
	}

	//================ x1, x2, y, z1, z2
	@Override
	public void setBlocks(int x1, int x2, int y, int z1, int z2, Material material) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				setBlock(x, y, z, material);
			}
		}
	}

	//================ Walls
	@Override
	public void setWalls(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
		setBlocks(x1, x2, y1, y2, z1, z1 + 1, material);
		setBlocks(x1, x2, y1, y2, z2 - 1, z2, material);
		setBlocks(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, material);
		setBlocks(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, material);
	}
	
	public void setWalls(int x1, int x2, int y1, int y2, int z1, int z2, MaterialData material) {
		setBlocks(x1, x2, y1, y2, z1, z1 + 1, material);
		setBlocks(x1, x2, y1, y2, z2 - 1, z2, material);
		setBlocks(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, material);
		setBlocks(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, material);
	}
	
	@Override
	public void setBlock(int x, int y, int z, MaterialData material) {
		chunkData.setBlock(x, y, z, material);
	}
	
	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, MaterialData material) {
		chunkData.setRegion(x1, y1, z1, x2, y2, z2, material);
	}
	
//	@Override
//	public void setBlockTypeAndColor(int x, int y, int z, Material material, DyeColor color) {
//		BlackMagic.setBlockTypeAndColor(chunkData, x, y, z, material, color);
//	}
//	
//	@Override
//	public final void setStair(int x, int y, int z, Material material, BadMagic.Stair direction) {
//		BlackMagic.setBlockTypeAndData(chunkData, x, y, z, material, direction.getData());
//	}
	
	//================ Layers
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
	public final boolean setEmptyBlock(int x, int y, int z, Material material) {
		if (isEmpty(x, y, z)) {
			setBlock(x, y, z, material);
			return true;
		} else
			return false;
	}

	@Override
	public final void setEmptyBlocks(int x1, int x2, int y, int z1, int z2, Material material) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				if (isEmpty(x, y, z))
					setBlock(x, y, z, material);
			}
		}
	}
	
}
