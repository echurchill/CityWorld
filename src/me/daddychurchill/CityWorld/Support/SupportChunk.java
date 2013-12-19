package me.daddychurchill.CityWorld.Support;

import me.daddychurchill.CityWorld.WorldGenerator;

import org.bukkit.Material;
import org.bukkit.World;

public abstract class SupportChunk {
	
	public World world;
	public int chunkX;
	public int chunkZ;
	public int worldX;
	public int worldZ;
	public int width;
	public int height;
	
	public static final int chunksBlockWidth = 16;
	public static final int sectionsPerChunk = 16;
	
	public SupportChunk(WorldGenerator generator) {
		super();
		
		world = generator.getWorld();
		
		width = chunksBlockWidth;
		height = generator.height;
	}
	
	public static int getBlockX(int chunkX, int x) {
		return chunkX * chunksBlockWidth + x;
	}
	
	public static int getBlockZ(int chunkZ, int z) {
		return chunkZ * chunksBlockWidth + z;
	}
	
	public final int getBlockX(int x) {
		return getOriginX() + x;
	}

	public final int getBlockZ(int z) {
		return getOriginZ() + z;
	}

	public final int getOriginX() {
		return chunkX * width;
	}

	public final int getOriginZ() {
		return chunkZ * width;
	}

	//TODO rename this to getBlockTypeId
	public abstract byte getBlockType(int x, int y, int z);
	
	//TODO these should really return booleans
	public abstract void setBlock(int x, int y, int z, byte materialId);
	public abstract void setBlocks(int x1, int x2, int y, int z1, int z2, byte materialId);
	public abstract void setBlocks(int x, int y1, int y2, int z, byte materialId);
	
	public abstract void clearBlock(int x, int y, int z);
	public abstract void clearBlocks(int x, int y1, int y2, int z);
	public abstract void clearBlocks(int x1, int x2, int y1, int y2, int z1, int z2);
	
	public final boolean isType(int x, int y, int z, byte type) {
		return getBlockType(x, y, z) == type;
	}
	
	public final boolean isType(int x, int y, int z, Material material) {
		return getBlockType(x, y, z) == BlackMagic.getMaterialId(material);
	}
	
	public final boolean isOfTypes(int x, int y, int z, Material ... types) {
		byte type = getBlockType(x, y, z);
		for (Material test : types)
			if (type == BlackMagic.getMaterialId(test))
				return true;
		return false;
	}
	
	public final boolean isOfTypes(int x, int y, int z, byte ... types) {
		byte type = getBlockType(x, y, z);
		for (int test : types)
			if (type == test)
				return true;
		return false;
	}
	
	public final boolean isEmpty(int x, int y, int z) {
		return getBlockType(x, y, z) == BlackMagic.airId;
	}
	
	public final boolean isPlantable(int x, int y, int z) {
		return getBlockType(x, y, z) == BlackMagic.grassId;
	}
	
	public final boolean isWater(int x, int y, int z) {
		return isOfTypes(x, y, z, BlackMagic.stillWaterId, BlackMagic.fluidWaterId);
	}
	
	public final boolean isLava(int x, int y, int z) {
		return isOfTypes(x, y, z, BlackMagic.stillLavaId, BlackMagic.fluidLavaId);
	}
	
	public final boolean isLiquid(int x, int y, int z) {
		return isOfTypes(x, y, z, BlackMagic.stillWaterId, BlackMagic.stillLavaId, 
								  BlackMagic.fluidWaterId, BlackMagic.fluidLavaId, 
								  BlackMagic.iceId);
	}
	
	public final boolean isPartialHeight(int x, int y, int z) {
		//TODO this list really should be extended to support all partial height blocks
		return isOfTypes(x, y, z, BlackMagic.airId, 
								  BlackMagic.stepStoneId, BlackMagic.stepWoodId, 
								  BlackMagic.plateStoneId, BlackMagic.plateWoodId, 
								  BlackMagic.stillWaterId, BlackMagic.stillLavaId, 
								  BlackMagic.fluidWaterId, BlackMagic.fluidLavaId);
	}
	
	public final boolean isSurroundedByEmpty(int x, int y, int z) {
		return (x > 0 && x < 15 && z > 0 && z < 15) && 
			   (isEmpty(x - 1, y, z) && 
				isEmpty(x + 1, y, z) &&
				isEmpty(x, y, z - 1) && 
				isEmpty(x, y, z + 1));
	}
	
	public final boolean isSurroundedByWater(int x, int y, int z) {
		return (x > 0 && x < 15 && z > 0 && z < 15) && 
			   (isWater(x - 1, y, z) || 
				isWater(x + 1, y, z) ||
				isWater(x, y, z - 1) || 
				isWater(x, y, z + 1));
	}
	
	public final void setBlocks(int x, int y1, int y2, int z, byte primaryId, byte secondaryId, MaterialFactory maker) {
		maker.placeMaterial(this, primaryId, secondaryId, x, y1, y2, z);
	}
	
	public final void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, byte primaryId, byte secondaryId, MaterialFactory maker) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				setBlocks(x, y1, y2, z, primaryId, secondaryId, maker);
			}
		}
	}
	
	private void drawCircleBlocks(int cx, int cz, int x, int z, int y, byte materialId) {
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
	
	private void drawCircleBlocks(int cx, int cz, int x, int z, int y1, int y2, byte materialId) {
		for (int y = y1; y < y2; y++) {
			drawCircleBlocks(cx, cz, x, z, y, materialId);
		}
	}
	
	private void fillCircleBlocks(int cx, int cz, int x, int z, int y, byte materialId) {
		// Ref: Notes/BCircle.PDF
		setBlocks(cx - x - 1, cx - x, y, cz - z - 1, cz + z + 1, materialId); // point in octant 5
		setBlocks(cx - z - 1, cx - z, y, cz - x - 1, cz + x + 1, materialId); // point in octant 6
		setBlocks(cx + z, cx + z + 1, y, cz - x - 1, cz + x + 1, materialId); // point in octant 7
		setBlocks(cx + x, cx + x + 1, y, cz - z - 1, cz + z + 1, materialId); // point in octant 8
	}
	
	private void fillCircleBlocks(int cx, int cz, int x, int z, int y1, int y2, byte materialId) {
		for (int y = y1; y < y2; y++) {
			fillCircleBlocks(cx, cz, x, z, y, materialId);
		}
	}
	
	public final void setCircle(int cx, int cz, int r, int y, Material material, boolean fill) {
		setCircle(cx, cz, r, y, y + 1, BlackMagic.getMaterialId(material), fill);
	}
	
	public final void setCircle(int cx, int cz, int r, int y, byte materialId, boolean fill) {
		setCircle(cx, cz, r, y, y + 1, materialId, fill);
	}
	
	public final void setCircle(int cx, int cz, int r, int y1, int y2, Material material, boolean fill) {
		setCircle(cx, cz, r, y1, y2, BlackMagic.getMaterialId(material), fill);
	}
	
	public final void setCircle(int cx, int cz, int r, int y1, int y2, byte materialId, boolean fill) {
		// Ref: Notes/BCircle.PDF
		int x = r;
		int z = 0;
		int xChange = 1 - 2 * r;
		int zChange = 1;
		int rError = 0;
		
		while (x >= z) {
			if (fill)
				fillCircleBlocks(cx, cz, x, z, y1, y2, materialId);
			else
				drawCircleBlocks(cx, cz, x, z, y1, y2, materialId);
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
	
	public final void setSphere(int cx, int cy, int cz, int r, byte materialId, boolean fill) {
		for (int r1 = 1; r1 < r; r1++) {
			setCircle(cx, cz, r - r1, cy + r1, materialId, fill);
			setCircle(cx, cz, r - r1, cy - r1, materialId, fill);
		}
		setCircle(cx, cz, r, cy, materialId, fill);
	}
}
