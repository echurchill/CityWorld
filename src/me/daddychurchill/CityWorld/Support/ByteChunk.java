package me.daddychurchill.CityWorld.Support;

import java.util.Arrays;
import me.daddychurchill.CityWorld.WorldGenerator;

import org.bukkit.Material;

// new 1.2.3 block code is loosely based on Mike Primm's updated version of 
// DinnerBone's Moon generator from: https://github.com/mikeprimm/BukkitFullOfMoon

public class ByteChunk extends SupportChunk {
	public byte[][] blocks;
	
	public static final int bytesPerSection = chunksBlockWidth * chunksBlockWidth * chunksBlockWidth;

	//TODO: Remove this
	private static final byte airId = BlackMagic.getMaterialId(Material.AIR);
//	public static final byte bedrockId = getMaterialId(Material.BEDROCK);
//	public static final byte stoneId = getMaterialId(Material.STONE);
//	public static final byte gravelId = getMaterialId(Material.GRAVEL);
//	public static final byte dirtId = getMaterialId(Material.DIRT);
//	public static final byte grassId = getMaterialId(Material.GRASS);
//	public static final byte glassMaterial = getMaterialId(Material.GLASS);
	
	public ByteChunk(WorldGenerator aGenerator, int aChunkX, int aChunkZ) {
		super(aGenerator);
		
		chunkX = aChunkX;
		chunkZ = aChunkZ;
		worldX = chunkX * width;
		worldZ = chunkZ * width;
		
		blocks = new byte[sectionsPerChunk][];
	}

	@Deprecated
	@Override
	public byte getBlockType(int x, int y, int z) {
		return getBlock(x, y, z);
	}
	
	@Deprecated
	public byte getBlock(int x, int y, int z) {
        if (blocks[y >> 4] == null)
        	return airId;
        else
        	return blocks[y >> 4][((y & 0xF) << 8) | (z << 4) | x];
	}
	
	@Deprecated
	public void setBlock(int x, int y, int z, byte wallMaterial) {
        if (blocks[y >> 4] == null) {
        	blocks[y >> 4] = new byte[bytesPerSection];
        }
        blocks[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = wallMaterial;
	}
	
	@Deprecated
	public void setBlocks(int x1, int x2, int y, int z1, int z2, byte wallMaterial) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				setBlock(x, y, z, wallMaterial);
			}
		}
	}
	
	public void setBlock(int x, int y, int z, Material material) {
		setBlock(x, y, z, BlackMagic.getMaterialId(material));
	}
	
	public void setBlockIfAir(int x, int y, int z, Material material) {
		setBlockIfAir(x, y, z, BlackMagic.getMaterialId(material));
	}
	
	public void setBlockIfAir(int x, int y, int z, byte wallMaterial) {
		if (getBlock(x, y, z) == airId && getBlock(x, y - 1, z) != airId)
			setBlock(x, y, z, wallMaterial);
	}
	
	public void setBlocks(int x, int y1, int y2, int z, byte wallMaterial) {
		for (int y = y1; y < y2; y++)
			setBlock(x, y, z, wallMaterial);
	}
	
	public void setBlocks(int x, int y1, int y2, int z, Material material) {
		setBlocks(x, y1, y2, z, BlackMagic.getMaterialId(material));
	}
	
	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, byte wallMaterial) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				for (int y = y1; y < y2; y++)
					setBlock(x, y, z, wallMaterial);
			}
		}
	}
	
	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
		setBlocks(x1, x2, y1, y2, z1, z2, BlackMagic.getMaterialId(material));
	}
	
	public void setBlocks(int x1, int x2, int y, int z1, int z2, Material material) {
		setBlocks(x1, x2, y, z1, z2, BlackMagic.getMaterialId(material));
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
	public void setWalls(int x1, int x2, int y1, int y2, int z1, int z2, byte wallMaterial) {
		setBlocks(x1, x2, y1, y2, z1, z1 + 1, wallMaterial);
		setBlocks(x1, x2, y1, y2, z2 - 1, z2, wallMaterial);
		setBlocks(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, wallMaterial);
		setBlocks(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, wallMaterial);
	}
	
	public void setWalls(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
		setWalls(x1, x2, y1, y2, z1, z2, BlackMagic.getMaterialId(material));
	}
	
	public boolean setEmptyBlock(int x, int y, int z, byte wallMaterial) {
		if (getBlock(x, y, z) == airId) {
			setBlock(x, y, z, wallMaterial);
			return true;
		} else
			return false;
	}
	
	public boolean setEmptyBlock(int x, int y, int z, Material material) {
		return setEmptyBlock(x, y, z, BlackMagic.getMaterialId(material));
	}

	public void setEmptyBlocks(int x1, int x2, int y, int z1, int z2, byte wallMaterial) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				if (getBlock(x, y, z) == airId)
					setBlock(x, y, z, wallMaterial);
			}
		}
	}
	
	public void setEmptyBlocks(int x1, int x2, int y, int z1, int z2, Material material) {
		setEmptyBlocks(x1, x2, y, z1, z2, BlackMagic.getMaterialId(material));
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
	
	public void setBlocksAt(int y, byte wallMaterial) {
		setBlocks(0, width, y, y + 1, 0, width, wallMaterial);
	}
	
	public void setBlocksAt(int y, Material material) {
		setBlocks(0, width, y, y + 1, 0, width, BlackMagic.getMaterialId(material));
	}
	
	public void setBlocksAt(int y1, int y2, byte wallMaterial) {
		setBlocks(0, width, y1, y2, 0, width, wallMaterial);
	}
	
	public void setBlocksAt(int y1, int y2, Material material) {
		setBlocks(0, width, y1, y2, 0, width, BlackMagic.getMaterialId(material));
	}
	
	public void setAllBlocks(byte wallMaterial) {
		// shortcut if we are simply clearing everything
		if (wallMaterial == airId) {
			for (int c = 0; c < sectionsPerChunk; c++) {
				blocks[c] = null;
			}
		
		// fine.. do it the hard way
		} else {
			for (int c = 0; c < sectionsPerChunk; c++) {
				if (blocks[c] == null)
					blocks[c] = new byte[bytesPerSection];
				Arrays.fill(blocks[c], 0, bytesPerSection, wallMaterial);
			}
		}	
	}

	public void setAllBlocks(Material material) {
		setAllBlocks(BlackMagic.getMaterialId(material));
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
		replaceBlocks(BlackMagic.getMaterialId(fromMaterial), BlackMagic.getMaterialId(toMaterial));
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
	
	public int setLayer(int blocky, byte wallMaterial) {
		setBlocks(0, width, blocky, blocky + 1, 0, width, wallMaterial);
		return blocky + 1;
	}
	
	public int setLayer(int blocky, int height, byte wallMaterial) {
		setBlocks(0, width, blocky, blocky + height, 0, width, wallMaterial);
		return blocky + height;
	}
	
	public int setLayer(int blocky, int height, int inset, byte wallMaterial) {
		setBlocks(inset, width - inset, blocky, blocky + height, inset, width - inset, wallMaterial);
		return blocky + height;
	}
	
	public void setArcNorthWest(int inset, int y1, int y2, Material wallMaterial, boolean fill) {
		setArcNorthWest(inset, y1, y2, wallMaterial, wallMaterial, null, fill);
	}

	public void setArcSouthWest(int inset, int y1, int y2, Material wallMaterial, boolean fill) {
		setArcSouthWest(inset, y1, y2, wallMaterial, wallMaterial, null, fill);
	}

	public void setArcNorthEast(int inset, int y1, int y2, Material wallMaterial, boolean fill) {
		setArcNorthEast(inset, y1, y2, wallMaterial, wallMaterial, null, fill);
	}
	
	public void setArcSouthEast(int inset, int y1, int y2, Material wallMaterial, boolean fill) {
		setArcSouthEast(inset, y1, y2, wallMaterial, wallMaterial, null, fill);
	}

	public void setArcNorthWest(int inset, int y1, int y2, Material wallMaterial, Material glassMaterial, MaterialFactory maker) {
		setArcNorthWest(inset, y1, y2, wallMaterial, glassMaterial, maker, false);
	}
	
	public void setArcSouthWest(int inset, int y1, int y2, Material wallMaterial, Material glassMaterial, MaterialFactory maker) {
		setArcSouthWest(inset, y1, y2, wallMaterial, glassMaterial, maker, false);
	}
	
	public void setArcNorthEast(int inset, int y1, int y2, Material wallMaterial, Material glassMaterial, MaterialFactory maker) {
		setArcNorthEast(inset, y1, y2, wallMaterial, glassMaterial, maker, false);
	}
	
	public void setArcSouthEast(int inset, int y1, int y2, Material wallMaterial, Material glassMaterial, MaterialFactory maker) {
		setArcSouthEast(inset, y1, y2, wallMaterial, glassMaterial, maker, false);
	}
	
	protected void setArcNorthWest(int inset, int y1, int y2, Material wallMaterial, Material glassMaterial, MaterialFactory maker, boolean fill) {
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
				setBlocks(cx, cx + x + 1, y1, y2, cz + z, cz + z + 1, wallMaterial); // point in octant 1 ENE
				setBlocks(cx, cx + z + 1, y1, y2, cz + x, cz + x + 1, wallMaterial); // point in octant 2 NNE
			} else if (maker != null) {
				maker.placeMaterial(this, wallMaterial, glassMaterial, cx + x, y1, y2, cz + z); // point in octant 1 ENE
				maker.placeMaterial(this, wallMaterial, glassMaterial, cx + z, y1, y2, cz + x); // point in octant 2 NNE
			} else {
				setBlock(cx + x, y1, cz + z, wallMaterial); // point in octant 1 ENE
				setBlocks(cx + x, y1 + 1, y2, cz + z, glassMaterial); // point in octant 1 ENE
				setBlock(cx + z, y1, cz + x, wallMaterial); // point in octant 2 NNE
				setBlocks(cx + z, y1 + 1, y2, cz + x, glassMaterial); // point in octant 2 NNE
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
	
	protected void setArcSouthWest(int inset, int y1, int y2, Material wallMaterial, Material glassMaterial, MaterialFactory maker, boolean fill) {
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
				setBlocks(cx, cx + z + 1, y1, y2, cz - x - 1, cz - x, wallMaterial); // point in octant 7 WNW
				setBlocks(cx, cx + x + 1, y1, y2, cz - z - 1, cz - z, wallMaterial); // point in octant 8 NNW
			} else if (maker != null) {
				maker.placeMaterial(this, wallMaterial, glassMaterial, cx + z, y1, y2, cz - x - 1); // point in octant 7 WNW
				maker.placeMaterial(this, wallMaterial, glassMaterial, cx + x, y1, y2, cz - z - 1); // point in octant 8 NNW
			} else {
				setBlock(cx + z, y1, cz - x - 1, wallMaterial); // point in octant 7 WNW
				setBlocks(cx + z, y1 + 1, y2, cz - x - 1, glassMaterial); // point in octant 7 WNW
				setBlock(cx + x, y1, cz - z - 1, wallMaterial); // point in octant 8 NNW
				setBlocks(cx + x, y1 + 1, y2, cz - z - 1, glassMaterial); // point in octant 8 NNW
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
	
	protected void setArcNorthEast(int inset, int y1, int y2, Material wallMaterial, Material glassMaterial, MaterialFactory maker, boolean fill) {
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
				setBlocks(cx - z - 1, cx, y1, y2, cz + x, cz + x + 1, wallMaterial); // point in octant 3 ESE
				setBlocks(cx - x - 1, cx, y1, y2, cz + z, cz + z + 1, wallMaterial); // point in octant 4 SSE
			} else if (maker != null) {
				maker.placeMaterial(this, wallMaterial, glassMaterial, cx - z - 1, y1, y2, cz + x); // point in octant 3 ESE
				maker.placeMaterial(this, wallMaterial, glassMaterial, cx - x - 1, y1, y2, cz + z); // point in octant 4 SSE
			} else {
				setBlock(cx - z - 1, y1, cz + x, wallMaterial); // point in octant 3 ESE
				setBlocks(cx - z - 1, y1 + 1, y2, cz + x, glassMaterial); // point in octant 3 ESE
				setBlock(cx - x - 1, y1, cz + z, wallMaterial); // point in octant 4 SSE
				setBlocks(cx - x - 1, y1 + 1, y2, cz + z, glassMaterial); // point in octant 4 SSE
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
	
	protected void setArcSouthEast(int inset, int y1, int y2, Material wallMaterial, Material glassMaterial, MaterialFactory maker, boolean fill) {
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
				setBlocks(cx - x - 1, cx, y1, y2, cz - z - 1, cz - z, wallMaterial); // point in octant 5 SSW
				setBlocks(cx - z - 1, cx, y1, y2, cz - x - 1, cz - x, wallMaterial); // point in octant 6 WSW
			} else if (maker != null) {
				maker.placeMaterial(this, wallMaterial, glassMaterial, cx - x - 1, y1, y2, cz - z - 1); // point in octant 5 SSW
				maker.placeMaterial(this, wallMaterial, glassMaterial, cx - z - 1, y1, y2, cz - x - 1); // point in octant 6 WSW
			} else {
				setBlock(cx - x - 1, y1, cz - z - 1, wallMaterial); // point in octant 5 SSW
				setBlocks(cx - x - 1, y1 + 1, y2, cz - z - 1, glassMaterial); // point in octant 5 SSW
				setBlock(cx - z - 1, y1, cz - x - 1, wallMaterial); // point in octant 6 WSW
				setBlocks(cx - z - 1, y1 + 1, y2, cz - x - 1, glassMaterial); // point in octant 6 WSW
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
