package me.daddychurchill.CityWorld.Clipboard;

import me.daddychurchill.CityWorld.CityWorld;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plugins.Provider;

public abstract class PasteProvider extends Provider {

	public enum SchematicFamily {ART, HIGHRISE, MIDRISE, LOWRISE, INDUSTRIAL, CITYCENTER, CONSTRUCTION, NEIGHBORHOOD, FARM, NATURE};
	
	public PasteProvider() {
		super();
		// TODO Auto-generated constructor stub
	}

	public abstract ClipboardList getFamilyClips(WorldGenerator generator, SchematicFamily family);
	
	// Loosely based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	public static PasteProvider loadProvider(WorldGenerator generator) {

		PasteProvider provider = null;
		
		// try worldedit...
		provider = PasteProvider_WorldEdit.loadWorldEdit(generator);
		
		// default to stock PasteProvider
		if (provider == null) {
			CityWorld.reportMessage("[PasteProvider] WorldEdit not found, schematics disabled");
			provider = new PasteProvider_Normal(generator);
		}
	
		return provider;
	}
}
