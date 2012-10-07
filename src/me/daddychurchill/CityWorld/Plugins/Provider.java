package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.plugin.Plugin;

public abstract class Provider {

	public Provider() {
		// TODO Auto-generated constructor stub
	}
	
	protected static boolean isPlugInVersionOrBetter(Plugin plugin, String version) {
		// doesn't exist?
		if (plugin == null)
			return false;
		
		// if it exists, what is it's version?
		String text = plugin.getDescription().getVersion();
		return text.compareToIgnoreCase(version) >= 0;
	}

}
