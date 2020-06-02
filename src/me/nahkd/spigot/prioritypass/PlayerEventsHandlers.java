package me.nahkd.spigot.prioritypass;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class PlayerEventsHandlers implements Listener {
	
	private PriorityPass plugin;
	
	public PlayerEventsHandlers(PriorityPass plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		int loggedRegularPlayers = plugin.getServer().getOnlinePlayers().size() - plugin.loggedPriorityPlayers.size();
		
		if (event.getPlayer().hasPermission(PriorityPass.PERMISSION)) {
			event.allow();
			plugin.logger.sendMessage("§bPriorityPass §7>> §f" + event.getPlayer().getName() + " used their priority pass.");
			plugin.loggedPriorityPlayers.add(event.getPlayer().getUniqueId());
		} else if (loggedRegularPlayers >= plugin.defaultMaxPlayers) {
			event.disallow(Result.KICK_FULL, plugin.kickMessage);
		} else event.allow();
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		if (plugin.loggedPriorityPlayers.contains(event.getPlayer().getUniqueId())) {
			plugin.loggedPriorityPlayers.remove(event.getPlayer().getUniqueId());
		}
	}
	
}
