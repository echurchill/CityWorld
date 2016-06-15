package me.daddychurchill.CityWorld.Support;

import java.util.Stack;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.material.MaterialData;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;

public final class WorldBlocks extends SupportBlocks {
	
	//====================
	//WARNING: the x,z coordinates in this variant of SupportSection are world absolute (unlike init and real sections)
	//====================

	protected Odds odds;
	protected int farthestFall;
	
	public WorldBlocks(CityWorldGenerator generator, Odds odds) {
		super(generator);
		
		this.odds = odds;
		this.farthestFall = generator.streetLevel - 6; // just a bit more
	}

	@Override
	public Block getActualBlock(int x, int y, int z) {
		return world.getBlockAt(x, y, z);
	}
	
	@Override
	public boolean isSurroundedByEmpty(int x, int y, int z) {
		return isEmpty(x - 1, y, z) && 
				isEmpty(x + 1, y, z) &&
				isEmpty(x, y, z - 1) && 
				isEmpty(x, y, z + 1);
	}
	
	@Override
	public boolean isByWater(int x, int y, int z) {
		return isWater(x - 1, y, z) || 
				isWater(x + 1, y, z) ||
				isWater(x, y, z - 1) || 
				isWater(x, y, z + 1);
	}
	
	public void destroyWithin(int x1, int x2, int y1, int y2, int z1, int z2) {
		int count = Math.max(1, (y2 - y1) / DataContext.FloorHeight);
		
		// now destroy it
		while (count > 0) {
			
			// find a place
			int cx = getBlockX(odds.getRandomInt(x2 - x1) + x1);
			int cz = getBlockZ(odds.getRandomInt(z2 - z1) + z1);
			int cy = odds.getRandomInt(Math.max(1, y2 - y1)) + y1;
			int radius = odds.getRandomInt(3) + 3;
			
			// make it go away
			desperseArea(cx, cy, cz, radius);
			
			// done with this round
			count--;
		}
	}
	
	private static class debrisItem {
		protected Material oldMaterial;
		protected MaterialData oldData;
		
		public debrisItem(Block block) {
			oldMaterial = block.getType();
			oldData = block.getState().getData().clone();
		}
	}
	
	private void disperseLine(int x1, int x2, int y, int z1, int z2, Stack<debrisItem> debris) {
		for (int x = x1; x < x2; x++) {
			for (int z = z1; z < z2; z++) {
				Block block = getActualBlock(x, y, z);
				if (!block.isEmpty()) {
					if (!isNonstackableBlock(block))
						debris.push(new debrisItem(block));
					block.setType(Material.AIR);
				}
			}
		}
	}
	
	private void disperseCircle(int cx, int cz, int r, int y, Stack<debrisItem> debris) {

		// Ref: Notes/BCircle.PDF
		int x = r;
		int z = 0;
		int xChange = 1 - 2 * r;
		int zChange = 1;
		int rError = 0;
		
		while (x >= z) {
			
			disperseLine(cx - x - 1, cx - x, y, cz - z - 1, cz + z + 1, debris); // point in octant 5
			disperseLine(cx - z - 1, cx - z, y, cz - x - 1, cz + x + 1, debris); // point in octant 6
			disperseLine(cx + z, cx + z + 1, y, cz - x - 1, cz + x + 1, debris); // point in octant 7
			disperseLine(cx + x, cx + x + 1, y, cz - z - 1, cz + z + 1, debris); // point in octant 8
			
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
	
	//TODO while is approximates a sphere it isn't really a good one
	private void desperseSphere(int cx, int cy, int cz, int r, Stack<debrisItem> debris) {
		// for each slice of the sphere
		for (int r1 = 1; r1 < r; r1++) {
			disperseCircle(cx, cz, r - r1, cy + r1, debris);
			disperseCircle(cx, cz, r - r1, cy - r1, debris);
		}
		disperseCircle(cx, cz, r, cy, debris);
	}
	
	private static double oddsOfDebris = Odds.oddsLikely;//Odds.oddsPrettyLikely; // Reduced the amount of debris to speed things up
	private void sprinkleDebris(int cx, int cy, int cz, int radius, Stack<debrisItem> debris) {

		// calculate a few things
		int r2 = radius * 2;
		int r4 = r2 * 2;
		int x1 = cx - r2;
		int z1 = cz - r2;
		
		// while there is still something left to do
		while (!debris.empty()) {
			
			// grab the next one
			debrisItem item = debris.pop();
			
			// do this one?
			if (odds.playOdds(oddsOfDebris)) {
				
				// where do we drop it?
				int x = x1 + odds.getRandomInt(r4);
				int z = z1 + odds.getRandomInt(r4);
				int y = findLastEmptyBelow(x, cy, z, farthestFall);
				
				// not too far?
				//TODO: I think this is a bit wrong. For example we should be removing/ignoring non-stackable blocks as we search for a resting point
				if (y >= farthestFall) {
					
					// look out for invalid blocks
					Block block = getActualBlock(x, y - 1, z);
					
					// find the bottom of the pool
					if (block.isLiquid()) {
						do {
							y--;
							block = getActualBlock(x, y - 1, z);
						} while (block.isLiquid());
					
					// partial height blocks?
					} else if (isNonstackableBlock(block)) {
						setBlock(block, item.oldMaterial, item.oldData);
					
					// other blocks?
					} else {
						setBlock(x, y, z, item.oldMaterial, item.oldData);
					}
				}
			}
		}
	}

	public void desperseArea(int x, int y, int z, int radius) {
		
		// debris
		Stack<debrisItem> debris = new Stack<debrisItem>();
		
		// clear out the space
		desperseSphere(x, y, z, radius, debris);
		
		// now sprinkle blocks around
		sprinkleDebris(x, y, z, radius, debris);
	}

}
