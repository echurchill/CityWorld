package me.daddychurchill.CityWorld.Support;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.World;

// new 1.2.3 block code is loosely based on Mike Primm's updated version of 
// DinnerBone's Moon generator from: https://github.com/mikeprimm/BukkitFullOfMoon

public class ByteChunk {
	public int chunkX;
	public int chunkZ;
	public int width;
	public int height;
	public byte[][] blocks;
	
	public static final byte airId = (byte) Material.AIR.getId();
	public static final int chunksBlockWidth = 16;
	public static final int sectionsPerChunk = 16;
	public static final int bytesPerSection = chunksBlockWidth * chunksBlockWidth * chunksBlockWidth;
		
	public ByteChunk (World aWorld, int aChunkX, int aChunkZ) {
		super();	
		chunkX = aChunkX;
		chunkZ = aChunkZ;
		width = chunksBlockWidth;
		height = aWorld.getMaxHeight();
		blocks = new byte[sectionsPerChunk][];
	}
	
	public int getBlockX(int x) {
		return chunkX * width + x;
	}
	
	public int getBlockZ(int z) {
		return chunkZ * width + z;
	}
	
	public byte getBlock(int x, int y, int z) {
        if (blocks[y >> 4] == null)
        	return airId;
        else
        	return blocks[y >> 4][((y & 0xF) << 8) | (z << 4) | x];
	}
	
	public void setBlock(int x, int y, int z, byte materialId) {
        if (blocks[y >> 4] == null) {
        	blocks[y >> 4] = new byte[bytesPerSection];
        }
        blocks[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = materialId;
	}
	
	public void setBlock(int x, int y, int z, Material material) {
		setBlock(x, y, z, (byte) material.getId());
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
		setBlocks(x, y1, y2, z, (byte) material.getId());
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
		setBlocks(x1, x2, y1, y2, z1, z2, (byte) material.getId());
	}
	
	public void setBlocksAt(int y, byte materialId) {
		setBlocks(0, width, y, y + 1, 0, width, materialId);
	}
	
	public void setBlocksAt(int y, Material material) {
		setBlocks(0, width, y, y + 1, 0, width, (byte) material.getId());
	}
	
	public void setBlocksAt(int y1, int y2, byte materialId) {
		setBlocks(0, width, y1, y2, 0, width, materialId);
	}
	
	public void setBlocksAt(int y1, int y2, Material material) {
		setBlocks(0, width, y1, y2, 0, width, (byte) material.getId());
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
		setAllBlocks((byte) material.getId());
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
		replaceBlocks((byte) fromMaterial.getId(), (byte) toMaterial.getId());
	}
	
	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, byte primaryId, byte secondaryId, MaterialFactory maker) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				byte materialId = maker.pickMaterial(primaryId, secondaryId, x, z);
				for (int y = y1; y < y2; y++)
					setBlock(x, y, z, materialId);
			}
		}
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
	
	private void setCircleBlocks(int cx, int cz, int x, int z, int y, byte materialId) {
		// Ref: Notes/BCircle.PDF
		setBlock(cx + x, y, cz + z, materialId); // point in octant 1
		setBlock(cx + z, y, cz + x, materialId); // point in octant 2
		setBlock(cx - z - 1, y, cz + x, materialId); // point in octant 3
		setBlock(cx - x - 1, y, cz + z, materialId); // point in octant 4
		setBlock(cx - x - 1, y, cz - z - 1, materialId); // point in octant 5
		setBlock(cx - z - 1, y, cz - x - 1, materialId); // point in octant 6
		setBlock(cx + z, y, cz - x - 1, materialId); // point in octant 7
		setBlock(cx + x, y, cz - z - 1, materialId); // point in octant 8
	}
	
	public void setCircle(int cx, int cz, int r, int y, byte materialId) {
		// Ref: Notes/BCircle.PDF
		int x = r;
		int z = 0;
		int xChange = 1 - 2 * r;
		int zChange = 1;
		int rError = 0;
		
		while (x >= z) {
			setCircleBlocks(cx, cz, x, z, y, materialId);
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
	
	public void setArcNorthWest(int inset, int y1, int y2, byte materialId, boolean fill) {
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
			} else {
				setBlocks(cx + x, y1, y2, cz + z, materialId); // point in octant 1 ENE
				setBlocks(cx + z, y1, y2, cz + x, materialId); // point in octant 2 NNE
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
	
	public void setArcSouthWest(int inset, int y1, int y2, byte materialId, boolean fill) {
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
			} else {
				setBlocks(cx + z, y1, y2, cz - x - 1, materialId); // point in octant 7 WNW
				setBlocks(cx + x, y1, y2, cz - z - 1, materialId); // point in octant 8 NNW
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
	
	public void setArcNorthEast(int inset, int y1, int y2, byte materialId, boolean fill) {
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
			} else {
				setBlocks(cx - z - 1, y1, y2, cz + x, materialId); // point in octant 3 ESE
				setBlocks(cx - x - 1, y1, y2, cz + z, materialId); // point in octant 4 SSE
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
	
	public void setArcSouthEast(int inset, int y1, int y2, byte materialId, boolean fill) {
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
			} else {
				setBlocks(cx - x - 1, y1, y2, cz - z - 1, materialId); // point in octant 5 SSW
				setBlocks(cx - z - 1, y1, y2, cz - x - 1, materialId); // point in octant 6 WSW
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
