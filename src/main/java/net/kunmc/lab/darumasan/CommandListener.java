package net.kunmc.lab.darumasan;

import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandListener implements CommandExecutor {
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
                            registerOniEvent(oni, locationOfCommandBlock);
                        }
                        break;
                    }
                }
            }
        }
        return false;
    }

    public void registerOniEvent(Player oni, Location locationOfCommandBlock) {
        new OniListener(plugin, oni, locationOfCommandBlock);
    }

}
