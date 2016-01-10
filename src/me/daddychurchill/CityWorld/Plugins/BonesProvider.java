package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.BadMagic.Stair;
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
	
	public void generateBones(CityWorldGenerator generator, RealBlocks chunk, DataContext context, Odds odds) {
		
		int x = 7;
		int y = 200;
		int z = 3;
		Material matBlock = Material.QUARTZ_BLOCK;
		Material matStair = Material.QUARTZ_STAIRS;

		boolean gotTorso = odds.flipCoin();
		boolean gotHind = odds.flipCoin();
		if (gotTorso && gotHind)
			gotTorso = odds.playOdds(Odds.oddsPrettyUnlikely);
		
		// pelvis & front legs
		generateLegs(chunk, odds, x, y, z, matBlock, matStair);
		
		// torso?
		int headOffset = 0;
		if (gotTorso) {
			headOffset = odds.calcRandomRange(4, 6);
			int shoulderOffset = odds.calcRandomRange(2, 3);
			
			// spine
			chunk.setBlocks(x, y, y + headOffset, z, matBlock);
			
			// ribs
			if (shoulderOffset > 2 && odds.flipCoin()) {
				for (int y1 = y + 1; y1 < y + headOffset; y1++) {
					chunk.setStair(x - 1, y1, z, matStair, Stair.EAST);
					chunk.setStair(x + 1, y1, z, matStair, Stair.WEST);
				}
			}
			
			// shoulders and arms
			generateLimbs(chunk, odds, x, y + headOffset, z, shoulderOffset, headOffset - 1, matBlock, matStair);
		}
		
		// head?
		if (odds.playOdds(Odds.oddsExceedinglyLikely)) {
			headOffset++;
			chunk.setBlock(x, y + headOffset, z, matBlock);
			if (odds.flipCoin() && gotTorso)
				generateHead(chunk, odds, x, y + headOffset + 1, z, matBlock, matStair);
			else {
				if (odds.flipCoin())
					headOffset--;
				
				chunk.setStair(x, y + headOffset + 1, z, matStair, Stair.NORTH);
				chunk.setStair(x, y + headOffset, z - 1, matStair, Stair.SOUTHFLIP);
				if (odds.flipCoin())
					generateHead(chunk, odds, x, y + headOffset + 1, z - 1, matBlock, matStair);
				else {
					chunk.setBlock(x, y + headOffset + 1, z - 1, matBlock);
					chunk.setStair(x, y + headOffset + 2, z - 1, matStair, Stair.NORTH);
					chunk.setStair(x, y + headOffset + 1, z - 2, matStair, Stair.SOUTHFLIP);
					generateHead(chunk, odds, x, y + headOffset + 2, z - 2, matBlock, matStair);
				}
			}
		}
		
		// hind?
		int hindOffset = 0;
		if (gotHind) {
			
			// horizontal rib cage
			hindOffset = odds.calcRandomRange(3, 8);
			for (int zO = 1; zO < hindOffset; zO++) {
				chunk.setBlock(x, y, z + zO, matBlock);
				if (zO % 2 == 0 && zO + 1 < hindOffset && odds.playOdds(Odds.oddsPrettyLikely)) {
					chunk.setStair(x - 1, y, z + zO, matStair, Stair.EAST);
					chunk.setStair(x + 1, y, z + zO, matStair, Stair.WEST);
					
					chunk.setBlock(x - 1, y - 1, z + zO, matBlock);
					chunk.setBlock(x + 1, y - 1, z + zO, matBlock);
					
					chunk.setStair(x - 1, y - 2, z + zO, matStair, Stair.EASTFLIP);
					chunk.setStair(x + 1, y - 2, z + zO, matStair, Stair.WESTFLIP);
				}
			}
			
			// pelvis & back legs
			generateLegs(chunk, odds, x, y, z + hindOffset, matBlock, matStair);
			hindOffset++;
		}
		
		// got a tail?
		if (gotHind || odds.playOdds(Odds.oddsExtremelyUnlikely)) {
			int tailOffset = odds.calcRandomRange(0, 4);
			
			// long enough to bother doing?
			if (tailOffset > 0) {
				int yTail = y;
				for (int zO = 0; zO < tailOffset; zO++) {
					if (odds.flipCoin()) {
						chunk.setStair(x, yTail, z + hindOffset + zO, matStair, Stair.NORTH);
						yTail--;
					}
					chunk.setBlock(x, yTail, z + hindOffset + zO, matBlock);
				}
			}
		}
	}
	
	private void generateLegs(RealBlocks chunk, Odds odds, int x, int y, int z, Material matBlock, Material matStair) {
		int xO = odds.calcRandomRange(1, 3);
		int yO = odds.calcRandomRange(2, 5);
		generateLimbs(chunk, odds, x, y, z, xO, yO, matBlock, matStair);
	}
	
	private void generateLimbs(RealBlocks chunk, Odds odds, int x, int y, int z, int xO, int yO, Material matBlock, Material matStair) {
		chunk.setBlocks(x - xO, x + xO + 1, y, z, z + 1, matBlock);
		if (odds.flipCoin()) { // rounded tops
			chunk.setStair(x - xO, y, z, matStair, Stair.EAST);
			chunk.setStair(x + xO, y, z, matStair, Stair.WEST);
		}
		
		// vertical bits
		chunk.setBlocks(x - xO, x - xO + 1, y - yO + 1, y, z, z + 1, matBlock);
		chunk.setBlocks(x + xO, x + xO + 1, y - yO + 1, y, z, z + 1, matBlock);

		// wrist/heels
		if (odds.flipCoin()) {
			chunk.setBlock(x - xO, y - yO, z, matBlock);
			chunk.setBlock(x + xO, y - yO, z, matBlock);
		} else {
			chunk.setStair(x - xO, y - yO, z, matStair, Stair.NORTHFLIP);
			chunk.setStair(x + xO, y - yO, z, matStair, Stair.NORTHFLIP);
		}
		
		// fingers/toes
		if (odds.flipCoin()) {
			chunk.setStair(x - xO, y - yO, z - 1, matStair, Stair.SOUTH);
			chunk.setStair(x + xO, y - yO, z - 1, matStair, Stair.SOUTH);
		}
	}
	
	private void generateHead(RealBlocks chunk, Odds odds, int x, int y, int z, Material matBlock, Material matStair) {
		int yT = y + 1;
		if (odds.flipCoin())
			yT++;
		
		chunk.setBlocks(x - 1, x + 2, y, yT + 1, z, z + 1, matBlock);
		
		if (odds.flipCoin()) { // rounded bottoms
			chunk.setStair(x - 1, y, z, matStair, Stair.EASTFLIP);
			chunk.setStair(x + 1, y, z, matStair, Stair.WESTFLIP);
		} else if (odds.flipCoin()) // mouth
			chunk.setStair(x, y, z, matStair, Stair.SOUTH);
		else if (odds.flipCoin()) // snout
			chunk.setStair(x, yT - 1, z - 1, matStair, Stair.SOUTH);
		
		if (odds.flipCoin()) { // eyes
			chunk.setStair(x - 1, yT, z, matStair, Stair.SOUTHFLIP);
			chunk.setStair(x + 1, yT, z, matStair, Stair.SOUTHFLIP);
		} else if (odds.flipCoin()) { // rounded tops
			chunk.setStair(x - 1, yT, z, matStair, Stair.EAST);
			chunk.setStair(x + 1, yT, z, matStair, Stair.WEST);
		}
	}
	
}
