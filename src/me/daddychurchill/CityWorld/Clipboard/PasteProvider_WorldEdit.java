package me.daddychurchill.CityWorld.Clipboard;

import java.io.File;
import java.io.FilenameFilter;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.SupportChunk;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class PasteProvider_WorldEdit extends PasteProvider {

	private static String name = "WorldEdit";
	private static String minVersion = "5.4.2";
	private File schematicsFolder;
	
	@Override
	public void reportStatus(WorldGenerator generator) {
		generator.reportMessage("[WorldEdit] Loaded " + schematicsLoaded + " schematic(s) for world " + generator.worldName);
	}
	
	public PasteProvider_WorldEdit(WorldGenerator generator) throws Exception {
		super();
		
		// find the files
		File pluginFolder = generator.getPlugin().getDataFolder();
		if (pluginFolder.isDirectory()) {
			
			// forget all those shape and ore type and just go for the world name
			schematicsFolder = findFolder(pluginFolder, "Schematics for " + generator.worldName);
			
//			// shape folder (normal, floating, etc.)
//			File shapeFolder = findFolder(pluginFolder, generator.shapeProvider.getCollectionName());
//			
//			// finally ores are used to figure out the collection folder (normal, nether, theend, etc.)
//			schematicsFolder = findFolder(shapeFolder, generator.oreProvider.getCollectionName());
		}
	}
	
	private File findFolder(File parent, String name) throws Exception {
		name = toCamelCase(name);
		File result = new File(parent, name);
		if (!result.isDirectory())
			if (!result.mkdir())
				throw new UnsupportedOperationException("[WorldEdit] Could not create/find the folder: " + parent.getAbsolutePath() + File.separator + name);
		return result;
	}
	
	private FilenameFilter matchSchematics() {
		return new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".schematic");
			}
		};
	}
	
	private String toCamelCase(String text) {
		return text.substring(0, 1).toUpperCase() + text.substring(1, text.length()).toLowerCase();
	}

	@Override
	public ClipboardList loadClips(WorldGenerator generator, SchematicFamily family, int maxX, int maxZ) throws Exception {
		
		// things aren't happy
		if (schematicsFolder == null)
			return null;
		
		// now for each of the context styles
		File contextFolder = findFolder(schematicsFolder, family.toString());
		
		// make room for clips
		ClipboardList clips = new ClipboardList();
		
		// now load those schematic files
		File[] schematicFiles = contextFolder.listFiles(matchSchematics());
		for (File schematicFile: schematicFiles) {
			try {
				
				// load a clipboard
				Clipboard clip = new Clipboard_WorldEdit(generator, schematicFile);
				
				// too big?
				if (clip.chunkX > maxX || clip.chunkZ > maxZ) {
					generator.reportMessage("[WorldEdit] Schematic " + schematicFile.getName() + 
							" too large, max size = " + 
							maxX * SupportChunk.chunksBlockWidth + " by " + 
							maxZ * SupportChunk.chunksBlockWidth + " it is = " + 
							clip.sizeX + " by " + clip.sizeZ + ", skipped");
					
				} else {
				
					// add the clip to the result
					clips.put(clip);
				}
				
//				generator.reportMessage("[WorldEdit] Schematic " + schematicFile.getName() + " loaded");
			} catch (Exception e) {
				generator.reportException("[WorldEdit] Schematic " + schematicFile.getName() + " could NOT be loaded", e);
			}
		}
		
		// final report
		return clips;
	}
	
	// VERY Loosely based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	public static PasteProvider loadWorldEdit(WorldGenerator generator) {
		WorldEditPlugin worldEditPlugin = null;

		try {
			PluginManager pm = Bukkit.getServer().getPluginManager();
			worldEditPlugin = (WorldEditPlugin) pm.getPlugin(name);
			if (!isPlugInVersionOrBetter(worldEditPlugin, minVersion))
				throw new UnsupportedOperationException("CityWorld requires WorldEdit v" + minVersion + " or better");
			
			// not there? darn
			if (worldEditPlugin == null)
				return null;

			// make sure it is enabled
			if (!pm.isPluginEnabled(worldEditPlugin))
				pm.enablePlugin(worldEditPlugin);

			// woot!
			generator.reportMessage("[PasteProvider] Found " + name + ", loading its schematics");
			
			return new PasteProvider_WorldEdit(generator);
			
		} catch (Exception e) {
			generator.reportException("[PasteProvider] Problem with WorldEdit", e);
			return null;
		}
	}

}
