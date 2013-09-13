package me.daddychurchill.CityWorld.Clipboard;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plugins.Provider;
import me.daddychurchill.CityWorld.Plugins.WorldEdit.PasteProvider_WorldEdit;

public abstract class PasteProvider extends Provider {

	public enum SchematicFamily {ROUNDABOUT, PARK, HIGHRISE, MIDRISE, LOWRISE, INDUSTRIAL, MUNICIPAL, CONSTRUCTION, 
		NEIGHBORHOOD, FARM, NATURE};
	
	public PasteProvider() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClipboardList getFamilyClips(WorldGenerator generator, SchematicFamily family, int maxChunkX, int maxChunkZ) {
		ClipboardList clips = new ClipboardList();
		try {
			
			// try and load
			loadClips(generator, family, clips, maxChunkX, maxChunkZ);
			
		} catch (Exception e) {
			generator.reportException("[PasteProvider] " + family.toString() + " could NOT be loaded", e);

		} 
			
		// return the clips
		schematicsLoaded += clips.count();
		return clips;
	}
	
	protected abstract void loadClips(WorldGenerator generator, SchematicFamily family, ClipboardList clips, int maxX, int maxZ) throws Exception;

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
