package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Support.HouseFactory;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class PlatHouse extends PlatIsolated {

	public PlatHouse(Random random, PlatMap platmap) {
		super(random, platmap);
		
		style = LotStyle.STRUCTURE;
	}

	@Override
	public void generateBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, ContextData context, int platX, int platZ) {
		super.generateBlocks(generator, platmap, chunk, context, platX, platZ);

		// ground please
		chunk.setLayer(context.streetLevel, Material.GRASS);
		
		// now make a house
		HouseFactory.generateHouse(chunk, context, context.streetLevel + 1, 2);
	}
	
}
