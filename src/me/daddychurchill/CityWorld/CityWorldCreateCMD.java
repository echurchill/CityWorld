package me.daddychurchill.CityWorld;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CityWorldCreateCMD implements CommandExecutor {
    private final CityWorld plugin;

    public CityWorldCreateCMD(CityWorld plugin)
    {
        this.plugin = plugin;
    }

	public boolean onCommand(CommandSender sender, Command command, String label, String[] split) 
    {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("cityworld.command")) {
				boolean leaving = false;
				boolean error = false;
				
				// arguments?
				if (split.length > 0) {
					if (split[0].compareToIgnoreCase("LEAVE") == 0)
						leaving = true;
					else 
						error = true;
				}
				
				// that isn't an option we support
				if (error) {
					sender.sendMessage("Syntax error");
					return false;
					
				// let's try to leave the city
				} else if (leaving) {
					World world = Bukkit.getServer().getWorld("world");
					if (world == null) {
						sender.sendMessage("Cannot find the default world");
						return false;
					} else if (player.getLocation().getWorld() == world) {
						sender.sendMessage("You are already there");
						return true;
					} else {
						player.sendMessage("Leaving CityWorld... Come back soon!");
						player.teleport(world.getHighestBlockAt(world.getSpawnLocation()).getLocation());
						return true;
					}
				
				// ok let's enter the city
				} else {
					World world = plugin.getCityWorld();
					if (world == null) {
						sender.sendMessage("Cannot find the default CityWorld");
						return false;
					} else if (player.getLocation().getWorld() == world) {
						sender.sendMessage("You are already there");
						return true;
					} else {
						player.sendMessage("Loading/creating CityWorld... This might take a moment...");
						player.teleport(plugin.getCityWorld().getSpawnLocation());
						return true;
					}
				}
			} else {
				sender.sendMessage("You do not have permission to use this command");
				return false;
			}
		} else {
			sender.sendMessage("This command is only usable by a player");
			return false;
		}
    }
}
