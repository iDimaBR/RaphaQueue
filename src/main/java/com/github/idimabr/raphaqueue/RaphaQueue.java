package com.github.idimabr.raphaqueue;

import com.github.idimabr.raphaqueue.listeners.JoinListener;
import com.github.idimabr.raphaqueue.managers.QueueManager;
import com.github.idimabr.raphaqueue.runnables.QueueRunnable;
import com.github.idimabr.raphaqueue.utils.ConfigUtil;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.HashMap;
import java.util.Map;

public final class RaphaQueue extends JavaPlugin implements PluginMessageListener {

    private QueueManager manager;
    private Map<String, Integer> servers = new HashMap<>();
    private ConfigUtil config;

    public static RaphaQueue getInstance() {
        return getPlugin(RaphaQueue.class);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        config = new ConfigUtil(null, "config.yml", false);
        Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

        manager = new QueueManager(this);
        config.saveConfig();

        new QueueRunnable(this).runTaskTimerAsynchronously(this, 20L * config.getInt("delayToJoin"), 20L * config.getInt("delayToJoin"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("PlayerCount")) {
            String server = in.readUTF();
            int playerCount = in.readInt();
            servers.put(server, playerCount);
        }
    }

    public ConfigUtil getConfiguration() {
        return config;
    }

    public Map<String, Integer> getServers() {
        return servers;
    }

    public QueueManager getManager() {
        return manager;
    }
}
