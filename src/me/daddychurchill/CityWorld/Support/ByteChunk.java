package me.daddychurchill.CityWorld.Support;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.World;

public class ByteChunk {
		
	public int chunkX;
	public int chunkZ;
	public byte[] blocks;
	public int width;
	public int height;
	
	private static byte airId = (byte) Material.AIR.getId();
		
	public ByteChunk (World world, int chunkX, int chunkZ) {
		super();
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		this.width = 16;
		this.height = world.getMaxHeight();
		this.blocks = new byte[width * width * height];
	}
	
	public void setBlock(int x, int y, int z, byte materialId) {
		blocks[(x * width + z) * height + y] = materialId;
	}
	
	public void setBlockIfAir(int x, int y, int z, byte materialId) {
		if (blocks[(x * width + z) * height + y] == airId &&
			blocks[(x * width + z) * height + y - 1] != airId)
			blocks[(x * width + z) * height + y] = materialId;
	}
	
	public byte getBlock(int x, int y, int z) {
		return blocks[(x * width + z) * height + y];
	}
	
	public void setBlocks(int x, int y1, int y2, int z, byte materialId) {
		int xz = (x * width + z) * height;
		Arrays.fill(blocks, xz + y1, xz + y2, materialId);
	}
	
	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, byte materialId) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				int xz = (x * width + z) * height;
				Arrays.fill(blocks, xz + y1, xz + y2, materialId);
			}
		}
	}
	
	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, byte primaryId, byte secondaryId, MaterialFactory maker) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				int xz = (x * width + z) * height;
				Arrays.fill(blocks, xz + y1, xz + y2, maker.pickMaterial(primaryId, secondaryId, x, z));
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
	
//	private void setSafeBlock(int x, int y, int z, byte materialId) {
//		if (!safeBlock || x >= 0 && x < Width && z >= 0 && z < Width && y >= 0 && y < Height)
//			setBlock(x, y, z, materialId);
//		else
//			CityWorld.log.info("Block out of bounds: " + x + ", " + y + ", " + z);
//	}
//	
//	private void setSafeBlocks(int x, int y1, int y2, int z, byte materialId) {
//		if (!safeBlock || x >= 0 && x < Width &&
//						  z >= 0 && z < Width && 
//						  y1 >= 0 && y1 < Height && y2 > 0 && y2 <= Height)
//			setBlocks(x, y1, y2, z, materialId);
//		else
//			CityWorld.log.info("Blocks out of bounds: " + x + ", " + y1 + "-" + y2 + ", " + z);
//	}
//	
//	private void setSafeBlocks(int x1, int x2, int y1, int y2, int z1, int z2, byte materialId) {
//		if (!safeBlock || x1 >= 0 && x1 < Width && x2 > 0 && x2 <= Width && 
//						  z1 >= 0 && z1 < Width && z2 > 0 && z2 <= Width && 
//						  y1 >= 0 && y1 < Height && y2 > 0 && y2 <= Height)
//			setBlocks(x1, x2, y1, y2, z1, z2, materialId);
//		else
//			CityWorld.log.info("Blocks out of bounds: " + x1 + "-" + x2 + ", " 
//														+ y1 + "-" + y2 + ", " 
//														+ z1 + "-" + z2);
//	}
	
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
	
	//TODO Debug
	public void drawCoordinate(int X, int Z, int blocky, boolean andNorth) {
		drawNumber(X, 0, 3, blocky + 1);
		drawNumber(Z, 0, 8, blocky + 1);
		if (andNorth) {
			drawNorth(0, 14, blocky + 1);
		}
	}
	
	//TODO Debug
	private void drawNorth(int X, int Z, int blocky) {
		byte id = (byte) Material.GLOWSTONE.getId();
		this.setBlock(X + 4, blocky, Z - 2, id);
		
		this.setBlock(X + 3, blocky, Z - 1, id);
		this.setBlock(X + 3, blocky, Z - 2, id);
		this.setBlock(X + 3, blocky, Z - 3, id);
		
		this.setBlock(X + 2, blocky, Z    , id);
		this.setBlock(X + 2, blocky, Z - 1, id);
		this.setBlock(X + 2, blocky, Z - 2, id);
		this.setBlock(X + 2, blocky, Z - 3, id);
		this.setBlock(X + 2, blocky, Z - 4, id);

		this.setBlock(X + 1, blocky, Z - 2, id);
		this.setBlock(X    , blocky, Z - 2, id);
	}
	
	//TODO Debug
	private void drawNumber(int I, int X, int Z, int Y) {
		drawPixel(I, X + 4, Z - 2, Y,    2, 3, 4, 5,    7, 8, 9, 0);
		drawPixel(I, X + 4, Z - 1, Y,    2, 3,    5, 6, 7, 8, 9, 0);
		drawPixel(I, X + 4, Z - 0, Y, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0);

		drawPixel(I, X + 3, Z - 2, Y,          4, 5, 6,    8, 9, 0);
		drawPixel(I, X + 3, Z - 0, Y, 1, 2, 3, 4,       7, 8, 9, 0);

		drawPixel(I, X + 2, Z - 2, Y,    2, 3, 4, 5, 6,    8, 9, 0);
		drawPixel(I, X + 2, Z - 1, Y,    2, 3, 4, 5, 6,    8, 9   );
		drawPixel(I, X + 2, Z - 0, Y, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0);

		drawPixel(I, X + 1, Z - 2, Y,    2,          6,    8,    0);
		drawPixel(I, X + 1, Z - 0, Y, 1,    3, 4, 5, 6, 7, 8, 9, 0);

		drawPixel(I, X + 0, Z - 2, Y,    2, 3,    5, 6,    8,    0);
		drawPixel(I, X + 0, Z - 1, Y,    2, 3,    5, 6,    8,    0);
		drawPixel(I, X + 0, Z - 0, Y, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0);
	}
	
	//TODO Debug
	private void drawPixel(int I, int X, int Z, int Y, int ... vals) {
		boolean setit = false;
		for (int v : vals) {
			if (I == v) {
				setit = true;
				break;
			}
		}
		if (setit) {
			setBlock(X, Y, Z, (byte) Material.GLOWSTONE.getId());
		}
	}
}
