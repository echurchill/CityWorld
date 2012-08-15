package me.daddychurchill.CityWorld.Support;

import java.util.Random;

import org.bukkit.Material;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;

public class BalloonFactory {
	
	//TODO need bigger balloons
	//TODO need better balloons
	//TODO need better strings
	
	public final static void generateBalloon(WorldGenerator generator, RealChunk chunk, DataContext context, 
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
		chunk.setBlock(balloonX, balloonY + 7, balloonZ, Material.AIR);
		chunk.setBlock(balloonX, balloonY + 6, balloonZ, context.lightMat, true);
	}
}
