package me.daddychurchill.CityWorld;

import java.util.logging.Logger;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class CityWorld extends JavaPlugin{
	
	public final static Logger log = Logger.getLogger("Minecraft.CityWorld");
	
    public CityWorld() {
		super();
		
	}
    
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String name, String style){
		return new CityWorldGenerator(this, name, style);
	}
	
	@Override
	public void onDisable() {
		// remember for the next time
		saveConfig();
		
		// tell the world we are out of here
		//reportMessage("Disabled");
	}

	@Override
	public void onEnable() {
		addCommand("cityworld", new CommandCityWorld(this));
		addCommand("citychunk", new CommandCityChunk(this));
		//addCommand("citylines", new CommandCityLines(this));
		addCommand("cityinfo", new CommandCityInfo(this)); // added by Sablednah (see below)
		// configFile can be retrieved via getConfig()
		//reportMessage("Enabled" );
	}
	
	private void addCommand(String keyword, CommandExecutor exec) {
		PluginCommand cmd = getCommand(keyword);
		if (cmd == null || exec == null) {
			reportMessage("[Lexicon] Cannot create command for " + keyword);
		} else {
			cmd.setExecutor(exec);
		}
	}
	
	public String getPluginName() {
		return getDescription().getName();
	}
	
	private String getQuotedPluginName() {
		return "[" + getPluginName() + "]";
	}
	
	public void reportMessage(String message) {
		if (!message.startsWith("["))
			message = " " + message;
		log.info(getQuotedPluginName() + message);
	}

	public void reportMessage(String message1, String message2) {
		reportMessage(message1);
		log.info(" \\__" + message2);
	}

	public void reportException(String message, Exception e) {
		reportMessage(message, "Exception: " + e.getMessage());
		e.printStackTrace();
	}
	
	// Added by Sablednah
	// https://github.com/echurchill/CityWorld/pull/4
	// Modified a bit by DaddyChurchill
	public CityWorldAPI getAPI(Plugin p) {
		if (p instanceof CityWorld)
			return new CityWorldAPI((CityWorld)p);
		else
			return null;
	}
}

