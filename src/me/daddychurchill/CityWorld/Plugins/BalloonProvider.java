package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;
import org.bukkit.World.Environment;

public class BalloonProvider {

	public BalloonProvider() {
		// TODO Auto-generated constructor stub
	}
	
	public final static BalloonProvider loadProvider(WorldGenerator generator) {
		// for now
		return new BalloonProvider();
	}

	//TODO need bigger balloons
	//TODO need better balloons
	//TODO need better strings
	
	public void generateBalloon(WorldGenerator generator, RealChunk chunk, DataContext context, 
			int attachX, int attachY, int attachZ, Random random) {
		int balloonX = attachX;
		int balloonY = attachY + random.nextInt(10) + 5;
		int balloonZ = attachZ;
		
		// a little string
		chunk.setBlocks(balloonX, attachY, balloonY, balloonZ, Material.FENCE);
		
		// pick the colors
		byte primaryColor = (byte) random.nextInt(7);
		byte secondaryColor = (byte) (random.nextInt(8) + 7);
		
		// draw the balloon
		chunk.setBlocks(balloonX, balloonY, balloonY + 2, balloonZ, Material.WOOL, primaryColor);
		chunk.setBlocks(balloonX - 1, balloonX + 2, balloonY + 2, balloonY + 4, balloonZ - 1, balloonZ + 2, Material.WOOL, primaryColor);
		chunk.setBlocks(balloonX - 2, balloonX + 3, balloonY + 4, balloonY + 5, balloonZ - 2, balloonZ + 3, Material.WOOL, primaryColor);
		chunk.setBlocks(balloonX - 2, balloonX + 3, balloonY + 5, balloonY + 6, balloonZ - 2, balloonZ + 3, Material.WOOL, secondaryColor);
		chunk.setBlocks(balloonX - 2, balloonX + 3, balloonY + 6, balloonY + 7, balloonZ - 2, balloonZ + 3, Material.WOOL, primaryColor);
		chunk.setBlocks(balloonX - 1, balloonX + 2, balloonY + 7, balloonY + 8, balloonZ - 1, balloonZ + 2, Material.WOOL, primaryColor);
		
		// candle in the middle
		addLight(chunk, context, balloonX, balloonY + 7, balloonZ);
	}
	
	public void generateBlimp(WorldGenerator generator, RealChunk chunk, DataContext context, 
			int attachY, Random random) {
		
		int balloonY1 = attachY + random.nextInt(4) + 4;
		int balloonY2 = balloonY1 + random.nextInt(15) + 15;
		
		// pick the colors
		byte primaryColor = getPrimaryColor(generator, random);
		byte secondaryColor = getSecondaryColor(generator, random);
		
		// draw the strings
		chunk.setBlocks(7 + random.nextInt(2), attachY, balloonY1 + 4, 1, Material.FENCE);
		chunk.setBlocks(7 + random.nextInt(2), attachY, balloonY1 + 4, 14, Material.FENCE);
		chunk.setBlocks(1, attachY, balloonY1 + 4, 7 + random.nextInt(2), Material.FENCE);
		chunk.setBlocks(14, attachY, balloonY1 + 4, 7 + random.nextInt(2), Material.FENCE);
		
		// draw the bottom of the blimp
		chunk.setCircle(8, 8, 4, balloonY1, balloonY1 + 2, Material.WOOL, primaryColor, true);
		chunk.setCircle(8, 8, 5, balloonY1 + 2, balloonY1 + 4, Material.WOOL, primaryColor, true);
		chunk.setCircle(8, 8, 6, balloonY1 + 4, Material.WOOL, primaryColor, true);
		
		// middle of the blimp
		int step = random.nextInt(4) + 2;
		int y = balloonY1 + 5;
		do {
			byte color = primaryColor;
			if (y % step != 0)
				color = secondaryColor;
			chunk.setCircle(8, 8, 6, y, Material.WOOL, color, false);
			y++;
		} while (y < balloonY2 - 3);
		
		// now the top of the balloon
		chunk.setCircle(8, 8, 6, balloonY2 - 3, Material.WOOL, primaryColor, true);
		chunk.setCircle(8, 8, 5, balloonY2 - 2, balloonY2, Material.WOOL, primaryColor, true);
		chunk.setCircle(8, 8, 4, balloonY2, Material.WOOL, primaryColor, true);
		
		// add the lights
		addLight(chunk, context, 7, balloonY2, 4);
		addLight(chunk, context, 8, balloonY2, 11);
		addLight(chunk, context, 4, balloonY2, 8);
		addLight(chunk, context, 11, balloonY2, 7);
	}
	
	private byte getPrimaryColor(WorldGenerator generator, Random random) {
		if (generator.settings.environmentStyle == Environment.NETHER)
			return getSecondaryColor(generator, random);
		else
			return (byte) random.nextInt(7);
	}

	private byte getSecondaryColor(WorldGenerator generator, Random random) {
		return (byte) (random.nextInt(9) + 7);
	}
	
	private void addLight(RealChunk chunk, DataContext context, int x, int y, int z) {
		chunk.setBlock(x, y, z, Material.AIR);
		chunk.setBlock(x, y - 1, z, context.lightMat, true);
	}
}
