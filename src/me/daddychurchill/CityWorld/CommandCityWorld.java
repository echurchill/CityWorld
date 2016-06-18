package me.daddychurchill.CityWorld;

import me.daddychurchill.CityWorld.CityWorldGenerator.WorldStyle;

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
					if (split[n].compareToIgnoreCase("LEAVE") == 0) {
						leaving = true;
						break;
					}
					
					else if (split[n].compareToIgnoreCase("NETHER") == 0) {
						environment = Environment.NETHER;
					}
					
					else if (split[n].compareToIgnoreCase("THE_END") == 0) {
						environment = Environment.THE_END;
					}
					
					else try {
						style = WorldStyle.valueOf(split[n].trim().toUpperCase());
					} catch (IllegalArgumentException e) {
						CityWorld.log.info("[CityWorld] Unknown world style " + split[n]);
						
						WorldStyle[] styles = WorldStyle.values();
						StringBuilder worldStyles = new StringBuilder();
						for (WorldStyle worldStyle : styles) {
							if (worldStyles.length() != 0)
								worldStyles.append("|");
							worldStyles.append(worldStyle.toString().toLowerCase());
						}
						CityWorld.log.info("[CityWorld] this version knows about these styles: " + worldStyles.toString());
						style  = WorldStyle.NORMAL;
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
					String worldName = getDefaultWorldName(style, environment);
					World world = Bukkit.getServer().getWorld(worldName);
					
					// if the world doesn't exist but the player has permission to create it
					if (world == null && player.hasPermission("cityworld.create")) {
						sender.sendMessage("Creating " + worldName + "... This will take a moment...");
						world = getDefaultCityWorld(style, environment);
					}
					
					// test to see if it exists
					if (world == null) {
						sender.sendMessage("Cannot find or create " + worldName);
						return false;
					} else {
						
						// are we actually going to the right place
						if (!(world.getGenerator() instanceof CityWorldGenerator))
							sender.sendMessage("WARNING: The world called " + worldName + " does NOT use the CityWorld generator");
						
						// actually go there then
						if (player.getLocation().getWorld() == world) {
							sender.sendMessage("You are already here");
							return true;
						} else {
							player.sendMessage("Entering " + worldName + "...");
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
	
	public final static String getDefaultWorldName(WorldStyle style, Environment environment) {
		String worldName = DEFAULT_WORLD_NAME;
		style = CityWorldGenerator.validateStyle(style);
		if (style != WorldStyle.NORMAL)
			worldName = worldName + "_" + style.toString().toLowerCase();
		if (environment != Environment.NORMAL)
			worldName = worldName + "_" + environment.toString().toLowerCase();
		return worldName;
	}
	
	public World getDefaultCityWorld(WorldStyle style, Environment environment) {
		
		// built yet?
		String worldName = getDefaultWorldName(style, environment);
		World cityWorldPrime = Bukkit.getServer().getWorld(worldName);
		if (cityWorldPrime == null) {
			
			// if neither then create/build it!
			WorldCreator worldcreator = new WorldCreator(worldName);
			//worldcreator.seed(-7457540200860308014L); // Beta seed
			//worldcreator.seed(5509442565638151977L); // 82,-35
			worldcreator.environment(environment);
			worldcreator.generator(new CityWorldGenerator(plugin, worldName, style.toString()));
			cityWorldPrime = Bukkit.getServer().createWorld(worldcreator);
		}
		return cityWorldPrime;
	}
}
