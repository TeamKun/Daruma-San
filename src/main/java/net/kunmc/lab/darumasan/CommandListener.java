package net.kunmc.lab.darumasan;

import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class CommandListener implements CommandExecutor, TabCompleter {
    private JavaPlugin plugin;

    public CommandListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("daruma")) {
            if(sender instanceof BlockCommandSender) {
                BlockCommandSender blockCommandSender = (BlockCommandSender) sender;
                Player oni;
                Entity[] entities = blockCommandSender.getBlock().getChunk().getEntities();
                for(Entity entity : entities) {
                    if(!(entity instanceof Player)) {
                        continue;
                    }
                    oni = (Player) entity;
                    Location locationOfCommandBlock = blockCommandSender.getBlock().getLocation();
                    double distance = oni.getLocation().distance(locationOfCommandBlock);
                    if(2 > distance) {
                        if(!DarumaSan.getIsOnCommandBlock()) {
                            DarumaSan.setIsOnCommandBlock(true);
                            new OniListener(plugin, oni, locationOfCommandBlock);
                        }
                        break;
                    }
                }
            }
            if(sender instanceof Player) {
                if(args.length != 1) {
                    return false;
                }
                plugin.getServer().getOnlinePlayers().forEach(player -> {
                    if (player.getName().equals(args[0])) {
                        new OniListener(plugin, player, player.getLocation());
                    }
                });
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equals("daruma")) {
            if(args.length == 1) {
                ArrayList<String> players = new ArrayList<String>();
                plugin.getServer().getOnlinePlayers().forEach(player -> {
                    players.add(player.getName());
                });
                return players;
            }
        }
        return plugin.onTabComplete(sender, command, label, args);
    }

}
