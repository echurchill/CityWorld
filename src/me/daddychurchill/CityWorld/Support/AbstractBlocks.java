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

	public abstract void setBlockIfEmpty(int x, int y, int z, Material material);
	public abstract void setBlock(int x, int y, int z, Material material);
	public abstract void setBlocks(int x, int y1, int y2, int z, Material material);
	public abstract void setBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Material material);
	public abstract void setBlocks(int x1, int x2, int y, int z1, int z2, Material material);
	public abstract void setWalls(int x1, int x2, int y1, int y2, int z1, int z2, Material material);
	
	public void setWalls(int x1, int x2, int y, int z1, int z2, Material material) {
		setWalls(x1, x2, y, y + 1, z1, z2, material);
	}
	
	public abstract int setLayer(int blocky, Material material);
	public abstract int setLayer(int blocky, int height, Material material);
	public abstract int setLayer(int blocky, int height, int inset, Material material);
	
	public abstract boolean isEmpty(int x, int y, int z);
	public abstract void clearBlock(int x, int y, int z);
	public abstract boolean setEmptyBlock(int x, int y, int z, Material material);
	public abstract void setEmptyBlocks(int x1, int x2, int y, int z1, int z2, Material material);
	
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

//	public int findFirstEmpty(int x, int y, int z) {
//		return findFirstEmpty(x, y, z, 0, height);
//	}
	
	public final int findFirstEmpty(int x, int y, int z, int minY, int maxY) {
		if (isEmpty(x, y, z))
			return findLastEmptyBelow(x, y, z, minY);
		else
			return findFirstEmptyAbove(x, y, z, maxY);
	}
	
//	public int findFirstEmptyAbove(int x, int y, int z) {
//		return findFirstEmptyAbove(x, y, z, height);
//	}
	
	public final int findFirstEmptyAbove(int x, int y, int z, int maxY) {
		int y1 = y;
		while (y1 < maxY - 1) {
			if (isEmpty(x, y1, z))
				return y1;
			y1++;
		}
		return height - 1;
	}
	
//	public int findLastEmptyAbove(int x, int y, int z) {
//		return findLastEmptyAbove(x, y, z, height);
//	}
	
	public final int findLastEmptyAbove(int x, int y, int z, int maxY) {
		int y1 = y;
		while (y1 < maxY - 1 && isEmpty(x, y1 + 1, z)) {
			y1++;
		}
		return y1;
	}
	
//	public int findLastEmptyBelow(int x, int y, int z) {
//		return findLastEmptyBelow(x, y, z, 0);
//	}
	
	public final int findLastEmptyBelow(int x, int y, int z, int minY) {
		int y1 = y;
		while (y1 > minY && isEmpty(x, y1 - 1, z)) {
			y1--;
		}
		return y1;
	}
	
	public void setBlocksUpward(int x, int y1, int z, int maxY, Material material) {
		int y2 = findLastEmptyAbove(x, y1, z, maxY);
		setBlocks(x, y1, y2 + 1, z, material);
	}
	
	public void setBlocksDownward(int x, int y2, int z, int minY, Material material) {
		int y1 = findLastEmptyBelow(x, y2, z, minY);
		setBlocks(x, y1, y2, z, material);
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
	
	public final void airoutBlock(CityWorldGenerator generator, int x, int y, int z) {
		airoutBlock(generator, x, y, z, false);
	}

	public final void airoutBlock(CityWorldGenerator generator, int x, int y, int z, boolean forceIt) {
		if (forceIt || generator.shapeProvider.clearAtmosphere(generator))
			setBlock(x, y, z, generator.shapeProvider.findAtmosphereMaterialAt(generator, y));
	}

	public final void airoutBlocks(CityWorldGenerator generator, int x, int y1, int y2, int z) {
		airoutBlocks(generator, x, y1, y2, z, false);
	}

	public final void airoutBlocks(CityWorldGenerator generator, int x, int y1, int y2, int z, boolean forceIt) {
		if (forceIt || generator.shapeProvider.clearAtmosphere(generator))
			for (int y = y1; y < y2; y++)
				setBlock(x, y, z, generator.shapeProvider.findAtmosphereMaterialAt(generator, y));
	}

	public final void airoutBlocks(CityWorldGenerator generator, int x1, int x2, int y, int z1, int z2) {
		airoutBlocks(generator, x1, x2, y, z1, z2, false);
	}
	
	public final void airoutBlocks(CityWorldGenerator generator, int x1, int x2, int y, int z1, int z2, boolean forceIt) {
		if (forceIt || generator.shapeProvider.clearAtmosphere(generator)) {
			Material air = generator.shapeProvider.findAtmosphereMaterialAt(generator, y);
			for (int x = x1; x < x2; x++)
				for (int z = z1; z < z2; z++)
					setBlock(x, y, z, air);
		}
	}
	
	public final void airoutBlocks(CityWorldGenerator generator, int x1, int x2, int y1, int y2, int z1, int z2) {
		airoutBlocks(generator, x1, x2, y1, y2, z1, z2, false);
	}
	
	public final void airoutBlocks(CityWorldGenerator generator, int x1, int x2, int y1, int y2, int z1, int z2, boolean forceIt) {
		if (forceIt || generator.shapeProvider.clearAtmosphere(generator))
			for (int y = y1; y < y2; y++) {
				Material air = generator.shapeProvider.findAtmosphereMaterialAt(generator, y);
				for (int x = x1; x < x2; x++)
					for (int z = z1; z < z2; z++)
						setBlock(x, y, z, air);
			}
	}
	
	public void airoutLayer(CityWorldGenerator generator, int blocky) {
		airoutBlocks(generator, 0, 16, blocky, 0, 16, false);
	}
	
	public void airoutLayer(CityWorldGenerator generator, int blocky, boolean forceIt) {
		airoutBlocks(generator, 0, 16, blocky, 0, 16, forceIt);
	}
	
	public void airoutLayer(CityWorldGenerator generator, int blocky, int height) {
		airoutBlocks(generator, 0, 16, blocky, blocky + height, 0, 16, false);
	}
	
	public void airoutLayer(CityWorldGenerator generator, int blocky, int height, boolean forceIt) {
		airoutBlocks(generator, 0, 16, blocky, blocky + height, 0, 16, forceIt);
	}
	
	public void airoutLayer(CityWorldGenerator generator, int blocky, int height, int inset) {
		airoutBlocks(generator, inset, 16 - inset, blocky, blocky + height, inset, 16 - inset, false);
	}

	public void airoutLayer(CityWorldGenerator generator, int blocky, int height, int inset, boolean forceIt) {
		airoutBlocks(generator, inset, 16 - inset, blocky, blocky + height, inset, 16 - inset, forceIt);
	}

	public void pepperBlocks(int x, int y1, int y2, int z, Odds odds, Material material) {
		pepperBlocks(x, y1, y2, z, odds, Odds.oddsLikely, material);
	}

	public void pepperBlocks(int x1, int x2, int y, int z1, int z2, Odds odds, Material material) {
		pepperBlocks(x1, x2, y, z1, z2, odds, Odds.oddsLikely, material);
	}
	
	public void pepperBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Odds odds, Material material) {
		pepperBlocks(x1, x2, y1, y2, z1, z2, odds, Odds.oddsLikely, material);
	}
	
	public void pepperBlocks(int x, int y1, int y2, int z, Odds odds, double theOdds, Material material) {
		for (int y = y1; y < y2; y++)
			if (odds.playOdds(theOdds))
				setBlock(x, y, z, material);
	}

	public void pepperBlocks(int x1, int x2, int y, int z1, int z2, Odds odds, double theOdds, Material material) {
		for (int x = x1; x < x2; x++)
			for (int z = z1; z < z2; z++)
				if (odds.playOdds(theOdds))
					setBlock(x, y, z, material);
	}
	
	public void pepperBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Odds odds, double theOdds, Material material) {
		for (int x = x1; x < x2; x++)
			for (int z = z1; z < z2; z++)
				for (int y = y1; y < y2; y++)
					if (odds.playOdds(theOdds))
						setBlock(x, y, z, material);
	}
	
	public void pepperBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Odds odds, double bottomOdds,
			double topOdds, Material material) {
		if (bottomOdds == topOdds)
			pepperBlocks(x1, x2, y1, y2, z1, z2, odds, bottomOdds, material);
		else {
			// calculate odds stepper
			int atStep = 0;
			int steps = y2 - y1;
			double stepOdds = 0;
			if (topOdds > bottomOdds)
				stepOdds = (topOdds - bottomOdds) / steps;
			else
				stepOdds = (bottomOdds - topOdds) / steps;

			// now do it
			for (int y = y1; y < y2; y++) {
				double theOdds = bottomOdds + atStep * stepOdds;
				for (int x = x1; x < x2; x++) {
					for (int z = z1; z < z2; z++) {
						if (odds.playOdds(theOdds))
							setBlock(x, y, z, material);
					}
				}
				atStep++;
			}
		}
	}
}
