package me.daddychurchill.CityWorld;

import java.util.Random;

import org.bukkit.Material;

public class Chunk {
	public byte[] blocks;
		
	Chunk () {
		blocks = new byte[32768];
	}
	
	protected int coordsToByte(int x, int y, int z){
		return (x * 16 + z) * 128 + y;
	}
	
	public void setLayer(Random random, int y, Material material) {
		int x, z;
		byte materialId = (byte) material.getId();
		
		for (x = 0; x < 16; x++) {
			for (z = 0; z < 16; z++) {
				blocks[this.coordsToByte(x, y, z)] = materialId;
			}
		}
	}

	private void setFloor(Random random, int y, Material material) {
		int i, x, z;
		byte materialId = (byte) material.getId();
		
		for (i = 1; i < 15; i++) {
			
			// TODO: add options back in to make windows less random
			// don't put windows on the corners, it looks strange
			if (i == 1 || i == 14 || random.nextInt(4) != 0) {
				blocks[this.coordsToByte(i, y, 1)] = materialId;
				blocks[this.coordsToByte(i, y + 1, 1)] = materialId;
				blocks[this.coordsToByte(i, y + 2, 1)] = materialId;
			}
			if (i == 1 || i == 14 || random.nextInt(4) != 0) {
				blocks[this.coordsToByte(i, y, 14)] = materialId;
				blocks[this.coordsToByte(i, y + 1, 14)] = materialId;
				blocks[this.coordsToByte(i, y + 2, 14)] = materialId;
			}

			if (i == 1 || i == 14 || random.nextInt(4) != 0) {
				blocks[this.coordsToByte(1, y, i)] = materialId;
				blocks[this.coordsToByte(1, y + 1, i)] = materialId;
				blocks[this.coordsToByte(1, y + 2, i)] = materialId;
			}
			if (i == 1 || i == 14 || random.nextInt(4) != 0) {
				blocks[this.coordsToByte(14, y, i)] = materialId;
				blocks[this.coordsToByte(14, y + 1, i)] = materialId;
				blocks[this.coordsToByte(14, y + 2, i)] = materialId;
			}
		}
		
		for (x = 1; x < 15; x++) {
			for (z = 1; z < 15; z++) {
				blocks[this.coordsToByte(x, y + 3, z)] = materialId;
			}
		}
	}
	
	public void setFloors(Random random, int y, int floors, Material material) {
		int floor;
		
		for (floor = 0; floor < floors; floor++) {
			setFloor(random, y + floor * 4, material);
		}
	}
	
	private void setSidewalkPart(int y, int xStart, int zStart, int xSize, int zSize, Material material) {
		int x, z;
		byte materialId = (byte) material.getId();
		
		for (x = xStart; x < xStart + xSize; x++) {
			for (z = zStart; z < zStart + zSize; z++) {
				blocks[this.coordsToByte(x, y, z)] = materialId;
			}
		}
	}

	public void setSidewalks(Random random, int y, Material material, boolean northsouth,
			boolean eastwest) {
		
		// corners
		setSidewalkPart(y, 0, 0, 3, 3, material);
		setSidewalkPart(y, 13, 0, 3, 3, material);
		setSidewalkPart(y, 0, 13, 3, 3, material);
		setSidewalkPart(y, 13, 13, 3, 3, material);
		
		// strait bits
		if (!northsouth) {
			setSidewalkPart(y, 3, 0, 10, 3, material);
			setSidewalkPart(y, 3, 13, 10, 3, material);
		}
		if (!eastwest) {
			setSidewalkPart(y, 0, 3, 3, 10, material);
			setSidewalkPart(y, 13, 3, 3, 10, material);
		}
	}
}
