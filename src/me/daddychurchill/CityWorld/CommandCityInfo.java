package me.daddychurchill.CityWorld;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCityInfo implements CommandExecutor {
	private final CityWorld	plugin;

	public CommandCityInfo(CityWorld plugin) {
		this.plugin = plugin;
	}

	public CityWorld getWorld() {
		return plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

			if (player.hasPermission("cityinfo.command")) {

				// find ourselves
				World world = player.getWorld();
				Location location = player.getLocation();
				Chunk chunk = world.getChunkAt(location);

				// get API instance
				CityWorldAPI api = new CityWorldAPI(plugin);
				// fetch full info hash
				HashMap<String, String> info = api.getFullInfo(chunk);
				// wite a message to player for each line
				for (Entry<String, String> entry : info.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					player.sendMessage(key + ": " + value);
				}

				// all done
				return true;

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
