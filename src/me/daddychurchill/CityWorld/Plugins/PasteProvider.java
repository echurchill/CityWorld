package me.daddychurchill.CityWorld.Plugins;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.ConstructClipboard;

public abstract class PasteProvider {

	public PasteProvider() {
		// TODO Auto-generated constructor stub
	}

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	
	public abstract ConstructClipboard findConstruct(WorldGenerator generator, Random random, int sizeX, int sizeZ, String prefix);

	public static PasteProvider loadProvider(WorldGenerator generator) {

		PasteProvider provider = null;
		
		// try worldedit...
		provider = PasteProvider_WorldEdit.loadWorldEdit(generator);
		
		// default to stock PasteProvider
		if (provider == null) {
			provider = new PasteProvider_Normal(generator);
		}
	
		return provider;
	}
}
