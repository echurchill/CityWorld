package me.daddychurchill.CityWorld.Rooms;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;

public abstract class PlatRoom {

	PlatRoom() {
		// TODO Auto-generated constructor stub
	}

	public abstract void drawFixture(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int floor, int x, int y,
			int z, int width, int height, int depth, BlockFace sideWithWall, Material materialWall,
			Material materialGlass);

	private final Material[] TableTops = { Material.ACACIA_PRESSURE_PLATE, Material.BIRCH_PRESSURE_PLATE,
			Material.DARK_OAK_PRESSURE_PLATE, Material.JUNGLE_PRESSURE_PLATE, Material.OAK_PRESSURE_PLATE,
			Material.SPRUCE_PRESSURE_PLATE,

			Material.HEAVY_WEIGHTED_PRESSURE_PLATE, Material.LIGHT_WEIGHTED_PRESSURE_PLATE,
			Material.STONE_PRESSURE_PLATE,

			Material.BLACK_CARPET, Material.BLUE_CARPET, Material.BROWN_CARPET, Material.CYAN_CARPET,
			Material.GRAY_CARPET, Material.GREEN_CARPET, Material.LIGHT_BLUE_CARPET, Material.LIGHT_GRAY_CARPET,
			Material.LIME_CARPET, Material.MAGENTA_CARPET, Material.ORANGE_CARPET, Material.PINK_CARPET,
			Material.PURPLE_CARPET, Material.RED_CARPET, Material.WHITE_CARPET, Material.YELLOW_CARPET };

	private final Material[] TableLegs = { Material.ACACIA_FENCE, Material.BIRCH_FENCE, Material.DARK_OAK_FENCE,
			Material.JUNGLE_FENCE, Material.OAK_FENCE, Material.SPRUCE_FENCE,

			Material.NETHER_BRICK_FENCE, Material.COBBLESTONE_WALL, Material.MOSSY_COBBLESTONE_WALL,
			Material.IRON_BARS };

	Material getTableTop(Odds odds) {
		return odds.getRandomMaterial(TableTops);
	}

	Material getTableLeg(Odds odds) {
		return odds.getRandomMaterial(TableLegs);
	}
}
