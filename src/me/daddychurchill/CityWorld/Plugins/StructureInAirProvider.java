package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.AbstractBlocks;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.World.Environment;

public class StructureInAirProvider extends Provider {

	public StructureInAirProvider() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public final static StructureInAirProvider loadProvider(CityWorldGenerator generator) {
		// for now
		return new StructureInAirProvider();
	}

	//TODO need bigger balloons
	//TODO need better balloons
	//TODO need better strings
	
	public void generateBalloon(CityWorldGenerator generator, SupportBlocks chunk, DataContext context, 
			int attachX, int attachY, int attachZ, Odds odds) {
		// where is the balloon
		int balloonX = attachX;
		int balloonY1 = attachY + 5 + odds.getRandomInt(10);
		int balloonY2 = balloonY1 + 8 + odds.getRandomInt(3);
		int balloonZ = attachZ;
		
		// string please
		if (attachString(chunk, balloonX, attachY, balloonY1, balloonZ)) {
			
			// pick the colors
			DyeColor primaryColor = getPrimaryColor(generator, odds);
			DyeColor secondaryColor = getSecondaryColor(generator, odds);
			
			// draw the balloon
			chunk.setWool(balloonX, balloonX + 1, balloonY1, balloonY1 + 2, balloonZ, balloonZ + 1, primaryColor);
			
			chunk.setWool(balloonX - 1, balloonX + 2, balloonY1 + 2, balloonY1 + 4, balloonZ - 1, balloonZ + 2, primaryColor);
			chunk.setBlock(balloonX - 1, balloonY1 + 2, balloonZ - 1, Material.AIR);
			chunk.setBlock(balloonX - 1, balloonY1 + 2, balloonZ + 1, Material.AIR);
			chunk.setBlock(balloonX + 1, balloonY1 + 2, balloonZ - 1, Material.AIR);
			chunk.setBlock(balloonX + 1, balloonY1 + 2, balloonZ + 1, Material.AIR);
			
			chunk.setWool(balloonX - 2, balloonX + 3, balloonY1 + 4, balloonY1 + 6, balloonZ - 2, balloonZ + 3, primaryColor);
			chunk.setBlock(balloonX - 2, balloonY1 + 4, balloonZ - 2, Material.AIR);
			chunk.setBlock(balloonX - 2, balloonY1 + 4, balloonZ + 2, Material.AIR);
			chunk.setBlock(balloonX + 2, balloonY1 + 4, balloonZ - 2, Material.AIR);
			chunk.setBlock(balloonX + 2, balloonY1 + 4, balloonZ + 2, Material.AIR);
			
//			for (int y = balloonY1 + 6; y < balloonY2 - 1; y++)
//				chunk.setWool(balloonX - 2, balloonX + 3, y, y + 1, balloonZ - 2, balloonZ + 3, secondaryColor);
			chunk.setWool(balloonX - 2, balloonX + 3, balloonY1 + 6, balloonY2 - 1, balloonZ - 2, balloonZ + 3, secondaryColor);
			
			chunk.setWool(balloonX - 2, balloonX + 3, balloonY2 - 1, balloonY2, balloonZ - 2, balloonZ + 3, primaryColor);
			chunk.setWool(balloonX - 1, balloonX + 2, balloonY2, balloonY2 + 1, balloonZ - 1, balloonZ + 2, primaryColor);
			
			// candle in the middle
			addLight(chunk, context, balloonX, balloonY2, balloonZ);
		}
	}

	public final static int hotairBalloonHeight = 30;
	public void generateHotairBalloon(CityWorldGenerator generator, SupportBlocks chunk, DataContext context, 
			int bottomY, Odds odds) {
		int balloonY1 = bottomY + 5;
		int balloonY2 = balloonY1 + 20;
		
		chunk.setBlocks(6, 10, bottomY, 6, 10, Material.HAY_BLOCK);
		chunk.setWalls(5, 11, bottomY + 1, bottomY + 2, 5, 11, Material.HAY_BLOCK);

		generator.spawnProvider.spawnBeing(generator, chunk, odds, 7, bottomY + 1, 7);
		
		attachString(chunk, 5, bottomY + 2, balloonY1, 5);
		attachString(chunk, 5, bottomY + 2, balloonY1, 10);
		attachString(chunk, 10, bottomY + 2, balloonY1, 5);
		attachString(chunk, 10, bottomY + 2, balloonY1, 10);
		
		generateBigBalloon(generator, chunk, context, balloonY1, balloonY2, odds);
	}
	
	public void generateBigBalloon(CityWorldGenerator generator, SupportBlocks chunk, DataContext context, 
			int attachY, Odds odds) {
		int balloonY1 = attachY + 4 + odds.getRandomInt(4);
		int balloonY2 = balloonY1 + 15 + odds.getRandomInt(15);
		
		// draw the strings
		boolean strung = attachString(chunk, 7 + odds.getRandomInt(2), attachY, balloonY1 + 5, 4);
		strung = attachString(chunk, 7 + odds.getRandomInt(2), attachY, balloonY1 + 5, 11) || strung;
		strung = attachString(chunk, 4, attachY, balloonY1 + 5, 7 + odds.getRandomInt(2)) || strung;
		strung = attachString(chunk, 11, attachY, balloonY1 + 5, 7 + odds.getRandomInt(2)) || strung;
		
		// are we attached?
		if (strung)
			generateBigBalloon(generator, chunk, context, balloonY1, balloonY2, odds);
	}
	
	private void generateBigBalloon(CityWorldGenerator generator, SupportBlocks chunk, DataContext context, 
			int balloonY1, int balloonY2, Odds odds) {
		
		// pick the colors
		DyeColor primaryColor = getPrimaryColor(generator, odds);
		DyeColor secondaryColor = getSecondaryColor(generator, odds);
		
		// draw the bottom of the blimp
		chunk.setCircle(8, 8, 3, balloonY1 - 1, Material.WOOL, primaryColor);
		chunk.setCircle(8, 8, 4, balloonY1, balloonY1 + 3, Material.WOOL, primaryColor, true);
		chunk.setCircle(8, 8, 5, balloonY1 + 3, balloonY1 + 7, Material.WOOL, primaryColor, true);
		chunk.setCircle(8, 8, 6, balloonY1 + 7, Material.WOOL, primaryColor, true);
		
		// middle of the blimp
		int step = 2 + odds.getRandomInt(4);
		int y = balloonY1 + 8;
		do {
			DyeColor color = primaryColor;
			if (y % step != 0)
				color = secondaryColor;
			chunk.setCircle(8, 8, 6, y, Material.WOOL, color, true);
			y++;
		} while (y < balloonY2 - 3);
		
		// now the top of the balloon
		chunk.setCircle(8, 8, 6, balloonY2 - 3, Material.WOOL, primaryColor, true);
		chunk.setCircle(8, 8, 5, balloonY2 - 2, balloonY2, Material.WOOL, primaryColor, true);
		chunk.setCircle(8, 8, 4, balloonY2, Material.WOOL, primaryColor, true);
		
		// add the lights
		addLight(chunk, context, 8, balloonY2, 8);
//		addLight(chunk, context, 7, balloonY2, 4);
//		addLight(chunk, context, 8, balloonY2, 11);
//		addLight(chunk, context, 4, balloonY2, 8);
//		addLight(chunk, context, 11, balloonY2, 7);

	}
	
	private boolean attachString(AbstractBlocks chunk, int x, int y1, int y2, int z) {
		boolean result = !chunk.isEmpty(x, y1 - 1, z);
		if (result)
			chunk.setBlocks(x, y1, y2, z, Material.FENCE);
		return result;
	}
	
	private DyeColor getPrimaryColor(CityWorldGenerator generator, Odds odds) {
		if (generator.worldEnvironment == Environment.NETHER)
			return getSecondaryColor(generator, odds);
		else
			return odds.getRandomLightColor();
	}

	private DyeColor getSecondaryColor(CityWorldGenerator generator, Odds odds) {
		return odds.getRandomDarkColor();
	}
	
	private void addLight(AbstractBlocks chunk, DataContext context, int x, int y, int z) {
		chunk.setBlock(x, y, z, Material.AIR);
		chunk.setBlock(x, y - 1, z, context.lightMat);
	}
	
	public void generateSaucer(CityWorldGenerator generator, SupportBlocks chunk, int y, boolean drawLegs) {
		generateSaucer(generator, chunk, 7, y, 7, drawLegs);
	}
	
	public void generateSaucer(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z, boolean drawLegs) {
		if (drawLegs) {
			generateSaucer(generator, chunk, x, y + 2, z);

			// now the legs
			chunk.setBlocks(x - 3, y, y + 2, z - 3, Material.QUARTZ_BLOCK);
			chunk.setBlocks(x + 2, y, y + 2, z - 3, Material.QUARTZ_BLOCK);
			chunk.setBlocks(x - 3, y, y + 2, z + 2, Material.QUARTZ_BLOCK);
			chunk.setBlocks(x + 2, y, y + 2, z + 2, Material.QUARTZ_BLOCK);
		} else
			generateSaucer(generator, chunk, x, y, z);
	}
	
	private void generateSaucer(CityWorldGenerator generator, SupportBlocks chunk, int x, int y, int z) {
		chunk.setCircle(x, z, 4, y, Material.QUARTZ_BLOCK, true);
		chunk.setCircle(x, z, 1, y, Material.GLASS, true);
		chunk.setCircle(x, z, 5, y + 1, Material.QUARTZ_BLOCK, true);
		chunk.setCircle(x, z, 2, y + 1, Material.GLASS, true);
		chunk.setCircle(x, z, 4, y + 2, Material.REDSTONE_BLOCK, true);
		chunk.setCircle(x, z, 2, y + 3, Material.QUARTZ_BLOCK, true);
		chunk.setCircle(x, z, 1, y + 4, Material.GLASS, true);
	}
}
