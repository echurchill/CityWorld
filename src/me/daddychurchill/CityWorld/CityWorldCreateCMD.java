package me.daddychurchill.CityWorld;

import java.util.List;

import me.daddychurchill.CityWorld.Support.SupportChunk;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
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
				boolean regening = false;
				boolean error = false;
				
				// arguments?
				if (split.length > 0) {
					if (split[0].compareToIgnoreCase("LEAVE") == 0)
						leaving = true;
					else if (split[0].compareToIgnoreCase("REGEN") == 0)
						regening = true;
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
				
				// try to regenerate the current chunk
				} else if (regening) {
					
					// find ourselves
					World world = player.getWorld();
					Location location = player.getLocation();
					Chunk chunk = world.getChunkAt(location);
					int chunkX = chunk.getX();
					int chunkZ = chunk.getZ();
					
					// do more than just here?
					int radius = 0;
					if (split.length > 1)
						radius = Math.max(0, Math.min(5, Integer.parseInt(split[1])));
					
					// iterate through the chunks
					for (int x = chunkX - radius; x <= chunkX + radius; x++) {
						for (int z = chunkZ - radius; z <= chunkZ + radius; z++) {
							player.sendMessage("Regenerating chunk[" + x + ", " + z + "]");
							world.regenerateChunk(x, z);
						}
					}
					
					// cleaning up chunks of stray items
					player.sendMessage("Cleaning up orphan items");
					int x1 = (chunkX - radius) * SupportChunk.chunksBlockWidth;
					int x2 = (chunkX + radius + 1) * SupportChunk.chunksBlockWidth;
					int z1 = (chunkZ - radius) * SupportChunk.chunksBlockWidth;
					int z2 = (chunkZ + radius + 1) * SupportChunk.chunksBlockWidth;
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
//					
//					// iterate through the chunks
//					for (int x = chunkX - radius; x <= chunkX + radius; x++) {
//						for (int z = chunkZ - radius; z <= chunkZ + radius; z++) {
//							player.sendMessage("Refreshing chunk[" + x + ", " + z + "]");
//							world.refreshChunk(x, z);
//						}
//					}
					
					// all done
					player.sendMessage("Finished regenerating chunks");
					return true;
				
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
