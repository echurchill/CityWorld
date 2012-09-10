package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;
import org.bukkit.World.Environment;

public class BalloonProvider extends Provider {

	public BalloonProvider() {
		super();
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
		// where is the balloon
		int balloonX = attachX;
		int balloonY1 = attachY + random.nextInt(10) + 5;
		int balloonY2 = balloonY1 + random.nextInt(3) + 8;
		int balloonZ = attachZ;
		
		// string please
		if (attachString(chunk, balloonX, attachY, balloonY1, balloonZ)) {
			
			// pick the colors
			byte primaryColor = getPrimaryColor(generator, random);
			byte secondaryColor = getSecondaryColor(generator, random);
			
			// draw the balloon
			chunk.setBlocks(balloonX, balloonY1, balloonY1 + 2, balloonZ, Material.WOOL, primaryColor);
			
			chunk.setBlocks(balloonX - 1, balloonX + 2, balloonY1 + 2, balloonY1 + 4, balloonZ - 1, balloonZ + 2, Material.WOOL, primaryColor);
			chunk.setBlock(balloonX - 1, balloonY1 + 2, balloonZ - 1, Material.AIR);
			chunk.setBlock(balloonX - 1, balloonY1 + 2, balloonZ + 1, Material.AIR);
			chunk.setBlock(balloonX + 1, balloonY1 + 2, balloonZ - 1, Material.AIR);
			chunk.setBlock(balloonX + 1, balloonY1 + 2, balloonZ + 1, Material.AIR);
			
			chunk.setBlocks(balloonX - 2, balloonX + 3, balloonY1 + 4, balloonY1 + 6, balloonZ - 2, balloonZ + 3, Material.WOOL, primaryColor);
			chunk.setBlock(balloonX - 2, balloonY1 + 4, balloonZ - 2, Material.AIR);
			chunk.setBlock(balloonX - 2, balloonY1 + 4, balloonZ + 2, Material.AIR);
			chunk.setBlock(balloonX + 2, balloonY1 + 4, balloonZ - 2, Material.AIR);
			chunk.setBlock(balloonX + 2, balloonY1 + 4, balloonZ + 2, Material.AIR);
			
			for (int y = balloonY1 + 6; y < balloonY2 - 1; y++)
				chunk.setBlocks(balloonX - 2, balloonX + 3, y, balloonZ - 2, balloonZ + 3, Material.WOOL, secondaryColor);
			
			chunk.setBlocks(balloonX - 2, balloonX + 3, balloonY2 - 1, balloonZ - 2, balloonZ + 3, Material.WOOL, primaryColor);
			chunk.setBlocks(balloonX - 1, balloonX + 2, balloonY2, balloonZ - 1, balloonZ + 2, Material.WOOL, primaryColor);
			
			// candle in the middle
			addLight(chunk, context, balloonX, balloonY2, balloonZ);
		}
	}

	public void generateBlimp(WorldGenerator generator, RealChunk chunk, DataContext context, 
			int attachY, Random random) {
		int balloonY1 = attachY + random.nextInt(4) + 4;
		int balloonY2 = balloonY1 + random.nextInt(15) + 15;
		
		// draw the strings
		boolean strung = attachString(chunk, 7 + random.nextInt(2), attachY, balloonY1 + 5, 2);
		strung = attachString(chunk, 7 + random.nextInt(2), attachY, balloonY1 + 5, 13) || strung;
		strung = attachString(chunk, 2, attachY, balloonY1 + 5, 7 + random.nextInt(2)) || strung;
		strung = attachString(chunk, 13, attachY, balloonY1 + 5, 7 + random.nextInt(2)) || strung;
		
		// are we attached?
		if (strung) {
			
			// pick the colors
			byte primaryColor = getPrimaryColor(generator, random);
			byte secondaryColor = getSecondaryColor(generator, random);
			
			// draw the bottom of the blimp
			chunk.setCircle(8, 8, 3, balloonY1 - 1, Material.WOOL, primaryColor, true);
			chunk.setCircle(8, 8, 4, balloonY1, balloonY1 + 3, Material.WOOL, primaryColor, true);
			chunk.setCircle(8, 8, 5, balloonY1 + 3, balloonY1 + 7, Material.WOOL, primaryColor, true);
			chunk.setCircle(8, 8, 6, balloonY1 + 7, Material.WOOL, primaryColor, true);
			
			// middle of the blimp
			int step = random.nextInt(4) + 2;
			int y = balloonY1 + 8;
			do {
				byte color = primaryColor;
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
			addLight(chunk, context, 7, balloonY2, 4);
			addLight(chunk, context, 8, balloonY2, 11);
			addLight(chunk, context, 4, balloonY2, 8);
			addLight(chunk, context, 11, balloonY2, 7);
		}
	}
	
	private boolean attachString(RealChunk chunk, int x, int y1, int y2, int z) {
		boolean result = !chunk.isEmpty(x, y1 - 1, z);
		if (result)
			chunk.setBlocks(x, y1, y2, z, Material.FENCE);
		return result;
	}
	
	private byte getPrimaryColor(WorldGenerator generator, Random random) {
		if (generator.worldEnvironment == Environment.NETHER)
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
