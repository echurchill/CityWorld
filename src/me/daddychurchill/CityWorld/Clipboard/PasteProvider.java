package me.daddychurchill.CityWorld.Clipboard;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plugins.Provider;

public abstract class PasteProvider extends Provider {

	public enum SchematicFamily {ART, PARK, HIGHRISE, MIDRISE, LOWRISE, INDUSTRIAL, MUNICIPAL, CONSTRUCTION, NEIGHBORHOOD, FARM, NATURE};
	
	public PasteProvider() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClipboardList getFamilyClips(WorldGenerator generator, SchematicFamily family, int maxChunkX, int maxChunkZ) {
		try {
			
			// try and load
			ClipboardList clips = loadClips(generator, family, maxChunkX, maxChunkZ);
			if (clips != null) {
				schematicsLoaded += clips.count();
				return clips;
			}
			
		} catch (Exception e) {
			generator.reportException("[PasteProvider] " + family.toString() + " could NOT be loaded", e);
		}
		
		// assume failure
		return null;
	}
	
	protected abstract ClipboardList loadClips(WorldGenerator generator, SchematicFamily family, int maxX, int maxZ) throws Exception;

	protected int schematicsLoaded = 0;
	public abstract void reportStatus(WorldGenerator generator);
	
	// Loosely based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	public static PasteProvider loadProvider(WorldGenerator generator) {

		PasteProvider provider = null;
		
		// try worldedit...
		provider = PasteProvider_WorldEdit.loadWorldEdit(generator);
		
		// default to stock PasteProvider
		if (provider == null) {
			generator.reportMessage("[PasteProvider] WorldEdit not found, schematics disabled");
			provider = new PasteProvider_Normal(generator);
		}
	
		return provider;
	}
}
