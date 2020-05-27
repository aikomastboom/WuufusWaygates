package com.github.jarada.waygates.callbacks;

import com.github.jarada.waygates.data.Gate;
import com.github.jarada.waygates.util.Util;
import org.bukkit.entity.Player;

public abstract class ChatCallback {

    private final Player player;
    private final Gate currentWaygate;

    public ChatCallback(Player player, Gate currentWaygate) {
        this.player = player;
        this.currentWaygate = currentWaygate;
    }

    public Player getPlayer() {
        return player;
    }

    public Gate getCurrentWaygate() {
        return currentWaygate;
    }

    public boolean isPlayerNearGate() {
        return Util.getNearbyPlayers(getCurrentWaygate().getCenterBlock().getLocation(), 5).contains(getPlayer());
    }

    public abstract boolean verify(String chat);

    public abstract void success(String chat);

    public abstract void failure();

    public abstract void callback();

}
