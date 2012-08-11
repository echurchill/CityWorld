package me.daddychurchill.CityWorld;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandCityWorld implements CommandExecutor {
    private final CityWorld plugin;

    public CommandCityWorld(CityWorld plugin)
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
				
				// okay, let's enter the city
				} else {
					World world = getDefaultCityWorld();
					if (world == null) {
						sender.sendMessage("Cannot find the default CityWorld");
						return false;
					} else if (player.getLocation().getWorld() == world) {
						sender.sendMessage("You are already there");
						return true;
					} else {
						player.sendMessage("Loading/creating CityWorld... This might take a moment...");
						player.teleport(world.getSpawnLocation());
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

    // prime world support (loosely based on ExpansiveTerrain)
	public final static String DEFAULT_WORLD_NAME = "CityWorld";
	public World getDefaultCityWorld() {
		
		// built yet?
		World cityWorldPrime = Bukkit.getServer().getWorld(DEFAULT_WORLD_NAME);
		if (cityWorldPrime == null) {
			
			// if neither then create/build it!
			WorldCreator worldcreator = new WorldCreator(DEFAULT_WORLD_NAME);
			worldcreator.environment(World.Environment.NORMAL);
			//worldcreator.seed(-7457540200860308014L); // Beta seed
			//worldcreator.seed(5509442565638151977L); // 82,-35
			worldcreator.generator(new WorldGenerator(plugin, DEFAULT_WORLD_NAME, ""));
			cityWorldPrime = Bukkit.getServer().createWorld(worldcreator);
		}
		return cityWorldPrime;
	}
}
