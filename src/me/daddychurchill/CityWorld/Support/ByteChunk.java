package me.daddychurchill.CityWorld.Support;

import java.util.Arrays;
import me.daddychurchill.CityWorld.WorldGenerator;

import org.bukkit.Material;

// new 1.2.3 block code is loosely based on Mike Primm's updated version of 
// DinnerBone's Moon generator from: https://github.com/mikeprimm/BukkitFullOfMoon

public final class ByteChunk extends AbstractChunk {
	public byte[][] blocks;
	
	public final static int bytesPerSection = chunksBlockWidth * chunksBlockWidth * chunksBlockWidth;

	public final static byte AIR = BlackMagic.getMaterialId(Material.AIR);
	public final static byte BEDROCK = BlackMagic.getMaterialId(Material.BEDROCK);
	public final static byte STONE = BlackMagic.getMaterialId(Material.STONE);
	public final static byte GRAVEL = BlackMagic.getMaterialId(Material.GRAVEL);
	public final static byte DIRT = BlackMagic.getMaterialId(Material.DIRT);
	public final static byte GRASS = BlackMagic.getMaterialId(Material.GRASS);
	public final static byte GLASS = BlackMagic.getMaterialId(Material.GLASS);
	public final static byte SAND = BlackMagic.getMaterialId(Material.SAND);
	public final static byte SANDSTONE = BlackMagic.getMaterialId(Material.SANDSTONE); 

	public final static byte COAL_ORE = BlackMagic.getMaterialId(Material.COAL_ORE); 
	public final static byte IRON_ORE = BlackMagic.getMaterialId(Material.IRON_ORE);
	public final static byte GOLD_ORE = BlackMagic.getMaterialId(Material.GOLD_ORE);
	public final static byte LAPIS_ORE = BlackMagic.getMaterialId(Material.LAPIS_ORE);
	public final static byte REDSTONE_ORE = BlackMagic.getMaterialId(Material.REDSTONE_ORE);
	public final static byte DIAMOND_ORE = BlackMagic.getMaterialId(Material.DIAMOND_ORE);
	public final static byte EMERALD_ORE = BlackMagic.getMaterialId(Material.EMERALD_ORE);
	
	public final static byte STEP = BlackMagic.getMaterialId(Material.STEP);
	public final static byte WOOD = BlackMagic.getMaterialId(Material.WOOD);
	public final static byte WOOD_STEP = BlackMagic.getMaterialId(Material.WOOD_STEP);
	public final static byte FENCE = BlackMagic.getMaterialId(Material.FENCE);
	public final static byte SNOW_BLOCK = BlackMagic.getMaterialId(Material.SNOW_BLOCK);
	public final static byte SNOW = BlackMagic.getMaterialId(Material.SNOW);
	public final static byte ICE = BlackMagic.getMaterialId(Material.ICE); 
	
	public final static byte WATER = BlackMagic.getMaterialId(Material.WATER); 
	public final static byte LAVA = BlackMagic.getMaterialId(Material.LAVA); 
	public final static byte STATIONARY_WATER = BlackMagic.getMaterialId(Material.STATIONARY_WATER); 
	public final static byte STATIONARY_LAVA = BlackMagic.getMaterialId(Material.STATIONARY_LAVA); 
	public final static byte STONE_PLATE = BlackMagic.getMaterialId(Material.STONE_PLATE);
	public final static byte WOOD_PLATE = BlackMagic.getMaterialId(Material.WOOD_PLATE);
	
	public final static byte ENDER_STONE = BlackMagic.getMaterialId(Material.ENDER_STONE);
	public final static byte NETHERRACK = BlackMagic.getMaterialId(Material.NETHERRACK);
	public final static byte SOUL_SAND = BlackMagic.getMaterialId(Material.SOUL_SAND);
	public final static byte GLOWSTONE = BlackMagic.getMaterialId(Material.GLOWSTONE);
	
	public final static byte CAULDRON = BlackMagic.getMaterialId(Material.CAULDRON);
	
	private static int sectionsPerChunk = 16;
	
	public ByteChunk(WorldGenerator aGenerator, int aChunkX, int aChunkZ) {
		super(aGenerator);
		
		chunkX = aChunkX;
		chunkZ = aChunkZ;
		
		blocks = new byte[sectionsPerChunk][];
	}

	public byte getBlockType(int x, int y, int z) {
		return getBlock(x, y, z);
	}
	
	public boolean isType(int x, int y, int z, byte typeId) {
		return getBlockType(x, y, z) == typeId;
	}
	
	public boolean isOfTypes(int x, int y, int z, byte ... typeIds) {
		byte typeId = getBlockType(x, y, z);
		for (byte testId : typeIds)
			if (typeId == testId)
				return true;
		return false;
	}
	
	public boolean isEmpty(int x, int y, int z) {
		return getBlockType(x, y, z) == AIR;
	}
	
	public byte getBlock(int x, int y, int z) {
        if (blocks[y >> 4] == null)
        	return AIR;
        else
        	return blocks[y >> 4][((y & 0xF) << 8) | (z << 4) | x];
	}
	
	public void setBlock(int x, int y, int z, byte typeId) {
        if (blocks[y >> 4] == null) {
        	blocks[y >> 4] = new byte[bytesPerSection];
        }
        blocks[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = typeId;
	}
	
	@Override
	public void setBlock(int x, int y, int z, Material material) {
		setBlock(x, y, z, BlackMagic.getMaterialId(material));
	}

	public void setBlockIfAir(int x, int y, int z, byte typeId) {
		if (getBlock(x, y, z) == AIR && getBlock(x, y - 1, z) != AIR)
			setBlock(x, y, z, typeId);
	}
	
	@Override
	public void setBlockIfAir(int x, int y, int z, Material material) {
		setBlockIfAir(x, y, z, BlackMagic.getMaterialId(material));
	}
	
	@Override
	public void clearBlock(int x, int y, int z) {
        if (blocks[y >> 4] != null) {
        	blocks[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = AIR;
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
	
	//================ x, y1, y2, z
	public void setBlocks(int x, int y1, int y2, int z, byte typeId) {
		for (int y = y1; y < y2; y++)
			setBlock(x, y, z, typeId);
	}
	
	@Override
	public void setBlocks(int x, int y1, int y2, int z, Material material) {
		setBlocks(x, y1, y2, z, BlackMagic.getMaterialId(material));
	}

	//================ x1, x2, y1, y2, z1, z2
	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, byte typeId) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				for (int y = y1; y < y2; y++)
					setBlock(x, y, z, typeId);
			}
		}
	}
	
	@Override
	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
		setBlocks(x1, x2, y1, y2, z1, z2, BlackMagic.getMaterialId(material));
	}

	//================ x1, x2, y, z1, z2
	public void setBlocks(int x1, int x2, int y, int z1, int z2, byte typeId) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				setBlock(x, y, z, typeId);
			}
		}
	}
	
	@Override
	public void setBlocks(int x1, int x2, int y, int z1, int z2, Material material) {
		setBlocks(x1, x2, y, z1, z2, BlackMagic.getMaterialId(material));
	}

	//================ Walls
	public void setWalls(int x1, int x2, int y1, int y2, int z1, int z2, byte typeId) {
		setBlocks(x1, x2, y1, y2, z1, z1 + 1, typeId);
		setBlocks(x1, x2, y1, y2, z2 - 1, z2, typeId);
		setBlocks(x1, x1 + 1, y1, y2, z1 + 1, z2 - 1, typeId);
		setBlocks(x2 - 1, x2, y1, y2, z1 + 1, z2 - 1, typeId);
	}

	@Override
	public void setWalls(int x1, int x2, int y1, int y2, int z1, int z2, Material material) {
		setBlocks(x1, x2, y1, y2, z1, z2, BlackMagic.getMaterialId(material));
	}
	
	//================ Layers
	public int setLayer(int blocky, byte typeId) {
		setBlocks(0, width, blocky, blocky + 1, 0, width, typeId);
		return blocky + 1;
	}
	
	@Override
	public int setLayer(int blocky, Material material) {
		return setLayer(blocky, BlackMagic.getMaterialId(material));
	}

	public int setLayer(int blocky, int height, byte typeId) {
		setBlocks(0, width, blocky, blocky + height, 0, width, typeId);
		return blocky + height;
	}
	
	@Override
	public int setLayer(int blocky, int height, Material material) {
		return setLayer(blocky, height, BlackMagic.getMaterialId(material));
	}

	public int setLayer(int blocky, int height, int inset, byte typeId) {
		setBlocks(inset, width - inset, blocky, blocky + height, inset, width - inset, typeId);
		return blocky + height;
	}
	
	@Override
	public int setLayer(int blocky, int height, int inset, Material material) {
		return setLayer(blocky, height, inset, BlackMagic.getMaterialId(material));
	}
	
	//================ MaterialFactories
	public void setBlocks(int x, int y1, int y2, int z, byte primaryId, byte secondaryId, MaterialFactory maker) {
		maker.placeMaterial(this, primaryId, secondaryId, x, y1, y2, z);
	}

	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, byte primaryId, byte secondaryId, MaterialFactory maker) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				maker.placeMaterial(this, primaryId, secondaryId, x, y1, y2, z);
			}
		}
	}
	
	public void setBlocks(int x, int y1, int y2, int z, Material primary, Material secondary, MaterialFactory maker) {
		setBlocks(x, y1, y2, z, BlackMagic.getMaterialId(primary), BlackMagic.getMaterialId(secondary), maker);
	}

	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material primary, Material secondary, MaterialFactory maker) {
		setBlocks(x1, x2, y1, y2, z1, z2, BlackMagic.getMaterialId(primary), BlackMagic.getMaterialId(secondary), maker);
	}

	public boolean setEmptyBlock(int x, int y, int z, byte typeId) {
		if (getBlock(x, y, z) == AIR) {
			setBlock(x, y, z, typeId);
			return true;
		} else
			return false;
	}
	
	@Override
	public final boolean setEmptyBlock(int x, int y, int z, Material material) {
		return setEmptyBlock(x, y, z, BlackMagic.getMaterialId(material));
	}

	public void setEmptyBlocks(int x1, int x2, int y, int z1, int z2, byte typeId) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				if (getBlock(x, y, z) == AIR)
					setBlock(x, y, z, typeId);
			}
		}
	}
	
	@Override
	public final void setEmptyBlocks(int x1, int x2, int y, int z1, int z2, Material material) {
		setEmptyBlocks(x1, x2, y, z1, z2, BlackMagic.getMaterialId(material));
	}
	
	@Override
	public final int findFirstEmpty(int x, int y, int z) {
		if (getBlock(x, y, z) == AIR)
			return findLastEmptyBelow(x, y, z);
		else
			return findFirstEmptyAbove(x, y, z);
	}
	
	@Override
	public final int findFirstEmptyAbove(int x, int y, int z) {
		int y1 = y;
		while (y1 < height - 1) {
			if (getBlock(x, y1, z) == AIR)
				return y1;
			y1++;
		}
		return height - 1;
	}
	
	@Override
	public int findLastEmptyAbove(int x, int y, int z) {
		int y1 = y;
		while (y1 < height - 1 && getBlock(x, y1 + 1, z) == AIR) {
			y1++;
		}
		return y1;
	}
	
	@Override
	public int findLastEmptyBelow(int x, int y, int z) {
		int y1 = y;
		while (y1 > 0 && getBlock(x, y1 - 1, z) == AIR) {
			y1--;
		}
		return y1;
	}
	
	public void setBlocksAt(int y, byte typeId) {
		setBlocks(0, width, y, y + 1, 0, width, typeId);
	}
	
	public void setBlocksAt(int y1, int y2, byte typeId) {
		setBlocks(0, width, y1, y2, 0, width, typeId);
	}
	
	public void setAllBlocks(byte typeId) {
		// shortcut if we are simply clearing everything
		if (typeId == AIR) {
			for (int c = 0; c < sectionsPerChunk; c++) {
				blocks[c] = null;
			}
		
		// fine.. do it the hard way
		} else {
			for (int c = 0; c < sectionsPerChunk; c++) {
				if (blocks[c] == null)
					blocks[c] = new byte[bytesPerSection];
				Arrays.fill(blocks[c], 0, bytesPerSection, typeId);
			}
		}	
	}

	public void replaceBlocks(byte fromId, byte toId) {
		// if we are replacing air we might need to do this the hard way
		if (fromId == AIR) {
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

	public void setArcNorthWest(int inset, int y1, int y2, byte primaryId, boolean fill) {
		setArcNorthWest(inset, y1, y2, primaryId, primaryId, null, fill);
	}

	public void setArcSouthWest(int inset, int y1, int y2, byte primaryId, boolean fill) {
		setArcSouthWest(inset, y1, y2, primaryId, primaryId, null, fill);
	}

	public void setArcNorthEast(int inset, int y1, int y2, byte primaryId, boolean fill) {
		setArcNorthEast(inset, y1, y2, primaryId, primaryId, null, fill);
	}
	
	public void setArcSouthEast(int inset, int y1, int y2, byte primaryId, boolean fill) {
		setArcSouthEast(inset, y1, y2, primaryId, primaryId, null, fill);
	}

	public void setArcNorthWest(int inset, int y1, int y2, Material primary, boolean fill) {
		setArcNorthWest(inset, y1, y2, BlackMagic.getMaterialId(primary), BlackMagic.getMaterialId(primary), null, fill);
	}

	public void setArcSouthWest(int inset, int y1, int y2, Material primary, boolean fill) {
		setArcSouthWest(inset, y1, y2, BlackMagic.getMaterialId(primary), BlackMagic.getMaterialId(primary), null, fill);
	}

	public void setArcNorthEast(int inset, int y1, int y2, Material primary, boolean fill) {
		setArcNorthEast(inset, y1, y2, BlackMagic.getMaterialId(primary), BlackMagic.getMaterialId(primary), null, fill);
	}
	
	public void setArcSouthEast(int inset, int y1, int y2, Material primary, boolean fill) {
		setArcSouthEast(inset, y1, y2, BlackMagic.getMaterialId(primary), BlackMagic.getMaterialId(primary), null, fill);
	}

	public void setArcNorthWest(int inset, int y1, int y2, byte primaryId, byte secondaryId, MaterialFactory maker) {
		setArcNorthWest(inset, y1, y2, primaryId, secondaryId, maker, false);
	}
	
	public void setArcSouthWest(int inset, int y1, int y2, byte primaryId, byte secondaryId, MaterialFactory maker) {
		setArcSouthWest(inset, y1, y2, primaryId, secondaryId, maker, false);
	}
	
	public void setArcNorthEast(int inset, int y1, int y2, byte primaryId, byte secondaryId, MaterialFactory maker) {
		setArcNorthEast(inset, y1, y2, primaryId, secondaryId, maker, false);
	}
	
	public void setArcSouthEast(int inset, int y1, int y2, byte primaryId, byte secondaryId, MaterialFactory maker) {
		setArcSouthEast(inset, y1, y2, primaryId, secondaryId, maker, false);
	}
	
	public void setArcNorthWest(int inset, int y1, int y2, Material primary, Material secondary, MaterialFactory maker) {
		setArcNorthWest(inset, y1, y2, BlackMagic.getMaterialId(primary), BlackMagic.getMaterialId(secondary), maker, false);
	}
	
	public void setArcSouthWest(int inset, int y1, int y2, Material primary, Material secondary, MaterialFactory maker) {
		setArcSouthWest(inset, y1, y2, BlackMagic.getMaterialId(primary), BlackMagic.getMaterialId(secondary), maker, false);
	}
	
	public void setArcNorthEast(int inset, int y1, int y2, Material primary, Material secondary, MaterialFactory maker) {
		setArcNorthEast(inset, y1, y2, BlackMagic.getMaterialId(primary), BlackMagic.getMaterialId(secondary), maker, false);
	}
	
	public void setArcSouthEast(int inset, int y1, int y2, Material primary, Material secondary, MaterialFactory maker) {
		setArcSouthEast(inset, y1, y2, BlackMagic.getMaterialId(primary), BlackMagic.getMaterialId(secondary), maker, false);
	}
	
	protected void setArcNorthWest(int inset, int y1, int y2, byte primaryId, byte secondaryId, MaterialFactory maker, boolean fill) {
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
				setBlocks(cx, cx + x + 1, y1, y2, cz + z, cz + z + 1, primaryId); // point in octant 1 ENE
				setBlocks(cx, cx + z + 1, y1, y2, cz + x, cz + x + 1, primaryId); // point in octant 2 NNE
			} else if (maker != null) {
				maker.placeMaterial(this, primaryId, secondaryId, cx + x, y1, y2, cz + z); // point in octant 1 ENE
				maker.placeMaterial(this, primaryId, secondaryId, cx + z, y1, y2, cz + x); // point in octant 2 NNE
			} else {
				setBlock(cx + x, y1, cz + z, primaryId); // point in octant 1 ENE
				setBlocks(cx + x, y1 + 1, y2, cz + z, secondaryId); // point in octant 1 ENE
				setBlock(cx + z, y1, cz + x, primaryId); // point in octant 2 NNE
				setBlocks(cx + z, y1 + 1, y2, cz + x, secondaryId); // point in octant 2 NNE
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
	
	protected void setArcSouthWest(int inset, int y1, int y2, byte primaryId, byte secondaryId, MaterialFactory maker, boolean fill) {
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
				setBlocks(cx, cx + z + 1, y1, y2, cz - x - 1, cz - x, primaryId); // point in octant 7 WNW
				setBlocks(cx, cx + x + 1, y1, y2, cz - z - 1, cz - z, primaryId); // point in octant 8 NNW
			} else if (maker != null) {
				maker.placeMaterial(this, primaryId, secondaryId, cx + z, y1, y2, cz - x - 1); // point in octant 7 WNW
				maker.placeMaterial(this, primaryId, secondaryId, cx + x, y1, y2, cz - z - 1); // point in octant 8 NNW
			} else {
				setBlock(cx + z, y1, cz - x - 1, primaryId); // point in octant 7 WNW
				setBlocks(cx + z, y1 + 1, y2, cz - x - 1, secondaryId); // point in octant 7 WNW
				setBlock(cx + x, y1, cz - z - 1, primaryId); // point in octant 8 NNW
				setBlocks(cx + x, y1 + 1, y2, cz - z - 1, secondaryId); // point in octant 8 NNW
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
	
	protected void setArcNorthEast(int inset, int y1, int y2, byte primaryId, byte secondaryId, MaterialFactory maker, boolean fill) {
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
				setBlocks(cx - z - 1, cx, y1, y2, cz + x, cz + x + 1, primaryId); // point in octant 3 ESE
				setBlocks(cx - x - 1, cx, y1, y2, cz + z, cz + z + 1, primaryId); // point in octant 4 SSE
			} else if (maker != null) {
				maker.placeMaterial(this, primaryId, secondaryId, cx - z - 1, y1, y2, cz + x); // point in octant 3 ESE
				maker.placeMaterial(this, primaryId, secondaryId, cx - x - 1, y1, y2, cz + z); // point in octant 4 SSE
			} else {
				setBlock(cx - z - 1, y1, cz + x, primaryId); // point in octant 3 ESE
				setBlocks(cx - z - 1, y1 + 1, y2, cz + x, secondaryId); // point in octant 3 ESE
				setBlock(cx - x - 1, y1, cz + z, primaryId); // point in octant 4 SSE
				setBlocks(cx - x - 1, y1 + 1, y2, cz + z, secondaryId); // point in octant 4 SSE
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
	
	protected void setArcSouthEast(int inset, int y1, int y2, byte primaryId, byte secondaryId, MaterialFactory maker, boolean fill) {
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
				setBlocks(cx - x - 1, cx, y1, y2, cz - z - 1, cz - z, primaryId); // point in octant 5 SSW
				setBlocks(cx - z - 1, cx, y1, y2, cz - x - 1, cz - x, primaryId); // point in octant 6 WSW
			} else if (maker != null) {
				maker.placeMaterial(this, primaryId, secondaryId, cx - x - 1, y1, y2, cz - z - 1); // point in octant 5 SSW
				maker.placeMaterial(this, primaryId, secondaryId, cx - z - 1, y1, y2, cz - x - 1); // point in octant 6 WSW
			} else {
				setBlock(cx - x - 1, y1, cz - z - 1, primaryId); // point in octant 5 SSW
				setBlocks(cx - x - 1, y1 + 1, y2, cz - z - 1, secondaryId); // point in octant 5 SSW
				setBlock(cx - z - 1, y1, cz - x - 1, primaryId); // point in octant 6 WSW
				setBlocks(cx - z - 1, y1 + 1, y2, cz - x - 1, secondaryId); // point in octant 6 WSW
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
	
	private void drawCircleBlocks(int cx, int cz, int x, int z, int y, byte typeId) {
		// Ref: Notes/BCircle.PDF
		setBlock(cx + x, y, cz + z, typeId); // point in octant 1
		setBlock(cx + z, y, cz + x, typeId); // point in octant 2
		setBlock(cx - z - 1, y, cz + x, typeId); // point in octant 3
		setBlock(cx - x - 1, y, cz + z, typeId); // point in octant 4
		setBlock(cx - x - 1, y, cz - z - 1, typeId); // point in octant 5
		setBlock(cx - z - 1, y, cz - x - 1, typeId); // point in octant 6
		setBlock(cx + z, y, cz - x - 1, typeId); // point in octant 7
		setBlock(cx + x, y, cz - z - 1, typeId); // point in octant 8
	}
	
	private void drawCircleBlocks(int cx, int cz, int x, int z, int y1, int y2, byte typeId) {
		for (int y = y1; y < y2; y++) {
			drawCircleBlocks(cx, cz, x, z, y, typeId);
		}
	}
	
	private void fillCircleBlocks(int cx, int cz, int x, int z, int y, byte typeId) {
		// Ref: Notes/BCircle.PDF
		setBlocks(cx - x - 1, cx - x, y, cz - z - 1, cz + z + 1, typeId); // point in octant 5
		setBlocks(cx - z - 1, cx - z, y, cz - x - 1, cz + x + 1, typeId); // point in octant 6
		setBlocks(cx + z, cx + z + 1, y, cz - x - 1, cz + x + 1, typeId); // point in octant 7
		setBlocks(cx + x, cx + x + 1, y, cz - z - 1, cz + z + 1, typeId); // point in octant 8
	}
	
	private void fillCircleBlocks(int cx, int cz, int x, int z, int y1, int y2, byte typeId) {
		for (int y = y1; y < y2; y++) {
			fillCircleBlocks(cx, cz, x, z, y, typeId);
		}
	}
	
	public void setCircle(int cx, int cz, int r, int y, byte typeId, boolean fill) {
		setCircle(cx, cz, r, y, y + 1, typeId, fill);
	}
	
	public void setCircle(int cx, int cz, int r, int y1, int y2, byte typeId, boolean fill) {
		// Ref: Notes/BCircle.PDF
		int x = r;
		int z = 0;
		int xChange = 1 - 2 * r;
		int zChange = 1;
		int rError = 0;
		
		while (x >= z) {
			if (fill)
				fillCircleBlocks(cx, cz, x, z, y1, y2, typeId);
			else
				drawCircleBlocks(cx, cz, x, z, y1, y2, typeId);
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
	
	@Override
	public void setCircle(int cx, int cz, int r, int y, Material material) {
		setCircle(cx, cz, r, y, y + 1, BlackMagic.getMaterialId(material), false);
	}
	
	@Override	
	public void setCircle(int cx, int cz, int r, int y, Material material, boolean fill) {
		setCircle(cx, cz, r, y, y + 1, BlackMagic.getMaterialId(material), fill);
	}
	
	@Override
	public void setCircle(int cx, int cz, int r, int y1, int y2, Material material) {
		setCircle(cx, cz, r, y1, y2, BlackMagic.getMaterialId(material), false);
	}
	
	@Override
	public void setCircle(int cx, int cz, int r, int y1, int y2, Material material, boolean fill) {
		setCircle(cx, cz, r, y1, y2, BlackMagic.getMaterialId(material), fill);
	}
	
	public void setSphere(int cx, int cy, int cz, int r, byte typeId, boolean fill) {
		for (int r1 = 1; r1 < r; r1++) {
			setCircle(cx, cz, r - r1, cy + r1, typeId, fill);
			setCircle(cx, cz, r - r1, cy - r1, typeId, fill);
		}
		setCircle(cx, cz, r, cy, typeId, fill);
	}
}
