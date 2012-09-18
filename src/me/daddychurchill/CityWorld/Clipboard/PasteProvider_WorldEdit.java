package me.daddychurchill.CityWorld.Clipboard;

import java.io.File;
import java.io.FilenameFilter;
import me.daddychurchill.CityWorld.CityWorld;
import me.daddychurchill.CityWorld.WorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class PasteProvider_WorldEdit extends PasteProvider {

	private SchematicFamilyList families;
	
	public PasteProvider_WorldEdit(WorldGenerator generator) throws Exception {
		super();
		int schematicsLoaded = 0;
		
		// find the files
		File pluginFolder = generator.getPlugin().getDataFolder();
		if (pluginFolder.isDirectory()) {
			
			// shape folder (normal, floating, etc.)
			File shapeFolder = findFolder(pluginFolder, generator.shapeProvider.getCollectionName());
			
			// finally ores are used to figure out the collection folder (normal, nether, theend, etc.)
			File environmentFolder = findFolder(shapeFolder, generator.oreProvider.getCollectionName());
			
			// make room for context types
			families = new SchematicFamilyList();
			
			// now for each of the context styles
			for (SchematicFamily family: SchematicFamily.values()) {
				File contextFolder = findFolder(environmentFolder, family.toString());
				
				// make room for clips
				ClipboardList clips = families.add(family, new ClipboardList());
				
				// now load those schematic files
				File[] schematicFiles = contextFolder.listFiles(matchSchematics());
				for (File schematicFile: schematicFiles) {
					try {
						
						// create a copy of the clipboard
						clips.put(new Clipboard_WorldEdit(generator, schematicFile));
						schematicsLoaded++;
						
//						CityWorld.reportMessage("[WorldEdit] Schematic " + schematicFile.getName() + " loaded");
					} catch (Exception e) {
						CityWorld.reportException("WorldEdit] Schematic " + schematicFile.getName() + " could NOT be loaded", e);
					}
				}
			}
			
			// final report
			CityWorld.reportMessage("[WorldEdit] Loaded " + schematicsLoaded + " schematic(s)");
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
	public ClipboardList getFamilyClips(WorldGenerator generator, SchematicFamily family) {
		return families.get(family);
	}
	
	// Loosely based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	private static String name = "WorldEdit";
	private static String minVersion = "5.4.2";
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
			CityWorld.reportMessage("[PasteProvider] Found " + name + ", loading its schematics");
			
			return new PasteProvider_WorldEdit(generator);
			
		} catch (Exception e) {
			CityWorld.reportException("[PasteProvider] Problem with WorldEdit", e);
			return null;
		}
	}

}
