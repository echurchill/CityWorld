package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import me.daddychurchill.CityWorld.Support.BadMagic.Facing;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public class LoungeKitchenetteRoom extends LoungeRoom {

	public LoungeKitchenetteRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x,
			int y, int z, int width, int height, int depth,
			Facing sideWithWall, Material materialWall, Material materialGlass) {
		switch (sideWithWall) {
		case NORTH:
			chunk.setBlocks(x, x + 1, y, y + height, z, z + depth, materialWall);
			placeStuff(generator, chunk, odds, x + 1, y, z, BlockFace.EAST);
			chunk.setCauldron(x + 1, y, z + 1, odds);
			chunk.setBlockTypeAndDirection(x + 1, y, z + 2, Material.PISTON_BASE, BlockFace.UP);
			if (odds.flipCoin())
				chunk.setBlock(x + 1, y + 1, z, Material.BREWING_STAND);
			break;
		case SOUTH:
			chunk.setBlocks(x + width - 1, x + width, y, y + height, z, z + depth, materialWall);
			placeStuff(generator, chunk, odds, x + 1, y, z, BlockFace.WEST);
			chunk.setCauldron(x + 1, y, z + 1, odds);
			chunk.setBlockTypeAndDirection(x + 1, y, z + 2, Material.PISTON_BASE, BlockFace.UP);
			if (odds.flipCoin())
				chunk.setBlock(x + 1, y + 1, z + 2, Material.BREWING_STAND);
			break;
		case WEST:
			chunk.setBlocks(x, x + width, y, y + height, z + depth - 1, z + depth, materialWall);
			placeStuff(generator, chunk, odds, x, y, z + 1, BlockFace.NORTH);
			chunk.setCauldron(x + 1, y, z + 1, odds);
			chunk.setBlockTypeAndDirection(x + 2, y, z + 1, Material.PISTON_BASE, BlockFace.UP);
			if (odds.flipCoin())
				chunk.setBlock(x, y + 1, z + 1, Material.BREWING_STAND);
			break;
		case EAST:
			chunk.setBlocks(x, x + width, y, y + height, z, z + 1, materialWall);
			placeStuff(generator, chunk, odds, x, y, z + 1, BlockFace.SOUTH);
			chunk.setCauldron(x + 1, y, z + 1, odds);
			chunk.setBlockTypeAndDirection(x + 2, y, z + 1, Material.PISTON_BASE, BlockFace.UP);
			if (odds.flipCoin())
				chunk.setBlock(x + 2, y + 1, z + 1, Material.BREWING_STAND);
			break;
		}
	}

	private void placeStuff(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int x, int y, int z, BlockFace facing) {
		if (odds.playOdds(generator.settings.oddsOfTreasureInBuildings))
			chunk.setChest(generator, x, y, z, facing, odds, generator.lootProvider, LootLocation.FOOD);
		else
			chunk.setBlockTypeAndDirection(x, y, z, Material.PISTON_BASE, BlockFace.UP);
	}
}
