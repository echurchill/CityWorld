package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Factories.MaterialFactory;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.ChunkData;

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

	public void setBlockIfAir(int x, int y, int z, Material material) {
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
	
	public void setArcNorthWest(int inset, int y1, int y2, Material primary, boolean fill) {
		setArcNorthWest(inset, y1, y2, primary, primary, null, fill);
	}

	public void setArcSouthWest(int inset, int y1, int y2, Material primary, boolean fill) {
		setArcSouthWest(inset, y1, y2, primary, primary, null, fill);
	}

	public void setArcNorthEast(int inset, int y1, int y2, Material primary, boolean fill) {
		setArcNorthEast(inset, y1, y2, primary, primary, null, fill);
	}
	
	public void setArcSouthEast(int inset, int y1, int y2, Material primary, boolean fill) {
		setArcSouthEast(inset, y1, y2, primary, primary, null, fill);
	}

	public void setArcNorthWest(int inset, int y1, int y2, Material primary, Material secondary, MaterialFactory maker) {
		setArcNorthWest(inset, y1, y2, primary, secondary, maker, false);
	}
	
	public void setArcSouthWest(int inset, int y1, int y2, Material primary, Material secondary, MaterialFactory maker) {
		setArcSouthWest(inset, y1, y2, primary, secondary, maker, false);
	}
	
	public void setArcNorthEast(int inset, int y1, int y2, Material primary, Material secondary, MaterialFactory maker) {
		setArcNorthEast(inset, y1, y2, primary, secondary, maker, false);
	}
	
	public void setArcSouthEast(int inset, int y1, int y2, Material primary, Material secondary, MaterialFactory maker) {
		setArcSouthEast(inset, y1, y2, primary, secondary, maker, false);
	}
	
	public void setArcNorthWest(int inset, int y1, int y2, Material primary, Material secondary, MaterialFactory maker, boolean fill) {
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
				setBlocks(cx, cx + x + 1, y1, y2, cz + z, cz + z + 1, primary); // point in octant 1 ENE
				setBlocks(cx, cx + z + 1, y1, y2, cz + x, cz + x + 1, primary); // point in octant 2 NNE
			} else if (maker != null) {
				maker.placeMaterial(this, primary, secondary, cx + x, y1, y2, cz + z); // point in octant 1 ENE
				maker.placeMaterial(this, primary, secondary, cx + z, y1, y2, cz + x); // point in octant 2 NNE
			} else {
				setBlock(cx + x, y1, cz + z, primary); // point in octant 1 ENE
				setBlocks(cx + x, y1 + 1, y2, cz + z, secondary); // point in octant 1 ENE
				setBlock(cx + z, y1, cz + x, primary); // point in octant 2 NNE
				setBlocks(cx + z, y1 + 1, y2, cz + x, secondary); // point in octant 2 NNE
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
	
	public void setArcSouthWest(int inset, int y1, int y2, Material primary, Material secondary, MaterialFactory maker, boolean fill) {
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
				setBlocks(cx, cx + z + 1, y1, y2, cz - x - 1, cz - x, primary); // point in octant 7 WNW
				setBlocks(cx, cx + x + 1, y1, y2, cz - z - 1, cz - z, primary); // point in octant 8 NNW
			} else if (maker != null) {
				maker.placeMaterial(this, primary, secondary, cx + z, y1, y2, cz - x - 1); // point in octant 7 WNW
				maker.placeMaterial(this, primary, secondary, cx + x, y1, y2, cz - z - 1); // point in octant 8 NNW
			} else {
				setBlock(cx + z, y1, cz - x - 1, primary); // point in octant 7 WNW
				setBlocks(cx + z, y1 + 1, y2, cz - x - 1, secondary); // point in octant 7 WNW
				setBlock(cx + x, y1, cz - z - 1, primary); // point in octant 8 NNW
				setBlocks(cx + x, y1 + 1, y2, cz - z - 1, secondary); // point in octant 8 NNW
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
	
	public void setArcNorthEast(int inset, int y1, int y2, Material primary, Material secondary, MaterialFactory maker, boolean fill) {
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
				setBlocks(cx - z - 1, cx, y1, y2, cz + x, cz + x + 1, primary); // point in octant 3 ESE
				setBlocks(cx - x - 1, cx, y1, y2, cz + z, cz + z + 1, primary); // point in octant 4 SSE
			} else if (maker != null) {
				maker.placeMaterial(this, primary, secondary, cx - z - 1, y1, y2, cz + x); // point in octant 3 ESE
				maker.placeMaterial(this, primary, secondary, cx - x - 1, y1, y2, cz + z); // point in octant 4 SSE
			} else {
				setBlock(cx - z - 1, y1, cz + x, primary); // point in octant 3 ESE
				setBlocks(cx - z - 1, y1 + 1, y2, cz + x, secondary); // point in octant 3 ESE
				setBlock(cx - x - 1, y1, cz + z, primary); // point in octant 4 SSE
				setBlocks(cx - x - 1, y1 + 1, y2, cz + z, secondary); // point in octant 4 SSE
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
	
	public void setArcSouthEast(int inset, int y1, int y2, Material primary, Material secondary, MaterialFactory maker, boolean fill) {
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
				setBlocks(cx - x - 1, cx, y1, y2, cz - z - 1, cz - z, primary); // point in octant 5 SSW
				setBlocks(cx - z - 1, cx, y1, y2, cz - x - 1, cz - x, primary); // point in octant 6 WSW
			} else if (maker != null) {
				maker.placeMaterial(this, primary, secondary, cx - x - 1, y1, y2, cz - z - 1); // point in octant 5 SSW
				maker.placeMaterial(this, primary, secondary, cx - z - 1, y1, y2, cz - x - 1); // point in octant 6 WSW
			} else {
				setBlock(cx - x - 1, y1, cz - z - 1, primary); // point in octant 5 SSW
				setBlocks(cx - x - 1, y1 + 1, y2, cz - z - 1, secondary); // point in octant 5 SSW
				setBlock(cx - z - 1, y1, cz - x - 1, primary); // point in octant 6 WSW
				setBlocks(cx - z - 1, y1 + 1, y2, cz - x - 1, secondary); // point in octant 6 WSW
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
	
	private void drawCircleBlocks(int cx, int cz, int x, int z, int y, Material material) {
		// Ref: Notes/BCircle.PDF
		setBlock(cx + x, y, cz + z, material); // point in octant 1
		setBlock(cx + z, y, cz + x, material); // point in octant 2
		setBlock(cx - z - 1, y, cz + x, material); // point in octant 3
		setBlock(cx - x - 1, y, cz + z, material); // point in octant 4
		setBlock(cx - x - 1, y, cz - z - 1, material); // point in octant 5
		setBlock(cx - z - 1, y, cz - x - 1, material); // point in octant 6
		setBlock(cx + z, y, cz - x - 1, material); // point in octant 7
		setBlock(cx + x, y, cz - z - 1, material); // point in octant 8
	}
	
	private void drawCircleBlocks(int cx, int cz, int x, int z, int y1, int y2, Material material) {
		for (int y = y1; y < y2; y++) {
			drawCircleBlocks(cx, cz, x, z, y, material);
		}
	}
	
	private void fillCircleBlocks(int cx, int cz, int x, int z, int y, Material material) {
		// Ref: Notes/BCircle.PDF
		setBlocks(cx - x - 1, cx - x, y, cz - z - 1, cz + z + 1, material); // point in octant 5
		setBlocks(cx - z - 1, cx - z, y, cz - x - 1, cz + x + 1, material); // point in octant 6
		setBlocks(cx + z, cx + z + 1, y, cz - x - 1, cz + x + 1, material); // point in octant 7
		setBlocks(cx + x, cx + x + 1, y, cz - z - 1, cz + z + 1, material); // point in octant 8
	}
	
	private void fillCircleBlocks(int cx, int cz, int x, int z, int y1, int y2, Material material) {
		for (int y = y1; y < y2; y++) {
			fillCircleBlocks(cx, cz, x, z, y, material);
		}
	}
	
	@Override	
	public void setCircle(int cx, int cz, int r, int y, Material material, boolean fill) {
		setCircle(cx, cz, r, y, y + 1, material, fill);
	}
	
	@Override	
	public void setCircle(int cx, int cz, int r, int y1, int y2, Material material, boolean fill) {
		// Ref: Notes/BCircle.PDF
		int x = r;
		int z = 0;
		int xChange = 1 - 2 * r;
		int zChange = 1;
		int rError = 0;
		
		while (x >= z) {
			if (fill)
				fillCircleBlocks(cx, cz, x, z, y1, y2, material);
			else
				drawCircleBlocks(cx, cz, x, z, y1, y2, material);
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
