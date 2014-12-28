package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.World.Environment;

public class BalloonProvider extends Provider {

	public BalloonProvider() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public final static BalloonProvider loadProvider(CityWorldGenerator generator) {
		// for now
		return new BalloonProvider();
	}

	//TODO need bigger balloons
	//TODO need better balloons
	//TODO need better strings
	
	public void generateBalloon(CityWorldGenerator generator, RealChunk chunk, DataContext context, 
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

	public void generateBlimp(CityWorldGenerator generator, RealChunk chunk, DataContext context, 
			int attachY, Odds odds) {
		int balloonY1 = attachY + 4 + odds.getRandomInt(4);
		int balloonY2 = balloonY1 + 15 + odds.getRandomInt(15);
		
		// draw the strings
		boolean strung = attachString(chunk, 7 + odds.getRandomInt(2), attachY, balloonY1 + 5, 2);
		strung = attachString(chunk, 7 + odds.getRandomInt(2), attachY, balloonY1 + 5, 13) || strung;
		strung = attachString(chunk, 2, attachY, balloonY1 + 5, 7 + odds.getRandomInt(2)) || strung;
		strung = attachString(chunk, 13, attachY, balloonY1 + 5, 7 + odds.getRandomInt(2)) || strung;
		
		// are we attached?
		if (strung) {
			
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
//			addLight(chunk, context, 7, balloonY2, 4);
//			addLight(chunk, context, 8, balloonY2, 11);
//			addLight(chunk, context, 4, balloonY2, 8);
//			addLight(chunk, context, 11, balloonY2, 7);
		}
	}
	
	private boolean attachString(RealChunk chunk, int x, int y1, int y2, int z) {
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
	
	private void addLight(RealChunk chunk, DataContext context, int x, int y, int z) {
		chunk.setBlock(x, y, z, Material.AIR);
		chunk.setBlock(x, y - 1, z, context.lightMat);
	}
}
