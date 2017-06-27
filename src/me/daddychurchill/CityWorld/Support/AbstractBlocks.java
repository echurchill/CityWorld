package me.daddychurchill.CityWorld.Support;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.material.MaterialData;

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
	
	public abstract void setBlock(int x, int y, int z, MaterialData material);
//	public void setBlock(int x, int y, int z, RealMaterial material) {
//		setBlock(x, y, z, material.);
//	}
	
//	public abstract void setStair(int x, int y, int z, Material material, BadMagic.Stair direction);
//	public abstract void setBlockTypeAndColor(int x, int y, int z, Material material, DyeColor color);
	
	public final void setWalls(int x1, int x2, int y, int z1, int z2, Material material) {
		setWalls(x1, x2, y, y + 1, z1, z2, material);
	}
	
	public abstract int setLayer(int blocky, Material material);
	public abstract int setLayer(int blocky, int height, Material material);
	public abstract int setLayer(int blocky, int height, int inset, Material material);
	
	public abstract boolean isEmpty(int x, int y, int z);
	public abstract void clearBlock(int x, int y, int z);
	public abstract boolean setEmptyBlock(int x, int y, int z, Material material);
	public abstract void setEmptyBlocks(int x1, int x2, int y, int z1, int z2, Material material);
	
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
	
	public final void setCircle(int cx, int cz, int r, int y, Material material, boolean fill) {
		setCircle(cx, cz, r, y, y + 1, material, fill);
	}
	
	public final void setCircle(int cx, int cz, int r, int y1, int y2, Material material, boolean fill) {
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
		return y1;
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
	
	public final void setBlocksUpward(int x, int y1, int z, int maxY, Material material) {
		int y2 = findLastEmptyAbove(x, y1, z, maxY);
		setBlocks(x, y1, y2 + 1, z, material);
	}
	
	public final void setBlocksDownward(int x, int y2, int z, int minY, Material material) {
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

	public final void clearBlocks(int x, int y1, int y2, int z) {
		for (int y = y1; y < y2; y++)
			clearBlock(x, y, z);
	}

	public final void clearBlocks(int x1, int x2, int y, int z1, int z2) {
		for (int x = x1; x < x2; x++)
			for (int z = z1; z < z2; z++)
				clearBlock(x, y, z);
	}
	
	public final void clearBlocks(int x1, int x2, int y1, int y2, int z1, int z2) {
		for (int x = x1; x < x2; x++)
			for (int z = z1; z < z2; z++)
				for (int y = y1; y < y2; y++)
					clearBlock(x, y, z);
	}
	
	public final void clearBlocks(int x, int y1, int y2, int z, Odds odds) {
		for (int y = y1; y < y2; y++)
			if (odds.flipCoin())
				clearBlock(x, y, z);
	}

	public final void clearBlocks(int x1, int x2, int y, int z1, int z2, Odds odds) {
		for (int x = x1; x < x2; x++)
			for (int z = z1; z < z2; z++)
				if (odds.flipCoin())
					clearBlock(x, y, z);
	}
	
	public final void clearBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Odds odds) {
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
	
	public final void airoutLayer(CityWorldGenerator generator, int blocky) {
		airoutBlocks(generator, 0, 16, blocky, 0, 16, false);
	}
	
	public final void airoutLayer(CityWorldGenerator generator, int blocky, boolean forceIt) {
		airoutBlocks(generator, 0, 16, blocky, 0, 16, forceIt);
	}
	
	public final void airoutLayer(CityWorldGenerator generator, int blocky, int height) {
		airoutBlocks(generator, 0, 16, blocky, blocky + height, 0, 16, false);
	}
	
	public final void airoutLayer(CityWorldGenerator generator, int blocky, int height, boolean forceIt) {
		airoutBlocks(generator, 0, 16, blocky, blocky + height, 0, 16, forceIt);
	}
	
	public final void airoutLayer(CityWorldGenerator generator, int blocky, int height, int inset) {
		airoutBlocks(generator, inset, 16 - inset, blocky, blocky + height, inset, 16 - inset, false);
	}

	public final void airoutLayer(CityWorldGenerator generator, int blocky, int height, int inset, boolean forceIt) {
		airoutBlocks(generator, inset, 16 - inset, blocky, blocky + height, inset, 16 - inset, forceIt);
	}

	public final void pepperBlocks(int x, int y1, int y2, int z, Odds odds, Material material) {
		pepperBlocks(x, y1, y2, z, odds, Odds.oddsLikely, material);
	}

	public final void pepperBlocks(int x1, int x2, int y, int z1, int z2, Odds odds, Material material) {
		pepperBlocks(x1, x2, y, z1, z2, odds, Odds.oddsLikely, material);
	}
	
	public final void pepperBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Odds odds, Material material) {
		pepperBlocks(x1, x2, y1, y2, z1, z2, odds, Odds.oddsLikely, material);
	}
	
	public final void pepperBlocks(int x, int y1, int y2, int z, Odds odds, double theOdds, Material material) {
		for (int y = y1; y < y2; y++)
			if (odds.playOdds(theOdds))
				setBlock(x, y, z, material);
	}

	public final void pepperBlocks(int x1, int x2, int y, int z1, int z2, Odds odds, double theOdds, Material material) {
		for (int x = x1; x < x2; x++)
			for (int z = z1; z < z2; z++)
				if (odds.playOdds(theOdds))
					setBlock(x, y, z, material);
	}
	
	public final void pepperBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Odds odds, double theOdds, Material material) {
		for (int x = x1; x < x2; x++)
			for (int z = z1; z < z2; z++)
				for (int y = y1; y < y2; y++)
					if (odds.playOdds(theOdds))
						setBlock(x, y, z, material);
	}
	
	public final void pepperBlocks(int x1, int x2, int y1, int y2, int z1, int z2, Odds odds, double bottomOdds,
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

	public final void setArcNorthWest(int inset, int y1, int y2, Material primary, boolean fill) {
		setArcNorthWest(inset, y1, y2, primary, primary, null, fill);
	}

	public final void setArcSouthWest(int inset, int y1, int y2, Material primary, boolean fill) {
		setArcSouthWest(inset, y1, y2, primary, primary, null, fill);
	}

	public final void setArcNorthEast(int inset, int y1, int y2, Material primary, boolean fill) {
		setArcNorthEast(inset, y1, y2, primary, primary, null, fill);
	}
	
	public final void setArcSouthEast(int inset, int y1, int y2, Material primary, boolean fill) {
		setArcSouthEast(inset, y1, y2, primary, primary, null, fill);
	}

	public final void setArcNorthWest(int inset, int y1, int y2, Material primary, Material secondary, MaterialFactory maker) {
		setArcNorthWest(inset, y1, y2, primary, secondary, maker, false);
	}
	
	public final void setArcSouthWest(int inset, int y1, int y2, Material primary, Material secondary, MaterialFactory maker) {
		setArcSouthWest(inset, y1, y2, primary, secondary, maker, false);
	}
	
	public final void setArcNorthEast(int inset, int y1, int y2, Material primary, Material secondary, MaterialFactory maker) {
		setArcNorthEast(inset, y1, y2, primary, secondary, maker, false);
	}
	
	public final void setArcSouthEast(int inset, int y1, int y2, Material primary, Material secondary, MaterialFactory maker) {
		setArcSouthEast(inset, y1, y2, primary, secondary, maker, false);
	}
	
	public final void setArcNorthWest(int inset, int y1, int y2, Material primary, Material secondary, MaterialFactory maker, boolean fill) {
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
	
	public final void setArcSouthWest(int inset, int y1, int y2, Material primary, Material secondary, MaterialFactory maker, boolean fill) {
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
	
	public final void setArcNorthEast(int inset, int y1, int y2, Material primary, Material secondary, MaterialFactory maker, boolean fill) {
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
	
	public final void setArcSouthEast(int inset, int y1, int y2, Material primary, Material secondary, MaterialFactory maker, boolean fill) {
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
	
}
