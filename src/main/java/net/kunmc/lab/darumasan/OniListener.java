package net.kunmc.lab.darumasan;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class OniListener implements Listener {
    private JavaPlugin plugin;
    private Player oni;
    private int yaw;
    private Location locationOfCommandBlock;
    private int beforeLetterCount;
    public OniListener(JavaPlugin plugin, Player oni, Location locationOfCommandBlock) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
        this.oni = oni;
        this.locationOfCommandBlock = locationOfCommandBlock;
        this.beforeLetterCount = 0;
        yaw = (int) Location.normalizeYaw(oni.getLocation().getYaw());
        sendTitleMessage("Start");
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        try {
            if (!event.getPlayer().getName().equals(oni.getName())) {
                return;
             }
            double distance = oni.getLocation().distance(locationOfCommandBlock);
            if (distance > 2) {
                DarumaSan.setIsOnCommandBlock(false);
                HandlerList.unregisterAll(plugin);
                return;
            }
            int yawNow = (int) Location.normalizeYaw(oni.getLocation().getYaw());
            int differenceOfYaw = Math.abs(yaw - yawNow);
            int end = DarumaSan.getDegree();
            if (end == 0) {
                return;
            }
            int unitOfCount = (int) end / 10;
            int letterCount;
            if (unitOfCount > differenceOfYaw)  {
                return;
            }
            if(differenceOfYaw >= 180) {
                differenceOfYaw = Math.abs(differenceOfYaw - 360);
            }
            letterCount = (int) differenceOfYaw / unitOfCount;
            if(differenceOfYaw >= end) {
                letterCount = 10;
            }
            if (letterCount == beforeLetterCount) {
                return;
            }
            beforeLetterCount = letterCount;
            //plugin.getServer().broadcastMessage(""+differenceOfYaw);
            final String title_message = "だるまさんがころんだ";
            sendTitleMessage(title_message.substring(0, letterCount));
            if (letterCount == 10) {
                HandlerList.unregisterAll(plugin);
                new PlayerListener(plugin, oni);
            }
        } catch (Exception e) {
            return;
        }
    }

    @EventHandler
    public void onPlayerDamageEvent(EntityDamageEvent event) {
        if(!(event.getEntity().getType() == EntityType.PLAYER)) {
            return;
        }
        Player player = (Player) event.getEntity();
        if(player.getName().equals(oni.getName())) {
            player.setHealth(0);
        }
    }

    @EventHandler
    private void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if(player.getName().equals(oni.getName())) {
            event.setDeathMessage(player.getName() + DarumaSan.getDeathLogOni());
        }
    }

    private void sendTitleMessage(String message) {
        plugin.getServer().getOnlinePlayers().forEach(player -> {
            player.resetTitle();
            if(DarumaSan.getSeCustom() != null && !message.equals("Start")) {
                player.getWorld().playSound(player.getLocation(), DarumaSan.getSeCustom() + "." + (message.length() - 1), 1, 1);
            } else {
                if(message.length() == 10) {
                    player.getWorld().playSound(player.getLocation(), Sound.valueOf(DarumaSan.getSeLast()), 1, 1);
                } else {
                    player.getWorld().playSound(player.getLocation(), Sound.valueOf(DarumaSan.getSe()), 1, 1);
                }
            }
            if(message.length() == 10) {
                player.sendTitle("§6" + message, "", 0, 70, 20);
                return;
            }
            player.sendTitle(message, "", 0, 70, 20);
        });
    }

}
