package me.nahkd.spigot.prioritypass;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PriorityPass extends JavaPlugin {
	
	public static final String PERMISSION = "prioritypass.pass";
	
	public ConsoleCommandSender logger;
	public int defaultMaxPlayers;
	public HashSet<UUID> loggedPriorityPlayers;
	public String kickMessage = "";
	
	@Override
	public void onEnable() {
		logger = getServer().getConsoleSender();
		logger.sendMessage("§bPriorityPass §7>> §fEnabling plugin...");
		// General thing
		defaultMaxPlayers = getServer().getMaxPlayers();
		loggedPriorityPlayers = new HashSet<UUID>();
		
		// Config file
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
			saveResource("config.yml", false);
			logger.sendMessage("§bPriorityPass §7>> §fSaved resource.");
			reloadConfig();
		}
		getConfig().getStringList("Kick Message").forEach((line) -> {kickMessage += line + "\n";});
		kickMessage = kickMessage.substring(0, kickMessage.length() - 1);
		
		if (getServer().getOnlinePlayers().size() > 0) {
			logger.sendMessage("§bPriorityPass §7>> §eIt's appear that you've used /reload... Don't do that my dude.");
			for (Player player : getServer().getOnlinePlayers()) if (player.hasPermission(PERMISSION)) {
				loggedPriorityPlayers.add(player.getUniqueId());
			}
		}
		
		// Events
		getServer().getPluginManager().registerEvents(new PlayerEventsHandlers(this), this);
		logger.sendMessage("§bPriorityPass §7>> §fDone! Enjoy.");
	}
	
}
