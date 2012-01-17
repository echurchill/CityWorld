package me.daddychurchill.CityWorld;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CityWorldBlockCMD implements CommandExecutor {
    private final CityWorld plugin;

	public CityWorldBlockCMD(CityWorld plugin)
    {
        this.plugin = plugin;
    }

	public CityWorld getWorld() {
		return plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
//		// validate the player
//		if (!(sender instanceof Player)) {
//			sender.sendMessage(String.format("%s is only usable by a player", label));
//			return true;
//		} 
//		Player player = (Player) sender;
//		if (player.getGameMode() != GameMode.CREATIVE) {
//			sender.sendMessage(String.format("%s is only usable by a player in Creative mode", label));
//			return true;
//		} 
//		
//		// validate the argument count
//		if (split.length != 1) {
//			return false; // false will report out the syntax
//		}
//		player.sendMessage("split[0] = " + split[0]);
//		
//		// validate the world the player is in
//		World playerWorld = player.getWorld();
//		ChunkGenerator generator = playerWorld.getGenerator();
//		if (!(generator instanceof CityWorldChunkGenerator)) {
//			sender.sendMessage(String.format("%s is only usable by a player in CityWorld", label));
//			return true;
//		} 
//		
//		CityWorld playerCity = ((CityWorldChunkGenerator) generator).getWorld();
//		Chunk playerChunk = player.getLocation().getChunk();
//		int chunkX = playerChunk.getX();
//		int chunkZ = playerChunk.getZ();
//		PlatMap platmap = playerCity.getPlatMap(playerWorld, null, chunkX, chunkZ);
//		
//		
//		player.sendMessage("DO: " + split[0]);
		sender.sendMessage("Not working yet");
		return true;
	}
}
