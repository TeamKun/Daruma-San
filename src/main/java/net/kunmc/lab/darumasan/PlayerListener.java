package net.kunmc.lab.darumasan;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class PlayerListener implements Listener {
    private JavaPlugin plugin;
    private Player oni;
    private HashMap<String, Location> playersFirstLoc;
    private Timer timer;
    public PlayerListener(JavaPlugin plugin, Player oni) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
        this.oni = oni;
        playersFirstLoc = new HashMap<String, Location>();
        plugin.getServer().getOnlinePlayers().forEach(player -> {
            playersFirstLoc.put(player.getName(), player.getLocation());
        });
        timer = new Timer(DarumaSan.getJudgePeriod(), plugin);
        timer.runTaskTimer(plugin, 0, 20L);
    }

    public void setPlayerFirstLoc(Player player, Location loc) {
        playersFirstLoc.put(player.getName(), loc);
    }

    public Location getPlayerFirstLoc(Player player) {
        if(playersFirstLoc.containsKey(player.getName())) {
            return playersFirstLoc.get(player.getName());
        }
        setPlayerFirstLoc(player, player.getLocation());
        return player.getLocation();
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        try {
            Player target = event.getPlayer();
            if (target.getPlayer().getName().equals(oni.getName())) {
                return;
             }
            double distance = target.getLocation().distance(getPlayerFirstLoc(target));
            if (distance > 1) {
                target.setHealth(0);
                return;
            }
            int yaw = (int) Location.normalizeYaw(getPlayerFirstLoc(target).getYaw());
            int yawNow = (int) Location.normalizeYaw(target.getLocation().getYaw());
            int differenceOfYaw = Math.abs(yaw - yawNow);
            if(differenceOfYaw > 90) {
                target.setHealth(0);
                return;
            }
        } catch (Exception e) {
            return;
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if(player.getName().equals(oni.getName())) {
            event.setDeathMessage(player.getName() + DarumaSan.getDeathLogOni());
        } else if (DarumaSan.getSeCustom() != null) {
            player.getWorld().playSound(player.getLocation(), DarumaSan.getSeCustom() + "." + "death", 1, 1);
        }
        event.setDeathMessage(player.getName() + DarumaSan.getDeathLogPlayer());
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if(!(event.getEntity().getType() == EntityType.PLAYER)) {
            return;
        }
        Player player = (Player) event.getEntity();
        if(player.getName().equals(oni.getName())) {
            event.setCancelled(true);
        }
    }


    static class Timer extends BukkitRunnable {
        private int sec;
        private JavaPlugin plugin;

        public Timer(int sec, JavaPlugin plugin) {
            this.sec = sec;
            this.plugin = plugin;
        }

        @Override
        public void run() {
            plugin.getServer().getOnlinePlayers().forEach(player -> {
                player.resetTitle();
                player.sendTitle("§6だるまさんがころんだ", "§f判定終わりまで残り§4" + sec + "秒", 0, 20 * DarumaSan.getJudgePeriod(), 20);
            });
            sec--;
            if (0 > sec) {
                DarumaSan.setIsOnCommandBlock(false);
                HandlerList.unregisterAll(plugin);
                cancel();
                    return;
            }
        }
    }

}
