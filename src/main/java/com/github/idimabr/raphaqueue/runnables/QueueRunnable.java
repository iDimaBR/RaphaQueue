package com.github.idimabr.raphaqueue.runnables;

import com.github.idimabr.raphaqueue.RaphaQueue;
import com.github.idimabr.raphaqueue.utils.BungeeUtils;
import com.github.idimabr.raphaqueue.utils.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

public class QueueRunnable extends BukkitRunnable {

    private RaphaQueue plugin;
    private ConfigUtil config;

    public QueueRunnable(RaphaQueue plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfiguration();
    }
    
    @Override
    public void run() {
        BungeeUtils.refreshCount(config.getString("serverName"));

        int position = -1;
        Player toRemove = null;
        for (Map.Entry<UUID, Integer> entry : plugin.getManager().getQueue().entrySet()) {
            Player player = Bukkit.getPlayer(entry.getKey());

            position++;
            if(position == 0) {
                if (player == null) continue;
                player.sendMessage(config.getString("Messages.Connecting").replace("&","§"));

                BungeeUtils.sendServer(player, config.getString("serverName"));
                toRemove = player;
                continue;
            }
            player.sendMessage(config.getString("Messages.Queue").replace("&","§").replace("%position%", (plugin.getManager().getQueue().size() - 1)+""));
        }

        if(toRemove == null) return;
        plugin.getManager().removeQueue(toRemove);
    }
    
}
