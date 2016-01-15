package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.BadMagic.Stair;
import me.daddychurchill.CityWorld.Support.CachedYs;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class BonesProvider extends Provider {

	public BonesProvider() {
		// TODO Auto-generated constructor stub
	}

	public final static BonesProvider loadProvider(CityWorldGenerator generator) {
		// for now
		return new BonesProvider();
	}
	
	public void generateBones(CityWorldGenerator generator, PlatLot lot, RealBlocks chunk, CachedYs blockYs, Odds odds) {
		Material matBlock = Material.QUARTZ_BLOCK;
		Material matStair = Material.QUARTZ_STAIRS;
		if (generator.settings.includeDecayedNature) {
			matBlock = Material.SANDSTONE;
			matStair = Material.SANDSTONE_STAIRS;
		}

		// figure the location
		int x = 7;
		int y = odds.calcRandomRange(10, blockYs.minHeight - 25);
		int z = 15;
//		chunk.setBlocks(0, 16, 220, 221, 0, 16, Material.GLASS);

		// what bits does it have?
		boolean gotTorso = odds.flipCoin(); // arms just below the head
		boolean gotHind = odds.flipCoin(); // legs on either end
		if (gotTorso && gotHind) // a centaur, pretty rare beast?
			gotTorso = odds.playOdds(Odds.oddsPrettyUnlikely);

		// figure out the legs
		int backLegHeight = odds.calcRandomRange(3, 5);
		int backLegWidth = odds.calcRandomRange(1, 3);
		int frontLegHeight = Math.min(odds.calcRandomRange(2, 5), backLegHeight);
		int frontLegWidth = odds.calcRandomRange(1, 3);
		int armLength = odds.calcRandomRange(2, 4);
		int armWidth = odds.calcRandomRange(2, 3);
		
		// calculate the lengths of the sections
		int spineLength = 0;
		if (gotTorso)
			spineLength = odds.calcRandomRange(armLength + 1, armLength + 3);
		int hindLength = 0;
		if (gotHind)
			hindLength = odds.calcRandomRange(3, 6);
		
		// up on the back legs?
		boolean isHindUpright = gotHind && odds.playOdds(Odds.oddsSomewhatLikely); 

		// figure out the tail bit
		int tailLength = 0;
		if (gotHind || odds.playOdds(Odds.oddsExtremelyUnlikely))
			tailLength = odds.calcRandomRange(0, 3);
			
		// start at the back
		int sectionZ = z;
		int sectionY = y;
		
		// tail?
		if (tailLength > 0) {
			sectionZ = sectionZ - tailLength;
			int tailZ = sectionZ + 1;
			int tailY = sectionY + backLegHeight;
			for (int zO = 0; zO < tailLength; zO++) {
				if (tailY > y && odds.flipCoin()) {
					chunk.setStair(x, tailY, tailZ + zO, matStair, Stair.NORTH);
					tailY--;
				}
				chunk.setBlock(x, tailY, tailZ + zO, matBlock);
			}
		}
		
		// back legs
		sectionY = sectionY + backLegHeight;
		generateLimbs(chunk, odds, x, sectionY, sectionZ, backLegWidth, backLegHeight, matBlock, matStair);
		
		// hind section
		if (gotHind) {
			sectionZ = sectionZ - hindLength;
			int hindZ = sectionZ;
			int hindY = y + Math.max(backLegHeight, frontLegHeight);
			if (isHindUpright)
				hindY = hindY + hindLength;
			sectionY = hindY;

			// now the front legs
			generateLimbs(chunk, odds, x, hindY, hindZ, frontLegWidth, frontLegHeight, matBlock, matStair);
			
			// now for the spine and ribs
			for (int zO = 0; zO <= hindLength; zO++) {
				chunk.setBlock(x, hindY, hindZ + zO, matBlock);
				
				// ribs
				if (zO > 0 && zO % 2 == 0 && zO + 1 < hindLength && odds.playOdds(Odds.oddsPrettyLikely)) {
					chunk.setStair(x - 1, hindY, hindZ + zO, matStair, Stair.EAST);
					chunk.setStair(x + 1, hindY, hindZ + zO, matStair, Stair.WEST);
					
					chunk.setBlock(x - 1, hindY - 1, hindZ + zO, matBlock);
					chunk.setBlock(x + 1, hindY - 1, hindZ + zO, matBlock);
					
					chunk.setStair(x - 1, hindY - 2, hindZ + zO, matStair, Stair.EASTFLIP);
					chunk.setStair(x + 1, hindY - 2, hindZ + zO, matStair, Stair.WESTFLIP);
				}
				
				// move down 
				if (isHindUpright) {
					chunk.setStair(x, hindY + 1, hindZ + zO, matStair, Stair.NORTH);
					chunk.setStair(x, hindY - 1, hindZ + zO, matStair, Stair.SOUTHFLIP);
					hindY--;
				}
			}
		}
		
		// torso section
		if (gotTorso) {
			
			// spine
			boolean showRibs = armWidth > 2 && odds.flipCoin();
			for (int yO = 0; yO < spineLength; yO++) {
				chunk.setBlock(x, sectionY + yO, sectionZ, matBlock);
				if (yO > 0 && yO < spineLength - 1 && showRibs) {
					chunk.setStair(x - 1, sectionY + yO, sectionZ, matStair, Stair.EAST);
					chunk.setStair(x + 1, sectionY + yO, sectionZ, matStair, Stair.WEST);
				}
			}
			
			// arms
			sectionY += spineLength;
			generateLimbs(chunk, odds, x, sectionY, sectionZ, armWidth, armLength, matBlock, matStair);
		}
		
		// add the head
		if (odds.playOdds(Odds.oddsTremendouslyLikely)) {
			
			// long neck
			boolean tiltedNeck = (gotHind && !gotTorso && isHindUpright && odds.playOdds(Odds.oddsSomewhatUnlikely)) ||
							   (gotHind && !gotTorso && odds.playOdds(Odds.oddsVeryLikely)) ||
							   (gotTorso && odds.playOdds(Odds.oddsExtremelyUnlikely));
			
			// so do it
			int neckLength = odds.calcRandomRange(1, 3);
			if (tiltedNeck) {
				for (int yO = 0; yO < neckLength; yO++) {
					
					chunk.setBlock(x, sectionY, sectionZ, matBlock);
					chunk.setStair(x, sectionY + 1, sectionZ, matStair, Stair.NORTH);
					chunk.setStair(x, sectionY, sectionZ - 1, matStair, Stair.SOUTHFLIP);
					
					sectionY++;
					sectionZ--;
				}
			} else {
				if (odds.playOdds(Odds.oddsPrettyUnlikely))
					neckLength = odds.calcRandomRange(3, 6);
				chunk.setBlocks(x, sectionY, sectionY + neckLength, sectionZ, matBlock);
				sectionY += neckLength;
			}
			
			// now the head itself
			generateHead(chunk, odds, x, sectionY, sectionZ, matBlock, matStair);
		}
	}
	
	private void generateLimbs(RealBlocks chunk, Odds odds, int x, int y, int z, int xO, int yO, Material matBlock, Material matStair) {
		if (odds.flipCoin()) { // rounded tops
			chunk.setBlocks(x - xO, x, y, z, z + 1, matBlock);
			chunk.setBlocks(x + 1, x + xO + 1, y, z, z + 1, matBlock);
		} else {
			chunk.setBlocks(x - xO + 1, x, y, z, z + 1, matBlock);
			chunk.setBlocks(x + 1, x + xO, y, z, z + 1, matBlock);
			
			chunk.setStair(x - xO, y, z, matStair, Stair.EAST);
			chunk.setStair(x + xO, y, z, matStair, Stair.WEST);
		}
		
		// vertical bits
		chunk.setBlocks(x - xO, x - xO + 1, y - yO + 1, y, z, z + 1, matBlock);
		chunk.setBlocks(x + xO, x + xO + 1, y - yO + 1, y, z, z + 1, matBlock);

		// wrist/heels
		boolean forceToes = false;
		if (odds.flipCoin()) {
			chunk.setBlock(x - xO, y - yO, z, matBlock);
			chunk.setBlock(x + xO, y - yO, z, matBlock);
		} else {
			forceToes = true;
			chunk.setStair(x - xO, y - yO, z, matStair, Stair.NORTHFLIP);
			chunk.setStair(x + xO, y - yO, z, matStair, Stair.NORTHFLIP);
		}
		
		// fingers/toes
		if (forceToes || odds.flipCoin()) {
			chunk.setStair(x - xO, y - yO, z - 1, matStair, Stair.SOUTH);
			chunk.setStair(x + xO, y - yO, z - 1, matStair, Stair.SOUTH);
		}
	}
	
	private void generateHead(RealBlocks chunk, Odds odds, int x, int y, int z, Material matBlock, Material matStair) {
		int headHeight = odds.calcRandomRange(2, 3);
		int y1 = y;
		int y2 = y + 1;
		int y3 = y + headHeight - 1;
		boolean tallHead = y2 != y3;
		boolean needEyes = odds.playOdds(Odds.oddsExceedinglyLikely);
		boolean cyclopsEye = tallHead && odds.playOdds(Odds.oddsLikely);
		
		// whole head
		chunk.setBlocks(x - 1, x + 2, y, y + headHeight, z, z + 1, matBlock);
		
		// edit the top bit
		if (odds.flipCoin()) { // rounded tops
			chunk.setStair(x - 1, y3, z, matStair, Stair.EAST);
			chunk.setStair(x + 1, y3, z, matStair, Stair.WEST);
//		} else if (odds.flipCoin()) { // hatish top
//			chunk.setStair(x - 1, y3, z, matStair, Stair.EASTFLIP);
//			chunk.setStair(x + 1, y3, z, matStair, Stair.WESTFLIP);
		} else if (!cyclopsEye && needEyes && odds.playOdds(Odds.oddsLikely)) {
			chunk.setStair(x - 1, y3, z, matStair, Stair.SOUTHFLIP);
			chunk.setStair(x + 1, y3, z, matStair, Stair.SOUTHFLIP);
			needEyes = false;
		} 
		
		// edit the bottom bit
		if (odds.flipCoin()) { // rounded bottoms
			chunk.setStair(x - 1, y1, z, matStair, Stair.EASTFLIP);
			chunk.setStair(x + 1, y1, z, matStair, Stair.WESTFLIP);
//		} else if (odds.flipCoin()) { // second neck
//			chunk.setStair(x - 1, y1, z, matStair, Stair.EAST);
//			chunk.setStair(x + 1, y1, z, matStair, Stair.WEST);
		}
		
		// got to eat
		if (odds.playOdds(Odds.oddsPrettyLikely)) // mouth
			chunk.setStair(x, y1, z, matStair, Stair.SOUTH);
		else if (!cyclopsEye && odds.flipCoin()) { // snout/beak
			chunk.setStair(x, y2, z - 1, matStair, Stair.SOUTH);
			chunk.setStair(x, y1, z - 1, matStair, Stair.SOUTHFLIP);
		}
		
		// finalize the eye bits
		if (cyclopsEye && tallHead) {
			chunk.setStair(x, y3, z, matStair, Stair.SOUTHFLIP);
			chunk.setStair(x, y2, z, matStair, Stair.SOUTH);
			needEyes = false;
		} else if (needEyes) {
			if (tallHead) {
				chunk.setStair(x - 1, y2, z, matStair, Stair.SOUTHFLIP);
				chunk.setStair(x + 1, y2, z, matStair, Stair.SOUTHFLIP);
			} else {
				chunk.setStair(x - 1, y2, z, matStair, Stair.SOUTH);
				chunk.setStair(x + 1, y2, z, matStair, Stair.SOUTH);
			}
			needEyes = false;
		}
	}
	
}
