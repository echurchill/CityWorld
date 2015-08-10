package me.daddychurchill.CityWorld;

import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCityLines implements CommandExecutor {
    private final CityWorld plugin;

	public CommandCityLines(CityWorld plugin)
    {
        this.plugin = plugin;
    }

	public CityWorld getWorld() {
		return plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

			if (player.hasPermission("citylines.command")) {
				int widthX = 1;
				int widthZ = 1;
				int floorsUp = 1;
				int floorsDown = 0;
				boolean error = false;
				
				// arguments?
				try {
					if (split.length >= 1) {
						widthX = Math.min(8, Math.max(1, Integer.parseInt(split[0])));
						if (split.length >= 2) {
							widthZ = Math.min(8, Math.max(1, Integer.parseInt(split[1])));
							if (split.length >= 3) {
								floorsUp = Math.min(16, Math.max(1, Integer.parseInt(split[2])));
								if (split.length >= 4) {
									floorsDown = Math.min(4, Math.max(0, Integer.parseInt(split[3])));
								}
							}
						}
					}
				} catch (NumberFormatException e) {
					error = true;
				}
				
				// that isn't an option we support or no option was given
				if (error) {
					sender.sendMessage("Syntax error");
					return false;
						
				// let's do our stuff
				} else {
					
					// find ourselves
					World world = player.getWorld();
					Location location = player.getLocation();
					Chunk chunk = world.getChunkAt(location);
					int areaX1 = chunk.getX() * SupportBlocks.sectionBlockWidth;
					int areaX2 = areaX1 + widthX * SupportBlocks.sectionBlockWidth;
					int groundY = location.getBlockY();
					int areaZ1 = chunk.getZ() * SupportBlocks.sectionBlockWidth;
					int areaZ2 = areaZ1 + widthZ * SupportBlocks.sectionBlockWidth;
					
					// place markers
					for (int x = 0; x <= widthX; x++) {
						int markerX = areaX1 + x * SupportBlocks.sectionBlockWidth;
						placeMarker(world, markerX, groundY, areaZ1 - 1, floorsUp, floorsDown);
						placeMarker(world, markerX, groundY, areaZ2 + 1, floorsUp, floorsDown);
					}
					for (int z = 0; z <= widthZ; z++) {
						int markerZ = areaZ1 + z * SupportBlocks.sectionBlockWidth;
						placeMarker(world, areaX1 - 1, groundY, markerZ, floorsUp, floorsDown);
						placeMarker(world, areaX2 + 1, groundY, markerZ, floorsUp, floorsDown);
					}
					
					// all done
					player.sendMessage("Finished chunk lines");
					return true;
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
	
	private void placeMarker(World world, int markerX, int groundY, int markerZ, int floorsUp, int floorsDown) {
		for (int b = 1; b < floorsDown; b++)
			world.getBlockAt(markerX, groundY - b * DataContext.FloorHeight, markerZ).setType(Material.BEDROCK);
		for (int y = 0; y <= floorsUp; y++)
			world.getBlockAt(markerX, groundY + y * DataContext.FloorHeight, markerZ).setType(Material.BEDROCK);
	}
}
