package net.kunmc.lab.darumasan;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class OniListener implements Listener {
    private JavaPlugin plugin;
    private Player oni;
    private float yaw;
    private Location locationOfCommandBlock;
    public OniListener(JavaPlugin plugin, Player oni, Location locationOfCommandBlock) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
        this.oni = oni;
        this.locationOfCommandBlock = locationOfCommandBlock;
        yaw = oni.getLocation().getYaw();
        DarumaSan.setIsOnCommandBlock(true);
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        if(!event.getPlayer().getName().equals(oni.getName())) {
            return;
        }
        if(oni.getLocation().distance(locationOfCommandBlock) > 2) {
            unregisterOniEvent();
            return;
        }
        float yaw_now = oni.getLocation().getYaw();
        float differenceOfYaw = Math.abs(yaw - yaw_now);
        int end = 90;
        int letterCount = 0;
        if(5 > differenceOfYaw) {
            letterCount = 0;
        } else if(10 > differenceOfYaw) {
            letterCount = 1;
        } else if(differenceOfYaw > end) {
            letterCount = 10;
        }
        else {
            int adjustCount = 10 > end - 90 ? 0 : (int) Math.floor((end - 90) / 10);
            letterCount = (int) Math.floor(differenceOfYaw / (10 + adjustCount)) + 1;
        }
        final String title_message = "だるまさんがころんだ";
        sendTitleMessage(title_message.substring(0, letterCount));
        if(letterCount == 10) {
            unregisterOniEvent();
        }
    }

    private void sendTitleMessage(String message) {
        plugin.getServer().getOnlinePlayers().forEach(player -> {
            player.resetTitle();
            if(message.length() == 0) {
                return;
            }
            if(message.length() == 10) {
                player.sendTitle("§6" + message, "", 0, 70, 0);
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
                return;
            }
            player.sendTitle(message, "", 0, 70, 0);
            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
        });
    }

    public void unregisterOniEvent() {
        DarumaSan.setIsOnCommandBlock(false);
        HandlerList.unregisterAll(plugin);
    }
}
