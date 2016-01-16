package me.daddychurchill.CityWorld.Support;

import org.bukkit.Material;
import org.bukkit.World;
import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Factories.MaterialFactory;

public abstract class AbstractBlocks {

	public World world;
	public int width;
	public int height;
	public int sectionX;
	public int sectionZ;
	
	public final static int sectionBlockWidth = 16;
	
	public AbstractBlocks(CityWorldGenerator generator) {
		super();
		
		this.world = generator.getWorld();
		this.width = sectionBlockWidth;
		this.height = generator.height;
	}

	public final static int getBlockX(int sectionX, int x) {
		return sectionX * sectionBlockWidth + x;
	}
	
	public final static int getBlockZ(int sectionZ, int z) {
		return sectionZ * sectionBlockWidth + z;
	}
	
	public final int getBlockX(int x) {
		return getOriginX() + x;
	}

	public final int getBlockZ(int z) {
		return getOriginZ() + z;
	}

	public final int getOriginX() {
		return sectionX * width;
	}

	public final int getOriginZ() {
		return sectionZ * width;
	}

	public abstract void setBlockIfAir(int x, int y, int z, Material material);
	public abstract void setBlock(int x, int y, int z, Material material);
	public abstract void setBlocks(int x, int y1, int y2, int z, Material material);
	public abstract void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material);
	public abstract void setBlocks(int x1, int x2, int y, int z1, int z2, Material material);
	public abstract void setWalls(int x1, int x2, int y1, int y2, int z1, int z2, Material material);
	
	public abstract int setLayer(int blocky, Material material);
	public abstract int setLayer(int blocky, int height, Material material);
	public abstract int setLayer(int blocky, int height, int inset, Material material);
	
	public abstract void clearBlock(int x, int y, int z);
	public abstract boolean setEmptyBlock(int x, int y, int z, Material material);
	public abstract void setEmptyBlocks(int x1, int x2, int y, int z1, int z2, Material material);
	public abstract int findFirstEmpty(int x, int y, int z);
	public abstract int findFirstEmptyAbove(int x, int y, int z);
	public abstract int findLastEmptyAbove(int x, int y, int z);
	public abstract int findLastEmptyBelow(int x, int y, int z);

	public abstract void setCircle(int cx, int cz, int r, int y, Material material, boolean fill);
	public abstract void setCircle(int cx, int cz, int r, int y1, int y2, Material material, boolean fill);
	
	public final void setCircle(int cx, int cz, int r, int y, Material material) {
		setCircle(cx, cz, r, y, material, false);
	}
	
	public final void setCircle(int cx, int cz, int r, int y1, int y2, Material material) {
		setCircle(cx, cz, r, y1, y2, material, false);
	}
	
	public final void setSphere(int cx, int cy, int cz, int r, Material material, boolean fill) {
		for (int r1 = 1; r1 < r; r1++) {
			setCircle(cx, cz, r - r1, cy + r1, material, fill);
			setCircle(cx, cz, r - r1, cy - r1, material, fill);
		}
		setCircle(cx, cz, r, cy, material, fill);
	}

	public final void setBlocks(int x, int y1, int y2, int z, Material primary, Material secondary, MaterialFactory maker) {
		maker.placeMaterial(this, primary, secondary, x, y1, y2, z);
	}

	public final void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material primary, Material secondary, MaterialFactory maker) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				maker.placeMaterial(this, primary, secondary, x, y1, y2, z);
			}
		}
	}

	public void clearBlocks(int x, int y1, int y2, int z) {
		for (int y = y1; y < y2; y++)
			clearBlock(x, y, z);
	}

	public void clearBlocks(int x1, int x2, int y, int z1, int z2) {
		for (int x = x1; x < x2; x++)
			for (int z = z1; z < z2; z++)
				clearBlock(x, y, z);
	}
	
	public void clearBlocks(int x1, int x2, int y1, int y2, int z1, int z2) {
		for (int x = x1; x < x2; x++)
			for (int z = z1; z < z2; z++)
				for (int y = y1; y < y2; y++)
					clearBlock(x, y, z);
	}
	
	public void clearBlocks(int x, int y1, int y2, int z, Odds odds) {
		for (int y = y1; y < y2; y++)
			if (odds.flipCoin())
				clearBlock(x, y, z);
	}

	public void clearBlocks(int x1, int x2, int y, int z1, int z2, Odds odds) {
		for (int x = x1; x < x2; x++)
			for (int z = z1; z < z2; z++)
				if (odds.flipCoin())
					clearBlock(x, y, z);
	}
	
	public void clearBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Odds odds) {
		for (int x = x1; x < x2; x++)
			for (int z = z1; z < z2; z++)
				for (int y = y1; y < y2; y++)
					if (odds.flipCoin())
						clearBlock(x, y, z);
	}
	
}
