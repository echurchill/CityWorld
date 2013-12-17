package me.daddychurchill.CityWorld.Support;

import java.util.Arrays;
import me.daddychurchill.CityWorld.WorldGenerator;

import org.bukkit.Material;

// new 1.2.3 block code is loosely based on Mike Primm's updated version of 
// DinnerBone's Moon generator from: https://github.com/mikeprimm/BukkitFullOfMoon

public class ByteChunk extends SupportChunk {
	public byte[][] blocks;
	
	public static final int bytesPerSection = chunksBlockWidth * chunksBlockWidth * chunksBlockWidth;
	
	public ByteChunk(WorldGenerator aGenerator, int aChunkX, int aChunkZ) {
		super(aGenerator);
		
		chunkX = aChunkX;
		chunkZ = aChunkZ;
		worldX = chunkX * width;
		worldZ = chunkZ * width;
		
		blocks = new byte[sectionsPerChunk][];
	}
	
	@Override
	public byte getBlockType(int x, int y, int z) {
		return getBlock(x, y, z);
	}
	
	public byte getBlock(int x, int y, int z) {
        if (blocks[y >> 4] == null)
        	return airId;
        else
        	return blocks[y >> 4][((y & 0xF) << 8) | (z << 4) | x];
	}
	
	@Override
	public void setBlock(int x, int y, int z, byte materialId) {
        if (blocks[y >> 4] == null) {
        	blocks[y >> 4] = new byte[bytesPerSection];
        }
        blocks[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = materialId;
	}
	
	public void setBlock(int x, int y, int z, Material material) {
		setBlock(x, y, z, getMaterialId(material));
	}
	
	public void setBlockIfAir(int x, int y, int z, byte materialId) {
		if (getBlock(x, y, z) == airId && getBlock(x, y - 1, z) != airId)
			setBlock(x, y, z, materialId);
	}
	
	public void setBlocks(int x, int y1, int y2, int z, byte materialId) {
		for (int y = y1; y < y2; y++)
			setBlock(x, y, z, materialId);
	}
	
	public void setBlocks(int x, int y1, int y2, int z, Material material) {
		setBlocks(x, y1, y2, z, getMaterialId(material));
	}
	
	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, byte materialId) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				for (int y = y1; y < y2; y++)
					setBlock(x, y, z, materialId);
			}
		}
	}
	
	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
		setBlocks(x1, x2, y1, y2, z1, z2, getMaterialId(material));
	}
	
	@Override
	public void setBlocks(int x1, int x2, int y, int z1, int z2, byte materialId) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				setBlock(x, y, z, materialId);
			}
		}
	}
	
	public void setBlocks(int x1, int x2, int y, int z1, int z2, Material material) {
		setBlocks(x1, x2, y, z1, z2, getMaterialId(material));
	}
	
	@Override
	public void clearBlock(int x, int y, int z) {
        if (blocks[y >> 4] != null) {
        	blocks[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = airId;
        }
	}

	@Override
	public void clearBlocks(int x, int y1, int y2, int z) {
		for (int y = y1; y < y2; y++)
			clearBlock(x, y, z);
	}

	@Override
	public void clearBlocks(int x1, int x2, int y1, int y2, int z1, int z2) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				for (int y = y1; y < y2; y++)
					clearBlock(x, y, z);
			}
		}
	}
	public void setWalls(int x1, int x2, int y1, int y2, int z1, int z2, byte materialId) {
		setBlocks(x1, x2, y1, y2, z1, z1 + 1, materialId);
		setBlocks(x1, x2, y1, y2, z2 - 1, z2, materialId);
		setBlocks(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, materialId);
		setBlocks(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, materialId);
	}
	
	public void setWalls(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
		setWalls(x1, x2, y1, y2, z1, z2, getMaterialId(material));
	}
	
	public boolean setEmptyBlock(int x, int y, int z, byte materialId) {
		if (getBlock(x, y, z) == airId) {
			setBlock(x, y, z, materialId);
			return true;
		} else
			return false;
	}
	
	public boolean setEmptyBlock(int x, int y, int z, Material material) {
		return setEmptyBlock(x, y, z, getMaterialId(material));
	}

	public void setEmptyBlocks(int x1, int x2, int y, int z1, int z2, byte materialId) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				if (getBlock(x, y, z) == airId)
					setBlock(x, y, z, materialId);
			}
		}
	}
	
	public void setEmptyBlocks(int x1, int x2, int y, int z1, int z2, Material material) {
		setEmptyBlocks(x1, x2, y, z1, z2, getMaterialId(material));
	}
	
	public int findLastEmptyAbove(int x, int y, int z) {
		int y1 = y;
		while (y1 < height - 1 && getBlock(x, y1 + 1, z) == airId) {
			y1++;
		}
		return y1;
	}
	
	public int findLastEmptyBelow(int x, int y, int z) {
		int y1 = y;
		while (y1 > 0 && getBlock(x, y1 - 1, z) == airId) {
			y1--;
		}
		return y1;
	}
	
	public void setBlocksAt(int y, byte materialId) {
		setBlocks(0, width, y, y + 1, 0, width, materialId);
	}
	
	public void setBlocksAt(int y, Material material) {
		setBlocks(0, width, y, y + 1, 0, width, getMaterialId(material));
	}
	
	public void setBlocksAt(int y1, int y2, byte materialId) {
		setBlocks(0, width, y1, y2, 0, width, materialId);
	}
	
	public void setBlocksAt(int y1, int y2, Material material) {
		setBlocks(0, width, y1, y2, 0, width, getMaterialId(material));
	}
	
	public void setAllBlocks(byte materialID) {
		// shortcut if we are simply clearing everything
		if (materialID == airId) {
			for (int c = 0; c < sectionsPerChunk; c++) {
				blocks[c] = null;
			}
		
		// fine.. do it the hard way
		} else {
			for (int c = 0; c < sectionsPerChunk; c++) {
				if (blocks[c] == null)
					blocks[c] = new byte[bytesPerSection];
				Arrays.fill(blocks[c], 0, bytesPerSection, materialID);
			}
		}	
	}

	public void setAllBlocks(Material material) {
		setAllBlocks(getMaterialId(material));
	}
	
	public void replaceBlocks(byte fromId, byte toId) {
		// if we are replacing air we might need to do this the hard way
		if (fromId == airId) {
			for (int c = 0; c < sectionsPerChunk; c++) {
				if (blocks[c] == null)
					blocks[c] = new byte[bytesPerSection];
				for (int i = 0; i < bytesPerSection; i++) {
					if (blocks[c][i] == fromId)
						blocks[c][i] = toId;
				}
			}
		
		// if not then take a short cut if we can
		} else {
			for (int c = 0; c < sectionsPerChunk; c++) {
				if (blocks[c] != null) {
					for (int i = 0; i < bytesPerSection; i++) {
						if (blocks[c][i] == fromId)
							blocks[c][i] = toId;
					}
				}
			}
		}
	}

	public void replaceBlocks(Material fromMaterial, Material toMaterial) {
		replaceBlocks(getMaterialId(fromMaterial), getMaterialId(toMaterial));
	}
	
	public int setLayer(int blocky, byte materialId) {
		setBlocks(0, width, blocky, blocky + 1, 0, width, materialId);
		return blocky + 1;
	}
	
	public int setLayer(int blocky, int height, byte materialId) {
		setBlocks(0, width, blocky, blocky + height, 0, width, materialId);
		return blocky + height;
	}
	
	public int setLayer(int blocky, int height, int inset, byte materialId) {
		setBlocks(inset, width - inset, blocky, blocky + height, inset, width - inset, materialId);
		return blocky + height;
	}
	
	public void setArcNorthWest(int inset, int y1, int y2, byte materialId, boolean fill) {
		setArcNorthWest(inset, y1, y2, materialId, materialId, null, fill);
	}

	public void setArcSouthWest(int inset, int y1, int y2, byte materialId, boolean fill) {
		setArcSouthWest(inset, y1, y2, materialId, materialId, null, fill);
	}

	public void setArcNorthEast(int inset, int y1, int y2, byte materialId, boolean fill) {
		setArcNorthEast(inset, y1, y2, materialId, materialId, null, fill);
	}
	
	public void setArcSouthEast(int inset, int y1, int y2, byte materialId, boolean fill) {
		setArcSouthEast(inset, y1, y2, materialId, materialId, null, fill);
	}

	public void setArcNorthWest(int inset, int y1, int y2, byte materialId, byte glassId, MaterialFactory maker) {
		setArcNorthWest(inset, y1, y2, materialId, glassId, maker, false);
	}
	
	public void setArcSouthWest(int inset, int y1, int y2, byte materialId, byte glassId, MaterialFactory maker) {
		setArcSouthWest(inset, y1, y2, materialId, glassId, maker, false);
	}
	
	public void setArcNorthEast(int inset, int y1, int y2, byte materialId, byte glassId, MaterialFactory maker) {
		setArcNorthEast(inset, y1, y2, materialId, glassId, maker, false);
	}
	
	public void setArcSouthEast(int inset, int y1, int y2, byte materialId, byte glassId, MaterialFactory maker) {
		setArcSouthEast(inset, y1, y2, materialId, glassId, maker, false);
	}
	
	protected void setArcNorthWest(int inset, int y1, int y2, byte materialId, byte glassId, MaterialFactory maker, boolean fill) {
		// Ref: Notes/BCircle.PDF
		int cx = inset;
		int cz = inset;
		int r = width - inset * 2 - 1;
		int x = r;
		int z = 0;
		int xChange = 1 - 2 * r;
		int zChange = 1;
		int rError = 0;
		
		while (x >= z) {
			if (fill) {
				setBlocks(cx, cx + x + 1, y1, y2, cz + z, cz + z + 1, materialId); // point in octant 1 ENE
				setBlocks(cx, cx + z + 1, y1, y2, cz + x, cz + x + 1, materialId); // point in octant 2 NNE
			} else if (maker != null) {
				maker.placeMaterial(this, materialId, glassId, cx + x, y1, y2, cz + z); // point in octant 1 ENE
				maker.placeMaterial(this, materialId, glassId, cx + z, y1, y2, cz + x); // point in octant 2 NNE
			} else {
				setBlock(cx + x, y1, cz + z, materialId); // point in octant 1 ENE
				setBlocks(cx + x, y1 + 1, y2, cz + z, glassId); // point in octant 1 ENE
				setBlock(cx + z, y1, cz + x, materialId); // point in octant 2 NNE
				setBlocks(cx + z, y1 + 1, y2, cz + x, glassId); // point in octant 2 NNE
			}
			
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
	
	protected void setArcSouthWest(int inset, int y1, int y2, byte materialId, byte glassId, MaterialFactory maker, boolean fill) {
		// Ref: Notes/BCircle.PDF
		int cx = inset;
		int cz = width - inset;
		int r = width - inset * 2 - 1;
		int x = r;
		int z = 0;
		int xChange = 1 - 2 * r;
		int zChange = 1;
		int rError = 0;
		
		while (x >= z) {
			if (fill) {
				setBlocks(cx, cx + z + 1, y1, y2, cz - x - 1, cz - x, materialId); // point in octant 7 WNW
				setBlocks(cx, cx + x + 1, y1, y2, cz - z - 1, cz - z, materialId); // point in octant 8 NNW
			} else if (maker != null) {
				maker.placeMaterial(this, materialId, glassId, cx + z, y1, y2, cz - x - 1); // point in octant 7 WNW
				maker.placeMaterial(this, materialId, glassId, cx + x, y1, y2, cz - z - 1); // point in octant 8 NNW
			} else {
				setBlock(cx + z, y1, cz - x - 1, materialId); // point in octant 7 WNW
				setBlocks(cx + z, y1 + 1, y2, cz - x - 1, glassId); // point in octant 7 WNW
				setBlock(cx + x, y1, cz - z - 1, materialId); // point in octant 8 NNW
				setBlocks(cx + x, y1 + 1, y2, cz - z - 1, glassId); // point in octant 8 NNW
			}
			
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
	
	protected void setArcNorthEast(int inset, int y1, int y2, byte materialId, byte glassId, MaterialFactory maker, boolean fill) {
		// Ref: Notes/BCircle.PDF
		int cx = width - inset;
		int cz = inset;
		int r = width - inset * 2 - 1;
		int x = r;
		int z = 0;
		int xChange = 1 - 2 * r;
		int zChange = 1;
		int rError = 0;
		
		while (x >= z) {
			if (fill) {
				setBlocks(cx - z - 1, cx, y1, y2, cz + x, cz + x + 1, materialId); // point in octant 3 ESE
				setBlocks(cx - x - 1, cx, y1, y2, cz + z, cz + z + 1, materialId); // point in octant 4 SSE
			} else if (maker != null) {
				maker.placeMaterial(this, materialId, glassId, cx - z - 1, y1, y2, cz + x); // point in octant 3 ESE
				maker.placeMaterial(this, materialId, glassId, cx - x - 1, y1, y2, cz + z); // point in octant 4 SSE
			} else {
				setBlock(cx - z - 1, y1, cz + x, materialId); // point in octant 3 ESE
				setBlocks(cx - z - 1, y1 + 1, y2, cz + x, glassId); // point in octant 3 ESE
				setBlock(cx - x - 1, y1, cz + z, materialId); // point in octant 4 SSE
				setBlocks(cx - x - 1, y1 + 1, y2, cz + z, glassId); // point in octant 4 SSE
			}
			
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
	
	protected void setArcSouthEast(int inset, int y1, int y2, byte materialId, byte glassId, MaterialFactory maker, boolean fill) {
		// Ref: Notes/BCircle.PDF
		int cx = width - inset;
		int cz = width - inset;
		int r = width - inset * 2 - 1;
		int x = r;
		int z = 0;
		int xChange = 1 - 2 * r;
		int zChange = 1;
		int rError = 0;
		
		while (x >= z) {
			if (fill) {
				setBlocks(cx - x - 1, cx, y1, y2, cz - z - 1, cz - z, materialId); // point in octant 5 SSW
				setBlocks(cx - z - 1, cx, y1, y2, cz - x - 1, cz - x, materialId); // point in octant 6 WSW
			} else if (maker != null) {
				maker.placeMaterial(this, materialId, glassId, cx - x - 1, y1, y2, cz - z - 1); // point in octant 5 SSW
				maker.placeMaterial(this, materialId, glassId, cx - z - 1, y1, y2, cz - x - 1); // point in octant 6 WSW
			} else {
				setBlock(cx - x - 1, y1, cz - z - 1, materialId); // point in octant 5 SSW
				setBlocks(cx - x - 1, y1 + 1, y2, cz - z - 1, glassId); // point in octant 5 SSW
				setBlock(cx - z - 1, y1, cz - x - 1, materialId); // point in octant 6 WSW
				setBlocks(cx - z - 1, y1 + 1, y2, cz - x - 1, glassId); // point in octant 6 WSW
			}
			
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
}
