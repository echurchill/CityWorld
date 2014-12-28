package me.daddychurchill.CityWorld.Plugins;

import me.daddychurchill.CityWorld.CityWorldGenerator;

import org.bukkit.plugin.Plugin;

public abstract class Provider {

	public Provider() {
		// TODO Auto-generated constructor stub
	}
	
	protected static boolean isPlugInVersionOrBetter(CityWorldGenerator generator, Plugin plugin, String minVersion) {
		// doesn't exist?
		if (plugin == null)
			return false;
		
		// if it exists, what is it's version?
		String pluginVersion = plugin.getDescription().getVersion();
		
		// wrong or odd version?
		if (pluginVersion.compareToIgnoreCase(minVersion) < 0) {
			generator.reportMessage("WARNING: Found " + plugin.getName() + " version (" + pluginVersion + "), alas " + 
									generator.getPluginName() + " was tested against version (" + minVersion + ").",
									plugin.getName() + " is either out of date or is a VERY non-standard build.");

			return false;
		} else
			return true;
	}

}
