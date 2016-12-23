package me.daddychurchill.CityWorld.Plugins.WorldEdit;

import java.io.File;
import java.io.FilenameFilter;

import me.daddychurchill.CityWorld.CityWorldSettings;
import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Clipboard.Clipboard;
import me.daddychurchill.CityWorld.Clipboard.ClipboardList;
import me.daddychurchill.CityWorld.Clipboard.PasteProvider;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class PasteProvider_WorldEdit extends PasteProvider {

	private static String pluginName = "WorldEdit";
	private static String pluginMinVersion = "5.6";
	private File schematicsFolder;
	
	@Override
	public void reportStatus(CityWorldGenerator generator) {
		generator.reportMessage("[WorldEdit] Loaded " + schematicsLoaded + " schematic(s) for world " + generator.worldName);
	}
	
	public PasteProvider_WorldEdit(CityWorldGenerator generator) throws Exception {
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
	public void loadClips(CityWorldGenerator generator, SchematicFamily family, ClipboardList clips, int maxX, int maxZ) throws Exception {
		
		// things aren't happy
		if (schematicsFolder != null) {
			
			// now for each of the context styles
			File contextFolder = findFolder(schematicsFolder, family.toString());
			
			// now load those schematic files
			File[] schematicFiles = contextFolder.listFiles(matchSchematics());
			if (schematicFiles != null) {
				for (File schematicFile: schematicFiles) {
					try {
						
						// load a clipboard
						Clipboard clip = new Clipboard_WorldEdit(generator, schematicFile);
						
						// too big?
						if (clip.chunkX > maxX || clip.chunkZ > maxZ) {
							generator.reportMessage("[WorldEdit] Schematic " + schematicFile.getName() + 
									" too large, max size = " + 
									maxX * SupportBlocks.sectionBlockWidth + " by " + 
									maxZ * SupportBlocks.sectionBlockWidth + " it is = " + 
									clip.sizeX + " by " + clip.sizeZ + ", skipped");
							
						} else {
						
							// add the clip to the result
							clips.put(clip);
						}
						
//						generator.reportMessage("[WorldEdit] Schematic " + schematicFile.getName() + " loaded");
					} catch (Exception e) {
						generator.reportException("[WorldEdit] Schematic " + schematicFile.getName() + " could NOT be loaded", e);
					}
				}
			}
		}
	}
	
	// VERY Loosely based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	public static PasteProvider loadWorldEdit(CityWorldGenerator generator) {
//		return null;
		WorldEditPlugin worldEditPlugin = null;

		try {
			PluginManager pm = Bukkit.getServer().getPluginManager();
			if (pm != null) {
				Plugin plugin = pm.getPlugin(pluginName);
				if (plugin != null)
					worldEditPlugin = (WorldEditPlugin)plugin;
			}
			
			if (worldEditPlugin == null) {
				generator.reportMessage("[PasteProvider] Problem loading WorldEdit, could not find it");
				return null;
			}
						
			// got the right version?
			if (!isPlugInVersionOrBetter(generator, worldEditPlugin, pluginMinVersion))
				
				// Use it anyway?
				if (generator.settings.forceLoadWorldEdit) {
					generator.reportMessage("'" + CityWorldSettings.tagForceLoadWorldEdit + "' setting enabled!");
					
				// Well that didn't work... let's tell the user about a potential workaround
				} else {
					generator.reportMessage("[PasteProvider] Cannot use the installed WorldEdit. ", 
											"See the '" + CityWorldSettings.tagForceLoadWorldEdit + "' setting for possible workaround.");
					return null;
				}
			
			// make sure it is enabled
			if (!pm.isPluginEnabled(worldEditPlugin))
				pm.enablePlugin(worldEditPlugin);

			// woot!
			generator.reportMessage("[PasteProvider] Found WorldEdit v" + worldEditPlugin.getDescription().getVersion() + ", enabling its schematics");
			
			return new PasteProvider_WorldEdit(generator);
		} catch (Exception e) {
			generator.reportMessage("[PasteProvider] Problem loading WorldEdit (" + e.getMessage() + ")");
			return null;
		}
	}

}
