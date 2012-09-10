package me.daddychurchill.CityWorld.Clipboard;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Random;

import me.daddychurchill.CityWorld.CityWorld;
import me.daddychurchill.CityWorld.WorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class PasteProvider_WorldEdit extends PasteProvider {

	private ClipboardList clips;
	private File collectionFolder;
	
	public PasteProvider_WorldEdit(WorldGenerator generator) {
		super();
		clips = new ClipboardList();
		
		// find the files
		File pluginFolder = generator.getPlugin().getDataFolder();
		if (pluginFolder.isDirectory()) {
			
			// shape folder
			File shapeFolder = new File(pluginFolder, generator.shapeProvider.getCollectionName());
			if (!shapeFolder.isDirectory())
				pluginFolder.mkdir();
			
			// finally ores are used to figure out the collection folder 
			collectionFolder = new File(shapeFolder, generator.oreProvider.getCollectionName());
			if (!collectionFolder.isDirectory())
				collectionFolder.mkdir();
			
			// 
			File schematicFolder = new File(pluginFolder, "schematics");
			if (schematicFolder.isDirectory()) {
				File[] schematicFiles = schematicFolder.listFiles(matchSchematics());
				for (File schematicFile: schematicFiles) {
					try {
						
						// create a copy of the clipboard
						clips.put(new Clipboard_WorldEdit(generator, schematicFile));
						
						CityWorld.reportMessage("[WorldEdit] Schematic " + schematicFile.getName() + " loaded");
					} catch (Exception e) {
						CityWorld.reportException("WorldEdit] Schematic " + schematicFile.getName() + " could NOT be loaded", e);
					}
				}
			}
		}
	}
	
	private FilenameFilter matchSchematics() {
		return new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return !name.endsWith(".schematics");
			}
		};
	}

	@Override
	public Clipboard findConstruct(WorldGenerator generator, Random random, AreaTypes area, PlatTypes plat, int sizeX, int sizeZ) {
		if (!clips.isEmpty())
			return clips.getHack();
		else
			return null;
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
			CityWorld.reportMessage("[PasteProvider] Found " + name + ", schematics enabled");
			return new PasteProvider_WorldEdit(generator);
			
		} catch (Exception e) {
			CityWorld.reportException("[PasteProvider] Problem with WorldEdit", e);
			return null;
		}
	}

}
