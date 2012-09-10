package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.plugin.Plugin;

public abstract class Provider {

	public Provider() {
		// TODO Auto-generated constructor stub
	}
	
	protected static boolean isPlugInVersionOrBetter(Plugin plugin, String version) {
		String text = plugin.getDescription().getVersion();
		return text.compareToIgnoreCase(version) >= 0;
	}

}
