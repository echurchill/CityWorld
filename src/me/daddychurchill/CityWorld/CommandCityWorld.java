package me.daddychurchill.CityWorld;

import me.daddychurchill.CityWorld.WorldGenerator.WorldStyle;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
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
				WorldStyle style = WorldStyle.NORMAL;
				Environment environment = Environment.NORMAL;
				boolean error = false;
				
				// arguments?
				for (int n = 0; n < split.length; n++) {
					if (split[n].compareToIgnoreCase("LEAVE") == 0)
						leaving = true;
					
					else if (split[n].compareToIgnoreCase("NETHER") == 0)
						environment = Environment.NETHER;
					
					else if (split[n].compareToIgnoreCase("THE_END") == 0)
						environment = Environment.THE_END;
					
//					else if (split[n].compareToIgnoreCase("FLOATING") == 0)
//						style = WorldStyle.FLOATING;
//					
//					else if (split[n].compareToIgnoreCase("FLOODED") == 0)
//						style = WorldStyle.FLOODED;
//					
//					else if (split[n].compareToIgnoreCase("SANDDUNES") == 0)
//						style = WorldStyle.SANDDUNES;
//					
//					else if (split[n].compareToIgnoreCase("SNOWDUNES") == 0)
//						style = WorldStyle.SNOWDUNES;
//					
					else if (split[n].compareToIgnoreCase("NORMAL") == 0) {
						environment = Environment.NORMAL;
						style = WorldStyle.NORMAL;
					
					} else {
						error = true;
						break;
					}
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
					World world = Bukkit.getServer().getWorld(DEFAULT_WORLD_NAME);
					
					// if the world doesn't exist but the player has permission to create it
					if (world == null && player.hasPermission("cityworld.create")) {
						sender.sendMessage("Creating CityWorld... This will take a moment...");
						world = getDefaultCityWorld(style, environment);
					}
					
					// test to see if it exists
					if (world == null) {
						sender.sendMessage("Cannot find or create the default CityWorld");
						return false;
					} else {
						
						// are we actually going to the right place
						if (!(world.getGenerator() instanceof WorldGenerator))
							sender.sendMessage("WARNING: The world called CityWorld does NOT use the CityWorld generator");
						
						// actually go there then
						if (player.getLocation().getWorld() == world) {
							sender.sendMessage("You are already here");
							return true;
						} else {
							player.sendMessage("Entering CityWorld...");
							player.teleport(world.getSpawnLocation());
							return true;
						}
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
	public World getDefaultCityWorld(WorldStyle style, Environment environment) {
		
		// built yet?
		World cityWorldPrime = Bukkit.getServer().getWorld(DEFAULT_WORLD_NAME);
		if (cityWorldPrime == null) {
			
			// if neither then create/build it!
			WorldCreator worldcreator = new WorldCreator(DEFAULT_WORLD_NAME);
			//worldcreator.seed(-7457540200860308014L); // Beta seed
			//worldcreator.seed(5509442565638151977L); // 82,-35
			worldcreator.environment(environment);
			worldcreator.generator(new WorldGenerator(plugin, DEFAULT_WORLD_NAME, style.toString()));
			cityWorldPrime = Bukkit.getServer().createWorld(worldcreator);
		}
		return cityWorldPrime;
	}
}
