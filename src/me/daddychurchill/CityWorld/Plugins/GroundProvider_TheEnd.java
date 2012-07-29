package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;

public class GroundProvider_TheEnd extends GroundProvider_Normal {

	public final static byte grassId = (byte) Material.GRASS.getId();
	public final static byte stillWaterId = (byte) Material.STATIONARY_WATER.getId();
	public final static byte endStoneId = (byte) Material.ENDER_STONE.getId();

	public GroundProvider_TheEnd(WorldGenerator generator, Random random) {
		super(generator, random);
		
		fluidId = stillWaterId;
		surfaceId = grassId;
		subsurfaceId = endStoneId;
		substratumId = endStoneId;
	}

}
