package me.daddychurchill.CityWorld.Plugins;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Random;

import me.daddychurchill.CityWorld.CityWorld;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.ConstructClipboard;
import me.daddychurchill.CityWorld.Support.WorldEditClipboard;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class PasteProvider_WorldEdit extends PasteProvider {

	private WorldEditPlugin worldEditPlugin;
	private ArrayList<ConstructClipboard> clips;
	
	public PasteProvider_WorldEdit(WorldGenerator generator, WorldEditPlugin plugin) {
		super();
		worldEditPlugin = plugin;
		clips = new ArrayList<ConstructClipboard>();
		
		// find the files
		File pluginFolder = generator.getPlugin().getDataFolder();
		if (pluginFolder.isDirectory()) {
			File schematicFolder = new File(pluginFolder, "schematics");
			if (schematicFolder.isDirectory()) {
				File[] schematicFiles = schematicFolder.listFiles(matchSchematics());
				for (File schematicFile: schematicFiles) {
					try {
						clips.add(new WorldEditClipboard(worldEditPlugin, schematicFile));
						
					} catch (Exception e) {
						CityWorld.log.info("[CityWorld][WorldEdit] " + e.getMessage());
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
	public ConstructClipboard findConstruct(WorldGenerator generator, Random random, int sizeX, int sizeZ, String prefix) {
		if (!clips.isEmpty())
			return clips.get(0);
		else
			return null;
	}

	// Based on work contributed by drew-bahrue (https://github.com/echurchill/CityWorld/pull/2)
	
	private static String name = "WorldEdit";
	public static PasteProvider loadWorldEdit(WorldGenerator generator) {
		WorldEditPlugin worldEditPlugin = null;

		PluginManager pm = Bukkit.getServer().getPluginManager();

		try {
			worldEditPlugin = (WorldEditPlugin) pm.getPlugin(name);
		} catch (Exception ex) {
			CityWorld.log.info(String.format("[CityWorld][PasteProvider] Bad Version %s.", name));
		}

		if (worldEditPlugin == null)
			return null;

		CityWorld.log.info(String.format("[CityWorld][PasteProvider] Found %s.", name));
		
		try {

			if (!pm.isPluginEnabled(worldEditPlugin)) {
				//CityWorld.log.info(String.format("[CityWorld][PasteProvider] Enabling %s.", name));
				pm.enablePlugin(worldEditPlugin);
				CityWorld.log.info(String.format("[CityWorld][PasteProvider] %s Enabled.", name));
			}
			
			return new PasteProvider_WorldEdit(generator, worldEditPlugin);
			
		} catch (Exception ex) {
			CityWorld.log.info(String.format("[CityWorld][PasteProvider] Failed to enable %s.", name));
			CityWorld.log.info(ex.getStackTrace().toString());
			return null;
		}
	}

}
