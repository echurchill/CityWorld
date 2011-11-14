package me.daddychurchill.CityWorld;

import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Handler for the /debug command.
 * @author HaxtorMoogle
 */
public class CityWorldDebugCMD implements CommandExecutor 
{
    private final CityWorld plugin;

    public CityWorldDebugCMD(CityWorld plugin)
    {
        this.plugin = plugin;
    }

	public boolean onCommand(CommandSender sender, Command command, String label, String[] split) 
    {
        if (sender instanceof Player) 
        {
            Player player = (Player) sender;
            plugin.setDebugging(player, !plugin.isDebugging(player));
            return true;
        } 
        else 
        {
            return false;
        }
    }
}
