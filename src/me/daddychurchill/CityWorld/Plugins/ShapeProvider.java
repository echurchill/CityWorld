package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.World;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import me.daddychurchill.CityWorld.WorldGenerator;

public abstract class ShapeProvider {
	
	//TODO add a floating ground version for TheEnd
	//TODO add a cave version for Nether
	
	public abstract int getWorldHeight();
	public abstract int getSeaLevel();
	public abstract int getLandRange();
	public abstract int getSeaRange();
	
	public abstract double findPerciseY(WorldGenerator generator, int blockX, int blockZ);

	//TODO refactor these over to UndergroundProvider (which should include PlatLot's mines generator code)
	//TODO rename these to ifSoAndSo
	public abstract boolean isHorizontalNSShaft(int chunkX, int chunkY, int chunkZ);
	public abstract boolean isHorizontalWEShaft(int chunkX, int chunkY, int chunkZ);
	public abstract boolean isVerticalShaft(int chunkX, int chunkY, int chunkZ);
	
	//TODO refactor this so that it is a positive (maybe ifCave) instead of a negative
	public abstract boolean notACave(WorldGenerator generator, int blockX, int blockY, int blockZ);
	
	public SimplexNoiseGenerator macroShape;
	public SimplexNoiseGenerator microShape;
	
	public double macroScale = 1.0 / 384.0;
	public double microScale = 2.0;
	
	public double getMicroNoiseAt(double x, double z, int a) {
		return microShape.noise(x * microScale, z * microScale, a);
	}
	
	public double getMacroNoiseAt(double x, double z, int a) {
		return macroShape.noise(x * macroScale, z * macroScale, a);
	}
	
	public ShapeProvider(WorldGenerator generator) {
		World world = generator.getWorld();
		long seed = world.getSeed();
		
		macroShape = new SimplexNoiseGenerator(seed + 2);
		microShape = new SimplexNoiseGenerator(seed + 3);
	}

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	public static ShapeProvider loadProvider(WorldGenerator generator) {

		ShapeProvider provider = null;
		
//		// try need something like PhatLoot but for ores
//		provider = ShapeProvider_PhatShape.loadPhatShapes();
		
		// default to stock ShapeProvider
		if (provider == null) {
			
//			if (generator.settings.environment == Environment.NETHER)
//				provider = new ShapeProvider_Nether();
//			else if (generator.settings.environment == Environment.THE_END)
//				provider = new ShapeProvider_TheEnd();
//			else
				provider = new ShapeProvider_Normal(generator);
		}
	
		return provider;
	}
}
