package com.github.idimabr.raphaqueue.listeners;

import com.github.idimabr.raphaqueue.RaphaQueue;
import com.github.idimabr.raphaqueue.utils.BungeeUtils;
import com.github.idimabr.raphaqueue.utils.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private RaphaQueue plugin;
    private ConfigUtil config;

    public JoinListener(RaphaQueue plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfiguration();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        if(player.hasPermission(config.getString("permissionVIP"))){
            player.sendMessage(config.getString("Messages.VIPBypass").replace("&","§"));
            Bukkit.getScheduler().runTaskLater(RaphaQueue.getInstance(), () -> BungeeUtils.sendServer(player, config.getString("serverName")), 7);
            return;
        }

        if(BungeeUtils.getCount(config.getString("serverName")) < config.getInt("minPlayersToHaveQueue")) return;

        player.sendMessage( BungeeUtils.getCount(config.getString("serverName")) + "");

        plugin.getManager().addQueue(player);
        player.sendMessage(config.getString("Messages.EnterQueue").replace("&","§").replace("%position%", plugin.getManager().getQueue().size()+""));
    }
}
