package me.daddychurchill.CityWorld;

import java.util.List;

import me.daddychurchill.CityWorld.Support.SupportBlocks;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public class CommandCityChunk implements CommandExecutor {
    private final CityWorld plugin;

	public CommandCityChunk(CityWorld plugin)
    {
        this.plugin = plugin;
    }

	public CityWorld getWorld() {
		return plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
		Player player = null;
		if (sender instanceof Player)
			player = (Player) sender;
//		else {
//			player = sender.getServer().getPlayer("echurchill");
//			if (player != null)
//				sender.sendMessage("Found " + player);
//		}

		if (player != null) {
			if (player.hasPermission("citychunk.command")) {
				boolean cleaning = false;
				boolean regening = false;
				boolean error = false;
				int radius = 0;
				
				// arguments?
				for (int n = 0; n < split.length; n++) {
					if (split[n].compareToIgnoreCase("CLEAN") == 0 && !cleaning)
						cleaning = true;
					else if (split[n].compareToIgnoreCase("REGEN") == 0 && !regening)
						regening = true;
					else if (cleaning || regening) {
						try {
							radius = Integer.parseInt(split[n]);
						} catch (NumberFormatException e) {
							error = true;
							break;
						}
						radius = Math.max(0, Math.min(15, radius));
					} else {
						error = true;
						break;
					}
				}
				
				// that isn't an option we support or no option was given
				if (error || !(cleaning || regening)) {
					sender.sendMessage("Syntax error");
					return false;
						
				// let's do our stuff
				} else {
					
					// find ourselves
					World world = player.getWorld();
					Location location = player.getLocation();
					Chunk chunk = world.getChunkAt(location);
					int chunkX = chunk.getX();
					int chunkZ = chunk.getZ();
					
					// iterate through the chunks
					if (regening) {
						for (int x = chunkX - radius; x <= chunkX + radius; x++) {
							for (int z = chunkZ - radius; z <= chunkZ + radius; z++) {
								player.sendMessage("Regenerating chunk[" + x + ", " + z + "]");
								world.regenerateChunk(x, z);
							}
						}
					}
					
					// cleaning up chunks of stray items
					if (cleaning) {
						player.sendMessage("Cleaning up orphan items");
						int x1 = (chunkX - radius) * SupportBlocks.sectionBlockWidth;
						int x2 = (chunkX + radius + 1) * SupportBlocks.sectionBlockWidth;
						int z1 = (chunkZ - radius) * SupportBlocks.sectionBlockWidth;
						int z2 = (chunkZ + radius + 1) * SupportBlocks.sectionBlockWidth;
						List<Entity> entities = world.getEntities();
						for (Entity entity : entities) {
							
							// something we care about?
							if (entity instanceof Item) {
								
								// is it in the right place?
								Location entityAt = entity.getLocation();
								if (entityAt.getBlockX() >= x1 && entityAt.getBlockX() < x2 &&
									entityAt.getBlockZ() >= z1 && entityAt.getBlockZ() < z2)
									entity.remove();
							}
						}
					}
					
					// all done
					player.sendMessage("Finished chunk operation");
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
}
