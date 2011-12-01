package me.daddychurchill.CityWorld.Support;

import java.util.Arrays;

import org.bukkit.Material;

public class ByteChunk {
	public final static int Width = 16;
	public final static int Height = 128;

	public int X;
	public int Z;
	public byte[] blocks;
		
	public ByteChunk (int chunkX, int chunkZ) {
		super();
		X = chunkX;
		Z = chunkZ;
		blocks = new byte[Width * Width * Height];
	}
	
	public void setBlock(int x, int y, int z, byte materialId) {
		blocks[(x * Width + z) * Height + y] = materialId;
	}
	
	public byte getBlock(int x, int y, int z) {
		return blocks[(x * Width + z) * Height + y];
	}
	
	public void setBlocks(int x, int y1, int y2, int z, byte materialId) {
		int xz = (x * Width + z) * Height;
		Arrays.fill(blocks, xz + y1, xz + y2, materialId);
	}
	
	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, byte materialId) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				int xz = (x * Width + z) * Height;
				Arrays.fill(blocks, xz + y1, xz + y2, materialId);
			}
		}
	}
	
	public void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, byte primaryId, byte secondaryId, MaterialFactory maker) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				int xz = (x * Width + z) * Height;
				Arrays.fill(blocks, xz + y1, xz + y2, maker.pickMaterial(primaryId, secondaryId, x, z));
			}
		}
	}
	
	public int setLayer(int blocky, byte materialId) {
		setBlocks(0, Width, blocky, blocky + 1, 0, Width, materialId);
		return blocky + 1;
	}
	
	public int setLayer(int blocky, int height, byte materialId) {
		setBlocks(0, Width, blocky, blocky + height, 0, Width, materialId);
		return blocky + height;
	}
	
	public int setLayer(int blocky, int height, int inset, byte materialId) {
		setBlocks(inset, Width - inset, blocky, blocky + height, inset, Width - inset, materialId);
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
		
// SIMPLE CONVERSION... only rendered a single octant
//		// clamp r
//		r = Math.min(r, Width / 2);
//		
//		// work space
//		int x = 0;
//		int z = r;
//		int d = 1 - r;
//		
//		// initial block
//		setBlock(x, y, z, materialId);
//		
//		// figure out the rest of the circle
//		while (x < z) {
//			if (d < 0) {
//				d = d + 2 * x + 3;
//				x = x + 1;
//			} else {
//				d = d + 2 * (x - z) + 5;
//				x = x + 1;
//				z = z - 1;
//			}
//			setBlock(x, y, z, materialId);
//		}

// ORIGINAL CODE
//			int x1=0, y=r, d = 1-r;
//			circlePoints(x, y); //This will the point on the circle
//			while(x < y) {
//				if (d < 0) {
//					d = d+2*x+3;
//					x = x+1;
//				} else {
//					d = d+2*(x-y)+5;
//					x = x+1;
//					y = y-1;
//				}
//				circlePoints(x,y); //This will the point on the circle
//			}
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
