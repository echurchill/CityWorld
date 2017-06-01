package me.daddychurchill.CityWorld.Plats.Rural;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wood;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.IsolatedLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import me.daddychurchill.CityWorld.Support.BadMagic.Facing;
import me.daddychurchill.CityWorld.Support.BadMagic.General;
import me.daddychurchill.CityWorld.Support.BlackMagic;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

public class BarnLot extends IsolatedLot {

	private Material wallMat;
	private MaterialData wallData;
	private Material roofMat;
	private MaterialData roofData;
	private Material windowsMat;
	
	public BarnLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		style = LotStyle.STRUCTURE;
		
		wallMat = Material.WOOD;
		wallData = new Wood(TreeSpecies.ACACIA);
		roofMat = Material.WOOD;
		roofData = new Wood(TreeSpecies.BIRCH);
		windowsMat = Material.THIN_GLASS;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new BarnLot(platmap, chunkX, chunkZ);
	}

	@Override
	public int getBottomY(CityWorldGenerator generator) {
		return generator.streetLevel;
	}

	@Override
	public int getTopY(CityWorldGenerator generator) {
		return generator.streetLevel + 15;
	}

	@Override
	protected void generateActualChunk(CityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk,
			BiomeGrid biomes, DataContext context, int platX, int platZ) {

//		generator.reportFormatted("@@@@@@@ chunk %d,%d [0][0] = %4d", chunk.sectionX, chunk.sectionZ, generator.shapeProvider.findBlockY(generator, chunk.getBlockX(0), chunk.getBlockZ(0)));
//		burp(generator, 100, true);
//		
//		if (blockYs.minHeight > generator.streetLevel) {
//			blockYs.report(generator, "#######");
//			blockYs.reportMatrix(generator, "#######");
//		}
//		
//		blockYs.draw(chunk);
//		
//		if (blockYs.minHeight > generator.streetLevel)
//			blockYs.report(generator, "*******");

		// ground please
		chunk.setWalls(0, 16, generator.streetLevel, generator.streetLevel + 1, 0, 16, Material.GRASS);
		chunk.setBlocks(1, 15, generator.streetLevel, 1, 15, Material.SANDSTONE);
	}

	@Override
	protected void generateActualBlocks(CityWorldGenerator generator, PlatMap platmap, RealBlocks chunk,
			DataContext context, int platX, int platZ) {
		int y1 = generator.streetLevel + 1;
		int y2 = y1 + 4;
		int y3 = y2 + 4;
		
		// walls and windows first
		chunk.setWalls(1, 15, y1, y3, 1, 15, wallMat, wallData);
		punchWindows(chunk, y1 + 1);
		punchWindows(chunk, y2 + 1);

		// figure out the doors
		boolean firstDoor = chunkOdds.flipCoin();
		boolean secondDoor = chunkOdds.flipCoin();
		if (!firstDoor && !secondDoor)
			firstDoor = true;
		
		// paddocks
		boolean firstPaddock = chunkOdds.playOdds(Odds.oddsSomewhatUnlikely);
		boolean secondPaddock = chunkOdds.playOdds(Odds.oddsSomewhatUnlikely);
		
		// NORTH/SOUTH or EAST/WEST
		if (chunkOdds.flipCoin()) {
			
			// bottom stuff
			if (firstPaddock) {

				// generate fence & gate
				chunk.setBlocks(4, 5, y1, 2, 6, Material.FENCE);
				chunk.setBlocks(4, 5, y1, 10, 14, Material.FENCE);
				chunk.setBlocks(5, 6, y1, 5, 11, Material.FENCE);
				BlackMagic.setBlocks(chunk, 5, 6, y1, 7, 9, Material.FENCE_GATE, Facing.EAST.getData()); // open east

				// hay & water please
				chunk.setBlock(2, y1, 2, Material.HAY_BLOCK);
				chunk.setCauldron(2, y1, 13, chunkOdds);
				
				// spawn horses
				spawnHorses(generator, chunk, 2, y1, 7);
			} else
				// or just a pile of hay
				hayPile(chunk, 2, 5, y1, 2, 14);
			if (secondPaddock) {
				
				// generate fence & gate
				chunk.setBlocks(11, 12, y1, 2, 6, Material.FENCE);
				chunk.setBlocks(11, 12, y1, 10, 14, Material.FENCE);
				chunk.setBlocks(10, 11, y1, 5, 11, Material.FENCE);
				BlackMagic.setBlocks(chunk, 10, 11, y1, 7, 9, Material.FENCE_GATE, Facing.WEST.getData()); // open west

				// hay & water please
				chunk.setBlock(13, y1, 2, Material.HAY_BLOCK);
				chunk.setCauldron(13, y1, 13, chunkOdds);
				
				// spawn horses
				spawnHorses(generator, chunk, 12, y1, 7);
			} else
				// or just a pile of hay
				hayPile(chunk, 11, 14, y1, 2, 14);
			
			// top stuff
			hayLoft(chunk, 2, 5, y2, 2, 14);
			hayLoft(chunk, 11, 14, y2, 2, 14);

			// columns
			chunk.setBlocks(4, y1, y2, 4, wallMat, wallData);
			chunk.setBlocks(11, y1, y2, 4, wallMat, wallData);
			chunk.setBlocks(4, y1, y2, 11, wallMat, wallData);
			chunk.setBlocks(11, y1, y2, 11, wallMat, wallData);
			
			// access and back fill
			if (firstDoor) {
				chunk.clearBlocks(5, 11, y1, y2, 1, 2);
				chunk.setLadder(5, y1, y2 + 1, 4, BlockFace.WEST);
				chunk.setLadder(10, y1, y2 + 1, 4, BlockFace.EAST);
				if (!secondDoor) {
					hayLoft(chunk, 5, 11, y2, 11, 14);
					hayPile(chunk, 5, 11, y1, 11, 14);
					
					// treasures
					placeChest(generator, chunk, 7, y1, 10, General.NORTH);
					placeChest(generator, chunk, 8, y1, 10, General.NORTH);
				}
			}
			if (secondDoor) { 
				chunk.clearBlocks(5, 11, y1, y2, 14, 15);
				chunk.setLadder(5, y1, y2 + 1, 11, BlockFace.WEST);
				chunk.setLadder(10, y1, y2 + 1, 11, BlockFace.EAST);
				if (!firstDoor) {
					hayLoft(chunk, 5, 11, y2, 2, 5);
					hayPile(chunk, 5, 11, y1, 2, 5);

					// treasures
					placeChest(generator, chunk, 7, y1, 5, General.SOUTH);
					placeChest(generator, chunk, 8, y1, 5, General.SOUTH);
				}
			}
			
			// roof
			chunk.setBlocks(0, 1, y3 - 1, 0, 16, roofMat, roofData);
			chunk.setBlocks(1, 2, y3, 0, 16, roofMat, roofData);
			chunk.setBlocks(2, 3, y3 + 1, 0, 16, roofMat, roofData);
			chunk.setBlocks(3, 5, y3 + 2, 0, 16, roofMat, roofData);
			chunk.setBlocks(5, 7, y3 + 3, 0, 16, roofMat, roofData);
			chunk.setBlocks(7, 9, y3 + 4, 0, 16, roofMat, roofData);
			chunk.setBlocks(9, 11, y3 + 3, 0, 16, roofMat, roofData);
			chunk.setBlocks(11, 13, y3 + 2, 0, 16, roofMat, roofData);
			chunk.setBlocks(13, 14, y3 + 1, 0, 16, roofMat, roofData);
			chunk.setBlocks(14, 15, y3, 0, 16, roofMat, roofData);
			chunk.setBlocks(15, 16, y3 - 1, 0, 16, roofMat, roofData);
			
			// end cap
			chunk.setBlocks(2, 14, y3, 1, 2, wallMat, wallData);
			chunk.setBlocks(3, 13, y3 + 1, 1, 2, wallMat, wallData);
			chunk.setBlocks(5, 11, y3 + 2, 1, 2, wallMat, wallData);
			chunk.setBlocks(2, 14, y3, 14, 15, wallMat, wallData);
			chunk.setBlocks(3, 13, y3 + 1, 14, 15, wallMat, wallData);
			chunk.setBlocks(5, 11, y3 + 2, 14, 15, wallMat, wallData);
			
			// a few more windows
			punchWindows(chunk, 7, y3 + 2, 1);
			punchWindows(chunk, 8, y3 + 2, 1);
			punchWindows(chunk, 7, y3 + 2, 14);
			punchWindows(chunk, 8, y3 + 2, 14);
		} else {
			// bottom stuff
			if (firstPaddock) {
				
				// generate fence & gate
				chunk.setBlocks(2, 6, y1, 4, 5, Material.FENCE);
				chunk.setBlocks(10, 14, y1, 4, 5, Material.FENCE);
				chunk.setBlocks(5, 11, y1, 5, 6, Material.FENCE);
				BlackMagic.setBlocks(chunk, 7, 9, y1, 5, 6, Material.FENCE_GATE, Facing.SOUTH.getData()); // open south

				// hay & water please
				chunk.setBlock(2, y1, 2, Material.HAY_BLOCK);
				chunk.setCauldron(13, y1, 2, chunkOdds);
				
				// spawn horses
				spawnHorses(generator, chunk, 7, y1, 2);
			} else
				// or just a pile of hay
				hayPile(chunk, 2, 14, y1, 2, 5);
			if (secondPaddock) {
				
				// generate fence & gate
				chunk.setBlocks(2, 6, y1, 11, 12, Material.FENCE);
				chunk.setBlocks(10, 14, y1, 11, 12, Material.FENCE);
				chunk.setBlocks(5, 11, y1, 10, 11, Material.FENCE);
				BlackMagic.setBlocks(chunk, 7, 9, y1, 10, 11, Material.FENCE_GATE, Facing.NORTH.getData()); // open north

				// hay & water please
				chunk.setBlock(2, y1, 13, Material.HAY_BLOCK);
				chunk.setCauldron(13, y1, 13, chunkOdds);
				
				// spawn horses
				spawnHorses(generator, chunk, 7, y1, 13);
			} else
				// or just a pile of hay
				hayPile(chunk, 2, 14, y1, 11, 14);
			
			// top stuff
			hayLoft(chunk, 2, 14, y2, 2, 5);
			hayLoft(chunk, 2, 14, y2, 11, 14);

			// columns
			chunk.setBlocks(4, y1, y2, 4, wallMat, wallData);
			chunk.setBlocks(11, y1, y2, 4, wallMat, wallData);
			chunk.setBlocks(4, y1, y2, 11, wallMat, wallData);
			chunk.setBlocks(11, y1, y2, 11, wallMat, wallData);
			
			// access and back fill
			if (firstDoor) {
				chunk.clearBlocks(1, 2, y1, y2, 5, 11);
				chunk.setLadder(4, y1, y2 + 1, 5, BlockFace.NORTH);
				chunk.setLadder(4, y1, y2 + 1, 10, BlockFace.SOUTH);
				if (!secondDoor) {
					hayLoft(chunk, 11, 14, y2, 5, 11);
					hayPile(chunk, 11, 14, y1, 5, 11);

					// treasures
					placeChest(generator, chunk, 10, y1, 7, General.WEST);
					placeChest(generator, chunk, 10, y1, 8, General.WEST);
				}
			}
			if (secondDoor) {
				chunk.clearBlocks(14, 15, y1, y2, 5, 11);
				chunk.setLadder(11, y1, y2 + 1, 5, BlockFace.NORTH);
				chunk.setLadder(11, y1, y2 + 1, 10, BlockFace.SOUTH);
				if (!firstDoor) {
					hayLoft(chunk, 2, 5, y2, 5, 11);
					hayPile(chunk, 2, 5, y1, 5, 11);

					// treasures
					placeChest(generator, chunk, 5, y1, 7, General.EAST);
					placeChest(generator, chunk, 5, y1, 8, General.EAST);
				}
			}
			
			// roof
			chunk.setBlocks(0, 16, y3 - 1, 0, 1, roofMat, roofData);
			chunk.setBlocks(0, 16, y3, 1, 2, roofMat, roofData);
			chunk.setBlocks(0, 16, y3 + 1, 2, 3, roofMat, roofData);
			chunk.setBlocks(0, 16, y3 + 2, 3, 5, roofMat, roofData);
			chunk.setBlocks(0, 16, y3 + 3, 5, 7, roofMat, roofData);
			chunk.setBlocks(0, 16, y3 + 4, 7, 9, roofMat, roofData);
			chunk.setBlocks(0, 16, y3 + 3, 9, 11, roofMat, roofData);
			chunk.setBlocks(0, 16, y3 + 2, 11, 13, roofMat, roofData);
			chunk.setBlocks(0, 16, y3 + 1, 13, 14, roofMat, roofData);
			chunk.setBlocks(0, 16, y3, 14, 15, roofMat, roofData);
			chunk.setBlocks(0, 16, y3 - 1, 15, 16, roofMat, roofData);

			// endcaps
			chunk.setBlocks(1, 2, y3, 2, 14, wallMat, wallData);
			chunk.setBlocks(1, 2, y3 + 1, 3, 13, wallMat, wallData);
			chunk.setBlocks(1, 2, y3 + 2, 5, 11, wallMat, wallData);
			chunk.setBlocks(14, 15, y3, 2, 14, wallMat, wallData);
			chunk.setBlocks(14, 15, y3 + 1, 3, 13, wallMat, wallData);
			chunk.setBlocks(14, 15, y3 + 2, 5, 11, wallMat, wallData);

			// a few more windows
			punchWindows(chunk, 1, y3 + 2, 7);
			punchWindows(chunk, 1, y3 + 2, 8);
			punchWindows(chunk, 14, y3 + 2, 7);
			punchWindows(chunk, 14, y3 + 2, 8);
		}
		
		// not a happy place?
		if (generator.settings.includeDecayedBuildings)
			destroyBuilding(generator, y1, 3);
	}
	
	private void spawnHorses(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z) {
		EntityType animal = EntityType.HORSE;
		if (chunkOdds.playOdds(Odds.oddsUnlikely))
			animal = EntityType.DONKEY;
		generator.spawnProvider.spawnAnimals(generator, chunk, chunkOdds, x, y, z, animal);
	}
	
	private void punchWindows(RealBlocks chunk, int y) {
		punchWindows(chunk, 3, y, 1);
		punchWindows(chunk, 1, y, 3);
		punchWindows(chunk, 12, y, 1);
		punchWindows(chunk, 1, y, 12);
		punchWindows(chunk, 14, y, 3);
		punchWindows(chunk, 3, y, 14);
		punchWindows(chunk, 12, y, 14);
		punchWindows(chunk, 14, y, 12);
	}
	
	private void punchWindows(RealBlocks chunk, int x, int y, int z) {
		chunk.setBlocks(x, y, y + 2, z, windowsMat);
	}
	
	private void hayLoft(RealBlocks chunk, int x1, int x2, int y, int z1, int z2) {
		chunk.setBlocks(x1, x2, y, y + 1, z1, z2, wallMat, wallData);
		hayPile(chunk, x1, x2, y + 1, z1, z2);
	}
	
	private void hayPile(RealBlocks chunk, int x1, int x2, int y, int z1, int z2) {
		if (chunkOdds.flipCoin())
			for (int x = x1; x < x2; x++)
				for (int z = z1; z < z2; z++)
					chunk.setBlocks(x, y, y + chunkOdds.getRandomInt(3), z, Material.HAY_BLOCK);
	}
	
	private void placeChest(CityWorldGenerator generator, RealBlocks chunk, 
			int x, int y, int z, General towards) {
		if (chunkOdds.flipCoin()) {
			chunk.setChest(generator, x, y, z, towards, chunkOdds, 
					generator.lootProvider, LootLocation.FARMWORKSOUTPUT);
		} else if (chunkOdds.flipCoin()){
			chunk.setChest(generator, x, y, z, towards, chunkOdds, 
					generator.lootProvider, LootLocation.FARMWORKS);
		}
	}
}
