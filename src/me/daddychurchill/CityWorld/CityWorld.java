package me.daddychurchill.CityWorld;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CityWorld extends JavaPlugin{


	/**
     * Logger for debugging.
     */
    public static final Logger log = Logger.getLogger("Minecraft.CityWorld");
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    
   	protected String pluignDirPath;
   	protected File configFile;
   	
	@Override
	public void onDisable() {
		log.info("CityWorld has been disabled");
		
	}

	@Override
	public void onEnable() {
		pluignDirPath = getDataFolder().getAbsolutePath();
		configFile = new File(pluignDirPath + File.separator + "config.yml");

		PluginManager pm = getServer().getPluginManager();
		getCommand("debug").setExecutor(new CityWorldDebugCMD(this));

		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );

	}
	public boolean isDebugging(final Player player) 
	{
        if (debugees.containsKey(player))
        {
            return debugees.get(player);
        } 
        else 
        {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) 
    {
        debugees.put(player, value);
    }

	
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id){
		return new CityWorldGenerator(this, id);
	}
}
