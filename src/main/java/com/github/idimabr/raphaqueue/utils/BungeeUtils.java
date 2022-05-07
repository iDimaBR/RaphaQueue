package com.github.idimabr.raphaqueue.utils;

import com.github.idimabr.raphaqueue.RaphaQueue;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class BungeeUtils {

    public static void sendServer(Player player, String server) {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            out.writeUTF("Connect");
            out.writeUTF(server);
            player.sendPluginMessage(RaphaQueue.getInstance(), "BungeeCord", b.toByteArray());
            b.close();
            out.close();
        }
        catch (Exception e) {
            player.sendMessage("§cErro ao tentar conectar ao servidor " + server);
        }
    }

    public static int getCount(String server){
        return RaphaQueue.getInstance().getServers().getOrDefault(server, 0);
    }

    public static void refreshCount(String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF(server);
        Bukkit.getServer().sendPluginMessage(RaphaQueue.getInstance(), "BungeeCord", out.toByteArray());
    }

}
