package de.stormfox2.core.player;

import de.stormfox2.core.ban.BanManager;
import de.stormfox2.core.mysql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CorePlayer {
    private Player player;
    UUID uuid;
    MySQL mySQL;
    int bannpoints;
    int money;

    public CorePlayer(){  };
    public CorePlayer(Player player){
        init(player);
    }
    public CorePlayer(UUID uuid) {
        if(Bukkit.getPlayer(uuid) != null)
            init(Bukkit.getPlayer(uuid));
        else
            init(uuid);
    }

    private void init(Player player){
        this.player = player;
    }

    private void init(UUID uuid){
        this.player = Bukkit.getPlayer(uuid);
        bannpoints = BanManager.getBannPoints(this);

    }

    public UUID getUUID() {
        return player.getUniqueId();
    }
}
