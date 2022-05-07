package com.github.idimabr.raphaqueue.managers;

import com.github.idimabr.raphaqueue.RaphaQueue;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class QueueManager {

    private RaphaQueue plugin;
    private HashMap<UUID, Integer> queue = new HashMap<>();

    public QueueManager(RaphaQueue plugin) {
        this.plugin = plugin;
    }

    public void addQueue(Player player){
        this.queue.put(player.getUniqueId(), (this.queue.size() + 1));
    }

    public void removeQueue(Player player){
        this.queue.remove(player.getUniqueId());
    }

    public HashMap<UUID, Integer> getQueue() {
        return queue;
    }
}
